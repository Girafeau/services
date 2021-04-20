package org.miage.courses.boundaries;

import org.miage.courses.controllers.user.UserService;
import org.miage.courses.entities.user.User;
import org.miage.courses.entities.user.UserInput;
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
@RequestMapping(value="/api/users", produces= MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(User.class)
public class UserRepresentation {

    private final UserService userService;

    public UserRepresentation(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        Iterable<User> all = this.userService.all();
        return ResponseEntity.ok(userToResource(all));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") String id) {
        return Optional.ofNullable(this.userService.find(id)).filter(Optional::isPresent)
                .map(user -> ResponseEntity.ok(userToResource(user.get(), true)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid UserInput input) {
        User user = input.transform();
        User saved = this.userService.save(user);
        URI location = linkTo(org.miage.users.boundaries.UserRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    private CollectionModel<EntityModel<User>> userToResource(Iterable<User> users) {
        Link self = linkTo(methodOn(UserRepresentation.class).all()).withSelfRel();
        List<EntityModel<User>> resources = new ArrayList();
        users.forEach(user -> resources.add(userToResource(user, false)));
        return  CollectionModel.of(resources, self);
    }

    private EntityModel<User> userToResource(User user, Boolean collection) {
        var self = linkTo(UserRepresentation.class).slash(user.getId()).withSelfRel();
        if (Boolean.TRUE.equals(collection)) {
            Link collectionLink = linkTo(methodOn(UserRepresentation.class).all()).withRel("collection");
            return EntityModel.of(user, self, collectionLink);
        } else {
            return EntityModel.of(user, self);
        }
    }


}
