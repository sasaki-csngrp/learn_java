# 基底クラス
class Animal:
    def __init__(self, name: str):
        self._name = name  # protectedな変数（慣習的に_で始める）
    
    def make_sound(self) -> None:
        """動物の鳴き声"""
        print("動物の鳴き声")


# 派生クラス
class Dog(Animal):
    def __init__(self, name: str):
        super().__init__(name)  # 親クラスのコンストラクタを呼び出す
    
    def make_sound(self) -> None:
        """犬の鳴き声（オーバーライド）"""
        print(f"{self._name}がワンワンと鳴く")


if __name__ == "__main__":
    dog = Dog("ポチ")
    dog.make_sound()
