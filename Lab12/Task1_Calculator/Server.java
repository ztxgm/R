import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Сервер запущен. Ожидание подключений...");
            
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    
                    System.out.println("Клиент подключен: " + clientSocket.getInetAddress());
                    
                    String input;
                    while ((input = in.readLine()) != null) {
                        System.out.println("Получено от клиента: " + input);
                        
                        if (input.equalsIgnoreCase("exit")) {
                            System.out.println("Клиент отключился");
                            break;
                        }
                        
                        try {
                            String result = calculate(input);
                            out.println(result);
                            System.out.println("Отправлено клиенту: " + result);
                        } catch (Exception e) {
                            out.println("Ошибка: " + e.getMessage());
                            System.out.println("Ошибка вычисления: " + e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка соединения с клиентом: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка сервера: " + e.getMessage());
        }
    }
    
    private static String calculate(String expression) {
        expression = expression.trim();
        
        // Проверяем арифметических операций
        String[] parts;
        double a, b;
        
        if (expression.contains("+")) {
            parts = expression.split("\\+");
            a = Double.parseDouble(parts[0].trim());
            b = Double.parseDouble(parts[1].trim());
            return String.valueOf(a + b);
        } else if (expression.contains("-")) {
            parts = expression.split("-");
            // Учитываем отрицательные числа
            if (expression.startsWith("-")) {
                String[] temp = expression.substring(1).split("-", 2);
                a = -Double.parseDouble(temp[0].trim());
                if (temp.length > 1) {
                    b = Double.parseDouble(temp[1].trim());
                } else {
                    throw new IllegalArgumentException("Некорректное выражение");
                }
            } else {
                a = Double.parseDouble(parts[0].trim());
                b = Double.parseDouble(parts[1].trim());
            }
            return String.valueOf(a - b);
        } else if (expression.contains("*")) {
            parts = expression.split("\\*");
            a = Double.parseDouble(parts[0].trim());
            b = Double.parseDouble(parts[1].trim());
            return String.valueOf(a * b);
        } else if (expression.contains("/")) {
            parts = expression.split("/");
            a = Double.parseDouble(parts[0].trim());
            b = Double.parseDouble(parts[1].trim());
            
            if (b == 0) {
                throw new ArithmeticException("Деление на ноль невозможно");
            }
            
            return String.valueOf(a / b);
        } else {
            throw new IllegalArgumentException("Некорректная операция. Используйте +, -, *, /");
        }
    }
}