package shop.mybookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.service.CartItemService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-items")
public class CartItemController {

    CartItemService cartItemService;

    @PostMapping("/book/add")
    public ResponseEntity<ApiResponse> addBookToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long bookId,
                                                     @RequestParam Integer quantity) {
        try {
        cartItemService.addBookToCart(cartId, bookId, quantity);
        return ResponseEntity.ok(new ApiResponse("Book added to cart", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/book/{bookId}/update-quantity")
    public ResponseEntity<ApiResponse> updateBookQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long bookId,
                                                          @RequestParam Integer quantity) {
        try {
            cartItemService.updateBookQuantity(cartId, bookId, quantity);
            return ResponseEntity.ok(new ApiResponse("Quantity successfully updated", null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/book/{bookId}/remove")
    public ResponseEntity<ApiResponse> removeBookFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long bookId) {
        try {
            cartItemService.removeBookFromCart(cartId, bookId);
            return ResponseEntity.ok(new ApiResponse("Book successfully deleted", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
