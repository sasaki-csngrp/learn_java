package P04_Builder;

// ビルダーインターフェース（Builder）
// クライアントは、この型でビルダーを利用する
public interface CarBuilder {
    void buildEngine();
    void buildWheels();
    void buildInterior();
    void buildColor();
    Car getCar();
}
