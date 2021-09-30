package com.example.myrestfulservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
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
}
