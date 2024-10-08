package com.irwi.transporters.infrastructure.persistences;

import com.irwi.transporters.domain.entities.Charges;
import com.irwi.transporters.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChargesRepository extends JpaRepository<Charges, Long> {
    List<Charges> findByUser (UserEntity user);
}
