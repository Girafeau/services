package org.miage.courses.entities.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CardList implements Serializable {

    private List<Card> cards;

    public CardList() {
        this.cards = new ArrayList<>();
    }
}
