package shop.mybookstore.controller.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mybookstore.entity.Book;
import shop.mybookstore.exception.BookNotFoundException;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.model.BookModel;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.service.BookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books/manage")
public class BookManagementController {

    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBook(@RequestBody BookModel bookModel) {
        try {
            Book addedBook = bookService.addBook(bookModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Following book successfully created: ", addedBook));
        } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error while adding book", e.getMessage()));
        }
    }

    @PutMapping("/{bookId}/update")
    public ResponseEntity<ApiResponse> updateBook(@RequestBody BookModel bookModel, @PathVariable Long bookId) {
        try {
            bookService.updateBook(bookModel, bookId);
            return ResponseEntity.ok(new ApiResponse("Book successfully updated", bookId));
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Book not found", bookId));
        }
    }

    @DeleteMapping("/{bookId}/delete")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse("Book successfully deleted", bookId));
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Book not found", bookId));
        }
    }
}
