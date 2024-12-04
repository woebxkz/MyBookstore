package shop.mybookstore.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findBooksByTitleContainingIgnoreCase(String keyword);
    List<Book> findBooksByAuthorContainsIgnoreCase(String author);
    List<Book> findBooksByPriceBetween(Double minPrice, Double maxPrice);
    List<Book> findBooksByCategoryName(String categoryName);

}
