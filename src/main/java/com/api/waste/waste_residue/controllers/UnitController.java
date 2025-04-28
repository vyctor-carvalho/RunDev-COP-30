package com.api.waste.waste_residue.controllers;

import com.api.waste.waste_residue.models.Unit;
import com.api.waste.waste_residue.repository.UnitRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resisted/units")
@CrossOrigin
public class UnitController {

    @Autowired
    private UnitRepository unitRepository;

    // Qualquer estudante ou admin pode visualizar
    @GetMapping("/getunit")
    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    // Só ADMIN pode criar unidade
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Unit createUnit(@RequestBody @Valid Unit unit) {
        return unitRepository.save(unit);
    }

    // Só ADMIN pode atualizar unidade
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Unit updateUnit(@PathVariable Integer id, @RequestBody @Valid Unit unitDetails) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + id));
        unit.setName(unitDetails.getName());
        unit.setAddress(unitDetails.getAddress());
        return unitRepository.save(unit);
    }

    // Só ADMIN pode deletar unidade
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUnit(@PathVariable Integer id) {
        unitRepository.deleteById(id);
    }
}
