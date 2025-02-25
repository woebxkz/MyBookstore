package shop.mybookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mybookstore.dto.UserDto;
import shop.mybookstore.entity.User;
import shop.mybookstore.exception.AlreadyExistsException;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User createUser(UserDto userDto) {
        User user = new User(userDto);
        if (userRepository.existsById(user.getId())) {
        throw new AlreadyExistsException("User already exists");
    } else { return userRepository.save(user); }}

    public User updatePassword(Long userId, String currentPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(currentPassword)) {
                user.setPassword(newPassword);
                return userRepository.save(user);
            } else {
                throw new IllegalArgumentException("New password is the same as old password");
            }
        } else {
                throw new NoSuchElementException("User not found");
            }
        }


    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () -> {
            throw new ResourceNotFoundException("User not found");
        });
    }

}
