package com.example.myrestfulservices.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
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
    public MappingJacksonValue retrieveAllUsers() {

        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joniDate", "password");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    // /admin/users/{id} => /admin/v1/users/{id}
    // @GetMapping("/v1/users/{id}")

    // /admin/users/3/?version=2
    // GetMapping 에 두 가지 이상의 정보가 올 때는 value 안에 엔드포인트를 적어 넣는다.
    // {id}/ 로 끝나는 이유는 / 뒤에 version 이라는 정보가 추가로 전달되어야 하기 때문에 넣는 것이다.
    // @GetMapping(value = "/users/{id}/", params = "version=1")

    // header 에 키값으로 X-API-VERSION 밸류 값으로 1 이 오면 아래 컨트롤러에서 값을 처리
    // @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")

    // mime 타입을 이용하는 방법
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        User user = service.findOne(id);

        if ( user == null) {
                    throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // filter 생성 filterOutAllExcept() 안에 존재하는 속성들을 불러옴
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joniDate", "ssn", "password");

        MappingJacksonValue mapping = new MappingJacksonValue(user);

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        mapping.setFilters(filters);

        return mapping;
    }


    // @GetMapping("/v2/users/{id}")
    // @GetMapping(value = "/users/{id}/", params = "version=2")
    // @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = service.findOne(id);

        if ( user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // UserV2 객체 생성
        UserV2 userV2 = new UserV2();

        // User 객체의 값을 가져와서 User2 에 값을 카피하기 위한 작업
        // BeanUtils 빈 들 간에 필요한 유용한 기능들 지원, copyProperties => 해당하는 값을 카피하는 기능
        BeanUtils.copyProperties(user, userV2); // id, name, joinDate, password, ssn
        userV2.setGrade("VIP");

        // filter 생성 filterOutAllExcept() 안에 존재하는 속성들을 불러옴
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joniDate", "grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);


        mapping.setFilters(filters);

        return mapping;
    }


}
