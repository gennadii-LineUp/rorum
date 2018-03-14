package rog.service.dto;

import rog.domain.GlossaryOfKeyRiskIndicators;
import rog.domain.GlossaryOfPurposes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class GlossaryOfKeyRiskIndicatorsDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate importantTo;

    private Long purposeId;

    public GlossaryOfKeyRiskIndicatorsDTO(){
    }

    public GlossaryOfKeyRiskIndicatorsDTO(GlossaryOfKeyRiskIndicators glossaryOfKeyRiskIndicators){

        if(Objects.isNull(glossaryOfKeyRiskIndicators)){
            return;
        }

        this.id = glossaryOfKeyRiskIndicators.getId();
        this.name = glossaryOfKeyRiskIndicators.getName();
        this.importantTo = glossaryOfKeyRiskIndicators.getImportantTo();

        GlossaryOfPurposes glossaryOfPurposes = glossaryOfKeyRiskIndicators.getGlossaryOfPurposes();
        if(Objects.nonNull(glossaryOfPurposes)){
            this.purposeId = glossaryOfPurposes.getId();
        }
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

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getImportantTo() {
        return importantTo;
    }

    public void setImportantTo(LocalDate importantTo) {
        this.importantTo = importantTo;
    }

    public Long getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(Long purposeId) {
        this.purposeId = purposeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GlossaryOfKeyRiskIndicatorsDTO that = (GlossaryOfKeyRiskIndicatorsDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
