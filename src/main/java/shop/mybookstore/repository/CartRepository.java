package shop.mybookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.mybookstore.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
