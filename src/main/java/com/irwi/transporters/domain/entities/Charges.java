package com.irwi.transporters.domain.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.irwi.transporters.domain.enums.StatesCharges;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "charges")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Charges extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatesCharges states;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "pallet_id", nullable = false)
    @JsonBackReference
    private Pallets pallet;

}
