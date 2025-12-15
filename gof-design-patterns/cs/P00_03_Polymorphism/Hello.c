#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 前方宣言
typedef struct Human Human;
typedef struct Japanese Japanese;
typedef struct American American;

// インターフェース相当の関数ポインタ型定義
typedef void (*SayHelloFunc)(Human* self);
typedef void (*DestroyFunc)(Human* self);

// インターフェース（Human）の構造体定義
// これは基底構造体として機能し、ポリモーフィズムの基盤となる
struct Human {
    // 仮想関数テーブル（vtable）に相当する関数ポインタ
    SayHelloFunc sayHello;
    DestroyFunc destroy;
    
    // 型識別用（オプション、必要に応じて使用）
    // int type; // 0: Japanese, 1: American
};

// Japanese構造体（Humanインターフェースを実装）
struct Japanese {
    Human base;  // 基底構造体の埋め込み（継承/実装の実現）
    char* name;  // Japanese固有のデータ
};

// American構造体（Humanインターフェースを実装）
struct American {
    Human base;  // 基底構造体の埋め込み（継承/実装の実現）
    char* name;  // American固有のデータ
};

// JapaneseのsayHello実装
static void japanese_sayHello(Human* self) {
    if (self != NULL) {
        // HumanポインタからJapaneseポインタにキャスト
        // 構造体の埋め込みにより、Japaneseの最初のメンバーがHumanなので安全
        Japanese* japanese = (Japanese*)self;
        printf("こんにちは。私の名前は%sです。\n", japanese->name);
    }
}

// AmericanのsayHello実装
static void american_sayHello(Human* self) {
    if (self != NULL) {
        // HumanポインタからAmericanポインタにキャスト
        American* american = (American*)self;
        printf("Hello. My name is %s.\n", american->name);
    }
}

// Japaneseデストラクタ
static void japanese_destroy(Human* self) {
    if (self != NULL) {
        Japanese* japanese = (Japanese*)self;
        if (japanese->name != NULL) {
            free(japanese->name);
        }
        free(japanese);
    }
}

// Americanデストラクタ
static void american_destroy(Human* self) {
    if (self != NULL) {
        American* american = (American*)self;
        if (american->name != NULL) {
            free(american->name);
        }
        free(american);
    }
}

// Japaneseコンストラクタ
Japanese* Japanese_new(const char* name) {
    Japanese* japanese = (Japanese*)malloc(sizeof(Japanese));
    if (japanese == NULL) {
        return NULL;
    }
    
    // 名前のコピー
    japanese->name = (char*)malloc(strlen(name) + 1);
    if (japanese->name == NULL) {
        free(japanese);
        return NULL;
    }
    strcpy(japanese->name, name);
    
    // 基底構造体の関数ポインタを設定（ポリモーフィズムの実現）
    japanese->base.sayHello = japanese_sayHello;
    japanese->base.destroy = japanese_destroy;
    
    return japanese;
}

// Americanコンストラクタ
American* American_new(const char* name) {
    American* american = (American*)malloc(sizeof(American));
    if (american == NULL) {
        return NULL;
    }
    
    // 名前のコピー
    american->name = (char*)malloc(strlen(name) + 1);
    if (american->name == NULL) {
        free(american);
        return NULL;
    }
    strcpy(american->name, name);
    
    // 基底構造体の関数ポインタを設定（ポリモーフィズムの実現）
    american->base.sayHello = american_sayHello;
    american->base.destroy = american_destroy;
    
    return american;
}

// main関数（ポリモーフィズムのデモンストレーション）
int main(void) {
    // ポリモーフィズムの実現：
    // 同じHuman型のポインタに、異なる実装（Japanese/American）を代入可能
    Human* human;
    
    // Japaneseオブジェクトを作成し、Humanポインタに代入
    human = (Human*)Japanese_new("太郎");
    if (human == NULL) {
        fprintf(stderr, "メモリの割り当てに失敗しました\n");
        return 1;
    }
    // 同じインターフェース経由で呼び出すが、Japaneseの実装が実行される
    human->sayHello(human);
    
    // メモリ解放
    human->destroy(human);
    
    // Americanオブジェクトを作成し、同じHumanポインタに代入
    human = (Human*)American_new("John");
    if (human == NULL) {
        fprintf(stderr, "メモリの割り当てに失敗しました\n");
        return 1;
    }
    // 同じインターフェース経由で呼び出すが、今度はAmericanの実装が実行される
    human->sayHello(human);
    
    // メモリ解放
    human->destroy(human);
    
    return 0;
}
