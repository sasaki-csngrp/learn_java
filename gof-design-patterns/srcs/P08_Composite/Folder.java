package P08_Composite;

import java.util.ArrayList;
import java.util.List;

// 具象Composite
public class Folder implements FolderComposite {
    private String name;
    private List<FileComponent> components = new ArrayList<>();
    
    public Folder(String name) {
        this.name = name;
    }
    
    @Override
    public void showDetails() {
        System.out.println("フォルダ: " + name);
        for (FileComponent component : components) {
            component.showDetails();
        }
    }
    
    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void addComponent(FileComponent component) {
        components.add(component);
    }
    
    @Override
    public void removeComponent(FileComponent component) {
        components.remove(component);
    }
    
    @Override
    public FileComponent getChild(int index) {
        return components.get(index);
    }
    
    @Override
    public int getComponentCount() {
        return components.size();
    }
}
