import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Введите три стороны треугольника: ");
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();
        
        triangle(a, b, c);
        scanner.close();
    }
    
    public static void triangle(double a, double b, double c) {
        if (a + b > c && a + c > b && b + c > a) {
            System.out.println("Это треугольник");
        } else {
            System.out.println("Это не треугольник");
        }
    }
}