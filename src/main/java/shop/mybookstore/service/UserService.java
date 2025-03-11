package shop.mybookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.regex.Pattern;

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

        final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        User user = userOptional.get();

        if (user.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("New password cannot be the same as old password");
        }

        if (!pattern.matcher(newPassword).matches()) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one letter and one number.");
        }

        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        return user;
    }

    public void deleteUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(userId);
    }

}
