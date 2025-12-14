package P03_Abstract_Factory;

// 使用例
public class AbstractFactoryExample {
    public static void main(String[] args) {
        // OSを検出
        String os = System.getProperty("os.name").toLowerCase();
        UIFactory factory;
        
        if (os.contains("windows")) {
            factory = new WindowsUIFactory();
        } else if (os.contains("mac")) {
            factory = new MacUIFactory();
        } else if (os.contains("linux")) {
            factory = new LinuxUIFactory();
        } else {
            // デフォルトとしてWindowsを使用（またはエラーをスロー）
            System.out.println("サポートされていないOS: " + os + "。Windowsスタイルを使用します。");
            factory = new WindowsUIFactory();
        }

        // 状況によってファクトリーを選択するのは、ファクトリーメソッドパターンと同じ
        // でも、ファクトリーメソッドパターンは、1つのオブジェクトを生成するのに対して、
        // 抽象ファクトリーパターンは、関連するオブジェクトのファミリーを生成する。
        // クライアントは具体的なクラスを知らない
        // どころか、ファクトリーを生成するのもクライアントの責務ではない。
        // 渡されたファクトリーを使って、オブジェクトを生成するのはクライアントの責務。
        Application app = new Application(factory);
        app.render();
        app.interact();
    }
}
