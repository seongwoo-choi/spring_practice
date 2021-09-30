package com.example.myrestfulservices.user;

// HTTP Status code
// 2XX => OK
// 4XX => Client
// 5XX => Server
// 실행 시 발생하는 오류 => RuntimeExceptions

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 이 예외 클래스는 404 status code 를 보낸다.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
