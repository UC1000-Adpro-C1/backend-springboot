package id.ac.ui.cs.advprog.farrel.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import id.ac.ui.cs.advprog.farrel.model.CartItem;
import id.ac.ui.cs.advprog.farrel.service.CartItemRestService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CartItemRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartItemRestService cartItemRestService;

    @InjectMocks
    private CartItemRestController cartItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartItemController).build();
    }

    @Test
    void testCreateCartItem() throws Exception {
        CartItem cartItem = new CartItem("productId", 2, "cartId", 10.0);
        when(cartItemRestService.createCartItem(any(CartItem.class))).thenReturn(cartItem);

        mockMvc.perform(post("/api/cart-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\": \"productId\", \"quantity\": 2, \"cartId\": \"cartId\", \"price\": 10.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value("productId"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.cartId").value("cartId"))
                .andExpect(jsonPath("$.price").value(10.0));

        verify(cartItemRestService, times(1)).createCartItem(any(CartItem.class));
    }

    // Other test methods for increaseQuantity, decreaseQuantity, deleteCartItem, and updatePrice
    @Test
    void testIncreaseQuantity() throws Exception {
        int incrementQuantity = 2;
        CartItem cartItem = new CartItem("productId", 3, "cartId", 10.0);
        String itemId = cartItem.getItemId();
        CartItem updatedCartItem = new CartItem("productId", 5, "cartId", 10.0);
        updatedCartItem.setItemId(itemId);
        when(cartItemRestService.increaseQuantity(itemId, incrementQuantity)).thenReturn(updatedCartItem);

        mockMvc.perform(put("/api/cart-items/{itemId}/increase-quantity", itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"quantity\": " + incrementQuantity + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(itemId))
                .andExpect(jsonPath("$.quantity").value(5));

        verify(cartItemRestService, times(1)).increaseQuantity(itemId, incrementQuantity);
    }

    @Test
    void testDecreaseQuantity() throws Exception {
        int decrementQuantity = 1;
        CartItem cartItem = new CartItem("productId", 3, "cartId", 10.0);
        String itemId = cartItem.getItemId();
        CartItem updatedCartItem = new CartItem("productId", 2, "cartId", 10.0);
        updatedCartItem.setItemId(itemId);
        when(cartItemRestService.decreaseQuantity(itemId, decrementQuantity)).thenReturn(updatedCartItem);

        mockMvc.perform(put("/api/cart-items/{itemId}/decrease-quantity", itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"quantity\": " + decrementQuantity + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(itemId))
                .andExpect(jsonPath("$.quantity").value(2));

        verify(cartItemRestService, times(1)).decreaseQuantity(itemId, decrementQuantity);
    }

    @Test
    void testDeleteCartItem() throws Exception {
        String itemId = "itemId";

        mockMvc.perform(delete("/api/cart-items/{itemId}", itemId))
                .andExpect(status().isNoContent());

        verify(cartItemRestService, times(1)).deleteCartItem(itemId);
    }

    @Test
    void testUpdatePrice() throws Exception {
        double newPrice = 15.0;
        CartItem cartItem = new CartItem("productId", 2, "cartId", 10.0);
        String itemId = cartItem.getItemId();
        CartItem updatedCartItem = new CartItem("productId", 2, "cartId", newPrice);
        updatedCartItem.setItemId(itemId);
        when(cartItemRestService.updatePrice(itemId, newPrice)).thenReturn(updatedCartItem);

        mockMvc.perform(put("/api/cart-items/{itemId}/update-price", itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"price\": " + newPrice + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(itemId))
                .andExpect(jsonPath("$.price").value(newPrice));

        verify(cartItemRestService, times(1)).updatePrice(itemId, newPrice);
    }
}
