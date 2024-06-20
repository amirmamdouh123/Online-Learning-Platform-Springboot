package com.learning.OnlineLearning.Repos;

import com.learning.OnlineLearning.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    public Optional<User> findByEmail(String email);

    @Query(value = "SELECT u.* FROM users u JOIN users_authorities ua ON u.id = ua.user_id JOIN authorities a ON a.id = ua.role_id WHERE a.name = :authority", nativeQuery = true)
    public List<User> getUsersByAuthority(@Param("authority") String authority);


}
