package P00_04_Abstraction;

// 抽象クラス
abstract class Vehicle {
    protected String brand;
    
    public Vehicle(String brand) {
        this.brand = brand;
    }
    
    // 抽象メソッド（実装はサブクラスで定義）
    public abstract void start();
    public abstract void stop();
    
    // 具象メソッド
    public void displayBrand() {
        System.out.println("ブランド: " + brand);
    }
}

// 具象クラス
public class Car extends Vehicle {
    public Car(String brand) {
        super(brand);
    }
    
    @Override
    public void start() {
        System.out.println(brand + "の車がエンジンを始動しました");
    }
    
    @Override
    public void stop() {
        System.out.println(brand + "の車が停止しました");
    }


    public static void main(String[] args) {
        Car car = new Car("Toyota");
        car.start();
        car.stop();
        car.displayBrand();
    }
}