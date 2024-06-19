package com.learning.OnlineLearning.Repos;

import com.learning.OnlineLearning.Entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepo extends JpaRepository<Authority,Long> {

    public Optional<Authority> getByName(String name);
}
