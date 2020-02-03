package com.shaffiro.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DispositivoNoAsociado entity.
 */
public class DispositivoNoAsociadoDTO implements Serializable {

    private Long id;

    
    private String mac;

    private String uuid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DispositivoNoAsociadoDTO dispositivoNoAsociadoDTO = (DispositivoNoAsociadoDTO) o;
        if (dispositivoNoAsociadoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dispositivoNoAsociadoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DispositivoNoAsociadoDTO{" +
            "id=" + getId() +
            ", mac='" + getMac() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
