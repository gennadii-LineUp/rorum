package rog.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "glossary_of_control_mechanisms")
public class GlossaryOfControlMechanisms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @NotNull
    @Column(name = "possible_to_mark", nullable = false)
    private Boolean possibleToMark;

    public GlossaryOfControlMechanisms() {
    }

    public GlossaryOfControlMechanisms(Long id){
        this.id = id;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getPossibleToMark() {
        return possibleToMark;
    }

    public void setPossibleToMark(Boolean possibleToMark) {
        this.possibleToMark = possibleToMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GlossaryOfControlMechanisms glossaryOfControlMechanisms = (GlossaryOfControlMechanisms) o;
        return glossaryOfControlMechanisms.getId() != null && getId() != null &&
            Objects.equals(getId(), glossaryOfControlMechanisms.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
