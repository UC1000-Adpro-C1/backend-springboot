package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.Cart;
import id.ac.ui.cs.advprog.farrel.model.CartItem;
import id.ac.ui.cs.advprog.farrel.model.Payment;

import java.util.Optional;
import java.util.UUID;

public interface CartRestService {
    Cart createCart();
    Cart getCartById(UUID cartId);
    void addItemToCart(UUID cartId, CartItem cartItem);
    void removeItemFromCart(UUID cartId, UUID itemId);
    void deleteCart(UUID cartId);
    Optional<Cart> getActiveCartByUserId(UUID userId);
    void setUserIdForCart(UUID cartId, UUID userId);
    Payment checkoutCart(UUID cartId, String userId);
}
