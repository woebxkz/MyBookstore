package shop.mybookstore.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shop.mybookstore.entity.Book;
import shop.mybookstore.model.BookModel;
import shop.mybookstore.model.response.BookResponse;
import shop.mybookstore.repository.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

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

    public BookResponse createBook(BookModel bookModel) {
        Book book = new Book();
        book.setTitle(bookModel.getTitle());
        book.setAuthor(bookModel.getAuthor());
        book.setPrice(bookModel.getPrice());
        book.setStock(bookModel.getStock());
        book.setCategory(bookModel.getCategory());
        book.setPublishedDate(bookModel.getPublishedDate());
        book.setPublisher(bookModel.getPublisher());

        Book savedBook = bookRepository.save(book);

        return new BookResponse(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getCategory(),
                savedBook.getPrice(),
                savedBook.getStock(),
                savedBook.getPublishedDate(),
                savedBook.getPublisher()
        );
    }


}
