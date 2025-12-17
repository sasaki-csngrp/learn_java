// インターフェース
interface Human {
    sayHello(): void;
}

class Japanese implements Human {
    private name: string;
    
    constructor(name: string) {
        this.name = name;
    }
    
    sayHello(): void {
        console.log(`こんにちは。私の名前は${this.name}です。`);
    }
}

class American implements Human {
    private name: string;
    
    constructor(name: string) {
        this.name = name;
    }
    
    sayHello(): void {
        console.log(`Hello. My name is ${this.name}.`);
    }
}

// 実行例
let human: Human;
human = new Japanese("太郎");
human.sayHello();
human = new American("John");
human.sayHello();
