package com.shoppingapp.service;

import com.shoppingapp.dto.request.UserRequestDto;
import com.shoppingapp.dto.response.UserResponseDto;
import com.shoppingapp.exception.BadRequestException;
import com.shoppingapp.exception.ResourceNotFoundException;
import com.shoppingapp.model.User;
import com.shoppingapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldNormalizeAndReturnResponse() {
        UserRequestDto request = new UserRequestDto(" John ", " Doe ", " TEST@MAIL.COM ", " 1234567890 ", " customer ");

        User saved = new User();
        saved.setUserId(1L);
        saved.setFirstName("John");
        saved.setLastName("Doe");
        saved.setEmail("test@mail.com");
        saved.setPhone("1234567890");
        saved.setRole("CUSTOMER");

        when(userRepository.save(any(User.class))).thenReturn(saved);

        UserResponseDto response = userService.createUser(request);

        assertEquals(1L, response.getUserId());
        assertEquals("test@mail.com", response.getEmail());
        assertEquals("CUSTOMER", response.getRole());
    }

    @Test
    void getAllUsers_shouldMapAllRows() {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("A");
        user.setLastName("B");
        user.setEmail("a@b.com");
        user.setPhone("123");
        user.setRole("ADMIN");

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponseDto> response = userService.getAllUsers();

        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).getUserId());
    }

    @Test
    void getUserById_whenMissing_shouldThrow() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void updateUser_whenEmailAlreadyExists_shouldThrow() {
        User existing = new User();
        existing.setUserId(1L);
        existing.setEmail("old@mail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.existsByEmail("new@mail.com")).thenReturn(true);

        UserRequestDto request = new UserRequestDto("A", "B", "new@mail.com", "123", "admin");

        assertThrows(BadRequestException.class, () -> userService.updateUser(1L, request));
    }
}
