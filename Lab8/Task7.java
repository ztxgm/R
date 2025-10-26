import java.util.*;
import java.util.stream.Collectors;

enum PhoneType {
    MOBILE, LANDLINE
}

class Phone {
    private String number;
    private PhoneType type;
    
    public Phone(String number, PhoneType type) {
        this.number = number;
        this.type = type;
    }
    
    public PhoneType getType() { return type; }
    public String getNumber() { return number; }
}

class Client {
    private int id;
    private String name;
    private int age;
    private List<Phone> phones;
    
    public Client(int id, String name, int age, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phones = phones;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public List<Phone> getPhones() { return phones; }
}

public class Task7 {
    public static void main(String[] args) {
        List<Client> clients = Arrays.asList(
            new Client(1, "Иван", 25, Arrays.asList(
                new Phone("+79111111111", PhoneType.MOBILE),
                new Phone("+74951111111", PhoneType.LANDLINE)
            )),
            new Client(2, "Мария", 22, Arrays.asList(
                new Phone("+79222222222", PhoneType.MOBILE)
            )),
            new Client(3, "Петр", 30, Arrays.asList(
                new Phone("+74953333333", PhoneType.LANDLINE)
            )),
            new Client(4, "Анна", 19, Arrays.asList(
                new Phone("+79333333333", PhoneType.MOBILE),
                new Phone("+74954444444", PhoneType.LANDLINE)
            ))
        );
        
        Optional<Client> youngestClient = clients.stream()
                .filter(client -> client.getPhones().stream()
                        .anyMatch(phone -> phone.getType() == PhoneType.MOBILE))
                .min(Comparator.comparingInt(Client::getAge));
        
        if (youngestClient.isPresent()) {
            Client client = youngestClient.get();
            System.out.println("Самый молодой клиент с мобильным телефоном:");
            System.out.println("Имя: " + client.getName() + ", Возраст: " + client.getAge());
        } else {
            System.out.println("Клиенты с мобильными телефонами не найдены");
        }
    }
}