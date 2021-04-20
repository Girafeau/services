package org.miage.courses.entities.episode;

import org.miage.courses.entities.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeResource extends JpaRepository<Episode, String> {
}
