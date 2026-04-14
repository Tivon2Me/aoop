package com.smartstock.test;

import com.smartstock.model.Product;

public class TestProduct {
    public static void main(String[] args) {

        Product p = new Product(1, "Laptop Bag", 5, 2500.0);

        p.displayInfo();
    }
}
