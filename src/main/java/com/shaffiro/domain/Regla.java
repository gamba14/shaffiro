package com.shaffiro.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
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

    @Column(name = "antecedente")
    private String antecedente;

    @Column(name = "concecuente")
    private String concecuente;

    @Column(name = "operador")
    private String operador;

    @ManyToOne
    @JsonIgnoreProperties("reglas")
    private Dispositivo dispositivoAsociado;

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

    public String getAntecedente() {
        return antecedente;
    }

    public Regla antecedente(String antecedente) {
        this.antecedente = antecedente;
        return this;
    }

    public void setAntecedente(String antecedente) {
        this.antecedente = antecedente;
    }

    public String getConcecuente() {
        return concecuente;
    }

    public Regla concecuente(String concecuente) {
        this.concecuente = concecuente;
        return this;
    }

    public void setConcecuente(String concecuente) {
        this.concecuente = concecuente;
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

    public Dispositivo getDispositivoAsociado() {
        return dispositivoAsociado;
    }

    public Regla dispositivoAsociado(Dispositivo dispositivo) {
        this.dispositivoAsociado = dispositivo;
        return this;
    }

    public void setDispositivoAsociado(Dispositivo dispositivo) {
        this.dispositivoAsociado = dispositivo;
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
            ", antecedente='" + getAntecedente() + "'" +
            ", concecuente='" + getConcecuente() + "'" +
            ", operador='" + getOperador() + "'" +
            "}";
    }
}
