 package com.example.demo.service;

import com.example.demo.entity.Address;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Item;
import com.example.demo.model.AddressModel;
import com.example.demo.model.CustomerModel;
import com.example.demo.model.ItemModel;
import com.example.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


 @Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }
    public String saveCustomerDetails(List<CustomerModel> customerModels) {
        Validator validator=new Validator();
        try{
            for(CustomerModel customerModel:customerModels) {//3user

                Customer customer = new Customer();
                if( validator.validateCustomerName(customerModel.getCustomerName())) {
                    customer.setCustomerName(customerModel.getCustomerName());
                    System.out.println("Customer name is valid");
                }else {
                    return "Please provide valid customer name";
                }
                if(validator.validateMobileNumber(customerModel.getMobileNumber())) {
                    customer.setMobileNumber(customerModel.getMobileNumber());
                }else {
                    return "Please provide  at least 10 digit number";
                }
                if(validator.validateCountry(customerModel.getCountry())) {
                    customer.setCountry(customerModel.getCountry());
                }else {
                    return "Please provide valid country name ";

                }
                customer.setInstitute(customerModel.getInstitute());
                List<ItemModel> itemModels = customerModel.getFoodItem();
                for (ItemModel itemModel : itemModels) {
                    Item item = new Item();
                    item.setItemsName(itemModel.getItemsName());
                    item.setItemQuantity(itemModel.getItemsQuantity());
                    customer.addItems(item);
                    AddressModel addressModel=customerModel.getAddress();
                    Address address= new Address();
                    address.setCity(addressModel.getCity());
                    address.setPincode(addressModel.getPincode());
                    address.setLocation(addressModel.getLocation());
                    customer.addAddress(address);
                }

                this.customerRepository.save(customer);
            }
        } catch (Exception e) {
            System.out.println("Error detail::" + e.getMessage());
return e.getMessage();
        }
        return "success";
    }
    // Eetch the data from data baseSQL
    public Customer fetchDetailsForSelectCustomer(Integer customerId){
        Customer customer = new Customer();
        Optional<Customer>result=this.customerRepository.findById(customerId);
        if(result !=null && !result.isEmpty()){
            customer = result.get();
        }
        return customer;
    }
    public CustomerModel populatedCustomerModel(Customer customer){
        CustomerModel customerModel=new CustomerModel();
        customerModel.setCountry(customer.getCountry());
        customerModel.setMobileNumber(customer.getMobileNumber());
        customerModel.setCustomerName(customer.getCustomerName());

        List<ItemModel>itemModels= new ArrayList<>();
        List<Item> itemList= new ArrayList<>();
        for(Item item: itemList){
            ItemModel itemModel=new ItemModel();
            itemModel.setItemsName(item.getItemsName());
            itemModel.setItemsQuantity(item.getItemQuantity());
            itemModels.add(itemModel);
        }
        customerModel.setFoodItem(itemModels);
        return customerModel;
    }
    //fetch all data from data base
     public List <Customer> fetchDetailsAllCustomer(){
        List<Customer> result= (List<Customer>) this.customerRepository.findAll();
        if(result !=null&& !result.isEmpty()){
            return result;
        }
        return new ArrayList<>();
     }
     public List<CustomerModel> populatedAllCustomerModel(List<Customer>customerList) {
         List<CustomerModel> customerModels = new ArrayList<>();
         for (Customer customer : customerList) {
             CustomerModel customerModel = new CustomerModel();

             customerModel.setCustomerName(customer.getCustomerName());
             customerModel.setMobileNumber(customer.getMobileNumber());
             customerModel.setCountry(customer.getCountry());
             List<ItemModel> allItemModel = new ArrayList<>();
             List<Item> itemList = new ArrayList<>();
             for (Item item : itemList) {
                 ItemModel itemModel = new ItemModel();
                 itemModel.setItemsName(item.getItemsName());
                 itemModel.setItemsQuantity(item.getItemQuantity());
                 allItemModel.add(itemModel);

             }
             customerModel.setFoodItem(allItemModel);
             customerModels.add(customerModel);
         }
         return customerModels;
     }



//Update customer records in data base
 public String updateCustomerInfo(CustomerModel customerModel) {
      Customer customer = this.customerRepository.fetchCustomerByName(customerModel.getCustomerName(), customerModel.getCountry());
    // List<Customer> customerList = (List<Customer>) this.customerRepository.findAll();
     if (customer != null) {

         customer.setInstitute(customerModel.getInstitute());
         customer.setMobileNumber(customerModel.getMobileNumber());

         this.customerRepository.save(customer);

     }
         return "success";
     }

     // update infoBase on CustomerId
     public String updateInfoBaseOnCustomerId (Integer customerId,CustomerModel customerModel){
         Optional<Customer> result = this.customerRepository.findById(customerId);
         if (result != null) {
             Customer customer = result.get();
             customer.setCustomerName(customerModel.getCustomerName());
             customer.setCountry(customerModel.getCountry());
             customer.setMobileNumber(customerModel.getMobileNumber());
             this.customerRepository.save(customer);

         }
         return "success";
     }

// Delecte CustomerInformation
     public Boolean deleteRecords(Integer customerId) {
         this.customerRepository.deleteById(customerId);
         return true;
     }
 }

