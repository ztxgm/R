import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private static WorkerThread workerThread;
    private static ServerSocket serverSocket;
    
    public static void main(String[] args) {
        int port = 5555;
        
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен на порту " + port);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключен: " + clientSocket.getInetAddress());
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void handleClient(Socket clientSocket) {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Получена команда: " + inputLine);
                
                switch (inputLine) {
                    case "START":
                        if (workerThread != null) {
                            workerThread.stopThread();
                        }
                        workerThread = new WorkerThread(out);
                        workerThread.start();
                        break;
                        
                    case "PAUSE":
                        if (workerThread != null) {
                            workerThread.pauseThread();
                        }
                        break;
                        
                    case "RESUME":
                        if (workerThread != null) {
                            workerThread.resumeThread();
                        }
                        break;
                        
                    case "STOP":
                        if (workerThread != null) {
                            workerThread.stopThread();
                            workerThread = null;
                        }
                        out.println("PROGRESS:0");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static class WorkerThread extends Thread {
        private final PrintWriter out;
        private final AtomicInteger progress = new AtomicInteger(0);
        private final AtomicBoolean paused = new AtomicBoolean(false);
        private final AtomicBoolean stopped = new AtomicBoolean(false);
        private final Object lock = new Object();
        
        public WorkerThread(PrintWriter out) {
            this.out = out;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 0; i <= 1000; i++) {
                    if (stopped.get()) {
                        break;
                    }
                    
                    synchronized (lock) {
                        while (paused.get() && !stopped.get()) {
                            lock.wait();
                        }
                    }
                    
                    if (stopped.get()) {
                        break;
                    }
                    
                    progress.set(i);
                    out.println("PROGRESS:" + i);
                    
                    Thread.sleep(20);
                }
                
                if (!stopped.get()) {
                    out.println("FINISHED");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        public void pauseThread() {
            paused.set(true);
        }
        
        public void resumeThread() {
            synchronized (lock) {
                paused.set(false);
                lock.notify();
            }
        }
        
        public void stopThread() {
            stopped.set(true);
            synchronized (lock) {
                paused.set(false);
                lock.notify();
            }
        }
    }
}