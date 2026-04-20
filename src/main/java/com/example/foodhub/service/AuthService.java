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

    // REGISTER
    public String register(RegisterRequest request) {

        if (request.getEmail() == null ||
                request.getPassword() == null ||
                request.getName() == null) {
            throw new RuntimeException("Missing required fields");
        }

        String email = request.getEmail().trim().toLowerCase();
        String password = request.getPassword().trim();
        String name = request.getName().trim();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException("ROLE_USER not found in database"));

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepository.save(user);

        return "Register success";
    }

    // LOGIN
    public LoginResponse login(LoginRequest request) {

        if (request.getEmail() == null ||
                request.getPassword() == null) {
            throw new RuntimeException("Missing email or password");
        }

        String email = request.getEmail().trim().toLowerCase();
        String password = request.getPassword().trim();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String roleName = user.getRole().getName();

        String token = jwtUtil.generateToken(
                user.getEmail(),
                roleName
        );

        return new LoginResponse(
                token,
                roleName,
                user.getEmail(),
                user.getName()
        );
    }
}
