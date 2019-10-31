package com.shaffiro.service;

import com.shaffiro.domain.Regla;
import com.shaffiro.repository.ReglaRepository;
import com.shaffiro.service.dto.ReglaDTO;
import com.shaffiro.service.mapper.ReglaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Regla.
 */
@Service
@Transactional
public class ReglaService {

    private final Logger log = LoggerFactory.getLogger(ReglaService.class);

    private final ReglaRepository reglaRepository;

    private final ReglaMapper reglaMapper;

    public ReglaService(ReglaRepository reglaRepository, ReglaMapper reglaMapper) {
        this.reglaRepository = reglaRepository;
        this.reglaMapper = reglaMapper;
    }

    /**
     * Save a regla.
     *
     * @param reglaDTO the entity to save
     * @return the persisted entity
     */
    public ReglaDTO save(ReglaDTO reglaDTO) {
        log.debug("Request to save Regla : {}", reglaDTO);
        Regla regla = reglaMapper.toEntity(reglaDTO);
        regla = reglaRepository.save(regla);
        return reglaMapper.toDto(regla);
    }

    /**
     * Get all the reglas.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReglaDTO> findAll() {
        log.debug("Request to get all Reglas");
        return reglaRepository.findAll().stream()
            .map(reglaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one regla by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ReglaDTO> findOne(Long id) {
        log.debug("Request to get Regla : {}", id);
        return reglaRepository.findById(id)
            .map(reglaMapper::toDto);
    }

    /**
     * Delete the regla by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Regla : {}", id);
        reglaRepository.deleteById(id);
    }
}
