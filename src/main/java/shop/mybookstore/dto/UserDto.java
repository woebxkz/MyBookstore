package shop.mybookstore.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import shop.mybookstore.entity.RoleEnum;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private RoleEnum role;

    public UserDto(String firstName, String lastName, String username, String email, String password, RoleEnum role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
