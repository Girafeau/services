package org.miage.users.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.miage.users.entities.card.Card;
import javax.persistence.NamedQueries;
import java.util.Collection;
import java.util.List;

public interface UserResource extends JpaRepository<User, String> {

    List<User> findByStatus(String status);

    List<Card> allCards(String id);

}
