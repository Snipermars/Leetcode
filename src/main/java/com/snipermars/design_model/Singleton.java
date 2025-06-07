package com.snipermars.design_model;

// 单例模式
public class Singleton {
    private static Singleton instance;

    // 私有构造函数
    private Singleton() {}

    // 获取唯一实例
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // 懒汉式单例模式，线程安全
    public static Singleton getInstanceDoubleCheck(){
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }

    // 示例方法
    public void showMessage() {
        System.out.println("Hello, I am a Singleton!");
    }

    // 示例用法
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        singleton.showMessage();
    }
}