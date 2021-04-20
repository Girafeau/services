package org.miage.courses.boundaries;


import org.miage.courses.controllers.episode.EpisodeService;
import org.miage.courses.controllers.user.UserService;
import org.miage.courses.controllers.view.ViewService;
import org.miage.courses.entities.episode.Episode;
import org.miage.courses.entities.user.User;
import org.miage.courses.entities.view.View;
import org.miage.courses.entities.view.ViewInput;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value="/api/views", produces= MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(View.class)
public class ViewRepresentation {


    private final UserService userService;
    private final EpisodeService episodeService;
    private final ViewService viewService;

    public ViewRepresentation(UserService userService, EpisodeService episodeService, ViewService viewService) {
        this.userService = userService;
        this.episodeService = episodeService;
        this.viewService = viewService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid ViewInput input) {
        Optional<User> user = this.userService.find(input.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Episode> episode = this.episodeService.find(input.getEpisodeId());
        if (episode.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        View saved = this.viewService.view(user.get(), episode.get());
        URI location = linkTo(PurchaseRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
