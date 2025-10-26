import java.util.function.Function;

public class Task4 {
    public static void main(String[] args) {
        Function<Integer, String> checkNumber = num -> {
            if (num > 0) return "Положительное число";
            else if (num < 0) return "Отрицательное число";
            else return "Ноль";
        };
        
        // Тестирование
        System.out.println("5: " + checkNumber.apply(5));
        System.out.println("-3: " + checkNumber.apply(-3));
        System.out.println("0: " + checkNumber.apply(0));
    }
}