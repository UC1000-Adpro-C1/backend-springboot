package id.ac.ui.cs.advprog.farrel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private String id;
    private String userId;
    private String cartId;
    private String productId;
    private int quantity;

    public CartItem(String id, String userId, String cartId, String productId, int quantity) {
        this.id = id;
        this.userId = userId;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id.equals(cartItem.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}