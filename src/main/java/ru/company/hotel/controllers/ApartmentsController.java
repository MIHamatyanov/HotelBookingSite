package ru.company.hotel.controllers;

import org.springframework.web.bind.annotation.*;
import ru.company.hotel.entities.Apartment;
import ru.company.hotel.entities.Order;
import ru.company.hotel.repos.ApartmentRepository;
import ru.company.hotel.repos.OrderRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentsController {
    private ApartmentRepository apartmentRepository;
    private OrderRepository orderRepository;

    public ApartmentsController(ApartmentRepository apartmentRepository, OrderRepository orderRepository) {
        this.apartmentRepository = apartmentRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<Apartment> getAvailableApartments(@RequestParam String city,
                                                  @RequestParam String checkIn,
                                                  @RequestParam String checkOut,
                                                  @RequestParam String guests
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
        LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);

        LocalDate today = LocalDate.now();
        if (checkInDate.isBefore(today) || checkOutDate.isBefore(checkInDate)) {
            return null;
        }

        List<Apartment> result = new ArrayList<>();

        List<Apartment> apartments = apartmentRepository.findByHotelAddressCityNameAndCapacity(city, Integer.parseInt(guests));
        apartments.forEach(apartment -> {
            List<Order> ordersCheckInBetween = orderRepository.findByApartmentAndCheckInDateBetween(apartment, checkInDate, checkOutDate);
            List<Order> ordersCheckOutBetween = orderRepository.findByApartmentAndCheckOutDateBetween(apartment, checkInDate, checkOutDate);
            if (ordersCheckInBetween.isEmpty() && ordersCheckOutBetween.isEmpty()) {
                result.add(apartment);
            }
        });

        return result;
    }

    @GetMapping("{id}")
    public Apartment getApartmentById(@PathVariable("id") Apartment apartment) {
        return apartment;
    }
}
