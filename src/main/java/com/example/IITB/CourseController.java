package com.example.IITB;

import com.example.IITB.Entity.Course;
import com.example.IITB.Entity.CourseInstance;
import com.example.IITB.Repo.CourseInstanceRepository;
import com.example.IITB.Repo.CourseRepository;
import com.example.IITB.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;


    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseInstanceRepository courseInstanceRepository;

    // POST /api/courses - Create a new course
    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        System.out.println("Hello mitr");
        System.out.println(course.getTitle()+" "+course.getCourseCode()+" "+course.getDescription());
        Course savedCourse = courseRepository.save(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    // GET /api/courses - List of all courses available
    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        System.out.println("Hello bhai");
        return courseRepository.findAll();
    }

    // GET /api/courses/{id} - View detailed information about a course with ID = 23
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // DELETE /api/courses/{id} - Delete a course with ID = 24
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        try {
            courseService.deleteCourseById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST /api/instances - Create a new instance of a course delivery
    @PostMapping("/instances")
    public ResponseEntity<CourseInstance> createCourseInstance(@RequestBody CourseInstance instance) {
        try {
            // Check if the course with the given courseId exists
            Optional<Course> courseOpt = courseRepository.findById(instance.getCourseId());
            if (courseOpt.isPresent()) {
                // Save the CourseInstance
                CourseInstance savedInstance = courseInstanceRepository.save(instance);
                return new ResponseEntity<>(savedInstance, HttpStatus.CREATED);
            } else {
                // Course with given courseId not found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle the exception, e.g., log it and return a response indicating the error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // GET /api/instances/{year}/{semester} - List of courses delivered in year YYYY and semester N
    @GetMapping("/instances/{year}/{semester}")
    public ResponseEntity<List<Course>> getCoursesByYearAndSemester(
            @PathVariable int year, @PathVariable int semester) {

        // Fetch the CourseInstances for the given year and semester
        List<CourseInstance> instances = courseInstanceRepository.findByYearAndSemester(year, semester);

        // Extract unique courseIds from the instances
        List<String> courseIds = instances.stream()
                .map(CourseInstance::getCourseId)
                .distinct()
                .collect(Collectors.toList());

        // Fetch the Course objects corresponding to the courseIds
        List<Course> courses = courseRepository.findAllById(courseIds);

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }


    @GetMapping("/instances/{year}/{semester}/{courseId}")
    public ResponseEntity<CourseInstance> getCourseInstanceDetails(
            @PathVariable int year, @PathVariable int semester, @PathVariable String courseId) {

        Optional<CourseInstance> instance = courseInstanceRepository
                .findByYearAndSemesterAndCourseId(year, semester, courseId);

        return instance.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/instances/{year}/{semester}/{courseId}")
    public ResponseEntity<Void> deleteCourseInstance(
            @PathVariable int year, @PathVariable int semester, @PathVariable String courseId) {

        Optional<CourseInstance> instance = courseInstanceRepository
                .findByYearAndSemesterAndCourseId(year, semester, courseId);

        if (instance.isPresent()) {
            courseInstanceRepository.delete(instance.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
