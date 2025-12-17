package P19_Observer;

import java.util.ArrayList;
import java.util.List;

// この例はプッシュ型
// 被観測者が、観測者に対して、メッセージを送信する。notifyObservers()で送信。
// ここがいろいろなバリエーションがありそう。プル型・イベント型・プッシュ型など。
// 確かに、Reactのようなフレームワークでは、プッシュ型で使われてそう。
// （ステートが変わった⇒画面をリフレッシュする。みたいな。）

// 1. Observerインターフェース（抽象観測者）
interface Observer {
    // 観測者は、被観測者からメッセージを受け取る。
    void update(String message);
}

// 2. Subject抽象クラス（抽象被観測者）
abstract class Subject {
    // 被観測者は、観測者のリストを保持する。
    private List<Observer> observers = new ArrayList<>();
    
    // 観測者の追加
    public void attach(Observer observer) {
        observers.add(observer);
        System.out.println("Observerを追加しました");
    }
    
    // 観測者の削除
    public void detach(Observer observer) {
        observers.remove(observer);
        System.out.println("Observerを削除しました");
    }
    
    // 観測者にメッセージを送信（通知）
    // protected なので、サブクラスからのみ呼び出し可能。
    protected void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
    
    public int getObserverCount() {
        return observers.size();
    }
}

// 3. ConcreteSubject（具象被観測者）
// ニュース局。ニュースをニュースチャンネルへ発信する。
class NewsAgency extends Subject {
    private String news;
    
    public void setNews(String news) {
        this.news = news;
        System.out.println("ニュースが更新されました: " + news);
        // ニュースが設定されたことを観測者（ニュースチャンネル）へ通知
        notifyObservers(news);
    }
    
    public String getNews() {
        return news;
    }
}

// 4. ConcreteObserver（具象観測者）
// ニュースチャンネル。ニュース局からニュースを受信する。
class NewsChannel implements Observer {
    private String channelName;
    
    public NewsChannel(String channelName) {
        this.channelName = channelName;
    }
    
    // ニュース局がニュースを発信・通知したことを、ニュースチャンネルが受信する。
    @Override
    public void update(String message) {
        System.out.println(channelName + " がニュースを受信: " + message);
    }
}

public class ObserverExample {
    public static void main(String[] args) {
        // Subjectを作成
        NewsAgency newsAgency = new NewsAgency();
        
        // Observerを作成して登録
        Observer channel1 = new NewsChannel("チャンネル1");
        Observer channel2 = new NewsChannel("チャンネル2");
        Observer channel3 = new NewsChannel("チャンネル3");
        
        newsAgency.attach(channel1);
        newsAgency.attach(channel2);
        newsAgency.attach(channel3);
        
        // ニュースを更新（すべてのObserverに通知される）
        newsAgency.setNews("重要なニュース1");
        
        // Observerを削除
        newsAgency.detach(channel2);
        
        // 再度ニュースを更新
        newsAgency.setNews("重要なニュース2");
    }
}
