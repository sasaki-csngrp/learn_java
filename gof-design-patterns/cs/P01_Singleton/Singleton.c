#include <stdio.h>
#include <stddef.h>
#include "Singleton.h"

// doSomethingメソッドの実装
static void singleton_doSomething(Singleton* self) {
    if (self != NULL) {
        printf("シングルトンのメソッドが呼ばれました\n");
    }
}

// 唯一のインスタンスを保持するstatic変数（Javaのprivate staticに相当）
// mallocを使わず、静的に確保
static Singleton instance;

// 初期化フラグ（初回のみ初期化を行うため）
static int initialized = 0;

// インスタンスへのアクセスを提供する関数（JavaのgetInstance()に相当）
Singleton* Singleton_getInstance(void) {
    // 初回呼び出し時のみ初期化
    if (!initialized) {
        // 関数ポインタを設定
        instance.doSomething = singleton_doSomething;
        
        // その他の初期化処理（必要に応じて）
        // instance.someData = 0;
        
        initialized = 1;
    }
    return &instance;
}
