package com.example.myrestfulservices.helloWorld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    // @RequestMapping(value="/hello-world", method= RequestMethod.GET)
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "hello world";
    }

    // JSON 타입으로 값이 반환된다.
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("hello world");
    }

    @GetMapping(path = "hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        // return new HelloWorldBean("HelloWorld" + name);
        return new HelloWorldBean(String.format("HelloWorld, %s", name));
    }

    @GetMapping(path = "/hello-world-internationalized")
    // 헤더에 name 으로 값이 올 수 있고, 필수값은 아니다. 헤더에 값이 실려있지 않으면 디폴트 locale 값을 사용한다.
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
