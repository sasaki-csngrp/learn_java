# テンプレートメソッドパターン（Template Method Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [テンプレートメソッドパターンとは](#テンプレートメソッドパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

テンプレートメソッドパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**振る舞いに関するパターン（Behavioral Pattern）**に分類されます。

このパターンは、アルゴリズムの骨組みを抽象クラスで定義し、一部のステップをサブクラスで実装できるようにします。アルゴリズムの構造を統一しつつ、特定のステップをカスタマイズできます。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- テンプレートメソッドパターンの目的と利点を理解する
- 基本的なテンプレートメソッドパターンの実装方法を理解する
- アルゴリズムの骨組みを定義する方法を理解する
- フックメソッドの使用方法を理解する
- 実際のプロジェクトでテンプレートメソッドパターンを適用できる

---

## テンプレートメソッドパターンとは

### 定義

テンプレートメソッドパターンは、アルゴリズムの骨組みを抽象クラスで定義し、一部のステップをサブクラスで実装できるようにするデザインパターンです。アルゴリズムの構造を統一しつつ、特定のステップをカスタマイズできます。

### 主な特徴

1. **アルゴリズムの骨組み**: 抽象クラスでアルゴリズムの構造を定義
2. **ステップのカスタマイズ**: サブクラスで特定のステップを実装
3. **コードの再利用**: 共通の処理を抽象クラスに集約
4. **制御の反転**: 抽象クラスがサブクラスのメソッドを呼び出す

### 使用される場面

テンプレートメソッドパターンは、以下のような場面で使用されます：

- **フレームワーク**: アプリケーションフレームワークのライフサイクル管理
- **データ処理**: データの読み込み、処理、保存の一連の流れ
- **レポート生成**: データ収集、フォーマット、出力の一連の流れ
- **テストフレームワーク**: テストのセットアップ、実行、クリーンアップ
- **ゲーム開発**: ゲームの初期化、更新、終了処理
- **ドキュメント処理**: ドキュメントの読み込み、変換、保存
- **ビルドシステム**: ビルドの準備、コンパイル、パッケージング

### メリット

- **コードの再利用**: 共通の処理を抽象クラスに集約し、コードの重複を削減
- **一貫性**: アルゴリズムの構造を統一し、一貫性を保つ
- **保守性**: アルゴリズムの変更が1箇所で済む
- **拡張性**: 新しいサブクラスを追加する際に、既存のコードを変更する必要がない
- **制御の反転**: 抽象クラスが処理の流れを制御

### デメリット

- **継承の制約**: 継承を使用するため、コンポジションよりも柔軟性が低い
- **複雑性の増加**: シンプルなケースでは過剰な設計になる可能性がある
- **テンプレートメソッドの変更**: テンプレートメソッドを変更すると、すべてのサブクラスに影響する
- **理解の困難さ**: 抽象クラスとサブクラスの関係を理解する必要がある

---

## 基本的な実装

### 実装のポイント

テンプレートメソッドパターンを実装するには、以下の要素が必要です：

1. **AbstractClass（抽象クラス）**: テンプレートメソッドと抽象メソッドを定義
2. **ConcreteClass（具象クラス）**: 抽象メソッドを実装し、特定の処理を行う

### 基本的な実装例

```java
// 1. AbstractClass（抽象クラス）
public abstract class DataProcessor {
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
public class CSVDataProcessor extends DataProcessor {
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

public class XMLDataProcessor extends DataProcessor {
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
```

### 使用例

```java
public class TemplateMethodExample {
    public static void main(String[] args) {
        DataProcessor csvProcessor = new CSVDataProcessor();
        System.out.println("=== CSV処理 ===");
        csvProcessor.process();
        
        System.out.println("\n=== XML処理 ===");
        DataProcessor xmlProcessor = new XMLDataProcessor();
        xmlProcessor.process();
    }
}
```

### パターンの構造

```
AbstractClass
  ├─ templateMethod() [final] → アルゴリズムの骨組み
  ├─ step1() [abstract] → サブクラスで実装
  ├─ step2() [abstract] → サブクラスで実装
  └─ step3() [abstract] → サブクラスで実装
        ↓
ConcreteClass1
  ├─ step1() [実装]
  ├─ step2() [実装]
  └─ step3() [実装]
        ↓
ConcreteClass2
  ├─ step1() [実装]
  ├─ step2() [実装]
  └─ step3() [実装]
```

---

## 実装のバリエーション

### バリエーション1: フックメソッド

オプションのステップをフックメソッドとして提供する方法です。

```java
public abstract class DataProcessorWithHooks {
    // テンプレートメソッド
    public final void process() {
        initialize();
        readData();
        if (shouldProcess()) {
            processData();
        }
        saveData();
        cleanup();
    }
    
    // 抽象メソッド（必須）
    protected abstract void readData();
    protected abstract void processData();
    protected abstract void saveData();
    
    // フックメソッド（オプション、デフォルト実装を提供）
    protected void initialize() {
        // デフォルト実装（何もしない）
    }
    
    protected boolean shouldProcess() {
        // デフォルト実装（常にtrue）
        return true;
    }
    
    protected void cleanup() {
        // デフォルト実装（何もしない）
    }
}

// 使用例
public class CSVDataProcessorWithHooks extends DataProcessorWithHooks {
    @Override
    protected void initialize() {
        System.out.println("CSVプロセッサーを初期化");
    }
    
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
    
    @Override
    protected boolean shouldProcess() {
        // 条件に応じて処理をスキップ
        return true;
    }
    
    @Override
    protected void cleanup() {
        System.out.println("CSVプロセッサーをクリーンアップ");
    }
}
```

### バリエーション2: 複数のテンプレートメソッド

複数のテンプレートメソッドを提供する方法です。

```java
public abstract class MultiTemplateProcessor {
    // テンプレートメソッド1
    public final void processFull() {
        initialize();
        readData();
        processData();
        saveData();
        cleanup();
    }
    
    // テンプレートメソッド2（簡易版）
    public final void processQuick() {
        readData();
        processData();
    }
    
    // 抽象メソッド
    protected abstract void readData();
    protected abstract void processData();
    protected abstract void saveData();
    
    // フックメソッド
    protected void initialize() {
        // デフォルト実装
    }
    
    protected void cleanup() {
        // デフォルト実装
    }
}
```

### バリエーション3: 条件分岐を含むテンプレートメソッド

条件に応じて異なる処理を行うテンプレートメソッドです。

```java
public abstract class ConditionalProcessor {
    // テンプレートメソッド
    public final void process() {
        if (needsInitialization()) {
            initialize();
        }
        
        readData();
        
        if (needsValidation()) {
            validateData();
        }
        
        processData();
        saveData();
        
        if (needsCleanup()) {
            cleanup();
        }
    }
    
    // 抽象メソッド
    protected abstract void readData();
    protected abstract void processData();
    protected abstract void saveData();
    
    // フックメソッド
    protected boolean needsInitialization() {
        return false;
    }
    
    protected void initialize() {
        // デフォルト実装
    }
    
    protected boolean needsValidation() {
        return false;
    }
    
    protected void validateData() {
        // デフォルト実装
    }
    
    protected boolean needsCleanup() {
        return false;
    }
    
    protected void cleanup() {
        // デフォルト実装
    }
}
```

### バリエーション4: インターフェースとデフォルトメソッド

Java 8以降のデフォルトメソッドを使用する方法です。

```java
public interface DataProcessorInterface {
    // テンプレートメソッド（デフォルトメソッド）
    default void process() {
        readData();
        processData();
        saveData();
    }
    
    // 抽象メソッド
    void readData();
    void processData();
    void saveData();
}

// 実装クラス
public class CSVDataProcessorInterface implements DataProcessorInterface {
    @Override
    public void readData() {
        System.out.println("CSVファイルからデータを読み込み");
    }
    
    @Override
    public void processData() {
        System.out.println("CSVデータを処理");
    }
    
    @Override
    public void saveData() {
        System.out.println("処理済みデータをCSVファイルに保存");
    }
}
```

---

## 実践例

### 例1: ゲームのテンプレート

ゲームの初期化、実行、終了処理をテンプレート化する例です。

```java
// AbstractClass
public abstract class Game {
    // テンプレートメソッド
    public final void play() {
        initialize();
        startPlay();
        endPlay();
    }
    
    // 抽象メソッド
    protected abstract void initialize();
    protected abstract void startPlay();
    protected abstract void endPlay();
}

// ConcreteClass
public class Football extends Game {
    @Override
    protected void initialize() {
        System.out.println("フットボールゲームを初期化");
        System.out.println("チームを選択");
        System.out.println("スタジアムを選択");
    }
    
    @Override
    protected void startPlay() {
        System.out.println("フットボールゲームを開始");
        System.out.println("キックオフ！");
    }
    
    @Override
    protected void endPlay() {
        System.out.println("フットボールゲームを終了");
        System.out.println("結果を表示");
    }
}

public class Cricket extends Game {
    @Override
    protected void initialize() {
        System.out.println("クリケットゲームを初期化");
        System.out.println("チームを選択");
        System.out.println("グラウンドを選択");
    }
    
    @Override
    protected void startPlay() {
        System.out.println("クリケットゲームを開始");
        System.out.println("バッティング開始！");
    }
    
    @Override
    protected void endPlay() {
        System.out.println("クリケットゲームを終了");
        System.out.println("スコアを表示");
    }
}

// 使用例
public class GameExample {
    public static void main(String[] args) {
        System.out.println("=== フットボールゲーム ===");
        Game football = new Football();
        football.play();
        
        System.out.println("\n=== クリケットゲーム ===");
        Game cricket = new Cricket();
        cricket.play();
    }
}
```

### 例2: レポート生成のテンプレート

レポート生成の一連の流れをテンプレート化する例です。

```java
import java.util.List;
import java.util.ArrayList;

// AbstractClass
public abstract class ReportGenerator {
    // テンプレートメソッド
    public final void generateReport() {
        List<String> data = collectData();
        String formattedData = formatData(data);
        outputReport(formattedData);
    }
    
    // 抽象メソッド
    protected abstract List<String> collectData();
    protected abstract String formatData(List<String> data);
    protected abstract void outputReport(String formattedData);
}

// ConcreteClass
public class PDFReportGenerator extends ReportGenerator {
    @Override
    protected List<String> collectData() {
        System.out.println("データベースからデータを収集");
        List<String> data = new ArrayList<>();
        data.add("データ1");
        data.add("データ2");
        data.add("データ3");
        return data;
    }
    
    @Override
    protected String formatData(List<String> data) {
        System.out.println("PDF形式にデータをフォーマット");
        StringBuilder formatted = new StringBuilder();
        formatted.append("=== PDFレポート ===\n");
        for (String item : data) {
            formatted.append("- ").append(item).append("\n");
        }
        return formatted.toString();
    }
    
    @Override
    protected void outputReport(String formattedData) {
        System.out.println("PDFファイルに出力:");
        System.out.println(formattedData);
    }
}

public class ExcelReportGenerator extends ReportGenerator {
    @Override
    protected List<String> collectData() {
        System.out.println("APIからデータを収集");
        List<String> data = new ArrayList<>();
        data.add("項目A");
        data.add("項目B");
        data.add("項目C");
        return data;
    }
    
    @Override
    protected String formatData(List<String> data) {
        System.out.println("Excel形式にデータをフォーマット");
        StringBuilder formatted = new StringBuilder();
        formatted.append("=== Excelレポート ===\n");
        for (int i = 0; i < data.size(); i++) {
            formatted.append("行").append(i + 1).append(": ").append(data.get(i)).append("\n");
        }
        return formatted.toString();
    }
    
    @Override
    protected void outputReport(String formattedData) {
        System.out.println("Excelファイルに出力:");
        System.out.println(formattedData);
    }
}

// 使用例
public class ReportGeneratorExample {
    public static void main(String[] args) {
        System.out.println("=== PDFレポート生成 ===");
        ReportGenerator pdfReport = new PDFReportGenerator();
        pdfReport.generateReport();
        
        System.out.println("\n=== Excelレポート生成 ===");
        ReportGenerator excelReport = new ExcelReportGenerator();
        excelReport.generateReport();
    }
}
```

### 例3: テストフレームワークのテンプレート

テストのセットアップ、実行、クリーンアップをテンプレート化する例です。

```java
// AbstractClass
public abstract class TestCase {
    // テンプレートメソッド
    public final void runTest() {
        setUp();
        try {
            executeTest();
        } finally {
            tearDown();
        }
    }
    
    // 抽象メソッド
    protected abstract void setUp();
    protected abstract void executeTest();
    protected abstract void tearDown();
}

// ConcreteClass
public class DatabaseTest extends TestCase {
    private String connection;
    
    @Override
    protected void setUp() {
        System.out.println("データベーステストのセットアップ");
        connection = "データベースに接続";
        System.out.println("接続: " + connection);
    }
    
    @Override
    protected void executeTest() {
        System.out.println("データベースクエリを実行");
        System.out.println("結果を検証");
    }
    
    @Override
    protected void tearDown() {
        System.out.println("データベース接続を閉じる");
        connection = null;
    }
}

public class APITest extends TestCase {
    private String apiClient;
    
    @Override
    protected void setUp() {
        System.out.println("APIテストのセットアップ");
        apiClient = "APIクライアントを初期化";
        System.out.println("クライアント: " + apiClient);
    }
    
    @Override
    protected void executeTest() {
        System.out.println("APIリクエストを送信");
        System.out.println("レスポンスを検証");
    }
    
    @Override
    protected void tearDown() {
        System.out.println("APIクライアントをクリーンアップ");
        apiClient = null;
    }
}

// 使用例
public class TestCaseExample {
    public static void main(String[] args) {
        System.out.println("=== データベーステスト ===");
        TestCase dbTest = new DatabaseTest();
        dbTest.runTest();
        
        System.out.println("\n=== APIテスト ===");
        TestCase apiTest = new APITest();
        apiTest.runTest();
    }
}
```

### 例4: ドキュメント処理のテンプレート

ドキュメントの読み込み、変換、保存をテンプレート化する例です。

```java
// AbstractClass
public abstract class DocumentProcessor {
    // テンプレートメソッド
    public final void processDocument(String inputPath, String outputPath) {
        if (validateInput(inputPath)) {
            String content = readDocument(inputPath);
            String convertedContent = convertDocument(content);
            saveDocument(convertedContent, outputPath);
            postProcess(outputPath);
        } else {
            System.out.println("入力ファイルが無効です");
        }
    }
    
    // 抽象メソッド
    protected abstract String readDocument(String path);
    protected abstract String convertDocument(String content);
    protected abstract void saveDocument(String content, String path);
    
    // フックメソッド
    protected boolean validateInput(String path) {
        return path != null && !path.isEmpty();
    }
    
    protected void postProcess(String path) {
        // デフォルト実装（何もしない）
    }
}

// ConcreteClass
public class PDFToWordProcessor extends DocumentProcessor {
    @Override
    protected String readDocument(String path) {
        System.out.println("PDFファイルを読み込み: " + path);
        return "PDFコンテンツ";
    }
    
    @Override
    protected String convertDocument(String content) {
        System.out.println("PDFをWord形式に変換");
        return "Wordコンテンツ";
    }
    
    @Override
    protected void saveDocument(String content, String path) {
        System.out.println("Wordファイルに保存: " + path);
    }
    
    @Override
    protected void postProcess(String path) {
        System.out.println("Wordファイルを最適化: " + path);
    }
}

public class WordToPDFProcessor extends DocumentProcessor {
    @Override
    protected String readDocument(String path) {
        System.out.println("Wordファイルを読み込み: " + path);
        return "Wordコンテンツ";
    }
    
    @Override
    protected String convertDocument(String content) {
        System.out.println("WordをPDF形式に変換");
        return "PDFコンテンツ";
    }
    
    @Override
    protected void saveDocument(String content, String path) {
        System.out.println("PDFファイルに保存: " + path);
    }
}

// 使用例
public class DocumentProcessorExample {
    public static void main(String[] args) {
        System.out.println("=== PDF to Word ===");
        DocumentProcessor pdfToWord = new PDFToWordProcessor();
        pdfToWord.processDocument("input.pdf", "output.docx");
        
        System.out.println("\n=== Word to PDF ===");
        DocumentProcessor wordToPdf = new WordToPDFProcessor();
        wordToPdf.processDocument("input.docx", "output.pdf");
    }
}
```

### 例5: ビルドシステムのテンプレート

ビルドの準備、コンパイル、パッケージングをテンプレート化する例です。

```java
// AbstractClass
public abstract class BuildSystem {
    // テンプレートメソッド
    public final void build() {
        prepareBuild();
        if (shouldCompile()) {
            compile();
        }
        if (shouldTest()) {
            runTests();
        }
        if (shouldPackage()) {
            packageArtifact();
        }
        cleanup();
    }
    
    // 抽象メソッド
    protected abstract void prepareBuild();
    protected abstract void compile();
    protected abstract void packageArtifact();
    
    // フックメソッド
    protected boolean shouldCompile() {
        return true;
    }
    
    protected boolean shouldTest() {
        return true;
    }
    
    protected void runTests() {
        System.out.println("テストを実行");
    }
    
    protected boolean shouldPackage() {
        return true;
    }
    
    protected void cleanup() {
        System.out.println("ビルド成果物をクリーンアップ");
    }
}

// ConcreteClass
public class JavaBuildSystem extends BuildSystem {
    @Override
    protected void prepareBuild() {
        System.out.println("Javaビルドの準備");
        System.out.println("依存関係を解決");
        System.out.println("ビルドディレクトリを作成");
    }
    
    @Override
    protected void compile() {
        System.out.println("Javaソースコードをコンパイル");
        System.out.println("クラスファイルを生成");
    }
    
    @Override
    protected void packageArtifact() {
        System.out.println("JARファイルを作成");
    }
    
    @Override
    protected void runTests() {
        System.out.println("JUnitテストを実行");
    }
}

public class CppBuildSystem extends BuildSystem {
    @Override
    protected void prepareBuild() {
        System.out.println("C++ビルドの準備");
        System.out.println("Makefileを生成");
        System.out.println("ビルドディレクトリを作成");
    }
    
    @Override
    protected void compile() {
        System.out.println("C++ソースコードをコンパイル");
        System.out.println("オブジェクトファイルを生成");
    }
    
    @Override
    protected void packageArtifact() {
        System.out.println("実行ファイルを作成");
    }
    
    @Override
    protected void runTests() {
        System.out.println("C++テストを実行");
    }
}

// 使用例
public class BuildSystemExample {
    public static void main(String[] args) {
        System.out.println("=== Javaビルド ===");
        BuildSystem javaBuild = new JavaBuildSystem();
        javaBuild.build();
        
        System.out.println("\n=== C++ビルド ===");
        BuildSystem cppBuild = new CppBuildSystem();
        cppBuild.build();
    }
}
```

### 例6: データベース接続のテンプレート

データベース接続の確立、クエリ実行、切断をテンプレート化する例です。

```java
// AbstractClass
public abstract class DatabaseOperation {
    // テンプレートメソッド
    public final void execute() {
        connect();
        try {
            executeQuery();
            if (shouldCommit()) {
                commit();
            }
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            disconnect();
        }
    }
    
    // 抽象メソッド
    protected abstract void connect();
    protected abstract void executeQuery();
    protected abstract void disconnect();
    
    // フックメソッド
    protected boolean shouldCommit() {
        return true;
    }
    
    protected void commit() {
        System.out.println("トランザクションをコミット");
    }
    
    protected void rollback() {
        System.out.println("トランザクションをロールバック");
    }
}

// ConcreteClass
public class MySQLOperation extends DatabaseOperation {
    @Override
    protected void connect() {
        System.out.println("MySQLデータベースに接続");
    }
    
    @Override
    protected void executeQuery() {
        System.out.println("MySQLクエリを実行: SELECT * FROM users");
    }
    
    @Override
    protected void disconnect() {
        System.out.println("MySQLデータベースから切断");
    }
}

public class PostgreSQLOperation extends DatabaseOperation {
    @Override
    protected void connect() {
        System.out.println("PostgreSQLデータベースに接続");
    }
    
    @Override
    protected void executeQuery() {
        System.out.println("PostgreSQLクエリを実行: SELECT * FROM products");
    }
    
    @Override
    protected void disconnect() {
        System.out.println("PostgreSQLデータベースから切断");
    }
}

// 使用例
public class DatabaseOperationExample {
    public static void main(String[] args) {
        System.out.println("=== MySQL操作 ===");
        DatabaseOperation mysqlOp = new MySQLOperation();
        mysqlOp.execute();
        
        System.out.println("\n=== PostgreSQL操作 ===");
        DatabaseOperation postgresOp = new PostgreSQLOperation();
        postgresOp.execute();
    }
}
```

---

## まとめ

### 学習のポイント

1. **テンプレートメソッドパターンの目的**: アルゴリズムの骨組みを定義し、一部のステップをサブクラスで実装
2. **基本的な構造**: AbstractClass（テンプレートメソッドと抽象メソッド）、ConcreteClass（抽象メソッドの実装）
3. **フックメソッド**: オプションのステップをフックメソッドとして提供
4. **制御の反転**: 抽象クラスがサブクラスのメソッドを呼び出す

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | コードの再利用、一貫性、保守性、拡張性、制御の反転 |
| **注意点** | 継承の制約、複雑性の増加、テンプレートメソッドの変更の影響、理解の困難さ |
| **適用場面** | フレームワーク、データ処理、レポート生成、テストフレームワーク、ゲーム開発、ドキュメント処理、ビルドシステム |

### 他のパターンとの関係

- **Strategy**: ストラテジーパターンはコンポジションを使用し、テンプレートメソッドパターンは継承を使用
- **Factory Method**: ファクトリーメソッドパターンはテンプレートメソッドパターンの特殊なケース
- **Observer**: オブザーバーパターンと組み合わせて、テンプレートメソッド内で通知を行うことがある

### 注意点

1. **finalキーワード**: テンプレートメソッドはfinalで修飾し、オーバーライドを防止する
2. **フックメソッドの使用**: オプションのステップはフックメソッドとして提供する
3. **過度な使用を避ける**: シンプルなケースでは過剰な設計になる可能性がある
4. **継承の制約**: 継承を使用するため、コンポジションよりも柔軟性が低い

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. フレームワークでテンプレートメソッドパターンを適用してみる
3. フックメソッドの使用方法を理解する
4. Factory Methodパターンを学習する（テンプレートメソッドパターンの特殊なケース）
5. Strategyパターンを学習する（テンプレートメソッドパターンと比較）

### 参考資料

- [cs-techblog.com - テンプレートメソッドパターン](https://cs-techblog.com/technical/template-method-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、テンプレートメソッドパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
