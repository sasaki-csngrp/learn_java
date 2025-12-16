class BankAccount {
    private balance: number;  // データをprivateで隠蔽
    
    constructor(initialBalance: number) {
        this.balance = initialBalance;
    }
    
    deposit(amount: number): void {
        if (amount > 0) {
            this.balance += amount;
        }
    }
    
    withdraw(amount: number): void {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
        }
    }
    
    getBalance(): number {
        return this.balance;
    }
}

// 実行例
const account = new BankAccount(1000);
account.deposit(500);
account.withdraw(200);
console.log(`残高: ${account.getBalance()}`);
