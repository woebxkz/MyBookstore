package shop.mybookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.service.CartService;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(new ApiResponse("There are following books in your cart: ", cart));
    }

    @GetMapping("/{cartId}/totalAmount")
    public ResponseEntity<ApiResponse> getTotalAmount(Long cartId) {
        BigDecimal totalAmount = cartService.getCart(cartId).getTotalAmount();
        return ResponseEntity.ok(new ApiResponse("Your total amount is: ", totalAmount));
    }

    public void clearCart() {}
}
