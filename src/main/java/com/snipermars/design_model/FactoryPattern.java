package com.snipermars.design_model;

// 工厂模式
public class FactoryPattern {
    // 产品接口
    interface Product {
        void use();
    }

    // 具体产品A
    static class ProductA implements Product {
        @Override
        public void use() {
            System.out.println("Using Product A");
        }
    }

    // 具体产品B
    static class ProductB implements Product {
        @Override
        public void use() {
            System.out.println("Using Product B");
        }
    }

    // 工厂类
    static class ProductFactory {
        public Product createProduct(String type) {
            if (type.equals("A")) {
                return new ProductA();
            } else if (type.equals("B")) {
                return new ProductB();
            }
            return null;
        }
    }

    // 示例用法
    public static void main(String[] args) {
        ProductFactory factory = new ProductFactory();
        Product productA = factory.createProduct("A");
        productA.use();

        Product productB = factory.createProduct("B");
        productB.use();
    }
}