package org.miage.users.controllers.user;

import org.miage.users.entities.user.User;
import org.miage.users.entities.card.Card;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> all();

    User save(User user);

    Optional<User> find(String id);

    User delete(User user);

    User update(User user);

    List<User> findByStatus(String status);

    List<Card> allCards(String id);

}
