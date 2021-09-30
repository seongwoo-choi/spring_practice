package com.example.myrestfulservices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// HelloWorldBean 의 모든 프로퍼티에 대한 게터, 세터가 만들어진다.
@Data
// 생성자 생성
@AllArgsConstructor
// 매개변수가 없는 디폴트 생성자를 생성
@NoArgsConstructor
public class HelloWorldBean {
    private String message;
}
