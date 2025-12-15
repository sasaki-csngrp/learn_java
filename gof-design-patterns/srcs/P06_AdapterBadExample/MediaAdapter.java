package P06_AdapterBadExample;

// 3. Adapter（アダプタークラス）
public class MediaAdapter implements MediaPlayer {
    // Adapteeを保持（これがコンポジション（has-a）の関係）
    private OldMediaPlayer oldMediaPlayer;
    
    public MediaAdapter() {
        // ここに拡張性がなくなるから、これはNGだって言われた。
        // １．アダプターがOldMediaPlayerに依存している。（密結合）
        // つまり、違うアダプティークラスを使いたい場合に、修正が必要になる。
        // オリジナルなら、モックにするとか、新しいアダプティーを利用する際に、
        // 注入するだけで済む。
        this.oldMediaPlayer = new OldMediaPlayer();
    }
    
    // 新しいメソッドで、古いメソッドを呼ぶ事で変換
    @Override
    public void play(String fileName) {
        // Adapteeのメソッドを呼び出して変換
        oldMediaPlayer.playOldMedia(fileName);
    }
}
