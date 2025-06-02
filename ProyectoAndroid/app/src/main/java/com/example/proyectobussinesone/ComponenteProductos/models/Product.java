package com.example.proyectobussinesone.ComponenteProductos.models;

// Clase auxiliar para productos

import androidx.annotation.NonNull;
import java.util.Objects;

public class Product {
    public String code;
    public String name;
    public double price;
    public String imagePath;    // o String imageBase64; o Uri imageUri;
    public int quantity;
    public Long creatorId;

    public Product(String code, String name, double price, String imagePath, int quantity, long creatorId) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
        this.creatorId = creatorId;
    }

    public Product(Product other) {
        this.code = other.code;
        this.name = other.name;
        this.price = other.price;
        this.imagePath = other.imagePath;
        this.quantity = other.quantity;
        this.creatorId = other.creatorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product p = (Product) o;
        return Double.compare(p.price, price) == 0
                && quantity == p.quantity
                && code.equals(p.code)
                && name.equals(p.name)
                && Objects.equals(imagePath, p.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, price, imagePath,quantity, creatorId);
    }

    // Constructor, getters y setters
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

}
