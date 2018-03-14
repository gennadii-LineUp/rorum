package rog.service.dto;

import rog.domain.FilledMeasureUnits;
import rog.domain.GlossaryOfMeasureUnits;
import rog.domain.MeasureUnitsPurposes;
import rog.domain.enumeration.PurposeAccomplishmentStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FilledMeasureUnitsDTO implements Serializable{

    private Long id;

    private Integer baseValue;

    private Integer actualValue;

    private Integer finalValue;

    private Double costOfPurposeRealisation;

    private Long measureUnitsPurposesId;

    private GlossaryOfMeasureUnitsDTO glossaryOfMeasureUnitsDTO;

    private PurposeAccomplishmentStatus purposeAccomplishmentStatus;

    private LocalDateTime creationDate = LocalDateTime.now();

    private Map<String, String> errors = new HashMap<>();

    private boolean isSaved = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(Integer baseValue) {
        this.baseValue = baseValue;
    }

    public Integer getActualValue() {
        return actualValue;
    }

    public void setActualValue(Integer actualValue) {
        this.actualValue = actualValue;
    }

    public Integer getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(Integer finalValue) {
        this.finalValue = finalValue;
    }

    public Long getMeasureUnitsPurposesId() {
        return measureUnitsPurposesId;
    }

    public void setMeasureUnitsPurposesId(Long measureUnitsPurposesId) {
        this.measureUnitsPurposesId = measureUnitsPurposesId;
    }

    public GlossaryOfMeasureUnitsDTO getGlossaryOfMeasureUnitsDTO() {
        return glossaryOfMeasureUnitsDTO;
    }

    public void setGlossaryOfMeasureUnitsDTO(GlossaryOfMeasureUnitsDTO glossaryOfMeasureUnitsDTO) {
        this.glossaryOfMeasureUnitsDTO = glossaryOfMeasureUnitsDTO;
    }

    public PurposeAccomplishmentStatus getPurposeAccomplishmentStatus() {
        return purposeAccomplishmentStatus;
    }

    public void setPurposeAccomplishmentStatus(PurposeAccomplishmentStatus purposeAccomplishmentStatus) {
        this.purposeAccomplishmentStatus = purposeAccomplishmentStatus;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public Boolean getSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public Double getCostOfPurposeRealisation() {
        return costOfPurposeRealisation;
    }

    public void setCostOfPurposeRealisation(Double costOfPurposeRealisation) {
        this.costOfPurposeRealisation = costOfPurposeRealisation;
    }

    public FilledMeasureUnitsDTO() {
    }

    public FilledMeasureUnitsDTO(Long id, Integer baseValue, Integer actualValue, Integer finalValue, Double costOfPurposeRealisation, Long measureUnitsPurposesId, GlossaryOfMeasureUnitsDTO glossaryOfMeasureUnitsDTO, PurposeAccomplishmentStatus purposeAccomplishmentStatus, LocalDateTime creationDate, Map<String, String> errors, boolean isSaved) {
        this.id = id;
        this.baseValue = baseValue;
        this.actualValue = actualValue;
        this.finalValue = finalValue;
        this.costOfPurposeRealisation = costOfPurposeRealisation;
        this.measureUnitsPurposesId = measureUnitsPurposesId;
        this.glossaryOfMeasureUnitsDTO = glossaryOfMeasureUnitsDTO;
        this.purposeAccomplishmentStatus = purposeAccomplishmentStatus;
        this.creationDate = creationDate;
        this.errors = errors;
        this.isSaved = isSaved;
    }

    public FilledMeasureUnitsDTO(FilledMeasureUnits filledMeasureUnits){

        if(Objects.isNull(filledMeasureUnits)){
            return;
        }

        this.id = filledMeasureUnits.getId();
        this.baseValue = filledMeasureUnits.getBaseValue();
        this.actualValue = filledMeasureUnits.getActualValue();

        BigDecimal costOfPurposeRealisation = filledMeasureUnits.getCostOfPurposeRealisation();
        if(Objects.nonNull(costOfPurposeRealisation)) {
            this.costOfPurposeRealisation = costOfPurposeRealisation.doubleValue();
        }

        this.purposeAccomplishmentStatus = filledMeasureUnits.getPurposeAccomplishmentStatus();
        this.finalValue = filledMeasureUnits.getFinalValue();
        this.creationDate = filledMeasureUnits.getCreationDate();
        this.isSaved = filledMeasureUnits.getSaved();

        MeasureUnitsPurposes measureUnitsPurposes = filledMeasureUnits.getMeasureUnitsPurposes();
        if(Objects.nonNull(measureUnitsPurposes)){
            this.measureUnitsPurposesId = measureUnitsPurposes.getId();
        }

        GlossaryOfMeasureUnits glossaryOfMeasureUnits = filledMeasureUnits.getGlossaryOfMeasureUnits();
        this.glossaryOfMeasureUnitsDTO = new GlossaryOfMeasureUnitsDTO(glossaryOfMeasureUnits);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilledMeasureUnitsDTO that = (FilledMeasureUnitsDTO) o;
        return isSaved == that.isSaved &&
            Objects.equals(id, that.id) &&
            Objects.equals(baseValue, that.baseValue) &&
            Objects.equals(actualValue, that.actualValue) &&
            Objects.equals(finalValue, that.finalValue) &&
            Objects.equals(costOfPurposeRealisation, that.costOfPurposeRealisation) &&
            Objects.equals(measureUnitsPurposesId, that.measureUnitsPurposesId) &&
            Objects.equals(glossaryOfMeasureUnitsDTO, that.glossaryOfMeasureUnitsDTO) &&
            purposeAccomplishmentStatus == that.purposeAccomplishmentStatus &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, baseValue, actualValue, finalValue, costOfPurposeRealisation, measureUnitsPurposesId, glossaryOfMeasureUnitsDTO, purposeAccomplishmentStatus, creationDate, errors, isSaved);
    }
}
