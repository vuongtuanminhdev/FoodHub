package com.example.foodhub.service;

import com.example.foodhub.dto.FoodRequest;
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

    // 🔹 GET ALL
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    // 🔹 GET BY ID
    public Food getFoodById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
    }

    // 🔥 CREATE (DTO)
    public Food createFood(FoodRequest request) {

        if (request.getCategoryId() == null) {
            throw new RuntimeException("Category ID không được null");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Food food = new Food();
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setDescription(request.getDescription());
        food.setCategory(category);

        return foodRepository.save(food);
    }

    // 🔥 UPDATE (DTO)
    public Food updateFood(Long id, FoodRequest request) {

        Food food = getFoodById(id);

        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setDescription(request.getDescription());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            food.setCategory(category);
        }

        return foodRepository.save(food);
    }

    // 🔹 DELETE
    public void deleteFood(Long id) {
        if (!foodRepository.existsById(id)) {
            throw new RuntimeException("Food not found");
        }
        foodRepository.deleteById(id);
    }
}