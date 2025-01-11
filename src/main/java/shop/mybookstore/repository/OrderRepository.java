package shop.mybookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.mybookstore.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
