package org.miage.courses.controllers.episode;

import org.miage.courses.entities.course.Course;
import org.miage.courses.entities.episode.Episode;

import java.util.Optional;

public interface EpisodeService {

    Episode save(Course course, Episode episode);

    Iterable<Episode> all();

    Optional<Episode> find(String id);
}
