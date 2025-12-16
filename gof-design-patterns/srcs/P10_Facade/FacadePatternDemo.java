package P10_Facade;

// 複雑な複数のサブシステムを、1つのファサードクラスで簡潔に扱う。

// 1. Subsystem Classes（サブシステムクラス）

// 書籍検索サブシステム
class BookSearch {
    public void search(String bookTitle) {
        System.out.println("書籍を検索中: " + bookTitle);
        // 実際の検索処理
    }
    
    public boolean isAvailable(String bookTitle) {
        System.out.println("書籍の在庫を確認中: " + bookTitle);
        // 実際の在庫確認処理
        return true;
    }
}

// 書籍貸出サブシステム
class BookLoan {
    public void loan(String bookTitle, String userId) {
        System.out.println("書籍を貸出中: " + bookTitle + " (ユーザー: " + userId + ")");
        // 実際の貸出処理
    }
    
    public void recordLoan(String bookTitle, String userId) {
        System.out.println("貸出記録を保存中: " + bookTitle);
        // 実際の記録処理
    }
}

// 書籍返却サブシステム
class BookReturn {
    public void returnBook(String bookTitle, String userId) {
        System.out.println("書籍を返却中: " + bookTitle + " (ユーザー: " + userId + ")");
        // 実際の返却処理
    }
    
    public void updateInventory(String bookTitle) {
        System.out.println("在庫を更新中: " + bookTitle);
        // 実際の在庫更新処理
    }
}

// 2. Facade（ファサードクラス）
class LibraryFacade {
    // サブシステムクラスのインスタンスを保持する。
    private BookSearch searcher;
    private BookLoan loaner;
    private BookReturn returner;
    
    // コンストラクタでサブシステムクラスのインスタンスを生成して保持する。
    public LibraryFacade() {
        this.searcher = new BookSearch();
        this.loaner = new BookLoan();
        this.returner = new BookReturn();
    }
    
    // 書籍を検索して貸出する（簡潔なインターフェース）
    // ファサードクラスのメソッドで、サブシステムクラスのメソッドを呼び出す。
    public void findAndLoanBook(String bookTitle, String userId) {
        System.out.println("=== 書籍の検索と貸出 ===");
        searcher.search(bookTitle);
        if (searcher.isAvailable(bookTitle)) {
            loaner.loan(bookTitle, userId);
            loaner.recordLoan(bookTitle, userId);
            System.out.println("貸出が完了しました");
        } else {
            System.out.println("書籍は現在利用できません");
        }
    }
    
    // 書籍を返却する（簡潔なインターフェース）
    public void returnBook(String bookTitle, String userId) {
        System.out.println("=== 書籍の返却 ===");
        returner.returnBook(bookTitle, userId);
        returner.updateInventory(bookTitle);
        System.out.println("返却が完了しました");
    }
    
    // 書籍を検索する（簡潔なインターフェース）
    public boolean searchBook(String bookTitle) {
        System.out.println("=== 書籍の検索 ===");
        searcher.search(bookTitle);
        return searcher.isAvailable(bookTitle);
    }
}

// 3. クライアントコード
public class FacadePatternDemo {
    public static void main(String[] args) {
        // ファサードを使用（簡潔なインターフェース）
        // クライアントからは、複雑な複数のサブシステムは見えない。
        LibraryFacade library = new LibraryFacade();
        
        // 書籍を検索して貸出
        library.findAndLoanBook("デザインパターン入門", "user123");
        
        System.out.println();
        
        // 書籍を返却
        library.returnBook("デザインパターン入門", "user123");
        
        System.out.println();
        
        // 書籍を検索
        boolean available = library.searchBook("Java入門");
        System.out.println("利用可能: " + available);
    }
}