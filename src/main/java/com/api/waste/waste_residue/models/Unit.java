package com.api.waste.waste_residue.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    @Id
    @Column(name = "id_unit")
    private Integer idUnit;

    private String name;
    private String address;
}