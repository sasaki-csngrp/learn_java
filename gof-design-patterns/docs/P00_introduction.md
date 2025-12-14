# GoF デザインパターン 入門 ~入門するまで編~

## 目次

1. [はじめに](#はじめに)
2. [オブジェクト指向プログラミングの基本](#オブジェクト指向プログラミングの基本)
3. [SOLID原則](#solid原則)
4. [デザインパターンとは](#デザインパターンとは)
5. [GoFデザインパターンの分類](#gofデザインパターンの分類)
6. [まとめ](#まとめ)

---

## はじめに

デザインパターンは、ソフトウェア開発において繰り返し発生する問題に対する再利用可能な解決策です。GoF（Gang of Four）によって提唱された23のデザインパターンは、オブジェクト指向プログラミングの実践的な知識として広く知られています。

この教材では、デザインパターンを理解するために必要な基礎知識から、GoFデザインパターンの全体像までを学習します。

---

## オブジェクト指向プログラミングの基本

デザインパターンを理解するためには、まずオブジェクト指向プログラミング（OOP）の基本概念を理解する必要があります。

### 1. カプセル化（Encapsulation）

カプセル化は、データとメソッドを一つのクラスにまとめ、内部の実装を隠蔽する手法です。

**例：**

```java
public class BankAccount {
    private double balance;  // データをprivateで隠蔽
    
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }
    
    public double getBalance() {
        return balance;
    }
}
```

**メリット：**
- データの整合性を保つことができる
- 内部実装の変更が外部に影響しない
- 再利用性が向上する

### 2. 継承（Inheritance）

継承は、既存のクラスの特性を新しいクラスに引き継ぎ、拡張する仕組みです。

**例：**

```java
// 基底クラス
public class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void makeSound() {
        System.out.println("動物の鳴き声");
    }
}

// 派生クラス
public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + "がワンワンと鳴く");
    }
}
```

**メリット：**
- コードの重複を減らせる
- 既存のコードを拡張しやすい
- 階層的な設計が可能

### 3. ポリモーフィズム（Polymorphism）

ポリモーフィズムは、同じインターフェースを持つ異なるクラスのオブジェクトが、それぞれ異なる振る舞いを可能にする仕組みです。

**例：**

```java
// インターフェース
public interface Shape {
    double calculateArea();
}

// 実装クラス1
public class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// 実装クラス2
public class Rectangle implements Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
}

// 使用例
public class ShapeCalculator {
    public void printArea(Shape shape) {
        System.out.println("面積: " + shape.calculateArea());
    }
    
    public static void main(String[] args) {
        ShapeCalculator calculator = new ShapeCalculator();
        calculator.printArea(new Circle(5.0));      // 円の面積を計算
        calculator.printArea(new Rectangle(4.0, 6.0)); // 長方形の面積を計算
    }
}
```

**メリット：**
- 柔軟性の高いコードが書ける
- 拡張性が向上する
- コードの保守性が向上する

### 4. 抽象化（Abstraction）

抽象化は、複雑なシステムを単純化し、必要な部分だけを表現する手法です。

**例：**

```java
// 抽象クラス
public abstract class Vehicle {
    protected String brand;
    
    public Vehicle(String brand) {
        this.brand = brand;
    }
    
    // 抽象メソッド（実装はサブクラスで定義）
    public abstract void start();
    public abstract void stop();
    
    // 具象メソッド
    public void displayBrand() {
        System.out.println("ブランド: " + brand);
    }
}

// 具象クラス
public class Car extends Vehicle {
    public Car(String brand) {
        super(brand);
    }
    
    @Override
    public void start() {
        System.out.println(brand + "の車がエンジンを始動しました");
    }
    
    @Override
    public void stop() {
        System.out.println(brand + "の車が停止しました");
    }
}
```

---

## SOLID原則

SOLID原則は、オブジェクト指向設計の5つの重要な原則です。これらの原則を理解することで、より良いコード設計が可能になります。

### 1. 単一責任の原則（Single Responsibility Principle: SRP）

**定義：** クラスは一つの責任のみを持つべきである。

**悪い例：**

```java
// 複数の責任を持つクラス
public class User {
    private String name;
    private String email;
    
    // ユーザー情報の管理
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    
    // データベース操作（別の責任）
    public void saveToDatabase() {
        // データベースに保存する処理
    }
    
    // メール送信（別の責任）
    public void sendEmail(String message) {
        // メールを送信する処理
    }
}
```

**良い例：**

```java
// ユーザー情報のみを管理
public class User {
    private String name;
    private String email;
    
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }
}

// データベース操作は別クラスで
public class UserRepository {
    public void save(User user) {
        // データベースに保存する処理
    }
}

// メール送信は別クラスで
public class EmailService {
    public void sendEmail(String email, String message) {
        // メールを送信する処理
    }
}
```

### 2. オープン・クローズドの原則（Open/Closed Principle: OCP）

**定義：** クラスは拡張に対して開かれ、修正に対して閉じられるべきである。

**悪い例：**

```java
public class AreaCalculator {
    public double calculateArea(Object shape) {
        if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            return Math.PI * circle.getRadius() * circle.getRadius();
        } else if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            return rectangle.getWidth() * rectangle.getHeight();
        }
        // 新しい図形を追加するたびに、このメソッドを修正する必要がある
        return 0;
    }
}
```

**良い例：**

```java
// インターフェースで拡張可能に
public interface Shape {
    double calculateArea();
}

public class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

public class Rectangle implements Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
}

// 新しい図形を追加しても、このクラスは修正不要
public class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.calculateArea();
    }
}
```

### 3. リスコフの置換原則（Liskov Substitution Principle: LSP）

**定義：** 派生クラスは基底クラスと置き換え可能でなければならない。

**悪い例：**

```java
public class Rectangle {
    protected int width;
    protected int height;
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getArea() {
        return width * height;
    }
}

// 正方形は長方形の一種だが、setWidth/setHeightの動作が異なる
public class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;  // 正方形は幅と高さが同じ
    }
    
    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}
```

**良い例：**

```java
// 共通のインターフェース
public interface Shape {
    int getArea();
}

public class Rectangle implements Shape {
    private int width;
    private int height;
    
    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public int getArea() {
        return width * height;
    }
}

public class Square implements Shape {
    private int side;
    
    public Square(int side) {
        this.side = side;
    }
    
    @Override
    public int getArea() {
        return side * side;
    }
}
```

### 4. インターフェース分離の原則（Interface Segregation Principle: ISP）

**定義：** クライアントは自身が使用しないインターフェースへの依存を強制されるべきでない。

**悪い例：**

```java
// すべての機能を含む大きなインターフェース
public interface Worker {
    void work();
    void eat();
    void sleep();
}

// ロボットはeat()とsleep()を実装する必要がない
public class Robot implements Worker {
    @Override
    public void work() {
        System.out.println("ロボットが働いています");
    }
    
    @Override
    public void eat() {
        // ロボットは食べないので、空実装が必要
        throw new UnsupportedOperationException("ロボットは食べません");
    }
    
    @Override
    public void sleep() {
        // ロボットは眠らないので、空実装が必要
        throw new UnsupportedOperationException("ロボットは眠りません");
    }
}
```

**良い例：**

```java
// インターフェースを分離
public interface Workable {
    void work();
}

public interface Eatable {
    void eat();
}

public interface Sleepable {
    void sleep();
}

// 必要なインターフェースのみを実装
public class Robot implements Workable {
    @Override
    public void work() {
        System.out.println("ロボットが働いています");
    }
}

public class Human implements Workable, Eatable, Sleepable {
    @Override
    public void work() {
        System.out.println("人間が働いています");
    }
    
    @Override
    public void eat() {
        System.out.println("人間が食べています");
    }
    
    @Override
    public void sleep() {
        System.out.println("人間が眠っています");
    }
}
```

### 5. 依存関係逆転の原則（Dependency Inversion Principle: DIP）

**定義：** 高レベルモジュールは低レベルモジュールに依存すべきでなく、両者とも抽象に依存すべきである。

**悪い例：**

```java
// 低レベルモジュール
public class MySQLDatabase {
    public void save(String data) {
        System.out.println("MySQLに保存: " + data);
    }
}

// 高レベルモジュールが低レベルモジュールに直接依存
public class UserService {
    private MySQLDatabase database;  // 具体的な実装に依存
    
    public UserService() {
        this.database = new MySQLDatabase();
    }
    
    public void saveUser(String userData) {
        database.save(userData);
    }
}
```

**良い例：**

```java
// 抽象（インターフェース）
public interface Database {
    void save(String data);
}

// 低レベルモジュール
public class MySQLDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("MySQLに保存: " + data);
    }
}

public class PostgreSQLDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("PostgreSQLに保存: " + data);
    }
}

// 高レベルモジュールは抽象に依存
public class UserService {
    private Database database;  // 抽象に依存
    
    public UserService(Database database) {
        this.database = database;  // 依存性注入
    }
    
    public void saveUser(String userData) {
        database.save(userData);
    }
}
```

---

## デザインパターンとは

デザインパターンは、ソフトウェア開発において繰り返し発生する問題に対する再利用可能な解決策です。

### デザインパターンの利点

1. **再利用性**: 実証済みの解決策を再利用できる
2. **保守性**: 理解しやすい構造でコードを整理できる
3. **拡張性**: 将来の変更に対応しやすい設計になる
4. **コミュニケーション**: 開発者間で共通の用語として機能する

### デザインパターンの構成要素

デザインパターンは通常、以下の要素で構成されます：

- **名前**: パターンを識別する名前
- **問題**: パターンが解決する問題
- **解決策**: 問題を解決する方法
- **結果**: パターンを使用することの利点とトレードオフ

---

## GoFデザインパターンの分類

GoF（Gang of Four）によって提唱された23のデザインパターンは、3つのカテゴリに分類されます。

### 1. 生成に関するパターン（Creational Patterns）

オブジェクトの生成方法を抽象化し、柔軟性と再利用性を向上させます。

#### 主要なパターン

1. **Singleton（シングルトン）**
   - クラスのインスタンスが1つだけ存在することを保証
   - 使用例: データベース接続、ログ管理

2. **Factory Method（ファクトリメソッド）**
   - オブジェクトの生成をサブクラスに委譲
   - 使用例: 異なる種類のUIコンポーネントの生成

3. **Abstract Factory（抽象ファクトリ）**
   - 関連するオブジェクト群を生成するためのインターフェースを提供
   - 使用例: 異なるOS向けのUIコンポーネント群の生成

4. **Builder（ビルダー）**
   - 複雑なオブジェクトを段階的に構築
   - 使用例: SQLクエリの構築、設定オブジェクトの構築

5. **Prototype（プロトタイプ）**
   - 既存のインスタンスをコピーして新しいインスタンスを作成
   - 使用例: ゲームオブジェクトの複製

### 2. 構造に関するパターン（Structural Patterns）

クラスやオブジェクトを組み合わせてより大きな構造を作成します。

#### 主要なパターン

1. **Adapter（アダプター）**
   - 互換性のないインターフェースを接続
   - 使用例: レガシーコードとの統合

2. **Bridge（ブリッジ）**
   - 実装と抽象を分離し、独立して変更可能にする
   - 使用例: プラットフォーム依存の機能の抽象化

3. **Composite（コンポジット）**
   - オブジェクトをツリー構造で表現し、部分と全体を統一して扱う
   - 使用例: ファイルシステム、UIコンポーネント階層

4. **Decorator（デコレーター）**
   - オブジェクトに動的に機能を追加
   - 使用例: JavaのInputStream/OutputStream

5. **Facade（ファサード）**
   - 複雑なサブシステムにシンプルなインターフェースを提供
   - 使用例: ライブラリの簡易API

6. **Flyweight（フライウェイト）**
   - 多数の小さなオブジェクトを効率的に共有
   - 使用例: 文字列プール、ゲームの粒子システム

7. **Proxy（プロキシ）**
   - 他のオブジェクトへのアクセスを制御する代理オブジェクト
   - 使用例: 遅延読み込み、アクセス制御

### 3. 振る舞いに関するパターン（Behavioral Patterns）

オブジェクト間の責任の分散と通信方法を定義します。

#### 主要なパターン

1. **Chain of Responsibility（責任連鎖）**
   - リクエストを連鎖的に処理
   - 使用例: 例外処理、イベントハンドリング

2. **Command（コマンド）**
   - リクエストをオブジェクトとしてカプセル化
   - 使用例: アンドゥ/リドゥ機能、キュー処理

3. **Interpreter（インタープリター）**
   - 言語の文法を表現し、解釈する
   - 使用例: 正規表現、SQLパーサー

4. **Iterator（イテレーター）**
   - コレクションの要素に順次アクセス
   - 使用例: JavaのIteratorインターフェース

5. **Mediator（メディエーター）**
   - オブジェクト間の通信を仲介
   - 使用例: GUIコンポーネント間の通信

6. **Memento（メメント）**
   - オブジェクトの内部状態を保存・復元
   - 使用例: アンドゥ機能、チェックポイント

7. **Observer（オブザーバー）**
   - オブジェクトの状態変化を通知
   - 使用例: MVCパターン、イベント駆動プログラミング

8. **State（ステート）**
   - オブジェクトの状態に応じた振る舞いを変更
   - 使用例: 状態マシン、ゲームのキャラクター状態

9. **Strategy（ストラテジー）**
   - アルゴリズムをカプセル化し、交換可能にする
   - 使用例: ソートアルゴリズムの選択、支払い方法の選択

10. **Template Method（テンプレートメソッド）**
    - アルゴリズムの骨組みを定義し、一部をサブクラスで実装
    - 使用例: フレームワークのライフサイクル

11. **Visitor（ビジター）**
    - オブジェクト構造に対する操作を分離
    - 使用例: 構文木の走査、レポート生成

---

## まとめ

### 学習のポイント

1. **基礎を固める**: オブジェクト指向の基本概念とSOLID原則を理解する
2. **パターンを分類して理解する**: 生成・構造・振る舞いの3つのカテゴリを意識する
3. **実践を通じて学ぶ**: 実際のコードでパターンを適用してみる
4. **過度な使用を避ける**: パターンは手段であり、目的ではない

### 次のステップ

- 各デザインパターンの詳細な実装例を学習する
- 実際のプロジェクトでパターンを適用してみる
- パターンの組み合わせ方法を学ぶ
- アンチパターンについても学習する

### 参考資料

- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）
- 「Clean Code」（Robert C. Martin著）

---

**注意**: この教材は入門編です。各デザインパターンの詳細な実装例や、より高度な応用については、別の教材で学習してください。

