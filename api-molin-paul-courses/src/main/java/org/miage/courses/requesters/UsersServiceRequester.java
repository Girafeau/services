package org.miage.courses.requesters;

import org.miage.courses.entities.card.Card;
import org.miage.courses.entities.user.User;

public interface UsersServiceRequester {

    Card[] getUserCards(User user);

    void patchCardAmount(Card card);
}
