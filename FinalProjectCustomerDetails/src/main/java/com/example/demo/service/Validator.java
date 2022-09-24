package com.example.demo.service;

public class Validator {
    public boolean validateCustomerName(String customerName){
        if(customerName!=null ){
            return true;
        }
        return  false;
    }

    public boolean validateCountry(String country){
        if(country!=null ){
            return true;
        }
        return  false;
    }
    public boolean validateMobileNumber(long mobileNumber){
        String mobNumber= String.valueOf(mobileNumber);
        if(mobNumber!=null && mobNumber.length()==10){
            return true;
        }
        return  false;
    }
}


