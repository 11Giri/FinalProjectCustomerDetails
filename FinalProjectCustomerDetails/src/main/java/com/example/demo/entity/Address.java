package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer addressId;
    @Column
    private String city;
    @Column
    private Integer pincode;
    @Column
    private String location;
    @OneToOne(mappedBy = "address")
    private Customer customer;

}
