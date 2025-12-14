# ブリッジパターン（Bridge Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [ブリッジパターンとは](#ブリッジパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

ブリッジパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**構造に関するパターン（Structural Pattern）**に分類されます。

このパターンは、抽象化と実装を分離し、両者が独立して進化できるようにすることで、システムの柔軟性と拡張性を向上させます。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- ブリッジパターンの目的と利点を理解する
- 基本的なブリッジパターンの実装方法を理解する
- 抽象化と実装の分離の重要性を理解する
- 実際のプロジェクトでブリッジパターンを適用できる

---

## ブリッジパターンとは

### 定義

ブリッジパターンは、抽象化と実装を分離し、両者を独立した階層構造にするデザインパターンです。これにより、抽象化と実装の両方を独立して拡張でき、クライアントコードへの影響を最小限に抑えながら、システムの柔軟性と拡張性を向上させます。

### 主な特徴

1. **抽象化と実装の分離**: 抽象化と実装を独立した階層構造に分離
2. **独立した拡張性**: 抽象化と実装を独立して拡張可能
3. **実行時の結合**: 実行時に抽象化と実装を動的に結合可能
4. **疎結合**: 抽象化と実装の間の結合度を低く保つ

### 使用される場面

ブリッジパターンは、以下のような場面で使用されます：

- **複数のバリエーションのサポート**: 抽象化と実装の複数のバリエーションをサポートする必要がある場合
- **実装の変更の影響を最小化**: 実装の変更がクライアントコードに影響を与えないようにする場合
- **実行時の動的結合**: 実行時に抽象化と実装を動的に結合する必要がある場合
- **プラットフォーム依存の分離**: プラットフォーム依存の実装を抽象化から分離する場合
- **UIコンポーネント**: 異なるプラットフォーム（Windows、Mac、Linux）でのUIコンポーネントの実装

### メリット

- **柔軟性**: 抽象化と実装を独立して変更・拡張可能
- **拡張性**: 新しい抽象化や実装を追加する際に、既存のコードを変更する必要がない（オープン・クローズドの原則）
- **疎結合**: 抽象化と実装の間の結合度を低く保つ
- **実行時の結合**: 実行時に抽象化と実装を動的に結合可能

### デメリット

- **複雑性の増加**: 抽象化と実装を分離することで、コードの複雑性が増加
- **理解の難しさ**: パターンを理解していない開発者には複雑に見える
- **過剰な設計**: シンプルなケースでは過剰な設計になる可能性

---

## 基本的な実装

### 実装のポイント

ブリッジパターンを実装するには、以下の要素が必要です：

1. **Abstraction（抽象化）**: クライアントが使用する抽象化のインターフェース
2. **RefinedAbstraction（改良された抽象化）**: Abstractionを拡張した具象クラス
3. **Implementor（実装者）**: 実装のインターフェース
4. **ConcreteImplementor（具象実装者）**: Implementorの具象実装クラス

### 基本的な実装例

```java
// 1. Implementor（実装のインターフェース）
public interface Color {
    void applyColor();
}

// 2. ConcreteImplementor（具象実装クラス）
public class RedColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("赤色を適用");
    }
}

public class BlueColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("青色を適用");
    }
}

public class GreenColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("緑色を適用");
    }
}

// 3. Abstraction（抽象化クラス）
public abstract class Shape {
    protected Color color;
    
    protected Shape(Color color) {
        this.color = color;
    }
    
    public abstract void draw();
}

// 4. RefinedAbstraction（改良された抽象化）
public class Circle extends Shape {
    public Circle(Color color) {
        super(color);
    }
    
    @Override
    public void draw() {
        System.out.print("円を描画 ");
        color.applyColor();
    }
}

public class Square extends Shape {
    public Square(Color color) {
        super(color);
    }
    
    @Override
    public void draw() {
        System.out.print("四角形を描画 ");
        color.applyColor();
    }
}

public class Triangle extends Shape {
    public Triangle(Color color) {
        super(color);
    }
    
    @Override
    public void draw() {
        System.out.print("三角形を描画 ");
        color.applyColor();
    }
}

// 5. クライアントコード
public class BridgePatternDemo {
    public static void main(String[] args) {
        // 赤い円
        Shape redCircle = new Circle(new RedColor());
        redCircle.draw();
        
        // 青い四角形
        Shape blueSquare = new Square(new BlueColor());
        blueSquare.draw();
        
        // 緑の三角形
        Shape greenTriangle = new Triangle(new GreenColor());
        greenTriangle.draw();
    }
}
```

### パターンの構造

```
Client
  ↓
Abstraction (抽象化)
  ├─ RefinedAbstraction (改良された抽象化)
  └─ Implementor (実装者インターフェース)
      └─ ConcreteImplementor (具象実装者)
```

### 実行結果

```
円を描画 赤色を適用
四角形を描画 青色を適用
三角形を描画 緑色を適用
```

### ブリッジパターンを使わない場合の問題

ブリッジパターンを使わない場合、形状と色の組み合わせごとにクラスを作成する必要があります：

```java
// 問題のある設計：クラス爆発
public class RedCircle { }
public class BlueCircle { }
public class GreenCircle { }
public class RedSquare { }
public class BlueSquare { }
public class GreenSquare { }
// ... 組み合わせが増えるとクラス数が爆発的に増加
```

ブリッジパターンを使用することで、形状と色を独立して拡張でき、クラス数の増加を防げます。

---

## 実装のバリエーション

### バリエーション1: インターフェースベースの実装

Abstractionをインターフェースとして定義する方法です。

```java
// Implementor
public interface Renderer {
    void render();
}

// ConcreteImplementor
public class VectorRenderer implements Renderer {
    @Override
    public void render() {
        System.out.println("ベクター形式で描画");
    }
}

public class RasterRenderer implements Renderer {
    @Override
    public void render() {
        System.out.println("ラスター形式で描画");
    }
}

// Abstraction（インターフェース）
public interface Shape {
    void draw();
}

// RefinedAbstraction
public class Circle implements Shape {
    private Renderer renderer;
    
    public Circle(Renderer renderer) {
        this.renderer = renderer;
    }
    
    @Override
    public void draw() {
        System.out.print("円を描画 - ");
        renderer.render();
    }
}

public class Square implements Shape {
    private Renderer renderer;
    
    public Square(Renderer renderer) {
        this.renderer = renderer;
    }
    
    @Override
    public void draw() {
        System.out.print("四角形を描画 - ");
        renderer.render();
    }
}

// 使用例
public class InterfaceBasedExample {
    public static void main(String[] args) {
        Shape circle = new Circle(new VectorRenderer());
        circle.draw();
        
        Shape square = new Square(new RasterRenderer());
        square.draw();
    }
}
```

### バリエーション2: 複数の実装インターフェース

複数の実装インターフェースを持つ例です。

```java
// Implementor 1
public interface Color {
    void applyColor();
}

// Implementor 2
public interface Texture {
    void applyTexture();
}

// ConcreteImplementor
public class RedColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("赤色を適用");
    }
}

public class SmoothTexture implements Texture {
    @Override
    public void applyTexture() {
        System.out.println("滑らかな質感を適用");
    }
}

// Abstraction
public abstract class Shape {
    protected Color color;
    protected Texture texture;
    
    protected Shape(Color color, Texture texture) {
        this.color = color;
        this.texture = texture;
    }
    
    public abstract void draw();
}

// RefinedAbstraction
public class Circle extends Shape {
    public Circle(Color color, Texture texture) {
        super(color, texture);
    }
    
    @Override
    public void draw() {
        System.out.print("円を描画 - ");
        color.applyColor();
        System.out.print(" - ");
        texture.applyTexture();
    }
}

// 使用例
public class MultipleImplementorExample {
    public static void main(String[] args) {
        Shape circle = new Circle(new RedColor(), new SmoothTexture());
        circle.draw();
    }
}
```

### バリエーション3: 実行時の動的結合

実行時に抽象化と実装を動的に結合する例です。

```java
// Implementor
public interface Device {
    void turnOn();
    void turnOff();
    void setVolume(int volume);
    int getVolume();
}

// ConcreteImplementor
public class TV implements Device {
    private boolean isOn = false;
    private int volume = 0;
    
    @Override
    public void turnOn() {
        isOn = true;
        System.out.println("TVをオンにしました");
    }
    
    @Override
    public void turnOff() {
        isOn = false;
        System.out.println("TVをオフにしました");
    }
    
    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("TVの音量を " + volume + " に設定しました");
    }
    
    @Override
    public int getVolume() {
        return volume;
    }
}

public class Radio implements Device {
    private boolean isOn = false;
    private int volume = 0;
    
    @Override
    public void turnOn() {
        isOn = true;
        System.out.println("ラジオをオンにしました");
    }
    
    @Override
    public void turnOff() {
        isOn = false;
        System.out.println("ラジオをオフにしました");
    }
    
    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("ラジオの音量を " + volume + " に設定しました");
    }
    
    @Override
    public int getVolume() {
        return volume;
    }
}

// Abstraction
public abstract class RemoteControl {
    protected Device device;
    
    public RemoteControl(Device device) {
        this.device = device;
    }
    
    public abstract void power();
    public abstract void volumeUp();
    public abstract void volumeDown();
}

// RefinedAbstraction
public class BasicRemoteControl extends RemoteControl {
    public BasicRemoteControl(Device device) {
        super(device);
    }
    
    @Override
    public void power() {
        if (device.getVolume() == 0) {
            device.turnOn();
        } else {
            device.turnOff();
        }
    }
    
    @Override
    public void volumeUp() {
        device.setVolume(device.getVolume() + 10);
    }
    
    @Override
    public void volumeDown() {
        device.setVolume(Math.max(0, device.getVolume() - 10));
    }
}

public class AdvancedRemoteControl extends RemoteControl {
    public AdvancedRemoteControl(Device device) {
        super(device);
    }
    
    @Override
    public void power() {
        device.turnOn();
    }
    
    @Override
    public void volumeUp() {
        device.setVolume(device.getVolume() + 5);
    }
    
    @Override
    public void volumeDown() {
        device.setVolume(Math.max(0, device.getVolume() - 5));
    }
    
    public void mute() {
        device.setVolume(0);
        System.out.println("ミュートにしました");
    }
}

// 使用例（実行時に動的に結合）
public class DynamicBindingExample {
    public static void main(String[] args) {
        // 実行時にTVとリモコンを結合
        Device tv = new TV();
        RemoteControl remote1 = new BasicRemoteControl(tv);
        remote1.power();
        remote1.volumeUp();
        remote1.volumeUp();
        
        System.out.println();
        
        // 実行時にラジオとリモコンを結合
        Device radio = new Radio();
        RemoteControl remote2 = new AdvancedRemoteControl(radio);
        remote2.power();
        remote2.volumeUp();
        remote2.mute();
    }
}
```

---

## 実践例

### 例1: 形状と色の描画システム

形状と色を独立して拡張できる描画システムの例です。

```java
// Implementor
public interface Color {
    void applyColor();
    String getColorName();
}

// ConcreteImplementor
public class RedColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("赤色を適用");
    }
    
    @Override
    public String getColorName() {
        return "赤";
    }
}

public class BlueColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("青色を適用");
    }
    
    @Override
    public String getColorName() {
        return "青";
    }
}

public class GreenColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("緑色を適用");
    }
    
    @Override
    public String getColorName() {
        return "緑";
    }
}

// Abstraction
public abstract class Shape {
    protected Color color;
    
    protected Shape(Color color) {
        this.color = color;
    }
    
    public abstract void draw();
    public abstract double getArea();
    public abstract double getPerimeter();
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
}

// RefinedAbstraction
public class Circle extends Shape {
    private double radius;
    
    public Circle(Color color, double radius) {
        super(color);
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        System.out.print("半径 " + radius + " の円を描画（" + color.getColorName() + "） - ");
        color.applyColor();
    }
    
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}

public class Rectangle extends Shape {
    private double width;
    private double height;
    
    public Rectangle(Color color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void draw() {
        System.out.print("幅 " + width + "、高さ " + height + " の四角形を描画（" + color.getColorName() + "） - ");
        color.applyColor();
    }
    
    @Override
    public double getArea() {
        return width * height;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }
}

// 使用例
public class ShapeColorExample {
    public static void main(String[] args) {
        // 赤い円
        Shape redCircle = new Circle(new RedColor(), 5.0);
        redCircle.draw();
        System.out.println("面積: " + redCircle.getArea());
        
        System.out.println();
        
        // 青い四角形
        Shape blueRectangle = new Rectangle(new BlueColor(), 4.0, 6.0);
        blueRectangle.draw();
        System.out.println("面積: " + blueRectangle.getArea());
        
        System.out.println();
        
        // 実行時に色を変更
        blueRectangle.setColor(new GreenColor());
        blueRectangle.draw();
    }
}
```

### 例2: リモコンとデバイス

リモコンとデバイスを独立して拡張できるシステムの例です。

```java
// Implementor
public interface Device {
    boolean isEnabled();
    void enable();
    void disable();
    int getVolume();
    void setVolume(int volume);
    int getChannel();
    void setChannel(int channel);
}

// ConcreteImplementor
public class TV implements Device {
    private boolean enabled = false;
    private int volume = 50;
    private int channel = 1;
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public void enable() {
        enabled = true;
        System.out.println("TVをオンにしました");
    }
    
    @Override
    public void disable() {
        enabled = false;
        System.out.println("TVをオフにしました");
    }
    
    @Override
    public int getVolume() {
        return volume;
    }
    
    @Override
    public void setVolume(int volume) {
        this.volume = Math.max(0, Math.min(100, volume));
        System.out.println("TVの音量を " + this.volume + " に設定しました");
    }
    
    @Override
    public int getChannel() {
        return channel;
    }
    
    @Override
    public void setChannel(int channel) {
        this.channel = channel;
        System.out.println("TVのチャンネルを " + channel + " に設定しました");
    }
}

public class Radio implements Device {
    private boolean enabled = false;
    private int volume = 30;
    private int channel = 1;
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public void enable() {
        enabled = true;
        System.out.println("ラジオをオンにしました");
    }
    
    @Override
    public void disable() {
        enabled = false;
        System.out.println("ラジオをオフにしました");
    }
    
    @Override
    public int getVolume() {
        return volume;
    }
    
    @Override
    public void setVolume(int volume) {
        this.volume = Math.max(0, Math.min(100, volume));
        System.out.println("ラジオの音量を " + this.volume + " に設定しました");
    }
    
    @Override
    public int getChannel() {
        return channel;
    }
    
    @Override
    public void setChannel(int channel) {
        this.channel = channel;
        System.out.println("ラジオのチャンネルを " + channel + " に設定しました");
    }
}

// Abstraction
public abstract class RemoteControl {
    protected Device device;
    
    public RemoteControl(Device device) {
        this.device = device;
    }
    
    public abstract void togglePower();
    public abstract void volumeDown();
    public abstract void volumeUp();
    public abstract void channelDown();
    public abstract void channelUp();
}

// RefinedAbstraction
public class BasicRemoteControl extends RemoteControl {
    public BasicRemoteControl(Device device) {
        super(device);
    }
    
    @Override
    public void togglePower() {
        if (device.isEnabled()) {
            device.disable();
        } else {
            device.enable();
        }
    }
    
    @Override
    public void volumeDown() {
        device.setVolume(device.getVolume() - 10);
    }
    
    @Override
    public void volumeUp() {
        device.setVolume(device.getVolume() + 10);
    }
    
    @Override
    public void channelDown() {
        device.setChannel(device.getChannel() - 1);
    }
    
    @Override
    public void channelUp() {
        device.setChannel(device.getChannel() + 1);
    }
}

public class AdvancedRemoteControl extends RemoteControl {
    public AdvancedRemoteControl(Device device) {
        super(device);
    }
    
    @Override
    public void togglePower() {
        device.enable();
    }
    
    @Override
    public void volumeDown() {
        device.setVolume(device.getVolume() - 5);
    }
    
    @Override
    public void volumeUp() {
        device.setVolume(device.getVolume() + 5);
    }
    
    @Override
    public void channelDown() {
        device.setChannel(device.getChannel() - 1);
    }
    
    @Override
    public void channelUp() {
        device.setChannel(device.getChannel() + 1);
    }
    
    public void mute() {
        device.setVolume(0);
        System.out.println("ミュートにしました");
    }
}

// 使用例
public class RemoteControlExample {
    public static void main(String[] args) {
        // TVと基本リモコン
        Device tv = new TV();
        RemoteControl tvRemote = new BasicRemoteControl(tv);
        tvRemote.togglePower();
        tvRemote.volumeUp();
        tvRemote.channelUp();
        
        System.out.println();
        
        // ラジオと高度なリモコン
        Device radio = new Radio();
        RemoteControl radioRemote = new AdvancedRemoteControl(radio);
        radioRemote.togglePower();
        radioRemote.volumeUp();
        radioRemote.mute();
    }
}
```

### 例3: ドキュメントとプリンター

ドキュメントとプリンターを独立して拡張できるシステムの例です。

```java
// Implementor
public interface Printer {
    void print(String content);
    void printHeader(String header);
    void printFooter(String footer);
}

// ConcreteImplementor
public class InkjetPrinter implements Printer {
    @Override
    public void print(String content) {
        System.out.println("[インクジェットプリンター] " + content);
    }
    
    @Override
    public void printHeader(String header) {
        System.out.println("[インクジェットプリンター] === " + header + " ===");
    }
    
    @Override
    public void printFooter(String footer) {
        System.out.println("[インクジェットプリンター] --- " + footer + " ---");
    }
}

public class LaserPrinter implements Printer {
    @Override
    public void print(String content) {
        System.out.println("[レーザープリンター] " + content);
    }
    
    @Override
    public void printHeader(String header) {
        System.out.println("[レーザープリンター] === " + header + " ===");
    }
    
    @Override
    public void printFooter(String footer) {
        System.out.println("[レーザープリンター] --- " + footer + " ---");
    }
}

public class DotMatrixPrinter implements Printer {
    @Override
    public void print(String content) {
        System.out.println("[ドットマトリックスプリンター] " + content);
    }
    
    @Override
    public void printHeader(String header) {
        System.out.println("[ドットマトリックスプリンター] === " + header + " ===");
    }
    
    @Override
    public void printFooter(String footer) {
        System.out.println("[ドットマトリックスプリンター] --- " + footer + " ---");
    }
}

// Abstraction
public abstract class Document {
    protected Printer printer;
    protected String title;
    protected String content;
    
    public Document(Printer printer, String title, String content) {
        this.printer = printer;
        this.title = title;
        this.content = content;
    }
    
    public abstract void print();
    
    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}

// RefinedAbstraction
public class ReportDocument extends Document {
    public ReportDocument(Printer printer, String title, String content) {
        super(printer, title, content);
    }
    
    @Override
    public void print() {
        printer.printHeader("レポート: " + title);
        printer.print(content);
        printer.printFooter("レポート終了");
    }
}

public class LetterDocument extends Document {
    public LetterDocument(Printer printer, String title, String content) {
        super(printer, title, content);
    }
    
    @Override
    public void print() {
        printer.printHeader("手紙: " + title);
        printer.print(content);
        printer.printFooter("以上");
    }
}

public class InvoiceDocument extends Document {
    private double amount;
    
    public InvoiceDocument(Printer printer, String title, String content, double amount) {
        super(printer, title, content);
        this.amount = amount;
    }
    
    @Override
    public void print() {
        printer.printHeader("請求書: " + title);
        printer.print(content);
        printer.print("金額: " + amount + "円");
        printer.printFooter("ご請求金額");
    }
}

// 使用例
public class DocumentPrinterExample {
    public static void main(String[] args) {
        // レポートをインクジェットプリンターで印刷
        Document report = new ReportDocument(
            new InkjetPrinter(),
            "月次レポート",
            "今月の売上は好調でした。"
        );
        report.print();
        
        System.out.println();
        
        // 手紙をレーザープリンターで印刷
        Document letter = new LetterDocument(
            new LaserPrinter(),
            "お客様各位",
            "いつもお世話になっております。"
        );
        letter.print();
        
        System.out.println();
        
        // 請求書をドットマトリックスプリンターで印刷
        Document invoice = new InvoiceDocument(
            new DotMatrixPrinter(),
            "2024年1月分",
            "商品代金",
            50000.0
        );
        invoice.print();
    }
}
```

### 例4: UIコンポーネントとプラットフォーム

UIコンポーネントとプラットフォームを独立して拡張できるシステムの例です。

```java
// Implementor
public interface Platform {
    void renderButton(String text);
    void renderWindow(String title);
    void renderMenu(String[] items);
}

// ConcreteImplementor
public class WindowsPlatform implements Platform {
    @Override
    public void renderButton(String text) {
        System.out.println("[Windows] ボタンを描画: " + text);
    }
    
    @Override
    public void renderWindow(String title) {
        System.out.println("[Windows] ウィンドウを描画: " + title);
    }
    
    @Override
    public void renderMenu(String[] items) {
        System.out.print("[Windows] メニューを描画: ");
        for (String item : items) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}

public class MacPlatform implements Platform {
    @Override
    public void renderButton(String text) {
        System.out.println("[Mac] ボタンを描画: " + text);
    }
    
    @Override
    public void renderWindow(String title) {
        System.out.println("[Mac] ウィンドウを描画: " + title);
    }
    
    @Override
    public void renderMenu(String[] items) {
        System.out.print("[Mac] メニューを描画: ");
        for (String item : items) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}

public class LinuxPlatform implements Platform {
    @Override
    public void renderButton(String text) {
        System.out.println("[Linux] ボタンを描画: " + text);
    }
    
    @Override
    public void renderWindow(String title) {
        System.out.println("[Linux] ウィンドウを描画: " + title);
    }
    
    @Override
    public void renderMenu(String[] items) {
        System.out.print("[Linux] メニューを描画: ");
        for (String item : items) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}

// Abstraction
public abstract class UIComponent {
    protected Platform platform;
    
    public UIComponent(Platform platform) {
        this.platform = platform;
    }
    
    public abstract void render();
    
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}

// RefinedAbstraction
public class Button extends UIComponent {
    private String text;
    
    public Button(Platform platform, String text) {
        super(platform);
        this.text = text;
    }
    
    @Override
    public void render() {
        platform.renderButton(text);
    }
    
    public void onClick() {
        System.out.println("ボタンがクリックされました: " + text);
    }
}

public class Window extends UIComponent {
    private String title;
    private UIComponent[] children;
    
    public Window(Platform platform, String title, UIComponent[] children) {
        super(platform);
        this.title = title;
        this.children = children;
    }
    
    @Override
    public void render() {
        platform.renderWindow(title);
        if (children != null) {
            for (UIComponent child : children) {
                child.render();
            }
        }
    }
}

public class Menu extends UIComponent {
    private String[] items;
    
    public Menu(Platform platform, String[] items) {
        super(platform);
        this.items = items;
    }
    
    @Override
    public void render() {
        platform.renderMenu(items);
    }
}

// 使用例
public class UIPlatformExample {
    public static void main(String[] args) {
        // Windowsプラットフォーム
        Platform windows = new WindowsPlatform();
        Button windowsButton = new Button(windows, "OK");
        Menu windowsMenu = new Menu(windows, new String[]{"ファイル", "編集", "表示"});
        Window windowsWindow = new WindowsPlatform(windows, "メインウィンドウ", 
            new UIComponent[]{windowsButton, windowsMenu});
        windowsWindow.render();
        
        System.out.println();
        
        // Macプラットフォーム
        Platform mac = new MacPlatform();
        Button macButton = new Button(mac, "キャンセル");
        Menu macMenu = new Menu(mac, new String[]{"File", "Edit", "View"});
        Window macWindow = new Window(mac, "Main Window", 
            new UIComponent[]{macButton, macMenu});
        macWindow.render();
    }
}
```

### 例5: データベース接続とドライバー

データベース接続とドライバーを独立して拡張できるシステムの例です。

```java
// Implementor
public interface DatabaseDriver {
    void connect(String url, String username, String password);
    void executeQuery(String query);
    void disconnect();
    String getDriverName();
}

// ConcreteImplementor
public class MySQLDriver implements DatabaseDriver {
    @Override
    public void connect(String url, String username, String password) {
        System.out.println("[MySQL] 接続: " + url + " (ユーザー: " + username + ")");
    }
    
    @Override
    public void executeQuery(String query) {
        System.out.println("[MySQL] クエリを実行: " + query);
    }
    
    @Override
    public void disconnect() {
        System.out.println("[MySQL] 切断");
    }
    
    @Override
    public String getDriverName() {
        return "MySQL";
    }
}

public class PostgreSQLDriver implements DatabaseDriver {
    @Override
    public void connect(String url, String username, String password) {
        System.out.println("[PostgreSQL] 接続: " + url + " (ユーザー: " + username + ")");
    }
    
    @Override
    public void executeQuery(String query) {
        System.out.println("[PostgreSQL] クエリを実行: " + query);
    }
    
    @Override
    public void disconnect() {
        System.out.println("[PostgreSQL] 切断");
    }
    
    @Override
    public String getDriverName() {
        return "PostgreSQL";
    }
}

public class OracleDriver implements DatabaseDriver {
    @Override
    public void connect(String url, String username, String password) {
        System.out.println("[Oracle] 接続: " + url + " (ユーザー: " + username + ")");
    }
    
    @Override
    public void executeQuery(String query) {
        System.out.println("[Oracle] クエリを実行: " + query);
    }
    
    @Override
    public void disconnect() {
        System.out.println("[Oracle] 切断");
    }
    
    @Override
    public String getDriverName() {
        return "Oracle";
    }
}

// Abstraction
public abstract class DatabaseConnection {
    protected DatabaseDriver driver;
    protected String url;
    protected String username;
    protected String password;
    
    public DatabaseConnection(DatabaseDriver driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    public abstract void open();
    public abstract void execute(String query);
    public abstract void close();
    
    public void setDriver(DatabaseDriver driver) {
        this.driver = driver;
    }
}

// RefinedAbstraction
public class BasicConnection extends DatabaseConnection {
    public BasicConnection(DatabaseDriver driver, String url, String username, String password) {
        super(driver, url, username, password);
    }
    
    @Override
    public void open() {
        driver.connect(url, username, password);
    }
    
    @Override
    public void execute(String query) {
        driver.executeQuery(query);
    }
    
    @Override
    public void close() {
        driver.disconnect();
    }
}

public class PooledConnection extends DatabaseConnection {
    private boolean isOpen = false;
    
    public PooledConnection(DatabaseDriver driver, String url, String username, String password) {
        super(driver, url, username, password);
    }
    
    @Override
    public void open() {
        if (!isOpen) {
            driver.connect(url, username, password);
            isOpen = true;
            System.out.println("[" + driver.getDriverName() + "] 接続プールから取得");
        } else {
            System.out.println("[" + driver.getDriverName() + "] 既に接続されています");
        }
    }
    
    @Override
    public void execute(String query) {
        if (isOpen) {
            driver.executeQuery(query);
        } else {
            System.out.println("[" + driver.getDriverName() + "] 接続が開かれていません");
        }
    }
    
    @Override
    public void close() {
        if (isOpen) {
            System.out.println("[" + driver.getDriverName() + "] 接続プールに返却");
            isOpen = false;
        }
    }
}

// 使用例
public class DatabaseConnectionExample {
    public static void main(String[] args) {
        // MySQL基本接続
        DatabaseConnection mysqlBasic = new BasicConnection(
            new MySQLDriver(),
            "jdbc:mysql://localhost:3306/mydb",
            "user",
            "password"
        );
        mysqlBasic.open();
        mysqlBasic.execute("SELECT * FROM users");
        mysqlBasic.close();
        
        System.out.println();
        
        // PostgreSQLプール接続
        DatabaseConnection postgresPooled = new PooledConnection(
            new PostgreSQLDriver(),
            "jdbc:postgresql://localhost:5432/mydb",
            "user",
            "password"
        );
        postgresPooled.open();
        postgresPooled.execute("SELECT * FROM products");
        postgresPooled.close();
    }
}
```

---

## まとめ

### 学習のポイント

1. **ブリッジパターンの目的**: 抽象化と実装を分離し、両者を独立して拡張可能にする
2. **基本的な構造**: Abstraction、RefinedAbstraction、Implementor、ConcreteImplementorの4つの主要コンポーネント
3. **クラス爆発の回避**: 組み合わせが増えてもクラス数の爆発的な増加を防ぐ
4. **実行時の結合**: 実行時に抽象化と実装を動的に結合可能

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 柔軟性、拡張性、疎結合、実行時の動的結合 |
| **注意点** | 複雑性の増加、理解の難しさ、過剰な設計の可能性 |
| **適用場面** | 複数のバリエーション、実装変更の影響最小化、プラットフォーム依存の分離など |

### ブリッジパターンを使わない場合の問題

| 問題 | 説明 |
|------|------|
| **クラス爆発** | 組み合わせごとにクラスを作成する必要があり、クラス数が爆発的に増加 |
| **保守性の低下** | 実装の変更が多くのクラスに影響を与える |
| **拡張性の欠如** | 新しい抽象化や実装を追加する際に、既存のコードを大幅に変更する必要がある |

### 他のパターンとの関係

- **Adapter**: ブリッジは設計時に分離するが、アダプターは既存のインターフェースに適合させる
- **Strategy**: ブリッジは構造的な分離に焦点を当てるが、ストラテジーはアルゴリズムの選択に焦点を当てる
- **State**: ブリッジは抽象化と実装の分離に焦点を当てるが、ステートは状態の変化に焦点を当てる

### 注意点

1. **適切な使用場面**: 抽象化と実装の両方に複数のバリエーションがある場合に使用
2. **過剰な設計を避ける**: シンプルなケースでは過剰な設計になる可能性がある
3. **理解の促進**: チームメンバーがパターンを理解できるように、適切なドキュメントを作成
4. **パフォーマンス**: 抽象化と実装の分離によるわずかなオーバーヘッドを考慮

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. 実際のプロジェクトでブリッジパターンを適用してみる
3. Adapterパターンを学習する（既存インターフェースへの適合）
4. Strategyパターンを学習する（アルゴリズムの選択）

### 参考資料

- [cs-techblog.com - ブリッジパターン](https://cs-techblog.com/technical/bridge-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、ブリッジパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
