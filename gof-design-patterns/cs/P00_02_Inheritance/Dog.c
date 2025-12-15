#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 前方宣言
typedef struct Animal Animal;
typedef struct Dog Dog;

// 関数ポインタ型の定義
typedef void (*MakeSoundFunc)(Animal* self);

// 基底クラス（Animal）の構造体定義
struct Animal {
    char* name;  // protected相当のデータメンバー
    
    // 仮想関数テーブル（vtable）に相当する関数ポインタ
    MakeSoundFunc makeSound;
};

// 派生クラス（Dog）の構造体定義
// 継承を実現するため、最初のメンバーとしてAnimal構造体を含める
struct Dog {
    Animal base;  // 構造体の埋め込みによる継承の実現
    // Dog固有のメンバーがあればここに追加
};

// AnimalのmakeSound実装（基底クラスのデフォルト実装）
static void animal_makeSound(Animal* self) {
    if (self != NULL) {
        printf("動物の鳴き声\n");
    }
}

// DogのmakeSound実装（オーバーライド）
static void dog_makeSound(Animal* self) {
    if (self != NULL) {
        // AnimalポインタからDogポインタにキャスト
        // 構造体の埋め込みにより、Dogの最初のメンバーがAnimalなので安全
        Dog* dog = (Dog*)self;
        printf("%sがワンワンと鳴く\n", dog->base.name);
    }
}

// Animalコンストラクタ
Animal* Animal_new(const char* name) {
    Animal* animal = (Animal*)malloc(sizeof(Animal));
    if (animal == NULL) {
        return NULL;
    }
    
    // 名前のコピー
    animal->name = (char*)malloc(strlen(name) + 1);
    if (animal->name == NULL) {
        free(animal);
        return NULL;
    }
    strcpy(animal->name, name);
    
    // デフォルトの関数ポインタを設定
    animal->makeSound = animal_makeSound;
    
    return animal;
}

// Animalデストラクタ
void Animal_destroy(Animal* self) {
    if (self != NULL) {
        if (self->name != NULL) {
            free(self->name);
        }
        free(self);
    }
}

// Dogコンストラクタ
Dog* Dog_new(const char* name) {
    Dog* dog = (Dog*)malloc(sizeof(Dog));
    if (dog == NULL) {
        return NULL;
    }
    
    // 基底クラスの初期化
    dog->base.name = (char*)malloc(strlen(name) + 1);
    if (dog->base.name == NULL) {
        free(dog);
        return NULL;
    }
    strcpy(dog->base.name, name);
    
    // オーバーライドされた関数ポインタを設定
    dog->base.makeSound = dog_makeSound;
    
    return dog;
}

// Dogデストラクタ
void Dog_destroy(Dog* self) {
    if (self != NULL) {
        // 基底クラスのクリーンアップ
        if (self->base.name != NULL) {
            free(self->base.name);
        }
        free(self);
    }
}

// main関数（テスト用）
int main(void) {
    // Dogオブジェクトの作成
    Dog* dog = Dog_new("ポチ");
    
    if (dog == NULL) {
        fprintf(stderr, "メモリの割り当てに失敗しました\n");
        return 1;
    }
    
    // 仮想関数の呼び出し（ポリモーフィズム）
    // DogのmakeSoundが呼ばれる
    dog->base.makeSound((Animal*)dog);
    
    // 基底クラスのポインタとしても使用可能（継承の利点）
    Animal* animal = (Animal*)dog;
    animal->makeSound(animal);
    
    // メモリの解放
    Dog_destroy(dog);
    
    return 0;
}
