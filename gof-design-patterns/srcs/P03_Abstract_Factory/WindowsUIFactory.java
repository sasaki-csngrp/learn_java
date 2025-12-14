package P03_Abstract_Factory;

// 具象ファクトリークラス（Concrete Factory） - Windows用
public class WindowsUIFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
    
    @Override
    public Dialog createDialog() {
        return new WindowsDialog();
    }
}
