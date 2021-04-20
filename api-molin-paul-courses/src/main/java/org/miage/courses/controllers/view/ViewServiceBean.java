package org.miage.courses.controllers.view;

import org.miage.courses.controllers.user.UserService;
import org.miage.courses.entities.episode.Episode;
import org.miage.courses.entities.user.User;
import org.miage.courses.entities.user.UserResource;
import org.miage.courses.entities.view.View;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ViewServiceBean implements ViewService {

    private final UserResource userResource;

    public ViewServiceBean(UserResource userResource) {
        this.userResource = userResource;
    }

    @Override
    public View view(User user, Episode episode) {
        View view = new View();
        view.setId(UUID.randomUUID().toString());
        view.setEpisode(episode);
        user.getViews().add(view);
        this.userResource.save(user);
        return view;
    }
}
