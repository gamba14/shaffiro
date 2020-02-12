package com.shaffiro.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.shaffiro.domain.enumeration.TipoDispositivo;

/**
 * A Dispositivo.
 */
@Entity
@Table(name = "dispositivo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dispositivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoDispositivo tipo;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "configuracion")
    private String configuracion;

    @OneToMany(mappedBy = "dispositivo")
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Regla> reglas = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Dispositivo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoDispositivo getTipo() {
        return tipo;
    }

    public Dispositivo tipo(TipoDispositivo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoDispositivo tipo) {
        this.tipo = tipo;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Dispositivo activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getConfiguracion() {
        return configuracion;
    }

    public Dispositivo configuracion(String configuracion) {
        this.configuracion = configuracion;
        return this;
    }

    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }

    public Set<Regla> getReglas() {
        return reglas;
    }

    public Dispositivo reglas(Set<Regla> reglas) {
        this.reglas = reglas;
        return this;
    }

    public Dispositivo addRegla(Regla regla) {
        this.reglas.add(regla);
        regla.setDispositivo(this);
        return this;
    }

    public Dispositivo removeRegla(Regla regla) {
        this.reglas.remove(regla);
        regla.setDispositivo(null);
        return this;
    }

    public void setReglas(Set<Regla> reglas) {
        this.reglas = reglas;
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
        Dispositivo dispositivo = (Dispositivo) o;
        if (dispositivo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dispositivo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", activo='" + isActivo() + "'" +
            ", configuracion='" + getConfiguracion() + "'" +
            "}";
    }
}
