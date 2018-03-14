package rog.service.dto;

import rog.domain.*;
import rog.domain.enumeration.AnalyzeOfRisk;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class HighRiskDTO implements Serializable{

    private Long id;

    private Boolean rejectToAccomplishPurpose;

    private Boolean postponePurposeAccomplishment;

    private Boolean restrictRangeOfPurposeAccomplishment;

    private Double costOfListedPossibilities;

    private LocalDate projectedTermDeployStart;

    private LocalDate projectedTermDeployFinish;

    private Integer probabilityToReach;

    private Integer powerOfInfluenceToReach;

    private AnalyzeOfRisk analyze;

    private String substantiationForAnalyze;

    private String commentToHighRiskProcedure;

    private Long decisionForRiskId;

    private Set<String> possibilitiesToImproveRisks = new HashSet<>();

    private Map<String, String> errors = new HashMap<>();

    private Long filledRiskId;

    public HighRiskDTO() {
    }

    public HighRiskDTO(Long id, Boolean rejectToAccomplishPurpose, Boolean postponePurposeAccomplishment,
                       Boolean restrictRangeOfPurposeAccomplishment, Double costOfListedPossibilities,
                       LocalDate projectedTermDeployStart, LocalDate projectedTermDeployFinish, Integer probabilityToReach,
                       Integer powerOfInfluenceToReach, AnalyzeOfRisk analyze, String substantiationForAnalyze,
                       String commentToHighRiskProcedure, Long decisionForRiskId, Set<String> possibilitiesToImproveRisks,
                       Map<String, String> errors, Long filledRiskId) {
        this.id = id;
        this.rejectToAccomplishPurpose = rejectToAccomplishPurpose;
        this.postponePurposeAccomplishment = postponePurposeAccomplishment;
        this.restrictRangeOfPurposeAccomplishment = restrictRangeOfPurposeAccomplishment;
        this.costOfListedPossibilities = costOfListedPossibilities;
        this.projectedTermDeployStart = projectedTermDeployStart;
        this.projectedTermDeployFinish = projectedTermDeployFinish;
        this.probabilityToReach = probabilityToReach;
        this.powerOfInfluenceToReach = powerOfInfluenceToReach;
        this.analyze = analyze;
        this.substantiationForAnalyze = substantiationForAnalyze;
        this.commentToHighRiskProcedure = commentToHighRiskProcedure;
        this.decisionForRiskId = decisionForRiskId;
        this.possibilitiesToImproveRisks = possibilitiesToImproveRisks;
        this.errors = errors;
        this.filledRiskId = filledRiskId;
    }

    public HighRiskDTO(HighRisk highRisk){

        if(highRisk == null){
            return;
        }

        this.id = highRisk.getId();
        this.rejectToAccomplishPurpose = highRisk.isRejectToAccomplishPurpose();
        this.postponePurposeAccomplishment = highRisk.isPostponePurposeAccomplishment();
        this.restrictRangeOfPurposeAccomplishment = highRisk.isRestrictRangeOfPurposeAccomplishment();

        BigDecimal costOfListedPossibilities = highRisk.getCostOfListedPossibilities();
        if(costOfListedPossibilities != null){
            this.costOfListedPossibilities = costOfListedPossibilities.doubleValue();
        }

        this.projectedTermDeployStart = highRisk.getProjectedTermDeployStart();
        this.projectedTermDeployFinish = highRisk.getProjectedTermDeployFinish();
        this.probabilityToReach = highRisk.getProbabilityToReach();
        this.powerOfInfluenceToReach = highRisk.getPowerOfInfluenceToReach();
        this.analyze = highRisk.getAnalyze();
        this.substantiationForAnalyze = highRisk.getSubstantiationForAnalyze();
        this.commentToHighRiskProcedure = highRisk.getCommentToHighRiskProcedure();

        DecisionForRisk decisionForRisk = highRisk.getDecisionForRisk();
        if(decisionForRisk != null){
            this.decisionForRiskId = decisionForRisk.getId();
        }

        this.possibilitiesToImproveRisks = highRisk.getPossibilitiesToImproveRisks()
            .stream().map(PossibilitiesToImproveRisk::getName).collect(Collectors.toSet());

        FilledRisks filledRisks = highRisk.getFilledRisks();
        if(filledRisks != null){
            this.filledRiskId = filledRisks.getId();
        }
    }

    public HighRiskDTO(HighCommercialRisk highCommercialRisk){

        if(Objects.isNull(highCommercialRisk)){
            return;
        }

        this.id = highCommercialRisk.getId();
        this.rejectToAccomplishPurpose = highCommercialRisk.isRejectToAccomplishPurpose();
        this.postponePurposeAccomplishment = highCommercialRisk.isPostponePurposeAccomplishment();
        this.restrictRangeOfPurposeAccomplishment = highCommercialRisk.isRestrictRangeOfPurposeAccomplishment();

        BigDecimal costOfListedPossibilities = highCommercialRisk.getCostOfListedPossibilities();
        if(costOfListedPossibilities != null){
            this.costOfListedPossibilities = costOfListedPossibilities.doubleValue();
        }

        this.projectedTermDeployStart = highCommercialRisk.getProjectedTermDeployStart();
        this.projectedTermDeployFinish = highCommercialRisk.getProjectedTermDeployFinish();
        this.probabilityToReach = highCommercialRisk.getProbabilityToReach();
        this.powerOfInfluenceToReach = highCommercialRisk.getPowerOfInfluenceToReach();
        this.analyze = highCommercialRisk.getAnalyze();
        this.substantiationForAnalyze = highCommercialRisk.getSubstantiationForAnalyze();
        this.commentToHighRiskProcedure = highCommercialRisk.getCommentToHighRiskProcedure();

        DecisionForRisk decisionForRisk = highCommercialRisk.getDecisionForRisk();
        if(decisionForRisk != null){
            this.decisionForRiskId = decisionForRisk.getId();
        }

        this.possibilitiesToImproveRisks = highCommercialRisk.getPossibilitiesToImproveRisks()
            .stream()
            .map(PossibilitiesToImproveRisk::getName)
            .collect(Collectors.toSet());

        FilledCommercialRisks filledCommercialRisks = highCommercialRisk.getFilledCommercialRisks();
        if(Objects.nonNull(filledCommercialRisks)){
            this.filledRiskId = filledCommercialRisks.getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRejectToAccomplishPurpose() {
        return rejectToAccomplishPurpose;
    }

    public void setRejectToAccomplishPurpose(Boolean rejectToAccomplishPurpose) {
        this.rejectToAccomplishPurpose = rejectToAccomplishPurpose;
    }

    public Boolean getPostponePurposeAccomplishment() {
        return postponePurposeAccomplishment;
    }

    public void setPostponePurposeAccomplishment(Boolean postponePurposeAccomplishment) {
        this.postponePurposeAccomplishment = postponePurposeAccomplishment;
    }

    public Boolean getRestrictRangeOfPurposeAccomplishment() {
        return restrictRangeOfPurposeAccomplishment;
    }

    public void setRestrictRangeOfPurposeAccomplishment(Boolean restrictRangeOfPurposeAccomplishment) {
        this.restrictRangeOfPurposeAccomplishment = restrictRangeOfPurposeAccomplishment;
    }

    public Double getCostOfListedPossibilities() {
        return costOfListedPossibilities;
    }

    public void setCostOfListedPossibilities(Double costOfListedPossibilities) {
        this.costOfListedPossibilities = costOfListedPossibilities;
    }

    public LocalDate getProjectedTermDeployStart() {
        return projectedTermDeployStart;
    }

    public void setProjectedTermDeployStart(LocalDate projectedTermDeployStart) {
        this.projectedTermDeployStart = projectedTermDeployStart;
    }

    public LocalDate getProjectedTermDeployFinish() {
        return projectedTermDeployFinish;
    }

    public void setProjectedTermDeployFinish(LocalDate projectedTermDeployFinish) {
        this.projectedTermDeployFinish = projectedTermDeployFinish;
    }

    public Integer getProbabilityToReach() {
        return probabilityToReach;
    }

    public void setProbabilityToReach(Integer probabilityToReach) {
        this.probabilityToReach = probabilityToReach;
    }

    public Integer getPowerOfInfluenceToReach() {
        return powerOfInfluenceToReach;
    }

    public void setPowerOfInfluenceToReach(Integer powerOfInfluenceToReach) {
        this.powerOfInfluenceToReach = powerOfInfluenceToReach;
    }

    public AnalyzeOfRisk getAnalyze() {
        return analyze;
    }

    public void setAnalyze(AnalyzeOfRisk analyze) {
        this.analyze = analyze;
    }

    public String getSubstantiationForAnalyze() {
        return substantiationForAnalyze;
    }

    public void setSubstantiationForAnalyze(String substantiationForAnalyze) {
        this.substantiationForAnalyze = substantiationForAnalyze;
    }

    public String getCommentToHighRiskProcedure() {
        return commentToHighRiskProcedure;
    }

    public void setCommentToHighRiskProcedure(String commentToHighRiskProcedure) {
        this.commentToHighRiskProcedure = commentToHighRiskProcedure;
    }

    public Long getDecisionForRiskId() {
        return decisionForRiskId;
    }

    public void setDecisionForRiskId(Long decisionForRiskId) {
        this.decisionForRiskId = decisionForRiskId;
    }

    public Set<String> getPossibilitiesToImproveRisks() {
        return possibilitiesToImproveRisks;
    }

    public void setPossibilitiesToImproveRisks(Set<String> possibilitiesToImproveRisks) {
        this.possibilitiesToImproveRisks = possibilitiesToImproveRisks;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public Long getFilledRiskId() {
        return filledRiskId;
    }

    public void setFilledRiskId(Long filledRiskId) {
        this.filledRiskId = filledRiskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HighRiskDTO that = (HighRiskDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(rejectToAccomplishPurpose, that.rejectToAccomplishPurpose) &&
            Objects.equals(postponePurposeAccomplishment, that.postponePurposeAccomplishment) &&
            Objects.equals(restrictRangeOfPurposeAccomplishment, that.restrictRangeOfPurposeAccomplishment) &&
            Objects.equals(costOfListedPossibilities, that.costOfListedPossibilities) &&
            Objects.equals(projectedTermDeployStart, that.projectedTermDeployStart) &&
            Objects.equals(projectedTermDeployFinish, that.projectedTermDeployFinish) &&
            Objects.equals(probabilityToReach, that.probabilityToReach) &&
            Objects.equals(powerOfInfluenceToReach, that.powerOfInfluenceToReach) &&
            analyze == that.analyze &&
            Objects.equals(substantiationForAnalyze, that.substantiationForAnalyze) &&
            Objects.equals(commentToHighRiskProcedure, that.commentToHighRiskProcedure) &&
            Objects.equals(decisionForRiskId, that.decisionForRiskId) &&
            Objects.equals(possibilitiesToImproveRisks, that.possibilitiesToImproveRisks) &&
            Objects.equals(errors, that.errors) &&
            Objects.equals(filledRiskId, that.filledRiskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rejectToAccomplishPurpose, postponePurposeAccomplishment, restrictRangeOfPurposeAccomplishment, costOfListedPossibilities, projectedTermDeployStart, projectedTermDeployFinish, probabilityToReach, powerOfInfluenceToReach, analyze, substantiationForAnalyze, commentToHighRiskProcedure, decisionForRiskId, possibilitiesToImproveRisks, errors, filledRiskId);
    }
}
