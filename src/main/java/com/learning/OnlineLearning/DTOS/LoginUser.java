package com.learning.OnlineLearning.DTOS;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Setter
@Getter
public class LoginUser {

    String email;

    String password;

}
