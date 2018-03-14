package rog.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import rog.domain.enumeration.ReactionOnRisks;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A FilledCommercialRisks.
 */
@Entity
@Table(name = "filled_commercial_risks")
public class FilledCommercialRisks implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "probability")
    private Integer probability;

    @Column(name = "power_of_influence")
    private Integer powerOfInfluence;

    @Column(name = "strength_of_control_function_probability")
    private Integer strengthOfControlFunctionProbability;

    @Column(name = "strength_of_control_function_power_of_influence")
    private Integer strengthOfControlFunctionPowerOfInfluence ;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_on_risk")
    private ReactionOnRisks reactionOnRisks;

    @Column(name = "notation_concern_risk")
    private String notationConcernRisk;

    @ManyToOne
    @NotNull
    @JsonBackReference
    private GlossaryOfCommercialRisks glossaryOfCommercialRisks;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonBackReference
    private CommercialRisksPurposes commercialRisksPurposes;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "is_saved", nullable = false)
    private Boolean isSaved = Boolean.FALSE;

    @Column(name = "responsible_person")
    private String responsiblePerson;

    @Column(name = "state_for_day")
    private LocalDate stateForDay;

    @OneToOne(mappedBy = "filledCommercialRisks", cascade = CascadeType.ALL, orphanRemoval = true)
    private HighCommercialRisk highCommercialRisk;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "filled_commercial_risks_probability_configuration",
        joinColumns = @JoinColumn(name="filled_commercial_risks_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="probability_id", referencedColumnName="id"))
    private Set<Probability> probabilities = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "filled_commercial_risks_power_of_influence_configuration",
        joinColumns = @JoinColumn(name="filled_commercial_risks_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="power_of_influence_id", referencedColumnName="id"))
    private Set<PowerOfInfluence> powerOfInfluences = new HashSet<>();

    @OneToMany(mappedBy = "filledCommercialRisks", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Incident> incidents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProbability() {
        return probability;
    }

    public FilledCommercialRisks probability(Integer probability) {
        this.probability = probability;
        return this;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Integer getPowerOfInfluence() {
        return powerOfInfluence;
    }

    public FilledCommercialRisks powerOfInfluence(Integer powerOfInfluence) {
        this.powerOfInfluence = powerOfInfluence;
        return this;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setPowerOfInfluence(Integer powerOfInfluence) {
        this.powerOfInfluence = powerOfInfluence;
    }

    public GlossaryOfCommercialRisks getGlossaryOfCommercialRisks() {
        return glossaryOfCommercialRisks;
    }

    public FilledCommercialRisks glossaryOfCommercialRisks(GlossaryOfCommercialRisks glossaryOfCommercialRisks) {
        this.glossaryOfCommercialRisks = glossaryOfCommercialRisks;
        return this;
    }

    public void setGlossaryOfCommercialRisks(GlossaryOfCommercialRisks glossaryOfCommercialRisks) {
        this.glossaryOfCommercialRisks = glossaryOfCommercialRisks;
    }

    public CommercialRisksPurposes getCommercialRisksPurposes() {
        return commercialRisksPurposes;
    }

    public FilledCommercialRisks commercialRisksPurposes(CommercialRisksPurposes commercialRisksPurposes) {
        this.commercialRisksPurposes = commercialRisksPurposes;
        return this;
    }

    public void setCommercialRisksPurposes(CommercialRisksPurposes commercialRisksPurposes) {
        this.commercialRisksPurposes = commercialRisksPurposes;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public HighCommercialRisk getHighCommercialRisk() {
        return highCommercialRisk;
    }

    public void setHighCommercialRisk(HighCommercialRisk highCommercialRisk) {
        this.highCommercialRisk = highCommercialRisk;
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

    public Set<Probability> getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(Set<Probability> probabilities) {
        this.probabilities = probabilities;
    }

    public Set<PowerOfInfluence> getPowerOfInfluences() {
        return powerOfInfluences;
    }

    public void setPowerOfInfluences(Set<PowerOfInfluence> powerOfInfluences) {
        this.powerOfInfluences = powerOfInfluences;
    }

    public Set<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(Set<Incident> incidents) {
        this.incidents = incidents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilledCommercialRisks that = (FilledCommercialRisks) o;
        return
            Objects.equals(glossaryOfCommercialRisks, that.glossaryOfCommercialRisks) &&
            Objects.equals(commercialRisksPurposes, that.commercialRisksPurposes) &&
            Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FilledCommercialRisks{" +
            "id=" + id +
            ", probability=" + probability +
            ", powerOfInfluence=" + powerOfInfluence +
            ", reactionOnRisks=" + reactionOnRisks +
            ", notationConcernRisk='" + notationConcernRisk + '\'' +
            ", commercialRisksPurposes=" + commercialRisksPurposes +
            ", creationDate=" + creationDate +
            ", isSaved=" + isSaved +
            '}';
    }
}
