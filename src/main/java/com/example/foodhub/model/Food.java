//package com.example.foodhub.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "foods")
//@Getter @Setter
//public class Food {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private Double price;
//    private String description;
//
//    @ManyToOne
//    @JoinColumn(name = "store_id")
//    private Store store;
//
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//}
