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
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    OrderRepository orderRepository;
    CartService cartService;
    BookRepository bookRepository;

    public void placeOrder(Long userId){
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);


    }

    public List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getBooks().stream().map(cartItem -> {
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

    public BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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
