package shop.mybookstore.cart;

import jakarta.persistence.*;
import shop.mybookstore.book.Book;
import shop.mybookstore.user.User;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Column(name = "user_id")
    private User userId;

    @OneToMany(mappedBy = "books", cascade = CascadeType.ALL)
    @Column(name = "book_id")
    private Book bookId;

    @Column(name = "quantity")
    private Integer quantity;

}
