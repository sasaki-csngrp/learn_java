class BankAccount:
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
    account.deposit(500)
    account.withdraw(200)
    print(f"残高: {account.get_balance()}")

    #  Pythonの__は完全なプライベートではなく、名前修飾による「意図しないアクセスを避ける」仕組みです。
    # 外部から直接アクセスしてもエラーにはなりませんが、クラス内の属性とは別物として扱われます。
    # name mangling という仕組みらしい。
    account.__balance = 100000
    print(f"残高: {account.get_balance()}")
