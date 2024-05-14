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

    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) {
        CartItem createdCartItem = cartItemRestService.createCartItem(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCartItem);
    }

    @PutMapping("/{itemId}/increase-quantity")
    public ResponseEntity<CartItem> increaseQuantity(@PathVariable UUID itemId, @RequestBody Map<String, Integer> requestBody) {
        int incrementQuantity = requestBody.get("quantity");
        CartItem updatedCartItem = cartItemRestService.increaseQuantity(itemId, incrementQuantity);
        return ResponseEntity.ok(updatedCartItem);
    }

    @PutMapping("/{itemId}/decrease-quantity")
    public ResponseEntity<CartItem> decreaseQuantity(@PathVariable UUID itemId, @RequestBody Map<String, Integer> requestBody) {
        int decrementQuantity = requestBody.get("quantity");
        CartItem updatedCartItem = cartItemRestService.decreaseQuantity(itemId, decrementQuantity);
        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable UUID itemId) {
        cartItemRestService.deleteCartItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{itemId}/update-price")
    public ResponseEntity<CartItem> updatePrice(@PathVariable UUID itemId, @RequestBody Map<String, Double> requestBody) {
        double newPrice = requestBody.get("price");
        CartItem updatedCartItem = cartItemRestService.updatePrice(itemId, newPrice);
        return ResponseEntity.ok(updatedCartItem);
    }
}
