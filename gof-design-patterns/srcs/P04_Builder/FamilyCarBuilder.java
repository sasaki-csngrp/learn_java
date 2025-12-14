package P04_Builder;

// 具象ビルダークラス（Concrete Builder）
public class FamilyCarBuilder implements CarBuilder {
    private String engine;
    private String wheels;
    private String interior;
    private String color;
    
    @Override
    public void buildEngine() {
        this.engine = "4気筒エンジン";
    }
    
    @Override
    public void buildWheels() {
        this.wheels = "標準ホイール";
    }
    
    @Override
    public void buildInterior() {
        this.interior = "ファブリックシート";
    }
    
    @Override
    public void buildColor() {
        this.color = "シルバー";
    }
    
    @Override
    public Car getCar() {
        return new Car(engine, wheels, interior, color);
    }
}
