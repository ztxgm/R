import java.util.*;

class PrimesGenerator implements Iterator<Long> {
    private long current = 2;
    private final int count;
    private int generated = 0;
    
    public PrimesGenerator(int count) {
        this.count = count;
    }
    
    private boolean isPrime(long n) {
        if (n < 2) return false;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
    
    @Override
    public boolean hasNext() {
        return generated < count;
    }
    
    @Override
    public Long next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        while (!isPrime(current)) {
            current++;
        }
        long prime = current;
        current++;
        generated++;
        return prime;
    }
}

public class Task2 {
    public static void main(String[] args) {
        System.out.println("=== 2: Генератор простых чисел ===");
        demonstratePrimesGenerator();
    }
    
    public static void demonstratePrimesGenerator() {
        int n = 15;
        System.out.println("Генерация первых " + n + " простых чисел:");
        
        PrimesGenerator gen = new PrimesGenerator(n);
        
        // Прямой порядок
        List<Long> primes = new ArrayList<>();
        while (gen.hasNext()) {
            primes.add(gen.next());
        }
        System.out.println("Прямой порядок: " + primes);
        
        // Обратный порядок
        List<Long> reversedPrimes = new ArrayList<>(primes);
        Collections.reverse(reversedPrimes);
        System.out.println("Обратный порядок: " + reversedPrimes);
        
        // Проверка первых 10 простых чисел
        System.out.println("Первые 10 простых чисел должны быть: [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]");
    }
}