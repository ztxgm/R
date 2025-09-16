import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Введите номер числа Трибоначчи: ");
        int n = scanner.nextInt();
        
        int result = tribonacci(n);
        System.out.println("tribonacci(" + n + ") = " + result);
        scanner.close();
    }
    
    public static int tribonacci(int n) {
        // Базовые
        if (n == 0) return 0;
        if (n == 1) return 0;
        if (n == 2) return 1;
        
        // Рекурсивный
        return tribonacci(n - 1) + tribonacci(n - 2) + tribonacci(n - 3);
    }
}