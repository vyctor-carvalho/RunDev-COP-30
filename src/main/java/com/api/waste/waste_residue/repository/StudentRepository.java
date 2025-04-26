package com.api.waste.waste_residue.repository;

import com.api.waste.waste_residue.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByRegistration(Integer registration);
}
