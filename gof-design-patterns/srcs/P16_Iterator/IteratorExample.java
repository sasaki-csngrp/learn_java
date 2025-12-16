package P16_Iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

// どちらかといえば、自分で実装するよりは、言語標準のIteratorを使う場合が多いだろう。
// ex: Javaのjava.util.Iterator, Pythonのiter(), Rubyのeach(), JavaScriptのforEach(), C#のforeach(), Kotlinのiterator(), etc... 
// 自分で作ったクラスをIteratorで走査する場合は、このパターンを使う。

// 1. Iteratorインターフェース
interface Iterator<T> {
    boolean hasNext();
    T next();
    void remove(); // オプション
}

// 2. Aggregateインターフェース
interface Aggregate<T> {
    Iterator<T> createIterator();
}

// 3. ConcreteIterator（具象イテレーター）
class BookIterator implements Iterator<Book> {
    private BookCollection collection;
    private int position = 0;
    
    public BookIterator(BookCollection collection) {
        this.collection = collection;
    }
    
    @Override
    public boolean hasNext() {
        return position < collection.size();
    }
    
    @Override
    public Book next() {
        if (hasNext()) {
            return collection.get(position++);
        }
        throw new NoSuchElementException();
    }
    
    @Override
    public void remove() {
        if (position <= 0) {
            throw new IllegalStateException();
        }
        collection.remove(--position);
    }
}

// 4. ConcreteAggregate（具象集合体）
class BookCollection implements Aggregate<Book> {
    private List<Book> books = new ArrayList<>();
    
    public void addBook(Book book) {
        books.add(book);
    }
    
    public Book get(int index) {
        return books.get(index);
    }
    
    public int size() {
        return books.size();
    }
    
    public void remove(int index) {
        books.remove(index);
    }
    
    @Override
    public Iterator<Book> createIterator() {
        return new BookIterator(this);
    }
}

// 5. 使用するクラス
class Book {
    private String title;
    private String author;
    
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    @Override
    public String toString() {
        return title + " by " + author;
    }
}

public class IteratorExample {
    public static void main(String[] args) {
        BookCollection collection = new BookCollection();
        collection.addBook(new Book("Java入門", "山田太郎"));
        collection.addBook(new Book("デザインパターン", "佐藤花子"));
        collection.addBook(new Book("リファクタリング", "鈴木一郎"));
        
        Iterator<Book> iterator = collection.createIterator();
        
        System.out.println("=== 本のリスト ===");
        while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println(book);
        }
    }
}
