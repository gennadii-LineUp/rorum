package rog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "percentages_of_calculated_values")
public class PercentagesOfCalculatedValues {

    @Id
    private Integer id;

    @Column
    private Integer min;

    @Column
    private Integer max;

    @Column
    private String name;

    @Column
    private String color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentagesOfCalculatedValues that = (PercentagesOfCalculatedValues) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(min, that.min) &&
            Objects.equals(max, that.max) &&
            Objects.equals(name, that.name) &&
            Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, min, max, name, color);
    }
}
