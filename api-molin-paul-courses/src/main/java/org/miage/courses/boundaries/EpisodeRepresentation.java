package org.miage.courses.boundaries;

import org.miage.courses.controllers.course.CourseService;
import org.miage.courses.controllers.episode.EpisodeService;
import org.miage.courses.entities.course.Course;
import org.miage.courses.entities.episode.Episode;
import org.miage.courses.entities.episode.EpisodeInput;
import org.miage.courses.entities.purchase.Purchase;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value="/api/episodes", produces= MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Episode.class)
public class EpisodeRepresentation {

    private final CourseService courseService;

    private final EpisodeService episodeService;

    public EpisodeRepresentation(CourseService courseService, EpisodeService episodeService) {
        this.courseService = courseService;
        this.episodeService = episodeService;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        Iterable<Episode> all = this.episodeService.all();
        return ResponseEntity.ok(episodeToResource(all));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        return Optional.ofNullable(this.episodeService.find(id)).filter(Optional::isPresent)
                .map(course -> ResponseEntity.ok(episodeToResource(course.get(), true)))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid EpisodeInput input) {
        Episode episode = input.transform();
        Optional<Course> course = this.courseService.find(input.getCourseId());
        if (course.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Episode saved = this.episodeService.save(course.get(), episode);
        URI location = linkTo(EpisodeRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    private CollectionModel<EntityModel<Episode>> episodeToResource(Iterable<Episode> episodes) {
        Link self = linkTo(methodOn(EpisodeRepresentation.class).all()).withSelfRel();
        List<EntityModel<Episode>> resources = new ArrayList();
        episodes.forEach(episode -> resources.add(episodeToResource(episode, false)));
        return  CollectionModel.of(resources, self);
    }

    private EntityModel<Episode> episodeToResource(Episode episode, Boolean collection) {
        var self = linkTo(EpisodeRepresentation.class).slash(episode.getId()).withSelfRel();
        if (Boolean.TRUE.equals(collection)) {
            Link collectionLink = linkTo(methodOn(EpisodeRepresentation.class).all()).withRel("collection");
            return EntityModel.of(episode, self, collectionLink);
        } else {
            return EntityModel.of(episode, self);
        }
    }

}
