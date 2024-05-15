package id.ac.ui.cs.advprog.farrel.model.state;

import id.ac.ui.cs.advprog.farrel.model.Cart;
import id.ac.ui.cs.advprog.farrel.model.CartItem;
import java.util.UUID;

public class ActiveCartState implements CartState {
    @Override
    public void addItem(Cart cart, CartItem item) {
        cart.getItems().add(item);
    }

    @Override
    public void removeItem(Cart cart, UUID itemId) {
        cart.getItems().removeIf(item -> item.getItemId().equals(itemId));
        if (cart.getItems().isEmpty()) {
            cart.setState(new EmptyCartState()); // Transition to Empty state
        }
    }

    @Override
    public void checkout(Cart cart) {
        cart.setState(new CheckedOutCartState()); // Transition to CheckedOut state
    }
}
