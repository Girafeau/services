package org.miage.courses.entities.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseResource extends JpaRepository<Course, String> {
}
