# 设计模式

## 单例模式

1. 全局共享，独一份；
2. 构造函数不暴露（如果暴露便不能保证一份），自己负责自己的构造；
3. 懒汉式：Lazy load，用到才加载，非线程安全。如何保证线程安全?：

- synchronized getInstance()。
- 双重检查加锁（volatile）。

4. 饿汉式：一开始就申请好，浪费了点资源，但其线程安全。
5. Holder模式：

- 改成内部类，由JVM保证线程安全性。
