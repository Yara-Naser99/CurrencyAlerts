package com.example.currencyalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.currencyalerts.Models.User;
import com.example.currencyalerts.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.*;

@DirtiesContext
@SpringBootTest
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        User user = getUser();
        userRepository.save(user);
        Optional<User> oResult = userRepository.findById(user.getId());
        User result;
        if (oResult.isPresent()) {
            result = oResult.get();
            assertEquals(user.getId(), result.getId());
        }
        cleanUp(user);
    }

    @Test
    public void testFindAll() {
        User user = getUser();
        userRepository.save(user);
        List<User> result = new ArrayList<>(userRepository.findAll());
        assertEquals(result.size(), userRepository.count());
        cleanUp(user);
    }

    @Test
    public void testUpdateUser() {
        User user = getUser();
        userRepository.save(user);
        user.setEmail("newEmail");
        Optional<User> oResult = userRepository.findById(user.getId());
        if (oResult.isPresent()) {
            assertEquals("newEmail", user.getEmail());
        }
        cleanUp(user);
    }

    @Test
    public void testDeleteById() {
        User user = getUser();
        userRepository.save(user);
        userRepository.deleteById(user.getId());
        Optional<User> oUser = userRepository.findById(user.getId());
        assertEquals(oUser, Optional.empty());
    }

    private User getUser() {
        User user = new User(User.Role.USER, "userName", "firstName", "lastName", "email", "phoneNumber");
        return user;
    }

    private void cleanUp(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            userRepository.deleteById(user.getId());
        }
    }
}
