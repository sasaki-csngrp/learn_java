package P04_BuilderV1;

// 使用例
public class FluentBuilderExample {
    public static void main(String[] args) {
        // メソッドチェーンで構築
        Computer computer = new Computer.ComputerBuilder("Intel i7", "16GB", "512GB SSD")
            .graphicsCard("NVIDIA RTX 3080")
            .bluetooth(true)
            .wifi(true)
            .build();
        
        System.out.println(computer);
        
        // 最小構成で構築
        Computer simpleComputer = new Computer.ComputerBuilder("Intel i5", "8GB", "256GB SSD")
            .build();
        
        System.out.println(simpleComputer);
    }
}
