package id.ac.ui.cs.advprog.farrel.model;

import lombok.Getter;
import java.util.UUID;

@Getter
public class CartItem {
    private String itemId;
    private String productId;
    private String cartId;
    private int quantity;
    private double price;

    public CartItem(String productId, int quantity, String cartId, double price) {
        this.itemId = UUID.randomUUID().toString();
        this.productId = productId;
        this.quantity = quantity;
        this.cartId = cartId;
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            this.quantity = 0;
        } else {
            this.quantity = quantity;
        }
    }

    public void setPrice(double price) {
        if (price < 0) {
            this.price = 0;
        } else {
            this.price = price;
        }
    }
}