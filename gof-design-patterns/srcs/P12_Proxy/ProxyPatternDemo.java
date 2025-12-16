package P12_Proxy;

// 要は、サブジェクト（主題）とクライアントの間に、
// 代理（Proxy）を設けることで、サブジェクトの処理を代理する。
// （リアルサブジェクトと同じインターフェースを実装し、同じ機能を保証）
// 代理が、いろいろとこねこね出来るので、何にでも応用できそう。

// 1. Subjectインターフェース
interface Image {
    void display();
}

// 2. RealSubjectクラス（実際の処理を行うクラス）
class RealImage implements Image {
    private String fileName;
    
    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk(fileName); // コンストラクタで画像をロード（高コストな操作）
    }
    
    private void loadFromDisk(String fileName) {
        System.out.println("Loading " + fileName + " from disk...");
        // 実際の画像ロード処理（時間がかかる）
        try {
            Thread.sleep(1000); // ロード時間をシミュレート
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void display() {
        System.out.println("Displaying " + fileName);
    }
}

// 3. Proxyクラス（代理クラス）
class ProxyImage implements Image {
    private RealImage realImage;
    private String fileName;
    
    public ProxyImage(String fileName) {
        this.fileName = fileName;
        // プロキシの作成時には実際の画像をロードしない（遅延初期化）
    }
    
    @Override
    public void display() {
        // 初回アクセス時のみ実際の画像をロード
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}

// 4. クライアントコード
public class ProxyPatternDemo {
    public static void main(String[] args) {
        System.out.println("=== プロキシパターンのデモ ===");
        
        // プロキシを作成（この時点では画像はロードされない）
        Image image = new ProxyImage("test_image.jpg");
        
        System.out.println("\n--- 1回目の表示 ---");
        // 初回の表示時に画像がロードされる
        image.display();
        
        System.out.println("\n--- 2回目の表示 ---");
        // 2回目以降は既にロード済みなので、ロード処理は省略される
        image.display();
    }
}