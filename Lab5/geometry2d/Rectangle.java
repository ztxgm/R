package geometry2d;
import Exceptions.IException;

public class Rectangle implements Figure {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) throws IException {
        if (width <= 0 || height <= 0) {
            throw new IException("Ширина и высота прямоугольника должны быть положительными числами.");
        }
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double Area() {
        return width * height;
    }
    
    @Override
    public void Show() {
        System.out.println("Прямоугольник: ширина = " + width + ", высота = " + height + ", площадь = " + String.format("%.2f", Area()));
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
}