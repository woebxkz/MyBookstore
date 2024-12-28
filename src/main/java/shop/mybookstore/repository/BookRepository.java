package shop.mybookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.mybookstore.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findByAuthor(String author);

    List<Book> findByTitle(String title);

    List<Book> findByAuthorAndTitle(String author, String title);

    List<Book> findByCategory(String category);
}
