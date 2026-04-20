package com.example.foodhub.service;

import com.example.foodhub.model.Category;
import com.example.foodhub.model.Food;
import com.example.foodhub.repository.CategoryRepository;
import com.example.foodhub.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;

    // 🔹 Lấy tất cả món
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    // 🔹 Lấy theo id
    public Food getFoodById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
    }

    // 🔥 THÊM MÓN
    public Food createFood(Food food) {

        String categoryName = food.getCategory().getName();

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        food.setCategory(category);

        return foodRepository.save(food);
    }

    // 🔹 UPDATE
    public Food updateFood(Long id, Food updatedFood) {
        Food food = getFoodById(id);

        food.setName(updatedFood.getName());
        food.setPrice(updatedFood.getPrice());
        food.setDescription(updatedFood.getDescription());

        if (updatedFood.getCategory() != null) {
            String categoryName = updatedFood.getCategory().getName();

            Category category = categoryRepository.findByName(categoryName)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            food.setCategory(category);
        }

        return foodRepository.save(food);
    }

    // 🔹 DELETE
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }
}

