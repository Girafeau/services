package org.miage.courses.controllers.course;

import org.miage.courses.entities.course.Course;
import org.miage.courses.entities.course.CourseResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceBean implements CourseService {

    private final CourseResource resource;

    public CourseServiceBean(CourseResource resource) {
        this.resource = resource;
    }

    @Override
    public List<Course> all() {
        return this.resource.findAll();
    }

    @Override
    public Course save(Course course) {
        course.setId(UUID.randomUUID().toString());
        return this.resource.save(course);
    }

    @Override
    public Optional<Course> find(String id) {
        return this.resource.findById(id);
    }

    @Override
    public Course delete(Course course) {
        return null;
    }

    @Override
    public Course update(Course course) {
        return this.resource.save(course);
    }

}
