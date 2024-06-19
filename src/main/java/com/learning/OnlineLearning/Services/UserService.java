package com.learning.OnlineLearning.Services;

import com.learning.OnlineLearning.Entities.Authority;
import com.learning.OnlineLearning.Entities.Course;
import com.learning.OnlineLearning.Repos.UserRepo;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.learning.OnlineLearning.Entities.User;

import javax.naming.AuthenticationException;
import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthorityService authorityService;


    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user= userRepo.findByEmail(username);

        if(user.isPresent()){
            return user.get();
        }
        try {
            throw new AuthenticationException("email is not correct...");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

    }

    //register
    public User insertUser(User user){
        Optional<User> currentUser= userRepo.findByEmail(user.getEmail());
        if(currentUser.isPresent()){
            throw new RuntimeException("Email is already in use...");
        }
        if( user.getAuthorities() == null || user.getAuthorities().isEmpty()){  //by default the new user will be has Student roles
            Authority authority=authorityService.getByName("ROLE_Student");
            user.setAuthorities(List.of(authority));
        }
        return userRepo.save(user);
    }

    public User getUserByEmail(String  name) throws NameNotFoundException {
        Optional<User> course=userRepo.findByEmail(name);
        if(course.isPresent()){
            return course.get();
        }
        throw new NameNotFoundException("User email is not Matched..");
    }
}
