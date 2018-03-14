package rog.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import rog.domain.enumeration.UnitsOfMeasurement;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "filled_key_risk_indicator")
public class FilledKeyRiskIndicator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Min(value = -100000)
    @Max(value = 100000)
    @Column(name = "min_cautionary_step")
    private Integer minCautionaryStep;

    @Min(value = -100000)
    @Max(value = 100000)
    @Column(name = "first_cautionary_step")
    private Integer firstCautionaryStep;

    @Min(value = -100000)
    @Max(value = 100000)
    @Column(name = "second_cautionary_step")
    private Integer secondCautionaryStep;

    @Min(value = -100000)
    @Max(value = 100000)
    @Column(name = "third_cautionary_step")
    private Integer thirdCautionaryStep;

    @Min(value = -100000)
    @Max(value = 100000)
    @Column(name = "fourth_cautionary_step")
    private Integer fourthCautionaryStep;

    @Min(value = -100000)
    @Max(value = 100000)
    @Column(name = "max_cautionary_step")
    private Integer maxCautionaryStep;

    @Column(name = "kri_value")
    private Integer kriValue;

    @NotNull
    @Column(name = "does_user_realize")
    private Boolean doesUserRealize = Boolean.FALSE;

    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column
    private UnitsOfMeasurement jm;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonBackReference
    private KeyRiskIndicatorPurposes keyRiskIndicatorPurposes;

    @ManyToOne
    @JsonBackReference
    @NotNull
    private GlossaryOfKeyRiskIndicators glossaryOfKeyRiskIndicators;

    @Column
    @NotNull
    private Boolean saved = Boolean.FALSE;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinCautionaryStep() {
        return minCautionaryStep;
    }

    public void setMinCautionaryStep(Integer minCautionaryStep) {
        this.minCautionaryStep = minCautionaryStep;
    }

    public Integer getFirstCautionaryStep() {
        return firstCautionaryStep;
    }

    public void setFirstCautionaryStep(Integer firstCautionaryStep) {
        this.firstCautionaryStep = firstCautionaryStep;
    }

    public Integer getSecondCautionaryStep() {
        return secondCautionaryStep;
    }

    public void setSecondCautionaryStep(Integer secondCautionaryStep) {
        this.secondCautionaryStep = secondCautionaryStep;
    }

    public Integer getThirdCautionaryStep() {
        return thirdCautionaryStep;
    }

    public void setThirdCautionaryStep(Integer thirdCautionaryStep) {
        this.thirdCautionaryStep = thirdCautionaryStep;
    }

    public Integer getFourthCautionaryStep() {
        return fourthCautionaryStep;
    }

    public void setFourthCautionaryStep(Integer fourthCautionaryStep) {
        this.fourthCautionaryStep = fourthCautionaryStep;
    }

    public Integer getMaxCautionaryStep() {
        return maxCautionaryStep;
    }

    public void setMaxCautionaryStep(Integer maxCautionaryStep) {
        this.maxCautionaryStep = maxCautionaryStep;
    }

    public Integer getKriValue() {
        return kriValue;
    }

    public void setKriValue(Integer kriValue) {
        this.kriValue = kriValue;
    }

    public Boolean getDoesUserRealize() {
        return doesUserRealize;
    }

    public void setDoesUserRealize(Boolean doesUserRealize) {
        this.doesUserRealize = doesUserRealize;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public UnitsOfMeasurement getJm() {
        return jm;
    }

    public void setJm(UnitsOfMeasurement jm) {
        this.jm = jm;
    }

    public KeyRiskIndicatorPurposes getKeyRiskIndicatorPurposes() {
        return keyRiskIndicatorPurposes;
    }

    public void setKeyRiskIndicatorPurposes(KeyRiskIndicatorPurposes keyRiskIndicatorPurposes) {
        this.keyRiskIndicatorPurposes = keyRiskIndicatorPurposes;
    }

    public GlossaryOfKeyRiskIndicators getGlossaryOfKeyRiskIndicators() {
        return glossaryOfKeyRiskIndicators;
    }

    public void setGlossaryOfKeyRiskIndicators(GlossaryOfKeyRiskIndicators glossaryOfKeyRiskIndicators) {
        this.glossaryOfKeyRiskIndicators = glossaryOfKeyRiskIndicators;
    }

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilledKeyRiskIndicator filledKeyRiskIndicator = (FilledKeyRiskIndicator) o;
        return filledKeyRiskIndicator.getId() != null && getId() != null &&
            Objects.equals(getId(), filledKeyRiskIndicator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
