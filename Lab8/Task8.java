import java.util.*;
import java.util.stream.Collectors;

class Student {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private int birthYear;
    private String address;
    private String phone;
    private String faculty;
    private int course;
    private String group;
    
    public Student(int id, String lastName, String firstName, String faculty, int course) {
        this(id, lastName, firstName, "", 0, "", "", faculty, course, "");
    }
    
    public Student(int id, String lastName, String firstName, String middleName, 
                  int birthYear, String address, String phone, String faculty, 
                  int course, String group) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthYear = birthYear;
        this.address = address;
        this.phone = phone;
        this.faculty = faculty;
        this.course = course;
        this.group = group;
    }
    
    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    
    public int getBirthYear() { return birthYear; }
    public void setBirthYear(int birthYear) { this.birthYear = birthYear; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
    
    public int getCourse() { return course; }
    public void setCourse(int course) { this.course = course; }
    
    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }
    
    @Override
    public String toString() {
        return String.format("Студент: %s %s %s, Факультет: %s, Курс: %d, Группа: %s, Год рождения: %d",
                lastName, firstName, middleName, faculty, course, group, birthYear);
    }
}

public class Task8 {
    private static List<Student> students;
    
    static {
        students = Arrays.asList(
            new Student(1, "Иванов", "Иван", "Иванович", 2000, "ул. Ленина 1", "+79111111111", "Информатика", 3, "МОА-231"),
            new Student(2, "Петрова", "Мария", "Сергеевна", 2001, "ул. Мира 15", "+79222222222", "Математика", 2, "ИФИЯМ-211"),
            new Student(3, "Сидоров", "Алексей", "Петрович", 1999, "ул. Советская 25", "+79333333333", "Информатика", 4, "КБ-241"),
            new Student(4, "Козлова", "Елена", "Владимировна", 2002, "ул. Центральная 8", "+79444444444", "Физика", 1, "ПИ-211"),
            new Student(5, "Николаев", "Дмитрий", "Александрович", 2000, "ул. Школьная 3", "+79555555555", "Математика", 3, "МОА-231")
        );
    }
    
    public static void main(String[] args) {
        System.out.println("=== Все студенты ===");
        students.forEach(System.out::println);
        
        String targetFaculty = "Информатика";
        int targetYear = 2000;
        
        System.out.println("\n=== a. Список студентов факультета " + targetFaculty + " ===");
        
        // Способ 1: Циклы и операторы условия
        System.out.println("Способ 1 (циклы):");
        for (Student student : students) {
            if (student.getFaculty().equals(targetFaculty)) {
                System.out.println(student);
            }
        }
        
        // Способ 2: Методы коллекций
        System.out.println("\nСпособ 2 (методы коллекций):");
        List<Student> facultyStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getFaculty().equals(targetFaculty)) {
                facultyStudents.add(student);
            }
        }
        facultyStudents.forEach(System.out::println);
        
        // Способ 3: Stream API
        System.out.println("\nСпособ 3 (Stream API):");
        students.stream()
                .filter(student -> student.getFaculty().equals(targetFaculty))
                .forEach(System.out::println);
        
        // b. Списки студентов для каждого факультета и курса
        System.out.println("\n=== b. Студенты по факультетам и курсам ===");
        Map<String, Map<Integer, List<Student>>> byFacultyAndCourse = students.stream()
                .collect(Collectors.groupingBy(
                    Student::getFaculty,
                    Collectors.groupingBy(Student::getCourse)
                ));
        
        byFacultyAndCourse.forEach((faculty, courseMap) -> {
            System.out.println("Факультет: " + faculty);
            courseMap.forEach((course, studentList) -> {
                System.out.println("  Курс " + course + ": " + studentList.size() + " студентов");
                studentList.forEach(student -> System.out.println("    - " + student.getLastName()));
            });
        });
        
        // c. Список студентов, родившихся после заданного года
        System.out.println("\n=== c. Студенты, родившиеся после " + targetYear + " года ===");
        
        // Способ 1: Циклы и операторы условия
        System.out.println("Способ 1 (циклы):");
        for (Student student : students) {
            if (student.getBirthYear() > targetYear) {
                System.out.println(student);
            }
        }
        
        // Способ 2: Методы коллекций
        System.out.println("\nСпособ 2 (методы коллекций):");
        List<Student> youngStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getBirthYear() > targetYear) {
                youngStudents.add(student);
            }
        }
        youngStudents.forEach(System.out::println);
        
        // Способ 3: Stream API
        System.out.println("\nСпособ 3 (Stream API):");
        students.stream()
                .filter(student -> student.getBirthYear() > targetYear)
                .forEach(System.out::println);
    }
}