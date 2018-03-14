package rog.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "glossary_of_kri")
public class GlossaryOfKeyRiskIndicators implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column(name = "important_to")
    private LocalDate importantTo;

    @ManyToOne
    @JsonBackReference
    private GlossaryOfPurposes glossaryOfPurposes;

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
        GlossaryOfKeyRiskIndicators glossaryOfKeyRiskIndicators = (GlossaryOfKeyRiskIndicators) o;
        return glossaryOfKeyRiskIndicators.getId() != null && getId() != null &&
            Objects.equals(getId(), glossaryOfKeyRiskIndicators.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
