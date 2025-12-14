package P03_Abstract_Factory;

// 具象製品クラス（Concrete Product） - Windows用
public class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("Windowsスタイルのボタンを描画");
    }
    
    @Override
    public void onClick() {
        System.out.println("Windowsボタンがクリックされました");
    }
}
