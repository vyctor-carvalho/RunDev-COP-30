package com.api.waste.waste_residue.controllers;

import com.api.waste.waste_residue.models.TypeWasteResidue;
import com.api.waste.waste_residue.repository.TypeWasteResidueRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resisted/type-waste-residues")
@CrossOrigin
public class TypeWasteResidueController {

    @Autowired
    private TypeWasteResidueRepository typeWasteResidueRepository;

    // Qualquer estudante ou admin pode visualizar
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public List<TypeWasteResidue> getAllTypes() {
        return typeWasteResidueRepository.findAll();
    }

    // Só ADMIN pode criar
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public TypeWasteResidue createType(@RequestBody @Valid TypeWasteResidue typeWasteResidue) {
        return typeWasteResidueRepository.save(typeWasteResidue);
    }

    // Só ADMIN pode atualizar
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TypeWasteResidue updateType(@PathVariable Integer id, @RequestBody @Valid TypeWasteResidue typeDetails) {
        TypeWasteResidue typeWasteResidue = typeWasteResidueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Type not found with id: " + id));
        typeWasteResidue.setTypeName(typeDetails.getTypeName());
        return typeWasteResidueRepository.save(typeWasteResidue);
    }

    // Só ADMIN pode deletar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteType(@PathVariable Integer id) {
        typeWasteResidueRepository.deleteById(id);
    }
}
