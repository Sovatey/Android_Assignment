package com.example.assingment_coffee.models;

import java.io.Serializable;

public class CartModel implements Serializable {

    private String cartID;
    private double amount;
    private FoodModel food;

    public CartModel() {
    }

    public CartModel(String cartID, double amount, FoodModel food) {
        this.cartID = cartID;
        this.amount = amount;
        this.food = food;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public FoodModel getFood() {
        return food;
    }

    public void setFood(FoodModel food) {
        this.food = food;
    }

}
