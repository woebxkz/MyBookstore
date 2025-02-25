package shop.mybookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shop.mybookstore.entity.Book;
import shop.mybookstore.exception.BookNotFoundException;
import shop.mybookstore.model.BookModel;
import shop.mybookstore.repository.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findByTitle(title);
    }

    public List<Book> getBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findByAuthor(author);
    }


    public List<Book> getBooksByAuthorAndTitle(String author, String title) {
        if (author == null || author.trim().isEmpty()) {
            return Collections.emptyList();
        } else if (title == null || title.trim().isEmpty()) {
            return Collections.emptyList();
        } else return bookRepository.findByAuthorAndTitle(author, title);
    }

    public List<Book> getBooksByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findByCategory(category);
    }


    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + bookId + " does not exist"));

        bookRepository.delete(book);
    }

    public Book addBook(BookModel bookModel) {
        Book book = new Book(
                bookModel.getTitle(),
                bookModel.getAuthor(),
                bookModel.getPrice(),
                bookModel.getStock(),
                bookModel.getCategory(),
                bookModel.getPublishedDate(),
                bookModel.getPublisher()
        );
        return bookRepository.save(book);
    }

    public Book updateBook(BookModel bookModel, Long bookId){
        return bookRepository.findById(bookId)
                .map(existingBook -> updateExistingBook(existingBook, bookModel))
                .map(bookRepository::save)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    private Book updateExistingBook(Book existingBook, BookModel bookModel) {
        existingBook.setTitle(bookModel.getTitle());
        existingBook.setAuthor(bookModel.getAuthor());
        existingBook.setCategory(bookModel.getCategory());
        existingBook.setPrice(bookModel.getPrice());
        existingBook.setStock(bookModel.getStock());
        existingBook.setPublishedDate(bookModel.getPublishedDate());
        existingBook.setPublisher(bookModel.getPublisher());

        return existingBook;
    }

}
