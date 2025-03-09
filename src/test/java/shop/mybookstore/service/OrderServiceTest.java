package shop.mybookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.mybookstore.IdConverter;
import shop.mybookstore.entity.*;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.repository.BookRepository;
import shop.mybookstore.repository.CartRepository;
import shop.mybookstore.repository.OrderRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartService cartService;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private OrderService orderService;


    @Test
    public void placeOrderTest_Success() {

        UUID uuid = UUID.randomUUID();
        Long orderId = IdConverter.convertUuidToLong(uuid);
        Long cartId = IdConverter.convertUuidToLong(uuid);
        Long bookId = IdConverter.convertUuidToLong(uuid);
        Long cartItemId = IdConverter.convertUuidToLong(uuid);
        Long userId = IdConverter.convertUuidToLong(uuid);

        User user = new User("jdoe", "John", "Doe", "default_password", "jdoe@example.com", RoleEnum.CUSTOMER);
        user.setId(userId);

        Order order = new Order();
        order.setOrderId(orderId);

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUser(user);
        user.setCart(cart);

        Book book = new Book();
        book.setId(bookId);
        book.setStock(4);

        CartItem cartItem = new CartItem(1, 12.99, book, cart);
        cartItem.setCartItemId(cartItemId);
        cart.addToCart(cartItem);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setOrderId(orderId);
            return savedOrder;
        });
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        orderService.placeOrder(userId);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        Order savedOrder = orderCaptor.getValue();

        verify(cartService).clearCart(cartId);
        assertEquals(1, savedOrder.getOrderItems().size());
        assertEquals("Pending", savedOrder.getStatus());
    }

    @Test
    public void insufficientStockTest() {

        UUID uuid = UUID.randomUUID();
        Long cartId = IdConverter.convertUuidToLong(uuid);
        Long bookId = IdConverter.convertUuidToLong(uuid);
        Long cartItemId = IdConverter.convertUuidToLong(uuid);
        Long userId = IdConverter.convertUuidToLong(uuid);

        User user = new User("jdoe", "John", "Doe", "default_password", "jdoe@example.com", RoleEnum.CUSTOMER);
        user.setId(userId);

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUser(user);
        user.setCart(cart);

        Book book = new Book();
        book.setId(bookId);
        book.setStock(0);

        CartItem cartItem = new CartItem(1, 14.99, book, cart);
        cartItem.setCartItemId(cartItemId);
        cart.addToCart(cartItem);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        assertThrows(IllegalStateException.class, () -> orderService.placeOrder(userId));

    }


    @Test
    public void noCartFoundForUserTest() {

        UUID uuid = UUID.randomUUID();
        Long userId = IdConverter.convertUuidToLong(uuid);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.placeOrder(userId));

    }

}

