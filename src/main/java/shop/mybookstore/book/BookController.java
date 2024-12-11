package shop.mybookstore.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * GET /books - Retrieves all books.
     * @return ResponseEntity containing the list of books and HTTP status.
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();

        // Check if the list is empty and return the appropriate response
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build(); // No Content - HTTP 204
        } else {
            return ResponseEntity.ok(books); // OK with the list - HTTP 200
        }
    }

    /**
     * GET /books/{id} - Retrieves all books.
     * @param 'id' The ID of the book to retrieve.
     * @return ResponseEntity containing the list of books and HTTP status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId) {
        Optional<Book> book = bookService.getBookById(bookId);

        if (book.isPresent()) {
            return ResponseEntity.ok(book.get()); // OK with the list - HTTP 200
        } else {
            return ResponseEntity.notFound().build(); //NOT FOUND - 404
        }
    }

}
