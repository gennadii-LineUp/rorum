package rog.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import rog.domain.enumeration.StatusOfSending;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "set_of_sent_purposes")
public class SetOfSentPurposes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_of_sending")
    private StatusOfSending statusOfSending = StatusOfSending.CONFIRMED_PURPOSES;

    @Column(name = "is_last_version")
    private Boolean isLastVersion = Boolean.TRUE;

    @Column(name = "creation_date", updatable = false)
    private LocalDate creationDate = LocalDate.now();

    @Column
    private String notation;

    @ManyToOne
    @NotNull
    private Orders orders;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "set_of_sent_purposes_glossary_of_purposes",
        joinColumns = @JoinColumn(name="set_of_sent_purposes_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="glossary_of_purposes_id", referencedColumnName="id"))
    @NotNull
    private Set<GlossaryOfPurposes> glossaryOfPurposes = new HashSet<>();

    @OneToMany(mappedBy = "setOfSentPurposes", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<MeasureUnitsPurposes> measureUnitsPurposes = new HashSet<>();

    @OneToMany(mappedBy = "setOfSentPurposes", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<RisksPurposes> risksPurposes = new HashSet<>();

    @OneToOne(mappedBy = "setOfSentPurposes", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private CommercialRisksPurposes commercialRisksPurposes;

    @OneToMany(mappedBy = "setOfSentPurposes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Incident> incidents = new HashSet<>();

    @OneToMany(mappedBy = "setOfSentPurposes", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<KeyRiskIndicatorPurposes> keyRiskIndicatorPurposes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusOfSending getStatusOfSending() {
        return statusOfSending;
    }

    public void setStatusOfSending(StatusOfSending statusOfSending) {
        this.statusOfSending = statusOfSending;
    }

    public Boolean getIsLastVersion() {
        return isLastVersion;
    }

    public void setIsLastVersion(Boolean isLastVersion) {
        this.isLastVersion = isLastVersion;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<GlossaryOfPurposes> getGlossaryOfPurposes() {
        return glossaryOfPurposes;
    }

    public void setGlossaryOfPurposes(Set<GlossaryOfPurposes> glossaryOfPurposes) {
        this.glossaryOfPurposes = glossaryOfPurposes;
    }

    public Set<MeasureUnitsPurposes> getMeasureUnitsPurposes() {
        return measureUnitsPurposes;
    }

    public void setMeasureUnitsPurposes(Set<MeasureUnitsPurposes> measureUnitsPurposes) {
        this.measureUnitsPurposes = measureUnitsPurposes;
    }

    public Set<RisksPurposes> getRisksPurposes() {
        return risksPurposes;
    }

    public void setRisksPurposes(Set<RisksPurposes> risksPurposes) {
        this.risksPurposes = risksPurposes;
    }

    public CommercialRisksPurposes getCommercialRisksPurposes() {
        return commercialRisksPurposes;
    }

    public void setCommercialRisksPurposes(CommercialRisksPurposes commercialRisksPurposes) {
        this.commercialRisksPurposes = commercialRisksPurposes;
    }

    public Set<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(Set<Incident> incidents) {
        this.incidents = incidents;
    }

    public Set<KeyRiskIndicatorPurposes> getKeyRiskIndicatorPurposes() {
        return keyRiskIndicatorPurposes;
    }

    public void setKeyRiskIndicatorPurposes(Set<KeyRiskIndicatorPurposes> keyRiskIndicatorPurposes) {
        this.keyRiskIndicatorPurposes = keyRiskIndicatorPurposes;
    }

    public Set<FilledMeasureUnits> getFilledMeasureUnits(){
        if(measureUnitsPurposes == null){
            return Collections.emptySet();
        }
        return measureUnitsPurposes.stream()
            .flatMap(rp -> rp.getFilledMeasureUnits().stream())
            .collect(Collectors.toSet());
    }

    public Set<FilledRisks> getFilledRisks(){
        if(risksPurposes == null){
            return Collections.emptySet();
        }
        return risksPurposes.stream()
            .flatMap(rp -> rp.getFilledRisks().stream())
            .collect(Collectors.toSet());
    }

    public Set<FilledKeyRiskIndicator> getFilledKeyRiskIndicators(){
        if(keyRiskIndicatorPurposes == null){
            return Collections.emptySet();
        }
        return keyRiskIndicatorPurposes.stream()
            .flatMap(krip -> krip.getFilledKeyRiskIndicators().stream())
            .collect(Collectors.toSet());
    }

    public Set<FilledCommercialRisks> getFilledCommercialRisks(){
        if(commercialRisksPurposes == null){
            return Collections.emptySet();
        }
        return commercialRisksPurposes.getFilledCommercialRisks();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SetOfSentPurposes setOfSentPurposes = (SetOfSentPurposes) o;
        return setOfSentPurposes.getId() != null && getId() != null &&
            Objects.equals(getId(), setOfSentPurposes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SetOfSentPurposes{" +
            "id=" + id +
            ", statusOfSending=" + statusOfSending +
            ", isLastVersion=" + isLastVersion +
            ", creationDate=" + creationDate +
            ", notation='" + notation + '\'' +
            '}';
    }
}
