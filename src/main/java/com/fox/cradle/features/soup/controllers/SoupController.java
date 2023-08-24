package com.fox.cradle.features.soup.controllers;

import com.fox.cradle.features.soup.models.Soup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/soups")
public class SoupController
{
    @GetMapping("/{id}")
    public ResponseEntity<Soup> getSoupById(@PathVariable long id)
    {
        // Fetch or create a soup object based on the id.
        // For the sake of this example, I'll create a mock soup.
        Soup soup = new Soup();
        soup.setId(id);
        soup.setName("Chicken Soup");
        soup.setIngredients("Chicken, Water, Salt, Vegetables");

        return ResponseEntity.ok(soup);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Soup>> getAllSoups()
    {
        // Fetch all soups from the database.
        // For the sake of this example, I'll create a mock soup.
        Soup soup = new Soup();
        soup.setId(1L);
        soup.setName("Chicken Soup");
        soup.setIngredients("Chicken, Water, Salt, Vegetables");

        Soup soup2 = new Soup();
        soup2.setId(2L);
        soup2.setName("Beef Soup");
        soup2.setIngredients("Beef, Water, Salt, Vegetables");

        Soup soup3 = new Soup();
        soup3.setId(3L);
        soup3.setName("Vegetable Soup");
        soup3.setIngredients("Water, Salt, Vegetables");

        List<Soup> soups = List.of(soup, soup2, soup3);

        return ResponseEntity.ok(soups);
    }
}
