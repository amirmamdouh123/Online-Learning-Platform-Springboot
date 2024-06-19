package com.learning.OnlineLearning.Controllers;

import com.learning.OnlineLearning.DTOS.LoginUser;
import com.learning.OnlineLearning.Entities.User;
import com.learning.OnlineLearning.JwtUtities.JwtService;
import com.learning.OnlineLearning.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ProviderManager providerManager;


    @Autowired
    JwtService jwtService;


    @PostMapping("register")
    public User insertUser(@RequestBody User user){
        System.out.println("register");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.insertUser(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("in")
    public String in() {
        return "in";
    }

    @PostMapping("login")
    public String login(@RequestBody LoginUser loginUser) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginUser.getEmail(),loginUser.getPassword());
        UsernamePasswordAuthenticationToken loggedIn =(UsernamePasswordAuthenticationToken)providerManager.authenticate(login);
        SecurityContextHolder.getContext().setAuthentication(loggedIn);

        UserDetails user = userService.loadUserByUsername(login.getName());
        String jwt= jwtService.generateJWT(user);
        return jwt;
    }

}
