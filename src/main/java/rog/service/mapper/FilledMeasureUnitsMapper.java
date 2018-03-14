package rog.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.FilledMeasureUnits;
import rog.domain.MeasureUnitsPurposes;
import rog.service.dto.FilledMeasureUnitsDTO;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilledMeasureUnitsMapper {

    @Autowired
    private GlossaryOfMeasureUnitsMapper glossaryOfMeasureUnitsMapper;

    public FilledMeasureUnitsDTO filledMeasureUnitsDTO(FilledMeasureUnits filledMeasureUnits) {
        return new FilledMeasureUnitsDTO(filledMeasureUnits);
    }

    public Set<FilledMeasureUnits> filledMeasureUnits(Set<FilledMeasureUnitsDTO> filledMeasureUnits) {
        return filledMeasureUnits
            .stream()
            .filter(Objects::nonNull)
            .map(this::filledMeasureUnit)
            .collect(Collectors.toSet());
    }

    public FilledMeasureUnits filledMeasureUnit(FilledMeasureUnitsDTO filledMeasureUnitsDTO) {

        if (Objects.isNull(filledMeasureUnitsDTO)) {
            return null;
        }

        FilledMeasureUnits filledMeasureUnits = new FilledMeasureUnits();
        filledMeasureUnits.setId(filledMeasureUnitsDTO.getId());
        filledMeasureUnits.setBaseValue(filledMeasureUnitsDTO.getBaseValue());
        filledMeasureUnits.setActualValue(filledMeasureUnitsDTO.getActualValue());
        filledMeasureUnits.setFinalValue(filledMeasureUnitsDTO.getFinalValue());
        if(filledMeasureUnitsDTO.getCostOfPurposeRealisation() != null) {
            filledMeasureUnits.setCostOfPurposeRealisation(BigDecimal.valueOf(filledMeasureUnitsDTO.getCostOfPurposeRealisation()));
        }
        filledMeasureUnits.setCreationDate(filledMeasureUnitsDTO.getCreationDate());
        filledMeasureUnits.setPurposeAccomplishmentStatus(filledMeasureUnitsDTO.getPurposeAccomplishmentStatus());
        filledMeasureUnits.setSaved(filledMeasureUnitsDTO.getSaved());

        filledMeasureUnits.setGlossaryOfMeasureUnits(glossaryOfMeasureUnitsMapper
            .glossaryOfMeasureUnit(filledMeasureUnitsDTO.getGlossaryOfMeasureUnitsDTO()));

        MeasureUnitsPurposes measureUnitsPurposes = new MeasureUnitsPurposes();
        measureUnitsPurposes.setId(filledMeasureUnitsDTO.getMeasureUnitsPurposesId());
        filledMeasureUnits.setMeasureUnitsPurposes(measureUnitsPurposes);

        return filledMeasureUnits;
    }
}
