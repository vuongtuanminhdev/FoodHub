//package com.example.foodhub.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "stores")
//@Getter @Setter
//public class Store {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private String description;
//
//    @ManyToOne
//    @JoinColumn(name = "status_id")
//    private StoreStatus status;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//}
//
