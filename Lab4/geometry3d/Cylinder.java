package geometry3d;
import geometry2d.Figure;

public class Cylinder {
    private Figure base;
    private double height;
    
    public Cylinder(Figure base, double height) {
        this.base = base;
        this.height = height;}
    
    public double Volume() {
        return base.Area() * height;}
    
    public void Show() {
        System.out.println("Цилиндр:");
        base.Show();
        System.out.println("Высота цилиндра: " + height);
        System.out.println("Объем цилиндра: " + String.format("%.2f", Volume()));}
    
    public Figure getBase() {
        return base;}
    
    public double getHeight() {
        return height;}
}