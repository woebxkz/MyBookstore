package shop.mybookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.mybookstore.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

}
