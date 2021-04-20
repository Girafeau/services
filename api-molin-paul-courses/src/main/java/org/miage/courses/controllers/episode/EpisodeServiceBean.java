package org.miage.courses.controllers.episode;

import org.miage.courses.entities.course.Course;
import org.miage.courses.entities.course.CourseResource;
import org.miage.courses.entities.episode.Episode;
import org.miage.courses.entities.episode.EpisodeResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EpisodeServiceBean implements EpisodeService {

    private final CourseResource courseResource;

    private final EpisodeResource episodeResource;

    public EpisodeServiceBean(CourseResource courseResource, EpisodeResource episodeResource) {
        this.courseResource = courseResource;
        this.episodeResource = episodeResource;
    }


    @Override
    public Episode save(Course course, Episode episode) {
        episode.setId(UUID.randomUUID().toString());
        course.getEpisodes().add(episode);
        this.courseResource.save(course);
        return episode;
    }

    @Override
    public List<Episode> all() {
        return this.episodeResource.findAll();
    }

    @Override
    public Optional<Episode> find(String id) {
        return this.episodeResource.findById(id);
    }
}
