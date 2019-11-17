package com.shaffiro.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Regla.
 */
@Entity
@Table(name = "regla")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Regla implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "logica")
    private String logica;

    @OneToMany(mappedBy = "regla")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dispositivo> dispositivos = new HashSet<>();
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

    public Regla nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogica() {
        return logica;
    }

    public Regla logica(String logica) {
        this.logica = logica;
        return this;
    }

    public void setLogica(String logica) {
        this.logica = logica;
    }

    public Set<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public Regla dispositivos(Set<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
        return this;
    }

    public Regla addDispositivo(Dispositivo dispositivo) {
        this.dispositivos.add(dispositivo);
        dispositivo.setRegla(this);
        return this;
    }

    public Regla removeDispositivo(Dispositivo dispositivo) {
        this.dispositivos.remove(dispositivo);
        dispositivo.setRegla(null);
        return this;
    }

    public void setDispositivos(Set<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
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
        Regla regla = (Regla) o;
        if (regla.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regla.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Regla{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", logica='" + getLogica() + "'" +
            "}";
    }
}
