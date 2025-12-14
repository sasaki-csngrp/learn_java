# コマンドパターン（Command Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [コマンドパターンとは](#コマンドパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

コマンドパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**振る舞いに関するパターン（Behavioral Pattern）**に分類されます。

このパターンは、リクエストをオブジェクトとしてカプセル化し、異なるリクエストをパラメータ化された方法で実行できるようにします。これにより、リクエストの送信者と受信者を分離し、リクエストのキューイング、ロギング、元に戻す操作（Undo）などをサポートできます。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- コマンドパターンの目的と利点を理解する
- 基本的なコマンドパターンの実装方法を理解する
- Undo/Redo機能の実装方法を理解する
- 実際のプロジェクトでコマンドパターンを適用できる

---

## コマンドパターンとは

### 定義

コマンドパターンは、リクエストをオブジェクトとしてカプセル化するデザインパターンです。これにより、クライアントを異なるリクエストでパラメータ化し、リクエストのキューイング、ロギング、元に戻す操作（Undo）などをサポートできます。

### 主な特徴

1. **リクエストのカプセル化**: リクエストをオブジェクトとして扱う
2. **送信者と受信者の分離**: 送信者は受信者を知る必要がない
3. **パラメータ化**: 異なるリクエストでクライアントをパラメータ化
4. **Undo/Redoサポート**: 操作を元に戻したり、やり直したりできる

### 使用される場面

コマンドパターンは、以下のような場面で使用されます：

- **GUIアプリケーション**: ボタンやメニューの操作をコマンドとして実装
- **Undo/Redo機能**: テキストエディタやグラフィックエディタでの操作の取り消し・やり直し
- **マクロ機能**: 複数の操作を記録して再生
- **ロギング**: 実行されたコマンドをログに記録
- **トランザクション**: 複数の操作を1つのトランザクションとして扱う
- **リモートプロシージャコール**: ネットワーク経由での操作の実行

### メリット

- **送信者と受信者の分離**: 送信者は受信者を知る必要がない
- **柔軟性**: 新しいコマンドを追加する際に既存のコードを変更する必要がない
- **Undo/Redoサポート**: 操作を元に戻したり、やり直したりできる
- **マクロ機能**: 複数のコマンドを組み合わせてマクロを作成可能
- **ロギングと監査**: 実行されたコマンドを記録可能

### デメリット

- **クラス数の増加**: 各操作に対してコマンドクラスが必要
- **複雑性の増加**: シンプルな操作でもコマンドクラスが必要になる
- **メモリ使用量**: コマンドオブジェクトがメモリを消費する

---

## 基本的な実装

### 実装のポイント

コマンドパターンを実装するには、以下の要素が必要です：

1. **Command（コマンドインターフェース）**: 実行メソッドを定義するインターフェース
2. **ConcreteCommand（具象コマンド）**: Commandインターフェースを実装し、Receiverとアクションを結びつける
3. **Receiver（受信者）**: コマンドに関連付けられた操作を実行する方法を知っている
4. **Invoker（起動者）**: コマンドにリクエストの実行を依頼する
5. **Client（クライアント）**: 具体的なコマンドオブジェクトを作成し、Receiverを設定する

### パターンの構造

```
Client
  ↓
Command (インターフェース)
  └─ execute()
      ↓
ConcreteCommand
  ├─ receiver: Receiver
  └─ execute() [receiver.action()を呼び出す]
      ↓
Receiver
  └─ action() [実際の処理]
      ↓
Invoker
  ├─ command: Command
  └─ invoke() [command.execute()を呼び出す]
```

### 基本的な実装例

```java
// 1. Commandインターフェース
public interface Command {
    void execute();
}

// 2. Receiverクラス（実際の処理を行うクラス）
public class Light {
    public void on() {
        System.out.println("Light is ON");
    }
    
    public void off() {
        System.out.println("Light is OFF");
    }
}

// 3. ConcreteCommandクラス
public class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.on();
    }
}

public class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.off();
    }
}

// 4. Invokerクラス（コマンドを実行するクラス）
public class RemoteControl {
    private Command command;
    
    public void setCommand(Command command) {
        this.command = command;
    }
    
    public void pressButton() {
        if (command != null) {
            command.execute();
        }
    }
}

// 5. クライアントコード
public class CommandPatternDemo {
    public static void main(String[] args) {
        System.out.println("=== コマンドパターンのデモ ===\n");
        
        // Receiverを作成
        Light livingRoomLight = new Light();
        
        // ConcreteCommandを作成
        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);
        
        // Invokerを作成
        RemoteControl remote = new RemoteControl();
        
        // ライトを点灯
        System.out.println("--- Turning light ON ---");
        remote.setCommand(lightOn);
        remote.pressButton();
        
        // ライトを消灯
        System.out.println("\n--- Turning light OFF ---");
        remote.setCommand(lightOff);
        remote.pressButton();
    }
}
```

### 使用例の出力

```
=== コマンドパターンのデモ ===

--- Turning light ON ---
Light is ON

--- Turning light OFF ---
Light is OFF
```

### 実装のポイント

1. **コマンドのカプセル化**: リクエストをオブジェクトとしてカプセル化
2. **Receiverの分離**: コマンドはReceiverを知っているが、Invokerは知らない
3. **柔軟な実行**: Invokerはコマンドの種類に関係なく、同じ方法で実行できる

---

## 実装のバリエーション

### バリエーション1: Undo機能の実装

操作を元に戻す機能を実装する方法です。

```java
// Commandインターフェース（Undo機能付き）
public interface Command {
    void execute();
    void undo();
}

// ConcreteCommandクラス（Undo機能付き）
public class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.on();
    }
    
    @Override
    public void undo() {
        light.off();
    }
}

public class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.off();
    }
    
    @Override
    public void undo() {
        light.on();
    }
}

// Invokerクラス（Undo機能付き）
import java.util.Stack;

public class RemoteControlWithUndo {
    private Command command;
    private Stack<Command> undoStack;
    
    public RemoteControlWithUndo() {
        this.undoStack = new Stack<>();
    }
    
    public void setCommand(Command command) {
        this.command = command;
    }
    
    public void pressButton() {
        if (command != null) {
            command.execute();
            undoStack.push(command);
        }
    }
    
    public void pressUndo() {
        if (!undoStack.isEmpty()) {
            Command lastCommand = undoStack.pop();
            lastCommand.undo();
        }
    }
}

// 使用例
public class UndoExample {
    public static void main(String[] args) {
        Light light = new Light();
        RemoteControlWithUndo remote = new RemoteControlWithUndo();
        
        remote.setCommand(new LightOnCommand(light));
        remote.pressButton(); // ライトを点灯
        
        remote.setCommand(new LightOffCommand(light));
        remote.pressButton(); // ライトを消灯
        
        remote.pressUndo(); // 元に戻す（ライトを点灯）
        remote.pressUndo(); // さらに元に戻す（ライトを消灯）
    }
}
```

### バリエーション2: マクロコマンド

複数のコマンドを1つのコマンドとして実行する方法です。

```java
import java.util.ArrayList;
import java.util.List;

// マクロコマンドクラス
public class MacroCommand implements Command {
    private List<Command> commands;
    
    public MacroCommand(List<Command> commands) {
        this.commands = new ArrayList<>(commands);
    }
    
    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }
    
    @Override
    public void undo() {
        // 逆順でundoを実行
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }
}

// 使用例
public class MacroExample {
    public static void main(String[] args) {
        Light light1 = new Light();
        Light light2 = new Light();
        Light light3 = new Light();
        
        List<Command> commands = new ArrayList<>();
        commands.add(new LightOnCommand(light1));
        commands.add(new LightOnCommand(light2));
        commands.add(new LightOnCommand(light3));
        
        MacroCommand partyMode = new MacroCommand(commands);
        
        RemoteControlWithUndo remote = new RemoteControlWithUndo();
        remote.setCommand(partyMode);
        remote.pressButton(); // すべてのライトを点灯
        
        remote.pressUndo(); // すべてのライトを消灯
    }
}
```

### バリエーション3: パラメータ付きコマンド

コマンドにパラメータを渡す方法です。

```java
// パラメータ付きCommandインターフェース
public interface ParameterizedCommand {
    void execute(Object... parameters);
}

// Receiverクラス
public class TV {
    private int channel;
    private int volume;
    
    public void setChannel(int channel) {
        this.channel = channel;
        System.out.println("Channel set to: " + channel);
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("Volume set to: " + volume);
    }
    
    public int getChannel() {
        return channel;
    }
    
    public int getVolume() {
        return volume;
    }
}

// パラメータ付きConcreteCommand
public class SetChannelCommand implements ParameterizedCommand {
    private TV tv;
    
    public SetChannelCommand(TV tv) {
        this.tv = tv;
    }
    
    @Override
    public void execute(Object... parameters) {
        if (parameters.length > 0 && parameters[0] instanceof Integer) {
            tv.setChannel((Integer) parameters[0]);
        }
    }
}

public class SetVolumeCommand implements ParameterizedCommand {
    private TV tv;
    
    public SetVolumeCommand(TV tv) {
        this.tv = tv;
    }
    
    @Override
    public void execute(Object... parameters) {
        if (parameters.length > 0 && parameters[0] instanceof Integer) {
            tv.setVolume((Integer) parameters[0]);
        }
    }
}

// 使用例
public class ParameterizedExample {
    public static void main(String[] args) {
        TV tv = new TV();
        ParameterizedCommand setChannel = new SetChannelCommand(tv);
        ParameterizedCommand setVolume = new SetVolumeCommand(tv);
        
        setChannel.execute(5);
        setVolume.execute(20);
    }
}
```

---

## 実践例

### 例1: テキストエディタのUndo/Redo機能

テキストエディタでの操作の取り消し・やり直し機能の例です。

```java
// テキストエディタ（Receiver）
public class TextEditor {
    private StringBuilder text;
    
    public TextEditor() {
        this.text = new StringBuilder();
    }
    
    public void insert(String text, int position) {
        this.text.insert(position, text);
    }
    
    public void delete(int start, int end) {
        this.text.delete(start, end);
    }
    
    public String getText() {
        return text.toString();
    }
    
    public void setText(String text) {
        this.text = new StringBuilder(text);
    }
}

// Commandインターフェース
public interface TextCommand {
    void execute();
    void undo();
}

// Insertコマンド
public class InsertCommand implements TextCommand {
    private TextEditor editor;
    private String text;
    private int position;
    
    public InsertCommand(TextEditor editor, String text, int position) {
        this.editor = editor;
        this.text = text;
        this.position = position;
    }
    
    @Override
    public void execute() {
        editor.insert(text, position);
        System.out.println("Inserted: \"" + text + "\" at position " + position);
    }
    
    @Override
    public void undo() {
        editor.delete(position, position + text.length());
        System.out.println("Undone: Insert");
    }
}

// Deleteコマンド
public class DeleteCommand implements TextCommand {
    private TextEditor editor;
    private int start;
    private int end;
    private String deletedText;
    
    public DeleteCommand(TextEditor editor, int start, int end) {
        this.editor = editor;
        this.start = start;
        this.end = end;
    }
    
    @Override
    public void execute() {
        deletedText = editor.getText().substring(start, end);
        editor.delete(start, end);
        System.out.println("Deleted: \"" + deletedText + "\"");
    }
    
    @Override
    public void undo() {
        editor.insert(deletedText, start);
        System.out.println("Undone: Delete");
    }
}

// コマンドマネージャー（Invoker）
import java.util.Stack;

public class CommandManager {
    private Stack<TextCommand> undoStack;
    private Stack<TextCommand> redoStack;
    
    public CommandManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }
    
    public void executeCommand(TextCommand command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Redoスタックをクリア
    }
    
    public void undo() {
        if (!undoStack.isEmpty()) {
            TextCommand command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        } else {
            System.out.println("Nothing to undo");
        }
    }
    
    public void redo() {
        if (!redoStack.isEmpty()) {
            TextCommand command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        } else {
            System.out.println("Nothing to redo");
        }
    }
}

// 使用例
public class TextEditorExample {
    public static void main(String[] args) {
        System.out.println("=== テキストエディタのUndo/Redo機能 ===\n");
        
        TextEditor editor = new TextEditor();
        CommandManager manager = new CommandManager();
        
        // テキストを挿入
        manager.executeCommand(new InsertCommand(editor, "Hello", 0));
        manager.executeCommand(new InsertCommand(editor, " World", 5));
        System.out.println("Text: " + editor.getText());
        
        // 削除
        manager.executeCommand(new DeleteCommand(editor, 0, 5));
        System.out.println("Text: " + editor.getText());
        
        // Undo
        System.out.println("\n--- Undo ---");
        manager.undo();
        System.out.println("Text: " + editor.getText());
        
        manager.undo();
        System.out.println("Text: " + editor.getText());
        
        // Redo
        System.out.println("\n--- Redo ---");
        manager.redo();
        System.out.println("Text: " + editor.getText());
    }
}
```

### 例2: リモートコントロール

複数のデバイスを制御するリモートコントロールの例です。

```java
// Receiverインターフェース
public interface Device {
    void on();
    void off();
    String getName();
}

// 具象Receiverクラス
public class Light implements Device {
    private String location;
    
    public Light(String location) {
        this.location = location;
    }
    
    @Override
    public void on() {
        System.out.println(location + " light is ON");
    }
    
    @Override
    public void off() {
        System.out.println(location + " light is OFF");
    }
    
    @Override
    public String getName() {
        return location + " Light";
    }
}

public class TV implements Device {
    private String location;
    
    public TV(String location) {
        this.location = location;
    }
    
    @Override
    public void on() {
        System.out.println(location + " TV is ON");
    }
    
    @Override
    public void off() {
        System.out.println(location + " TV is OFF");
    }
    
    @Override
    public String getName() {
        return location + " TV";
    }
}

public class Stereo implements Device {
    private String location;
    
    public Stereo(String location) {
        this.location = location;
    }
    
    @Override
    public void on() {
        System.out.println(location + " Stereo is ON");
    }
    
    @Override
    public void off() {
        System.out.println(location + " Stereo is OFF");
    }
    
    @Override
    public String getName() {
        return location + " Stereo";
    }
}

// Commandインターフェース
public interface DeviceCommand extends Command {
    void undo();
}

// 汎用Onコマンド
public class DeviceOnCommand implements DeviceCommand {
    private Device device;
    
    public DeviceOnCommand(Device device) {
        this.device = device;
    }
    
    @Override
    public void execute() {
        device.on();
    }
    
    @Override
    public void undo() {
        device.off();
    }
}

// 汎用Offコマンド
public class DeviceOffCommand implements DeviceCommand {
    private Device device;
    
    public DeviceOffCommand(Device device) {
        this.device = device;
    }
    
    @Override
    public void execute() {
        device.off();
    }
    
    @Override
    public void undo() {
        device.on();
    }
}

// リモートコントロール（Invoker）
import java.util.Stack;

public class RemoteControl {
    private DeviceCommand[] onCommands;
    private DeviceCommand[] offCommands;
    private Stack<DeviceCommand> undoStack;
    private static final int SLOTS = 7;
    
    public RemoteControl() {
        this.onCommands = new DeviceCommand[SLOTS];
        this.offCommands = new DeviceCommand[SLOTS];
        this.undoStack = new Stack<>();
        
        // デフォルトでNoCommandを設定
        NoCommand noCommand = new NoCommand();
        for (int i = 0; i < SLOTS; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }
    
    public void setCommand(int slot, DeviceCommand onCommand, DeviceCommand offCommand) {
        if (slot >= 0 && slot < SLOTS) {
            onCommands[slot] = onCommand;
            offCommands[slot] = offCommand;
        }
    }
    
    public void onButtonWasPushed(int slot) {
        if (slot >= 0 && slot < SLOTS) {
            onCommands[slot].execute();
            undoStack.push(onCommands[slot]);
        }
    }
    
    public void offButtonWasPushed(int slot) {
        if (slot >= 0 && slot < SLOTS) {
            offCommands[slot].execute();
            undoStack.push(offCommands[slot]);
        }
    }
    
    public void undoButtonWasPushed() {
        if (!undoStack.isEmpty()) {
            undoStack.pop().undo();
        }
    }
    
    public void printStatus() {
        System.out.println("\n--- Remote Control ---");
        for (int i = 0; i < SLOTS; i++) {
            System.out.println("Slot " + i + ": " + 
                             onCommands[i].getClass().getSimpleName() + " / " + 
                             offCommands[i].getClass().getSimpleName());
        }
    }
}

// NoCommandクラス（Null Objectパターン）
public class NoCommand implements DeviceCommand {
    @Override
    public void execute() {
        // 何もしない
    }
    
    @Override
    public void undo() {
        // 何もしない
    }
}

// 使用例
public class RemoteControlExample {
    public static void main(String[] args) {
        System.out.println("=== リモートコントロールのデモ ===\n");
        
        RemoteControl remote = new RemoteControl();
        
        // デバイスを作成
        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        TV livingRoomTV = new TV("Living Room");
        Stereo livingRoomStereo = new Stereo("Living Room");
        
        // コマンドを設定
        remote.setCommand(0, 
                         new DeviceOnCommand(livingRoomLight), 
                         new DeviceOffCommand(livingRoomLight));
        remote.setCommand(1, 
                         new DeviceOnCommand(kitchenLight), 
                         new DeviceOffCommand(kitchenLight));
        remote.setCommand(2, 
                         new DeviceOnCommand(livingRoomTV), 
                         new DeviceOffCommand(livingRoomTV));
        remote.setCommand(3, 
                         new DeviceOnCommand(livingRoomStereo), 
                         new DeviceOffCommand(livingRoomStereo));
        
        // コマンドを実行
        System.out.println("--- Turning devices ON ---");
        remote.onButtonWasPushed(0);
        remote.onButtonWasPushed(1);
        remote.onButtonWasPushed(2);
        remote.onButtonWasPushed(3);
        
        System.out.println("\n--- Turning devices OFF ---");
        remote.offButtonWasPushed(0);
        remote.offButtonWasPushed(1);
        remote.offButtonWasPushed(2);
        remote.offButtonWasPushed(3);
        
        System.out.println("\n--- Undo ---");
        remote.undoButtonWasPushed();
        remote.undoButtonWasPushed();
    }
}
```

### 例3: トランザクション管理

複数の操作を1つのトランザクションとして扱う例です。

```java
// Commandインターフェース（トランザクション対応）
public interface TransactionCommand {
    void execute();
    void rollback();
    boolean isExecuted();
}

// データベース操作コマンド（簡易版）
public class DatabaseInsertCommand implements TransactionCommand {
    private String table;
    private String data;
    private boolean executed;
    
    public DatabaseInsertCommand(String table, String data) {
        this.table = table;
        this.data = data;
        this.executed = false;
    }
    
    @Override
    public void execute() {
        System.out.println("INSERT INTO " + table + " VALUES (" + data + ")");
        executed = true;
    }
    
    @Override
    public void rollback() {
        if (executed) {
            System.out.println("ROLLBACK: DELETE FROM " + table + " WHERE data = " + data);
            executed = false;
        }
    }
    
    @Override
    public boolean isExecuted() {
        return executed;
    }
}

public class DatabaseUpdateCommand implements TransactionCommand {
    private String table;
    private String condition;
    private String newValue;
    private String oldValue;
    private boolean executed;
    
    public DatabaseUpdateCommand(String table, String condition, String newValue) {
        this.table = table;
        this.condition = condition;
        this.newValue = newValue;
        this.executed = false;
    }
    
    @Override
    public void execute() {
        System.out.println("UPDATE " + table + " SET value = " + newValue + " WHERE " + condition);
        oldValue = "old_value"; // 実際には元の値を保存
        executed = true;
    }
    
    @Override
    public void rollback() {
        if (executed) {
            System.out.println("ROLLBACK: UPDATE " + table + " SET value = " + oldValue + " WHERE " + condition);
            executed = false;
        }
    }
    
    @Override
    public boolean isExecuted() {
        return executed;
    }
}

// トランザクションマネージャー
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private List<TransactionCommand> commands;
    
    public TransactionManager() {
        this.commands = new ArrayList<>();
    }
    
    public void addCommand(TransactionCommand command) {
        commands.add(command);
    }
    
    public void execute() {
        System.out.println("=== Starting Transaction ===");
        List<TransactionCommand> executedCommands = new ArrayList<>();
        
        try {
            for (TransactionCommand command : commands) {
                command.execute();
                executedCommands.add(command);
            }
            System.out.println("=== Transaction Committed ===");
        } catch (Exception e) {
            System.out.println("=== Transaction Failed, Rolling Back ===");
            // 逆順でロールバック
            for (int i = executedCommands.size() - 1; i >= 0; i--) {
                executedCommands.get(i).rollback();
            }
            throw e;
        }
    }
    
    public void clear() {
        commands.clear();
    }
}

// 使用例
public class TransactionExample {
    public static void main(String[] args) {
        System.out.println("=== トランザクション管理のデモ ===\n");
        
        TransactionManager manager = new TransactionManager();
        
        // トランザクションにコマンドを追加
        manager.addCommand(new DatabaseInsertCommand("users", "'John', 'john@example.com'"));
        manager.addCommand(new DatabaseUpdateCommand("users", "id = 1", "'updated@example.com'"));
        manager.addCommand(new DatabaseInsertCommand("orders", "'Order123', 100.00"));
        
        // トランザクションを実行
        try {
            manager.execute();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### 例4: ログ記録機能

実行されたコマンドをログに記録する例です。

```java
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// ログ記録付きCommandインターフェース
public interface LoggableCommand extends Command {
    String getDescription();
    LocalDateTime getExecutionTime();
}

// ログ記録付きConcreteCommand
public class LoggableLightOnCommand implements LoggableCommand {
    private Light light;
    private LocalDateTime executionTime;
    
    public LoggableLightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        executionTime = LocalDateTime.now();
        light.on();
    }
    
    @Override
    public String getDescription() {
        return "Turn light ON";
    }
    
    @Override
    public LocalDateTime getExecutionTime() {
        return executionTime;
    }
}

// ログマネージャー
public class CommandLogger {
    private List<LoggableCommand> log;
    
    public CommandLogger() {
        this.log = new ArrayList<>();
    }
    
    public void executeAndLog(LoggableCommand command) {
        command.execute();
        log.add(command);
        System.out.println("Logged: " + command.getDescription() + 
                         " at " + command.getExecutionTime());
    }
    
    public void printLog() {
        System.out.println("\n=== Command Log ===");
        for (LoggableCommand command : log) {
            System.out.println(command.getDescription() + " - " + command.getExecutionTime());
        }
    }
}

// 使用例
public class LoggingExample {
    public static void main(String[] args) {
        System.out.println("=== ログ記録機能のデモ ===\n");
        
        Light light = new Light();
        CommandLogger logger = new CommandLogger();
        
        logger.executeAndLog(new LoggableLightOnCommand(light));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.executeAndLog(new LoggableLightOffCommand(light));
        
        logger.printLog();
    }
}

// LoggableLightOffCommandも同様に実装
class LoggableLightOffCommand implements LoggableCommand {
    private Light light;
    private LocalDateTime executionTime;
    
    public LoggableLightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        executionTime = LocalDateTime.now();
        light.off();
    }
    
    @Override
    public String getDescription() {
        return "Turn light OFF";
    }
    
    @Override
    public LocalDateTime getExecutionTime() {
        return executionTime;
    }
}
```

---

## まとめ

### 学習のポイント

1. **コマンドパターンの目的**: リクエストをオブジェクトとしてカプセル化し、送信者と受信者を分離
2. **基本構造**: Commandインターフェース、ConcreteCommand、Receiver、Invoker、Client
3. **Undo/Redo機能**: コマンドにundoメソッドを追加することで、操作を元に戻せる
4. **マクロ機能**: 複数のコマンドを組み合わせてマクロコマンドを作成可能

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 送信者と受信者の分離、柔軟性、Undo/Redoサポート、マクロ機能、ロギングと監査 |
| **注意点** | クラス数の増加、複雑性の増加、メモリ使用量 |
| **適用場面** | GUIアプリケーション、Undo/Redo機能、マクロ機能、ロギング、トランザクション、リモートプロシージャコール |

### 実装のベストプラクティス

1. **Commandインターフェース**: シンプルで一貫したインターフェースを定義
2. **Undo機能**: 必要に応じてundoメソッドを追加
3. **Null Objectパターン**: コマンドが設定されていない場合の処理にNoCommandを使用
4. **マクロコマンド**: 複数のコマンドを組み合わせてマクロを作成

### 他のパターンとの関係

- **Strategy**: コマンドパターンはリクエストをカプセル化するが、ストラテジーパターンはアルゴリズムをカプセル化する
- **Memento**: Undo機能を実装する際に、Mementoパターンと組み合わせることがある
- **Composite**: マクロコマンドを実装する際に、Compositeパターンと組み合わせることがある

### 注意点

1. **過度な使用を避ける**: シンプルな操作では、コマンドパターンは不要な場合がある
2. **メモリ管理**: Undoスタックが大きくなりすぎないように注意する
3. **スレッドセーフ**: マルチスレッド環境では、適切な同期が必要

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. Undo/Redo機能を実装してみる
3. 実際のプロジェクトでコマンドパターンを適用してみる
4. 他の振る舞いに関するパターン（Observer、Strategy、Stateなど）を学習する

### 参考資料

- [cs-techblog.com - コマンドパターン](https://cs-techblog.com/technical/command-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、コマンドパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
