package be.bookstore_backend.service;

import be.bookstore_backend.model.CartItem;
import java.util.List;

public interface CartService {
    List<CartItem> getCart(String username);
    CartItem addItemToCart(String username, Long bookId, int quantity);
    void removeItemFromCart(Long cartItemId);
    CartItem updateItemQuantity(Long cartItemId, int quantity);
    void clearCart(String username);
    void checkout(String username);
}
