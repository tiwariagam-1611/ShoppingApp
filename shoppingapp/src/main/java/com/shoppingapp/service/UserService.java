package com.shoppingapp.service;

import com.shoppingapp.dto.request.UserRequestDto;
import com.shoppingapp.dto.response.UserResponseDto;
import com.shoppingapp.exception.BadRequestException;
import com.shoppingapp.exception.ResourceNotFoundException;
import com.shoppingapp.model.User;
import com.shoppingapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserRequestDto dto) {
        String email = normalizeEmail(dto.getEmail());

        User user = new User();
        user.setFirstName(dto.getFirstName().trim());
        user.setLastName(dto.getLastName().trim());
        user.setEmail(email);
        user.setPhone(dto.getPhone().trim());
        user.setRole(dto.getRole().trim().toUpperCase());

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(toResponse(user));
        }
        return result;
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return toResponse(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        String newEmail = normalizeEmail(dto.getEmail());
        if (!existing.getEmail().equalsIgnoreCase(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new BadRequestException("Email already exists: " + newEmail);
        }

        existing.setFirstName(dto.getFirstName().trim());
        existing.setLastName(dto.getLastName().trim());
        existing.setEmail(newEmail);
        existing.setPhone(dto.getPhone().trim());
        existing.setRole(dto.getRole().trim().toUpperCase());

        User saved = userRepository.save(existing);
        return toResponse(saved);
    }

    public boolean existsUser(Long id) {
        return userRepository.existsById(id);
    }

    private UserResponseDto toResponse(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        return dto;
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}