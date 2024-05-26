package id.ac.ui.cs.advprog.farrel.model.state;

import id.ac.ui.cs.advprog.farrel.model.Cart;
import id.ac.ui.cs.advprog.farrel.model.CartItem;
import java.util.UUID;

public class EmptyCartState implements CartState {
    @Override
    public void addItem(Cart cart, CartItem item) {
        cart.getItems().add(item);
        cart.setState(new ActiveCartState()); // Transition to Active state
    }

    @Override
    public void removeItem(Cart cart, UUID itemId) {
        throw new IllegalStateException("Cannot remove item from an empty cart");
    }

    @Override
    public void checkout(Cart cart) {
        throw new IllegalStateException("Cannot checkout an empty cart");
    }
}
