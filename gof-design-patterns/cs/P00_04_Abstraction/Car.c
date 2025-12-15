#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 前方宣言
typedef struct Vehicle Vehicle;
typedef struct Car Car;

// 関数ポインタ型の定義
typedef void (*StartFunc)(Vehicle* self);
typedef void (*StopFunc)(Vehicle* self);
typedef void (*DisplayBrandFunc)(Vehicle* self);
typedef void (*DestroyFunc)(Vehicle* self);

// 抽象クラス（Vehicle）の構造体定義
struct Vehicle {
    char* brand;  // protected相当のデータメンバー
    
    // 抽象メソッド（関数ポインタ）
    // NULLの場合は未実装（抽象メソッド）を意味する
    StartFunc start;
    StopFunc stop;
    
    // 具象メソッド（関数ポインタ）
    DisplayBrandFunc displayBrand;
    
    // デストラクタ
    DestroyFunc destroy;
    
    // 抽象クラスかどうかを示すフラグ（オプション）
    // int isAbstract; // 1: 抽象クラス（インスタンス化不可）
};

// 派生クラス（Car）の構造体定義
struct Car {
    Vehicle base;  // 基底構造体の埋め込み（継承の実現）
    // Car固有のメンバーがあればここに追加
};

// Vehicleの具象メソッド実装（displayBrand）
static void vehicle_displayBrand(Vehicle* self) {
    if (self != NULL && self->brand != NULL) {
        printf("ブランド: %s\n", self->brand);
    }
}

// Carのstart実装（抽象メソッドの実装）
static void car_start(Vehicle* self) {
    if (self != NULL) {
        Car* car = (Car*)self;
        printf("%sの車がエンジンを始動しました\n", car->base.brand);
    }
}

// Carのstop実装（抽象メソッドの実装）
static void car_stop(Vehicle* self) {
    if (self != NULL) {
        Car* car = (Car*)self;
        printf("%sの車が停止しました\n", car->base.brand);
    }
}

// Vehicleデストラクタ（基底クラス用）
static void vehicle_destroy(Vehicle* self) {
    if (self != NULL) {
        if (self->brand != NULL) {
            free(self->brand);
        }
        free(self);
    }
}

// Carデストラクタ
static void car_destroy(Vehicle* self) {
    if (self != NULL) {
        Car* car = (Car*)self;
        if (car->base.brand != NULL) {
            free(car->base.brand);
        }
        free(car);
    }
}

// Vehicleコンストラクタ（抽象クラス用）
// 注意: 抽象クラスは直接インスタンス化すべきではないが、
// 派生クラスの初期化で使用するため、内部関数として定義
static Vehicle* vehicle_init(Vehicle* vehicle, const char* brand) {
    if (vehicle == NULL || brand == NULL) {
        return NULL;
    }
    
    // ブランド名のコピー
    vehicle->brand = (char*)malloc(strlen(brand) + 1);
    if (vehicle->brand == NULL) {
        return NULL;
    }
    strcpy(vehicle->brand, brand);
    
    // 抽象メソッドはNULLに設定（実装されていないことを示す）
    vehicle->start = NULL;  // 抽象メソッド：派生クラスで実装必須
    vehicle->stop = NULL;   // 抽象メソッド：派生クラスで実装必須
    
    // 具象メソッドを設定
    vehicle->displayBrand = vehicle_displayBrand;
    
    return vehicle;
}

// Carコンストラクタ
Car* Car_new(const char* brand) {
    Car* car = (Car*)malloc(sizeof(Car));
    if (car == NULL) {
        return NULL;
    }
    
    // 基底クラスの初期化
    if (vehicle_init((Vehicle*)car, brand) == NULL) {
        free(car);
        return NULL;
    }
    
    // 抽象メソッドの実装（Car固有の実装を設定）
    car->base.start = car_start;
    car->base.stop = car_stop;
    
    // デストラクタを設定
    car->base.destroy = car_destroy;
    
    return car;
}

// 抽象クラスのインスタンス化を試みる関数（エラーを示すため）
// この関数は使用すべきではないが、抽象クラスの概念を示すために含める
Vehicle* Vehicle_new(const char* brand) {
    fprintf(stderr, "エラー: 抽象クラスVehicleは直接インスタンス化できません\n");
    return NULL;
}

// main関数（テスト用）
int main(void) {
    // Carオブジェクトの作成（抽象クラスVehicleを継承）
    Car* car = Car_new("Toyota");
    
    if (car == NULL) {
        fprintf(stderr, "メモリの割り当てに失敗しました\n");
        return 1;
    }
    
    // 抽象メソッドの呼び出し（Carで実装されたメソッドが実行される）
    car->base.start((Vehicle*)car);
    car->base.stop((Vehicle*)car);
    
    // 具象メソッドの呼び出し（基底クラスで実装されたメソッドが実行される）
    car->base.displayBrand((Vehicle*)car);
    
    // 基底クラスのポインタとしても使用可能
    Vehicle* vehicle = (Vehicle*)car;
    vehicle->start(vehicle);
    vehicle->stop(vehicle);
    vehicle->displayBrand(vehicle);
    
    // メモリの解放
    car->base.destroy((Vehicle*)car);
    
    // 抽象クラスの直接インスタンス化を試みる（エラーになる）
    // Vehicle* abstract_vehicle = Vehicle_new("Test"); // これはNULLを返す
    
    return 0;
}
