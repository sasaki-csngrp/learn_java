# ビルダーパターン（Builder Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [ビルダーパターンとは](#ビルダーパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

ビルダーパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**生成に関するパターン（Creational Pattern）**に分類されます。

このパターンは、複雑なオブジェクトの構築過程を表現から分離し、同じ構築過程で異なる表現を作成できるようにします。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- ビルダーパターンの目的と利点を理解する
- 基本的なビルダーパターンの実装方法を理解する
- パターンのバリエーション（Fluent Builder、DirectorなしBuilderなど）を理解する
- 実際のプロジェクトでビルダーパターンを適用できる

---

## ビルダーパターンとは

### 定義

ビルダーパターンは、複雑なオブジェクトの構築過程を表現から分離するデザインパターンです。このパターンにより、同じ構築過程で異なる表現のオブジェクトを作成できます。

### 主な特徴

1. **構築過程の分離**: オブジェクトの構築過程を表現から分離
2. **段階的な構築**: オブジェクトを段階的に構築できる
3. **柔軟性**: 同じ構築過程で異なる表現を作成可能
4. **可読性**: コードの可読性と保守性が向上

### 使用される場面

ビルダーパターンは、以下のような場面で使用されます：

- **複雑なオブジェクトの構築**: 多くのパラメータを持つオブジェクトの構築
- **オプショナルパラメータ**: 多くのオプショナルパラメータを持つオブジェクトの構築
- **段階的な構築**: オブジェクトを段階的に構築する必要がある場合
- **不変オブジェクト**: 不変（immutable）オブジェクトの構築
- **SQLクエリ構築**: 動的なSQLクエリの構築
- **設定オブジェクト**: 複雑な設定オブジェクトの構築

### メリット

- **可読性の向上**: メソッドチェーンにより、コードが読みやすくなる
- **柔軟性**: オプショナルパラメータを簡単に扱える
- **不変性の保証**: 不変オブジェクトを構築できる
- **検証の容易さ**: 構築時にパラメータの検証が可能
- **段階的な構築**: オブジェクトを段階的に構築できる

### デメリット

- **クラス数の増加**: Builderクラスが必要になる
- **コードの複雑性**: シンプルなオブジェクトには過剰な設計になる可能性
- **メモリオーバーヘッド**: Builderオブジェクトの作成によるメモリ使用量の増加

---

## 基本的な実装

### 実装のポイント

ビルダーパターンを実装するには、以下の要素が必要です：

1. **製品クラス（Product）**: 構築される複雑なオブジェクト
2. **ビルダーインターフェース（Builder）**: 製品の各部分を構築するメソッドを定義
3. **具象ビルダークラス（Concrete Builder）**: ビルダーインターフェースを実装
4. **ディレクター（Director）**: 構築過程を管理（オプション）

### 基本的な実装例（Directorを使用）

```java
// 1. 製品クラス（Product）
public class Car {
    private String engine;
    private String wheels;
    private String interior;
    private String color;
    
    public Car(String engine, String wheels, String interior, String color) {
        this.engine = engine;
        this.wheels = wheels;
        this.interior = interior;
        this.color = color;
    }
    
    @Override
    public String toString() {
        return "Car [Engine=" + engine + ", Wheels=" + wheels + 
               ", Interior=" + interior + ", Color=" + color + "]";
    }
    
    // Getterメソッド
    public String getEngine() { return engine; }
    public String getWheels() { return wheels; }
    public String getInterior() { return interior; }
    public String getColor() { return color; }
}

// 2. ビルダーインターフェース（Builder）
public interface CarBuilder {
    void buildEngine();
    void buildWheels();
    void buildInterior();
    void buildColor();
    Car getCar();
}

// 3. 具象ビルダークラス（Concrete Builder）
public class SportsCarBuilder implements CarBuilder {
    private String engine;
    private String wheels;
    private String interior;
    private String color;
    
    @Override
    public void buildEngine() {
        this.engine = "V8エンジン";
    }
    
    @Override
    public void buildWheels() {
        this.wheels = "スポーツホイール";
    }
    
    @Override
    public void buildInterior() {
        this.interior = "レザーシート";
    }
    
    @Override
    public void buildColor() {
        this.color = "レッド";
    }
    
    @Override
    public Car getCar() {
        return new Car(engine, wheels, interior, color);
    }
}

public class FamilyCarBuilder implements CarBuilder {
    private String engine;
    private String wheels;
    private String interior;
    private String color;
    
    @Override
    public void buildEngine() {
        this.engine = "4気筒エンジン";
    }
    
    @Override
    public void buildWheels() {
        this.wheels = "標準ホイール";
    }
    
    @Override
    public void buildInterior() {
        this.interior = "ファブリックシート";
    }
    
    @Override
    public void buildColor() {
        this.color = "シルバー";
    }
    
    @Override
    public Car getCar() {
        return new Car(engine, wheels, interior, color);
    }
}

// 4. ディレクター（Director）
public class CarDirector {
    private CarBuilder builder;
    
    public CarDirector(CarBuilder builder) {
        this.builder = builder;
    }
    
    public void constructCar() {
        builder.buildEngine();
        builder.buildWheels();
        builder.buildInterior();
        builder.buildColor();
    }
}

// 使用例
public class BuilderExample {
    public static void main(String[] args) {
        // スポーツカーの構築
        CarBuilder sportsBuilder = new SportsCarBuilder();
        CarDirector director = new CarDirector(sportsBuilder);
        director.constructCar();
        Car sportsCar = sportsBuilder.getCar();
        System.out.println("スポーツカー: " + sportsCar);
        
        // ファミリーカーの構築
        CarBuilder familyBuilder = new FamilyCarBuilder();
        director = new CarDirector(familyBuilder);
        director.constructCar();
        Car familyCar = familyBuilder.getCar();
        System.out.println("ファミリーカー: " + familyCar);
    }
}
```

### パターンの構造

```
Client
  ↓
Director (オプション)
  ↓
Builder (インターフェース)
  ├─ buildPartA()
  ├─ buildPartB()
  └─ getProduct()
      ↓
ConcreteBuilder
  ├─ buildPartA() [実装]
  ├─ buildPartB() [実装]
  └─ getProduct() [実装]
      ↓
Product
```

---

## 実装のバリエーション

### バリエーション1: Fluent Builder（メソッドチェーン）

Directorを使用せず、メソッドチェーンで構築する方法です。最も一般的な実装方法です。

```java
// 製品クラス
public class Computer {
    private String cpu;
    private String memory;
    private String storage;
    private String graphicsCard;
    private boolean hasBluetooth;
    private boolean hasWifi;
    
    private Computer(ComputerBuilder builder) {
        this.cpu = builder.cpu;
        this.memory = builder.memory;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.hasBluetooth = builder.hasBluetooth;
        this.hasWifi = builder.hasWifi;
    }
    
    @Override
    public String toString() {
        return "Computer [CPU=" + cpu + ", Memory=" + memory + 
               ", Storage=" + storage + ", GraphicsCard=" + graphicsCard +
               ", Bluetooth=" + hasBluetooth + ", WiFi=" + hasWifi + "]";
    }
    
    // Builderクラス（内部クラス）
    public static class ComputerBuilder {
        // 必須パラメータ
        private String cpu;
        private String memory;
        private String storage;
        
        // オプショナルパラメータ
        private String graphicsCard;
        private boolean hasBluetooth = false;
        private boolean hasWifi = false;
        
        public ComputerBuilder(String cpu, String memory, String storage) {
            this.cpu = cpu;
            this.memory = memory;
            this.storage = storage;
        }
        
        public ComputerBuilder graphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }
        
        public ComputerBuilder bluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }
        
        public ComputerBuilder wifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }
        
        public Computer build() {
            // 検証処理
            if (cpu == null || memory == null || storage == null) {
                throw new IllegalStateException("必須パラメータが不足しています");
            }
            return new Computer(this);
        }
    }
}

// 使用例
public class FluentBuilderExample {
    public static void main(String[] args) {
        // メソッドチェーンで構築
        Computer computer = new Computer.ComputerBuilder("Intel i7", "16GB", "512GB SSD")
            .graphicsCard("NVIDIA RTX 3080")
            .bluetooth(true)
            .wifi(true)
            .build();
        
        System.out.println(computer);
        
        // 最小構成で構築
        Computer simpleComputer = new Computer.ComputerBuilder("Intel i5", "8GB", "256GB SSD")
            .build();
        
        System.out.println(simpleComputer);
    }
}
```

### バリエーション2: 抽象ビルダーと具象ビルダー

抽象クラスを使用して、共通の構築ロジックを提供する方法です。

```java
// 抽象ビルダー
public abstract class PizzaBuilder {
    protected Pizza pizza;
    
    public Pizza getPizza() {
        return pizza;
    }
    
    public void createNewPizza() {
        pizza = new Pizza();
    }
    
    public abstract void buildDough();
    public abstract void buildSauce();
    public abstract void buildTopping();
}

// 具象ビルダー
public class MargheritaPizzaBuilder extends PizzaBuilder {
    @Override
    public void buildDough() {
        pizza.setDough("薄いクラスト");
    }
    
    @Override
    public void buildSauce() {
        pizza.setSauce("トマトソース");
    }
    
    @Override
    public void buildTopping() {
        pizza.setTopping("モッツァレラチーズ、バジル");
    }
}

public class PepperoniPizzaBuilder extends PizzaBuilder {
    @Override
    public void buildDough() {
        pizza.setDough("厚いクラスト");
    }
    
    @Override
    public void buildSauce() {
        pizza.setSauce("トマトソース");
    }
    
    @Override
    public void buildTopping() {
        pizza.setTopping("ペパロニ、チーズ");
    }
}

// 製品クラス
public class Pizza {
    private String dough;
    private String sauce;
    private String topping;
    
    public void setDough(String dough) { this.dough = dough; }
    public void setSauce(String sauce) { this.sauce = sauce; }
    public void setTopping(String topping) { this.topping = topping; }
    
    @Override
    public String toString() {
        return "Pizza [Dough=" + dough + ", Sauce=" + sauce + ", Topping=" + topping + "]";
    }
}

// 使用例
public class PizzaExample {
    public static void main(String[] args) {
        PizzaBuilder builder = new MargheritaPizzaBuilder();
        builder.createNewPizza();
        builder.buildDough();
        builder.buildSauce();
        builder.buildTopping();
        Pizza pizza = builder.getPizza();
        System.out.println(pizza);
    }
}
```

### バリエーション3: ジェネリックビルダー

ジェネリクスを使用して、型安全なビルダーを実装する方法です。

```java
public class GenericBuilder<T> {
    private T object;
    private java.util.function.Consumer<T> initializer;
    
    public GenericBuilder(Class<T> clazz) {
        try {
            this.object = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("オブジェクトの作成に失敗しました", e);
        }
    }
    
    public GenericBuilder<T> with(java.util.function.Consumer<T> initializer) {
        this.initializer = this.initializer == null 
            ? initializer 
            : this.initializer.andThen(initializer);
        return this;
    }
    
    public T build() {
        if (initializer != null) {
            initializer.accept(object);
        }
        return object;
    }
}

// 使用例
public class Person {
    private String name;
    private int age;
    private String email;
    
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
    
    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", email=" + email + "]";
    }
}

public class GenericBuilderExample {
    public static void main(String[] args) {
        Person person = new GenericBuilder<>(Person.class)
            .with(p -> p.setName("山田太郎"))
            .with(p -> p.setAge(30))
            .with(p -> p.setEmail("yamada@example.com"))
            .build();
        
        System.out.println(person);
    }
}
```

---

## 実践例

### 例1: SQLクエリビルダー

動的なSQLクエリを構築する例です。

```java
public class SQLQuery {
    private String select;
    private String from;
    private String where;
    private String orderBy;
    private Integer limit;
    
    private SQLQuery(SQLQueryBuilder builder) {
        this.select = builder.select;
        this.from = builder.from;
        this.where = builder.where;
        this.orderBy = builder.orderBy;
        this.limit = builder.limit;
    }
    
    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(select != null ? select : "*");
        sql.append(" FROM ").append(from);
        
        if (where != null) {
            sql.append(" WHERE ").append(where);
        }
        
        if (orderBy != null) {
            sql.append(" ORDER BY ").append(orderBy);
        }
        
        if (limit != null) {
            sql.append(" LIMIT ").append(limit);
        }
        
        return sql.toString();
    }
    
    public static class SQLQueryBuilder {
        private String select;
        private String from;
        private String where;
        private String orderBy;
        private Integer limit;
        
        public SQLQueryBuilder select(String columns) {
            this.select = columns;
            return this;
        }
        
        public SQLQueryBuilder from(String table) {
            this.from = table;
            return this;
        }
        
        public SQLQueryBuilder where(String condition) {
            this.where = condition;
            return this;
        }
        
        public SQLQueryBuilder orderBy(String column) {
            this.orderBy = column;
            return this;
        }
        
        public SQLQueryBuilder limit(int limit) {
            this.limit = limit;
            return this;
        }
        
        public SQLQuery build() {
            if (from == null) {
                throw new IllegalStateException("FROM句は必須です");
            }
            return new SQLQuery(this);
        }
    }
}

// 使用例
public class SQLQueryExample {
    public static void main(String[] args) {
        SQLQuery query1 = new SQLQuery.SQLQueryBuilder()
            .select("id, name, email")
            .from("users")
            .where("age > 18")
            .orderBy("name")
            .limit(10)
            .build();
        
        System.out.println("クエリ1: " + query1);
        
        SQLQuery query2 = new SQLQuery.SQLQueryBuilder()
            .from("products")
            .where("price < 1000")
            .build();
        
        System.out.println("クエリ2: " + query2);
    }
}
```

### 例2: HTTPリクエストビルダー

HTTPリクエストを構築する例です。

```java
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String url;
    private Map<String, String> headers;
    private String body;
    
    private HttpRequest(HttpRequestBuilder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.headers = builder.headers != null ? new HashMap<>(builder.headers) : new HashMap<>();
        this.body = builder.body;
    }
    
    public String getMethod() { return method; }
    public String getUrl() { return url; }
    public Map<String, String> getHeaders() { return new HashMap<>(headers); }
    public String getBody() { return body; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(method).append(" ").append(url).append("\n");
        headers.forEach((key, value) -> 
            sb.append(key).append(": ").append(value).append("\n"));
        if (body != null) {
            sb.append("\n").append(body);
        }
        return sb.toString();
    }
    
    public static class HttpRequestBuilder {
        private String method = "GET";
        private String url;
        private Map<String, String> headers;
        private String body;
        
        public HttpRequestBuilder url(String url) {
            this.url = url;
            return this;
        }
        
        public HttpRequestBuilder method(String method) {
            this.method = method;
            return this;
        }
        
        public HttpRequestBuilder header(String key, String value) {
            if (this.headers == null) {
                this.headers = new HashMap<>();
            }
            this.headers.put(key, value);
            return this;
        }
        
        public HttpRequestBuilder body(String body) {
            this.body = body;
            return this;
        }
        
        public HttpRequest build() {
            if (url == null) {
                throw new IllegalStateException("URLは必須です");
            }
            return new HttpRequest(this);
        }
    }
}

// 使用例
public class HttpRequestExample {
    public static void main(String[] args) {
        HttpRequest getRequest = new HttpRequest.HttpRequestBuilder()
            .url("https://api.example.com/users")
            .method("GET")
            .header("Accept", "application/json")
            .header("Authorization", "Bearer token123")
            .build();
        
        System.out.println("GETリクエスト:\n" + getRequest);
        
        HttpRequest postRequest = new HttpRequest.HttpRequestBuilder()
            .url("https://api.example.com/users")
            .method("POST")
            .header("Content-Type", "application/json")
            .body("{\"name\":\"山田太郎\",\"age\":30}")
            .build();
        
        System.out.println("\nPOSTリクエスト:\n" + postRequest);
    }
}
```

### 例3: 設定オブジェクトビルダー

アプリケーション設定を構築する例です。

```java
import java.util.ArrayList;
import java.util.List;

public class ApplicationConfig {
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;
    private int maxConnections;
    private boolean enableCache;
    private int cacheSize;
    private List<String> allowedOrigins;
    private int serverPort;
    
    private ApplicationConfig(ConfigBuilder builder) {
        this.databaseUrl = builder.databaseUrl;
        this.databaseUsername = builder.databaseUsername;
        this.databasePassword = builder.databasePassword;
        this.maxConnections = builder.maxConnections;
        this.enableCache = builder.enableCache;
        this.cacheSize = builder.cacheSize;
        this.allowedOrigins = builder.allowedOrigins != null 
            ? new ArrayList<>(builder.allowedOrigins) 
            : new ArrayList<>();
        this.serverPort = builder.serverPort;
    }
    
    @Override
    public String toString() {
        return "ApplicationConfig [databaseUrl=" + databaseUrl + 
               ", maxConnections=" + maxConnections + 
               ", enableCache=" + enableCache + 
               ", cacheSize=" + cacheSize + 
               ", serverPort=" + serverPort + "]";
    }
    
    public static class ConfigBuilder {
        // 必須パラメータ
        private String databaseUrl;
        private String databaseUsername;
        private String databasePassword;
        
        // オプショナルパラメータ（デフォルト値あり）
        private int maxConnections = 10;
        private boolean enableCache = false;
        private int cacheSize = 100;
        private List<String> allowedOrigins;
        private int serverPort = 8080;
        
        public ConfigBuilder(String databaseUrl, String username, String password) {
            this.databaseUrl = databaseUrl;
            this.databaseUsername = username;
            this.databasePassword = password;
        }
        
        public ConfigBuilder maxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
            return this;
        }
        
        public ConfigBuilder enableCache(boolean enableCache) {
            this.enableCache = enableCache;
            return this;
        }
        
        public ConfigBuilder cacheSize(int cacheSize) {
            this.cacheSize = cacheSize;
            return this;
        }
        
        public ConfigBuilder allowedOrigin(String origin) {
            if (this.allowedOrigins == null) {
                this.allowedOrigins = new ArrayList<>();
            }
            this.allowedOrigins.add(origin);
            return this;
        }
        
        public ConfigBuilder serverPort(int port) {
            this.serverPort = port;
            return this;
        }
        
        public ApplicationConfig build() {
            // 検証処理
            if (databaseUrl == null || databaseUrl.isEmpty()) {
                throw new IllegalStateException("データベースURLは必須です");
            }
            if (maxConnections <= 0) {
                throw new IllegalStateException("最大接続数は1以上である必要があります");
            }
            return new ApplicationConfig(this);
        }
    }
}

// 使用例
public class ConfigExample {
    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig.ConfigBuilder(
                "jdbc:mysql://localhost:3306/mydb",
                "user",
                "password"
            )
            .maxConnections(20)
            .enableCache(true)
            .cacheSize(500)
            .allowedOrigin("http://localhost:3000")
            .allowedOrigin("https://example.com")
            .serverPort(9090)
            .build();
        
        System.out.println(config);
    }
}
```

### 例4: 不変オブジェクトの構築

不変（immutable）オブジェクトを構築する例です。

```java
public final class User {
    private final String name;
    private final String email;
    private final int age;
    private final String address;
    
    private User(UserBuilder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.address = builder.address;
    }
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public String getAddress() { return address; }
    
    @Override
    public String toString() {
        return "User [name=" + name + ", email=" + email + 
               ", age=" + age + ", address=" + address + "]";
    }
    
    public static class UserBuilder {
        private String name;
        private String email;
        private int age;
        private String address;
        
        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }
        
        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }
        
        public User build() {
            // 検証処理
            if (name == null || name.isEmpty()) {
                throw new IllegalStateException("名前は必須です");
            }
            if (email == null || !email.contains("@")) {
                throw new IllegalStateException("有効なメールアドレスを入力してください");
            }
            if (age < 0) {
                throw new IllegalStateException("年齢は0以上である必要があります");
            }
            return new User(this);
        }
    }
}

// 使用例
public class ImmutableExample {
    public static void main(String[] args) {
        User user = new User.UserBuilder()
            .name("山田太郎")
            .email("yamada@example.com")
            .age(30)
            .address("東京都渋谷区")
            .build();
        
        System.out.println(user);
        
        // 不変オブジェクトなので、変更できない
        // user.setName("変更"); // コンパイルエラー
    }
}
```

---

## まとめ

### 学習のポイント

1. **ビルダーパターンの目的**: 複雑なオブジェクトの構築過程を表現から分離
2. **基本的な構造**: 製品クラス、ビルダーインターフェース、具象ビルダークラス、ディレクター（オプション）
3. **Fluent Builder**: メソッドチェーンを使用した実装が一般的
4. **不変オブジェクト**: 不変オブジェクトの構築に適している

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 可読性の向上、柔軟性、不変性の保証、検証の容易さ、段階的な構築 |
| **注意点** | クラス数の増加、コードの複雑性、メモリオーバーヘッド |
| **適用場面** | 複雑なオブジェクト、オプショナルパラメータ、SQLクエリ、設定オブジェクトなど |

### 実装方法の比較

| 実装方法 | Director | メソッドチェーン | 使用場面 |
|---------|---------|----------------|---------|
| 古典的Builder | ✅ | ❌ | 複雑な構築過程が必要な場合 |
| Fluent Builder | ❌ | ✅ | 一般的な用途（推奨） |
| 抽象Builder | ✅ | ❌ | 複数のビルダーで共通ロジックがある場合 |

### 注意点

1. **過剰な設計を避ける**: シンプルなオブジェクトには、コンストラクタやセッターで十分な場合もある
2. **必須パラメータの扱い**: 必須パラメータはコンストラクタで受け取るか、build()メソッドで検証する
3. **不変性の保証**: 不変オブジェクトを構築する場合は、privateコンストラクタとfinalフィールドを使用
4. **検証処理**: build()メソッドでパラメータの検証を行う

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. 実際のプロジェクトでビルダーパターンを適用してみる
3. Prototypeパターンを学習する（別の生成に関するパターン）
4. 他のGoFデザインパターンを学習する

### 参考資料

- [cs-techblog.com - ビルダーパターン](https://cs-techblog.com/technical/builder-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「Effective Java」（Joshua Bloch著）

---

**注意**: この学習プランは、ビルダーパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
