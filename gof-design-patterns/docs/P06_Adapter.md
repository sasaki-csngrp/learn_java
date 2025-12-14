# アダプターパターン（Adapter Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [アダプターパターンとは](#アダプターパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

アダプターパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**構造に関するパターン（Structural Pattern）**に分類されます。

このパターンは、互換性のないインターフェースを持つオブジェクト同士を接続するためのブリッジとして機能し、既存のコードを変更することなく新しい機能を統合できるようにします。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- アダプターパターンの目的と利点を理解する
- 基本的なアダプターパターンの実装方法を理解する
- オブジェクトアダプターとクラスアダプターの違いを理解する
- 実際のプロジェクトでアダプターパターンを適用できる

---

## アダプターパターンとは

### 定義

アダプターパターンは、互換性のないインターフェースを持つクラス同士を接続するためのデザインパターンです。アダプタークラスが、既存のクラス（Adaptee）を新しいインターフェース（Target）に適合させることで、クライアントコードが既存のクラスを新しいインターフェースとして使用できるようにします。

### 主な特徴

1. **インターフェースの変換**: 既存のインターフェースを新しいインターフェースに変換
2. **既存コードの保護**: 既存のコードを変更することなく統合可能
3. **疎結合**: クライアントコードと既存のクラスを分離
4. **再利用性**: 既存のクラスを新しいコンテキストで再利用可能

### 使用される場面

アダプターパターンは、以下のような場面で使用されます：

- **レガシーシステムの統合**: 既存のシステムを新しいシステムに統合する際
- **サードパーティライブラリの統合**: 異なるインターフェースを持つライブラリを使用する際
- **APIの統一**: 異なるベンダーのAPIを統一されたインターフェースで使用する際
- **データ形式の変換**: 異なるデータ形式間の変換が必要な際
- **プラグインシステム**: 異なるインターフェースを持つプラグインを統合する際

### メリット

- **既存コードの保護**: 既存のクラスを変更することなく統合可能
- **柔軟性**: 異なるインターフェースを持つクラスを統一的に扱える
- **再利用性**: 既存のクラスを新しいコンテキストで再利用可能
- **疎結合**: クライアントコードと既存のクラスを分離

### デメリット

- **複雑性の増加**: アダプタークラスが追加されることで、コードの複雑性が増加
- **パフォーマンス**: アダプターを通すことで、わずかなオーバーヘッドが発生する可能性
- **理解の難しさ**: パターンを理解していない開発者には複雑に見える

---

## 基本的な実装

### 実装のポイント

アダプターパターンを実装するには、以下の要素が必要です：

1. **Target（ターゲット）**: クライアントが期待するインターフェース
2. **Adaptee（アダプティー）**: 既存のクラスで、変換が必要なインターフェースを持つ
3. **Adapter（アダプター）**: Targetインターフェースを実装し、Adapteeをラップして変換を行う

### 基本的な実装例

```java
// 1. Target（ターゲットインターフェース）
public interface MediaPlayer {
    void play(String fileName);
}

// 2. Adaptee（既存のクラス）
public class OldMediaPlayer {
    public void playOldMedia(String fileName) {
        System.out.println("古いメディアプレイヤーで再生: " + fileName);
    }
}

// 3. Adapter（アダプタークラス）
public class MediaAdapter implements MediaPlayer {
    private OldMediaPlayer oldMediaPlayer;
    
    public MediaAdapter(OldMediaPlayer oldMediaPlayer) {
        this.oldMediaPlayer = oldMediaPlayer;
    }
    
    @Override
    public void play(String fileName) {
        // Adapteeのメソッドを呼び出して変換
        oldMediaPlayer.playOldMedia(fileName);
    }
}

// 4. クライアントコード
public class Client {
    public static void main(String[] args) {
        // 既存のクラスをインスタンス化
        OldMediaPlayer oldPlayer = new OldMediaPlayer();
        
        // アダプターを使用して新しいインターフェースとして使用
        MediaPlayer player = new MediaAdapter(oldPlayer);
        
        // 新しいインターフェースで既存のクラスを使用
        player.play("song.mp3");
    }
}
```

### パターンの構造

```
Client
  ↓
Target (インターフェース)
  ↑
Adapter (実装)
  ↓
Adaptee (既存のクラス)
```

### 実行結果

```
古いメディアプレイヤーで再生: song.mp3
```

---

## 実装のバリエーション

### バリエーション1: オブジェクトアダプター（Object Adapter）

オブジェクトアダプターは、コンポジション（has-a関係）を使用してAdapteeをラップします。これは最も一般的な実装方法です。

```java
// Target
public interface Target {
    void request();
}

// Adaptee
public class Adaptee {
    public void specificRequest() {
        System.out.println("Adapteeの特定のリクエスト");
    }
}

// Adapter（オブジェクトアダプター）
public class ObjectAdapter implements Target {
    private Adaptee adaptee;
    
    public ObjectAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }
    
    @Override
    public void request() {
        adaptee.specificRequest();
    }
}

// 使用例
public class ObjectAdapterExample {
    public static void main(String[] args) {
        Adaptee adaptee = new Adaptee();
        Target target = new ObjectAdapter(adaptee);
        target.request();
    }
}
```

**メリット**:
- Adapteeのサブクラスを作成する必要がない
- 複数のAdapteeをラップできる
- より柔軟

### バリエーション2: クラスアダプター（Class Adapter）

クラスアダプターは、継承（is-a関係）を使用してAdapteeを継承します。Javaでは多重継承ができないため、この方法は限定的です。

```java
// Target
public interface Target {
    void request();
}

// Adaptee
public class Adaptee {
    public void specificRequest() {
        System.out.println("Adapteeの特定のリクエスト");
    }
}

// Adapter（クラスアダプター）
// 注意: Javaでは多重継承ができないため、Adapteeがクラスの場合は使用できない
// この例では、Adapteeがインターフェースの場合を想定
public interface AdapteeInterface {
    void specificRequest();
}

public class AdapteeImpl implements AdapteeInterface {
    @Override
    public void specificRequest() {
        System.out.println("Adapteeの特定のリクエスト");
    }
}

// クラスアダプターはJavaでは実装が難しいため、
// オブジェクトアダプターを使用することを推奨
```

**注意**: Javaでは多重継承ができないため、クラスアダプターは実装が困難です。オブジェクトアダプターを使用することを推奨します。

### バリエーション3: 複数のAdapteeをラップ

1つのアダプターで複数のAdapteeをラップする例です。

```java
// Target
public interface PaymentProcessor {
    void processPayment(double amount);
}

// Adaptee 1
public class CreditCardPayment {
    public void charge(double amount) {
        System.out.println("クレジットカードで支払い: " + amount + "円");
    }
}

// Adaptee 2
public class PayPalPayment {
    public void sendPayment(double amount) {
        System.out.println("PayPalで支払い: " + amount + "円");
    }
}

// Adapter（複数のAdapteeをラップ）
public class PaymentAdapter implements PaymentProcessor {
    private CreditCardPayment creditCard;
    private PayPalPayment paypal;
    private String paymentMethod;
    
    public PaymentAdapter(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.creditCard = new CreditCardPayment();
        this.paypal = new PayPalPayment();
    }
    
    @Override
    public void processPayment(double amount) {
        if ("credit".equals(paymentMethod)) {
            creditCard.charge(amount);
        } else if ("paypal".equals(paymentMethod)) {
            paypal.sendPayment(amount);
        } else {
            throw new IllegalArgumentException("未知の支払い方法: " + paymentMethod);
        }
    }
}

// 使用例
public class PaymentExample {
    public static void main(String[] args) {
        PaymentProcessor processor1 = new PaymentAdapter("credit");
        processor1.processPayment(1000.0);
        
        PaymentProcessor processor2 = new PaymentAdapter("paypal");
        processor2.processPayment(2000.0);
    }
}
```

### バリエーション4: 双方向アダプター

双方向アダプターは、両方向の変換をサポートします。

```java
// Target 1
public interface NewInterface {
    void newMethod();
}

// Target 2
public interface OldInterface {
    void oldMethod();
}

// Adaptee
public class LegacyClass implements OldInterface {
    @Override
    public void oldMethod() {
        System.out.println("レガシークラスのメソッド");
    }
}

// Adapter（双方向）
public class BidirectionalAdapter implements NewInterface, OldInterface {
    private LegacyClass legacy;
    
    public BidirectionalAdapter(LegacyClass legacy) {
        this.legacy = legacy;
    }
    
    @Override
    public void newMethod() {
        legacy.oldMethod();
    }
    
    @Override
    public void oldMethod() {
        legacy.oldMethod();
    }
}

// 使用例
public class BidirectionalExample {
    public static void main(String[] args) {
        LegacyClass legacy = new LegacyClass();
        BidirectionalAdapter adapter = new BidirectionalAdapter(legacy);
        
        NewInterface newInterface = adapter;
        OldInterface oldInterface = adapter;
        
        newInterface.newMethod();
        oldInterface.oldMethod();
    }
}
```

---

## 実践例

### 例1: メディアプレイヤーの統合

異なる形式のメディアプレイヤーを統合する例です。

```java
// Target
public interface MediaPlayer {
    void play(String audioType, String fileName);
}

// Adaptee 1
public class Mp4Player {
    public void playMp4(String fileName) {
        System.out.println("MP4ファイルを再生: " + fileName);
    }
}

// Adaptee 2
public class VlcPlayer {
    public void playVlc(String fileName) {
        System.out.println("VLCファイルを再生: " + fileName);
    }
}

// Adapter
public class MediaAdapter implements MediaPlayer {
    private Mp4Player mp4Player;
    private VlcPlayer vlcPlayer;
    
    public MediaAdapter(String audioType) {
        if (audioType.equalsIgnoreCase("mp4")) {
            mp4Player = new Mp4Player();
        } else if (audioType.equalsIgnoreCase("vlc")) {
            vlcPlayer = new VlcPlayer();
        }
    }
    
    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("mp4")) {
            mp4Player.playMp4(fileName);
        } else if (audioType.equalsIgnoreCase("vlc")) {
            vlcPlayer.playVlc(fileName);
        }
    }
}

// クライアント
public class AudioPlayer implements MediaPlayer {
    private MediaAdapter mediaAdapter;
    
    @Override
    public void play(String audioType, String fileName) {
        // 組み込みサポート
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("MP3ファイルを再生: " + fileName);
        }
        // アダプターを使用して他の形式をサポート
        else if (audioType.equalsIgnoreCase("mp4") || 
                 audioType.equalsIgnoreCase("vlc")) {
            mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType, fileName);
        } else {
            System.out.println("サポートされていない形式: " + audioType);
        }
    }
}

// 使用例
public class MediaPlayerExample {
    public static void main(String[] args) {
        AudioPlayer audioPlayer = new AudioPlayer();
        
        audioPlayer.play("mp3", "song.mp3");
        audioPlayer.play("mp4", "video.mp4");
        audioPlayer.play("vlc", "movie.vlc");
        audioPlayer.play("avi", "movie.avi");
    }
}
```

### 例2: データベース接続の統合

異なるデータベース接続インターフェースを統合する例です。

```java
// Target
public interface DatabaseConnection {
    void connect();
    void executeQuery(String query);
    void disconnect();
}

// Adaptee 1
public class MySQLConnection {
    public void openConnection() {
        System.out.println("MySQL接続を開く");
    }
    
    public void runQuery(String query) {
        System.out.println("MySQLでクエリを実行: " + query);
    }
    
    public void closeConnection() {
        System.out.println("MySQL接続を閉じる");
    }
}

// Adaptee 2
public class PostgreSQLConnection {
    public void establishConnection() {
        System.out.println("PostgreSQL接続を確立");
    }
    
    public void performQuery(String query) {
        System.out.println("PostgreSQLでクエリを実行: " + query);
    }
    
    public void terminateConnection() {
        System.out.println("PostgreSQL接続を終了");
    }
}

// Adapter for MySQL
public class MySQLAdapter implements DatabaseConnection {
    private MySQLConnection mysqlConnection;
    
    public MySQLAdapter(MySQLConnection mysqlConnection) {
        this.mysqlConnection = mysqlConnection;
    }
    
    @Override
    public void connect() {
        mysqlConnection.openConnection();
    }
    
    @Override
    public void executeQuery(String query) {
        mysqlConnection.runQuery(query);
    }
    
    @Override
    public void disconnect() {
        mysqlConnection.closeConnection();
    }
}

// Adapter for PostgreSQL
public class PostgreSQLAdapter implements DatabaseConnection {
    private PostgreSQLConnection postgresConnection;
    
    public PostgreSQLAdapter(PostgreSQLConnection postgresConnection) {
        this.postgresConnection = postgresConnection;
    }
    
    @Override
    public void connect() {
        postgresConnection.establishConnection();
    }
    
    @Override
    public void executeQuery(String query) {
        postgresConnection.performQuery(query);
    }
    
    @Override
    public void disconnect() {
        postgresConnection.terminateConnection();
    }
}

// 使用例
public class DatabaseExample {
    public static void main(String[] args) {
        // MySQL接続
        MySQLConnection mysql = new MySQLConnection();
        DatabaseConnection db1 = new MySQLAdapter(mysql);
        db1.connect();
        db1.executeQuery("SELECT * FROM users");
        db1.disconnect();
        
        System.out.println();
        
        // PostgreSQL接続
        PostgreSQLConnection postgres = new PostgreSQLConnection();
        DatabaseConnection db2 = new PostgreSQLAdapter(postgres);
        db2.connect();
        db2.executeQuery("SELECT * FROM products");
        db2.disconnect();
    }
}
```

### 例3: ログ出力システムの統合

異なるログ出力ライブラリを統合する例です。

```java
// Target
public interface Logger {
    void log(String level, String message);
}

// Adaptee 1
public class FileLogger {
    public void writeToFile(String message) {
        System.out.println("[FILE] " + message);
    }
}

// Adaptee 2
public class ConsoleLogger {
    public void printToConsole(String message) {
        System.out.println("[CONSOLE] " + message);
    }
}

// Adaptee 3
public class DatabaseLogger {
    public void saveToDatabase(String level, String message) {
        System.out.println("[DATABASE:" + level + "] " + message);
    }
}

// Adapter for FileLogger
public class FileLoggerAdapter implements Logger {
    private FileLogger fileLogger;
    
    public FileLoggerAdapter(FileLogger fileLogger) {
        this.fileLogger = fileLogger;
    }
    
    @Override
    public void log(String level, String message) {
        fileLogger.writeToFile("[" + level + "] " + message);
    }
}

// Adapter for ConsoleLogger
public class ConsoleLoggerAdapter implements Logger {
    private ConsoleLogger consoleLogger;
    
    public ConsoleLoggerAdapter(ConsoleLogger consoleLogger) {
        this.consoleLogger = consoleLogger;
    }
    
    @Override
    public void log(String level, String message) {
        consoleLogger.printToConsole("[" + level + "] " + message);
    }
}

// Adapter for DatabaseLogger
public class DatabaseLoggerAdapter implements Logger {
    private DatabaseLogger databaseLogger;
    
    public DatabaseLoggerAdapter(DatabaseLogger databaseLogger) {
        this.databaseLogger = databaseLogger;
    }
    
    @Override
    public void log(String level, String message) {
        databaseLogger.saveToDatabase(level, message);
    }
}

// 使用例
public class LoggerExample {
    public static void main(String[] args) {
        // ファイルロガー
        FileLogger fileLogger = new FileLogger();
        Logger logger1 = new FileLoggerAdapter(fileLogger);
        logger1.log("INFO", "アプリケーションが起動しました");
        
        // コンソールロガー
        ConsoleLogger consoleLogger = new ConsoleLogger();
        Logger logger2 = new ConsoleLoggerAdapter(consoleLogger);
        logger2.log("WARN", "警告メッセージ");
        
        // データベースロガー
        DatabaseLogger dbLogger = new DatabaseLogger();
        Logger logger3 = new DatabaseLoggerAdapter(dbLogger);
        logger3.log("ERROR", "エラーが発生しました");
    }
}
```

### 例4: 決済システムの統合

異なる決済プロバイダーを統合する例です。

```java
// Target
public interface PaymentService {
    void processPayment(double amount, String currency);
    boolean isPaymentSuccessful();
}

// Adaptee 1
public class StripePayment {
    public void charge(double amount) {
        System.out.println("Stripeで支払い処理: $" + amount);
    }
    
    public boolean getStatus() {
        return true;
    }
}

// Adaptee 2
public class PayPalPayment {
    public void sendMoney(double amount, String currency) {
        System.out.println("PayPalで送金: " + amount + " " + currency);
    }
    
    public boolean checkStatus() {
        return true;
    }
}

// Adaptee 3
public class BankTransfer {
    public void transfer(double amount) {
        System.out.println("銀行振込で送金: $" + amount);
    }
    
    public boolean verifyTransfer() {
        return true;
    }
}

// Adapter for Stripe
public class StripeAdapter implements PaymentService {
    private StripePayment stripe;
    
    public StripeAdapter(StripePayment stripe) {
        this.stripe = stripe;
    }
    
    @Override
    public void processPayment(double amount, String currency) {
        stripe.charge(amount);
    }
    
    @Override
    public boolean isPaymentSuccessful() {
        return stripe.getStatus();
    }
}

// Adapter for PayPal
public class PayPalAdapter implements PaymentService {
    private PayPalPayment paypal;
    
    public PayPalAdapter(PayPalPayment paypal) {
        this.paypal = paypal;
    }
    
    @Override
    public void processPayment(double amount, String currency) {
        paypal.sendMoney(amount, currency);
    }
    
    @Override
    public boolean isPaymentSuccessful() {
        return paypal.checkStatus();
    }
}

// Adapter for BankTransfer
public class BankTransferAdapter implements PaymentService {
    private BankTransfer bankTransfer;
    
    public BankTransferAdapter(BankTransfer bankTransfer) {
        this.bankTransfer = bankTransfer;
    }
    
    @Override
    public void processPayment(double amount, String currency) {
        bankTransfer.transfer(amount);
    }
    
    @Override
    public boolean isPaymentSuccessful() {
        return bankTransfer.verifyTransfer();
    }
}

// 使用例
public class PaymentServiceExample {
    public static void main(String[] args) {
        // Stripe決済
        StripePayment stripe = new StripePayment();
        PaymentService payment1 = new StripeAdapter(stripe);
        payment1.processPayment(100.0, "USD");
        System.out.println("決済成功: " + payment1.isPaymentSuccessful());
        
        System.out.println();
        
        // PayPal決済
        PayPalPayment paypal = new PayPalPayment();
        PaymentService payment2 = new PayPalAdapter(paypal);
        payment2.processPayment(200.0, "JPY");
        System.out.println("決済成功: " + payment2.isPaymentSuccessful());
        
        System.out.println();
        
        // 銀行振込
        BankTransfer bank = new BankTransfer();
        PaymentService payment3 = new BankTransferAdapter(bank);
        payment3.processPayment(300.0, "USD");
        System.out.println("決済成功: " + payment3.isPaymentSuccessful());
    }
}
```

### 例5: 温度変換システム

異なる温度単位を統合する例です。

```java
// Target
public interface TemperatureConverter {
    double convertToCelsius(double temperature);
    double convertToFahrenheit(double temperature);
    double convertToKelvin(double temperature);
}

// Adaptee 1
public class CelsiusThermometer {
    public double getCelsius() {
        return 25.0;
    }
}

// Adaptee 2
public class FahrenheitThermometer {
    public double getFahrenheit() {
        return 77.0;
    }
}

// Adaptee 3
public class KelvinThermometer {
    public double getKelvin() {
        return 298.15;
    }
}

// Adapter for Celsius
public class CelsiusAdapter implements TemperatureConverter {
    private CelsiusThermometer celsius;
    
    public CelsiusAdapter(CelsiusThermometer celsius) {
        this.celsius = celsius;
    }
    
    @Override
    public double convertToCelsius(double temperature) {
        return celsius.getCelsius();
    }
    
    @Override
    public double convertToFahrenheit(double temperature) {
        return (celsius.getCelsius() * 9.0 / 5.0) + 32.0;
    }
    
    @Override
    public double convertToKelvin(double temperature) {
        return celsius.getCelsius() + 273.15;
    }
}

// Adapter for Fahrenheit
public class FahrenheitAdapter implements TemperatureConverter {
    private FahrenheitThermometer fahrenheit;
    
    public FahrenheitAdapter(FahrenheitThermometer fahrenheit) {
        this.fahrenheit = fahrenheit;
    }
    
    @Override
    public double convertToCelsius(double temperature) {
        return (fahrenheit.getFahrenheit() - 32.0) * 5.0 / 9.0;
    }
    
    @Override
    public double convertToFahrenheit(double temperature) {
        return fahrenheit.getFahrenheit();
    }
    
    @Override
    public double convertToKelvin(double temperature) {
        return convertToCelsius(temperature) + 273.15;
    }
}

// Adapter for Kelvin
public class KelvinAdapter implements TemperatureConverter {
    private KelvinThermometer kelvin;
    
    public KelvinAdapter(KelvinThermometer kelvin) {
        this.kelvin = kelvin;
    }
    
    @Override
    public double convertToCelsius(double temperature) {
        return kelvin.getKelvin() - 273.15;
    }
    
    @Override
    public double convertToFahrenheit(double temperature) {
        return (kelvin.getKelvin() - 273.15) * 9.0 / 5.0 + 32.0;
    }
    
    @Override
    public double convertToKelvin(double temperature) {
        return kelvin.getKelvin();
    }
}

// 使用例
public class TemperatureExample {
    public static void main(String[] args) {
        // 摂氏温度計
        CelsiusThermometer celsius = new CelsiusThermometer();
        TemperatureConverter converter1 = new CelsiusAdapter(celsius);
        System.out.println("摂氏: " + converter1.convertToCelsius(0));
        System.out.println("華氏: " + converter1.convertToFahrenheit(0));
        System.out.println("ケルビン: " + converter1.convertToKelvin(0));
        
        System.out.println();
        
        // 華氏温度計
        FahrenheitThermometer fahrenheit = new FahrenheitThermometer();
        TemperatureConverter converter2 = new FahrenheitAdapter(fahrenheit);
        System.out.println("摂氏: " + converter2.convertToCelsius(0));
        System.out.println("華氏: " + converter2.convertToFahrenheit(0));
        System.out.println("ケルビン: " + converter2.convertToKelvin(0));
    }
}
```

---

## まとめ

### 学習のポイント

1. **アダプターパターンの目的**: 互換性のないインターフェースを持つクラス同士を接続
2. **基本的な構造**: Target、Adapter、Adapteeの3つの主要コンポーネント
3. **実装方法**: オブジェクトアダプター（推奨）とクラスアダプター
4. **既存コードの保護**: 既存のコードを変更することなく統合可能

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 既存コードの保護、柔軟性、再利用性、疎結合 |
| **注意点** | 複雑性の増加、パフォーマンス、理解の難しさ |
| **適用場面** | レガシーシステム統合、サードパーティライブラリ、API統一、データ形式変換など |

### オブジェクトアダプター vs クラスアダプター

| 項目 | オブジェクトアダプター | クラスアダプター |
|------|---------------------|----------------|
| **実装方法** | コンポジション（has-a） | 継承（is-a） |
| **Javaでの実装** | ✅ 容易 | ❌ 困難（多重継承不可） |
| **柔軟性** | ⭐⭐⭐⭐ | ⭐⭐⭐ |
| **推奨度** | ⭐⭐⭐⭐⭐ | ⭐ |

### 他のパターンとの関係

- **Decorator**: アダプターはインターフェースを変換するが、デコレーターは機能を追加する
- **Facade**: アダプターは1つのクラスを変換するが、ファサードは複数のクラスを統合する
- **Bridge**: アダプターは既存のインターフェースに適合させるが、ブリッジは設計時に分離する

### 注意点

1. **過剰な使用を避ける**: シンプルなケースでは、直接統合する方が適切な場合もある
2. **パフォーマンス**: アダプターを通すことで、わずかなオーバーヘッドが発生する可能性
3. **適切な抽象化**: Targetインターフェースは適切な抽象化レベルで設計する
4. **Javaでの実装**: クラスアダプターはJavaでは実装が困難なため、オブジェクトアダプターを使用することを推奨

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. 実際のプロジェクトでアダプターパターンを適用してみる
3. Decoratorパターンを学習する（機能追加のパターン）
4. Facadeパターンを学習する（複数クラス統合のパターン）

### 参考資料

- [cs-techblog.com - アダプターパターン](https://cs-techblog.com/technical/adapter-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、アダプターパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
