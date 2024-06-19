package com.learning.OnlineLearning.Services;

import com.learning.OnlineLearning.Entities.Course;
import com.learning.OnlineLearning.Repos.CourseRepo;
import com.learning.OnlineLearning.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepo courseRepo;


    public List<Course> getAllCourses(){
        return courseRepo.findAll();
    }

    public Course getCourseById(Long id) throws NameNotFoundException {
        Optional<Course> course=courseRepo.findById(id);
        if(course.isPresent()){
            return course.get();
        }
        throw new NameNotFoundException("Id is not Matched..");
    }


}
