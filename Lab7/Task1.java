import java.util.*;

public class Task1 {
    public static void main(String[] args) {
        System.out.println("=== 1: Методы класса Collections ===");
        demonstrateCollectionsMethods();
    }
    
    public static void demonstrateCollectionsMethods() {
        // a) Создание массива
        Integer[] array = {5, 3, 8, 1, 3, 9, 2, 5, 7, 1};
        System.out.println("a) Исходный массив: " + Arrays.toString(array));
        
        // b) Создание списка на основе массива
        List<Integer> list = new ArrayList<>(Arrays.asList(array));
        System.out.println("b) Список из массива: " + list);
        
        // c) Сортировка в натуральном порядке
        Collections.sort(list);
        System.out.println("c) После сортировки: " + list);
        
        // d) Сортировка в обратном порядке
        Collections.sort(list, Collections.reverseOrder());
        System.out.println("d) Обратная сортировка: " + list);
        
        // e) Перемешивание
        Collections.shuffle(list);
        System.out.println("e) После перемешивания: " + list);
        
        // f) Циклический сдвиг на 1
        Collections.rotate(list, 1);
        System.out.println("f) После сдвига на 1: " + list);
        
        // g) Уникальные элементы
        Set<Integer> uniqueSet = new LinkedHashSet<>(list);
        List<Integer> uniqueList = new ArrayList<>(uniqueSet);
        System.out.println("g) Уникальные элементы: " + uniqueList);
        
        // h) Дублирующиеся элементы
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        for (Integer num : list) {
            if (!seen.add(num)) duplicates.add(num);
        }
        System.out.println("h) Дубликаты: " + duplicates);
        
        // i) Преобразование в массив
        Integer[] newArray = list.toArray(new Integer[0]);
        System.out.println("i) Новый массив: " + Arrays.toString(newArray));
    }
}