package com.example.IITB.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.IITB.Entity.Course;
import com.example.IITB.Entity.CourseInstance;
import com.example.IITB.Repo.CourseRepository;
import com.example.IITB.Repo.CourseInstanceRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseInstanceRepository courseInstanceRepository;

    @Transactional
    public void deleteCourseById(String courseId) {
        // Delete all CourseInstances with the given courseId
        courseInstanceRepository.deleteBycourseId(courseId);

        // Delete the Course
        courseRepository.deleteById(courseId);
    }
}
