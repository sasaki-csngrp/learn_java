class Singleton:
    # 唯一のインスタンスを保持するクラス変数
    _instance = None
    
    # __new__ メソッドは、インスタンス生成前に呼ばれるメソッドです。
    # __new__ の約割りは、インスタンスの生成です。
    # __new__ の第一引数 cls は、クラス自身を参照します。
    # __new__ の戻り値は、インスタンスです。(必須)
    # __new__ の主な用途は、シングルトンパターン・イミュータブル型のサブクラス化・インスタンス生成のカスタマイズです。
    def __new__(cls):
        """インスタンス作成を制御するメソッド"""
        if cls._instance is None:
            cls._instance = super(Singleton, cls).__new__(cls)
        return cls._instance
    
    # __init__ メソッドは、インスタンス生成後に呼ばれるメソッドです。
    # __init__ の約割りは、インスタンスの初期化です。
    # __init__ の第一引数 self は、インスタンス自身を参照します。
    # __init__ の戻り値は、Noneです。
    # __init__ の主な用途は、インスタンスの初期化です。
    def __init__(self):
        """初期化処理（複数回呼ばれても問題ないようにする）"""
        # 初期化処理
        pass
    
    @classmethod
    def get_instance(cls):
        """インスタンスへのアクセスを提供するクラスメソッド"""
        if cls._instance is None:
            cls._instance = cls()
        return cls._instance
    
    def do_something(self) -> None:
        """その他のメソッド"""
        print("シングルトンのメソッドが呼ばれました")
