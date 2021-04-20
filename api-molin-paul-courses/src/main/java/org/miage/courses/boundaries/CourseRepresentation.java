package org.miage.courses.boundaries;

import org.miage.courses.controllers.course.CourseService;
import org.miage.courses.controllers.episode.EpisodeService;
import org.miage.courses.entities.course.Course;
import org.miage.courses.entities.course.CourseInput;
import org.miage.courses.entities.course.CourseValidator;
import org.miage.courses.entities.episode.Episode;
import org.miage.courses.entities.episode.EpisodeInput;
import org.miage.users.boundaries.UserRepresentation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value="/api/courses", produces= MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Course.class)
public class CourseRepresentation {

    private final CourseService courseService;

    private final CourseValidator validator;

    private final EpisodeService episodeService;

    public CourseRepresentation(CourseService courseService, CourseValidator validator, EpisodeService episodeService) {
        this.courseService = courseService;
        this.validator = validator;
        this.episodeService = episodeService;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        Iterable<Course> all = this.courseService.all();
        return ResponseEntity.ok(courseToResource(all));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        return Optional.ofNullable(this.courseService.find(id)).filter(Optional::isPresent)
                .map(course -> ResponseEntity.ok(courseToResource(course.get(), true)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        Optional<Course> optional = this.courseService.find(id);
        optional.ifPresent(course -> this.courseService.delete(course));
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid CourseInput input) {
        Course course = input.transform();
        Course saved = this.courseService.save(course);
        URI location = linkTo(UserRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{id}/episodes")
    @Transactional
    public ResponseEntity<?> save(@PathVariable("id") String id, @RequestBody @Valid EpisodeInput input) {
        Episode episode = input.transform();
        Optional<Course> optional = this.courseService.find(id);
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Episode saved = this.episodeService.save(optional.get(), episode);
        URI location = linkTo(UserRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody @Valid CourseInput input) {
        Optional<Course> optional = this.courseService.find(id);
        if (optional.isPresent()) {
            Course course = input.transform();
            course.setId(optional.get().getId());
            this.courseService.update(course);
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> partialUpdate(@PathVariable("id") String id, @RequestBody Map<Object, Object> fields) {
        Optional<Course> optional = this.courseService.find(id);
        if (optional.isPresent()) {
            Course course = optional.get();
            fields.forEach((f, v) -> {
                Field field = ReflectionUtils.findField(Course.class, f.toString());
                field.setAccessible(true);
                ReflectionUtils.setField(field, course, v);
            });
            CourseInput input = new CourseInput(course.getTheme(), course.getTitle(), course.getDescription(), course.getPrice());
            validator.validate(input);
            course.setId(course.getId());
            this.courseService.update(course);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    private CollectionModel<EntityModel<Course>> courseToResource(Iterable<Course> courses) {
        Link self = linkTo(methodOn(CourseRepresentation.class).all()).withSelfRel();
        List<EntityModel<Course>> resources = new ArrayList();
        courses.forEach(course -> resources.add(courseToResource(course, false)));
        return  CollectionModel.of(resources, self);
    }

    private EntityModel<Course> courseToResource(Course course, Boolean collection) {
        var self = linkTo(CourseRepresentation.class).slash(course.getId()).withSelfRel();
        if (Boolean.TRUE.equals(collection)) {
            Link collectionLink = linkTo(methodOn(CourseRepresentation.class).all()).withRel("collection");
            return EntityModel.of(course, self, collectionLink);
        } else {
            return EntityModel.of(course, self);
        }
    }
}
