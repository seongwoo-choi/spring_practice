package com.example.myrestfulservices.exception;

import com.example.myrestfulservices.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
// 모든 컨트롤러가 실행될 때 ControllerAdvice 어노테이션을 가진 컨트롤러가 먼저 실행된다. 일종의 미들웨어라고 생각하면 될 듯?
@ControllerAdvice
public class customizeResponseEntityExceptionsHandler extends ResponseEntityExceptionHandler {

    // ExceptionHandler 어노테이션을 사용하여 핸들러로 컨테이너에 등록
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        // new Date() => 에러가 발생한 현재 시간, request.getDescription => request 의 자세한 설명을 표기할 것인지 true/false, ex.getMessage() => 에러 메세지
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // UserNotFoundException 이 발생하면 아래 핸들러에서 로직을 처리한다.
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
