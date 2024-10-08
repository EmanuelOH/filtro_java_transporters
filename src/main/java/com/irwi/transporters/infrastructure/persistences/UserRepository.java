package com.irwi.transporters.infrastructure.persistences;

import com.irwi.transporters.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsernameOrEmail (String username, String email);
}
