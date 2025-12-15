package P07_Bridge;

// バリエーション1: インターフェースベースの実装
// Abstractionをインターフェースとして定義する方法です。
// 実装1と実装2を分離し、それぞれ独立して拡張可能。
// 実装1と実装2は、実行時に動的に結合される。

// Implementor 実装1
interface Renderer {
    void render();
}

// ConcreteImplementor
class VectorRenderer implements Renderer {
    @Override
    public void render() {
        System.out.println("ベクター形式で描画");
    }
}

class RasterRenderer implements Renderer {
    @Override
    public void render() {
        System.out.println("ラスター形式で描画");
    }
}

// Abstraction（インターフェース）　実装2
interface Shape {
    void draw();
}

// RefinedAbstraction
class Circle implements Shape {
    private Renderer renderer;
    
    public Circle(Renderer renderer) {
        this.renderer = renderer;
    }
    
    @Override
    public void draw() {
        System.out.print("円を描画 - ");
        renderer.render();
    }
}

class Square implements Shape {
    private Renderer renderer;
    
    public Square(Renderer renderer) {
        this.renderer = renderer;
    }
    
    @Override
    public void draw() {
        System.out.print("四角形を描画 - ");
        renderer.render();
    }
}

// 使用例
public class InterfaceBasedExample {
    public static void main(String[] args) {
        Shape circle = new Circle(new VectorRenderer());
        circle.draw();
        
        Shape square = new Square(new RasterRenderer());
        square.draw();
    }
}

