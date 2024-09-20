package org.java5.demojava5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.java5.demojava5.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}