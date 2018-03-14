package rog.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import rog.domain.enumeration.PurposeAccomplishmentStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A FilledMeasureUnits.
 */
@Entity
@Table(name = "filled_measure_units")
public class FilledMeasureUnits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "base_value", updatable = false)
    private Integer baseValue;

    @Column(name = "actual_value")
    private Integer actualValue;

    @Column(name = "final_value", updatable = false)
    private Integer finalValue;

    @Column(name = "cost_of_purpose_realisation")
    private BigDecimal costOfPurposeRealisation;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose_accomplishment_status")
    private PurposeAccomplishmentStatus purposeAccomplishmentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @NotNull
    private MeasureUnitsPurposes measureUnitsPurposes;

    @ManyToOne
    @JsonBackReference
    @NotNull
    private GlossaryOfMeasureUnits glossaryOfMeasureUnits;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "is_saved", nullable = false)
    private Boolean isSaved = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBaseValue() {
        return baseValue;
    }

    public FilledMeasureUnits value(Integer value) {
        this.baseValue = value;
        return this;
    }

    public void setBaseValue(Integer baseValue) {
        this.baseValue = baseValue;
    }

    public MeasureUnitsPurposes getMeasureUnitsPurposes() {
        return measureUnitsPurposes;
    }

    public FilledMeasureUnits measureUnitsPurposes(MeasureUnitsPurposes measureUnitsPurposes) {
        this.measureUnitsPurposes = measureUnitsPurposes;
        return this;
    }

    public void setMeasureUnitsPurposes(MeasureUnitsPurposes measureUnitsPurposes) {
        this.measureUnitsPurposes = measureUnitsPurposes;
    }

    public GlossaryOfMeasureUnits getGlossaryOfMeasureUnits() {
        return glossaryOfMeasureUnits;
    }

    public FilledMeasureUnits glossaryOfMeasureUnits(GlossaryOfMeasureUnits glossaryOfMeasureUnits) {
        this.glossaryOfMeasureUnits = glossaryOfMeasureUnits;
        return this;
    }

    public void setGlossaryOfMeasureUnits(GlossaryOfMeasureUnits glossaryOfMeasureUnits) {
        this.glossaryOfMeasureUnits = glossaryOfMeasureUnits;
    }

    public Integer getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(Integer finalValue) {
        this.finalValue = finalValue;
    }

    public PurposeAccomplishmentStatus getPurposeAccomplishmentStatus() {
        return purposeAccomplishmentStatus;
    }

    public void setPurposeAccomplishmentStatus(PurposeAccomplishmentStatus purposeAccomplishmentStatus) {
        this.purposeAccomplishmentStatus = purposeAccomplishmentStatus;
    }

    public Integer getActualValue() {
        return actualValue;
    }

    public void setActualValue(Integer actualValue) {
        this.actualValue = actualValue;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public BigDecimal getCostOfPurposeRealisation() {
        return costOfPurposeRealisation;
    }

    public void setCostOfPurposeRealisation(BigDecimal costOfPurposeRealisation) {
        this.costOfPurposeRealisation = costOfPurposeRealisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilledMeasureUnits filledMeasureUnits = (FilledMeasureUnits) o;
        return filledMeasureUnits.getId() != null && getId() != null &&
            Objects.equals(getId(), filledMeasureUnits.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FilledMeasureUnits{" +
            "id=" + id +
            ", baseValue=" + baseValue +
            ", finalValue=" + finalValue +
            ", measureUnitsPurposes=" + measureUnitsPurposes +
            ", glossaryOfMeasureUnits=" + glossaryOfMeasureUnits +
            '}';
    }
}
