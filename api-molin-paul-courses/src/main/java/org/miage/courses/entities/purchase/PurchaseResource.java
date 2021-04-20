package org.miage.courses.entities.purchase;

import org.miage.courses.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseResource extends JpaRepository<Purchase, String> {
}
