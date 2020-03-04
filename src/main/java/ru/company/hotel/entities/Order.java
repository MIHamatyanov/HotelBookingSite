package ru.company.hotel.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "aparts_orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private User guest;
    private LocalDate creationDate;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
