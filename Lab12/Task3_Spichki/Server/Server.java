import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int TOTAL_MATCHES = 37;
    private static int remainingMatches = TOTAL_MATCHES;
    private static int currentPlayer = 1; // 1 или 2
    private static boolean gameFinished = false;
    
    private static List<ClientHandler> clients = new ArrayList<>();
    private static int connectedPlayers = 0;
    
    public static void main(String[] args) {
        int port = 5555;
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер игры запущен на порту " + port);
            System.out.println("Ожидание подключения двух игроков...");
            
            // Ожидаем подключения двух игроков
            while (connectedPlayers < 2) {
                Socket clientSocket = serverSocket.accept();
                connectedPlayers++;
                System.out.println("Игрок " + connectedPlayers + " подключен");
                
                ClientHandler clientHandler = new ClientHandler(clientSocket, connectedPlayers);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
                
                // Отправляем игроку его номер
                clientHandler.sendMessage("PLAYER_NUMBER:" + connectedPlayers);
                
                if (connectedPlayers == 2) {
                    System.out.println("Оба игрока подключены. Начинаем игру!");
                    broadcastMessage("GAME_START:" + TOTAL_MATCHES);
                    broadcastMessage("TURN:1"); // Первый ход у игрока 1
                    broadcastMessage("STATUS:Игра началась. Осталось спичек: " + TOTAL_MATCHES);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
    
    private static synchronized void processMove(int playerNumber, int matchesTaken) {
        if (gameFinished) return;
        if (playerNumber != currentPlayer) {
            clients.get(playerNumber - 1).sendMessage("ERROR:Сейчас не ваш ход");
            return;
        }
        
        if (matchesTaken < 1 || matchesTaken > 5) {
            clients.get(playerNumber - 1).sendMessage("ERROR:Можно брать от 1 до 5 спичек");
            return;
        }
        
        if (matchesTaken > remainingMatches) {
            clients.get(playerNumber - 1).sendMessage("ERROR:Нельзя взять больше спичек, чем осталось");
            return;
        }
        
        // Обновляем состояние игры
        remainingMatches -= matchesTaken;
        
        System.out.println("Игрок " + playerNumber + " взял " + matchesTaken + " спичек. Осталось: " + remainingMatches);
        
        // Проверяем конец игры
        if (remainingMatches == 0) {
            gameFinished = true;
            broadcastMessage("GAME_OVER:Игрок " + playerNumber + " победил!");
            broadcastMessage("WINNER:" + playerNumber);
            
            // Определяем проигравшего
            int loser = (playerNumber == 1) ? 2 : 1;
            clients.get(playerNumber - 1).sendMessage("RESULT:ПОБЕДА! Вы взяли последнюю спичку!");
            clients.get(loser - 1).sendMessage("RESULT:ПОРАЖЕНИЕ! Игрок " + playerNumber + " взял последнюю спичку.");
            
            // Закрываем соединения
            try {
                Thread.sleep(5000); // Даем время прочитать результат
                for (ClientHandler client : clients) {
                    client.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        
        // Меняем ход
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        
        // Отправляем обновления всем игрокам
        broadcastMessage("UPDATE:" + remainingMatches);
        broadcastMessage("TURN:" + currentPlayer);
        broadcastMessage("STATUS:Игрок " + playerNumber + " взял " + matchesTaken + 
                        " спичек. Осталось: " + remainingMatches + ". Ход игрока " + currentPlayer);
    }
    
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private int playerNumber;
        
        public ClientHandler(Socket socket, int playerNumber) {
            this.socket = socket;
            this.playerNumber = playerNumber;
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        @Override
        public void run() {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null && !gameFinished) {
                    if (inputLine.startsWith("MOVE:")) {
                        try {
                            int matchesTaken = Integer.parseInt(inputLine.substring(5));
                            processMove(playerNumber, matchesTaken);
                        } catch (NumberFormatException e) {
                            sendMessage("ERROR:Некорректное число спичек");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Игрок " + playerNumber + " отключился");
            } finally {
                close();
            }
        }
        
        public void sendMessage(String message) {
            if (out != null) {
                out.println(message);
            }
        }
        
        public void close() {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}