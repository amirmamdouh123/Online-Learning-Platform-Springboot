package com.learning.OnlineLearning.Controllers;

import com.learning.OnlineLearning.DTOS.LoginUser;
import com.learning.OnlineLearning.Entities.Authority;
import com.learning.OnlineLearning.Entities.User;
import com.learning.OnlineLearning.JwtUtities.JwtService;
import com.learning.OnlineLearning.Services.AuthorityService;
import com.learning.OnlineLearning.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
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

    @Autowired
    AuthorityService authorityService;


    @PostMapping("register")
    public User insertUser(@RequestBody User user){
        System.out.println("register");
        List<Authority> userAuthorities =user.getAuthorities()
                .stream().map((auth)->
                            authorityService.getById( ( (Authority) auth).getId()) )
                .toList();
        user.setAuthorities(userAuthorities);
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

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByName(@PathVariable("email") String email){
        try {
            System.out.println("email: "+email);
            return ResponseEntity.ok(userService.getUserByEmail(email));
        }
        catch (NameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
