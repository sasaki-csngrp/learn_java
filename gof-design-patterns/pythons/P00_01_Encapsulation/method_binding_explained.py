"""
同じメソッドが「関数」になったり「メソッド」になったりする理由を理解する
"""

class BankAccount:
    def __init__(self, initial_balance: float):
        self.__balance = initial_balance
    
    def deposit(self, amount: float) -> None:
        """インスタンスメソッド：定義は常に同じ"""
        if amount > 0:
            self.__balance += amount
        print(f"deposit実行: 残高={self.__balance}")
    
    def get_balance(self) -> float:
        """残高を取得"""
        return self.__balance


if __name__ == "__main__":
    print("=" * 70)
    print("同じdepositが「関数」になったり「メソッド」になったりする理由")
    print("=" * 70)
    
    account = BankAccount(1000)
    
    # ============================================================
    # 重要なポイント：depositの定義は常に同じ！
    # ============================================================
    print("\n【重要なポイント】")
    print("-" * 70)
    print("""
    ❌ 誤解: depositが状況によって「関数」になったり「メソッド」になったりする
    
    ✅ 正解: depositの定義は常に同じ（インスタンスメソッド）
            アクセス方法によって「見え方」が変わるだけ
    
    同じdepositを、異なる方法でアクセスすると：
    - account.deposit      → バウンドメソッド（bound method）
    - BankAccount.deposit  → アンバウンド関数（unbound function）
    
    しかし、実際の関数は同じ！
    """)
    
    # ============================================================
    # 1. 型の違いを確認
    # ============================================================
    print("\n【1. 型の違いを確認】")
    print("-" * 70)
    print(f"account.deposit の型: {type(account.deposit)}")
    print(f"BankAccount.deposit の型: {type(BankAccount.deposit)}")
    
    # ============================================================
    # 2. 実は同じ関数であることを確認
    # ============================================================
    print("\n【2. 実は同じ関数であることを確認】")
    print("-" * 70)
    
    # バウンドメソッドから元の関数を取得
    bound_method = account.deposit
    original_function = bound_method.__func__
    
    # クラスから直接アクセスした関数
    unbound_function = BankAccount.deposit
    
    print(f"account.deposit.__func__: {original_function}")
    print(f"BankAccount.deposit:      {unbound_function}")
    print(f"\n→ これらは同じ関数か？ {original_function is unbound_function}")
    print(f"→ 同じ関数オブジェクトです！")
    
    # ============================================================
    # 3. バウンドメソッドの内部構造
    # ============================================================
    print("\n【3. バウンドメソッドの内部構造】")
    print("-" * 70)
    print(f"account.deposit は:")
    print(f"  - 型: {type(account.deposit)}")
    print(f"  - 元の関数: {account.deposit.__func__}")
    print(f"  - バインドされているインスタンス: {account.deposit.__self__}")
    print(f"  - バインドされているクラス: {account.deposit.__self__.__class__}")
    
    print(f"\nBankAccount.deposit は:")
    print(f"  - 型: {type(BankAccount.deposit)}")
    print(f"  - 関数そのもの: {BankAccount.deposit}")
    print(f"  - まだバインドされていない（アンバウンド）")
    
    # ============================================================
    # 4. 呼び出し方法の違い
    # ============================================================
    print("\n【4. 呼び出し方法の違い】")
    print("-" * 70)
    
    print("\n● バウンドメソッドから呼び出す（推奨）:")
    print("  account.deposit(500)")
    account.deposit(500)
    
    print("\n● アンバウンド関数から呼び出す:")
    print("  BankAccount.deposit(account, 200)")
    BankAccount.deposit(account, 200)
    
    print("\n● バウンドメソッドの__func__から呼び出す:")
    print("  account.deposit.__func__(account, 100)")
    account.deposit.__func__(account, 100)
    
    # ============================================================
    # 5. メソッドバインディングの仕組み
    # ============================================================
    print("\n【5. メソッドバインディングの仕組み】")
    print("-" * 70)
    print("""
    Pythonのメソッドバインディング（Descriptor Protocol）:
    
    1. クラス定義時:
       def deposit(self, amount): ...
       → これは通常の関数として定義される
    
    2. インスタンスからアクセス時:
       account.deposit
       → Pythonが自動的に「バウンドメソッド」を作成
       → 元の関数 + インスタンスを組み合わせたオブジェクト
    
    3. クラスからアクセス時:
       BankAccount.deposit
       → 元の関数そのまま（アンバウンド）
    
    つまり：
    - depositの定義は常に同じ（インスタンスメソッド）
    - アクセス方法によって「見え方」が変わる
    - account.deposit は「関数 + インスタンス」のラッパー
    - BankAccount.deposit は「関数そのもの」
    """)
    
    # ============================================================
    # 6. 視覚的な理解
    # ============================================================
    print("\n【6. 視覚的な理解】")
    print("-" * 70)
    print("""
    同じdeposit関数を、異なる「窓」から見ているイメージ：
    
    ┌─────────────────────────────────────┐
    │  BankAccount.deposit (関数そのもの) │
    │  ┌───────────────────────────────┐ │
    │  │ def deposit(self, amount):    │ │
    │  │     self.__balance += amount   │ │
    │  └───────────────────────────────┘ │
    └─────────────────────────────────────┘
              ↑              ↑
              │              │
    ┌─────────┘              └─────────┐
    │                                  │
    │  account.deposit                 │  BankAccount.deposit
    │  (バウンドメソッド)              │  (アンバウンド関数)
    │  ┌──────────────────────────┐   │  ┌──────────────────┐
    │  │ 関数: deposit             │   │  │ 関数: deposit     │
    │  │ インスタンス: account ────┼───┼──┼ インスタンス: なし│
    │  └──────────────────────────┘   │  └──────────────────┘
    │                                  │
    └──────────────────────────────────┘
    
    同じ関数だが、account.depositは「インスタンス付き」のラッパー
    """)
    
    # ============================================================
    # 7. 実際の動作確認
    # ============================================================
    print("\n【7. 実際の動作確認】")
    print("-" * 70)
    
    account2 = BankAccount(5000)
    
    print(f"\naccount の残高: {account.get_balance()}")
    print(f"account2 の残高: {account2.get_balance()}")
    
    # 同じ関数オブジェクトを使っていることを確認
    func1 = account.deposit.__func__
    func2 = account2.deposit.__func__
    func3 = BankAccount.deposit
    
    print(f"\naccount.deposit.__func__ は func3 と同じ？ {func1 is func3}")
    print(f"account2.deposit.__func__ は func3 と同じ？ {func2 is func3}")
    print(f"→ すべて同じ関数オブジェクトです！")
    
    print("\n→ つまり、depositの定義は1つだけ")
    print("  → インスタンスごとに異なる関数が作られるわけではない")
    print("  → すべてのインスタンスが同じ関数を共有している")
    print("  → バウンドメソッドは「関数 + インスタンス」の組み合わせ")
