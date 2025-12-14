package P02_Factory_Method;

// 使用例
public class FactoryMethodExample {
    public static void main(String[] args) {
        // CarFactoryを使用してCarを作成
        VehicleFactory carFactory = new CarFactory();
        // car のクラスを知らなくても、createVehicle() メソッドを使って Car インスタンスを作成できる
        // ポリモーフィズムをもう一段階抽象化した感じ
        // Vehicle car = new Car(); これはできない(コンストラクタが無い。ファクトリーだけがある。)
        Vehicle car = carFactory.createVehicle();
        car.drive();
        
        // BikeFactoryを使用してBikeを作成
        VehicleFactory bikeFactory = new BikeFactory();
        Vehicle bike = bikeFactory.createVehicle();
        bike.drive();
        
        // テンプレートメソッドの使用
        System.out.println("\n=== テンプレートメソッドの使用 ===");
        carFactory.useVehicle();
        bikeFactory.useVehicle();
    }
}
