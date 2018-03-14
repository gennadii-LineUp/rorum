package rog.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import rog.domain.enumeration.StatusOfIncident;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Incident.
 */
@Entity
@Table(name = "incident")
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "description_of_reaction")
    private String descriptionOfReaction;

    @Column(name = "description_of_planned_activities")
    private String descriptionOfPlannedActivities;

    @Column(name = "is_critical")
    private Boolean isCritical;

    @NotNull
    @ManyToOne
    @JsonBackReference
    private SetOfSentPurposes setOfSentPurposes;

    @ManyToOne
    @JsonBackReference
    private GlossaryOfPurposes glossaryOfPurposes;

    @ManyToOne
    @JsonBackReference
    private FilledRisks filledRisks;

    @ManyToOne
    @JsonBackReference
    private FilledCommercialRisks filledCommercialRisks;

    @NotNull
    @ManyToOne
    private User user;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusOfIncident statusOfIncident = StatusOfIncident.REPORTED;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Incident description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionOfReaction() {
        return descriptionOfReaction;
    }

    public Incident descriptionOfReaction(String descriptionOfReaction) {
        this.descriptionOfReaction = descriptionOfReaction;
        return this;
    }

    public void setDescriptionOfReaction(String descriptionOfReaction) {
        this.descriptionOfReaction = descriptionOfReaction;
    }

    public String getDescriptionOfPlannedActivities() {
        return descriptionOfPlannedActivities;
    }

    public Incident descriptionOfPlannedActivities(String descriptionOfPlannedActivities) {
        this.descriptionOfPlannedActivities = descriptionOfPlannedActivities;
        return this;
    }

    public void setDescriptionOfPlannedActivities(String descriptionOfPlannedActivities) {
        this.descriptionOfPlannedActivities = descriptionOfPlannedActivities;
    }

    public Boolean isCritical() {
        return isCritical;
    }

    public void setCritical(Boolean isCritical) {
        this.isCritical = isCritical;
    }

    public SetOfSentPurposes getSetOfSentPurposes() {
        return setOfSentPurposes;
    }

    public Incident setOfSentPurposes(SetOfSentPurposes setOfSentPurposes) {
        this.setOfSentPurposes = setOfSentPurposes;
        return this;
    }

    public void setSetOfSentPurposes(SetOfSentPurposes setOfSentPurposes) {
        this.setOfSentPurposes = setOfSentPurposes;
    }

    public GlossaryOfPurposes getGlossaryOfPurposes() {
        return glossaryOfPurposes;
    }

    public Incident glossaryOfPurposes(GlossaryOfPurposes glossaryOfPurposes) {
        this.glossaryOfPurposes = glossaryOfPurposes;
        return this;
    }

    public void setGlossaryOfPurposes(GlossaryOfPurposes glossaryOfPurposes) {
        this.glossaryOfPurposes = glossaryOfPurposes;
    }

    public FilledRisks getFilledRisks() {
        return filledRisks;
    }

    public Incident filledRisks(FilledRisks filledRisks) {
        this.filledRisks = filledRisks;
        return this;
    }

    public void setFilledRisks(FilledRisks filledRisks) {
        this.filledRisks = filledRisks;
    }

    public FilledCommercialRisks getFilledCommercialRisks() {
        return filledCommercialRisks;
    }

    public Incident filledCommercialRisks(FilledCommercialRisks filledCommercialRisks) {
        this.filledCommercialRisks = filledCommercialRisks;
        return this;
    }

    public void setFilledCommercialRisks(FilledCommercialRisks filledCommercialRisks) {
        this.filledCommercialRisks = filledCommercialRisks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public StatusOfIncident getStatusOfIncident() {
        return statusOfIncident;
    }

    public void setStatusOfIncident(StatusOfIncident statusOfIncident) {
        this.statusOfIncident = statusOfIncident;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incident incident = (Incident) o;
        return Objects.equals(id, incident.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Incident{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", descriptionOfReaction='" + descriptionOfReaction + '\'' +
            ", descriptionOfPlannedActivities='" + descriptionOfPlannedActivities + '\'' +
            ", isCritical=" + isCritical +
            ", setOfSentPurposes=" + setOfSentPurposes +
            ", glossaryOfPurposes=" + glossaryOfPurposes +
            ", filledRisks=" + filledRisks +
            ", filledCommercialRisks=" + filledCommercialRisks +
            ", user=" + user +
            ", creationDate=" + creationDate +
            ", statusOfIncident=" + statusOfIncident +
            '}';
    }
}
