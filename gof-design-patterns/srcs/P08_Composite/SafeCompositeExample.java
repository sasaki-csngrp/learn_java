package P08_Composite;

// 使用例
public class SafeCompositeExample {
    public static void main(String[] args) {
        // ファイルを作成
        FileLeaf file1 = new FileLeaf("Document.txt", 120);
        FileLeaf file2 = new FileLeaf("Photo.jpg", 450);
        FileLeaf file3 = new FileLeaf("Video.mp4", 1200);
        
        // フォルダを作成
        FolderComposite folder1 = new Folder("MyDocuments");
        FolderComposite folder2 = new Folder("Images");
        
        // ファイルをフォルダに追加
        folder1.addComponent(file1);
        folder1.addComponent(file2);
        folder2.addComponent(file3);
        
        // フォルダを別のフォルダに追加（再帰的な構造）
        folder1.addComponent(folder2);
        
        // 詳細を表示
        folder1.showDetails();
        System.out.println("合計サイズ: " + folder1.getSize() + "KB");
    }
}
