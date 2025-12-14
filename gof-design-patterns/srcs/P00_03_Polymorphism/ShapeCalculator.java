package P00_03_Polymorphism;

// インターフェース
interface Shape {
    double calculateArea();
}

// 実装クラス1
class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// 実装クラス2
class Rectangle implements Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
}

// 使用例
public class ShapeCalculator {
    public void printArea(Shape shape) {
        System.out.println("面積: " + shape.calculateArea());
    }
    
    public static void main(String[] args) {
        ShapeCalculator calculator = new ShapeCalculator();
        calculator.printArea(new Circle(5.0));      // 円の面積を計算
        calculator.printArea(new Rectangle(4.0, 6.0)); // 長方形の面積を計算
    }
}