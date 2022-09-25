package org.aibles.authentication.repository;

import java.util.Optional;
import org.aibles.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
}
