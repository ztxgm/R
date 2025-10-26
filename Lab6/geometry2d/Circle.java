package geometry2d;
import Exceptions.IException;
import java.util.logging.*;
import java.io.IOException;

public class Circle implements Figure {
    private double radius;
    private static final Logger logger = Logger.getLogger(Circle.class.getName());
    
    static {
        try {
            FileHandler fileHandler = new FileHandler("figures.log", true);
            fileHandler.setFormatter(new XMLFormatter());
            fileHandler.setLevel(Level.SEVERE);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            logger.severe("Не удалось создать файл лога: " + e.getMessage());
        }
    }
    
    public Circle(double radius) throws IException {
        if (radius <= 0) {
            logger.severe("Попытка создать круг с отрицательным радиусом: " + radius);
            throw new IException("Радиус круга должен быть положительным числом.");
        }
        this.radius = radius;
    }
    
    @Override
    public double Area() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public void Show() {
        System.out.println("Круг: радиус = " + radius + ", площадь = " + String.format("%.2f", Area()));
    }
    
    public double getRadius() {
        return radius;
    }
}