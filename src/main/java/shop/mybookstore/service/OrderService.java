package shop.mybookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mybookstore.entity.Book;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.entity.Order;
import shop.mybookstore.entity.OrderItem;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.repository.BookRepository;
import shop.mybookstore.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final BookRepository bookRepository;

    public Order placeOrder(Long userId){
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    public List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Book book = cartItem.getBook();
            book.setStock(book.getStock() - cartItem.getQuantity());
            bookRepository.save(book);
            return new OrderItem(
                    order,
                    book,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
    }

    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("Pending");
        order.setOrderDate(LocalDate.now());
        return order;
    }

    public Double calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .mapToDouble(item -> item.getPrice()* item.getQuantity())
                .reduce(0.0, Double::sum);
    }

    public Order getOrder(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public List<Order> getUserOrders(Long userId){
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().toList();
    }

}
