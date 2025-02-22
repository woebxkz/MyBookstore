package shop.mybookstore.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.mybookstore.entity.Book;
import shop.mybookstore.model.BookModel;
import shop.mybookstore.repository.BookRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


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
}
