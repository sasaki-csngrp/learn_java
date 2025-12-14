package P03_Abstract_Factory;

// 具象製品クラス（Concrete Product） - Linux用
public class LinuxButton implements Button {
    @Override
    public void paint() {
        System.out.println("Linuxスタイルのボタンを描画");
    }
    
    @Override
    public void onClick() {
        System.out.println("Linuxボタンがクリックされました");
    }
}
