package ru.company.hotel.controllers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import ru.company.hotel.entities.Order;
import ru.company.hotel.repos.OrderRepository;
import ru.company.hotel.repos.UserRepository;

import javax.mail.MessagingException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private JavaMailSender javaMailSender;

    public OrdersController(OrderRepository orderRepository, UserRepository userRepository, JavaMailSender javaMailSender) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("{id}")
    public Order getOrder(@PathVariable("id") Order order) {
        return order;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) throws MessagingException {
        order.setCreationDate(LocalDate.now());
        userRepository.save(order.getGuest());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hotelinfomx@mail.ru");
        message.setTo(order.getGuest().getEmail());
        message.setSubject("Hotel book info");
        message.setText("Информация о бронировании: \n" +
                "Время бронирования: " + order.getCreationDate() + "\n" +
                "Отель: " + order.getApartment().getHotel().getName() + "\n" +
                "Адрес: ул." + order.getApartment().getHotel().getAddress().getStreet() + " " + order.getApartment().getHotel().getAddress().getHouseNumber() + "\n" +
                "Дата заселения: " + order.getCheckInDate() + " / Дата выселения: " + order.getCheckOutDate() + "\n" +
                "Имя: " + order.getGuest().getName() + "\n" +
                "Номер для связи: " + order.getGuest().getPhoneNumber());
        javaMailSender.send(message);

        return orderRepository.save(order);
    }
}
