// 抽象クラス
abstract class Vehicle {
    protected brand: string;  // protectedな変数
    
    constructor(brand: string) {
        this.brand = brand;
    }
    
    // 抽象メソッド（実装はサブクラスで定義）
    abstract start(): void;
    abstract stop(): void;
    
    // 具象メソッド
    displayBrand(): void {
        console.log(`ブランド: ${this.brand}`);
    }
}

// 具象クラス
class Car extends Vehicle {
    constructor(brand: string) {
        super(brand);  // 親クラスのコンストラクタを呼び出す
    }
    
    start(): void {
        // 抽象メソッドの実装
        console.log(`${this.brand}の車がエンジンを始動しました`);
    }
    
    stop(): void {
        // 抽象メソッドの実装
        console.log(`${this.brand}の車が停止しました`);
    }
}

// 実行例
const car = new Car("Toyota");
car.start();
car.stop();
car.displayBrand();
