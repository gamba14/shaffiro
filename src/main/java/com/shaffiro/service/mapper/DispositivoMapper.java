package com.shaffiro.service.mapper;

import com.shaffiro.domain.*;
import com.shaffiro.service.dto.DispositivoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dispositivo and its DTO DispositivoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DispositivoMapper extends EntityMapper<DispositivoDTO, Dispositivo> {



    default Dispositivo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(id);
        return dispositivo;
    }
}
