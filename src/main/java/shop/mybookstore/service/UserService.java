package shop.mybookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mybookstore.entity.Cart;
import shop.mybookstore.entity.Order;
import shop.mybookstore.entity.RoleEnum;
import shop.mybookstore.entity.User;
import shop.mybookstore.repository.CartRepository;
import shop.mybookstore.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    UserRepository userRepository;


    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }

    public User createUser(String username,
                           String firstName,
                           String lastName,
                           String password,
                           String email,
                           RoleEnum role){
       User user = new User(username, firstName, lastName, password, email, role);
       return user;
    }

    public void updateUser(Long userId){
        Optional<User> user = userRepository.findById(userId);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

}
