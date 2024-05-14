package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.CartItem;
import java.util.UUID;

public interface CartItemRestService {
    CartItem createCartItem(CartItem cartItem);
    CartItem increaseQuantity(UUID itemId, int incrementQuantity);
    CartItem decreaseQuantity(UUID itemId, int decrementQuantity);
    void deleteCartItem(UUID itemId);
    CartItem updatePrice(UUID itemId, double newPrice);
}