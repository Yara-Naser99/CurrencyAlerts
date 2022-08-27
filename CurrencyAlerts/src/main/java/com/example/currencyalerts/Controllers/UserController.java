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

    @GetMapping("/users/{id}")
    public User findUserById( @PathVariable(value="id") int id) throws Exception {
        return service.findUserById(id);
    }

    @PostMapping("users")
    public User addUser(@RequestBody User user){
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