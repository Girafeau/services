package org.miage.courses.entities.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class Card implements Serializable {

    private String id;
    private int number;
    private int code;
    private String owner;
    private String date;
    private double amount;
}
