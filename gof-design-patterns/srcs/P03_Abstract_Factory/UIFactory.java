package P03_Abstract_Factory;

// 抽象ファクトリーインターフェース（Abstract Factory）
public interface UIFactory {
    Button createButton();
    Dialog createDialog();
}
