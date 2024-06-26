package id.ac.ui.cs.advprog.farrel.model;

import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.farrel.model.state.ActiveCartState;
import id.ac.ui.cs.advprog.farrel.model.state.CheckedOutCartState;
import id.ac.ui.cs.advprog.farrel.model.state.EmptyCartState;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class CartTest {
    private Cart cart;
    private CartItem cartItem;
    private UUID cartItemId;
    private UUID cartId;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cartId = UUID.randomUUID();
        cart.setCartId(cartId);

        cartItem = new CartItem("productId", 2, cart.getCartId(), 1000.0);
        cartItemId = UUID.randomUUID();
        cartItem.setItemId(cartItemId);
    }

    @Test
    void testGetCartId() {
        assertNotNull(cart.getCartId());
    }

    @Test
    void testAddItem() {
        cart.addItem(cartItem);
        assertFalse(cart.getItems().isEmpty());
        assertEquals(1, cart.getItems().size());
    }

    @Test
    void testRemoveItem() {
        cart.addItem(cartItem);
        cart.removeItem(cartItem.getItemId());
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void testInitialState() {
        assertTrue(cart.getItems().isEmpty());
        assertNotNull(cart.getState());
    }

    @Test
    void testStateTransitionToActive() {
        cart.addItem(cartItem);
        assertTrue(cart.getState() instanceof ActiveCartState);
    }

    @Test
    void testStateTransitionToCheckedOut() {
        cart.addItem(cartItem);
        cart.checkout();
        assertTrue(cart.getState() instanceof CheckedOutCartState);
    }

    @Test
    void testStateTransitionToEmptyFromActive() {
        cart.addItem(cartItem);
        cart.removeItem(cartItem.getItemId());
        assertTrue(cart.getState() instanceof EmptyCartState);
    }

    @Test
    void testCheckout() {
        cart.addItem(cartItem);
        cart.checkout();
        assertTrue(cart.getState() instanceof CheckedOutCartState);
    }

    @Test
    void testAddItemInCheckedOutState() {
        cart.addItem(cartItem);
        cart.checkout();
        assertThrows(IllegalStateException.class, () -> cart.addItem(new CartItem("productId2", 1, cart.getCartId(), 500.0)));
    }

    @Test
    void testRemoveItemInCheckedOutState() {
        cart.addItem(cartItem);
        cart.checkout();
        assertThrows(IllegalStateException.class, () -> cart.removeItem(cartItem.getItemId()));
    }

    @Test
    void testCheckoutFromEmptyCart() {
        assertThrows(IllegalStateException.class, () -> cart.checkout());
    }
}
