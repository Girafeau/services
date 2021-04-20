package org.miage.courses.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResource extends JpaRepository<User, String>  {
}
