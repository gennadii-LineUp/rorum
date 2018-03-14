package rog.service.dto;

import rog.domain.GlossaryOfPurposes;
import rog.domain.RisksPurposes;
import rog.domain.SetOfSentPurposes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RisksPurposesDTO implements Serializable {

    private Long id;

    private Set<FilledRisksDTO> filledRisksDTOS = new HashSet<>();

    public Set<FilledRisksDTO> getFilledRisksDTOS() {
        return filledRisksDTOS;
    }

    private Long glossaryOfPurposesId;

    private Long setOfSentPurposesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFilledRisksDTOS(Set<FilledRisksDTO> filledRisksDTOS) {
        this.filledRisksDTOS = filledRisksDTOS;
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

    public RisksPurposesDTO() {
    }

    public RisksPurposesDTO(Long id, Set<FilledRisksDTO> filledRisksDTOS, Long glossaryOfPurposesId, Long setOfSentPurposesId) {
        this.id = id;
        this.filledRisksDTOS = filledRisksDTOS;
        this.glossaryOfPurposesId = glossaryOfPurposesId;
        this.setOfSentPurposesId = setOfSentPurposesId;
    }

    public RisksPurposesDTO(RisksPurposes risksPurposes){

        if(Objects.isNull(risksPurposes)){
            return;
        }

        Set<FilledRisksDTO> filledRisksDTOS = new HashSet<>();
        risksPurposes.getFilledRisks().forEach(item -> filledRisksDTOS.add(new FilledRisksDTO(item)));

        this.id = risksPurposes.getId();
        this.filledRisksDTOS = filledRisksDTOS;

        GlossaryOfPurposes glossaryOfPurposes = risksPurposes.getGlossaryOfPurposes();
        if(Objects.nonNull(glossaryOfPurposes)){
            this.glossaryOfPurposesId = glossaryOfPurposes.getId();
        }

        SetOfSentPurposes setOfSentPurposes = risksPurposes.getSetOfSentPurposes();
        if(Objects.nonNull(setOfSentPurposes)) {
            this.setOfSentPurposesId = setOfSentPurposes.getId();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RisksPurposesDTO that = (RisksPurposesDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(filledRisksDTOS, that.filledRisksDTOS) &&
            Objects.equals(glossaryOfPurposesId, that.glossaryOfPurposesId) &&
            Objects.equals(setOfSentPurposesId, that.setOfSentPurposesId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, filledRisksDTOS, glossaryOfPurposesId, setOfSentPurposesId);
    }
}
