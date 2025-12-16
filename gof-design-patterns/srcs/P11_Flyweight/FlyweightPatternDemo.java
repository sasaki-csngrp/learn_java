package P11_Flyweight;

// この例で言うと
// 
// 内在的状態：color（色）　
// 外在的状態：x, y, radius（半径）
// 
// ちょっと例が解りづらいけど、具象フライウェイトクラスの Circle は 
// Color 色をこのオブジェクトの属性として持ち（コンストラクタで指定）
// x, y, radiusは、描画（draw）時にパラメータとして渡す。
// 
// つまり、color を指定したオブジェクトを作って、その Color （色）で描画（draw）するだけで、
// x, y, radius を持ったオブジェクトは作っていない。
// 
// もし、これが、color, x, y, radius をオブジェクト毎に設定するなら、
// このパターンは使えない（というか、4属性が完全一致した場合のみ、オブジェクトを共有可能）

import java.util.HashMap;
import java.util.Map;

// 1. フライウェイトインターフェース（Flyweight）
interface Shape {
    void draw(int x, int y, int radius);
}

// 2. 具象フライウェイトクラス（ConcreteFlyweight）
class Circle implements Shape {
    // 内在的状態（共有可能な不変の状態）
    private String color;
    
    public Circle(String color) {
        this.color = color;
        System.out.println("Creating circle of color: " + color);
    }
    
    // 外在的状態（x, y, radius）はメソッドのパラメータとして受け取る
    @Override
    public void draw(int x, int y, int radius) {
        System.out.println("Circle: Draw() [Color: " + color + 
                         ", x: " + x + ", y: " + y + ", radius: " + radius + "]");
    }
}

// 3. フライウェイトファクトリー（FlyweightFactory）

class ShapeFactory {
    // フライウェイトオブジェクトを保持するキャッシュ
    private static final Map<String, Shape> circleMap = new HashMap<>();
    
    public static Shape getCircle(String color) {
        Circle circle = (Circle) circleMap.get(color);
        
        // 同じ色のCircleが存在しない場合のみ新規作成
        if (circle == null) {
            circle = new Circle(color);
            circleMap.put(color, circle);
        }
        
        return circle;
    }
    
    // キャッシュ内のオブジェクト数を取得（デバッグ用）
    public static int getCircleCount() {
        return circleMap.size();
    }
}

// 4. クライアントコード
public class FlyweightPatternDemo {
    private static final String[] colors = {"Red", "Green", "Blue", "White", "Black"};
    
    public static void main(String[] args) {
        System.out.println("=== フライウェイトパターンのデモ ===");
        
        // 20個のCircleを作成（実際には5色しかないので、同じ色のCircleは共有される）
        for (int i = 0; i < 20; i++) {
            Circle circle = (Circle) ShapeFactory.getCircle(getRandomColor());
            circle.draw(getRandomX(), getRandomY(), 100);
        }
        
        System.out.println("\n実際に作成されたCircleオブジェクトの数: " + 
                          ShapeFactory.getCircleCount());
        System.out.println("（20個のCircleを描画したが、実際には5個のオブジェクトのみ作成）");
    }
    
    private static String getRandomColor() {
        return colors[(int) (Math.random() * colors.length)];
    }
    
    private static int getRandomX() {
        return (int) (Math.random() * 100);
    }
    
    private static int getRandomY() {
        return (int) (Math.random() * 100);
    }
}