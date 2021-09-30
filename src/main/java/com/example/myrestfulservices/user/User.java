package com.example.myrestfulservices.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
// value 에 해당하는 속성의 값들이 JSON 에 나타나지 않는다.
//@JsonIgnoreProperties(value={"password", "ssn"})
@JsonFilter("UserInfo")
public class User {
    private Integer id;

    @Size(min=2, message = "Name 은 두글자 이상 입력해주세요.")
    private String name;
    @Past
    private Date joinDate;

    // JSON 에 아래 값들이 나타나지 않음
    // @JsonIgnore
    private String password;
    // @JsonIgnore
    private String ssn;
}
