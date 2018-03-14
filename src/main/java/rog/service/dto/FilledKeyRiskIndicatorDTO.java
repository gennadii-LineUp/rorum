package rog.service.dto;

import rog.domain.FilledKeyRiskIndicator;
import rog.domain.GlossaryOfKeyRiskIndicators;
import rog.domain.KeyRiskIndicatorPurposes;
import rog.domain.enumeration.UnitsOfMeasurement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FilledKeyRiskIndicatorDTO implements Serializable{

    private Long id;

    private Integer minCautionaryStep;

    private Integer firstCautionaryStep;

    private Integer secondCautionaryStep;

    private Integer thirdCautionaryStep;

    private Integer fourthCautionaryStep;

    private Integer maxCautionaryStep;

    private Integer kriValue;

    private Boolean doesUserRealize = Boolean.FALSE;

    private UnitsOfMeasurement jm;

    private Long keyRiskIndicatorPurposesId;

    private GlossaryOfKeyRiskIndicatorsDTO glossaryOfKeyRiskIndicatorsDTO;

    private Boolean isSaved;

    private LocalDateTime creationDate;

    private Map<String, String> errors = new HashMap<>();

    public FilledKeyRiskIndicatorDTO(){
    }

    public FilledKeyRiskIndicatorDTO(FilledKeyRiskIndicator filledKeyRiskIndicator){
        if(Objects.isNull(filledKeyRiskIndicator)){
            return;
        }

        this.id = filledKeyRiskIndicator.getId();
        this.minCautionaryStep = filledKeyRiskIndicator.getMinCautionaryStep();
        this.firstCautionaryStep = filledKeyRiskIndicator.getFirstCautionaryStep();
        this.secondCautionaryStep = filledKeyRiskIndicator.getSecondCautionaryStep();
        this.thirdCautionaryStep = filledKeyRiskIndicator.getThirdCautionaryStep();
        this.fourthCautionaryStep = filledKeyRiskIndicator.getFourthCautionaryStep();
        this.maxCautionaryStep = filledKeyRiskIndicator.getMaxCautionaryStep();
        this.kriValue = filledKeyRiskIndicator.getKriValue();
        this.doesUserRealize = filledKeyRiskIndicator.getDoesUserRealize();
        this.isSaved = filledKeyRiskIndicator.getSaved();
        this.jm = filledKeyRiskIndicator.getJm();
        this.creationDate = filledKeyRiskIndicator.getCreationDate();

        KeyRiskIndicatorPurposes keyRiskIndicatorPurposes = filledKeyRiskIndicator.getKeyRiskIndicatorPurposes();
        if(Objects.nonNull(keyRiskIndicatorPurposes)){
            this.keyRiskIndicatorPurposesId = keyRiskIndicatorPurposes.getId();
        }

        GlossaryOfKeyRiskIndicators glossaryOfKeyRiskIndicators = filledKeyRiskIndicator.getGlossaryOfKeyRiskIndicators();
        this.glossaryOfKeyRiskIndicatorsDTO = new GlossaryOfKeyRiskIndicatorsDTO(glossaryOfKeyRiskIndicators);
    }

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

    public UnitsOfMeasurement getJm() {
        return jm;
    }

    public void setJm(UnitsOfMeasurement jm) {
        this.jm = jm;
    }

    public Long getKeyRiskIndicatorPurposesId() {
        return keyRiskIndicatorPurposesId;
    }

    public void setKeyRiskIndicatorPurposesId(Long keyRiskIndicatorPurposesId) {
        this.keyRiskIndicatorPurposesId = keyRiskIndicatorPurposesId;
    }

    public GlossaryOfKeyRiskIndicatorsDTO getGlossaryOfKeyRiskIndicatorsDTO() {
        return glossaryOfKeyRiskIndicatorsDTO;
    }

    public void setGlossaryOfKeyRiskIndicatorsDTO(GlossaryOfKeyRiskIndicatorsDTO glossaryOfKeyRiskIndicatorsDTO) {
        this.glossaryOfKeyRiskIndicatorsDTO = glossaryOfKeyRiskIndicatorsDTO;
    }

    public Boolean getSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FilledKeyRiskIndicatorDTO that = (FilledKeyRiskIndicatorDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
