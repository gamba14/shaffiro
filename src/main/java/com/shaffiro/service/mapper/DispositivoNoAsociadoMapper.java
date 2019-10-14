package com.shaffiro.service.mapper;

import com.shaffiro.domain.*;
import com.shaffiro.service.dto.DispositivoNoAsociadoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DispositivoNoAsociado and its DTO DispositivoNoAsociadoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DispositivoNoAsociadoMapper extends EntityMapper<DispositivoNoAsociadoDTO, DispositivoNoAsociado> {



    default DispositivoNoAsociado fromId(Long id) {
        if (id == null) {
            return null;
        }
        DispositivoNoAsociado dispositivoNoAsociado = new DispositivoNoAsociado();
        dispositivoNoAsociado.setId(id);
        return dispositivoNoAsociado;
    }
}
