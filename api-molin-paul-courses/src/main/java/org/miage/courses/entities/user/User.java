package org.miage.courses.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.miage.courses.entities.course.Course;
import org.miage.courses.entities.episode.Episode;
import org.miage.courses.entities.purchase.Purchase;
import org.miage.courses.entities.view.View;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class User {

    @Id
    private String id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Purchase> purchases;
    @OneToMany(cascade = CascadeType.ALL)
    private List<View> views;

    public User(String id) {
        this.id = id;
    }

    public User() {

    }
}
