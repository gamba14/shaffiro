package com.shaffiro.service;

import com.shaffiro.domain.DispositivoNoAsociado;
import com.shaffiro.repository.DispositivoNoAsociadoRepository;
import com.shaffiro.service.dto.DispositivoNoAsociadoDTO;
import com.shaffiro.service.mapper.DispositivoNoAsociadoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DispositivoNoAsociado.
 */
@Service
@Transactional
public class DispositivoNoAsociadoService {

    private final Logger log = LoggerFactory.getLogger(DispositivoNoAsociadoService.class);

    private final DispositivoNoAsociadoRepository dispositivoNoAsociadoRepository;

    private final DispositivoNoAsociadoMapper dispositivoNoAsociadoMapper;

    public DispositivoNoAsociadoService(DispositivoNoAsociadoRepository dispositivoNoAsociadoRepository, DispositivoNoAsociadoMapper dispositivoNoAsociadoMapper) {
        this.dispositivoNoAsociadoRepository = dispositivoNoAsociadoRepository;
        this.dispositivoNoAsociadoMapper = dispositivoNoAsociadoMapper;
    }

    /**
     * Save a dispositivoNoAsociado.
     *
     * @param dispositivoNoAsociadoDTO the entity to save
     * @return the persisted entity
     */
    public DispositivoNoAsociadoDTO save(DispositivoNoAsociadoDTO dispositivoNoAsociadoDTO) {
        log.debug("Request to save DispositivoNoAsociado : {}", dispositivoNoAsociadoDTO);
        DispositivoNoAsociado dispositivoNoAsociado = dispositivoNoAsociadoMapper.toEntity(dispositivoNoAsociadoDTO);
        dispositivoNoAsociado = dispositivoNoAsociadoRepository.save(dispositivoNoAsociado);
        return dispositivoNoAsociadoMapper.toDto(dispositivoNoAsociado);
    }

    /**
     * Get all the dispositivoNoAsociados.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DispositivoNoAsociadoDTO> findAll() {
        log.debug("Request to get all DispositivoNoAsociados");
        return dispositivoNoAsociadoRepository.findAll().stream()
            .map(dispositivoNoAsociadoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dispositivoNoAsociado by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DispositivoNoAsociadoDTO> findOne(Long id) {
        log.debug("Request to get DispositivoNoAsociado : {}", id);
        return dispositivoNoAsociadoRepository.findById(id)
            .map(dispositivoNoAsociadoMapper::toDto);
    }

    /**
     * Delete the dispositivoNoAsociado by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DispositivoNoAsociado : {}", id);
        dispositivoNoAsociadoRepository.deleteById(id);
    }
}
