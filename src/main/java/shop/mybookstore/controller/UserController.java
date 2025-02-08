package shop.mybookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mybookstore.dto.UserDto;
import shop.mybookstore.entity.User;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    UserService userService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse("List of all users: ", users));
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
        Optional<User> user = userService.getUserById(userId);
            return ResponseEntity.ok(new ApiResponse("Success", user));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User not found", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);
        return ResponseEntity.ok(new ApiResponse("User successfully created", user));
    }

    @PostMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updatePassword(@PathVariable Long userId,
                                                      @RequestBody String currentPassword,
                                                      @RequestBody String newPassword) {
        User user = userService.updatePassword(userId, currentPassword, newPassword);
        return ResponseEntity.ok(new ApiResponse("Password successfully updated", user));
    }


}
