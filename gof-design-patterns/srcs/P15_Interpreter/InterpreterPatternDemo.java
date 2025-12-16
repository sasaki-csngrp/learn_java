package P15_Interpreter;

// これを使う事は、ほとんどないだろう。なので概要だけ。
// 
// 1. Expression（直訳すると表現だけど、式の方が近い？）は interpret()で数値を返せる。
// 2. 数字自体（Number）も、Add や Sub 等の計算も、Expression を実装。
// 3. そして、その組み合わせを、一気にinterpret()して、計算した値を取得する。

// 1. AbstractExpressionインターフェース
interface Expression {
    int interpret();
}

// 2. TerminalExpressionクラス（数値を表す）
class Number implements Expression {
    private int number;
    
    public Number(int number) {
        this.number = number;
    }
    
    @Override
    public int interpret() {
        return number;
    }
}

// 3. NonTerminalExpressionクラス（加算を表す）
class Add implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;
    
    public Add(Expression left, Expression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }
    
    @Override
    public int interpret() {
        return leftExpression.interpret() + rightExpression.interpret();
    }
}

// 4. NonTerminalExpressionクラス（減算を表す）
class Subtract implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;
    
    public Subtract(Expression left, Expression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }
    
    @Override
    public int interpret() {
        return leftExpression.interpret() - rightExpression.interpret();
    }
}

// 5. クライアントコード
public class InterpreterPatternDemo {
    public static void main(String[] args) {
        System.out.println("=== インタープリターパターンのデモ ===\n");
        
        // 式 "5 + 3 - 2" を構築
        // これは ((5 + 3) - 2) として解釈される
        Expression expression = new Subtract(
            new Add(new Number(5), new Number(3)),
            new Number(2)
        );
        
        // 式を評価
        int result = expression.interpret();
        System.out.println("Expression: 5 + 3 - 2");
        System.out.println("Result: " + result);
        
        // 別の式 "10 - 3 + 2" を構築
        Expression expression2 = new Add(
            new Subtract(new Number(10), new Number(3)),
            new Number(2)
        );
        
        int result2 = expression2.interpret();
        System.out.println("\nExpression: 10 - 3 + 2");
        System.out.println("Result: " + result2);
    }
}
