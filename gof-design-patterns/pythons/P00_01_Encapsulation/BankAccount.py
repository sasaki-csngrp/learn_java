class BankAccount:
    # その前に、そもそもPython の self って何？
    # java だと、self なんて渡さなくても、this で自分のメンバにアクセスできたけど
    # C言語での疑似実装に近い感じか？
    def __init__(self, initial_balance: float):
        """コンストラクタ：初期残高を設定"""
        # python では、宣言していい変数は、始めて利用した時に定義される。
        # python では、変数名の先頭に _ を付けると、private な変数として扱う。
        # __balance は private な変数として扱う
        # つまり、このインストラクタでの初期設定が、プライベート変数の宣言＋初期化となる
        self.__balance = initial_balance  # データをprivateで隠蔽（名前修飾を使用）
    
    def deposit(self, amount: float) -> None:
        """預金メソッド"""
        if amount > 0:
            self.__balance += amount
    
    def withdraw(self, amount: float) -> None:
        """引き出しメソッド"""
        if amount > 0 and amount <= self.__balance:
            self.__balance -= amount
    
    def get_balance(self) -> float:
        """残高を取得するメソッド"""
        return self.__balance


if __name__ == "__main__":
    account = BankAccount(1000)
    
    # 【通常の呼び出し】インスタンスから呼び出す（selfは自動的に渡される）
    account.deposit(500)
    account.withdraw(200)
    print(f"残高: {account.get_balance()}")
    
    # 【実はこれと同じ】クラスオブジェクト経由で呼び出す場合、明示的にselfを渡す必要がある
    # これにより、Pythonのメソッドが実際には関数であることが分かる
    print("\n=== メソッドバインディングの仕組みを確認 ===")
    print(f"account.deposit の型: {type(account.deposit)}")
    print(f"BankAccount.deposit の型: {type(BankAccount.deposit)}")
    
    # クラスオブジェクト経由で直接呼び出す場合（関数として扱う）
    BankAccount.deposit(account, 100)  # 明示的にself（accountインスタンス）を渡す
    print(f"クラスオブジェクト経由で呼び出し後の残高: {account.get_balance()}")
    
    # インスタンスから呼び出す場合（バウンドメソッドとして扱う）
    account.deposit(50)  # selfは自動的に渡される
    print(f"インスタンスから呼び出し後の残高: {account.get_balance()}")
    
    # 【参考】メソッドの実体を確認
    print("\n=== メソッドの実体 ===")
    print(f"account.deposit: {account.deposit}")
    print(f"BankAccount.deposit: {BankAccount.deposit}")
    print(f"account.deposit.__self__: {account.deposit.__self__}")  # バウンドされているインスタンス
    print(f"account.deposit.__func__: {account.deposit.__func__}")  # 元の関数
    
    #  Pythonの__は完全なプライベートではなく、名前修飾による「意図しないアクセスを避ける」仕組みです。
    # 外部から直接アクセスしてもエラーにはなりませんが、クラス内の属性とは別物として扱われます。
    # name mangling という仕組みらしい。
    print("\n=== 名前修飾（name mangling）の確認 ===")
    account.__balance = 100000  # これは別の属性として作成される
    print(f"残高（get_balance経由）: {account.get_balance()}")  # 元の__balanceは変更されていない
    print(f"残高（直接アクセス）: {account.__balance}")  # 新しく作成された属性
