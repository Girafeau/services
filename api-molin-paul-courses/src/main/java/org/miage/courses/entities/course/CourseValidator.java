package org.miage.courses.entities.course;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Service
public class CourseValidator {

    private Validator validator;

    CourseValidator(Validator validator) {
        this.validator = validator;
    }

    public void validate(CourseInput course) {
        Set<ConstraintViolation<CourseInput>> violations = validator.validate(course);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
