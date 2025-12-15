#include <stdio.h>
#include "Singleton.h"

int main(void) {
    // 通常のnewはできない（C言語では構造体の直接初期化を防ぐ方法がないが、
    // getInstance()を使うことで、同じインスタンスを取得することを保証）
    
    // getInstance()関数でインスタンスを取得
    Singleton* s1 = Singleton_getInstance();
    Singleton* s2 = Singleton_getInstance();
    
    // 同じインスタンスか確認（ポインタの比較）
    printf("s1 == s2: %s\n", (s1 == s2) ? "true" : "false");
    printf("s1 == s2: %d\n", (s1 == s2)); // 1 (true)
    
    // メソッドの呼び出し
    s1->doSomething(s1);
    s2->doSomething(s2);
    
    return 0;
}
