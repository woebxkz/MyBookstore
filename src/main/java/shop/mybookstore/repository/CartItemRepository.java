package shop.mybookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.entity.CartItem;
import shop.mybookstore.service.CartService;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

/*    public void addBookToCart(Long cartId, Long bookId, int quantity) {
    }*/

}
