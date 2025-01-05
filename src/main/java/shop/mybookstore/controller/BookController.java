package shop.mybookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mybookstore.entity.Book;
import shop.mybookstore.model.BookModel;
import shop.mybookstore.response.BookResponse;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/books")
public class BookController {

    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<BookResponse> getAllBooks() {
        List <Book> books = bookService.getAllBooks();
        List <BookModel> convertedBooks = bookService.getConvertedBooks(books);
        return ResponseEntity.ok(new BookResponse(convertedBooks));
    }

    @GetMapping("/search/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/search/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/search/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/search/{author}&{title}")
    public List<Book> getBooksByAuthorAndTitle(@PathVariable String author, String title) {
        return bookService.getBooksByAuthorAndTitle(author, title);
    }

    @GetMapping("/search/{category}")
    public List<Book> getBooksByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.status(FOUND).body(new ApiResponse("Book successfully deleted", id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Book not found", id));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBook(@RequestBody BookModel bookModel) {
        ApiResponse addedBook = bookService.addBook(bookModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Following book successfully created: ", addedBook));
    }
}
