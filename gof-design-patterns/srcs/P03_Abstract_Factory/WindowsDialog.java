package P03_Abstract_Factory;

// 具象製品クラス（Concrete Product） - Windows用
public class WindowsDialog implements Dialog {
    @Override
    public void render() {
        System.out.println("Windowsスタイルのダイアログを描画");
    }
    
    @Override
    public void show() {
        System.out.println("Windowsダイアログを表示");
    }
}
