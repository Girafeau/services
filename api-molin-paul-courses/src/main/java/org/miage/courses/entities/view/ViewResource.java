package org.miage.courses.entities.view;

import org.miage.courses.entities.episode.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewResource extends JpaRepository<View, String> {
}
