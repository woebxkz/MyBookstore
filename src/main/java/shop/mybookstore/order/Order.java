package shop.mybookstore.order;

import jakarta.persistence.*;
import shop.mybookstore.user.User;

import java.time.LocalDate;

//@Entity
//@Table(name = "orders")
public class Order {

    //@Id
    //@GeneratedValue
    private Long id;

    /*@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;*/

    private LocalDate orderDate;
    private String status;
    private int totalAmount;

}
