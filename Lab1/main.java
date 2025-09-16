import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        System.out.println("=== Задание 1 ===");
        task1();
        
        System.out.println("\n=== Задание 2 ===");
        task2();
        
        System.out.println("\n=== Задание 3 ===");
        task3();
        
        System.out.println("\n=== Задание 4 ===");
        task4();
        
        System.out.println("\n=== Задание 5 ===");
        task5();
    }
    
    // Задание 1: FizzBuzz
    public static void task1() {
        for (int i = 1; i <= 500; i++) {
            if (i % 5 == 0 && i % 7 == 0) {
                System.out.println("fizzbuzz");
            } else if (i % 5 == 0) {
                System.out.println("fizz");
            } else if (i % 7 == 0) {
                System.out.println("buzz");
            } else {
                System.out.println(i);
            }
        }
    }
    
    // Задание 2: Reverse string
    public static void task2() {
        String str = "make install";
        System.out.println("Исходная строка: " + str);
        
        System.out.print("Перевернутая строка: ");
        for (int i = str.length() - 1; i >= 0; i--) {
            System.out.print(str.charAt(i));
        }
        System.out.println();
    }
    
    // Задание 3: Квадратное уравнение
    public static void task3() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Решение квадратного уравнения ax² + bx + c = 0");
        System.out.print("Введите коэффициент a: ");
        double a = scanner.nextDouble();
        System.out.print("Введите коэффициент b: ");
        double b = scanner.nextDouble();
        System.out.print("Введите коэффициент c: ");
        double c = scanner.nextDouble();
        
        double discriminant = b * b - 4 * a * c;
        
        if (discriminant > 0) {
            double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            System.out.printf("Корни уравнения: x1 = %.2f, x2 = %.2f%n", x1, x2);
        } else if (discriminant == 0) {
            double x = -b / (2 * a);
            System.out.printf("Уравнение имеет один корень: x = %.2f%n", x);
        } else {
            System.out.println("Нет вещественных корней");
        }
    }
    
    // Задание 4: Сумма ряда
    public static void task4() {
        double sum = 0.0;
        double term;
        int n = 2;
        
        do {
            term = 1.0 / (n * n + n - 2);
            sum += term;
            n++;
        } while (term >= 1e-6);
        
        System.out.printf("Сумма ряда: %.8f%n", sum);
        System.out.println("Количество просуммированных членов: " + (n - 2));
    }
    
    // Задание 5: Палиндром
    public static void task5() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите строку для проверки на палиндром: ");
        String input = scanner.nextLine();
        
        String cleaned = input.replaceAll("\\s+", "").toLowerCase();
        
        boolean isPalindrome = true;
        int left = 0;
        int right = cleaned.length() - 1;
        
        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                isPalindrome = false;
                break;
            }
            left++;
            right--;
        }
        
        if (isPalindrome) {
            System.out.println("Строка \"" + input + "\" является палиндромом");
        } else {
            System.out.println("Строка \"" + input + "\" не является палиндромом");
        }
    }
}