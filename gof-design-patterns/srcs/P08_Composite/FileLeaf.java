package P08_Composite;

// Leaf
public class FileLeaf implements FileComponent {
    private String name;
    private int size;
    
    public FileLeaf(String name, int size) {
        this.name = name;
        this.size = size;
    }
    
    @Override
    public void showDetails() {
        System.out.println("ファイル: " + name + ", サイズ: " + size + "KB");
    }
    
    @Override
    public int getSize() {
        return size;
    }
    
    @Override
    public String getName() {
        return name;
    }
}