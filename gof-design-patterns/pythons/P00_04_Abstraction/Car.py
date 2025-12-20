from abc import ABC, abstractmethod
from typing import override

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
    @override
    def __init__(self, brand: str):
        super().__init__(brand)  # 親クラスのコンストラクタを呼び出す
    
    @override
    def start(self) -> None:
        """エンジンを始動する（実装）"""
        print(f"{self._brand}の車がエンジンを始動しました")
    
    @override
    def stop(self) -> None:
        """停止する（実装）"""
        print(f"{self._brand}の車が停止しました")


if __name__ == "__main__":
    vehicle: Vehicle = Car("Toyota")
    vehicle.start()
    vehicle.stop()
    vehicle.display_brand()

    car: Car = Car("Honda")
    car.start()
    car.stop()
    car.display_brand()

    vehicle = car
    vehicle.start()
    vehicle.stop()
    vehicle.display_brand()
