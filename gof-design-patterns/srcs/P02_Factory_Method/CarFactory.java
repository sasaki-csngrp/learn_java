package P02_Factory_Method;

// 具象ファクトリークラス（Concrete Creator）
public class CarFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Car();
    }
}
