package P03_Abstract_Factory;

// 具象ファクトリークラス（Concrete Factory） - Mac用
public class MacUIFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }
    
    @Override
    public Dialog createDialog() {
        return new MacDialog();
    }
}
