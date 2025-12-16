package P16_Iterator;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

// Java標準ライブラリのIteratorインターフェース
// 各種言語でも、Iteratorインターフェースが一般的になったので、もうなじみ深い
// ex: Pythonのiter(), Rubyのeach(), JavaScriptのforEach(), C#のforeach(), Kotlinのiterator(), etc... 

public class JavaIteratorExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("要素1");
        list.add("要素2");
        list.add("要素3");
        
        // Iteratorを使用した走査
        Iterator<String> iterator = list.iterator();
        
        System.out.println("hasNext()とwhileで走査");
        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println(element);
        }
        
        System.out.println("拡張forループ（内部でIteratorを使用）");
        for (String element : list) {
            System.out.println(element);
        }

        System.out.println("Java 8以降の方法では、他の言語同様 forEach()で走査できる。");
        list.forEach(System.out::println); // ラムダ式で書くなら、list.forEach(element -> System.out.println(element)); 
    }
}
