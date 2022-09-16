package com.example.currencyalerts.Controllers;

import com.example.currencyalerts.Models.User;
import com.example.currencyalerts.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class UserController{
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public List<User> findAll() {
        return service.findAll();
    }

    @PostMapping("users")
    public User addUser(@RequestParam (name = "role") User.Role role, @RequestParam (name = "userName") String userName,
                        @RequestParam (name = "firstName") String firstName, @RequestParam (name = "lastName") String lastName,
                        @RequestParam (name = "email") String email, @RequestParam (name = "phoneNumber") String phoneNumber){
        User user = new User(role, userName, firstName, lastName, email, phoneNumber);
        return service.addUser(user);
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable (value="id") int id ){
        service.deleteUserById(id);
    }

    @PutMapping("users/{id}")
    public User updateUser(@PathVariable (value="id") int id,@RequestBody User user){
        return service.updateUser(id,user);
    }

}