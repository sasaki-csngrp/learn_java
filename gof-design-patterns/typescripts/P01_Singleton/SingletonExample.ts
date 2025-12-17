import Singleton from './Singleton';

// 通常のnewはできない（コンパイルエラー）
// const s = new Singleton(); // エラー！

// getInstance()メソッドでインスタンスを取得
const s1 = Singleton.getInstance();
const s2 = Singleton.getInstance();

// 同じインスタンスか確認
console.log(`s1 === s2: ${s1 === s2}`); // true
console.log(`s1 == s2: ${s1 == s2}`); // true

s1.doSomething();
