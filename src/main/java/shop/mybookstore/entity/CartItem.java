package shop.mybookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    private int quantity;
    private Double unitPrice;
    private Double totalPrice;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    public CartItem(int quantity, Double unitPrice, Book book, Cart cart) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity*unitPrice;
        this.book = book;
        this.cart = cart;
    }

    public void setTotalPrice() {
        this.totalPrice = (this.unitPrice != null ? this.unitPrice : 0.0) * this.quantity;
    }

}
