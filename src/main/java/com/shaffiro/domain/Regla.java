package com.shaffiro.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.shaffiro.domain.enumeration.Unidad;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad")
    private Unidad unidad;

    @Column(name = "valor")
    private String valor;

    @Column(name = "operador")
    private String operador;

    @ManyToOne
    @JsonIgnoreProperties("reglas")
    private Dispositivo dispositivo;

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

    public Unidad getUnidad() {
        return unidad;
    }

    public Regla unidad(Unidad unidad) {
        this.unidad = unidad;
        return this;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public String getValor() {
        return valor;
    }

    public Regla valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getOperador() {
        return operador;
    }

    public Regla operador(String operador) {
        this.operador = operador;
        return this;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public Regla dispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
        return this;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
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
            ", unidad='" + getUnidad() + "'" +
            ", valor='" + getValor() + "'" +
            ", operador='" + getOperador() + "'" +
            "}";
    }
}
