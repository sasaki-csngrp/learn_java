# シングルトンパターン（Singleton Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [シングルトンパターンとは](#シングルトンパターンとは)
3. [基本的な実装](#基本的な実装)
4. [スレッドセーフな実装](#スレッドセーフな実装)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

シングルトンパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**生成に関するパターン（Creational Pattern）**に分類されます。

このパターンは、クラスのインスタンスがアプリケーション全体で1つだけ存在することを保証し、そのインスタンスへのグローバルなアクセスポイントを提供します。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- シングルトンパターンの目的と利点を理解する
- 基本的なシングルトンの実装方法を理解する
- マルチスレッド環境でのスレッドセーフな実装方法を理解する
- 実際のプロジェクトでシングルトンパターンを適用できる

---

## シングルトンパターンとは

### 定義

シングルトンパターンは、クラスのインスタンスが1つだけ存在することを保証し、そのインスタンスへのグローバルなアクセスポイントを提供するデザインパターンです。

### 主な特徴

1. **単一インスタンス**: アプリケーション全体で1つのインスタンスのみが存在
2. **グローバルアクセス**: どこからでもアクセス可能なアクセスポイント
3. **制御されたインスタンス化**: 外部からの直接的なインスタンス化を防止

### 使用される場面

シングルトンパターンは、以下のような場面で使用されます：

- **データベース接続管理**: データベースへの接続を1つに制限
- **ログ管理**: アプリケーション全体で1つのロガーを使用
- **設定管理**: アプリケーション設定を1つのオブジェクトで管理
- **キャッシュ管理**: キャッシュを1つのオブジェクトで管理
- **スレッドプール**: スレッドプールを1つに制限

### メリット

- **リソースの節約**: インスタンスを1つに制限することで、メモリ使用量を削減
- **状態の一元管理**: グローバルな状態を1つのオブジェクトで管理
- **アクセスの簡便性**: どこからでも簡単にアクセス可能

### デメリット

- **テストの困難さ**: グローバルな状態により、単体テストが難しくなる
- **マルチスレッド環境での複雑さ**: スレッドセーフな実装が必要
- **依存関係の隠蔽**: クラス間の依存関係が見えにくくなる

---

## 基本的な実装

### 実装のポイント

シングルトンパターンを実装するには、以下の3つの要素が必要です：

1. **privateコンストラクタ**: 外部からの直接的なインスタンス化を防止
2. **staticインスタンス変数**: 唯一のインスタンスを保持
3. **public staticメソッド**: インスタンスへのアクセスを提供

### 基本的な実装例

```java
public class Singleton {
    // 唯一のインスタンスを保持するstatic変数
    private static Singleton instance;
    
    // privateコンストラクタで外部からのインスタンス化を防止
    private Singleton() {
        // 初期化処理
    }
    
    // インスタンスへのアクセスを提供するpublic staticメソッド
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
    
    // その他のメソッド
    public void doSomething() {
        System.out.println("シングルトンのメソッドが呼ばれました");
    }
}
```

### 使用例

```java
public class SingletonExample {
    public static void main(String[] args) {
        // 通常のnewはできない（コンパイルエラー）
        // Singleton s1 = new Singleton(); // エラー！
        
        // getInstance()メソッドでインスタンスを取得
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        
        // 同じインスタンスか確認
        System.out.println("s1 == s2: " + (s1 == s2)); // true
        System.out.println("s1.equals(s2): " + s1.equals(s2)); // true
        
        s1.doSomething();
    }
}
```

### 問題点

この基本的な実装には、**マルチスレッド環境では問題**があります。複数のスレッドが同時に`getInstance()`を呼び出すと、複数のインスタンスが作成される可能性があります。

---

## スレッドセーフな実装

### 方法1: synchronizedメソッドを使用

最もシンプルな方法は、`getInstance()`メソッド全体を`synchronized`で修飾することです。

```java
public class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;
    
    private ThreadSafeSingleton() {
        // 初期化処理
    }
    
    // synchronizedメソッドでスレッドセーフに
    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
    
    public void doSomething() {
        System.out.println("スレッドセーフなシングルトン");
    }
}
```

**メリット**: 実装が簡単
**デメリット**: パフォーマンスが低下する可能性（毎回同期処理が発生）

### 方法2: ダブルチェックロッキング（Double-Checked Locking）

パフォーマンスを向上させるために、ダブルチェックロッキングパターンを使用します。

```java
public class DoubleCheckedLockingSingleton {
    // volatileキーワードで可視性を保証
    private static volatile DoubleCheckedLockingSingleton instance;
    
    private DoubleCheckedLockingSingleton() {
        // 初期化処理
    }
    
    public static DoubleCheckedLockingSingleton getInstance() {
        // 最初のチェック（同期なし）
        if (instance == null) {
            // 同期ブロックで保護
            synchronized (DoubleCheckedLockingSingleton.class) {
                // 2回目のチェック（同期内）
                if (instance == null) {
                    instance = new DoubleCheckedLockingSingleton();
                }
            }
        }
        return instance;
    }
    
    public void doSomething() {
        System.out.println("ダブルチェックロッキングのシングルトン");
    }
}
```

**ポイント**:
- `volatile`キーワードで、メモリの可視性を保証
- 最初のチェックで不要な同期処理を回避
- 2回目のチェックで、複数のスレッドが同時にインスタンスを作成することを防止

### 方法3: 初期化時にインスタンスを作成（Eager Initialization）

クラスがロードされた時点でインスタンスを作成する方法です。

```java
public class EagerSingleton {
    // クラスロード時にインスタンスを作成
    private static final EagerSingleton instance = new EagerSingleton();
    
    private EagerSingleton() {
        // 初期化処理
    }
    
    public static EagerSingleton getInstance() {
        return instance;
    }
    
    public void doSomething() {
        System.out.println("Eager初期化のシングルトン");
    }
}
```

**メリット**: スレッドセーフで、実装が簡単
**デメリット**: インスタンスが使用されなくても作成される

### 方法4: 内部クラスを使用（Lazy Initialization Holder）

インスタンスが必要になった時点で作成し、かつスレッドセーフな方法です。

```java
public class LazyInitializationHolderSingleton {
    private LazyInitializationHolderSingleton() {
        // 初期化処理
    }
    
    // 内部クラスでインスタンスを保持
    private static class SingletonHolder {
        private static final LazyInitializationHolderSingleton instance = 
            new LazyInitializationHolderSingleton();
    }
    
    public static LazyInitializationHolderSingleton getInstance() {
        // 内部クラスが初めてアクセスされた時にインスタンスが作成される
        return SingletonHolder.instance;
    }
    
    public void doSomething() {
        System.out.println("Lazy初期化ホルダーのシングルトン");
    }
}
```

**メリット**: 
- スレッドセーフ
- 遅延初期化（Lazy Initialization）
- パフォーマンスが良い

**推奨**: この方法が最も推奨される実装方法です。

---

## 実践例

### 例1: ログ管理クラス

アプリケーション全体で1つのロガーを使用する例です。

```java
public class Logger {
    private static Logger instance;
    private StringBuilder logBuffer;
    
    private Logger() {
        this.logBuffer = new StringBuilder();
    }
    
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    public void log(String message) {
        String timestamp = java.time.LocalDateTime.now().toString();
        logBuffer.append("[").append(timestamp).append("] ")
                 .append(message).append("\n");
    }
    
    public void printLogs() {
        System.out.println(logBuffer.toString());
    }
    
    public void clearLogs() {
        logBuffer.setLength(0);
    }
}

// 使用例
public class LoggerExample {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        
        logger1.log("最初のログメッセージ");
        logger2.log("2番目のログメッセージ");
        
        // 同じインスタンスなので、両方のログが記録されている
        logger1.printLogs();
    }
}
```

### 例2: データベース接続管理

データベース接続を1つに制限する例です。

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/mydb";
    private String username = "user";
    private String password = "password";
    
    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

### 例3: 設定管理クラス

アプリケーション設定を1つのオブジェクトで管理する例です。

```java
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private static Configuration instance;
    private Map<String, String> config;
    
    private Configuration() {
        this.config = new HashMap<>();
        // デフォルト設定を読み込む
        loadDefaultConfig();
    }
    
    public static synchronized Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }
    
    private void loadDefaultConfig() {
        config.put("app.name", "My Application");
        config.put("app.version", "1.0.0");
        config.put("database.url", "jdbc:mysql://localhost:3306/mydb");
    }
    
    public String get(String key) {
        return config.get(key);
    }
    
    public void set(String key, String value) {
        config.put(key, value);
    }
    
    public void printAll() {
        config.forEach((key, value) -> 
            System.out.println(key + " = " + value));
    }
}

// 使用例
public class ConfigurationExample {
    public static void main(String[] args) {
        Configuration config1 = Configuration.getInstance();
        Configuration config2 = Configuration.getInstance();
        
        System.out.println("アプリ名: " + config1.get("app.name"));
        config2.set("app.name", "Updated Application");
        System.out.println("更新後のアプリ名: " + config1.get("app.name"));
        
        // 同じインスタンスなので、config1でも更新が反映される
    }
}
```

---

## まとめ

### 学習のポイント

1. **シングルトンパターンの目的**: インスタンスを1つに制限し、グローバルなアクセスを提供
2. **基本的な実装**: privateコンストラクタ、static変数、public staticメソッド
3. **スレッドセーフ**: マルチスレッド環境では適切な実装が必要
4. **実装方法の選択**: 用途に応じて最適な実装方法を選択

### 実装方法の比較

| 実装方法 | スレッドセーフ | 遅延初期化 | パフォーマンス | 推奨度 |
|---------|--------------|----------|--------------|--------|
| 基本的な実装 | ❌ | ✅ | ⭐⭐⭐ | ⭐ |
| synchronizedメソッド | ✅ | ✅ | ⭐⭐ | ⭐⭐ |
| ダブルチェックロッキング | ✅ | ✅ | ⭐⭐⭐ | ⭐⭐⭐ |
| Eager初期化 | ✅ | ❌ | ⭐⭐⭐ | ⭐⭐ |
| 内部クラス（推奨） | ✅ | ✅ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

### 注意点

1. **過度な使用を避ける**: シングルトンは便利ですが、過度に使用するとテストが困難になる
2. **スレッドセーフ性**: マルチスレッド環境では必ずスレッドセーフな実装を使用
3. **依存関係の管理**: グローバルな状態により、依存関係が見えにくくなることに注意

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. マルチスレッド環境での動作を確認する
3. 実際のプロジェクトでシングルトンパターンを適用してみる
4. 他の生成に関するパターン（Factory Method、Builderなど）を学習する

### 参考資料

- [cs-techblog.com - シングルトンパターン](https://cs-techblog.com/technical/singleton-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「Effective Java」（Joshua Bloch著）

---

**注意**: この学習プランは、シングルトンパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
