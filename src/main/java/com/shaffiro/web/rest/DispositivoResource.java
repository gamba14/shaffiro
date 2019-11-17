package com.shaffiro.web.rest;
import com.shaffiro.service.DispositivoService;
import com.shaffiro.web.rest.errors.BadRequestAlertException;
import com.shaffiro.web.rest.util.HeaderUtil;
import com.shaffiro.service.dto.DispositivoDTO;
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
 * REST controller for managing Dispositivo.
 */
@RestController
@RequestMapping("/api")
public class DispositivoResource {

    private final Logger log = LoggerFactory.getLogger(DispositivoResource.class);

    private static final String ENTITY_NAME = "dispositivo";

    private final DispositivoService dispositivoService;

    public DispositivoResource(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    /**
     * POST  /dispositivos : Create a new dispositivo.
     *
     * @param dispositivoDTO the dispositivoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dispositivoDTO, or with status 400 (Bad Request) if the dispositivo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dispositivos")
    public ResponseEntity<DispositivoDTO> createDispositivo(@RequestBody DispositivoDTO dispositivoDTO) throws URISyntaxException {
        log.debug("REST request to save Dispositivo : {}", dispositivoDTO);
        if (dispositivoDTO.getId() != null) {
            throw new BadRequestAlertException("A new dispositivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DispositivoDTO result = dispositivoService.save(dispositivoDTO);
        return ResponseEntity.created(new URI("/api/dispositivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dispositivos : Updates an existing dispositivo.
     *
     * @param dispositivoDTO the dispositivoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dispositivoDTO,
     * or with status 400 (Bad Request) if the dispositivoDTO is not valid,
     * or with status 500 (Internal Server Error) if the dispositivoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dispositivos")
    public ResponseEntity<DispositivoDTO> updateDispositivo(@RequestBody DispositivoDTO dispositivoDTO) throws URISyntaxException {
        log.debug("REST request to update Dispositivo : {}", dispositivoDTO);
        if (dispositivoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DispositivoDTO result = dispositivoService.save(dispositivoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dispositivoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dispositivos : get all the dispositivos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dispositivos in body
     */
    @GetMapping("/dispositivos")
    public List<DispositivoDTO> getAllDispositivos() {
        log.debug("REST request to get all Dispositivos");
        return dispositivoService.findAll();
    }

    /**
     * GET  /dispositivos/:id : get the "id" dispositivo.
     *
     * @param id the id of the dispositivoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dispositivoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dispositivos/{id}")
    public ResponseEntity<DispositivoDTO> getDispositivo(@PathVariable Long id) {
        log.debug("REST request to get Dispositivo : {}", id);
        Optional<DispositivoDTO> dispositivoDTO = dispositivoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispositivoDTO);
    }

    /**
     * DELETE  /dispositivos/:id : delete the "id" dispositivo.
     *
     * @param id the id of the dispositivoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dispositivos/{id}")
    public ResponseEntity<Void> deleteDispositivo(@PathVariable Long id) {
        log.debug("REST request to delete Dispositivo : {}", id);
        dispositivoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
