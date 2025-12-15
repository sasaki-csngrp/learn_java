package P06_Adapter;

// 3. Adapter（アダプタークラス）
public class MediaAdapter implements MediaPlayer {
    // Adapteeを保持（これがコンポジション（has-a）の関係）
    private OldMediaPlayer oldMediaPlayer;
    
    public MediaAdapter(OldMediaPlayer oldMediaPlayer) {
        this.oldMediaPlayer = oldMediaPlayer;
    }
    
    // 新しいメソッドで、古いメソッドを呼ぶ事で変換
    @Override
    public void play(String fileName) {
        // Adapteeのメソッドを呼び出して変換
        oldMediaPlayer.playOldMedia(fileName);
    }
}
