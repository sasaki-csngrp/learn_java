# ステートパターン（State Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [ステートパターンとは](#ステートパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

ステートパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**振る舞いに関するパターン（Behavioral Pattern）**に分類されます。

このパターンは、オブジェクトの内部状態に応じて振る舞いを変更できるようにします。状態に依存する条件分岐を削減し、コードをより保守しやすく、拡張しやすくします。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- ステートパターンの目的と利点を理解する
- 基本的なステートパターンの実装方法を理解する
- 状態遷移を管理する方法を理解する
- 状態マシンを実装できる
- 実際のプロジェクトでステートパターンを適用できる

---

## ステートパターンとは

### 定義

ステートパターンは、オブジェクトの内部状態に応じて振る舞いを変更するデザインパターンです。オブジェクトが状態に依存する動作を持つ場合、各状態を独立したクラスとして表現し、状態に応じた振る舞いをカプセル化します。

### 主な特徴

1. **状態のカプセル化**: 各状態を独立したクラスとして表現
2. **状態遷移**: 状態間の遷移を明確に定義
3. **条件分岐の削減**: if-elseやswitch文による状態分岐を削減
4. **拡張性**: 新しい状態を追加する際に、既存のコードを変更する必要がない

### 使用される場面

ステートパターンは、以下のような場面で使用されます：

- **状態マシン**: 有限状態機械（FSM）の実装
- **ゲーム開発**: キャラクターの状態（待機、移動、攻撃、防御など）
- **ワークフロー管理**: タスクやドキュメントの状態管理
- **UIコンポーネント**: ボタンの状態（有効、無効、ホバーなど）
- **トランザクション管理**: トランザクションの状態（開始、コミット、ロールバックなど）
- **オーダー管理**: 注文の状態（受注、処理中、配送中、完了など）
- **デバイス制御**: デバイスの状態（オン、オフ、スタンバイなど）

### メリット

- **保守性**: 状態ごとの振る舞いが独立したクラスに分離されるため、保守が容易
- **拡張性**: 新しい状態を追加する際に、既存のコードを変更する必要がない
- **可読性**: 状態に依存する条件分岐が削減され、コードが読みやすくなる
- **単一責任の原則**: 各状態クラスが単一の責任を持つ
- **テスト容易性**: 各状態を独立してテストできる

### デメリット

- **クラス数の増加**: 状態の数だけクラスが増加する
- **複雑性の増加**: シンプルなケースでは過剰な設計になる可能性がある
- **状態遷移の管理**: 状態遷移が複雑になると、管理が困難になる場合がある
- **パフォーマンス**: 状態オブジェクトの作成・削除によるオーバーヘッドが発生する可能性がある

---

## 基本的な実装

### 実装のポイント

ステートパターンを実装するには、以下の要素が必要です：

1. **Stateインターフェース（State）**: 状態に依存する動作を定義するインターフェース
2. **ConcreteState（具象状態）**: Stateインターフェースを実装し、特定の状態の振る舞いを定義
3. **Context（コンテキスト）**: 現在の状態を保持し、状態に依存する操作を委譲するクラス

### 基本的な実装例

```java
// 1. Stateインターフェース
public interface State {
    void handle(Context context);
    String getStateName();
}

// 2. ConcreteState（具象状態）
public class StartState implements State {
    @Override
    public void handle(Context context) {
        System.out.println("開始状態で処理を実行");
        // 状態遷移の例
        // context.setState(new RunningState());
    }
    
    @Override
    public String getStateName() {
        return "開始";
    }
}

public class RunningState implements State {
    @Override
    public void handle(Context context) {
        System.out.println("実行中状態で処理を実行");
        // 状態遷移の例
        // context.setState(new StopState());
    }
    
    @Override
    public String getStateName() {
        return "実行中";
    }
}

public class StopState implements State {
    @Override
    public void handle(Context context) {
        System.out.println("停止状態で処理を実行");
    }
    
    @Override
    public String getStateName() {
        return "停止";
    }
}

// 3. Context（コンテキスト）
public class Context {
    private State currentState;
    
    public Context() {
        // 初期状態を設定
        this.currentState = new StartState();
    }
    
    public void setState(State state) {
        System.out.println("状態を " + currentState.getStateName() + 
                         " から " + state.getStateName() + " に変更");
        this.currentState = state;
    }
    
    public State getState() {
        return currentState;
    }
    
    public void request() {
        currentState.handle(this);
    }
}
```

### 使用例

```java
public class StateExample {
    public static void main(String[] args) {
        Context context = new Context();
        
        // 現在の状態で処理を実行
        context.request();
        System.out.println("現在の状態: " + context.getState().getStateName());
        
        // 状態を変更
        context.setState(new RunningState());
        context.request();
        System.out.println("現在の状態: " + context.getState().getStateName());
        
        // 状態を変更
        context.setState(new StopState());
        context.request();
        System.out.println("現在の状態: " + context.getState().getStateName());
    }
}
```

### パターンの構造

```
Context
  ├─ currentState: State
  ├─ setState(State)
  └─ request() → currentState.handle()
        ↓
State (インターフェース)
  ├─ handle(Context)
  └─ getStateName()
        ↓
ConcreteState1
  └─ handle(Context) [実装]
        ↓
ConcreteState2
  └─ handle(Context) [実装]
```

---

## 実装のバリエーション

### バリエーション1: 状態遷移をState内で管理

状態遷移のロジックをStateクラス内に含める方法です。

```java
// Stateインターフェース
public interface State {
    void handle(Context context);
    State getNextState();
    String getStateName();
}

// ConcreteState
public class StartState implements State {
    @Override
    public void handle(Context context) {
        System.out.println("開始状態で処理を実行");
        // 状態遷移を自動的に行う
        State nextState = getNextState();
        if (nextState != null) {
            context.setState(nextState);
        }
    }
    
    @Override
    public State getNextState() {
        return new RunningState();
    }
    
    @Override
    public String getStateName() {
        return "開始";
    }
}

public class RunningState implements State {
    @Override
    public void handle(Context context) {
        System.out.println("実行中状態で処理を実行");
        State nextState = getNextState();
        if (nextState != null) {
            context.setState(nextState);
        }
    }
    
    @Override
    public State getNextState() {
        return new StopState();
    }
    
    @Override
    public String getStateName() {
        return "実行中";
    }
}
```

### バリエーション2: 状態遷移テーブルを使用

状態遷移をテーブルで管理する方法です。

```java
import java.util.HashMap;
import java.util.Map;

// イベント
public enum Event {
    START,
    PAUSE,
    RESUME,
    STOP
}

// Stateインターフェース
public interface State {
    void handle(Context context, Event event);
    String getStateName();
}

// 状態遷移テーブルを持つState
public abstract class BaseState implements State {
    protected Map<Event, State> transitions = new HashMap<>();
    
    public BaseState() {
        initializeTransitions();
    }
    
    protected abstract void initializeTransitions();
    
    @Override
    public void handle(Context context, Event event) {
        State nextState = transitions.get(event);
        if (nextState != null) {
            System.out.println("イベント " + event + " により状態遷移: " + 
                             context.getState().getStateName() + " → " + 
                             nextState.getStateName());
            context.setState(nextState);
        } else {
            System.out.println("イベント " + event + " は現在の状態では無効です");
        }
    }
}

// ConcreteState
public class IdleState extends BaseState {
    @Override
    protected void initializeTransitions() {
        transitions.put(Event.START, new RunningState());
    }
    
    @Override
    public String getStateName() {
        return "待機";
    }
}

public class RunningState extends BaseState {
    @Override
    protected void initializeTransitions() {
        transitions.put(Event.PAUSE, new PausedState());
        transitions.put(Event.STOP, new StoppedState());
    }
    
    @Override
    public String getStateName() {
        return "実行中";
    }
}

public class PausedState extends BaseState {
    @Override
    protected void initializeTransitions() {
        transitions.put(Event.RESUME, new RunningState());
        transitions.put(Event.STOP, new StoppedState());
    }
    
    @Override
    public String getStateName() {
        return "一時停止";
    }
}

public class StoppedState extends BaseState {
    @Override
    protected void initializeTransitions() {
        // 停止状態からは遷移しない
    }
    
    @Override
    public String getStateName() {
        return "停止";
    }
}
```

### バリエーション3: シングルトン状態

状態オブジェクトをシングルトンとして実装する方法です。

```java
// シングルトン状態
public class StartState implements State {
    private static final StartState instance = new StartState();
    
    private StartState() {
        // プライベートコンストラクタ
    }
    
    public static StartState getInstance() {
        return instance;
    }
    
    @Override
    public void handle(Context context) {
        System.out.println("開始状態で処理を実行");
        context.setState(RunningState.getInstance());
    }
    
    @Override
    public String getStateName() {
        return "開始";
    }
}

public class RunningState implements State {
    private static final RunningState instance = new RunningState();
    
    private RunningState() {
        // プライベートコンストラクタ
    }
    
    public static RunningState getInstance() {
        return instance;
    }
    
    @Override
    public void handle(Context context) {
        System.out.println("実行中状態で処理を実行");
        context.setState(StopState.getInstance());
    }
    
    @Override
    public String getStateName() {
        return "実行中";
    }
}
```

### バリエーション4: 状態履歴の管理

状態の履歴を管理し、前の状態に戻れるようにする方法です。

```java
import java.util.Stack;

public class ContextWithHistory {
    private State currentState;
    private Stack<State> stateHistory = new Stack<>();
    
    public ContextWithHistory() {
        this.currentState = new StartState();
    }
    
    public void setState(State state) {
        stateHistory.push(currentState);
        System.out.println("状態を " + currentState.getStateName() + 
                         " から " + state.getStateName() + " に変更");
        this.currentState = state;
    }
    
    public void undo() {
        if (!stateHistory.isEmpty()) {
            State previousState = stateHistory.pop();
            System.out.println("状態を " + currentState.getStateName() + 
                             " から " + previousState.getStateName() + " に戻す");
            this.currentState = previousState;
        } else {
            System.out.println("戻る状態がありません");
        }
    }
    
    public State getState() {
        return currentState;
    }
    
    public void request() {
        currentState.handle(this);
    }
}
```

---

## 実践例

### 例1: プレイヤーの状態管理

ゲームのプレイヤーの状態（待機、移動、攻撃、防御）を管理する例です。

```java
// Stateインターフェース
public interface PlayerState {
    void move(Player player);
    void attack(Player player);
    void defend(Player player);
    void idle(Player player);
    String getStateName();
}

// ConcreteState
public class IdleState implements PlayerState {
    @Override
    public void move(Player player) {
        System.out.println("待機状態 → 移動状態");
        player.setState(new MovingState());
    }
    
    @Override
    public void attack(Player player) {
        System.out.println("待機状態 → 攻撃状態");
        player.setState(new AttackingState());
    }
    
    @Override
    public void defend(Player player) {
        System.out.println("待機状態 → 防御状態");
        player.setState(new DefendingState());
    }
    
    @Override
    public void idle(Player player) {
        System.out.println("既に待機状態です");
    }
    
    @Override
    public String getStateName() {
        return "待機";
    }
}

public class MovingState implements PlayerState {
    @Override
    public void move(Player player) {
        System.out.println("移動中...");
    }
    
    @Override
    public void attack(Player player) {
        System.out.println("移動中は攻撃できません");
    }
    
    @Override
    public void defend(Player player) {
        System.out.println("移動中は防御できません");
    }
    
    @Override
    public void idle(Player player) {
        System.out.println("移動状態 → 待機状態");
        player.setState(new IdleState());
    }
    
    @Override
    public String getStateName() {
        return "移動";
    }
}

public class AttackingState implements PlayerState {
    @Override
    public void move(Player player) {
        System.out.println("攻撃中は移動できません");
    }
    
    @Override
    public void attack(Player player) {
        System.out.println("攻撃中...");
    }
    
    @Override
    public void defend(Player player) {
        System.out.println("攻撃中は防御できません");
    }
    
    @Override
    public void idle(Player player) {
        System.out.println("攻撃状態 → 待機状態");
        player.setState(new IdleState());
    }
    
    @Override
    public String getStateName() {
        return "攻撃";
    }
}

public class DefendingState implements PlayerState {
    @Override
    public void move(Player player) {
        System.out.println("防御中は移動できません");
    }
    
    @Override
    public void attack(Player player) {
        System.out.println("防御中は攻撃できません");
    }
    
    @Override
    public void defend(Player player) {
        System.out.println("防御中...");
    }
    
    @Override
    public void idle(Player player) {
        System.out.println("防御状態 → 待機状態");
        player.setState(new IdleState());
    }
    
    @Override
    public String getStateName() {
        return "防御";
    }
}

// Context
public class Player {
    private PlayerState state;
    private String name;
    
    public Player(String name) {
        this.name = name;
        this.state = new IdleState();
    }
    
    public void setState(PlayerState state) {
        this.state = state;
    }
    
    public PlayerState getState() {
        return state;
    }
    
    public void move() {
        System.out.print(name + ": ");
        state.move(this);
    }
    
    public void attack() {
        System.out.print(name + ": ");
        state.attack(this);
    }
    
    public void defend() {
        System.out.print(name + ": ");
        state.defend(this);
    }
    
    public void idle() {
        System.out.print(name + ": ");
        state.idle(this);
    }
    
    public void displayState() {
        System.out.println(name + " の現在の状態: " + state.getStateName());
    }
}

// 使用例
public class PlayerStateExample {
    public static void main(String[] args) {
        Player player = new Player("プレイヤー1");
        player.displayState();
        
        player.move();
        player.displayState();
        
        player.attack();
        player.displayState();
        
        player.idle();
        player.displayState();
        
        player.defend();
        player.displayState();
    }
}
```

### 例2: 注文の状態管理

注文の状態（受注、処理中、配送中、完了、キャンセル）を管理する例です。

```java
// Stateインターフェース
public interface OrderState {
    void process(Order order);
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
    String getStateName();
}

// ConcreteState
public class PendingState implements OrderState {
    @Override
    public void process(Order order) {
        System.out.println("注文を処理中に変更");
        order.setState(new ProcessingState());
    }
    
    @Override
    public void ship(Order order) {
        System.out.println("受注状態からは配送できません。まず処理してください");
    }
    
    @Override
    public void deliver(Order order) {
        System.out.println("受注状態からは配達できません");
    }
    
    @Override
    public void cancel(Order order) {
        System.out.println("注文をキャンセル");
        order.setState(new CancelledState());
    }
    
    @Override
    public String getStateName() {
        return "受注";
    }
}

public class ProcessingState implements OrderState {
    @Override
    public void process(Order order) {
        System.out.println("既に処理中です");
    }
    
    @Override
    public void ship(Order order) {
        System.out.println("注文を配送中に変更");
        order.setState(new ShippedState());
    }
    
    @Override
    public void deliver(Order order) {
        System.out.println("処理中からは配達できません。まず配送してください");
    }
    
    @Override
    public void cancel(Order order) {
        System.out.println("処理中の注文をキャンセル");
        order.setState(new CancelledState());
    }
    
    @Override
    public String getStateName() {
        return "処理中";
    }
}

public class ShippedState implements OrderState {
    @Override
    public void process(Order order) {
        System.out.println("配送中から処理状態に戻すことはできません");
    }
    
    @Override
    public void ship(Order order) {
        System.out.println("既に配送中です");
    }
    
    @Override
    public void deliver(Order order) {
        System.out.println("注文を配達完了に変更");
        order.setState(new DeliveredState());
    }
    
    @Override
    public void cancel(Order order) {
        System.out.println("配送中の注文はキャンセルできません");
    }
    
    @Override
    public String getStateName() {
        return "配送中";
    }
}

public class DeliveredState implements OrderState {
    @Override
    public void process(Order order) {
        System.out.println("配達完了済みの注文は処理できません");
    }
    
    @Override
    public void ship(Order order) {
        System.out.println("配達完了済みの注文は配送できません");
    }
    
    @Override
    public void deliver(Order order) {
        System.out.println("既に配達完了しています");
    }
    
    @Override
    public void cancel(Order order) {
        System.out.println("配達完了済みの注文はキャンセルできません");
    }
    
    @Override
    public String getStateName() {
        return "配達完了";
    }
}

public class CancelledState implements OrderState {
    @Override
    public void process(Order order) {
        System.out.println("キャンセル済みの注文は処理できません");
    }
    
    @Override
    public void ship(Order order) {
        System.out.println("キャンセル済みの注文は配送できません");
    }
    
    @Override
    public void deliver(Order order) {
        System.out.println("キャンセル済みの注文は配達できません");
    }
    
    @Override
    public void cancel(Order order) {
        System.out.println("既にキャンセル済みです");
    }
    
    @Override
    public String getStateName() {
        return "キャンセル";
    }
}

// Context
public class Order {
    private OrderState state;
    private String orderId;
    private String customerName;
    
    public Order(String orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.state = new PendingState();
    }
    
    public void setState(OrderState state) {
        this.state = state;
    }
    
    public OrderState getState() {
        return state;
    }
    
    public void process() {
        System.out.print("注文 " + orderId + ": ");
        state.process(this);
    }
    
    public void ship() {
        System.out.print("注文 " + orderId + ": ");
        state.ship(this);
    }
    
    public void deliver() {
        System.out.print("注文 " + orderId + ": ");
        state.deliver(this);
    }
    
    public void cancel() {
        System.out.print("注文 " + orderId + ": ");
        state.cancel(this);
    }
    
    public void displayStatus() {
        System.out.println("注文 " + orderId + " (" + customerName + ") の状態: " + 
                         state.getStateName());
    }
}

// 使用例
public class OrderStateExample {
    public static void main(String[] args) {
        Order order = new Order("ORD-001", "山田太郎");
        order.displayStatus();
        
        order.process();
        order.displayStatus();
        
        order.ship();
        order.displayStatus();
        
        order.deliver();
        order.displayStatus();
    }
}
```

### 例3: ドアの状態管理

ドアの状態（閉じている、開いている、ロックされている）を管理する例です。

```java
// Stateインターフェース
public interface DoorState {
    void open(Door door);
    void close(Door door);
    void lock(Door door);
    void unlock(Door door);
    String getStateName();
}

// ConcreteState
public class ClosedState implements DoorState {
    @Override
    public void open(Door door) {
        System.out.println("ドアを開く");
        door.setState(new OpenState());
    }
    
    @Override
    public void close(Door door) {
        System.out.println("既に閉じています");
    }
    
    @Override
    public void lock(Door door) {
        System.out.println("ドアをロック");
        door.setState(new LockedState());
    }
    
    @Override
    public void unlock(Door door) {
        System.out.println("閉じているドアは既にアンロックされています");
    }
    
    @Override
    public String getStateName() {
        return "閉じている";
    }
}

public class OpenState implements DoorState {
    @Override
    public void open(Door door) {
        System.out.println("既に開いています");
    }
    
    @Override
    public void close(Door door) {
        System.out.println("ドアを閉じる");
        door.setState(new ClosedState());
    }
    
    @Override
    public void lock(Door door) {
        System.out.println("開いているドアはロックできません。まず閉じてください");
    }
    
    @Override
    public void unlock(Door door) {
        System.out.println("開いているドアは既にアンロックされています");
    }
    
    @Override
    public String getStateName() {
        return "開いている";
    }
}

public class LockedState implements DoorState {
    @Override
    public void open(Door door) {
        System.out.println("ロックされているドアは開けられません。まずアンロックしてください");
    }
    
    @Override
    public void close(Door door) {
        System.out.println("既に閉じています");
    }
    
    @Override
    public void lock(Door door) {
        System.out.println("既にロックされています");
    }
    
    @Override
    public void unlock(Door door) {
        System.out.println("ドアをアンロック");
        door.setState(new ClosedState());
    }
    
    @Override
    public String getStateName() {
        return "ロックされている";
    }
}

// Context
public class Door {
    private DoorState state;
    private String location;
    
    public Door(String location) {
        this.location = location;
        this.state = new ClosedState();
    }
    
    public void setState(DoorState state) {
        this.state = state;
    }
    
    public DoorState getState() {
        return state;
    }
    
    public void open() {
        System.out.print(location + "のドア: ");
        state.open(this);
    }
    
    public void close() {
        System.out.print(location + "のドア: ");
        state.close(this);
    }
    
    public void lock() {
        System.out.print(location + "のドア: ");
        state.lock(this);
    }
    
    public void unlock() {
        System.out.print(location + "のドア: ");
        state.unlock(this);
    }
    
    public void displayState() {
        System.out.println(location + "のドアの状態: " + state.getStateName());
    }
}

// 使用例
public class DoorStateExample {
    public static void main(String[] args) {
        Door door = new Door("エントランス");
        door.displayState();
        
        door.open();
        door.displayState();
        
        door.close();
        door.displayState();
        
        door.lock();
        door.displayState();
        
        door.unlock();
        door.displayState();
    }
}
```

### 例4: トランザクションの状態管理

データベーストランザクションの状態を管理する例です。

```java
// Stateインターフェース
public interface TransactionState {
    void begin(Transaction transaction);
    void commit(Transaction transaction);
    void rollback(Transaction transaction);
    void abort(Transaction transaction);
    String getStateName();
}

// ConcreteState
public class IdleState implements TransactionState {
    @Override
    public void begin(Transaction transaction) {
        System.out.println("トランザクションを開始");
        transaction.setState(new ActiveState());
    }
    
    @Override
    public void commit(Transaction transaction) {
        System.out.println("トランザクションが開始されていません");
    }
    
    @Override
    public void rollback(Transaction transaction) {
        System.out.println("トランザクションが開始されていません");
    }
    
    @Override
    public void abort(Transaction transaction) {
        System.out.println("トランザクションが開始されていません");
    }
    
    @Override
    public String getStateName() {
        return "待機";
    }
}

public class ActiveState implements TransactionState {
    @Override
    public void begin(Transaction transaction) {
        System.out.println("既にトランザクションが開始されています");
    }
    
    @Override
    public void commit(Transaction transaction) {
        System.out.println("トランザクションをコミット");
        transaction.setState(new CommittedState());
    }
    
    @Override
    public void rollback(Transaction transaction) {
        System.out.println("トランザクションをロールバック");
        transaction.setState(new RolledBackState());
    }
    
    @Override
    public void abort(Transaction transaction) {
        System.out.println("トランザクションをアボート");
        transaction.setState(new AbortedState());
    }
    
    @Override
    public String getStateName() {
        return "アクティブ";
    }
}

public class CommittedState implements TransactionState {
    @Override
    public void begin(Transaction transaction) {
        System.out.println("新しいトランザクションを開始");
        transaction.setState(new ActiveState());
    }
    
    @Override
    public void commit(Transaction transaction) {
        System.out.println("既にコミット済みです");
    }
    
    @Override
    public void rollback(Transaction transaction) {
        System.out.println("コミット済みのトランザクションはロールバックできません");
    }
    
    @Override
    public void abort(Transaction transaction) {
        System.out.println("コミット済みのトランザクションはアボートできません");
    }
    
    @Override
    public String getStateName() {
        return "コミット済み";
    }
}

public class RolledBackState implements TransactionState {
    @Override
    public void begin(Transaction transaction) {
        System.out.println("新しいトランザクションを開始");
        transaction.setState(new ActiveState());
    }
    
    @Override
    public void commit(Transaction transaction) {
        System.out.println("ロールバック済みのトランザクションはコミットできません");
    }
    
    @Override
    public void rollback(Transaction transaction) {
        System.out.println("既にロールバック済みです");
    }
    
    @Override
    public void abort(Transaction transaction) {
        System.out.println("既にロールバック済みです");
    }
    
    @Override
    public String getStateName() {
        return "ロールバック済み";
    }
}

public class AbortedState implements TransactionState {
    @Override
    public void begin(Transaction transaction) {
        System.out.println("新しいトランザクションを開始");
        transaction.setState(new ActiveState());
    }
    
    @Override
    public void commit(Transaction transaction) {
        System.out.println("アボート済みのトランザクションはコミットできません");
    }
    
    @Override
    public void rollback(Transaction transaction) {
        System.out.println("アボート済みのトランザクションはロールバックできません");
    }
    
    @Override
    public void abort(Transaction transaction) {
        System.out.println("既にアボート済みです");
    }
    
    @Override
    public String getStateName() {
        return "アボート済み";
    }
}

// Context
public class Transaction {
    private TransactionState state;
    private String transactionId;
    
    public Transaction(String transactionId) {
        this.transactionId = transactionId;
        this.state = new IdleState();
    }
    
    public void setState(TransactionState state) {
        this.state = state;
    }
    
    public TransactionState getState() {
        return state;
    }
    
    public void begin() {
        System.out.print("トランザクション " + transactionId + ": ");
        state.begin(this);
    }
    
    public void commit() {
        System.out.print("トランザクション " + transactionId + ": ");
        state.commit(this);
    }
    
    public void rollback() {
        System.out.print("トランザクション " + transactionId + ": ");
        state.rollback(this);
    }
    
    public void abort() {
        System.out.print("トランザクション " + transactionId + ": ");
        state.abort(this);
    }
    
    public void displayStatus() {
        System.out.println("トランザクション " + transactionId + " の状態: " + 
                         state.getStateName());
    }
}

// 使用例
public class TransactionStateExample {
    public static void main(String[] args) {
        Transaction transaction = new Transaction("TXN-001");
        transaction.displayStatus();
        
        transaction.begin();
        transaction.displayStatus();
        
        transaction.commit();
        transaction.displayStatus();
        
        // 新しいトランザクション
        Transaction transaction2 = new Transaction("TXN-002");
        transaction2.begin();
        transaction2.rollback();
        transaction2.displayStatus();
    }
}
```

### 例5: ビデオプレイヤーの状態管理

ビデオプレイヤーの状態（停止、再生、一時停止）を管理する例です。

```java
// Stateインターフェース
public interface PlayerState {
    void play(VideoPlayer player);
    void pause(VideoPlayer player);
    void stop(VideoPlayer player);
    String getStateName();
}

// ConcreteState
public class StoppedState implements PlayerState {
    @Override
    public void play(VideoPlayer player) {
        System.out.println("ビデオを再生");
        player.setState(new PlayingState());
    }
    
    @Override
    public void pause(VideoPlayer player) {
        System.out.println("停止中は一時停止できません");
    }
    
    @Override
    public void stop(VideoPlayer player) {
        System.out.println("既に停止しています");
    }
    
    @Override
    public String getStateName() {
        return "停止";
    }
}

public class PlayingState implements PlayerState {
    @Override
    public void play(VideoPlayer player) {
        System.out.println("既に再生中です");
    }
    
    @Override
    public void pause(VideoPlayer player) {
        System.out.println("ビデオを一時停止");
        player.setState(new PausedState());
    }
    
    @Override
    public void stop(VideoPlayer player) {
        System.out.println("ビデオを停止");
        player.setState(new StoppedState());
    }
    
    @Override
    public String getStateName() {
        return "再生中";
    }
}

public class PausedState implements PlayerState {
    @Override
    public void play(VideoPlayer player) {
        System.out.println("ビデオを再開");
        player.setState(new PlayingState());
    }
    
    @Override
    public void pause(VideoPlayer player) {
        System.out.println("既に一時停止中です");
    }
    
    @Override
    public void stop(VideoPlayer player) {
        System.out.println("ビデオを停止");
        player.setState(new StoppedState());
    }
    
    @Override
    public String getStateName() {
        return "一時停止";
    }
}

// Context
public class VideoPlayer {
    private PlayerState state;
    private String videoName;
    
    public VideoPlayer(String videoName) {
        this.videoName = videoName;
        this.state = new StoppedState();
    }
    
    public void setState(PlayerState state) {
        this.state = state;
    }
    
    public PlayerState getState() {
        return state;
    }
    
    public void play() {
        System.out.print(videoName + ": ");
        state.play(this);
    }
    
    public void pause() {
        System.out.print(videoName + ": ");
        state.pause(this);
    }
    
    public void stop() {
        System.out.print(videoName + ": ");
        state.stop(this);
    }
    
    public void displayState() {
        System.out.println(videoName + " の状態: " + state.getStateName());
    }
}

// 使用例
public class VideoPlayerExample {
    public static void main(String[] args) {
        VideoPlayer player = new VideoPlayer("サンプルビデオ");
        player.displayState();
        
        player.play();
        player.displayState();
        
        player.pause();
        player.displayState();
        
        player.play();
        player.displayState();
        
        player.stop();
        player.displayState();
    }
}
```

---

## まとめ

### 学習のポイント

1. **ステートパターンの目的**: オブジェクトの内部状態に応じて振る舞いを変更
2. **基本的な構造**: Stateインターフェース、ConcreteState、Contextの3つの要素
3. **状態遷移**: 状態間の遷移を明確に定義
4. **条件分岐の削減**: if-elseやswitch文による状態分岐を削減

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 保守性、拡張性、可読性、単一責任の原則、テスト容易性 |
| **注意点** | クラス数の増加、複雑性の増加、状態遷移の管理、パフォーマンス |
| **適用場面** | 状態マシン、ゲーム開発、ワークフロー管理、UIコンポーネント、トランザクション管理 |

### 他のパターンとの関係

- **Strategy**: ステートパターンとストラテジーパターンは構造が類似しているが、目的が異なる（状態の管理 vs アルゴリズムの選択）
- **Command**: コマンドパターンと組み合わせて、状態遷移をコマンドとして扱うことがある
- **Memento**: メメントパターンと組み合わせて、状態の履歴を管理することがある

### 注意点

1. **状態遷移の明確化**: 状態遷移のルールを明確に定義する
2. **状態オブジェクトの管理**: 状態オブジェクトの作成・削除を適切に管理する
3. **過度な使用を避ける**: シンプルなケースでは過剰な設計になる可能性がある
4. **状態の共有**: 状態が共有可能な場合は、シングルトンパターンを使用する

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. 状態マシンを実装してみる
3. ゲーム開発でステートパターンを適用してみる
4. Strategyパターンを学習する（ステートパターンと類似のパターン）
5. Mementoパターンを学習する（ステートパターンと組み合わせて使用）

### 参考資料

- [cs-techblog.com - ステートパターン](https://cs-techblog.com/technical/state-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、ステートパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
