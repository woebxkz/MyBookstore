package shop.mybookstore.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import shop.mybookstore.entity.Book;
import shop.mybookstore.exception.BookNotFoundException;
import shop.mybookstore.model.BookModel;
import shop.mybookstore.repository.BookRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void testAddBook() {

        BookModel bookModel = new BookModel("Title", "Author", 29.98, 5, "Category", LocalDate.of(2000, 12, 12), "Publisher");
        Book bookToReturn = new Book("Title", "Author", 29.98, 5, "Category", LocalDate.of(2000, 12, 12), "Publisher");

        when(bookRepository.save(any(Book.class))).thenReturn(bookToReturn);

        Book addedBook = bookService.addBook(bookModel);

        assertNotNull(addedBook, "The saved book should not be null");
        assertEquals("Title", addedBook.getTitle());
        assertEquals("Author", addedBook.getAuthor());
    }

    @Test
    void testUpdateBook_Success() {

        Long bookId = 1L;
        BookModel bookModel = new BookModel("Updated Title", "Updated Author", 35.99, 10, "Updated Category", LocalDate.of(2021, 1, 1), "Updated Publisher");
        Book existingBook = new Book("Original Title", "Original Author", 29.99, 5, "Original Category", LocalDate.of(2000, 12, 12), "Original Publisher");
        Book updatedBook = new Book("Updated Title", "Updated Author", 35.99, 10, "Updated Category", LocalDate.of(2021, 1, 1), "Updated Publisher");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(bookModel, bookId);

        assertNotNull(result);
        assertEquals("Updated Author", result.getAuthor());
        assertEquals("Updated Title", result.getTitle());
        assertEquals(35.99, result.getPrice());

    }

    @Test
    void testUpdateBook_NotFound() {

        Long bookId = 1L;
        BookModel bookModel = new BookModel("Updated Title", "Updated Author", 35.99, 10, "Updated Category", LocalDate.of(2021, 1, 1), "Updated Publisher");

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(bookModel, bookId));

    }

    @Test
    void testDeleteBook_Success() {

        Long bookId = 2L;
        Book bookToDelete = new Book("Title", "Author", 29.99, 5, "Category", LocalDate.of(2000, 12, 12), "Publisher");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookToDelete));

        bookService.deleteBook(bookId);

        verify(bookRepository).findById(bookId);
        verify(bookRepository).delete(bookToDelete);

    }

    @Test
    void testDeleteBook_NotFound() {

        Long bookId = 2L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> bookService.deleteBook(bookId));


    }
}
