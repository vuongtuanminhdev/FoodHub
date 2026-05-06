package com.example.foodhub.dto;

import lombok.Data;

@Data
public class FoodRequest {
    private String name;
    private Double price;
    private String description;
    private Long categoryId;
}