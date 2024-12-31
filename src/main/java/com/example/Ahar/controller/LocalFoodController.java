package com.example.Ahar.controller;

import com.example.Ahar.model.LocalFood;
import com.example.Ahar.service.LocalFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localfoods")
public class LocalFoodController {
    @Autowired
    private LocalFoodService localFoodService;

    @GetMapping
    public List<LocalFood> getAllLocalFoods()
    {
        return localFoodService.getAllLocalFoods();
    }

    @PostMapping
    public LocalFood addLocalFood(@RequestBody LocalFood localFood)
    {
        return localFoodService.addLocalFood(localFood);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalFood>getLocalFoodById(@PathVariable Long id)
    {
        return localFoodService.getLocalFoodById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public LocalFood updateLocalFood(@PathVariable Long id, @RequestBody LocalFood localFood)
    {
        return localFoodService.updateLocalFood(id, localFood);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteLocalFood(@PathVariable Long id)
    {
        localFoodService.deleteLocalFood(id);
        return ResponseEntity.noContent().build();
    }

}
