# GoF デザインパターン学習環境

このリポジトリは、GoF（Gang of Four）によって提唱された23のデザインパターンを学習するための環境です。

## 📚 概要

デザインパターンは、ソフトウェア開発において繰り返し発生する問題に対する再利用可能な解決策です。この学習環境では、Javaによる実装を中心に、C言語とPythonでの類似実装も提供し、異なるプログラミング言語でのパターンの実装方法を比較しながら学習できます。

C言語版、Python版とTypeScript版は、一部のパターンについて実装済みです。  

## 💡 なぜ今デザインパターンを学ぶのか？

GoFデザインパターンは1995年に発表されてから約30年が経過していますが、2025年現在でも学習する価値は非常に高いです。その理由を以下に説明します。

### 1. 普遍的な問題解決の原則

デザインパターンは特定の技術や言語に依存しない、設計上の問題に対する解決策です。プログラミング言語やフレームワークが変わっても、設計上の課題は本質的に変わりません。そのため、パターンの本質的な価値は時代を超えて有効です。

### 2. 現代の技術スタックでも広く使用されている

多くの現代的なフレームワークやライブラリが、内部でデザインパターンを活用しています：

- **フレームワーク・ライブラリ**: Spring Framework（Singleton、Factory Method、Proxy）、React（Observer、Strategy）、Express.js（Middleware = Chain of Responsibility）
- **言語機能**: JavaのStream API（Iterator）、Pythonのデコレーター（Decorator）、TypeScriptの型システム（Adapter）
- **アーキテクチャ**: マイクロサービス（Facade、Adapter）、イベント駆動アーキテクチャ（Observer、Command）

### 3. 設計の共通言語として機能

デザインパターンは、開発チーム間での共通語彙として機能します。「Strategyパターンで実装する」と伝えるだけで、設計の意図が明確に伝わります。これは、コードレビューや設計レビューにおいて特に有効です。

### 4. コードの品質向上

デザインパターンを理解することで、以下のようなメリットが得られます：

- **再利用性**: 実証済みの解決策を適用できる
- **保守性**: 構造が理解しやすく、変更に強いコードが書ける
- **拡張性**: 将来の変更に対応しやすい設計ができる

### 5. 批判的な視点も重要

一方で、デザインパターンは万能ではありません：

- **過度な適用は避ける**: シンプルな解決で十分な場面にパターンを適用すると、かえって複雑になる
- **現代の代替手段**: 一部のパターンは、現代の言語やフレームワークの機能で代替できる場合がある（例: 依存性注入がSingletonの一部用途を置き換える）

### 6. 学習の価値

デザインパターンを学ぶことで、以下の能力が身につきます：

- **設計の判断力**: どのパターンが適切かを判断できる
- **既存コードの理解**: フレームワークやライブラリの内部実装を理解しやすくなる
- **新しい技術の習得**: 多くのフレームワークがパターンを内包しているため、理解が早くなる

### 結論

30年前に発表されたパターンでも、設計上の課題は変わらないため、学習する意味は十分にあります。ただし、盲目的に適用するのではなく、問題に応じて適切に選択し、必要に応じて簡略化や現代的な代替を検討することが重要です。

この学習環境は、パターンの本質を理解し、適切に使い分ける判断力を身につけるためのものです。

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
│   ├── cs/                # C言語実装例（基礎概念と一部パターン）
│   ├── pythons/           # Python実装例（基礎概念と一部パターン）
│   └── typescripts/       # TypeScript実装例（基礎概念と一部パターン）
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

#### `pythons/`
Pythonによる実装例です。オブジェクト指向の基礎概念と一部のデザインパターンをPythonで実装しています。

#### `typescripts/`
TypeScriptによる実装例です。オブジェクト指向の基礎概念と一部のデザインパターンをTypeScriptで実装しています。


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

ここでも一度必ず手を動かして実装し、動作を確認する事で、記憶を確定する事が望ましいです。

### 2. 実装例を確認する

各パターンのディレクトリには、以下のファイルが含まれています：

- **ドキュメント**: `docs/PXX_PatternName.md`
- **Java実装**: `srcs/PXX_PatternName/` 配下の`.java`ファイル

### 3. 自分の手を動かして実装する


ドキュメントを参考にして、まずは**自分の手を動かしてコーディング**して実行します。  
入手した情報（一時記憶）を、**手を動かす事でアウトプットする**事で、**一時記憶が揮発せず定着**します。  


解らない事や詰まった事があれば、実装ファイルを参考にしてもよいが、**コピペは禁止**とします。  
その場合は、見て写す（写経する）のではなく、一度記憶してその記憶を元に手を動かす事が望ましいです。  


まずはJavaで作成し、可能であればC言語やPythonでも実装してみて、本質的な理解を更に深めて下さい。  
得意な言語があれば、まずはそちらで実装しても構いません。  
極端な話、ここに記載していない Go言語 や PowerBuilder で実装しても良い結果が得られます。  


### 4. コードを実行する

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

#### Pythonの実行例

```bash
cd gof-design-patterns/pythons/P00_01_Encapsulation
python3 BankAccount.py
```

#### TypeScriptの実行例

```bash
# TypeScriptをコンパイル
cd gof-design-patterns/typescripts/P00_01_Encapsulation
tsc BankAccount.ts

# 実行
node BankAccount.js
```

### 4. 比較学習

Java、C言語、Python、TypeScriptの実装を比較することで、パターンの本質をより深く理解できます。

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

4. **Python実装** (`pythons/P00_XX/`, `pythons/P01_XX/`) - 基礎概念と一部パターン
   - オブジェクト指向の概念とデザインパターンをPythonで実装

## 🔧 必要な環境

### Docker 開発コンテナ  

- ホストWindowsを汚さないよう、Docker で開発コンテナを構築する事を推奨します。  
- 開発コンテナに利用するイメージには、ubuntu 24.04 LTS を推奨します。
- IDEにはVScodeを推奨します。開発コンテナへアクセスする為に、拡張機能　Dev Containers が必要です。しかし、 eclipse等の使い慣れたIDEがあれば、それを利用して頂いて構いません。  
- 本内容には機密情報・個人情報は含まれませんので、無料のAIツール（Gemini Cli等）を利用して頂いて構いません。  

### インストール済みの環境

- **Java**: JDK 21 LTS（Java実装の実行に必要）
- **C言語**: build-essential（GCC等を含む、C言語実装の実行に必要）
- **Python**: 3.12.3（Ubuntuにバンドル、Python実装の実行に必要）
- **Node.js**: 24.x LTS（TypeScript実装の実行に必要）
- **TypeScript**: （TypeScript実装の実行に必要）
- **Markdownビューア**: ドキュメントの閲覧に推奨

### インストール方法

#### Java 21 LTS
```bash
# Ubuntu/Debianの場合
sudo apt update
sudo apt install openjdk-21-jdk
```

#### C言語（build-essential）
```bash
# Ubuntu/Debianの場合
sudo apt update
sudo apt install build-essential
```

#### Python 3.12.3
```bash
# Ubuntuにバンドルされている場合は追加インストール不要
# バージョン確認
python3 --version
```

#### Node.js 24.x LTS と TypeScript
```bash
# Node.js 24.x LTSとTypeScriptをインストール
curl -fsSL https://deb.nodesource.com/setup_24.x | sudo -E bash -
sudo apt update
sudo apt install nodejs

# TypeScriptをグローバルにインストール
sudo npm install -g typescript
```

## 📝 今後の拡張予定

- [ ] TypeScript版の追加実装（残りのパターン）
- [ ] Python版の追加実装（残りのパターン）
- [ ] C言語版の追加実装（残りのパターン）
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
