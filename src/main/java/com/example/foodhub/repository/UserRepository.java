package com.example.foodhub.repository;

import com.example.foodhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // dùng login
    Optional<User> findByEmail(String email);

    // kiểm tra email tồn tại khi create/update
    boolean existsByEmail(String email);
}
