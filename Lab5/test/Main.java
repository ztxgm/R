package test;

import geometry2d.Circle;
import geometry2d.Rectangle;
import geometry3d.Cylinder;
import Exceptions.IException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("!geometry2d!");
            
            // +
            Circle circle = new Circle(7.0);
            circle.Show();
            
            // +
            Rectangle rectangle = new Rectangle(2.0, 4.0);
            rectangle.Show();
            
            System.out.println("\n!geometry3d!");
            
            // +
            Cylinder cylinder1 = new Cylinder(circle, 20.0);
            cylinder1.Show();
            
            System.out.println();
            
            // +
            Cylinder cylinder2 = new Cylinder(rectangle, 16.0);
            cylinder2.Show();

            System.out.println("\n--- Попытка создать некорректные фигуры ---");

            // -
            Circle invalidCircle = new Circle(-5.0);
            invalidCircle.Show();

        } catch (IException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        try {
            Rectangle invalidRectangle = new Rectangle(10.0, 0);
            invalidRectangle.Show(); // -
        } catch (IException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}