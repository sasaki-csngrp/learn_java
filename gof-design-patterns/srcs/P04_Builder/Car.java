package P04_Builder;

// 製品クラス
// カービルダーが、GetCar()メソッドで返すオブジェクト
// スポーツカーだろうが、ファミリーカーだろうが、このクラスが使われる
// このクラス単体では、エンジン・ホイール・インテリア・カラーを保持し、
// コンストラクタで設定される。
// ただ、クライアントが直接このCarクラスを使うことはない。
// カービルダーが、まずビルダー内にエンジン・ホイール・インテリア・カラーを設定して、
// それをもとに、Carクラスのインスタンスを生成（new car()）して返す。
public class Car {
    private String engine;
    private String wheels;
    private String interior;
    private String color;
    
    public Car(String engine, String wheels, String interior, String color) {
        this.engine = engine;
        this.wheels = wheels;
        this.interior = interior;
        this.color = color;
    }
    
    @Override
    public String toString() {
        return "Car [Engine=" + engine + ", Wheels=" + wheels + 
               ", Interior=" + interior + ", Color=" + color + "]";
    }
    
    // Getterメソッド
    public String getEngine() { return engine; }
    public String getWheels() { return wheels; }
    public String getInterior() { return interior; }
    public String getColor() { return color; }
}
