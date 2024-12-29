package com.example.Ahar.service;

import com.example.Ahar.model.Restaurant;
import com.example.Ahar.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    //Get all restaurants
    public List<Restaurant> getAllRestaurants()
    {
        return restaurantRepository.findAll();
    }

    //Get restaurant by ID
    public Optional<Restaurant>getRestaurantById(Long id)
    {
        return restaurantRepository.findById(id);
    }

    //add a new restaurant
    public Restaurant addRestaurant(Restaurant restaurant)
    {
        return restaurantRepository.save(restaurant);
    }

    //update restaurant
    public Restaurant updateRestaurant(Long id, Restaurant restaurant)
    {
        restaurant.setId(id);
        return restaurantRepository.save(restaurant);
    }

    //delete restaurant
    public void deleteRestaurant(Long id)
    {
        restaurantRepository.deleteById(id);
    }
}