import java.util.*;

public class Task4 {
    public static void main(String[] args) {
        System.out.println("=== 4: Подсчет частоты слов ===");
        demonstrateWordFrequency();
    }
    
    public static void demonstrateWordFrequency() {
        String text = "I am so lazy to come up with checks. I wonder if will check the labs manually or not. Yes yes Yes yes No Laaabaaaa.";
        System.out.println("Исходный текст: " + text);
        
        Map<String, Integer> frequencyMap = new HashMap<>();
        
        // Разделяем на слова, удаляя знаки препинания
        String[] words = text.split("\\W+");
        
        for (String word : words) {
            if (!word.isEmpty()) {
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }
        
        System.out.println("\nЧастота слов (регистр учитывается):");
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            System.out.printf("Слово '%s': %d раз(а)%n", entry.getKey(), entry.getValue());
        }
        
        // Проверка работы
        System.out.println("\nПроверка:");
        System.out.println("Слово 'Yes' встречается " + frequencyMap.get("Yes") + " раз(а)");
        System.out.println("Слово 'yes' встречается " + frequencyMap.get("yes") + " раз(а)");
        System.out.println("Слово 'I' встречается " + frequencyMap.get("I") + " раз(а)");
        System.out.println("Слово 'wonder' встречается " + frequencyMap.get("wonder") + " раз(а)");
    }
}