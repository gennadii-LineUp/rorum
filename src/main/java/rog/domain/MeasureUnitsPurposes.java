package rog.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A MeasureUnitsPurposes.
 */
@Entity
@Table(name = "measure_units_purposes")
public class MeasureUnitsPurposes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @NotNull
    private SetOfSentPurposes setOfSentPurposes;

    @OneToMany(mappedBy = "measureUnitsPurposes", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<FilledMeasureUnits> filledMeasureUnits = new HashSet<>();

    @ManyToOne
    @JsonBackReference
    @NotNull
    private GlossaryOfPurposes glossaryOfPurposes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SetOfSentPurposes getSetOfSentPurposes() {
        return setOfSentPurposes;
    }

    public MeasureUnitsPurposes setOfSentPurposes(SetOfSentPurposes setOfSentPurposes) {
        this.setOfSentPurposes = setOfSentPurposes;
        return this;
    }

    public void setSetOfSentPurposes(SetOfSentPurposes setOfSentPurposes) {
        this.setOfSentPurposes = setOfSentPurposes;
    }

    public Set<FilledMeasureUnits> getFilledMeasureUnits() {
        return filledMeasureUnits;
    }

    public MeasureUnitsPurposes filledMeasureUnits(Set<FilledMeasureUnits> filledMeasureUnits) {
        this.filledMeasureUnits = filledMeasureUnits;
        return this;
    }

    public MeasureUnitsPurposes addFilledMeasureUnits(FilledMeasureUnits filledMeasureUnits) {
        this.filledMeasureUnits.add(filledMeasureUnits);
        filledMeasureUnits.setMeasureUnitsPurposes(this);
        return this;
    }

    public MeasureUnitsPurposes removeFilledMeasureUnits(FilledMeasureUnits filledMeasureUnits) {
        this.filledMeasureUnits.remove(filledMeasureUnits);
        filledMeasureUnits.setMeasureUnitsPurposes(null);
        return this;
    }

    public void setFilledMeasureUnits(Set<FilledMeasureUnits> filledMeasureUnits) {
        this.filledMeasureUnits = filledMeasureUnits;
    }

    public GlossaryOfPurposes getGlossaryOfPurposes() {
        return glossaryOfPurposes;
    }

    public MeasureUnitsPurposes glossaryOfPurposes(GlossaryOfPurposes glossaryOfPurposes) {
        this.glossaryOfPurposes = glossaryOfPurposes;
        return this;
    }

    public void setGlossaryOfPurposes(GlossaryOfPurposes glossaryOfPurposes) {
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
        MeasureUnitsPurposes measureUnitsPurposes = (MeasureUnitsPurposes) o;
        return measureUnitsPurposes.getId() != null && getId() != null &&
            Objects.equals(getId(), measureUnitsPurposes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
