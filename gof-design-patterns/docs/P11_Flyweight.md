# フライウェイトパターン（Flyweight Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [フライウェイトパターンとは](#フライウェイトパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

フライウェイトパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**構造に関するパターン（Structural Pattern）**に分類されます。

このパターンは、大量のオブジェクトを効率的に扱い、メモリ使用量を最小化するために設計されています。同じデータを共有することで、冗長なオブジェクトの作成を避け、システムのパフォーマンスを向上させます。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- フライウェイトパターンの目的と利点を理解する
- 内在的状態（Intrinsic State）と外在的状態（Extrinsic State）の概念を理解する
- 基本的なフライウェイトパターンの実装方法を理解する
- 実際のプロジェクトでフライウェイトパターンを適用できる

---

## フライウェイトパターンとは

### 定義

フライウェイトパターンは、オブジェクトのインスタンス生成に伴うメモリコストを削減するために利用されるデザインパターンです。多くのオブジェクトを共有してメモリ使用量を最小化し、冗長なオブジェクトを作成することなく必要な機能を提供することが主な目的です。

### 主な特徴

1. **オブジェクトの共有**: 同じデータを持つオブジェクトを共有して再利用
2. **メモリ効率**: 大量のオブジェクトを扱う際のメモリ使用量を削減
3. **状態の分離**: 内在的状態（共有可能）と外在的状態（個別）を分離
4. **ファクトリー管理**: フライウェイトオブジェクトの生成と管理をファクトリーで行う

### 使用される場面

フライウェイトパターンは、以下のような場面で使用されます：

- **テキストエディタ**: 大量の文字オブジェクトを扱う際、同じ文字を共有
- **ゲーム開発**: 大量の同じキャラクターやアイテムを扱う際
- **グラフィックシステム**: 同じ形状や色の図形を大量に描画する際
- **文書処理**: 同じフォントやスタイルの文字を扱う際
- **Webアプリケーション**: 同じアイコンや画像を大量に表示する際

### メリット

- **メモリ消費の削減**: 同じデータを持つオブジェクトを共有することで、メモリ使用量を大幅に削減
- **パフォーマンス向上**: オブジェクト生成のコストを削減し、システムのパフォーマンスを向上
- **スケーラビリティ**: 大量のオブジェクトを扱うアプリケーションで有効

### デメリット

- **複雑性の増加**: 内在的状態と外在的状態の管理が複雑になる
- **コードの可読性**: パターンを理解していない開発者には複雑に見える
- **状態の分離**: 状態の分離を正確に行う必要がある

---

## 基本的な実装

### 実装のポイント

フライウェイトパターンを実装するには、以下の要素が必要です：

1. **フライウェイトインターフェース（Flyweight）**: フライウェイトオブジェクトの共通インターフェース
2. **具象フライウェイトクラス（ConcreteFlyweight）**: 内在的状態を保持するクラス
3. **フライウェイトファクトリー（FlyweightFactory）**: フライウェイトオブジェクトの生成と管理を行うクラス
4. **クライアント**: フライウェイトオブジェクトを使用し、外在的状態を提供

### 状態の分離

フライウェイトパターンでは、オブジェクトの状態を2つに分けます：

- **内在的状態（Intrinsic State）**: オブジェクト間で共有可能な不変の状態
- **外在的状態（Extrinsic State）**: 各オブジェクトに固有の可変の状態

### 基本的な実装例

```java
// 1. フライウェイトインターフェース（Flyweight）
public interface Shape {
    void draw(int x, int y, int radius);
}

// 2. 具象フライウェイトクラス（ConcreteFlyweight）
public class Circle implements Shape {
    // 内在的状態（共有可能な不変の状態）
    private String color;
    
    public Circle(String color) {
        this.color = color;
        System.out.println("Creating circle of color: " + color);
    }
    
    // 外在的状態（x, y, radius）はメソッドのパラメータとして受け取る
    @Override
    public void draw(int x, int y, int radius) {
        System.out.println("Circle: Draw() [Color: " + color + 
                         ", x: " + x + ", y: " + y + ", radius: " + radius + "]");
    }
}

// 3. フライウェイトファクトリー（FlyweightFactory）
import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {
    // フライウェイトオブジェクトを保持するキャッシュ
    private static final Map<String, Shape> circleMap = new HashMap<>();
    
    public static Shape getCircle(String color) {
        Circle circle = (Circle) circleMap.get(color);
        
        // 同じ色のCircleが存在しない場合のみ新規作成
        if (circle == null) {
            circle = new Circle(color);
            circleMap.put(color, circle);
        }
        
        return circle;
    }
    
    // キャッシュ内のオブジェクト数を取得（デバッグ用）
    public static int getCircleCount() {
        return circleMap.size();
    }
}

// 4. クライアントコード
public class FlyweightPatternDemo {
    private static final String[] colors = {"Red", "Green", "Blue", "White", "Black"};
    
    public static void main(String[] args) {
        System.out.println("=== フライウェイトパターンのデモ ===");
        
        // 20個のCircleを作成（実際には5色しかないので、同じ色のCircleは共有される）
        for (int i = 0; i < 20; i++) {
            Circle circle = (Circle) ShapeFactory.getCircle(getRandomColor());
            circle.draw(getRandomX(), getRandomY(), 100);
        }
        
        System.out.println("\n実際に作成されたCircleオブジェクトの数: " + 
                          ShapeFactory.getCircleCount());
        System.out.println("（20個のCircleを描画したが、実際には5個のオブジェクトのみ作成）");
    }
    
    private static String getRandomColor() {
        return colors[(int) (Math.random() * colors.length)];
    }
    
    private static int getRandomX() {
        return (int) (Math.random() * 100);
    }
    
    private static int getRandomY() {
        return (int) (Math.random() * 100);
    }
}
```

### 使用例の出力

```
=== フライウェイトパターンのデモ ===
Creating circle of color: Red
Circle: Draw() [Color: Red, x: 45, y: 23, radius: 100]
Creating circle of color: Green
Circle: Draw() [Color: Green, x: 67, y: 12, radius: 100]
Creating circle of color: Blue
Circle: Draw() [Color: Blue, x: 34, y: 56, radius: 100]
Circle: Draw() [Color: Red, x: 78, y: 90, radius: 100]
Creating circle of color: White
Circle: Draw() [Color: White, x: 12, y: 45, radius: 100]
...
（中略）
...

実際に作成されたCircleオブジェクトの数: 5
（20個のCircleを描画したが、実際には5個のオブジェクトのみ作成）
```

### パターンの構造

```
Client
  ↓
FlyweightFactory
  ├─ getFlyweight(key) → Flyweight
  └─ cache: Map<String, Flyweight>
      ↓
Flyweight (インターフェース)
  └─ operation(extrinsicState)
      ↓
ConcreteFlyweight
  ├─ intrinsicState (共有状態)
  └─ operation(extrinsicState) (外在的状態をパラメータで受け取る)
```

---

## 実装のバリエーション

### バリエーション1: 複数の内在的状態を持つ

複数の内在的状態を持つフライウェイトオブジェクトの例です。

```java
public interface Character {
    void display(int x, int y, String font);
}

public class ConcreteCharacter implements Character {
    // 複数の内在的状態
    private char symbol;
    private int size;
    private String color;
    
    public ConcreteCharacter(char symbol, int size, String color) {
        this.symbol = symbol;
        this.size = size;
        this.color = color;
    }
    
    @Override
    public void display(int x, int y, String font) {
        System.out.println("Character: " + symbol + 
                         " [Size: " + size + ", Color: " + color + 
                         ", Font: " + font + ", Position: (" + x + ", " + y + ")]");
    }
}

public class CharacterFactory {
    private static final Map<String, Character> characterCache = new HashMap<>();
    
    public static Character getCharacter(char symbol, int size, String color) {
        String key = symbol + "_" + size + "_" + color;
        Character character = characterCache.get(key);
        
        if (character == null) {
            character = new ConcreteCharacter(symbol, size, color);
            characterCache.put(key, character);
        }
        
        return character;
    }
}
```

### バリエーション2: 不変オブジェクトとして実装

フライウェイトオブジェクトを不変（Immutable）として実装する方法です。

```java
// 不変クラスとして実装
public final class ImmutableCircle implements Shape {
    private final String color;
    
    public ImmutableCircle(String color) {
        this.color = color;
    }
    
    @Override
    public void draw(int x, int y, int radius) {
        System.out.println("Circle: Draw() [Color: " + color + 
                         ", x: " + x + ", y: " + y + ", radius: " + radius + "]");
    }
    
    // getterメソッド（必要に応じて）
    public String getColor() {
        return color;
    }
}
```

### バリエーション3: スレッドセーフなファクトリー

マルチスレッド環境に対応したファクトリーの実装です。

```java
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ThreadSafeShapeFactory {
    private static final Map<String, Shape> circleMap = new ConcurrentHashMap<>();
    
    public static Shape getCircle(String color) {
        // ConcurrentHashMapのcomputeIfAbsentを使用してスレッドセーフに
        return circleMap.computeIfAbsent(color, k -> {
            System.out.println("Creating circle of color: " + k);
            return new Circle(k);
        });
    }
    
    public static int getCircleCount() {
        return circleMap.size();
    }
}
```

---

## 実践例

### 例1: テキストエディタの文字管理

テキストエディタで大量の文字を扱う際の例です。

```java
// フライウェイトインターフェース
public interface TextCharacter {
    void display(int row, int column, String font);
}

// 具象フライウェイトクラス
public class Character implements TextCharacter {
    // 内在的状態（文字コード）
    private char charCode;
    
    public Character(char charCode) {
        this.charCode = charCode;
    }
    
    @Override
    public void display(int row, int column, String font) {
        System.out.println("Character '" + charCode + "' at (" + row + ", " + column + 
                         ") with font: " + font);
    }
    
    public char getCharCode() {
        return charCode;
    }
}

// フライウェイトファクトリー
public class CharacterFactory {
    private static final Map<Character, TextCharacter> characterCache = new HashMap<>();
    
    public static TextCharacter getCharacter(char charCode) {
        TextCharacter character = characterCache.get(charCode);
        
        if (character == null) {
            character = new Character(charCode);
            characterCache.put(charCode, character);
            System.out.println("Creating new character: " + charCode);
        }
        
        return character;
    }
    
    public static int getCharacterCount() {
        return characterCache.size();
    }
}

// テキストエディタ（クライアント）
public class TextEditor {
    private List<TextCharacter> characters = new ArrayList<>();
    private List<String> positions = new ArrayList<>(); // 外在的状態（位置情報）
    private List<String> fonts = new ArrayList<>();    // 外在的状態（フォント情報）
    
    public void addCharacter(char charCode, int row, int column, String font) {
        TextCharacter character = CharacterFactory.getCharacter(charCode);
        characters.add(character);
        positions.add(row + "," + column);
        fonts.add(font);
    }
    
    public void display() {
        for (int i = 0; i < characters.size(); i++) {
            String[] pos = positions.get(i).split(",");
            int row = Integer.parseInt(pos[0]);
            int column = Integer.parseInt(pos[1]);
            characters.get(i).display(row, column, fonts.get(i));
        }
    }
}

// 使用例
public class TextEditorExample {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        
        // "Hello World"という文字列を追加（同じ文字は共有される）
        String text = "Hello World";
        int row = 0;
        int column = 0;
        
        for (char c : text.toCharArray()) {
            editor.addCharacter(c, row, column++, "Arial");
            if (c == ' ') {
                row++;
                column = 0;
            }
        }
        
        editor.display();
        System.out.println("\n実際に作成された文字オブジェクトの数: " + 
                          CharacterFactory.getCharacterCount());
        System.out.println("（'Hello World'は11文字だが、実際には8種類の文字のみ）");
    }
}
```

### 例2: ゲームのキャラクター管理

ゲームで大量の同じタイプのキャラクターを扱う例です。

```java
// フライウェイトインターフェース
public interface GameCharacter {
    void render(int x, int y, int health);
}

// 具象フライウェイトクラス
public class Soldier implements GameCharacter {
    // 内在的状態（キャラクターの種類固有の情報）
    private String type;
    private String weapon;
    private int baseHealth;
    
    public Soldier(String type, String weapon, int baseHealth) {
        this.type = type;
        this.weapon = weapon;
        this.baseHealth = baseHealth;
    }
    
    @Override
    public void render(int x, int y, int health) {
        System.out.println("Soldier [Type: " + type + ", Weapon: " + weapon + 
                         ", Base Health: " + baseHealth + 
                         ", Position: (" + x + ", " + y + "), Current Health: " + health + "]");
    }
}

// フライウェイトファクトリー
public class GameCharacterFactory {
    private static final Map<String, GameCharacter> characterCache = new HashMap<>();
    
    public static GameCharacter getSoldier(String type, String weapon, int baseHealth) {
        String key = type + "_" + weapon + "_" + baseHealth;
        GameCharacter character = characterCache.get(key);
        
        if (character == null) {
            character = new Soldier(type, weapon, baseHealth);
            characterCache.put(key, character);
            System.out.println("Creating new soldier: " + key);
        }
        
        return character;
    }
    
    public static int getCharacterCount() {
        return characterCache.size();
    }
}

// ゲームマネージャー（クライアント）
public class GameManager {
    private List<GameCharacter> characters = new ArrayList<>();
    private List<int[]> positions = new ArrayList<>(); // 外在的状態
    private List<Integer> healths = new ArrayList<>(); // 外在的状態
    
    public void addSoldier(String type, String weapon, int baseHealth, int x, int y, int health) {
        GameCharacter soldier = GameCharacterFactory.getSoldier(type, weapon, baseHealth);
        characters.add(soldier);
        positions.add(new int[]{x, y});
        healths.add(health);
    }
    
    public void renderAll() {
        for (int i = 0; i < characters.size(); i++) {
            int[] pos = positions.get(i);
            characters.get(i).render(pos[0], pos[1], healths.get(i));
        }
    }
}

// 使用例
public class GameExample {
    public static void main(String[] args) {
        GameManager manager = new GameManager();
        
        // 100人の兵士を追加（実際には3種類のみ）
        for (int i = 0; i < 100; i++) {
            String type = (i % 3 == 0) ? "Infantry" : (i % 3 == 1) ? "Archer" : "Cavalry";
            String weapon = (i % 3 == 0) ? "Sword" : (i % 3 == 1) ? "Bow" : "Spear";
            int baseHealth = (i % 3 == 0) ? 100 : (i % 3 == 1) ? 80 : 120;
            
            manager.addSoldier(type, weapon, baseHealth, i * 10, i * 5, baseHealth - (i % 20));
        }
        
        System.out.println("=== 最初の10人の兵士を表示 ===");
        // 最初の10人だけ表示
        for (int i = 0; i < 10; i++) {
            GameCharacter soldier = manager.getCharacters().get(i);
            int[] pos = manager.getPositions().get(i);
            soldier.render(pos[0], pos[1], manager.getHealths().get(i));
        }
        
        System.out.println("\n実際に作成された兵士オブジェクトの数: " + 
                          GameCharacterFactory.getCharacterCount());
        System.out.println("（100人の兵士を追加したが、実際には3種類のオブジェクトのみ作成）");
    }
}
```

### 例3: アイコン管理システム

Webアプリケーションで同じアイコンを大量に表示する際の例です。

```java
// フライウェイトインターフェース
public interface Icon {
    void display(int x, int y, int size);
}

// 具象フライウェイトクラス
public class ConcreteIcon implements Icon {
    // 内在的状態（アイコンの種類）
    private String iconName;
    private String iconPath;
    
    public ConcreteIcon(String iconName, String iconPath) {
        this.iconName = iconName;
        this.iconPath = iconPath;
        System.out.println("Loading icon: " + iconName + " from " + iconPath);
    }
    
    @Override
    public void display(int x, int y, int size) {
        System.out.println("Displaying icon '" + iconName + "' at (" + x + ", " + y + 
                         ") with size: " + size + "px");
    }
    
    public String getIconName() {
        return iconName;
    }
}

// フライウェイトファクトリー
public class IconFactory {
    private static final Map<String, Icon> iconCache = new HashMap<>();
    
    public static Icon getIcon(String iconName) {
        Icon icon = iconCache.get(iconName);
        
        if (icon == null) {
            // 実際のアプリケーションでは、ここでアイコンファイルを読み込む
            String iconPath = "/icons/" + iconName + ".png";
            icon = new ConcreteIcon(iconName, iconPath);
            iconCache.put(iconName, icon);
        }
        
        return icon;
    }
    
    public static int getIconCount() {
        return iconCache.size();
    }
    
    public static void clearCache() {
        iconCache.clear();
    }
}

// UIコンポーネント（クライアント）
public class UIComponent {
    private List<Icon> icons = new ArrayList<>();
    private List<int[]> positions = new ArrayList<>();
    private List<Integer> sizes = new ArrayList<>();
    
    public void addIcon(String iconName, int x, int y, int size) {
        Icon icon = IconFactory.getIcon(iconName);
        icons.add(icon);
        positions.add(new int[]{x, y});
        sizes.add(size);
    }
    
    public void render() {
        for (int i = 0; i < icons.size(); i++) {
            int[] pos = positions.get(i);
            icons.get(i).display(pos[0], pos[1], sizes.get(i));
        }
    }
}

// 使用例
public class IconExample {
    public static void main(String[] args) {
        UIComponent component = new UIComponent();
        
        // 100個のアイコンを追加（実際には5種類のみ）
        String[] iconNames = {"home", "user", "settings", "search", "menu"};
        
        for (int i = 0; i < 100; i++) {
            String iconName = iconNames[i % iconNames.length];
            component.addIcon(iconName, i * 20, i * 20, 16 + (i % 3) * 8);
        }
        
        System.out.println("=== 最初の10個のアイコンを表示 ===");
        // 最初の10個だけ表示
        for (int i = 0; i < 10; i++) {
            Icon icon = component.getIcons().get(i);
            int[] pos = component.getPositions().get(i);
            icon.display(pos[0], pos[1], component.getSizes().get(i));
        }
        
        System.out.println("\n実際に読み込まれたアイコンオブジェクトの数: " + 
                          IconFactory.getIconCount());
        System.out.println("（100個のアイコンを表示したが、実際には5種類のアイコンのみ読み込み）");
    }
}
```

---

## まとめ

### 学習のポイント

1. **フライウェイトパターンの目的**: 大量のオブジェクトを効率的に扱い、メモリ使用量を削減
2. **状態の分離**: 内在的状態（共有可能）と外在的状態（個別）を明確に分離
3. **ファクトリーの役割**: フライウェイトオブジェクトの生成と管理を一元化
4. **適用場面**: 大量の同じデータを持つオブジェクトを扱う場合に有効

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | メモリ消費の削減、パフォーマンス向上、スケーラビリティ |
| **注意点** | 複雑性の増加、コードの可読性、状態の分離の正確性 |
| **適用場面** | テキストエディタ、ゲーム開発、グラフィックシステム、文書処理など |

### 他のパターンとの関係

- **Factory Method**: フライウェイトファクトリーでファクトリーメソッドパターンを使用
- **Singleton**: フライウェイトファクトリーをシングルトンとして実装することもある
- **Composite**: フライウェイトオブジェクトをコンポジット構造で管理することがある

### 実装のベストプラクティス

1. **不変オブジェクト**: フライウェイトオブジェクトは不変（Immutable）として設計する
2. **スレッドセーフ**: マルチスレッド環境では、スレッドセーフなコレクションを使用
3. **キャッシュ管理**: メモリ使用量を監視し、必要に応じてキャッシュをクリア
4. **状態の明確な分離**: 内在的状態と外在的状態を明確に区別する

### 注意点

1. **過度な使用を避ける**: オブジェクト数が少ない場合や、状態が複雑な場合は適用しない
2. **状態の分離**: 内在的状態と外在的状態の分離を正確に行う必要がある
3. **メモリ管理**: キャッシュが大きくなりすぎないように注意する

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. メモリ使用量を測定し、パターンの効果を確認する
3. 実際のプロジェクトでフライウェイトパターンを適用してみる
4. 他の構造に関するパターン（Adapter、Bridge、Compositeなど）を学習する

### 参考資料

- [cs-techblog.com - フライウェイトパターン](https://cs-techblog.com/technical/flyweight-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「Effective Java」（Joshua Bloch著）

---

**注意**: この学習プランは、フライウェイトパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
