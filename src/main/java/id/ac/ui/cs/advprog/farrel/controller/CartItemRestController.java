package id.ac.ui.cs.advprog.farrel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import id.ac.ui.cs.advprog.farrel.model.CartItem;
import id.ac.ui.cs.advprog.farrel.service.CartItemRestService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemRestController {

    private CartItemRestService cartItemRestService;

    @Autowired
    public CartItemRestController(CartItemRestService cartItemRestService) {
        this.cartItemRestService = cartItemRestService;
    }

    @PutMapping("/{itemId}/update-quantity")
    public ResponseEntity<CartItem> updateQuantity(@PathVariable UUID itemId,
            @RequestBody Map<String, Integer> requestBody) {
        int newQuantity = requestBody.get("quantity");
        CartItem updatedCartItem = cartItemRestService.updateQuantity(itemId, newQuantity);
        return ResponseEntity.ok(updatedCartItem);
    }
}
