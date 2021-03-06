package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Protocol.
 */
@Entity
@Table(name = "protocol")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Protocol implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "protocolname", nullable = false)
    private String protocolname;

    @ManyToOne
    @JsonIgnoreProperties("protocols")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProtocolname() {
        return protocolname;
    }

    public Protocol protocolname(String protocolname) {
        this.protocolname = protocolname;
        return this;
    }

    public void setProtocolname(String protocolname) {
        this.protocolname = protocolname;
    }

    public User getUser() {
        return user;
    }

    public Protocol user(User user) {
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
        Protocol protocol = (Protocol) o;
        if (protocol.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), protocol.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Protocol{" +
            "id=" + getId() +
            ", protocolname='" + getProtocolname() + "'" +
            "}";
    }
}
