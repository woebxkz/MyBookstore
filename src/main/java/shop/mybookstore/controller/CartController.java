package shop.mybookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.mybookstore.service.CartService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public void getCart() {}

    public void clearCart() {}

    public void getTotalAmount() {}

}
