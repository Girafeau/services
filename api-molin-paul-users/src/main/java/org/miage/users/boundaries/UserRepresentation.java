package org.miage.users.boundaries;

import org.miage.users.controllers.card.CardService;
import org.miage.users.controllers.user.UserService;
import org.miage.users.entities.card.Card;
import org.miage.users.entities.card.CardInput;
import org.miage.users.entities.user.User;
import org.miage.users.entities.user.UserInput;
import org.miage.users.entities.user.UserValidator;
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
@RequestMapping(value="/api/users", produces= MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(User.class)
public class UserRepresentation {

    private final UserService userService;

    private final CardService cardService;

    private final UserValidator validator;

    public UserRepresentation(UserService userService, CardService cardService, UserValidator validator) {
        this.userService = userService;
        this.cardService = cardService;
        this.validator = validator;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        Iterable<User> all = this.userService.all();
        return ResponseEntity.ok(userToResource(all));
    }

    @GetMapping(params = "status")
    public ResponseEntity<?> findByStatus(@RequestParam("status") String status) {
        Iterable<User> all = this.userService.findByStatus(status);
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
        URI location = linkTo(UserRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        Optional<User> optional = this.userService.find(id);
        if (optional.isPresent()) {
            this.userService.delete(optional.get());
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody @Valid UserInput input) {
        Optional<User> optional = this.userService.find(id);
        if (optional.isPresent()) {
            User user = input.transform();
            user.setId(optional.get().getId());
            this.userService.update(user);
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<?> partialUpdate(@PathVariable("id") String id, @RequestBody Map<Object, Object> fields) {
        Optional<User> optional = this.userService.find(id);
        if (optional.isPresent()) {
            User user = optional.get();
            fields.forEach((f, v) -> {
                Field field = ReflectionUtils.findField(User.class, f.toString());
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, v);
            });
            UserInput input = new UserInput(user.getEmail(), user.getFirstname(), user.getLastname());
            validator.validate(input);
            user.setId(user.getId());
            this.userService.update(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/cards")
    public ResponseEntity<?> allCards(@PathVariable("id") String id) {
        Optional<User> optional = this.userService.find(id);
        if (optional.isPresent()) {
            Iterable<Card> all = this.userService.allCards(id);
            return ResponseEntity.ok(all);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/{id}/cards")
    @Transactional
    public ResponseEntity<?> save(@PathVariable("id") String id, @RequestBody @Valid CardInput input) {
        Card card = input.transform();
        Optional<User> optional = this.userService.find(id);
        if (!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Card saved = this.cardService.save(optional.get(), card);
        URI location = linkTo(CardRepresentation.class).slash(saved.getId()).toUri();
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
