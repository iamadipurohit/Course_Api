package com.example.IITB.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "course_instances")
public class CourseInstance {

    @Id
    private String id;
    private int year;
    private int semester;

    // Storing only the courseId instead of a Course reference
    private String courseId;

    // Constructor
    public CourseInstance(String courseId, int year, int semester) {
        this.courseId = courseId;
        this.year = year;
        this.semester = semester;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
