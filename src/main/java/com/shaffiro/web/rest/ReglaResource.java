package com.shaffiro.web.rest;
import com.shaffiro.service.ReglaService;
import com.shaffiro.web.rest.errors.BadRequestAlertException;
import com.shaffiro.web.rest.util.HeaderUtil;
import com.shaffiro.service.dto.ReglaDTO;
import com.shaffiro.service.dto.ReglaCriteria;
import com.shaffiro.service.ReglaQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Regla.
 */
@RestController
@RequestMapping("/api")
public class ReglaResource {

    private final Logger log = LoggerFactory.getLogger(ReglaResource.class);

    private static final String ENTITY_NAME = "regla";

    private final ReglaService reglaService;

    private final ReglaQueryService reglaQueryService;

    public ReglaResource(ReglaService reglaService, ReglaQueryService reglaQueryService) {
        this.reglaService = reglaService;
        this.reglaQueryService = reglaQueryService;
    }

    /**
     * POST  /reglas : Create a new regla.
     *
     * @param reglaDTO the reglaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reglaDTO, or with status 400 (Bad Request) if the regla has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reglas")
    public ResponseEntity<ReglaDTO> createRegla(@RequestBody ReglaDTO reglaDTO) throws URISyntaxException {
        log.debug("REST request to save Regla : {}", reglaDTO);
        if (reglaDTO.getId() != null) {
            throw new BadRequestAlertException("A new regla cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReglaDTO result = reglaService.save(reglaDTO);
        return ResponseEntity.created(new URI("/api/reglas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reglas : Updates an existing regla.
     *
     * @param reglaDTO the reglaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reglaDTO,
     * or with status 400 (Bad Request) if the reglaDTO is not valid,
     * or with status 500 (Internal Server Error) if the reglaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reglas")
    public ResponseEntity<ReglaDTO> updateRegla(@RequestBody ReglaDTO reglaDTO) throws URISyntaxException {
        log.debug("REST request to update Regla : {}", reglaDTO);
        if (reglaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReglaDTO result = reglaService.save(reglaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reglaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reglas : get all the reglas.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of reglas in body
     */
    @GetMapping("/reglas")
    public ResponseEntity<List<ReglaDTO>> getAllReglas(ReglaCriteria criteria) {
        log.debug("REST request to get Reglas by criteria: {}", criteria);
        List<ReglaDTO> entityList = reglaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /reglas/count : count all the reglas.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/reglas/count")
    public ResponseEntity<Long> countReglas(ReglaCriteria criteria) {
        log.debug("REST request to count Reglas by criteria: {}", criteria);
        return ResponseEntity.ok().body(reglaQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /reglas/:id : get the "id" regla.
     *
     * @param id the id of the reglaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reglaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reglas/{id}")
    public ResponseEntity<ReglaDTO> getRegla(@PathVariable Long id) {
        log.debug("REST request to get Regla : {}", id);
        Optional<ReglaDTO> reglaDTO = reglaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reglaDTO);
    }

    /**
     * DELETE  /reglas/:id : delete the "id" regla.
     *
     * @param id the id of the reglaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reglas/{id}")
    public ResponseEntity<Void> deleteRegla(@PathVariable Long id) {
        log.debug("REST request to delete Regla : {}", id);
        reglaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
