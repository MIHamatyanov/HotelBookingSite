package ru.company.hotel.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.hotel.entities.Apartment;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    List<Apartment> findByHotelAddressCityNameAndCapacity(String city, int capacity);
}
