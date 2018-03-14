package rog.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "key_risk_indicator_purposes")
public class KeyRiskIndicatorPurposes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonBackReference
    private SetOfSentPurposes setOfSentPurposes;

    @ManyToOne
    @JsonBackReference
    @NotNull
    private GlossaryOfPurposes glossaryOfPurposes;

    @OneToMany(mappedBy = "keyRiskIndicatorPurposes", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<FilledKeyRiskIndicator> filledKeyRiskIndicators = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SetOfSentPurposes getSetOfSentPurposes() {
        return setOfSentPurposes;
    }

    public void setSetOfSentPurposes(SetOfSentPurposes setOfSentPurposes) {
        this.setOfSentPurposes = setOfSentPurposes;
    }

    public GlossaryOfPurposes getGlossaryOfPurposes() {
        return glossaryOfPurposes;
    }

    public void setGlossaryOfPurposes(GlossaryOfPurposes glossaryOfPurposes) {
        this.glossaryOfPurposes = glossaryOfPurposes;
    }

    public Set<FilledKeyRiskIndicator> getFilledKeyRiskIndicators() {
        return filledKeyRiskIndicators;
    }

    public void setFilledKeyRiskIndicators(Set<FilledKeyRiskIndicator> filledKeyRiskIndicators) {
        this.filledKeyRiskIndicators = filledKeyRiskIndicators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyRiskIndicatorPurposes keyRiskIndicatorPurposes = (KeyRiskIndicatorPurposes) o;
        return keyRiskIndicatorPurposes.getId() != null && getId() != null &&
            Objects.equals(getId(), keyRiskIndicatorPurposes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
