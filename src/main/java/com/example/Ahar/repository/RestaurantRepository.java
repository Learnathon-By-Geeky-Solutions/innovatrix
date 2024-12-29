package com.example.Ahar.repository;

import com.example.Ahar.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository <Restaurant, Long>{
    //can define custom queries here if needed
}