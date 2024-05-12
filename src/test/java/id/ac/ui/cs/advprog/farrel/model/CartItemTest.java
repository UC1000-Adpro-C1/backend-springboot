package id.ac.ui.cs.advprog.farrel.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    @Test
    void testConstructor() {
        // Arrange
        String id = "item1";
        String userId = "user1";
        String cartId = "cart1";
        String productId = "product1";
        int quantity = 2;

        // Act
        CartItem cartItem = new CartItem(id, userId, cartId, productId, quantity);

        // Assert
        assertEquals(id, cartItem.getId());
        assertEquals(userId, cartItem.getUserId());
        assertEquals(cartId, cartItem.getCartId());
        assertEquals(productId, cartItem.getProductId());
        assertEquals(quantity, cartItem.getQuantity());
    }

    @Test
    void testSetters() {
        // Arrange
        CartItem cartItem = new CartItem("item1", "user1", "cart1", "product1", 2);

        // Act
        cartItem.setId("item2");
        cartItem.setUserId("user2");
        cartItem.setCartId("cart2");
        cartItem.setProductId("product2");
        cartItem.setQuantity(3);

        // Assert
        assertEquals("item2", cartItem.getId());
        assertEquals("user2", cartItem.getUserId());
        assertEquals("cart2", cartItem.getCartId());
        assertEquals("product2", cartItem.getProductId());
        assertEquals(3, cartItem.getQuantity());
    }


}