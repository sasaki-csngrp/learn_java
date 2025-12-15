package P06_AdapterBadExample;

// 2. Adaptee（既存のクラス）
public class OldMediaPlayer {
    public OldMediaPlayer() {
        System.out.println("古いメディアプレイヤーを初期化");
    }
    public void playOldMedia(String fileName) {
        System.out.println("古いメディアプレイヤーで再生: " + fileName);
    }
}

