package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Reagent.
 */
@Entity
@Table(name = "reagent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reagent implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "reagentname", nullable = false)
    private String reagentname;

    @NotNull
    @Column(name = "unitprice", nullable = false)
    private Double unitprice;

    @NotNull
    @Column(name = "wasterunitprice", nullable = false)
    private Double wasterunitprice;

    @ManyToOne
    @JsonIgnoreProperties("reagents")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReagentname() {
        return reagentname;
    }

    public Reagent reagentname(String reagentname) {
        this.reagentname = reagentname;
        return this;
    }

    public void setReagentname(String reagentname) {
        this.reagentname = reagentname;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public Reagent unitprice(Double unitprice) {
        this.unitprice = unitprice;
        return this;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public Double getWasterunitprice() {
        return wasterunitprice;
    }

    public Reagent wasterunitprice(Double wasterunitprice) {
        this.wasterunitprice = wasterunitprice;
        return this;
    }

    public void setWasterunitprice(Double wasterunitprice) {
        this.wasterunitprice = wasterunitprice;
    }

    public User getUser() {
        return user;
    }

    public Reagent user(User user) {
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
        Reagent reagent = (Reagent) o;
        if (reagent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reagent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reagent{" +
            "id=" + getId() +
            ", reagentname='" + getReagentname() + "'" +
            ", unitprice=" + getUnitprice() +
            ", wasterunitprice=" + getWasterunitprice() +
            "}";
    }
}
