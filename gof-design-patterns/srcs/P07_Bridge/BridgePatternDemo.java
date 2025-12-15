package P07_Bridge;

// 抽象化と実装の分離　というから日本語が解りやすい
// interface（インターフェース） の implements （つまり実装）と、
// abstract class（抽象クラス）の extends（継承）を、
// それぞれ分離して拡張可能にする事だと。

// 1. Implementor（実装のインターフェース）
interface Color {
    void applyColor();
}

// 2. ConcreteImplementor（具象実装クラス）
class RedColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("赤色を適用");
    }
}

class BlueColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("青色を適用");
    }
}

class GreenColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("緑色を適用");
    }
}

// 3. Abstraction（抽象化クラス）
abstract class Shape {
    protected Color color; // 実装（ConcreteImplementor）を保持
    
    protected Shape(Color color) {
        this.color = color; // 実装（ConcreteImplementor）をコンストラクタで注入
    }
    
    public abstract void draw();
}

// 4. RefinedAbstraction（改良された抽象化）
class Circle extends Shape {
    public Circle(Color color) {
        super(color); // 実装（ConcreteImplementor）をコンストラクタで注入
    }
    
    @Override
    public void draw() {
        System.out.print("円を描画 ");
        color.applyColor(); // 実装（ConcreteImplementor）のメソッドを呼び出す
    }
}

class Square extends Shape {
    public Square(Color color) {
        super(color); // 実装（ConcreteImplementor）をコンストラクタで注入
    }
    
    @Override
    public void draw() {
        System.out.print("四角形を描画 ");
        color.applyColor(); // 実装（ConcreteImplementor）のメソッドを呼び出す
    }
}

class Triangle extends Shape {
    public Triangle(Color color) {
        super(color); // 実装（ConcreteImplementor）をコンストラクタで注入
    }
    
    @Override
    public void draw() {
        System.out.print("三角形を描画 ");
        color.applyColor(); // 実装（ConcreteImplementor）のメソッドを呼び出す
    }
}

// 5. クライアントコード
public class BridgePatternDemo {
    public static void main(String[] args) {
        // 赤い円
        Shape redCircle = new Circle(new RedColor()); // 抽象化（Shape）と実装（ConcreteImplementor）を動的に結合
        redCircle.draw(); // 抽象化（Shape）のメソッドを呼び出し、そこから実装（ConcreteImplementor）のメソッドも呼ばれる。
        
        // 青い四角形
        Shape blueSquare = new Square(new BlueColor()); // 抽象化（Shape）と実装（ConcreteImplementor）を動的に結合
        blueSquare.draw(); // 抽象化（Shape）のメソッドを呼び出し、そこから実装（ConcreteImplementor）のメソッドも呼ばれる。
        
        // 緑の三角形
        Shape greenTriangle = new Triangle(new GreenColor()); // 抽象化（Shape）と実装（ConcreteImplementor）を動的に結合
        greenTriangle.draw(); // 抽象化（Shape）のメソッドを呼び出し、そこから実装（ConcreteImplementor）のメソッドも呼ばれる。
    }
}
