package com.example.IITB.Repo;

import com.example.IITB.Entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    // Additional custom query methods can be added here if needed
}
