package P06_AdapterBadExample;

// 4. クライアントコード
public class Client {
    public static void main(String[] args) {
        // 下記の記述の方が、スッキリするかも？？
        MediaPlayer player2 = new MediaAdapter();
        player2.play("song2.mp3");
    }
}
