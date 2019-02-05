package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CalculatedEntry.
 */
@Entity
@Table(name = "calculated_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CalculatedEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "sequencenumber", nullable = false)
    private Integer sequencenumber;

    @NotNull
    @Column(name = "aa", nullable = false)
    private String aa;

    @NotNull
    @Column(name = "sc", nullable = false)
    private Integer sc;

    @NotNull
    @Column(name = "dc", nullable = false)
    private Integer dc;

    @NotNull
    @Column(name = "scale", nullable = false)
    private Integer scale;

    @Column(name = "mwwithprotection")
    private Double mwwithprotection;

    @Column(name = "mwwithoutprotection")
    private Double mwwithoutprotection;

    @Column(name = "eachaaweight")
    private Double eachaaweight;

    @Column(name = "difficulty")
    private Double difficulty;

    @Column(name = "currentresinweight")
    private Double currentresinweight;

    @Column(name = "protocolname")
    private String protocolname;

    @ManyToOne
    @JsonIgnoreProperties("calculatedEntries")
    private Calculated calculated;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSequencenumber() {
        return sequencenumber;
    }

    public CalculatedEntry sequencenumber(Integer sequencenumber) {
        this.sequencenumber = sequencenumber;
        return this;
    }

    public void setSequencenumber(Integer sequencenumber) {
        this.sequencenumber = sequencenumber;
    }

    public String getAa() {
        return aa;
    }

    public CalculatedEntry aa(String aa) {
        this.aa = aa;
        return this;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    public Integer getSc() {
        return sc;
    }

    public CalculatedEntry sc(Integer sc) {
        this.sc = sc;
        return this;
    }

    public void setSc(Integer sc) {
        this.sc = sc;
    }

    public Integer getDc() {
        return dc;
    }

    public CalculatedEntry dc(Integer dc) {
        this.dc = dc;
        return this;
    }

    public void setDc(Integer dc) {
        this.dc = dc;
    }

    public Integer getScale() {
        return scale;
    }

    public CalculatedEntry scale(Integer scale) {
        this.scale = scale;
        return this;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Double getMwwithprotection() {
        return mwwithprotection;
    }

    public CalculatedEntry mwwithprotection(Double mwwithprotection) {
        this.mwwithprotection = mwwithprotection;
        return this;
    }

    public void setMwwithprotection(Double mwwithprotection) {
        this.mwwithprotection = mwwithprotection;
    }

    public Double getMwwithoutprotection() {
        return mwwithoutprotection;
    }

    public CalculatedEntry mwwithoutprotection(Double mwwithoutprotection) {
        this.mwwithoutprotection = mwwithoutprotection;
        return this;
    }

    public void setMwwithoutprotection(Double mwwithoutprotection) {
        this.mwwithoutprotection = mwwithoutprotection;
    }

    public Double getEachaaweight() {
        return eachaaweight;
    }

    public CalculatedEntry eachaaweight(Double eachaaweight) {
        this.eachaaweight = eachaaweight;
        return this;
    }

    public void setEachaaweight(Double eachaaweight) {
        this.eachaaweight = eachaaweight;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public CalculatedEntry difficulty(Double difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }

    public Double getCurrentresinweight() {
        return currentresinweight;
    }

    public CalculatedEntry currentresinweight(Double currentresinweight) {
        this.currentresinweight = currentresinweight;
        return this;
    }

    public void setCurrentresinweight(Double currentresinweight) {
        this.currentresinweight = currentresinweight;
    }

    public String getProtocolname() {
        return protocolname;
    }

    public CalculatedEntry protocolname(String protocolname) {
        this.protocolname = protocolname;
        return this;
    }

    public void setProtocolname(String protocolname) {
        this.protocolname = protocolname;
    }

    public Calculated getCalculated() {
        return calculated;
    }

    public CalculatedEntry calculated(Calculated calculated) {
        this.calculated = calculated;
        return this;
    }

    public void setCalculated(Calculated calculated) {
        this.calculated = calculated;
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
        CalculatedEntry calculatedEntry = (CalculatedEntry) o;
        if (calculatedEntry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calculatedEntry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CalculatedEntry{" +
            "id=" + getId() +
            ", sequencenumber=" + getSequencenumber() +
            ", aa='" + getAa() + "'" +
            ", sc=" + getSc() +
            ", dc=" + getDc() +
            ", scale=" + getScale() +
            ", mwwithprotection=" + getMwwithprotection() +
            ", mwwithoutprotection=" + getMwwithoutprotection() +
            ", eachaaweight=" + getEachaaweight() +
            ", difficulty=" + getDifficulty() +
            ", currentresinweight=" + getCurrentresinweight() +
            ", protocolname='" + getProtocolname() + "'" +
            "}";
    }
}
