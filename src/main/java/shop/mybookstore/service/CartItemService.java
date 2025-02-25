package shop.mybookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mybookstore.entity.Book;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.entity.CartItem;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.repository.BookRepository;
import shop.mybookstore.repository.CartItemRepository;
import shop.mybookstore.repository.CartRepository;


@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final CartService cartService;

    public Cart addBookToCart(Long cartId, Long bookId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        CartItem cartItem = cart.getCartItems().stream()
                .filter(bookItem -> bookItem.getBook().getId().equals(bookId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getCartItemId() == null) {
            cartItem.setCart(cart);
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(book.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addToCart(cartItem);
        cartItemRepository.save(cartItem);
        return cartRepository.save(cart);
    }

    public void removeBookFromCart(Long cartId, Long bookId) {
        Cart cart = cartService.getCart(cartId);
        CartItem bookToRemove = getCartItem(cartId, bookId);
        cart.removeCartItem(bookToRemove);
        cartRepository.save(cart);
    }

    public Cart updateBookQuantity(Long cartId, Long bookId, int updatedQuantity) {
        Cart cart = cartService.getCart(cartId);
        CartItem booktoUpdate = getCartItem(cartId, bookId);
        booktoUpdate.setQuantity(updatedQuantity);
        double totalAmount = cart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getUnitPrice() * cartItem.getQuantity()).sum();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    public CartItem getCartItem(Long cartId, Long bookId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems()
                .stream().filter(book -> book.getBook().getId().equals(bookId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

}
