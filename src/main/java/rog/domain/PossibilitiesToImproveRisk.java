package rog.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PossibilitiesToImproveRisk.
 */
@Entity
@Table(name = "possibilities_to_improve_risk")
public class PossibilitiesToImproveRisk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PossibilitiesToImproveRisk name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PossibilitiesToImproveRisk() {
    }

    public PossibilitiesToImproveRisk(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PossibilitiesToImproveRisk possibilitiesToImproveRisk = (PossibilitiesToImproveRisk) o;
        return possibilitiesToImproveRisk.getId() != null && getId() != null &&
            Objects.equals(getId(), possibilitiesToImproveRisk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PossibilitiesToImproveRisk{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
