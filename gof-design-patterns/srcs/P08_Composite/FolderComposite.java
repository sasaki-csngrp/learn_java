package P08_Composite;

// Composite（管理メソッドを含む）
public interface FolderComposite extends FileComponent {
    void addComponent(FileComponent component);
    void removeComponent(FileComponent component);
    FileComponent getChild(int index);
    int getComponentCount();
}
