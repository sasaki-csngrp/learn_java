# オブザーバーパターン（Observer Pattern）学習プラン

## 目次

1. [はじめに](#はじめに)
2. [オブザーバーパターンとは](#オブザーバーパターンとは)
3. [基本的な実装](#基本的な実装)
4. [実装のバリエーション](#実装のバリエーション)
5. [実践例](#実践例)
6. [まとめ](#まとめ)

---

## はじめに

オブザーバーパターンは、GoF（Gang of Four）によって提唱された23のデザインパターンのうち、**振る舞いに関するパターン（Behavioral Pattern）**に分類されます。

このパターンは、オブジェクト間の依存関係を定義し、1つのオブジェクト（Subject）の状態が変化したときに、依存しているすべてのオブジェクト（Observer）に自動的に通知する仕組みを提供します。

### 学習目標

この学習プランを完了すると、以下のことができるようになります：

- オブザーバーパターンの目的と利点を理解する
- 基本的なオブザーバーパターンの実装方法を理解する
- SubjectとObserverの関係を理解する
- イベント駆動プログラミングでオブザーバーパターンを適用できる
- 実際のプロジェクトでオブザーバーパターンを適用できる

---

## オブザーバーパターンとは

### 定義

オブザーバーパターンは、オブジェクト間の依存関係を定義するデザインパターンです。1つのオブジェクト（Subject）の状態が変化したときに、依存しているすべてのオブジェクト（Observer）に自動的に通知し、更新を行います。

### 主な特徴

1. **一対多の依存関係**: 1つのSubjectが複数のObserverに通知
2. **疎結合**: SubjectとObserverは疎結合で、互いに直接参照しない
3. **自動通知**: 状態変化時に自動的にObserverに通知
4. **動的な追加・削除**: 実行時にObserverを追加・削除可能

### 使用される場面

オブザーバーパターンは、以下のような場面で使用されます：

- **GUIアプリケーション**: ボタンクリック、テキスト入力などのイベント処理
- **MVCアーキテクチャ**: Modelの変更をViewに通知
- **イベント駆動システム**: イベントの発行と購読
- **データバインディング**: データの変更をUIに反映
- **ログシステム**: ログの出力先を動的に追加・削除
- **ストリーミング**: データストリームの購読
- **リアクティブプログラミング**: データフローの処理

### メリット

- **疎結合**: SubjectとObserverが疎結合で、互いに直接参照しない
- **拡張性**: 新しいObserverを追加する際に、既存のコードを変更する必要がない
- **一貫性**: 状態変化時にすべてのObserverが自動的に更新される
- **再利用性**: SubjectとObserverを独立して再利用可能
- **動的な関係**: 実行時にObserverを追加・削除可能

### デメリット

- **予期しない更新**: 通知の順序が保証されない場合がある
- **パフォーマンス**: 多くのObserverが存在する場合、通知に時間がかかる
- **循環参照**: 不適切な実装により循環参照が発生する可能性がある
- **デバッグの困難さ**: 通知の流れが複雑になると、デバッグが困難になる場合がある

---

## 基本的な実装

### 実装のポイント

オブザーバーパターンを実装するには、以下の要素が必要です：

1. **Subject（被観測者）**: 状態を保持し、Observerのリストを管理するオブジェクト
2. **Observer（観測者）**: Subjectの状態変化を監視するオブジェクトのインターフェース
3. **ConcreteSubject（具象被観測者）**: Subjectを実装し、状態を管理するクラス
4. **ConcreteObserver（具象観測者）**: Observerを実装し、通知を受け取って処理を行うクラス

### 基本的な実装例

```java
import java.util.ArrayList;
import java.util.List;

// 1. Observerインターフェース
public interface Observer {
    void update(String message);
}

// 2. Subject抽象クラス
public abstract class Subject {
    private List<Observer> observers = new ArrayList<>();
    
    public void attach(Observer observer) {
        observers.add(observer);
        System.out.println("Observerを追加しました");
    }
    
    public void detach(Observer observer) {
        observers.remove(observer);
        System.out.println("Observerを削除しました");
    }
    
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
public class NewsAgency extends Subject {
    private String news;
    
    public void setNews(String news) {
        this.news = news;
        System.out.println("ニュースが更新されました: " + news);
        notifyObservers(news);
    }
    
    public String getNews() {
        return news;
    }
}

// 4. ConcreteObserver（具象観測者）
public class NewsChannel implements Observer {
    private String channelName;
    
    public NewsChannel(String channelName) {
        this.channelName = channelName;
    }
    
    @Override
    public void update(String message) {
        System.out.println(channelName + " がニュースを受信: " + message);
    }
}
```

### 使用例

```java
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
```

### パターンの構造

```
Subject (抽象クラス)
  ├─ observers: List<Observer>
  ├─ attach(Observer)
  ├─ detach(Observer)
  └─ notifyObservers()
        ↓
ConcreteSubject
  └─ setState() → notifyObservers()
        ↓
Observer (インターフェース)
  └─ update()
        ↓
ConcreteObserver
  └─ update() [実装]
```

---

## 実装のバリエーション

### バリエーション1: プッシュモデルとプルモデル

プッシュモデルでは、Subjectが変更されたデータをObserverに送信します。プルモデルでは、Observerが必要なデータをSubjectから取得します。

```java
// プッシュモデル（データを送信）
public interface PushObserver {
    void update(String news, String category, int priority);
}

// プルモデル（Subjectへの参照を保持）
public interface PullObserver {
    void update(Subject subject);
}

// プッシュモデルの実装
public class PushNewsChannel implements PushObserver {
    private String channelName;
    
    public PushNewsChannel(String channelName) {
        this.channelName = channelName;
    }
    
    @Override
    public void update(String news, String category, int priority) {
        System.out.println(channelName + " が受信: " + news + 
                          " (カテゴリ: " + category + ", 優先度: " + priority + ")");
    }
}

// プルモデルの実装
public class PullNewsChannel implements PullObserver {
    private String channelName;
    
    public PullNewsChannel(String channelName) {
        this.channelName = channelName;
    }
    
    @Override
    public void update(Subject subject) {
        if (subject instanceof NewsAgency) {
            NewsAgency agency = (NewsAgency) subject;
            String news = agency.getNews();
            System.out.println(channelName + " が取得: " + news);
        }
    }
}
```

### バリエーション2: イベントベースのオブザーバー

イベントタイプに応じて異なる処理を行うオブザーバーです。

```java
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

// イベントタイプ
public enum EventType {
    NEWS_UPDATE,
    BREAKING_NEWS,
    SPORTS_UPDATE,
    WEATHER_UPDATE
}

// イベント
public class Event {
    private EventType type;
    private String data;
    
    public Event(EventType type, String data) {
        this.type = type;
        this.data = data;
    }
    
    public EventType getType() {
        return type;
    }
    
    public String getData() {
        return data;
    }
}

// イベントベースのObserver
public interface EventObserver {
    void onEvent(Event event);
    boolean supports(EventType eventType);
}

// イベントベースのSubject
public class EventSubject {
    private Map<EventType, List<EventObserver>> observers = new HashMap<>();
    
    public void subscribe(EventType eventType, EventObserver observer) {
        observers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(observer);
    }
    
    public void unsubscribe(EventType eventType, EventObserver observer) {
        List<EventObserver> list = observers.get(eventType);
        if (list != null) {
            list.remove(observer);
        }
    }
    
    public void notify(Event event) {
        List<EventObserver> list = observers.get(event.event.getType());
        if (list != null) {
            for (EventObserver observer : list) {
                if (observer.supports(event.getType())) {
                    observer.onEvent(event);
                }
            }
        }
    }
}

// 具象Observer
public class BreakingNewsObserver implements EventObserver {
    @Override
    public void onEvent(Event event) {
        System.out.println("速報を受信: " + event.getData());
    }
    
    @Override
    public boolean supports(EventType eventType) {
        return eventType == EventType.BREAKING_NEWS;
    }
}
```

### バリエーション3: Java標準ライブラリの使用（非推奨）

Javaには`java.util.Observable`と`java.util.Observer`が提供されていましたが、Java 9以降は非推奨になっています。参考として以下に示します。

```java
import java.util.Observable;
import java.util.Observer;

// 非推奨: java.util.Observableを使用
@Deprecated
public class NewsAgencyObservable extends Observable {
    private String news;
    
    public void setNews(String news) {
        this.news = news;
        setChanged(); // 変更をマーク
        notifyObservers(news); // Observerに通知
    }
    
    public String getNews() {
        return news;
    }
}

// 非推奨: java.util.Observerを使用
@Deprecated
public class NewsChannelObserver implements Observer {
    private String channelName;
    
    public NewsChannelObserver(String channelName) {
        this.channelName = channelName;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof NewsAgencyObservable) {
            System.out.println(channelName + " がニュースを受信: " + arg);
        }
    }
}
```

### バリエーション4: 関数型インターフェースを使用

Java 8以降の関数型インターフェースを使用した実装です。

```java
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FunctionalSubject {
    private List<Consumer<String>> observers = new ArrayList<>();
    private String state;
    
    public void subscribe(Consumer<String> observer) {
        observers.add(observer);
    }
    
    public void unsubscribe(Consumer<String> observer) {
        observers.remove(observer);
    }
    
    public void setState(String state) {
        this.state = state;
        observers.forEach(observer -> observer.accept(state));
    }
    
    public String getState() {
        return state;
    }
}

// 使用例
public class FunctionalObserverExample {
    public static void main(String[] args) {
        FunctionalSubject subject = new FunctionalSubject();
        
        // ラムダ式でObserverを登録
        subject.subscribe(message -> 
            System.out.println("Observer 1: " + message));
        subject.subscribe(message -> 
            System.out.println("Observer 2: " + message));
        
        subject.setState("新しい状態");
    }
}
```

---

## 実践例

### 例1: 天気予報システム

天気情報が更新されたときに、複数の表示デバイスに通知する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Observerインターフェース
public interface WeatherObserver {
    void update(float temperature, float humidity, float pressure);
}

// Subject
public class WeatherStation {
    private List<WeatherObserver> observers = new ArrayList<>();
    private float temperature;
    private float humidity;
    private float pressure;
    
    public void registerObserver(WeatherObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }
    
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
    
    private void measurementsChanged() {
        notifyObservers();
    }
    
    private void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }
    
    public float getTemperature() {
        return temperature;
    }
    
    public float getHumidity() {
        return humidity;
    }
    
    public float getPressure() {
        return pressure;
    }
}

// ConcreteObserver
public class CurrentConditionsDisplay implements WeatherObserver {
    private float temperature;
    private float humidity;
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }
    
    public void display() {
        System.out.println("=== 現在の気象状況 ===");
        System.out.println("気温: " + temperature + "°C");
        System.out.println("湿度: " + humidity + "%");
    }
}

public class StatisticsDisplay implements WeatherObserver {
    private List<Float> temperatures = new ArrayList<>();
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;
    private float sumTemp = 0;
    private int count = 0;
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        temperatures.add(temperature);
        sumTemp += temperature;
        count++;
        
        if (temperature > maxTemp) {
            maxTemp = temperature;
        }
        if (temperature < minTemp) {
            minTemp = temperature;
        }
        
        display();
    }
    
    public void display() {
        System.out.println("=== 気象統計 ===");
        System.out.println("平均気温: " + (sumTemp / count) + "°C");
        System.out.println("最高気温: " + maxTemp + "°C");
        System.out.println("最低気温: " + minTemp + "°C");
    }
}

public class ForecastDisplay implements WeatherObserver {
    private float currentPressure = 29.92f;
    private float lastPressure;
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        lastPressure = currentPressure;
        currentPressure = pressure;
        display();
    }
    
    public void display() {
        System.out.println("=== 天気予報 ===");
        if (currentPressure > lastPressure) {
            System.out.println("天気は改善しています！");
        } else if (currentPressure == lastPressure) {
            System.out.println("天気は同じです");
        } else {
            System.out.println("雨が降る可能性があります");
        }
    }
}

// 使用例
public class WeatherStationExample {
    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();
        
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay();
        ForecastDisplay forecastDisplay = new ForecastDisplay();
        
        weatherStation.registerObserver(currentDisplay);
        weatherStation.registerObserver(statisticsDisplay);
        weatherStation.registerObserver(forecastDisplay);
        
        // 気象データを更新
        weatherStation.setMeasurements(25.0f, 65.0f, 30.4f);
        System.out.println();
        
        weatherStation.setMeasurements(27.0f, 70.0f, 29.2f);
        System.out.println();
        
        weatherStation.setMeasurements(23.0f, 90.0f, 29.2f);
    }
}
```

### 例2: ストック価格監視システム

株価の変動を監視し、複数の投資家に通知する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Observerインターフェース
public interface StockObserver {
    void update(String symbol, double price, double change);
}

// Subject
public class StockMarket {
    private List<StockObserver> observers = new ArrayList<>();
    private String symbol;
    private double price;
    
    public StockMarket(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
    }
    
    public void registerObserver(StockObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }
    
    public void setPrice(double newPrice) {
        double oldPrice = this.price;
        this.price = newPrice;
        double change = newPrice - oldPrice;
        notifyObservers(change);
    }
    
    private void notifyObservers(double change) {
        for (StockObserver observer : observers) {
            observer.update(symbol, price, change);
        }
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public double getPrice() {
        return price;
    }
}

// ConcreteObserver
public class Investor implements StockObserver {
    private String name;
    private double buyThreshold;
    private double sellThreshold;
    
    public Investor(String name, double buyThreshold, double sellThreshold) {
        this.name = name;
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
    }
    
    @Override
    public void update(String symbol, double price, double change) {
        System.out.println(name + " が通知を受信: " + symbol + 
                          " の価格が " + price + " に変更（変動: " + 
                          String.format("%.2f", change) + "）");
        
        if (change >= buyThreshold) {
            System.out.println(name + ": 買いシグナル！購入を検討します");
        } else if (change <= sellThreshold) {
            System.out.println(name + ": 売りシグナル！売却を検討します");
        }
    }
}

public class StockAlert implements StockObserver {
    private String alertType;
    
    public StockAlert(String alertType) {
        this.alertType = alertType;
    }
    
    @Override
    public void update(String symbol, double price, double change) {
        double changePercent = (change / (price - change)) * 100;
        
        if (alertType.equals("HIGH_VOLATILITY") && Math.abs(changePercent) > 5) {
            System.out.println("【アラート】" + symbol + " が高ボラティリティ: " + 
                            String.format("%.2f", changePercent) + "%");
        } else if (alertType.equals("PRICE_DROP") && change < -10) {
            System.out.println("【アラート】" + symbol + " が大幅下落: " + change);
        }
    }
}

// 使用例
public class StockMarketExample {
    public static void main(String[] args) {
        StockMarket market = new StockMarket("AAPL", 150.0);
        
        Investor investor1 = new Investor("投資家1", 5.0, -5.0);
        Investor investor2 = new Investor("投資家2", 10.0, -10.0);
        StockAlert alert = new StockAlert("HIGH_VOLATILITY");
        
        market.registerObserver(investor1);
        market.registerObserver(investor2);
        market.registerObserver(alert);
        
        // 株価を更新
        market.setPrice(155.0);
        System.out.println();
        
        market.setPrice(145.0);
        System.out.println();
        
        market.setPrice(160.0);
    }
}
```

### 例3: ファイル監視システム

ファイルの変更を監視し、複数のリスナーに通知する例です。

```java
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.io.IOException;

// Observerインターフェース
public interface FileObserver {
    void onFileChanged(Path file, FileTime lastModified);
    void onFileCreated(Path file);
    void onFileDeleted(Path file);
}

// Subject
public class FileWatcher {
    private List<FileObserver> observers = new ArrayList<>();
    private Path watchedFile;
    private FileTime lastModified;
    
    public FileWatcher(Path file) {
        this.watchedFile = file;
        try {
            if (Files.exists(file)) {
                this.lastModified = Files.getLastModifiedTime(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void registerObserver(FileObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(FileObserver observer) {
        observers.remove(observer);
    }
    
    public void checkForChanges() {
        try {
            if (!Files.exists(watchedFile)) {
                if (lastModified != null) {
                    notifyFileDeleted();
                    lastModified = null;
                }
                return;
            }
            
            FileTime currentModified = Files.getLastModifiedTime(watchedFile);
            
            if (lastModified == null) {
                notifyFileCreated();
            } else if (!currentModified.equals(lastModified)) {
                notifyFileChanged(currentModified);
            }
            
            lastModified = currentModified;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void notifyFileChanged(FileTime lastModified) {
        for (FileObserver observer : observers) {
            observer.onFileChanged(watchedFile, lastModified);
        }
    }
    
    private void notifyFileCreated() {
        for (FileObserver observer : observers) {
            observer.onFileCreated(watchedFile);
        }
    }
    
    private void notifyFileDeleted() {
        for (FileObserver observer : observers) {
            observer.onFileDeleted(watchedFile);
        }
    }
}

// ConcreteObserver
public class LogFileObserver implements FileObserver {
    @Override
    public void onFileChanged(Path file, FileTime lastModified) {
        System.out.println("【ログ】ファイルが変更されました: " + file.getFileName() + 
                          " (最終更新: " + lastModified + ")");
    }
    
    @Override
    public void onFileCreated(Path file) {
        System.out.println("【ログ】ファイルが作成されました: " + file.getFileName());
    }
    
    @Override
    public void onFileDeleted(Path file) {
        System.out.println("【ログ】ファイルが削除されました: " + file.getFileName());
    }
}

public class BackupFileObserver implements FileObserver {
    @Override
    public void onFileChanged(Path file, FileTime lastModified) {
        System.out.println("【バックアップ】ファイル変更を検出: " + file.getFileName() + 
                          " のバックアップを作成します");
    }
    
    @Override
    public void onFileCreated(Path file) {
        System.out.println("【バックアップ】新規ファイルを検出: " + file.getFileName());
    }
    
    @Override
    public void onFileDeleted(Path file) {
        System.out.println("【バックアップ】ファイル削除を検出: " + file.getFileName());
    }
}

// 使用例
public class FileWatcherExample {
    public static void main(String[] args) {
        Path file = Paths.get("test.txt");
        FileWatcher watcher = new FileWatcher(file);
        
        LogFileObserver logObserver = new LogFileObserver();
        BackupFileObserver backupObserver = new BackupFileObserver();
        
        watcher.registerObserver(logObserver);
        watcher.registerObserver(backupObserver);
        
        // ファイルの変更をチェック（実際の実装では、定期的にチェック）
        watcher.checkForChanges();
    }
}
```

### 例4: MVCアーキテクチャ

Modelの変更をViewに通知する例です。

```java
import java.util.ArrayList;
import java.util.List;

// Observerインターフェース（View）
public interface View {
    void update(String data);
}

// Subject（Model）
public class DataModel {
    private List<View> views = new ArrayList<>();
    private String data;
    
    public void attachView(View view) {
        views.add(view);
    }
    
    public void detachView(View view) {
        views.remove(view);
    }
    
    public void setData(String data) {
        this.data = data;
        notifyViews();
    }
    
    public String getData() {
        return data;
    }
    
    private void notifyViews() {
        for (View view : views) {
            view.update(data);
        }
    }
}

// ConcreteObserver（ConcreteView）
public class TextView implements View {
    private String name;
    
    public TextView(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String data) {
        System.out.println(name + " が更新: " + data);
        render();
    }
    
    private void render() {
        System.out.println("[" + name + "] テキストビューを描画");
    }
}

public class ChartView implements View {
    private String name;
    
    public ChartView(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String data) {
        System.out.println(name + " が更新: " + data);
        render();
    }
    
    private void render() {
        System.out.println("[" + name + "] チャートビューを描画");
    }
}

// Controller
public class Controller {
    private DataModel model;
    
    public Controller(DataModel model) {
        this.model = model;
    }
    
    public void updateData(String newData) {
        model.setData(newData);
    }
}

// 使用例
public class MVCExample {
    public static void main(String[] args) {
        DataModel model = new DataModel();
        Controller controller = new Controller(model);
        
        View textView = new TextView("テキストビュー1");
        View chartView = new ChartView("チャートビュー1");
        
        model.attachView(textView);
        model.attachView(chartView);
        
        // Controllerを通じてデータを更新
        controller.updateData("データ1");
        System.out.println();
        
        controller.updateData("データ2");
    }
}
```

### 例5: イベントバス

複数のイベントタイプを処理するイベントバスの例です。

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// イベント
public class Event {
    private String type;
    private Object data;
    
    public Event(String type, Object data) {
        this.type = type;
        this.data = data;
    }
    
    public String getType() {
        return type;
    }
    
    public Object getData() {
        return data;
    }
}

// イベントハンドラー
public interface EventHandler {
    void handle(Event event);
    String getEventType();
}

// イベントバス（Subject）
public class EventBus {
    private Map<String, List<EventHandler>> handlers = new HashMap<>();
    
    public void subscribe(String eventType, EventHandler handler) {
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
        System.out.println("イベントタイプ '" + eventType + "' にハンドラーを登録");
    }
    
    public void unsubscribe(String eventType, EventHandler handler) {
        List<EventHandler> handlerList = handlers.get(eventType);
        if (handlerList != null) {
            handlerList.remove(handler);
            System.out.println("イベントタイプ '" + eventType + "' からハンドラーを削除");
        }
    }
    
    public void publish(Event event) {
        List<EventHandler> handlerList = handlers.get(event.getType());
        if (handlerList != null) {
            System.out.println("イベント '" + event.getType() + "' を発行");
            for (EventHandler handler : handlerList) {
                handler.handle(event);
            }
        } else {
            System.out.println("イベントタイプ '" + event.getType() + "' のハンドラーが見つかりません");
        }
    }
}

// ConcreteEventHandler
public class UserEventHandler implements EventHandler {
    private String name;
    
    public UserEventHandler(String name) {
        this.name = name;
    }
    
    @Override
    public void handle(Event event) {
        System.out.println(name + " がイベントを処理: " + event.getType() + 
                          " - " + event.getData());
    }
    
    @Override
    public String getEventType() {
        return "USER_EVENT";
    }
}

public class OrderEventHandler implements EventHandler {
    private String name;
    
    public OrderEventHandler(String name) {
        this.name = name;
    }
    
    @Override
    public void handle(Event event) {
        System.out.println(name + " がイベントを処理: " + event.getType() + 
                          " - " + event.getData());
    }
    
    @Override
    public String getEventType() {
        return "ORDER_EVENT";
    }
}

// 使用例
public class EventBusExample {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        
        EventHandler userHandler1 = new UserEventHandler("ユーザーハンドラー1");
        EventHandler userHandler2 = new UserEventHandler("ユーザーハンドラー2");
        EventHandler orderHandler = new OrderEventHandler("注文ハンドラー");
        
        eventBus.subscribe("USER_EVENT", userHandler1);
        eventBus.subscribe("USER_EVENT", userHandler2);
        eventBus.subscribe("ORDER_EVENT", orderHandler);
        
        // イベントを発行
        eventBus.publish(new Event("USER_EVENT", "ユーザーがログインしました"));
        System.out.println();
        
        eventBus.publish(new Event("ORDER_EVENT", "注文が作成されました"));
        System.out.println();
        
        // ハンドラーを削除
        eventBus.unsubscribe("USER_EVENT", userHandler1);
        
        // 再度イベントを発行
        eventBus.publish(new Event("USER_EVENT", "ユーザーがログアウトしました"));
    }
}
```

---

## まとめ

### 学習のポイント

1. **オブザーバーパターンの目的**: オブジェクト間の依存関係を定義し、状態変化を自動的に通知
2. **基本的な構造**: Subject、Observer、ConcreteSubject、ConcreteObserverの4つの要素
3. **疎結合**: SubjectとObserverが疎結合で、互いに直接参照しない
4. **動的な関係**: 実行時にObserverを追加・削除可能

### パターンの利点と注意点

| 項目 | 内容 |
|------|------|
| **利点** | 疎結合、拡張性、一貫性、再利用性、動的な関係 |
| **注意点** | 予期しない更新、パフォーマンス、循環参照、デバッグの困難さ |
| **適用場面** | GUIアプリケーション、MVCアーキテクチャ、イベント駆動システム、データバインディング、ログシステム |

### 他のパターンとの関係

- **Mediator**: メディエーターパターンと組み合わせて、複雑な通信を管理することがある
- **Command**: コマンドパターンと組み合わせて、イベントをコマンドとして扱うことがある
- **Chain of Responsibility**: チェーンの各ハンドラーがObserverとして機能することがある

### 注意点

1. **メモリリーク**: Observerが適切に削除されないと、メモリリークが発生する可能性がある
2. **通知の順序**: Observerへの通知の順序が保証されない場合がある
3. **パフォーマンス**: 多くのObserverが存在する場合、通知に時間がかかる
4. **過度な使用を避ける**: シンプルなケースでは過剰な設計になる可能性がある

### 次のステップ

1. 実際にコードを書いて、各実装方法を試してみる
2. GUIアプリケーションでオブザーバーパターンを適用してみる
3. MVCアーキテクチャでオブザーバーパターンを使用してみる
4. イベントバスパターンを実装してみる
5. Mediatorパターンを学習する（オブザーバーパターンと組み合わせて使用）

### 参考資料

- [cs-techblog.com - オブザーバーパターン](https://cs-techblog.com/technical/observer-pattern/)
- 「オブジェクト指向における再利用のためのデザインパターン」（GoF著）
- 「リファクタリング」（Martin Fowler著）

---

**注意**: この学習プランは、オブザーバーパターンの基礎から実践的な応用までをカバーしています。実際のプロジェクトで使用する際は、プロジェクトの要件に応じて適切な実装方法を選択してください。
