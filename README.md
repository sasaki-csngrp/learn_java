# GoF デザインパターン学習環境

このリポジトリは、GoF（Gang of Four）によって提唱された23のデザインパターンを学習するための環境です。

## 📚 概要

デザインパターンは、ソフトウェア開発において繰り返し発生する問題に対する再利用可能な解決策です。この学習環境では、Javaによる実装を中心に、C言語での類似実装も提供し、異なるプログラミング言語でのパターンの実装方法を比較しながら学習できます。

将来的には、TypeScriptやPythonでの実装も追加予定です。

## 🎯 学習目標

- GoFの23のデザインパターンを体系的に学習する
- 各パターンの目的、構造、実装方法を理解する
- 異なるプログラミング言語での実装を比較し、パターンの本質を理解する
- 実際のプロジェクトでデザインパターンを適用できるようになる

## 📁 プロジェクト構造

```
learn_java/
├── gof-design-patterns/
│   ├── docs/              # 各パターンの詳細なドキュメント
│   ├── srcs/              # Java実装例
│   └── cs/                # C言語実装例（基礎概念と一部パターン）
└── README.md              # このファイル
```

### ディレクトリの説明

#### `docs/`
各デザインパターンの詳細な説明と学習プランを含むMarkdownファイルです。

- `P00_introduction.md` - オブジェクト指向の基礎とSOLID原則、デザインパターンの概要
- `P01_Singleton.md` - P23_Visitor.md - 各デザインパターンの詳細な説明

#### `srcs/`
Javaによる各デザインパターンの実装例です。

- `P00_01_Encapsulation/` - `P00_04_Abstraction/` - オブジェクト指向の基礎概念
- `P01_Singleton/` - `P23_Visitor/` - GoFの23のデザインパターンの実装

#### `cs/`
C言語による類似実装です。オブジェクト指向の基礎概念（カプセル化、継承、ポリモーフィズム、抽象化）をC言語で実現する方法を示しています。

- `P00_01_Encapsulation/` - 構造体と関数ポインタによるカプセル化
- `P00_02_Inheritance/` - C言語での継承の実現方法
- `P00_03_Polymorphism/` - 関数ポインタによるポリモーフィズム
- `P00_04_Abstraction/` - 抽象化の実現方法

## 🗂️ GoFデザインパターン一覧

### 生成に関するパターン（Creational Patterns）

1. **Singleton（シングルトン）** - `P01_Singleton`
2. **Factory Method（ファクトリメソッド）** - `P02_Factory_Method`
3. **Abstract Factory（抽象ファクトリ）** - `P03_Abstract_Factory`
4. **Builder（ビルダー）** - `P04_Builder`
5. **Prototype（プロトタイプ）** - `P05_Prototype`

### 構造に関するパターン（Structural Patterns）

6. **Adapter（アダプター）** - `P06_Adapter`
7. **Bridge（ブリッジ）** - `P07_Bridge`
8. **Composite（コンポジット）** - `P08_Composite`
9. **Decorator（デコレーター）** - `P09_Decorator`
10. **Facade（ファサード）** - `P10_Facade`
11. **Flyweight（フライウェイト）** - `P11_Flyweight`
12. **Proxy（プロキシ）** - `P12_Proxy`

### 振る舞いに関するパターン（Behavioral Patterns）

13. **Chain of Responsibility（責任連鎖）** - `P13_Chain_of_Responsibility`
14. **Command（コマンド）** - `P14_Command`
15. **Interpreter（インタープリター）** - `P15_Interpreter`
16. **Iterator（イテレーター）** - `P16_Iterator`
17. **Mediator（メディエーター）** - `P17_Mediator`
18. **Memento（メメント）** - `P18_Memento`
19. **Observer（オブザーバー）** - `P19_Observer`
20. **State（ステート）** - `P20_State`
21. **Strategy（ストラテジー）** - `P21_Strategy`
22. **Template Method（テンプレートメソッド）** - `P22_Template_Method`
23. **Visitor（ビジター）** - `P23_Visitor`

## 🚀 学習の進め方

### 1. 基礎を固める

まず、`docs/P00_introduction.md`を読んで、以下の内容を理解しましょう：

- オブジェクト指向プログラミングの基本概念（カプセル化、継承、ポリモーフィズム、抽象化）
- SOLID原則
- デザインパターンの概要と分類

### 2. 実装例を確認する

各パターンのディレクトリには、以下のファイルが含まれています：

- **Java実装**: `srcs/PXX_PatternName/` 配下の`.java`ファイル
- **ドキュメント**: `docs/PXX_PatternName.md`

### 3. コードを実行する

#### Javaの実行例

```bash
cd gof-design-patterns/srcs/P01_Singleton
javac *.java
java SingletonExample
```

#### C言語の実行例

```bash
cd gof-design-patterns/cs/P00_01_Encapsulation
gcc -o BankAccount BankAccount.c
./BankAccount
```

### 4. 比較学習

JavaとC言語の実装を比較することで、パターンの本質をより深く理解できます。

## 📖 各パターンの学習方法

各パターンのディレクトリには、以下の内容が含まれています：

1. **ドキュメント** (`docs/PXX_PatternName.md`)
   - パターンの目的と問題
   - 解決方法
   - 実装例
   - 使用例とベストプラクティス

2. **実装例** (`srcs/PXX_PatternName/`)
   - 動作するJavaコード
   - コメント付きの説明

3. **C言語実装** (`cs/P00_XX/`) - 基礎概念のみ
   - オブジェクト指向の概念をC言語で実現する方法

## 🔧 必要な環境

- **Java**: JDK 8以上（Java実装の実行に必要）
- **C言語**: GCC（C言語実装の実行に必要）
- **Markdownビューア**: ドキュメントの閲覧に推奨

## 📝 今後の拡張予定

- [ ] TypeScriptでの実装例
- [ ] Pythonでの実装例
- [ ] より詳細な実践例とユースケース
- [ ] パターンの組み合わせ例

## 📚 参考資料

- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）
- 「Clean Code」（Robert C. Martin著）

## 🤝 貢献

この学習環境を改善するための提案やプルリクエストを歓迎します。

## 📄 ライセンス

このプロジェクトは学習目的で作成されています。

---

**Happy Learning! 🎓**
