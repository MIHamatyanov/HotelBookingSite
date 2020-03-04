package ru.company.hotel.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.company.hotel.entities.City;
import ru.company.hotel.repos.CityRepository;
import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {
    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public List<City> getCities(@RequestParam String cityStartsWith) {
        return cityRepository.findByNameStartsWithIgnoreCase(cityStartsWith);
    }
}
