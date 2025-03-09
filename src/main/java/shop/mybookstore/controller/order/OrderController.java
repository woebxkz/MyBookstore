package shop.mybookstore.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mybookstore.entity.Order;
import shop.mybookstore.entity.User;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.service.OrderService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new-order")
    public ResponseEntity<ApiResponse> createOrder(@PathVariable Long userId){
        try{
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Order successful!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User not found", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Cannot place order", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order found", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Order not found", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
        try {
            Set<Order> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("User's orders found", orders));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found", e.getMessage()));
        }
    }

}
