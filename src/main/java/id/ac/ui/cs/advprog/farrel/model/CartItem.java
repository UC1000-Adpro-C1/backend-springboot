package id.ac.ui.cs.advprog.farrel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID itemId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "cart_id")
    private UUID cartId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    public CartItem(String productId, int quantity, UUID cartId, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.cartId = cartId;
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(quantity, 0);
    }

    public void setPrice(double price) {
        this.price = Math.max(price, 0);
    }
}