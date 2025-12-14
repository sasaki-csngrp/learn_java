# デコレーターパターン（Decorator Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [デコレーターパターンとは](#デコレーターパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

デコレーターパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**構造に関するパターン（Structural Pattern）**に分類されます。

このパターンは、オブジェクトに動的に責任を追加することを可能にし、既存のコードを変更することなく、柔軟で再利用可能な方法でクラスの機能を拡張します。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- デコレーターパターンの目的と利点を理解する
- 基本的なデコレーターパターンの実装方法を理解する
- 継承の代替としてのデコレーターパターンの利点を理解する
- 実際のプロジェクトでデコレーターパターンを適用できる

---

## デコレーターパターンとは

### 定義

デコレーターパターンは、オブジェクトに動的に責任を追加するためのデザインパターンです。このパターンにより、サブクラス化（継承）を使わずに、オブジェクトの機能を拡張できます。デコレーターは、元のオブジェクトをラップして、新しい機能を追加します。

### 主な特徴

1. **動的な機能追加**: 実行時にオブジェクトに機能を追加可能
2. **柔軟性**: 複数のデコレーターを組み合わせて複雑な動作を実現
3. **単一責任の原則**: 各デコレーターが特定の責任を担当
4. **オープン・クローズドの原則**: 既存のコードを変更せずに機能を拡張

### 使用される場面

デコレーターパターンは、以下のような場面で使用されます：

- **機能の動的追加**: 実行時にオブジェクトに機能を追加する必要がある場合
- **継承の代替**: 継承によるクラス爆発を避けたい場合
- **機能の組み合わせ**: 複数の機能を自由に組み合わせたい場合
- **ストリーム処理**: JavaのI/Oストリーム（BufferedReader、InputStreamReaderなど）
- **GUIコンポーネント**: スクロールバー、ボーダーなどの装飾機能
- **ミドルウェア**: 認証、ログ、キャッシュなどの横断的関心事

### メリット

- **柔軟性**: 実行時に機能を追加・削除可能
- **拡張性**: 新しいデコレーターを追加する際に、既存のコードを変更する必要がない（オープン・クローズドの原則）
- **単一責任の原則**: 各デコレーターが特定の責任を担当
- **クラス爆発の回避**: 継承によるクラス数の爆発的な増加を防ぐ

### デメリット

- **複雑性の増加**: デコレーターの階層が深くなると、理解が難しくなる
- **デバッグの困難さ**: デコレーターのチェーンが長くなると、デバッグが困難になる
- **オブジェクトの増加**: デコレーターごとに新しいオブジェクトが作成される

---

## 基本的な実装

### 実装のポイント

デコレーターパターンを実装するには、以下の要素が必要です：

1. **Component（コンポーネント）**: 機能を定義するインターフェースまたは抽象クラス
2. **ConcreteComponent（具象コンポーネント）**: Componentの具象実装クラス（基本動作を提供）
3. **Decorator（デコレーター）**: Componentを実装し、Componentへの参照を保持する抽象クラス
4. **ConcreteDecorator（具象デコレーター）**: Decoratorの具象実装クラス（新しい機能を追加）

### 基本的な実装例

```java
// 1. Component（コンポーネントインターフェース）
public interface Coffee {
    String getDescription();
    double getCost();
}

// 2. ConcreteComponent（具象コンポーネント）
public class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "シンプルコーヒー";
    }
    
    @Override
    public double getCost() {
        return 2.0;
    }
}

// 3. Decorator（デコレーター抽象クラス）
public abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;
    
    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost();
    }
}

// 4. ConcreteDecorator（具象デコレーター）
public class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", ミルク";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.5;
    }
}

public class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", 砂糖";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.2;
    }
}

public class WhippedCreamDecorator extends CoffeeDecorator {
    public WhippedCreamDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", ホイップクリーム";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.7;
    }
}

// 5. クライアントコード
public class CoffeeShop {
    public static void main(String[] args) {
        // シンプルコーヒー
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());
        
        // ミルクを追加
        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());
        
        // 砂糖を追加
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());
        
        // ホイップクリームを追加
        coffee = new WhippedCreamDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());
    }
}
```

### パターンの構造

```
Component (インターフェース)
  ↑
ConcreteComponent (具象実装)
  ↑
Decorator (抽象クラス)
  ↑
ConcreteDecorator (具象デコレーター)
  └─ Component (参照)
```

### 実行結果

```
シンプルコーヒー - $2.0
シンプルコーヒー, ミルク - $2.5
シンプルコーヒー, ミルク, 砂糖 - $2.7
シンプルコーヒー, ミルク, 砂糖, ホイップクリーム - $3.4
```

### デコレーターパターンを使わない場合の問題

デコレーターパターンを使わない場合、継承によるクラス爆発が発生します：

```java
// 問題のある設計：クラス爆発
public class SimpleCoffee { }
public class MilkCoffee extends SimpleCoffee { }
public class SugarCoffee extends SimpleCoffee { }
public class MilkSugarCoffee extends SimpleCoffee { }
public class WhippedCreamCoffee extends SimpleCoffee { }
public class MilkWhippedCreamCoffee extends SimpleCoffee { }
// ... 組み合わせが増えるとクラス数が爆発的に増加
```

デコレーターパターンを使用することで、機能を動的に組み合わせられ、クラス数の増加を防げます。

---

## 実装のバリエーション

### バリエーション1: インターフェースベースの実装

Decoratorを抽象クラスではなくインターフェースとして実装する方法です。

```java
// Component
public interface Text {
    String getContent();
    String getStyle();
}

// ConcreteComponent
public class PlainText implements Text {
    private String content;
    
    public PlainText(String content) {
        this.content = content;
    }
    
    @Override
    public String getContent() {
        return content;
    }
    
    @Override
    public String getStyle() {
        return "";
    }
}

// Decorator（インターフェース）
public interface TextDecorator extends Text {
    Text getText();
}

// ConcreteDecorator
public class BoldDecorator implements TextDecorator {
    private Text text;
    
    public BoldDecorator(Text text) {
        this.text = text;
    }
    
    @Override
    public Text getText() {
        return text;
    }
    
    @Override
    public String getContent() {
        return "<b>" + text.getContent() + "</b>";
    }
    
    @Override
    public String getStyle() {
        return text.getStyle() + "font-weight: bold; ";
    }
}

public class ItalicDecorator implements TextDecorator {
    private Text text;
    
    public ItalicDecorator(Text text) {
        this.text = text;
    }
    
    @Override
    public Text getText() {
        return text;
    }
    
    @Override
    public String getContent() {
        return "<i>" + text.getContent() + "</i>";
    }
    
    @Override
    public String getStyle() {
        return text.getStyle() + "font-style: italic; ";
    }
}

// 使用例
public class InterfaceBasedExample {
    public static void main(String[] args) {
        Text text = new PlainText("Hello World");
        text = new BoldDecorator(text);
        text = new ItalicDecorator(text);
        
        System.out.println(text.getContent());
        System.out.println("Style: " + text.getStyle());
    }
}
```

### バリエーション2: 複数のデコレーターの組み合わせ

複数のデコレーターを自由に組み合わせる例です。

```java
// Component
public interface Pizza {
    String getDescription();
    double getCost();
}

// ConcreteComponent
public class PlainPizza implements Pizza {
    @Override
    public String getDescription() {
        return "プレーンピザ";
    }
    
    @Override
    public double getCost() {
        return 10.0;
    }
}

// Decorator
public abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza;
    
    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription();
    }
    
    @Override
    public double getCost() {
        return pizza.getCost();
    }
}

// ConcreteDecorator
public class CheeseDecorator extends PizzaDecorator {
    public CheeseDecorator(Pizza pizza) {
        super(pizza);
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription() + ", チーズ";
    }
    
    @Override
    public double getCost() {
        return pizza.getCost() + 2.0;
    }
}

public class PepperoniDecorator extends PizzaDecorator {
    public PepperoniDecorator(Pizza pizza) {
        super(pizza);
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription() + ", ペパロニ";
    }
    
    @Override
    public double getCost() {
        return pizza.getCost() + 3.0;
    }
}

public class MushroomDecorator extends PizzaDecorator {
    public MushroomDecorator(Pizza pizza) {
        super(pizza);
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription() + ", マッシュルーム";
    }
    
    @Override
    public double getCost() {
        return pizza.getCost() + 1.5;
    }
}

// 使用例（複数の組み合わせ）
public class MultipleDecoratorExample {
    public static void main(String[] args) {
        // プレーンピザ
        Pizza pizza = new PlainPizza();
        System.out.println(pizza.getDescription() + " - $" + pizza.getCost());
        
        // チーズとペパロニを追加
        pizza = new CheeseDecorator(new PepperoniDecorator(pizza));
        System.out.println(pizza.getDescription() + " - $" + pizza.getCost());
        
        // さらにマッシュルームを追加
        pizza = new MushroomDecorator(pizza);
        System.out.println(pizza.getDescription() + " - $" + pizza.getCost());
        
        // 別の組み合わせ
        Pizza pizza2 = new PlainPizza();
        pizza2 = new MushroomDecorator(new CheeseDecorator(pizza2));
        System.out.println(pizza2.getDescription() + " - $" + pizza2.getCost());
    }
}
```

### バリエーション3: 条件付きデコレーター

条件に基づいて機能を追加するデコレーターの例です。

```java
// Component
public interface Message {
    String getContent();
    void send();
}

// ConcreteComponent
public class EmailMessage implements Message {
    private String content;
    private String recipient;
    
    public EmailMessage(String content, String recipient) {
        this.content = content;
        this.recipient = recipient;
    }
    
    @Override
    public String getContent() {
        return content;
    }
    
    @Override
    public void send() {
        System.out.println("メールを送信: " + recipient);
        System.out.println("内容: " + content);
    }
}

// Decorator
public abstract class MessageDecorator implements Message {
    protected Message message;
    
    public MessageDecorator(Message message) {
        this.message = message;
    }
    
    @Override
    public String getContent() {
        return message.getContent();
    }
    
    @Override
    public void send() {
        message.send();
    }
}

// ConcreteDecorator（条件付き）
public class EncryptionDecorator extends MessageDecorator {
    private boolean encrypt;
    
    public EncryptionDecorator(Message message, boolean encrypt) {
        super(message);
        this.encrypt = encrypt;
    }
    
    @Override
    public String getContent() {
        String content = message.getContent();
        if (encrypt) {
            return encrypt(content);
        }
        return content;
    }
    
    @Override
    public void send() {
        if (encrypt) {
            System.out.println("[暗号化] メッセージを暗号化して送信");
        }
        message.send();
    }
    
    private String encrypt(String content) {
        // 簡単な暗号化（実際の実装では適切な暗号化を使用）
        return "ENCRYPTED:" + content;
    }
}

public class LoggingDecorator extends MessageDecorator {
    private boolean log;
    
    public LoggingDecorator(Message message, boolean log) {
        super(message);
        this.log = log;
    }
    
    @Override
    public void send() {
        if (log) {
            System.out.println("[ログ] メッセージ送信を記録: " + java.time.LocalDateTime.now());
        }
        message.send();
    }
}

// 使用例
public class ConditionalDecoratorExample {
    public static void main(String[] args) {
        Message message = new EmailMessage("Hello", "user@example.com");
        
        // 暗号化を有効にして送信
        message = new EncryptionDecorator(message, true);
        message = new LoggingDecorator(message, true);
        message.send();
        
        System.out.println();
        
        // 暗号化なしで送信
        Message message2 = new EmailMessage("World", "admin@example.com");
        message2 = new EncryptionDecorator(message2, false);
        message2 = new LoggingDecorator(message2, true);
        message2.send();
    }
}
```

---

## 実践例

### 例1: コーヒーショップシステム

コーヒーに様々なトッピングを動的に追加するシステムの例です。

```java
// Component
public interface Beverage {
    String getDescription();
    double getCost();
    String getSize();
    void setSize(String size);
}

// ConcreteComponent
public class Espresso implements Beverage {
    private String size = "レギュラー";
    private double baseCost = 2.0;
    
    @Override
    public String getDescription() {
        return "エスプレッソ (" + size + ")";
    }
    
    @Override
    public double getCost() {
        double cost = baseCost;
        if (size.equals("ラージ")) {
            cost += 0.5;
        } else if (size.equals("スモール")) {
            cost -= 0.3;
        }
        return cost;
    }
    
    @Override
    public String getSize() {
        return size;
    }
    
    @Override
    public void setSize(String size) {
        this.size = size;
    }
}

public class Latte implements Beverage {
    private String size = "レギュラー";
    private double baseCost = 3.0;
    
    @Override
    public String getDescription() {
        return "ラテ (" + size + ")";
    }
    
    @Override
    public double getCost() {
        double cost = baseCost;
        if (size.equals("ラージ")) {
            cost += 0.5;
        } else if (size.equals("スモール")) {
            cost -= 0.3;
        }
        return cost;
    }
    
    @Override
    public String getSize() {
        return size;
    }
    
    @Override
    public void setSize(String size) {
        this.size = size;
    }
}

// Decorator
public abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;
    
    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription();
    }
    
    @Override
    public double getCost() {
        return beverage.getCost();
    }
    
    @Override
    public String getSize() {
        return beverage.getSize();
    }
    
    @Override
    public void setSize(String size) {
        beverage.setSize(size);
    }
}

// ConcreteDecorator
public class MilkDecorator extends BeverageDecorator {
    public MilkDecorator(Beverage beverage) {
        super(beverage);
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", ミルク";
    }
    
    @Override
    public double getCost() {
        return beverage.getCost() + 0.5;
    }
}

public class SoyMilkDecorator extends BeverageDecorator {
    public SoyMilkDecorator(Beverage beverage) {
        super(beverage);
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 豆乳";
    }
    
    @Override
    public double getCost() {
        return beverage.getCost() + 0.6;
    }
}

public class SugarDecorator extends BeverageDecorator {
    public SugarDecorator(Beverage beverage) {
        super(beverage);
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 砂糖";
    }
    
    @Override
    public double getCost() {
        return beverage.getCost() + 0.2;
    }
}

public class WhippedCreamDecorator extends BeverageDecorator {
    public WhippedCreamDecorator(Beverage beverage) {
        super(beverage);
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", ホイップクリーム";
    }
    
    @Override
    public double getCost() {
        return beverage.getCost() + 0.7;
    }
}

public class CaramelDecorator extends BeverageDecorator {
    public CaramelDecorator(Beverage beverage) {
        super(beverage);
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", キャラメル";
    }
    
    @Override
    public double getCost() {
        return beverage.getCost() + 0.8;
    }
}

// 使用例
public class CoffeeShopExample {
    public static void main(String[] args) {
        // エスプレッソにミルクと砂糖を追加
        Beverage beverage1 = new Espresso();
        beverage1 = new MilkDecorator(beverage1);
        beverage1 = new SugarDecorator(beverage1);
        System.out.println(beverage1.getDescription() + " - $" + beverage1.getCost());
        
        // ラテに豆乳、ホイップクリーム、キャラメルを追加
        Beverage beverage2 = new Latte();
        beverage2.setSize("ラージ");
        beverage2 = new SoyMilkDecorator(beverage2);
        beverage2 = new WhippedCreamDecorator(beverage2);
        beverage2 = new CaramelDecorator(beverage2);
        System.out.println(beverage2.getDescription() + " - $" + beverage2.getCost());
    }
}
```

### 例2: テキストエディタの装飾機能

テキストに様々な装飾を動的に追加するシステムの例です。

```java
// Component
public interface TextComponent {
    String render();
    String getText();
}

// ConcreteComponent
public class PlainText implements TextComponent {
    private String text;
    
    public PlainText(String text) {
        this.text = text;
    }
    
    @Override
    public String render() {
        return text;
    }
    
    @Override
    public String getText() {
        return text;
    }
}

// Decorator
public abstract class TextDecorator implements TextComponent {
    protected TextComponent textComponent;
    
    public TextDecorator(TextComponent textComponent) {
        this.textComponent = textComponent;
    }
    
    @Override
    public String render() {
        return textComponent.render();
    }
    
    @Override
    public String getText() {
        return textComponent.getText();
    }
}

// ConcreteDecorator
public class BoldDecorator extends TextDecorator {
    public BoldDecorator(TextComponent textComponent) {
        super(textComponent);
    }
    
    @Override
    public String render() {
        return "<b>" + textComponent.render() + "</b>";
    }
}

public class ItalicDecorator extends TextDecorator {
    public ItalicDecorator(TextComponent textComponent) {
        super(textComponent);
    }
    
    @Override
    public String render() {
        return "<i>" + textComponent.render() + "</i>";
    }
}

public class UnderlineDecorator extends TextDecorator {
    public UnderlineDecorator(TextComponent textComponent) {
        super(textComponent);
    }
    
    @Override
    public String render() {
        return "<u>" + textComponent.render() + "</u>";
    }
}

public class ColorDecorator extends TextDecorator {
    private String color;
    
    public ColorDecorator(TextComponent textComponent, String color) {
        super(textComponent);
        this.color = color;
    }
    
    @Override
    public String render() {
        return "<span style=\"color: " + color + ";\">" + textComponent.render() + "</span>";
    }
}

public class FontSizeDecorator extends TextDecorator {
    private int size;
    
    public FontSizeDecorator(TextComponent textComponent, int size) {
        super(textComponent);
        this.size = size;
    }
    
    @Override
    public String render() {
        return "<span style=\"font-size: " + size + "px;\">" + textComponent.render() + "</span>";
    }
}

// 使用例
public class TextEditorExample {
    public static void main(String[] args) {
        // プレーンテキスト
        TextComponent text = new PlainText("Hello World");
        System.out.println("プレーン: " + text.render());
        
        // 太字と斜体を追加
        text = new BoldDecorator(new ItalicDecorator(text));
        System.out.println("太字+斜体: " + text.render());
        
        // 下線と色を追加
        text = new UnderlineDecorator(new ColorDecorator(text, "red"));
        System.out.println("装飾後: " + text.render());
        
        // フォントサイズを追加
        text = new FontSizeDecorator(text, 24);
        System.out.println("最終: " + text.render());
    }
}
```

### 例3: ストリーム処理の装飾

データストリームに様々な処理を動的に追加するシステムの例です。

```java
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

// Component
public interface DataStream {
    byte[] read() throws IOException;
    void write(byte[] data) throws IOException;
    void close() throws IOException;
}

// ConcreteComponent
public class BasicStream implements DataStream {
    private byte[] buffer;
    private int position = 0;
    
    public BasicStream(byte[] initialData) {
        this.buffer = initialData != null ? initialData : new byte[0];
    }
    
    @Override
    public byte[] read() throws IOException {
        byte[] result = new byte[buffer.length - position];
        System.arraycopy(buffer, position, result, 0, result.length);
        position = buffer.length;
        return result;
    }
    
    @Override
    public void write(byte[] data) throws IOException {
        byte[] newBuffer = new byte[buffer.length + data.length];
        System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
        System.arraycopy(data, 0, newBuffer, buffer.length, data.length);
        buffer = newBuffer;
    }
    
    @Override
    public void close() throws IOException {
        buffer = null;
        System.out.println("ストリームを閉じました");
    }
}

// Decorator
public abstract class StreamDecorator implements DataStream {
    protected DataStream stream;
    
    public StreamDecorator(DataStream stream) {
        this.stream = stream;
    }
    
    @Override
    public byte[] read() throws IOException {
        return stream.read();
    }
    
    @Override
    public void write(byte[] data) throws IOException {
        stream.write(data);
    }
    
    @Override
    public void close() throws IOException {
        stream.close();
    }
}

// ConcreteDecorator
public class CompressionDecorator extends StreamDecorator {
    public CompressionDecorator(DataStream stream) {
        super(stream);
    }
    
    @Override
    public byte[] read() throws IOException {
        byte[] compressed = stream.read();
        System.out.println("[圧縮解除] データを展開します");
        return decompress(compressed);
    }
    
    @Override
    public void write(byte[] data) throws IOException {
        System.out.println("[圧縮] データを圧縮します");
        byte[] compressed = compress(data);
        stream.write(compressed);
    }
    
    private byte[] compress(byte[] data) {
        // 簡単な圧縮（実際の実装では適切な圧縮アルゴリズムを使用）
        return ("COMPRESSED:" + new String(data)).getBytes();
    }
    
    private byte[] decompress(byte[] compressed) {
        // 簡単な展開
        String str = new String(compressed);
        if (str.startsWith("COMPRESSED:")) {
            return str.substring(11).getBytes();
        }
        return compressed;
    }
}

public class EncryptionDecorator extends StreamDecorator {
    private String key;
    
    public EncryptionDecorator(DataStream stream, String key) {
        super(stream);
        this.key = key;
    }
    
    @Override
    public byte[] read() throws IOException {
        byte[] encrypted = stream.read();
        System.out.println("[復号化] データを復号化します");
        return decrypt(encrypted);
    }
    
    @Override
    public void write(byte[] data) throws IOException {
        System.out.println("[暗号化] データを暗号化します");
        byte[] encrypted = encrypt(data);
        stream.write(encrypted);
    }
    
    private byte[] encrypt(byte[] data) {
        // 簡単な暗号化（実際の実装では適切な暗号化アルゴリズムを使用）
        return ("ENCRYPTED:" + new String(data)).getBytes();
    }
    
    private byte[] decrypt(byte[] encrypted) {
        // 簡単な復号化
        String str = new String(encrypted);
        if (str.startsWith("ENCRYPTED:")) {
            return str.substring(10).getBytes();
        }
        return encrypted;
    }
}

public class LoggingDecorator extends StreamDecorator {
    public LoggingDecorator(DataStream stream) {
        super(stream);
    }
    
    @Override
    public byte[] read() throws IOException {
        System.out.println("[ログ] 読み取り操作: " + java.time.LocalDateTime.now());
        byte[] data = stream.read();
        System.out.println("[ログ] 読み取り完了: " + data.length + " bytes");
        return data;
    }
    
    @Override
    public void write(byte[] data) throws IOException {
        System.out.println("[ログ] 書き込み操作: " + java.time.LocalDateTime.now());
        System.out.println("[ログ] 書き込みデータ: " + data.length + " bytes");
        stream.write(data);
        System.out.println("[ログ] 書き込み完了");
    }
}

// 使用例
public class StreamProcessingExample {
    public static void main(String[] args) throws IOException {
        // 基本ストリーム
        DataStream stream = new BasicStream(null);
        
        // ログ、暗号化、圧縮の順で装飾
        stream = new LoggingDecorator(stream);
        stream = new EncryptionDecorator(stream, "secret-key");
        stream = new CompressionDecorator(stream);
        
        // データを書き込み
        stream.write("Hello World".getBytes());
        
        System.out.println();
        
        // データを読み取り
        byte[] data = stream.read();
        System.out.println("読み取ったデータ: " + new String(data));
        
        stream.close();
    }
}
```

### 例4: HTTPリクエストのミドルウェア

HTTPリクエストに様々なミドルウェア機能を動的に追加するシステムの例です。

```java
// Component
public interface HttpRequest {
    void execute();
    String getUrl();
    void setUrl(String url);
    String getMethod();
    void setMethod(String method);
}

// ConcreteComponent
public class BasicHttpRequest implements HttpRequest {
    private String url;
    private String method = "GET";
    
    @Override
    public void execute() {
        System.out.println("HTTP " + method + " リクエストを実行: " + url);
    }
    
    @Override
    public String getUrl() {
        return url;
    }
    
    @Override
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String getMethod() {
        return method;
    }
    
    @Override
    public void setMethod(String method) {
        this.method = method;
    }
}

// Decorator
public abstract class HttpRequestDecorator implements HttpRequest {
    protected HttpRequest request;
    
    public HttpRequestDecorator(HttpRequest request) {
        this.request = request;
    }
    
    @Override
    public void execute() {
        request.execute();
    }
    
    @Override
    public String getUrl() {
        return request.getUrl();
    }
    
    @Override
    public void setUrl(String url) {
        request.setUrl(url);
    }
    
    @Override
    public String getMethod() {
        return request.getMethod();
    }
    
    @Override
    public void setMethod(String method) {
        request.setMethod(method);
    }
}

// ConcreteDecorator
public class AuthenticationDecorator extends HttpRequestDecorator {
    private String token;
    
    public AuthenticationDecorator(HttpRequest request, String token) {
        super(request);
        this.token = token;
    }
    
    @Override
    public void execute() {
        System.out.println("[認証] トークンを追加: " + token);
        request.execute();
    }
}

public class LoggingDecorator extends HttpRequestDecorator {
    public LoggingDecorator(HttpRequest request) {
        super(request);
    }
    
    @Override
    public void execute() {
        System.out.println("[ログ] リクエスト開始: " + java.time.LocalDateTime.now());
        System.out.println("[ログ] URL: " + request.getUrl());
        System.out.println("[ログ] メソッド: " + request.getMethod());
        request.execute();
        System.out.println("[ログ] リクエスト完了: " + java.time.LocalDateTime.now());
    }
}

public class CachingDecorator extends HttpRequestDecorator {
    private static java.util.Map<String, String> cache = new java.util.HashMap<>();
    
    public CachingDecorator(HttpRequest request) {
        super(request);
    }
    
    @Override
    public void execute() {
        String cacheKey = request.getMethod() + ":" + request.getUrl();
        if (cache.containsKey(cacheKey)) {
            System.out.println("[キャッシュ] キャッシュから取得: " + cache.get(cacheKey));
            return;
        }
        request.execute();
        cache.put(cacheKey, "レスポンスデータ");
        System.out.println("[キャッシュ] キャッシュに保存");
    }
}

public class RetryDecorator extends HttpRequestDecorator {
    private int maxRetries;
    
    public RetryDecorator(HttpRequest request, int maxRetries) {
        super(request);
        this.maxRetries = maxRetries;
    }
    
    @Override
    public void execute() {
        int attempts = 0;
        while (attempts < maxRetries) {
            try {
                System.out.println("[リトライ] 試行 " + (attempts + 1) + "/" + maxRetries);
                request.execute();
                return;
            } catch (Exception e) {
                attempts++;
                if (attempts >= maxRetries) {
                    System.out.println("[リトライ] 最大試行回数に達しました");
                    throw e;
                }
                System.out.println("[リトライ] エラーが発生しました。再試行します...");
            }
        }
    }
}

// 使用例
public class HttpRequestExample {
    public static void main(String[] args) {
        // 基本リクエスト
        HttpRequest request = new BasicHttpRequest();
        request.setUrl("https://api.example.com/data");
        request.setMethod("GET");
        
        // ミドルウェアを追加
        request = new LoggingDecorator(request);
        request = new AuthenticationDecorator(request, "bearer-token-123");
        request = new CachingDecorator(request);
        
        // リクエストを実行
        request.execute();
        
        System.out.println();
        
        // キャッシュから取得（2回目）
        request.execute();
    }
}
```

### 例5: グラフィックオブジェクトの装飾

グラフィックオブジェクトに様々な装飾を動的に追加するシステムの例です。

```java
// Component
public interface Graphic {
    void draw();
    String getDescription();
}

// ConcreteComponent
public class Circle implements Graphic {
    private int radius;
    
    public Circle(int radius) {
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        System.out.println("円を描画 (半径: " + radius + ")");
    }
    
    @Override
    public String getDescription() {
        return "円 (半径: " + radius + ")";
    }
}

public class Rectangle implements Graphic {
    private int width, height;
    
    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void draw() {
        System.out.println("四角形を描画 (幅: " + width + ", 高さ: " + height + ")");
    }
    
    @Override
    public String getDescription() {
        return "四角形 (幅: " + width + ", 高さ: " + height + ")";
    }
}

// Decorator
public abstract class GraphicDecorator implements Graphic {
    protected Graphic graphic;
    
    public GraphicDecorator(Graphic graphic) {
        this.graphic = graphic;
    }
    
    @Override
    public void draw() {
        graphic.draw();
    }
    
    @Override
    public String getDescription() {
        return graphic.getDescription();
    }
}

// ConcreteDecorator
public class BorderDecorator extends GraphicDecorator {
    private String borderColor;
    private int borderWidth;
    
    public BorderDecorator(Graphic graphic, String borderColor, int borderWidth) {
        super(graphic);
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }
    
    @Override
    public void draw() {
        graphic.draw();
        System.out.println("  ボーダーを追加: 色=" + borderColor + ", 幅=" + borderWidth);
    }
    
    @Override
    public String getDescription() {
        return graphic.getDescription() + " [ボーダー: " + borderColor + ", " + borderWidth + "px]";
    }
}

public class ShadowDecorator extends GraphicDecorator {
    private int shadowOffset;
    
    public ShadowDecorator(Graphic graphic, int shadowOffset) {
        super(graphic);
        this.shadowOffset = shadowOffset;
    }
    
    @Override
    public void draw() {
        graphic.draw();
        System.out.println("  シャドウを追加: オフセット=" + shadowOffset);
    }
    
    @Override
    public String getDescription() {
        return graphic.getDescription() + " [シャドウ: " + shadowOffset + "px]";
    }
}

public class ColorFillDecorator extends GraphicDecorator {
    private String fillColor;
    
    public ColorFillDecorator(Graphic graphic, String fillColor) {
        super(graphic);
        this.fillColor = fillColor;
    }
    
    @Override
    public void draw() {
        graphic.draw();
        System.out.println("  塗りつぶしを追加: 色=" + fillColor);
    }
    
    @Override
    public String getDescription() {
        return graphic.getDescription() + " [塗りつぶし: " + fillColor + "]";
    }
}

// 使用例
public class GraphicDecoratorExample {
    public static void main(String[] args) {
        // 円を作成
        Graphic circle = new Circle(10);
        
        // 装飾を追加
        circle = new ColorFillDecorator(circle, "赤");
        circle = new BorderDecorator(circle, "黒", 2);
        circle = new ShadowDecorator(circle, 5);
        
        circle.draw();
        System.out.println("説明: " + circle.getDescription());
        
        System.out.println();
        
        // 四角形を作成
        Graphic rectangle = new Rectangle(20, 15);
        rectangle = new ColorFillDecorator(rectangle, "青");
        rectangle = new BorderDecorator(rectangle, "白", 1);
        
        rectangle.draw();
        System.out.println("説明: " + rectangle.getDescription());
    }
}
```

---

## まとめ

### 学習のポイント

1. **デコレーターパターンの目的**: オブジェクトに動的に責任を追加し、継承によるクラス爆発を防ぐ
2. **基本的な構造**: Component、ConcreteComponent、Decorator、ConcreteDecoratorの4つの主要コンポーネント
3. **継承との違い**: 継承はコンパイル時に決定されるが、デコレーターは実行時に機能を追加
4. **複数の組み合わせ**: 複数のデコレーターを自由に組み合わせて複雑な動作を実現

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 柔軟性、拡張性、単一責任の原則、クラス爆発の回避 |
| **注意点** | 複雑性の増加、デバッグの困難さ、オブジェクトの増加 |
| **適用場面** | 機能の動的追加、継承の代替、機能の組み合わせ、ストリーム処理など |

### 継承 vs デコレーターパターン

| 項目 | 継承 | デコレーターパターン |
|------|------|-------------------|
| **決定時期** | コンパイル時 | 実行時 |
| **柔軟性** | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| **クラス数** | 組み合わせ分だけ増加 | デコレーター数だけ増加 |
| **拡張性** | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| **複雑性** | ⭐⭐ | ⭐⭐⭐ |

### 他のパターンとの関係

- **Adapter**: デコレーターは機能を追加するが、アダプターはインターフェースを変換する
- **Composite**: デコレーターは1つのオブジェクトを装飾するが、コンポジットは複数のオブジェクトを統合する
- **Strategy**: デコレーターは機能を追加するが、ストラテジーはアルゴリズムを選択する

### 注意点

1. **適切な使用場面**: 実行時に機能を動的に追加する必要がある場合に使用
2. **デコレーターの順序**: デコレーターの適用順序によって結果が変わる可能性がある
3. **パフォーマンス**: デコレーターのチェーンが長くなると、パフォーマンスに影響を与える可能性
4. **デバッグ**: デコレーターの階層が深くなると、デバッグが困難になる

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. 実際のプロジェクトでデコレーターパターンを適用してみる
3. JavaのI/Oストリーム（BufferedReader、InputStreamReaderなど）を学習する
4. Adapterパターンを学習する（インターフェース変換のパターン）

### 参考資料

- [cs-techblog.com - デコレーターパターン](https://cs-techblog.com/technical/decorator-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「Effective Java」（Joshua Bloch著）

---

**注意**: この学習プランは、デコレーターパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
