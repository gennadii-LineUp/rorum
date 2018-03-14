package rog.service.dto;

import rog.domain.GlossaryOfMeasureUnits;
import rog.domain.GlossaryOfPurposes;
import rog.domain.OrganisationStructure;
import rog.domain.enumeration.FrequencyOfGatheringData;
import rog.domain.enumeration.UnitsOfMeasurement;

import java.io.Serializable;
import java.util.Objects;


public class GlossaryOfMeasureUnitsDTO implements Serializable {

    private Long id;

    private String name;

    private UnitsOfMeasurement unitsOfMeasurement;

    private FrequencyOfGatheringData frequencyOfGatheringData;

    private Long organisationId;

    private Long purposeId;

    private Boolean isChecked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitsOfMeasurement getUnitsOfMeasurement() {
        return unitsOfMeasurement;
    }

    public void setUnitsOfMeasurement(UnitsOfMeasurement unitsOfMeasurement) {
        this.unitsOfMeasurement = unitsOfMeasurement;
    }

    public FrequencyOfGatheringData getFrequencyOfGatheringData() {
        return frequencyOfGatheringData;
    }

    public void setFrequencyOfGatheringData(FrequencyOfGatheringData frequencyOfGatheringData) {
        this.frequencyOfGatheringData = frequencyOfGatheringData;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public Long getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(Long purposeId) {
        this.purposeId = purposeId;
    }

    public Boolean isChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public GlossaryOfMeasureUnitsDTO() {
    }

    public GlossaryOfMeasureUnitsDTO(Long id, String name, UnitsOfMeasurement unitsOfMeasurement,
                                     FrequencyOfGatheringData frequencyOfGatheringData, Long organisationId,
                                     Long purposeId, Boolean isChecked) {
        this.id = id;
        this.name = name;
        this.unitsOfMeasurement = unitsOfMeasurement;
        this.frequencyOfGatheringData = frequencyOfGatheringData;
        this.organisationId = organisationId;
        this.purposeId = purposeId;
        this.isChecked = isChecked;
    }

    public GlossaryOfMeasureUnitsDTO(GlossaryOfMeasureUnits glossaryOfMeasureUnits){

        if(Objects.isNull(glossaryOfMeasureUnits)){
            return;
        }

        this.id = glossaryOfMeasureUnits.getId();
        this.name = glossaryOfMeasureUnits.getName();
        this.frequencyOfGatheringData = glossaryOfMeasureUnits.getFrequencyOfGatheringData();
        this.unitsOfMeasurement = glossaryOfMeasureUnits.getUnitsOfMeasurement();
        this.isChecked = glossaryOfMeasureUnits.getChecked();

        GlossaryOfPurposes glossaryOfPurposes = glossaryOfMeasureUnits.getGlossaryOfPurposes();
        if(Objects.nonNull(glossaryOfPurposes)){
            this.purposeId = glossaryOfPurposes.getId();
        }

        OrganisationStructure organisationStructure = glossaryOfMeasureUnits.getOrganisationStructure();
        if(Objects.nonNull(organisationStructure)) {
            this.organisationId = organisationStructure.getId();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlossaryOfMeasureUnitsDTO that = (GlossaryOfMeasureUnitsDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            unitsOfMeasurement == that.unitsOfMeasurement &&
            frequencyOfGatheringData == that.frequencyOfGatheringData &&
            Objects.equals(organisationId, that.organisationId) &&
            Objects.equals(purposeId, that.purposeId) &&
            Objects.equals(isChecked, that.isChecked);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, unitsOfMeasurement, frequencyOfGatheringData, organisationId, purposeId, isChecked);
    }
}
