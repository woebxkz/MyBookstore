package shop.mybookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import shop.mybookstore.IdConverter;
import shop.mybookstore.entity.*;
import shop.mybookstore.repository.CartRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void testClearCart_Success() {

        UUID uuid = UUID.randomUUID();
        Long cartId = IdConverter.convertUuidToLong(uuid);
        Long bookId1 = IdConverter.convertUuidToLong(uuid);
        Long bookId2 = IdConverter.convertUuidToLong(uuid);
        Long cartItemId1 = IdConverter.convertUuidToLong(uuid);
        Long cartItemId2 = IdConverter.convertUuidToLong(uuid);

        Cart cart = new Cart();
        cart.setId(cartId);
        Book book1 = new Book();
        book1.setId(bookId1);
        Book book2 = new Book();
        book2.setId(bookId2);
        CartItem cartItem1 = new CartItem(1, 12.99, book1, cart);
        cartItem1.setCartItemId(cartItemId1);
        CartItem cartItem2 = new CartItem(2, 15.99, book2, cart);
        cartItem2.setCartItemId(cartItemId2);
        cart.setCartItems(new HashSet<>(Set.of(cartItem1, cartItem2)));

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        cartService.clearCart(cartId);

        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    void testClearCart_CartNotFound() {

        UUID uuid = UUID.randomUUID();
        Long cartId = IdConverter.convertUuidToLong(uuid);

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> cartService.clearCart(cartId));
    }



}
