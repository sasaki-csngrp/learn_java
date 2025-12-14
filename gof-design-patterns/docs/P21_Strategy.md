# ストラテジーパターン（Strategy Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [ストラテジーパターンとは](#ストラテジーパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

ストラテジーパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**振る舞いに関するパターン（Behavioral Pattern）**に分類されます。

このパターンは、アルゴリズムをカプセル化し、実行時にアルゴリズムを選択できるようにします。if-elseやswitch文による条件分岐を削減し、コードをより保守しやすく、拡張しやすくします。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- ストラテジーパターンの目的と利点を理解する
- 基本的なストラテジーパターンの実装方法を理解する
- アルゴリズムをカプセル化する方法を理解する
- 実行時にアルゴリズムを選択できるようにする
- 実際のプロジェクトでストラテジーパターンを適用できる

---

## ストラテジーパターンとは

### 定義

ストラテジーパターンは、アルゴリズムをカプセル化し、実行時にアルゴリズムを選択できるようにするデザインパターンです。同じ問題に対して複数の解決方法がある場合、各方法を独立したクラスとして表現し、実行時に選択できるようにします。

### 主な特徴

1. **アルゴリズムのカプセル化**: 各アルゴリズムを独立したクラスとして表現
2. **実行時の選択**: 実行時にアルゴリズムを選択可能
3. **条件分岐の削減**: if-elseやswitch文による条件分岐を削減
4. **拡張性**: 新しいアルゴリズムを追加する際に、既存のコードを変更する必要がない

### 使用される場面

ストラテジーパターンは、以下のような場面で使用されます：

- **ソートアルゴリズム**: 異なるソートアルゴリズム（クイックソート、マージソート、バブルソートなど）の選択
- **支払い方法**: 異なる支払い方法（クレジットカード、PayPal、銀行振込など）の処理
- **データ圧縮**: 異なる圧縮アルゴリズム（ZIP、RAR、7Zなど）の選択
- **検索アルゴリズム**: 異なる検索アルゴリズム（線形検索、二分検索など）の選択
- **計算方法**: 異なる計算方法（加算、減算、乗算、除算など）の選択
- **レポート生成**: 異なる形式（PDF、Excel、CSVなど）でのレポート生成
- **認証方式**: 異なる認証方式（パスワード、OAuth、LDAPなど）の選択

### メリット

- **保守性**: アルゴリズムごとの実装が独立したクラスに分離されるため、保守が容易
- **拡張性**: 新しいアルゴリズムを追加する際に、既存のコードを変更する必要がない
- **可読性**: 条件分岐が削減され、コードが読みやすくなる
- **単一責任の原則**: 各ストラテジークラスが単一の責任を持つ
- **テスト容易性**: 各アルゴリズムを独立してテストできる
- **実行時の選択**: 実行時にアルゴリズムを選択できるため、柔軟性が高い

### デメリット

- **クラス数の増加**: アルゴリズムの数だけクラスが増加する
- **複雑性の増加**: シンプルなケースでは過剰な設計になる可能性がある
- **クライアントの知識**: クライアントが適切なストラテジーを選択する必要がある
- **オーバーヘッド**: ストラテジーオブジェクトの作成によるオーバーヘッドが発生する可能性がある

---

## 基本的な実装

### 実装のポイント

ストラテジーパターンを実装するには、以下の要素が必要です：

1. **Strategyインターフェース（Strategy）**: アルゴリズムの共通インターフェース
2. **ConcreteStrategy（具象ストラテジー）**: Strategyインターフェースを実装し、特定のアルゴリズムを実装
3. **Context（コンテキスト）**: Strategyオブジェクトへの参照を保持し、ストラテジーを使用するクラス

### 基本的な実装例

```java
// 1. Strategyインターフェース
public interface Strategy {
    int execute(int a, int b);
    String getStrategyName();
}

// 2. ConcreteStrategy（具象ストラテジー）
public class AdditionStrategy implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a + b;
    }
    
    @Override
    public String getStrategyName() {
        return "加算";
    }
}

public class SubtractionStrategy implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a - b;
    }
    
    @Override
    public String getStrategyName() {
        return "減算";
    }
}

public class MultiplicationStrategy implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a * b;
    }
    
    @Override
    public String getStrategyName() {
        return "乗算";
    }
}

public class DivisionStrategy implements Strategy {
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
public class Calculator {
    private Strategy strategy;
    
    public Calculator(Strategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    
    public int calculate(int a, int b) {
        System.out.println("ストラテジー: " + strategy.getStrategyName());
        return strategy.execute(a, b);
    }
}
```

### 使用例

```java
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
```

### パターンの構造

```
Context
  ├─ strategy: Strategy
  ├─ setStrategy(Strategy)
  └─ execute() → strategy.execute()
        ↓
Strategy (インターフェース)
  └─ execute()
        ↓
ConcreteStrategy1
  └─ execute() [実装]
        ↓
ConcreteStrategy2
  └─ execute() [実装]
```

---

## 実装のバリエーション

### バリエーション1: 関数型インターフェースを使用

Java 8以降の関数型インターフェースを使用した実装です。

```java
import java.util.function.BinaryOperator;

public class FunctionalCalculator {
    private BinaryOperator<Integer> operation;
    private String operationName;
    
    public FunctionalCalculator(BinaryOperator<Integer> operation, String operationName) {
        this.operation = operation;
        this.operationName = operationName;
    }
    
    public void setOperation(BinaryOperator<Integer> operation, String operationName) {
        this.operation = operation;
        this.operationName = operationName;
    }
    
    public int calculate(int a, int b) {
        System.out.println("操作: " + operationName);
        return operation.apply(a, b);
    }
}

// 使用例
public class FunctionalStrategyExample {
    public static void main(String[] args) {
        FunctionalCalculator calculator = new FunctionalCalculator(
            (a, b) -> a + b, "加算");
        System.out.println("10 + 5 = " + calculator.calculate(10, 5));
        
        calculator.setOperation((a, b) -> a * b, "乗算");
        System.out.println("10 * 5 = " + calculator.calculate(10, 5));
    }
}
```

### バリエーション2: ストラテジーファクトリー

ストラテジーを生成するファクトリーを使用する方法です。

```java
import java.util.HashMap;
import java.util.Map;

public class StrategyFactory {
    private Map<String, Strategy> strategies = new HashMap<>();
    
    public StrategyFactory() {
        registerStrategy("add", new AdditionStrategy());
        registerStrategy("subtract", new SubtractionStrategy());
        registerStrategy("multiply", new MultiplicationStrategy());
        registerStrategy("divide", new DivisionStrategy());
    }
    
    public void registerStrategy(String key, Strategy strategy) {
        strategies.put(key, strategy);
    }
    
    public Strategy getStrategy(String key) {
        Strategy strategy = strategies.get(key);
        if (strategy == null) {
            throw new IllegalArgumentException("未知のストラテジー: " + key);
        }
        return strategy;
    }
    
    public void listStrategies() {
        System.out.println("利用可能なストラテジー:");
        strategies.keySet().forEach(key -> 
            System.out.println("  " + key + ": " + strategies.get(key).getStrategyName()));
    }
}

// 使用例
public class StrategyFactoryExample {
    public static void main(String[] args) {
        StrategyFactory factory = new StrategyFactory();
        factory.listStrategies();
        
        Calculator calculator = new Calculator(factory.getStrategy("add"));
        System.out.println("10 + 5 = " + calculator.calculate(10, 5));
        
        calculator.setStrategy(factory.getStrategy("multiply"));
        System.out.println("10 * 5 = " + calculator.calculate(10, 5));
    }
}
```

### バリエーション3: デフォルトストラテジー

デフォルトのストラテジーを提供する方法です。

```java
public class CalculatorWithDefault {
    private Strategy strategy;
    private Strategy defaultStrategy;
    
    public CalculatorWithDefault() {
        this.defaultStrategy = new AdditionStrategy();
        this.strategy = defaultStrategy;
    }
    
    public CalculatorWithDefault(Strategy strategy) {
        this.defaultStrategy = new AdditionStrategy();
        this.strategy = strategy != null ? strategy : defaultStrategy;
    }
    
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy != null ? strategy : defaultStrategy;
    }
    
    public void resetToDefault() {
        this.strategy = defaultStrategy;
    }
    
    public int calculate(int a, int b) {
        return strategy.execute(a, b);
    }
}
```

### バリエーション4: 複数のパラメータを持つストラテジー

複数のパラメータを受け取るストラテジーの例です。

```java
import java.util.List;

// Strategyインターフェース
public interface SortingStrategy {
    void sort(List<Integer> list);
    String getStrategyName();
}

// ConcreteStrategy
public class QuickSortStrategy implements SortingStrategy {
    @Override
    public void sort(List<Integer> list) {
        System.out.println("クイックソートを実行");
        // クイックソートの実装（簡略化）
        list.sort(Integer::compareTo);
    }
    
    @Override
    public String getStrategyName() {
        return "クイックソート";
    }
}

public class MergeSortStrategy implements SortingStrategy {
    @Override
    public void sort(List<Integer> list) {
        System.out.println("マージソートを実行");
        // マージソートの実装（簡略化）
        list.sort(Integer::compareTo);
    }
    
    @Override
    public String getStrategyName() {
        return "マージソート";
    }
}

public class BubbleSortStrategy implements SortingStrategy {
    @Override
    public void sort(List<Integer> list) {
        System.out.println("バブルソートを実行");
        // バブルソートの実装
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    int temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
    
    @Override
    public String getStrategyName() {
        return "バブルソート";
    }
}

// Context
import java.util.ArrayList;
import java.util.Arrays;

public class Sorter {
    private SortingStrategy strategy;
    
    public Sorter(SortingStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void sort(List<Integer> list) {
        System.out.println("ストラテジー: " + strategy.getStrategyName());
        strategy.sort(list);
    }
}
```

---

## 実践例

### 例1: 支払い方法の選択

異なる支払い方法（クレジットカード、PayPal、銀行振込）を処理する例です。

```java
// Strategyインターフェース
public interface PaymentStrategy {
    void pay(double amount);
    String getPaymentMethod();
}

// ConcreteStrategy
public class CreditCardStrategy implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;
    private String cvv;
    private String expiryDate;
    
    public CreditCardStrategy(String cardNumber, String cardHolderName, 
                              String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("クレジットカードで " + amount + " 円を支払います");
        System.out.println("カード番号: " + maskCardNumber(cardNumber));
        System.out.println("カード名義: " + cardHolderName);
    }
    
    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() > 4) {
            return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }
    
    @Override
    public String getPaymentMethod() {
        return "クレジットカード";
    }
}

public class PayPalStrategy implements PaymentStrategy {
    private String email;
    
    public PayPalStrategy(String email) {
        this.email = email;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("PayPalで " + amount + " 円を支払います");
        System.out.println("PayPalアカウント: " + email);
    }
    
    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }
}

public class BankTransferStrategy implements PaymentStrategy {
    private String bankName;
    private String accountNumber;
    
    public BankTransferStrategy(String bankName, String accountNumber) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("銀行振込で " + amount + " 円を支払います");
        System.out.println("銀行名: " + bankName);
        System.out.println("口座番号: " + accountNumber);
    }
    
    @Override
    public String getPaymentMethod() {
        return "銀行振込";
    }
}

// Context
public class ShoppingCart {
    private PaymentStrategy paymentStrategy;
    
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    
    public void checkout(double amount) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("支払い方法が選択されていません");
        }
        System.out.println("=== チェックアウト ===");
        System.out.println("支払い方法: " + paymentStrategy.getPaymentMethod());
        paymentStrategy.pay(amount);
    }
}

// 使用例
public class PaymentStrategyExample {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        
        // クレジットカードで支払い
        cart.setPaymentStrategy(new CreditCardStrategy(
            "1234567890123456", "山田太郎", "123", "12/25"));
        cart.checkout(10000.0);
        System.out.println();
        
        // PayPalで支払い
        cart.setPaymentStrategy(new PayPalStrategy("user@example.com"));
        cart.checkout(5000.0);
        System.out.println();
        
        // 銀行振込で支払い
        cart.setPaymentStrategy(new BankTransferStrategy("みずほ銀行", "1234567"));
        cart.checkout(20000.0);
    }
}
```

### 例2: ソートアルゴリズムの選択

異なるソートアルゴリズムを選択する例です。

```java
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// Strategyインターフェース
public interface SortStrategy {
    void sort(List<Integer> list);
    String getStrategyName();
}

// ConcreteStrategy
public class QuickSortStrategy implements SortStrategy {
    @Override
    public void sort(List<Integer> list) {
        quickSort(list, 0, list.size() - 1);
    }
    
    private void quickSort(List<Integer> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }
    
    private int partition(List<Integer> list, int low, int high) {
        int pivot = list.get(high);
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (list.get(j) < pivot) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }
    
    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
    
    @Override
    public String getStrategyName() {
        return "クイックソート";
    }
}

public class MergeSortStrategy implements SortStrategy {
    @Override
    public void sort(List<Integer> list) {
        mergeSort(list, 0, list.size() - 1);
    }
    
    private void mergeSort(List<Integer> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(list, left, mid);
            mergeSort(list, mid + 1, right);
            merge(list, left, mid, right);
        }
    }
    
    private void merge(List<Integer> list, int left, int mid, int right) {
        List<Integer> leftList = new ArrayList<>(list.subList(left, mid + 1));
        List<Integer> rightList = new ArrayList<>(list.subList(mid + 1, right + 1));
        
        int i = 0, j = 0, k = left;
        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i) <= rightList.get(j)) {
                list.set(k++, leftList.get(i++));
            } else {
                list.set(k++, rightList.get(j++));
            }
        }
        
        while (i < leftList.size()) {
            list.set(k++, leftList.get(i++));
        }
        
        while (j < rightList.size()) {
            list.set(k++, rightList.get(j++));
        }
    }
    
    @Override
    public String getStrategyName() {
        return "マージソート";
    }
}

public class BubbleSortStrategy implements SortStrategy {
    @Override
    public void sort(List<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    int temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
    
    @Override
    public String getStrategyName() {
        return "バブルソート";
    }
}

// Context
public class NumberSorter {
    private SortStrategy strategy;
    
    public NumberSorter(SortStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void sort(List<Integer> numbers) {
        System.out.println("ソートアルゴリズム: " + strategy.getStrategyName());
        System.out.println("ソート前: " + numbers);
        strategy.sort(numbers);
        System.out.println("ソート後: " + numbers);
    }
}

// 使用例
public class SortStrategyExample {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(64, 34, 25, 12, 22, 11, 90));
        
        NumberSorter sorter = new NumberSorter(new QuickSortStrategy());
        sorter.sort(new ArrayList<>(numbers));
        System.out.println();
        
        sorter.setStrategy(new MergeSortStrategy());
        sorter.sort(new ArrayList<>(numbers));
        System.out.println();
        
        sorter.setStrategy(new BubbleSortStrategy());
        sorter.sort(new ArrayList<>(numbers));
    }
}
```

### 例3: データ圧縮アルゴリズムの選択

異なる圧縮アルゴリズムを選択する例です。

```java
// Strategyインターフェース
public interface CompressionStrategy {
    byte[] compress(byte[] data);
    byte[] decompress(byte[] compressedData);
    String getAlgorithmName();
}

// ConcreteStrategy
public class ZipCompressionStrategy implements CompressionStrategy {
    @Override
    public byte[] compress(byte[] data) {
        System.out.println("ZIP形式で圧縮中...");
        // 実際の実装では、ZIPライブラリを使用
        // ここでは簡略化
        return data; // 実際には圧縮されたデータを返す
    }
    
    @Override
    public byte[] decompress(byte[] compressedData) {
        System.out.println("ZIP形式で展開中...");
        // 実際の実装では、ZIPライブラリを使用
        return compressedData; // 実際には展開されたデータを返す
    }
    
    @Override
    public String getAlgorithmName() {
        return "ZIP";
    }
}

public class RarCompressionStrategy implements CompressionStrategy {
    @Override
    public byte[] compress(byte[] data) {
        System.out.println("RAR形式で圧縮中...");
        return data;
    }
    
    @Override
    public byte[] decompress(byte[] compressedData) {
        System.out.println("RAR形式で展開中...");
        return compressedData;
    }
    
    @Override
    public String getAlgorithmName() {
        return "RAR";
    }
}

public class SevenZipCompressionStrategy implements CompressionStrategy {
    @Override
    public byte[] compress(byte[] data) {
        System.out.println("7Z形式で圧縮中...");
        return data;
    }
    
    @Override
    public byte[] decompress(byte[] compressedData) {
        System.out.println("7Z形式で展開中...");
        return compressedData;
    }
    
    @Override
    public String getAlgorithmName() {
        return "7Z";
    }
}

// Context
public class FileCompressor {
    private CompressionStrategy strategy;
    
    public FileCompressor(CompressionStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(CompressionStrategy strategy) {
        this.strategy = strategy;
    }
    
    public byte[] compress(byte[] data) {
        System.out.println("圧縮アルゴリズム: " + strategy.getAlgorithmName());
        return strategy.compress(data);
    }
    
    public byte[] decompress(byte[] compressedData) {
        System.out.println("展開アルゴリズム: " + strategy.getAlgorithmName());
        return strategy.decompress(compressedData);
    }
}

// 使用例
public class CompressionStrategyExample {
    public static void main(String[] args) {
        byte[] data = "サンプルデータ".getBytes();
        
        FileCompressor compressor = new FileCompressor(new ZipCompressionStrategy());
        byte[] compressed = compressor.compress(data);
        compressor.decompress(compressed);
        System.out.println();
        
        compressor.setStrategy(new RarCompressionStrategy());
        compressed = compressor.compress(data);
        compressor.decompress(compressed);
        System.out.println();
        
        compressor.setStrategy(new SevenZipCompressionStrategy());
        compressed = compressor.compress(data);
        compressor.decompress(compressed);
    }
}
```

### 例4: レポート生成の形式選択

異なる形式（PDF、Excel、CSV）でレポートを生成する例です。

```java
import java.util.List;
import java.util.ArrayList;

// Strategyインターフェース
public interface ReportStrategy {
    void generateReport(List<String> data);
    String getFormatName();
}

// ConcreteStrategy
public class PDFReportStrategy implements ReportStrategy {
    @Override
    public void generateReport(List<String> data) {
        System.out.println("=== PDFレポートを生成 ===");
        for (String item : data) {
            System.out.println("PDF: " + item);
        }
        System.out.println("PDFレポートが生成されました");
    }
    
    @Override
    public String getFormatName() {
        return "PDF";
    }
}

public class ExcelReportStrategy implements ReportStrategy {
    @Override
    public void generateReport(List<String> data) {
        System.out.println("=== Excelレポートを生成 ===");
        for (int i = 0; i < data.size(); i++) {
            System.out.println("Excel[" + (i + 1) + "]: " + data.get(i));
        }
        System.out.println("Excelレポートが生成されました");
    }
    
    @Override
    public String getFormatName() {
        return "Excel";
    }
}

public class CSVReportStrategy implements ReportStrategy {
    @Override
    public void generateReport(List<String> data) {
        System.out.println("=== CSVレポートを生成 ===");
        System.out.println(String.join(",", data));
        System.out.println("CSVレポートが生成されました");
    }
    
    @Override
    public String getFormatName() {
        return "CSV";
    }
}

// Context
public class ReportGenerator {
    private ReportStrategy strategy;
    
    public ReportGenerator(ReportStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(ReportStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void generate(List<String> data) {
        System.out.println("レポート形式: " + strategy.getFormatName());
        strategy.generateReport(data);
    }
}

// 使用例
public class ReportStrategyExample {
    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("項目1");
        data.add("項目2");
        data.add("項目3");
        
        ReportGenerator generator = new ReportGenerator(new PDFReportStrategy());
        generator.generate(data);
        System.out.println();
        
        generator.setStrategy(new ExcelReportStrategy());
        generator.generate(data);
        System.out.println();
        
        generator.setStrategy(new CSVReportStrategy());
        generator.generate(data);
    }
}
```

### 例5: 認証方式の選択

異なる認証方式（パスワード、OAuth、LDAP）を選択する例です。

```java
// Strategyインターフェース
public interface AuthenticationStrategy {
    boolean authenticate(String username, String credentials);
    String getAuthMethod();
}

// ConcreteStrategy
public class PasswordAuthenticationStrategy implements AuthenticationStrategy {
    @Override
    public boolean authenticate(String username, String password) {
        System.out.println("パスワード認証を実行: " + username);
        // 実際の実装では、データベースなどで認証
        // ここでは簡略化
        return password != null && password.length() >= 8;
    }
    
    @Override
    public String getAuthMethod() {
        return "パスワード認証";
    }
}

public class OAuthAuthenticationStrategy implements AuthenticationStrategy {
    @Override
    public boolean authenticate(String username, String token) {
        System.out.println("OAuth認証を実行: " + username);
        System.out.println("トークンを検証中...");
        // 実際の実装では、OAuthプロバイダーでトークンを検証
        return token != null && token.startsWith("oauth_");
    }
    
    @Override
    public String getAuthMethod() {
        return "OAuth認証";
    }
}

public class LDAPAuthenticationStrategy implements AuthenticationStrategy {
    @Override
    public boolean authenticate(String username, String password) {
        System.out.println("LDAP認証を実行: " + username);
        System.out.println("LDAPサーバーに接続中...");
        // 実際の実装では、LDAPサーバーで認証
        return username != null && password != null;
    }
    
    @Override
    public String getAuthMethod() {
        return "LDAP認証";
    }
}

// Context
public class AuthenticationService {
    private AuthenticationStrategy strategy;
    
    public AuthenticationService(AuthenticationStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(AuthenticationStrategy strategy) {
        this.strategy = strategy;
    }
    
    public boolean login(String username, String credentials) {
        System.out.println("認証方式: " + strategy.getAuthMethod());
        boolean result = strategy.authenticate(username, credentials);
        if (result) {
            System.out.println("認証成功: " + username);
        } else {
            System.out.println("認証失敗: " + username);
        }
        return result;
    }
}

// 使用例
public class AuthenticationStrategyExample {
    public static void main(String[] args) {
        AuthenticationService authService = new AuthenticationService(
            new PasswordAuthenticationStrategy());
        authService.login("user1", "password123");
        System.out.println();
        
        authService.setStrategy(new OAuthAuthenticationStrategy());
        authService.login("user2", "oauth_token_12345");
        System.out.println();
        
        authService.setStrategy(new LDAPAuthenticationStrategy());
        authService.login("user3", "ldap_password");
    }
}
```

---

## まとめ

### 学習のポイント

1. **ストラテジーパターンの目的**: アルゴリズムをカプセル化し、実行時に選択可能にする
2. **基本的な構造**: Strategyインターフェース、ConcreteStrategy、Contextの3つの要素
3. **条件分岐の削減**: if-elseやswitch文による条件分岐を削減
4. **拡張性**: 新しいアルゴリズムを追加する際に、既存のコードを変更する必要がない

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 保守性、拡張性、可読性、単一責任の原則、テスト容易性、実行時の選択 |
| **注意点** | クラス数の増加、複雑性の増加、クライアントの知識、オーバーヘッド |
| **適用場面** | ソートアルゴリズム、支払い方法、データ圧縮、検索アルゴリズム、計算方法、レポート生成、認証方式 |

### 他のパターンとの関係

- **State**: ステートパターンと構造が類似しているが、目的が異なる（状態の管理 vs アルゴリズムの選択）
- **Template Method**: テンプレートメソッドパターンと組み合わせて、アルゴリズムの骨組みを定義することがある
- **Factory**: ファクトリーパターンと組み合わせて、適切なストラテジーを生成することがある

### 注意点

1. **適切な使用**: シンプルなケースでは過剰な設計になる可能性がある
2. **ストラテジーの選択**: クライアントが適切なストラテジーを選択する必要がある
3. **パフォーマンス**: ストラテジーオブジェクトの作成によるオーバーヘッドを考慮する
4. **状態の共有**: ストラテジーが状態を持つ場合、共有に注意する

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. 支払いシステムでストラテジーパターンを適用してみる
3. ソートアルゴリズムの選択でストラテジーパターンを使用してみる
4. Stateパターンを学習する（ストラテジーパターンと類似のパターン）
5. Template Methodパターンを学習する（ストラテジーパターンと組み合わせて使用）

### 参考資料

- [cs-techblog.com - ストラテジーパターン](https://cs-techblog.com/technical/strategy-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、ストラテジーパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
