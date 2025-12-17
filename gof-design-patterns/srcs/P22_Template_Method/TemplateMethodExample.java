package P22_Template_Method;

// まあ、抽象クラスで骨組みを作って、それをサブクラスで実装骨組みみたいなイメージか。
// 抽象メソッドがProtected な理由は、サブクラスからのみアクセス可能なようにするため。

// 1. AbstractClass（抽象クラス）
abstract class DataProcessor {
    // テンプレートメソッド（finalでオーバーライドを防止）
    public final void process() {
        readData();
        processData();
        saveData();
    }
    
    // 抽象メソッド（サブクラスで実装必須）
    protected abstract void readData();
    protected abstract void processData();
    protected abstract void saveData();
}

// 2. ConcreteClass（具象クラス）
class CSVDataProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("CSVファイルからデータを読み込み");
    }
    
    @Override
    protected void processData() {
        System.out.println("CSVデータを処理");
    }
    
    @Override
    protected void saveData() {
        System.out.println("処理済みデータをCSVファイルに保存");
    }
}

class XMLDataProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("XMLファイルからデータを読み込み");
    }
    
    @Override
    protected void processData() {
        System.out.println("XMLデータを処理");
    }
    
    @Override
    protected void saveData() {
        System.out.println("処理済みデータをXMLファイルに保存");
    }
}

public class TemplateMethodExample {
    public static void main(String[] args) {

        // DataProcessor という名前でポリモーフィズムされる
        // クライアントは具体的なサブクラスの実装（CSV vs XML）を全く知る必要がない。
        System.out.println("=== CSV処理 ===");
        DataProcessor csvProcessor = new CSVDataProcessor();
        csvProcessor.process();
        
        System.out.println("\n=== XML処理 ===");
        DataProcessor xmlProcessor = new XMLDataProcessor();
        xmlProcessor.process();
    }
}
