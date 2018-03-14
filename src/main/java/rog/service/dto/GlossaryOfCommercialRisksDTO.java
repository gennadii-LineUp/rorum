package rog.service.dto;

import rog.domain.GlossaryOfCommercialRisks;
import rog.domain.OrganisationStructure;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class GlossaryOfCommercialRisksDTO implements Serializable {

    private Long id;

    private String name;

    private Long organisationId;

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

    public GlossaryOfCommercialRisksDTO() {
    }

    public GlossaryOfCommercialRisksDTO(Long id, String name, Long organisationId, LocalDate completionDate,
                                        LocalDate importantTo, Boolean isChecked) {
        this.id = id;
        this.name = name;
        this.organisationId = organisationId;
        this.completionDate = completionDate;
        this.importantTo = importantTo;
        this.isChecked = isChecked;
    }

    public GlossaryOfCommercialRisksDTO(GlossaryOfCommercialRisks glossaryOfCommercialRisks){

        if(Objects.isNull(glossaryOfCommercialRisks)){
            return;
        }

        this.id = glossaryOfCommercialRisks.getId();
        this.name = glossaryOfCommercialRisks.getName();
        this.completionDate = glossaryOfCommercialRisks.getCompletionDate();
        this.importantTo = glossaryOfCommercialRisks.getImportantTo();
        this.isChecked = glossaryOfCommercialRisks.getChecked();

        OrganisationStructure organisationStructure = glossaryOfCommercialRisks.getOrganisationStructure();
        if(Objects.nonNull(organisationStructure)) {
            this.organisationId = organisationStructure.getId();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlossaryOfCommercialRisksDTO that = (GlossaryOfCommercialRisksDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(organisationId, that.organisationId) &&
            Objects.equals(completionDate, that.completionDate) &&
            Objects.equals(importantTo, that.importantTo) &&
            Objects.equals(isChecked, that.isChecked);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, organisationId, completionDate, importantTo, isChecked);
    }
}
