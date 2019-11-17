package com.shaffiro.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Regla entity.
 */
public class ReglaDTO implements Serializable {

    private Long id;

    private String nombre;

    private String logica;


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

    public String getLogica() {
        return logica;
    }

    public void setLogica(String logica) {
        this.logica = logica;
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
            ", logica='" + getLogica() + "'" +
            "}";
    }
}
