package com.shaffiro.service.mapper;

import com.shaffiro.domain.*;
import com.shaffiro.service.dto.ReglaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Regla and its DTO ReglaDTO.
 */
@Mapper(componentModel = "spring", uses = {DispositivoMapper.class})
public interface ReglaMapper extends EntityMapper<ReglaDTO, Regla> {

    @Mapping(source = "dispositivoAsociado.id", target = "dispositivoAsociadoId")
    ReglaDTO toDto(Regla regla);

    @Mapping(source = "dispositivoAsociadoId", target = "dispositivoAsociado")
    Regla toEntity(ReglaDTO reglaDTO);

    default Regla fromId(Long id) {
        if (id == null) {
            return null;
        }
        Regla regla = new Regla();
        regla.setId(id);
        return regla;
    }
}
