# コンポジットパターン（Composite Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [コンポジットパターンとは](#コンポジットパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

コンポジットパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**構造に関するパターン（Structural Pattern）**に分類されます。

このパターンは、オブジェクトをツリー構造に組み合わせて、部分-全体階層を表現します。クライアントが個々のオブジェクトとオブジェクトの合成を統一して扱えるようにすることで、複雑な階層構造の管理を簡素化します。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- コンポジットパターンの目的と利点を理解する
- 基本的なコンポジットパターンの実装方法を理解する
- 部分-全体階層の表現方法を理解する
- 実際のプロジェクトでコンポジットパターンを適用できる

---

## コンポジットパターンとは

### 定義

コンポジットパターンは、オブジェクトをツリー構造に組み合わせて、部分-全体階層を表現するデザインパターンです。このパターンにより、クライアントは個々のオブジェクト（Leaf）とオブジェクトの合成（Composite）を統一して扱うことができ、複雑な階層構造を簡潔に管理できます。

### 主な特徴

1. **統一的な扱い**: 個々のオブジェクトとオブジェクトの合成を統一して扱える
2. **再帰的な構造**: コンポジットが他のコンポジットを含む再帰的な構造を表現
3. **柔軟性**: 階層構造を動的に構築・変更可能
4. **拡張性**: 新しい種類のコンポーネントを追加する際に、既存のコードを変更する必要がない

### 使用される場面

コンポジットパターンは、以下のような場面で使用されます：

- **ファイルシステム**: ファイルとフォルダの階層構造
- **UIコンポーネント**: ウィンドウ、パネル、ボタンなどの階層構造
- **組織構造**: 部署、チーム、従業員の階層構造
- **メニューシステム**: メニューとサブメニューの階層構造
- **グラフィックシステム**: 図形とグループの階層構造
- **ドキュメント構造**: セクション、段落、文の階層構造

### メリット

- **統一性**: 個々のオブジェクトと合成を統一して扱えるため、クライアントコードが簡潔になる
- **柔軟性**: 階層構造を動的に構築・変更可能
- **拡張性**: 新しい種類のコンポーネントを追加する際に、既存のコードを変更する必要がない（オープン・クローズドの原則）
- **再帰的な処理**: 再帰的な構造を自然に表現・処理できる

### デメリット

- **型安全性**: コンポジットとリーフを統一して扱うため、型安全性が低下する可能性
- **複雑性の増加**: 階層構造が深くなると、理解が難しくなる
- **パフォーマンス**: 再帰的な処理により、パフォーマンスに影響を与える可能性

---

## 基本的な実装

### 実装のポイント

コンポジットパターンを実装するには、以下の要素が必要です：

1. **Component（コンポーネント）**: 個々のオブジェクトと合成の両方に共通するインターフェースまたは抽象クラス
2. **Leaf（リーフ）**: 個々のオブジェクトを表すクラス（子を持たない）
3. **Composite（コンポジット）**: コンポーネントの集合を表すクラス（子を持つ）

### 基本的な実装例

```java
import java.util.ArrayList;
import java.util.List;

// 1. Component（コンポーネント）
public abstract class FileComponent {
    protected String name;
    
    public FileComponent(String name) {
        this.name = name;
    }
    
    public abstract void showDetails();
    public abstract int getSize();
    
    // デフォルト実装（Leafでは使用しない）
    public void addComponent(FileComponent component) {
        throw new UnsupportedOperationException("この操作はサポートされていません");
    }
    
    public void removeComponent(FileComponent component) {
        throw new UnsupportedOperationException("この操作はサポートされていません");
    }
    
    public FileComponent getChild(int index) {
        throw new UnsupportedOperationException("この操作はサポートされていません");
    }
    
    public String getName() {
        return name;
    }
}

// 2. Leaf（リーフ）
public class FileLeaf extends FileComponent {
    private int size;
    
    public FileLeaf(String name, int size) {
        super(name);
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
}

// 3. Composite（コンポジット）
public class FolderComposite extends FileComponent {
    private List<FileComponent> components = new ArrayList<>();
    
    public FolderComposite(String name) {
        super(name);
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
    
    public int getComponentCount() {
        return components.size();
    }
}

// 4. クライアントコード
public class CompositePatternExample {
    public static void main(String[] args) {
        // ファイルを作成
        FileLeaf file1 = new FileLeaf("Document.txt", 120);
        FileLeaf file2 = new FileLeaf("Photo.jpg", 450);
        FileLeaf file3 = new FileLeaf("Video.mp4", 1200);
        
        // フォルダを作成
        FolderComposite folder1 = new FolderComposite("MyDocuments");
        FolderComposite folder2 = new FolderComposite("Images");
        
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
```

### パターンの構造

```
Component (抽象クラス/インターフェース)
  ├─ Leaf (個々のオブジェクト)
  └─ Composite (コンポーネントの集合)
      └─ Component[] (子コンポーネントのリスト)
```

### 実行結果

```
フォルダ: MyDocuments
ファイル: Document.txt, サイズ: 120KB
ファイル: Photo.jpg, サイズ: 450KB
フォルダ: Images
ファイル: Video.mp4, サイズ: 1200KB
合計サイズ: 1770KB
```

### コンポジットパターンを使わない場合の問題

コンポジットパターンを使わない場合、個々のオブジェクトと合成を区別して扱う必要があります：

```java
// 問題のある設計：個々のオブジェクトと合成を区別
public void processFile(File file) { }
public void processFolder(Folder folder) { }
// クライアントコードが複雑になる
```

コンポジットパターンを使用することで、個々のオブジェクトと合成を統一して扱え、クライアントコードが簡潔になります。

---

## 実装のバリエーション

### バリエーション1: 安全なコンポジット（Safe Composite）

Leafで使用しないメソッドをComponentに含めない方法です。

```java
// Component（最小限のインターフェース）
public interface FileComponent {
    void showDetails();
    int getSize();
    String getName();
}

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

// Composite（管理メソッドを含む）
public interface FolderComposite extends FileComponent {
    void addComponent(FileComponent component);
    void removeComponent(FileComponent component);
    FileComponent getChild(int index);
    int getComponentCount();
}

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

// 使用例
public class SafeCompositeExample {
    public static void main(String[] args) {
        FileComponent file = new FileLeaf("test.txt", 100);
        FolderComposite folder = new Folder("MyFolder");
        
        // LeafではaddComponentが使用できない（コンパイルエラー）
        // file.addComponent(...); // エラー！
        
        // Compositeでのみ使用可能
        folder.addComponent(file);
        folder.showDetails();
    }
}
```

**メリット**: 型安全性が高い
**デメリット**: クライアントがLeafとCompositeを区別する必要がある

### バリエーション2: 透過的なコンポジット（Transparent Composite）

Leafでも管理メソッドを使用できるようにする方法です（基本実装例で使用）。

```java
// Component（すべてのメソッドを含む）
public abstract class FileComponent {
    protected String name;
    
    public FileComponent(String name) {
        this.name = name;
    }
    
    public abstract void showDetails();
    public abstract int getSize();
    
    // デフォルト実装（Leafでは例外をスロー）
    public void addComponent(FileComponent component) {
        throw new UnsupportedOperationException();
    }
    
    public void removeComponent(FileComponent component) {
        throw new UnsupportedOperationException();
    }
    
    public FileComponent getChild(int index) {
        throw new UnsupportedOperationException();
    }
    
    public String getName() {
        return name;
    }
}

// 使用例
public class TransparentCompositeExample {
    public static void main(String[] args) {
        FileComponent file = new FileLeaf("test.txt", 100);
        FileComponent folder = new FolderComposite("MyFolder");
        
        // どちらも同じインターフェースで扱える
        folder.addComponent(file);
        
        // ただし、LeafでaddComponentを呼ぶと例外が発生
        try {
            file.addComponent(new FileLeaf("another.txt", 50));
        } catch (UnsupportedOperationException e) {
            System.out.println("LeafではaddComponentは使用できません");
        }
    }
}
```

**メリット**: クライアントがLeafとCompositeを区別する必要がない
**デメリット**: 実行時エラーの可能性がある

### バリエーション3: イテレーターパターンとの組み合わせ

コンポジット内の要素を走査する際にイテレーターパターンを使用する方法です。

```java
import java.util.Iterator;

// Component
public abstract class FileComponent {
    protected String name;
    
    public FileComponent(String name) {
        this.name = name;
    }
    
    public abstract void showDetails();
    public abstract int getSize();
    public abstract Iterator<FileComponent> createIterator();
    
    public String getName() {
        return name;
    }
}

// Leaf
public class FileLeaf extends FileComponent {
    private int size;
    
    public FileLeaf(String name, int size) {
        super(name);
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
    public Iterator<FileComponent> createIterator() {
        return new NullIterator();
    }
    
    // 空のイテレーター
    private class NullIterator implements Iterator<FileComponent> {
        @Override
        public boolean hasNext() {
            return false;
        }
        
        @Override
        public FileComponent next() {
            return null;
        }
    }
}

// Composite
public class FolderComposite extends FileComponent {
    private List<FileComponent> components = new ArrayList<>();
    
    public FolderComposite(String name) {
        super(name);
    }
    
    public void addComponent(FileComponent component) {
        components.add(component);
    }
    
    @Override
    public void showDetails() {
        System.out.println("フォルダ: " + name);
        Iterator<FileComponent> iterator = createIterator();
        while (iterator.hasNext()) {
            iterator.next().showDetails();
        }
    }
    
    @Override
    public int getSize() {
        int totalSize = 0;
        Iterator<FileComponent> iterator = createIterator();
        while (iterator.hasNext()) {
            totalSize += iterator.next().getSize();
        }
        return totalSize;
    }
    
    @Override
    public Iterator<FileComponent> createIterator() {
        return components.iterator();
    }
}

// 使用例
public class IteratorCompositeExample {
    public static void main(String[] args) {
        FolderComposite folder = new FolderComposite("MyFolder");
        folder.addComponent(new FileLeaf("file1.txt", 100));
        folder.addComponent(new FileLeaf("file2.txt", 200));
        
        Iterator<FileComponent> iterator = folder.createIterator();
        while (iterator.hasNext()) {
            FileComponent component = iterator.next();
            System.out.println("コンポーネント: " + component.getName());
        }
    }
}
```

---

## 実践例

### 例1: ファイルシステム

ファイルとフォルダの階層構造を表現する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Component
public abstract class FileSystemComponent {
    protected String name;
    protected String path;
    
    public FileSystemComponent(String name, String path) {
        this.name = name;
        this.path = path;
    }
    
    public abstract void display(int depth);
    public abstract long getSize();
    public abstract int getFileCount();
    public abstract int getFolderCount();
    
    public String getName() {
        return name;
    }
    
    public String getPath() {
        return path;
    }
    
    // デフォルト実装
    public void addComponent(FileSystemComponent component) {
        throw new UnsupportedOperationException();
    }
    
    public void removeComponent(FileSystemComponent component) {
        throw new UnsupportedOperationException();
    }
}

// Leaf
public class File extends FileSystemComponent {
    private long size;
    
    public File(String name, String path, long size) {
        super(name, path);
        this.size = size;
    }
    
    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "📄 " + name + " (" + size + " bytes)");
    }
    
    @Override
    public long getSize() {
        return size;
    }
    
    @Override
    public int getFileCount() {
        return 1;
    }
    
    @Override
    public int getFolderCount() {
        return 0;
    }
}

// Composite
public class Folder extends FileSystemComponent {
    private List<FileSystemComponent> components = new ArrayList<>();
    
    public Folder(String name, String path) {
        super(name, path);
    }
    
    @Override
    public void addComponent(FileSystemComponent component) {
        components.add(component);
    }
    
    @Override
    public void removeComponent(FileSystemComponent component) {
        components.remove(component);
    }
    
    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "📁 " + name + "/");
        for (FileSystemComponent component : components) {
            component.display(depth + 1);
        }
    }
    
    @Override
    public long getSize() {
        long totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
    
    @Override
    public int getFileCount() {
        int count = 0;
        for (FileSystemComponent component : components) {
            count += component.getFileCount();
        }
        return count;
    }
    
    @Override
    public int getFolderCount() {
        int count = 1; // 自分自身
        for (FileSystemComponent component : components) {
            count += component.getFolderCount();
        }
        return count;
    }
}

// 使用例
public class FileSystemExample {
    public static void main(String[] args) {
        // ルートフォルダ
        Folder root = new Folder("root", "/");
        
        // サブフォルダ
        Folder documents = new Folder("Documents", "/Documents");
        Folder images = new Folder("Images", "/Images");
        
        // ファイル
        File file1 = new File("readme.txt", "/readme.txt", 1024);
        File file2 = new File("document.pdf", "/Documents/document.pdf", 2048);
        File file3 = new File("photo.jpg", "/Images/photo.jpg", 4096);
        
        // 構造を構築
        root.addComponent(file1);
        root.addComponent(documents);
        root.addComponent(images);
        documents.addComponent(file2);
        images.addComponent(file3);
        
        // 表示
        root.display(0);
        System.out.println("\n合計サイズ: " + root.getSize() + " bytes");
        System.out.println("ファイル数: " + root.getFileCount());
        System.out.println("フォルダ数: " + root.getFolderCount());
    }
}
```

### 例2: UIコンポーネント

ウィンドウ、パネル、ボタンなどの階層構造を表現する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Component
public abstract class UIComponent {
    protected String name;
    protected int x, y, width, height;
    
    public UIComponent(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public abstract void render();
    public abstract void handleClick(int x, int y);
    
    public String getName() {
        return name;
    }
    
    // デフォルト実装
    public void addComponent(UIComponent component) {
        throw new UnsupportedOperationException();
    }
    
    public void removeComponent(UIComponent component) {
        throw new UnsupportedOperationException();
    }
    
    protected boolean isPointInside(int px, int py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }
}

// Leaf
public class Button extends UIComponent {
    private String label;
    private Runnable onClickHandler;
    
    public Button(String name, int x, int y, int width, int height, String label) {
        super(name, x, y, width, height);
        this.label = label;
    }
    
    public void setOnClickHandler(Runnable handler) {
        this.onClickHandler = handler;
    }
    
    @Override
    public void render() {
        System.out.println("  [ボタン] " + name + ": \"" + label + "\" at (" + x + ", " + y + ")");
    }
    
    @Override
    public void handleClick(int x, int y) {
        if (isPointInside(x, y)) {
            System.out.println("ボタン \"" + label + "\" がクリックされました");
            if (onClickHandler != null) {
                onClickHandler.run();
            }
        }
    }
}

public class TextField extends UIComponent {
    private String text;
    
    public TextField(String name, int x, int y, int width, int height) {
        super(name, x, y, width, height);
        this.text = "";
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    @Override
    public void render() {
        System.out.println("  [テキストフィールド] " + name + ": \"" + text + "\" at (" + x + ", " + y + ")");
    }
    
    @Override
    public void handleClick(int x, int y) {
        if (isPointInside(x, y)) {
            System.out.println("テキストフィールド " + name + " がクリックされました");
        }
    }
}

// Composite
public class Panel extends UIComponent {
    private List<UIComponent> components = new ArrayList<>();
    private String backgroundColor;
    
    public Panel(String name, int x, int y, int width, int height, String backgroundColor) {
        super(name, x, y, width, height);
        this.backgroundColor = backgroundColor;
    }
    
    @Override
    public void addComponent(UIComponent component) {
        components.add(component);
    }
    
    @Override
    public void removeComponent(UIComponent component) {
        components.remove(component);
    }
    
    @Override
    public void render() {
        System.out.println("[パネル] " + name + " (背景色: " + backgroundColor + ") at (" + x + ", " + y + ")");
        for (UIComponent component : components) {
            component.render();
        }
    }
    
    @Override
    public void handleClick(int x, int y) {
        if (isPointInside(x, y)) {
            System.out.println("パネル " + name + " がクリックされました");
            // 子コンポーネントにクリックイベントを伝播
            for (UIComponent component : components) {
                component.handleClick(x, y);
            }
        }
    }
}

public class Window extends UIComponent {
    private List<UIComponent> components = new ArrayList<>();
    private String title;
    
    public Window(String name, int x, int y, int width, int height, String title) {
        super(name, x, y, width, height);
        this.title = title;
    }
    
    @Override
    public void addComponent(UIComponent component) {
        components.add(component);
    }
    
    @Override
    public void removeComponent(UIComponent component) {
        components.remove(component);
    }
    
    @Override
    public void render() {
        System.out.println("=== ウィンドウ: " + title + " ===");
        for (UIComponent component : components) {
            component.render();
        }
    }
    
    @Override
    public void handleClick(int x, int y) {
        System.out.println("ウィンドウ " + title + " がクリックされました");
        for (UIComponent component : components) {
            component.handleClick(x, y);
        }
    }
}

// 使用例
public class UIComponentExample {
    public static void main(String[] args) {
        // ウィンドウを作成
        Window window = new Window("mainWindow", 0, 0, 800, 600, "メインウィンドウ");
        
        // パネルを作成
        Panel mainPanel = new Panel("mainPanel", 10, 10, 780, 580, "白");
        Panel buttonPanel = new Panel("buttonPanel", 10, 500, 780, 70, "グレー");
        
        // ボタンを作成
        Button okButton = new Button("okButton", 600, 510, 80, 30, "OK");
        Button cancelButton = new Button("cancelButton", 690, 510, 80, 30, "キャンセル");
        
        // テキストフィールドを作成
        TextField nameField = new TextField("nameField", 10, 50, 200, 30);
        nameField.setText("山田太郎");
        
        // 構造を構築
        window.addComponent(mainPanel);
        mainPanel.addComponent(nameField);
        mainPanel.addComponent(buttonPanel);
        buttonPanel.addComponent(okButton);
        buttonPanel.addComponent(cancelButton);
        
        // レンダリング
        window.render();
        
        System.out.println();
        
        // クリックイベント
        window.handleClick(650, 525);
    }
}
```

### 例3: 組織構造

部署、チーム、従業員の階層構造を表現する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Component
public abstract class OrganizationComponent {
    protected String name;
    protected String role;
    
    public OrganizationComponent(String name, String role) {
        this.name = name;
        this.role = role;
    }
    
    public abstract void display(int depth);
    public abstract double getTotalSalary();
    public abstract int getEmployeeCount();
    
    public String getName() {
        return name;
    }
    
    public String getRole() {
        return role;
    }
    
    // デフォルト実装
    public void addComponent(OrganizationComponent component) {
        throw new UnsupportedOperationException();
    }
    
    public void removeComponent(OrganizationComponent component) {
        throw new UnsupportedOperationException();
    }
}

// Leaf
public class Employee extends OrganizationComponent {
    private double salary;
    private String department;
    
    public Employee(String name, String role, double salary, String department) {
        super(name, role);
        this.salary = salary;
        this.department = department;
    }
    
    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "👤 " + name + " (" + role + ") - 給与: " + salary + "円");
    }
    
    @Override
    public double getTotalSalary() {
        return salary;
    }
    
    @Override
    public int getEmployeeCount() {
        return 1;
    }
    
    public double getSalary() {
        return salary;
    }
}

// Composite
public class Department extends OrganizationComponent {
    private List<OrganizationComponent> components = new ArrayList<>();
    
    public Department(String name, String role) {
        super(name, role);
    }
    
    @Override
    public void addComponent(OrganizationComponent component) {
        components.add(component);
    }
    
    @Override
    public void removeComponent(OrganizationComponent component) {
        components.remove(component);
    }
    
    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "🏢 " + name + " (" + role + ")");
        for (OrganizationComponent component : components) {
            component.display(depth + 1);
        }
    }
    
    @Override
    public double getTotalSalary() {
        double total = 0;
        for (OrganizationComponent component : components) {
            total += component.getTotalSalary();
        }
        return total;
    }
    
    @Override
    public int getEmployeeCount() {
        int count = 0;
        for (OrganizationComponent component : components) {
            count += component.getEmployeeCount();
        }
        return count;
    }
}

// 使用例
public class OrganizationExample {
    public static void main(String[] args) {
        // 組織構造を作成
        Department company = new Department("株式会社ABC", "会社");
        
        Department salesDept = new Department("営業部", "部署");
        Department itDept = new Department("IT部", "部署");
        
        Department salesTeam1 = new Department("営業1チーム", "チーム");
        Department salesTeam2 = new Department("営業2チーム", "チーム");
        Department devTeam = new Department("開発チーム", "チーム");
        
        // 従業員を作成
        Employee emp1 = new Employee("山田太郎", "営業マネージャー", 500000, "営業部");
        Employee emp2 = new Employee("佐藤花子", "営業担当", 350000, "営業部");
        Employee emp3 = new Employee("鈴木一郎", "営業担当", 320000, "営業部");
        Employee emp4 = new Employee("田中次郎", "開発マネージャー", 600000, "IT部");
        Employee emp5 = new Employee("高橋三郎", "エンジニア", 450000, "IT部");
        
        // 構造を構築
        company.addComponent(salesDept);
        company.addComponent(itDept);
        
        salesDept.addComponent(salesTeam1);
        salesDept.addComponent(salesTeam2);
        itDept.addComponent(devTeam);
        
        salesTeam1.addComponent(emp1);
        salesTeam1.addComponent(emp2);
        salesTeam2.addComponent(emp3);
        devTeam.addComponent(emp4);
        devTeam.addComponent(emp5);
        
        // 表示
        company.display(0);
        System.out.println("\n総給与: " + company.getTotalSalary() + "円");
        System.out.println("従業員数: " + company.getEmployeeCount() + "人");
    }
}
```

### 例4: メニューシステム

メニューとサブメニューの階層構造を表現する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Component
public abstract class MenuComponent {
    protected String name;
    protected String description;
    
    public MenuComponent(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public abstract void display(int depth);
    public abstract void execute();
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    // デフォルト実装
    public void addComponent(MenuComponent component) {
        throw new UnsupportedOperationException();
    }
    
    public void removeComponent(MenuComponent component) {
        throw new UnsupportedOperationException();
    }
    
    public MenuComponent getChild(int index) {
        throw new UnsupportedOperationException();
    }
}

// Leaf
public class MenuItem extends MenuComponent {
    private Runnable action;
    
    public MenuItem(String name, String description, Runnable action) {
        super(name, description);
        this.action = action;
    }
    
    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "• " + name + " - " + description);
    }
    
    @Override
    public void execute() {
        System.out.println("メニュー項目 \"" + name + "\" を実行します");
        if (action != null) {
            action.run();
        }
    }
}

// Composite
public class Menu extends MenuComponent {
    private List<MenuComponent> components = new ArrayList<>();
    
    public Menu(String name, String description) {
        super(name, description);
    }
    
    @Override
    public void addComponent(MenuComponent component) {
        components.add(component);
    }
    
    @Override
    public void removeComponent(MenuComponent component) {
        components.remove(component);
    }
    
    @Override
    public MenuComponent getChild(int index) {
        return components.get(index);
    }
    
    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "📁 " + name + " - " + description);
        for (MenuComponent component : components) {
            component.display(depth + 1);
        }
    }
    
    @Override
    public void execute() {
        System.out.println("メニュー \"" + name + "\" を開きます");
        display(0);
    }
    
    public void executeItem(String itemName) {
        for (MenuComponent component : components) {
            if (component.getName().equals(itemName)) {
                component.execute();
                return;
            }
            // 再帰的に検索
            if (component instanceof Menu) {
                ((Menu) component).executeItem(itemName);
            }
        }
    }
}

// 使用例
public class MenuSystemExample {
    public static void main(String[] args) {
        // メインメニュー
        Menu mainMenu = new Menu("メインメニュー", "アプリケーションのメインメニュー");
        
        // ファイルメニュー
        Menu fileMenu = new Menu("ファイル", "ファイル操作");
        MenuItem newFile = new MenuItem("新規", "新しいファイルを作成", () -> System.out.println("新規ファイルを作成"));
        MenuItem openFile = new MenuItem("開く", "ファイルを開く", () -> System.out.println("ファイルを開く"));
        MenuItem saveFile = new MenuItem("保存", "ファイルを保存", () -> System.out.println("ファイルを保存"));
        
        // 編集メニュー
        Menu editMenu = new Menu("編集", "編集操作");
        MenuItem undo = new MenuItem("元に戻す", "操作を元に戻す", () -> System.out.println("元に戻す"));
        MenuItem redo = new MenuItem("やり直す", "操作をやり直す", () -> System.out.println("やり直す"));
        
        // 表示メニュー
        Menu viewMenu = new Menu("表示", "表示設定");
        MenuItem zoomIn = new MenuItem("拡大", "表示を拡大", () -> System.out.println("拡大"));
        MenuItem zoomOut = new MenuItem("縮小", "表示を縮小", () -> System.out.println("縮小"));
        
        // 構造を構築
        mainMenu.addComponent(fileMenu);
        mainMenu.addComponent(editMenu);
        mainMenu.addComponent(viewMenu);
        
        fileMenu.addComponent(newFile);
        fileMenu.addComponent(openFile);
        fileMenu.addComponent(saveFile);
        
        editMenu.addComponent(undo);
        editMenu.addComponent(redo);
        
        viewMenu.addComponent(zoomIn);
        viewMenu.addComponent(zoomOut);
        
        // メニューを表示
        mainMenu.execute();
        
        System.out.println();
        
        // 特定のメニュー項目を実行
        mainMenu.executeItem("保存");
    }
}
```

### 例5: グラフィックシステム

図形とグループの階層構造を表現する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Component
public abstract class Graphic {
    protected String name;
    protected int x, y;
    
    public Graphic(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public abstract void draw();
    public abstract void move(int deltaX, int deltaY);
    public abstract Graphic getBounds();
    
    public String getName() {
        return name;
    }
    
    // デフォルト実装
    public void addGraphic(Graphic graphic) {
        throw new UnsupportedOperationException();
    }
    
    public void removeGraphic(Graphic graphic) {
        throw new UnsupportedOperationException();
    }
}

// Leaf
public class Circle extends Graphic {
    private int radius;
    
    public Circle(String name, int x, int y, int radius) {
        super(name, x, y);
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        System.out.println("  ⭕ 円: " + name + " at (" + x + ", " + y + ") 半径: " + radius);
    }
    
    @Override
    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
        System.out.println("円 " + name + " を (" + deltaX + ", " + deltaY + ") 移動");
    }
    
    @Override
    public Graphic getBounds() {
        return new Rectangle("bounds_" + name, x - radius, y - radius, radius * 2, radius * 2);
    }
}

public class Rectangle extends Graphic {
    private int width, height;
    
    public Rectangle(String name, int x, int y, int width, int height) {
        super(name, x, y);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void draw() {
        System.out.println("  ▭ 四角形: " + name + " at (" + x + ", " + y + ") サイズ: " + width + "x" + height);
    }
    
    @Override
    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
        System.out.println("四角形 " + name + " を (" + deltaX + ", " + deltaY + ") 移動");
    }
    
    @Override
    public Graphic getBounds() {
        return new Rectangle("bounds_" + name, x, y, width, height);
    }
}

// Composite
public class Group extends Graphic {
    private List<Graphic> graphics = new ArrayList<>();
    
    public Group(String name, int x, int y) {
        super(name, x, y);
    }
    
    @Override
    public void addGraphic(Graphic graphic) {
        graphics.add(graphic);
    }
    
    @Override
    public void removeGraphic(Graphic graphic) {
        graphics.remove(graphic);
    }
    
    @Override
    public void draw() {
        System.out.println("📦 グループ: " + name + " at (" + x + ", " + y + ")");
        for (Graphic graphic : graphics) {
            graphic.draw();
        }
    }
    
    @Override
    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
        System.out.println("グループ " + name + " を (" + deltaX + ", " + deltaY + ") 移動");
        for (Graphic graphic : graphics) {
            graphic.move(deltaX, deltaY);
        }
    }
    
    @Override
    public Graphic getBounds() {
        // グループ内のすべての図形の境界を計算
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        
        for (Graphic graphic : graphics) {
            Graphic bounds = graphic.getBounds();
            if (bounds instanceof Rectangle) {
                Rectangle rect = (Rectangle) bounds;
                minX = Math.min(minX, rect.x);
                minY = Math.min(minY, rect.y);
                maxX = Math.max(maxX, rect.x + rect.width);
                maxY = Math.max(maxY, rect.y + rect.height);
            }
        }
        
        return new Rectangle("bounds_" + name, minX, minY, maxX - minX, maxY - minY);
    }
}

// 使用例
public class GraphicSystemExample {
    public static void main(String[] args) {
        // 図形を作成
        Circle circle1 = new Circle("circle1", 10, 10, 5);
        Circle circle2 = new Circle("circle2", 30, 30, 8);
        Rectangle rect1 = new Rectangle("rect1", 50, 50, 20, 15);
        
        // グループを作成
        Group group1 = new Group("group1", 0, 0);
        group1.addGraphic(circle1);
        group1.addGraphic(circle2);
        
        Group mainGroup = new Group("mainGroup", 0, 0);
        mainGroup.addGraphic(group1);
        mainGroup.addGraphic(rect1);
        
        // 描画
        mainGroup.draw();
        
        System.out.println();
        
        // 移動
        mainGroup.move(5, 5);
        
        System.out.println();
        
        // 再描画
        mainGroup.draw();
    }
}
```

---

## まとめ

### 学習のポイント

1. **コンポジットパターンの目的**: オブジェクトをツリー構造に組み合わせて、部分-全体階層を表現
2. **基本的な構造**: Component、Leaf、Compositeの3つの主要コンポーネント
3. **統一的な扱い**: 個々のオブジェクトと合成を統一して扱えるため、クライアントコードが簡潔になる
4. **再帰的な処理**: 再帰的な構造を自然に表現・処理できる

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 統一性、柔軟性、拡張性、再帰的な処理 |
| **注意点** | 型安全性、複雑性の増加、パフォーマンス |
| **適用場面** | ファイルシステム、UIコンポーネント、組織構造、メニューシステムなど |

### 安全なコンポジット vs 透過的なコンポジット

| 項目 | 安全なコンポジット | 透過的なコンポジット |
|------|------------------|-------------------|
| **型安全性** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **クライアントの簡潔性** | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| **実行時エラー** | なし | 可能性あり |
| **推奨度** | 型安全性が重要な場合 | 簡潔性が重要な場合 |

### 他のパターンとの関係

- **Decorator**: コンポジットは階層構造を表現するが、デコレーターは機能を追加する
- **Iterator**: コンポジット内の要素を走査する際にイテレーターを使用することが多い
- **Visitor**: コンポジット構造を走査して処理を行う際にビジターを使用することが多い

### 注意点

1. **適切な使用場面**: 部分-全体階層を表現する場合に使用
2. **型安全性**: 透過的なコンポジットでは実行時エラーに注意
3. **パフォーマンス**: 深い階層構造では、再帰的な処理がパフォーマンスに影響を与える可能性
4. **循環参照**: コンポジットが自分自身を含む循環参照に注意

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. 実際のプロジェクトでコンポジットパターンを適用してみる
3. Iteratorパターンを学習する（コンポジット内の要素の走査）
4. Visitorパターンを学習する（コンポジット構造の処理）

### 参考資料

- [cs-techblog.com - コンポジットパターン](https://cs-techblog.com/technical/composite-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、コンポジットパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
