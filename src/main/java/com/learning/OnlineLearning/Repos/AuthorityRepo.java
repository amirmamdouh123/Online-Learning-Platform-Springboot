package com.learning.OnlineLearning.Repos;

import com.learning.OnlineLearning.Entities.Authority;
import com.learning.OnlineLearning.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepo extends JpaRepository<Authority,Long> {

    public Optional<Authority> getByName(String name);



}
