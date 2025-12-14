package P04_BuilderV1;

// 製品クラス
public class Computer {
    private String cpu;
    private String memory;
    private String storage;
    private String graphicsCard;
    private boolean hasBluetooth;
    private boolean hasWifi;
    
    private Computer(ComputerBuilder builder) {
        this.cpu = builder.cpu;
        this.memory = builder.memory;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.hasBluetooth = builder.hasBluetooth;
        this.hasWifi = builder.hasWifi;
    }
    
    @Override
    public String toString() {
        return "Computer [CPU=" + cpu + ", Memory=" + memory + 
               ", Storage=" + storage + ", GraphicsCard=" + graphicsCard +
               ", Bluetooth=" + hasBluetooth + ", WiFi=" + hasWifi + "]";
    }
    
    // Builderクラス（内部クラス）
    public static class ComputerBuilder {
        // 必須パラメータ
        private String cpu;
        private String memory;
        private String storage;
        
        // オプショナルパラメータ
        private String graphicsCard;
        private boolean hasBluetooth = false;
        private boolean hasWifi = false;
        
        public ComputerBuilder(String cpu, String memory, String storage) {
            this.cpu = cpu;
            this.memory = memory;
            this.storage = storage;
        }
        
        public ComputerBuilder graphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }
        
        public ComputerBuilder bluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }
        
        public ComputerBuilder wifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }
        
        public Computer build() {
            // 検証処理
            if (cpu == null || memory == null || storage == null) {
                throw new IllegalStateException("必須パラメータが不足しています");
            }
            return new Computer(this);
        }
    }
}
