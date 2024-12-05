package shop.mybookstore.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            return Collections.emptyList();
        } else {
            books.forEach(System.out::println);
            return books;
        }
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
        return bookRepository.findBooksByAuthorContainsIgnoreCase(author.trim());
    }

    Book getBookDetails (Long bookId) {
        return null;
    }

}
