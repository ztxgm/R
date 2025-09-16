import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Введите число и степень: ");
        double a = scanner.nextDouble();
        int n = scanner.nextInt();
        
        double result = powerRecursive(a, n);
        System.out.printf("%.2f^%d = %.6f%n", a, n, result);
        scanner.close();
    }
    
    public static double powerRecursive(double a, int n) {
        if (n == 0) return 1;
        if (n == 1) return a;
        if (n == -1) return 1 / a;
        if (n > 0) {
            return a * powerRecursive(a, n - 1);
        } else {
            return (1 / a) * powerRecursive(a, n + 1);
        }
    }
}