package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Protocolentry.
 */
@Entity
@Table(name = "protocolentry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Protocolentry implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ordernumber", nullable = false)
    private String ordernumber;

    @ManyToOne
    @JsonIgnoreProperties("protocolentries")
    private Protocol protocol;

    @ManyToOne
    @JsonIgnoreProperties("protocolentries")
    private Sensor sensor;

    @ManyToOne
    @JsonIgnoreProperties("protocolentries")
    private Reagent reagent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public Protocolentry ordernumber(String ordernumber) {
        this.ordernumber = ordernumber;
        return this;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public Protocolentry protocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public Protocolentry sensor(Sensor sensor) {
        this.sensor = sensor;
        return this;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Reagent getReagent() {
        return reagent;
    }

    public Protocolentry reagent(Reagent reagent) {
        this.reagent = reagent;
        return this;
    }

    public void setReagent(Reagent reagent) {
        this.reagent = reagent;
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
        Protocolentry protocolentry = (Protocolentry) o;
        if (protocolentry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), protocolentry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Protocolentry{" +
            "id=" + getId() +
            ", ordernumber='" + getOrdernumber() + "'" +
            "}";
    }
}
