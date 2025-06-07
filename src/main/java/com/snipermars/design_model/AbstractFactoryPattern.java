package com.snipermars.design_model;

// 抽象工厂模式
public class AbstractFactoryPattern {
    // 产品接口
    interface Product {
        void use();
    }

    // 具体产品A1
    static class ProductA1 implements Product {
        @Override
        public void use() {
            System.out.println("Using Product A1");
        }
    }

    // 具体产品A2
    static class ProductA2 implements Product {
        @Override
        public void use() {
            System.out.println("Using Product A2");
        }
    }

    // 具体产品B1
    static class ProductB1 implements Product {
        @Override
        public void use() {
            System.out.println("Using Product B1");
        }
    }

    // 具体产品B2
    static class ProductB2 implements Product {
        @Override
        public void use() {
            System.out.println("Using Product B2");
        }
    }

    // 抽象工厂接口
    interface AbstractFactory {
        Product createProductA();
        Product createProductB();
    }

    // 具体工厂1
    static class ConcreteFactory1 implements AbstractFactory {
        @Override
        public Product createProductA() {
            return new ProductA1();
        }

        @Override
        public Product createProductB() {
            return new ProductB1();
        }
    }

    // 具体工厂2
    static class ConcreteFactory2 implements AbstractFactory {
        @Override
        public Product createProductA() {
            return new ProductA2();
        }

        @Override
        public Product createProductB() {
            return new ProductB2();
        }
    }

    // 示例用法
    public static void main(String[] args) {
        AbstractFactory factory1 = new ConcreteFactory1();
        Product productA1 = factory1.createProductA();
        productA1.use();
        Product productB1 = factory1.createProductB();
        productB1.use();

        AbstractFactory factory2 = new ConcreteFactory2();
        Product productA2 = factory2.createProductA();
        productA2.use();
        Product productB2 = factory2.createProductB();
        productB2.use();
    }
}