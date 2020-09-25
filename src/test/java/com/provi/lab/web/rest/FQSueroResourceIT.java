package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.FQSuero;
import com.provi.lab.domain.Area;
import com.provi.lab.domain.Producto;
import com.provi.lab.domain.UserExtra;
import com.provi.lab.domain.Personal;
import com.provi.lab.repository.FQSueroRepository;
import com.provi.lab.service.FQSueroService;
import com.provi.lab.service.dto.FQSueroCriteria;
import com.provi.lab.service.FQSueroQueryService;

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
 * Integration tests for the {@link FQSueroResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FQSueroResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_LOTE = "BBBBBBBBBB";

    private static final Double DEFAULT_ACIDEZ = 1D;
    private static final Double UPDATED_ACIDEZ = 2D;
    private static final Double SMALLER_ACIDEZ = 1D - 1D;

    private static final Double DEFAULT_TEMPERATURA = 1D;
    private static final Double UPDATED_TEMPERATURA = 2D;
    private static final Double SMALLER_TEMPERATURA = 1D - 1D;

    private static final Double DEFAULT_DELVO = 1D;
    private static final Double UPDATED_DELVO = 2D;
    private static final Double SMALLER_DELVO = 1D - 1D;

    private static final Double DEFAULT_SOLIDOS = 1D;
    private static final Double UPDATED_SOLIDOS = 2D;
    private static final Double SMALLER_SOLIDOS = 1D - 1D;

    private static final Double DEFAULT_NEUTRALIZANTES = 1D;
    private static final Double UPDATED_NEUTRALIZANTES = 2D;
    private static final Double SMALLER_NEUTRALIZANTES = 1D - 1D;

    private static final Double DEFAULT_PH = 1D;
    private static final Double UPDATED_PH = 2D;
    private static final Double SMALLER_PH = 1D - 1D;

    private static final Double DEFAULT_CLORO = 1D;
    private static final Double UPDATED_CLORO = 2D;
    private static final Double SMALLER_CLORO = 1D - 1D;

    private static final Double DEFAULT_ALMIDON = 1D;
    private static final Double UPDATED_ALMIDON = 2D;
    private static final Double SMALLER_ALMIDON = 1D - 1D;

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    @Autowired
    private FQSueroRepository fQSueroRepository;

    @Autowired
    private FQSueroService fQSueroService;

    @Autowired
    private FQSueroQueryService fQSueroQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFQSueroMockMvc;

    private FQSuero fQSuero;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FQSuero createEntity(EntityManager em) {
        FQSuero fQSuero = new FQSuero()
            .fecha(DEFAULT_FECHA)
            .lote(DEFAULT_LOTE)
            .acidez(DEFAULT_ACIDEZ)
            .temperatura(DEFAULT_TEMPERATURA)
            .delvo(DEFAULT_DELVO)
            .solidos(DEFAULT_SOLIDOS)
            .neutralizantes(DEFAULT_NEUTRALIZANTES)
            .ph(DEFAULT_PH)
            .cloro(DEFAULT_CLORO)
            .almidon(DEFAULT_ALMIDON)
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
        fQSuero.setArea(area);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        fQSuero.setProducto(producto);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        fQSuero.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        fQSuero.setProveedor(personal);
        return fQSuero;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FQSuero createUpdatedEntity(EntityManager em) {
        FQSuero fQSuero = new FQSuero()
            .fecha(UPDATED_FECHA)
            .lote(UPDATED_LOTE)
            .acidez(UPDATED_ACIDEZ)
            .temperatura(UPDATED_TEMPERATURA)
            .delvo(UPDATED_DELVO)
            .solidos(UPDATED_SOLIDOS)
            .neutralizantes(UPDATED_NEUTRALIZANTES)
            .ph(UPDATED_PH)
            .cloro(UPDATED_CLORO)
            .almidon(UPDATED_ALMIDON)
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
        fQSuero.setArea(area);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        fQSuero.setProducto(producto);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createUpdatedEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        fQSuero.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createUpdatedEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        fQSuero.setProveedor(personal);
        return fQSuero;
    }

    @BeforeEach
    public void initTest() {
        fQSuero = createEntity(em);
    }

    @Test
    @Transactional
    public void createFQSuero() throws Exception {
        int databaseSizeBeforeCreate = fQSueroRepository.findAll().size();
        // Create the FQSuero
        restFQSueroMockMvc.perform(post("/api/fq-sueros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQSuero)))
            .andExpect(status().isCreated());

        // Validate the FQSuero in the database
        List<FQSuero> fQSueroList = fQSueroRepository.findAll();
        assertThat(fQSueroList).hasSize(databaseSizeBeforeCreate + 1);
        FQSuero testFQSuero = fQSueroList.get(fQSueroList.size() - 1);
        assertThat(testFQSuero.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testFQSuero.getLote()).isEqualTo(DEFAULT_LOTE);
        assertThat(testFQSuero.getAcidez()).isEqualTo(DEFAULT_ACIDEZ);
        assertThat(testFQSuero.getTemperatura()).isEqualTo(DEFAULT_TEMPERATURA);
        assertThat(testFQSuero.getDelvo()).isEqualTo(DEFAULT_DELVO);
        assertThat(testFQSuero.getSolidos()).isEqualTo(DEFAULT_SOLIDOS);
        assertThat(testFQSuero.getNeutralizantes()).isEqualTo(DEFAULT_NEUTRALIZANTES);
        assertThat(testFQSuero.getPh()).isEqualTo(DEFAULT_PH);
        assertThat(testFQSuero.getCloro()).isEqualTo(DEFAULT_CLORO);
        assertThat(testFQSuero.getAlmidon()).isEqualTo(DEFAULT_ALMIDON);
        assertThat(testFQSuero.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void createFQSueroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fQSueroRepository.findAll().size();

        // Create the FQSuero with an existing ID
        fQSuero.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFQSueroMockMvc.perform(post("/api/fq-sueros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQSuero)))
            .andExpect(status().isBadRequest());

        // Validate the FQSuero in the database
        List<FQSuero> fQSueroList = fQSueroRepository.findAll();
        assertThat(fQSueroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fQSueroRepository.findAll().size();
        // set the field null
        fQSuero.setFecha(null);

        // Create the FQSuero, which fails.


        restFQSueroMockMvc.perform(post("/api/fq-sueros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQSuero)))
            .andExpect(status().isBadRequest());

        List<FQSuero> fQSueroList = fQSueroRepository.findAll();
        assertThat(fQSueroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = fQSueroRepository.findAll().size();
        // set the field null
        fQSuero.setLote(null);

        // Create the FQSuero, which fails.


        restFQSueroMockMvc.perform(post("/api/fq-sueros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQSuero)))
            .andExpect(status().isBadRequest());

        List<FQSuero> fQSueroList = fQSueroRepository.findAll();
        assertThat(fQSueroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFQSueros() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList
        restFQSueroMockMvc.perform(get("/api/fq-sueros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fQSuero.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].acidez").value(hasItem(DEFAULT_ACIDEZ.doubleValue())))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA.doubleValue())))
            .andExpect(jsonPath("$.[*].delvo").value(hasItem(DEFAULT_DELVO.doubleValue())))
            .andExpect(jsonPath("$.[*].solidos").value(hasItem(DEFAULT_SOLIDOS.doubleValue())))
            .andExpect(jsonPath("$.[*].neutralizantes").value(hasItem(DEFAULT_NEUTRALIZANTES.doubleValue())))
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.doubleValue())))
            .andExpect(jsonPath("$.[*].cloro").value(hasItem(DEFAULT_CLORO.doubleValue())))
            .andExpect(jsonPath("$.[*].almidon").value(hasItem(DEFAULT_ALMIDON.doubleValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }
    
    @Test
    @Transactional
    public void getFQSuero() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get the fQSuero
        restFQSueroMockMvc.perform(get("/api/fq-sueros/{id}", fQSuero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fQSuero.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.lote").value(DEFAULT_LOTE))
            .andExpect(jsonPath("$.acidez").value(DEFAULT_ACIDEZ.doubleValue()))
            .andExpect(jsonPath("$.temperatura").value(DEFAULT_TEMPERATURA.doubleValue()))
            .andExpect(jsonPath("$.delvo").value(DEFAULT_DELVO.doubleValue()))
            .andExpect(jsonPath("$.solidos").value(DEFAULT_SOLIDOS.doubleValue()))
            .andExpect(jsonPath("$.neutralizantes").value(DEFAULT_NEUTRALIZANTES.doubleValue()))
            .andExpect(jsonPath("$.ph").value(DEFAULT_PH.doubleValue()))
            .andExpect(jsonPath("$.cloro").value(DEFAULT_CLORO.doubleValue()))
            .andExpect(jsonPath("$.almidon").value(DEFAULT_ALMIDON.doubleValue()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }


    @Test
    @Transactional
    public void getFQSuerosByIdFiltering() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        Long id = fQSuero.getId();

        defaultFQSueroShouldBeFound("id.equals=" + id);
        defaultFQSueroShouldNotBeFound("id.notEquals=" + id);

        defaultFQSueroShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFQSueroShouldNotBeFound("id.greaterThan=" + id);

        defaultFQSueroShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFQSueroShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFQSuerosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where fecha equals to DEFAULT_FECHA
        defaultFQSueroShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the fQSueroList where fecha equals to UPDATED_FECHA
        defaultFQSueroShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where fecha not equals to DEFAULT_FECHA
        defaultFQSueroShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the fQSueroList where fecha not equals to UPDATED_FECHA
        defaultFQSueroShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultFQSueroShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the fQSueroList where fecha equals to UPDATED_FECHA
        defaultFQSueroShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where fecha is not null
        defaultFQSueroShouldBeFound("fecha.specified=true");

        // Get all the fQSueroList where fecha is null
        defaultFQSueroShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQSuerosByLoteIsEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where lote equals to DEFAULT_LOTE
        defaultFQSueroShouldBeFound("lote.equals=" + DEFAULT_LOTE);

        // Get all the fQSueroList where lote equals to UPDATED_LOTE
        defaultFQSueroShouldNotBeFound("lote.equals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByLoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where lote not equals to DEFAULT_LOTE
        defaultFQSueroShouldNotBeFound("lote.notEquals=" + DEFAULT_LOTE);

        // Get all the fQSueroList where lote not equals to UPDATED_LOTE
        defaultFQSueroShouldBeFound("lote.notEquals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByLoteIsInShouldWork() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where lote in DEFAULT_LOTE or UPDATED_LOTE
        defaultFQSueroShouldBeFound("lote.in=" + DEFAULT_LOTE + "," + UPDATED_LOTE);

        // Get all the fQSueroList where lote equals to UPDATED_LOTE
        defaultFQSueroShouldNotBeFound("lote.in=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByLoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where lote is not null
        defaultFQSueroShouldBeFound("lote.specified=true");

        // Get all the fQSueroList where lote is null
        defaultFQSueroShouldNotBeFound("lote.specified=false");
    }
                @Test
    @Transactional
    public void getAllFQSuerosByLoteContainsSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where lote contains DEFAULT_LOTE
        defaultFQSueroShouldBeFound("lote.contains=" + DEFAULT_LOTE);

        // Get all the fQSueroList where lote contains UPDATED_LOTE
        defaultFQSueroShouldNotBeFound("lote.contains=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByLoteNotContainsSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where lote does not contain DEFAULT_LOTE
        defaultFQSueroShouldNotBeFound("lote.doesNotContain=" + DEFAULT_LOTE);

        // Get all the fQSueroList where lote does not contain UPDATED_LOTE
        defaultFQSueroShouldBeFound("lote.doesNotContain=" + UPDATED_LOTE);
    }


    @Test
    @Transactional
    public void getAllFQSuerosByAcidezIsEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where acidez equals to DEFAULT_ACIDEZ
        defaultFQSueroShouldBeFound("acidez.equals=" + DEFAULT_ACIDEZ);

        // Get all the fQSueroList where acidez equals to UPDATED_ACIDEZ
        defaultFQSueroShouldNotBeFound("acidez.equals=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAcidezIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where acidez not equals to DEFAULT_ACIDEZ
        defaultFQSueroShouldNotBeFound("acidez.notEquals=" + DEFAULT_ACIDEZ);

        // Get all the fQSueroList where acidez not equals to UPDATED_ACIDEZ
        defaultFQSueroShouldBeFound("acidez.notEquals=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAcidezIsInShouldWork() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where acidez in DEFAULT_ACIDEZ or UPDATED_ACIDEZ
        defaultFQSueroShouldBeFound("acidez.in=" + DEFAULT_ACIDEZ + "," + UPDATED_ACIDEZ);

        // Get all the fQSueroList where acidez equals to UPDATED_ACIDEZ
        defaultFQSueroShouldNotBeFound("acidez.in=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAcidezIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where acidez is not null
        defaultFQSueroShouldBeFound("acidez.specified=true");

        // Get all the fQSueroList where acidez is null
        defaultFQSueroShouldNotBeFound("acidez.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAcidezIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where acidez is greater than or equal to DEFAULT_ACIDEZ
        defaultFQSueroShouldBeFound("acidez.greaterThanOrEqual=" + DEFAULT_ACIDEZ);

        // Get all the fQSueroList where acidez is greater than or equal to UPDATED_ACIDEZ
        defaultFQSueroShouldNotBeFound("acidez.greaterThanOrEqual=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAcidezIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where acidez is less than or equal to DEFAULT_ACIDEZ
        defaultFQSueroShouldBeFound("acidez.lessThanOrEqual=" + DEFAULT_ACIDEZ);

        // Get all the fQSueroList where acidez is less than or equal to SMALLER_ACIDEZ
        defaultFQSueroShouldNotBeFound("acidez.lessThanOrEqual=" + SMALLER_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAcidezIsLessThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where acidez is less than DEFAULT_ACIDEZ
        defaultFQSueroShouldNotBeFound("acidez.lessThan=" + DEFAULT_ACIDEZ);

        // Get all the fQSueroList where acidez is less than UPDATED_ACIDEZ
        defaultFQSueroShouldBeFound("acidez.lessThan=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAcidezIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where acidez is greater than DEFAULT_ACIDEZ
        defaultFQSueroShouldNotBeFound("acidez.greaterThan=" + DEFAULT_ACIDEZ);

        // Get all the fQSueroList where acidez is greater than SMALLER_ACIDEZ
        defaultFQSueroShouldBeFound("acidez.greaterThan=" + SMALLER_ACIDEZ);
    }


    @Test
    @Transactional
    public void getAllFQSuerosByTemperaturaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where temperatura equals to DEFAULT_TEMPERATURA
        defaultFQSueroShouldBeFound("temperatura.equals=" + DEFAULT_TEMPERATURA);

        // Get all the fQSueroList where temperatura equals to UPDATED_TEMPERATURA
        defaultFQSueroShouldNotBeFound("temperatura.equals=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByTemperaturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where temperatura not equals to DEFAULT_TEMPERATURA
        defaultFQSueroShouldNotBeFound("temperatura.notEquals=" + DEFAULT_TEMPERATURA);

        // Get all the fQSueroList where temperatura not equals to UPDATED_TEMPERATURA
        defaultFQSueroShouldBeFound("temperatura.notEquals=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByTemperaturaIsInShouldWork() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where temperatura in DEFAULT_TEMPERATURA or UPDATED_TEMPERATURA
        defaultFQSueroShouldBeFound("temperatura.in=" + DEFAULT_TEMPERATURA + "," + UPDATED_TEMPERATURA);

        // Get all the fQSueroList where temperatura equals to UPDATED_TEMPERATURA
        defaultFQSueroShouldNotBeFound("temperatura.in=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByTemperaturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where temperatura is not null
        defaultFQSueroShouldBeFound("temperatura.specified=true");

        // Get all the fQSueroList where temperatura is null
        defaultFQSueroShouldNotBeFound("temperatura.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQSuerosByTemperaturaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where temperatura is greater than or equal to DEFAULT_TEMPERATURA
        defaultFQSueroShouldBeFound("temperatura.greaterThanOrEqual=" + DEFAULT_TEMPERATURA);

        // Get all the fQSueroList where temperatura is greater than or equal to UPDATED_TEMPERATURA
        defaultFQSueroShouldNotBeFound("temperatura.greaterThanOrEqual=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByTemperaturaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where temperatura is less than or equal to DEFAULT_TEMPERATURA
        defaultFQSueroShouldBeFound("temperatura.lessThanOrEqual=" + DEFAULT_TEMPERATURA);

        // Get all the fQSueroList where temperatura is less than or equal to SMALLER_TEMPERATURA
        defaultFQSueroShouldNotBeFound("temperatura.lessThanOrEqual=" + SMALLER_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByTemperaturaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where temperatura is less than DEFAULT_TEMPERATURA
        defaultFQSueroShouldNotBeFound("temperatura.lessThan=" + DEFAULT_TEMPERATURA);

        // Get all the fQSueroList where temperatura is less than UPDATED_TEMPERATURA
        defaultFQSueroShouldBeFound("temperatura.lessThan=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByTemperaturaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where temperatura is greater than DEFAULT_TEMPERATURA
        defaultFQSueroShouldNotBeFound("temperatura.greaterThan=" + DEFAULT_TEMPERATURA);

        // Get all the fQSueroList where temperatura is greater than SMALLER_TEMPERATURA
        defaultFQSueroShouldBeFound("temperatura.greaterThan=" + SMALLER_TEMPERATURA);
    }


    @Test
    @Transactional
    public void getAllFQSuerosByDelvoIsEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where delvo equals to DEFAULT_DELVO
        defaultFQSueroShouldBeFound("delvo.equals=" + DEFAULT_DELVO);

        // Get all the fQSueroList where delvo equals to UPDATED_DELVO
        defaultFQSueroShouldNotBeFound("delvo.equals=" + UPDATED_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByDelvoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where delvo not equals to DEFAULT_DELVO
        defaultFQSueroShouldNotBeFound("delvo.notEquals=" + DEFAULT_DELVO);

        // Get all the fQSueroList where delvo not equals to UPDATED_DELVO
        defaultFQSueroShouldBeFound("delvo.notEquals=" + UPDATED_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByDelvoIsInShouldWork() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where delvo in DEFAULT_DELVO or UPDATED_DELVO
        defaultFQSueroShouldBeFound("delvo.in=" + DEFAULT_DELVO + "," + UPDATED_DELVO);

        // Get all the fQSueroList where delvo equals to UPDATED_DELVO
        defaultFQSueroShouldNotBeFound("delvo.in=" + UPDATED_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByDelvoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where delvo is not null
        defaultFQSueroShouldBeFound("delvo.specified=true");

        // Get all the fQSueroList where delvo is null
        defaultFQSueroShouldNotBeFound("delvo.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQSuerosByDelvoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where delvo is greater than or equal to DEFAULT_DELVO
        defaultFQSueroShouldBeFound("delvo.greaterThanOrEqual=" + DEFAULT_DELVO);

        // Get all the fQSueroList where delvo is greater than or equal to UPDATED_DELVO
        defaultFQSueroShouldNotBeFound("delvo.greaterThanOrEqual=" + UPDATED_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByDelvoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where delvo is less than or equal to DEFAULT_DELVO
        defaultFQSueroShouldBeFound("delvo.lessThanOrEqual=" + DEFAULT_DELVO);

        // Get all the fQSueroList where delvo is less than or equal to SMALLER_DELVO
        defaultFQSueroShouldNotBeFound("delvo.lessThanOrEqual=" + SMALLER_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByDelvoIsLessThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where delvo is less than DEFAULT_DELVO
        defaultFQSueroShouldNotBeFound("delvo.lessThan=" + DEFAULT_DELVO);

        // Get all the fQSueroList where delvo is less than UPDATED_DELVO
        defaultFQSueroShouldBeFound("delvo.lessThan=" + UPDATED_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByDelvoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where delvo is greater than DEFAULT_DELVO
        defaultFQSueroShouldNotBeFound("delvo.greaterThan=" + DEFAULT_DELVO);

        // Get all the fQSueroList where delvo is greater than SMALLER_DELVO
        defaultFQSueroShouldBeFound("delvo.greaterThan=" + SMALLER_DELVO);
    }


    @Test
    @Transactional
    public void getAllFQSuerosBySolidosIsEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where solidos equals to DEFAULT_SOLIDOS
        defaultFQSueroShouldBeFound("solidos.equals=" + DEFAULT_SOLIDOS);

        // Get all the fQSueroList where solidos equals to UPDATED_SOLIDOS
        defaultFQSueroShouldNotBeFound("solidos.equals=" + UPDATED_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllFQSuerosBySolidosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where solidos not equals to DEFAULT_SOLIDOS
        defaultFQSueroShouldNotBeFound("solidos.notEquals=" + DEFAULT_SOLIDOS);

        // Get all the fQSueroList where solidos not equals to UPDATED_SOLIDOS
        defaultFQSueroShouldBeFound("solidos.notEquals=" + UPDATED_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllFQSuerosBySolidosIsInShouldWork() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where solidos in DEFAULT_SOLIDOS or UPDATED_SOLIDOS
        defaultFQSueroShouldBeFound("solidos.in=" + DEFAULT_SOLIDOS + "," + UPDATED_SOLIDOS);

        // Get all the fQSueroList where solidos equals to UPDATED_SOLIDOS
        defaultFQSueroShouldNotBeFound("solidos.in=" + UPDATED_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllFQSuerosBySolidosIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where solidos is not null
        defaultFQSueroShouldBeFound("solidos.specified=true");

        // Get all the fQSueroList where solidos is null
        defaultFQSueroShouldNotBeFound("solidos.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQSuerosBySolidosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where solidos is greater than or equal to DEFAULT_SOLIDOS
        defaultFQSueroShouldBeFound("solidos.greaterThanOrEqual=" + DEFAULT_SOLIDOS);

        // Get all the fQSueroList where solidos is greater than or equal to UPDATED_SOLIDOS
        defaultFQSueroShouldNotBeFound("solidos.greaterThanOrEqual=" + UPDATED_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllFQSuerosBySolidosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where solidos is less than or equal to DEFAULT_SOLIDOS
        defaultFQSueroShouldBeFound("solidos.lessThanOrEqual=" + DEFAULT_SOLIDOS);

        // Get all the fQSueroList where solidos is less than or equal to SMALLER_SOLIDOS
        defaultFQSueroShouldNotBeFound("solidos.lessThanOrEqual=" + SMALLER_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllFQSuerosBySolidosIsLessThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where solidos is less than DEFAULT_SOLIDOS
        defaultFQSueroShouldNotBeFound("solidos.lessThan=" + DEFAULT_SOLIDOS);

        // Get all the fQSueroList where solidos is less than UPDATED_SOLIDOS
        defaultFQSueroShouldBeFound("solidos.lessThan=" + UPDATED_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllFQSuerosBySolidosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where solidos is greater than DEFAULT_SOLIDOS
        defaultFQSueroShouldNotBeFound("solidos.greaterThan=" + DEFAULT_SOLIDOS);

        // Get all the fQSueroList where solidos is greater than SMALLER_SOLIDOS
        defaultFQSueroShouldBeFound("solidos.greaterThan=" + SMALLER_SOLIDOS);
    }


    @Test
    @Transactional
    public void getAllFQSuerosByNeutralizantesIsEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where neutralizantes equals to DEFAULT_NEUTRALIZANTES
        defaultFQSueroShouldBeFound("neutralizantes.equals=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQSueroList where neutralizantes equals to UPDATED_NEUTRALIZANTES
        defaultFQSueroShouldNotBeFound("neutralizantes.equals=" + UPDATED_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByNeutralizantesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where neutralizantes not equals to DEFAULT_NEUTRALIZANTES
        defaultFQSueroShouldNotBeFound("neutralizantes.notEquals=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQSueroList where neutralizantes not equals to UPDATED_NEUTRALIZANTES
        defaultFQSueroShouldBeFound("neutralizantes.notEquals=" + UPDATED_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByNeutralizantesIsInShouldWork() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where neutralizantes in DEFAULT_NEUTRALIZANTES or UPDATED_NEUTRALIZANTES
        defaultFQSueroShouldBeFound("neutralizantes.in=" + DEFAULT_NEUTRALIZANTES + "," + UPDATED_NEUTRALIZANTES);

        // Get all the fQSueroList where neutralizantes equals to UPDATED_NEUTRALIZANTES
        defaultFQSueroShouldNotBeFound("neutralizantes.in=" + UPDATED_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByNeutralizantesIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where neutralizantes is not null
        defaultFQSueroShouldBeFound("neutralizantes.specified=true");

        // Get all the fQSueroList where neutralizantes is null
        defaultFQSueroShouldNotBeFound("neutralizantes.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQSuerosByNeutralizantesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where neutralizantes is greater than or equal to DEFAULT_NEUTRALIZANTES
        defaultFQSueroShouldBeFound("neutralizantes.greaterThanOrEqual=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQSueroList where neutralizantes is greater than or equal to UPDATED_NEUTRALIZANTES
        defaultFQSueroShouldNotBeFound("neutralizantes.greaterThanOrEqual=" + UPDATED_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByNeutralizantesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where neutralizantes is less than or equal to DEFAULT_NEUTRALIZANTES
        defaultFQSueroShouldBeFound("neutralizantes.lessThanOrEqual=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQSueroList where neutralizantes is less than or equal to SMALLER_NEUTRALIZANTES
        defaultFQSueroShouldNotBeFound("neutralizantes.lessThanOrEqual=" + SMALLER_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByNeutralizantesIsLessThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where neutralizantes is less than DEFAULT_NEUTRALIZANTES
        defaultFQSueroShouldNotBeFound("neutralizantes.lessThan=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQSueroList where neutralizantes is less than UPDATED_NEUTRALIZANTES
        defaultFQSueroShouldBeFound("neutralizantes.lessThan=" + UPDATED_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByNeutralizantesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where neutralizantes is greater than DEFAULT_NEUTRALIZANTES
        defaultFQSueroShouldNotBeFound("neutralizantes.greaterThan=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQSueroList where neutralizantes is greater than SMALLER_NEUTRALIZANTES
        defaultFQSueroShouldBeFound("neutralizantes.greaterThan=" + SMALLER_NEUTRALIZANTES);
    }


    @Test
    @Transactional
    public void getAllFQSuerosByPhIsEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where ph equals to DEFAULT_PH
        defaultFQSueroShouldBeFound("ph.equals=" + DEFAULT_PH);

        // Get all the fQSueroList where ph equals to UPDATED_PH
        defaultFQSueroShouldNotBeFound("ph.equals=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByPhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where ph not equals to DEFAULT_PH
        defaultFQSueroShouldNotBeFound("ph.notEquals=" + DEFAULT_PH);

        // Get all the fQSueroList where ph not equals to UPDATED_PH
        defaultFQSueroShouldBeFound("ph.notEquals=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByPhIsInShouldWork() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where ph in DEFAULT_PH or UPDATED_PH
        defaultFQSueroShouldBeFound("ph.in=" + DEFAULT_PH + "," + UPDATED_PH);

        // Get all the fQSueroList where ph equals to UPDATED_PH
        defaultFQSueroShouldNotBeFound("ph.in=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByPhIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where ph is not null
        defaultFQSueroShouldBeFound("ph.specified=true");

        // Get all the fQSueroList where ph is null
        defaultFQSueroShouldNotBeFound("ph.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQSuerosByPhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where ph is greater than or equal to DEFAULT_PH
        defaultFQSueroShouldBeFound("ph.greaterThanOrEqual=" + DEFAULT_PH);

        // Get all the fQSueroList where ph is greater than or equal to UPDATED_PH
        defaultFQSueroShouldNotBeFound("ph.greaterThanOrEqual=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByPhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where ph is less than or equal to DEFAULT_PH
        defaultFQSueroShouldBeFound("ph.lessThanOrEqual=" + DEFAULT_PH);

        // Get all the fQSueroList where ph is less than or equal to SMALLER_PH
        defaultFQSueroShouldNotBeFound("ph.lessThanOrEqual=" + SMALLER_PH);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByPhIsLessThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where ph is less than DEFAULT_PH
        defaultFQSueroShouldNotBeFound("ph.lessThan=" + DEFAULT_PH);

        // Get all the fQSueroList where ph is less than UPDATED_PH
        defaultFQSueroShouldBeFound("ph.lessThan=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByPhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where ph is greater than DEFAULT_PH
        defaultFQSueroShouldNotBeFound("ph.greaterThan=" + DEFAULT_PH);

        // Get all the fQSueroList where ph is greater than SMALLER_PH
        defaultFQSueroShouldBeFound("ph.greaterThan=" + SMALLER_PH);
    }


    @Test
    @Transactional
    public void getAllFQSuerosByCloroIsEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where cloro equals to DEFAULT_CLORO
        defaultFQSueroShouldBeFound("cloro.equals=" + DEFAULT_CLORO);

        // Get all the fQSueroList where cloro equals to UPDATED_CLORO
        defaultFQSueroShouldNotBeFound("cloro.equals=" + UPDATED_CLORO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByCloroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where cloro not equals to DEFAULT_CLORO
        defaultFQSueroShouldNotBeFound("cloro.notEquals=" + DEFAULT_CLORO);

        // Get all the fQSueroList where cloro not equals to UPDATED_CLORO
        defaultFQSueroShouldBeFound("cloro.notEquals=" + UPDATED_CLORO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByCloroIsInShouldWork() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where cloro in DEFAULT_CLORO or UPDATED_CLORO
        defaultFQSueroShouldBeFound("cloro.in=" + DEFAULT_CLORO + "," + UPDATED_CLORO);

        // Get all the fQSueroList where cloro equals to UPDATED_CLORO
        defaultFQSueroShouldNotBeFound("cloro.in=" + UPDATED_CLORO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByCloroIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where cloro is not null
        defaultFQSueroShouldBeFound("cloro.specified=true");

        // Get all the fQSueroList where cloro is null
        defaultFQSueroShouldNotBeFound("cloro.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQSuerosByCloroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where cloro is greater than or equal to DEFAULT_CLORO
        defaultFQSueroShouldBeFound("cloro.greaterThanOrEqual=" + DEFAULT_CLORO);

        // Get all the fQSueroList where cloro is greater than or equal to UPDATED_CLORO
        defaultFQSueroShouldNotBeFound("cloro.greaterThanOrEqual=" + UPDATED_CLORO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByCloroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where cloro is less than or equal to DEFAULT_CLORO
        defaultFQSueroShouldBeFound("cloro.lessThanOrEqual=" + DEFAULT_CLORO);

        // Get all the fQSueroList where cloro is less than or equal to SMALLER_CLORO
        defaultFQSueroShouldNotBeFound("cloro.lessThanOrEqual=" + SMALLER_CLORO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByCloroIsLessThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where cloro is less than DEFAULT_CLORO
        defaultFQSueroShouldNotBeFound("cloro.lessThan=" + DEFAULT_CLORO);

        // Get all the fQSueroList where cloro is less than UPDATED_CLORO
        defaultFQSueroShouldBeFound("cloro.lessThan=" + UPDATED_CLORO);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByCloroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where cloro is greater than DEFAULT_CLORO
        defaultFQSueroShouldNotBeFound("cloro.greaterThan=" + DEFAULT_CLORO);

        // Get all the fQSueroList where cloro is greater than SMALLER_CLORO
        defaultFQSueroShouldBeFound("cloro.greaterThan=" + SMALLER_CLORO);
    }


    @Test
    @Transactional
    public void getAllFQSuerosByAlmidonIsEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where almidon equals to DEFAULT_ALMIDON
        defaultFQSueroShouldBeFound("almidon.equals=" + DEFAULT_ALMIDON);

        // Get all the fQSueroList where almidon equals to UPDATED_ALMIDON
        defaultFQSueroShouldNotBeFound("almidon.equals=" + UPDATED_ALMIDON);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAlmidonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where almidon not equals to DEFAULT_ALMIDON
        defaultFQSueroShouldNotBeFound("almidon.notEquals=" + DEFAULT_ALMIDON);

        // Get all the fQSueroList where almidon not equals to UPDATED_ALMIDON
        defaultFQSueroShouldBeFound("almidon.notEquals=" + UPDATED_ALMIDON);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAlmidonIsInShouldWork() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where almidon in DEFAULT_ALMIDON or UPDATED_ALMIDON
        defaultFQSueroShouldBeFound("almidon.in=" + DEFAULT_ALMIDON + "," + UPDATED_ALMIDON);

        // Get all the fQSueroList where almidon equals to UPDATED_ALMIDON
        defaultFQSueroShouldNotBeFound("almidon.in=" + UPDATED_ALMIDON);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAlmidonIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where almidon is not null
        defaultFQSueroShouldBeFound("almidon.specified=true");

        // Get all the fQSueroList where almidon is null
        defaultFQSueroShouldNotBeFound("almidon.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAlmidonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where almidon is greater than or equal to DEFAULT_ALMIDON
        defaultFQSueroShouldBeFound("almidon.greaterThanOrEqual=" + DEFAULT_ALMIDON);

        // Get all the fQSueroList where almidon is greater than or equal to UPDATED_ALMIDON
        defaultFQSueroShouldNotBeFound("almidon.greaterThanOrEqual=" + UPDATED_ALMIDON);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAlmidonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where almidon is less than or equal to DEFAULT_ALMIDON
        defaultFQSueroShouldBeFound("almidon.lessThanOrEqual=" + DEFAULT_ALMIDON);

        // Get all the fQSueroList where almidon is less than or equal to SMALLER_ALMIDON
        defaultFQSueroShouldNotBeFound("almidon.lessThanOrEqual=" + SMALLER_ALMIDON);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAlmidonIsLessThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where almidon is less than DEFAULT_ALMIDON
        defaultFQSueroShouldNotBeFound("almidon.lessThan=" + DEFAULT_ALMIDON);

        // Get all the fQSueroList where almidon is less than UPDATED_ALMIDON
        defaultFQSueroShouldBeFound("almidon.lessThan=" + UPDATED_ALMIDON);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByAlmidonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where almidon is greater than DEFAULT_ALMIDON
        defaultFQSueroShouldNotBeFound("almidon.greaterThan=" + DEFAULT_ALMIDON);

        // Get all the fQSueroList where almidon is greater than SMALLER_ALMIDON
        defaultFQSueroShouldBeFound("almidon.greaterThan=" + SMALLER_ALMIDON);
    }


    @Test
    @Transactional
    public void getAllFQSuerosByObservacionesIsEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where observaciones equals to DEFAULT_OBSERVACIONES
        defaultFQSueroShouldBeFound("observaciones.equals=" + DEFAULT_OBSERVACIONES);

        // Get all the fQSueroList where observaciones equals to UPDATED_OBSERVACIONES
        defaultFQSueroShouldNotBeFound("observaciones.equals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByObservacionesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where observaciones not equals to DEFAULT_OBSERVACIONES
        defaultFQSueroShouldNotBeFound("observaciones.notEquals=" + DEFAULT_OBSERVACIONES);

        // Get all the fQSueroList where observaciones not equals to UPDATED_OBSERVACIONES
        defaultFQSueroShouldBeFound("observaciones.notEquals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByObservacionesIsInShouldWork() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where observaciones in DEFAULT_OBSERVACIONES or UPDATED_OBSERVACIONES
        defaultFQSueroShouldBeFound("observaciones.in=" + DEFAULT_OBSERVACIONES + "," + UPDATED_OBSERVACIONES);

        // Get all the fQSueroList where observaciones equals to UPDATED_OBSERVACIONES
        defaultFQSueroShouldNotBeFound("observaciones.in=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByObservacionesIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where observaciones is not null
        defaultFQSueroShouldBeFound("observaciones.specified=true");

        // Get all the fQSueroList where observaciones is null
        defaultFQSueroShouldNotBeFound("observaciones.specified=false");
    }
                @Test
    @Transactional
    public void getAllFQSuerosByObservacionesContainsSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where observaciones contains DEFAULT_OBSERVACIONES
        defaultFQSueroShouldBeFound("observaciones.contains=" + DEFAULT_OBSERVACIONES);

        // Get all the fQSueroList where observaciones contains UPDATED_OBSERVACIONES
        defaultFQSueroShouldNotBeFound("observaciones.contains=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQSuerosByObservacionesNotContainsSomething() throws Exception {
        // Initialize the database
        fQSueroRepository.saveAndFlush(fQSuero);

        // Get all the fQSueroList where observaciones does not contain DEFAULT_OBSERVACIONES
        defaultFQSueroShouldNotBeFound("observaciones.doesNotContain=" + DEFAULT_OBSERVACIONES);

        // Get all the fQSueroList where observaciones does not contain UPDATED_OBSERVACIONES
        defaultFQSueroShouldBeFound("observaciones.doesNotContain=" + UPDATED_OBSERVACIONES);
    }


    @Test
    @Transactional
    public void getAllFQSuerosByAreaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Area area = fQSuero.getArea();
        fQSueroRepository.saveAndFlush(fQSuero);
        Long areaId = area.getId();

        // Get all the fQSueroList where area equals to areaId
        defaultFQSueroShouldBeFound("areaId.equals=" + areaId);

        // Get all the fQSueroList where area equals to areaId + 1
        defaultFQSueroShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }


    @Test
    @Transactional
    public void getAllFQSuerosByProductoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Producto producto = fQSuero.getProducto();
        fQSueroRepository.saveAndFlush(fQSuero);
        Long productoId = producto.getId();

        // Get all the fQSueroList where producto equals to productoId
        defaultFQSueroShouldBeFound("productoId.equals=" + productoId);

        // Get all the fQSueroList where producto equals to productoId + 1
        defaultFQSueroShouldNotBeFound("productoId.equals=" + (productoId + 1));
    }


    @Test
    @Transactional
    public void getAllFQSuerosByAnalistaIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserExtra analista = fQSuero.getAnalista();
        fQSueroRepository.saveAndFlush(fQSuero);
        Long analistaId = analista.getId();

        // Get all the fQSueroList where analista equals to analistaId
        defaultFQSueroShouldBeFound("analistaId.equals=" + analistaId);

        // Get all the fQSueroList where analista equals to analistaId + 1
        defaultFQSueroShouldNotBeFound("analistaId.equals=" + (analistaId + 1));
    }


    @Test
    @Transactional
    public void getAllFQSuerosByProveedorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Personal proveedor = fQSuero.getProveedor();
        fQSueroRepository.saveAndFlush(fQSuero);
        Long proveedorId = proveedor.getId();

        // Get all the fQSueroList where proveedor equals to proveedorId
        defaultFQSueroShouldBeFound("proveedorId.equals=" + proveedorId);

        // Get all the fQSueroList where proveedor equals to proveedorId + 1
        defaultFQSueroShouldNotBeFound("proveedorId.equals=" + (proveedorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFQSueroShouldBeFound(String filter) throws Exception {
        restFQSueroMockMvc.perform(get("/api/fq-sueros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fQSuero.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].acidez").value(hasItem(DEFAULT_ACIDEZ.doubleValue())))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA.doubleValue())))
            .andExpect(jsonPath("$.[*].delvo").value(hasItem(DEFAULT_DELVO.doubleValue())))
            .andExpect(jsonPath("$.[*].solidos").value(hasItem(DEFAULT_SOLIDOS.doubleValue())))
            .andExpect(jsonPath("$.[*].neutralizantes").value(hasItem(DEFAULT_NEUTRALIZANTES.doubleValue())))
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.doubleValue())))
            .andExpect(jsonPath("$.[*].cloro").value(hasItem(DEFAULT_CLORO.doubleValue())))
            .andExpect(jsonPath("$.[*].almidon").value(hasItem(DEFAULT_ALMIDON.doubleValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));

        // Check, that the count call also returns 1
        restFQSueroMockMvc.perform(get("/api/fq-sueros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFQSueroShouldNotBeFound(String filter) throws Exception {
        restFQSueroMockMvc.perform(get("/api/fq-sueros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFQSueroMockMvc.perform(get("/api/fq-sueros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFQSuero() throws Exception {
        // Get the fQSuero
        restFQSueroMockMvc.perform(get("/api/fq-sueros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFQSuero() throws Exception {
        // Initialize the database
        fQSueroService.save(fQSuero);

        int databaseSizeBeforeUpdate = fQSueroRepository.findAll().size();

        // Update the fQSuero
        FQSuero updatedFQSuero = fQSueroRepository.findById(fQSuero.getId()).get();
        // Disconnect from session so that the updates on updatedFQSuero are not directly saved in db
        em.detach(updatedFQSuero);
        updatedFQSuero
            .fecha(UPDATED_FECHA)
            .lote(UPDATED_LOTE)
            .acidez(UPDATED_ACIDEZ)
            .temperatura(UPDATED_TEMPERATURA)
            .delvo(UPDATED_DELVO)
            .solidos(UPDATED_SOLIDOS)
            .neutralizantes(UPDATED_NEUTRALIZANTES)
            .ph(UPDATED_PH)
            .cloro(UPDATED_CLORO)
            .almidon(UPDATED_ALMIDON)
            .observaciones(UPDATED_OBSERVACIONES);

        restFQSueroMockMvc.perform(put("/api/fq-sueros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFQSuero)))
            .andExpect(status().isOk());

        // Validate the FQSuero in the database
        List<FQSuero> fQSueroList = fQSueroRepository.findAll();
        assertThat(fQSueroList).hasSize(databaseSizeBeforeUpdate);
        FQSuero testFQSuero = fQSueroList.get(fQSueroList.size() - 1);
        assertThat(testFQSuero.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFQSuero.getLote()).isEqualTo(UPDATED_LOTE);
        assertThat(testFQSuero.getAcidez()).isEqualTo(UPDATED_ACIDEZ);
        assertThat(testFQSuero.getTemperatura()).isEqualTo(UPDATED_TEMPERATURA);
        assertThat(testFQSuero.getDelvo()).isEqualTo(UPDATED_DELVO);
        assertThat(testFQSuero.getSolidos()).isEqualTo(UPDATED_SOLIDOS);
        assertThat(testFQSuero.getNeutralizantes()).isEqualTo(UPDATED_NEUTRALIZANTES);
        assertThat(testFQSuero.getPh()).isEqualTo(UPDATED_PH);
        assertThat(testFQSuero.getCloro()).isEqualTo(UPDATED_CLORO);
        assertThat(testFQSuero.getAlmidon()).isEqualTo(UPDATED_ALMIDON);
        assertThat(testFQSuero.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingFQSuero() throws Exception {
        int databaseSizeBeforeUpdate = fQSueroRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFQSueroMockMvc.perform(put("/api/fq-sueros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQSuero)))
            .andExpect(status().isBadRequest());

        // Validate the FQSuero in the database
        List<FQSuero> fQSueroList = fQSueroRepository.findAll();
        assertThat(fQSueroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFQSuero() throws Exception {
        // Initialize the database
        fQSueroService.save(fQSuero);

        int databaseSizeBeforeDelete = fQSueroRepository.findAll().size();

        // Delete the fQSuero
        restFQSueroMockMvc.perform(delete("/api/fq-sueros/{id}", fQSuero.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FQSuero> fQSueroList = fQSueroRepository.findAll();
        assertThat(fQSueroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
