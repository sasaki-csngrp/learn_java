package P20_State;

// ポイントとしては、Contextクラスが、Stateの状態を保持・制御するが、
// 具体的な状態（開始状態、実行中状態、停止状態）は、Contextクラスで管理するので、
// Contextクラスは、具体的な状態を知らないで良い。　って感じか。
//
// これの、どこが社長起立なのか、全く理解できない。
// （そもそも、ポリモーフィズム使ってないじゃん。。。）
// 状態　＜－　開始・実行中・停止　が
// 社員　＜－　担当・主任・部長　って事？？？

// 1. Stateインターフェース
interface State {
    // handole 状態を操作・管理する。
    // EX：開始⇒実行中。実行中⇒停止。停止⇒開始。等々
    void handle(Context context);
    // getStateName 状態の名前を取得する。
    // EX：開始、実行中、停止。等々
    String getStateName();
}

// 2. ConcreteState（具象状態）
// 具体的な状態を実装する。
// EX：開始状態、実行中状態、停止状態。等々
class StartState implements State {
    // 開始状態で処理を実行
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

// 実行中状態
class RunningState implements State {
    // 実行中状態で処理を実行
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

// 停止状態
class StopState implements State {
    // 停止状態で処理を実行
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
// 現在の状態を保持し、状態に依存する操作を委譲するクラス。
// 状態の変更を管理する。
class Context {
    // 現在の状態を保持
    private State currentState;
    
    public Context() {
        // 初期状態を設定(開始状態)
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

public class StateExample {
    public static void main(String[] args) {
        Context context = new Context();
        
        // 現在の状態で処理を実行
        // 最初は開始状態からの状態遷移を実行
        context.request();
        System.out.println("現在の状態: " + context.getState().getStateName());
        
        // 状態を変更(実行中状態)
        context.setState(new RunningState());
        // 実行中状態からの状態遷移を実行
        context.request();
        System.out.println("現在の状態: " + context.getState().getStateName());
        
        // 状態を変更(停止状態)
        context.setState(new StopState());
        // 停止状態からの状態遷移を実行
        context.request();
        System.out.println("現在の状態: " + context.getState().getStateName());
    }
}
