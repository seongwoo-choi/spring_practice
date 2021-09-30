package com.example.myrestfulservices.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    // 생성자를 통해 의존성 주입
    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUsers(@PathVariable int id) {
        return service.findOne(id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        User saveUser = service.save(user);
        return saveUser;
    }

}
