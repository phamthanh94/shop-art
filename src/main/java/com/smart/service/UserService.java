package com.smart.service;

import com.smart.constants.AppConstant;
import com.smart.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getUserByUserName(String username) {
        User user = new User();
        user.setName(AppConstant.USER_NAME);
        user.setPassword(AppConstant.PASSWORD);
        user.setRole("ROLE_ADMIN");
        user.setEmail(AppConstant.USER_NAME);
        return user;
    }
}
