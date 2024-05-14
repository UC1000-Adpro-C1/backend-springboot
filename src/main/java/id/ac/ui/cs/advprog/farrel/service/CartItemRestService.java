package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.CartItem;

public interface CartItemRestService {
    CartItem createCartItem(CartItem cartItem);
    CartItem increaseQuantity(String itemId, int incrementQuantity);
    CartItem decreaseQuantity(String itemId, int decrementQuantity);
    void deleteCartItem(String itemId);
    CartItem updatePrice(String itemId, double newPrice);
}