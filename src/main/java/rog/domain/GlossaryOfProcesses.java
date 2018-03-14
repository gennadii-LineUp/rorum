package rog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A GlossaryOfProcesses.
 */
@Entity
@Table(name = "glossary_of_processes")
public class GlossaryOfProcesses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "important_to")
    private LocalDate importantTo;

    @OneToMany(mappedBy = "glossaryOfProcesses", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrganisationStructure> organisationStructures = new HashSet<>();

    @OneToMany(mappedBy = "glossaryOfProcesses", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<GlossaryOfPurposes> glossaryOfPurposes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GlossaryOfProcesses name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getImportantTo() {
        return importantTo;
    }

    public GlossaryOfProcesses importantTo(LocalDate importantTo) {
        this.importantTo = importantTo;
        return this;
    }

    public void setImportantTo(LocalDate importantTo) {
        this.importantTo = importantTo;
    }

    public Set<OrganisationStructure> getOrganisationStructures() {
        return organisationStructures;
    }

    public GlossaryOfProcesses organisationStructures(Set<OrganisationStructure> organisationStructures) {
        this.organisationStructures = organisationStructures;
        return this;
    }

    public GlossaryOfProcesses addOrganisationStructure(OrganisationStructure organisationStructure) {
        this.organisationStructures.add(organisationStructure);
        organisationStructure.setGlossaryOfProcesses(this);
        return this;
    }

    public GlossaryOfProcesses removeOrganisationStructure(OrganisationStructure organisationStructure) {
        this.organisationStructures.remove(organisationStructure);
        organisationStructure.setGlossaryOfProcesses(null);
        return this;
    }

    public void setOrganisationStructures(Set<OrganisationStructure> organisationStructures) {
        this.organisationStructures = organisationStructures;
    }

    public Set<GlossaryOfPurposes> getGlossaryOfPurposes() {
        return glossaryOfPurposes;
    }

    public GlossaryOfProcesses glossaryOfPurposes(Set<GlossaryOfPurposes> glossaryOfPurposes) {
        this.glossaryOfPurposes = glossaryOfPurposes;
        return this;
    }

    public GlossaryOfProcesses addGlossaryOfPurposes(GlossaryOfPurposes glossaryOfPurposes) {
        this.glossaryOfPurposes.add(glossaryOfPurposes);
        glossaryOfPurposes.setGlossaryOfProcesses(this);
        return this;
    }

    public GlossaryOfProcesses removeGlossaryOfPurposes(GlossaryOfPurposes glossaryOfPurposes) {
        this.glossaryOfPurposes.remove(glossaryOfPurposes);
        glossaryOfPurposes.setGlossaryOfProcesses(null);
        return this;
    }

    public void setGlossaryOfPurposes(Set<GlossaryOfPurposes> glossaryOfPurposes) {
        this.glossaryOfPurposes = glossaryOfPurposes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GlossaryOfProcesses glossaryOfProcesses = (GlossaryOfProcesses) o;
        return glossaryOfProcesses.getId() != null && getId() != null &&
            Objects.equals(getId(), glossaryOfProcesses.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GlossaryOfProcesses{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", importantTo='" + getImportantTo() + "'" +
            "}";
    }
}
