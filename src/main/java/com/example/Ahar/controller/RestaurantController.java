package com.example.Ahar.controller;

import com.example.Ahar.model.Restaurant;
import com.example.Ahar.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    //Get all restaurants
    @GetMapping
    public List<Restaurant> getAllRestaurants()
    {
        return restaurantService.getAllRestaurants();
    }

    //Get restaurant by id
    @GetMapping("/{id}")
    public Optional<Restaurant>getRestaurantById(@PathVariable Long id)
    {
        return restaurantService.getRestaurantById(id);
    }

    //Create a new restaurant
    @PostMapping
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant)
    {
        return  restaurantService.addRestaurant(restaurant);
    }

    //Update an existing restaurant
    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant)
    {
        return restaurantService.updateRestaurant(id, restaurant);
    }

    //Delete a restaurant
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id)
    {
        restaurantService.deleteRestaurant(id);
    }
}