package org.miage.users.entities.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardInput {

    @NotNull
    private int number;
    @NotNull
    private int code;
    @NotBlank
    @NotNull
    private String owner;
    @NotBlank
    @NotNull
    private String date;
    @NotNull
    private double amount;


    public Card transform() {
        return new Card(this.number, this.code, this.owner, this.date, this.amount);
    }

}
