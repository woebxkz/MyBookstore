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

    public Cart addNewBookToCart(Long cartId, Long bookId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getPrice() == null) {
            throw new RuntimeException("Book price is not set");
        }

        boolean bookExists = cart.getCartItems().stream()
                .anyMatch(item -> bookId.equals(item.getBook().getId()));

        if (!bookExists) {
            CartItem newItem = new CartItem(1, book.getPrice(), book, cart);
            cart.addToCart(newItem);
            cartItemRepository.save(newItem);
            cartRepository.save(cart);
        }

        return cart;
    }


    public Cart removeBookFromCart(Long cartId, Long bookId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem bookToRemove = getCartItem(cartId, bookId);
        cart.removeCartItem(bookToRemove);
        cartRepository.save(cart);
        return cart;
    }

    public Cart updateBookQuantity(Long cartId, Long bookId, int updatedQuantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> bookId.equals(item.getBook().getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found in cart"));

        cartItem.setQuantity(updatedQuantity);
        cartItem.setTotalPrice();

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

        return cart;
    }


    public CartItem getCartItem(Long cartId, Long bookId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cart.getCartItems()
                .stream().filter(book -> book.getBook().getId().equals(bookId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

}
