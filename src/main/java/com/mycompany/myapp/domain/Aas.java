package com.mycompany.myapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Aas.
 */
@Entity
@Table(name = "aas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Aas implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "aaname", nullable = false)
    private String aaname;

    @NotNull
    @Column(name = "threeletter", nullable = false)
    private String threeletter;

    @NotNull
    @Column(name = "mwwithprotection", nullable = false)
    private Double mwwithprotection;

    @NotNull
    @Column(name = "mwwithoutprotection", nullable = false)
    private Double mwwithoutprotection;

    @NotNull
    @Column(name = "pi", nullable = false)
    private Double pi;

    @NotNull
    @Column(name = "unitprice", nullable = false)
    private Double unitprice;

    @NotNull
    @Column(name = "difficulty", nullable = false)
    private Double difficulty;

    @NotNull
    @Column(name = "numc", nullable = false)
    private Integer numc;

    @NotNull
    @Column(name = "numh", nullable = false)
    private Integer numh;

    @NotNull
    @Column(name = "numn", nullable = false)
    private Integer numn;

    @NotNull
    @Column(name = "numo", nullable = false)
    private Integer numo;

    @NotNull
    @Column(name = "nums", nullable = false)
    private Integer nums;

    @NotNull
    @Column(name = "solubility", nullable = false)
    private String solubility;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAaname() {
        return aaname;
    }

    public Aas aaname(String aaname) {
        this.aaname = aaname;
        return this;
    }

    public void setAaname(String aaname) {
        this.aaname = aaname;
    }

    public String getThreeletter() {
        return threeletter;
    }

    public Aas threeletter(String threeletter) {
        this.threeletter = threeletter;
        return this;
    }

    public void setThreeletter(String threeletter) {
        this.threeletter = threeletter;
    }

    public Double getMwwithprotection() {
        return mwwithprotection;
    }

    public Aas mwwithprotection(Double mwwithprotection) {
        this.mwwithprotection = mwwithprotection;
        return this;
    }

    public void setMwwithprotection(Double mwwithprotection) {
        this.mwwithprotection = mwwithprotection;
    }

    public Double getMwwithoutprotection() {
        return mwwithoutprotection;
    }

    public Aas mwwithoutprotection(Double mwwithoutprotection) {
        this.mwwithoutprotection = mwwithoutprotection;
        return this;
    }

    public void setMwwithoutprotection(Double mwwithoutprotection) {
        this.mwwithoutprotection = mwwithoutprotection;
    }

    public Double getPi() {
        return pi;
    }

    public Aas pi(Double pi) {
        this.pi = pi;
        return this;
    }

    public void setPi(Double pi) {
        this.pi = pi;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public Aas unitprice(Double unitprice) {
        this.unitprice = unitprice;
        return this;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public Aas difficulty(Double difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getNumc() {
        return numc;
    }

    public Aas numc(Integer numc) {
        this.numc = numc;
        return this;
    }

    public void setNumc(Integer numc) {
        this.numc = numc;
    }

    public Integer getNumh() {
        return numh;
    }

    public Aas numh(Integer numh) {
        this.numh = numh;
        return this;
    }

    public void setNumh(Integer numh) {
        this.numh = numh;
    }

    public Integer getNumn() {
        return numn;
    }

    public Aas numn(Integer numn) {
        this.numn = numn;
        return this;
    }

    public void setNumn(Integer numn) {
        this.numn = numn;
    }

    public Integer getNumo() {
        return numo;
    }

    public Aas numo(Integer numo) {
        this.numo = numo;
        return this;
    }

    public void setNumo(Integer numo) {
        this.numo = numo;
    }

    public Integer getNums() {
        return nums;
    }

    public Aas nums(Integer nums) {
        this.nums = nums;
        return this;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public String getSolubility() {
        return solubility;
    }

    public Aas solubility(String solubility) {
        this.solubility = solubility;
        return this;
    }

    public void setSolubility(String solubility) {
        this.solubility = solubility;
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
        Aas aas = (Aas) o;
        if (aas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Aas{" +
            "id=" + getId() +
            ", aaname='" + getAaname() + "'" +
            ", threeletter='" + getThreeletter() + "'" +
            ", mwwithprotection=" + getMwwithprotection() +
            ", mwwithoutprotection=" + getMwwithoutprotection() +
            ", pi=" + getPi() +
            ", unitprice=" + getUnitprice() +
            ", difficulty=" + getDifficulty() +
            ", numc=" + getNumc() +
            ", numh=" + getNumh() +
            ", numn=" + getNumn() +
            ", numo=" + getNumo() +
            ", nums=" + getNums() +
            ", solubility='" + getSolubility() + "'" +
            "}";
    }
}
