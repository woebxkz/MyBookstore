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

    private final BookService bookService;

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

    @PostMapping
    public void addBook(@RequestParam("title") String title,
                        @RequestParam("author") String author,
                        @RequestParam("category") String category,
                       // @RequestParam("user_id") long user_id,
                        @RequestParam(value = "file") Optional<MultipartFile> file) throws IOException {
        CategoryEnum categoryEnum = Enum.valueOf(CategoryEnum.class, category);
        bookService.save(new BookModel(title,author,categoryEnum,user_id), file);
    }

    @PostMapping("/{id}")
    public void updateBook(@PathVariable Long id,
                           @RequestParam("title") String title,
                           @RequestParam("author") String author,
                           @RequestParam("category") String category,
                           @RequestParam("user_id") long user_id,
                           @RequestParam(value="file", required = false) Optional<MultipartFile> file,
                           @RequestParam(value = "isChange", required = false) boolean isChange) throws IOException {
        CategoryEnum categoryEnum = Enum.valueOf(CategoryEnum.class, category);
        bookService.updateBook(id, new BookModel(title,author,categoryEnum,user_id), file, isChange);
    }
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

    @GetMapping("/user/{id}")
    public List<BookResponse> getUserBooks(@PathVariable Long id){
        return  bookService.getUserBooks(id);
    }

}
