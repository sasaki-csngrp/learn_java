# イテレーターパターン（Iterator Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [イテレーターパターンとは](#イテレーターパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

イテレーターパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**振る舞いに関するパターン（Behavioral Pattern）**に分類されます。

このパターンは、コレクションの内部構造を公開せずに、その要素に順次アクセスする方法を提供します。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- イテレーターパターンの目的と利点を理解する
- 基本的なイテレーターパターンの実装方法を理解する
- Javaの標準Iteratorインターフェースの使用方法を理解する
- カスタムコレクションにイテレーターを実装できる
- 実際のプロジェクトでイテレーターパターンを適用できる

---

## イテレーターパターンとは

### 定義

イテレーターパターンは、コレクションの内部構造を公開せずに、その要素に順次アクセスする方法を提供するデザインパターンです。このパターンにより、クライアントコードはコレクションの実装詳細を知ることなく、要素を走査できます。

### 主な特徴

1. **内部構造の隠蔽**: コレクションの内部実装を隠蔽
2. **統一されたアクセス方法**: 異なるコレクションに対して統一されたアクセス方法を提供
3. **走査の分離**: 走査ロジックをコレクションから分離
4. **複数の走査**: 同じコレクションに対して複数の独立した走査が可能

### 使用される場面

イテレーターパターンは、以下のような場面で使用されます：

- **コレクションの走査**: リスト、セット、マップなどのコレクション要素の走査
- **複雑なデータ構造**: ツリー、グラフなどの複雑なデータ構造の走査
- **統一されたインターフェース**: 異なるコレクション型に対して統一された走査方法を提供
- **遅延評価**: 要素を必要に応じて取得する（遅延評価）
- **フィルタリング**: 走査中に要素をフィルタリング

### メリット

- **内部構造の隠蔽**: コレクションの内部実装を隠蔽し、カプセル化を促進
- **統一されたインターフェース**: 異なるコレクション型に対して統一された走査方法を提供
- **走査の分離**: 走査ロジックをコレクションから分離し、単一責任の原則に従う
- **複数の走査**: 同じコレクションに対して複数の独立した走査が可能
- **拡張性**: 新しい走査方法を追加しやすい

### デメリット

- **オーバーヘッド**: 単純なコレクションでは、直接アクセスの方が効率的な場合がある
- **複雑性の増加**: シンプルなケースでは過剰な設計になる可能性
- **変更の困難さ**: 走査中にコレクションを変更すると、予期しない動作が発生する可能性

---

## 基本的な実装

### 実装のポイント

イテレーターパターンを実装するには、以下の要素が必要です：

1. **Iteratorインターフェース（Iterator）**: 要素へのアクセスと走査のためのメソッドを定義
2. **ConcreteIterator（具象イテレーター）**: Iteratorインターフェースを実装し、特定のコレクションに対する走査ロジックを提供
3. **Aggregateインターフェース（Aggregate）**: Iteratorを作成するメソッドを定義
4. **ConcreteAggregate（具象集合体）**: Aggregateインターフェースを実装し、Iteratorを作成

### 基本的な実装例（カスタム実装）

```java
// 1. Iteratorインターフェース
public interface Iterator<T> {
    boolean hasNext();
    T next();
    void remove(); // オプション
}

// 2. Aggregateインターフェース
public interface Aggregate<T> {
    Iterator<T> createIterator();
}

// 3. ConcreteIterator（具象イテレーター）
public class BookIterator implements Iterator<Book> {
    private BookCollection collection;
    private int position = 0;
    
    public BookIterator(BookCollection collection) {
        this.collection = collection;
    }
    
    @Override
    public boolean hasNext() {
        return position < collection.size();
    }
    
    @Override
    public Book next() {
        if (hasNext()) {
            return collection.get(position++);
        }
        throw new NoSuchElementException();
    }
    
    @Override
    public void remove() {
        if (position <= 0) {
            throw new IllegalStateException();
        }
        collection.remove(--position);
    }
}

// 4. ConcreteAggregate（具象集合体）
public class BookCollection implements Aggregate<Book> {
    private List<Book> books = new ArrayList<>();
    
    public void addBook(Book book) {
        books.add(book);
    }
    
    public Book get(int index) {
        return books.get(index);
    }
    
    public int size() {
        return books.size();
    }
    
    public void remove(int index) {
        books.remove(index);
    }
    
    @Override
    public Iterator<Book> createIterator() {
        return new BookIterator(this);
    }
}

// 5. 使用するクラス
public class Book {
    private String title;
    private String author;
    
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    @Override
    public String toString() {
        return title + " by " + author;
    }
}
```

### 使用例

```java
public class IteratorExample {
    public static void main(String[] args) {
        BookCollection collection = new BookCollection();
        collection.addBook(new Book("Java入門", "山田太郎"));
        collection.addBook(new Book("デザインパターン", "佐藤花子"));
        collection.addBook(new Book("リファクタリング", "鈴木一郎"));
        
        Iterator<Book> iterator = collection.createIterator();
        
        System.out.println("=== 本のリスト ===");
        while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println(book);
        }
    }
}
```

### Java標準ライブラリのIteratorインターフェース

Javaには標準の`java.util.Iterator`インターフェースが提供されています。多くのコレクションクラス（ArrayList、HashSet、HashMapなど）は、このインターフェースを実装しています。

```java
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class JavaIteratorExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("要素1");
        list.add("要素2");
        list.add("要素3");
        
        // Iteratorを使用した走査
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println(element);
        }
        
        // 拡張forループ（内部でIteratorを使用）
        for (String element : list) {
            System.out.println(element);
        }
    }
}
```

### パターンの構造

```
Client
  ↓
Aggregate (インターフェース)
  └─ createIterator()
      ↓
ConcreteAggregate (具象クラス)
  └─ createIterator() [実装]
      ↓
Iterator (インターフェース)
  ├─ hasNext()
  ├─ next()
  └─ remove() [オプション]
      ↓
ConcreteIterator (具象クラス)
  └─ 走査ロジックの実装
```

---

## 実装のバリエーション

### バリエーション1: Java標準Iteratorインターフェースを使用

Java標準の`java.util.Iterator`インターフェースを使用する方法です。

```java
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class BookCollection implements Iterable<Book> {
    private List<Book> books = new ArrayList<>();
    
    public void addBook(Book book) {
        books.add(book);
    }
    
    @Override
    public Iterator<Book> iterator() {
        return books.iterator();
    }
}

// 使用例
public class StandardIteratorExample {
    public static void main(String[] args) {
        BookCollection collection = new BookCollection();
        collection.addBook(new Book("Java入門", "山田太郎"));
        collection.addBook(new Book("デザインパターン", "佐藤花子"));
        
        // 拡張forループで使用可能
        for (Book book : collection) {
            System.out.println(book);
        }
        
        // Iteratorを直接使用
        Iterator<Book> iterator = collection.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
```

### バリエーション2: 逆順走査イテレーター

コレクションを逆順に走査するイテレーターです。

```java
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class ReverseIterator<T> implements Iterator<T> {
    private List<T> list;
    private int position;
    
    public ReverseIterator(List<T> list) {
        this.list = list;
        this.position = list.size() - 1;
    }
    
    @Override
    public boolean hasNext() {
        return position >= 0;
    }
    
    @Override
    public T next() {
        if (hasNext()) {
            return list.get(position--);
        }
        throw new java.util.NoSuchElementException();
    }
}

// 使用例
public class ReverseIteratorExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("要素1");
        list.add("要素2");
        list.add("要素3");
        
        ReverseIterator<String> reverseIterator = new ReverseIterator<>(list);
        while (reverseIterator.hasNext()) {
            System.out.println(reverseIterator.next());
        }
        // 出力: 要素3, 要素2, 要素1
    }
}
```

### バリエーション3: フィルタリングイテレーター

条件に一致する要素のみを返すイテレーターです。

```java
import java.util.Iterator;
import java.util.function.Predicate;

public class FilterIterator<T> implements Iterator<T> {
    private Iterator<T> sourceIterator;
    private Predicate<T> predicate;
    private T nextElement;
    private boolean hasNextElement;
    
    public FilterIterator(Iterator<T> sourceIterator, Predicate<T> predicate) {
        this.sourceIterator = sourceIterator;
        this.predicate = predicate;
        findNext();
    }
    
    private void findNext() {
        hasNextElement = false;
        nextElement = null;
        
        while (sourceIterator.hasNext()) {
            T element = sourceIterator.next();
            if (predicate.test(element)) {
                nextElement = element;
                hasNextElement = true;
                break;
            }
        }
    }
    
    @Override
    public boolean hasNext() {
        return hasNextElement;
    }
    
    @Override
    public T next() {
        if (!hasNextElement) {
            throw new java.util.NoSuchElementException();
        }
        T result = nextElement;
        findNext();
        return result;
    }
}

// 使用例
public class FilterIteratorExample {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        
        // 偶数だけをフィルタリング
        FilterIterator<Integer> evenIterator = new FilterIterator<>(
            numbers.iterator(),
            n -> n % 2 == 0
        );
        
        while (evenIterator.hasNext()) {
            System.out.println(evenIterator.next());
        }
        // 出力: 2, 4
    }
}
```

### バリエーション4: 複数のコレクションを走査するイテレーター

複数のコレクションを順番に走査するイテレーターです。

```java
import java.util.Iterator;
import java.util.List;

public class MultiCollectionIterator<T> implements Iterator<T> {
    private List<Iterator<T>> iterators;
    private int currentIndex = 0;
    
    public MultiCollectionIterator(List<Iterator<T>> iterators) {
        this.iterators = iterators;
    }
    
    @Override
    public boolean hasNext() {
        // 現在のイテレーターに要素があるか確認
        while (currentIndex < iterators.size()) {
            if (iterators.get(currentIndex).hasNext()) {
                return true;
            }
            currentIndex++;
        }
        return false;
    }
    
    @Override
    public T next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        }
        return iterators.get(currentIndex).next();
    }
}

// 使用例
public class MultiCollectionIteratorExample {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("リスト1-要素1");
        list1.add("リスト1-要素2");
        
        List<String> list2 = new ArrayList<>();
        list2.add("リスト2-要素1");
        list2.add("リスト2-要素2");
        
        List<Iterator<String>> iterators = new ArrayList<>();
        iterators.add(list1.iterator());
        iterators.add(list2.iterator());
        
        MultiCollectionIterator<String> multiIterator = new MultiCollectionIterator<>(iterators);
        while (multiIterator.hasNext()) {
            System.out.println(multiIterator.next());
        }
    }
}
```

---

## 実践例

### 例1: カスタムコレクションクラス

独自のコレクションクラスにイテレーターを実装する例です。

```java
import java.util.Iterator;
import java.util.NoSuchElementException;

// カスタムコレクションクラス
public class CustomList<T> implements Iterable<T> {
    private Node<T> head;
    private int size;
    
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }
    
    public int size() {
        return size;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new CustomListIterator();
    }
    
    private class CustomListIterator implements Iterator<T> {
        private Node<T> current = head;
        
        @Override
        public boolean hasNext() {
            return current != null;
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}

// 使用例
public class CustomListExample {
    public static void main(String[] args) {
        CustomList<String> list = new CustomList<>();
        list.add("要素1");
        list.add("要素2");
        list.add("要素3");
        
        // 拡張forループで使用可能
        for (String element : list) {
            System.out.println(element);
        }
    }
}
```

### 例2: ツリー構造の走査

ツリー構造を走査するイテレーターの例です。

```java
import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;

// ツリーノード
public class TreeNode<T> {
    private T data;
    private TreeNode<T> left;
    private TreeNode<T> right;
    
    public TreeNode(T data) {
        this.data = data;
    }
    
    public T getData() {
        return data;
    }
    
    public TreeNode<T> getLeft() {
        return left;
    }
    
    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }
    
    public TreeNode<T> getRight() {
        return right;
    }
    
    public void setRight(TreeNode<T> right) {
        this.right = right;
    }
    
    // イテレーターを作成（深さ優先探索）
    public Iterator<T> createIterator() {
        return new TreeIterator(this);
    }
}

// ツリーイテレーター（深さ優先探索）
class TreeIterator<T> implements Iterator<T> {
    private Stack<TreeNode<T>> stack = new Stack<>();
    
    public TreeIterator(TreeNode<T> root) {
        if (root != null) {
            stack.push(root);
        }
    }
    
    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }
    
    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        
        TreeNode<T> node = stack.pop();
        T data = node.getData();
        
        // 右の子を先にスタックに追加（左を先に処理するため）
        if (node.getRight() != null) {
            stack.push(node.getRight());
        }
        if (node.getLeft() != null) {
            stack.push(node.getLeft());
        }
        
        return data;
    }
}

// 使用例
public class TreeIteratorExample {
    public static void main(String[] args) {
        // ツリー構造を作成
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode<Integer> root = new TreeNode<>(1);
        root.setLeft(new TreeNode<>(2));
        root.setRight(new TreeNode<>(3));
        root.getLeft().setLeft(new TreeNode<>(4));
        root.getLeft().setRight(new TreeNode<>(5));
        
        Iterator<Integer> iterator = root.createIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        // 出力: 1, 2, 4, 5, 3（深さ優先探索）
    }
}
```

### 例3: 範囲指定イテレーター

指定した範囲の要素のみを返すイテレーターです。

```java
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class RangeIterator<T> implements Iterator<T> {
    private List<T> list;
    private int start;
    private int end;
    private int current;
    
    public RangeIterator(List<T> list, int start, int end) {
        if (start < 0 || end > list.size() || start > end) {
            throw new IllegalArgumentException("無効な範囲です");
        }
        this.list = list;
        this.start = start;
        this.end = end;
        this.current = start;
    }
    
    @Override
    public boolean hasNext() {
        return current < end;
    }
    
    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return list.get(current++);
    }
}

// 使用例
public class RangeIteratorExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("要素" + i);
        }
        
        // インデックス2から5までを走査
        RangeIterator<String> rangeIterator = new RangeIterator<>(list, 2, 6);
        while (rangeIterator.hasNext()) {
            System.out.println(rangeIterator.next());
        }
        // 出力: 要素2, 要素3, 要素4, 要素5
    }
}
```

### 例4: スキップイテレーター

指定した数の要素をスキップするイテレーターです。

```java
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SkipIterator<T> implements Iterator<T> {
    private Iterator<T> sourceIterator;
    private int skipCount;
    private boolean skipped = false;
    
    public SkipIterator(Iterator<T> sourceIterator, int skipCount) {
        this.sourceIterator = sourceIterator;
        this.skipCount = skipCount;
    }
    
    private void skip() {
        if (!skipped) {
            for (int i = 0; i < skipCount && sourceIterator.hasNext(); i++) {
                sourceIterator.next();
            }
            skipped = true;
        }
    }
    
    @Override
    public boolean hasNext() {
        skip();
        return sourceIterator.hasNext();
    }
    
    @Override
    public T next() {
        skip();
        if (!sourceIterator.hasNext()) {
            throw new NoSuchElementException();
        }
        return sourceIterator.next();
    }
}

// 使用例
public class SkipIteratorExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("要素" + i);
        }
        
        // 最初の3要素をスキップ
        SkipIterator<String> skipIterator = new SkipIterator<>(list.iterator(), 3);
        while (skipIterator.hasNext()) {
            System.out.println(skipIterator.next());
        }
        // 出力: 要素3, 要素4, 要素5, 要素6, 要素7, 要素8, 要素9
    }
}
```

### 例5: コンポジットパターンとの組み合わせ

コンポジット構造を走査するイテレーターの例です。

```java
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.NoSuchElementException;

// Component
public abstract class FileComponent {
    protected String name;
    
    public FileComponent(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public abstract Iterator<FileComponent> createIterator();
    public abstract void showDetails();
}

// Leaf
public class FileLeaf extends FileComponent {
    private int size;
    
    public FileLeaf(String name, int size) {
        super(name);
        this.size = size;
    }
    
    public int getSize() {
        return size;
    }
    
    @Override
    public Iterator<FileComponent> createIterator() {
        return new NullIterator();
    }
    
    @Override
    public void showDetails() {
        System.out.println("ファイル: " + name + ", サイズ: " + size + "KB");
    }
    
    // 空のイテレーター
    private class NullIterator implements Iterator<FileComponent> {
        @Override
        public boolean hasNext() {
            return false;
        }
        
        @Override
        public FileComponent next() {
            throw new NoSuchElementException();
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
    public Iterator<FileComponent> createIterator() {
        return new CompositeIterator(components.iterator());
    }
    
    @Override
    public void showDetails() {
        System.out.println("フォルダ: " + name);
        Iterator<FileComponent> iterator = createIterator();
        while (iterator.hasNext()) {
            iterator.next().showDetails();
        }
    }
}

// コンポジットイテレーター（再帰的に走査）
class CompositeIterator implements Iterator<FileComponent> {
    private Stack<Iterator<FileComponent>> stack = new Stack<>();
    
    public CompositeIterator(Iterator<FileComponent> iterator) {
        stack.push(iterator);
    }
    
    @Override
    public boolean hasNext() {
        if (stack.isEmpty()) {
            return false;
        }
        
        Iterator<FileComponent> iterator = stack.peek();
        if (iterator.hasNext()) {
            return true;
        } else {
            stack.pop();
            return hasNext();
        }
    }
    
    @Override
    public FileComponent next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        
        Iterator<FileComponent> iterator = stack.peek();
        FileComponent component = iterator.next();
        
        // コンポジットの場合は、そのイテレーターもスタックに追加
        if (component instanceof FolderComposite) {
            stack.push(component.createIterator());
        }
        
        return component;
    }
}

// 使用例
public class CompositeIteratorExample {
    public static void main(String[] args) {
        FolderComposite root = new FolderComposite("ルート");
        
        FolderComposite folder1 = new FolderComposite("フォルダ1");
        folder1.addComponent(new FileLeaf("ファイル1.txt", 100));
        folder1.addComponent(new FileLeaf("ファイル2.txt", 200));
        
        FolderComposite folder2 = new FolderComposite("フォルダ2");
        folder2.addComponent(new FileLeaf("ファイル3.txt", 150));
        
        root.addComponent(folder1);
        root.addComponent(folder2);
        root.addComponent(new FileLeaf("ファイル4.txt", 300));
        
        // すべての要素を再帰的に走査
        Iterator<FileComponent> iterator = root.createIterator();
        while (iterator.hasNext()) {
            FileComponent component = iterator.next();
            component.showDetails();
        }
    }
}
```

---

## まとめ

### 学習のポイント

1. **イテレーターパターンの目的**: コレクションの内部構造を公開せずに、要素に順次アクセスする方法を提供
2. **基本的な構造**: Iteratorインターフェース、ConcreteIterator、Aggregateインターフェース、ConcreteAggregate
3. **Java標準ライブラリ**: `java.util.Iterator`と`Iterable`インターフェースを活用
4. **走査の分離**: 走査ロジックをコレクションから分離し、単一責任の原則に従う

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 内部構造の隠蔽、統一されたインターフェース、走査の分離、複数の走査、拡張性 |
| **注意点** | オーバーヘッド、複雑性の増加、走査中の変更による予期しない動作 |
| **適用場面** | コレクションの走査、複雑なデータ構造の走査、統一されたインターフェースの提供 |

### 他のパターンとの関係

- **Composite**: コンポジット構造を走査する際にイテレーターパターンを使用
- **Visitor**: ビジターパターンと組み合わせて、走査と処理を分離
- **Factory Method**: イテレーターの作成にファクトリーメソッドパターンを使用することがある

### 注意点

1. **走査中の変更**: 走査中にコレクションを変更すると、`ConcurrentModificationException`が発生する可能性がある
2. **適切な使用**: シンプルなコレクションでは、直接アクセスの方が効率的な場合もある
3. **状態の管理**: イテレーターの状態を適切に管理し、複数の走査が独立して動作するようにする

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. Java標準ライブラリのIteratorインターフェースの使用方法を理解する
3. カスタムコレクションクラスにイテレーターを実装してみる
4. Compositeパターンと組み合わせた実装を試してみる
5. Visitorパターンを学習する（イテレーターと組み合わせて使用）

### 参考資料

- [cs-techblog.com - イテレーターパターン](https://cs-techblog.com/technical/iterator-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「Effective Java」（Joshua Bloch著）

---

**注意**: この学習プランは、イテレーターパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
