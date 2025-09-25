public class main {
    public static void main(String[] args) {
        System.out.println("Задание 1:");
        Button btn = new Button();
        btn.click();
        btn.click();
        btn.click();
        
        System.out.println("\nЗадание 2:");
        Balance balance = new Balance();
        balance.addRight(10);
        balance.addLeft(5);
        balance.result();
        
        balance.addLeft(5);
        balance.result();
        
        balance.addLeft(3);
        balance.result();
        
        System.out.println("\nЗадание 3:");
        Bell bell = new Bell();
        bell.sound();
        bell.sound();
        bell.sound();
        bell.sound();
        
        System.out.println("\nЗадание 4:");
        OddEvenSeparator separator = new OddEvenSeparator();
        separator.addNumber(1);
        separator.addNumber(2);
        separator.addNumber(3);
        separator.addNumber(4);
        separator.addNumber(5);
        
        System.out.print("Четные: ");
        separator.even(); // [2, 4]
        System.out.print("Нечетные: ");
        separator.odd(); // [1, 3, 5]
        
        System.out.println("\nЗадание 5:");
        Table table = new Table(3, 4);
        table.setValue(0, 0, 5);
        table.setValue(0, 1, 10);
        table.setValue(1, 2, 15);
        table.setValue(2, 3, 20);
        
        System.out.println("Таблица:");
        System.out.println(table.toString());
        
        System.out.println("Значение в ячейке (1,2): " + table.getValue(1, 2));
        System.out.println("Строк: " + table.rows());
        System.out.println("Столбцов: " + table.cols());
        System.out.println("Среднее арифметическое: " + table.average());
    }
}

// 1
class Button {
    private int clickCount;
    
    public Button() {
        this.clickCount = 0;
    }
    
    public void click() {
        clickCount++;
        System.out.println("Button was clicked " + clickCount + " times");
    }
}

// 2
class Balance {
    private int leftWeight;
    private int rightWeight;
    
    public Balance() {
        this.leftWeight = 0;
        this.rightWeight = 0;
    }
    
    public void addLeft(int weight) {
        leftWeight += weight;
    }
    
    public void addRight(int weight) {
        rightWeight += weight;
    }
    
    public void result() {
        if (leftWeight == rightWeight) {
            System.out.println("=");
        } else if (rightWeight > leftWeight) {
            System.out.println("R");
        } else {
            System.out.println("L");
        }
    }
}

// 3
class Bell {
    private boolean isDing = true;
    
    public void sound() {
        if (isDing) {
            System.out.println("ding");
        } else {
            System.out.println("dong");
        }
        isDing = !isDing;
    }
}

// 4
class OddEvenSeparator {
    private java.util.List<Integer> numbers;
    
    public OddEvenSeparator() {
        numbers = new java.util.ArrayList<>();
    }
    
    public void addNumber(int number) {
        numbers.add(number);
    }
    
    public void even() {
        System.out.print("[");
        boolean first = true;
        for (int num : numbers) {
            if (num % 2 == 0) {
                if (!first) System.out.print(", ");
                System.out.print(num);
                first = false;
            }
        }
        System.out.println("]");
    }
    
    public void odd() {
        System.out.print("[");
        boolean first = true;
        for (int num : numbers) {
            if (num % 2 != 0) {
                if (!first) System.out.print(", ");
                System.out.print(num);
                first = false;
            }
        }
        System.out.println("]");
    }
}

// 5
class Table {
    private int[][] data;
    private int rows;
    private int cols;
    
    public Table(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols];
    }
    
    public int getValue(int row, int col) {
        return data[row][col];
    }
    
    public void setValue(int row, int col, int value) {
        data[row][col] = value;
    }
    
    public int rows() {
        return rows;
    }
    
    public int cols() {
        return cols;
    }
    
    public double average() {
        if (rows == 0 || cols == 0) return 0;
        
        double sum = 0;
        int count = 0;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += data[i][j];
                count++;
            }
        }
        
        return sum / count;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]);
                if (j < cols - 1) {
                    sb.append("\t");
                }
            }
            if (i < rows - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}