package shop.mybookstore.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    /*@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/

    @ManyToOne
    private List<CartItem> books = new ArrayList<>();

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;


    private void updateTotalAmount() {
        this.totalAmount = books.stream().map(book -> {
            BigDecimal unitPrice = book.getUnitPrice();
            return unitPrice.multiply(BigDecimal.valueOf(book.getQuantity()))
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
