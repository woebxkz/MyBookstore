package shop.mybookstore.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.repository.CartItemRepository;
import shop.mybookstore.repository.CartRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {

    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    public void clearCart(Long cartId) {
        Cart cart = getCart(cartId);
        cartItemRepository.deleteAllByCartId(cartId);
        cart.getBooks().clear();
        cartRepository.deleteById(cartId);
    }


}
