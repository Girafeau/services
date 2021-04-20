package org.miage.courses.entities.course;

import lombok.*;
import org.miage.courses.entities.episode.Episode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
public class Course {

    @Id
    private String id;
    private String theme;
    private String title;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Episode> episodes;
    private double price;
    private String createdAt;

    public Course(String theme, String description, String title, double price) {
        this.theme = theme;
        this.description = description;
        this.title = title;
        this.price = price;
    }

    public Course() {

    }
}
