package org.miage.courses.entities.episode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeInput {

    private String title;
    private String description;
    private String source;
    private String courseId;

    public Episode transform() {
        return new Episode(this.title, this.description, this.source);
    }
}
