import java.util.function.Supplier;
import java.util.Random;

public class Task5 {
    public static void main(String[] args) {
        Supplier<Integer> randomSupplier = () -> new Random().nextInt(11);
        
        System.out.println("Случайные числа от 0 до 10:");
        for (int i = 0; i < 5; i++) {
            System.out.println(randomSupplier.get());
        }
    }
}