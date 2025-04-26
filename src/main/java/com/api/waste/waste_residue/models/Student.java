package com.api.waste.waste_residue.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @Column(name = "registration")
    private Integer registration;

    private String name;
    private Integer age;
    private String semester;

    @Column(name = "shif")
    private String shift;

    @Column(name = "class")
    private String className;

    @ManyToOne
    @JoinColumn(name = "unit_id_unit")
    private Unit unit;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
}