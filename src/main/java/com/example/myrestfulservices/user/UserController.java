package com.example.myrestfulservices.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
        User user = service.findOne(id);

        if ( user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }

    // @Valid => User 도메인 클래스에서 설정한 값을 바탕으로 유효성 검사를 한다.
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User saveUser = service.save(user);

        // server 에서 반환하려는 값을 response 에 담기 위해 ServletUriComponentsBuilder 을 사용한다.
        // 입력받은 도메인 값 + 만들어진 id 가 포함된 주소를 만들어주는 로직
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();

        // response 에 location 속성을 만들어서 값을 반환한다.
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if ( user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    @PutMapping("/users/{id}")
    public void putUser(@PathVariable int id, @RequestBody User user){
        User savedUser = service.putById(user, id);

        if (savedUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

}
