package shop.mybookstore.service;

import org.springframework.stereotype.Service;
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

   public List<Book> searchBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findBooksByTitleContainingIgnoreCase(title.trim());
    }

    public List<Book> searchBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.getAuthorIgnoreCase(author);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    Book getBookDetails (BookController bookId) {
        return null;
    }

}
