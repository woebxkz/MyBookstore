package shop.mybookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mybookstore.entity.*;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.repository.BookRepository;
import shop.mybookstore.repository.CartRepository;
import shop.mybookstore.repository.OrderRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final BookRepository bookRepository;

    public Order placeOrder(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user ID: " + userId));

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cannot place an order with an empty cart.");
        }

        Order order = createOrderFromCart(cart);
        Set<OrderItem> orderItems = convertCartItemsToOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        order.setStatus("Pending");
        order.setOrderDate(LocalDate.now());

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    public Set<OrderItem> convertCartItemsToOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Book book = cartItem.getBook();
            int requestedQuantity = cartItem.getQuantity();

            if (book.getStock() < requestedQuantity) {
                throw new IllegalStateException("Insufficient stock for book: " + book.getTitle());
            }

            book.setStock(book.getStock() - requestedQuantity);
            bookRepository.save(book);

            return new OrderItem(order, book, requestedQuantity, cartItem.getUnitPrice());
        }).collect(Collectors.toSet());
    }

    public Order createOrderFromCart(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        return order;
    }

    public Double calculateTotalAmount(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public Order getOrder(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public Set<Order> getUserOrders(Long userId){
        return orderRepository.findByUserId(userId);
    }

}
