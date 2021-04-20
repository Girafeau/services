package org.miage.users.controllers.card;

import org.miage.users.entities.card.Card;
import org.miage.users.entities.user.User;
import org.miage.users.entities.card.CardResource;
import org.miage.users.entities.user.UserResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardServiceBean implements CardService {

    private final CardResource cardResource;

    private final UserResource userResource;

    public CardServiceBean(CardResource cardResource, UserResource userResource) {
        this.cardResource = cardResource;
        this.userResource = userResource;
    }

    @Override
    public List<Card> all() {
        return this.cardResource.findAll();
    }

    @Override
    public Card save(User user, Card card) {
        card.setId(UUID.randomUUID().toString());
        user.getCards().add(card);
        this.userResource.save(user);
        return card;
    }

    @Override
    public Optional<Card> find(String id) {
        return this.cardResource.findById(id);
    }

    @Override
    public Card delete(Card card) {
        return null;
    }

    @Override
    public Card update(Card card) {
        return this.cardResource.save(card);
    }
}
