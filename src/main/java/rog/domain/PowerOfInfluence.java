package rog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "power_of_influence_configuration")
public class PowerOfInfluence implements Serializable{

    @Id
    private Long id;

    @NotNull
    @Column
    private Integer number;

    @NotNull
    @Column
    private Integer value;

    @NotNull
    @Column
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public PowerOfInfluence() {
    }

    public PowerOfInfluence(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowerOfInfluence that = (PowerOfInfluence) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(number, that.number) &&
            Objects.equals(value, that.value) &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name);
    }
}
