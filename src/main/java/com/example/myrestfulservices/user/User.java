package com.example.myrestfulservices.user;

import lombok.AllArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class User {
    private Integer id;

    @Size(min=2, message = "Name 은 두글자 이상 입력해주세요.")
    private String name;
    @Past
    private Date joinDate;
}
