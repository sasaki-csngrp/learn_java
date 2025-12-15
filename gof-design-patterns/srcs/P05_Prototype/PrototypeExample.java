package P05_Prototype;

// 1. プロトタイプクラス（Cloneableインターフェースを実装）
// これはCloneableインターフェースを実装しているので、clone()メソッドを実装し、
// Clone()メソッドを呼び出すことで、オブジェクトを複製することができる。
public class PrototypeExample implements Cloneable {
    private String data;
    private int number;
    
    public PrototypeExample(String data, int number) {
        this.data = data;
        this.number = number;
    }
    
    // クローンメソッド
    @Override
    public PrototypeExample clone() {
        try {
            return (PrototypeExample) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("クローンに失敗しました", e);
        }
    }
    
    // Getter/Setter
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    
    @Override
    public String toString() {
        return "PrototypeExample [data=" + data + ", number=" + number + "]";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrototypeExample that = (PrototypeExample) obj;
        return number == that.number && 
               (data == null ? that.data == null : data.equals(that.data));
    }
    
    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + number;
        return result;
    }
}
