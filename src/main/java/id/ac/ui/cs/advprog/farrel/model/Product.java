package id.ac.ui.cs.advprog.farrel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Setter @Getter
public class Product {
    private String productId;
    private String productName;
    private String description;
    private double price;
    private int stockQuantity;

    public Product(ProductBuilder builder){

        this.productId = UUID.randomUUID().toString();
        this.productName = builder.name;
        this.price = builder.price;
        this.stockQuantity = builder.stock;
        this.description = builder.description;
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

        public Product build() {
            return new Product(this);
        }
    }
}
