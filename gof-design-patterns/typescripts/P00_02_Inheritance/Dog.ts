// 基底クラス
class Animal {
    protected name: string;  // protectedな変数
    
    constructor(name: string) {
        this.name = name;
    }
    
    makeSound(): void {
        console.log("動物の鳴き声");
    }
}

// 派生クラス
class Dog extends Animal {
    constructor(name: string) {
        super(name);  // 親クラスのコンストラクタを呼び出す
    }
    
    makeSound(): void {
        // メソッドのオーバーライド
        console.log(`${this.name}がワンワンと鳴く`);
    }
}

// 実行例
const dog = new Dog("ポチ");
dog.makeSound();
