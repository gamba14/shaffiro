package com.shaffiro.service.dto;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.shaffiro.domain.enumeration.TipoDispositivo;

/**
 * A DTO for the Dispositivo entity.
 */
public class DispositivoDTO implements Serializable {

    private Long id;

    private String nombre;

    private TipoDispositivo tipo;

    private Boolean activo;

    private String configuracion;

    private Set<ReglaDTO> reglas;


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

    public TipoDispositivo getTipo() {
        return tipo;
    }

    public void setTipo(TipoDispositivo tipo) {
        this.tipo = tipo;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }

    public Set<ReglaDTO> getReglas() {
        return reglas;
    }

    public void setReglas(Set<ReglaDTO> reglas) {
        this.reglas = reglas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DispositivoDTO dispositivoDTO = (DispositivoDTO) o;
        if (dispositivoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dispositivoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DispositivoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", activo='" + isActivo() + "'" +
            ", configuracion='" + getConfiguracion() + "'" +
            "}";
    }
}
