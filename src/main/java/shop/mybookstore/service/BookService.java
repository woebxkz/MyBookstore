package shop.mybookstore.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shop.mybookstore.controller.BookController;
import shop.mybookstore.entity.Book;
import shop.mybookstore.repository.BookRepository;

import java.util.*;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

   public List<Book> getBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findBooksByTitleContainingIgnoreCase(title);
    }

    public List<Book> getBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findBooksByAuthorIgnoreCase(author);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + id + "does not exist"));

        bookRepository.delete(book);
    }
}
