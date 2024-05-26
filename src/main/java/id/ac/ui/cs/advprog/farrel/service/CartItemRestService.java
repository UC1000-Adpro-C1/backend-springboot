package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.CartItem;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRestService {
    CartItem createCartItem(CartItem cartItem);
    CartItem updateQuantity(UUID itemId, int newQuantity);
    CartItem updatePrice(UUID itemId, double newPrice);
    Optional<CartItem> getCartItem(UUID itemId);

}