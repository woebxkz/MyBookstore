package shop.mybookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import shop.mybookstore.dto.UserDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public User(String username, String firstName, String lastName, String password, String email, RoleEnum role) {
    }

    public User(UserDto userDto) {
    }
}
