package org.miage.users.controllers.card;

import org.miage.users.entities.card.Card;
import org.miage.users.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface CardService {

    List<Card> all();

    Card save(User user, Card card);

    Optional<Card> find(String id);

    Card delete(Card card);

    Card update(Card card);
}
