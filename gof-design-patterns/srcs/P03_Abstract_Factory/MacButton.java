package P03_Abstract_Factory;

// 具象製品クラス（Concrete Product） - Mac用
public class MacButton implements Button {
    @Override
    public void paint() {
        System.out.println("Macスタイルのボタンを描画");
    }
    
    @Override
    public void onClick() {
        System.out.println("Macボタンがクリックされました");
    }
}
