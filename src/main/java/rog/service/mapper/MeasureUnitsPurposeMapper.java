package rog.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.FilledMeasureUnits;
import rog.domain.GlossaryOfPurposes;
import rog.domain.MeasureUnitsPurposes;
import rog.domain.SetOfSentPurposes;
import rog.service.dto.FilledMeasureUnitsDTO;
import rog.service.dto.MeasureUnitsPurposesDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MeasureUnitsPurposeMapper {

    @Autowired
    private FilledMeasureUnitsMapper filledMeasureUnitsMapper;

    public Set<MeasureUnitsPurposes> measureUnitsPurposeDTOsToMeasureUnitsPurposes(Set<MeasureUnitsPurposesDTO> measureUnitsPurposesDTOS) {
        return measureUnitsPurposesDTOS.stream()
            .filter(Objects::nonNull)
            .map(this::measureUnitsPurposeDTOToMeasureUnitsPurposes)
            .collect(Collectors.toSet());
    }

    public MeasureUnitsPurposes measureUnitsPurposeDTOToMeasureUnitsPurposes(MeasureUnitsPurposesDTO measureUnitsPurposesDTO){

        if (Objects.isNull(measureUnitsPurposesDTO)) {
            return null;
        }

        Set<FilledMeasureUnitsDTO> filledMeasureUnitsDTOS = measureUnitsPurposesDTO.getFilledMeasureUnitsDTOS();

        Set<FilledMeasureUnits> filledMeasureUnits =
            filledMeasureUnitsMapper.filledMeasureUnits(filledMeasureUnitsDTOS);

        MeasureUnitsPurposes measureUnitsPurposes = new MeasureUnitsPurposes();
        measureUnitsPurposes.setFilledMeasureUnits(filledMeasureUnits);
        measureUnitsPurposes.setId(measureUnitsPurposesDTO.getId());

        SetOfSentPurposes setOfSentPurposes = new SetOfSentPurposes();
        setOfSentPurposes.setId(measureUnitsPurposesDTO.getSetOfSentPurposesId());
        measureUnitsPurposes.setSetOfSentPurposes(setOfSentPurposes);

        GlossaryOfPurposes glossaryOfPurposes = new GlossaryOfPurposes();
        glossaryOfPurposes.setId(measureUnitsPurposesDTO.getGlossaryOfPurposesId());
        measureUnitsPurposes.setGlossaryOfPurposes(glossaryOfPurposes);

        return measureUnitsPurposes;
    }
}
