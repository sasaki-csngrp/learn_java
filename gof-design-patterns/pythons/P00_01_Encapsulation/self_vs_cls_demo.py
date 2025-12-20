"""
self と cls の違いを理解するためのデモコード
"""

class BankAccount:
    """通常のインスタンスメソッドの例"""
    
    def __init__(self, initial_balance: float):
        self.__balance = initial_balance
    
    def deposit(self, amount: float) -> None:
        """インスタンスメソッド：第一引数はself（インスタンス）"""
        if amount > 0:
            self.__balance += amount
        print(f"depositメソッド: selfの型={type(self)}, self={self}")
    
    @classmethod
    def create_account(cls, initial_balance: float) -> 'BankAccount':
        """クラスメソッド：第一引数はcls（クラス自身）"""
        print(f"create_accountメソッド: clsの型={type(cls)}, cls={cls}")
        return cls(initial_balance)
    
    @staticmethod
    def calculate_interest(balance: float, rate: float) -> float:
        """静的メソッド：第一引数にselfもclsも不要"""
        return balance * rate
    
    def get_balance(self) -> float:
        return self.__balance


if __name__ == "__main__":
    print("=" * 60)
    print("self と cls の違いを理解する")
    print("=" * 60)
    
    # ============================================================
    # 1. インスタンスメソッド（self）
    # ============================================================
    print("\n【1. インスタンスメソッド（self）】")
    print("-" * 60)
    
    account = BankAccount(1000)
    
    # インスタンスから呼び出す（selfは自動的に渡される）
    print("\n● インスタンスから呼び出す:")
    account.deposit(500)
    
    # クラスオブジェクト経由で呼び出す（明示的にselfを渡す）
    print("\n● クラスオブジェクト経由で呼び出す:")
    BankAccount.deposit(account, 200)
    
    print(f"\n→ どちらの場合も、第一引数はself（インスタンス）")
    print(f"  account.deposit(500) は BankAccount.deposit(account, 500) と同じ")
    print(f"  第一引数は「インスタンス（オブジェクト）」")
    
    # ============================================================
    # 2. クラスメソッド（cls）
    # ============================================================
    print("\n\n【2. クラスメソッド（cls）】")
    print("-" * 60)
    
    # クラスから呼び出す（clsは自動的に渡される）
    print("\n● クラスから呼び出す:")
    account2 = BankAccount.create_account(2000)
    
    # インスタンスから呼び出す（clsは自動的に渡される）
    print("\n● インスタンスから呼び出す:")
    account3 = account2.create_account(3000)
    
    print(f"\n→ どちらの場合も、第一引数はcls（クラス自身）")
    print(f"  BankAccount.create_account(2000) の第一引数clsは BankAccount クラス")
    print(f"  account2.create_account(3000) の第一引数clsも BankAccount クラス（同じ）")
    print(f"  第一引数は「クラス（型）」")
    
    # ============================================================
    # 3. 比較：self と cls の違い
    # ============================================================
    print("\n\n【3. 比較：self と cls の違い】")
    print("-" * 60)
    
    print("\n● インスタンスメソッド（self）:")
    print(f"  定義: def deposit(self, amount)")
    print(f"  呼び出し: account.deposit(500)")
    print(f"  実際: BankAccount.deposit(account, 500)")
    print(f"  → 第一引数selfは「インスタンス（オブジェクト）」")
    
    print("\n● クラスメソッド（cls）:")
    print(f"  定義: @classmethod def create_account(cls, initial_balance)")
    print(f"  呼び出し: BankAccount.create_account(2000)")
    print(f"  実際: BankAccount.create_account(BankAccount, 2000)")
    print(f"  → 第一引数clsは「クラス（型）」")
    
    # ============================================================
    # 4. 重要なポイント
    # ============================================================
    print("\n\n【4. 重要なポイント】")
    print("-" * 60)
    print("""
    BankAccount.deposit(account, 100) について：
    
    ❌ 誤解: 「クラスから呼び出すから、第一引数はcls（クラス）」
    ✅ 正解: 「depositはインスタンスメソッドなので、第一引数はself（インスタンス）」
    
    つまり：
    - BankAccountは「クラスオブジェクト」だが、
    - depositは「インスタンスメソッド」なので、
    - 第一引数はself（インスタンス）であり、cls（クラス）ではない
    
    clsが関係するのは：
    - @classmethodデコレータが付いたクラスメソッド
    - __new__メソッド
    のみです。
    """)
    
    # ============================================================
    # 5. 実際の型を確認
    # ============================================================
    print("\n\n【5. 実際の型を確認】")
    print("-" * 60)
    print(f"account.deposit の型: {type(account.deposit)}")
    print(f"BankAccount.deposit の型: {type(BankAccount.deposit)}")
    print(f"BankAccount.create_account の型: {type(BankAccount.create_account)}")
    print(f"account.create_account の型: {type(account.create_account)}")
    
    print(f"\naccount.deposit.__self__: {account.deposit.__self__}")  # インスタンス
    print(f"account.create_account.__self__: {account.create_account.__self__}")  # クラス
