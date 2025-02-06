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
    private String email;
    private String password;
    private RoleEnum role;

}
