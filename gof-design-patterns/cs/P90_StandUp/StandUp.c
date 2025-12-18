#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 前方宣言
typedef struct Shain Shain;
typedef struct Tanto Tanto;
typedef struct Shunin Shunin;
typedef struct Bucho Bucho;

// 関数ポインタ型の定義
// 抽象メソッド（protected abstract相当）
typedef int (*GetSalaryFunc)(Shain* self);
typedef const char* (*GetStyleFunc)(Shain* self);
// 具象メソッド（public final相当）
typedef void (*StandupFunc)(Shain* self);
// デストラクタ
typedef void (*DestroyFunc)(Shain* self);

// 基底クラス（抽象クラスShain）の構造体定義
struct Shain {
    // データメンバー（private相当）
    char* name;
    int baseSalary;
    
    // 抽象メソッド（関数ポインタ）
    GetSalaryFunc getSalary;
    GetStyleFunc getStyle;
    
    // 具象メソッド（テンプレートメソッド）
    StandupFunc standup;
    
    // デストラクタ
    DestroyFunc destroy;
};

// 派生クラス：Tanto（担当）
struct Tanto {
    Shain base;  // 基底構造体の埋め込み（継承の実現）
};

// 派生クラス：Shunin（主任）
struct Shunin {
    Shain base;  // 基底構造体の埋め込み（継承の実現）
};

// 派生クラス：Bucho（部長）
struct Bucho {
    Shain base;  // 基底構造体の埋め込み（継承の実現）
};

// protected相当のメソッド（基底クラスのメソッド）
// 基底クラスのメソッドとして実装（getBaseSalary相当）
static int shain_getBaseSalary(Shain* self) {
    if (self != NULL) {
        return self->baseSalary;
    }
    return 0;
}

// TantoのgetSalary実装
static int tanto_getSalary(Shain* self) {
    if (self != NULL) {
        return shain_getBaseSalary(self);
    }
    return 0;
}

// TantoのgetStyle実装
static const char* tanto_getStyle(Shain* self) {
    (void)self;  // 未使用パラメータの警告を抑制
    return "普通に";
}

// ShuninのgetSalary実装
static int shunin_getSalary(Shain* self) {
    if (self != NULL) {
        return shain_getBaseSalary(self) * 2 + 1;
    }
    return 0;
}

// ShuninのgetStyle実装
static const char* shunin_getStyle(Shain* self) {
    (void)self;  // 未使用パラメータの警告を抑制
    return "シャキッと";
}

// BuchoのgetSalary実装
static int bucho_getSalary(Shain* self) {
    if (self != NULL) {
        return shain_getBaseSalary(self) * 3;
    }
    return 0;
}

// BuchoのgetStyle実装
static const char* bucho_getStyle(Shain* self) {
    (void)self;  // 未使用パラメータの警告を抑制
    return "だるそうに";
}

// テンプレートメソッドの実装（standup）
// これは基底クラスで実装され、抽象メソッド（getStyle, getSalary）を呼び出す
static void shain_standup(Shain* self) {
    if (self != NULL && self->getStyle != NULL && self->getSalary != NULL) {
        const char* style = self->getStyle(self);
        int salary = self->getSalary(self);
        printf("%sが%s起立して答えました。給料は%d円です。\n", 
               self->name, style, salary);
    }
}

// Shainデストラクタ（基底クラス用）
static void shain_destroy(Shain* self) {
    if (self != NULL) {
        if (self->name != NULL) {
            free(self->name);
        }
        free(self);
    }
}

// Shain基底クラスの初期化関数（内部使用）
static Shain* shain_init(Shain* shain, const char* name, int baseSalary,
                         GetSalaryFunc getSalary, GetStyleFunc getStyle) {
    if (shain == NULL || name == NULL) {
        return NULL;
    }
    
    // 名前のコピー
    shain->name = (char*)malloc(strlen(name) + 1);
    if (shain->name == NULL) {
        return NULL;
    }
    strcpy(shain->name, name);
    
    shain->baseSalary = baseSalary;
    
    // 抽象メソッドの関数ポインタを設定
    shain->getSalary = getSalary;
    shain->getStyle = getStyle;
    
    // テンプレートメソッドを設定（すべての派生クラスで同じ実装を使用）
    shain->standup = shain_standup;
    
    // デストラクタを設定
    shain->destroy = shain_destroy;
    
    return shain;
}

// Tantoコンストラクタ
Tanto* Tanto_new(const char* name, int baseSalary) {
    Tanto* tanto = (Tanto*)malloc(sizeof(Tanto));
    if (tanto == NULL) {
        return NULL;
    }
    
    // 基底クラスの初期化
    if (shain_init((Shain*)tanto, name, baseSalary, 
                   tanto_getSalary, tanto_getStyle) == NULL) {
        free(tanto);
        return NULL;
    }
    
    return tanto;
}

// Shuninコンストラクタ
Shunin* Shunin_new(const char* name, int baseSalary) {
    Shunin* shunin = (Shunin*)malloc(sizeof(Shunin));
    if (shunin == NULL) {
        return NULL;
    }
    
    // 基底クラスの初期化
    if (shain_init((Shain*)shunin, name, baseSalary, 
                   shunin_getSalary, shunin_getStyle) == NULL) {
        free(shunin);
        return NULL;
    }
    
    return shunin;
}

// Buchoコンストラクタ
Bucho* Bucho_new(const char* name, int baseSalary) {
    Bucho* bucho = (Bucho*)malloc(sizeof(Bucho));
    if (bucho == NULL) {
        return NULL;
    }
    
    // 基底クラスの初期化
    if (shain_init((Shain*)bucho, name, baseSalary, 
                   bucho_getSalary, bucho_getStyle) == NULL) {
        free(bucho);
        return NULL;
    }
    
    return bucho;
}

// ファクトリーメソッド（SyainFactory.createShain相当）
Shain* SyainFactory_createShain(const char* type, int baseSalary) {
    if (type == NULL) {
        return NULL;
    }
    
    if (strcmp(type, "Tanto") == 0) {
        return (Shain*)Tanto_new("担当", baseSalary);
    } else if (strcmp(type, "Shunin") == 0) {
        return (Shain*)Shunin_new("主任", baseSalary);
    } else if (strcmp(type, "Bucho") == 0) {
        return (Shain*)Bucho_new("部長", baseSalary);
    } else {
        fprintf(stderr, "エラー: 無効なタイプです: %s\n", type);
        return NULL;
    }
}

// main関数
int main(void) {
    Shain* shain = NULL;
    
    // ファクトリーメソッドを使用してTantoを作成
    shain = SyainFactory_createShain("Tanto", 100);
    if (shain != NULL) {
        // ポリモーフィズム：同じインターフェース（standup）で呼び出すが、
        // 実際の実装（getStyle, getSalary）は各派生クラスで異なる
        shain->standup(shain);
        shain->destroy(shain);
    }
    
    // ファクトリーメソッドを使用してShuninを作成
    shain = SyainFactory_createShain("Shunin", 100);
    if (shain != NULL) {
        shain->standup(shain);
        shain->destroy(shain);
    }
    
    // ファクトリーメソッドを使用してBuchoを作成
    shain = SyainFactory_createShain("Bucho", 100);
    if (shain != NULL) {
        shain->standup(shain);
        shain->destroy(shain);
    }
    
    return 0;
}
