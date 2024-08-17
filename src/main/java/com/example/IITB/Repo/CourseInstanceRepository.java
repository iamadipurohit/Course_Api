package com.example.IITB.Repo;

import com.example.IITB.Entity.CourseInstance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseInstanceRepository extends MongoRepository<CourseInstance, String> {

    // Find all course instances by year and semester
    List<CourseInstance> findByYearAndSemester(int year, int semester);

    // Find a specific course instance by year, semester, and course ID
    Optional<CourseInstance> findByYearAndSemesterAndCourseId(int year, int semester, String courseId);

    void deleteBycourseId(String courseId);
}
