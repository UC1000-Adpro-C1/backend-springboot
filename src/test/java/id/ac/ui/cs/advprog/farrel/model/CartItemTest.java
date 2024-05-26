package id.ac.ui.cs.advprog.farrel.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


import java.util.UUID;

class CartItemTest {
    private CartItem cartItem;
    private UUID itemId;
    private UUID cartId;

    @BeforeEach
    void setUp() {
        cartId = UUID.randomUUID();
        cartItem = new CartItem("productId", 2, cartId, 1000.0);
        itemId = UUID.randomUUID();
        cartItem.setItemId(itemId);
    }

    @Test
    void testGetItemId() {
        assertNotNull(cartItem.getItemId());
    }
    
    @Test
    void testGetProductId() {
        assertEquals("productId", cartItem.getProductId());
    }

    @Test
    void testGetQuantity() {
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    void testGetCartId() {
        assertEquals(cartId, cartItem.getCartId());
    }

    @Test
    void testGetPrice() {
        assertEquals(1000.0, cartItem.getPrice());
    }

    @Test
    void testSetQuantity() {
        cartItem.setQuantity(3);
        assertEquals(3, cartItem.getQuantity());
    }

    @Test
    void testSetQuantityWithNegativeValue() {
        cartItem.setQuantity(-1);
        assertEquals(0, cartItem.getQuantity());
    }
}