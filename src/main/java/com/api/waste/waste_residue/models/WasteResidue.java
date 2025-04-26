package com.api.waste.waste_residue.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "waste_residue")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WasteResidue {
    @Id
    @Column(name = "id_residue")
    private Integer idResidue;

    @Column(name = "amount_kg")
    private String amountKg;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "student_registration", referencedColumnName = "registration"),
            @JoinColumn(name = "student_unit", referencedColumnName = "unit_id_unit")
    })
    private Student student;

    @ManyToOne
    @JoinColumn(name = "type_residue")
    private TypeWasteResidue typeWasteResidue;
}