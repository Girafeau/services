package org.miage.users.entities.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.miage.users.entities.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
public class Card {

    @Id
    private String id;
    private int number;
    private int code;
    private String owner;
    private String date;
    private double amount;

    public Card() {

    }

    public Card(int number, int code, String owner, String date, double amount) {
        this.number = number;
        this.code = code;
        this.owner = owner;
        this.date = date;
        this.amount = amount;
    }
}
