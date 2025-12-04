package com.example.bil_projekt.model;

public class DamageMatrix {
    private int matrix_id;
    private String damage_type;
    private double price;

    public DamageMatrix() {}

    public int getMatrix_id() {
        return matrix_id;
    }
    public void setMatrix_id(int matrix_id) {
        this.matrix_id = matrix_id;
    }

    public String getDamage_type() {
        return damage_type;
    }
    public void setDamage_type(String damage_type) {
        this.damage_type = damage_type;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

}
