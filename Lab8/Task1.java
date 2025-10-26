import java.util.function.Predicate;

public class Task1 {
    public static void main(String[] args) {
        // a. Проверка что строка не null
        Predicate<String> isNotNull = str -> str != null;
        
        // b. Проверка что строка не пуста
        Predicate<String> isNotEmpty = str -> !str.isEmpty();
        
        // c. Комбинированная проверка
        Predicate<String> isNotNullAndNotEmpty = isNotNull.and(isNotEmpty);
        
        // Тестирование
        System.out.println("=== Проверка строк ===");
        System.out.println("null: " + isNotNullAndNotEmpty.test(null));
        System.out.println("пустая строка: " + isNotNullAndNotEmpty.test(""));
        System.out.println("текст: " + isNotNullAndNotEmpty.test("Hello"));
    }
}