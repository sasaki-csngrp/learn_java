class Singleton {
    // 唯一のインスタンスを保持するstatic変数
    private static instance: Singleton;
    
    // privateコンストラクタで外部からのインスタンス化を防止
    private constructor() {
        // 初期化処理
    }
    
    // インスタンスへのアクセスを提供するpublic staticメソッド
    public static getInstance(): Singleton {
        if (Singleton.instance == null) {
            Singleton.instance = new Singleton();
        }
        return Singleton.instance;
    }
    
    // その他のメソッド
    public doSomething(): void {
        console.log("シングルトンのメソッドが呼ばれました");
    }
}

export default Singleton;
