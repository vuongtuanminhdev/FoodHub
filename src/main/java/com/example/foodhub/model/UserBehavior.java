//package com.example.foodhub.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "user_behavior")
//@Getter
//@Setter
//public class UserBehavior {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "food_id")
//    private Food food;
//
//    @ManyToOne
//    @JoinColumn(name = "action_type_id")
//    private ActionType actionType;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//}
