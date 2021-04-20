package org.miage.courses.controllers.view;

import org.miage.courses.entities.course.Course;
import org.miage.courses.entities.episode.Episode;
import org.miage.courses.entities.purchase.Purchase;
import org.miage.courses.entities.user.User;
import org.miage.courses.entities.view.View;

public interface ViewService {

    View view(User user, Episode episode);
}
