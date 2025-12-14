# ビジターパターン（Visitor Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [ビジターパターンとは](#ビジターパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

ビジターパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**振る舞いに関するパターン（Behavioral Pattern）**に分類されます。

このパターンは、オブジェクト構造に対する操作を分離し、既存のオブジェクト構造を変更せずに新しい操作を追加できるようにします。アルゴリズムとオブジェクト構造を分離することで、コードの保守性と拡張性を向上させます。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- ビジターパターンの目的と利点を理解する
- 基本的なビジターパターンの実装方法を理解する
- ダブルディスパッチの概念を理解する
- オブジェクト構造に対する操作を分離する方法を理解する
- 実際のプロジェクトでビジターパターンを適用できる

---

## ビジターパターンとは

### 定義

ビジターパターンは、オブジェクト構造に対する操作を分離するデザインパターンです。既存のオブジェクト構造を変更せずに、新しい操作を追加できるようにします。アルゴリズムとオブジェクト構造を分離することで、コードの保守性と拡張性を向上させます。

### 主な特徴

1. **操作の分離**: オブジェクト構造に対する操作を独立したビジタークラスに分離
2. **ダブルディスパッチ**: ビジターと要素の両方の型に基づいてメソッドを選択
3. **拡張性**: 新しい操作を追加する際に、既存のオブジェクト構造を変更する必要がない
4. **オープン・クローズドの原則**: 拡張に対して開いており、修正に対して閉じている

### 使用される場面

ビジターパターンは、以下のような場面で使用されます：

- **コンパイラ**: 抽象構文木（AST）に対する操作（型チェック、コード生成など）
- **ドキュメント処理**: ドキュメント要素に対する操作（レンダリング、エクスポート、スペルチェックなど）
- **グラフィックスシステム**: グラフィカルオブジェクトに対する操作（レンダリング、変換など）
- **構文解析**: 構文木に対する操作（評価、最適化など）
- **レポート生成**: データ構造に対する操作（集計、フォーマットなど）
- **ファイルシステム**: ファイルシステム要素に対する操作（検索、統計、バックアップなど）

### メリット

- **操作の分離**: オブジェクト構造に対する操作を独立したクラスに分離
- **拡張性**: 新しい操作を追加する際に、既存のオブジェクト構造を変更する必要がない
- **保守性**: 操作のロジックが1箇所に集約されるため、保守が容易
- **単一責任の原則**: 各ビジタークラスが単一の操作に責任を持つ
- **オープン・クローズドの原則**: 拡張に対して開いており、修正に対して閉じている

### デメリット

- **新しい要素の追加が困難**: 新しい要素型を追加する際に、すべてのビジターを更新する必要がある
- **要素とビジターの結合**: 要素とビジターが密結合になる
- **複雑性の増加**: シンプルなケースでは過剰な設計になる可能性がある
- **ダブルディスパッチの理解**: ダブルディスパッチの概念を理解する必要がある

---

## 基本的な実装

### 実装のポイント

ビジターパターンを実装するには、以下の要素が必要です：

1. **Visitorインターフェース（Visitor）**: 各要素型に対するvisitメソッドを定義
2. **ConcreteVisitor（具象ビジター）**: Visitorインターフェースを実装し、特定の操作を実装
3. **Elementインターフェース（Element）**: acceptメソッドを定義
4. **ConcreteElement（具象要素）**: Elementインターフェースを実装し、acceptメソッドでビジターを受け入れる

### 基本的な実装例

```java
// 1. Visitorインターフェース
public interface Visitor {
    void visit(ConcreteElementA element);
    void visit(ConcreteElementB element);
}

// 2. ConcreteVisitor（具象ビジター）
public class ConcreteVisitor1 implements Visitor {
    @Override
    public void visit(ConcreteElementA element) {
        System.out.println("Visitor1がConcreteElementAを処理: " + element.getData());
    }
    
    @Override
    public void visit(ConcreteElementB element) {
        System.out.println("Visitor1がConcreteElementBを処理: " + element.getData());
    }
}

public class ConcreteVisitor2 implements Visitor {
    @Override
    public void visit(ConcreteElementA element) {
        System.out.println("Visitor2がConcreteElementAを処理: " + element.getData());
    }
    
    @Override
    public void visit(ConcreteElementB element) {
        System.out.println("Visitor2がConcreteElementBを処理: " + element.getData());
    }
}

// 3. Elementインターフェース
public interface Element {
    void accept(Visitor visitor);
}

// 4. ConcreteElement（具象要素）
public class ConcreteElementA implements Element {
    private String data;
    
    public ConcreteElementA(String data) {
        this.data = data;
    }
    
    public String getData() {
        return data;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

public class ConcreteElementB implements Element {
    private String data;
    
    public ConcreteElementB(String data) {
        this.data = data;
    }
    
    public String getData() {
        return data;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
```

### 使用例

```java
import java.util.ArrayList;
import java.util.List;

public class VisitorExample {
    public static void main(String[] args) {
        List<Element> elements = new ArrayList<>();
        elements.add(new ConcreteElementA("要素Aのデータ"));
        elements.add(new ConcreteElementB("要素Bのデータ"));
        
        Visitor visitor1 = new ConcreteVisitor1();
        Visitor visitor2 = new ConcreteVisitor2();
        
        System.out.println("=== Visitor1による処理 ===");
        for (Element element : elements) {
            element.accept(visitor1);
        }
        
        System.out.println("\n=== Visitor2による処理 ===");
        for (Element element : elements) {
            element.accept(visitor2);
        }
    }
}
```

### パターンの構造

```
Visitor (インターフェース)
  ├─ visit(ConcreteElementA)
  └─ visit(ConcreteElementB)
        ↓
ConcreteVisitor1
  ├─ visit(ConcreteElementA) [実装]
  └─ visit(ConcreteElementB) [実装]
        ↓
Element (インターフェース)
  └─ accept(Visitor)
        ↓
ConcreteElementA
  └─ accept(Visitor) → visitor.visit(this)
        ↓
ConcreteElementB
  └─ accept(Visitor) → visitor.visit(this)
```

### ダブルディスパッチ

ビジターパターンは、ダブルディスパッチ（二重ディスパッチ）を使用します：

1. **第1のディスパッチ**: `element.accept(visitor)` - 要素の型に基づいてacceptメソッドが選択される
2. **第2のディスパッチ**: `visitor.visit(this)` - ビジターの型と要素の型に基づいてvisitメソッドが選択される

これにより、ビジターと要素の両方の型に基づいて適切なメソッドが呼び出されます。

---

## 実装のバリエーション

### バリエーション1: 戻り値を持つビジター

ビジターが結果を返す場合の実装です。

```java
// Visitorインターフェース
public interface Visitor<T> {
    T visit(ConcreteElementA element);
    T visit(ConcreteElementB element);
}

// ConcreteVisitor
public class SumVisitor implements Visitor<Integer> {
    @Override
    public Integer visit(ConcreteElementA element) {
        return element.getValue();
    }
    
    @Override
    public Integer visit(ConcreteElementB element) {
        return element.getValue();
    }
}

// Elementインターフェース
public interface Element {
    <T> T accept(Visitor<T> visitor);
}

// ConcreteElement
public class ConcreteElementA implements Element {
    private int value;
    
    public ConcreteElementA(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
```

### バリエーション2: デフォルト実装を持つビジター

抽象クラスを使用してデフォルト実装を提供する方法です。

```java
// Visitor抽象クラス
public abstract class Visitor {
    public void visit(ConcreteElementA element) {
        // デフォルト実装
        System.out.println("デフォルト処理: " + element.getData());
    }
    
    public void visit(ConcreteElementB element) {
        // デフォルト実装
        System.out.println("デフォルト処理: " + element.getData());
    }
}

// ConcreteVisitor（必要なメソッドのみオーバーライド）
public class SpecialVisitor extends Visitor {
    @Override
    public void visit(ConcreteElementA element) {
        System.out.println("特別な処理: " + element.getData());
    }
    // visit(ConcreteElementB)はデフォルト実装を使用
}
```

### バリエーション3: 複合構造に対するビジター

Compositeパターンと組み合わせた実装です。

```java
// Visitorインターフェース
public interface Visitor {
    void visit(File file);
    void visit(Folder folder);
}

// Elementインターフェース
public interface FileSystemElement {
    void accept(Visitor visitor);
}

// ConcreteElement
public class File implements FileSystemElement {
    private String name;
    private int size;
    
    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }
    
    public String getName() {
        return name;
    }
    
    public int getSize() {
        return size;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

public class Folder implements FileSystemElement {
    private String name;
    private java.util.List<FileSystemElement> children = new java.util.ArrayList<>();
    
    public Folder(String name) {
        this.name = name;
    }
    
    public void add(FileSystemElement element) {
        children.add(element);
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        for (FileSystemElement child : children) {
            child.accept(visitor);
        }
    }
}
```

---

## 実践例

### 例1: 構文木の評価

抽象構文木（AST）に対する操作を実装する例です。

```java
// Visitorインターフェース
public interface ExpressionVisitor {
    int visit(NumberExpression expr);
    int visit(AddExpression expr);
    int visit(SubtractExpression expr);
    int visit(MultiplyExpression expr);
}

// Elementインターフェース
public interface Expression {
    int accept(ExpressionVisitor visitor);
}

// ConcreteElement
public class NumberExpression implements Expression {
    private int value;
    
    public NumberExpression(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    public int accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}

public class AddExpression implements Expression {
    private Expression left;
    private Expression right;
    
    public AddExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    public Expression getLeft() {
        return left;
    }
    
    public Expression getRight() {
        return right;
    }
    
    @Override
    public int accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}

public class SubtractExpression implements Expression {
    private Expression left;
    private Expression right;
    
    public SubtractExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    public Expression getLeft() {
        return left;
    }
    
    public Expression getRight() {
        return right;
    }
    
    @Override
    public int accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}

public class MultiplyExpression implements Expression {
    private Expression left;
    private Expression right;
    
    public MultiplyExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    public Expression getLeft() {
        return left;
    }
    
    public Expression getRight() {
        return right;
    }
    
    @Override
    public int accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}

// ConcreteVisitor
public class EvaluateVisitor implements ExpressionVisitor {
    @Override
    public int visit(NumberExpression expr) {
        return expr.getValue();
    }
    
    @Override
    public int visit(AddExpression expr) {
        return expr.getLeft().accept(this) + expr.getRight().accept(this);
    }
    
    @Override
    public int visit(SubtractExpression expr) {
        return expr.getLeft().accept(this) - expr.getRight().accept(this);
    }
    
    @Override
    public int visit(MultiplyExpression expr) {
        return expr.getLeft().accept(this) * expr.getRight().accept(this);
    }
}

public class PrintVisitor implements ExpressionVisitor {
    @Override
    public int visit(NumberExpression expr) {
        System.out.print(expr.getValue());
        return 0;
    }
    
    @Override
    public int visit(AddExpression expr) {
        System.out.print("(");
        expr.getLeft().accept(this);
        System.out.print(" + ");
        expr.getRight().accept(this);
        System.out.print(")");
        return 0;
    }
    
    @Override
    public int visit(SubtractExpression expr) {
        System.out.print("(");
        expr.getLeft().accept(this);
        System.out.print(" - ");
        expr.getRight().accept(this);
        System.out.print(")");
        return 0;
    }
    
    @Override
    public int visit(MultiplyExpression expr) {
        System.out.print("(");
        expr.getLeft().accept(this);
        System.out.print(" * ");
        expr.getRight().accept(this);
        System.out.print(")");
        return 0;
    }
}

// 使用例
public class ExpressionVisitorExample {
    public static void main(String[] args) {
        // (3 + 4) * 2 を表現
        Expression expr = new MultiplyExpression(
            new AddExpression(
                new NumberExpression(3),
                new NumberExpression(4)
            ),
            new NumberExpression(2)
        );
        
        EvaluateVisitor evaluator = new EvaluateVisitor();
        PrintVisitor printer = new PrintVisitor();
        
        System.out.print("式: ");
        expr.accept(printer);
        System.out.println();
        
        int result = expr.accept(evaluator);
        System.out.println("結果: " + result);
    }
}
```

### 例2: ファイルシステムの操作

ファイルシステム要素に対する操作を実装する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Visitorインターフェース
public interface FileSystemVisitor {
    void visit(File file);
    void visit(Folder folder);
}

// Elementインターフェース
public interface FileSystemElement {
    void accept(FileSystemVisitor visitor);
    String getName();
    int getSize();
}

// ConcreteElement
public class File implements FileSystemElement {
    private String name;
    private int size;
    
    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getSize() {
        return size;
    }
    
    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
    }
}

public class Folder implements FileSystemElement {
    private String name;
    private List<FileSystemElement> children = new ArrayList<>();
    
    public Folder(String name) {
        this.name = name;
    }
    
    public void add(FileSystemElement element) {
        children.add(element);
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemElement child : children) {
            totalSize += child.getSize();
        }
        return totalSize;
    }
    
    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
        for (FileSystemElement child : children) {
            child.accept(visitor);
        }
    }
}

// ConcreteVisitor
public class PrintVisitor implements FileSystemVisitor {
    private int indent = 0;
    
    @Override
    public void visit(File file) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
        System.out.println("ファイル: " + file.getName() + " (" + file.getSize() + " bytes)");
    }
    
    @Override
    public void visit(Folder folder) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
        System.out.println("フォルダ: " + folder.getName());
        indent++;
        // 子要素の訪問はFolderのacceptメソッドで行われる
        indent--;
    }
}

public class SizeVisitor implements FileSystemVisitor {
    private int totalSize = 0;
    
    @Override
    public void visit(File file) {
        totalSize += file.getSize();
    }
    
    @Override
    public void visit(Folder folder) {
        // フォルダ自体のサイズは0（子要素のサイズのみ）
    }
    
    public int getTotalSize() {
        return totalSize;
    }
}

public class SearchVisitor implements FileSystemVisitor {
    private String searchName;
    private List<FileSystemElement> results = new ArrayList<>();
    
    public SearchVisitor(String searchName) {
        this.searchName = searchName;
    }
    
    @Override
    public void visit(File file) {
        if (file.getName().contains(searchName)) {
            results.add(file);
        }
    }
    
    @Override
    public void visit(Folder folder) {
        if (folder.getName().contains(searchName)) {
            results.add(folder);
        }
    }
    
    public List<FileSystemElement> getResults() {
        return results;
    }
}

// 使用例
public class FileSystemVisitorExample {
    public static void main(String[] args) {
        Folder root = new Folder("ルート");
        Folder documents = new Folder("ドキュメント");
        Folder images = new Folder("画像");
        
        documents.add(new File("レポート.txt", 1024));
        documents.add(new File("メモ.txt", 512));
        images.add(new File("写真1.jpg", 2048));
        images.add(new File("写真2.jpg", 3072));
        
        root.add(documents);
        root.add(images);
        
        System.out.println("=== ファイル構造の表示 ===");
        PrintVisitor printer = new PrintVisitor();
        root.accept(printer);
        
        System.out.println("\n=== サイズの計算 ===");
        SizeVisitor sizeVisitor = new SizeVisitor();
        root.accept(sizeVisitor);
        System.out.println("合計サイズ: " + sizeVisitor.getTotalSize() + " bytes");
        
        System.out.println("\n=== 検索 ===");
        SearchVisitor searchVisitor = new SearchVisitor("写真");
        root.accept(searchVisitor);
        System.out.println("検索結果:");
        for (FileSystemElement element : searchVisitor.getResults()) {
            System.out.println("  - " + element.getName());
        }
    }
}
```

### 例3: ドキュメント要素の処理

ドキュメント要素に対する操作を実装する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Visitorインターフェース
public interface DocumentVisitor {
    void visit(TextElement element);
    void visit(ImageElement element);
    void visit(TableElement element);
}

// Elementインターフェース
public interface DocumentElement {
    void accept(DocumentVisitor visitor);
}

// ConcreteElement
public class TextElement implements DocumentElement {
    private String content;
    
    public TextElement(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return content;
    }
    
    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}

public class ImageElement implements DocumentElement {
    private String src;
    private String alt;
    
    public ImageElement(String src, String alt) {
        this.src = src;
        this.alt = alt;
    }
    
    public String getSrc() {
        return src;
    }
    
    public String getAlt() {
        return alt;
    }
    
    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}

public class TableElement implements DocumentElement {
    private List<List<String>> rows = new ArrayList<>();
    
    public void addRow(List<String> row) {
        rows.add(row);
    }
    
    public List<List<String>> getRows() {
        return rows;
    }
    
    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}

// ConcreteVisitor
public class HTMLExportVisitor implements DocumentVisitor {
    private StringBuilder html = new StringBuilder();
    
    @Override
    public void visit(TextElement element) {
        html.append("<p>").append(element.getContent()).append("</p>\n");
    }
    
    @Override
    public void visit(ImageElement element) {
        html.append("<img src=\"").append(element.getSrc())
            .append("\" alt=\"").append(element.getAlt()).append("\">\n");
    }
    
    @Override
    public void visit(TableElement element) {
        html.append("<table>\n");
        for (List<String> row : element.getRows()) {
            html.append("  <tr>\n");
            for (String cell : row) {
                html.append("    <td>").append(cell).append("</td>\n");
            }
            html.append("  </tr>\n");
        }
        html.append("</table>\n");
    }
    
    public String getHTML() {
        return html.toString();
    }
}

public class PlainTextExportVisitor implements DocumentVisitor {
    private StringBuilder text = new StringBuilder();
    
    @Override
    public void visit(TextElement element) {
        text.append(element.getContent()).append("\n");
    }
    
    @Override
    public void visit(ImageElement element) {
        text.append("[画像: ").append(element.getAlt()).append("]\n");
    }
    
    @Override
    public void visit(TableElement element) {
        for (List<String> row : element.getRows()) {
            text.append(String.join(" | ", row)).append("\n");
        }
    }
    
    public String getText() {
        return text.toString();
    }
}

// 使用例
public class DocumentVisitorExample {
    public static void main(String[] args) {
        List<DocumentElement> document = new ArrayList<>();
        
        document.add(new TextElement("これはテキスト要素です"));
        document.add(new ImageElement("image.jpg", "サンプル画像"));
        
        TableElement table = new TableElement();
        table.addRow(List.of("列1", "列2", "列3"));
        table.addRow(List.of("データ1", "データ2", "データ3"));
        document.add(table);
        
        System.out.println("=== HTMLエクスポート ===");
        HTMLExportVisitor htmlVisitor = new HTMLExportVisitor();
        for (DocumentElement element : document) {
            element.accept(htmlVisitor);
        }
        System.out.println(htmlVisitor.getHTML());
        
        System.out.println("=== プレーンテキストエクスポート ===");
        PlainTextExportVisitor textVisitor = new PlainTextExportVisitor();
        for (DocumentElement element : document) {
            element.accept(textVisitor);
        }
        System.out.println(textVisitor.getText());
    }
}
```

### 例4: 形状の描画

グラフィカルオブジェクトに対する操作を実装する例です。

```java
// Visitorインターフェース
public interface ShapeVisitor {
    void visit(Circle circle);
    void visit(Rectangle rectangle);
    void visit(Triangle triangle);
}

// Elementインターフェース
public interface Shape {
    void accept(ShapeVisitor visitor);
    String getName();
}

// ConcreteElement
public class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    public double getRadius() {
        return radius;
    }
    
    @Override
    public String getName() {
        return "円";
    }
    
    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}

public class Rectangle implements Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
    
    @Override
    public String getName() {
        return "長方形";
    }
    
    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}

public class Triangle implements Shape {
    private double base;
    private double height;
    
    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }
    
    public double getBase() {
        return base;
    }
    
    public double getHeight() {
        return height;
    }
    
    @Override
    public String getName() {
        return "三角形";
    }
    
    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}

// ConcreteVisitor
public class DrawVisitor implements ShapeVisitor {
    @Override
    public void visit(Circle circle) {
        System.out.println("円を描画: 半径 = " + circle.getRadius());
    }
    
    @Override
    public void visit(Rectangle rectangle) {
        System.out.println("長方形を描画: 幅 = " + rectangle.getWidth() + 
                          ", 高さ = " + rectangle.getHeight());
    }
    
    @Override
    public void visit(Triangle triangle) {
        System.out.println("三角形を描画: 底辺 = " + triangle.getBase() + 
                          ", 高さ = " + triangle.getHeight());
    }
}

public class AreaVisitor implements ShapeVisitor {
    private double totalArea = 0;
    
    @Override
    public void visit(Circle circle) {
        double area = Math.PI * circle.getRadius() * circle.getRadius();
        System.out.println("円の面積: " + area);
        totalArea += area;
    }
    
    @Override
    public void visit(Rectangle rectangle) {
        double area = rectangle.getWidth() * rectangle.getHeight();
        System.out.println("長方形の面積: " + area);
        totalArea += area;
    }
    
    @Override
    public void visit(Triangle triangle) {
        double area = 0.5 * triangle.getBase() * triangle.getHeight();
        System.out.println("三角形の面積: " + area);
        totalArea += area;
    }
    
    public double getTotalArea() {
        return totalArea;
    }
}

// 使用例
import java.util.ArrayList;
import java.util.List;

public class ShapeVisitorExample {
    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle(5.0));
        shapes.add(new Rectangle(4.0, 6.0));
        shapes.add(new Triangle(3.0, 4.0));
        
        System.out.println("=== 形状の描画 ===");
        DrawVisitor drawVisitor = new DrawVisitor();
        for (Shape shape : shapes) {
            shape.accept(drawVisitor);
        }
        
        System.out.println("\n=== 面積の計算 ===");
        AreaVisitor areaVisitor = new AreaVisitor();
        for (Shape shape : shapes) {
            shape.accept(areaVisitor);
        }
        System.out.println("合計面積: " + areaVisitor.getTotalArea());
    }
}
```

### 例5: コンパイラの型チェック

抽象構文木に対する型チェック操作を実装する例です。

```java
// Visitorインターフェース
public interface TypeCheckVisitor {
    String visit(VariableDeclaration decl);
    String visit(AssignmentStatement stmt);
    String visit(IfStatement stmt);
}

// Elementインターフェース
public interface ASTNode {
    String accept(TypeCheckVisitor visitor);
}

// ConcreteElement
public class VariableDeclaration implements ASTNode {
    private String name;
    private String type;
    
    public VariableDeclaration(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    @Override
    public String accept(TypeCheckVisitor visitor) {
        return visitor.visit(this);
    }
}

public class AssignmentStatement implements ASTNode {
    private String variable;
    private ASTNode expression;
    
    public AssignmentStatement(String variable, ASTNode expression) {
        this.variable = variable;
        this.expression = expression;
    }
    
    public String getVariable() {
        return variable;
    }
    
    public ASTNode getExpression() {
        return expression;
    }
    
    @Override
    public String accept(TypeCheckVisitor visitor) {
        return visitor.visit(this);
    }
}

public class IfStatement implements ASTNode {
    private ASTNode condition;
    private ASTNode thenBranch;
    private ASTNode elseBranch;
    
    public IfStatement(ASTNode condition, ASTNode thenBranch, ASTNode elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    
    public ASTNode getCondition() {
        return condition;
    }
    
    public ASTNode getThenBranch() {
        return thenBranch;
    }
    
    public ASTNode getElseBranch() {
        return elseBranch;
    }
    
    @Override
    public String accept(TypeCheckVisitor visitor) {
        return visitor.visit(this);
    }
}

// ConcreteVisitor
public class TypeChecker implements TypeCheckVisitor {
    private java.util.Map<String, String> symbolTable = new java.util.HashMap<>();
    
    @Override
    public String visit(VariableDeclaration decl) {
        symbolTable.put(decl.getName(), decl.getType());
        System.out.println("変数 " + decl.getName() + " を型 " + decl.getType() + " で宣言");
        return decl.getType();
    }
    
    @Override
    public String visit(AssignmentStatement stmt) {
        String varType = symbolTable.get(stmt.getVariable());
        if (varType == null) {
            System.out.println("エラー: 変数 " + stmt.getVariable() + " が宣言されていません");
            return "ERROR";
        }
        
        String exprType = stmt.getExpression().accept(this);
        if (!varType.equals(exprType)) {
            System.out.println("エラー: 型の不一致 - " + stmt.getVariable() + 
                             " は " + varType + " 型ですが、式は " + exprType + " 型です");
            return "ERROR";
        }
        
        System.out.println("代入文: " + stmt.getVariable() + " = " + exprType);
        return varType;
    }
    
    @Override
    public String visit(IfStatement stmt) {
        String condType = stmt.getCondition().accept(this);
        if (!condType.equals("boolean")) {
            System.out.println("エラー: if文の条件はboolean型である必要があります");
            return "ERROR";
        }
        
        stmt.getThenBranch().accept(this);
        if (stmt.getElseBranch() != null) {
            stmt.getElseBranch().accept(this);
        }
        
        return "void";
    }
}

// 使用例
public class TypeCheckExample {
    public static void main(String[] args) {
        // int x; x = 10; if (true) { int y; }
        VariableDeclaration xDecl = new VariableDeclaration("x", "int");
        AssignmentStatement assign = new AssignmentStatement("x", 
            new VariableDeclaration("temp", "int")); // 簡略化
        
        TypeChecker typeChecker = new TypeChecker();
        xDecl.accept(typeChecker);
        assign.accept(typeChecker);
    }
}
```

---

## まとめ

### 学習のポイント

1. **ビジターパターンの目的**: オブジェクト構造に対する操作を分離し、既存の構造を変更せずに新しい操作を追加
2. **基本的な構造**: Visitorインターフェース、ConcreteVisitor、Elementインターフェース、ConcreteElementの4つの要素
3. **ダブルディスパッチ**: ビジターと要素の両方の型に基づいてメソッドを選択
4. **オープン・クローズドの原則**: 拡張に対して開いており、修正に対して閉じている

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 操作の分離、拡張性、保守性、単一責任の原則、オープン・クローズドの原則 |
| **注意点** | 新しい要素の追加が困難、要素とビジターの結合、複雑性の増加、ダブルディスパッチの理解 |
| **適用場面** | コンパイラ、ドキュメント処理、グラフィックスシステム、構文解析、レポート生成、ファイルシステム |

### 他のパターンとの関係

- **Composite**: コンポジットパターンと組み合わせて、複合構造に対する操作を実装することが多い
- **Iterator**: イテレーターパターンと組み合わせて、コレクションの要素を走査することがある
- **Interpreter**: インタープリターパターンと組み合わせて、構文木に対する操作を実装することがある

### 注意点

1. **新しい要素型の追加**: 新しい要素型を追加する際に、すべてのビジターを更新する必要がある
2. **要素とビジターの結合**: 要素とビジターが密結合になることに注意する
3. **過度な使用を避ける**: シンプルなケースでは過剰な設計になる可能性がある
4. **ダブルディスパッチの理解**: ダブルディスパッチの概念を理解することが重要

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. 構文木や抽象構文木でビジターパターンを適用してみる
3. Compositeパターンと組み合わせた実装を試してみる
4. ダブルディスパッチの概念を理解する
5. Interpreterパターンを学習する（ビジターパターンと組み合わせて使用）

### 参考資料

- [cs-techblog.com - ビジターパターン](https://cs-techblog.com/technical/visitor-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、ビジターパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
