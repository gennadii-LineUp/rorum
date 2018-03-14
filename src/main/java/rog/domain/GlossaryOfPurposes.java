package rog.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import rog.domain.enumeration.AssignmentToCell;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A GlossaryOfPurposes.
 */
@Entity
@Table(name = "glossary_of_purposes")
public class GlossaryOfPurposes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "sum_up")
    private Boolean sumUp;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_to_cell")
    private AssignmentToCell assignmentToCell;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private GlossaryOfProcesses glossaryOfProcesses;

    @Column(name = "is_checked")
    private Boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private OrganisationStructure organisationStructure;

    @OneToMany(mappedBy = "glossaryOfPurposes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<GlossaryOfMeasureUnits> glossaryOfMeasureUnits = new HashSet<>();

    @OneToMany(mappedBy = "glossaryOfPurposes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<GlossaryOfRisks> glossaryOfRisks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "setOfSentPurposes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Incident> incidents = new HashSet<>();

    @OneToMany(mappedBy = "glossaryOfPurposes", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<GlossaryOfKeyRiskIndicators> glossaryOfKeyRiskIndicators = new HashSet<>();


    public GlossaryOfPurposes() {
    }

    public GlossaryOfPurposes(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GlossaryOfPurposes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public GlossaryOfPurposes parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean isSumUp() {
        return sumUp;
    }

    public GlossaryOfPurposes sumUp(Boolean sumUp) {
        this.sumUp = sumUp;
        return this;
    }

    public void setSumUp(Boolean sumUp) {
        this.sumUp = sumUp;
    }

    public AssignmentToCell getAssignmentToCell() {
        return assignmentToCell;
    }

    public GlossaryOfPurposes assignmentToCell(AssignmentToCell assignmentToCell) {
        this.assignmentToCell = assignmentToCell;
        return this;
    }

    public void setAssignmentToCell(AssignmentToCell assignmentToCell) {
        this.assignmentToCell = assignmentToCell;
    }

    public GlossaryOfProcesses getGlossaryOfProcesses() {
        return glossaryOfProcesses;
    }

    public GlossaryOfPurposes glossaryOfProcesses(GlossaryOfProcesses glossaryOfProcesses) {
        this.glossaryOfProcesses = glossaryOfProcesses;
        return this;
    }

    public void setGlossaryOfProcesses(GlossaryOfProcesses glossaryOfProcesses) {
        this.glossaryOfProcesses = glossaryOfProcesses;
    }

    public Set<GlossaryOfMeasureUnits> getGlossaryOfMeasureUnits() {
        return glossaryOfMeasureUnits;
    }

    public GlossaryOfPurposes glossaryOfMeasureUnits(Set<GlossaryOfMeasureUnits> glossaryOfMeasureUnits) {
        this.glossaryOfMeasureUnits = glossaryOfMeasureUnits;
        return this;
    }

    public GlossaryOfPurposes addGlossaryOfMeasureUnits(GlossaryOfMeasureUnits glossaryOfMeasureUnits) {
        this.glossaryOfMeasureUnits.add(glossaryOfMeasureUnits);
        glossaryOfMeasureUnits.setGlossaryOfPurposes(this);
        return this;
    }

    public GlossaryOfPurposes removeGlossaryOfMeasureUnits(GlossaryOfMeasureUnits glossaryOfMeasureUnits) {
        this.glossaryOfMeasureUnits.remove(glossaryOfMeasureUnits);
        glossaryOfMeasureUnits.setGlossaryOfPurposes(null);
        return this;
    }

    public void setGlossaryOfMeasureUnits(Set<GlossaryOfMeasureUnits> glossaryOfMeasureUnits) {
        this.glossaryOfMeasureUnits = glossaryOfMeasureUnits;
    }

    public Set<GlossaryOfRisks> getGlossaryOfRisks() {
        return glossaryOfRisks;
    }

    public GlossaryOfPurposes glossaryOfRisks(Set<GlossaryOfRisks> glossaryOfRisks) {
        this.glossaryOfRisks = glossaryOfRisks;
        return this;
    }

    public GlossaryOfPurposes addGlossaryOfRisks(GlossaryOfRisks glossaryOfRisks) {
        this.glossaryOfRisks.add(glossaryOfRisks);
        glossaryOfRisks.setGlossaryOfPurposes(this);
        return this;
    }

    public GlossaryOfPurposes removeGlossaryOfRisks(GlossaryOfRisks glossaryOfRisks) {
        this.glossaryOfRisks.remove(glossaryOfRisks);
        glossaryOfRisks.setGlossaryOfPurposes(null);
        return this;
    }

    public void setGlossaryOfRisks(Set<GlossaryOfRisks> glossaryOfRisks) {
        this.glossaryOfRisks = glossaryOfRisks;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public OrganisationStructure getOrganisationStructure() {
        return organisationStructure;
    }

    public void setOrganisationStructure(OrganisationStructure organisationStructure) {
        this.organisationStructure = organisationStructure;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(Set<Incident> incidents) {
        this.incidents = incidents;
    }

    public Set<GlossaryOfKeyRiskIndicators> getGlossaryOfKeyRiskIndicators() {
        return glossaryOfKeyRiskIndicators;
    }

    public void setGlossaryOfKeyRiskIndicators(Set<GlossaryOfKeyRiskIndicators> glossaryOfKeyRiskIndicators) {
        this.glossaryOfKeyRiskIndicators = glossaryOfKeyRiskIndicators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlossaryOfPurposes that = (GlossaryOfPurposes) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GlossaryOfPurposes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", sumUp='" + isSumUp() + "'" +
            ", assignmentToCell='" + getAssignmentToCell() + "'" +
            "}";
    }
}
