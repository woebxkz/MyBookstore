package shop.mybookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mybookstore.entity.Book;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.entity.CartItem;
import shop.mybookstore.repository.BookRepository;
import shop.mybookstore.repository.CartItemRepository;
import shop.mybookstore.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartItemService {

    CartItemRepository cartItemRepository;
    CartRepository cartRepository;
    BookRepository bookRepository;

    public void addBookToCart(Long cartId, Long bookId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        CartItem cartItem = cart.getBooks().stream()
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
        cartRepository.save(cart);
    }

    public void removeBookFromCart() {}

    public void updateBookQuantity() {}


}
