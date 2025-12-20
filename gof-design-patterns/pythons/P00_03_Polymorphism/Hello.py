# abcは「Abstract Base Class」の略で、抽象基底クラスを定義するためのモジュールです。
# ABCは抽象基底クラスの基底クラスです。
# abstractmethodは抽象メソッドを定義するデコレータです。
from abc import ABC, abstractmethod
from typing import override

# インターフェース（抽象基底クラス）
# HumanがABCを継承することで、抽象基底クラスになります。
# これにより、Humanは直接インスタンス化できず、継承して実装する必要があります。
#human = Human()  # これはエラーになります。
class Human(ABC):
    # @abstractmethod デコレータで、このメソッドは抽象メソッドであることを示します。
    # 継承先で必ず実装する必要があります。未実装だとインスタンス化時にエラーになります。
    @abstractmethod 
    def say_hello(self) -> None:
        """挨拶をするメソッド"""
        pass

    @classmethod
    @abstractmethod
    def get_type(cls) -> str:
        """種族を返すメソッド"""
        pass

class Japanese(Human):
    @override
    def __init__(self, name: str):
        super().__init__()
        self.__name = name
    
    @override
    def say_hello(self) -> None:
        """日本語で挨拶"""
        print(f"こんにちは。私の名前は{self.__name}です。")

    
    @classmethod
    @override
    def get_type(cls) -> str:
        """日本語の種族を返す"""
        return "日本人"


class American(Human):
    @override
    def __init__(self, name: str):
        self.__name = name
    
    @override
    def say_hello(self) -> None:
        """英語で挨拶"""
        print(f"Hello. My name is {self.__name}.")

    @classmethod
    @override
    def get_type(cls) -> str:
        """英語の種族を返す"""
        return "アメリカ人"

if __name__ == "__main__":
    human: Human
    human = Japanese("太郎")
    human.say_hello()
    print(human.get_type())

    human = American("John")
    human.say_hello()
    print(human.get_type())