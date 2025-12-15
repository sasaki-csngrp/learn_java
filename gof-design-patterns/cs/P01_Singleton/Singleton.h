#ifndef SINGLETON_H
#define SINGLETON_H

// 前方宣言
typedef struct Singleton Singleton;

// 関数ポインタ型定義（メソッドに相当）
typedef void (*DoSomethingFunc)(Singleton* self);

// Singleton構造体定義
struct Singleton {
    // メソッドに相当する関数ポインタ
    DoSomethingFunc doSomething;
    
    // その他のデータメンバー（必要に応じて追加）
    // int someData;
};

// インスタンスへのアクセスを提供する関数（JavaのgetInstance()に相当）
Singleton* Singleton_getInstance(void);

#endif // SINGLETON_H
