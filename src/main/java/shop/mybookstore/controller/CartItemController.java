package shop.mybookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cartItems")
public class CartItemController {

    public void addBookToCart() {}

    public void updateBookQuantity() {}

    public void removeBookFromCart() {}

}
