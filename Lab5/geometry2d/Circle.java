package geometry2d;
import Exceptions.IException;

public class Circle implements Figure {
    private double radius;
    
    public Circle(double radius) throws IException {
        if (radius <= 0) {
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