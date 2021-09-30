package com.example.myrestfulservices.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    // 생성자를 통해 의존성 주입
    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    // RequestMapping("/admin") => 모든 url 의 프리픽스로 /admin 이 붙는다. => /admin/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // /admin/users/{id}
    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUsers(@PathVariable int id) {
        User user = service.findOne(id);

        if ( user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joniDate", "ssn", "password");

        MappingJacksonValue mapping = new MappingJacksonValue(user);

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        mapping.setFilters(filters);

        return mapping;
    }


}
