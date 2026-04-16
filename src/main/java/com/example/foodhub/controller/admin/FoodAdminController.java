package com.example.foodhub.controller.admin;

import com.example.foodhub.model.Food;
import com.example.foodhub.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/foods")
@RequiredArgsConstructor
public class FoodAdminController {

    private final FoodService foodService;

    // 🔹 1. Lấy tất cả món
    @GetMapping
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    // 🔹 2. Lấy theo id
    @GetMapping("/{id}")
    public Food getFood(@PathVariable Long id) {
        return foodService.getFoodById(id);
    }

    // 🔥 3. Thêm món
    @PostMapping
    public Food createFood(@RequestBody Food food) {
        return foodService.createFood(food);
    }

    // 🔹 4. Update
    @PutMapping("/{id}")
    public Food updateFood(@PathVariable Long id, @RequestBody Food food) {
        return foodService.updateFood(id, food);
    }

    // 🔹 5. Delete
    @DeleteMapping("/{id}")
    public String deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return "Deleted successfully";
    }
}
