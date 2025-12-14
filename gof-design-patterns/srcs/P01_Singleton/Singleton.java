package P01_Singleton;

public class Singleton {
    // 唯一のインスタンスを保持するstatic変数
    private static Singleton instance;
    
    // privateコンストラクタで外部からのインスタンス化を防止
    private Singleton() {
        // 初期化処理
    }
    
    // インスタンスへのアクセスを提供するpublic staticメソッド
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
    
    // その他のメソッド
    public void doSomething() {
        System.out.println("シングルトンのメソッドが呼ばれました");
    }
}