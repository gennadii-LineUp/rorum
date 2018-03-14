package rog.service.mapper;

import org.springframework.stereotype.Service;
import rog.domain.GlossaryOfMeasureUnits;
import rog.domain.GlossaryOfPurposes;
import rog.domain.OrganisationStructure;
import rog.service.dto.GlossaryOfMeasureUnitsDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GlossaryOfMeasureUnitsMapper {

    public GlossaryOfMeasureUnitsDTO glossaryOfMeasureUnitsDTO(GlossaryOfMeasureUnits glossaryOfMeasureUnits) {
        return new GlossaryOfMeasureUnitsDTO(glossaryOfMeasureUnits);
    }

    public Set<GlossaryOfMeasureUnits> glossaryOfMeasureUnits(Set<GlossaryOfMeasureUnitsDTO> glossaryOfMeasureUnitsDTOS) {
        return glossaryOfMeasureUnitsDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(this::glossaryOfMeasureUnit)
            .collect(Collectors.toSet());
    }

    public GlossaryOfMeasureUnits glossaryOfMeasureUnit(GlossaryOfMeasureUnitsDTO glossaryOfMeasureUnitsDTO) {

        if (Objects.isNull(glossaryOfMeasureUnitsDTO)) {
            return null;
        }

        GlossaryOfMeasureUnits glossaryOfMeasureUnits = new GlossaryOfMeasureUnits();
        glossaryOfMeasureUnits.setId(glossaryOfMeasureUnitsDTO.getId());
        glossaryOfMeasureUnits.setName(glossaryOfMeasureUnitsDTO.getName());
        glossaryOfMeasureUnits.setFrequencyOfGatheringData(glossaryOfMeasureUnitsDTO.getFrequencyOfGatheringData());
        glossaryOfMeasureUnits.setUnitsOfMeasurement(glossaryOfMeasureUnitsDTO.getUnitsOfMeasurement());
        glossaryOfMeasureUnits.setChecked(glossaryOfMeasureUnitsDTO.isChecked());

        OrganisationStructure organisationStructure = new OrganisationStructure();
        organisationStructure.setId(glossaryOfMeasureUnitsDTO.getOrganisationId());
        glossaryOfMeasureUnits.setOrganisationStructure(organisationStructure);

        GlossaryOfPurposes glossaryOfPurposes = new GlossaryOfPurposes();
        glossaryOfPurposes.setId(glossaryOfMeasureUnitsDTO.getPurposeId());
        glossaryOfMeasureUnits.setGlossaryOfPurposes(glossaryOfPurposes);

        return glossaryOfMeasureUnits;
    }
}
