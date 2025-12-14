package P04_Builder;

// ディレクター（Director）
public class CarDirector {
    private CarBuilder builder;
    
    public CarDirector(CarBuilder builder) {
        this.builder = builder;
    }
    
    public void constructCar() {
        builder.buildEngine();
        builder.buildWheels();
        builder.buildInterior();
        builder.buildColor();
    }
}
