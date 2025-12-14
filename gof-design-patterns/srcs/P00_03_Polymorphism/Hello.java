package P00_03_Polymorphism;

// インターフェース
interface Human {
    void sayHello();
}

class Japanese implements Human {
    private String name;
    public Japanese(String name) {
        this.name = name;
    }
    @Override
    public void sayHello() {
        System.out.println("こんにちは。私の名前は" + name + "です。");
    }
}

class American implements Human {
    private String name;
    public American(String name) {
        this.name = name;
    }
    @Override
    public void sayHello() {
        System.out.println("Hello. My name is " + name + ".");
    }
}

public class Hello {
    public static void main(String[] args) {
        Human human;
        human = new Japanese("太郎");
        human.sayHello();
        human = new American("John");
        human.sayHello();
    }
}