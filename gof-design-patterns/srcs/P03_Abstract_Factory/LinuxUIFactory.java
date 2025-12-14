package P03_Abstract_Factory;

// 具象ファクトリークラス（Concrete Factory） - Linux用
public class LinuxUIFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new LinuxButton();
    }
    
    @Override
    public Dialog createDialog() {
        return new LinuxDialog();
    }
}
