package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/firstTest") //http://localhost:8081/cart/firstTest?id=5
    public String getData() {
        return "Hello";
    }

    @GetMapping(value = "/secondTest") //http://localhost:8081/cart/secondTest
    public String getSecondData() {
        return "Hello Second";
    }

    //save the data to database
    @PostMapping(value = "/saveData") //http://localhost:8081/cart/saveData
    public String saveCustomerRecord(@RequestBody List<UserModel> userModels) {
        System.out.println("The list Size is ::" +userModels.size());
        String result = this.userService.saveUserDetails(userModels);
        if (("success").equalsIgnoreCase(result)) {
            return "Data Save Successfully";
        } else {
            return result;
        }
    }

    //Fetch  the data from database
    @GetMapping(value = "/fetchUserInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel fetchRecords(@RequestParam(name="userId") Integer userId) {
        User retrievedUser = this.userService.fetchDetailsForSelectedUser(userId);
        UserModel finalUserModel = this.userService.populateUserModel(retrievedUser);
        return finalUserModel;
    }

    //Fetch all the records from DB
    @GetMapping(value = "/fetchAllUserInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserModel> fetchAllUserInfo() {
        List<User> userList = this.userService.fetchDetailsAllUser();
        List<UserModel> userModels = this.userService.populateAllUserModel(userList);
        return userModels;
    }

    //Updating records
    @PutMapping("/updateUserInfo")
    public String updateUserInfo(@RequestBody UserModel userModel) {
        String result = this.userService.updateUserInfo(userModel);
        return result;
    }

    @PutMapping("/updateInfoBasedOnUserId")
    public String updateInfoBasedOnUserId(@RequestParam("userId") Integer userId, @RequestBody UserModel userModel) {
        String result = this.userService.updateInfoBasedOnUserId(userId, userModel);
        return result;
    }

    //Deleting records
    @DeleteMapping("/deleteRecords")
    public Boolean deleteRecords(@RequestParam("userId") Integer userId) {
        return this.userService.deleteRecords(userId);
    }
}

