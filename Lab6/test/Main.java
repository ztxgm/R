package test;
import geometry2d.Circle;
import geometry2d.Rectangle;
import geometry3d.Cylinder;
import Exceptions.IException;
import java.util.logging.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    static {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        consoleHandler.setLevel(Level.FINE);
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.FINE);
    }
    
    public static void main(String[] args) {
        logger.fine("Начало работы программы");
        
        try {
            System.out.println("!geometry2d!");
            logger.fine("Создание круга с радиусом 7.0");
            
            Circle circle = new Circle(7.0);
            circle.Show();
            
            logger.fine("Создание прямоугольника 2.0x4.0");
            Rectangle rectangle = new Rectangle(2.0, 4.0);
            rectangle.Show();
            
            System.out.println("\n!geometry3d!");
            logger.fine("Создание цилиндра с круглым основанием");
            
            Cylinder cylinder1 = new Cylinder(circle, 20.0);
            cylinder1.Show();
            
            System.out.println();
            logger.fine("Создание цилиндра с прямоугольным основанием");
            
            Cylinder cylinder2 = new Cylinder(rectangle, 16.0);
            cylinder2.Show();

            System.out.println("\n--- Попытка создать некорректные фигуры ---");
            logger.fine("Попытка создать круг с отрицательным радиусом");

            Circle invalidCircle = new Circle(-5.0);
            invalidCircle.Show();

        } catch (IException e) {
            System.err.println("Ошибка: " + e.getMessage());
            logger.fine("Перехвачена ошибка: " + e.getMessage());
        }

        try {
            logger.fine("Попытка создать прямоугольник с нулевой высотой");
            Rectangle invalidRectangle = new Rectangle(10.0, 0);
            invalidRectangle.Show();
        } catch (IException e) {
            System.err.println("Ошибка: " + e.getMessage());
            logger.fine("Перехвачена ошибка: " + e.getMessage());
        }
        
        logger.fine("Завершение работы программы");
    }
}