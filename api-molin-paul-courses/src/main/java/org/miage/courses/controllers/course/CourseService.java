package org.miage.courses.controllers.course;

import org.miage.courses.entities.course.Course;
import org.miage.courses.entities.episode.Episode;
import org.miage.courses.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> all();

    Course save(Course course);

    Optional<Course> find(String id);

    Course delete(Course course);

    Course update(Course course);

}
