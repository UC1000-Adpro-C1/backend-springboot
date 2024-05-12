package id.ac.ui.cs.advprog.farrel.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void testConstructor() {
        // Arrange
        String id = "cart1";
        String userId = "user1";

        // Act
        Cart cart = new Cart(id, userId);

        // Assert
        assertEquals(id, cart.getId());
        assertEquals(userId, cart.getUserId());
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void testAddItem() {
        // Arrange
        Cart cart = new Cart("cart1", "user1");
        CartItem item1 = new CartItem("item1", "user1", "cart1", "product1", 2);
        CartItem item2 = new CartItem("item2", "user1", "cart1", "product2", 3);

        // Act
        cart.addItem(item1);
        cart.addItem(item2);

        // Assert
        assertEquals(2, cart.getItems().size());
        assertTrue(cart.getItems().contains(item1));
        assertTrue(cart.getItems().contains(item2));
    }

    @Test
    void testRemoveItem() {
        // Arrange
        Cart cart = new Cart("cart1", "user1");
        CartItem item1 = new CartItem("item1", "user1", "cart1", "product1", 2);
        CartItem item2 = new CartItem("item2", "user1", "cart1", "product2", 3);
        cart.addItem(item1);
        cart.addItem(item2);

        // Act
        cart.removeItem(item1);

        // Assert
        assertEquals(1, cart.getItems().size());
        assertFalse(cart.getItems().contains(item1));
        assertTrue(cart.getItems().contains(item2));
    }
}