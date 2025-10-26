import java.util.function.Consumer;

class HeavyBox {
    private int weight;
    
    public HeavyBox(int weight) {
        this.weight = weight;
    }
    
    public int getWeight() {
        return weight;
    }
}

public class Task3 {
    public static void main(String[] args) {
        Consumer<HeavyBox> ship = box -> 
            System.out.println("Отгрузили ящик с весом " + box.getWeight());
        Consumer<HeavyBox> send = box -> 
            System.out.println("Отправляем ящик с весом " + box.getWeight());
        
        Consumer<HeavyBox> combinedAction = ship.andThen(send);
        
        HeavyBox box = new HeavyBox(150);
        combinedAction.accept(box);
    }
}