package shop.mybookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount = 0.0;


    private Double updateTotalAmount() {
        return this.totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
    }

    public Cart addToCart(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
        updateTotalAmount();
        return this;
    }

    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null);
        updateTotalAmount();
    }

    public void clearAllItems() {
        Iterator<CartItem> it = cartItems.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

}
