package org.miage.courses.controllers.user;

import org.miage.courses.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> all();

    User save(User user);

    Optional<User> find(String id);

}
