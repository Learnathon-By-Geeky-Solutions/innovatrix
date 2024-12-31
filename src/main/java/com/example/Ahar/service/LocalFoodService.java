package com.example.Ahar.service;

import com.example.Ahar.model.LocalFood;
import com.example.Ahar.repository.LocalFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalFoodService {
    @Autowired
    private LocalFoodRepository localFoodRepository;

    //Get all local foods
    public
    List<LocalFood> getAllLocalFoods()
    {
        return localFoodRepository.findAll();
    }

    //Get local food by id
    public Optional<LocalFood>getLocalFoodById(Long id)
    {
        return localFoodRepository.findById(id);

    }

    //Add a local food
    public LocalFood addLocalFood(LocalFood localFood)
    {
        return localFoodRepository.save(localFood);
    }

    //Update a localFood
    public LocalFood updateLocalFood(Long id, LocalFood updatedFood)
    {
        return localFoodRepository.findById(id).map(food->{
            food.setName(updatedFood.getName());
            food.setDescription(updatedFood.getDescription());
            food.setLocation(updatedFood.getLocation());
            food.setPrice(updatedFood.getPrice());
            food.setRating(updatedFood.getRating());
            return localFoodRepository.save(food);
        }).orElseThrow(()-> new RuntimeException("Local food not Found"));
    }

    //Delete a local food
    public void deleteLocalFood(Long id)
    {
        localFoodRepository.deleteById(id);
    }


}
