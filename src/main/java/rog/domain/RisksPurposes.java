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
 * A RisksPurposes.
 */
@Entity
@Table(name = "risks_purposes")
public class RisksPurposes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @NotNull
    private SetOfSentPurposes setOfSentPurposes;

    @OneToMany(mappedBy = "risksPurposes", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<FilledRisks> filledRisks = new HashSet<>();

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

    public RisksPurposes setOfSentPurposes(SetOfSentPurposes setOfSentPurposes) {
        this.setOfSentPurposes = setOfSentPurposes;
        return this;
    }

    public void setSetOfSentPurposes(SetOfSentPurposes setOfSentPurposes) {
        this.setOfSentPurposes = setOfSentPurposes;
    }

    public Set<FilledRisks> getFilledRisks() {
        return filledRisks;
    }

    public RisksPurposes filledRisks(Set<FilledRisks> filledRisks) {
        this.filledRisks = filledRisks;
        return this;
    }

    public RisksPurposes addFilledRisks(FilledRisks filledRisks) {
        this.filledRisks.add(filledRisks);
        filledRisks.setRisksPurposes(this);
        return this;
    }

    public RisksPurposes removeFilledRisks(FilledRisks filledRisks) {
        this.filledRisks.remove(filledRisks);
        filledRisks.setRisksPurposes(null);
        return this;
    }

    public void setFilledRisks(Set<FilledRisks> filledRisks) {
        this.filledRisks = filledRisks;
    }

    public GlossaryOfPurposes getGlossaryOfPurposes() {
        return glossaryOfPurposes;
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
        RisksPurposes risksPurposes = (RisksPurposes) o;
        return risksPurposes.getId() != null && getId() != null &&
            Objects.equals(getId(), risksPurposes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
