package P18_Memento;

import java.util.ArrayList;
import java.util.List;

// 状態を直接配列にリスト（セーブ・ロード）せず、Mementoをリストに使う理由
// 
// 1. Mementoでカプセル化する事により、Orijinator と Caretaker を疎結合にする。
// 　 どちらも、Mementoだけ意識すれば良い。
// 　 お互いの仕様変更を Memento が吸収できる。
// 
// 2. この例では単純な文字列だから、そのまま配列にしても？と思えるが、
// 　 複数のフィールドを保持する場合は、クラス化がどちらにせよ必要。（＝Mementoにするだけ）
// 
// 3. Memento の ステートは
//      private final String state;
//    とfinal修飾子がついている。
// 　 ＝意図せず変更される事を防げる。
// 　 ＝不変性（Immutability）の保証
// 
// その他にも、
// 4. 型安全性と意図の明確化   Memento型に統一
// 5. 拡張性：メタデータの追加 Mementoにフィールド追加
// 6. アクセス制御             Privateや内部クラスで、アクセス者を限定
// 
// などのメリットもある。

// 1. Memento（メメント）
class Memento {
    // 保存したい状態を保持する。セーブ内容。
    private final String state;
    
    // MementoはOriginatorからのみ作成可能（パッケージプライベートまたは内部クラス）
    // この例では、default（修飾子無し）なので、同じパッケージ内からのみアクセス可能。
    Memento(String state) {
        this.state = state;
    }
    
    String getState() {
        return state;
    }
}

// 2. Originator（オリジネーター）
// 元になり、状態を保存・復元する事ができるオブジェクト。
class Originator {
    // 普通にステート
    private String state;
    
    public void setState(String state) {
        System.out.println("状態を設定: " + state);
        this.state = state;
    }
    
    public String getState() {
        return state;
    }
    
    // 現在の状態をMemento（形見）として保存
    // 受け取った側（クライアント）は、そのメメントをCaretaker（世話人）に追加する。
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
// 管理人として、Mementoのリストを管理する。
class Caretaker {
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
