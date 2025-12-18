package P90_StandUp;

abstract class Shain  {
    private String name;
    private int baseSalary;

    public Shain(String name, int baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public final void standup() {
        System.out.println(name + "が" + getStyle() + "起立して答えました。給料は" + getSalary() + "円です。");
    }

    protected abstract int getSalary();
    protected int getBaseSalary(){
        return baseSalary;
    }
    protected abstract String getStyle();
}

class Tanto extends Shain {
    public Tanto(String name, int baseSalary) {
        super(name, baseSalary);
    }
    @Override
    protected int getSalary() {
        return getBaseSalary();
    }
    @Override
    protected String getStyle() {
        return "普通に";
    }
}

class Shunin extends Shain {
    public Shunin(String name, int baseSalary) {
        super(name, baseSalary);
    }
    @Override
    protected int getSalary() {
        return getBaseSalary() * 2 + 1;
    }
    @Override
    protected String getStyle() {
        return "シャキッと";
    }
}

class Bucho extends Shain {
    public Bucho(String name, int baseSalary) {
        super(name, baseSalary);
    }
    @Override
    protected int getSalary() {
        return getBaseSalary() * 3;
    }
    @Override
    protected String getStyle() {
        return "だるそうに";
    }
}

class SyainFactory {
    public static Shain createShain(String name, int baseSalary) {
        if (name.equals("Tanto")) {
            return new Tanto("担当", baseSalary);
        } else if (name.equals("Shunin")) {
            return new Shunin("主任", baseSalary);
        } else if (name.equals("Bucho")) {
            return new Bucho("部長", baseSalary);
        }
        else {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
    }
}

public class StandUp {
    public static void main(String[] args) {
        Shain shain = null;
        shain = SyainFactory.createShain("Tanto", 100);
        shain.standup();
        shain = SyainFactory.createShain("Shunin", 100);
        shain.standup();
        shain = SyainFactory.createShain("Bucho", 100);
        shain.standup();
    }
}
