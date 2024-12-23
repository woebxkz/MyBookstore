package shop.mybookstore.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> books = new HashSet<>();

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;


    private void updateTotalAmount() {
        this.totalAmount = books.stream().map(book -> {
            BigDecimal unitPrice = book.getUnitPrice();
            return unitPrice.multiply(BigDecimal.valueOf(book.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addBook(CartItem book) {
        this.books.add(book);
        book.setCart(this);
        updateTotalAmount();
    }

    public void deleteBook(CartItem book) {
        this.books.remove(book);
        book.setCart(null);
        updateTotalAmount();
    }

}
