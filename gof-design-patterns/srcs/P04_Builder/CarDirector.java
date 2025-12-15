package P04_Builder;

// ディレクター（Director）
// クライアントは、この型でディレクターを利用し、コンストラクトカーで各部品を生成する。
// これがないと、クライアント（もしくはカービルダー）が、各部品を生成する必要がある。
// この construct （部品の生成）　と、build （部品の設定）　の違いの感覚が良く理解でいないんだ。
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
