package org.miage.users.controllers.user;

import org.miage.users.requesters.CoursesServiceRequester;
import org.miage.users.utils.DateUtils;
import org.miage.users.entities.card.Card;
import org.miage.users.entities.user.Status;
import org.miage.users.entities.user.User;
import org.miage.users.entities.user.UserResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserServiceBean implements UserService {

    private UserResource resource;

    private CoursesServiceRequester proxy;

    public UserServiceBean(UserResource resource, RestTemplate restTemplate, CoursesServiceRequester proxy) {
        this.resource = resource;
        this.proxy = proxy;
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
        user.setId(UUID.randomUUID().toString());
        this.proxy.postUser(user);
        user.setStatus(Status.unverified);
        user.setCards(new ArrayList<Card>());
        user.setCreatedAt(DateUtils.now());
        user.setLastModifiedAt(DateUtils.now());
        return this.resource.save(user);
    }

    @Override
    public User delete(User user) {
        user.setStatus(Status.deleted);
        user.setLastModifiedAt(DateUtils.now());
        return this.resource.save(user);
    }

    @Override
    public User update(User user) {
        user.setLastModifiedAt(DateUtils.now());
        return this.resource.save(user);
    }

    @Override
    public List<User> findByStatus(String status) {
        return this.resource.findByStatus(status);
    }

    @Override
    public List<Card> allCards(String id) {
        return this.resource.allCards(id);
    }

}
