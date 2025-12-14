package P04_Builder;

// 具象ビルダークラス（Concrete Builder）
public class SportsCarBuilder implements CarBuilder {
    private String engine;
    private String wheels;
    private String interior;
    private String color;
    
    @Override
    public void buildEngine() {
        this.engine = "V8エンジン";
    }
    
    @Override
    public void buildWheels() {
        this.wheels = "スポーツホイール";
    }
    
    @Override
    public void buildInterior() {
        this.interior = "レザーシート";
    }
    
    @Override
    public void buildColor() {
        this.color = "レッド";
    }
    
    @Override
    public Car getCar() {
        return new Car(engine, wheels, interior, color);
    }
}
