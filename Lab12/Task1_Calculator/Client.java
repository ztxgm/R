import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Подключено к серверу");
            System.out.println("Введите математическое выражение");
            System.out.println("Поддерживаемые операции: +, -, *, /");
            System.out.println("Для выхода введите 'exit'");
            System.out.println("================================");
            
            while (true) {
                System.out.print("Введите выражение: ");
                String expression = scanner.nextLine();
                
                if (expression.equalsIgnoreCase("exit")) {
                    out.println("exit");
                    break;
                }
                
                // Удаляем пробелы на всякий
                expression = expression.replaceAll("\\s+", "");
                
                out.println(expression);
                
                String response = in.readLine();
                System.out.println("Результат: " + response);
                System.out.println("------------------------");
            }
            
            System.out.println("Соединение закрыто");
        } catch (UnknownHostException e) {
            System.out.println("Хост не найден: " + host);
        } catch (IOException e) {
            System.out.println("Ошибка подключения: " + e.getMessage());
        }
    }
}