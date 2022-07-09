package com.example.nodayst.event.infra;

import com.example.nodayst.event.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findById(String id);
}
