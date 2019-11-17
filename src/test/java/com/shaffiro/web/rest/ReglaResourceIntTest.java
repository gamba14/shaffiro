package com.shaffiro.web.rest;

import com.shaffiro.ShaffiroApp;

import com.shaffiro.domain.Regla;
import com.shaffiro.domain.Dispositivo;
import com.shaffiro.repository.ReglaRepository;
import com.shaffiro.service.ReglaService;
import com.shaffiro.service.dto.ReglaDTO;
import com.shaffiro.service.mapper.ReglaMapper;
import com.shaffiro.web.rest.errors.ExceptionTranslator;
import com.shaffiro.service.dto.ReglaCriteria;
import com.shaffiro.service.ReglaQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.shaffiro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReglaResource REST controller.
 *
 * @see ReglaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShaffiroApp.class)
public class ReglaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_LOGICA = "AAAAAAAAAA";
    private static final String UPDATED_LOGICA = "BBBBBBBBBB";

    @Autowired
    private ReglaRepository reglaRepository;

    @Autowired
    private ReglaMapper reglaMapper;

    @Autowired
    private ReglaService reglaService;

    @Autowired
    private ReglaQueryService reglaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restReglaMockMvc;

    private Regla regla;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReglaResource reglaResource = new ReglaResource(reglaService, reglaQueryService);
        this.restReglaMockMvc = MockMvcBuilders.standaloneSetup(reglaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regla createEntity(EntityManager em) {
        Regla regla = new Regla()
            .nombre(DEFAULT_NOMBRE)
            .logica(DEFAULT_LOGICA);
        return regla;
    }

    @Before
    public void initTest() {
        regla = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegla() throws Exception {
        int databaseSizeBeforeCreate = reglaRepository.findAll().size();

        // Create the Regla
        ReglaDTO reglaDTO = reglaMapper.toDto(regla);
        restReglaMockMvc.perform(post("/api/reglas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reglaDTO)))
            .andExpect(status().isCreated());

        // Validate the Regla in the database
        List<Regla> reglaList = reglaRepository.findAll();
        assertThat(reglaList).hasSize(databaseSizeBeforeCreate + 1);
        Regla testRegla = reglaList.get(reglaList.size() - 1);
        assertThat(testRegla.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRegla.getLogica()).isEqualTo(DEFAULT_LOGICA);
    }

    @Test
    @Transactional
    public void createReglaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reglaRepository.findAll().size();

        // Create the Regla with an existing ID
        regla.setId(1L);
        ReglaDTO reglaDTO = reglaMapper.toDto(regla);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReglaMockMvc.perform(post("/api/reglas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reglaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Regla in the database
        List<Regla> reglaList = reglaRepository.findAll();
        assertThat(reglaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReglas() throws Exception {
        // Initialize the database
        reglaRepository.saveAndFlush(regla);

        // Get all the reglaList
        restReglaMockMvc.perform(get("/api/reglas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regla.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].logica").value(hasItem(DEFAULT_LOGICA.toString())));
    }
    
    @Test
    @Transactional
    public void getRegla() throws Exception {
        // Initialize the database
        reglaRepository.saveAndFlush(regla);

        // Get the regla
        restReglaMockMvc.perform(get("/api/reglas/{id}", regla.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regla.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.logica").value(DEFAULT_LOGICA.toString()));
    }

    @Test
    @Transactional
    public void getAllReglasByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        reglaRepository.saveAndFlush(regla);

        // Get all the reglaList where nombre equals to DEFAULT_NOMBRE
        defaultReglaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the reglaList where nombre equals to UPDATED_NOMBRE
        defaultReglaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllReglasByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        reglaRepository.saveAndFlush(regla);

        // Get all the reglaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultReglaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the reglaList where nombre equals to UPDATED_NOMBRE
        defaultReglaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllReglasByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        reglaRepository.saveAndFlush(regla);

        // Get all the reglaList where nombre is not null
        defaultReglaShouldBeFound("nombre.specified=true");

        // Get all the reglaList where nombre is null
        defaultReglaShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllReglasByLogicaIsEqualToSomething() throws Exception {
        // Initialize the database
        reglaRepository.saveAndFlush(regla);

        // Get all the reglaList where logica equals to DEFAULT_LOGICA
        defaultReglaShouldBeFound("logica.equals=" + DEFAULT_LOGICA);

        // Get all the reglaList where logica equals to UPDATED_LOGICA
        defaultReglaShouldNotBeFound("logica.equals=" + UPDATED_LOGICA);
    }

    @Test
    @Transactional
    public void getAllReglasByLogicaIsInShouldWork() throws Exception {
        // Initialize the database
        reglaRepository.saveAndFlush(regla);

        // Get all the reglaList where logica in DEFAULT_LOGICA or UPDATED_LOGICA
        defaultReglaShouldBeFound("logica.in=" + DEFAULT_LOGICA + "," + UPDATED_LOGICA);

        // Get all the reglaList where logica equals to UPDATED_LOGICA
        defaultReglaShouldNotBeFound("logica.in=" + UPDATED_LOGICA);
    }

    @Test
    @Transactional
    public void getAllReglasByLogicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        reglaRepository.saveAndFlush(regla);

        // Get all the reglaList where logica is not null
        defaultReglaShouldBeFound("logica.specified=true");

        // Get all the reglaList where logica is null
        defaultReglaShouldNotBeFound("logica.specified=false");
    }

    @Test
    @Transactional
    public void getAllReglasByDispositivoIsEqualToSomething() throws Exception {
        // Initialize the database
        Dispositivo dispositivo = DispositivoResourceIntTest.createEntity(em);
        em.persist(dispositivo);
        em.flush();
        regla.addDispositivo(dispositivo);
        reglaRepository.saveAndFlush(regla);
        Long dispositivoId = dispositivo.getId();

        // Get all the reglaList where dispositivo equals to dispositivoId
        defaultReglaShouldBeFound("dispositivoId.equals=" + dispositivoId);

        // Get all the reglaList where dispositivo equals to dispositivoId + 1
        defaultReglaShouldNotBeFound("dispositivoId.equals=" + (dispositivoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReglaShouldBeFound(String filter) throws Exception {
        restReglaMockMvc.perform(get("/api/reglas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regla.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].logica").value(hasItem(DEFAULT_LOGICA)));

        // Check, that the count call also returns 1
        restReglaMockMvc.perform(get("/api/reglas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReglaShouldNotBeFound(String filter) throws Exception {
        restReglaMockMvc.perform(get("/api/reglas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReglaMockMvc.perform(get("/api/reglas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRegla() throws Exception {
        // Get the regla
        restReglaMockMvc.perform(get("/api/reglas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegla() throws Exception {
        // Initialize the database
        reglaRepository.saveAndFlush(regla);

        int databaseSizeBeforeUpdate = reglaRepository.findAll().size();

        // Update the regla
        Regla updatedRegla = reglaRepository.findById(regla.getId()).get();
        // Disconnect from session so that the updates on updatedRegla are not directly saved in db
        em.detach(updatedRegla);
        updatedRegla
            .nombre(UPDATED_NOMBRE)
            .logica(UPDATED_LOGICA);
        ReglaDTO reglaDTO = reglaMapper.toDto(updatedRegla);

        restReglaMockMvc.perform(put("/api/reglas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reglaDTO)))
            .andExpect(status().isOk());

        // Validate the Regla in the database
        List<Regla> reglaList = reglaRepository.findAll();
        assertThat(reglaList).hasSize(databaseSizeBeforeUpdate);
        Regla testRegla = reglaList.get(reglaList.size() - 1);
        assertThat(testRegla.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRegla.getLogica()).isEqualTo(UPDATED_LOGICA);
    }

    @Test
    @Transactional
    public void updateNonExistingRegla() throws Exception {
        int databaseSizeBeforeUpdate = reglaRepository.findAll().size();

        // Create the Regla
        ReglaDTO reglaDTO = reglaMapper.toDto(regla);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReglaMockMvc.perform(put("/api/reglas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reglaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Regla in the database
        List<Regla> reglaList = reglaRepository.findAll();
        assertThat(reglaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegla() throws Exception {
        // Initialize the database
        reglaRepository.saveAndFlush(regla);

        int databaseSizeBeforeDelete = reglaRepository.findAll().size();

        // Delete the regla
        restReglaMockMvc.perform(delete("/api/reglas/{id}", regla.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Regla> reglaList = reglaRepository.findAll();
        assertThat(reglaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regla.class);
        Regla regla1 = new Regla();
        regla1.setId(1L);
        Regla regla2 = new Regla();
        regla2.setId(regla1.getId());
        assertThat(regla1).isEqualTo(regla2);
        regla2.setId(2L);
        assertThat(regla1).isNotEqualTo(regla2);
        regla1.setId(null);
        assertThat(regla1).isNotEqualTo(regla2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReglaDTO.class);
        ReglaDTO reglaDTO1 = new ReglaDTO();
        reglaDTO1.setId(1L);
        ReglaDTO reglaDTO2 = new ReglaDTO();
        assertThat(reglaDTO1).isNotEqualTo(reglaDTO2);
        reglaDTO2.setId(reglaDTO1.getId());
        assertThat(reglaDTO1).isEqualTo(reglaDTO2);
        reglaDTO2.setId(2L);
        assertThat(reglaDTO1).isNotEqualTo(reglaDTO2);
        reglaDTO1.setId(null);
        assertThat(reglaDTO1).isNotEqualTo(reglaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reglaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reglaMapper.fromId(null)).isNull();
    }
}
