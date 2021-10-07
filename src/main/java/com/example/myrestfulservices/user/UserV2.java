package com.example.myrestfulservices.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
// value 에 해당하는 속성의 값들이 JSON 에 나타나지 않는다.
//@JsonIgnoreProperties(value={"password", "ssn"})
@JsonFilter("UserInfoV2")
public class UserV2 extends User {
    private String grade;
}
