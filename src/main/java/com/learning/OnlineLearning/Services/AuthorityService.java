package com.learning.OnlineLearning.Services;

import com.learning.OnlineLearning.Entities.Authority;
import com.learning.OnlineLearning.Repos.AuthorityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    AuthorityRepo authorityRepo;

    public Authority getByName(String name){
        return authorityRepo.getByName(name).orElseThrow();
    }
    public Authority getById(Long id){
        return authorityRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("id is not found"));
    }


}
