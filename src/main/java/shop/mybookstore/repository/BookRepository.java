package shop.mybookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.mybookstore.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    @Query(value = "SELECT * FROM book WHERE LOWER(author) = LOWER(?1)", nativeQuery = true)
    List<Book> findBooksByAuthorIgnoreCase(String author);

    @Query(value = "SELECT * FROM book WHERE LOWER(title) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
    List<Book> findBooksByTitleContainingIgnoreCase(String title);

}
