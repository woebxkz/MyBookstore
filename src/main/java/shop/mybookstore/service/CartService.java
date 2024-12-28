package shop.mybookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import shop.mybookstore.entity.Book;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.entity.CartItem;
import shop.mybookstore.repository.CartRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void getCart(Long id){
        Cart cart = cartRepository.findById(id).orElseThrow(); //add customized exception
        //add total amount
        //add to cart
        //return cart
    }

    public void addToCart(CartItem book) {

    }

    public void clearCart(){

    }


}
