package com.shaffiro.web.rest;
import com.shaffiro.service.DispositivoNoAsociadoService;
import com.shaffiro.web.rest.errors.BadRequestAlertException;
import com.shaffiro.web.rest.util.HeaderUtil;
import com.shaffiro.service.dto.DispositivoNoAsociadoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DispositivoNoAsociado.
 */
@RestController
@RequestMapping("/api")
public class DispositivoNoAsociadoResource {

    private final Logger log = LoggerFactory.getLogger(DispositivoNoAsociadoResource.class);

    private static final String ENTITY_NAME = "dispositivoNoAsociado";

    private final DispositivoNoAsociadoService dispositivoNoAsociadoService;

    public DispositivoNoAsociadoResource(DispositivoNoAsociadoService dispositivoNoAsociadoService) {
        this.dispositivoNoAsociadoService = dispositivoNoAsociadoService;
    }

    /**
     * POST  /dispositivo-no-asociados : Create a new dispositivoNoAsociado.
     *
     * @param dispositivoNoAsociadoDTO the dispositivoNoAsociadoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dispositivoNoAsociadoDTO, or with status 400 (Bad Request) if the dispositivoNoAsociado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dispositivo-no-asociados")
    public ResponseEntity<DispositivoNoAsociadoDTO> createDispositivoNoAsociado(@Valid @RequestBody DispositivoNoAsociadoDTO dispositivoNoAsociadoDTO) throws URISyntaxException {
        log.debug("REST request to save DispositivoNoAsociado : {}", dispositivoNoAsociadoDTO);
        if (dispositivoNoAsociadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new dispositivoNoAsociado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DispositivoNoAsociadoDTO result = dispositivoNoAsociadoService.save(dispositivoNoAsociadoDTO);
        return ResponseEntity.created(new URI("/api/dispositivo-no-asociados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dispositivo-no-asociados : Updates an existing dispositivoNoAsociado.
     *
     * @param dispositivoNoAsociadoDTO the dispositivoNoAsociadoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dispositivoNoAsociadoDTO,
     * or with status 400 (Bad Request) if the dispositivoNoAsociadoDTO is not valid,
     * or with status 500 (Internal Server Error) if the dispositivoNoAsociadoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dispositivo-no-asociados")
    public ResponseEntity<DispositivoNoAsociadoDTO> updateDispositivoNoAsociado(@Valid @RequestBody DispositivoNoAsociadoDTO dispositivoNoAsociadoDTO) throws URISyntaxException {
        log.debug("REST request to update DispositivoNoAsociado : {}", dispositivoNoAsociadoDTO);
        if (dispositivoNoAsociadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DispositivoNoAsociadoDTO result = dispositivoNoAsociadoService.save(dispositivoNoAsociadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dispositivoNoAsociadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dispositivo-no-asociados : get all the dispositivoNoAsociados.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dispositivoNoAsociados in body
     */
    @GetMapping("/dispositivo-no-asociados")
    public List<DispositivoNoAsociadoDTO> getAllDispositivoNoAsociados() {
        log.debug("REST request to get all DispositivoNoAsociados");
        return dispositivoNoAsociadoService.findAll();
    }

    /**
     * GET  /dispositivo-no-asociados/:id : get the "id" dispositivoNoAsociado.
     *
     * @param id the id of the dispositivoNoAsociadoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dispositivoNoAsociadoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dispositivo-no-asociados/{id}")
    public ResponseEntity<DispositivoNoAsociadoDTO> getDispositivoNoAsociado(@PathVariable Long id) {
        log.debug("REST request to get DispositivoNoAsociado : {}", id);
        Optional<DispositivoNoAsociadoDTO> dispositivoNoAsociadoDTO = dispositivoNoAsociadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispositivoNoAsociadoDTO);
    }

    /**
     * DELETE  /dispositivo-no-asociados/:id : delete the "id" dispositivoNoAsociado.
     *
     * @param id the id of the dispositivoNoAsociadoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dispositivo-no-asociados/{id}")
    public ResponseEntity<Void> deleteDispositivoNoAsociado(@PathVariable Long id) {
        log.debug("REST request to delete DispositivoNoAsociado : {}", id);
        dispositivoNoAsociadoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
