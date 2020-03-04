package ru.company.hotel.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.hotel.entities.City;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByNameStartsWithIgnoreCase(String cityStartsWith);
}
