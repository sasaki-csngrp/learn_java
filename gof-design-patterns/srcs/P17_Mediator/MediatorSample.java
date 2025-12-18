package P17_Mediator;

// メディエータ（調停者・コントローラー）として、スマートホームのコントローラーを作成する。
// 同僚（コンポーネント）は、照明、カーテン、空調の3つを準備する。
// クライアント（ユーザー、マネージャー、mainメソッド）は、個々のコンポーネントを起動しなくて良くて、
// メディエーター（調停者・コントローラー）に起動を伝えるだけでよい。
// 
// Hey! Mediator! 朝モードにして！！
// かしこまりました。照明を点けます。カーテンを空けます。エアコンをつけます。
// みたいな。
// 
// ぶっちゃけ、このパターンだとメディエーターはNotify（通知）し、
// Colleague（コンポーネント）はReceive（受信）するだけで済む。
// つまり、メディエーターがコントローラーで、コンポーネントが部屋の機器だと思えば良い。
//
// これで、メディエーターも概要はOKかな。

// イベントの種類を定義
enum EventType {
    MORNING_MODE,
    NIGHT_MODE,
}

// （同僚だと解り辛いから）コンポーネントインターフェースと名付けておく
interface Component {
    // イベントを受け取る
    void receive(EventType event);
}

// 電気を操作するクラス
class Light implements Component{

    public void turnOn() { 
        System.out.println("照明をつけます"); 
    }
    public void turnOff() { 
        System.out.println("照明を消します"); 
    }

    /**
     * Lightクラスは、Colleagueインターフェースを実装しています。
     * 受け取ったイベントに応じて、照明を操作します。
     */
    @Override
    public void receive(EventType event) {
        switch (event) {
            case MORNING_MODE:
                turnOn();
                break;
            case NIGHT_MODE:
                turnOff();
                break;
            default:
                break;
        }
    }
}

// カーテンを操作するクラス
class Curtain implements Component {

    // カーテンはオン・オフではなくオープン・クローズだから、
    // あえてabstract クラスは利用していない。
    public void open() { 
        System.out.println("カーテンを開けます");
    }
    public void close() { 
        System.out.println("カーテンを閉めます"); 
    }

    /**
     * Curtainクラスは、Colleagueインターフェースを実装しています。
     * 受け取ったイベントに応じて、カーテンを操作します。
     */
    @Override
    public void receive(EventType event) {
        switch (event) {
            case MORNING_MODE:
                open();
                break;
            case NIGHT_MODE:
                close();
                break;
            default:
                break;
        }
    }
}

// 空調を操作するクラス
class AirConditioner implements Component {

    public void turnOn() { 
        System.out.println("エアコンをつけます"); 
    }
    public void turnOff() { 
        System.out.println("エアコンを消します"); 
    }

    /**
     * AirConditionerクラスは、Colleagueインターフェースを実装しています。
     * 受け取ったイベントに応じて、エアコンを操作します。
     */
    @Override
    public void receive(EventType event) {
        switch (event) {
            case MORNING_MODE:
                turnOn();
                break;
            case NIGHT_MODE:
                turnOff();
                break;
            default:
                break;
        }
    }
}

// メディエーター（調停者。コントローラーの方が解りやすい？）インターフェース
interface Mediator {
    // イベントを通知する
    void notify(EventType event);
}

// スマートホームのメディエータークラス
class SmartHomeMediator implements Mediator {
    // メディエーターは抽象インターフェース（コンポーネント）だけを意識し、
    // 具象クラスを意識しないように変更したら、とってもすっきりした。

    // コンポーネントのリストを内包している
    private java.util.List<Component> components;

    public SmartHomeMediator() {
        this.components = new java.util.ArrayList<>();
    }

    // コンポーネントのリストを設定する
    public void setDevices(java.util.List<Component> devices) {
        this.components.clear();
        this.components.addAll(devices);
    }

    // メディエーター（調停者）がイベントを通知する(notifyメソッド)
    // コンポーネント（同僚）はイベントを受け取る（receiveメソッド）
    @Override
    public void notify(EventType event) {
        for (Component component : components) {
            component.receive(event);
        }
    }
}

public class MediatorSample {
    public static void main(String[] args) {
        // Componentのリストを作成し、各インスタンスを格納
        java.util.List<Component> devices = new java.util.ArrayList<>();
        devices.add(new Light());
        devices.add(new Curtain());
        devices.add(new AirConditioner());

        SmartHomeMediator mediator = new SmartHomeMediator();
        mediator.setDevices(devices);

        // 朝モード
        System.out.println("---部屋を朝モードにします---");
        mediator.notify(EventType.MORNING_MODE);
        // 夜モード
        System.out.println("---部屋を夜モードにします---");
        mediator.notify(EventType.NIGHT_MODE);
    }
}