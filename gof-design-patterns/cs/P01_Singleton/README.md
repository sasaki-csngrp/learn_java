# Singleton パターン（C言語実装）

## 概要

このディレクトリには、JavaのSingletonパターンをC言語で実装した例が含まれています。

## 特徴

- **`malloc`を使わない静的確保**: Javaの`static`変数と同様に、C言語の`static`キーワードを使用して、メモリを静的に確保しています
- **関数ポインタによるメソッド実装**: 構造体に関数ポインタを含めることで、Javaのメソッドに相当する機能を実現しています
- **Lazy Initialization**: 初回の`getInstance()`呼び出し時にのみインスタンスを初期化します

## ファイル構成

- `Singleton.h`: ヘッダーファイル（構造体定義と関数宣言）
- `Singleton.c`: 実装ファイル（Singletonの実装）
- `SingletonExample.c`: 使用例

## 実装のポイント

### 1. 静的インスタンスの確保

```c
// 唯一のインスタンスを保持するstatic変数（Javaのprivate staticに相当）
// mallocを使わず、静的に確保
static Singleton instance;
```

### 2. 初期化フラグによるLazy Initialization

```c
// 初期化フラグ（初回のみ初期化を行うため）
static int initialized = 0;

Singleton* Singleton_getInstance(void) {
    if (!initialized) {
        // 初回のみ初期化
        instance.doSomething = singleton_doSomething;
        initialized = 1;
    }
    return &instance;
}
```

### 3. 関数ポインタによるメソッド実装

```c
struct Singleton {
    DoSomethingFunc doSomething;  // 関数ポインタ
};
```

## コンパイルと実行

```bash
gcc -o SingletonExample Singleton.c SingletonExample.c
./SingletonExample
```

## 実行結果

```
s1 == s2: true
s1 == s2: 1
シングルトンのメソッドが呼ばれました
シングルトンのメソッドが呼ばれました
```

## Javaとの対応関係

| Java | C言語 |
|------|-------|
| `private static Singleton instance;` | `static Singleton instance;` |
| `public static Singleton getInstance()` | `Singleton* Singleton_getInstance(void)` |
| `public void doSomething()` | `DoSomethingFunc doSomething;` (関数ポインタ) |
| `instance = new Singleton();` | 静的変数の初期化（`malloc`不要） |

## 注意点

- C言語では、Javaのような`private`コンストラクタによる完全なインスタンス化防止はできませんが、`getInstance()`を使うことで、同じインスタンスを取得することを保証します
- マルチスレッド環境では、初期化フラグのチェックと設定がアトミックではないため、追加の同期機構が必要です（この実装はシングルスレッド用です）
