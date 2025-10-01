package geometry2d;

public class Circle implements Figure {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;}
    
    @Override
    public double Area() {
        return Math.PI * radius * radius;}
    
    @Override
    public void Show() {
        System.out.println("Круг: радиус = " + radius + ", площадь = " + String.format("%.2f", Area()));   }
    
    public double getRadius() {
        return radius;}
}