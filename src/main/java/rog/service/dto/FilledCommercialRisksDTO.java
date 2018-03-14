package rog.service.dto;

import rog.domain.*;
import rog.domain.enumeration.ReactionOnRisks;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FilledCommercialRisksDTO implements Serializable {

    private Long id;

    private Integer probability;

    private Integer powerOfInfluence;

    private Integer strengthOfControlFunctionProbability;

    private Integer strengthOfControlFunctionPowerOfInfluence;

    private ReactionOnRisks reactionOnRisks;

    private String notationConcernRisk;

    private String responsiblePerson;

    private LocalDate stateForDay;

    private Long commercialRiskPurposesId;

    private GlossaryOfCommercialRisksDTO glossaryOfCommercialRisksDTO;

    private LocalDateTime creationDate = LocalDateTime.now();

    private Map<String, String> errors = new HashMap<>();

    private Boolean isSaved = Boolean.FALSE;

    private HighRiskDTO highRiskDTO;

    private Set<Long> probabilities = new HashSet<>();

    private Set<Long> powerOfInfluences = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Integer getPowerOfInfluence() {
        return powerOfInfluence;
    }

    public void setPowerOfInfluence(Integer powerOfInfluence) {
        this.powerOfInfluence = powerOfInfluence;
    }

    public Long getCommercialRiskPurposesId() {
        return commercialRiskPurposesId;
    }

    public void setCommercialRiskPurposesId(Long commercialRiskPurposesId) {
        this.commercialRiskPurposesId = commercialRiskPurposesId;
    }

    public GlossaryOfCommercialRisksDTO getGlossaryOfCommercialRisksDTO() {
        return glossaryOfCommercialRisksDTO;
    }

    public void setGlossaryOfCommercialRisksDTO(GlossaryOfCommercialRisksDTO glossaryOfCommercialRisksDTO) {
        this.glossaryOfCommercialRisksDTO = glossaryOfCommercialRisksDTO;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ReactionOnRisks getReactionOnRisks() {
        return reactionOnRisks;
    }

    public void setReactionOnRisks(ReactionOnRisks reactionOnRisks) {
        this.reactionOnRisks = reactionOnRisks;
    }

    public String getNotationConcernRisk() {
        return notationConcernRisk;
    }

    public void setNotationConcernRisk(String notationConcernRisk) {
        this.notationConcernRisk = notationConcernRisk;
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

    public HighRiskDTO getHighRiskDTO() {
        return highRiskDTO;
    }

    public void setHighRiskDTO(HighRiskDTO highRiskDTO) {
        this.highRiskDTO = highRiskDTO;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public LocalDate getStateForDay() {
        return stateForDay;
    }

    public void setStateForDay(LocalDate stateForDay) {
        this.stateForDay = stateForDay;
    }

    public Integer getStrengthOfControlFunctionProbability() {
        return strengthOfControlFunctionProbability;
    }

    public void setStrengthOfControlFunctionProbability(Integer strengthOfControlFunctionProbability) {
        this.strengthOfControlFunctionProbability = strengthOfControlFunctionProbability;
    }

    public Integer getStrengthOfControlFunctionPowerOfInfluence() {
        return strengthOfControlFunctionPowerOfInfluence;
    }

    public void setStrengthOfControlFunctionPowerOfInfluence(Integer strengthOfControlFunctionPowerOfInfluence) {
        this.strengthOfControlFunctionPowerOfInfluence = strengthOfControlFunctionPowerOfInfluence;
    }

    public Set<Long> getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(Set<Long> probabilities) {
        this.probabilities = probabilities;
    }

    public Set<Long> getPowerOfInfluences() {
        return powerOfInfluences;
    }

    public void setPowerOfInfluences(Set<Long> powerOfInfluences) {
        this.powerOfInfluences = powerOfInfluences;
    }

    public FilledCommercialRisksDTO() {
    }

    public FilledCommercialRisksDTO(Long id, Integer probability, Integer powerOfInfluence, Integer strengthOfControlFunctionProbability, Integer strengthOfControlFunctionPowerOfInfluence, ReactionOnRisks reactionOnRisks, String notationConcernRisk, String responsiblePerson, LocalDate stateForDay, Long commercialRiskPurposesId, GlossaryOfCommercialRisksDTO glossaryOfCommercialRisksDTO, LocalDateTime creationDate, Map<String, String> errors, Boolean isSaved, HighRiskDTO highRiskDTO, Set<Long> probabilities, Set<Long> powerOfInfluences) {
        this.id = id;
        this.probability = probability;
        this.powerOfInfluence = powerOfInfluence;
        this.strengthOfControlFunctionProbability = strengthOfControlFunctionProbability;
        this.strengthOfControlFunctionPowerOfInfluence = strengthOfControlFunctionPowerOfInfluence;
        this.reactionOnRisks = reactionOnRisks;
        this.notationConcernRisk = notationConcernRisk;
        this.responsiblePerson = responsiblePerson;
        this.stateForDay = stateForDay;
        this.commercialRiskPurposesId = commercialRiskPurposesId;
        this.glossaryOfCommercialRisksDTO = glossaryOfCommercialRisksDTO;
        this.creationDate = creationDate;
        this.errors = errors;
        this.isSaved = isSaved;
        this.highRiskDTO = highRiskDTO;
        this.probabilities = probabilities;
        this.powerOfInfluences = powerOfInfluences;
    }

    public FilledCommercialRisksDTO(FilledCommercialRisks filledCommercialRisks){

        if(Objects.isNull(filledCommercialRisks)){
            return;
        }

        this.id = filledCommercialRisks.getId();
        this.powerOfInfluence = filledCommercialRisks.getPowerOfInfluence();
        this.probability = filledCommercialRisks.getProbability();
        this.strengthOfControlFunctionPowerOfInfluence = filledCommercialRisks.getStrengthOfControlFunctionPowerOfInfluence();
        this.strengthOfControlFunctionProbability = filledCommercialRisks.getStrengthOfControlFunctionProbability();
        this.creationDate = filledCommercialRisks.getCreationDate();
        this.notationConcernRisk = filledCommercialRisks.getNotationConcernRisk();
        this.reactionOnRisks = filledCommercialRisks.getReactionOnRisks();
        this.responsiblePerson = filledCommercialRisks.getResponsiblePerson();
        this.stateForDay = filledCommercialRisks.getStateForDay();
        this.isSaved = filledCommercialRisks.getSaved();

        this.probabilities = filledCommercialRisks.getProbabilities()
            .stream()
            .map(Probability::getId)
            .collect(Collectors.toSet());

        this.powerOfInfluences = filledCommercialRisks.getPowerOfInfluences()
            .stream()
            .map(PowerOfInfluence::getId)
            .collect(Collectors.toSet());

        CommercialRisksPurposes commercialRisksPurposes = filledCommercialRisks.getCommercialRisksPurposes();
        if(Objects.nonNull(commercialRisksPurposes)) {
            this.commercialRiskPurposesId = commercialRisksPurposes.getId();
        }

        GlossaryOfCommercialRisks glossaryOfCommercialRisks = filledCommercialRisks.getGlossaryOfCommercialRisks();
        this.glossaryOfCommercialRisksDTO = new GlossaryOfCommercialRisksDTO(glossaryOfCommercialRisks);

        HighCommercialRisk highCommercialRisk = filledCommercialRisks.getHighCommercialRisk();
        this.highRiskDTO = new HighRiskDTO(highCommercialRisk);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilledCommercialRisksDTO that = (FilledCommercialRisksDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(probability, that.probability) &&
            Objects.equals(powerOfInfluence, that.powerOfInfluence) &&
            Objects.equals(strengthOfControlFunctionProbability, that.strengthOfControlFunctionProbability) &&
            Objects.equals(strengthOfControlFunctionPowerOfInfluence, that.strengthOfControlFunctionPowerOfInfluence) &&
            reactionOnRisks == that.reactionOnRisks &&
            Objects.equals(notationConcernRisk, that.notationConcernRisk) &&
            Objects.equals(responsiblePerson, that.responsiblePerson) &&
            Objects.equals(stateForDay, that.stateForDay) &&
            Objects.equals(commercialRiskPurposesId, that.commercialRiskPurposesId) &&
            Objects.equals(glossaryOfCommercialRisksDTO, that.glossaryOfCommercialRisksDTO) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(errors, that.errors) &&
            Objects.equals(isSaved, that.isSaved) &&
            Objects.equals(highRiskDTO, that.highRiskDTO) &&
            Objects.equals(probabilities, that.probabilities) &&
            Objects.equals(powerOfInfluences, that.powerOfInfluences);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, probability, powerOfInfluence, strengthOfControlFunctionProbability, strengthOfControlFunctionPowerOfInfluence, reactionOnRisks, notationConcernRisk, responsiblePerson, stateForDay, commercialRiskPurposesId, glossaryOfCommercialRisksDTO, creationDate, errors, isSaved, highRiskDTO, probabilities, powerOfInfluences);
    }
}
