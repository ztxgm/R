import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

public class Task6 {
    
    // a. Среднее значение списка целых чисел
    public static OptionalDouble average(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average();
    }
    
    // b. Строки в верхнем регистре с префиксом
    public static List<String> toUpperCaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(str -> "new" + str.toUpperCase())
                .collect(Collectors.toList());
    }
    
    // c. Квадраты уникальных элементов
    public static List<Integer> squaresOfUnique(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(entry -> entry.getKey() * entry.getKey())
                .collect(Collectors.toList());
    }
    
    // d. Строки начинающиеся с заданной буквы, отсортированные
    public static List<String> filterAndSort(Collection<String> strings, char letter) {
        return strings.stream()
                .filter(str -> !str.isEmpty() && str.charAt(0) == letter)
                .sorted()
                .collect(Collectors.toList());
    }
    
    // e. Последний элемент коллекции
    public static <T> T getLastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Коллекция пуста"));
    }
    
    // f. Сумма чётных чисел
    public static int sumEvenNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }
    
    // g. Преобразование строк в Map
    public static Map<Character, String> stringsToMap(List<String> strings) {
        return strings.stream()
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toMap(
                    str -> str.charAt(0),
                    str -> str.length() > 1 ? str.substring(1) : "",
                    (existing, replacement) -> existing
                ));
    }
    
    public static void main(String[] args) {
        // Тестирование методов
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        List<String> strings = Arrays.asList("apple", "banana", "avocado", "cherry");
        
        System.out.println("a. Среднее: " + average(numbers));
        System.out.println("b. С префиксом: " + toUpperCaseWithPrefix(strings));
        System.out.println("c. Квадраты уникальных: " + squaresOfUnique(numbers));
        System.out.println("d. С буквы 'a': " + filterAndSort(strings, 'a'));
        System.out.println("e. Последний элемент: " + getLastElement(strings));
        
        int[] arr = {1, 2, 3, 4, 5, 6};
        System.out.println("f. Сумма чётных: " + sumEvenNumbers(arr));
        System.out.println("g. Map из строк: " + stringsToMap(strings));
    }
}