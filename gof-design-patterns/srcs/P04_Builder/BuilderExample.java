package P04_Builder;

// 使用例
// ビルダーパターンは、オブジェクトの生成を、ビルダークラスに任せるパターン
// 抽象ファクトリーパターンと同様に、複数のオブジェクトを生成するが、
// ビルダークラスを準備するのが、抽象ファクトリーパターンと異なる。
// ビルダークラスを用いる事で、部品の構築過程も抽象化する。
public class BuilderExample {
    public static void main(String[] args) {
        // スポーツカーの構築
        // １．カービルダー（具象ビルダー）を生成
        CarBuilder sportsBuilder = new SportsCarBuilder();
        // ２．ディレクター（ディレクター）にカービルダー（具象ビルダー）を渡して生成
        CarDirector director = new CarDirector(sportsBuilder);
        // ３．ディレクターがカーを構築
        director.constructCar();
        // ４．カービルダー（具象ビルダー）からカーを取得
        Car sportsCar = sportsBuilder.getCar();
        // ５．カーを表示
        System.out.println("スポーツカー: " + sportsCar);
        
        // ファミリーカーの構築
        CarBuilder familyBuilder = new FamilyCarBuilder();
        director = new CarDirector(familyBuilder);
        director.constructCar();
        Car familyCar = familyBuilder.getCar();
        System.out.println("ファミリーカー: " + familyCar);
    }
}
