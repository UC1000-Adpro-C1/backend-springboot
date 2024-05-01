package id.ac.ui.cs.advprog.farrel.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Setter @Getter
@Entity
@Table(name = "product")
public class Product {
    @Id
    private String productId;

    private String productName;
    private String description;
    private double price;
    private int stockQuantity;
    private String imageUrl;
    private String sellerId;

    public Product(){

    }

    public Product(ProductBuilder builder){

        this.productId = UUID.randomUUID().toString();
        this.productName = builder.name;
        this.price = builder.price;
        this.stockQuantity = builder.stock;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
        this.sellerId = builder.sellerId;
    }

    public void setStockQuantity(int stock) {
        if (stock < 0) {
            this.stockQuantity = 0;
        } else {
            this.stockQuantity = stock;
        }
    }

    public static class ProductBuilder {
        // Required parameters
        private String name;
        private int price;

        // Optional parameters
        private int stock;
        private String description;
        private String imageUrl;
        private String sellerId;

        public ProductBuilder(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public ProductBuilder setStockQuantity(int stock) {
            this.stock = stock;
            
            return this;
        }

        public ProductBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder setSellerId(String sellerId) {
            this.sellerId = sellerId;
            return this;
        }
        public Product build() {
            return new Product(this);
        }
    }
}
