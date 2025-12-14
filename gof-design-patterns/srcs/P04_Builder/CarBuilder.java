package P04_Builder;

// ビルダーインターフェース（Builder）
public interface CarBuilder {
    void buildEngine();
    void buildWheels();
    void buildInterior();
    void buildColor();
    Car getCar();
}
