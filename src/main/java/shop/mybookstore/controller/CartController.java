package shop.mybookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.service.CartService;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/total-amount")
    public ResponseEntity<ApiResponse> getTotalAmount(Long cartId) {
        BigDecimal totalAmount = cartService.getTotalPrice(cartId);
        return ResponseEntity.ok(new ApiResponse("Your total amount is: ", totalAmount));
    }

    @PutMapping("/clear")
    public ResponseEntity<ApiResponse> clearCart(Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new ApiResponse("Cart has been cleared", cartId));
    }

}
