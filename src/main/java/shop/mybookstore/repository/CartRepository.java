package shop.mybookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.mybookstore.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
