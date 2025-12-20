# 基底クラス
from typing import override


class Animal:
    def __init__(self, name: str):
        self._name = name  # protectedな変数（慣習的に_で始める）
    
    def make_sound(self) -> None:
        """動物の鳴き声"""
        print("動物の鳴き声")

    @classmethod
    def get_species(cls) -> str:
        """クラスメソッド：種族名を返す"""
        return "動物"
    
    def get_name(self) -> str:
        """インスタンスメソッド：名前を返す"""
        return self._name


# 派生クラス
class Dog(Animal):
    def __init__(self, name: str):
        super().__init__(name)  # 親クラスのコンストラクタを呼び出す
    
    @override # ＜－オプション：オーバーライドを明示するデコレータ（Python 3.12以降）
    def make_sound(self) -> None:
        """犬の鳴き声（オーバーライド）"""
        print(f"{self._name}がワンワンと鳴く")

    @classmethod
    @override  # クラスメソッドもオーバーライド可能
    def get_species(cls) -> str:
        """クラスメソッドのオーバーライド：犬の種族名を返す"""
        return "犬"
    
    @override
    def get_name(self) -> str:
        """インスタンスメソッドのオーバーライド：名前を返す"""
        return self._name + "は犬です"

if __name__ == "__main__":
    print("=" * 60)
    print("インスタンスメソッドのオーバーライド")
    print("=" * 60)
    
    animal: Animal = Dog("ポチ")
    animal.make_sound()
    print(animal.get_name())

    dog = Dog("ポチ太")
    dog.make_sound()
    print(dog.get_name())
    
    print("\n" + "=" * 60)
    print("クラスメソッドのオーバーライド")
    print("=" * 60)
    
    # クラスメソッドはクラスから直接呼び出す
    print(f"Animal.get_species() = {Animal.get_species()}")
    print(f"Dog.get_species() = {Dog.get_species()}")
    
    # インスタンスからも呼び出せる（クラスメソッドが呼ばれる）
    print(f"animal.get_species() = {animal.get_species()}")
    print(f"dog.get_species() = {dog.get_species()}")