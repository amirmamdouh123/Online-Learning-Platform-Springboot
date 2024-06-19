package com.learning.OnlineLearning.Repos;

import com.learning.OnlineLearning.Entities.Course;
import com.learning.OnlineLearning.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {

    public Optional<Course> findByName(String CourseName);

}
