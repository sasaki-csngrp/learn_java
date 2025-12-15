#include <stdio.h>
#include <stdlib.h>

// 不完全型でbalanceを隠蔽（カプセル化）
typedef struct BankAccount BankAccount;

// 関数ポインタ型の定義
typedef void (*DepositFunc)(BankAccount* self, double amount);
typedef void (*WithdrawFunc)(BankAccount* self, double amount);
typedef double (*GetBalanceFunc)(BankAccount* self);
typedef void (*DestroyFunc)(BankAccount* self);

// BankAccount構造体の定義
// 実際のデータメンバーは実装ファイル内で定義（カプセル化）
struct BankAccount {
    double balance;  // データを隠蔽（通常は実装ファイル内で定義）
    
    // 関数ポインタ（メソッドに相当）
    DepositFunc deposit;
    WithdrawFunc withdraw;
    GetBalanceFunc getBalance;
    DestroyFunc destroy;
};

// メソッドの実装
static void deposit_impl(BankAccount* self, double amount) {
    if (self != NULL && amount > 0) {
        self->balance += amount;
    }
}

static void withdraw_impl(BankAccount* self, double amount) {
    if (self != NULL && amount > 0 && amount <= self->balance) {
        self->balance -= amount;
    }
}

static double getBalance_impl(BankAccount* self) {
    if (self != NULL) {
        return self->balance;
    }
    return 0.0;
}

static void destroy_impl(BankAccount* self) {
    if (self != NULL) {
        free(self);
    }
}

// コンストラクタ（オブジェクト生成関数）
BankAccount* BankAccount_new(double initialBalance) {
    BankAccount* account = (BankAccount*)malloc(sizeof(BankAccount));
    if (account == NULL) {
        return NULL;
    }
    
    // データメンバーの初期化
    account->balance = initialBalance;
    
    // 関数ポインタの初期化（メソッドの割り当て）
    account->deposit = deposit_impl;
    account->withdraw = withdraw_impl;
    account->getBalance = getBalance_impl;
    account->destroy = destroy_impl;
    
    return account;
}

// main関数（テスト用）
int main(void) {
    // オブジェクトの作成
    BankAccount* account = BankAccount_new(1000);
    
    if (account == NULL) {
        fprintf(stderr, "メモリの割り当てに失敗しました\n");
        return 1;
    }
    
    // メソッドの呼び出し（関数ポインタ経由）
    account->deposit(account, 500);
    account->withdraw(account, 200);
    
    printf("残高: %.2f\n", account->getBalance(account));
    
    // メモリの解放
    account->destroy(account);
    
    return 0;
}
