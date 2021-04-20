package org.miage.courses.controllers.user;


import org.miage.courses.entities.user.User;
import org.miage.courses.entities.user.UserResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceBean implements UserService {

    private final UserResource resource;

    public UserServiceBean(UserResource resource) {
        this.resource = resource;
    }

    @Override
    public List<User> all() {
        return this.resource.findAll();
    }

    @Override
    public Optional<User> find(String id) {
        return this.resource.findById(id);
    }

    @Override
    public User save(User user) {
        return this.resource.save(user);
    }

}
