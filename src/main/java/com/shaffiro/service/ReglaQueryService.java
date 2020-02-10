package com.shaffiro.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.shaffiro.domain.Regla;
import com.shaffiro.domain.*; // for static metamodels
import com.shaffiro.repository.ReglaRepository;
import com.shaffiro.service.dto.ReglaCriteria;
import com.shaffiro.service.dto.ReglaDTO;
import com.shaffiro.service.mapper.ReglaMapper;

/**
 * Service for executing complex queries for Regla entities in the database.
 * The main input is a {@link ReglaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReglaDTO} or a {@link Page} of {@link ReglaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReglaQueryService extends QueryService<Regla> {

    private final Logger log = LoggerFactory.getLogger(ReglaQueryService.class);

    private final ReglaRepository reglaRepository;

    private final ReglaMapper reglaMapper;

    public ReglaQueryService(ReglaRepository reglaRepository, ReglaMapper reglaMapper) {
        this.reglaRepository = reglaRepository;
        this.reglaMapper = reglaMapper;
    }

    /**
     * Return a {@link List} of {@link ReglaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReglaDTO> findByCriteria(ReglaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Regla> specification = createSpecification(criteria);
        return reglaMapper.toDto(reglaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReglaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReglaDTO> findByCriteria(ReglaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Regla> specification = createSpecification(criteria);
        return reglaRepository.findAll(specification, page)
            .map(reglaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReglaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Regla> specification = createSpecification(criteria);
        return reglaRepository.count(specification);
    }

    /**
     * Function to convert ReglaCriteria to a {@link Specification}
     */
    private Specification<Regla> createSpecification(ReglaCriteria criteria) {
        Specification<Regla> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Regla_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Regla_.nombre));
            }
            if (criteria.getUnidad() != null) {
                specification = specification.and(buildSpecification(criteria.getUnidad(), Regla_.unidad));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValor(), Regla_.valor));
            }
            if (criteria.getOperador() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOperador(), Regla_.operador));
            }
            if (criteria.getDispositivoAsociadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDispositivoAsociadoId(),
                    root -> root.join(Regla_.dispositivoAsociado, JoinType.LEFT).get(Dispositivo_.id)));
            }
        }
        return specification;
    }
}
