import java.util.*;

class Human implements Comparable<Human> {
    private String fName;
    private String lName;
    private int age;
    
    public Human(String fName, String lName, int age) {
        this.fName = fName;
        this.lName = lName;
        this.age = age;
    }
    
    public String getfName() { return fName; }
    public String getlName() { return lName; }
    public int getAge() { return age; }
    
    @Override
    public int compareTo(Human o) {
        if(this.equals(o))
            return 0;
        int rez = fName.compareToIgnoreCase(o.getfName());
        if(rez != 0)
            return rez;
        rez = lName.compareToIgnoreCase(o.getlName());
        if(rez != 0)
            return rez;
        else
            return age - o.getAge();
    }
    
    @Override
    public String toString() {
        return fName + " " + lName + " (" + age + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Human)) return false;
        Human human = (Human) o;
        return age == human.age &&
               Objects.equals(fName, human.fName) &&
               Objects.equals(lName, human.lName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(fName, lName, age);
    }
}

class HumanComparatorByLName implements Comparator<Human> {
    @Override
    public int compare(Human h1, Human h2) {
        return h1.getlName().compareToIgnoreCase(h2.getlName());
    }
}

public class Task3 {
    public static void main(String[] args) {
        System.out.println("=== 3: Класс Human и компараторы ===");
        demonstrateHumanCollections();
    }
    
    public static void demonstrateHumanCollections() {
        // Создаем тестовые данные
        List<Human> humans = Arrays.asList(
            new Human("John", "Doe", 25),
            new Human("Jane", "Smith", 30),
            new Human("Bob", "Johnson", 20),
            new Human("Alice", "Brown", 35),
            new Human("John", "Doe", 28),
            new Human("Charlie", "Adams", 40)
        );
        
        System.out.println("Исходный список людей:");
        humans.forEach(System.out::println);
        
        // a) HashSet
        System.out.println("\na) HashSet (порядок не гарантирован):");
        Set<Human> hashSet = new HashSet<>(humans);
        hashSet.forEach(System.out::println);

        // b) LinkedHashSet
        System.out.println("\nb) LinkedHashSet (порядок вставки):");
        Set<Human> linkedSet = new LinkedHashSet<>(humans);
        linkedSet.forEach(System.out::println);

        // c) TreeSet (natural ordering)
        System.out.println("\nc) TreeSet (естественная сортировка по имени->фамилии->возрасту):");
        Set<Human> treeSet = new TreeSet<>(humans);
        treeSet.forEach(System.out::println);

        // d) TreeSet с компаратором по фамилии
        System.out.println("\nd) TreeSet с компаратором HumanComparatorByLName:");
        Set<Human> treeSetByLName = new TreeSet<>(new HumanComparatorByLName());
        treeSetByLName.addAll(humans);
        treeSetByLName.forEach(System.out::println);

        // e) TreeSet с анонимным компаратором по возрасту
        System.out.println("\ne) TreeSet с анонимным компаратором по возрасту:");
        Set<Human> treeSetByAge = new TreeSet<>(new Comparator<Human>() {
            @Override
            public int compare(Human h1, Human h2) {
                return Integer.compare(h1.getAge(), h2.getAge());
            }
        });
        treeSetByAge.addAll(humans);
        treeSetByAge.forEach(System.out::println);
    }
}