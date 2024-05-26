package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.Cart;
import id.ac.ui.cs.advprog.farrel.model.CartItem;
import id.ac.ui.cs.advprog.farrel.model.Payment;
import id.ac.ui.cs.advprog.farrel.model.state.CheckedOutCartState;
import id.ac.ui.cs.advprog.farrel.model.state.EmptyCartState;
import id.ac.ui.cs.advprog.farrel.repository.CartItemRepository;
import id.ac.ui.cs.advprog.farrel.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartRestServiceImpl implements CartRestService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private StaffRestService staffRestService;

    @Override
    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(UUID cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with id: " + cartId));
        return cart;
    }

    @Override
    public void addItemToCart(UUID cartId, CartItem cartItem) {
        Cart cart = getCartById(cartId);
        cartItem.setCartId(cartId);  
        cart.addItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(UUID cartId, UUID itemId) {
        Cart cart = getCartById(cartId);
        cart.removeItem(itemId);

        cartItemRepository.deleteById(itemId);

        if (cart.getItems().isEmpty()) {
            cart.setState(new EmptyCartState());
        }
        
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(UUID cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public Optional<Cart> getActiveCartByUserId(UUID userId) {
        Optional<Cart> cart = cartRepository.findByUserIdAndStateString(userId, "ActiveCartState");
        if (cart.isEmpty()) {
            cart = cartRepository.findByUserIdAndStateString(userId, "EmptyCartState");
        }
        return cart;
    }


    @Override
    public void setUserIdForCart(UUID cartId, UUID userId) {
        Cart cart = getCartById(cartId);
        cart.setUserId(userId);
        cartRepository.save(cart);
    }

    @Override
    public Payment checkoutCart(UUID cartId, String userId) {
        Cart cart = getCartById(cartId);

        // Calculate total amount
        long totalAmount = (long) calculateTotalAmount(cart);

        // Create a new payment
        Payment payment = new Payment.PaymentBuilder(UUID.randomUUID(), totalAmount, userId)
                .build();

        Payment createdPayment = staffRestService.createPayment(payment);

        // Update the cart state to CheckedOut
        cart.setState(new CheckedOutCartState());
        cartRepository.save(cart);

        return createdPayment;
    }

    public double calculateTotalAmount(Cart cart) {
        return cart.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }

}