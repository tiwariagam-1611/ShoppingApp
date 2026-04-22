package com.shoppingapp.service;

import com.shoppingapp.dto.request.UserRequestDto;
import com.shoppingapp.dto.response.UserResponseDto;
import com.shoppingapp.exception.BadRequestException;
import com.shoppingapp.exception.ResourceNotFoundException;
import com.shoppingapp.model.User;
import com.shoppingapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User saveUser(String firstName, String lastName, String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone("1234567890");
        user.setRole("CUSTOMER");
        return userRepository.save(user);
    }

    @Test
    void createUser_shouldNormalizeAndReturnResponse() {
        UserRequestDto request = new UserRequestDto(" John ", " Doe ", " TEST@MAIL.COM ", " 1234567890 ", " customer ");

        UserResponseDto response = userService.createUser(request);

        assertEquals("John", response.getFirstName());
        assertEquals("test@mail.com", response.getEmail());
        assertEquals("CUSTOMER", response.getRole());
    }

    @Test
    void getAllUsers_shouldMapAllRows() {
        saveUser("A", "B", "a@b.com");

        List<UserResponseDto> response = userService.getAllUsers();

        assertEquals(1, response.size());
        assertEquals("a@b.com", response.get(0).getEmail());
    }

    @Test
    void getUserById_whenMissing_shouldThrow() {
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void updateUser_whenEmailAlreadyExists_shouldThrow() {
        User existing = saveUser("Old", "User", "old@mail.com");
        saveUser("Other", "User", "new@mail.com");

        UserRequestDto request = new UserRequestDto("A", "B", "new@mail.com", "123", "admin");

        assertThrows(BadRequestException.class, () -> userService.updateUser(existing.getUserId(), request));
    }

    @Test
    void updateUser_whenValid_shouldReturnUpdatedResponse() {
        User existing = saveUser("Old", "User", "old@mail.com");

        UserRequestDto request = new UserRequestDto(" Jane ", " Doe ", " NEW@MAIL.COM ", " 999 ", " admin ");

        UserResponseDto response = userService.updateUser(existing.getUserId(), request);

        assertEquals(existing.getUserId(), response.getUserId());
        assertEquals("new@mail.com", response.getEmail());
        assertEquals("ADMIN", response.getRole());
    }

    @Test
    void existsUser_shouldReturnRepositoryState() {
        User created = saveUser("Exists", "User", "exists@mail.com");

        assertTrue(userService.existsUser(created.getUserId()));
        assertFalse(userService.existsUser(created.getUserId() + 100L));
    }
}
