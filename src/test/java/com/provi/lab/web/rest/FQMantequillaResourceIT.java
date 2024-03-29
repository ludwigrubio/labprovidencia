package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.FQMantequilla;
import com.provi.lab.domain.Area;
import com.provi.lab.domain.Producto;
import com.provi.lab.domain.UserExtra;
import com.provi.lab.domain.Personal;
import com.provi.lab.domain.Contenedor;
import com.provi.lab.domain.Proceso;
import com.provi.lab.repository.FQMantequillaRepository;
import com.provi.lab.service.FQMantequillaService;
import com.provi.lab.service.dto.FQMantequillaCriteria;
import com.provi.lab.service.FQMantequillaQueryService;

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
 * Integration tests for the {@link FQMantequillaResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FQMantequillaResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_LOTE = "BBBBBBBBBB";

    private static final Double DEFAULT_PH = 1D;
    private static final Double UPDATED_PH = 2D;
    private static final Double SMALLER_PH = 1D - 1D;

    private static final Double DEFAULT_HUMEDAD = 1D;
    private static final Double UPDATED_HUMEDAD = 2D;
    private static final Double SMALLER_HUMEDAD = 1D - 1D;

    private static final Double DEFAULT_DUMMY_1 = 1D;
    private static final Double UPDATED_DUMMY_1 = 2D;
    private static final Double SMALLER_DUMMY_1 = 1D - 1D;

    private static final Double DEFAULT_DUMMY_2 = 1D;
    private static final Double UPDATED_DUMMY_2 = 2D;
    private static final Double SMALLER_DUMMY_2 = 1D - 1D;

    private static final Double DEFAULT_DUMMY_3 = 1D;
    private static final Double UPDATED_DUMMY_3 = 2D;
    private static final Double SMALLER_DUMMY_3 = 1D - 1D;

    private static final Double DEFAULT_DUMMY_4 = 1D;
    private static final Double UPDATED_DUMMY_4 = 2D;
    private static final Double SMALLER_DUMMY_4 = 1D - 1D;

    private static final Double DEFAULT_DUMMY_5 = 1D;
    private static final Double UPDATED_DUMMY_5 = 2D;
    private static final Double SMALLER_DUMMY_5 = 1D - 1D;

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    @Autowired
    private FQMantequillaRepository fQMantequillaRepository;

    @Autowired
    private FQMantequillaService fQMantequillaService;

    @Autowired
    private FQMantequillaQueryService fQMantequillaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFQMantequillaMockMvc;

    private FQMantequilla fQMantequilla;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FQMantequilla createEntity(EntityManager em) {
        FQMantequilla fQMantequilla = new FQMantequilla()
            .fecha(DEFAULT_FECHA)
            .lote(DEFAULT_LOTE)
            .ph(DEFAULT_PH)
            .humedad(DEFAULT_HUMEDAD)
            .dummy1(DEFAULT_DUMMY_1)
            .dummy2(DEFAULT_DUMMY_2)
            .dummy3(DEFAULT_DUMMY_3)
            .dummy4(DEFAULT_DUMMY_4)
            .dummy5(DEFAULT_DUMMY_5)
            .observaciones(DEFAULT_OBSERVACIONES);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        fQMantequilla.setArea(area);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        fQMantequilla.setProducto(producto);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        fQMantequilla.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        fQMantequilla.setProveedor(personal);
        return fQMantequilla;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FQMantequilla createUpdatedEntity(EntityManager em) {
        FQMantequilla fQMantequilla = new FQMantequilla()
            .fecha(UPDATED_FECHA)
            .lote(UPDATED_LOTE)
            .ph(UPDATED_PH)
            .humedad(UPDATED_HUMEDAD)
            .dummy1(UPDATED_DUMMY_1)
            .dummy2(UPDATED_DUMMY_2)
            .dummy3(UPDATED_DUMMY_3)
            .dummy4(UPDATED_DUMMY_4)
            .dummy5(UPDATED_DUMMY_5)
            .observaciones(UPDATED_OBSERVACIONES);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createUpdatedEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        fQMantequilla.setArea(area);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        fQMantequilla.setProducto(producto);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createUpdatedEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        fQMantequilla.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createUpdatedEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        fQMantequilla.setProveedor(personal);
        return fQMantequilla;
    }

    @BeforeEach
    public void initTest() {
        fQMantequilla = createEntity(em);
    }

    @Test
    @Transactional
    public void createFQMantequilla() throws Exception {
        int databaseSizeBeforeCreate = fQMantequillaRepository.findAll().size();
        // Create the FQMantequilla
        restFQMantequillaMockMvc.perform(post("/api/fq-mantequillas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQMantequilla)))
            .andExpect(status().isCreated());

        // Validate the FQMantequilla in the database
        List<FQMantequilla> fQMantequillaList = fQMantequillaRepository.findAll();
        assertThat(fQMantequillaList).hasSize(databaseSizeBeforeCreate + 1);
        FQMantequilla testFQMantequilla = fQMantequillaList.get(fQMantequillaList.size() - 1);
        assertThat(testFQMantequilla.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testFQMantequilla.getLote()).isEqualTo(DEFAULT_LOTE);
        assertThat(testFQMantequilla.getPh()).isEqualTo(DEFAULT_PH);
        assertThat(testFQMantequilla.getHumedad()).isEqualTo(DEFAULT_HUMEDAD);
        assertThat(testFQMantequilla.getDummy1()).isEqualTo(DEFAULT_DUMMY_1);
        assertThat(testFQMantequilla.getDummy2()).isEqualTo(DEFAULT_DUMMY_2);
        assertThat(testFQMantequilla.getDummy3()).isEqualTo(DEFAULT_DUMMY_3);
        assertThat(testFQMantequilla.getDummy4()).isEqualTo(DEFAULT_DUMMY_4);
        assertThat(testFQMantequilla.getDummy5()).isEqualTo(DEFAULT_DUMMY_5);
        assertThat(testFQMantequilla.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void createFQMantequillaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fQMantequillaRepository.findAll().size();

        // Create the FQMantequilla with an existing ID
        fQMantequilla.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFQMantequillaMockMvc.perform(post("/api/fq-mantequillas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQMantequilla)))
            .andExpect(status().isBadRequest());

        // Validate the FQMantequilla in the database
        List<FQMantequilla> fQMantequillaList = fQMantequillaRepository.findAll();
        assertThat(fQMantequillaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = fQMantequillaRepository.findAll().size();
        // set the field null
        fQMantequilla.setLote(null);

        // Create the FQMantequilla, which fails.


        restFQMantequillaMockMvc.perform(post("/api/fq-mantequillas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQMantequilla)))
            .andExpect(status().isBadRequest());

        List<FQMantequilla> fQMantequillaList = fQMantequillaRepository.findAll();
        assertThat(fQMantequillaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFQMantequillas() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList
        restFQMantequillaMockMvc.perform(get("/api/fq-mantequillas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fQMantequilla.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.doubleValue())))
            .andExpect(jsonPath("$.[*].humedad").value(hasItem(DEFAULT_HUMEDAD.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy1").value(hasItem(DEFAULT_DUMMY_1.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy2").value(hasItem(DEFAULT_DUMMY_2.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy3").value(hasItem(DEFAULT_DUMMY_3.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy4").value(hasItem(DEFAULT_DUMMY_4.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy5").value(hasItem(DEFAULT_DUMMY_5.doubleValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }
    
    @Test
    @Transactional
    public void getFQMantequilla() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get the fQMantequilla
        restFQMantequillaMockMvc.perform(get("/api/fq-mantequillas/{id}", fQMantequilla.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fQMantequilla.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.lote").value(DEFAULT_LOTE))
            .andExpect(jsonPath("$.ph").value(DEFAULT_PH.doubleValue()))
            .andExpect(jsonPath("$.humedad").value(DEFAULT_HUMEDAD.doubleValue()))
            .andExpect(jsonPath("$.dummy1").value(DEFAULT_DUMMY_1.doubleValue()))
            .andExpect(jsonPath("$.dummy2").value(DEFAULT_DUMMY_2.doubleValue()))
            .andExpect(jsonPath("$.dummy3").value(DEFAULT_DUMMY_3.doubleValue()))
            .andExpect(jsonPath("$.dummy4").value(DEFAULT_DUMMY_4.doubleValue()))
            .andExpect(jsonPath("$.dummy5").value(DEFAULT_DUMMY_5.doubleValue()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }


    @Test
    @Transactional
    public void getFQMantequillasByIdFiltering() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        Long id = fQMantequilla.getId();

        defaultFQMantequillaShouldBeFound("id.equals=" + id);
        defaultFQMantequillaShouldNotBeFound("id.notEquals=" + id);

        defaultFQMantequillaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFQMantequillaShouldNotBeFound("id.greaterThan=" + id);

        defaultFQMantequillaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFQMantequillaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where fecha equals to DEFAULT_FECHA
        defaultFQMantequillaShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the fQMantequillaList where fecha equals to UPDATED_FECHA
        defaultFQMantequillaShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where fecha not equals to DEFAULT_FECHA
        defaultFQMantequillaShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the fQMantequillaList where fecha not equals to UPDATED_FECHA
        defaultFQMantequillaShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultFQMantequillaShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the fQMantequillaList where fecha equals to UPDATED_FECHA
        defaultFQMantequillaShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where fecha is not null
        defaultFQMantequillaShouldBeFound("fecha.specified=true");

        // Get all the fQMantequillaList where fecha is null
        defaultFQMantequillaShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByLoteIsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where lote equals to DEFAULT_LOTE
        defaultFQMantequillaShouldBeFound("lote.equals=" + DEFAULT_LOTE);

        // Get all the fQMantequillaList where lote equals to UPDATED_LOTE
        defaultFQMantequillaShouldNotBeFound("lote.equals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByLoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where lote not equals to DEFAULT_LOTE
        defaultFQMantequillaShouldNotBeFound("lote.notEquals=" + DEFAULT_LOTE);

        // Get all the fQMantequillaList where lote not equals to UPDATED_LOTE
        defaultFQMantequillaShouldBeFound("lote.notEquals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByLoteIsInShouldWork() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where lote in DEFAULT_LOTE or UPDATED_LOTE
        defaultFQMantequillaShouldBeFound("lote.in=" + DEFAULT_LOTE + "," + UPDATED_LOTE);

        // Get all the fQMantequillaList where lote equals to UPDATED_LOTE
        defaultFQMantequillaShouldNotBeFound("lote.in=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByLoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where lote is not null
        defaultFQMantequillaShouldBeFound("lote.specified=true");

        // Get all the fQMantequillaList where lote is null
        defaultFQMantequillaShouldNotBeFound("lote.specified=false");
    }
                @Test
    @Transactional
    public void getAllFQMantequillasByLoteContainsSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where lote contains DEFAULT_LOTE
        defaultFQMantequillaShouldBeFound("lote.contains=" + DEFAULT_LOTE);

        // Get all the fQMantequillaList where lote contains UPDATED_LOTE
        defaultFQMantequillaShouldNotBeFound("lote.contains=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByLoteNotContainsSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where lote does not contain DEFAULT_LOTE
        defaultFQMantequillaShouldNotBeFound("lote.doesNotContain=" + DEFAULT_LOTE);

        // Get all the fQMantequillaList where lote does not contain UPDATED_LOTE
        defaultFQMantequillaShouldBeFound("lote.doesNotContain=" + UPDATED_LOTE);
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByPhIsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where ph equals to DEFAULT_PH
        defaultFQMantequillaShouldBeFound("ph.equals=" + DEFAULT_PH);

        // Get all the fQMantequillaList where ph equals to UPDATED_PH
        defaultFQMantequillaShouldNotBeFound("ph.equals=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByPhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where ph not equals to DEFAULT_PH
        defaultFQMantequillaShouldNotBeFound("ph.notEquals=" + DEFAULT_PH);

        // Get all the fQMantequillaList where ph not equals to UPDATED_PH
        defaultFQMantequillaShouldBeFound("ph.notEquals=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByPhIsInShouldWork() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where ph in DEFAULT_PH or UPDATED_PH
        defaultFQMantequillaShouldBeFound("ph.in=" + DEFAULT_PH + "," + UPDATED_PH);

        // Get all the fQMantequillaList where ph equals to UPDATED_PH
        defaultFQMantequillaShouldNotBeFound("ph.in=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByPhIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where ph is not null
        defaultFQMantequillaShouldBeFound("ph.specified=true");

        // Get all the fQMantequillaList where ph is null
        defaultFQMantequillaShouldNotBeFound("ph.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByPhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where ph is greater than or equal to DEFAULT_PH
        defaultFQMantequillaShouldBeFound("ph.greaterThanOrEqual=" + DEFAULT_PH);

        // Get all the fQMantequillaList where ph is greater than or equal to UPDATED_PH
        defaultFQMantequillaShouldNotBeFound("ph.greaterThanOrEqual=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByPhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where ph is less than or equal to DEFAULT_PH
        defaultFQMantequillaShouldBeFound("ph.lessThanOrEqual=" + DEFAULT_PH);

        // Get all the fQMantequillaList where ph is less than or equal to SMALLER_PH
        defaultFQMantequillaShouldNotBeFound("ph.lessThanOrEqual=" + SMALLER_PH);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByPhIsLessThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where ph is less than DEFAULT_PH
        defaultFQMantequillaShouldNotBeFound("ph.lessThan=" + DEFAULT_PH);

        // Get all the fQMantequillaList where ph is less than UPDATED_PH
        defaultFQMantequillaShouldBeFound("ph.lessThan=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByPhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where ph is greater than DEFAULT_PH
        defaultFQMantequillaShouldNotBeFound("ph.greaterThan=" + DEFAULT_PH);

        // Get all the fQMantequillaList where ph is greater than SMALLER_PH
        defaultFQMantequillaShouldBeFound("ph.greaterThan=" + SMALLER_PH);
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByHumedadIsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where humedad equals to DEFAULT_HUMEDAD
        defaultFQMantequillaShouldBeFound("humedad.equals=" + DEFAULT_HUMEDAD);

        // Get all the fQMantequillaList where humedad equals to UPDATED_HUMEDAD
        defaultFQMantequillaShouldNotBeFound("humedad.equals=" + UPDATED_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByHumedadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where humedad not equals to DEFAULT_HUMEDAD
        defaultFQMantequillaShouldNotBeFound("humedad.notEquals=" + DEFAULT_HUMEDAD);

        // Get all the fQMantequillaList where humedad not equals to UPDATED_HUMEDAD
        defaultFQMantequillaShouldBeFound("humedad.notEquals=" + UPDATED_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByHumedadIsInShouldWork() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where humedad in DEFAULT_HUMEDAD or UPDATED_HUMEDAD
        defaultFQMantequillaShouldBeFound("humedad.in=" + DEFAULT_HUMEDAD + "," + UPDATED_HUMEDAD);

        // Get all the fQMantequillaList where humedad equals to UPDATED_HUMEDAD
        defaultFQMantequillaShouldNotBeFound("humedad.in=" + UPDATED_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByHumedadIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where humedad is not null
        defaultFQMantequillaShouldBeFound("humedad.specified=true");

        // Get all the fQMantequillaList where humedad is null
        defaultFQMantequillaShouldNotBeFound("humedad.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByHumedadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where humedad is greater than or equal to DEFAULT_HUMEDAD
        defaultFQMantequillaShouldBeFound("humedad.greaterThanOrEqual=" + DEFAULT_HUMEDAD);

        // Get all the fQMantequillaList where humedad is greater than or equal to UPDATED_HUMEDAD
        defaultFQMantequillaShouldNotBeFound("humedad.greaterThanOrEqual=" + UPDATED_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByHumedadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where humedad is less than or equal to DEFAULT_HUMEDAD
        defaultFQMantequillaShouldBeFound("humedad.lessThanOrEqual=" + DEFAULT_HUMEDAD);

        // Get all the fQMantequillaList where humedad is less than or equal to SMALLER_HUMEDAD
        defaultFQMantequillaShouldNotBeFound("humedad.lessThanOrEqual=" + SMALLER_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByHumedadIsLessThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where humedad is less than DEFAULT_HUMEDAD
        defaultFQMantequillaShouldNotBeFound("humedad.lessThan=" + DEFAULT_HUMEDAD);

        // Get all the fQMantequillaList where humedad is less than UPDATED_HUMEDAD
        defaultFQMantequillaShouldBeFound("humedad.lessThan=" + UPDATED_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByHumedadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where humedad is greater than DEFAULT_HUMEDAD
        defaultFQMantequillaShouldNotBeFound("humedad.greaterThan=" + DEFAULT_HUMEDAD);

        // Get all the fQMantequillaList where humedad is greater than SMALLER_HUMEDAD
        defaultFQMantequillaShouldBeFound("humedad.greaterThan=" + SMALLER_HUMEDAD);
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByDummy1IsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy1 equals to DEFAULT_DUMMY_1
        defaultFQMantequillaShouldBeFound("dummy1.equals=" + DEFAULT_DUMMY_1);

        // Get all the fQMantequillaList where dummy1 equals to UPDATED_DUMMY_1
        defaultFQMantequillaShouldNotBeFound("dummy1.equals=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy1 not equals to DEFAULT_DUMMY_1
        defaultFQMantequillaShouldNotBeFound("dummy1.notEquals=" + DEFAULT_DUMMY_1);

        // Get all the fQMantequillaList where dummy1 not equals to UPDATED_DUMMY_1
        defaultFQMantequillaShouldBeFound("dummy1.notEquals=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy1IsInShouldWork() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy1 in DEFAULT_DUMMY_1 or UPDATED_DUMMY_1
        defaultFQMantequillaShouldBeFound("dummy1.in=" + DEFAULT_DUMMY_1 + "," + UPDATED_DUMMY_1);

        // Get all the fQMantequillaList where dummy1 equals to UPDATED_DUMMY_1
        defaultFQMantequillaShouldNotBeFound("dummy1.in=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy1IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy1 is not null
        defaultFQMantequillaShouldBeFound("dummy1.specified=true");

        // Get all the fQMantequillaList where dummy1 is null
        defaultFQMantequillaShouldNotBeFound("dummy1.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy1 is greater than or equal to DEFAULT_DUMMY_1
        defaultFQMantequillaShouldBeFound("dummy1.greaterThanOrEqual=" + DEFAULT_DUMMY_1);

        // Get all the fQMantequillaList where dummy1 is greater than or equal to UPDATED_DUMMY_1
        defaultFQMantequillaShouldNotBeFound("dummy1.greaterThanOrEqual=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy1 is less than or equal to DEFAULT_DUMMY_1
        defaultFQMantequillaShouldBeFound("dummy1.lessThanOrEqual=" + DEFAULT_DUMMY_1);

        // Get all the fQMantequillaList where dummy1 is less than or equal to SMALLER_DUMMY_1
        defaultFQMantequillaShouldNotBeFound("dummy1.lessThanOrEqual=" + SMALLER_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy1IsLessThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy1 is less than DEFAULT_DUMMY_1
        defaultFQMantequillaShouldNotBeFound("dummy1.lessThan=" + DEFAULT_DUMMY_1);

        // Get all the fQMantequillaList where dummy1 is less than UPDATED_DUMMY_1
        defaultFQMantequillaShouldBeFound("dummy1.lessThan=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy1 is greater than DEFAULT_DUMMY_1
        defaultFQMantequillaShouldNotBeFound("dummy1.greaterThan=" + DEFAULT_DUMMY_1);

        // Get all the fQMantequillaList where dummy1 is greater than SMALLER_DUMMY_1
        defaultFQMantequillaShouldBeFound("dummy1.greaterThan=" + SMALLER_DUMMY_1);
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByDummy2IsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy2 equals to DEFAULT_DUMMY_2
        defaultFQMantequillaShouldBeFound("dummy2.equals=" + DEFAULT_DUMMY_2);

        // Get all the fQMantequillaList where dummy2 equals to UPDATED_DUMMY_2
        defaultFQMantequillaShouldNotBeFound("dummy2.equals=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy2 not equals to DEFAULT_DUMMY_2
        defaultFQMantequillaShouldNotBeFound("dummy2.notEquals=" + DEFAULT_DUMMY_2);

        // Get all the fQMantequillaList where dummy2 not equals to UPDATED_DUMMY_2
        defaultFQMantequillaShouldBeFound("dummy2.notEquals=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy2IsInShouldWork() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy2 in DEFAULT_DUMMY_2 or UPDATED_DUMMY_2
        defaultFQMantequillaShouldBeFound("dummy2.in=" + DEFAULT_DUMMY_2 + "," + UPDATED_DUMMY_2);

        // Get all the fQMantequillaList where dummy2 equals to UPDATED_DUMMY_2
        defaultFQMantequillaShouldNotBeFound("dummy2.in=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy2IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy2 is not null
        defaultFQMantequillaShouldBeFound("dummy2.specified=true");

        // Get all the fQMantequillaList where dummy2 is null
        defaultFQMantequillaShouldNotBeFound("dummy2.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy2 is greater than or equal to DEFAULT_DUMMY_2
        defaultFQMantequillaShouldBeFound("dummy2.greaterThanOrEqual=" + DEFAULT_DUMMY_2);

        // Get all the fQMantequillaList where dummy2 is greater than or equal to UPDATED_DUMMY_2
        defaultFQMantequillaShouldNotBeFound("dummy2.greaterThanOrEqual=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy2 is less than or equal to DEFAULT_DUMMY_2
        defaultFQMantequillaShouldBeFound("dummy2.lessThanOrEqual=" + DEFAULT_DUMMY_2);

        // Get all the fQMantequillaList where dummy2 is less than or equal to SMALLER_DUMMY_2
        defaultFQMantequillaShouldNotBeFound("dummy2.lessThanOrEqual=" + SMALLER_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy2IsLessThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy2 is less than DEFAULT_DUMMY_2
        defaultFQMantequillaShouldNotBeFound("dummy2.lessThan=" + DEFAULT_DUMMY_2);

        // Get all the fQMantequillaList where dummy2 is less than UPDATED_DUMMY_2
        defaultFQMantequillaShouldBeFound("dummy2.lessThan=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy2 is greater than DEFAULT_DUMMY_2
        defaultFQMantequillaShouldNotBeFound("dummy2.greaterThan=" + DEFAULT_DUMMY_2);

        // Get all the fQMantequillaList where dummy2 is greater than SMALLER_DUMMY_2
        defaultFQMantequillaShouldBeFound("dummy2.greaterThan=" + SMALLER_DUMMY_2);
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByDummy3IsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy3 equals to DEFAULT_DUMMY_3
        defaultFQMantequillaShouldBeFound("dummy3.equals=" + DEFAULT_DUMMY_3);

        // Get all the fQMantequillaList where dummy3 equals to UPDATED_DUMMY_3
        defaultFQMantequillaShouldNotBeFound("dummy3.equals=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy3 not equals to DEFAULT_DUMMY_3
        defaultFQMantequillaShouldNotBeFound("dummy3.notEquals=" + DEFAULT_DUMMY_3);

        // Get all the fQMantequillaList where dummy3 not equals to UPDATED_DUMMY_3
        defaultFQMantequillaShouldBeFound("dummy3.notEquals=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy3IsInShouldWork() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy3 in DEFAULT_DUMMY_3 or UPDATED_DUMMY_3
        defaultFQMantequillaShouldBeFound("dummy3.in=" + DEFAULT_DUMMY_3 + "," + UPDATED_DUMMY_3);

        // Get all the fQMantequillaList where dummy3 equals to UPDATED_DUMMY_3
        defaultFQMantequillaShouldNotBeFound("dummy3.in=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy3IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy3 is not null
        defaultFQMantequillaShouldBeFound("dummy3.specified=true");

        // Get all the fQMantequillaList where dummy3 is null
        defaultFQMantequillaShouldNotBeFound("dummy3.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy3 is greater than or equal to DEFAULT_DUMMY_3
        defaultFQMantequillaShouldBeFound("dummy3.greaterThanOrEqual=" + DEFAULT_DUMMY_3);

        // Get all the fQMantequillaList where dummy3 is greater than or equal to UPDATED_DUMMY_3
        defaultFQMantequillaShouldNotBeFound("dummy3.greaterThanOrEqual=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy3 is less than or equal to DEFAULT_DUMMY_3
        defaultFQMantequillaShouldBeFound("dummy3.lessThanOrEqual=" + DEFAULT_DUMMY_3);

        // Get all the fQMantequillaList where dummy3 is less than or equal to SMALLER_DUMMY_3
        defaultFQMantequillaShouldNotBeFound("dummy3.lessThanOrEqual=" + SMALLER_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy3IsLessThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy3 is less than DEFAULT_DUMMY_3
        defaultFQMantequillaShouldNotBeFound("dummy3.lessThan=" + DEFAULT_DUMMY_3);

        // Get all the fQMantequillaList where dummy3 is less than UPDATED_DUMMY_3
        defaultFQMantequillaShouldBeFound("dummy3.lessThan=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy3 is greater than DEFAULT_DUMMY_3
        defaultFQMantequillaShouldNotBeFound("dummy3.greaterThan=" + DEFAULT_DUMMY_3);

        // Get all the fQMantequillaList where dummy3 is greater than SMALLER_DUMMY_3
        defaultFQMantequillaShouldBeFound("dummy3.greaterThan=" + SMALLER_DUMMY_3);
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByDummy4IsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy4 equals to DEFAULT_DUMMY_4
        defaultFQMantequillaShouldBeFound("dummy4.equals=" + DEFAULT_DUMMY_4);

        // Get all the fQMantequillaList where dummy4 equals to UPDATED_DUMMY_4
        defaultFQMantequillaShouldNotBeFound("dummy4.equals=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy4 not equals to DEFAULT_DUMMY_4
        defaultFQMantequillaShouldNotBeFound("dummy4.notEquals=" + DEFAULT_DUMMY_4);

        // Get all the fQMantequillaList where dummy4 not equals to UPDATED_DUMMY_4
        defaultFQMantequillaShouldBeFound("dummy4.notEquals=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy4IsInShouldWork() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy4 in DEFAULT_DUMMY_4 or UPDATED_DUMMY_4
        defaultFQMantequillaShouldBeFound("dummy4.in=" + DEFAULT_DUMMY_4 + "," + UPDATED_DUMMY_4);

        // Get all the fQMantequillaList where dummy4 equals to UPDATED_DUMMY_4
        defaultFQMantequillaShouldNotBeFound("dummy4.in=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy4IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy4 is not null
        defaultFQMantequillaShouldBeFound("dummy4.specified=true");

        // Get all the fQMantequillaList where dummy4 is null
        defaultFQMantequillaShouldNotBeFound("dummy4.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy4 is greater than or equal to DEFAULT_DUMMY_4
        defaultFQMantequillaShouldBeFound("dummy4.greaterThanOrEqual=" + DEFAULT_DUMMY_4);

        // Get all the fQMantequillaList where dummy4 is greater than or equal to UPDATED_DUMMY_4
        defaultFQMantequillaShouldNotBeFound("dummy4.greaterThanOrEqual=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy4 is less than or equal to DEFAULT_DUMMY_4
        defaultFQMantequillaShouldBeFound("dummy4.lessThanOrEqual=" + DEFAULT_DUMMY_4);

        // Get all the fQMantequillaList where dummy4 is less than or equal to SMALLER_DUMMY_4
        defaultFQMantequillaShouldNotBeFound("dummy4.lessThanOrEqual=" + SMALLER_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy4IsLessThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy4 is less than DEFAULT_DUMMY_4
        defaultFQMantequillaShouldNotBeFound("dummy4.lessThan=" + DEFAULT_DUMMY_4);

        // Get all the fQMantequillaList where dummy4 is less than UPDATED_DUMMY_4
        defaultFQMantequillaShouldBeFound("dummy4.lessThan=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy4 is greater than DEFAULT_DUMMY_4
        defaultFQMantequillaShouldNotBeFound("dummy4.greaterThan=" + DEFAULT_DUMMY_4);

        // Get all the fQMantequillaList where dummy4 is greater than SMALLER_DUMMY_4
        defaultFQMantequillaShouldBeFound("dummy4.greaterThan=" + SMALLER_DUMMY_4);
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByDummy5IsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy5 equals to DEFAULT_DUMMY_5
        defaultFQMantequillaShouldBeFound("dummy5.equals=" + DEFAULT_DUMMY_5);

        // Get all the fQMantequillaList where dummy5 equals to UPDATED_DUMMY_5
        defaultFQMantequillaShouldNotBeFound("dummy5.equals=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy5 not equals to DEFAULT_DUMMY_5
        defaultFQMantequillaShouldNotBeFound("dummy5.notEquals=" + DEFAULT_DUMMY_5);

        // Get all the fQMantequillaList where dummy5 not equals to UPDATED_DUMMY_5
        defaultFQMantequillaShouldBeFound("dummy5.notEquals=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy5IsInShouldWork() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy5 in DEFAULT_DUMMY_5 or UPDATED_DUMMY_5
        defaultFQMantequillaShouldBeFound("dummy5.in=" + DEFAULT_DUMMY_5 + "," + UPDATED_DUMMY_5);

        // Get all the fQMantequillaList where dummy5 equals to UPDATED_DUMMY_5
        defaultFQMantequillaShouldNotBeFound("dummy5.in=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy5IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy5 is not null
        defaultFQMantequillaShouldBeFound("dummy5.specified=true");

        // Get all the fQMantequillaList where dummy5 is null
        defaultFQMantequillaShouldNotBeFound("dummy5.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy5 is greater than or equal to DEFAULT_DUMMY_5
        defaultFQMantequillaShouldBeFound("dummy5.greaterThanOrEqual=" + DEFAULT_DUMMY_5);

        // Get all the fQMantequillaList where dummy5 is greater than or equal to UPDATED_DUMMY_5
        defaultFQMantequillaShouldNotBeFound("dummy5.greaterThanOrEqual=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy5 is less than or equal to DEFAULT_DUMMY_5
        defaultFQMantequillaShouldBeFound("dummy5.lessThanOrEqual=" + DEFAULT_DUMMY_5);

        // Get all the fQMantequillaList where dummy5 is less than or equal to SMALLER_DUMMY_5
        defaultFQMantequillaShouldNotBeFound("dummy5.lessThanOrEqual=" + SMALLER_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy5IsLessThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy5 is less than DEFAULT_DUMMY_5
        defaultFQMantequillaShouldNotBeFound("dummy5.lessThan=" + DEFAULT_DUMMY_5);

        // Get all the fQMantequillaList where dummy5 is less than UPDATED_DUMMY_5
        defaultFQMantequillaShouldBeFound("dummy5.lessThan=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByDummy5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where dummy5 is greater than DEFAULT_DUMMY_5
        defaultFQMantequillaShouldNotBeFound("dummy5.greaterThan=" + DEFAULT_DUMMY_5);

        // Get all the fQMantequillaList where dummy5 is greater than SMALLER_DUMMY_5
        defaultFQMantequillaShouldBeFound("dummy5.greaterThan=" + SMALLER_DUMMY_5);
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByObservacionesIsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where observaciones equals to DEFAULT_OBSERVACIONES
        defaultFQMantequillaShouldBeFound("observaciones.equals=" + DEFAULT_OBSERVACIONES);

        // Get all the fQMantequillaList where observaciones equals to UPDATED_OBSERVACIONES
        defaultFQMantequillaShouldNotBeFound("observaciones.equals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByObservacionesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where observaciones not equals to DEFAULT_OBSERVACIONES
        defaultFQMantequillaShouldNotBeFound("observaciones.notEquals=" + DEFAULT_OBSERVACIONES);

        // Get all the fQMantequillaList where observaciones not equals to UPDATED_OBSERVACIONES
        defaultFQMantequillaShouldBeFound("observaciones.notEquals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByObservacionesIsInShouldWork() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where observaciones in DEFAULT_OBSERVACIONES or UPDATED_OBSERVACIONES
        defaultFQMantequillaShouldBeFound("observaciones.in=" + DEFAULT_OBSERVACIONES + "," + UPDATED_OBSERVACIONES);

        // Get all the fQMantequillaList where observaciones equals to UPDATED_OBSERVACIONES
        defaultFQMantequillaShouldNotBeFound("observaciones.in=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByObservacionesIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where observaciones is not null
        defaultFQMantequillaShouldBeFound("observaciones.specified=true");

        // Get all the fQMantequillaList where observaciones is null
        defaultFQMantequillaShouldNotBeFound("observaciones.specified=false");
    }
                @Test
    @Transactional
    public void getAllFQMantequillasByObservacionesContainsSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where observaciones contains DEFAULT_OBSERVACIONES
        defaultFQMantequillaShouldBeFound("observaciones.contains=" + DEFAULT_OBSERVACIONES);

        // Get all the fQMantequillaList where observaciones contains UPDATED_OBSERVACIONES
        defaultFQMantequillaShouldNotBeFound("observaciones.contains=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQMantequillasByObservacionesNotContainsSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);

        // Get all the fQMantequillaList where observaciones does not contain DEFAULT_OBSERVACIONES
        defaultFQMantequillaShouldNotBeFound("observaciones.doesNotContain=" + DEFAULT_OBSERVACIONES);

        // Get all the fQMantequillaList where observaciones does not contain UPDATED_OBSERVACIONES
        defaultFQMantequillaShouldBeFound("observaciones.doesNotContain=" + UPDATED_OBSERVACIONES);
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByAreaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Area area = fQMantequilla.getArea();
        fQMantequillaRepository.saveAndFlush(fQMantequilla);
        Long areaId = area.getId();

        // Get all the fQMantequillaList where area equals to areaId
        defaultFQMantequillaShouldBeFound("areaId.equals=" + areaId);

        // Get all the fQMantequillaList where area equals to areaId + 1
        defaultFQMantequillaShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByProductoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Producto producto = fQMantequilla.getProducto();
        fQMantequillaRepository.saveAndFlush(fQMantequilla);
        Long productoId = producto.getId();

        // Get all the fQMantequillaList where producto equals to productoId
        defaultFQMantequillaShouldBeFound("productoId.equals=" + productoId);

        // Get all the fQMantequillaList where producto equals to productoId + 1
        defaultFQMantequillaShouldNotBeFound("productoId.equals=" + (productoId + 1));
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByAnalistaIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserExtra analista = fQMantequilla.getAnalista();
        fQMantequillaRepository.saveAndFlush(fQMantequilla);
        Long analistaId = analista.getId();

        // Get all the fQMantequillaList where analista equals to analistaId
        defaultFQMantequillaShouldBeFound("analistaId.equals=" + analistaId);

        // Get all the fQMantequillaList where analista equals to analistaId + 1
        defaultFQMantequillaShouldNotBeFound("analistaId.equals=" + (analistaId + 1));
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByProveedorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Personal proveedor = fQMantequilla.getProveedor();
        fQMantequillaRepository.saveAndFlush(fQMantequilla);
        Long proveedorId = proveedor.getId();

        // Get all the fQMantequillaList where proveedor equals to proveedorId
        defaultFQMantequillaShouldBeFound("proveedorId.equals=" + proveedorId);

        // Get all the fQMantequillaList where proveedor equals to proveedorId + 1
        defaultFQMantequillaShouldNotBeFound("proveedorId.equals=" + (proveedorId + 1));
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByContenedorIsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);
        Contenedor contenedor = ContenedorResourceIT.createEntity(em);
        em.persist(contenedor);
        em.flush();
        fQMantequilla.setContenedor(contenedor);
        fQMantequillaRepository.saveAndFlush(fQMantequilla);
        Long contenedorId = contenedor.getId();

        // Get all the fQMantequillaList where contenedor equals to contenedorId
        defaultFQMantequillaShouldBeFound("contenedorId.equals=" + contenedorId);

        // Get all the fQMantequillaList where contenedor equals to contenedorId + 1
        defaultFQMantequillaShouldNotBeFound("contenedorId.equals=" + (contenedorId + 1));
    }


    @Test
    @Transactional
    public void getAllFQMantequillasByProcesoIsEqualToSomething() throws Exception {
        // Initialize the database
        fQMantequillaRepository.saveAndFlush(fQMantequilla);
        Proceso proceso = ProcesoResourceIT.createEntity(em);
        em.persist(proceso);
        em.flush();
        fQMantequilla.setProceso(proceso);
        fQMantequillaRepository.saveAndFlush(fQMantequilla);
        Long procesoId = proceso.getId();

        // Get all the fQMantequillaList where proceso equals to procesoId
        defaultFQMantequillaShouldBeFound("procesoId.equals=" + procesoId);

        // Get all the fQMantequillaList where proceso equals to procesoId + 1
        defaultFQMantequillaShouldNotBeFound("procesoId.equals=" + (procesoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFQMantequillaShouldBeFound(String filter) throws Exception {
        restFQMantequillaMockMvc.perform(get("/api/fq-mantequillas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fQMantequilla.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.doubleValue())))
            .andExpect(jsonPath("$.[*].humedad").value(hasItem(DEFAULT_HUMEDAD.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy1").value(hasItem(DEFAULT_DUMMY_1.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy2").value(hasItem(DEFAULT_DUMMY_2.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy3").value(hasItem(DEFAULT_DUMMY_3.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy4").value(hasItem(DEFAULT_DUMMY_4.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy5").value(hasItem(DEFAULT_DUMMY_5.doubleValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));

        // Check, that the count call also returns 1
        restFQMantequillaMockMvc.perform(get("/api/fq-mantequillas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFQMantequillaShouldNotBeFound(String filter) throws Exception {
        restFQMantequillaMockMvc.perform(get("/api/fq-mantequillas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFQMantequillaMockMvc.perform(get("/api/fq-mantequillas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFQMantequilla() throws Exception {
        // Get the fQMantequilla
        restFQMantequillaMockMvc.perform(get("/api/fq-mantequillas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFQMantequilla() throws Exception {
        // Initialize the database
        fQMantequillaService.save(fQMantequilla);

        int databaseSizeBeforeUpdate = fQMantequillaRepository.findAll().size();

        // Update the fQMantequilla
        FQMantequilla updatedFQMantequilla = fQMantequillaRepository.findById(fQMantequilla.getId()).get();
        // Disconnect from session so that the updates on updatedFQMantequilla are not directly saved in db
        em.detach(updatedFQMantequilla);
        updatedFQMantequilla
            .fecha(UPDATED_FECHA)
            .lote(UPDATED_LOTE)
            .ph(UPDATED_PH)
            .humedad(UPDATED_HUMEDAD)
            .dummy1(UPDATED_DUMMY_1)
            .dummy2(UPDATED_DUMMY_2)
            .dummy3(UPDATED_DUMMY_3)
            .dummy4(UPDATED_DUMMY_4)
            .dummy5(UPDATED_DUMMY_5)
            .observaciones(UPDATED_OBSERVACIONES);

        restFQMantequillaMockMvc.perform(put("/api/fq-mantequillas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFQMantequilla)))
            .andExpect(status().isOk());

        // Validate the FQMantequilla in the database
        List<FQMantequilla> fQMantequillaList = fQMantequillaRepository.findAll();
        assertThat(fQMantequillaList).hasSize(databaseSizeBeforeUpdate);
        FQMantequilla testFQMantequilla = fQMantequillaList.get(fQMantequillaList.size() - 1);
        assertThat(testFQMantequilla.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFQMantequilla.getLote()).isEqualTo(UPDATED_LOTE);
        assertThat(testFQMantequilla.getPh()).isEqualTo(UPDATED_PH);
        assertThat(testFQMantequilla.getHumedad()).isEqualTo(UPDATED_HUMEDAD);
        assertThat(testFQMantequilla.getDummy1()).isEqualTo(UPDATED_DUMMY_1);
        assertThat(testFQMantequilla.getDummy2()).isEqualTo(UPDATED_DUMMY_2);
        assertThat(testFQMantequilla.getDummy3()).isEqualTo(UPDATED_DUMMY_3);
        assertThat(testFQMantequilla.getDummy4()).isEqualTo(UPDATED_DUMMY_4);
        assertThat(testFQMantequilla.getDummy5()).isEqualTo(UPDATED_DUMMY_5);
        assertThat(testFQMantequilla.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingFQMantequilla() throws Exception {
        int databaseSizeBeforeUpdate = fQMantequillaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFQMantequillaMockMvc.perform(put("/api/fq-mantequillas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQMantequilla)))
            .andExpect(status().isBadRequest());

        // Validate the FQMantequilla in the database
        List<FQMantequilla> fQMantequillaList = fQMantequillaRepository.findAll();
        assertThat(fQMantequillaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFQMantequilla() throws Exception {
        // Initialize the database
        fQMantequillaService.save(fQMantequilla);

        int databaseSizeBeforeDelete = fQMantequillaRepository.findAll().size();

        // Delete the fQMantequilla
        restFQMantequillaMockMvc.perform(delete("/api/fq-mantequillas/{id}", fQMantequilla.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FQMantequilla> fQMantequillaList = fQMantequillaRepository.findAll();
        assertThat(fQMantequillaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
