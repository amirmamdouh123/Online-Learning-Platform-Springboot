package com.learning.OnlineLearning.Controllers;

import com.learning.OnlineLearning.Entities.Course;
import com.learning.OnlineLearning.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    CourseService courseService;


    @Autowired


    @GetMapping  //all
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/id/{id}")
    //all
    public ResponseEntity<?> getCourseById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok(courseService.getCourseById(id));
        }
        catch (NameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCourseByName(@PathVariable("name") String name){
        try {
            System.out.println("name: "+name);
            return ResponseEntity.ok(courseService.getCourseByName(name));
        }
        catch (NameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> insertNewCourse(@RequestBody Course course){
        try {
            return ResponseEntity.ok(courseService.insertCourse(course));
        }
        catch (NameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('Admin') || hasRole('Instructor') ")
    public ResponseEntity<?> updateCourse(@PathVariable Long id ,@RequestBody Course course){
        try {
            return ResponseEntity.ok(courseService.updateCourse(id ,course));
        }
        catch (NameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/delete/{name}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> deleteCourse(@PathVariable("name") String name){
        try {
            return ResponseEntity.ok(courseService.deleteByName(name));
        }
        catch (NameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
