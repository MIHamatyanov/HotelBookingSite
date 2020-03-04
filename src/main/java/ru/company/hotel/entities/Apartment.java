package ru.company.hotel.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    private String name;
    private String photoUrl;
    private String description;
    private int price;
    private int capacity;
    private int bedNumber;
}
