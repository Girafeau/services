package org.miage.courses.controllers.purchase;

import org.miage.courses.entities.course.Course;
import org.miage.courses.entities.episode.Episode;
import org.miage.courses.entities.purchase.Purchase;
import org.miage.courses.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {

    Purchase purchase(User user, Course course);

    Optional<Purchase> find(String id);

    List<Purchase> all();
}
