package com.zepo_lifestyle.hack_your_life.classes;

import androidx.annotation.NonNull;

public class Product extends Item {

    private String description;
    private double price;
    private int quantity;
    private boolean pending;

    public Product(long id, String name, String description, double price, int quantity, boolean urgent, boolean pending) {
        super(id, name, urgent);

        this.description = description;

        this.price = price;
        this.quantity = quantity;
        this.pending = pending;
    }

    /*
     * Default Constructor
     *
     *
     *
     * */

    public static Product createProduct(String name, Boolean pending) {
        return new Product(
                -1,
                (name == null) ? "" : name,
                "",
                0,
                1,
                false,
                (pending != null) && pending);
    }

    /*
     * Getters / Setters
     *
     *
     *
     * */

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSortedDescription() {
        return sort(description);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public void invertPending() {
        pending = !pending;
    }

    /*
     * RecyclerView
     *
     *
     *
     * */

    public String getSortedString() {
        return ((pending) ? "0" : "1") + "."
                + ((urgent) ? "0" : "1") + "."
                + name;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + "."
                + description + "."
                + price + "."
                + quantity + "."
                + ((pending) ? "1" : "0");
    }

}
