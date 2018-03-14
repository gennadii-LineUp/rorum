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
 * A CommercialRisksPurposes.
 */
@Entity
@Table(name = "commercial_risks_purposes")
public class CommercialRisksPurposes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @NotNull
    private SetOfSentPurposes setOfSentPurposes;

    @OneToMany(mappedBy = "commercialRisksPurposes", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<FilledCommercialRisks> filledCommercialRisks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SetOfSentPurposes getSetOfSentPurposes() {
        return setOfSentPurposes;
    }

    public CommercialRisksPurposes setOfSentPurposes(SetOfSentPurposes setOfSentPurposes) {
        this.setOfSentPurposes = setOfSentPurposes;
        return this;
    }

    public void setSetOfSentPurposes(SetOfSentPurposes setOfSentPurposes) {
        this.setOfSentPurposes = setOfSentPurposes;
    }

    public Set<FilledCommercialRisks> getFilledCommercialRisks() {
        return filledCommercialRisks;
    }

    public void setFilledCommercialRisks(Set<FilledCommercialRisks> filledCommercialRisks) {
        this.filledCommercialRisks = filledCommercialRisks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommercialRisksPurposes commercialRisksPurposes = (CommercialRisksPurposes) o;
        return commercialRisksPurposes.getId() != null && getId() != null &&
            Objects.equals(getId(), commercialRisksPurposes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
