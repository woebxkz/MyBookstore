package shop.mybookstore.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mybookstore.dto.UserDto;
import shop.mybookstore.entity.User;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.request.PasswordChangeRequest;
import shop.mybookstore.response.ApiResponse;
import shop.mybookstore.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse("List of all users: ", users));
    }

    @GetMapping("/{userId}")
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

    @PutMapping("/{userId}/password")
    public ResponseEntity<ApiResponse> changePassword(@PathVariable Long userId,
                                                      @RequestBody PasswordChangeRequest request){
        try {
            User user = userService.updatePassword(userId, request.getNewPassword());
            return ResponseEntity.ok(new ApiResponse("Password successfully changed", user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid password update", e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User successfully deleted", userId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User not found", userId));
        }
    }

}
