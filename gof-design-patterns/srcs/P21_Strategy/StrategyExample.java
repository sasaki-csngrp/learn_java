package P21_Strategy;

// ステートパターンと構造が類似しているが、
// 目的が異なる（状態の管理 vs アルゴリズムの選択）
// 戦略　＜－　加算・減算・乗算・除算

// 1. Strategyインターフェース
interface Strategy {
    int execute(int a, int b);
    String getStrategyName();
}

// 2. ConcreteStrategy（具象ストラテジー）
class AdditionStrategy implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a + b;
    }
    
    @Override
    public String getStrategyName() {
        return "加算";
    }
}

class SubtractionStrategy implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a - b;
    }
    
    @Override
    public String getStrategyName() {
        return "減算";
    }
}

class MultiplicationStrategy implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a * b;
    }
    
    @Override
    public String getStrategyName() {
        return "乗算";
    }
}

class DivisionStrategy implements Strategy {
    @Override
    public int execute(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("ゼロで割ることはできません");
        }
        return a / b;
    }
    
    @Override
    public String getStrategyName() {
        return "除算";
    }
}

// 3. Context（コンテキスト）
// 計算機という名前のコンテキスト
class Calculator {
    // 戦略とい名前のアルゴリズム（加算・減算・乗算・除算）
    private Strategy strategy;
    
    public Calculator(Strategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    
    // 計算の実行
    public int calculate(int a, int b) {
        System.out.println("ストラテジー: " + strategy.getStrategyName());
        // 戦略を実行するだけ。（ポリモーフィズム）
        return strategy.execute(a, b);
    }
}

public class StrategyExample {
    public static void main(String[] args) {
        Calculator calculator = new Calculator(new AdditionStrategy());
        
        System.out.println("10 + 5 = " + calculator.calculate(10, 5));
        
        calculator.setStrategy(new SubtractionStrategy());
        System.out.println("10 - 5 = " + calculator.calculate(10, 5));
        
        calculator.setStrategy(new MultiplicationStrategy());
        System.out.println("10 * 5 = " + calculator.calculate(10, 5));
        
        calculator.setStrategy(new DivisionStrategy());
        System.out.println("10 / 5 = " + calculator.calculate(10, 5));
    }
}