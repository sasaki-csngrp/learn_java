package P03_Abstract_Factory;

// 具象製品クラス（Concrete Product） - Linux用
public class LinuxDialog implements Dialog {
    @Override
    public void render() {
        System.out.println("Linuxスタイルのダイアログを描画");
    }
    
    @Override
    public void show() {
        System.out.println("Linuxダイアログを表示");
    }
}
