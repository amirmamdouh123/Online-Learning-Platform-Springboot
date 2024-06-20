package com.learning.OnlineLearning.Entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity
@Table(name = "Authorities" , schema = "onelinelearning")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;


    @ManyToMany(mappedBy = "authorities")
    List<User> users;


    @Override
    public String getAuthority() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
