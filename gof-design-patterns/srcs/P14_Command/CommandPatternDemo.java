package P14_Command;

// ようは
// 
// 1. コマンドインターフェースを実装した、コマンドクラスが複数ある。
// 2. そのコマンドクラスは、生成時にレシーバー（実際に処理するひと）を渡す
// 3. コマンドクラスを実行すると、レシーバーにリクエストを行う。
// 4. そのコマンドクラスを受け取って、コマンドを実行するインボーカーもいる。
// 5. インボーカーに、生成した具象コマンドを渡して、インボークする事で、コマンドを実行する。。。。
// 
// リクエストをオブジェクトとして扱う。⇒まあ解るっちゃ解る。
// 送信者と受信者の分離。⇒まあ解るっちゃ解る。インボーカーはレシーバーを知らない。レシーバーを知っているのはコマンド
// 
// うーん、でも、これは具体例がイメージできなさすぎるな。

// 1. Commandインターフェース
interface Command {
    void execute();
}

// 2. Receiverクラス（実際の処理を行うクラス）
class Light {
    public void on() {
        System.out.println("Light is ON");
    }
    
    public void off() {
        System.out.println("Light is OFF");
    }
}

// 3. ConcreteCommandクラス
class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.on();
    }
}

class LightOffCommand implements Command {
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
class RemoteControl {
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
