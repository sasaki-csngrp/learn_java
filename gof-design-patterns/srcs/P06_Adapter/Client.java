package P06_Adapter;

// 4. クライアントコード
public class Client {
    public static void main(String[] args) {
        // 既存のクラスをインスタンス化
        OldMediaPlayer oldPlayer = new OldMediaPlayer();

        // アダプターを使用して新しいインターフェースとして使用
        MediaPlayer player = new MediaAdapter(oldPlayer);

        // 新しいインターフェースで既存のクラスを使用
        // 新しいメソッドで古いメソッドを呼び出すことができる
        player.play("song.mp3");

        // 下記の記述の方が、スッキリするかも？？
        MediaPlayer player2 = new MediaAdapter(new OldMediaPlayer());
        player2.play("song2.mp3");
    }
}
