# 責任の連鎖パターン（Chain of Responsibility Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [責任の連鎖パターンとは](#責任の連鎖パターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

責任の連鎖パターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**振る舞いに関するパターン（Behavioral Pattern）**に分類されます。

このパターンは、リクエストを処理する複数のオブジェクトをチェーン状に連結し、リクエストを順番に渡していくことで、どのオブジェクトが処理するかを動的に決定します。送信者と受信者を分離し、システムの柔軟性と拡張性を向上させます。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- 責任の連鎖パターンの目的と利点を理解する
- 基本的な責任の連鎖パターンの実装方法を理解する
- チェーンの構築方法とリクエストの処理フローを理解する
- 実際のプロジェクトで責任の連鎖パターンを適用できる

---

## 責任の連鎖パターンとは

### 定義

責任の連鎖パターンは、リクエストを処理する複数のオブジェクトをチェーン状に連結し、リクエストを順番に渡していくデザインパターンです。各オブジェクトは、自分で処理できる場合は処理し、できない場合は次のオブジェクトにリクエストを渡します。

### 主な特徴

1. **チェーン構造**: 複数のハンドラーをチェーン状に連結
2. **動的な処理決定**: 実行時にどのハンドラーが処理するかを決定
3. **送信者と受信者の分離**: 送信者はどのハンドラーが処理するかを知る必要がない
4. **柔軟な拡張性**: 新しいハンドラーを簡単に追加可能

### 使用される場面

責任の連鎖パターンは、以下のような場面で使用されます：

- **イベント処理**: GUIアプリケーションでのイベントハンドリング
- **ログ処理**: 異なるレベルのログを異なるハンドラーで処理
- **バリデーション**: 複数のバリデーションルールを順番に適用
- **例外処理**: 複数の例外ハンドラーで例外を処理
- **認証・認可**: 複数の認証方式を順番に試行
- **購入承認プロセス**: 複数の承認者による段階的な承認

### メリット

- **疎結合**: 送信者と受信者を分離し、結合度を低く保つ
- **柔軟性**: ハンドラーの順序や追加・削除が容易
- **単一責任の原則**: 各ハンドラーが単一の責任を持つ
- **拡張性**: 新しいハンドラーを追加する際に既存のコードを変更する必要がない

### デメリット

- **処理の保証がない**: リクエストが処理されない可能性がある
- **パフォーマンス**: チェーンを辿ることで、わずかなオーバーヘッドが発生
- **デバッグの困難さ**: どのハンドラーが処理したかを追跡するのが難しい場合がある
- **循環参照のリスク**: チェーンの構築を誤ると循環参照が発生する可能性がある

---

## 基本的な実装

### 実装のポイント

責任の連鎖パターンを実装するには、以下の要素が必要です：

1. **Handler（抽象ハンドラー）**: リクエストを処理するインターフェースまたは抽象クラス
2. **ConcreteHandler（具象ハンドラー）**: 実際の処理を行う具象クラス
3. **Client（クライアント）**: リクエストを送信するクラス
4. **Request（リクエスト）**: 処理されるリクエストオブジェクト

### パターンの構造

```
Client
  ↓
Handler (抽象クラス)
  ├─ next: Handler
  ├─ setNext(Handler)
  └─ handleRequest(Request)
      ↓
ConcreteHandler1
  └─ handleRequest(Request) [処理できる場合: 処理 / できない場合: next.handleRequest()]
      ↓
ConcreteHandler2
  └─ handleRequest(Request) [処理できる場合: 処理 / できない場合: next.handleRequest()]
      ↓
ConcreteHandler3
  └─ handleRequest(Request) [処理できる場合: 処理 / できない場合: next.handleRequest()]
```

### 基本的な実装例

```java
// 1. リクエストクラス
public class Request {
    private String type;
    private String content;
    
    public Request(String type, String content) {
        this.type = type;
        this.content = content;
    }
    
    public String getType() {
        return type;
    }
    
    public String getContent() {
        return content;
    }
}

// 2. Handler抽象クラス
public abstract class Handler {
    protected Handler next;
    
    public Handler setNext(Handler next) {
        this.next = next;
        return next; // メソッドチェーンを可能にする
    }
    
    public abstract void handleRequest(Request request);
    
    // 次のハンドラーにリクエストを渡す
    protected void passToNext(Request request) {
        if (next != null) {
            next.handleRequest(request);
        } else {
            System.out.println("No handler can process this request: " + request.getType());
        }
    }
}

// 3. 具象ハンドラークラス
public class ConcreteHandlerA extends Handler {
    @Override
    public void handleRequest(Request request) {
        if ("A".equals(request.getType())) {
            System.out.println("ConcreteHandlerA processed request: " + request.getContent());
        } else {
            System.out.println("ConcreteHandlerA cannot process, passing to next handler");
            passToNext(request);
        }
    }
}

public class ConcreteHandlerB extends Handler {
    @Override
    public void handleRequest(Request request) {
        if ("B".equals(request.getType())) {
            System.out.println("ConcreteHandlerB processed request: " + request.getContent());
        } else {
            System.out.println("ConcreteHandlerB cannot process, passing to next handler");
            passToNext(request);
        }
    }
}

public class ConcreteHandlerC extends Handler {
    @Override
    public void handleRequest(Request request) {
        if ("C".equals(request.getType())) {
            System.out.println("ConcreteHandlerC processed request: " + request.getContent());
        } else {
            System.out.println("ConcreteHandlerC cannot process, passing to next handler");
            passToNext(request);
        }
    }
}

// 4. クライアントコード
public class ChainOfResponsibilityDemo {
    public static void main(String[] args) {
        System.out.println("=== 責任の連鎖パターンのデモ ===\n");
        
        // ハンドラーのチェーンを構築
        Handler handlerA = new ConcreteHandlerA();
        Handler handlerB = new ConcreteHandlerB();
        Handler handlerC = new ConcreteHandlerC();
        
        // チェーンを連結
        handlerA.setNext(handlerB).setNext(handlerC);
        
        // リクエストを処理
        System.out.println("--- Request A ---");
        handlerA.handleRequest(new Request("A", "Request A content"));
        
        System.out.println("\n--- Request B ---");
        handlerA.handleRequest(new Request("B", "Request B content"));
        
        System.out.println("\n--- Request C ---");
        handlerA.handleRequest(new Request("C", "Request C content"));
        
        System.out.println("\n--- Request D (no handler) ---");
        handlerA.handleRequest(new Request("D", "Request D content"));
    }
}
```

### 使用例の出力

```
=== 責任の連鎖パターンのデモ ===

--- Request A ---
ConcreteHandlerA processed request: Request A content

--- Request B ---
ConcreteHandlerA cannot process, passing to next handler
ConcreteHandlerB processed request: Request B content

--- Request C ---
ConcreteHandlerA cannot process, passing to next handler
ConcreteHandlerB cannot process, passing to next handler
ConcreteHandlerC processed request: Request C content

--- Request D (no handler) ---
ConcreteHandlerA cannot process, passing to next handler
ConcreteHandlerB cannot process, passing to next handler
ConcreteHandlerC cannot process, passing to next handler
No handler can process this request: D
```

### 実装のポイント

1. **チェーンの構築**: `setNext()`メソッドでチェーンを構築
2. **処理の判定**: 各ハンドラーが自分で処理できるかどうかを判定
3. **次のハンドラーへの委譲**: 処理できない場合は次のハンドラーに委譲
4. **終端の処理**: チェーンの最後まで処理されない場合の処理

---

## 実装のバリエーション

### バリエーション1: インターフェースを使用

抽象クラスの代わりにインターフェースを使用する方法です。

```java
// Handlerインターフェース
public interface Handler {
    void setNext(Handler next);
    void handleRequest(Request request);
}

// 具象ハンドラークラス
public class ConcreteHandlerA implements Handler {
    private Handler next;
    
    @Override
    public void setNext(Handler next) {
        this.next = next;
    }
    
    @Override
    public void handleRequest(Request request) {
        if ("A".equals(request.getType())) {
            System.out.println("ConcreteHandlerA processed: " + request.getContent());
        } else if (next != null) {
            next.handleRequest(request);
        }
    }
}
```

### バリエーション2: デフォルト実装を持つ抽象クラス

抽象クラスにデフォルト実装を提供する方法です。

```java
public abstract class Handler {
    protected Handler next;
    
    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }
    
    // デフォルト実装：次のハンドラーに渡す
    public void handleRequest(Request request) {
        if (next != null) {
            next.handleRequest(request);
        } else {
            System.out.println("No handler can process: " + request.getType());
        }
    }
    
    // サブクラスでオーバーライド
    protected abstract boolean canHandle(Request request);
    protected abstract void doHandle(Request request);
}

public class ConcreteHandlerA extends Handler {
    @Override
    protected boolean canHandle(Request request) {
        return "A".equals(request.getType());
    }
    
    @Override
    protected void doHandle(Request request) {
        System.out.println("ConcreteHandlerA processed: " + request.getContent());
    }
    
    @Override
    public void handleRequest(Request request) {
        if (canHandle(request)) {
            doHandle(request);
        } else {
            super.handleRequest(request);
        }
    }
}
```

### バリエーション3: テンプレートメソッドパターンとの組み合わせ

テンプレートメソッドパターンを使用して、処理フローを統一する方法です。

```java
public abstract class Handler {
    protected Handler next;
    
    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }
    
    // テンプレートメソッド
    public final void handleRequest(Request request) {
        if (canHandle(request)) {
            doHandle(request);
        } else {
            passToNext(request);
        }
    }
    
    protected abstract boolean canHandle(Request request);
    protected abstract void doHandle(Request request);
    
    protected void passToNext(Request request) {
        if (next != null) {
            next.handleRequest(request);
        } else {
            System.out.println("No handler can process: " + request.getType());
        }
    }
}
```

---

## 実践例

### 例1: ログ処理システム

異なるレベルのログを異なるハンドラーで処理する例です。

```java
// ログレベル
public enum LogLevel {
    INFO, WARNING, ERROR, CRITICAL
}

// ログリクエスト
public class LogRequest {
    private LogLevel level;
    private String message;
    
    public LogRequest(LogLevel level, String message) {
        this.level = level;
        this.message = message;
    }
    
    public LogLevel getLevel() {
        return level;
    }
    
    public String getMessage() {
        return message;
    }
}

// ログハンドラー抽象クラス
public abstract class LogHandler {
    protected LogHandler next;
    protected LogLevel level;
    
    public LogHandler(LogLevel level) {
        this.level = level;
    }
    
    public LogHandler setNext(LogHandler next) {
        this.next = next;
        return next;
    }
    
    public void handleLog(LogRequest request) {
        if (canHandle(request)) {
            doHandle(request);
        } else if (next != null) {
            next.handleLog(request);
        } else {
            System.out.println("No handler for log level: " + request.getLevel());
        }
    }
    
    protected abstract boolean canHandle(LogRequest request);
    protected abstract void doHandle(LogRequest request);
}

// 具象ログハンドラー
public class InfoLogHandler extends LogHandler {
    public InfoLogHandler() {
        super(LogLevel.INFO);
    }
    
    @Override
    protected boolean canHandle(LogRequest request) {
        return request.getLevel() == LogLevel.INFO;
    }
    
    @Override
    protected void doHandle(LogRequest request) {
        System.out.println("[INFO] " + request.getMessage());
    }
}

public class WarningLogHandler extends LogHandler {
    public WarningLogHandler() {
        super(LogLevel.WARNING);
    }
    
    @Override
    protected boolean canHandle(LogRequest request) {
        return request.getLevel() == LogLevel.WARNING;
    }
    
    @Override
    protected void doHandle(LogRequest request) {
        System.out.println("[WARNING] " + request.getMessage());
    }
}

public class ErrorLogHandler extends LogHandler {
    public ErrorLogHandler() {
        super(LogLevel.ERROR);
    }
    
    @Override
    protected boolean canHandle(LogRequest request) {
        return request.getLevel() == LogLevel.ERROR;
    }
    
    @Override
    protected void doHandle(LogRequest request) {
        System.out.println("[ERROR] " + request.getMessage());
    }
}

public class CriticalLogHandler extends LogHandler {
    public CriticalLogHandler() {
        super(LogLevel.CRITICAL);
    }
    
    @Override
    protected boolean canHandle(LogRequest request) {
        return request.getLevel() == LogLevel.CRITICAL;
    }
    
    @Override
    protected void doHandle(LogRequest request) {
        System.out.println("[CRITICAL] " + request.getMessage());
        // クリティカルなログの場合は、管理者に通知するなどの追加処理
        System.out.println("Alerting administrator...");
    }
}

// 使用例
public class LogHandlerExample {
    public static void main(String[] args) {
        System.out.println("=== ログ処理システムのデモ ===\n");
        
        // ログハンドラーのチェーンを構築
        LogHandler infoHandler = new InfoLogHandler();
        LogHandler warningHandler = new WarningLogHandler();
        LogHandler errorHandler = new ErrorLogHandler();
        LogHandler criticalHandler = new CriticalLogHandler();
        
        // チェーンを連結
        infoHandler.setNext(warningHandler).setNext(errorHandler).setNext(criticalHandler);
        
        // 様々なレベルのログを処理
        infoHandler.handleLog(new LogRequest(LogLevel.INFO, "Application started"));
        infoHandler.handleLog(new LogRequest(LogLevel.WARNING, "Low memory warning"));
        infoHandler.handleLog(new LogRequest(LogLevel.ERROR, "Database connection failed"));
        infoHandler.handleLog(new LogRequest(LogLevel.CRITICAL, "System crash imminent"));
    }
}
```

### 例2: バリデーションシステム

複数のバリデーションルールを順番に適用する例です。

```java
// バリデーションリクエスト
public class ValidationRequest {
    private String username;
    private String password;
    private String email;
    
    public ValidationRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getEmail() {
        return email;
    }
}

// バリデーション結果
public class ValidationResult {
    private boolean valid;
    private String errorMessage;
    
    public ValidationResult(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}

// バリデーションハンドラー抽象クラス
public abstract class ValidationHandler {
    protected ValidationHandler next;
    
    public ValidationHandler setNext(ValidationHandler next) {
        this.next = next;
        return next;
    }
    
    public ValidationResult validate(ValidationRequest request) {
        ValidationResult result = doValidate(request);
        
        if (!result.isValid()) {
            return result; // バリデーション失敗時は即座に返す
        }
        
        // バリデーション成功時は次のハンドラーに渡す
        if (next != null) {
            return next.validate(request);
        }
        
        // すべてのバリデーションが成功
        return new ValidationResult(true, "All validations passed");
    }
    
    protected abstract ValidationResult doValidate(ValidationRequest request);
}

// 具象バリデーションハンドラー
public class UsernameValidationHandler extends ValidationHandler {
    @Override
    protected ValidationResult doValidate(ValidationRequest request) {
        String username = request.getUsername();
        
        if (username == null || username.isEmpty()) {
            return new ValidationResult(false, "Username cannot be empty");
        }
        
        if (username.length() < 3) {
            return new ValidationResult(false, "Username must be at least 3 characters");
        }
        
        if (username.length() > 20) {
            return new ValidationResult(false, "Username must be at most 20 characters");
        }
        
        return new ValidationResult(true, null);
    }
}

public class PasswordValidationHandler extends ValidationHandler {
    @Override
    protected ValidationResult doValidate(ValidationRequest request) {
        String password = request.getPassword();
        
        if (password == null || password.isEmpty()) {
            return new ValidationResult(false, "Password cannot be empty");
        }
        
        if (password.length() < 8) {
            return new ValidationResult(false, "Password must be at least 8 characters");
        }
        
        if (!password.matches(".*[A-Z].*")) {
            return new ValidationResult(false, "Password must contain at least one uppercase letter");
        }
        
        if (!password.matches(".*[a-z].*")) {
            return new ValidationResult(false, "Password must contain at least one lowercase letter");
        }
        
        if (!password.matches(".*[0-9].*")) {
            return new ValidationResult(false, "Password must contain at least one digit");
        }
        
        return new ValidationResult(true, null);
    }
}

public class EmailValidationHandler extends ValidationHandler {
    @Override
    protected ValidationResult doValidate(ValidationRequest request) {
        String email = request.getEmail();
        
        if (email == null || email.isEmpty()) {
            return new ValidationResult(false, "Email cannot be empty");
        }
        
        // 簡単なメールアドレス形式チェック
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return new ValidationResult(false, "Invalid email format");
        }
        
        return new ValidationResult(true, null);
    }
}

// 使用例
public class ValidationExample {
    public static void main(String[] args) {
        System.out.println("=== バリデーションシステムのデモ ===\n");
        
        // バリデーションハンドラーのチェーンを構築
        ValidationHandler usernameHandler = new UsernameValidationHandler();
        ValidationHandler passwordHandler = new PasswordValidationHandler();
        ValidationHandler emailHandler = new EmailValidationHandler();
        
        // チェーンを連結
        usernameHandler.setNext(passwordHandler).setNext(emailHandler);
        
        // バリデーションを実行
        System.out.println("--- Test 1: Valid data ---");
        ValidationRequest request1 = new ValidationRequest("john_doe", "Password123", "john@example.com");
        ValidationResult result1 = usernameHandler.validate(request1);
        System.out.println("Result: " + (result1.isValid() ? "Valid" : "Invalid: " + result1.getErrorMessage()));
        
        System.out.println("\n--- Test 2: Invalid username ---");
        ValidationRequest request2 = new ValidationRequest("ab", "Password123", "john@example.com");
        ValidationResult result2 = usernameHandler.validate(request2);
        System.out.println("Result: " + (result2.isValid() ? "Valid" : "Invalid: " + result2.getErrorMessage()));
        
        System.out.println("\n--- Test 3: Invalid password ---");
        ValidationRequest request3 = new ValidationRequest("john_doe", "weak", "john@example.com");
        ValidationResult result3 = usernameHandler.validate(request3);
        System.out.println("Result: " + (result3.isValid() ? "Valid" : "Invalid: " + result3.getErrorMessage()));
        
        System.out.println("\n--- Test 4: Invalid email ---");
        ValidationRequest request4 = new ValidationRequest("john_doe", "Password123", "invalid-email");
        ValidationResult result4 = usernameHandler.validate(request4);
        System.out.println("Result: " + (result4.isValid() ? "Valid" : "Invalid: " + result4.getErrorMessage()));
    }
}
```

### 例3: 購入承認プロセス

複数の承認者による段階的な承認プロセスの例です。

```java
// 購入リクエスト
public class PurchaseRequest {
    private String item;
    private double amount;
    private String requester;
    
    public PurchaseRequest(String item, double amount, String requester) {
        this.item = item;
        this.amount = amount;
        this.requester = requester;
    }
    
    public String getItem() {
        return item;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getRequester() {
        return requester;
    }
}

// 承認結果
public class ApprovalResult {
    private boolean approved;
    private String approver;
    private String reason;
    
    public ApprovalResult(boolean approved, String approver, String reason) {
        this.approved = approved;
        this.approver = approver;
        this.reason = reason;
    }
    
    public boolean isApproved() {
        return approved;
    }
    
    public String getApprover() {
        return approver;
    }
    
    public String getReason() {
        return reason;
    }
}

// 承認ハンドラー抽象クラス
public abstract class ApprovalHandler {
    protected ApprovalHandler next;
    protected String name;
    protected double approvalLimit;
    
    public ApprovalHandler(String name, double approvalLimit) {
        this.name = name;
        this.approvalLimit = approvalLimit;
    }
    
    public ApprovalHandler setNext(ApprovalHandler next) {
        this.next = next;
        return next;
    }
    
    public ApprovalResult approve(PurchaseRequest request) {
        if (canApprove(request)) {
            return doApprove(request);
        } else if (next != null) {
            System.out.println(name + " cannot approve. Passing to next approver.");
            return next.approve(request);
        } else {
            return new ApprovalResult(false, null, "No approver can handle this request");
        }
    }
    
    protected boolean canApprove(PurchaseRequest request) {
        return request.getAmount() <= approvalLimit;
    }
    
    protected ApprovalResult doApprove(PurchaseRequest request) {
        System.out.println(name + " approved purchase of " + request.getItem() + 
                         " for $" + request.getAmount());
        return new ApprovalResult(true, name, "Approved by " + name);
    }
}

// 具象承認ハンドラー
public class ManagerHandler extends ApprovalHandler {
    public ManagerHandler() {
        super("Manager", 1000.0);
    }
}

public class DirectorHandler extends ApprovalHandler {
    public DirectorHandler() {
        super("Director", 5000.0);
    }
}

public class VPHandler extends ApprovalHandler {
    public VPHandler() {
        super("Vice President", 10000.0);
    }
}

public class PresidentHandler extends ApprovalHandler {
    public PresidentHandler() {
        super("President", Double.MAX_VALUE);
    }
}

// 使用例
public class ApprovalExample {
    public static void main(String[] args) {
        System.out.println("=== 購入承認プロセスのデモ ===\n");
        
        // 承認ハンドラーのチェーンを構築
        ApprovalHandler manager = new ManagerHandler();
        ApprovalHandler director = new DirectorHandler();
        ApprovalHandler vp = new VPHandler();
        ApprovalHandler president = new PresidentHandler();
        
        // チェーンを連結
        manager.setNext(director).setNext(vp).setNext(president);
        
        // 様々な金額の購入リクエストを処理
        System.out.println("--- Request 1: $500 ---");
        PurchaseRequest request1 = new PurchaseRequest("Office Supplies", 500.0, "John");
        ApprovalResult result1 = manager.approve(request1);
        System.out.println("Result: " + (result1.isApproved() ? 
                         "Approved by " + result1.getApprover() : result1.getReason()));
        
        System.out.println("\n--- Request 2: $2500 ---");
        PurchaseRequest request2 = new PurchaseRequest("Equipment", 2500.0, "Jane");
        ApprovalResult result2 = manager.approve(request2);
        System.out.println("Result: " + (result2.isApproved() ? 
                         "Approved by " + result2.getApprover() : result2.getReason()));
        
        System.out.println("\n--- Request 3: $7500 ---");
        PurchaseRequest request3 = new PurchaseRequest("Software License", 7500.0, "Bob");
        ApprovalResult result3 = manager.approve(request3);
        System.out.println("Result: " + (result3.isApproved() ? 
                         "Approved by " + result3.getApprover() : result3.getReason()));
        
        System.out.println("\n--- Request 4: $15000 ---");
        PurchaseRequest request4 = new PurchaseRequest("Server Hardware", 15000.0, "Alice");
        ApprovalResult result4 = manager.approve(request4);
        System.out.println("Result: " + (result4.isApproved() ? 
                         "Approved by " + result4.getApprover() : result4.getReason()));
    }
}
```

### 例4: 認証システム

複数の認証方式を順番に試行する例です。

```java
// 認証リクエスト
public class AuthRequest {
    private String username;
    private String password;
    private String token;
    
    public AuthRequest(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getToken() {
        return token;
    }
}

// 認証結果
public class AuthResult {
    private boolean authenticated;
    private String authMethod;
    private String message;
    
    public AuthResult(boolean authenticated, String authMethod, String message) {
        this.authenticated = authenticated;
        this.authMethod = authMethod;
        this.message = message;
    }
    
    public boolean isAuthenticated() {
        return authenticated;
    }
    
    public String getAuthMethod() {
        return authMethod;
    }
    
    public String getMessage() {
        return message;
    }
}

// 認証ハンドラー抽象クラス
public abstract class AuthHandler {
    protected AuthHandler next;
    protected String authMethod;
    
    public AuthHandler(String authMethod) {
        this.authMethod = authMethod;
    }
    
    public AuthHandler setNext(AuthHandler next) {
        this.next = next;
        return next;
    }
    
    public AuthResult authenticate(AuthRequest request) {
        if (canAuthenticate(request)) {
            AuthResult result = doAuthenticate(request);
            if (result.isAuthenticated()) {
                return result;
            }
        }
        
        // 認証失敗時は次のハンドラーに渡す
        if (next != null) {
            return next.authenticate(request);
        }
        
        return new AuthResult(false, null, "Authentication failed: No valid authentication method");
    }
    
    protected abstract boolean canAuthenticate(AuthRequest request);
    protected abstract AuthResult doAuthenticate(AuthRequest request);
}

// 具象認証ハンドラー
public class TokenAuthHandler extends AuthHandler {
    public TokenAuthHandler() {
        super("Token");
    }
    
    @Override
    protected boolean canAuthenticate(AuthRequest request) {
        return request.getToken() != null && !request.getToken().isEmpty();
    }
    
    @Override
    protected AuthResult doAuthenticate(AuthRequest request) {
        // トークンの検証（簡易版）
        if ("valid_token_123".equals(request.getToken())) {
            return new AuthResult(true, authMethod, "Authenticated using token");
        }
        return new AuthResult(false, authMethod, "Invalid token");
    }
}

public class PasswordAuthHandler extends AuthHandler {
    public PasswordAuthHandler() {
        super("Password");
    }
    
    @Override
    protected boolean canAuthenticate(AuthRequest request) {
        return request.getUsername() != null && request.getPassword() != null;
    }
    
    @Override
    protected AuthResult doAuthenticate(AuthRequest request) {
        // パスワード認証（簡易版）
        if ("admin".equals(request.getUsername()) && "password123".equals(request.getPassword())) {
            return new AuthResult(true, authMethod, "Authenticated using password");
        }
        return new AuthResult(false, authMethod, "Invalid username or password");
    }
}

public class LDAPAuthHandler extends AuthHandler {
    public LDAPAuthHandler() {
        super("LDAP");
    }
    
    @Override
    protected boolean canAuthenticate(AuthRequest request) {
        // LDAP認証は常に試行可能（フォールバック）
        return true;
    }
    
    @Override
    protected AuthResult doAuthenticate(AuthRequest request) {
        // LDAP認証（簡易版）
        if (request.getUsername() != null && request.getUsername().startsWith("ldap_")) {
            return new AuthResult(true, authMethod, "Authenticated using LDAP");
        }
        return new AuthResult(false, authMethod, "LDAP authentication failed");
    }
}

// 使用例
public class AuthExample {
    public static void main(String[] args) {
        System.out.println("=== 認証システムのデモ ===\n");
        
        // 認証ハンドラーのチェーンを構築
        AuthHandler tokenHandler = new TokenAuthHandler();
        AuthHandler passwordHandler = new PasswordAuthHandler();
        AuthHandler ldapHandler = new LDAPAuthHandler();
        
        // チェーンを連結
        tokenHandler.setNext(passwordHandler).setNext(ldapHandler);
        
        // 様々な認証リクエストを処理
        System.out.println("--- Test 1: Token authentication ---");
        AuthRequest request1 = new AuthRequest(null, null, "valid_token_123");
        AuthResult result1 = tokenHandler.authenticate(request1);
        System.out.println("Result: " + (result1.isAuthenticated() ? 
                         "Authenticated via " + result1.getAuthMethod() : result1.getMessage()));
        
        System.out.println("\n--- Test 2: Password authentication ---");
        AuthRequest request2 = new AuthRequest("admin", "password123", null);
        AuthResult result2 = tokenHandler.authenticate(request2);
        System.out.println("Result: " + (result2.isAuthenticated() ? 
                         "Authenticated via " + result2.getAuthMethod() : result2.getMessage()));
        
        System.out.println("\n--- Test 3: LDAP authentication ---");
        AuthRequest request3 = new AuthRequest("ldap_user1", "password", null);
        AuthResult result3 = tokenHandler.authenticate(request3);
        System.out.println("Result: " + (result3.isAuthenticated() ? 
                         "Authenticated via " + result3.getAuthMethod() : result3.getMessage()));
        
        System.out.println("\n--- Test 4: Authentication failure ---");
        AuthRequest request4 = new AuthRequest("invalid", "wrong", null);
        AuthResult result4 = tokenHandler.authenticate(request4);
        System.out.println("Result: " + (result4.isAuthenticated() ? 
                         "Authenticated via " + result4.getAuthMethod() : result4.getMessage()));
    }
}
```

---

## まとめ

### 学習のポイント

1. **責任の連鎖パターンの目的**: リクエストを処理する複数のオブジェクトをチェーン状に連結し、動的に処理を決定
2. **基本構造**: Handler抽象クラス、ConcreteHandler具象クラス、チェーンの構築
3. **処理フロー**: 各ハンドラーが処理できるか判定し、できない場合は次のハンドラーに委譲
4. **柔軟性**: 新しいハンドラーを簡単に追加・削除可能

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 疎結合、柔軟性、単一責任の原則、拡張性 |
| **注意点** | 処理の保証がない、パフォーマンスオーバーヘッド、デバッグの困難さ、循環参照のリスク |
| **適用場面** | イベント処理、ログ処理、バリデーション、例外処理、認証・認可、承認プロセスなど |

### 実装のベストプラクティス

1. **チェーンの構築**: `setNext()`メソッドでメソッドチェーンを可能にする
2. **処理の判定**: 各ハンドラーが明確に処理できる条件を定義
3. **終端の処理**: チェーンの最後まで処理されない場合の適切な処理
4. **エラーハンドリング**: リクエストが処理されない場合のエラーハンドリング

### 他のパターンとの関係

- **Decorator**: 責任の連鎖パターンは複数のオブジェクトで処理を分担するが、デコレータは機能を追加する
- **Command**: 責任の連鎖パターンはリクエストを処理するが、コマンドパターンはリクエストをオブジェクト化する
- **Composite**: 責任の連鎖パターンは線形のチェーンを構築するが、コンポジットは木構造を構築する

### 注意点

1. **処理の保証**: リクエストが処理されない可能性があるため、適切なエラーハンドリングが必要
2. **パフォーマンス**: チェーンが長い場合、パフォーマンスに影響を与える可能性がある
3. **デバッグ**: どのハンドラーが処理したかを追跡するためのロギングを検討する
4. **循環参照**: チェーンの構築時に循環参照が発生しないように注意する

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. 実際のプロジェクトで責任の連鎖パターンを適用してみる
3. 他の振る舞いに関するパターン（Command、Observer、Strategyなど）を学習する
4. 責任の連鎖パターンと他のパターンの組み合わせを検討する

### 参考資料

- [cs-techblog.com - 責任の連鎖パターン](https://cs-techblog.com/technical/chain-of-responsibility-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、責任の連鎖パターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
