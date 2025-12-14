# メメントパターン（Memento Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [メメントパターンとは](#メメントパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

メメントパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**振る舞いに関するパターン（Behavioral Pattern）**に分類されます。

このパターンは、オブジェクトの内部状態を保存し、後で復元できるようにする方法を提供します。オブジェクトの内部構造を公開せずに状態を保存・復元できるため、カプセル化を維持しながらアンドゥ/リドゥ機能などを実装できます。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- メメントパターンの目的と利点を理解する
- 基本的なメメントパターンの実装方法を理解する
- オブジェクトの状態を保存・復元する方法を理解する
- アンドゥ/リドゥ機能を実装できる
- 実際のプロジェクトでメメントパターンを適用できる

---

## メメントパターンとは

### 定義

メメントパターンは、オブジェクトの内部状態を保存し、後で復元できるようにするデザインパターンです。オブジェクトの内部構造を公開せずに状態を保存・復元できるため、カプセル化を維持しながら状態管理を行えます。

### 主な特徴

1. **状態の保存**: オブジェクトの内部状態を外部に公開せずに保存
2. **カプセル化の維持**: オブジェクトの内部構造を隠蔽したまま状態を管理
3. **復元機能**: 保存した状態を後で復元可能
4. **履歴管理**: 複数の状態を保存し、履歴として管理可能

### 使用される場面

メメントパターンは、以下のような場面で使用されます：

- **アンドゥ/リドゥ機能**: テキストエディタ、画像編集ソフトなどでの操作の取り消し・やり直し
- **ゲームのセーブ/ロード**: ゲームの状態を保存・復元
- **設定の保存**: アプリケーション設定の保存・復元
- **トランザクション管理**: データベースのロールバック機能
- **チェックポイント**: 長時間実行される処理のチェックポイント機能
- **状態のスナップショット**: システムの状態をスナップショットとして保存

### メリット

- **カプセル化の維持**: オブジェクトの内部構造を公開せずに状態を保存・復元
- **アンドゥ/リドゥ機能**: 操作の履歴を管理し、取り消し・やり直しを実装可能
- **状態の分離**: 状態の保存・復元ロジックをオブジェクトから分離
- **柔軟性**: 複数の状態を保存し、任意の時点に戻ることが可能

### デメリット

- **メモリ使用量**: 多くの状態を保存すると、メモリ使用量が増加
- **パフォーマンス**: 状態の保存・復元に時間がかかる場合がある
- **複雑性の増加**: シンプルなケースでは過剰な設計になる可能性
- **深いコピーの必要性**: 複雑なオブジェクトの状態を保存する際に、ディープコピーが必要な場合がある

---

## 基本的な実装

### 実装のポイント

メメントパターンを実装するには、以下の要素が必要です：

1. **Memento（メメント）**: オブジェクトの内部状態を保存するオブジェクト
2. **Originator（オリジネーター）**: 状態を保存・復元するオブジェクト
3. **Caretaker（ケアテイカー）**: Mementoを管理し、Originatorに状態の保存・復元を要求するオブジェクト

### 基本的な実装例

```java
// 1. Memento（メメント）
public class Memento {
    private final String state;
    
    // MementoはOriginatorからのみ作成可能（パッケージプライベートまたは内部クラス）
    Memento(String state) {
        this.state = state;
    }
    
    String getState() {
        return state;
    }
}

// 2. Originator（オリジネーター）
public class Originator {
    private String state;
    
    public void setState(String state) {
        System.out.println("状態を設定: " + state);
        this.state = state;
    }
    
    public String getState() {
        return state;
    }
    
    // 現在の状態をMementoとして保存
    public Memento saveStateToMemento() {
        System.out.println("状態をMementoに保存: " + state);
        return new Memento(state);
    }
    
    // Mementoから状態を復元
    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
        System.out.println("Mementoから状態を復元: " + state);
    }
}

// 3. Caretaker（ケアテイカー）
import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private List<Memento> mementoList = new ArrayList<>();
    
    public void add(Memento state) {
        mementoList.add(state);
    }
    
    public Memento get(int index) {
        return mementoList.get(index);
    }
    
    public int getSize() {
        return mementoList.size();
    }
}
```

### 使用例

```java
public class MementoExample {
    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
        
        // 状態1を設定して保存
        originator.setState("状態1");
        caretaker.add(originator.saveStateToMemento());
        
        // 状態2を設定して保存
        originator.setState("状態2");
        caretaker.add(originator.saveStateToMemento());
        
        // 状態3を設定
        originator.setState("状態3");
        System.out.println("現在の状態: " + originator.getState());
        
        // 状態2に戻す
        originator.getStateFromMemento(caretaker.get(1));
        System.out.println("復元後の状態: " + originator.getState());
        
        // 状態1に戻す
        originator.getStateFromMemento(caretaker.get(0));
        System.out.println("復元後の状態: " + originator.getState());
    }
}
```

### パターンの構造

```
Originator
  ├─ state: 内部状態
  ├─ saveStateToMemento(): Mementoを作成
  └─ getStateFromMemento(Memento): 状態を復元
        ↓
Memento
  └─ state: 保存された状態
        ↑
Caretaker
  └─ mementoList: Mementoのリストを管理
```

---

## 実装のバリエーション

### バリエーション1: 内部クラスを使用したMemento

MementoをOriginatorの内部クラスとして定義し、外部からのアクセスを制限する方法です。

```java
// Originator
public class Originator {
    private String state;
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getState() {
        return state;
    }
    
    // Mementoを作成（内部クラス）
    public Memento saveStateToMemento() {
        return new Memento(state);
    }
    
    // Mementoから状態を復元
    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
    }
    
    // 内部クラスとしてMementoを定義
    public static class Memento {
        private final String state;
        
        // パッケージプライベートコンストラクタ
        Memento(String state) {
            this.state = state;
        }
        
        // パッケージプライベートゲッター
        String getState() {
            return state;
        }
    }
}

// Caretaker
public class Caretaker {
    private List<Originator.Memento> mementoList = new ArrayList<>();
    
    public void add(Originator.Memento memento) {
        mementoList.add(memento);
    }
    
    public Originator.Memento get(int index) {
        return mementoList.get(index);
    }
}
```

### バリエーション2: インターフェースを使用したMemento

Mementoをインターフェースとして定義し、実装を隠蔽する方法です。

```java
// Mementoインターフェース
public interface Memento {
    // インターフェースは空でも良い（型安全性のため）
}

// Originator
public class Originator {
    private String state;
    
    public void setState(String state) {
        this.state = state;
    }
    
    public Memento saveStateToMemento() {
        return new OriginatorMemento(state);
    }
    
    public void getStateFromMemento(Memento memento) {
        if (memento instanceof OriginatorMemento) {
            OriginatorMemento om = (OriginatorMemento) memento;
            state = om.getState();
        }
    }
    
    // 内部クラスで実装
    private static class OriginatorMemento implements Memento {
        private final String state;
        
        private OriginatorMemento(String state) {
            this.state = state;
        }
        
        private String getState() {
            return state;
        }
    }
}
```

### バリエーション3: 複数の状態を保存するMemento

複数のフィールドの状態を保存するMementoです。

```java
// Memento
public class ComplexMemento {
    private final String state1;
    private final int state2;
    private final boolean state3;
    
    ComplexMemento(String state1, int state2, boolean state3) {
        this.state1 = state1;
        this.state2 = state2;
        this.state3 = state3;
    }
    
    String getState1() {
        return state1;
    }
    
    int getState2() {
        return state2;
    }
    
    boolean getState3() {
        return state3;
    }
}

// Originator
public class ComplexOriginator {
    private String state1;
    private int state2;
    private boolean state3;
    
    public void setState(String state1, int state2, boolean state3) {
        this.state1 = state1;
        this.state2 = state2;
        this.state3 = state3;
    }
    
    public ComplexMemento saveStateToMemento() {
        return new ComplexMemento(state1, state2, state3);
    }
    
    public void getStateFromMemento(ComplexMemento memento) {
        this.state1 = memento.getState1();
        this.state2 = memento.getState2();
        this.state3 = memento.getState3();
    }
    
    @Override
    public String toString() {
        return "State1: " + state1 + ", State2: " + state2 + ", State3: " + state3;
    }
}
```

### バリエーション4: スタックベースのアンドゥ/リドゥ

スタックを使用してアンドゥ/リドゥ機能を実装する方法です。

```java
import java.util.Stack;

public class UndoRedoCaretaker {
    private Stack<Memento> undoStack = new Stack<>();
    private Stack<Memento> redoStack = new Stack<>();
    
    public void saveState(Memento memento) {
        undoStack.push(memento);
        redoStack.clear(); // 新しい操作が行われたので、リドゥスタックをクリア
    }
    
    public Memento undo(Memento currentState) {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentState);
            return undoStack.pop();
        }
        return null;
    }
    
    public Memento redo(Memento currentState) {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentState);
            return redoStack.pop();
        }
        return null;
    }
    
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}

// 使用例
public class UndoRedoExample {
    public static void main(String[] args) {
        Originator originator = new Originator();
        UndoRedoCaretaker caretaker = new UndoRedoCaretaker();
        
        // 操作1
        originator.setState("状態1");
        caretaker.saveState(originator.saveStateToMemento());
        
        // 操作2
        originator.setState("状態2");
        caretaker.saveState(originator.saveStateToMemento());
        
        // 操作3
        originator.setState("状態3");
        caretaker.saveState(originator.saveStateToMemento());
        
        // アンドゥ
        if (caretaker.canUndo()) {
            Memento memento = caretaker.undo(originator.saveStateToMemento());
            if (memento != null) {
                originator.getStateFromMemento(memento);
                System.out.println("アンドゥ後: " + originator.getState());
            }
        }
        
        // リドゥ
        if (caretaker.canRedo()) {
            Memento memento = caretaker.redo(originator.saveStateToMemento());
            if (memento != null) {
                originator.getStateFromMemento(memento);
                System.out.println("リドゥ後: " + originator.getState());
            }
        }
    }
}
```

---

## 実践例

### 例1: テキストエディタのアンドゥ/リドゥ機能

テキストエディタのアンドゥ/リドゥ機能を実装する例です。

```java
import java.util.Stack;

// Memento
public class TextMemento {
    private final String text;
    private final int cursorPosition;
    
    TextMemento(String text, int cursorPosition) {
        this.text = text;
        this.cursorPosition = cursorPosition;
    }
    
    String getText() {
        return text;
    }
    
    int getCursorPosition() {
        return cursorPosition;
    }
}

// Originator
public class TextEditor {
    private String text = "";
    private int cursorPosition = 0;
    
    public void setText(String text) {
        this.text = text;
        this.cursorPosition = text.length();
    }
    
    public void appendText(String additionalText) {
        text += additionalText;
        cursorPosition = text.length();
    }
    
    public void setCursorPosition(int position) {
        if (position >= 0 && position <= text.length()) {
            cursorPosition = position;
        }
    }
    
    public String getText() {
        return text;
    }
    
    public int getCursorPosition() {
        return cursorPosition;
    }
    
    public TextMemento saveState() {
        return new TextMemento(text, cursorPosition);
    }
    
    public void restoreState(TextMemento memento) {
        this.text = memento.getText();
        this.cursorPosition = memento.getCursorPosition();
    }
    
    public void display() {
        System.out.println("テキスト: " + text);
        System.out.println("カーソル位置: " + cursorPosition);
    }
}

// Caretaker
public class TextEditorCaretaker {
    private Stack<TextMemento> undoStack = new Stack<>();
    private Stack<TextMemento> redoStack = new Stack<>();
    
    public void saveState(TextMemento memento) {
        undoStack.push(memento);
        redoStack.clear();
    }
    
    public TextMemento undo(TextMemento currentState) {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentState);
            return undoStack.pop();
        }
        return null;
    }
    
    public TextMemento redo(TextMemento currentState) {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentState);
            return redoStack.pop();
        }
        return null;
    }
    
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}

// 使用例
public class TextEditorExample {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        TextEditorCaretaker caretaker = new TextEditorCaretaker();
        
        // 初期状態を保存
        caretaker.saveState(editor.saveState());
        
        // テキストを追加
        editor.appendText("Hello");
        caretaker.saveState(editor.saveState());
        editor.display();
        
        // さらにテキストを追加
        editor.appendText(" World");
        caretaker.saveState(editor.saveState());
        editor.display();
        
        // アンドゥ
        if (caretaker.canUndo()) {
            TextMemento memento = caretaker.undo(editor.saveState());
            if (memento != null) {
                editor.restoreState(memento);
                System.out.println("\nアンドゥ後:");
                editor.display();
            }
        }
        
        // リドゥ
        if (caretaker.canRedo()) {
            TextMemento memento = caretaker.redo(editor.saveState());
            if (memento != null) {
                editor.restoreState(memento);
                System.out.println("\nリドゥ後:");
                editor.display();
            }
        }
    }
}
```

### 例2: ゲームのセーブ/ロード機能

ゲームの状態を保存・復元する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Memento
public class GameMemento {
    private final int level;
    private final int score;
    private final int health;
    private final List<String> inventory;
    
    GameMemento(int level, int score, int health, List<String> inventory) {
        this.level = level;
        this.score = score;
        this.health = health;
        this.inventory = new ArrayList<>(inventory); // コピーを作成
    }
    
    int getLevel() {
        return level;
    }
    
    int getScore() {
        return score;
    }
    
    int getHealth() {
        return health;
    }
    
    List<String> getInventory() {
        return new ArrayList<>(inventory); // コピーを返す
    }
}

// Originator
public class Game {
    private int level = 1;
    private int score = 0;
    private int health = 100;
    private List<String> inventory = new ArrayList<>();
    
    public void play() {
        level++;
        score += 100;
        health -= 10;
        System.out.println("ゲーム進行: レベル=" + level + ", スコア=" + score + ", 体力=" + health);
    }
    
    public void addItem(String item) {
        inventory.add(item);
        System.out.println("アイテムを追加: " + item);
    }
    
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
        System.out.println("ダメージを受けた: 体力=" + health);
    }
    
    public GameMemento save() {
        System.out.println("ゲームの状態を保存");
        return new GameMemento(level, score, health, inventory);
    }
    
    public void load(GameMemento memento) {
        this.level = memento.getLevel();
        this.score = memento.getScore();
        this.health = memento.getHealth();
        this.inventory = memento.getInventory();
        System.out.println("ゲームの状態を復元: レベル=" + level + ", スコア=" + score + ", 体力=" + health);
    }
    
    public void displayStatus() {
        System.out.println("=== ゲーム状態 ===");
        System.out.println("レベル: " + level);
        System.out.println("スコア: " + score);
        System.out.println("体力: " + health);
        System.out.println("インベントリ: " + inventory);
    }
}

// Caretaker
public class GameSaveManager {
    private List<GameMemento> saves = new ArrayList<>();
    
    public void saveGame(GameMemento memento) {
        saves.add(memento);
        System.out.println("セーブスロット " + saves.size() + " に保存");
    }
    
    public GameMemento loadGame(int slot) {
        if (slot > 0 && slot <= saves.size()) {
            System.out.println("セーブスロット " + slot + " から読み込み");
            return saves.get(slot - 1);
        }
        return null;
    }
    
    public int getSaveCount() {
        return saves.size();
    }
}

// 使用例
public class GameExample {
    public static void main(String[] args) {
        Game game = new Game();
        GameSaveManager saveManager = new GameSaveManager();
        
        // ゲーム開始
        game.displayStatus();
        
        // セーブ1
        game.play();
        game.addItem("剣");
        saveManager.saveGame(game.save());
        
        // セーブ2
        game.play();
        game.addItem("盾");
        game.takeDamage(20);
        saveManager.saveGame(game.save());
        
        // セーブ1から復元
        GameMemento memento = saveManager.loadGame(1);
        if (memento != null) {
            game.load(memento);
            game.displayStatus();
        }
    }
}
```

### 例3: 設定の保存・復元

アプリケーション設定を保存・復元する例です。

```java
import java.util.HashMap;
import java.util.Map;

// Memento
public class SettingsMemento {
    private final Map<String, String> settings;
    
    SettingsMemento(Map<String, String> settings) {
        this.settings = new HashMap<>(settings); // ディープコピー
    }
    
    Map<String, String> getSettings() {
        return new HashMap<>(settings); // コピーを返す
    }
}

// Originator
public class ApplicationSettings {
    private Map<String, String> settings = new HashMap<>();
    
    public void setSetting(String key, String value) {
        settings.put(key, value);
        System.out.println("設定を変更: " + key + " = " + value);
    }
    
    public String getSetting(String key) {
        return settings.get(key);
    }
    
    public SettingsMemento save() {
        System.out.println("設定を保存");
        return new SettingsMemento(settings);
    }
    
    public void restore(SettingsMemento memento) {
        this.settings = memento.getSettings();
        System.out.println("設定を復元");
    }
    
    public void display() {
        System.out.println("=== 現在の設定 ===");
        settings.forEach((key, value) -> 
            System.out.println(key + " = " + value));
    }
}

// Caretaker
import java.util.ArrayList;
import java.util.List;

public class SettingsManager {
    private List<SettingsMemento> history = new ArrayList<>();
    private int currentIndex = -1;
    
    public void saveSettings(SettingsMemento memento) {
        // 現在の位置より後ろの履歴を削除（分岐を削除）
        if (currentIndex < history.size() - 1) {
            history = new ArrayList<>(history.subList(0, currentIndex + 1));
        }
        history.add(memento);
        currentIndex = history.size() - 1;
    }
    
    public SettingsMemento undo() {
        if (currentIndex > 0) {
            currentIndex--;
            return history.get(currentIndex);
        }
        return null;
    }
    
    public SettingsMemento redo() {
        if (currentIndex < history.size() - 1) {
            currentIndex++;
            return history.get(currentIndex);
        }
        return null;
    }
    
    public boolean canUndo() {
        return currentIndex > 0;
    }
    
    public boolean canRedo() {
        return currentIndex < history.size() - 1;
    }
}

// 使用例
public class SettingsExample {
    public static void main(String[] args) {
        ApplicationSettings settings = new ApplicationSettings();
        SettingsManager manager = new SettingsManager();
        
        // 初期設定
        settings.setSetting("theme", "dark");
        settings.setSetting("language", "ja");
        manager.saveSettings(settings.save());
        settings.display();
        
        // 設定を変更
        settings.setSetting("theme", "light");
        manager.saveSettings(settings.save());
        settings.display();
        
        // さらに設定を変更
        settings.setSetting("fontSize", "14");
        manager.saveSettings(settings.save());
        settings.display();
        
        // アンドゥ
        if (manager.canUndo()) {
            SettingsMemento memento = manager.undo();
            if (memento != null) {
                settings.restore(memento);
                System.out.println("\nアンドゥ後:");
                settings.display();
            }
        }
        
        // リドゥ
        if (manager.canRedo()) {
            SettingsMemento memento = manager.redo();
            if (memento != null) {
                settings.restore(memento);
                System.out.println("\nリドゥ後:");
                settings.display();
            }
        }
    }
}
```

### 例4: ドキュメントのバージョン管理

ドキュメントの複数のバージョンを管理する例です。

```java
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Memento
public class DocumentMemento {
    private final String content;
    private final Date timestamp;
    private final String version;
    
    DocumentMemento(String content, Date timestamp, String version) {
        this.content = content;
        this.timestamp = new Date(timestamp.getTime()); // コピー
        this.version = version;
    }
    
    String getContent() {
        return content;
    }
    
    Date getTimestamp() {
        return new Date(timestamp.getTime()); // コピーを返す
    }
    
    String getVersion() {
        return version;
    }
}

// Originator
public class Document {
    private String content = "";
    private String currentVersion = "1.0";
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public void appendContent(String text) {
        content += text;
    }
    
    public String getContent() {
        return content;
    }
    
    public DocumentMemento save(String version) {
        System.out.println("ドキュメントを保存: バージョン " + version);
        return new DocumentMemento(content, new Date(), version);
    }
    
    public void restore(DocumentMemento memento) {
        this.content = memento.getContent();
        this.currentVersion = memento.getVersion();
        System.out.println("ドキュメントを復元: バージョン " + currentVersion);
    }
    
    public void display() {
        System.out.println("=== ドキュメント (バージョン " + currentVersion + ") ===");
        System.out.println(content);
    }
}

// Caretaker
public class VersionManager {
    private List<DocumentMemento> versions = new ArrayList<>();
    
    public void saveVersion(DocumentMemento memento) {
        versions.add(memento);
        System.out.println("バージョン " + memento.getVersion() + " を保存しました");
    }
    
    public DocumentMemento getVersion(String version) {
        for (DocumentMemento memento : versions) {
            if (memento.getVersion().equals(version)) {
                return memento;
            }
        }
        return null;
    }
    
    public List<String> getAllVersions() {
        List<String> versionList = new ArrayList<>();
        for (DocumentMemento memento : versions) {
            versionList.add(memento.getVersion() + " (" + memento.getTimestamp() + ")");
        }
        return versionList;
    }
}

// 使用例
public class DocumentVersionExample {
    public static void main(String[] args) {
        Document document = new Document();
        VersionManager versionManager = new VersionManager();
        
        // バージョン1.0
        document.setContent("初版の内容");
        versionManager.saveVersion(document.save("1.0"));
        document.display();
        
        // バージョン1.1
        document.appendContent("\n更新内容を追加");
        versionManager.saveVersion(document.save("1.1"));
        document.display();
        
        // バージョン2.0
        document.appendContent("\n大幅な更新");
        versionManager.saveVersion(document.save("2.0"));
        document.display();
        
        // バージョン1.0に戻す
        DocumentMemento memento = versionManager.getVersion("1.0");
        if (memento != null) {
            document.restore(memento);
            document.display();
        }
        
        // すべてのバージョンを表示
        System.out.println("\n=== すべてのバージョン ===");
        versionManager.getAllVersions().forEach(System.out::println);
    }
}
```

### 例5: チェックポイント機能

長時間実行される処理のチェックポイント機能を実装する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Memento
public class ProcessMemento {
    private final int step;
    private final String data;
    private final long timestamp;
    
    ProcessMemento(int step, String data, long timestamp) {
        this.step = step;
        this.data = data;
        this.timestamp = timestamp;
    }
    
    int getStep() {
        return step;
    }
    
    String getData() {
        return data;
    }
    
    long getTimestamp() {
        return timestamp;
    }
}

// Originator
public class LongRunningProcess {
    private int currentStep = 0;
    private String processedData = "";
    
    public void processStep(String input) {
        currentStep++;
        processedData += "Step " + currentStep + ": " + input + "\n";
        System.out.println("処理ステップ " + currentStep + " を実行");
    }
    
    public ProcessMemento createCheckpoint() {
        System.out.println("チェックポイントを作成: ステップ " + currentStep);
        return new ProcessMemento(currentStep, processedData, System.currentTimeMillis());
    }
    
    public void restoreFromCheckpoint(ProcessMemento memento) {
        this.currentStep = memento.getStep();
        this.processedData = memento.getData();
        System.out.println("チェックポイントから復元: ステップ " + currentStep);
    }
    
    public int getCurrentStep() {
        return currentStep;
    }
    
    public void display() {
        System.out.println("=== 処理状態 ===");
        System.out.println("現在のステップ: " + currentStep);
        System.out.println("処理済みデータ:\n" + processedData);
    }
}

// Caretaker
public class CheckpointManager {
    private List<ProcessMemento> checkpoints = new ArrayList<>();
    
    public void saveCheckpoint(ProcessMemento memento) {
        checkpoints.add(memento);
        System.out.println("チェックポイント " + checkpoints.size() + " を保存");
    }
    
    public ProcessMemento getLatestCheckpoint() {
        if (!checkpoints.isEmpty()) {
            return checkpoints.get(checkpoints.size() - 1);
        }
        return null;
    }
    
    public ProcessMemento getCheckpoint(int index) {
        if (index >= 0 && index < checkpoints.size()) {
            return checkpoints.get(index);
        }
        return null;
    }
    
    public int getCheckpointCount() {
        return checkpoints.size();
    }
}

// 使用例
public class CheckpointExample {
    public static void main(String[] args) {
        LongRunningProcess process = new LongRunningProcess();
        CheckpointManager manager = new CheckpointManager();
        
        // 処理を実行
        process.processStep("データ1を処理");
        process.processStep("データ2を処理");
        
        // チェックポイント1
        manager.saveCheckpoint(process.createCheckpoint());
        
        // さらに処理を実行
        process.processStep("データ3を処理");
        process.processStep("データ4を処理");
        
        // チェックポイント2
        manager.saveCheckpoint(process.createCheckpoint());
        
        // さらに処理を実行
        process.processStep("データ5を処理");
        process.display();
        
        // チェックポイント2から復元
        ProcessMemento memento = manager.getCheckpoint(1);
        if (memento != null) {
            process.restoreFromCheckpoint(memento);
            process.display();
        }
    }
}
```

---

## まとめ

### 学習のポイント

1. **メメントパターンの目的**: オブジェクトの内部状態を保存・復元し、カプセル化を維持
2. **基本的な構造**: Memento、Originator、Caretakerの3つの要素
3. **カプセル化の維持**: オブジェクトの内部構造を公開せずに状態を保存・復元
4. **アンドゥ/リドゥ機能**: 操作の履歴を管理し、取り消し・やり直しを実装可能

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | カプセル化の維持、アンドゥ/リドゥ機能、状態の分離、柔軟性 |
| **注意点** | メモリ使用量、パフォーマンス、複雑性の増加、深いコピーの必要性 |
| **適用場面** | アンドゥ/リドゥ機能、ゲームのセーブ/ロード、設定の保存、トランザクション管理、チェックポイント |

### 他のパターンとの関係

- **Command**: コマンドパターンと組み合わせて、アンドゥ/リドゥ機能を実装することが多い
- **Prototype**: プロトタイプパターンを使用してMementoをコピーすることがある
- **State**: ステートパターンと組み合わせて、状態の保存・復元を行うことがある

### 注意点

1. **メモリ管理**: 多くの状態を保存するとメモリ使用量が増加するため、適切に管理する
2. **ディープコピー**: 複雑なオブジェクトの状態を保存する際は、ディープコピーが必要
3. **パフォーマンス**: 状態の保存・復元に時間がかかる場合は、最適化を検討する
4. **過度な使用を避ける**: シンプルなケースでは過剰な設計になる可能性がある

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. テキストエディタのアンドゥ/リドゥ機能を実装してみる
3. Commandパターンと組み合わせた実装を試してみる
4. ゲームのセーブ/ロード機能を実装してみる
5. Stateパターンを学習する（メメントパターンと組み合わせて使用）

### 参考資料

- [cs-techblog.com - メメントパターン](https://cs-techblog.com/technical/memento-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、メメントパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
