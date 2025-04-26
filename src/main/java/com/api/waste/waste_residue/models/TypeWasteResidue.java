package com.api.waste.waste_residue.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "type_waste_residue")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeWasteResidue {
    @Id
    private Integer id;

    @Column(name = "type")
    private String typeName;
}