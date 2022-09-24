package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomerModel {
    private String customerName;
    private long mobileNumber;
    private String country;
    private String institute;
private List<ItemModel>foodItem;
private  AddressModel address;
}
