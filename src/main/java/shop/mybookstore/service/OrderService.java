package shop.mybookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.entity.CartItem;
import shop.mybookstore.entity.Order;
import shop.mybookstore.entity.OrderItem;
import shop.mybookstore.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    OrderRepository orderRepository;
    CartService cartService;

    public void placeOrder(Long userId){
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);


    }

    public void createOrderItems(Order order, Cart cart) {}

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

    public void getOrder(){}

    public void getUserOrders(){}

}
