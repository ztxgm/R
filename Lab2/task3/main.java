import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Введите число и степень: ");
        double a = scanner.nextDouble();
        int n = scanner.nextInt();
        
        double result = powerIterative(a, n);
        System.out.printf("%.2f^%d = %.6f%n", a, n, result);
        scanner.close();
    }
    
    public static double powerIterative(double a, int n) {
        if (n == 0) return 1;
        if (a == 0) return 0;
        
        double result = 1;
        int absN = Math.abs(n);
        
        for (int i = 0; i < absN; i++) {
            result *= a;
        }
        
        return n > 0 ? result : 1 / result;
    }
}