package shop.mybookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shop.mybookstore.entity.Book;
import shop.mybookstore.exception.BookNotFoundException;
import shop.mybookstore.model.BookModel;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.response.BookResponse;
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


    //only for Admin - for later
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + id + "does not exist"));

        bookRepository.delete(book);
    }

    public Book addBook(BookModel bookModel) {
        Book book = new Book();
        book.setTitle(bookModel.getTitle());
        book.setAuthor(bookModel.getAuthor());
        book.setCategory(bookModel.getCategory());
        book.setPrice(bookModel.getPrice());
        book.setPublishedDate(bookModel.getPublishedDate());
        book.setPublisher(bookModel.getPublisher());

        Book savedBook = bookRepository.save(book);

        return savedBook;
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


    public BookModel convertToModel(Book book) {
        BookModel bookModel = new BookModel();
        bookModel.setId(book.getId());
        bookModel.setTitle(book.getTitle());
        bookModel.setAuthor(book.getAuthor());
        bookModel.setCategory(book.getCategory());
        bookModel.setPrice(book.getPrice());
        bookModel.setStock(book.getStock());
        bookModel.setPublishedDate(book.getPublishedDate());
        bookModel.setPublisher(book.getPublisher());

        return bookModel;
    }

    public List<BookModel> getConvertedBooks(List<Book> books) {
        return books.stream().map(this::convertToModel).toList();
    }


}
