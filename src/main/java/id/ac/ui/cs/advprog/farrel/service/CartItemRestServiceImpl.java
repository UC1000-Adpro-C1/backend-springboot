package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.CartItem;
import id.ac.ui.cs.advprog.farrel.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CartItemRestServiceImpl implements CartItemRestService {

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemRestServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem increaseQuantity(String itemId, int incrementQuantity) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(itemId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            int newQuantity = cartItem.getQuantity() + incrementQuantity;
            cartItem.setQuantity(newQuantity);
            return cartItemRepository.save(cartItem);
        }
        throw new IllegalArgumentException("CartItem not found with itemId: " + itemId);
    }

    @Override
    public CartItem decreaseQuantity(String itemId, int decrementQuantity) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(itemId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            int newQuantity = cartItem.getQuantity() - decrementQuantity;
            cartItem.setQuantity(newQuantity);
            return cartItemRepository.save(cartItem);
        }
        throw new IllegalArgumentException("CartItem not found with itemId: " + itemId);
    }

    @Override
    public void deleteCartItem(String itemId) {
        cartItemRepository.deleteById(itemId);
    }

    @Override
    public CartItem updatePrice(String itemId, double newPrice) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(itemId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setPrice(newPrice);
            return cartItemRepository.save(cartItem);
        }
        throw new IllegalArgumentException("CartItem not found with itemId: " + itemId);
    }
}