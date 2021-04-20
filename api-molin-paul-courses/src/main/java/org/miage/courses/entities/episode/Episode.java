package org.miage.courses.entities.episode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class Episode {

    @Id
    private String id;
    private String title;
    private String description;
    private String source;

    public Episode(String title, String description, String source) {
        this.title = title;
        this.description = description;
        this.source = source;
    }

    public Episode() {

    }
}
