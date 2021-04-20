package org.miage.courses.entities.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInput {

    @NotNull
    @NotBlank
    private String theme;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private double price;

    public Course transform() {
        return new Course(this.theme, this.description, this.title, this.price);
    }
}
