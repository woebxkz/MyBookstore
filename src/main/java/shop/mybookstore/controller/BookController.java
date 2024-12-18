package shop.mybookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mybookstore.model.BookModel;
import shop.mybookstore.model.response.BookResponse;
import shop.mybookstore.service.BookService;
import shop.mybookstore.entity.Book;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookModel bookModel) {
        BookResponse createdBook = bookService.createBook(bookModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }


}
