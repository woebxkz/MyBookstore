package shop.mybookstore.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shop.mybookstore.controller.BookController;
import shop.mybookstore.entity.Book;
import shop.mybookstore.model.BookModel;
import shop.mybookstore.model.response.BookResponse;
import shop.mybookstore.repository.BookRepository;

import java.util.*;

@Service
public class BookService {

    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String getAllBooks() {
        return bookRepository.findAll().toString();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

   public List<Book> getBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findBooksByTitleContainingIgnoreCase(title);
    }

    public List<Book> getBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findBooksByAuthorIgnoreCase(author);
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
