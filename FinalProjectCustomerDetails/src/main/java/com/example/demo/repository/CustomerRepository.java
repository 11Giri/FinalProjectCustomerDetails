package com.example.demo.repository;

import com.example.demo.entity.Customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {
    @Query("select c from Customer c where c.customerName=?1 and c.country=?2")
    Customer fetchCustomerByName(String customerName,String country);
}
