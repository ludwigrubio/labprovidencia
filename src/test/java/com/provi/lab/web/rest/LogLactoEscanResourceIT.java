package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.LogLactoEscan;
import com.provi.lab.repository.LogLactoEscanRepository;
import com.provi.lab.service.LogLactoEscanService;
import com.provi.lab.service.dto.LogLactoEscanCriteria;
import com.provi.lab.service.LogLactoEscanQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LogLactoEscanResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LogLactoEscanResourceIT {

    private static final Integer DEFAULT_TIPO = 1;
    private static final Integer UPDATED_TIPO = 2;
    private static final Integer SMALLER_TIPO = 1 - 1;

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOMBRE_ARCHIVO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ARCHIVO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_FILA = 1;
    private static final Integer UPDATED_NUMERO_FILA = 2;
    private static final Integer SMALLER_NUMERO_FILA = 1 - 1;

    private static final String DEFAULT_MENSAJE_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_MENSAJE_ERROR = "BBBBBBBBBB";

    @Autowired
    private LogLactoEscanRepository logLactoEscanRepository;

    @Autowired
    private LogLactoEscanService logLactoEscanService;

    @Autowired
    private LogLactoEscanQueryService logLactoEscanQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogLactoEscanMockMvc;

    private LogLactoEscan logLactoEscan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogLactoEscan createEntity(EntityManager em) {
        LogLactoEscan logLactoEscan = new LogLactoEscan()
            .tipo(DEFAULT_TIPO)
            .fecha(DEFAULT_FECHA)
            .nombreArchivo(DEFAULT_NOMBRE_ARCHIVO)
            .numeroFila(DEFAULT_NUMERO_FILA)
            .mensajeError(DEFAULT_MENSAJE_ERROR);
        return logLactoEscan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogLactoEscan createUpdatedEntity(EntityManager em) {
        LogLactoEscan logLactoEscan = new LogLactoEscan()
            .tipo(UPDATED_TIPO)
            .fecha(UPDATED_FECHA)
            .nombreArchivo(UPDATED_NOMBRE_ARCHIVO)
            .numeroFila(UPDATED_NUMERO_FILA)
            .mensajeError(UPDATED_MENSAJE_ERROR);
        return logLactoEscan;
    }

    @BeforeEach
    public void initTest() {
        logLactoEscan = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogLactoEscan() throws Exception {
        int databaseSizeBeforeCreate = logLactoEscanRepository.findAll().size();
        // Create the LogLactoEscan
        restLogLactoEscanMockMvc.perform(post("/api/log-lacto-escans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logLactoEscan)))
            .andExpect(status().isCreated());

        // Validate the LogLactoEscan in the database
        List<LogLactoEscan> logLactoEscanList = logLactoEscanRepository.findAll();
        assertThat(logLactoEscanList).hasSize(databaseSizeBeforeCreate + 1);
        LogLactoEscan testLogLactoEscan = logLactoEscanList.get(logLactoEscanList.size() - 1);
        assertThat(testLogLactoEscan.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testLogLactoEscan.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testLogLactoEscan.getNombreArchivo()).isEqualTo(DEFAULT_NOMBRE_ARCHIVO);
        assertThat(testLogLactoEscan.getNumeroFila()).isEqualTo(DEFAULT_NUMERO_FILA);
        assertThat(testLogLactoEscan.getMensajeError()).isEqualTo(DEFAULT_MENSAJE_ERROR);
    }

    @Test
    @Transactional
    public void createLogLactoEscanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logLactoEscanRepository.findAll().size();

        // Create the LogLactoEscan with an existing ID
        logLactoEscan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogLactoEscanMockMvc.perform(post("/api/log-lacto-escans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logLactoEscan)))
            .andExpect(status().isBadRequest());

        // Validate the LogLactoEscan in the database
        List<LogLactoEscan> logLactoEscanList = logLactoEscanRepository.findAll();
        assertThat(logLactoEscanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = logLactoEscanRepository.findAll().size();
        // set the field null
        logLactoEscan.setTipo(null);

        // Create the LogLactoEscan, which fails.


        restLogLactoEscanMockMvc.perform(post("/api/log-lacto-escans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logLactoEscan)))
            .andExpect(status().isBadRequest());

        List<LogLactoEscan> logLactoEscanList = logLactoEscanRepository.findAll();
        assertThat(logLactoEscanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = logLactoEscanRepository.findAll().size();
        // set the field null
        logLactoEscan.setFecha(null);

        // Create the LogLactoEscan, which fails.


        restLogLactoEscanMockMvc.perform(post("/api/log-lacto-escans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logLactoEscan)))
            .andExpect(status().isBadRequest());

        List<LogLactoEscan> logLactoEscanList = logLactoEscanRepository.findAll();
        assertThat(logLactoEscanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreArchivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = logLactoEscanRepository.findAll().size();
        // set the field null
        logLactoEscan.setNombreArchivo(null);

        // Create the LogLactoEscan, which fails.


        restLogLactoEscanMockMvc.perform(post("/api/log-lacto-escans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logLactoEscan)))
            .andExpect(status().isBadRequest());

        List<LogLactoEscan> logLactoEscanList = logLactoEscanRepository.findAll();
        assertThat(logLactoEscanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMensajeErrorIsRequired() throws Exception {
        int databaseSizeBeforeTest = logLactoEscanRepository.findAll().size();
        // set the field null
        logLactoEscan.setMensajeError(null);

        // Create the LogLactoEscan, which fails.


        restLogLactoEscanMockMvc.perform(post("/api/log-lacto-escans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logLactoEscan)))
            .andExpect(status().isBadRequest());

        List<LogLactoEscan> logLactoEscanList = logLactoEscanRepository.findAll();
        assertThat(logLactoEscanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscans() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList
        restLogLactoEscanMockMvc.perform(get("/api/log-lacto-escans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logLactoEscan.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].nombreArchivo").value(hasItem(DEFAULT_NOMBRE_ARCHIVO)))
            .andExpect(jsonPath("$.[*].numeroFila").value(hasItem(DEFAULT_NUMERO_FILA)))
            .andExpect(jsonPath("$.[*].mensajeError").value(hasItem(DEFAULT_MENSAJE_ERROR)));
    }
    
    @Test
    @Transactional
    public void getLogLactoEscan() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get the logLactoEscan
        restLogLactoEscanMockMvc.perform(get("/api/log-lacto-escans/{id}", logLactoEscan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logLactoEscan.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.nombreArchivo").value(DEFAULT_NOMBRE_ARCHIVO))
            .andExpect(jsonPath("$.numeroFila").value(DEFAULT_NUMERO_FILA))
            .andExpect(jsonPath("$.mensajeError").value(DEFAULT_MENSAJE_ERROR));
    }


    @Test
    @Transactional
    public void getLogLactoEscansByIdFiltering() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        Long id = logLactoEscan.getId();

        defaultLogLactoEscanShouldBeFound("id.equals=" + id);
        defaultLogLactoEscanShouldNotBeFound("id.notEquals=" + id);

        defaultLogLactoEscanShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLogLactoEscanShouldNotBeFound("id.greaterThan=" + id);

        defaultLogLactoEscanShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLogLactoEscanShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLogLactoEscansByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where tipo equals to DEFAULT_TIPO
        defaultLogLactoEscanShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the logLactoEscanList where tipo equals to UPDATED_TIPO
        defaultLogLactoEscanShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where tipo not equals to DEFAULT_TIPO
        defaultLogLactoEscanShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the logLactoEscanList where tipo not equals to UPDATED_TIPO
        defaultLogLactoEscanShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultLogLactoEscanShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the logLactoEscanList where tipo equals to UPDATED_TIPO
        defaultLogLactoEscanShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where tipo is not null
        defaultLogLactoEscanShouldBeFound("tipo.specified=true");

        // Get all the logLactoEscanList where tipo is null
        defaultLogLactoEscanShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByTipoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where tipo is greater than or equal to DEFAULT_TIPO
        defaultLogLactoEscanShouldBeFound("tipo.greaterThanOrEqual=" + DEFAULT_TIPO);

        // Get all the logLactoEscanList where tipo is greater than or equal to UPDATED_TIPO
        defaultLogLactoEscanShouldNotBeFound("tipo.greaterThanOrEqual=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByTipoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where tipo is less than or equal to DEFAULT_TIPO
        defaultLogLactoEscanShouldBeFound("tipo.lessThanOrEqual=" + DEFAULT_TIPO);

        // Get all the logLactoEscanList where tipo is less than or equal to SMALLER_TIPO
        defaultLogLactoEscanShouldNotBeFound("tipo.lessThanOrEqual=" + SMALLER_TIPO);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByTipoIsLessThanSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where tipo is less than DEFAULT_TIPO
        defaultLogLactoEscanShouldNotBeFound("tipo.lessThan=" + DEFAULT_TIPO);

        // Get all the logLactoEscanList where tipo is less than UPDATED_TIPO
        defaultLogLactoEscanShouldBeFound("tipo.lessThan=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByTipoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where tipo is greater than DEFAULT_TIPO
        defaultLogLactoEscanShouldNotBeFound("tipo.greaterThan=" + DEFAULT_TIPO);

        // Get all the logLactoEscanList where tipo is greater than SMALLER_TIPO
        defaultLogLactoEscanShouldBeFound("tipo.greaterThan=" + SMALLER_TIPO);
    }


    @Test
    @Transactional
    public void getAllLogLactoEscansByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where fecha equals to DEFAULT_FECHA
        defaultLogLactoEscanShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the logLactoEscanList where fecha equals to UPDATED_FECHA
        defaultLogLactoEscanShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where fecha not equals to DEFAULT_FECHA
        defaultLogLactoEscanShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the logLactoEscanList where fecha not equals to UPDATED_FECHA
        defaultLogLactoEscanShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultLogLactoEscanShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the logLactoEscanList where fecha equals to UPDATED_FECHA
        defaultLogLactoEscanShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where fecha is not null
        defaultLogLactoEscanShouldBeFound("fecha.specified=true");

        // Get all the logLactoEscanList where fecha is null
        defaultLogLactoEscanShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNombreArchivoIsEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where nombreArchivo equals to DEFAULT_NOMBRE_ARCHIVO
        defaultLogLactoEscanShouldBeFound("nombreArchivo.equals=" + DEFAULT_NOMBRE_ARCHIVO);

        // Get all the logLactoEscanList where nombreArchivo equals to UPDATED_NOMBRE_ARCHIVO
        defaultLogLactoEscanShouldNotBeFound("nombreArchivo.equals=" + UPDATED_NOMBRE_ARCHIVO);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNombreArchivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where nombreArchivo not equals to DEFAULT_NOMBRE_ARCHIVO
        defaultLogLactoEscanShouldNotBeFound("nombreArchivo.notEquals=" + DEFAULT_NOMBRE_ARCHIVO);

        // Get all the logLactoEscanList where nombreArchivo not equals to UPDATED_NOMBRE_ARCHIVO
        defaultLogLactoEscanShouldBeFound("nombreArchivo.notEquals=" + UPDATED_NOMBRE_ARCHIVO);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNombreArchivoIsInShouldWork() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where nombreArchivo in DEFAULT_NOMBRE_ARCHIVO or UPDATED_NOMBRE_ARCHIVO
        defaultLogLactoEscanShouldBeFound("nombreArchivo.in=" + DEFAULT_NOMBRE_ARCHIVO + "," + UPDATED_NOMBRE_ARCHIVO);

        // Get all the logLactoEscanList where nombreArchivo equals to UPDATED_NOMBRE_ARCHIVO
        defaultLogLactoEscanShouldNotBeFound("nombreArchivo.in=" + UPDATED_NOMBRE_ARCHIVO);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNombreArchivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where nombreArchivo is not null
        defaultLogLactoEscanShouldBeFound("nombreArchivo.specified=true");

        // Get all the logLactoEscanList where nombreArchivo is null
        defaultLogLactoEscanShouldNotBeFound("nombreArchivo.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogLactoEscansByNombreArchivoContainsSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where nombreArchivo contains DEFAULT_NOMBRE_ARCHIVO
        defaultLogLactoEscanShouldBeFound("nombreArchivo.contains=" + DEFAULT_NOMBRE_ARCHIVO);

        // Get all the logLactoEscanList where nombreArchivo contains UPDATED_NOMBRE_ARCHIVO
        defaultLogLactoEscanShouldNotBeFound("nombreArchivo.contains=" + UPDATED_NOMBRE_ARCHIVO);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNombreArchivoNotContainsSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where nombreArchivo does not contain DEFAULT_NOMBRE_ARCHIVO
        defaultLogLactoEscanShouldNotBeFound("nombreArchivo.doesNotContain=" + DEFAULT_NOMBRE_ARCHIVO);

        // Get all the logLactoEscanList where nombreArchivo does not contain UPDATED_NOMBRE_ARCHIVO
        defaultLogLactoEscanShouldBeFound("nombreArchivo.doesNotContain=" + UPDATED_NOMBRE_ARCHIVO);
    }


    @Test
    @Transactional
    public void getAllLogLactoEscansByNumeroFilaIsEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where numeroFila equals to DEFAULT_NUMERO_FILA
        defaultLogLactoEscanShouldBeFound("numeroFila.equals=" + DEFAULT_NUMERO_FILA);

        // Get all the logLactoEscanList where numeroFila equals to UPDATED_NUMERO_FILA
        defaultLogLactoEscanShouldNotBeFound("numeroFila.equals=" + UPDATED_NUMERO_FILA);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNumeroFilaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where numeroFila not equals to DEFAULT_NUMERO_FILA
        defaultLogLactoEscanShouldNotBeFound("numeroFila.notEquals=" + DEFAULT_NUMERO_FILA);

        // Get all the logLactoEscanList where numeroFila not equals to UPDATED_NUMERO_FILA
        defaultLogLactoEscanShouldBeFound("numeroFila.notEquals=" + UPDATED_NUMERO_FILA);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNumeroFilaIsInShouldWork() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where numeroFila in DEFAULT_NUMERO_FILA or UPDATED_NUMERO_FILA
        defaultLogLactoEscanShouldBeFound("numeroFila.in=" + DEFAULT_NUMERO_FILA + "," + UPDATED_NUMERO_FILA);

        // Get all the logLactoEscanList where numeroFila equals to UPDATED_NUMERO_FILA
        defaultLogLactoEscanShouldNotBeFound("numeroFila.in=" + UPDATED_NUMERO_FILA);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNumeroFilaIsNullOrNotNull() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where numeroFila is not null
        defaultLogLactoEscanShouldBeFound("numeroFila.specified=true");

        // Get all the logLactoEscanList where numeroFila is null
        defaultLogLactoEscanShouldNotBeFound("numeroFila.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNumeroFilaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where numeroFila is greater than or equal to DEFAULT_NUMERO_FILA
        defaultLogLactoEscanShouldBeFound("numeroFila.greaterThanOrEqual=" + DEFAULT_NUMERO_FILA);

        // Get all the logLactoEscanList where numeroFila is greater than or equal to UPDATED_NUMERO_FILA
        defaultLogLactoEscanShouldNotBeFound("numeroFila.greaterThanOrEqual=" + UPDATED_NUMERO_FILA);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNumeroFilaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where numeroFila is less than or equal to DEFAULT_NUMERO_FILA
        defaultLogLactoEscanShouldBeFound("numeroFila.lessThanOrEqual=" + DEFAULT_NUMERO_FILA);

        // Get all the logLactoEscanList where numeroFila is less than or equal to SMALLER_NUMERO_FILA
        defaultLogLactoEscanShouldNotBeFound("numeroFila.lessThanOrEqual=" + SMALLER_NUMERO_FILA);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNumeroFilaIsLessThanSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where numeroFila is less than DEFAULT_NUMERO_FILA
        defaultLogLactoEscanShouldNotBeFound("numeroFila.lessThan=" + DEFAULT_NUMERO_FILA);

        // Get all the logLactoEscanList where numeroFila is less than UPDATED_NUMERO_FILA
        defaultLogLactoEscanShouldBeFound("numeroFila.lessThan=" + UPDATED_NUMERO_FILA);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByNumeroFilaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where numeroFila is greater than DEFAULT_NUMERO_FILA
        defaultLogLactoEscanShouldNotBeFound("numeroFila.greaterThan=" + DEFAULT_NUMERO_FILA);

        // Get all the logLactoEscanList where numeroFila is greater than SMALLER_NUMERO_FILA
        defaultLogLactoEscanShouldBeFound("numeroFila.greaterThan=" + SMALLER_NUMERO_FILA);
    }


    @Test
    @Transactional
    public void getAllLogLactoEscansByMensajeErrorIsEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where mensajeError equals to DEFAULT_MENSAJE_ERROR
        defaultLogLactoEscanShouldBeFound("mensajeError.equals=" + DEFAULT_MENSAJE_ERROR);

        // Get all the logLactoEscanList where mensajeError equals to UPDATED_MENSAJE_ERROR
        defaultLogLactoEscanShouldNotBeFound("mensajeError.equals=" + UPDATED_MENSAJE_ERROR);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByMensajeErrorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where mensajeError not equals to DEFAULT_MENSAJE_ERROR
        defaultLogLactoEscanShouldNotBeFound("mensajeError.notEquals=" + DEFAULT_MENSAJE_ERROR);

        // Get all the logLactoEscanList where mensajeError not equals to UPDATED_MENSAJE_ERROR
        defaultLogLactoEscanShouldBeFound("mensajeError.notEquals=" + UPDATED_MENSAJE_ERROR);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByMensajeErrorIsInShouldWork() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where mensajeError in DEFAULT_MENSAJE_ERROR or UPDATED_MENSAJE_ERROR
        defaultLogLactoEscanShouldBeFound("mensajeError.in=" + DEFAULT_MENSAJE_ERROR + "," + UPDATED_MENSAJE_ERROR);

        // Get all the logLactoEscanList where mensajeError equals to UPDATED_MENSAJE_ERROR
        defaultLogLactoEscanShouldNotBeFound("mensajeError.in=" + UPDATED_MENSAJE_ERROR);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByMensajeErrorIsNullOrNotNull() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where mensajeError is not null
        defaultLogLactoEscanShouldBeFound("mensajeError.specified=true");

        // Get all the logLactoEscanList where mensajeError is null
        defaultLogLactoEscanShouldNotBeFound("mensajeError.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogLactoEscansByMensajeErrorContainsSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where mensajeError contains DEFAULT_MENSAJE_ERROR
        defaultLogLactoEscanShouldBeFound("mensajeError.contains=" + DEFAULT_MENSAJE_ERROR);

        // Get all the logLactoEscanList where mensajeError contains UPDATED_MENSAJE_ERROR
        defaultLogLactoEscanShouldNotBeFound("mensajeError.contains=" + UPDATED_MENSAJE_ERROR);
    }

    @Test
    @Transactional
    public void getAllLogLactoEscansByMensajeErrorNotContainsSomething() throws Exception {
        // Initialize the database
        logLactoEscanRepository.saveAndFlush(logLactoEscan);

        // Get all the logLactoEscanList where mensajeError does not contain DEFAULT_MENSAJE_ERROR
        defaultLogLactoEscanShouldNotBeFound("mensajeError.doesNotContain=" + DEFAULT_MENSAJE_ERROR);

        // Get all the logLactoEscanList where mensajeError does not contain UPDATED_MENSAJE_ERROR
        defaultLogLactoEscanShouldBeFound("mensajeError.doesNotContain=" + UPDATED_MENSAJE_ERROR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLogLactoEscanShouldBeFound(String filter) throws Exception {
        restLogLactoEscanMockMvc.perform(get("/api/log-lacto-escans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logLactoEscan.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].nombreArchivo").value(hasItem(DEFAULT_NOMBRE_ARCHIVO)))
            .andExpect(jsonPath("$.[*].numeroFila").value(hasItem(DEFAULT_NUMERO_FILA)))
            .andExpect(jsonPath("$.[*].mensajeError").value(hasItem(DEFAULT_MENSAJE_ERROR)));

        // Check, that the count call also returns 1
        restLogLactoEscanMockMvc.perform(get("/api/log-lacto-escans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLogLactoEscanShouldNotBeFound(String filter) throws Exception {
        restLogLactoEscanMockMvc.perform(get("/api/log-lacto-escans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLogLactoEscanMockMvc.perform(get("/api/log-lacto-escans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLogLactoEscan() throws Exception {
        // Get the logLactoEscan
        restLogLactoEscanMockMvc.perform(get("/api/log-lacto-escans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogLactoEscan() throws Exception {
        // Initialize the database
        logLactoEscanService.save(logLactoEscan);

        int databaseSizeBeforeUpdate = logLactoEscanRepository.findAll().size();

        // Update the logLactoEscan
        LogLactoEscan updatedLogLactoEscan = logLactoEscanRepository.findById(logLactoEscan.getId()).get();
        // Disconnect from session so that the updates on updatedLogLactoEscan are not directly saved in db
        em.detach(updatedLogLactoEscan);
        updatedLogLactoEscan
            .tipo(UPDATED_TIPO)
            .fecha(UPDATED_FECHA)
            .nombreArchivo(UPDATED_NOMBRE_ARCHIVO)
            .numeroFila(UPDATED_NUMERO_FILA)
            .mensajeError(UPDATED_MENSAJE_ERROR);

        restLogLactoEscanMockMvc.perform(put("/api/log-lacto-escans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogLactoEscan)))
            .andExpect(status().isOk());

        // Validate the LogLactoEscan in the database
        List<LogLactoEscan> logLactoEscanList = logLactoEscanRepository.findAll();
        assertThat(logLactoEscanList).hasSize(databaseSizeBeforeUpdate);
        LogLactoEscan testLogLactoEscan = logLactoEscanList.get(logLactoEscanList.size() - 1);
        assertThat(testLogLactoEscan.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testLogLactoEscan.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testLogLactoEscan.getNombreArchivo()).isEqualTo(UPDATED_NOMBRE_ARCHIVO);
        assertThat(testLogLactoEscan.getNumeroFila()).isEqualTo(UPDATED_NUMERO_FILA);
        assertThat(testLogLactoEscan.getMensajeError()).isEqualTo(UPDATED_MENSAJE_ERROR);
    }

    @Test
    @Transactional
    public void updateNonExistingLogLactoEscan() throws Exception {
        int databaseSizeBeforeUpdate = logLactoEscanRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogLactoEscanMockMvc.perform(put("/api/log-lacto-escans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logLactoEscan)))
            .andExpect(status().isBadRequest());

        // Validate the LogLactoEscan in the database
        List<LogLactoEscan> logLactoEscanList = logLactoEscanRepository.findAll();
        assertThat(logLactoEscanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLogLactoEscan() throws Exception {
        // Initialize the database
        logLactoEscanService.save(logLactoEscan);

        int databaseSizeBeforeDelete = logLactoEscanRepository.findAll().size();

        // Delete the logLactoEscan
        restLogLactoEscanMockMvc.perform(delete("/api/log-lacto-escans/{id}", logLactoEscan.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogLactoEscan> logLactoEscanList = logLactoEscanRepository.findAll();
        assertThat(logLactoEscanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
