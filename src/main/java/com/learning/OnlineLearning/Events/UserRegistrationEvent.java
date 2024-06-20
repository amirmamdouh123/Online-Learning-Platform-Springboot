package com.learning.OnlineLearning.Events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class UserRegistrationEvent {

    String email;
}
