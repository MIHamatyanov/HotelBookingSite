package ru.company.hotel.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.hotel.entities.Apartment;
import ru.company.hotel.entities.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByApartmentAndCheckInDateBetween(Apartment apartment, LocalDate checkIn, LocalDate checkOut);
    List<Order> findByApartmentAndCheckOutDateBetween(Apartment apartment, LocalDate checkIn, LocalDate checkOut);
}
