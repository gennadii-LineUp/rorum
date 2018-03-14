package rog.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "financing_year")
    private Integer financingYear;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "first_reporting_date")
    private LocalDate firstReportingDate;

    @Column(name = "second_reporting_date")
    private LocalDate secondReportingDate;

    @Column(name = "third_reporting_date")
    private LocalDate thirdReportingDate;

    @Column(name = "final_date")
    private LocalDate finalDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Orders name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFinancingYear() {
        return financingYear;
    }

    public Orders financingYear(Integer financingYear) {
        this.financingYear = financingYear;
        return this;
    }

    public void setFinancingYear(Integer financingYear) {
        this.financingYear = financingYear;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Orders startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFirstReportingDate() {
        return firstReportingDate;
    }

    public Orders firstReportingDate(LocalDate firstReportingDate) {
        this.firstReportingDate = firstReportingDate;
        return this;
    }

    public void setFirstReportingDate(LocalDate firstReportingDate) {
        this.firstReportingDate = firstReportingDate;
    }

    public LocalDate getSecondReportingDate() {
        return secondReportingDate;
    }

    public Orders secondReportingDate(LocalDate secondReportingDate) {
        this.secondReportingDate = secondReportingDate;
        return this;
    }

    public void setSecondReportingDate(LocalDate secondReportingDate) {
        this.secondReportingDate = secondReportingDate;
    }

    public LocalDate getThirdReportingDate() {
        return thirdReportingDate;
    }

    public Orders thirdReportingDate(LocalDate thirdReportingDate) {
        this.thirdReportingDate = thirdReportingDate;
        return this;
    }

    public void setThirdReportingDate(LocalDate thirdReportingDate) {
        this.thirdReportingDate = thirdReportingDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public Orders finalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
        return this;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orders orders = (Orders) o;
        return orders.getId() != null && getId() != null &&
            Objects.equals(getId(), orders.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", financingYear='" + getFinancingYear() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", firstReportingDate='" + getFirstReportingDate() + "'" +
            ", secondReportingDate='" + getSecondReportingDate() + "'" +
            ", thirdReportingDate='" + getThirdReportingDate() + "'" +
            ", finalDate='" + getFinalDate() + "'" +
            "}";
    }
}
