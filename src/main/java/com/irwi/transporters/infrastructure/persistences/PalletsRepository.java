package com.irwi.transporters.infrastructure.persistences;

import com.irwi.transporters.domain.entities.Pallets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PalletsRepository extends JpaRepository<Pallets, Long> {
}
