from abc import ABC, abstractmethod

# 抽象クラス
class Vehicle(ABC):
    def __init__(self, brand: str):
        self._brand = brand  # protectedな変数（慣習的に_で始める）
    
    # 抽象メソッド（実装はサブクラスで定義）
    @abstractmethod
    def start(self) -> None:
        """エンジンを始動する（抽象メソッド）"""
        pass
    
    @abstractmethod
    def stop(self) -> None:
        """停止する（抽象メソッド）"""
        pass
    
    # 具象メソッド
    def display_brand(self) -> None:
        """ブランドを表示する"""
        print(f"ブランド: {self._brand}")


# 具象クラス
class Car(Vehicle):
    def __init__(self, brand: str):
        super().__init__(brand)  # 親クラスのコンストラクタを呼び出す
    
    def start(self) -> None:
        """エンジンを始動する（実装）"""
        print(f"{self._brand}の車がエンジンを始動しました")
    
    def stop(self) -> None:
        """停止する（実装）"""
        print(f"{self._brand}の車が停止しました")


if __name__ == "__main__":
    car = Car("Toyota")
    car.start()
    car.stop()
    car.display_brand()
