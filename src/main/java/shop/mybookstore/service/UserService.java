package shop.mybookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mybookstore.entity.RoleEnum;
import shop.mybookstore.entity.User;
import shop.mybookstore.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User createUser(String username,
                           String firstName,
                           String lastName,
                           String password,
                           String email,
                           RoleEnum role) {
        User user = new User(username, firstName, lastName, password, email, role);
        return user;
    }

    public User updatePassword(Long userId, String currentPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(currentPassword)) {
                user.setPassword(newPassword);
                return userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Current password is incorrect");
            }
        } else {
                throw new NoSuchElementException("User not found");
            }
        }


    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
