package shop.mybookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mybookstore.entity.Book;
import shop.mybookstore.exception.BookNotFoundException;
import shop.mybookstore.model.BookModel;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(new ApiResponse("All books available: ",books));
    }

    @GetMapping("/search/by-id/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/search/by-author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/search/by-title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/search/by-author-and-title/{author}&{title}")
    public List<Book> getBooksByAuthorAndTitle(@PathVariable String author, String title) {
        return bookService.getBooksByAuthorAndTitle(author, title);
    }

    @GetMapping("/search/by-category/{category}")
    public List<Book> getBooksByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }


    @DeleteMapping("/{bookId}/delete")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.status(FOUND).body(new ApiResponse("Book successfully deleted", id));
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Book not found", id));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBook(@RequestBody BookModel bookModel) {
        Book addedBook = bookService.addBook(bookModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Following book successfully created: ", addedBook));
    }

    @PutMapping("/{bookId}/update")
    public ResponseEntity<ApiResponse> updateBook(@RequestBody BookModel bookModel, @PathVariable Long id) {
        try {
            bookService.updateBook(bookModel, id);
            return ResponseEntity.ok(new ApiResponse("Book successfully updated", id));
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Book not found", id));
        }

    }
}
