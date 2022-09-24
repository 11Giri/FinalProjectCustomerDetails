package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.model.RoleModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String saveUserDetails(List<UserModel> userModels) {
        UserValidator userValidator = new UserValidator();
        try {
            for (UserModel userModel: userModels) {// 3 user
                User user = new User();
                if (userValidator.validateUserName(userModel.getFirstName(), userModel.getLastName())) {
                    user.setFirstName(userModel.getFirstName());
                    user.setLastName(userModel.getLastName());
                } else {
                    return "User Name is Incorrect!!!";
                }

                if (userValidator.validateEmail(userModel.getEmail())) {
                    user.setEmail(userModel.getEmail());
                } else {
                    return "Email Id is Incorrect.. Please use @!!";
                }

                user.setPassword(userModel.getPassword());
                List<RoleModel> roleModels = userModel.getRole();
                for(RoleModel roleModel : roleModels) {
                    Role role = new Role();
                    role.setName(roleModel.getName());
                    role.setDept(roleModel.getDept());
                    user.addRoles(role);
                }
                this.userRepository.save(user);
            }
        } catch(Exception e) {
            System.err.println("Error details ::" + e.getMessage());
            return e.getMessage();
        }

        return "success";
    }

    public User fetchDetailsForSelectedUser (Integer userId) {
        User user  = new User();
        Optional<User> result = this.userRepository.findById(userId);
        if (result != null && !result.isEmpty()) {
            user = result.get();
        }
        return user;
    }

    public UserModel populateUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setEmail(user.getEmail());
        userModel.setLastName(user.getLastName());
        userModel.setFirstName(user.getFirstName());
        userModel.setPassword(user.getPassword());
        List<RoleModel> roleModels = new ArrayList<>();
        Set<Role> roleList = user.getRoles();
        for(Role role: roleList) {
            RoleModel roleModel = new RoleModel();
            roleModel.setName(role.getName());
            roleModel.setDept(role.getDept());
            roleModels.add(roleModel);
        }
        userModel.setRole(roleModels);
        return userModel;
    }

    public List<User> fetchDetailsAllUser() {
        List<User> result = (List<User>)this.userRepository.findAll();
        if (result != null && !result.isEmpty()) {
            return result;
        }
        return new ArrayList<>();
    }

    public List<UserModel> populateAllUserModel(List<User> userList) {
        List<UserModel> userModels = new ArrayList<>();
        for (User user: userList) {
            UserModel userModel = new UserModel();
            userModel.setFirstName(user.getFirstName());
            userModel.setLastName(user.getLastName());
            userModel.setPassword(user.getPassword());
            userModel.setEmail(user.getEmail());
            List<RoleModel> allRoleModel = new ArrayList<>();
            Set<Role> roleList = user.getRoles();
            for(Role role: roleList) {
                RoleModel roleModel = new RoleModel();
                roleModel.setName(role.getName());
                roleModel.setDept(role.getDept());
                allRoleModel.add(roleModel);
            }
            userModel.setRole(allRoleModel);
            userModels.add(userModel);
        }
        return userModels;
    }

    public String updateUserInfo(UserModel userModel) {
        User user = this.userRepository.fetchUserByName(userModel.getFirstName(), userModel.getLastName());
        if(user != null) {
            user.setEmail(userModel.getEmail());
            user.setPassword(userModel.getPassword());
            this.userRepository.save(user);
        }
        return "Success";
    }

    public String updateInfoBasedOnUserId(Integer userId, UserModel userModel) {
        Optional<User> result = this.userRepository.findById(userId);
        if (result != null) {
            User user = result.get();

            user.setFirstName(userModel.getFirstName());
            user.setLastName(userModel.getLastName());
            user.setEmail(userModel.getEmail());
            user.setPassword(user.getPassword());

            this.userRepository.save(user);
        }

        return "Success";
    }

    public Boolean deleteRecords(Integer userId) {
        this.userRepository.deleteById(userId);
        return true;
    }
}

