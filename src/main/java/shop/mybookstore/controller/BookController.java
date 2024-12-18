package shop.mybookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.mybookstore.service.BookService;
import shop.mybookstore.entity.Book;

import java.io.IOException;
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


}
