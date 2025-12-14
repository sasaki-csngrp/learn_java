package P03_Abstract_Factory;

// クライアント（Client）
public class Application {
    // クライアントは、ファクトリーを使って、オブジェクトを生成する。
    // ファクトリーも生成された製品も、具体的なクラスは全く知らない。
    private Button button;
    private Dialog dialog;
    private UIFactory factory;
    
    public Application(UIFactory factory) {
        this.factory = factory;
        this.button = this.factory.createButton();
        this.dialog = this.factory.createDialog();
    }
    
    public void render() {
        button.paint();
        dialog.render();
    }
    
    public void interact() {
        button.onClick();
        dialog.show();
    }
}
