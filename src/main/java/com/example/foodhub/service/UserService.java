package com.example.foodhub.service;

import com.example.foodhub.model.Role;
import com.example.foodhub.model.User;
import com.example.foodhub.repository.RoleRepository;
import com.example.foodhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // Lấy tất cả user
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy user theo id
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 🔹 THÊM USER (NEW)
    public User createUser(User user) {

        // tìm role theo name (ADMIN hoặc USER)
        Role role = roleRepository.findByName(user.getRole().getName())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // Xóa user
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // Update user
    public User updateUser(Integer id, User updatedUser) {
        User user = getUserById(id);

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());

//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
