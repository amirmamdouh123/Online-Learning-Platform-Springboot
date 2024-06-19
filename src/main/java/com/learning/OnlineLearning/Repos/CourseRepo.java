package com.learning.OnlineLearning.Repos;

import com.learning.OnlineLearning.Entities.Course;
import com.learning.OnlineLearning.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {
}
