package P02_Factory_Method;

// 具象製品クラス（Concrete Product）
public class Bike implements Vehicle {
    @Override
    public void drive() {
        System.out.println("バイクが走っています");
    }
}
