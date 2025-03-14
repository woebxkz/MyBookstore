package shop.mybookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.mybookstore.IdConverter;
import shop.mybookstore.entity.Book;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.entity.CartItem;
import shop.mybookstore.repository.BookRepository;
import shop.mybookstore.repository.CartItemRepository;
import shop.mybookstore.repository.CartRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    CartItemRepository cartItemRepository;

    @InjectMocks
    CartItemService cartItemService;

    @Test
    public void addBookToCartTest_Success() {

        UUID uuid = UUID.randomUUID();
        Long cartId = IdConverter.convertUuidToLong(uuid);
        Long bookId = IdConverter.convertUuidToLong(uuid);
        Long cartItemId = IdConverter.convertUuidToLong(uuid);

        int quantity = 1;
        Double price = 20.99;
        Book book = new Book();
        book.setId(bookId);
        Cart cart = new Cart();
        cart.setId(cartId);
        book.setPrice(price);
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(cartItemId);
        cartItem.setUnitPrice(price);

        Mockito.lenient().when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItem));
        Mockito.lenient().when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        Mockito.lenient().when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Cart updatedCart = cartItemService.addNewBookToCart(cartId, bookId);

        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getCartItems().size());
        CartItem addedItem = updatedCart.getCartItems().iterator().next();
        assertEquals(bookId, addedItem.getBook().getId());
        assertEquals(quantity, addedItem.getQuantity());
        assertEquals(book.getPrice()*quantity, updatedCart.getTotalAmount());
    }

    @Test
    public void addBookToCartTest_NotFound() {

        UUID uuid = UUID.randomUUID();
        Long cartId = IdConverter.convertUuidToLong(uuid);
        Long bookId = IdConverter.convertUuidToLong(uuid);
        Long cartItemId = IdConverter.convertUuidToLong(uuid);

        Mockito.lenient().when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.empty());
        Mockito.lenient().when(cartRepository.findById(cartId)).thenReturn(Optional.empty());
        Mockito.lenient().when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cartItemService.addNewBookToCart(cartId, bookId));
    }

    @Test
    public void updateBookQuantityTest_Success() {

        UUID uuid = UUID.randomUUID();
        Long cartId = IdConverter.convertUuidToLong(uuid);
        Long bookId = IdConverter.convertUuidToLong(uuid);

        Book book = new Book();
        book.setId(bookId);
        book.setPrice(20.0);

        Cart cart = new Cart();
        cart.setId(cartId);

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setCart(cart);
        cartItem.setQuantity(1);
        cartItem.setUnitPrice(book.getPrice());
        cart.addToCart(cartItem);

        Mockito.lenient().when(cartItemRepository.findById(cartItem.getCartItemId())).thenReturn(Optional.of(cartItem));
        Mockito.lenient().when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        Mockito.lenient().when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        Cart updatedCart = cartItemService.updateBookQuantity(cart.getId(), book.getId(), 2);

        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getCartItems().size());
        CartItem addedItem = updatedCart.getCartItems().iterator().next();
        assertEquals(book.getId(), addedItem.getBook().getId());
        assertEquals(2, addedItem.getQuantity());
    }

    @Test
    public void updateBookQuantityTest_NotFound() {

        UUID uuid = UUID.randomUUID();
        Long cartId = IdConverter.convertUuidToLong(uuid);
        Long bookId = IdConverter.convertUuidToLong(uuid);
        Long cartItemId = IdConverter.convertUuidToLong(uuid);

        Mockito.lenient().when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.empty());
        Mockito.lenient().when(cartRepository.findById(cartId)).thenReturn(Optional.empty());
        Mockito.lenient().when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cartItemService.updateBookQuantity(cartId, bookId, 2));
    }

    @Test
    public void removeBookFromCart_Success() {

        UUID uuid = UUID.randomUUID();
        Long cartId = IdConverter.convertUuidToLong(uuid);
        Long bookId = IdConverter.convertUuidToLong(uuid);
        Long cartItemId = IdConverter.convertUuidToLong(uuid);

        Cart cart = new Cart();
        cart.setId(cartId);
        Book book = new Book();
        book.setId(bookId);
        book.setPrice(20.0);
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(cartItemId);
        cartItem.setCart(cart);
        cartItem.setBook(book);
        cartItem.setUnitPrice(20.99);
        cart.addToCart(cartItem);

        Mockito.lenient().when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItem));
        Mockito.lenient().when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        Mockito.lenient().when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Cart updatedCart = cartItemService.removeBookFromCart(cartId, bookId);

        assertEquals(0, updatedCart.getCartItems().size());

    }

    @Test
    public void removeBookFromCart_NotFound() {

        UUID uuid = UUID.randomUUID();
        Long cartId = IdConverter.convertUuidToLong(uuid);
        Long bookId = IdConverter.convertUuidToLong(uuid);
        Long cartItemId = IdConverter.convertUuidToLong(uuid);

        Mockito.lenient().when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.empty());
        Mockito.lenient().when(cartRepository.findById(cartId)).thenReturn(Optional.empty());
        Mockito.lenient().when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cartItemService.removeBookFromCart(cartId, bookId));
    }

}
