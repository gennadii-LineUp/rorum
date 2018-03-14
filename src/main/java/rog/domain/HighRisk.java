package rog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import rog.domain.enumeration.AnalyzeOfRisk;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A HighRisk.
 */
@Entity
@Table(name = "high_risk")
public class HighRisk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "reject_to_accomplish_purpose")
    private Boolean rejectToAccomplishPurpose;

    @Column(name = "postpone_purpose_accomplishment")
    private Boolean postponePurposeAccomplishment;

    @Column(name = "restrict_range_of_purpose_accomplishment")
    private Boolean restrictRangeOfPurposeAccomplishment;

    @Column(name = "cost_of_listed_possibilities", precision=10, scale=2)
    private BigDecimal costOfListedPossibilities;

    @Column(name = "projected_term_deploy_start")
    private LocalDate projectedTermDeployStart;

    @Column(name = "projected_term_deploy_finish")
    private LocalDate projectedTermDeployFinish;

    @Column(name = "probability_to_reach")
    private Integer probabilityToReach;

    @Column(name = "power_of_influence_to_reach")
    private Integer powerOfInfluenceToReach;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_analyze")
    private AnalyzeOfRisk analyze;

    @Column(name = "substantiation_for_analyze")
    private String substantiationForAnalyze;

    @Column(name = "comment_to_high_risk_procedure")
    private String commentToHighRiskProcedure;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filled_risk_id")
    @JsonIgnore
    @NotNull
    private FilledRisks filledRisks;

    @OneToOne
    private DecisionForRisk decisionForRisk;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "high_risk_possibilities_to_improve_risk",
               joinColumns = @JoinColumn(name="high_risks_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="possibilities_to_improve_risks_id", referencedColumnName="id"))
    private Set<PossibilitiesToImproveRisk> possibilitiesToImproveRisks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isRejectToAccomplishPurpose() {
        return rejectToAccomplishPurpose;
    }

    public HighRisk rejectToAccomplishPurpose(Boolean rejectToAccomplishPurpose) {
        this.rejectToAccomplishPurpose = rejectToAccomplishPurpose;
        return this;
    }

    public void setRejectToAccomplishPurpose(Boolean rejectToAccomplishPurpose) {
        this.rejectToAccomplishPurpose = rejectToAccomplishPurpose;
    }

    public Boolean isPostponePurposeAccomplishment() {
        return postponePurposeAccomplishment;
    }

    public HighRisk postponePurposeAccomplishment(Boolean postponePurposeAccomplishment) {
        this.postponePurposeAccomplishment = postponePurposeAccomplishment;
        return this;
    }

    public void setPostponePurposeAccomplishment(Boolean postponePurposeAccomplishment) {
        this.postponePurposeAccomplishment = postponePurposeAccomplishment;
    }

    public Boolean isRestrictRangeOfPurposeAccomplishment() {
        return restrictRangeOfPurposeAccomplishment;
    }

    public HighRisk restrictRangeOfPurposeAccomplishment(Boolean restrictRangeOfPurposeAccomplishment) {
        this.restrictRangeOfPurposeAccomplishment = restrictRangeOfPurposeAccomplishment;
        return this;
    }

    public void setRestrictRangeOfPurposeAccomplishment(Boolean restrictRangeOfPurposeAccomplishment) {
        this.restrictRangeOfPurposeAccomplishment = restrictRangeOfPurposeAccomplishment;
    }

    public BigDecimal getCostOfListedPossibilities() {
        return costOfListedPossibilities;
    }

    public HighRisk costOfListedPossibilities(BigDecimal costOfListedPossibilities) {
        this.costOfListedPossibilities = costOfListedPossibilities;
        return this;
    }

    public void setCostOfListedPossibilities(BigDecimal costOfListedPossibilities) {
        this.costOfListedPossibilities = costOfListedPossibilities;
    }

    public LocalDate getProjectedTermDeployStart() {
        return projectedTermDeployStart;
    }

    public HighRisk projectedTermDeployStart(LocalDate projectedTermDeployStart) {
        this.projectedTermDeployStart = projectedTermDeployStart;
        return this;
    }

    public void setProjectedTermDeployStart(LocalDate projectedTermDeployStart) {
        this.projectedTermDeployStart = projectedTermDeployStart;
    }

    public LocalDate getProjectedTermDeployFinish() {
        return projectedTermDeployFinish;
    }

    public HighRisk projectedTermDeployFinish(LocalDate projectedTermDeployFinish) {
        this.projectedTermDeployFinish = projectedTermDeployFinish;
        return this;
    }

    public void setProjectedTermDeployFinish(LocalDate projectedTermDeployFinish) {
        this.projectedTermDeployFinish = projectedTermDeployFinish;
    }

    public Integer getProbabilityToReach() {
        return probabilityToReach;
    }

    public HighRisk probabilityToReach(Integer probabilityToReach) {
        this.probabilityToReach = probabilityToReach;
        return this;
    }

    public void setProbabilityToReach(Integer probabilityToReach) {
        this.probabilityToReach = probabilityToReach;
    }

    public Integer getPowerOfInfluenceToReach() {
        return powerOfInfluenceToReach;
    }

    public HighRisk powerOfInfluenceToReach(Integer powerOfInfluenceToReach) {
        this.powerOfInfluenceToReach = powerOfInfluenceToReach;
        return this;
    }

    public void setPowerOfInfluenceToReach(Integer powerOfInfluenceToReach) {
        this.powerOfInfluenceToReach = powerOfInfluenceToReach;
    }

    public AnalyzeOfRisk getAnalyze() {
        return analyze;
    }

    public HighRisk analyze(AnalyzeOfRisk analyze) {
        this.analyze = analyze;
        return this;
    }

    public void setAnalyze(AnalyzeOfRisk analyze) {
        this.analyze = analyze;
    }

    public String getSubstantiationForAnalyze() {
        return substantiationForAnalyze;
    }

    public HighRisk substantiationForAnalyze(String substantiationForAnalyze) {
        this.substantiationForAnalyze = substantiationForAnalyze;
        return this;
    }

    public void setSubstantiationForAnalyze(String substantiationForAnalyze) {
        this.substantiationForAnalyze = substantiationForAnalyze;
    }

    public String getCommentToHighRiskProcedure() {
        return commentToHighRiskProcedure;
    }

    public HighRisk commentToHighRiskProcedure(String commentToHighRiskProcedure) {
        this.commentToHighRiskProcedure = commentToHighRiskProcedure;
        return this;
    }

    public void setCommentToHighRiskProcedure(String commentToHighRiskProcedure) {
        this.commentToHighRiskProcedure = commentToHighRiskProcedure;
    }

    public FilledRisks getFilledRisks() {
        return filledRisks;
    }

    public HighRisk filledRisks(FilledRisks filledRisks) {
        this.filledRisks = filledRisks;
        return this;
    }

    public void setFilledRisks(FilledRisks filledRisks) {
        this.filledRisks = filledRisks;
    }

    public DecisionForRisk getDecisionForRisk() {
        return decisionForRisk;
    }

    public HighRisk decisionForRisk(DecisionForRisk decisionForRisk) {
        this.decisionForRisk = decisionForRisk;
        return this;
    }

    public void setDecisionForRisk(DecisionForRisk decisionForRisk) {
        this.decisionForRisk = decisionForRisk;
    }

    public Set<PossibilitiesToImproveRisk> getPossibilitiesToImproveRisks() {
        return possibilitiesToImproveRisks;
    }

    public HighRisk possibilitiesToImproveRisks(Set<PossibilitiesToImproveRisk> possibilitiesToImproveRisks) {
        this.possibilitiesToImproveRisks = possibilitiesToImproveRisks;
        return this;
    }


    public void setPossibilitiesToImproveRisks(Set<PossibilitiesToImproveRisk> possibilitiesToImproveRisks) {
        this.possibilitiesToImproveRisks = possibilitiesToImproveRisks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HighRisk highRisk = (HighRisk) o;
        return highRisk.getId() != null && getId() != null &&
            Objects.equals(getId(), highRisk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HighRisk{" +
            "id=" + getId() +
            ", rejectToAccomplishPurpose='" + isRejectToAccomplishPurpose() + "'" +
            ", postponePurposeAccomplishment='" + isPostponePurposeAccomplishment() + "'" +
            ", restrictRangeOfPurposeAccomplishment='" + isRestrictRangeOfPurposeAccomplishment() + "'" +
            ", costOfListedPossibilities='" + getCostOfListedPossibilities() + "'" +
            ", projectedTermDeployStart='" + getProjectedTermDeployStart() + "'" +
            ", projectedTermDeployFinish='" + getProjectedTermDeployFinish() + "'" +
            ", probabilityToReach='" + getProbabilityToReach() + "'" +
            ", powerOfInfluenceToReach='" + getPowerOfInfluenceToReach() + "'" +
            ", analyze='" + getAnalyze() + "'" +
            ", substantiationForAnalyze='" + getSubstantiationForAnalyze() + "'" +
            ", commentToHighRiskProcedure='" + getCommentToHighRiskProcedure() + "'" +
            "}";
    }
}
