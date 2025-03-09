package shop.mybookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.mybookstore.IdConverter;
import shop.mybookstore.dto.UserDto;
import shop.mybookstore.entity.RoleEnum;
import shop.mybookstore.entity.User;
import shop.mybookstore.exception.AlreadyExistsException;
import shop.mybookstore.exception.ResourceNotFoundException;
import shop.mybookstore.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUserTest_Success() {

        UUID uuid = UUID.randomUUID();
        Long userId = IdConverter.convertUuidToLong(uuid);
        UserDto userDto = new UserDto("Mark", "Brown", "mark_brown123","mark.brown@email", "password123", RoleEnum.CUSTOMER);

        Mockito.lenient().when(userRepository.existsById(userId)).thenReturn(false);
        User user = new User();
        user.setId(userId);
        Mockito.lenient().when(userRepository.save(any(User.class))).thenReturn(user);

        userService.createUser(userDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("Brown", capturedUser.getLastName());

    }

    @Test
    public void createUserTest_UserAlreadyExists() {

        UserDto userDto = new UserDto("Mark", "Brown", "mark_brown123", "mark.brown@email", "password123", RoleEnum.CUSTOMER);

        Mockito.lenient().when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> userService.createUser(userDto));
        assertEquals("User already exists", exception.getMessage());

    }

    @Test
    public void updatePasswordTest_Success() {

        UUID uuid = UUID.randomUUID();
        Long userId = IdConverter.convertUuidToLong(uuid);
        User user = new User();
        user.setId(userId);
        String currentPassword = "password";
        user.setPassword(currentPassword);
        String newPassword = "new_password";

        Mockito.lenient().when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.updatePassword(userId, newPassword);

        assertEquals("new_password", user.getPassword());

    }

    @Test
    public void updatePasswordTest_PasswordTheSameAsBefore() {

        UUID uuid = UUID.randomUUID();
        Long userId = IdConverter.convertUuidToLong(uuid);
        User user = new User();
        user.setId(userId);
        String currentPassword = "password";
        user.setPassword(currentPassword);
        String newPassword = "password";

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> userService.updatePassword(userId, newPassword));

    }

    @Test
    public void deleteUserTest_Success() {

        UUID uuid = UUID.randomUUID();
        Long userId = IdConverter.convertUuidToLong(uuid);
        User userToDelete = new User();
        userToDelete.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userToDelete));

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);

    }

    @Test
    public void deleteUserTest_NotFound() {

        UUID uuid = UUID.randomUUID();
        Long userId = IdConverter.convertUuidToLong(uuid);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userId));

    }


}
