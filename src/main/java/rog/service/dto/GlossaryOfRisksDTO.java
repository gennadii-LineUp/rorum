package rog.service.dto;

import rog.domain.GlossaryOfPurposes;
import rog.domain.GlossaryOfRisks;
import rog.domain.OrganisationStructure;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class GlossaryOfRisksDTO implements Serializable {

    private Long id;

    private String name;

    private Long organisationId;

    private Long purposeId;

    private LocalDate completionDate;

    private LocalDate importantTo;

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

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public LocalDate getImportantTo() {
        return importantTo;
    }

    public void setImportantTo(LocalDate importantTo) {
        this.importantTo = importantTo;
    }

    public Boolean isChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
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

    public GlossaryOfRisksDTO() {
    }

    public GlossaryOfRisksDTO(Long id, String name, Long organisationId, Long purposeId, LocalDate completionDate,
                              LocalDate importantTo, Boolean isChecked) {
        this.id = id;
        this.name = name;
        this.organisationId = organisationId;
        this.purposeId = purposeId;
        this.completionDate = completionDate;
        this.importantTo = importantTo;
        this.isChecked = isChecked;
    }

    public GlossaryOfRisksDTO(GlossaryOfRisks glossaryOfRisks){

        if(Objects.isNull(glossaryOfRisks)){
            return;
        }

        this.id = glossaryOfRisks.getId();
        this.name = glossaryOfRisks.getName();
        this.completionDate = glossaryOfRisks.getCompletionDate();
        this.importantTo = glossaryOfRisks.getImportantTo();
        this.isChecked = glossaryOfRisks.getChecked();

        GlossaryOfPurposes glossaryOfPurposes = glossaryOfRisks.getGlossaryOfPurposes();
        if(Objects.nonNull(glossaryOfPurposes)){
            this.purposeId = glossaryOfPurposes.getId();
        }

        OrganisationStructure organisationStructure = glossaryOfRisks.getOrganisationStructure();
        if(Objects.nonNull(organisationStructure)) {
            this.organisationId = organisationStructure.getId();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlossaryOfRisksDTO that = (GlossaryOfRisksDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(organisationId, that.organisationId) &&
            Objects.equals(purposeId, that.purposeId) &&
            Objects.equals(completionDate, that.completionDate) &&
            Objects.equals(importantTo, that.importantTo) &&
            Objects.equals(isChecked, that.isChecked);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, organisationId, purposeId, completionDate, importantTo, isChecked);
    }
}
