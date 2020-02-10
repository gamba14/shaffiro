package com.shaffiro.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.shaffiro.domain.enumeration.Unidad;

/**
 * A DTO for the Regla entity.
 */
public class ReglaDTO implements Serializable {

    private Long id;

    private String nombre;

    private Unidad unidad;

    private String valor;

    private String operador;


    private Long dispositivoAsociadoId;

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

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Long getDispositivoAsociadoId() {
        return dispositivoAsociadoId;
    }

    public void setDispositivoAsociadoId(Long dispositivoId) {
        this.dispositivoAsociadoId = dispositivoId;
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
            ", unidad='" + getUnidad() + "'" +
            ", valor='" + getValor() + "'" +
            ", operador='" + getOperador() + "'" +
            ", dispositivoAsociado=" + getDispositivoAsociadoId() +
            "}";
    }
}
