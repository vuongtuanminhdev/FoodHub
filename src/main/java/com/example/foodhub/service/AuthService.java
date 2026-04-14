package com.example.foodhub.service;

import com.example.foodhub.config.JwtUtil;
import com.example.foodhub.dto.LoginRequest;
import com.example.foodhub.dto.LoginResponse;
import com.example.foodhub.dto.RegisterRequest;
import com.example.foodhub.model.Role;
import com.example.foodhub.model.User;
import com.example.foodhub.repository.RoleRepository;
import com.example.foodhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;

    public String register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);
        return "Register success";
    }

    // SỬA METHOD LOGIN - trả về LoginResponse thay vì String
    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        // Lấy role name
        String roleName = user.getRole().getName(); // "ROLE_ADMIN" hoặc "ROLE_USER"

        // Tạo token với email và role
        String token = jwtUtil.generateToken(user.getEmail(), roleName);

        // Trả về đối tượng LoginResponse
        return new LoginResponse(token, roleName, user.getEmail(), user.getName());
    }
}