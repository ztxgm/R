package test;
import geometry2d.Circle;
import geometry2d.Rectangle;
import geometry3d.Cylinder;

public class Main {
    public static void main(String[] args) {
        System.out.println("!geometry2d!");
        
        // Circle
        Circle circle = new Circle(7.0);
        circle.Show();
        
        // Rectangle
        Rectangle rectangle = new Rectangle(2.0, 4.0);
        rectangle.Show();
        
        System.out.println("\n!geometry3d!");
        
        // Cylinder
        Cylinder cylinder1 = new Cylinder(circle, 20.0);
        cylinder1.Show();
        
        System.out.println();
        
        // Cylinder
        Cylinder cylinder2 = new Cylinder(rectangle, 16.0);
        cylinder2.Show();}
}