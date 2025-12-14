package P01_Singleton;

public class SingletonExample {
    public static void main(String[] args) {
        // 通常のnewはできない（コンパイルエラー）
        // Singleton s = new Singleton(); // エラー！
        
        // getInstance()メソッドでインスタンスを取得
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        
        // 同じインスタンスか確認
        System.out.println("s1 == s2: " + (s1 == s2)); // true
        System.out.println("s1.equals(s2): " + s1.equals(s2)); // true
        
        s1.doSomething();
    }
}