import java.util.function.Predicate;

public class Task2 {
    public static void main(String[] args) {
        Predicate<String> startsWithJorN = str -> str.startsWith("J") || str.startsWith("N");
        Predicate<String> endsWithA = str -> str.endsWith("A");
        
        Predicate<String> combinedCheck = startsWithJorN.and(endsWithA);
        
        // Тестирование
        System.out.println("=== Проверка паттерна ===");
        System.out.println("JAVA: " + combinedCheck.test("JAVA"));
        System.out.println("NATA: " + combinedCheck.test("NATA"));
        System.out.println("TEST: " + combinedCheck.test("TEST"));
    }
}