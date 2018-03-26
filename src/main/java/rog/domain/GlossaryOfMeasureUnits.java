package rog.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import rog.domain.enumeration.FrequencyOfGatheringData;
import rog.domain.enumeration.UnitsOfMeasurement;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GlossaryOfMeasureUnits.
 */
@Entity
@Table(name = "glossary_of_measure_units")
public class GlossaryOfMeasureUnits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "units_of_measurement")
    private UnitsOfMeasurement unitsOfMeasurement;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency_of_gathering_data")
    private FrequencyOfGatheringData frequencyOfGatheringData;

    @ManyToOne
//    @JsonBackReference
    private GlossaryOfPurposes glossaryOfPurposes;

    @Column(name = "is_checked")
    private Boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private OrganisationStructure organisationStructure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GlossaryOfMeasureUnits name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitsOfMeasurement getUnitsOfMeasurement() {
        return unitsOfMeasurement;
    }

    public GlossaryOfMeasureUnits unitsOfMeasurement(UnitsOfMeasurement unitsOfMeasurement) {
        this.unitsOfMeasurement = unitsOfMeasurement;
        return this;
    }

    public void setUnitsOfMeasurement(UnitsOfMeasurement unitsOfMeasurement) {
        this.unitsOfMeasurement = unitsOfMeasurement;
    }

    public FrequencyOfGatheringData getFrequencyOfGatheringData() {
        return frequencyOfGatheringData;
    }

    public GlossaryOfMeasureUnits frequencyOfGatheringData(FrequencyOfGatheringData frequencyOfGatheringData) {
        this.frequencyOfGatheringData = frequencyOfGatheringData;
        return this;
    }

    public void setFrequencyOfGatheringData(FrequencyOfGatheringData frequencyOfGatheringData) {
        this.frequencyOfGatheringData = frequencyOfGatheringData;
    }

    public GlossaryOfPurposes getGlossaryOfPurposes() {
        return glossaryOfPurposes;
    }

    public GlossaryOfMeasureUnits glossaryOfPurposes(GlossaryOfPurposes glossaryOfPurposes) {
        this.glossaryOfPurposes = glossaryOfPurposes;
        return this;
    }

    public void setGlossaryOfPurposes(GlossaryOfPurposes glossaryOfPurposes) {
        this.glossaryOfPurposes = glossaryOfPurposes;
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

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlossaryOfMeasureUnits that = (GlossaryOfMeasureUnits) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GlossaryOfMeasureUnits{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", unitsOfMeasurement='" + getUnitsOfMeasurement() + "'" +
            ", frequencyOfGatheringData='" + getFrequencyOfGatheringData() + "'" +
            "}";
    }
}
