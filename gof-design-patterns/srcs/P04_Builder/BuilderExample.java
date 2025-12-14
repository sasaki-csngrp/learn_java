package P04_Builder;

// 使用例
public class BuilderExample {
    public static void main(String[] args) {
        // だめだ、ビルダーパターンは理解出来ていない！！！
        // 履修が必要！！！！！！！！！！！！
        // スポーツカーの構築
        CarBuilder sportsBuilder = new SportsCarBuilder();
        CarDirector director = new CarDirector(sportsBuilder);
        director.constructCar();
        Car sportsCar = sportsBuilder.getCar();
        System.out.println("スポーツカー: " + sportsCar);
        
        // ファミリーカーの構築
        CarBuilder familyBuilder = new FamilyCarBuilder();
        director = new CarDirector(familyBuilder);
        director.constructCar();
        Car familyCar = familyBuilder.getCar();
        System.out.println("ファミリーカー: " + familyCar);
    }
}
