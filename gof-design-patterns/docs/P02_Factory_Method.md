# ファクトリーメソッドパターン（Factory Method Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [ファクトリーメソッドパターンとは](#ファクトリーメソッドパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

ファクトリーメソッドパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**生成に関するパターン（Creational Pattern）**に分類されます。

このパターンは、オブジェクトの生成をサブクラスに委譲することで、クライアントコードが具体的なクラスを知ることなく、オブジェクトを作成できるようにします。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- ファクトリーメソッドパターンの目的と利点を理解する
- 基本的なファクトリーメソッドパターンの実装方法を理解する
- パターンのバリエーションを理解する
- 実際のプロジェクトでファクトリーメソッドパターンを適用できる

---

## ファクトリーメソッドパターンとは

### 定義

ファクトリーメソッドパターンは、オブジェクトの生成をサブクラスに委譲するデザインパターンです。抽象クラスまたはインターフェースでファクトリーメソッドを定義し、サブクラスが具体的な実装を行うことで、クライアントコードは具体的なクラスを知ることなくオブジェクトを作成できます。

### 主な特徴

1. **生成の委譲**: オブジェクトの生成をサブクラスに委譲
2. **抽象化**: クライアントコードは抽象的な型のみを知る
3. **拡張性**: 新しい型を追加する際に、既存のコードを変更する必要がない
4. **疎結合**: クラス間の結合度を低く保つ

### 使用される場面

ファクトリーメソッドパターンは、以下のような場面で使用されます：

- **UIコンポーネントの生成**: OSやプラットフォームに応じた異なるUIコンポーネントの生成
- **ドキュメント処理**: 異なる種類のドキュメント（PDF、Word、Excelなど）の生成
- **データベース接続**: 異なるデータベース（MySQL、PostgreSQL、Oracleなど）への接続
- **ログ出力**: 異なる出力先（ファイル、コンソール、データベースなど）へのログ出力
- **ゲーム開発**: 異なるキャラクターやアイテムの生成

### メリット

- **拡張性**: 新しい型を追加する際に、既存のコードを変更する必要がない（オープン・クローズドの原則）
- **疎結合**: クライアントコードが具体的なクラスに依存しない
- **保守性**: オブジェクト生成のロジックが一箇所に集約される
- **柔軟性**: 実行時に生成するオブジェクトの型を決定できる

### デメリット

- **クラス数の増加**: 各製品クラスに対応するファクトリークラスが必要
- **複雑性の増加**: シンプルなケースでは過剰な設計になる可能性
- **理解の難しさ**: パターンを理解していない開発者には複雑に見える

---

## 基本的な実装

### 実装のポイント

ファクトリーメソッドパターンを実装するには、以下の要素が必要です：

1. **製品インターフェース（Product）**: 作成されるオブジェクトの共通インターフェース
2. **具象製品クラス（Concrete Product）**: 製品インターフェースの実装クラス
3. **ファクトリー抽象クラス（Creator）**: ファクトリーメソッドを定義する抽象クラス
4. **具象ファクトリークラス（Concrete Creator）**: ファクトリーメソッドを実装するクラス

### 基本的な実装例

```java
// 1. 製品インターフェース（Product）
public interface Vehicle {
    void drive();
}

// 2. 具象製品クラス（Concrete Product）
public class Car implements Vehicle {
    @Override
    public void drive() {
        System.out.println("車が走っています");
    }
}

public class Bike implements Vehicle {
    @Override
    public void drive() {
        System.out.println("バイクが走っています");
    }
}

// 3. ファクトリー抽象クラス（Creator）
public abstract class VehicleFactory {
    // ファクトリーメソッド（抽象メソッド）
    public abstract Vehicle createVehicle();
    
    // テンプレートメソッド（オプション）
    public void useVehicle() {
        Vehicle vehicle = createVehicle();
        vehicle.drive();
    }
}

// 4. 具象ファクトリークラス（Concrete Creator）
public class CarFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Car();
    }
}

public class BikeFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Bike();
    }
}
```

### 使用例

```java
public class FactoryMethodExample {
    public static void main(String[] args) {
        // CarFactoryを使用してCarを作成
        VehicleFactory carFactory = new CarFactory();
        Vehicle car = carFactory.createVehicle();
        car.drive();
        
        // BikeFactoryを使用してBikeを作成
        VehicleFactory bikeFactory = new BikeFactory();
        Vehicle bike = bikeFactory.createVehicle();
        bike.drive();
        
        // テンプレートメソッドの使用
        carFactory.useVehicle();
    }
}
```

### パターンの構造

```
Client
  ↓
Creator (抽象クラス)
  ├─ createProduct() [抽象メソッド]
  └─ someOperation() [テンプレートメソッド]
      ↓
ConcreteCreator (具象クラス)
  └─ createProduct() [実装]
      ↓
Product (インターフェース)
  ↓
ConcreteProduct (具象クラス)
```

---

## 実装のバリエーション

### バリエーション1: インターフェースを使用

抽象クラスの代わりにインターフェースを使用する方法です。

```java
// ファクトリーインターフェース
public interface VehicleFactory {
    Vehicle createVehicle();
}

// 具象ファクトリークラス
public class CarFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Car();
    }
}

public class BikeFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Bike();
    }
}
```

### バリエーション2: パラメータ付きファクトリーメソッド

パラメータに基づいて異なる製品を生成する方法です。

```java
public abstract class VehicleFactory {
    public abstract Vehicle createVehicle(String type);
}

public class SimpleVehicleFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle(String type) {
        switch (type.toLowerCase()) {
            case "car":
                return new Car();
            case "bike":
                return new Bike();
            default:
                throw new IllegalArgumentException("未知の車両タイプ: " + type);
        }
    }
}
```

### バリエーション3: デフォルト実装を持つ抽象クラス

抽象クラスにデフォルト実装を提供する方法です。

```java
public abstract class VehicleFactory {
    // デフォルト実装
    public Vehicle createVehicle() {
        return createDefaultVehicle();
    }
    
    // サブクラスでオーバーライド可能
    protected Vehicle createDefaultVehicle() {
        return new Car(); // デフォルトはCar
    }
}

public class BikeFactory extends VehicleFactory {
    @Override
    protected Vehicle createDefaultVehicle() {
        return new Bike();
    }
}
```

---

## 実践例

### 例1: ドキュメント処理システム

異なる種類のドキュメント（PDF、Word、Excel）を生成する例です。

```java
// 製品インターフェース
public interface Document {
    void open();
    void save();
    void close();
}

// 具象製品クラス
public class PDFDocument implements Document {
    @Override
    public void open() {
        System.out.println("PDFドキュメントを開きます");
    }
    
    @Override
    public void save() {
        System.out.println("PDFドキュメントを保存します");
    }
    
    @Override
    public void close() {
        System.out.println("PDFドキュメントを閉じます");
    }
}

public class WordDocument implements Document {
    @Override
    public void open() {
        System.out.println("Wordドキュメントを開きます");
    }
    
    @Override
    public void save() {
        System.out.println("Wordドキュメントを保存します");
    }
    
    @Override
    public void close() {
        System.out.println("Wordドキュメントを閉じます");
    }
}

public class ExcelDocument implements Document {
    @Override
    public void open() {
        System.out.println("Excelドキュメントを開きます");
    }
    
    @Override
    public void save() {
        System.out.println("Excelドキュメントを保存します");
    }
    
    @Override
    public void close() {
        System.out.println("Excelドキュメントを閉じます");
    }
}

// ファクトリー抽象クラス
public abstract class DocumentFactory {
    public abstract Document createDocument();
    
    public void processDocument() {
        Document doc = createDocument();
        doc.open();
        doc.save();
        doc.close();
    }
}

// 具象ファクトリークラス
public class PDFDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new PDFDocument();
    }
}

public class WordDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new WordDocument();
    }
}

public class ExcelDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new ExcelDocument();
    }
}

// 使用例
public class DocumentExample {
    public static void main(String[] args) {
        DocumentFactory pdfFactory = new PDFDocumentFactory();
        Document pdf = pdfFactory.createDocument();
        pdf.open();
        pdf.save();
        
        DocumentFactory wordFactory = new WordDocumentFactory();
        wordFactory.processDocument(); // テンプレートメソッドを使用
    }
}
```

### 例2: ログ出力システム

異なる出力先（ファイル、コンソール、データベース）へのログ出力の例です。

```java
// 製品インターフェース
public interface Logger {
    void log(String message);
}

// 具象製品クラス
public class FileLogger implements Logger {
    private String filename;
    
    public FileLogger(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void log(String message) {
        System.out.println("[" + filename + "] " + message);
    }
}

public class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("[CONSOLE] " + message);
    }
}

public class DatabaseLogger implements Logger {
    private String connectionString;
    
    public DatabaseLogger(String connectionString) {
        this.connectionString = connectionString;
    }
    
    @Override
    public void log(String message) {
        System.out.println("[DATABASE:" + connectionString + "] " + message);
    }
}

// ファクトリー抽象クラス
public abstract class LoggerFactory {
    public abstract Logger createLogger();
    
    public void logMessage(String message) {
        Logger logger = createLogger();
        logger.log(message);
    }
}

// 具象ファクトリークラス
public class FileLoggerFactory extends LoggerFactory {
    private String filename;
    
    public FileLoggerFactory(String filename) {
        this.filename = filename;
    }
    
    @Override
    public Logger createLogger() {
        return new FileLogger(filename);
    }
}

public class ConsoleLoggerFactory extends LoggerFactory {
    @Override
    public Logger createLogger() {
        return new ConsoleLogger();
    }
}

public class DatabaseLoggerFactory extends LoggerFactory {
    private String connectionString;
    
    public DatabaseLoggerFactory(String connectionString) {
        this.connectionString = connectionString;
    }
    
    @Override
    public Logger createLogger() {
        return new DatabaseLogger(connectionString);
    }
}

// 使用例
public class LoggerExample {
    public static void main(String[] args) {
        LoggerFactory consoleFactory = new ConsoleLoggerFactory();
        Logger consoleLogger = consoleFactory.createLogger();
        consoleLogger.log("コンソールにログを出力");
        
        LoggerFactory fileFactory = new FileLoggerFactory("app.log");
        fileFactory.logMessage("ファイルにログを出力");
        
        LoggerFactory dbFactory = new DatabaseLoggerFactory("jdbc:mysql://localhost/db");
        dbFactory.logMessage("データベースにログを出力");
    }
}
```

### 例3: UIコンポーネント生成

OSに応じた異なるUIコンポーネントを生成する例です。

```java
// 製品インターフェース
public interface Button {
    void render();
    void onClick();
}

public interface Dialog {
    void render();
    void show();
}

// 具象製品クラス（Windows用）
public class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("Windowsスタイルのボタンを描画");
    }
    
    @Override
    public void onClick() {
        System.out.println("Windowsボタンがクリックされました");
    }
}

public class WindowsDialog implements Dialog {
    @Override
    public void render() {
        System.out.println("Windowsスタイルのダイアログを描画");
    }
    
    @Override
    public void show() {
        System.out.println("Windowsダイアログを表示");
    }
}

// 具象製品クラス（Mac用）
public class MacButton implements Button {
    @Override
    public void render() {
        System.out.println("Macスタイルのボタンを描画");
    }
    
    @Override
    public void onClick() {
        System.out.println("Macボタンがクリックされました");
    }
}

public class MacDialog implements Dialog {
    @Override
    public void render() {
        System.out.println("Macスタイルのダイアログを描画");
    }
    
    @Override
    public void show() {
        System.out.println("Macダイアログを表示");
    }
}

// ファクトリー抽象クラス
public abstract class UIFactory {
    public abstract Button createButton();
    public abstract Dialog createDialog();
    
    public void renderUI() {
        Button button = createButton();
        Dialog dialog = createDialog();
        button.render();
        dialog.render();
    }
}

// 具象ファクトリークラス
public class WindowsUIFactory extends UIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
    
    @Override
    public Dialog createDialog() {
        return new WindowsDialog();
    }
}

public class MacUIFactory extends UIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }
    
    @Override
    public Dialog createDialog() {
        return new MacDialog();
    }
}

// 使用例
public class UIExample {
    public static void main(String[] args) {
        // OSを検出（例としてWindowsを選択）
        String os = System.getProperty("os.name").toLowerCase();
        UIFactory factory;
        
        if (os.contains("windows")) {
            factory = new WindowsUIFactory();
        } else if (os.contains("mac")) {
            factory = new MacUIFactory();
        } else {
            throw new UnsupportedOperationException("サポートされていないOS: " + os);
        }
        
        Button button = factory.createButton();
        Dialog dialog = factory.createDialog();
        
        button.render();
        button.onClick();
        dialog.show();
    }
}
```

### 例4: データベース接続

異なるデータベースへの接続を生成する例です。

```java
// 製品インターフェース
public interface DatabaseConnection {
    void connect();
    void disconnect();
    void executeQuery(String query);
}

// 具象製品クラス
public class MySQLConnection implements DatabaseConnection {
    private String url;
    private String username;
    private String password;
    
    public MySQLConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    @Override
    public void connect() {
        System.out.println("MySQLデータベースに接続: " + url);
    }
    
    @Override
    public void disconnect() {
        System.out.println("MySQLデータベースから切断");
    }
    
    @Override
    public void executeQuery(String query) {
        System.out.println("MySQLでクエリを実行: " + query);
    }
}

public class PostgreSQLConnection implements DatabaseConnection {
    private String url;
    private String username;
    private String password;
    
    public PostgreSQLConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    @Override
    public void connect() {
        System.out.println("PostgreSQLデータベースに接続: " + url);
    }
    
    @Override
    public void disconnect() {
        System.out.println("PostgreSQLデータベースから切断");
    }
    
    @Override
    public void executeQuery(String query) {
        System.out.println("PostgreSQLでクエリを実行: " + query);
    }
}

// ファクトリー抽象クラス
public abstract class DatabaseConnectionFactory {
    protected String url;
    protected String username;
    protected String password;
    
    public DatabaseConnectionFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    public abstract DatabaseConnection createConnection();
    
    public void executeOperation(String query) {
        DatabaseConnection connection = createConnection();
        connection.connect();
        connection.executeQuery(query);
        connection.disconnect();
    }
}

// 具象ファクトリークラス
public class MySQLConnectionFactory extends DatabaseConnectionFactory {
    public MySQLConnectionFactory(String url, String username, String password) {
        super(url, username, password);
    }
    
    @Override
    public DatabaseConnection createConnection() {
        return new MySQLConnection(url, username, password);
    }
}

public class PostgreSQLConnectionFactory extends DatabaseConnectionFactory {
    public PostgreSQLConnectionFactory(String url, String username, String password) {
        super(url, username, password);
    }
    
    @Override
    public DatabaseConnection createConnection() {
        return new PostgreSQLConnection(url, username, password);
    }
}

// 使用例
public class DatabaseExample {
    public static void main(String[] args) {
        DatabaseConnectionFactory mysqlFactory = 
            new MySQLConnectionFactory("jdbc:mysql://localhost:3306/mydb", "user", "pass");
        DatabaseConnection mysqlConn = mysqlFactory.createConnection();
        mysqlConn.connect();
        mysqlConn.executeQuery("SELECT * FROM users");
        
        DatabaseConnectionFactory postgresFactory = 
            new PostgreSQLConnectionFactory("jdbc:postgresql://localhost:5432/mydb", "user", "pass");
        postgresFactory.executeOperation("SELECT * FROM products");
    }
}
```

---

## まとめ

### 学習のポイント

1. **ファクトリーメソッドパターンの目的**: オブジェクトの生成をサブクラスに委譲し、クライアントコードを具体的なクラスから分離
2. **基本的な構造**: 製品インターフェース、具象製品クラス、ファクトリー抽象クラス、具象ファクトリークラス
3. **拡張性**: 新しい型を追加する際に、既存のコードを変更する必要がない（オープン・クローズドの原則）
4. **実装のバリエーション**: インターフェース使用、パラメータ付き、デフォルト実装など

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 拡張性、疎結合、保守性、柔軟性 |
| **注意点** | クラス数の増加、複雑性の増加、理解の難しさ |
| **適用場面** | UIコンポーネント、ドキュメント処理、データベース接続、ログ出力など |

### 他のパターンとの関係

- **Abstract Factory**: ファクトリーメソッドパターンを拡張し、関連するオブジェクト群を生成
- **Template Method**: ファクトリー抽象クラスでテンプレートメソッドパターンを使用することが多い
- **Strategy**: 生成するオブジェクトの型を戦略として扱う

### 注意点

1. **過剰な設計を避ける**: シンプルなケースでは、直接newを使用する方が適切な場合もある
2. **適切な抽象化**: 製品インターフェースは適切な抽象化レベルで設計する
3. **命名規則**: ファクトリークラスには「Factory」という接尾辞を使用する

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. 実際のプロジェクトでファクトリーメソッドパターンを適用してみる
3. Abstract Factoryパターンを学習する（ファクトリーメソッドパターンの拡張）
4. Builderパターンを学習する（別の生成に関するパターン）

### 参考資料

- [cs-techblog.com - ファクトリーメソッドパターン](https://cs-techblog.com/technical/factory-method-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、ファクトリーメソッドパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
