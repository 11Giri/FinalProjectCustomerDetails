package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;
    @Column
    private String customerName;
    @Column
    private long mobileNumber;
    @Column
    private String country;
    @Column
    private String institute;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Item> item=new ArrayList<Item>();

@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "address_id")
private Address address;

    public void addItems(Item item) {
        this.item.add(item);
        item.setCustomer(this);
    }
    public void addAddress(Address address){
        this.setAddress(address);
    }
}