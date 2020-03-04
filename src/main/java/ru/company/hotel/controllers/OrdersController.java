package ru.company.hotel.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        order.setCreationDate(LocalDate.now());
        userRepository.save(order.getGuest());
        return orderRepository.save(order);
    }
}
