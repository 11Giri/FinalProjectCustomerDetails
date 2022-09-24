package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.model.CustomerModel;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/final")
public class FinalApplicationController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/saveData")//http://localhost:8080/final/saveData
    public String saveCustomerDetails(@RequestBody List<CustomerModel>customerModels) {
        System.out.println("The list of size is::" + customerModels.size());
        String result = this.customerService.saveCustomerDetails(customerModels);
        if (("success").equalsIgnoreCase(result)) {
            System.out.println("data save successfully");
            return "Data save successfully";
        } else {
            return result;
        }
    }
        //Fetch the data from data base
        @GetMapping(value = "/fetchCustomerInfo",produces = MediaType.APPLICATION_JSON_VALUE)//http://localhost:8080/final/fetchCustomerInfo
                public CustomerModel fetchRecords(@RequestParam(name = "customerId") String customerId){
            String []str=customerId.split(",");
            Integer cId=Integer.parseInt(str[0]);
            Customer retrievedCustomer = this.customerService.fetchDetailsForSelectCustomer(cId);
            CustomerModel finalCustomerModel=this.customerService.populatedCustomerModel(retrievedCustomer);
            return  finalCustomerModel;
        }
//Fetch all the data records from Db//http://localhost:8080/final/fetchAllCustomerInfo
        @GetMapping(value = "/fetchAllCustomerInfo",produces = MediaType.APPLICATION_JSON_VALUE)
                public List<CustomerModel>fetchAllCustomerInfo(){
            List<Customer>customerList=this.customerService.fetchDetailsAllCustomer();
            List<CustomerModel>customerModels=this.customerService.populatedAllCustomerModel(customerList);
            return customerModels;
        }
        // update customer records using information
        @PutMapping("/updateCustomerInfo")
                public String updateCustomerInfo(@RequestBody CustomerModel customerModel){
            String result=this.customerService.updateCustomerInfo(customerModel);
            return result;
        }
        //update userDetails using Id
        @PutMapping("/updateInfoBaseOnCustomerId")
                public String updateInfoBaseOnCustomerId(@RequestParam("customerId")Integer customerId,@RequestBody CustomerModel customerModel){
            String result =this.customerService.updateInfoBaseOnCustomerId(customerId, customerModel);
            return  result;
        }
        //Delete records from database
        @DeleteMapping("/deleteRecords")
                public Boolean deleteRecords(@RequestParam("customerId")Integer customerId){
              return this.customerService.deleteRecords(customerId);

        }
    }

