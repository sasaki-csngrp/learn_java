package P00_02_Inheritance;

// 基底クラス
public class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void makeSound() {
        System.out.println("動物の鳴き声");
    }
}