# インタープリターパターン（Interpreter Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [インタープリターパターンとは](#インタープリターパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

インタープリターパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**振る舞いに関するパターン（Behavioral Pattern）**に分類されます。

このパターンは、言語の文法表現を定義し、その言語の文を処理するインタープリターを提供します。特定の文法規則に基づいて式や文を評価する際に特に有効です。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- インタープリターパターンの目的と利点を理解する
- 基本的なインタープリターパターンの実装方法を理解する
- 終端表現（Terminal Expression）と非終端表現（Non-Terminal Expression）の違いを理解する
- 実際のプロジェクトでインタープリターパターンを適用できる

---

## インタープリターパターンとは

### 定義

インタープリターパターンは、言語の文法表現を定義し、その言語の文を処理するインタープリターを提供するデザインパターンです。特定の文法規則に基づいて式や文を評価し、ドメイン固有言語（DSL）の解釈に特に有効です。

### 主な特徴

1. **文法の定義**: 言語の文法を明確な構造で定義
2. **式の評価**: 式や文の評価を自動化
3. **ドメイン固有言語（DSL）**: 特定のドメイン向けの言語処理を可能
4. **構文木**: 式を構文木として表現

### 使用される場面

インタープリターパターンは、以下のような場面で使用されます：

- **式の評価**: 算術式、論理式などの評価
- **SQLクエリ**: SQLクエリの解析と実行
- **正規表現**: 正規表現のマッチング
- **スクリプト言語**: スクリプト言語のインタープリター
- **設定ファイル**: 設定ファイルの解析
- **コマンド言語**: コマンドラインインターフェースの解析

### メリット

- **文法の拡張**: 新しい言語規則や式を簡単に追加可能
- **コードの明確性**: 言語解釈に関するコードの明確性と保守性が向上
- **柔軟性**: 文法を変更する際に、既存のコードへの影響を最小化
- **再利用性**: 式の要素を再利用可能

### デメリット

- **複雑な階層**: 複雑な文法では、クラス階層が複雑になる
- **パフォーマンス**: 大規模または複雑な言語構造では効率が低下する可能性
- **保守性**: 文法が複雑になると、保守が困難になる場合がある
- **適用範囲**: シンプルな文法に適しており、複雑な言語には適さない場合がある

---

## 基本的な実装

### 実装のポイント

インタープリターパターンを実装するには、以下の要素が必要です：

1. **AbstractExpression（抽象表現）**: 式を表す抽象クラスまたはインターフェース
2. **TerminalExpression（終端表現）**: 文法の終端記号を表すクラス
3. **NonTerminalExpression（非終端表現）**: 文法の非終端記号を表すクラス
4. **Context（コンテキスト）**: 解釈に必要な情報を保持するクラス
5. **Client（クライアント）**: 式を構築し、解釈を実行するクラス

### パターンの構造

```
Client
  ↓
AbstractExpression (抽象クラス/インターフェース)
  └─ interpret(Context)
      ↓
TerminalExpression (終端表現)
  └─ interpret(Context) [終端記号の解釈]
      ↓
NonTerminalExpression (非終端表現)
  ├─ leftExpression: Expression
  ├─ rightExpression: Expression
  └─ interpret(Context) [非終端記号の解釈]
      ↓
Context
  └─ 解釈に必要な情報を保持
```

### 基本的な実装例

```java
// 1. AbstractExpressionインターフェース
public interface Expression {
    int interpret();
}

// 2. TerminalExpressionクラス（数値を表す）
public class Number implements Expression {
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
public class Add implements Expression {
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
public class Subtract implements Expression {
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
```

### 使用例の出力

```
=== インタープリターパターンのデモ ===

Expression: 5 + 3 - 2
Result: 6

Expression: 10 - 3 + 2
Result: 9
```

### 実装のポイント

1. **終端表現と非終端表現**: 終端表現は値を直接返し、非終端表現は他の表現を組み合わせて評価
2. **構文木の構築**: 式を構文木として表現し、再帰的に評価
3. **再帰的な評価**: 非終端表現は子表現を再帰的に評価して結果を計算

---

## 実装のバリエーション

### バリエーション1: 乗算と除算の追加

より複雑な算術式をサポートする例です。

```java
// 乗算を表すNonTerminalExpression
public class Multiply implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;
    
    public Multiply(Expression left, Expression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }
    
    @Override
    public int interpret() {
        return leftExpression.interpret() * rightExpression.interpret();
    }
}

// 除算を表すNonTerminalExpression
public class Divide implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;
    
    public Divide(Expression left, Expression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }
    
    @Override
    public int interpret() {
        int right = rightExpression.interpret();
        if (right == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return leftExpression.interpret() / right;
    }
}

// 使用例
public class ArithmeticExample {
    public static void main(String[] args) {
        // 式 "5 * 3 + 2" を構築
        Expression expression = new Add(
            new Multiply(new Number(5), new Number(3)),
            new Number(2)
        );
        
        System.out.println("Expression: 5 * 3 + 2");
        System.out.println("Result: " + expression.interpret());
        
        // 式 "(10 - 2) / 4" を構築
        Expression expression2 = new Divide(
            new Subtract(new Number(10), new Number(2)),
            new Number(4)
        );
        
        System.out.println("\nExpression: (10 - 2) / 4");
        System.out.println("Result: " + expression2.interpret());
    }
}
```

### バリエーション2: 論理式の評価

真偽値を扱う論理式の例です。

```java
// 論理式用のExpressionインターフェース
public interface BooleanExpression {
    boolean interpret();
}

// 真偽値のTerminalExpression
public class BooleanValue implements BooleanExpression {
    private boolean value;
    
    public BooleanValue(boolean value) {
        this.value = value;
    }
    
    @Override
    public boolean interpret() {
        return value;
    }
}

// AND演算
public class And implements BooleanExpression {
    private BooleanExpression leftExpression;
    private BooleanExpression rightExpression;
    
    public And(BooleanExpression left, BooleanExpression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }
    
    @Override
    public boolean interpret() {
        return leftExpression.interpret() && rightExpression.interpret();
    }
}

// OR演算
public class Or implements BooleanExpression {
    private BooleanExpression leftExpression;
    private BooleanExpression rightExpression;
    
    public Or(BooleanExpression left, BooleanExpression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }
    
    @Override
    public boolean interpret() {
        return leftExpression.interpret() || rightExpression.interpret();
    }
}

// NOT演算
public class Not implements BooleanExpression {
    private BooleanExpression expression;
    
    public Not(BooleanExpression expression) {
        this.expression = expression;
    }
    
    @Override
    public boolean interpret() {
        return !expression.interpret();
    }
}

// 使用例
public class BooleanExample {
    public static void main(String[] args) {
        // 式 "true AND false OR true" を構築
        BooleanExpression expression = new Or(
            new And(new BooleanValue(true), new BooleanValue(false)),
            new BooleanValue(true)
        );
        
        System.out.println("Expression: true AND false OR true");
        System.out.println("Result: " + expression.interpret());
        
        // 式 "NOT (true OR false)" を構築
        BooleanExpression expression2 = new Not(
            new Or(new BooleanValue(true), new BooleanValue(false))
        );
        
        System.out.println("\nExpression: NOT (true OR false)");
        System.out.println("Result: " + expression2.interpret());
    }
}
```

### バリエーション3: 変数を含む式

変数を含む式を評価する例です。

```java
import java.util.HashMap;
import java.util.Map;

// コンテキストクラス（変数の値を保持）
public class Context {
    private Map<String, Integer> variables;
    
    public Context() {
        this.variables = new HashMap<>();
    }
    
    public void setVariable(String name, int value) {
        variables.put(name, value);
    }
    
    public int getVariable(String name) {
        Integer value = variables.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Variable not found: " + name);
        }
        return value;
    }
}

// 変数を表すTerminalExpression
public class Variable implements Expression {
    private String name;
    
    public Variable(String name) {
        this.name = name;
    }
    
    @Override
    public int interpret(Context context) {
        return context.getVariable(name);
    }
}

// Contextを受け取るExpressionインターフェース
public interface Expression {
    int interpret(Context context);
}

// NumberクラスをContext対応に修正
public class Number implements Expression {
    private int number;
    
    public Number(int number) {
        this.number = number;
    }
    
    @Override
    public int interpret(Context context) {
        return number;
    }
}

// AddクラスをContext対応に修正
public class Add implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;
    
    public Add(Expression left, Expression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }
    
    @Override
    public int interpret(Context context) {
        return leftExpression.interpret(context) + rightExpression.interpret(context);
    }
}

// 使用例
public class VariableExample {
    public static void main(String[] args) {
        Context context = new Context();
        context.setVariable("x", 10);
        context.setVariable("y", 5);
        
        // 式 "x + y - 3" を構築
        Expression expression = new Subtract(
            new Add(new Variable("x"), new Variable("y")),
            new Number(3)
        );
        
        System.out.println("Expression: x + y - 3");
        System.out.println("x = 10, y = 5");
        System.out.println("Result: " + expression.interpret(context));
    }
}
```

---

## 実践例

### 例1: 簡易SQLクエリインタープリター

簡易的なSQLクエリを解釈する例です。

```java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// コンテキスト（データベーステーブルをシミュレート）
public class DatabaseContext {
    private List<Map<String, Object>> table;
    
    public DatabaseContext() {
        this.table = new ArrayList<>();
    }
    
    public void addRow(Map<String, Object> row) {
        table.add(row);
    }
    
    public List<Map<String, Object>> getTable() {
        return table;
    }
}

// SQL式インターフェース
public interface SQLExpression {
    List<Map<String, Object>> interpret(DatabaseContext context);
}

// SELECT式
public class SelectExpression implements SQLExpression {
    private List<String> columns;
    private WhereExpression whereClause;
    
    public SelectExpression(List<String> columns) {
        this.columns = columns;
    }
    
    public void setWhereClause(WhereExpression whereClause) {
        this.whereClause = whereClause;
    }
    
    @Override
    public List<Map<String, Object>> interpret(DatabaseContext context) {
        List<Map<String, Object>> result = new ArrayList<>(context.getTable());
        
        // WHERE句の適用
        if (whereClause != null) {
            result = whereClause.interpret(result);
        }
        
        // カラムの選択
        if (!columns.contains("*")) {
            result = result.stream()
                .map(row -> {
                    Map<String, Object> filteredRow = new HashMap<>();
                    for (String column : columns) {
                        filteredRow.put(column, row.get(column));
                    }
                    return filteredRow;
                })
                .collect(Collectors.toList());
        }
        
        return result;
    }
}

// WHERE式
public class WhereExpression implements SQLExpression {
    private String column;
    private String operator;
    private Object value;
    
    public WhereExpression(String column, String operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }
    
    @Override
    public List<Map<String, Object>> interpret(List<Map<String, Object>> rows) {
        return rows.stream()
            .filter(row -> {
                Object rowValue = row.get(column);
                switch (operator) {
                    case "=":
                        return rowValue != null && rowValue.equals(value);
                    case ">":
                        if (rowValue instanceof Number && value instanceof Number) {
                            return ((Number) rowValue).doubleValue() > ((Number) value).doubleValue();
                        }
                        return false;
                    case "<":
                        if (rowValue instanceof Number && value instanceof Number) {
                            return ((Number) rowValue).doubleValue() < ((Number) value).doubleValue();
                        }
                        return false;
                    default:
                        return false;
                }
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> interpret(DatabaseContext context) {
        return interpret(context.getTable());
    }
}

// 使用例
import java.util.HashMap;
import java.util.Map;

public class SQLExample {
    public static void main(String[] args) {
        System.out.println("=== 簡易SQLクエリインタープリター ===\n");
        
        // データベースコンテキストを作成
        DatabaseContext context = new DatabaseContext();
        
        // データを追加
        Map<String, Object> row1 = new HashMap<>();
        row1.put("id", 1);
        row1.put("name", "Alice");
        row1.put("age", 25);
        context.addRow(row1);
        
        Map<String, Object> row2 = new HashMap<>();
        row2.put("id", 2);
        row2.put("name", "Bob");
        row2.put("age", 30);
        context.addRow(row2);
        
        Map<String, Object> row3 = new HashMap<>();
        row3.put("id", 3);
        row3.put("name", "Charlie");
        row3.put("age", 25);
        context.addRow(row3);
        
        // SELECT * FROM table WHERE age = 25
        SelectExpression select = new SelectExpression(List.of("*"));
        select.setWhereClause(new WhereExpression("age", "=", 25));
        
        List<Map<String, Object>> result = select.interpret(context);
        System.out.println("Query: SELECT * FROM table WHERE age = 25");
        System.out.println("Result:");
        for (Map<String, Object> row : result) {
            System.out.println(row);
        }
    }
}
```

### 例2: 正規表現風のパターンマッチング

簡易的な正規表現風のパターンマッチングの例です。

```java
// パターン式インターフェース
public interface PatternExpression {
    boolean match(String text);
}

// 文字列リテラル（終端表現）
public class Literal implements PatternExpression {
    private String literal;
    
    public Literal(String literal) {
        this.literal = literal;
    }
    
    @Override
    public boolean match(String text) {
        return text.contains(literal);
    }
}

// 繰り返し（*）
public class Repeat implements PatternExpression {
    private PatternExpression expression;
    
    public Repeat(PatternExpression expression) {
        this.expression = expression;
    }
    
    @Override
    public boolean match(String text) {
        // 簡易実装：0回以上の繰り返し
        if (text.isEmpty()) {
            return true;
        }
        // 実際の実装ではより複雑なロジックが必要
        return expression.match(text);
    }
}

// 連結（非終端表現）
public class Concatenate implements PatternExpression {
    private PatternExpression left;
    private PatternExpression right;
    
    public Concatenate(PatternExpression left, PatternExpression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public boolean match(String text) {
        // 簡易実装
        return left.match(text) && right.match(text);
    }
}

// 選択（|）
public class Alternation implements PatternExpression {
    private PatternExpression left;
    private PatternExpression right;
    
    public Alternation(PatternExpression left, PatternExpression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public boolean match(String text) {
        return left.match(text) || right.match(text);
    }
}

// 使用例
public class PatternExample {
    public static void main(String[] args) {
        System.out.println("=== 正規表現風のパターンマッチング ===\n");
        
        // パターン "hello" | "world"
        PatternExpression pattern = new Alternation(
            new Literal("hello"),
            new Literal("world")
        );
        
        System.out.println("Pattern: hello | world");
        System.out.println("Match 'hello': " + pattern.match("hello"));
        System.out.println("Match 'world': " + pattern.match("world"));
        System.out.println("Match 'test': " + pattern.match("test"));
    }
}
```

### 例3: 計算機アプリケーション

数式を評価する計算機の例です。

```java
import java.util.Stack;

// 数式パーサー
public class ExpressionParser {
    public static Expression parse(String expression) {
        // 簡易パーサー（実際の実装ではより複雑）
        // 例: "5 + 3 - 2" を解析
        String[] tokens = expression.split("\\s+");
        Stack<Expression> stack = new Stack<>();
        
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            
            if (isNumber(token)) {
                stack.push(new Number(Integer.parseInt(token)));
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression");
                }
                Expression right = stack.pop();
                Expression left = stack.pop();
                
                switch (token) {
                    case "+":
                        stack.push(new Add(left, right));
                        break;
                    case "-":
                        stack.push(new Subtract(left, right));
                        break;
                    case "*":
                        stack.push(new Multiply(left, right));
                        break;
                    case "/":
                        stack.push(new Divide(left, right));
                        break;
                }
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }
        
        return stack.pop();
    }
    
    private static boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || 
               token.equals("*") || token.equals("/");
    }
}

// 計算機クラス
public class Calculator {
    public int calculate(String expression) {
        Expression expr = ExpressionParser.parse(expression);
        return expr.interpret();
    }
}

// 使用例
public class CalculatorExample {
    public static void main(String[] args) {
        System.out.println("=== 計算機アプリケーション ===\n");
        
        Calculator calculator = new Calculator();
        
        System.out.println("5 + 3 = " + calculator.calculate("5 + 3"));
        System.out.println("10 - 4 = " + calculator.calculate("10 - 4"));
        System.out.println("6 * 2 = " + calculator.calculate("6 * 2"));
        System.out.println("8 / 2 = " + calculator.calculate("8 / 2"));
    }
}
```

### 例4: 設定ファイルの解析

設定ファイルを解析する例です。

```java
import java.util.HashMap;
import java.util.Map;

// 設定式インターフェース
public interface ConfigExpression {
    Object interpret(Map<String, Object> config);
}

// 設定値の取得（終端表現）
public class ConfigValue implements ConfigExpression {
    private String key;
    
    public ConfigValue(String key) {
        this.key = key;
    }
    
    @Override
    public Object interpret(Map<String, Object> config) {
        return config.get(key);
    }
}

// デフォルト値の設定
public class DefaultValue implements ConfigExpression {
    private ConfigExpression expression;
    private Object defaultValue;
    
    public DefaultValue(ConfigExpression expression, Object defaultValue) {
        this.expression = expression;
        this.defaultValue = defaultValue;
    }
    
    @Override
    public Object interpret(Map<String, Object> config) {
        Object value = expression.interpret(config);
        return value != null ? value : defaultValue;
    }
}

// 設定の結合
public class ConfigConcat implements ConfigExpression {
    private ConfigExpression left;
    private ConfigExpression right;
    
    public ConfigConcat(ConfigExpression left, ConfigExpression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Object interpret(Map<String, Object> config) {
        Object leftValue = left.interpret(config);
        Object rightValue = right.interpret(config);
        return (leftValue != null ? leftValue.toString() : "") + 
               (rightValue != null ? rightValue.toString() : "");
    }
}

// 使用例
public class ConfigExample {
    public static void main(String[] args) {
        System.out.println("=== 設定ファイルの解析 ===\n");
        
        Map<String, Object> config = new HashMap<>();
        config.put("host", "localhost");
        config.put("port", 8080);
        // database.name は設定されていない
        
        // 設定値の取得
        ConfigExpression hostExpr = new ConfigValue("host");
        System.out.println("Host: " + hostExpr.interpret(config));
        
        // デフォルト値付きの取得
        ConfigExpression dbNameExpr = new DefaultValue(
            new ConfigValue("database.name"),
            "default_db"
        );
        System.out.println("Database name: " + dbNameExpr.interpret(config));
    }
}
```

---

## まとめ

### 学習のポイント

1. **インタープリターパターンの目的**: 言語の文法表現を定義し、その言語の文を処理するインタープリターを提供
2. **基本構造**: AbstractExpression、TerminalExpression、NonTerminalExpression、Context
3. **終端表現と非終端表現**: 終端表現は値を直接返し、非終端表現は他の表現を組み合わせて評価
4. **構文木**: 式を構文木として表現し、再帰的に評価

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 文法の拡張、コードの明確性、柔軟性、再利用性 |
| **注意点** | 複雑な階層、パフォーマンス、保守性、適用範囲 |
| **適用場面** | 式の評価、SQLクエリ、正規表現、スクリプト言語、設定ファイル、コマンド言語 |

### 実装のベストプラクティス

1. **文法の設計**: シンプルで明確な文法を設計する
2. **構文木の構築**: 式を構文木として表現し、再帰的に評価
3. **コンテキストの活用**: 変数や環境情報をコンテキストで管理
4. **エラーハンドリング**: 無効な式に対する適切なエラーハンドリング

### 他のパターンとの関係

- **Composite**: インタープリターパターンは構文木を構築するため、Compositeパターンと類似
- **Visitor**: 構文木を走査する際に、Visitorパターンと組み合わせることがある
- **Strategy**: 異なる解釈方法をStrategyパターンで実装することがある

### 注意点

1. **適用範囲**: シンプルな文法に適しており、複雑な言語には適さない場合がある
2. **パフォーマンス**: 大規模または複雑な言語構造では効率が低下する可能性がある
3. **文法の複雑さ**: 文法が複雑になると、クラス階層が複雑になる
4. **パーサーの実装**: 実際の実装では、パーサーの実装が必要になる場合が多い

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. より複雑な文法を実装してみる
3. 実際のプロジェクトでインタープリターパターンを適用してみる
4. 他の振る舞いに関するパターン（Visitor、Strategy、Stateなど）を学習する

### 参考資料

- [cs-techblog.com - インタープリターパターン](https://cs-techblog.com/technical/interpreter-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「実践ドメイン駆動設計」（Vaughn Vernon著）

---

**注意**: この学習プランは、インタープリターパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。複雑な言語の実装には、専用のパーサージェネレータ（ANTLR、Yaccなど）の使用を検討してください。
