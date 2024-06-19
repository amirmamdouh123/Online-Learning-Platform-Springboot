package com.learning.OnlineLearning.Controllers;

import com.learning.OnlineLearning.Entities.Course;
import com.learning.OnlineLearning.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NameNotFoundException;
import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    @PreAuthorize("hasRole('Student')")
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCourseById(@PathVariable("{id}") Long id){
        try {
            return ResponseEntity.ok(courseService.getCourseById(id));
        }
        catch (NameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
