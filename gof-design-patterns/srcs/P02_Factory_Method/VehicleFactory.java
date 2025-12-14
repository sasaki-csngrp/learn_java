package P02_Factory_Method;

// ファクトリー抽象クラス（Creator）
public abstract class VehicleFactory {
    // ファクトリーメソッド（抽象メソッド）
    public abstract Vehicle createVehicle();
    
    // テンプレートメソッド（オプション）
    public void useVehicle() {
        Vehicle vehicle = createVehicle();
        vehicle.drive();
    }
}
