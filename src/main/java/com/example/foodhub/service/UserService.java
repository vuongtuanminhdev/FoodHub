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

    // =============================
    // LẤY TẤT CẢ USER
    // =============================
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // =============================
    // LẤY USER THEO ID
    // =============================
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));
    }

    // =============================
    // TẠO USER
    // =============================
    public User createUser(User user) {

        // check email trùng
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // check role
        String roleName = user.getRole().getName();

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() ->
                        new RuntimeException("Role not found: " + roleName));

        user.setRole(role);

        // mã hóa password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // =============================
    // UPDATE USER
    // =============================
    public User updateUser(Integer id, User updatedUser) {

        User user = getUserById(id);

        // update name
        user.setName(updatedUser.getName());

        // update email nếu đổi email
        if (!user.getEmail().equals(updatedUser.getEmail())) {

            if (userRepository.existsByEmail(updatedUser.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            user.setEmail(updatedUser.getEmail());
        }

        // update password nếu có nhập password mới
        if (updatedUser.getPassword() != null &&
                !updatedUser.getPassword().trim().isEmpty()) {

            user.setPassword(
                    passwordEncoder.encode(updatedUser.getPassword())
            );
        }

        // update role nếu có
        if (updatedUser.getRole() != null &&
                updatedUser.getRole().getName() != null) {

            String roleName = updatedUser.getRole().getName();

            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() ->
                            new RuntimeException("Role not found: " + roleName));

            user.setRole(role);
        }

        return userRepository.save(user);
    }

    // =============================
    // DELETE USER
    // =============================
    public void deleteUser(Integer id) {

        User user = getUserById(id);

        userRepository.delete(user);
    }
}
