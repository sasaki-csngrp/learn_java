package P09_Decorator;

// 1. Component（コンポーネントインターフェース）
interface Coffee {
    String getDescription();
    double getCost();
}

// 2. ConcreteComponent（具象コンポーネント）
// デコレートされる、大元のコンポーネント
class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "シンプルコーヒー";
    }
    
    @Override
    public double getCost() {
        return 2.0;
    }
}

// 3. Decorator（デコレーター抽象クラス）
// デコレートするための抽象クラス。
// デコレートされるコンポーネントと同じインターフェースを実装し、
// 大元のコンポーネントをラップする。
abstract class CoffeeDecorator implements Coffee {
    // デコレートされるコンポーネント
    protected Coffee decoratedCoffee;
    
    // デコレートされるコンポーネントを、コンストラクタで受取る
    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost();
    }
}

// 4. ConcreteDecorator（具象デコレーター）
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }
    
    // 継承している訳ではないので、super を使わず、
    // 大元のコンポーネントのメソッドを直接呼び出し、
    // それを修飾（デコレート）して返す。
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", ミルク";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.5;
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", 砂糖";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.2;
    }
}

class WhippedCreamDecorator extends CoffeeDecorator {
    public WhippedCreamDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", ホイップクリーム";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.7;
    }
}

// 5. クライアントコード
public class CoffeeShop {
    public static void main(String[] args) {
        // シンプルコーヒー
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());
        
        // ミルクを追加
        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());
        
        // 砂糖を追加
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());
        
        // ホイップクリームを追加
        coffee = new WhippedCreamDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());
    }
}
