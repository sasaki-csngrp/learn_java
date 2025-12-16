from Singleton import Singleton

if __name__ == "__main__":
    # Pythonでは、__new__をオーバーライドしているため、
    # 直接インスタンス化しても同じインスタンスが返される
    # ただし、getInstance()を使う方が明示的
    
    # get_instance()メソッドでインスタンスを取得
    s1 = Singleton.get_instance()
    s2 = Singleton.get_instance()
    
    # 直接インスタンス化しても同じインスタンスが返される
    s3 = Singleton()
    
    # 同じインスタンスか確認
    print(f"s1 == s2: {s1 is s2}")  # True
    print(f"s1 == s3: {s1 is s3}")  # True
    print(f"s1 is s2: {s1 is s2}")  # True（is演算子で同一性を確認）
    
    s1.do_something()
