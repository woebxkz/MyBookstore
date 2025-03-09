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
import java.util.Set;

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
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new AlreadyExistsException("User already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("User already exists");
        }

            userRepository.save(user);
            return user;
    }

    public User updatePassword(Long userId, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(newPassword)) {
                throw new IllegalArgumentException("New password cannot be the same as old password");
            }

            user.setPassword(newPassword);
            userRepository.save(user);
            return user;
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }


    public void deleteUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(userId);
    }

}
