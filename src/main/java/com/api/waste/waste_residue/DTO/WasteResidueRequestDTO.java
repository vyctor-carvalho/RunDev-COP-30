package com.api.waste.waste_residue.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WasteResidueRequestDTO {
    private String amountKg;
    private Integer registration;
    private Integer unitId;
    private Integer typeWasteResidueId;
}
