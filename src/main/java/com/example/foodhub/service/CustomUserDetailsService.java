package com.example.foodhub.service;

import com.example.foodhub.model.User;
import com.example.foodhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("🔍 Looking for user: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Lấy role name
        String roleName = "ROLE_USER"; // default

        if (user.getRole() != null) {
            roleName = user.getRole().getName();
            System.out.println("📌 Role from database: " + roleName);
        } else {
            System.out.println("⚠️ User role is null for: " + email);
        }

        // 🔥 FIX: Đảm bảo role có prefix ROLE_
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }

        // 🔥 TEMP: Force admin role cho email admin@gmail.com
        if (email.equalsIgnoreCase("admin@gmail.com")) {
            roleName = "ROLE_ADMIN";
            System.out.println("👑 FORCED ADMIN ROLE for: " + email);
        }

        System.out.println("✅ Final role for " + email + ": " + roleName);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(roleName))
        );
    }
}