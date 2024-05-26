package id.ac.ui.cs.advprog.farrel.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.farrel.model.CartItem;
import id.ac.ui.cs.advprog.farrel.repository.CartItemRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CartItemRestServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartItemRestServiceImpl cartItemRestService;

    @Test
    void testCreateCartItem() {
        UUID cartId = UUID.randomUUID();
        CartItem cartItem = new CartItem("productId", 2, cartId, 10.0);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartItem createdCartItem = cartItemRestService.createCartItem(cartItem);

        assertNotNull(createdCartItem);
        assertEquals(cartItem.getProductId(), createdCartItem.getProductId());
        assertEquals(cartItem.getQuantity(), createdCartItem.getQuantity());
        assertEquals(cartItem.getCartId(), createdCartItem.getCartId());
        assertEquals(cartItem.getPrice(), createdCartItem.getPrice());

        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    void testUpdateQuantity() {
        int newQuantity = 5;
        UUID cartId = UUID.randomUUID();
        CartItem cartItem = new CartItem("productId", 3, cartId, 10.0);
        UUID itemId = cartItem.getItemId();

        when(cartItemRepository.findById(itemId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        CartItem updatedCartItem = cartItemRestService.updateQuantity(itemId, newQuantity);

        assertNotNull(updatedCartItem);
        assertEquals(newQuantity, updatedCartItem.getQuantity());

        verify(cartItemRepository, times(1)).findById(itemId);
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    void testUpdatePrice() {
        UUID cartId = UUID.randomUUID();
        double newPrice = 15.0;
        CartItem cartItem = new CartItem("productId", 3, cartId, 10.0);
        UUID itemId = cartItem.getItemId();
        when(cartItemRepository.findById(itemId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        CartItem updatedCartItem = cartItemRestService.updatePrice(itemId, newPrice);

        assertNotNull(updatedCartItem);
        assertEquals(newPrice, updatedCartItem.getPrice());

        verify(cartItemRepository, times(1)).findById(itemId);
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    void testGetCartItem() {
        UUID cartId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        CartItem cartItem = new CartItem("productId", 2, cartId, 10.0);
        when(cartItemRepository.findById(itemId)).thenReturn(Optional.of(cartItem));

        Optional<CartItem> foundCartItemOptional = cartItemRestService.getCartItem(itemId);

        assertTrue(foundCartItemOptional.isPresent());
        CartItem foundCartItem = foundCartItemOptional.get();
        assertEquals(cartItem.getProductId(), foundCartItem.getProductId());

        verify(cartItemRepository, times(1)).findById(itemId);
    }
}