package com.smartstock.model;

public class Product extends Item {

    private int quantity;
    private double price;

    public Product() {}

    public Product(int id, String name, int quantity, double price) {
        super(id, name);
        this.quantity = quantity;
        this.price = price;
    }

    // Polymorphism (Override)
    @Override
    public void displayInfo() {
        System.out.println("Product: " + getName() +
                " | Qty: " + quantity +
                " | Price: " + price);
    }

    // Getters & Setters
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
