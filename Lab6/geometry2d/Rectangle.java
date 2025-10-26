package geometry2d;
import Exceptions.IException;
import java.util.logging.*;
import java.io.IOException;

public class Rectangle implements Figure {
    private double width;
    private double height;
    private static final Logger logger = Logger.getLogger(Rectangle.class.getName());
    
    static {
        try {
            FileHandler fileHandler = new FileHandler("figures.log", true);
            fileHandler.setFormatter(new XMLFormatter());
            fileHandler.setLevel(Level.INFO);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            logger.severe("Не удалось создать файл лога: " + e.getMessage());
        }
    }
    
    public Rectangle(double width, double height) throws IException {
        if (width <= 0 || height <= 0) {
            throw new IException("Ширина и высота прямоугольника должны быть положительными числами.");
        }
        this.width = width;
        this.height = height;
        logger.info("Создан прямоугольник: ширина=" + width + ", высота=" + height);
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