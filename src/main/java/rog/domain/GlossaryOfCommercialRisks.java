package rog.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A GlossaryOfCommercialRisks.
 */
@Entity
@Table(name = "glossary_of_commercial_risks")
public class GlossaryOfCommercialRisks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "creation_date", updatable = false)
    private LocalDate creationDate = LocalDate.now();

    @Column(name = "important_to")
    private LocalDate importantTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    @Column(name = "is_checked")
    private Boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private OrganisationStructure organisationStructure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GlossaryOfCommercialRisks name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public GlossaryOfCommercialRisks completionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
        return this;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public GlossaryOfCommercialRisks creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getImportantTo() {
        return importantTo;
    }

    public GlossaryOfCommercialRisks importantTo(LocalDate importantTo) {
        this.importantTo = importantTo;
        return this;
    }

    public void setImportantTo(LocalDate importantTo) {
        this.importantTo = importantTo;
    }

    public User getUser() {
        return user;
    }

    public GlossaryOfCommercialRisks user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrganisationStructure getOrganisationStructure() {
        return organisationStructure;
    }

    public GlossaryOfCommercialRisks organisationStructure(OrganisationStructure organisationStructure) {
        this.organisationStructure = organisationStructure;
        return this;
    }

    public void setOrganisationStructure(OrganisationStructure organisationStructure) {
        this.organisationStructure = organisationStructure;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GlossaryOfCommercialRisks glossaryOfCommercialRisks = (GlossaryOfCommercialRisks) o;
        return glossaryOfCommercialRisks.getId() != null && getId() != null &&
            Objects.equals(getId(), glossaryOfCommercialRisks.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GlossaryOfCommercialRisks{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", importantTo='" + getImportantTo() + "'" +
            "}";
    }
}
