package com.techelevator.dao;

import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    User getUserById(Long userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

   // boolean create(String username, String password, String role);
   boolean create(RegisterUserDTO user);

   RegisterUserDTO findUserByUsername(String username);

    // updating existing user details
    // object received will have a valid username
    void updateUserDetails(String username, RegisterUserDTO registeredUser);

}
