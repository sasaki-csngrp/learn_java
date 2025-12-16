package P13_Chain_of_Responsibility;

// リクエストを処理する複数のハンドラーをチェーン状に並べて、リクエストを処理するパターン
// ハンドラーは同じリクエストを、同じ形式でリクエストできるよう、同じ抽象クラスを継承している
// そのハンドラーをチェーン状に並べて、処理できるやつが処理するだけ。
// 当然、できない場合もありうる。
// まあ、エラーをハンドリングしていく感じに近い。

// 1. リクエストクラス
class Request {
    private String type;
    private String content;
    
    public Request(String type, String content) {
        this.type = type;
        this.content = content;
    }
    
    public String getType() {
        return type;
    }
    
    public String getContent() {
        return content;
    }
}

// 2. Handler抽象クラス
abstract class Handler {
    protected Handler next;
    
    public Handler setNext(Handler next) {
        this.next = next;
        return next; // メソッドチェーンを可能にする
    }
    
    public abstract void handleRequest(Request request);
    
    // 次のハンドラーにリクエストを渡す
    protected void passToNext(Request request) {
        if (next != null) {
            next.handleRequest(request);
        } else {
            System.out.println("No handler can process this request: " + request.getType());
        }
    }
}

// 3. 具象ハンドラークラス
class ConcreteHandlerCat extends Handler {
    @Override
    public void handleRequest(Request request) {
        if ("Cat".equals(request.getType())) {
            System.out.println("ConcreteHandlerCat processed request: " + request.getContent());
        } else {
            System.out.println("ConcreteHandlerCat cannot process, passing to next handler");
            passToNext(request);
        }
    }
}

class ConcreteHandlerDog extends Handler {
    @Override
    public void handleRequest(Request request) {
        if ("Dog".equals(request.getType())) {
            System.out.println("ConcreteHandlerDog processed request: " + request.getContent());
        } else {
            System.out.println("ConcreteHandlerDog cannot process, passing to next handler");
            passToNext(request);
        }
    }
}

class ConcreteHandlerBear extends Handler {
    @Override
    public void handleRequest(Request request) {
        if ("Bear".equals(request.getType())) {
            System.out.println("ConcreteHandlerBear processed request: " + request.getContent());
        } else {
            System.out.println("ConcreteHandlerBear cannot process, passing to next handler");
            passToNext(request);
        }
    }
}

// 4. クライアントコード
public class ChainOfResponsibilityDemo {
    public static void main(String[] args) {
        System.out.println("=== 責任の連鎖パターンのデモ ===\n");
        
        // ハンドラーのチェーンを構築
        Handler handlerDog = new ConcreteHandlerDog();
        Handler handlerBear = new ConcreteHandlerBear();
        Handler handlerCat = new ConcreteHandlerCat();
        
        // チェーンを連結
        handlerDog.setNext(handlerBear).setNext(handlerCat);
        
        // リクエストを処理
        System.out.println("--- Request Dog ---");
        handlerDog.handleRequest(new Request("Dog", "Request Dog content"));
        
        System.out.println("\n--- Request Bear ---");
        handlerDog.handleRequest(new Request("Bear", "Request Bear content"));
        
        System.out.println("\n--- Request Cat ---");
        handlerDog.handleRequest(new Request("Cat", "Request Cat content"));
        
        System.out.println("\n--- Request Lion (no handler) ---");
        handlerDog.handleRequest(new Request("Lion", "Request Lion content"));
    }
}