package com.irwi.transporters.domain.entities;


import com.irwi.transporters.domain.enums.StatesPalets;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity(name = "pallets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Pallets extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long capacity_max;

    @Column(nullable = false)
    private Long current_capacity;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatesPalets state;

    @OneToMany(mappedBy = "pallet", cascade = CascadeType.ALL)
    private Set<Charges> charges;

}
