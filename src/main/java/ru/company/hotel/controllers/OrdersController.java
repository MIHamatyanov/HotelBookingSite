package ru.company.hotel.controllers;

import org.springframework.web.bind.annotation.*;
import ru.company.hotel.entities.Order;
import ru.company.hotel.repos.OrderRepository;
import ru.company.hotel.repos.UserRepository;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    public OrdersController(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("{id}")
    public Order getOrder(@PathVariable("id") Order order) {
        return order;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        order.setCreationDate(LocalDate.now());
        userRepository.save(order.getGuest());
        return orderRepository.save(order);
    }
}
