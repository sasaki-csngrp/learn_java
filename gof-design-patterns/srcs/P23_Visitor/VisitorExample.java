package P23_Visitor;

import java.util.ArrayList;
import java.util.List;

// 1. Visitorインターフェース
// 新しい操作（visitメソッド）を実行するインターフェース
// ここで、具体的な具象クラスに依存している事に違和感を感じるが、
// これがVisitorパターンのポイントであり、Visitorパターンの利点であるらしい。
// この設計は、GoF の Visitor パターンの標準的な実装です。
// 要素型が比較的固定で、操作（Visitor）を頻繁に追加する場合に適しています。　との事。
// ConcreteVisitorは頻繁に増えるが、ConcreteElementは比較的固定というケースに有効。

interface Visitor {
    // 具象化エレメントAが渡された場合は、こちらが実行される。
    void visit(ConcreteElementA element);
    // 具象化エレメントBが渡された場合は、こちらが実行される。
    void visit(ConcreteElementB element);
}

// 2. ConcreteVisitor（具象ビジター）
class ConcreteVisitor1 implements Visitor {
    @Override
    public void visit(ConcreteElementA element) {
        System.out.println("Visitor1がConcreteElementAを処理: " + element.getData());
    }
    
    @Override
    public void visit(ConcreteElementB element) {
        System.out.println("Visitor1がConcreteElementBを処理: " + element.getData());
    }
}

class ConcreteVisitor2 implements Visitor {
    @Override
    public void visit(ConcreteElementA element) {
        System.out.println("Visitor2がConcreteElementAを処理: " + element.getData());
    }
    
    @Override
    public void visit(ConcreteElementB element) {
        System.out.println("Visitor2がConcreteElementBを処理: " + element.getData());
    }
}

// 3. Elementインターフェース
// 新しい操作を受け入れる、オブジェクト構造
// Visitor をacceptメソッドで受け入れる。
interface Element {
    void accept(Visitor visitor);
}

// 4. ConcreteElement（具象要素）
class ConcreteElementA implements Element {
    private String data;
    
    public ConcreteElementA(String data) {
        this.data = data;
    }
    
    public String getData() {
        return data;
    }
    
    @Override
    public void accept(Visitor visitor) {
        // this で具象エレメントAを渡す事で、具体的なVisitorのvisitメソッドが呼ばれる。
        visitor.visit(this);
    }
}

class ConcreteElementB implements Element {
    private String data;
    
    public ConcreteElementB(String data) {
        this.data = data;
    }
    
    public String getData() {
        return data;
    }
    
    @Override
    public void accept(Visitor visitor) {
        // this で具象エレメントBを渡す事で、具体的なVisitorのvisitメソッドが呼ばれる。
        visitor.visit(this);
    }
}

public class VisitorExample {
    public static void main(String[] args) {
        List<Element> elements = new ArrayList<>();
        elements.add(new ConcreteElementA("要素Aのデータ"));
        elements.add(new ConcreteElementB("要素Bのデータ"));
        
        Visitor visitor1 = new ConcreteVisitor1();
        Visitor visitor2 = new ConcreteVisitor2();
        
        System.out.println("=== Visitor1による処理 ===");
        for (Element element : elements) {
            element.accept(visitor1);
        }
        
        System.out.println("\n=== Visitor2による処理 ===");
        for (Element element : elements) {
            element.accept(visitor2);
        }
    }
}
