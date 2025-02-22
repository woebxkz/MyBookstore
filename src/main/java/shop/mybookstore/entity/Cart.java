package shop.mybookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount = Double.NaN;


    private void updateTotalAmount() {
        this.totalAmount = cartItems.stream().map(book -> {
            Double unitPrice = book.getUnitPrice();
            return unitPrice*book.getQuantity();
        }).reduce(0.0, Double::sum);
    }

    public void addToCart(CartItem book) {
        this.cartItems.add(book);
        book.setCart(this);
        updateTotalAmount();
    }

    public void deleteBook(CartItem book) {
        this.cartItems.remove(book);
        book.setCart(null);
        updateTotalAmount();
    }

}
