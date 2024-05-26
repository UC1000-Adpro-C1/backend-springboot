package id.ac.ui.cs.advprog.farrel.controller;

import id.ac.ui.cs.advprog.farrel.dto.CartItemRequest;
import id.ac.ui.cs.advprog.farrel.dto.CartResponse;
import id.ac.ui.cs.advprog.farrel.model.Cart;
import id.ac.ui.cs.advprog.farrel.model.CartItem;
import id.ac.ui.cs.advprog.farrel.model.Payment;
import id.ac.ui.cs.advprog.farrel.service.CartRestService;
import id.ac.ui.cs.advprog.farrel.service.CartItemRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartRestController {

    private final CartRestService cartRestService;
    private final CartItemRestService cartItemRestService;

    @Autowired
    public CartRestController(CartRestService cartRestService, CartItemRestService cartItemRestService) {
        this.cartRestService = cartRestService;
        this.cartItemRestService = cartItemRestService;
    }

    @PostMapping("/carts/items")
    public ResponseEntity<CartResponse> addProductToCart(@RequestBody CartItemRequest cartItemRequest) {
        UUID userId = cartItemRequest.getUserId();
        Optional<Cart> optionalCart = cartRestService.getActiveCartByUserId(userId);
        
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = cartRestService.createCart();
            cartRestService.setUserIdForCart(cart.getCartId(), userId);
        }

        CartItem cartItem = new CartItem(cartItemRequest.getProductId(), cartItemRequest.getQuantity(), cart.getCartId(), cartItemRequest.getPrice());
        cartItem = cartItemRestService.createCartItem(cartItem);
        cartRestService.addItemToCart(cart.getCartId(), cartItem);

        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getCartId());
        cartResponse.setUserId(cart.getUserId());
        cartResponse.setItemId(cartItem.getItemId());

        return ResponseEntity.ok(cartResponse);
    }
    
    @DeleteMapping("/carts/{cartId}/items/{itemId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable UUID cartId, @PathVariable UUID itemId) {
        cartRestService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/carts/active/{userId}")
    public ResponseEntity<?> getActiveCartByUserId(@PathVariable UUID userId) {
        Optional<Cart> optionalCart = cartRestService.getActiveCartByUserId(userId);

        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            if (cart.getItems().isEmpty()) {
                return ResponseEntity.ok("There is currently no active cart.");
            } else {
                return ResponseEntity.ok(cart.getItems());
            }
        } else {
            return ResponseEntity.ok("There is currently no active cart.");
        }
    }

    @PostMapping("/carts/{cartId}/checkout")
    public ResponseEntity<Payment> checkoutCart(@PathVariable UUID cartId, @RequestBody Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        Payment createdPayment = cartRestService.checkoutCart(cartId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }
}
