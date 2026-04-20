package com.example.foodhub.controller.admin;

import com.example.foodhub.model.User;
import com.example.foodhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5174")
public class AdminUsersController {

    private final UserService userService;

    // ==========================
    // GET ALL USERS
    // ==========================
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ==========================
    // GET USER BY ID
    // ==========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {

        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "success", false,
                            "error", e.getMessage()
                    ));
        }
    }

    // ==========================
    // CREATE USER
    // ==========================
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {

        try {
            User newUser = userService.createUser(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(newUser);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "success", false,
                            "error", e.getMessage()
                    ));
        }
    }

    // ==========================
    // UPDATE USER
    // ==========================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Integer id,
            @RequestBody User user
    ) {

        try {
            User updatedUser = userService.updateUser(id, user);

            return ResponseEntity.ok(updatedUser);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "success", false,
                            "error", e.getMessage()
                    ));
        }
    }

    // ==========================
    // DELETE USER
    // ==========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {

        try {
            userService.deleteUser(id);

            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "message", "Deleted successfully"
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "success", false,
                            "error", e.getMessage()
                    ));
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> toggleStatus(@PathVariable Integer id) {

        try {
            User user = userService.toggleStatus(id);

            return ResponseEntity.ok(user);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "success", false,
                            "error", e.getMessage()
                    ));
        }
    }

}
