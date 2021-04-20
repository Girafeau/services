package org.miage.courses.entities.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.miage.courses.entities.episode.Episode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class View {

    @Id
    private String id;
    private String date;
    @ManyToOne(cascade = CascadeType.ALL)
    private Episode episode;

}
