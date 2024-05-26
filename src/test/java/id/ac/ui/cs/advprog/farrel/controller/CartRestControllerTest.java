package id.ac.ui.cs.advprog.farrel.controller;

import id.ac.ui.cs.advprog.farrel.dto.CartItemRequest;
import id.ac.ui.cs.advprog.farrel.dto.CartResponse;
import id.ac.ui.cs.advprog.farrel.model.Cart;
import id.ac.ui.cs.advprog.farrel.model.CartItem;
import id.ac.ui.cs.advprog.farrel.service.CartItemRestService;
import id.ac.ui.cs.advprog.farrel.service.CartRestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CartRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartRestService cartRestService;

    @Mock
    private CartItemRestService cartItemRestService;

    @InjectMocks
    private CartRestController cartRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartRestController).build();
    }

    @Test
    void testAddProductToCart_NewCartItem() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID cartId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        CartItemRequest cartItemRequest = new CartItemRequest();
        cartItemRequest.setUserId(userId);
        cartItemRequest.setQuantity(2);
        cartItemRequest.setPrice(10.0);
        cartItemRequest.setProductId("productId");

        Cart cart = new Cart();
        cart.setCartId(cartId);
        cart.setUserId(userId);

        CartItem cartItem = new CartItem("productId", 2, cartId, 10.0);
        cartItem.setItemId(itemId);

        when(cartRestService.getActiveCartByUserId(userId)).thenReturn(Optional.empty());
        when(cartRestService.createCart()).thenReturn(cart);
        when(cartItemRestService.createCartItem(any(CartItem.class))).thenReturn(cartItem);
        doNothing().when(cartRestService).addItemToCart(cartId, cartItem);

        mockMvc.perform(post("/cart/carts/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\": \"productId\", \"quantity\": 2, \"price\": 10.0, \"userId\": \"" + userId + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value(cartId.toString()))
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.itemId").value(itemId.toString()));

        verify(cartRestService, times(1)).getActiveCartByUserId(userId);
        verify(cartRestService, times(1)).createCart();
        verify(cartItemRestService, times(1)).createCartItem(any(CartItem.class));
        verify(cartRestService, times(1)).addItemToCart(cartId, cartItem);
    }

    @Test
    void testRemoveProductFromCart() throws Exception {
        UUID cartId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        doNothing().when(cartRestService).removeItemFromCart(cartId, itemId);

        mockMvc.perform(delete("/cart/carts/{cartId}/items/{itemId}", cartId, itemId))
                .andExpect(status().isNoContent());

        verify(cartRestService, times(1)).removeItemFromCart(cartId, itemId);
    }

    @Test
    void testGetActiveCartByUserId_WithItems() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID cartId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setCartId(cartId);
        cart.setUserId(userId);
        CartItem cartItem = new CartItem("productId", 2, cartId, 10.0);
        cartItem.setItemId(itemId);
        cart.addItem(cartItem);

        when(cartRestService.getActiveCartByUserId(userId)).thenReturn(Optional.of(cart));

        mockMvc.perform(get("/cart/carts/active/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value("productId"))
                .andExpect(jsonPath("$[0].quantity").value(2))
                .andExpect(jsonPath("$[0].price").value(10.0))
                .andExpect(jsonPath("$[0].cartId").value(cartId.toString()))
                .andExpect(jsonPath("$[0].itemId").value(itemId.toString()));

        verify(cartRestService, times(1)).getActiveCartByUserId(userId);
    }

    @Test
    void testGetActiveCartByUserId_NoActiveCart() throws Exception {
        UUID userId = UUID.randomUUID();

        when(cartRestService.getActiveCartByUserId(userId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/cart/carts/active/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("There is currently no active cart."));

        verify(cartRestService, times(1)).getActiveCartByUserId(userId);
    }

}
