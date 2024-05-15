package id.ac.ui.cs.advprog.farrel.model.state;

import id.ac.ui.cs.advprog.farrel.model.Cart;
import id.ac.ui.cs.advprog.farrel.model.CartItem;
import java.util.UUID;

public class CheckedOutCartState implements CartState {
    @Override
    public void addItem(Cart cart, CartItem item) {
        throw new IllegalStateException("Cannot add item to a checked-out cart");
    }

    @Override
    public void removeItem(Cart cart, UUID itemId) {
        throw new IllegalStateException("Cannot remove item from a checked-out cart");
    }

    @Override
    public void checkout(Cart cart) {
        throw new IllegalStateException("Cart is already checked out");
    }
}
