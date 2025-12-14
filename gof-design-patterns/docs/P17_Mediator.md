# メディエーターパターン（Mediator Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [メディエーターパターンとは](#メディエーターパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

メディエーターパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**振る舞いに関するパターン（Behavioral Pattern）**に分類されます。

このパターンは、複数のオブジェクト間の複雑な通信や制御を中央集約化し、オブジェクト同士が直接参照し合うことを防ぐことで、疎結合を促進します。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- メディエーターパターンの目的と利点を理解する
- 基本的なメディエーターパターンの実装方法を理解する
- オブジェクト間の通信を中央集約化する方法を理解する
- GUIアプリケーションなどでメディエーターパターンを適用できる
- 実際のプロジェクトでメディエーターパターンを適用できる

---

## メディエーターパターンとは

### 定義

メディエーターパターンは、複数のオブジェクト間の複雑な通信や制御を中央集約化するデザインパターンです。オブジェクト同士が直接参照し合うことを防ぎ、代わりにメディエーターオブジェクトを通じて通信することで、疎結合を実現します。

### 主な特徴

1. **中央集約化**: オブジェクト間の通信をメディエーターに集約
2. **疎結合**: オブジェクト同士が直接参照しないため、結合度が低い
3. **複雑性の管理**: 複雑な相互作用を1つの場所で管理
4. **再利用性**: 個々のコンポーネントを独立して再利用可能

### 使用される場面

メディエーターパターンは、以下のような場面で使用されます：

- **GUIアプリケーション**: 複数のUIコンポーネント間の通信（ボタン、テキストフィールド、リストなど）
- **チャットアプリケーション**: 複数のユーザー間のメッセージ交換
- **航空管制システム**: 複数の航空機と管制塔の通信
- **取引システム**: 複数の参加者間の取引の仲介
- **ゲーム開発**: 複数のゲームオブジェクト間の相互作用
- **ワークフロー管理**: 複数のタスク間の依存関係の管理

### メリット

- **疎結合**: オブジェクト同士が直接参照しないため、結合度が低く、変更の影響が少ない
- **再利用性**: 個々のコンポーネントを独立して再利用可能
- **保守性**: 相互作用のロジックが1箇所に集約されるため、保守が容易
- **理解しやすさ**: 通信の流れが明確になり、システムが理解しやすくなる
- **拡張性**: 新しいコンポーネントを追加する際に、既存のコンポーネントを変更する必要がない

### デメリット

- **メディエーターの複雑化**: 多くのコンポーネントが存在する場合、メディエーターが複雑になる
- **単一障害点**: メディエーターが障害点になる可能性がある
- **パフォーマンス**: すべての通信がメディエーターを経由するため、わずかなオーバーヘッドが発生
- **過度な使用**: シンプルな相互作用では過剰な設計になる可能性がある

---

## 基本的な実装

### 実装のポイント

メディエーターパターンを実装するには、以下の要素が必要です：

1. **Mediatorインターフェース（Mediator）**: コンポーネント間の通信を定義するインターフェース
2. **ConcreteMediator（具象メディエーター）**: Mediatorインターフェースを実装し、コンポーネント間の通信を制御
3. **Colleague抽象クラス（Colleague）**: メディエーターを通じて通信するコンポーネントの基底クラス
4. **ConcreteColleague（具象同僚）**: Colleagueを継承し、実際のコンポーネントを実装

### 基本的な実装例

```java
// 1. Mediatorインターフェース
public interface Mediator {
    void notify(Colleague sender, String event);
}

// 2. Colleague抽象クラス
public abstract class Colleague {
    protected Mediator mediator;
    
    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }
    
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
    
    public abstract void receive(String message);
}

// 3. ConcreteColleague（具象同僚）
public class ComponentA extends Colleague {
    public ComponentA(Mediator mediator) {
        super(mediator);
    }
    
    public void doA() {
        System.out.println("ComponentA: 何か処理を実行");
        mediator.notify(this, "A");
    }
    
    @Override
    public void receive(String message) {
        System.out.println("ComponentA: " + message + " を受信");
    }
}

public class ComponentB extends Colleague {
    public ComponentB(Mediator mediator) {
        super(mediator);
    }
    
    public void doB() {
        System.out.println("ComponentB: 何か処理を実行");
        mediator.notify(this, "B");
    }
    
    @Override
    public void receive(String message) {
        System.out.println("ComponentB: " + message + " を受信");
    }
}

// 4. ConcreteMediator（具象メディエーター）
public class ConcreteMediator implements Mediator {
    private ComponentA componentA;
    private ComponentB componentB;
    
    public void setComponentA(ComponentA componentA) {
        this.componentA = componentA;
    }
    
    public void setComponentB(ComponentB componentB) {
        this.componentB = componentB;
    }
    
    @Override
    public void notify(Colleague sender, String event) {
        if (event.equals("A")) {
            System.out.println("Mediator: ComponentAからのイベントを処理");
            componentB.receive("ComponentAがイベントを送信しました");
        } else if (event.equals("B")) {
            System.out.println("Mediator: ComponentBからのイベントを処理");
            componentA.receive("ComponentBがイベントを送信しました");
        }
    }
}
```

### 使用例

```java
public class MediatorExample {
    public static void main(String[] args) {
        ConcreteMediator mediator = new ConcreteMediator();
        
        ComponentA componentA = new ComponentA(mediator);
        ComponentB componentB = new ComponentB(mediator);
        
        mediator.setComponentA(componentA);
        mediator.setComponentB(componentB);
        
        System.out.println("=== ComponentAの処理 ===");
        componentA.doA();
        
        System.out.println("\n=== ComponentBの処理 ===");
        componentB.doB();
    }
}
```

### パターンの構造

```
Colleague1  ──┐
              │
Colleague2  ──┼──> Mediator (インターフェース)
              │         │
Colleague3  ──┘         │
                        ↓
              ConcreteMediator (具象クラス)
                        │
                        ├─> Colleague1に通知
                        ├─> Colleague2に通知
                        └─> Colleague3に通知
```

---

## 実装のバリエーション

### バリエーション1: イベントベースのメディエーター

イベントタイプに基づいて異なる処理を行うメディエーターです。

```java
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventMediator implements Mediator {
    private Map<String, Consumer<Colleague>> eventHandlers = new HashMap<>();
    private Map<Class<? extends Colleague>, Colleague> colleagues = new HashMap<>();
    
    public void registerColleague(Colleague colleague) {
        colleagues.put(colleague.getClass(), colleague);
    }
    
    public void registerEventHandler(String event, Consumer<Colleague> handler) {
        eventHandlers.put(event, handler);
    }
    
    @Override
    public void notify(Colleague sender, String event) {
        Consumer<Colleague> handler = eventHandlers.get(event);
        if (handler != null) {
            handler.accept(sender);
        } else {
            System.out.println("未登録のイベント: " + event);
        }
    }
    
    public <T extends Colleague> T getColleague(Class<T> colleagueClass) {
        return colleagueClass.cast(colleagues.get(colleagueClass));
    }
}

// 使用例
public class EventMediatorExample {
    public static void main(String[] args) {
        EventMediator mediator = new EventMediator();
        
        ComponentA componentA = new ComponentA(mediator);
        ComponentB componentB = new ComponentB(mediator);
        
        mediator.registerColleague(componentA);
        mediator.registerColleague(componentB);
        
        // イベントハンドラーを登録
        mediator.registerEventHandler("A", sender -> {
            ComponentB b = mediator.getColleague(ComponentB.class);
            if (b != null) {
                b.receive("ComponentAからの通知");
            }
        });
        
        mediator.registerEventHandler("B", sender -> {
            ComponentA a = mediator.getColleague(ComponentA.class);
            if (a != null) {
                a.receive("ComponentBからの通知");
            }
        });
        
        componentA.doA();
        componentB.doB();
    }
}
```

### バリエーション2: 双方向通信メディエーター

コンポーネント間で双方向の通信を行うメディエーターです。

```java
public class BidirectionalMediator implements Mediator {
    private Map<String, Colleague> colleagues = new HashMap<>();
    
    public void registerColleague(String id, Colleague colleague) {
        colleagues.put(id, colleague);
    }
    
    @Override
    public void notify(Colleague sender, String event) {
        String[] parts = event.split(":");
        if (parts.length == 2) {
            String targetId = parts[0];
            String message = parts[1];
            
            Colleague target = colleagues.get(targetId);
            if (target != null) {
                target.receive(message);
            } else {
                System.out.println("ターゲットが見つかりません: " + targetId);
            }
        } else {
            // ブロードキャスト
            colleagues.values().forEach(colleague -> {
                if (colleague != sender) {
                    colleague.receive(event);
                }
            });
        }
    }
}

// 使用例
public class BidirectionalMediatorExample {
    public static void main(String[] args) {
        BidirectionalMediator mediator = new BidirectionalMediator();
        
        ComponentA componentA = new ComponentA(mediator);
        ComponentB componentB = new ComponentB(mediator);
        
        mediator.registerColleague("A", componentA);
        mediator.registerColleague("B", componentB);
        
        // AからBへメッセージを送信
        componentA.doA(); // イベントを "B:メッセージ" の形式で送信する必要がある
    }
}
```

### バリエーション3: 状態管理メディエーター

コンポーネントの状態を管理するメディエーターです。

```java
import java.util.HashMap;
import java.util.Map;

public class StateMediator implements Mediator {
    private Map<String, Object> state = new HashMap<>();
    private Map<String, Colleague> colleagues = new HashMap<>();
    
    public void registerColleague(String id, Colleague colleague) {
        colleagues.put(id, colleague);
    }
    
    public void setState(String key, Object value) {
        state.put(key, value);
        notifyStateChange(key, value);
    }
    
    public Object getState(String key) {
        return state.get(key);
    }
    
    private void notifyStateChange(String key, Object value) {
        colleagues.values().forEach(colleague -> {
            if (colleague instanceof StateAwareColleague) {
                ((StateAwareColleague) colleague).onStateChanged(key, value);
            }
        });
    }
    
    @Override
    public void notify(Colleague sender, String event) {
        // イベント処理
        System.out.println("StateMediator: " + event + " イベントを受信");
    }
}

// 状態を認識するColleague
public abstract class StateAwareColleague extends Colleague {
    public StateAwareColleague(Mediator mediator) {
        super(mediator);
    }
    
    public abstract void onStateChanged(String key, Object value);
}
```

---

## 実践例

### 例1: GUIアプリケーション（チャットアプリケーション風）

複数のUIコンポーネント間の通信を管理する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Mediatorインターフェース
public interface ChatMediator {
    void sendMessage(String message, User user);
    void addUser(User user);
}

// ConcreteMediator
public class ChatRoom implements ChatMediator {
    private List<User> users = new ArrayList<>();
    
    @Override
    public void addUser(User user) {
        users.add(user);
    }
    
    @Override
    public void sendMessage(String message, User sender) {
        for (User user : users) {
            if (user != sender) {
                user.receive(message, sender.getName());
            }
        }
    }
}

// Colleague抽象クラス
public abstract class User {
    protected ChatMediator mediator;
    protected String name;
    
    public User(ChatMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public abstract void send(String message);
    public abstract void receive(String message, String senderName);
}

// ConcreteColleague
public class ChatUser extends User {
    public ChatUser(ChatMediator mediator, String name) {
        super(mediator, name);
    }
    
    @Override
    public void send(String message) {
        System.out.println(name + " が送信: " + message);
        mediator.sendMessage(message, this);
    }
    
    @Override
    public void receive(String message, String senderName) {
        System.out.println(name + " が " + senderName + " から受信: " + message);
    }
}

// 使用例
public class ChatApplicationExample {
    public static void main(String[] args) {
        ChatMediator chatRoom = new ChatRoom();
        
        User user1 = new ChatUser(chatRoom, "太郎");
        User user2 = new ChatUser(chatRoom, "花子");
        User user3 = new ChatUser(chatRoom, "次郎");
        
        chatRoom.addUser(user1);
        chatRoom.addUser(user2);
        chatRoom.addUser(user3);
        
        user1.send("こんにちは！");
        user2.send("こんにちは、太郎さん！");
        user3.send("みなさん、こんにちは！");
    }
}
```

### 例2: 航空管制システム

複数の航空機と管制塔の通信を管理する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Mediatorインターフェース
public interface AirTrafficControl {
    void requestLanding(Aircraft aircraft);
    void requestTakeoff(Aircraft aircraft);
    void registerAircraft(Aircraft aircraft);
    void notifyAll(String message, Aircraft sender);
}

// ConcreteMediator
public class ControlTower implements AirTrafficControl {
    private List<Aircraft> aircrafts = new ArrayList<>();
    private boolean runwayAvailable = true;
    
    @Override
    public void registerAircraft(Aircraft aircraft) {
        aircrafts.add(aircraft);
    }
    
    @Override
    public void requestLanding(Aircraft aircraft) {
        if (runwayAvailable) {
            runwayAvailable = false;
            System.out.println("管制塔: " + aircraft.getName() + " の着陸を許可");
            aircraft.receive("着陸許可が下りました");
            notifyAll(aircraft.getName() + " が着陸しました", aircraft);
        } else {
            System.out.println("管制塔: " + aircraft.getName() + " の着陸を待機");
            aircraft.receive("滑走路が使用中です。待機してください");
        }
    }
    
    @Override
    public void requestTakeoff(Aircraft aircraft) {
        if (runwayAvailable) {
            runwayAvailable = false;
            System.out.println("管制塔: " + aircraft.getName() + " の離陸を許可");
            aircraft.receive("離陸許可が下りました");
            notifyAll(aircraft.getName() + " が離陸しました", aircraft);
        } else {
            System.out.println("管制塔: " + aircraft.getName() + " の離陸を待機");
            aircraft.receive("滑走路が使用中です。待機してください");
        }
    }
    
    public void releaseRunway() {
        runwayAvailable = true;
        System.out.println("管制塔: 滑走路が利用可能になりました");
    }
    
    @Override
    public void notifyAll(String message, Aircraft sender) {
        for (Aircraft aircraft : aircrafts) {
            if (aircraft != sender) {
                aircraft.receive(message);
            }
        }
    }
}

// Colleague抽象クラス
public abstract class Aircraft {
    protected AirTrafficControl atc;
    protected String name;
    
    public Aircraft(AirTrafficControl atc, String name) {
        this.atc = atc;
        this.name = name;
        atc.registerAircraft(this);
    }
    
    public String getName() {
        return name;
    }
    
    public abstract void requestLanding();
    public abstract void requestTakeoff();
    public abstract void receive(String message);
}

// ConcreteColleague
public class PassengerAircraft extends Aircraft {
    public PassengerAircraft(AirTrafficControl atc, String name) {
        super(atc, name);
    }
    
    @Override
    public void requestLanding() {
        System.out.println(name + ": 着陸を要求");
        atc.requestLanding(this);
    }
    
    @Override
    public void requestTakeoff() {
        System.out.println(name + ": 離陸を要求");
        atc.requestTakeoff(this);
    }
    
    @Override
    public void receive(String message) {
        System.out.println(name + " が受信: " + message);
    }
}

// 使用例
public class AirTrafficControlExample {
    public static void main(String[] args) {
        AirTrafficControl tower = new ControlTower();
        
        Aircraft flight1 = new PassengerAircraft(tower, "JAL123");
        Aircraft flight2 = new PassengerAircraft(tower, "ANA456");
        Aircraft flight3 = new PassengerAircraft(tower, "JAL789");
        
        flight1.requestLanding();
        flight2.requestLanding();
        ((ControlTower) tower).releaseRunway();
        flight2.requestLanding();
        ((ControlTower) tower).releaseRunway();
        flight1.requestTakeoff();
    }
}
```

### 例3: フォームバリデーションシステム

複数のフォームフィールド間の依存関係を管理する例です。

```java
import java.util.HashMap;
import java.util.Map;

// Mediatorインターフェース
public interface FormMediator {
    void fieldChanged(FormField field);
    void registerField(FormField field);
    boolean validateForm();
}

// ConcreteMediator
public class RegistrationFormMediator implements FormMediator {
    private Map<String, FormField> fields = new HashMap<>();
    private Map<String, String> values = new HashMap<>();
    
    @Override
    public void registerField(FormField field) {
        fields.put(field.getName(), field);
    }
    
    @Override
    public void fieldChanged(FormField field) {
        values.put(field.getName(), field.getValue());
        
        // 他のフィールドに影響を与える処理
        if (field.getName().equals("email")) {
            // メールアドレスが変更されたら、確認メールフィールドをリセット
            FormField confirmEmail = fields.get("confirmEmail");
            if (confirmEmail != null) {
                confirmEmail.setValue("");
                confirmEmail.setEnabled(true);
            }
        }
        
        if (field.getName().equals("password")) {
            // パスワードが変更されたら、確認パスワードフィールドをリセット
            FormField confirmPassword = fields.get("confirmPassword");
            if (confirmPassword != null) {
                confirmPassword.setValue("");
                confirmPassword.setEnabled(true);
            }
        }
        
        // バリデーション
        validateField(field);
    }
    
    private void validateField(FormField field) {
        String value = field.getValue();
        boolean isValid = true;
        String errorMessage = "";
        
        if (field.getName().equals("email")) {
            if (!value.contains("@")) {
                isValid = false;
                errorMessage = "有効なメールアドレスを入力してください";
            }
        } else if (field.getName().equals("confirmEmail")) {
            String email = values.get("email");
            if (!value.equals(email)) {
                isValid = false;
                errorMessage = "メールアドレスが一致しません";
            }
        } else if (field.getName().equals("password")) {
            if (value.length() < 8) {
                isValid = false;
                errorMessage = "パスワードは8文字以上である必要があります";
            }
        } else if (field.getName().equals("confirmPassword")) {
            String password = values.get("password");
            if (!value.equals(password)) {
                isValid = false;
                errorMessage = "パスワードが一致しません";
            }
        }
        
        field.setValid(isValid);
        field.setErrorMessage(errorMessage);
    }
    
    @Override
    public boolean validateForm() {
        boolean allValid = true;
        for (FormField field : fields.values()) {
            validateField(field);
            if (!field.isValid()) {
                allValid = false;
            }
        }
        return allValid;
    }
}

// Colleague抽象クラス
public abstract class FormField {
    protected FormMediator mediator;
    protected String name;
    protected String value = "";
    protected boolean enabled = true;
    protected boolean valid = true;
    protected String errorMessage = "";
    
    public FormField(FormMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
        mediator.registerField(this);
    }
    
    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
        mediator.fieldChanged(this);
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public abstract void render();
}

// ConcreteColleague
public class TextField extends FormField {
    public TextField(FormMediator mediator, String name) {
        super(mediator, name);
    }
    
    @Override
    public void render() {
        System.out.println("[" + name + "] " + 
            (enabled ? "有効" : "無効") + " | " +
            "値: " + value + " | " +
            (valid ? "✓" : "✗ " + errorMessage));
    }
}

// 使用例
public class FormValidationExample {
    public static void main(String[] args) {
        FormMediator mediator = new RegistrationFormMediator();
        
        FormField email = new TextField(mediator, "email");
        FormField confirmEmail = new TextField(mediator, "confirmEmail");
        FormField password = new TextField(mediator, "password");
        FormField confirmPassword = new TextField(mediator, "confirmPassword");
        
        System.out.println("=== フォーム入力開始 ===");
        email.setValue("test@example.com");
        email.render();
        
        confirmEmail.setValue("test@example.com");
        confirmEmail.render();
        
        password.setValue("password123");
        password.render();
        
        confirmPassword.setValue("password123");
        confirmPassword.render();
        
        System.out.println("\n=== フォーム検証 ===");
        boolean isValid = mediator.validateForm();
        System.out.println("フォームは有効: " + isValid);
    }
}
```

### 例4: 取引システム

複数の参加者間の取引を仲介する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Mediatorインターフェース
public interface TradingMediator {
    void registerTrader(Trader trader);
    void placeOrder(Order order, Trader trader);
    void matchOrders();
}

// ConcreteMediator
public class StockExchange implements TradingMediator {
    private List<Trader> traders = new ArrayList<>();
    private List<Order> buyOrders = new ArrayList<>();
    private List<Order> sellOrders = new ArrayList<>();
    
    @Override
    public void registerTrader(Trader trader) {
        traders.add(trader);
    }
    
    @Override
    public void placeOrder(Order order, Trader trader) {
        if (order.getType() == OrderType.BUY) {
            buyOrders.add(order);
            System.out.println("取引所: " + trader.getName() + " が買い注文を出しました");
        } else {
            sellOrders.add(order);
            System.out.println("取引所: " + trader.getName() + " が売り注文を出しました");
        }
        matchOrders();
    }
    
    @Override
    public void matchOrders() {
        for (Order buyOrder : new ArrayList<>(buyOrders)) {
            for (Order sellOrder : new ArrayList<>(sellOrders)) {
                if (buyOrder.getSymbol().equals(sellOrder.getSymbol()) &&
                    buyOrder.getPrice() >= sellOrder.getPrice()) {
                    
                    int quantity = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
                    double price = sellOrder.getPrice();
                    
                    System.out.println("取引所: 成約 - " + quantity + "株 @ " + price);
                    
                    buyOrder.getTrader().notifyTrade(OrderType.BUY, buyOrder.getSymbol(), quantity, price);
                    sellOrder.getTrader().notifyTrade(OrderType.SELL, sellOrder.getSymbol(), quantity, price);
                    
                    buyOrder.reduceQuantity(quantity);
                    sellOrder.reduceQuantity(quantity);
                    
                    if (buyOrder.getQuantity() == 0) {
                        buyOrders.remove(buyOrder);
                    }
                    if (sellOrder.getQuantity() == 0) {
                        sellOrders.remove(sellOrder);
                    }
                }
            }
        }
    }
}

// Orderクラス
public class Order {
    private OrderType type;
    private String symbol;
    private int quantity;
    private double price;
    private Trader trader;
    
    public Order(OrderType type, String symbol, int quantity, double price, Trader trader) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.trader = trader;
    }
    
    public OrderType getType() {
        return type;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void reduceQuantity(int amount) {
        this.quantity -= amount;
    }
    
    public double getPrice() {
        return price;
    }
    
    public Trader getTrader() {
        return trader;
    }
}

enum OrderType {
    BUY, SELL
}

// Colleague抽象クラス
public abstract class Trader {
    protected TradingMediator mediator;
    protected String name;
    
    public Trader(TradingMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
        mediator.registerTrader(this);
    }
    
    public String getName() {
        return name;
    }
    
    public abstract void placeBuyOrder(String symbol, int quantity, double price);
    public abstract void placeSellOrder(String symbol, int quantity, double price);
    public abstract void notifyTrade(OrderType type, String symbol, int quantity, double price);
}

// ConcreteColleague
public class StockTrader extends Trader {
    public StockTrader(TradingMediator mediator, String name) {
        super(mediator, name);
    }
    
    @Override
    public void placeBuyOrder(String symbol, int quantity, double price) {
        Order order = new Order(OrderType.BUY, symbol, quantity, price, this);
        mediator.placeOrder(order, this);
    }
    
    @Override
    public void placeSellOrder(String symbol, int quantity, double price) {
        Order order = new Order(OrderType.SELL, symbol, quantity, price, this);
        mediator.placeOrder(order, this);
    }
    
    @Override
    public void notifyTrade(OrderType type, String symbol, int quantity, double price) {
        System.out.println(name + ": " + type + " " + quantity + "株の" + symbol + " @ " + price + " で成約");
    }
}

// 使用例
public class TradingSystemExample {
    public static void main(String[] args) {
        TradingMediator exchange = new StockExchange();
        
        Trader trader1 = new StockTrader(exchange, "トレーダー1");
        Trader trader2 = new StockTrader(exchange, "トレーダー2");
        Trader trader3 = new StockTrader(exchange, "トレーダー3");
        
        trader1.placeBuyOrder("AAPL", 100, 150.0);
        trader2.placeSellOrder("AAPL", 50, 145.0);
        trader3.placeSellOrder("AAPL", 60, 148.0);
    }
}
```

### 例5: ゲーム開発（プレイヤー間の通信）

複数のプレイヤー間の通信を管理する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Mediatorインターフェース
public interface GameMediator {
    void registerPlayer(Player player);
    void sendMessage(String message, Player sender);
    void playerAction(Player player, String action);
}

// ConcreteMediator
public class GameRoom implements GameMediator {
    private List<Player> players = new ArrayList<>();
    private String gameState = "WAITING";
    
    @Override
    public void registerPlayer(Player player) {
        players.add(player);
        System.out.println("ゲームルーム: " + player.getName() + " が参加しました");
        
        if (players.size() >= 2 && gameState.equals("WAITING")) {
            startGame();
        }
    }
    
    private void startGame() {
        gameState = "PLAYING";
        System.out.println("ゲームルーム: ゲームを開始します！");
        notifyAll("ゲームが開始されました！", null);
    }
    
    @Override
    public void sendMessage(String message, Player sender) {
        for (Player player : players) {
            if (player != sender) {
                player.receiveMessage(message, sender.getName());
            }
        }
    }
    
    @Override
    public void playerAction(Player player, String action) {
        System.out.println("ゲームルーム: " + player.getName() + " が " + action + " を実行");
        notifyAll(player.getName() + " が " + action + " を実行しました", player);
    }
    
    private void notifyAll(String message, Player sender) {
        for (Player player : players) {
            if (player != sender) {
                player.receiveNotification(message);
            }
        }
    }
}

// Colleague抽象クラス
public abstract class Player {
    protected GameMediator mediator;
    protected String name;
    
    public Player(GameMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
        mediator.registerPlayer(this);
    }
    
    public String getName() {
        return name;
    }
    
    public abstract void sendMessage(String message);
    public abstract void receiveMessage(String message, String senderName);
    public abstract void receiveNotification(String notification);
    public abstract void performAction(String action);
}

// ConcreteColleague
public class GamePlayer extends Player {
    public GamePlayer(GameMediator mediator, String name) {
        super(mediator, name);
    }
    
    @Override
    public void sendMessage(String message) {
        System.out.println(name + " が送信: " + message);
        mediator.sendMessage(message, this);
    }
    
    @Override
    public void receiveMessage(String message, String senderName) {
        System.out.println(name + " が " + senderName + " から受信: " + message);
    }
    
    @Override
    public void receiveNotification(String notification) {
        System.out.println(name + " が通知を受信: " + notification);
    }
    
    @Override
    public void performAction(String action) {
        System.out.println(name + " がアクションを実行: " + action);
        mediator.playerAction(this, action);
    }
}

// 使用例
public class GameExample {
    public static void main(String[] args) {
        GameMediator gameRoom = new GameRoom();
        
        Player player1 = new GamePlayer(gameRoom, "プレイヤー1");
        Player player2 = new GamePlayer(gameRoom, "プレイヤー2");
        Player player3 = new GamePlayer(gameRoom, "プレイヤー3");
        
        player1.sendMessage("準備できました！");
        player2.performAction("攻撃");
        player3.performAction("防御");
    }
}
```

---

## まとめ

### 学習のポイント

1. **メディエーターパターンの目的**: 複数のオブジェクト間の複雑な通信を中央集約化し、疎結合を実現
2. **基本的な構造**: Mediatorインターフェース、ConcreteMediator、Colleague抽象クラス、ConcreteColleague
3. **通信の集約**: すべての通信をメディエーターを通じて行う
4. **疎結合の実現**: オブジェクト同士が直接参照しないため、結合度が低い

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 疎結合、再利用性、保守性、理解しやすさ、拡張性 |
| **注意点** | メディエーターの複雑化、単一障害点、パフォーマンス、過度な使用 |
| **適用場面** | GUIアプリケーション、チャットアプリケーション、航空管制システム、取引システム、ゲーム開発 |

### 他のパターンとの関係

- **Observer**: メディエーターがオブザーバーパターンを使用してコンポーネントに通知することがある
- **Facade**: メディエーターは複数のコンポーネントへの統一インターフェースを提供する点でFacadeと類似
- **Command**: メディエーターがコマンドパターンを使用してリクエストを処理することがある

### 注意点

1. **メディエーターの複雑化**: 多くのコンポーネントが存在する場合、メディエーターが複雑になるため、適切に設計する
2. **過度な使用を避ける**: シンプルな相互作用では過剰な設計になる可能性がある
3. **パフォーマンス**: すべての通信がメディエーターを経由するため、パフォーマンスに注意する
4. **単一責任の原則**: メディエーターが複数の責任を持たないようにする

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. GUIアプリケーションでメディエーターパターンを適用してみる
3. Observerパターンと組み合わせた実装を試してみる
4. Facadeパターンを学習する（メディエーターと類似のパターン）
5. Commandパターンを学習する（メディエーターと組み合わせて使用）

### 参考資料

- [cs-techblog.com - メディエーターパターン](https://cs-techblog.com/technical/mediator-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、メディエーターパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
