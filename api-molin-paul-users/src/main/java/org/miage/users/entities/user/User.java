package org.miage.users.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import org.miage.users.entities.card.Card;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NamedNativeQuery(name = "User.findByStatus", query = "SELECT * FROM user WHERE status = ?", resultClass = User.class)
@NamedNativeQuery(name = "User.allCards", query = "SELECT * FROM card INNER JOIN user_cards WHERE user_id = ?", resultClass = Card.class)
public class User implements Serializable  {

    @Id
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String createdAt;
    private String lastModifiedAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Card> cards;

    public User() {

    }

    public User(String email, String firstname, String lastname) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

}
