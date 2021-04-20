package org.miage.courses.boundaries;

import org.miage.courses.controllers.course.CourseService;
import org.miage.courses.controllers.purchase.PurchaseService;
import org.miage.courses.controllers.user.UserService;
import org.miage.courses.entities.course.Course;
import org.miage.courses.entities.episode.Episode;
import org.miage.courses.entities.purchase.Purchase;
import org.miage.courses.entities.purchase.PurchaseInput;
import org.miage.courses.entities.user.User;
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
@RequestMapping(value="/api/purchases", produces= MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Purchase.class)
public class PurchaseRepresentation {

    private final CourseService courseService;
    private final UserService userService;
    private final PurchaseService purchaseService;

    public PurchaseRepresentation(CourseService courseService, UserService userService, PurchaseService purchaseService) {
        this.courseService = courseService;
        this.userService = userService;
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        Iterable<Purchase> all = this.purchaseService.all();
        return ResponseEntity.ok(purchaseToResource(all));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        return Optional.ofNullable(this.purchaseService.find(id)).filter(Optional::isPresent)
                .map(purchase -> ResponseEntity.ok(purchaseToResource(purchase.get(), true)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid PurchaseInput input) {
        Optional<User> user = this.userService.find(input.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Course> course = this.courseService.find(input.getCourseId());
        if (course.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Purchase saved = this.purchaseService.purchase(user.get(), course.get());
        if(saved == null) {
            return ResponseEntity.status(403).build();
        }
        URI location = linkTo(PurchaseRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    private CollectionModel<EntityModel<Purchase>> purchaseToResource(Iterable<Purchase> purchases) {
        Link self = linkTo(methodOn(EpisodeRepresentation.class).all()).withSelfRel();
        List<EntityModel<Purchase>> resources = new ArrayList();
        purchases.forEach(purchase -> resources.add(purchaseToResource(purchase, false)));
        return  CollectionModel.of(resources, self);
    }

    private EntityModel<Purchase> purchaseToResource(Purchase purchase, Boolean collection) {
        var self = linkTo(PurchaseRepresentation.class).slash(purchase.getId()).withSelfRel();
        if (Boolean.TRUE.equals(collection)) {
            Link collectionLink = linkTo(methodOn(PurchaseRepresentation.class).all()).withRel("collection");
            return EntityModel.of(purchase, self, collectionLink);
        } else {
            return EntityModel.of(purchase, self);
        }
    }
}
