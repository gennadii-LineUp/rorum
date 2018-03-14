package rog.service.dto;

import rog.domain.CommercialRisksPurposes;
import rog.domain.GlossaryOfPurposes;
import rog.domain.SetOfSentPurposes;
import rog.domain.enumeration.StatusOfSending;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class SetOfSentPurposesDTO implements Serializable{

    private Long id;

    private Set<Long> ids = new HashSet<>();

    private String notation;

    private StatusOfSending statusOfSending = StatusOfSending.CONFIRMED_PURPOSES;

    private Set<MeasureUnitsPurposesDTO> measureUnitsPurposesDTOS = new HashSet<>();

    private Set<RisksPurposesDTO> risksPurposesDTOS = new HashSet<>();

    private CommercialRisksPurposesDTO commercialRisksPurposesDTO;

    private Set<KeyRiskIndicatorPurposesDTO> keyRiskIndicatorPurposesDTOS = new HashSet<>();

    private Map<String, String> errors = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getIds() {
        return ids;
    }

    public void setIds(Set<Long> ids) {
        this.ids = ids;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public StatusOfSending getStatusOfSending() {
        return statusOfSending;
    }

    public void setStatusOfSending(StatusOfSending statusOfSending) {
        this.statusOfSending = statusOfSending;
    }

    public Set<RisksPurposesDTO> getRisksPurposesDTOS() {
        return risksPurposesDTOS;
    }

    public Set<MeasureUnitsPurposesDTO> getMeasureUnitsPurposesDTOS() {
        return measureUnitsPurposesDTOS;
    }

    public void setMeasureUnitsPurposesDTOS(Set<MeasureUnitsPurposesDTO> measureUnitsPurposesDTOS) {
        this.measureUnitsPurposesDTOS = measureUnitsPurposesDTOS;
    }

    public void setRisksPurposesDTOS(Set<RisksPurposesDTO> risksPurposesDTOS) {
        this.risksPurposesDTOS = risksPurposesDTOS;
    }

    public CommercialRisksPurposesDTO getCommercialRisksPurposesDTO() {
        return commercialRisksPurposesDTO;
    }

    public void setCommercialRisksPurposesDTO(CommercialRisksPurposesDTO commercialRisksPurposesDTO) {
        this.commercialRisksPurposesDTO = commercialRisksPurposesDTO;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public Set<KeyRiskIndicatorPurposesDTO> getKeyRiskIndicatorPurposesDTOS() {
        return keyRiskIndicatorPurposesDTOS;
    }

    public void setKeyRiskIndicatorPurposesDTOS(Set<KeyRiskIndicatorPurposesDTO> keyRiskIndicatorPurposesDTOS) {
        this.keyRiskIndicatorPurposesDTOS = keyRiskIndicatorPurposesDTOS;
    }

    public SetOfSentPurposesDTO() {
    }

    public SetOfSentPurposesDTO(Long id, Set<Long> ids, String notation, StatusOfSending statusOfSending,
                                Set<MeasureUnitsPurposesDTO> measureUnitsPurposesDTOS,
                                Set<RisksPurposesDTO> risksPurposesDTOS,
                                CommercialRisksPurposesDTO commercialRisksPurposesDTO, Map<String, String> errors) {
        this.id = id;
        this.ids = ids;
        this.notation = notation;
        this.statusOfSending = statusOfSending;
        this.measureUnitsPurposesDTOS = measureUnitsPurposesDTOS;
        this.risksPurposesDTOS = risksPurposesDTOS;
        this.commercialRisksPurposesDTO = commercialRisksPurposesDTO;
        this.errors = errors;
    }

    public SetOfSentPurposesDTO(SetOfSentPurposes setOfSentPurposes){

        if(Objects.isNull(setOfSentPurposes)){
            return;
        }

        this.ids = setOfSentPurposes.getGlossaryOfPurposes()
            .stream()
            .map(GlossaryOfPurposes::getId)
            .collect(Collectors.toSet());

        this.id = setOfSentPurposes.getId();
        this.notation = setOfSentPurposes.getNotation();
        this.statusOfSending = setOfSentPurposes.getStatusOfSending();

        Set<MeasureUnitsPurposesDTO> measureUnitsPurposesDTOS = new HashSet<>();
        setOfSentPurposes.getMeasureUnitsPurposes()
            .forEach(item -> measureUnitsPurposesDTOS.add(new MeasureUnitsPurposesDTO(item)));
        this.measureUnitsPurposesDTOS = measureUnitsPurposesDTOS;

        Set<RisksPurposesDTO> risksPurposesDTOS = new HashSet<>();
        setOfSentPurposes.getRisksPurposes().forEach(item -> risksPurposesDTOS.add(new RisksPurposesDTO(item)));
        this.risksPurposesDTOS = risksPurposesDTOS;

        CommercialRisksPurposes commercialRisksPurposes = setOfSentPurposes.getCommercialRisksPurposes();
        this.commercialRisksPurposesDTO = new CommercialRisksPurposesDTO(commercialRisksPurposes);

        Set<KeyRiskIndicatorPurposesDTO> keyRiskIndicatorPurposesDTOS = new HashSet<>();
        setOfSentPurposes.getKeyRiskIndicatorPurposes()
            .forEach(item -> keyRiskIndicatorPurposesDTOS.add(new KeyRiskIndicatorPurposesDTO(item)));
        this.keyRiskIndicatorPurposesDTOS = keyRiskIndicatorPurposesDTOS;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetOfSentPurposesDTO that = (SetOfSentPurposesDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(ids, that.ids) &&
            Objects.equals(notation, that.notation) &&
            statusOfSending == that.statusOfSending &&
            Objects.equals(measureUnitsPurposesDTOS, that.measureUnitsPurposesDTOS) &&
            Objects.equals(risksPurposesDTOS, that.risksPurposesDTOS) &&
            Objects.equals(commercialRisksPurposesDTO, that.commercialRisksPurposesDTO) &&
            Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ids, notation, statusOfSending, measureUnitsPurposesDTOS, risksPurposesDTOS, commercialRisksPurposesDTO, errors);
    }
}
