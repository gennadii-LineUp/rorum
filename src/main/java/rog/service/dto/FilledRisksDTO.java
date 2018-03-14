package rog.service.dto;

import rog.domain.*;
import rog.domain.enumeration.ReactionOnRisks;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FilledRisksDTO implements Serializable {

    private Long id;

    private Integer probability;

    private Integer powerOfInfluence;

    private Integer strengthOfControlFunctionProbability;

    private Integer strengthOfControlFunctionPowerOfInfluence;

    private Long risksPurposesId;

    private ReactionOnRisks reactionOnRisks;

    private String notationConcernRisk;

    private GlossaryOfRisksDTO glossaryOfRisksDTO;

    private LocalDateTime creationDate = LocalDateTime.now();

    private String responsiblePerson;

    private LocalDate stateForDay;

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

    public Long getRisksPurposesId() {
        return risksPurposesId;
    }

    public void setRisksPurposesId(Long riskPurposesId) {
        this.risksPurposesId = riskPurposesId;
    }

    public GlossaryOfRisksDTO getGlossaryOfRisksDTO() {
        return glossaryOfRisksDTO;
    }

    public void setGlossaryOfRisksDTO(GlossaryOfRisksDTO glossaryOfRisksDTO) {
        this.glossaryOfRisksDTO = glossaryOfRisksDTO;
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

    public FilledRisksDTO() {
    }

    public FilledRisksDTO(Long id, Integer probability, Integer powerOfInfluence, Integer strengthOfControlFunctionProbability, Integer strengthOfControlFunctionPowerOfInfluence, Long risksPurposesId, ReactionOnRisks reactionOnRisks, String notationConcernRisk, GlossaryOfRisksDTO glossaryOfRisksDTO, LocalDateTime creationDate, String responsiblePerson, LocalDate stateForDay, Map<String, String> errors, Boolean isSaved, HighRiskDTO highRiskDTO, Set<Long> probabilities, Set<Long> powerOfInfluences) {
        this.id = id;
        this.probability = probability;
        this.powerOfInfluence = powerOfInfluence;
        this.strengthOfControlFunctionProbability = strengthOfControlFunctionProbability;
        this.strengthOfControlFunctionPowerOfInfluence = strengthOfControlFunctionPowerOfInfluence;
        this.risksPurposesId = risksPurposesId;
        this.reactionOnRisks = reactionOnRisks;
        this.notationConcernRisk = notationConcernRisk;
        this.glossaryOfRisksDTO = glossaryOfRisksDTO;
        this.creationDate = creationDate;
        this.responsiblePerson = responsiblePerson;
        this.stateForDay = stateForDay;
        this.errors = errors;
        this.isSaved = isSaved;
        this.highRiskDTO = highRiskDTO;
        this.probabilities = probabilities;
        this.powerOfInfluences = powerOfInfluences;
    }

    public FilledRisksDTO(FilledRisks filledRisks){

        if(Objects.isNull(filledRisks)){
            return;
        }

        this.id = filledRisks.getId();
        this.probability = filledRisks.getProbability();
        this.powerOfInfluence = filledRisks.getPowerOfInfluence();
        this.creationDate = filledRisks.getCreationDate();
        this.notationConcernRisk = filledRisks.getNotationConcernRisk();
        this.reactionOnRisks = filledRisks.getReactionOnRisks();
        this.isSaved = filledRisks.getSaved();
        this.responsiblePerson = filledRisks.getResponsiblePerson();
        this.stateForDay = filledRisks.getStateForDay();
        this.strengthOfControlFunctionPowerOfInfluence = filledRisks.getStrengthOfControlFunctionPowerOfInfluence();
        this.strengthOfControlFunctionProbability = filledRisks.getStrengthOfControlFunctionProbability();

        this.probabilities = filledRisks.getProbabilities().stream()
            .map(Probability::getId).collect(Collectors.toSet());

        this.powerOfInfluences = filledRisks.getPowerOfInfluences().stream()
            .map(PowerOfInfluence::getId).collect(Collectors.toSet());

        RisksPurposes risksPurposes = filledRisks.getRisksPurposes();
        if(Objects.nonNull(risksPurposes)){
            this.risksPurposesId = risksPurposes.getId();
        }

        GlossaryOfRisks glossaryOfRisks = filledRisks.getGlossaryOfRisks();
        this.glossaryOfRisksDTO = new GlossaryOfRisksDTO(glossaryOfRisks);

        HighRisk highRisk = filledRisks.getHighRisk();
        this.highRiskDTO = new HighRiskDTO(highRisk);
    }

    @Override
    public String toString() {
        return "FilledRisksDTO{" +
            "id=" + id +
            ", probability=" + probability +
            ", powerOfInfluence=" + powerOfInfluence +
            ", riskPurposesId=" + risksPurposesId +
            ", reactionOnRisks=" + reactionOnRisks +
            ", notationConcernRisk='" + notationConcernRisk + '\'' +
            ", glossaryOfRisksDTO=" + glossaryOfRisksDTO +
            ", creationDate=" + creationDate +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilledRisksDTO that = (FilledRisksDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(probability, that.probability) &&
            Objects.equals(powerOfInfluence, that.powerOfInfluence) &&
            Objects.equals(strengthOfControlFunctionProbability, that.strengthOfControlFunctionProbability) &&
            Objects.equals(strengthOfControlFunctionPowerOfInfluence, that.strengthOfControlFunctionPowerOfInfluence) &&
            Objects.equals(risksPurposesId, that.risksPurposesId) &&
            reactionOnRisks == that.reactionOnRisks &&
            Objects.equals(notationConcernRisk, that.notationConcernRisk) &&
            Objects.equals(glossaryOfRisksDTO, that.glossaryOfRisksDTO) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(responsiblePerson, that.responsiblePerson) &&
            Objects.equals(stateForDay, that.stateForDay) &&
            Objects.equals(errors, that.errors) &&
            Objects.equals(isSaved, that.isSaved) &&
            Objects.equals(highRiskDTO, that.highRiskDTO) &&
            Objects.equals(probabilities, that.probabilities) &&
            Objects.equals(powerOfInfluences, that.powerOfInfluences);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, probability, powerOfInfluence, strengthOfControlFunctionProbability, strengthOfControlFunctionPowerOfInfluence, risksPurposesId, reactionOnRisks, notationConcernRisk, glossaryOfRisksDTO, creationDate, responsiblePerson, stateForDay, errors, isSaved, highRiskDTO, probabilities, powerOfInfluences);
    }
}
