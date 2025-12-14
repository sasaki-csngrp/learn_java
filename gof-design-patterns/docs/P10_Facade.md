# ファサードパターン（Facade Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [ファサードパターンとは](#ファサードパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

ファサードパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**構造に関するパターン（Structural Pattern）**に分類されます。

このパターンは、複雑なサブシステムに対して簡潔なインターフェースを提供し、クライアントがサブシステムの複雑な詳細を理解することなく、システムと対話できるようにします。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- ファサードパターンの目的と利点を理解する
- 基本的なファサードパターンの実装方法を理解する
- サブシステムの複雑さを隠蔽する方法を理解する
- 実際のプロジェクトでファサードパターンを適用できる

---

## ファサードパターンとは

### 定義

ファサードパターンは、複雑なサブシステムに対して簡潔なインターフェースを提供するデザインパターンです。ファサードクラスは、サブシステムの複雑さを隠蔽し、クライアントが必要とする機能を提供する統一されたインターフェースを提供します。

### 主な特徴

1. **簡素化**: サブシステムの複雑さをクライアントから隠蔽
2. **疎結合**: クライアントとサブシステムの間の依存関係を削減
3. **柔軟性**: サブシステムの変更がクライアントコードに影響を与えない（ファサードのインターフェースが一定である限り）
4. **統一インターフェース**: 複数のサブシステムを統合した単一のインターフェースを提供

### 使用される場面

ファサードパターンは、以下のような場面で使用されます：

- **複雑なサブシステム**: 複数の相互依存するクラスやモジュールを持つシステム
- **ライブラリの統合**: 複数のライブラリを統合して使用する場合
- **APIの簡素化**: 複雑なAPIを簡潔なインターフェースで提供する場合
- **レガシーシステムの統合**: 既存の複雑なシステムを新しいシステムに統合する場合
- **フレームワーク**: フレームワークが複雑な処理を簡潔なAPIで提供する場合

### メリット

- **簡素化**: サブシステムの複雑さをクライアントから隠蔽し、使いやすいインターフェースを提供
- **疎結合**: クライアントとサブシステムの間の依存関係を削減
- **柔軟性**: サブシステムの変更がクライアントコードに影響を与えない
- **保守性**: サブシステムの変更が1箇所（ファサード）に集約される

### デメリット

- **機能の制限**: ファサードが提供する機能が限定的になる可能性
- **追加の抽象化層**: ファサードクラスが追加の抽象化層を導入
- **過度な使用**: シンプルなシステムでは過剰な設計になる可能性

---

## 基本的な実装

### 実装のポイント

ファサードパターンを実装するには、以下の要素が必要です：

1. **Subsystem Classes（サブシステムクラス）**: 複雑な機能を提供する既存のクラス群
2. **Facade（ファサード）**: サブシステムへの統一されたインターフェースを提供するクラス

### 基本的な実装例

```java
// 1. Subsystem Classes（サブシステムクラス）

// 書籍検索サブシステム
public class BookSearch {
    public void search(String bookTitle) {
        System.out.println("書籍を検索中: " + bookTitle);
        // 実際の検索処理
    }
    
    public boolean isAvailable(String bookTitle) {
        System.out.println("書籍の在庫を確認中: " + bookTitle);
        // 実際の在庫確認処理
        return true;
    }
}

// 書籍貸出サブシステム
public class BookLoan {
    public void loan(String bookTitle, String userId) {
        System.out.println("書籍を貸出中: " + bookTitle + " (ユーザー: " + userId + ")");
        // 実際の貸出処理
    }
    
    public void recordLoan(String bookTitle, String userId) {
        System.out.println("貸出記録を保存中: " + bookTitle);
        // 実際の記録処理
    }
}

// 書籍返却サブシステム
public class BookReturn {
    public void returnBook(String bookTitle, String userId) {
        System.out.println("書籍を返却中: " + bookTitle + " (ユーザー: " + userId + ")");
        // 実際の返却処理
    }
    
    public void updateInventory(String bookTitle) {
        System.out.println("在庫を更新中: " + bookTitle);
        // 実際の在庫更新処理
    }
}

// 2. Facade（ファサードクラス）
public class LibraryFacade {
    private BookSearch searcher;
    private BookLoan loaner;
    private BookReturn returner;
    
    public LibraryFacade() {
        this.searcher = new BookSearch();
        this.loaner = new BookLoan();
        this.returner = new BookReturn();
    }
    
    // 書籍を検索して貸出する（簡潔なインターフェース）
    public void findAndLoanBook(String bookTitle, String userId) {
        System.out.println("=== 書籍の検索と貸出 ===");
        searcher.search(bookTitle);
        if (searcher.isAvailable(bookTitle)) {
            loaner.loan(bookTitle, userId);
            loaner.recordLoan(bookTitle, userId);
            System.out.println("貸出が完了しました");
        } else {
            System.out.println("書籍は現在利用できません");
        }
    }
    
    // 書籍を返却する（簡潔なインターフェース）
    public void returnBook(String bookTitle, String userId) {
        System.out.println("=== 書籍の返却 ===");
        returner.returnBook(bookTitle, userId);
        returner.updateInventory(bookTitle);
        System.out.println("返却が完了しました");
    }
    
    // 書籍を検索する（簡潔なインターフェース）
    public boolean searchBook(String bookTitle) {
        System.out.println("=== 書籍の検索 ===");
        searcher.search(bookTitle);
        return searcher.isAvailable(bookTitle);
    }
}

// 3. クライアントコード
public class FacadePatternDemo {
    public static void main(String[] args) {
        // ファサードを使用（簡潔なインターフェース）
        LibraryFacade library = new LibraryFacade();
        
        // 書籍を検索して貸出
        library.findAndLoanBook("デザインパターン入門", "user123");
        
        System.out.println();
        
        // 書籍を返却
        library.returnBook("デザインパターン入門", "user123");
        
        System.out.println();
        
        // 書籍を検索
        boolean available = library.searchBook("Java入門");
        System.out.println("利用可能: " + available);
    }
}
```

### パターンの構造

```
Client
  ↓
Facade (統一インターフェース)
  ↓
Subsystem Classes (複雑なサブシステム)
  ├─ Class1
  ├─ Class2
  └─ Class3
```

### 実行結果

```
=== 書籍の検索と貸出 ===
書籍を検索中: デザインパターン入門
書籍の在庫を確認中: デザインパターン入門
書籍を貸出中: デザインパターン入門 (ユーザー: user123)
貸出記録を保存中: デザインパターン入門
貸出が完了しました

=== 書籍の返却 ===
書籍を返却中: デザインパターン入門 (ユーザー: user123)
在庫を更新中: デザインパターン入門
返却が完了しました

=== 書籍の検索 ===
書籍を検索中: Java入門
書籍の在庫を確認中: Java入門
利用可能: true
```

### ファサードパターンを使わない場合の問題

ファサードパターンを使わない場合、クライアントはサブシステムの複雑さを直接扱う必要があります：

```java
// 問題のある設計：クライアントがサブシステムの複雑さを直接扱う
public class Client {
    public void borrowBook(String bookTitle, String userId) {
        BookSearch searcher = new BookSearch();
        BookLoan loaner = new BookLoan();
        
        searcher.search(bookTitle);
        if (searcher.isAvailable(bookTitle)) {
            loaner.loan(bookTitle, userId);
            loaner.recordLoan(bookTitle, userId);
        }
        // クライアントコードが複雑になる
    }
}
```

ファサードパターンを使用することで、クライアントコードが簡潔になり、サブシステムの変更の影響を受けにくくなります。

---

## 実装のバリエーション

### バリエーション1: 複数のファサード

異なる用途に応じて複数のファサードを提供する方法です。

```java
// サブシステムクラス
public class PaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("支払いを処理: " + amount + "円");
    }
}

public class InventoryManager {
    public void updateStock(String productId, int quantity) {
        System.out.println("在庫を更新: " + productId + " (" + quantity + "個)");
    }
}

public class ShippingService {
    public void shipOrder(String orderId, String address) {
        System.out.println("注文を発送: " + orderId + " → " + address);
    }
}

public class EmailService {
    public void sendConfirmation(String email, String message) {
        System.out.println("確認メールを送信: " + email);
    }
}

// ファサード1: 注文処理用
public class OrderFacade {
    private PaymentProcessor payment;
    private InventoryManager inventory;
    private ShippingService shipping;
    private EmailService email;
    
    public OrderFacade() {
        this.payment = new PaymentProcessor();
        this.inventory = new InventoryManager();
        this.shipping = new ShippingService();
        this.email = new EmailService();
    }
    
    public void placeOrder(String orderId, String productId, double amount, 
                          String address, String email) {
        System.out.println("=== 注文処理 ===");
        payment.processPayment(amount);
        inventory.updateStock(productId, -1);
        shipping.shipOrder(orderId, address);
        this.email.sendConfirmation(email, "注文が確定しました");
        System.out.println("注文処理が完了しました");
    }
}

// ファサード2: 在庫管理用
public class InventoryFacade {
    private InventoryManager inventory;
    private EmailService email;
    
    public InventoryFacade() {
        this.inventory = new InventoryManager();
        this.email = new EmailService();
    }
    
    public void restock(String productId, int quantity) {
        System.out.println("=== 在庫補充 ===");
        inventory.updateStock(productId, quantity);
        email.sendConfirmation("admin@example.com", 
            productId + "の在庫を" + quantity + "個補充しました");
        System.out.println("在庫補充が完了しました");
    }
}

// 使用例
public class MultipleFacadeExample {
    public static void main(String[] args) {
        // 注文処理用ファサード
        OrderFacade orderFacade = new OrderFacade();
        orderFacade.placeOrder("ORD001", "PROD001", 5000.0, 
            "東京都...", "customer@example.com");
        
        System.out.println();
        
        // 在庫管理用ファサード
        InventoryFacade inventoryFacade = new InventoryFacade();
        inventoryFacade.restock("PROD001", 10);
    }
}
```

### バリエーション2: 設定可能なファサード

ファサードの動作を設定可能にする方法です。

```java
// サブシステムクラス
public class DatabaseConnection {
    public void connect() {
        System.out.println("データベースに接続");
    }
    
    public void disconnect() {
        System.out.println("データベースから切断");
    }
}

public class CacheManager {
    public void enableCache() {
        System.out.println("キャッシュを有効化");
    }
    
    public void disableCache() {
        System.out.println("キャッシュを無効化");
    }
}

public class Logger {
    public void enableLogging() {
        System.out.println("ログを有効化");
    }
    
    public void disableLogging() {
        System.out.println("ログを無効化");
    }
}

// 設定可能なファサード
public class ConfigurableFacade {
    private DatabaseConnection db;
    private CacheManager cache;
    private Logger logger;
    private boolean useCache;
    private boolean useLogging;
    
    public ConfigurableFacade(boolean useCache, boolean useLogging) {
        this.db = new DatabaseConnection();
        this.cache = new CacheManager();
        this.logger = new Logger();
        this.useCache = useCache;
        this.useLogging = useLogging;
    }
    
    public void initialize() {
        System.out.println("=== システム初期化 ===");
        db.connect();
        
        if (useCache) {
            cache.enableCache();
        }
        
        if (useLogging) {
            logger.enableLogging();
        }
        
        System.out.println("初期化が完了しました");
    }
    
    public void shutdown() {
        System.out.println("=== システム終了 ===");
        
        if (useLogging) {
            logger.disableLogging();
        }
        
        if (useCache) {
            cache.disableCache();
        }
        
        db.disconnect();
        System.out.println("終了が完了しました");
    }
}

// 使用例
public class ConfigurableFacadeExample {
    public static void main(String[] args) {
        // キャッシュとログを有効化
        ConfigurableFacade facade1 = new ConfigurableFacade(true, true);
        facade1.initialize();
        facade1.shutdown();
        
        System.out.println();
        
        // キャッシュとログを無効化
        ConfigurableFacade facade2 = new ConfigurableFacade(false, false);
        facade2.initialize();
        facade2.shutdown();
    }
}
```

### バリエーション3: 階層的なファサード

ファサードを階層的に構築する方法です。

```java
// 低レベルサブシステム
public class FileReader {
    public String readFile(String filename) {
        System.out.println("ファイルを読み込み: " + filename);
        return "ファイルの内容";
    }
}

public class FileWriter {
    public void writeFile(String filename, String content) {
        System.out.println("ファイルに書き込み: " + filename);
    }
}

// 中レベルファサード
public class FileOperationsFacade {
    private FileReader reader;
    private FileWriter writer;
    
    public FileOperationsFacade() {
        this.reader = new FileReader();
        this.writer = new FileWriter();
    }
    
    public String read(String filename) {
        return reader.readFile(filename);
    }
    
    public void write(String filename, String content) {
        writer.writeFile(filename, content);
    }
}

// 高レベルサブシステム
public class DataProcessor {
    public String process(String data) {
        System.out.println("データを処理中...");
        return "処理済み: " + data;
    }
}

public class DataValidator {
    public boolean validate(String data) {
        System.out.println("データを検証中...");
        return true;
    }
}

// 高レベルファサード（低レベルファサードを使用）
public class DataManagementFacade {
    private FileOperationsFacade fileOps;
    private DataProcessor processor;
    private DataValidator validator;
    
    public DataManagementFacade() {
        this.fileOps = new FileOperationsFacade();
        this.processor = new DataProcessor();
        this.validator = new DataValidator();
    }
    
    public void processFile(String inputFile, String outputFile) {
        System.out.println("=== ファイル処理 ===");
        String data = fileOps.read(inputFile);
        
        if (validator.validate(data)) {
            String processed = processor.process(data);
            fileOps.write(outputFile, processed);
            System.out.println("処理が完了しました");
        } else {
            System.out.println("データの検証に失敗しました");
        }
    }
}

// 使用例
public class HierarchicalFacadeExample {
    public static void main(String[] args) {
        DataManagementFacade facade = new DataManagementFacade();
        facade.processFile("input.txt", "output.txt");
    }
}
```

---

## 実践例

### 例1: 図書館管理システム

書籍の検索、貸出、返却を統合した図書館管理システムの例です。

```java
// サブシステムクラス
public class BookSearch {
    private java.util.Map<String, Boolean> books = new java.util.HashMap<>();
    
    public BookSearch() {
        books.put("デザインパターン入門", true);
        books.put("Java入門", true);
        books.put("データベース設計", false);
    }
    
    public void search(String bookTitle) {
        System.out.println("📚 書籍を検索中: " + bookTitle);
    }
    
    public boolean isAvailable(String bookTitle) {
        return books.getOrDefault(bookTitle, false);
    }
    
    public void displayBookInfo(String bookTitle) {
        System.out.println("  タイトル: " + bookTitle);
        System.out.println("  在庫状況: " + (isAvailable(bookTitle) ? "利用可能" : "貸出中"));
    }
}

public class BookLoan {
    private java.util.List<String> loanedBooks = new java.util.ArrayList<>();
    
    public void loan(String bookTitle, String userId) {
        System.out.println("📖 書籍を貸出中: " + bookTitle);
        System.out.println("  ユーザーID: " + userId);
        loanedBooks.add(bookTitle + ":" + userId);
    }
    
    public void recordLoan(String bookTitle, String userId) {
        System.out.println("💾 貸出記録を保存: " + bookTitle + " → " + userId);
    }
    
    public boolean isLoaned(String bookTitle) {
        return loanedBooks.stream()
            .anyMatch(record -> record.startsWith(bookTitle + ":"));
    }
}

public class BookReturn {
    public void returnBook(String bookTitle, String userId) {
        System.out.println("📥 書籍を返却中: " + bookTitle);
        System.out.println("  ユーザーID: " + userId);
    }
    
    public void updateInventory(String bookTitle) {
        System.out.println("📊 在庫を更新: " + bookTitle);
    }
    
    public void calculateFine(String bookTitle, int daysOverdue) {
        if (daysOverdue > 0) {
            double fine = daysOverdue * 100;
            System.out.println("💰 延滞料: " + fine + "円 (" + daysOverdue + "日)");
        }
    }
}

public class NotificationService {
    public void sendEmail(String email, String message) {
        System.out.println("📧 メール送信: " + email);
        System.out.println("  内容: " + message);
    }
    
    public void sendSMS(String phone, String message) {
        System.out.println("📱 SMS送信: " + phone);
        System.out.println("  内容: " + message);
    }
}

// ファサードクラス
public class LibraryFacade {
    private BookSearch searcher;
    private BookLoan loaner;
    private BookReturn returner;
    private NotificationService notification;
    
    public LibraryFacade() {
        this.searcher = new BookSearch();
        this.loaner = new BookLoan();
        this.returner = new BookReturn();
        this.notification = new NotificationService();
    }
    
    // 書籍を検索して貸出
    public boolean findAndLoanBook(String bookTitle, String userId, String email) {
        System.out.println("=== 書籍の検索と貸出 ===");
        searcher.search(bookTitle);
        searcher.displayBookInfo(bookTitle);
        
        if (searcher.isAvailable(bookTitle)) {
            loaner.loan(bookTitle, userId);
            loaner.recordLoan(bookTitle, userId);
            notification.sendEmail(email, 
                "「" + bookTitle + "」の貸出が完了しました");
            System.out.println("✅ 貸出が完了しました");
            return true;
        } else {
            System.out.println("❌ 書籍は現在利用できません");
            return false;
        }
    }
    
    // 書籍を返却
    public void returnBook(String bookTitle, String userId, int daysOverdue) {
        System.out.println("=== 書籍の返却 ===");
        returner.returnBook(bookTitle, userId);
        returner.updateInventory(bookTitle);
        returner.calculateFine(bookTitle, daysOverdue);
        System.out.println("✅ 返却が完了しました");
    }
    
    // 書籍を検索
    public boolean searchBook(String bookTitle) {
        System.out.println("=== 書籍の検索 ===");
        searcher.search(bookTitle);
        searcher.displayBookInfo(bookTitle);
        return searcher.isAvailable(bookTitle);
    }
}

// 使用例
public class LibrarySystemExample {
    public static void main(String[] args) {
        LibraryFacade library = new LibraryFacade();
        
        // 書籍を検索して貸出
        library.findAndLoanBook("デザインパターン入門", "user123", 
            "user123@example.com");
        
        System.out.println();
        
        // 書籍を返却
        library.returnBook("デザインパターン入門", "user123", 0);
        
        System.out.println();
        
        // 書籍を検索
        library.searchBook("Java入門");
    }
}
```

### 例2: ホームオートメーションシステム

家電製品を統合制御するホームオートメーションシステムの例です。

```java
// サブシステムクラス
public class LightSystem {
    public void turnOn() {
        System.out.println("💡 照明を点灯");
    }
    
    public void turnOff() {
        System.out.println("💡 照明を消灯");
    }
    
    public void setBrightness(int level) {
        System.out.println("💡 明るさを設定: " + level + "%");
    }
}

public class HeatingSystem {
    public void turnOn() {
        System.out.println("🔥 暖房を起動");
    }
    
    public void turnOff() {
        System.out.println("🔥 暖房を停止");
    }
    
    public void setTemperature(int temperature) {
        System.out.println("🔥 温度を設定: " + temperature + "°C");
    }
}

public class SecuritySystem {
    public void arm() {
        System.out.println("🔒 セキュリティシステムを有効化");
    }
    
    public void disarm() {
        System.out.println("🔒 セキュリティシステムを無効化");
    }
    
    public void lockDoors() {
        System.out.println("🔒 ドアをロック");
    }
    
    public void unlockDoors() {
        System.out.println("🔒 ドアをアンロック");
    }
}

public class MusicSystem {
    public void turnOn() {
        System.out.println("🎵 音楽システムを起動");
    }
    
    public void turnOff() {
        System.out.println("🎵 音楽システムを停止");
    }
    
    public void play(String playlist) {
        System.out.println("🎵 プレイリストを再生: " + playlist);
    }
}

// ファサードクラス
public class HomeAutomationFacade {
    private LightSystem lights;
    private HeatingSystem heating;
    private SecuritySystem security;
    private MusicSystem music;
    
    public HomeAutomationFacade() {
        this.lights = new LightSystem();
        this.heating = new HeatingSystem();
        this.security = new SecuritySystem();
        this.music = new MusicSystem();
    }
    
    // 帰宅モード
    public void arriveHome() {
        System.out.println("=== 帰宅モード ===");
        security.disarm();
        security.unlockDoors();
        lights.turnOn();
        lights.setBrightness(80);
        heating.setTemperature(22);
        music.turnOn();
        music.play("リラックス");
        System.out.println("✅ 帰宅モードが有効になりました");
    }
    
    // 外出モード
    public void leaveHome() {
        System.out.println("=== 外出モード ===");
        lights.turnOff();
        heating.turnOff();
        music.turnOff();
        security.lockDoors();
        security.arm();
        System.out.println("✅ 外出モードが有効になりました");
    }
    
    // 就寝モード
    public void sleepMode() {
        System.out.println("=== 就寝モード ===");
        lights.setBrightness(20);
        heating.setTemperature(18);
        music.turnOff();
        security.lockDoors();
        security.arm();
        System.out.println("✅ 就寝モードが有効になりました");
    }
    
    // 起床モード
    public void wakeUp() {
        System.out.println("=== 起床モード ===");
        lights.turnOn();
        lights.setBrightness(100);
        heating.setTemperature(22);
        music.turnOn();
        music.play("モーニング");
        System.out.println("✅ 起床モードが有効になりました");
    }
}

// 使用例
public class HomeAutomationExample {
    public static void main(String[] args) {
        HomeAutomationFacade home = new HomeAutomationFacade();
        
        // 帰宅
        home.arriveHome();
        
        System.out.println();
        
        // 就寝
        home.sleepMode();
        
        System.out.println();
        
        // 起床
        home.wakeUp();
        
        System.out.println();
        
        // 外出
        home.leaveHome();
    }
}
```

### 例3: オンラインショッピングシステム

注文処理、支払い、配送を統合したオンラインショッピングシステムの例です。

```java
// サブシステムクラス
public class InventoryService {
    private java.util.Map<String, Integer> stock = new java.util.HashMap<>();
    
    public InventoryService() {
        stock.put("PROD001", 10);
        stock.put("PROD002", 5);
        stock.put("PROD003", 0);
    }
    
    public boolean checkAvailability(String productId, int quantity) {
        int available = stock.getOrDefault(productId, 0);
        System.out.println("📦 在庫確認: " + productId + " (" + available + "個)");
        return available >= quantity;
    }
    
    public void updateStock(String productId, int quantity) {
        int current = stock.getOrDefault(productId, 0);
        stock.put(productId, current - quantity);
        System.out.println("📦 在庫更新: " + productId + " → " + stock.get(productId) + "個");
    }
}

public class PaymentService {
    public boolean processPayment(String paymentMethod, double amount) {
        System.out.println("💳 支払い処理: " + paymentMethod);
        System.out.println("   金額: " + amount + "円");
        // 実際の支払い処理
        return true;
    }
    
    public void refund(String transactionId, double amount) {
        System.out.println("💳 返金処理: " + transactionId);
        System.out.println("   金額: " + amount + "円");
    }
}

public class ShippingService {
    public String createShipment(String orderId, String address) {
        System.out.println("🚚 配送を作成: " + orderId);
        System.out.println("   配送先: " + address);
        return "SHIP-" + orderId;
    }
    
    public void trackShipment(String shipmentId) {
        System.out.println("🚚 配送状況を確認: " + shipmentId);
    }
}

public class NotificationService {
    public void sendOrderConfirmation(String email, String orderId) {
        System.out.println("📧 注文確認メール送信: " + email);
        System.out.println("   注文ID: " + orderId);
    }
    
    public void sendShippingNotification(String email, String shipmentId) {
        System.out.println("📧 配送通知メール送信: " + email);
        System.out.println("   配送ID: " + shipmentId);
    }
}

// ファサードクラス
public class ShoppingFacade {
    private InventoryService inventory;
    private PaymentService payment;
    private ShippingService shipping;
    private NotificationService notification;
    
    public ShoppingFacade() {
        this.inventory = new InventoryService();
        this.payment = new PaymentService();
        this.shipping = new ShippingService();
        this.notification = new NotificationService();
    }
    
    // 注文を処理
    public String placeOrder(String productId, int quantity, double amount, 
                           String paymentMethod, String address, String email) {
        System.out.println("=== 注文処理 ===");
        
        // 在庫確認
        if (!inventory.checkAvailability(productId, quantity)) {
            System.out.println("❌ 在庫が不足しています");
            return null;
        }
        
        // 支払い処理
        if (!payment.processPayment(paymentMethod, amount)) {
            System.out.println("❌ 支払い処理に失敗しました");
            return null;
        }
        
        // 在庫更新
        inventory.updateStock(productId, quantity);
        
        // 注文ID生成
        String orderId = "ORD-" + System.currentTimeMillis();
        
        // 配送作成
        String shipmentId = shipping.createShipment(orderId, address);
        
        // 通知送信
        notification.sendOrderConfirmation(email, orderId);
        notification.sendShippingNotification(email, shipmentId);
        
        System.out.println("✅ 注文処理が完了しました: " + orderId);
        return orderId;
    }
    
    // 注文をキャンセル
    public void cancelOrder(String orderId, double refundAmount) {
        System.out.println("=== 注文キャンセル ===");
        payment.refund(orderId, refundAmount);
        System.out.println("✅ 注文がキャンセルされました: " + orderId);
    }
    
    // 配送状況を確認
    public void trackOrder(String shipmentId) {
        System.out.println("=== 配送状況確認 ===");
        shipping.trackShipment(shipmentId);
    }
}

// 使用例
public class ShoppingSystemExample {
    public static void main(String[] args) {
        ShoppingFacade shop = new ShoppingFacade();
        
        // 注文を処理
        String orderId = shop.placeOrder(
            "PROD001", 
            2, 
            5000.0, 
            "クレジットカード",
            "東京都渋谷区...",
            "customer@example.com"
        );
        
        System.out.println();
        
        // 配送状況を確認
        if (orderId != null) {
            shop.trackOrder("SHIP-" + orderId);
        }
    }
}
```

### 例4: マルチメディアプレイヤー

複数のメディア処理を統合したマルチメディアプレイヤーの例です。

```java
// サブシステムクラス
public class AudioPlayer {
    public void playAudio(String filename) {
        System.out.println("🔊 音声を再生: " + filename);
    }
    
    public void stopAudio() {
        System.out.println("🔊 音声を停止");
    }
    
    public void setVolume(int volume) {
        System.out.println("🔊 音量を設定: " + volume + "%");
    }
}

public class VideoPlayer {
    public void playVideo(String filename) {
        System.out.println("🎬 動画を再生: " + filename);
    }
    
    public void stopVideo() {
        System.out.println("🎬 動画を停止");
    }
    
    public void setBrightness(int brightness) {
        System.out.println("🎬 明るさを設定: " + brightness + "%");
    }
}

public class SubtitleService {
    public void loadSubtitle(String filename) {
        System.out.println("📝 字幕を読み込み: " + filename);
    }
    
    public void enableSubtitle() {
        System.out.println("📝 字幕を有効化");
    }
    
    public void disableSubtitle() {
        System.out.println("📝 字幕を無効化");
    }
}

public class PlaylistManager {
    private java.util.List<String> playlist = new java.util.ArrayList<>();
    
    public void addToPlaylist(String filename) {
        playlist.add(filename);
        System.out.println("📋 プレイリストに追加: " + filename);
    }
    
    public void playNext() {
        if (!playlist.isEmpty()) {
            String next = playlist.remove(0);
            System.out.println("📋 次を再生: " + next);
        }
    }
}

// ファサードクラス
public class MediaPlayerFacade {
    private AudioPlayer audioPlayer;
    private VideoPlayer videoPlayer;
    private SubtitleService subtitleService;
    private PlaylistManager playlistManager;
    
    public MediaPlayerFacade() {
        this.audioPlayer = new AudioPlayer();
        this.videoPlayer = new VideoPlayer();
        this.subtitleService = new SubtitleService();
        this.playlistManager = new PlaylistManager();
    }
    
    // 動画を再生（音声、動画、字幕を統合）
    public void playMovie(String videoFile, String audioFile, String subtitleFile) {
        System.out.println("=== 動画再生 ===");
        videoPlayer.playVideo(videoFile);
        audioPlayer.playAudio(audioFile);
        subtitleService.loadSubtitle(subtitleFile);
        subtitleService.enableSubtitle();
        System.out.println("✅ 動画再生を開始しました");
    }
    
    // 音楽を再生
    public void playMusic(String audioFile, int volume) {
        System.out.println("=== 音楽再生 ===");
        audioPlayer.setVolume(volume);
        audioPlayer.playAudio(audioFile);
        System.out.println("✅ 音楽再生を開始しました");
    }
    
    // プレイリストを再生
    public void playPlaylist(java.util.List<String> files) {
        System.out.println("=== プレイリスト再生 ===");
        for (String file : files) {
            playlistManager.addToPlaylist(file);
        }
        playlistManager.playNext();
        System.out.println("✅ プレイリスト再生を開始しました");
    }
    
    // すべてを停止
    public void stopAll() {
        System.out.println("=== すべて停止 ===");
        videoPlayer.stopVideo();
        audioPlayer.stopAudio();
        subtitleService.disableSubtitle();
        System.out.println("✅ すべて停止しました");
    }
}

// 使用例
public class MediaPlayerExample {
    public static void main(String[] args) {
        MediaPlayerFacade player = new MediaPlayerFacade();
        
        // 動画を再生
        player.playMovie("movie.mp4", "audio.mp3", "subtitle.srt");
        
        System.out.println();
        
        // 音楽を再生
        player.playMusic("song.mp3", 80);
        
        System.out.println();
        
        // すべて停止
        player.stopAll();
    }
}
```

### 例5: データベース操作システム

複雑なデータベース操作を統合したシステムの例です。

```java
// サブシステムクラス
public class ConnectionManager {
    public void connect(String url, String username, String password) {
        System.out.println("🔌 データベースに接続: " + url);
        System.out.println("   ユーザー: " + username);
    }
    
    public void disconnect() {
        System.out.println("🔌 データベースから切断");
    }
}

public class QueryExecutor {
    public void executeQuery(String query) {
        System.out.println("📊 クエリを実行: " + query);
    }
    
    public void executeUpdate(String query) {
        System.out.println("📝 更新クエリを実行: " + query);
    }
}

public class TransactionManager {
    public void beginTransaction() {
        System.out.println("🔄 トランザクションを開始");
    }
    
    public void commit() {
        System.out.println("🔄 トランザクションをコミット");
    }
    
    public void rollback() {
        System.out.println("🔄 トランザクションをロールバック");
    }
}

public class CacheManager {
    private java.util.Map<String, Object> cache = new java.util.HashMap<>();
    
    public Object get(String key) {
        Object value = cache.get(key);
        if (value != null) {
            System.out.println("💾 キャッシュから取得: " + key);
        }
        return value;
    }
    
    public void put(String key, Object value) {
        cache.put(key, value);
        System.out.println("💾 キャッシュに保存: " + key);
    }
}

// ファサードクラス
public class DatabaseFacade {
    private ConnectionManager connectionManager;
    private QueryExecutor queryExecutor;
    private TransactionManager transactionManager;
    private CacheManager cacheManager;
    
    public DatabaseFacade() {
        this.connectionManager = new ConnectionManager();
        this.queryExecutor = new QueryExecutor();
        this.transactionManager = new TransactionManager();
        this.cacheManager = new CacheManager();
    }
    
    // データベース操作を実行（接続、クエリ実行、切断を統合）
    public void executeQuery(String url, String username, String password, String query) {
        System.out.println("=== データベース操作 ===");
        connectionManager.connect(url, username, password);
        queryExecutor.executeQuery(query);
        connectionManager.disconnect();
        System.out.println("✅ 操作が完了しました");
    }
    
    // トランザクション付き更新
    public void executeUpdateWithTransaction(String url, String username, String password, 
                                            String query) {
        System.out.println("=== トランザクション付き更新 ===");
        connectionManager.connect(url, username, password);
        
        try {
            transactionManager.beginTransaction();
            queryExecutor.executeUpdate(query);
            transactionManager.commit();
            System.out.println("✅ 更新が完了しました");
        } catch (Exception e) {
            transactionManager.rollback();
            System.out.println("❌ エラーが発生しました。ロールバックしました");
        } finally {
            connectionManager.disconnect();
        }
    }
    
    // キャッシュ付きクエリ
    public void executeCachedQuery(String url, String username, String password, 
                                  String query, String cacheKey) {
        System.out.println("=== キャッシュ付きクエリ ===");
        
        // キャッシュを確認
        Object cached = cacheManager.get(cacheKey);
        if (cached != null) {
            System.out.println("✅ キャッシュから取得しました");
            return;
        }
        
        // キャッシュにない場合はデータベースから取得
        connectionManager.connect(url, username, password);
        queryExecutor.executeQuery(query);
        cacheManager.put(cacheKey, "クエリ結果");
        connectionManager.disconnect();
        System.out.println("✅ クエリを実行し、キャッシュに保存しました");
    }
}

// 使用例
public class DatabaseSystemExample {
    public static void main(String[] args) {
        DatabaseFacade db = new DatabaseFacade();
        
        // クエリを実行
        db.executeQuery(
            "jdbc:mysql://localhost:3306/mydb",
            "user",
            "password",
            "SELECT * FROM users"
        );
        
        System.out.println();
        
        // トランザクション付き更新
        db.executeUpdateWithTransaction(
            "jdbc:mysql://localhost:3306/mydb",
            "user",
            "password",
            "UPDATE users SET name = 'John' WHERE id = 1"
        );
        
        System.out.println();
        
        // キャッシュ付きクエリ
        db.executeCachedQuery(
            "jdbc:mysql://localhost:3306/mydb",
            "user",
            "password",
            "SELECT * FROM products",
            "products_cache"
        );
    }
}
```

---

## まとめ

### 学習のポイント

1. **ファサードパターンの目的**: 複雑なサブシステムに対して簡潔なインターフェースを提供
2. **基本的な構造**: Subsystem ClassesとFacadeの2つの主要コンポーネント
3. **複雑さの隠蔽**: クライアントがサブシステムの複雑な詳細を理解する必要がない
4. **疎結合**: クライアントとサブシステムの間の依存関係を削減

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 簡素化、疎結合、柔軟性、保守性 |
| **注意点** | 機能の制限、追加の抽象化層、過度な使用の可能性 |
| **適用場面** | 複雑なサブシステム、ライブラリの統合、APIの簡素化、レガシーシステムの統合など |

### ファサードパターンを使うべき場面

| 場面 | 説明 |
|------|------|
| **複雑なサブシステム** | 複数の相互依存するクラスやモジュールを持つシステム |
| **頻繁に使用される操作** | 複数のサブシステムを組み合わせた操作が頻繁に使用される場合 |
| **レガシーシステム** | 既存の複雑なシステムを新しいシステムに統合する場合 |
| **APIの簡素化** | 複雑なAPIを簡潔なインターフェースで提供する場合 |

### 他のパターンとの関係

- **Adapter**: ファサードは複数のクラスを統合するが、アダプターは1つのクラスを変換する
- **Mediator**: ファサードはサブシステムへの統一インターフェースを提供するが、メディエーターはオブジェクト間の通信を仲介する
- **Singleton**: ファサードはシングルトンとして実装されることが多い

### 注意点

1. **適切な使用場面**: 複雑なサブシステムがある場合に使用
2. **機能の制限**: ファサードが提供する機能が限定的になる可能性がある
3. **過度な使用**: シンプルなシステムでは過剰な設計になる可能性
4. **サブシステムへの直接アクセス**: 必要に応じて、サブシステムへの直接アクセスも許可する

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. 実際のプロジェクトでファサードパターンを適用してみる
3. Mediatorパターンを学習する（オブジェクト間の通信仲介）
4. Adapterパターンを学習する（インターフェース変換）

### 参考資料

- [cs-techblog.com - ファサードパターン](https://cs-techblog.com/technical/facade-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、ファサードパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
