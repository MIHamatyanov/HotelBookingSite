package ru.company.hotel.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private double rating;

}
