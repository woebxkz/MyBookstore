package shop.mybookstore.controller.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.service.CartService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart/{cartId}")
public class CartManagementController {

    private final CartService cartService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Books in your cart: ", cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/total-amount")
    public ResponseEntity<ApiResponse> getTotalAmount(Long cartId) {
        Double totalAmount = cartService.getTotalPrice(cartId);
        return ResponseEntity.ok(new ApiResponse("Your total amount is: ", totalAmount));
    }

    @PutMapping("/clear")
    public ResponseEntity<ApiResponse> clearCart(Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new ApiResponse("Cart has been cleared", cartId));
    }
}
