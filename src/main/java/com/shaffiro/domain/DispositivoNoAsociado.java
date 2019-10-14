package com.shaffiro.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DispositivoNoAsociado.
 */
@Entity
@Table(name = "dispositivo_no_asociado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DispositivoNoAsociado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mac")
    private String mac;

    @Column(name = "uuid")
    private String uuid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public DispositivoNoAsociado mac(String mac) {
        this.mac = mac;
        return this;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUuid() {
        return uuid;
    }

    public DispositivoNoAsociado uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
        DispositivoNoAsociado dispositivoNoAsociado = (DispositivoNoAsociado) o;
        if (dispositivoNoAsociado.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dispositivoNoAsociado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DispositivoNoAsociado{" +
            "id=" + getId() +
            ", mac='" + getMac() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
