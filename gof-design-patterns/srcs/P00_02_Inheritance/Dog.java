package P00_02_Inheritance;

// 基底クラス
class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void makeSound() {
        System.out.println("動物の鳴き声");
    }
}

// 派生クラス
public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + "がワンワンと鳴く");
    }

    public static void main(String[] args) {
        Dog dog = new Dog("ポチ");
        dog.makeSound();
    }
}
