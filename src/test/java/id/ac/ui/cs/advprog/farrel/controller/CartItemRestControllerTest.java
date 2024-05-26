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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

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

    // Other test methods for increaseQuantity, decreaseQuantity, deleteCartItem, and updatePrice
    @Test
    void testUpdateQuantity() throws Exception {
        UUID cartId = UUID.randomUUID();
        int newQuantity = 5;
        CartItem updatedCartItem = new CartItem("productId", newQuantity, cartId, 10.0);
        
        when(cartItemRestService.updateQuantity(any(UUID.class), eq(newQuantity))).thenReturn(updatedCartItem);

        mockMvc.perform(put("/api/cart-items/{itemId}/update-quantity", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"quantity\": " + newQuantity + "}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.quantity").value(newQuantity));

        verify(cartItemRestService, times(1)).updateQuantity(any(UUID.class), eq(newQuantity));
    }

}
