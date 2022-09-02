package com.example.currencyalerts.Services;

import com.example.currencyalerts.Exceptions.UserNotFoundException;
import com.example.currencyalerts.Models.User;
import com.example.currencyalerts.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findUserById(int id) throws UserNotFoundException {

        Optional<User> oUser = repository.findById(id);

        if (!oUser.isPresent()) {
            throw new UserNotFoundException();
        }

        return oUser.get();
    }

    public User addUser(User user){
        return repository.save(user);
    }

    public void deleteUserById(int id){
        repository.deleteById(id);
    }

    public User updateUser(int id, User user){
        return repository.findById(id)
                .map(newUser -> {
                    newUser.setUserName(user.getUserName());
                    return repository.save(newUser);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return repository.save(user);
                });
    }

}