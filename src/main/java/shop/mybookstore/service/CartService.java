package shop.mybookstore.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.entity.CartItem;
import shop.mybookstore.repository.CartItemRepository;
import shop.mybookstore.repository.CartRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        Double totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    public Double getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    public void clearCart(Long cartId) {
        Cart cart = getCart(cartId);
        cart.clearAllItems();
        cartRepository.save(cart);
    }

    public Set<CartItem> deleteItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = getCart(cartId);
        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getCartItemId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));

        cart.removeCartItem(itemToRemove);
        cartRepository.save(cart);
        return cart.getCartItems();
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }


}
