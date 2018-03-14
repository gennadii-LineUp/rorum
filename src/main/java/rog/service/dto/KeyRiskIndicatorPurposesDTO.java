package rog.service.dto;

import rog.domain.GlossaryOfPurposes;
import rog.domain.KeyRiskIndicatorPurposes;
import rog.domain.SetOfSentPurposes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class KeyRiskIndicatorPurposesDTO implements Serializable{

    private Long id;

    private Set<FilledKeyRiskIndicatorDTO> filledKeyRiskIndicatorDTOS = new HashSet<>();

    private Long glossaryOfPurposesId;

    private Long setOfSentPurposesId;

    public KeyRiskIndicatorPurposesDTO(){
    }

    public KeyRiskIndicatorPurposesDTO(KeyRiskIndicatorPurposes keyRiskIndicatorPurposes){

        if(Objects.isNull(keyRiskIndicatorPurposes)){
            return;
        }

        Set<FilledKeyRiskIndicatorDTO> filledKeyRiskIndicatorDTOS = new HashSet<>();

        keyRiskIndicatorPurposes.getFilledKeyRiskIndicators()
            .forEach(item -> filledKeyRiskIndicatorDTOS.add(new FilledKeyRiskIndicatorDTO(item)));

        this.filledKeyRiskIndicatorDTOS = filledKeyRiskIndicatorDTOS;

        this.id = keyRiskIndicatorPurposes.getId();

        GlossaryOfPurposes glossaryOfPurposes = keyRiskIndicatorPurposes.getGlossaryOfPurposes();
        if(Objects.nonNull(glossaryOfPurposes)){
            this.glossaryOfPurposesId = glossaryOfPurposes.getId();
        }

        SetOfSentPurposes setOfSentPurposes = keyRiskIndicatorPurposes.getSetOfSentPurposes();
        if(Objects.nonNull(setOfSentPurposes)) {
            this.setOfSentPurposesId = setOfSentPurposes.getId();
        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<FilledKeyRiskIndicatorDTO> getFilledKeyRiskIndicatorDTOS() {
        return filledKeyRiskIndicatorDTOS;
    }

    public void setFilledKeyRiskIndicatorDTOS(Set<FilledKeyRiskIndicatorDTO> filledKeyRiskIndicatorDTOS) {
        this.filledKeyRiskIndicatorDTOS = filledKeyRiskIndicatorDTOS;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        KeyRiskIndicatorPurposesDTO that = (KeyRiskIndicatorPurposesDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
