package P05_Prototype;

public class PrototypeBasicExample {
    public static void main(String[] args) {
        // プロトタイプを作成
        PrototypeExample prototype = new PrototypeExample("初期データ", 100);
        System.out.println("プロトタイプ: " + prototype);
        
        // プロトタイプをコピー
        PrototypeExample clone1 = prototype.clone();
        clone1.setData("変更されたデータ");
        clone1.setNumber(200);
        System.out.println("クローン1: " + clone1);
        System.out.println("プロトタイプ（変更後）: " + prototype);
        
        // 複数のクローンを作成
        PrototypeExample clone2 = prototype.clone();
        PrototypeExample clone3 = prototype.clone();
        System.out.println("クローン2: " + clone2);
        System.out.println("クローン3: " + clone3);

        System.out.println("clone1 == clone2: " + (clone1 == clone2));
        System.out.println("clone1 == clone3: " + (clone1 == clone3));
        System.out.println("clone2 == clone3: " + (clone2 == clone3));
        System.out.println("clone1.equals(clone2): " + clone1.equals(clone2));
        System.out.println("clone1.equals(clone3): " + clone1.equals(clone3));
        System.out.println("clone2.equals(clone3): " + clone2.equals(clone3));
    }
}
