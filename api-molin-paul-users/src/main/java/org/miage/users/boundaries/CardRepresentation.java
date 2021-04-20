package org.miage.users.boundaries;


import org.miage.users.controllers.card.CardService;
import org.miage.users.controllers.user.UserService;
import org.miage.users.entities.card.Card;
import org.miage.users.entities.card.CardInput;
import org.miage.users.entities.user.User;
import org.miage.users.entities.card.CardValidator;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value="/api/cards", produces= MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Card.class)
public class CardRepresentation {


    private final CardService cardService;

    private final UserService userService;

    private final CardValidator validator;

    public CardRepresentation(CardService cardService, UserService userService, CardValidator validator) {
        this.cardService = cardService;
        this.userService = userService;
        this.validator = validator;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        Iterable<Card> all = this.cardService.all();
        return ResponseEntity.ok(cardToResource(all));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        return Optional.ofNullable(this.cardService.find(id)).filter(Optional::isPresent)
                .map(card -> ResponseEntity.ok(cardToResource(card.get(), true)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        Optional<Card> optional = this.cardService.find(id);
        optional.ifPresent(this.cardService::delete);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody @Valid CardInput input) {
        Optional<Card> optional = this.cardService.find(id);
        if (optional.isPresent()) {
            Card card = input.transform();
            card.setId(optional.get().getId());
            this.cardService.update(card);
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> partialUpdate(@PathVariable("id") String id, @RequestBody Map<Object, Object> fields) {
        Optional<Card> optional = this.cardService.find(id);
        if (optional.isPresent()) {
            Card card = optional.get();
            fields.forEach((f, v) -> {
                Field field = ReflectionUtils.findField(Card.class, f.toString());
                field.setAccessible(true);
                ReflectionUtils.setField(field, card, v);
            });
            CardInput input = new CardInput(card.getNumber(), card.getCode(), card.getOwner(), card.getDate(), card.getAmount());
            validator.validate(input);
            card.setId(card.getId());
            this.cardService.update(card);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CollectionModel<EntityModel<Card>> cardToResource(Iterable<Card> cards) {
        Link self = linkTo(methodOn(CardRepresentation.class).all()).withSelfRel();
        List<EntityModel<Card>> resources = new ArrayList();
        cards.forEach(card -> resources.add(cardToResource(card, false)));
        return  CollectionModel.of(resources, self);
    }

    private EntityModel<Card> cardToResource(Card card, Boolean collection) {
        var self = linkTo(CardRepresentation.class).slash("cards").slash(card.getId()).withSelfRel();
        if (Boolean.TRUE.equals(collection)) {
            Link collectionLink = linkTo(methodOn(CardRepresentation.class).all()).withRel("collection");
            return EntityModel.of(card, self, collectionLink);
        } else {
            return EntityModel.of(card, self);
        }
    }
}
