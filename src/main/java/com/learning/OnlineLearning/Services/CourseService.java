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

    public Course getCourseByName(String  name) throws NameNotFoundException {
        Optional<Course> course=courseRepo.findByName(name);
        if(course.isPresent()){
            return course.get();
        }
        throw new NameNotFoundException("Course Name is not Matched..");
    }

    public Course insertCourse(Course  course) throws NameNotFoundException {
        Optional<Course> alreadyFound = courseRepo.findByName(course.getName());
        if(alreadyFound.isPresent()){
            throw new NameNotFoundException("Course is already exists..");
        }
        return courseRepo.save(course);
    }

    public Course updateCourse(Long id,Course  course) throws NameNotFoundException {
        Optional<Course> alreadyFound = courseRepo.findById(id);
        if(alreadyFound.isPresent()){
            course.setId(id);
            return courseRepo.save(course);
        }
        throw new NameNotFoundException("Course Id is not Matched for the Update..");
    }

    public String deleteByName(String name) throws NameNotFoundException {
        Optional<Course> alreadyFound = courseRepo.findByName(name);
        if(alreadyFound.isPresent()){
            courseRepo.delete(alreadyFound.get());
            return "Deleted Successfully";
        }
        throw new NameNotFoundException("Course Id is not Matched for the Update..");
    }




}
