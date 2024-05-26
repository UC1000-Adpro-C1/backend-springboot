package id.ac.ui.cs.advprog.farrel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CartItemRequest {
    private String productId;
    private int quantity;
    private double price;
    private Integer userId;

    public void setQuantity(int quantity) {
        this.quantity = Math.max(quantity, 0);
    }

    public void setPrice(double price) {
        this.price = Math.max(price, 0);
    }
}
