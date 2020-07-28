package com.shaffiro.web.rest;

import com.shaffiro.ShaffiroApp;

import com.shaffiro.domain.DispositivoNoAsociado;
import com.shaffiro.repository.DispositivoNoAsociadoRepository;
import com.shaffiro.service.DispositivoNoAsociadoService;
import com.shaffiro.service.DispositivoService;
import com.shaffiro.service.dto.DispositivoNoAsociadoDTO;
import com.shaffiro.service.mapper.DispositivoNoAsociadoMapper;
import com.shaffiro.web.rest.errors.ExceptionTranslator;

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
 * Test class for the DispositivoNoAsociadoResource REST controller.
 *
 * @see DispositivoNoAsociadoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShaffiroApp.class)
public class DispositivoNoAsociadoResourceIntTest {

    private static final String DEFAULT_MAC = "AAAAAAAAAA";
    private static final String UPDATED_MAC = "BBBBBBBBBB";

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUERTO = 1;
    private static final Integer UPDATED_PUERTO = 2;

    @Autowired
    private DispositivoNoAsociadoRepository dispositivoNoAsociadoRepository;

    @Autowired
    private DispositivoNoAsociadoMapper dispositivoNoAsociadoMapper;

    @Autowired
    private DispositivoNoAsociadoService dispositivoNoAsociadoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private DispositivoService dispositivoService;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDispositivoNoAsociadoMockMvc;

    private DispositivoNoAsociado dispositivoNoAsociado;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DispositivoNoAsociadoResource dispositivoNoAsociadoResource = new DispositivoNoAsociadoResource(dispositivoNoAsociadoService, dispositivoService, discoveryService);
        this.restDispositivoNoAsociadoMockMvc = MockMvcBuilders.standaloneSetup(dispositivoNoAsociadoResource)
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
    public static DispositivoNoAsociado createEntity(EntityManager em) {
        DispositivoNoAsociado dispositivoNoAsociado = new DispositivoNoAsociado()
            .mac(DEFAULT_MAC)
            .uuid(DEFAULT_UUID)
            .puerto(DEFAULT_PUERTO);
        return dispositivoNoAsociado;
    }

    @Before
    public void initTest() {
        dispositivoNoAsociado = createEntity(em);
    }

    @Test
    @Transactional
    public void createDispositivoNoAsociado() throws Exception {
        int databaseSizeBeforeCreate = dispositivoNoAsociadoRepository.findAll().size();

        // Create the DispositivoNoAsociado
        DispositivoNoAsociadoDTO dispositivoNoAsociadoDTO = dispositivoNoAsociadoMapper.toDto(dispositivoNoAsociado);
        restDispositivoNoAsociadoMockMvc.perform(post("/api/dispositivo-no-asociados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispositivoNoAsociadoDTO)))
            .andExpect(status().isCreated());

        // Validate the DispositivoNoAsociado in the database
        List<DispositivoNoAsociado> dispositivoNoAsociadoList = dispositivoNoAsociadoRepository.findAll();
        assertThat(dispositivoNoAsociadoList).hasSize(databaseSizeBeforeCreate + 1);
        DispositivoNoAsociado testDispositivoNoAsociado = dispositivoNoAsociadoList.get(dispositivoNoAsociadoList.size() - 1);
        assertThat(testDispositivoNoAsociado.getMac()).isEqualTo(DEFAULT_MAC);
        assertThat(testDispositivoNoAsociado.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testDispositivoNoAsociado.getPuerto()).isEqualTo(DEFAULT_PUERTO);
    }

    @Test
    @Transactional
    public void createDispositivoNoAsociadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dispositivoNoAsociadoRepository.findAll().size();

        // Create the DispositivoNoAsociado with an existing ID
        dispositivoNoAsociado.setId(1L);
        DispositivoNoAsociadoDTO dispositivoNoAsociadoDTO = dispositivoNoAsociadoMapper.toDto(dispositivoNoAsociado);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispositivoNoAsociadoMockMvc.perform(post("/api/dispositivo-no-asociados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispositivoNoAsociadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DispositivoNoAsociado in the database
        List<DispositivoNoAsociado> dispositivoNoAsociadoList = dispositivoNoAsociadoRepository.findAll();
        assertThat(dispositivoNoAsociadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDispositivoNoAsociados() throws Exception {
        // Initialize the database
        dispositivoNoAsociadoRepository.saveAndFlush(dispositivoNoAsociado);

        // Get all the dispositivoNoAsociadoList
        restDispositivoNoAsociadoMockMvc.perform(get("/api/dispositivo-no-asociados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispositivoNoAsociado.getId().intValue())))
            .andExpect(jsonPath("$.[*].mac").value(hasItem(DEFAULT_MAC.toString())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].puerto").value(hasItem(DEFAULT_PUERTO)));
    }

    @Test
    @Transactional
    public void getDispositivoNoAsociado() throws Exception {
        // Initialize the database
        dispositivoNoAsociadoRepository.saveAndFlush(dispositivoNoAsociado);

        // Get the dispositivoNoAsociado
        restDispositivoNoAsociadoMockMvc.perform(get("/api/dispositivo-no-asociados/{id}", dispositivoNoAsociado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dispositivoNoAsociado.getId().intValue()))
            .andExpect(jsonPath("$.mac").value(DEFAULT_MAC.toString()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.puerto").value(DEFAULT_PUERTO));
    }

    @Test
    @Transactional
    public void getNonExistingDispositivoNoAsociado() throws Exception {
        // Get the dispositivoNoAsociado
        restDispositivoNoAsociadoMockMvc.perform(get("/api/dispositivo-no-asociados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDispositivoNoAsociado() throws Exception {
        // Initialize the database
        dispositivoNoAsociadoRepository.saveAndFlush(dispositivoNoAsociado);

        int databaseSizeBeforeUpdate = dispositivoNoAsociadoRepository.findAll().size();

        // Update the dispositivoNoAsociado
        DispositivoNoAsociado updatedDispositivoNoAsociado = dispositivoNoAsociadoRepository.findById(dispositivoNoAsociado.getId()).get();
        // Disconnect from session so that the updates on updatedDispositivoNoAsociado are not directly saved in db
        em.detach(updatedDispositivoNoAsociado);
        updatedDispositivoNoAsociado
            .mac(UPDATED_MAC)
            .uuid(UPDATED_UUID)
            .puerto(UPDATED_PUERTO);
        DispositivoNoAsociadoDTO dispositivoNoAsociadoDTO = dispositivoNoAsociadoMapper.toDto(updatedDispositivoNoAsociado);

        restDispositivoNoAsociadoMockMvc.perform(put("/api/dispositivo-no-asociados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispositivoNoAsociadoDTO)))
            .andExpect(status().isOk());

        // Validate the DispositivoNoAsociado in the database
        List<DispositivoNoAsociado> dispositivoNoAsociadoList = dispositivoNoAsociadoRepository.findAll();
        assertThat(dispositivoNoAsociadoList).hasSize(databaseSizeBeforeUpdate);
        DispositivoNoAsociado testDispositivoNoAsociado = dispositivoNoAsociadoList.get(dispositivoNoAsociadoList.size() - 1);
        assertThat(testDispositivoNoAsociado.getMac()).isEqualTo(UPDATED_MAC);
        assertThat(testDispositivoNoAsociado.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testDispositivoNoAsociado.getPuerto()).isEqualTo(UPDATED_PUERTO);
    }

    @Test
    @Transactional
    public void updateNonExistingDispositivoNoAsociado() throws Exception {
        int databaseSizeBeforeUpdate = dispositivoNoAsociadoRepository.findAll().size();

        // Create the DispositivoNoAsociado
        DispositivoNoAsociadoDTO dispositivoNoAsociadoDTO = dispositivoNoAsociadoMapper.toDto(dispositivoNoAsociado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositivoNoAsociadoMockMvc.perform(put("/api/dispositivo-no-asociados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispositivoNoAsociadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DispositivoNoAsociado in the database
        List<DispositivoNoAsociado> dispositivoNoAsociadoList = dispositivoNoAsociadoRepository.findAll();
        assertThat(dispositivoNoAsociadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDispositivoNoAsociado() throws Exception {
        // Initialize the database
        dispositivoNoAsociadoRepository.saveAndFlush(dispositivoNoAsociado);

        int databaseSizeBeforeDelete = dispositivoNoAsociadoRepository.findAll().size();

        // Delete the dispositivoNoAsociado
        restDispositivoNoAsociadoMockMvc.perform(delete("/api/dispositivo-no-asociados/{id}", dispositivoNoAsociado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DispositivoNoAsociado> dispositivoNoAsociadoList = dispositivoNoAsociadoRepository.findAll();
        assertThat(dispositivoNoAsociadoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositivoNoAsociado.class);
        DispositivoNoAsociado dispositivoNoAsociado1 = new DispositivoNoAsociado();
        dispositivoNoAsociado1.setId(1L);
        DispositivoNoAsociado dispositivoNoAsociado2 = new DispositivoNoAsociado();
        dispositivoNoAsociado2.setId(dispositivoNoAsociado1.getId());
        assertThat(dispositivoNoAsociado1).isEqualTo(dispositivoNoAsociado2);
        dispositivoNoAsociado2.setId(2L);
        assertThat(dispositivoNoAsociado1).isNotEqualTo(dispositivoNoAsociado2);
        dispositivoNoAsociado1.setId(null);
        assertThat(dispositivoNoAsociado1).isNotEqualTo(dispositivoNoAsociado2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositivoNoAsociadoDTO.class);
        DispositivoNoAsociadoDTO dispositivoNoAsociadoDTO1 = new DispositivoNoAsociadoDTO();
        dispositivoNoAsociadoDTO1.setId(1L);
        DispositivoNoAsociadoDTO dispositivoNoAsociadoDTO2 = new DispositivoNoAsociadoDTO();
        assertThat(dispositivoNoAsociadoDTO1).isNotEqualTo(dispositivoNoAsociadoDTO2);
        dispositivoNoAsociadoDTO2.setId(dispositivoNoAsociadoDTO1.getId());
        assertThat(dispositivoNoAsociadoDTO1).isEqualTo(dispositivoNoAsociadoDTO2);
        dispositivoNoAsociadoDTO2.setId(2L);
        assertThat(dispositivoNoAsociadoDTO1).isNotEqualTo(dispositivoNoAsociadoDTO2);
        dispositivoNoAsociadoDTO1.setId(null);
        assertThat(dispositivoNoAsociadoDTO1).isNotEqualTo(dispositivoNoAsociadoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dispositivoNoAsociadoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dispositivoNoAsociadoMapper.fromId(null)).isNull();
    }
}
