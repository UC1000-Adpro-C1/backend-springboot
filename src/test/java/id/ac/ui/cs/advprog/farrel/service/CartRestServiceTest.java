package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.Cart;
import id.ac.ui.cs.advprog.farrel.model.CartItem;
import id.ac.ui.cs.advprog.farrel.repository.CartItemRepository;
import id.ac.ui.cs.advprog.farrel.repository.CartRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CartRestServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartRestServiceImpl cartRestService;

    private Cart cart;
    private UUID cartId;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cartId = UUID.randomUUID();
        cart.setCartId(cartId);
    }

    @Test
    void testCreateCart() {
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart createdCart = cartRestService.createCart();

        assertNotNull(createdCart);
        assertEquals(cart.getCartId(), createdCart.getCartId());

        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testGetCartById() {
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        Cart foundCart = cartRestService.getCartById(cartId);

        assertNotNull(foundCart);
        assertEquals(cartId, foundCart.getCartId());

        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void testAddItemToCart() {
        CartItem cartItem = new CartItem("productId", 2, cartId, 1000.0);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        cartRestService.addItemToCart(cartId, cartItem);

        assertFalse(cart.getItems().isEmpty());
        assertEquals(1, cart.getItems().size());
        assertEquals(cartItem, cart.getItems().get(0));

        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testRemoveItemFromCart() {
        UUID itemId = UUID.randomUUID();
        CartItem cartItem = new CartItem("productId", 2, cartId, 1000.0);
        cartItem.setItemId(itemId);
        cart.addItem(cartItem);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);
        doNothing().when(cartItemRepository).deleteById(itemId);

        cartRestService.removeItemFromCart(cartId, itemId);

        assertTrue(cart.getItems().isEmpty());

        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testDeleteCart() {
        cartRestService.deleteCart(cartId);
        verify(cartRepository, times(1)).deleteById(cartId);
    }
}
