package rog.service.dto;

import rog.domain.GlossaryOfPurposes;
import rog.domain.MeasureUnitsPurposes;
import rog.domain.SetOfSentPurposes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MeasureUnitsPurposesDTO implements Serializable {

    private Long id;

    private Set<FilledMeasureUnitsDTO> filledMeasureUnitsDTOS = new HashSet<>();

    private Long glossaryOfPurposesId;

    private Long setOfSentPurposesId;

    public Set<FilledMeasureUnitsDTO> getFilledMeasureUnitsDTOS() {
        return filledMeasureUnitsDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFilledMeasureUnitsDTOS(Set<FilledMeasureUnitsDTO> filledMeasureUnitsDTOS) {
        this.filledMeasureUnitsDTOS = filledMeasureUnitsDTOS;
    }

    public Long getGlossaryOfPurposesId() {
        return glossaryOfPurposesId;
    }

    public void setGlossaryOfPurposesId(Long glossaryOfPurposesId) {
        this.glossaryOfPurposesId = glossaryOfPurposesId;
    }

    public Long getSetOfSentPurposesId() {
        return setOfSentPurposesId;
    }

    public void setSetOfSentPurposesId(Long setOfSentPurposesId) {
        this.setOfSentPurposesId = setOfSentPurposesId;
    }

    public MeasureUnitsPurposesDTO() {
    }

    public MeasureUnitsPurposesDTO(Long id, Set<FilledMeasureUnitsDTO> filledMeasureUnitsDTOS, Long glossaryOfPurposesId,
                                   Long setOfSentPurposesId) {
        this.id = id;
        this.filledMeasureUnitsDTOS = filledMeasureUnitsDTOS;
        this.glossaryOfPurposesId = glossaryOfPurposesId;
        this.setOfSentPurposesId = setOfSentPurposesId;
    }

    public MeasureUnitsPurposesDTO(MeasureUnitsPurposes measureUnitsPurposes){

        if(Objects.isNull(measureUnitsPurposes)){
            return;
        }

        Set<FilledMeasureUnitsDTO> filledMeasureUnitsDTOS = new HashSet<>();

        measureUnitsPurposes.getFilledMeasureUnits()
            .forEach(item -> filledMeasureUnitsDTOS.add(new FilledMeasureUnitsDTO(item)));

        this.filledMeasureUnitsDTOS = filledMeasureUnitsDTOS;
        this.id = measureUnitsPurposes.getId();

        GlossaryOfPurposes glossaryOfPurposes = measureUnitsPurposes.getGlossaryOfPurposes();
        if(Objects.nonNull(glossaryOfPurposes)){
            this.glossaryOfPurposesId = glossaryOfPurposes.getId();
        }

        SetOfSentPurposes setOfSentPurposes = measureUnitsPurposes.getSetOfSentPurposes();
        if(Objects.nonNull(setOfSentPurposes)) {
            this.setOfSentPurposesId = setOfSentPurposes.getId();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasureUnitsPurposesDTO that = (MeasureUnitsPurposesDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(filledMeasureUnitsDTOS, that.filledMeasureUnitsDTOS) &&
            Objects.equals(glossaryOfPurposesId, that.glossaryOfPurposesId) &&
            Objects.equals(setOfSentPurposesId, that.setOfSentPurposesId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, filledMeasureUnitsDTOS, glossaryOfPurposesId, setOfSentPurposesId);
    }
}
