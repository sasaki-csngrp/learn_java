package P03_Abstract_Factory;

// 具象製品クラス（Concrete Product） - Mac用
public class MacDialog implements Dialog {
    @Override
    public void render() {
        System.out.println("Macスタイルのダイアログを描画");
    }
    
    @Override
    public void show() {
        System.out.println("Macダイアログを表示");
    }
}
