package be.bookstore_backend.controller;

import be.bookstore_backend.model.CartItem;
import be.bookstore_backend.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<CartItem> getCart(Authentication authentication) {
        return cartService.getCart(authentication.getName());
    }

    @PostMapping
    public ResponseEntity<CartItem> addItemToCart(Authentication authentication, @Valid @RequestBody CartItemRequest request) {
        CartItem item = cartService.addItemToCart(authentication.getName(), request.getBookId(), request.getQuantity());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<CartItem> updateCartItemQuantity(@PathVariable Long itemId, @Valid @RequestBody UpdateQuantityRequest request) {
        CartItem item = cartService.updateItemQuantity(itemId, request.getQuantity());
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long itemId) {
        cartService.removeItemFromCart(itemId);
        return ResponseEntity.ok("Item successfully removed from cart");
    }

    @DeleteMapping
    public ResponseEntity<String> clearCart(Authentication authentication) {
        cartService.clearCart(authentication.getName());
        return ResponseEntity.ok("Cart successfully cleared");
    }
    
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(Authentication authentication) {
        cartService.checkout(authentication.getName());
        return ResponseEntity.ok("Order placed successfully!");
    }

    @Data
    public static class CartItemRequest {
        @NotNull(message = "bookId is required when adding to cart")
        private Long bookId;
        
        @Min(value = 1, message = "Quantity must be at least 1")
        private int quantity;
    }

    @Data
    public static class UpdateQuantityRequest {
        @Min(value = 1, message = "Quantity must be at least 1")
        private int quantity;
    }
}
