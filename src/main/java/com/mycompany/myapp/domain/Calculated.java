package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Calculated.
 */
@Entity
@Table(name = "calculated")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Calculated implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 65535)
    @Column(name = "jhi_sequence", length = 65535, nullable = false)
    private String sequence;

    @NotNull
    @Column(name = "sc", nullable = false)
    private Integer sc;

    @NotNull
    @Column(name = "dc", nullable = false)
    private Integer dc;

    @NotNull
    @Column(name = "scale", nullable = false)
    private Integer scale;

    @Column(name = "pi")
    private Double pi;

    @Column(name = "netcharge")
    private Double netcharge;

    @Column(name = "solubility")
    private String solubility;

    @Column(name = "numc")
    private Integer numc;

    @Column(name = "numh")
    private Integer numh;

    @Column(name = "numn")
    private Integer numn;

    @Column(name = "numo")
    private Integer numo;

    @Column(name = "nums")
    private Integer nums;

    @Column(name = "startresin")
    private Double startresin;

    @Column(name = "costresin")
    private Double costresin;

    @Column(name = "weightgain")
    private Double weightgain;

    @Column(name = "totalweight")
    private Double totalweight;

    @Column(name = "costaa")
    private Double costaa;

    @Column(name = "sumeachaaweight")
    private Double sumeachaaweight;

    @Column(name = "resinunitprice")
    private Double resinunitprice;

    @Column(name = "substitude")
    private Double substitude;

    @Column(name = "bound")
    private Integer bound;

    @Column(name = "costwaste")
    private Double costwaste;

    @Column(name = "protocolname")
    private String protocolname;

    @ManyToOne
    @JsonIgnoreProperties("calculateds")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public Calculated sequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Integer getSc() {
        return sc;
    }

    public Calculated sc(Integer sc) {
        this.sc = sc;
        return this;
    }

    public void setSc(Integer sc) {
        this.sc = sc;
    }

    public Integer getDc() {
        return dc;
    }

    public Calculated dc(Integer dc) {
        this.dc = dc;
        return this;
    }

    public void setDc(Integer dc) {
        this.dc = dc;
    }

    public Integer getScale() {
        return scale;
    }

    public Calculated scale(Integer scale) {
        this.scale = scale;
        return this;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Double getPi() {
        return pi;
    }

    public Calculated pi(Double pi) {
        this.pi = pi;
        return this;
    }

    public void setPi(Double pi) {
        this.pi = pi;
    }

    public Double getNetcharge() {
        return netcharge;
    }

    public Calculated netcharge(Double netcharge) {
        this.netcharge = netcharge;
        return this;
    }

    public void setNetcharge(Double netcharge) {
        this.netcharge = netcharge;
    }

    public String getSolubility() {
        return solubility;
    }

    public Calculated solubility(String solubility) {
        this.solubility = solubility;
        return this;
    }

    public void setSolubility(String solubility) {
        this.solubility = solubility;
    }

    public Integer getNumc() {
        return numc;
    }

    public Calculated numc(Integer numc) {
        this.numc = numc;
        return this;
    }

    public void setNumc(Integer numc) {
        this.numc = numc;
    }

    public Integer getNumh() {
        return numh;
    }

    public Calculated numh(Integer numh) {
        this.numh = numh;
        return this;
    }

    public void setNumh(Integer numh) {
        this.numh = numh;
    }

    public Integer getNumn() {
        return numn;
    }

    public Calculated numn(Integer numn) {
        this.numn = numn;
        return this;
    }

    public void setNumn(Integer numn) {
        this.numn = numn;
    }

    public Integer getNumo() {
        return numo;
    }

    public Calculated numo(Integer numo) {
        this.numo = numo;
        return this;
    }

    public void setNumo(Integer numo) {
        this.numo = numo;
    }

    public Integer getNums() {
        return nums;
    }

    public Calculated nums(Integer nums) {
        this.nums = nums;
        return this;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Double getStartresin() {
        return startresin;
    }

    public Calculated startresin(Double startresin) {
        this.startresin = startresin;
        return this;
    }

    public void setStartresin(Double startresin) {
        this.startresin = startresin;
    }

    public Double getCostresin() {
        return costresin;
    }

    public Calculated costresin(Double costresin) {
        this.costresin = costresin;
        return this;
    }

    public void setCostresin(Double costresin) {
        this.costresin = costresin;
    }

    public Double getWeightgain() {
        return weightgain;
    }

    public Calculated weightgain(Double weightgain) {
        this.weightgain = weightgain;
        return this;
    }

    public void setWeightgain(Double weightgain) {
        this.weightgain = weightgain;
    }

    public Double getTotalweight() {
        return totalweight;
    }

    public Calculated totalweight(Double totalweight) {
        this.totalweight = totalweight;
        return this;
    }

    public void setTotalweight(Double totalweight) {
        this.totalweight = totalweight;
    }

    public Double getCostaa() {
        return costaa;
    }

    public Calculated costaa(Double costaa) {
        this.costaa = costaa;
        return this;
    }

    public void setCostaa(Double costaa) {
        this.costaa = costaa;
    }

    public Double getSumeachaaweight() {
        return sumeachaaweight;
    }

    public Calculated sumeachaaweight(Double sumeachaaweight) {
        this.sumeachaaweight = sumeachaaweight;
        return this;
    }

    public void setSumeachaaweight(Double sumeachaaweight) {
        this.sumeachaaweight = sumeachaaweight;
    }

    public Double getResinunitprice() {
        return resinunitprice;
    }

    public Calculated resinunitprice(Double resinunitprice) {
        this.resinunitprice = resinunitprice;
        return this;
    }

    public void setResinunitprice(Double resinunitprice) {
        this.resinunitprice = resinunitprice;
    }

    public Double getSubstitude() {
        return substitude;
    }

    public Calculated substitude(Double substitude) {
        this.substitude = substitude;
        return this;
    }

    public void setSubstitude(Double substitude) {
        this.substitude = substitude;
    }

    public Integer getBound() {
        return bound;
    }

    public Calculated bound(Integer bound) {
        this.bound = bound;
        return this;
    }

    public void setBound(Integer bound) {
        this.bound = bound;
    }

    public Double getCostwaste() {
        return costwaste;
    }

    public Calculated costwaste(Double costwaste) {
        this.costwaste = costwaste;
        return this;
    }

    public void setCostwaste(Double costwaste) {
        this.costwaste = costwaste;
    }

    public String getProtocolname() {
        return protocolname;
    }

    public Calculated protocolname(String protocolname) {
        this.protocolname = protocolname;
        return this;
    }

    public void setProtocolname(String protocolname) {
        this.protocolname = protocolname;
    }

    public User getUser() {
        return user;
    }

    public Calculated user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Calculated calculated = (Calculated) o;
        if (calculated.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calculated.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Calculated{" +
            "id=" + getId() +
            ", sequence='" + getSequence() + "'" +
            ", sc=" + getSc() +
            ", dc=" + getDc() +
            ", scale=" + getScale() +
            ", pi=" + getPi() +
            ", netcharge=" + getNetcharge() +
            ", solubility='" + getSolubility() + "'" +
            ", numc=" + getNumc() +
            ", numh=" + getNumh() +
            ", numn=" + getNumn() +
            ", numo=" + getNumo() +
            ", nums=" + getNums() +
            ", startresin=" + getStartresin() +
            ", costresin=" + getCostresin() +
            ", weightgain=" + getWeightgain() +
            ", totalweight=" + getTotalweight() +
            ", costaa=" + getCostaa() +
            ", sumeachaaweight=" + getSumeachaaweight() +
            ", resinunitprice=" + getResinunitprice() +
            ", substitude=" + getSubstitude() +
            ", bound=" + getBound() +
            ", costwaste=" + getCostwaste() +
            ", protocolname='" + getProtocolname() + "'" +
            "}";
    }
}
