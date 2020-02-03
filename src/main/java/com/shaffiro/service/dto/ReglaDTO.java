package com.shaffiro.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Regla entity.
 */
public class ReglaDTO implements Serializable {

    private Long id;

    private String nombre;

    private String antecedente;

    private String concecuente;

    private String operador;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAntecedente() {
        return antecedente;
    }

    public void setAntecedente(String antecedente) {
        this.antecedente = antecedente;
    }

    public String getConcecuente() {
        return concecuente;
    }

    public void setConcecuente(String concecuente) {
        this.concecuente = concecuente;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReglaDTO reglaDTO = (ReglaDTO) o;
        if (reglaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reglaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReglaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", antecedente='" + getAntecedente() + "'" +
            ", concecuente='" + getConcecuente() + "'" +
            ", operador='" + getOperador() + "'" +
            "}";
    }
}
