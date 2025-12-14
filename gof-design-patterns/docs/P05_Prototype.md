# プロトタイプパターン（Prototype Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [プロトタイプパターンとは](#プロトタイプパターンとは)
3. [基本的な実装](#基本的な実装)
4. [シャローコピーとディープコピー](#シャローコピーとディープコピー)
5. [実装のバリエーション](#実装のバリエーション)
6. [実践例](#実践例)
7. [まとめ](#まとめ)

---

## はじめに

プロトタイプパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**生成に関するパターン（Creational Pattern）**に分類されます。

このパターンは、既存のオブジェクトをコピーして新しいオブジェクトを作成する方法を提供し、オブジェクト生成のコストを削減します。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- プロトタイプパターンの目的と利点を理解する
- 基本的なプロトタイプパターンの実装方法を理解する
- シャローコピーとディープコピーの違いを理解する
- 実際のプロジェクトでプロトタイプパターンを適用できる

---

## プロトタイプパターンとは

### 定義

プロトタイプパターンは、既存のオブジェクト（プロトタイプ）をコピーして新しいオブジェクトを作成するデザインパターンです。このパターンにより、オブジェクト生成のコストを削減し、実行時に動的にオブジェクトを作成できます。

### 主な特徴

1. **コピーによる生成**: 既存のオブジェクトをコピーして新しいオブジェクトを作成
2. **コスト削減**: オブジェクト生成のコストを削減
3. **動的な生成**: 実行時に動的にオブジェクトを作成可能
4. **柔軟性**: クラスに依存せずにオブジェクトを生成

### 使用される場面

プロトタイプパターンは、以下のような場面で使用されます：

- **高コストなオブジェクト生成**: オブジェクトの生成コストが高い場合
- **データベース接続**: データベース接続オブジェクトの複製
- **設定オブジェクト**: 設定オブジェクトの複製
- **ゲーム開発**: ゲームオブジェクト（敵、アイテムなど）の複製
- **ドキュメント処理**: ドキュメントテンプレートの複製
- **キャッシュ**: キャッシュされたオブジェクトの複製

### メリット

- **コスト削減**: オブジェクト生成のコストを削減
- **柔軟性**: 実行時に動的にオブジェクトを作成可能
- **クラスへの依存の削減**: クラスに依存せずにオブジェクトを生成
- **パフォーマンス**: 複雑な初期化処理を回避できる

### デメリット

- **コピーの複雑さ**: 深い階層構造を持つオブジェクトのコピーが複雑
- **循環参照**: 循環参照を持つオブジェクトのコピーが困難
- **シャローコピーの問題**: 参照型フィールドの共有による予期しない動作

---

## 基本的な実装

### 実装のポイント

プロトタイプパターンを実装するには、以下の要素が必要です：

1. **プロトタイプインターフェース（Prototype）**: クローン可能なオブジェクトのインターフェース
2. **具象プロトタイプクラス（Concrete Prototype）**: プロトタイプインターフェースを実装
3. **クライアント（Client）**: プロトタイプをコピーして使用

### 基本的な実装例（Cloneableインターフェースを使用）

```java
// 1. プロトタイプクラス（Cloneableインターフェースを実装）
public class PrototypeExample implements Cloneable {
    private String data;
    private int number;
    
    public PrototypeExample(String data, int number) {
        this.data = data;
        this.number = number;
    }
    
    // クローンメソッド
    @Override
    public PrototypeExample clone() {
        try {
            return (PrototypeExample) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("クローンに失敗しました", e);
        }
    }
    
    // Getter/Setter
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    
    @Override
    public String toString() {
        return "PrototypeExample [data=" + data + ", number=" + number + "]";
    }
}

// 使用例
public class PrototypeBasicExample {
    public static void main(String[] args) {
        // プロトタイプを作成
        PrototypeExample prototype = new PrototypeExample("初期データ", 100);
        System.out.println("プロトタイプ: " + prototype);
        
        // プロトタイプをコピー
        PrototypeExample clone1 = prototype.clone();
        clone1.setData("変更されたデータ");
        clone1.setNumber(200);
        System.out.println("クローン1: " + clone1);
        System.out.println("プロトタイプ（変更後）: " + prototype);
        
        // 複数のクローンを作成
        PrototypeExample clone2 = prototype.clone();
        PrototypeExample clone3 = prototype.clone();
        System.out.println("クローン2: " + clone2);
        System.out.println("クローン3: " + clone3);
    }
}
```

### カスタムプロトタイプインターフェースを使用

```java
// 1. プロトタイプインターフェース
public interface Prototype {
    Prototype clone();
}

// 2. 具象プロトタイプクラス
public class Document implements Prototype {
    private String title;
    private String content;
    private String author;
    
    public Document(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
    
    // コピーコンストラクタ
    public Document(Document other) {
        this.title = other.title;
        this.content = other.content;
        this.author = other.author;
    }
    
    @Override
    public Document clone() {
        return new Document(this);
    }
    
    // Getter/Setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    @Override
    public String toString() {
        return "Document [title=" + title + ", content=" + content + 
               ", author=" + author + "]";
    }
}

// 使用例
public class DocumentExample {
    public static void main(String[] args) {
        // プロトタイプドキュメントを作成
        Document template = new Document("テンプレート", "これはテンプレートです", "管理者");
        System.out.println("テンプレート: " + template);
        
        // テンプレートから新しいドキュメントを作成
        Document doc1 = template.clone();
        doc1.setTitle("ドキュメント1");
        doc1.setContent("ドキュメント1の内容");
        System.out.println("ドキュメント1: " + doc1);
        
        Document doc2 = template.clone();
        doc2.setTitle("ドキュメント2");
        doc2.setContent("ドキュメント2の内容");
        System.out.println("ドキュメント2: " + doc2);
    }
}
```

### パターンの構造

```
Client
  ↓
Prototype (インターフェース)
  └─ clone()
      ↓
ConcretePrototype
  └─ clone() [実装]
```

---

## シャローコピーとディープコピー

### シャローコピー（Shallow Copy）

シャローコピーは、オブジェクトのフィールドをそのままコピーします。参照型フィールドは、元のオブジェクトと同じ参照を共有します。

```java
import java.util.ArrayList;
import java.util.List;

public class ShallowCopyExample implements Cloneable {
    private String name;
    private List<String> items;
    
    public ShallowCopyExample(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }
    
    public void addItem(String item) {
        items.add(item);
    }
    
    @Override
    public ShallowCopyExample clone() {
        try {
            // シャローコピー（参照が共有される）
            return (ShallowCopyExample) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("クローンに失敗しました", e);
        }
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getItems() { return items; }
    
    @Override
    public String toString() {
        return "ShallowCopyExample [name=" + name + ", items=" + items + "]";
    }
}

// 使用例
public class ShallowCopyDemo {
    public static void main(String[] args) {
        ShallowCopyExample original = new ShallowCopyExample("オリジナル");
        original.addItem("アイテム1");
        original.addItem("アイテム2");
        
        ShallowCopyExample clone = original.clone();
        clone.setName("クローン");
        clone.addItem("アイテム3");
        
        System.out.println("オリジナル: " + original);
        System.out.println("クローン: " + clone);
        // 注意: itemsリストは共有されているため、両方にアイテム3が追加される
    }
}
```

### ディープコピー（Deep Copy）

ディープコピーは、オブジェクトとその参照先のオブジェクトもすべてコピーします。元のオブジェクトと完全に独立したオブジェクトが作成されます。

```java
import java.util.ArrayList;
import java.util.List;

public class DeepCopyExample implements Cloneable {
    private String name;
    private List<String> items;
    
    public DeepCopyExample(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }
    
    public void addItem(String item) {
        items.add(item);
    }
    
    @Override
    public DeepCopyExample clone() {
        try {
            DeepCopyExample clone = (DeepCopyExample) super.clone();
            // ディープコピー（新しいリストを作成）
            clone.items = new ArrayList<>(this.items);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("クローンに失敗しました", e);
        }
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getItems() { return items; }
    
    @Override
    public String toString() {
        return "DeepCopyExample [name=" + name + ", items=" + items + "]";
    }
}

// 使用例
public class DeepCopyDemo {
    public static void main(String[] args) {
        DeepCopyExample original = new DeepCopyExample("オリジナル");
        original.addItem("アイテム1");
        original.addItem("アイテム2");
        
        DeepCopyExample clone = original.clone();
        clone.setName("クローン");
        clone.addItem("アイテム3");
        
        System.out.println("オリジナル: " + original);
        System.out.println("クローン: " + clone);
        // itemsリストは独立しているため、オリジナルにはアイテム3が追加されない
    }
}
```

### シャローコピーとディープコピーの比較

| 項目 | シャローコピー | ディープコピー |
|------|--------------|--------------|
| **参照型フィールド** | 共有される | 新しいオブジェクトが作成される |
| **独立性** | 低い（参照先の変更が影響する） | 高い（完全に独立） |
| **パフォーマンス** | 高速 | 低速（再帰的なコピーが必要） |
| **メモリ使用量** | 少ない | 多い |
| **実装の複雑さ** | シンプル | 複雑 |

---

## 実装のバリエーション

### バリエーション1: プロトタイプマネージャー

複数のプロトタイプを管理し、名前で取得できるようにする方法です。

```java
import java.util.HashMap;
import java.util.Map;

public class PrototypeManager {
    private Map<String, Prototype> prototypes;
    
    public PrototypeManager() {
        this.prototypes = new HashMap<>();
    }
    
    public void register(String name, Prototype prototype) {
        prototypes.put(name, prototype);
    }
    
    public Prototype create(String name) {
        Prototype prototype = prototypes.get(name);
        if (prototype == null) {
            throw new IllegalArgumentException("プロトタイプが見つかりません: " + name);
        }
        return prototype.clone();
    }
}

// 使用例
public class PrototypeManagerExample {
    public static void main(String[] args) {
        PrototypeManager manager = new PrototypeManager();
        
        // プロトタイプを登録
        manager.register("document1", new Document("テンプレート1", "内容1", "著者1"));
        manager.register("document2", new Document("テンプレート2", "内容2", "著者2"));
        
        // プロトタイプから作成
        Document doc1 = (Document) manager.create("document1");
        doc1.setTitle("新しいドキュメント1");
        System.out.println(doc1);
        
        Document doc2 = (Document) manager.create("document2");
        doc2.setTitle("新しいドキュメント2");
        System.out.println(doc2);
    }
}
```

### バリエーション2: シリアライゼーションを使用したディープコピー

シリアライゼーションを使用してディープコピーを実現する方法です。

```java
import java.io.*;

public class SerializablePrototype implements Serializable {
    private String name;
    private List<String> items;
    
    public SerializablePrototype(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }
    
    public void addItem(String item) {
        items.add(item);
    }
    
    public SerializablePrototype deepCopy() {
        try {
            // シリアライゼーション
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            
            // デシリアライゼーション
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            SerializablePrototype copy = (SerializablePrototype) ois.readObject();
            ois.close();
            
            return copy;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("ディープコピーに失敗しました", e);
        }
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getItems() { return items; }
    
    @Override
    public String toString() {
        return "SerializablePrototype [name=" + name + ", items=" + items + "]";
    }
}
```

### バリエーション3: コピーコンストラクタを使用

コピーコンストラクタを使用してプロトタイプパターンを実装する方法です。

```java
public class CopyConstructorExample {
    private String name;
    private int value;
    private List<String> items;
    
    public CopyConstructorExample(String name, int value) {
        this.name = name;
        this.value = value;
        this.items = new ArrayList<>();
    }
    
    // コピーコンストラクタ
    public CopyConstructorExample(CopyConstructorExample other) {
        this.name = other.name;
        this.value = other.value;
        // ディープコピー
        this.items = new ArrayList<>(other.items);
    }
    
    public void addItem(String item) {
        items.add(item);
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
    public List<String> getItems() { return items; }
    
    @Override
    public String toString() {
        return "CopyConstructorExample [name=" + name + ", value=" + value + 
               ", items=" + items + "]";
    }
}

// 使用例
public class CopyConstructorDemo {
    public static void main(String[] args) {
        CopyConstructorExample original = new CopyConstructorExample("オリジナル", 100);
        original.addItem("アイテム1");
        
        // コピーコンストラクタでコピー
        CopyConstructorExample copy = new CopyConstructorExample(original);
        copy.setName("コピー");
        copy.addItem("アイテム2");
        
        System.out.println("オリジナル: " + original);
        System.out.println("コピー: " + copy);
    }
}
```

---

## 実践例

### 例1: ゲームオブジェクトの複製

ゲーム内の敵やアイテムなどのオブジェクトを複製する例です。

```java
import java.util.ArrayList;
import java.util.List;

public class GameObject implements Cloneable {
    private String name;
    private int health;
    private int attack;
    private List<String> abilities;
    
    public GameObject(String name, int health, int attack) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.abilities = new ArrayList<>();
    }
    
    public void addAbility(String ability) {
        abilities.add(ability);
    }
    
    @Override
    public GameObject clone() {
        try {
            GameObject clone = (GameObject) super.clone();
            // ディープコピー
            clone.abilities = new ArrayList<>(this.abilities);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("クローンに失敗しました", e);
        }
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }
    public List<String> getAbilities() { return abilities; }
    
    @Override
    public String toString() {
        return "GameObject [name=" + name + ", health=" + health + 
               ", attack=" + attack + ", abilities=" + abilities + "]";
    }
}

// プロトタイプマネージャー
public class GameObjectManager {
    private Map<String, GameObject> prototypes;
    
    public GameObjectManager() {
        this.prototypes = new HashMap<>();
    }
    
    public void register(String name, GameObject prototype) {
        prototypes.put(name, prototype);
    }
    
    public GameObject spawn(String name) {
        GameObject prototype = prototypes.get(name);
        if (prototype == null) {
            throw new IllegalArgumentException("ゲームオブジェクトが見つかりません: " + name);
        }
        return prototype.clone();
    }
}

// 使用例
public class GameExample {
    public static void main(String[] args) {
        GameObjectManager manager = new GameObjectManager();
        
        // 敵のプロトタイプを登録
        GameObject enemyPrototype = new GameObject("ゴブリン", 50, 10);
        enemyPrototype.addAbility("攻撃");
        enemyPrototype.addAbility("防御");
        manager.register("goblin", enemyPrototype);
        
        // アイテムのプロトタイプを登録
        GameObject itemPrototype = new GameObject("剣", 0, 20);
        itemPrototype.addAbility("切る");
        manager.register("sword", itemPrototype);
        
        // プロトタイプから複数のインスタンスを作成
        GameObject enemy1 = manager.spawn("goblin");
        enemy1.setName("ゴブリン1");
        System.out.println("敵1: " + enemy1);
        
        GameObject enemy2 = manager.spawn("goblin");
        enemy2.setName("ゴブリン2");
        System.out.println("敵2: " + enemy2);
        
        GameObject item1 = manager.spawn("sword");
        item1.setName("鋼の剣");
        System.out.println("アイテム1: " + item1);
    }
}
```

### 例2: 設定オブジェクトの複製

アプリケーション設定オブジェクトを複製する例です。

```java
import java.util.HashMap;
import java.util.Map;

public class Configuration implements Cloneable {
    private String appName;
    private String version;
    private Map<String, String> settings;
    
    public Configuration(String appName, String version) {
        this.appName = appName;
        this.version = version;
        this.settings = new HashMap<>();
    }
    
    public void setSetting(String key, String value) {
        settings.put(key, value);
    }
    
    public String getSetting(String key) {
        return settings.get(key);
    }
    
    @Override
    public Configuration clone() {
        try {
            Configuration clone = (Configuration) super.clone();
            // ディープコピー
            clone.settings = new HashMap<>(this.settings);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("クローンに失敗しました", e);
        }
    }
    
    public String getAppName() { return appName; }
    public void setAppName(String appName) { this.appName = appName; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public Map<String, String> getSettings() { return settings; }
    
    @Override
    public String toString() {
        return "Configuration [appName=" + appName + ", version=" + version + 
               ", settings=" + settings + "]";
    }
}

// 使用例
public class ConfigurationExample {
    public static void main(String[] args) {
        // デフォルト設定を作成
        Configuration defaultConfig = new Configuration("MyApp", "1.0.0");
        defaultConfig.setSetting("database.url", "localhost");
        defaultConfig.setSetting("database.port", "3306");
        defaultConfig.setSetting("cache.enabled", "true");
        
        System.out.println("デフォルト設定: " + defaultConfig);
        
        // 開発環境用の設定を複製
        Configuration devConfig = defaultConfig.clone();
        devConfig.setAppName("MyApp-Dev");
        devConfig.setSetting("database.url", "dev-server");
        devConfig.setSetting("cache.enabled", "false");
        System.out.println("開発環境設定: " + devConfig);
        
        // 本番環境用の設定を複製
        Configuration prodConfig = defaultConfig.clone();
        prodConfig.setAppName("MyApp-Prod");
        prodConfig.setSetting("database.url", "prod-server");
        System.out.println("本番環境設定: " + prodConfig);
    }
}
```

### 例3: ドキュメントテンプレートの複製

ドキュメントテンプレートから新しいドキュメントを作成する例です。

```java
import java.util.ArrayList;
import java.util.List;

public class DocumentTemplate implements Cloneable {
    private String title;
    private String header;
    private String footer;
    private List<String> sections;
    
    public DocumentTemplate(String title) {
        this.title = title;
        this.header = "標準ヘッダー";
        this.footer = "標準フッター";
        this.sections = new ArrayList<>();
    }
    
    public void addSection(String section) {
        sections.add(section);
    }
    
    @Override
    public DocumentTemplate clone() {
        try {
            DocumentTemplate clone = (DocumentTemplate) super.clone();
            // ディープコピー
            clone.sections = new ArrayList<>(this.sections);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("クローンに失敗しました", e);
        }
    }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getHeader() { return header; }
    public void setHeader(String header) { this.header = header; }
    public String getFooter() { return footer; }
    public void setFooter(String footer) { this.footer = footer; }
    public List<String> getSections() { return sections; }
    
    @Override
    public String toString() {
        return "DocumentTemplate [title=" + title + ", header=" + header + 
               ", footer=" + footer + ", sections=" + sections + "]";
    }
}

// 使用例
public class DocumentTemplateExample {
    public static void main(String[] args) {
        // テンプレートを作成
        DocumentTemplate template = new DocumentTemplate("レポートテンプレート");
        template.addSection("概要");
        template.addSection("詳細");
        template.addSection("結論");
        
        System.out.println("テンプレート: " + template);
        
        // テンプレートから新しいドキュメントを作成
        DocumentTemplate report1 = template.clone();
        report1.setTitle("月次レポート - 2024年1月");
        report1.setHeader("2024年1月 月次レポート");
        System.out.println("レポート1: " + report1);
        
        DocumentTemplate report2 = template.clone();
        report2.setTitle("月次レポート - 2024年2月");
        report2.setHeader("2024年2月 月次レポート");
        report2.addSection("追加セクション");
        System.out.println("レポート2: " + report2);
    }
}
```

### 例4: データベース接続設定の複製

データベース接続設定を複製する例です。

```java
import java.util.Properties;

public class DatabaseConfig implements Cloneable {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private Properties properties;
    
    public DatabaseConfig(String host, int port, String database) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.properties = new Properties();
    }
    
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
    
    @Override
    public DatabaseConfig clone() {
        try {
            DatabaseConfig clone = (DatabaseConfig) super.clone();
            // ディープコピー
            clone.properties = new Properties();
            clone.properties.putAll(this.properties);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("クローンに失敗しました", e);
        }
    }
    
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    public String getDatabase() { return database; }
    public void setDatabase(String database) { this.database = database; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Properties getProperties() { return properties; }
    
    @Override
    public String toString() {
        return "DatabaseConfig [host=" + host + ", port=" + port + 
               ", database=" + database + ", username=" + username + "]";
    }
}

// 使用例
public class DatabaseConfigExample {
    public static void main(String[] args) {
        // デフォルト設定を作成
        DatabaseConfig defaultConfig = new DatabaseConfig("localhost", 3306, "mydb");
        defaultConfig.setUsername("admin");
        defaultConfig.setPassword("password");
        defaultConfig.setProperty("useSSL", "false");
        defaultConfig.setProperty("autoReconnect", "true");
        
        System.out.println("デフォルト設定: " + defaultConfig);
        
        // 読み取り専用接続の設定を複製
        DatabaseConfig readOnlyConfig = defaultConfig.clone();
        readOnlyConfig.setUsername("readonly");
        readOnlyConfig.setPassword("readonly123");
        readOnlyConfig.setProperty("readOnly", "true");
        System.out.println("読み取り専用設定: " + readOnlyConfig);
        
        // 書き込み専用接続の設定を複製
        DatabaseConfig writeOnlyConfig = defaultConfig.clone();
        writeOnlyConfig.setUsername("writeonly");
        writeOnlyConfig.setPassword("writeonly123");
        writeOnlyConfig.setProperty("readOnly", "false");
        System.out.println("書き込み専用設定: " + writeOnlyConfig);
    }
}
```

---

## まとめ

### 学習のポイント

1. **プロトタイプパターンの目的**: 既存のオブジェクトをコピーして新しいオブジェクトを作成
2. **基本的な実装**: Cloneableインターフェースとclone()メソッド、またはカスタムプロトタイプインターフェース
3. **シャローコピーとディープコピー**: 参照型フィールドの扱いが重要
4. **コスト削減**: オブジェクト生成のコストを削減できる

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | コスト削減、柔軟性、クラスへの依存の削減、パフォーマンス向上 |
| **注意点** | コピーの複雑さ、循環参照、シャローコピーの問題 |
| **適用場面** | 高コストなオブジェクト生成、ゲーム開発、設定オブジェクト、テンプレートなど |

### コピーの種類

| 種類 | 説明 | 使用場面 |
|------|------|---------|
| **シャローコピー** | 参照を共有 | 参照型フィールドが不変な場合 |
| **ディープコピー** | 完全に独立 | 参照型フィールドが可変な場合 |

### 注意点

1. **シャローコピーの問題**: 参照型フィールドが共有されるため、予期しない動作が発生する可能性がある
2. **ディープコピーの実装**: 深い階層構造を持つオブジェクトのディープコピーは複雑
3. **循環参照**: 循環参照を持つオブジェクトのコピーには注意が必要
4. **パフォーマンス**: ディープコピーはパフォーマンスに影響を与える可能性がある

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. シャローコピーとディープコピーの違いを理解する
3. 実際のプロジェクトでプロトタイプパターンを適用してみる
4. 他のGoFデザインパターンを学習する

### 参考資料

- [cs-techblog.com - プロトタイプパターン](https://cs-techblog.com/technical/prototype-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「Effective Java」（Joshua Bloch著）

---

**注意**: この学習プランは、プロトタイプパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
