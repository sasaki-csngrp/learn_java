package P17_Mediator;

// うーん、これはイマイチ理解しきれない。
// Mdediator と Colleague の関係がよくわからない。
// お互いに、相手を内包しているような印象。
// これは飛ばして、先に進もう。

// 1. Mediatorインターフェース
interface Mediator {
    void notify(Colleague sender, String event);
}

// 2. Colleague抽象クラス
abstract class Colleague {
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
class ComponentA extends Colleague {
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

class ComponentB extends Colleague {
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
class ConcreteMediator implements Mediator {
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
