package com.api.waste.waste_residue.controllers;

import com.api.waste.waste_residue.models.WasteResidue;
import com.api.waste.waste_residue.repository.WasteResidueRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/waste-residues")
public class WasteResidueController {

    @Autowired
    private WasteResidueRepository wasteResidueRepository;

    // Qualquer estudante ou admin pode visualizar os resíduos
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public List<WasteResidue> getAllResidues() {
        return wasteResidueRepository.findAll();
    }

    // Apenas estudante pode criar registro de resíduo
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public WasteResidue createResidue(@RequestBody @Valid WasteResidue wasteResidue) {
        return wasteResidueRepository.save(wasteResidue);
    }

    // Apenas admin pode atualizar informações de resíduos
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public WasteResidue updateResidue(@PathVariable Integer id, @RequestBody @Valid WasteResidue residueDetails) {
        WasteResidue wasteResidue = wasteResidueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WasteResidue not found with id: " + id));
        wasteResidue.setAmountKg(residueDetails.getAmountKg());
        wasteResidue.setStudent(residueDetails.getStudent());
        wasteResidue.setTypeWasteResidue(residueDetails.getTypeWasteResidue());
        return wasteResidueRepository.save(wasteResidue);
    }

    // Apenas admin pode deletar resíduos
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteResidue(@PathVariable Integer id) {
        wasteResidueRepository.deleteById(id);
    }
}
