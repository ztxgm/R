public class task1 {
    private static final Object lock = new Object();
    private static volatile String currentThread = "Thread1";

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!currentThread.equals("Thread1")) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }}
                    System.out.println("Поток 1");
                    currentThread = "Thread2";
                    lock.notifyAll();}}});

        Thread thread2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!currentThread.equals("Thread2")) {
                        try {lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }}
                    System.out.println("Поток 2");
                    currentThread = "Thread1";
                    lock.notifyAll();}}});

        thread1.start();
        thread2.start();
    }
}