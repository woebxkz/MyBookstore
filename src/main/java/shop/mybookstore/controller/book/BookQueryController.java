package shop.mybookstore.controller.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.mybookstore.entity.Book;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books/search")
public class BookQueryController {

    private final BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(new ApiResponse("All books available: ", books));
    }

    @GetMapping("/by-id/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/by-author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/by-title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/by-author-and-title/{author}&{title}")
    public List<Book> getBooksByAuthorAndTitle(@PathVariable String author, @PathVariable String title) {
        return bookService.getBooksByAuthorAndTitle(author, title);
    }

    @GetMapping("/by-category/{category}")
    public List<Book> getBooksByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }
}
