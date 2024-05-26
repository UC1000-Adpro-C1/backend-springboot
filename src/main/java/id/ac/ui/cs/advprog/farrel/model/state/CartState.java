package id.ac.ui.cs.advprog.farrel.model.state;

import id.ac.ui.cs.advprog.farrel.model.Cart;
import id.ac.ui.cs.advprog.farrel.model.CartItem;
import java.util.UUID;

public interface CartState {
    void addItem(Cart cart, CartItem item);
    void removeItem(Cart cart, UUID itemId);
    void checkout(Cart cart);
}
