    package com.example.foodhub.controller.admin;

    import com.example.foodhub.model.User;
    import com.example.foodhub.service.UserService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/admin/users")
    @RequiredArgsConstructor
    public class HomeAdminController {

        private final UserService userService;

        // Lấy tất cả user
        @GetMapping
        public List<User> getAllUsers() {
            return userService.getAllUsers();
        }

        // Lấy user theo id
        @GetMapping("/{id}")
        public User getUser(@PathVariable Integer id) {
            return userService.getUserById(id);
        }

        // THÊM USER
        @PostMapping
        public User createUser(@RequestBody User user) {
            return userService.createUser(user);
        }

        // Update user
        @PutMapping("/{id}")
        public User updateUser(@PathVariable Integer id, @RequestBody User user) {
            return userService.updateUser(id, user);
        }

        // Xóa user
        @DeleteMapping("/{id}")
        public String deleteUser(@PathVariable Integer id) {
            userService.deleteUser(id);
            return "Deleted successfully";
        }
    }
