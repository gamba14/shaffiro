package com.shaffiro.web.rest;

import com.shaffiro.ShaffiroApp;

import com.shaffiro.domain.Dispositivo;
import com.shaffiro.repository.DispositivoRepository;
import com.shaffiro.service.DiscoveryService;
import com.shaffiro.service.DispositivoService;
import com.shaffiro.service.dto.DispositivoDTO;
import com.shaffiro.service.mapper.DispositivoMapper;
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

import com.shaffiro.domain.enumeration.TipoDispositivo;
/**
 * Test class for the DispositivoResource REST controller.
 *
 * @see DispositivoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShaffiroApp.class)
public class DispositivoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final TipoDispositivo DEFAULT_TIPO = TipoDispositivo.SENSOR;
    private static final TipoDispositivo UPDATED_TIPO = TipoDispositivo.ACTUADOR;

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String DEFAULT_CONFIGURACION = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGURACION = "BBBBBBBBBB";

    private static final String DEFAULT_REGLA = "AAAAAAAAAA";
    private static final String UPDATED_REGLA = "BBBBBBBBBB";

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private DispositivoMapper dispositivoMapper;

    @Autowired
    private DispositivoService dispositivoService;

    @Autowired
    private DiscoveryService discoveryService;

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

    private MockMvc restDispositivoMockMvc;

    private Dispositivo dispositivo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DispositivoResource dispositivoResource = new DispositivoResource(dispositivoService, discoveryService);
        this.restDispositivoMockMvc = MockMvcBuilders.standaloneSetup(dispositivoResource)
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
    public static Dispositivo createEntity(EntityManager em) {
        Dispositivo dispositivo = new Dispositivo()
            .nombre(DEFAULT_NOMBRE)
            .tipo(DEFAULT_TIPO)
            .activo(DEFAULT_ACTIVO)
            .configuracion(DEFAULT_CONFIGURACION)
            .regla(DEFAULT_REGLA);
        return dispositivo;
    }

    @Before
    public void initTest() {
        dispositivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createDispositivo() throws Exception {
        int databaseSizeBeforeCreate = dispositivoRepository.findAll().size();

        // Create the Dispositivo
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);
        restDispositivoMockMvc.perform(post("/api/dispositivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispositivoDTO)))
            .andExpect(status().isCreated());

        // Validate the Dispositivo in the database
        List<Dispositivo> dispositivoList = dispositivoRepository.findAll();
        assertThat(dispositivoList).hasSize(databaseSizeBeforeCreate + 1);
        Dispositivo testDispositivo = dispositivoList.get(dispositivoList.size() - 1);
        assertThat(testDispositivo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDispositivo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testDispositivo.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testDispositivo.getConfiguracion()).isEqualTo(DEFAULT_CONFIGURACION);
        assertThat(testDispositivo.getRegla()).isEqualTo(DEFAULT_REGLA);
    }

    @Test
    @Transactional
    public void createDispositivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dispositivoRepository.findAll().size();

        // Create the Dispositivo with an existing ID
        dispositivo.setId(1L);
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispositivoMockMvc.perform(post("/api/dispositivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispositivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dispositivo in the database
        List<Dispositivo> dispositivoList = dispositivoRepository.findAll();
        assertThat(dispositivoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDispositivos() throws Exception {
        // Initialize the database
        dispositivoRepository.saveAndFlush(dispositivo);

        // Get all the dispositivoList
        restDispositivoMockMvc.perform(get("/api/dispositivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispositivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].configuracion").value(hasItem(DEFAULT_CONFIGURACION.toString())))
            .andExpect(jsonPath("$.[*].regla").value(hasItem(DEFAULT_REGLA.toString())));
    }

    @Test
    @Transactional
    public void getDispositivo() throws Exception {
        // Initialize the database
        dispositivoRepository.saveAndFlush(dispositivo);

        // Get the dispositivo
        restDispositivoMockMvc.perform(get("/api/dispositivos/{id}", dispositivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dispositivo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.configuracion").value(DEFAULT_CONFIGURACION.toString()))
            .andExpect(jsonPath("$.regla").value(DEFAULT_REGLA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDispositivo() throws Exception {
        // Get the dispositivo
        restDispositivoMockMvc.perform(get("/api/dispositivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDispositivo() throws Exception {
        // Initialize the database
        dispositivoRepository.saveAndFlush(dispositivo);

        int databaseSizeBeforeUpdate = dispositivoRepository.findAll().size();

        // Update the dispositivo
        Dispositivo updatedDispositivo = dispositivoRepository.findById(dispositivo.getId()).get();
        // Disconnect from session so that the updates on updatedDispositivo are not directly saved in db
        em.detach(updatedDispositivo);
        updatedDispositivo
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .activo(UPDATED_ACTIVO)
            .configuracion(UPDATED_CONFIGURACION)
            .regla(UPDATED_REGLA);
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(updatedDispositivo);

        restDispositivoMockMvc.perform(put("/api/dispositivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispositivoDTO)))
            .andExpect(status().isOk());

        // Validate the Dispositivo in the database
        List<Dispositivo> dispositivoList = dispositivoRepository.findAll();
        assertThat(dispositivoList).hasSize(databaseSizeBeforeUpdate);
        Dispositivo testDispositivo = dispositivoList.get(dispositivoList.size() - 1);
        assertThat(testDispositivo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDispositivo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testDispositivo.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testDispositivo.getConfiguracion()).isEqualTo(UPDATED_CONFIGURACION);
        assertThat(testDispositivo.getRegla()).isEqualTo(UPDATED_REGLA);
    }

    @Test
    @Transactional
    public void updateNonExistingDispositivo() throws Exception {
        int databaseSizeBeforeUpdate = dispositivoRepository.findAll().size();

        // Create the Dispositivo
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositivoMockMvc.perform(put("/api/dispositivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispositivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dispositivo in the database
        List<Dispositivo> dispositivoList = dispositivoRepository.findAll();
        assertThat(dispositivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDispositivo() throws Exception {
        // Initialize the database
        dispositivoRepository.saveAndFlush(dispositivo);

        int databaseSizeBeforeDelete = dispositivoRepository.findAll().size();

        // Delete the dispositivo
        restDispositivoMockMvc.perform(delete("/api/dispositivos/{id}", dispositivo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dispositivo> dispositivoList = dispositivoRepository.findAll();
        assertThat(dispositivoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dispositivo.class);
        Dispositivo dispositivo1 = new Dispositivo();
        dispositivo1.setId(1L);
        Dispositivo dispositivo2 = new Dispositivo();
        dispositivo2.setId(dispositivo1.getId());
        assertThat(dispositivo1).isEqualTo(dispositivo2);
        dispositivo2.setId(2L);
        assertThat(dispositivo1).isNotEqualTo(dispositivo2);
        dispositivo1.setId(null);
        assertThat(dispositivo1).isNotEqualTo(dispositivo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositivoDTO.class);
        DispositivoDTO dispositivoDTO1 = new DispositivoDTO();
        dispositivoDTO1.setId(1L);
        DispositivoDTO dispositivoDTO2 = new DispositivoDTO();
        assertThat(dispositivoDTO1).isNotEqualTo(dispositivoDTO2);
        dispositivoDTO2.setId(dispositivoDTO1.getId());
        assertThat(dispositivoDTO1).isEqualTo(dispositivoDTO2);
        dispositivoDTO2.setId(2L);
        assertThat(dispositivoDTO1).isNotEqualTo(dispositivoDTO2);
        dispositivoDTO1.setId(null);
        assertThat(dispositivoDTO1).isNotEqualTo(dispositivoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dispositivoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dispositivoMapper.fromId(null)).isNull();
    }
}
