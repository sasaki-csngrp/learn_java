package P02_Factory_Method;

// 具象製品クラス（Concrete Product）
public class Car implements Vehicle {
    @Override
    public void drive() {
        System.out.println("車が走っています");
    }
}
