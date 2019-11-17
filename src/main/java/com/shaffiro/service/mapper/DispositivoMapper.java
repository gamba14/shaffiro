package com.shaffiro.service.mapper;

import com.shaffiro.domain.*;
import com.shaffiro.service.dto.DispositivoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dispositivo and its DTO DispositivoDTO.
 */
@Mapper(componentModel = "spring", uses = {ReglaMapper.class})
public interface DispositivoMapper extends EntityMapper<DispositivoDTO, Dispositivo> {

    @Mapping(source = "regla.id", target = "reglaId")
    DispositivoDTO toDto(Dispositivo dispositivo);

    @Mapping(source = "reglaId", target = "regla")
    Dispositivo toEntity(DispositivoDTO dispositivoDTO);

    default Dispositivo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(id);
        return dispositivo;
    }
}
