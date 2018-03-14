package rog.service.dto;

import rog.domain.CommercialRisksPurposes;
import rog.domain.SetOfSentPurposes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CommercialRisksPurposesDTO implements Serializable {

    private Long id;

    private Set<FilledCommercialRisksDTO> filledCommercialRisksDTOS = new HashSet<>();

    private Long setOfSentPurposesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<FilledCommercialRisksDTO> getFilledCommercialRisksDTOS() {
        return filledCommercialRisksDTOS;
    }

    public void setFilledCommercialRisksDTOS(Set<FilledCommercialRisksDTO> filledCommercialRisksDTOS) {
        this.filledCommercialRisksDTOS = filledCommercialRisksDTOS;
    }

    public Long getSetOfSentPurposesId() {
        return setOfSentPurposesId;
    }

    public void setSetOfSentPurposesId(Long setOfSentPurposesId) {
        this.setOfSentPurposesId = setOfSentPurposesId;
    }

    public CommercialRisksPurposesDTO() {
    }

    public CommercialRisksPurposesDTO(Long id, Set<FilledCommercialRisksDTO> filledCommercialRisksDTOS,
                                      Long setOfSentPurposesId) {
        this.id = id;
        this.filledCommercialRisksDTOS = filledCommercialRisksDTOS;
        this.setOfSentPurposesId = setOfSentPurposesId;
    }

    public CommercialRisksPurposesDTO(CommercialRisksPurposes commercialRisksPurposes){

        if(Objects.isNull(commercialRisksPurposes)){
            return;
        }

        this.id = commercialRisksPurposes.getId();

        SetOfSentPurposes setOfSentPurposes = commercialRisksPurposes.getSetOfSentPurposes();
        if(Objects.nonNull(setOfSentPurposes)) {
            this.setOfSentPurposesId = setOfSentPurposes.getId();
        }

        Set<FilledCommercialRisksDTO> filledCommercialRisksDTOS = new HashSet<>();
        commercialRisksPurposes.getFilledCommercialRisks()
            .forEach(item -> filledCommercialRisksDTOS.add(new FilledCommercialRisksDTO(item)));

        this.filledCommercialRisksDTOS = filledCommercialRisksDTOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommercialRisksPurposesDTO that = (CommercialRisksPurposesDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(filledCommercialRisksDTOS, that.filledCommercialRisksDTOS) &&
            Objects.equals(setOfSentPurposesId, that.setOfSentPurposesId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, filledCommercialRisksDTOS, setOfSentPurposesId);
    }
}
