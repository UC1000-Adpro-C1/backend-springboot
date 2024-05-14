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
        CartItem cartItem = new CartItem("productId", 2, "cartId", 10.0);
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
    void testIncreaseQuantity() {
        int incrementQuantity = 2;
        CartItem cartItem = new CartItem("productId", 3, "cartId", 10.0);
        UUID itemId = cartItem.getItemId();

        when(cartItemRepository.findById(itemId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
        
        CartItem updatedCartItem = cartItemRestService.increaseQuantity(itemId, incrementQuantity);

        assertNotNull(updatedCartItem);
        assertEquals(5, updatedCartItem.getQuantity());

        verify(cartItemRepository, times(1)).findById(itemId);
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    void testDecreaseQuantity() {
        int decrementQuantity = 1;
        CartItem cartItem = new CartItem("productId", 3, "cartId", 10.0);
        UUID itemId = cartItem.getItemId();
        when(cartItemRepository.findById(itemId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        CartItem updatedCartItem = cartItemRestService.decreaseQuantity(itemId, decrementQuantity);

        assertNotNull(updatedCartItem);
        assertEquals(2, updatedCartItem.getQuantity());

        verify(cartItemRepository, times(1)).findById(itemId);
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    void testDeleteCartItem() {
        UUID itemId = UUID.randomUUID();
        cartItemRestService.deleteCartItem(itemId);
        verify(cartItemRepository, times(1)).deleteById(itemId);
    }

    @Test
    void testUpdatePrice() {
        double newPrice = 15.0;
        CartItem cartItem = new CartItem("productId", 3, "cartId", 10.0);
        UUID itemId = cartItem.getItemId();
        when(cartItemRepository.findById(itemId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        CartItem updatedCartItem = cartItemRestService.updatePrice(itemId, newPrice);

        assertNotNull(updatedCartItem);
        assertEquals(newPrice, updatedCartItem.getPrice());

        verify(cartItemRepository, times(1)).findById(itemId);
        verify(cartItemRepository, times(1)).save(cartItem);
    }
}