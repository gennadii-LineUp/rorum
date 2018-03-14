package rog.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import rog.domain.enumeration.SpecifyingCells;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A OrganisationStructure.
 */
@Entity
@Table(name = "organisation_structure")
public class OrganisationStructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "supervisory_unit_id")
    private Long supervisoryUnitId;

    @Column(name = "status_id")
    private Boolean statusId;

    @Enumerated(EnumType.STRING)
    @Column(name = "specifying_cells")
    private SpecifyingCells specifyingCells;

    @ManyToOne
    @JsonBackReference
    private GlossaryOfProcesses glossaryOfProcesses;

    @OneToMany(mappedBy = "organisationStructure", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<GlossaryOfCommercialRisks> glossaryOfCommercialRisks = new HashSet<>();

    @OneToMany(mappedBy = "organisationStructure", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    public OrganisationStructure name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public OrganisationStructure parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getSupervisoryUnitId() {
        return supervisoryUnitId;
    }

    public OrganisationStructure supervisoryUnitId(Long supervisoryUnitId) {
        this.supervisoryUnitId = supervisoryUnitId;
        return this;
    }

    public void setSupervisoryUnitId(Long supervisoryUnitId) {
        this.supervisoryUnitId = supervisoryUnitId;
    }

    public Boolean isStatusId() {
        return statusId;
    }

    public OrganisationStructure statusId(Boolean statusId) {
        this.statusId = statusId;
        return this;
    }

    public void setStatusId(Boolean statusId) {
        this.statusId = statusId;
    }

    public SpecifyingCells getSpecifyingCells() {
        return specifyingCells;
    }

    public OrganisationStructure specifyingCells(SpecifyingCells specifyingCells) {
        this.specifyingCells = specifyingCells;
        return this;
    }

    public void setSpecifyingCells(SpecifyingCells specifyingCells) {
        this.specifyingCells = specifyingCells;
    }

    public GlossaryOfProcesses getGlossaryOfProcesses() {
        return glossaryOfProcesses;
    }

    public OrganisationStructure glossaryOfProcesses(GlossaryOfProcesses glossaryOfProcesses) {
        this.glossaryOfProcesses = glossaryOfProcesses;
        return this;
    }

    public void setGlossaryOfProcesses(GlossaryOfProcesses glossaryOfProcesses) {
        this.glossaryOfProcesses = glossaryOfProcesses;
    }

    public Set<GlossaryOfCommercialRisks> getGlossaryOfCommercialRisks() {
        return glossaryOfCommercialRisks;
    }

    public OrganisationStructure glossaryOfCommercialRisks(Set<GlossaryOfCommercialRisks> glossaryOfCommercialRisks) {
        this.glossaryOfCommercialRisks = glossaryOfCommercialRisks;
        return this;
    }

    public OrganisationStructure addGlossaryOfCommercialRisks(GlossaryOfCommercialRisks glossaryOfCommercialRisks) {
        this.glossaryOfCommercialRisks.add(glossaryOfCommercialRisks);
        glossaryOfCommercialRisks.setOrganisationStructure(this);
        return this;
    }

    public OrganisationStructure removeGlossaryOfCommercialRisks(GlossaryOfCommercialRisks glossaryOfCommercialRisks) {
        this.glossaryOfCommercialRisks.remove(glossaryOfCommercialRisks);
        glossaryOfCommercialRisks.setOrganisationStructure(null);
        return this;
    }

    public void setGlossaryOfCommercialRisks(Set<GlossaryOfCommercialRisks> glossaryOfCommercialRisks) {
        this.glossaryOfCommercialRisks = glossaryOfCommercialRisks;
    }

    public Set<GlossaryOfPurposes> getGlossaryOfPurposes() {
        return glossaryOfPurposes;
    }

    public void setGlossaryOfPurposes(Set<GlossaryOfPurposes> glossaryOfPurposes) {
        this.glossaryOfPurposes = glossaryOfPurposes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationStructure that = (OrganisationStructure) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrganisationStructure{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", supervisoryUnitId='" + getSupervisoryUnitId() + "'" +
            ", statusId='" + isStatusId() + "'" +
            ", specifyingCells='" + getSpecifyingCells() + "'" +
            "}";
    }
}
