package be.bookstore_backend.service;

import be.bookstore_backend.model.Book;
import be.bookstore_backend.repository.BookRepository;
import be.bookstore_backend.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetAllBooks() {
        Mockito.when(bookRepository.findAll()).thenReturn(Collections.emptyList());
        List<Book> books = bookService.getAllBooks();
        assertTrue(books.isEmpty());
    }

    @Test
    public void testGetBookById_HappyPath() {
        Book book = new Book();
        book.setId(1L);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        
        Optional<Book> found = bookService.getBookById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    public void testGetBookById_NotFound() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        
        Optional<Book> found = bookService.getBookById(1L);
        assertFalse(found.isPresent());
    }
}
