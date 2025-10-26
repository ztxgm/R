import java.util.*;

public class Task5 {
    public static void main(String[] args) {
        System.out.println("=== 5: Инвертирование Map ===");
        demonstrateMapInversion();
    }
    
    public static <K, V> Map<V, Collection<K>> invertMap(Map<K, V> map) {
        Map<V, Collection<K>> result = new HashMap<>();
        
        for (Map.Entry<K, V> entry : map.entrySet()) {
            result.computeIfAbsent(entry.getValue(), k -> new ArrayList<>())
                  .add(entry.getKey());
        }
        
        return result;
    }
    
    public static void demonstrateMapInversion() {
        // Тест 1: Map с дублирующимися значениями
        Map<String, Integer> testMap1 = new HashMap<>();
        testMap1.put("a", 1);
        testMap1.put("b", 2);
        testMap1.put("c", 1);
        testMap1.put("d", 3);
        testMap1.put("e", 2);
        
        System.out.println("Исходная Map: " + testMap1);
        Map<Integer, Collection<String>> inverted1 = invertMap(testMap1);
        System.out.println("Инвертированная Map: " + inverted1);
        
        // Тест 2: Map с уникальными значениями
        Map<String, String> testMap2 = new HashMap<>();
        testMap2.put("Россия", "Москва");
        testMap2.put("Франция", "Париж");
        testMap2.put("Германия", "Берлин");
        
        System.out.println("\nИсходная Map: " + testMap2);
        Map<String, Collection<String>> inverted2 = invertMap(testMap2);
        System.out.println("Инвертированная Map: " + inverted2);
        
        // Проверка результатов
        System.out.println("\nПроверка результатов:");
        System.out.println("Для значения 1 ожидаем ключи [a, c]: " + inverted1.get(1));
        System.out.println("Для значения 2 ожидаем ключи [b, e]: " + inverted1.get(2));
        System.out.println("Для значения 'Москва' ожидаем ключи [Россия]: " + inverted2.get("Москва"));
    }
}