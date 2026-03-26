package be.bookstore_backend.service;

import be.bookstore_backend.exception.ResourceNotFoundException;
import be.bookstore_backend.model.Book;
import be.bookstore_backend.model.CartItem;
import be.bookstore_backend.model.User;
import be.bookstore_backend.repository.BookRepository;
import be.bookstore_backend.repository.CartItemRepository;
import be.bookstore_backend.repository.OrderRepository;
import be.bookstore_backend.repository.UserRepository;
import be.bookstore_backend.service.impl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    public void testAddItemToCart_HappyPath() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        
        Book book = new Book();
        book.setId(1L);
        book.setPrice(java.math.BigDecimal.TEN);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(cartItemRepository.findByUserAndBook(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(cartItemRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        CartItem item = cartService.addItemToCart(username, 1L, 2);
        
        assertNotNull(item);
        assertEquals(2, item.getQuantity());
        assertEquals(book, item.getBook());
        assertEquals(user, item.getUser());
    }

    @Test
    public void testAddItemToCart_UserNotFound() {
        String username = "testuser";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            cartService.addItemToCart(username, 1L, 2);
        });
    }

    @Test
    public void testAddItemToCart_BookNotFound() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            cartService.addItemToCart(username, 1L, 2);
        });
    }

    @Test
    public void testRemoveItemFromCart_ItemNotFound() {
        Mockito.when(cartItemRepository.existsById(1L)).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> {
            cartService.removeItemFromCart(1L);
        });
    }
}
