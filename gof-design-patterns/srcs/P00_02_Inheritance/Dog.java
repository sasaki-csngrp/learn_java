package P00_02_Inheritance;

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
