package rog.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import rog.domain.*;
import rog.domain.enumeration.StatusOfIncident;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class IncidentDTO implements Serializable {

    private Long id;

    private String description;

    private String descriptionOfReaction;

    private String descriptionOfPlannedActivities;

    private Boolean isCritical;

    private StatusOfIncident statusOfIncident;

    private Long supervisedBy;

    private Long setOfSentPurposesId;

    private Long glossaryOfPurposesId;

    private Long filledRisksId;

    private Long filledCommercialRisksId;

    private Map<String, String> errors = new HashMap<>();

    public IncidentDTO(){
    }

    public IncidentDTO(Long id, String description, String descriptionOfReaction,
                       String descriptionOfPlannedActivities, Boolean isCritical, Long setOfSentPurposesId,
                       Long glossaryOfPurposesId, Long filledRisksId, Long filledCommercialRisksId,
                       Map<String, String> errors) {
        this.id = id;
        this.description = description;
        this.descriptionOfReaction = descriptionOfReaction;
        this.descriptionOfPlannedActivities = descriptionOfPlannedActivities;
        this.isCritical = isCritical;
        this.setOfSentPurposesId = setOfSentPurposesId;
        this.glossaryOfPurposesId = glossaryOfPurposesId;
        this.filledRisksId = filledRisksId;
        this.filledCommercialRisksId = filledCommercialRisksId;
        this.errors = errors;
    }

    public IncidentDTO(Long id, String description, String descriptionOfReaction,
                       String descriptionOfPlannedActivities, Boolean isCritical, Long setOfSentPurposesId,
                       Long glossaryOfPurposesId, Long filledRisksId, Long filledCommercialRisksId,
                       Map<String, String> errors, StatusOfIncident statusOfIncident, Long supervisedBy) {
        this.id = id;
        this.description = description;
        this.descriptionOfReaction = descriptionOfReaction;
        this.descriptionOfPlannedActivities = descriptionOfPlannedActivities;
        this.isCritical = isCritical;
        this.setOfSentPurposesId = setOfSentPurposesId;
        this.glossaryOfPurposesId = glossaryOfPurposesId;
        this.filledRisksId = filledRisksId;
        this.filledCommercialRisksId = filledCommercialRisksId;
        this.errors = errors;
        this.statusOfIncident = statusOfIncident;
        this.supervisedBy = supervisedBy;
    }

    public IncidentDTO(Incident incident){
        this.id = incident.getId();
        this.description = incident.getDescription();
        this.descriptionOfReaction = incident.getDescriptionOfReaction();
        this.descriptionOfPlannedActivities = incident.getDescriptionOfPlannedActivities();
        this.isCritical = incident.isCritical();
        this.supervisedBy = Optional.ofNullable(incident.getSupervisedBy()).orElse(0l);
        this.statusOfIncident = Optional.ofNullable(incident.getStatusOfIncident()).orElse(StatusOfIncident.REPORTED);

        SetOfSentPurposes setOfSentPurposes = incident.getSetOfSentPurposes();
        if(Objects.nonNull(setOfSentPurposes)){
            this.setOfSentPurposesId = setOfSentPurposes.getId();
        }

        GlossaryOfPurposes glossaryOfPurposes = incident.getGlossaryOfPurposes();
        if(Objects.nonNull(glossaryOfPurposes)){
            this.glossaryOfPurposesId = glossaryOfPurposes.getId();
        }

        FilledRisks filledRisks = incident.getFilledRisks();
        if(Objects.nonNull(filledRisks)){
            this.filledRisksId = filledRisks.getId();
        }

        FilledCommercialRisks filledCommercialRisks = incident.getFilledCommercialRisks();
        if(Objects.nonNull(filledCommercialRisks)){
            this.filledCommercialRisksId = filledCommercialRisks.getId();
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionOfReaction() {
        return descriptionOfReaction;
    }

    public void setDescriptionOfReaction(String descriptionOfReaction) {
        this.descriptionOfReaction = descriptionOfReaction;
    }

    public String getDescriptionOfPlannedActivities() {
        return descriptionOfPlannedActivities;
    }

    public void setDescriptionOfPlannedActivities(String descriptionOfPlannedActivities) {
        this.descriptionOfPlannedActivities = descriptionOfPlannedActivities;
    }

    @JsonProperty(value="isCritical")
    public Boolean getCritical() {
        return isCritical;
    }

    @JsonProperty(value="isCritical")
    public void setCritical(Boolean isCritical) {
        this.isCritical = isCritical;
    }

    public Long getSetOfSentPurposesId() {
        return setOfSentPurposesId;
    }

    public void setSetOfSentPurposesId(Long setOfSentPurposesId) {
        this.setOfSentPurposesId = setOfSentPurposesId;
    }

    public Long getGlossaryOfPurposesId() {
        return glossaryOfPurposesId;
    }

    public void setGlossaryOfPurposesId(Long glossaryOfPurposesId) {
        this.glossaryOfPurposesId = glossaryOfPurposesId;
    }

    public Long getFilledRisksId() {
        return filledRisksId;
    }

    public void setFilledRisksId(Long filledRisksId) {
        this.filledRisksId = filledRisksId;
    }

    public Long getFilledCommercialRisksId() {
        return filledCommercialRisksId;
    }

    public void setFilledCommercialRisksId(Long filledCommercialRisksId) {
        this.filledCommercialRisksId = filledCommercialRisksId;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public StatusOfIncident getStatusOfIncident() {
        return statusOfIncident;
    }

    public void setStatusOfIncident(StatusOfIncident statusOfIncident) {
        this.statusOfIncident = statusOfIncident;
    }

    public Long getSupervisedBy() {
        return supervisedBy;
    }

    public void setSupervisedBy(Long supervisedBy) {
        this.supervisedBy = supervisedBy;
    }
}
