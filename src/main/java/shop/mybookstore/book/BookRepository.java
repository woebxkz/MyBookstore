package shop.mybookstore.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {

    List<Book> findBooksByTitleContainsIgnoreCase(String title);
    List<Book> findBooksByAuthorContainsIgnoreCase(String author);
    List<Book> findBooksByCategory(String category);

}
