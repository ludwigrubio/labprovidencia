package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.FQQueso;
import com.provi.lab.domain.Area;
import com.provi.lab.domain.Producto;
import com.provi.lab.domain.UserExtra;
import com.provi.lab.domain.Personal;
import com.provi.lab.domain.Contenedor;
import com.provi.lab.domain.Proceso;
import com.provi.lab.repository.FQQuesoRepository;
import com.provi.lab.service.FQQuesoService;
import com.provi.lab.service.dto.FQQuesoCriteria;
import com.provi.lab.service.FQQuesoQueryService;

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
 * Integration tests for the {@link FQQuesoResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FQQuesoResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_LOTE = "BBBBBBBBBB";

    private static final Double DEFAULT_HUMEDAD = 1D;
    private static final Double UPDATED_HUMEDAD = 2D;
    private static final Double SMALLER_HUMEDAD = 1D - 1D;

    private static final Double DEFAULT_PH = 1D;
    private static final Double UPDATED_PH = 2D;
    private static final Double SMALLER_PH = 1D - 1D;

    private static final Integer DEFAULT_FUNDICION = 1;
    private static final Integer UPDATED_FUNDICION = 2;
    private static final Integer SMALLER_FUNDICION = 1 - 1;

    private static final Integer DEFAULT_PRESENTACION = 1;
    private static final Integer UPDATED_PRESENTACION = 2;
    private static final Integer SMALLER_PRESENTACION = 1 - 1;

    private static final Instant DEFAULT_CADUCIDAD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CADUCIDAD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_APARIENCIA = 1;
    private static final Integer UPDATED_APARIENCIA = 2;
    private static final Integer SMALLER_APARIENCIA = 1 - 1;

    private static final Integer DEFAULT_SABOR = 1;
    private static final Integer UPDATED_SABOR = 2;
    private static final Integer SMALLER_SABOR = 1 - 1;

    private static final Integer DEFAULT_COLOR = 1;
    private static final Integer UPDATED_COLOR = 2;
    private static final Integer SMALLER_COLOR = 1 - 1;

    private static final Integer DEFAULT_OLOR = 1;
    private static final Integer UPDATED_OLOR = 2;
    private static final Integer SMALLER_OLOR = 1 - 1;

    private static final Integer DEFAULT_TEXTURA = 1;
    private static final Integer UPDATED_TEXTURA = 2;
    private static final Integer SMALLER_TEXTURA = 1 - 1;

    private static final Integer DEFAULT_HILADO = 1;
    private static final Integer UPDATED_HILADO = 2;
    private static final Integer SMALLER_HILADO = 1 - 1;

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
    private FQQuesoRepository fQQuesoRepository;

    @Autowired
    private FQQuesoService fQQuesoService;

    @Autowired
    private FQQuesoQueryService fQQuesoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFQQuesoMockMvc;

    private FQQueso fQQueso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FQQueso createEntity(EntityManager em) {
        FQQueso fQQueso = new FQQueso()
            .fecha(DEFAULT_FECHA)
            .lote(DEFAULT_LOTE)
            .humedad(DEFAULT_HUMEDAD)
            .ph(DEFAULT_PH)
            .fundicion(DEFAULT_FUNDICION)
            .presentacion(DEFAULT_PRESENTACION)
            .caducidad(DEFAULT_CADUCIDAD)
            .apariencia(DEFAULT_APARIENCIA)
            .sabor(DEFAULT_SABOR)
            .color(DEFAULT_COLOR)
            .olor(DEFAULT_OLOR)
            .textura(DEFAULT_TEXTURA)
            .hilado(DEFAULT_HILADO)
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
        fQQueso.setArea(area);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        fQQueso.setProducto(producto);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        fQQueso.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        fQQueso.setProveedor(personal);
        return fQQueso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FQQueso createUpdatedEntity(EntityManager em) {
        FQQueso fQQueso = new FQQueso()
            .fecha(UPDATED_FECHA)
            .lote(UPDATED_LOTE)
            .humedad(UPDATED_HUMEDAD)
            .ph(UPDATED_PH)
            .fundicion(UPDATED_FUNDICION)
            .presentacion(UPDATED_PRESENTACION)
            .caducidad(UPDATED_CADUCIDAD)
            .apariencia(UPDATED_APARIENCIA)
            .sabor(UPDATED_SABOR)
            .color(UPDATED_COLOR)
            .olor(UPDATED_OLOR)
            .textura(UPDATED_TEXTURA)
            .hilado(UPDATED_HILADO)
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
        fQQueso.setArea(area);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        fQQueso.setProducto(producto);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createUpdatedEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        fQQueso.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createUpdatedEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        fQQueso.setProveedor(personal);
        return fQQueso;
    }

    @BeforeEach
    public void initTest() {
        fQQueso = createEntity(em);
    }

    @Test
    @Transactional
    public void createFQQueso() throws Exception {
        int databaseSizeBeforeCreate = fQQuesoRepository.findAll().size();
        // Create the FQQueso
        restFQQuesoMockMvc.perform(post("/api/fq-quesos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQQueso)))
            .andExpect(status().isCreated());

        // Validate the FQQueso in the database
        List<FQQueso> fQQuesoList = fQQuesoRepository.findAll();
        assertThat(fQQuesoList).hasSize(databaseSizeBeforeCreate + 1);
        FQQueso testFQQueso = fQQuesoList.get(fQQuesoList.size() - 1);
        assertThat(testFQQueso.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testFQQueso.getLote()).isEqualTo(DEFAULT_LOTE);
        assertThat(testFQQueso.getHumedad()).isEqualTo(DEFAULT_HUMEDAD);
        assertThat(testFQQueso.getPh()).isEqualTo(DEFAULT_PH);
        assertThat(testFQQueso.getFundicion()).isEqualTo(DEFAULT_FUNDICION);
        assertThat(testFQQueso.getPresentacion()).isEqualTo(DEFAULT_PRESENTACION);
        assertThat(testFQQueso.getCaducidad()).isEqualTo(DEFAULT_CADUCIDAD);
        assertThat(testFQQueso.getApariencia()).isEqualTo(DEFAULT_APARIENCIA);
        assertThat(testFQQueso.getSabor()).isEqualTo(DEFAULT_SABOR);
        assertThat(testFQQueso.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testFQQueso.getOlor()).isEqualTo(DEFAULT_OLOR);
        assertThat(testFQQueso.getTextura()).isEqualTo(DEFAULT_TEXTURA);
        assertThat(testFQQueso.getHilado()).isEqualTo(DEFAULT_HILADO);
        assertThat(testFQQueso.getDummy1()).isEqualTo(DEFAULT_DUMMY_1);
        assertThat(testFQQueso.getDummy2()).isEqualTo(DEFAULT_DUMMY_2);
        assertThat(testFQQueso.getDummy3()).isEqualTo(DEFAULT_DUMMY_3);
        assertThat(testFQQueso.getDummy4()).isEqualTo(DEFAULT_DUMMY_4);
        assertThat(testFQQueso.getDummy5()).isEqualTo(DEFAULT_DUMMY_5);
        assertThat(testFQQueso.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void createFQQuesoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fQQuesoRepository.findAll().size();

        // Create the FQQueso with an existing ID
        fQQueso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFQQuesoMockMvc.perform(post("/api/fq-quesos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQQueso)))
            .andExpect(status().isBadRequest());

        // Validate the FQQueso in the database
        List<FQQueso> fQQuesoList = fQQuesoRepository.findAll();
        assertThat(fQQuesoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fQQuesoRepository.findAll().size();
        // set the field null
        fQQueso.setFecha(null);

        // Create the FQQueso, which fails.


        restFQQuesoMockMvc.perform(post("/api/fq-quesos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQQueso)))
            .andExpect(status().isBadRequest());

        List<FQQueso> fQQuesoList = fQQuesoRepository.findAll();
        assertThat(fQQuesoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = fQQuesoRepository.findAll().size();
        // set the field null
        fQQueso.setLote(null);

        // Create the FQQueso, which fails.


        restFQQuesoMockMvc.perform(post("/api/fq-quesos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQQueso)))
            .andExpect(status().isBadRequest());

        List<FQQueso> fQQuesoList = fQQuesoRepository.findAll();
        assertThat(fQQuesoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFQQuesos() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList
        restFQQuesoMockMvc.perform(get("/api/fq-quesos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fQQueso.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].humedad").value(hasItem(DEFAULT_HUMEDAD.doubleValue())))
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.doubleValue())))
            .andExpect(jsonPath("$.[*].fundicion").value(hasItem(DEFAULT_FUNDICION)))
            .andExpect(jsonPath("$.[*].presentacion").value(hasItem(DEFAULT_PRESENTACION)))
            .andExpect(jsonPath("$.[*].caducidad").value(hasItem(DEFAULT_CADUCIDAD.toString())))
            .andExpect(jsonPath("$.[*].apariencia").value(hasItem(DEFAULT_APARIENCIA)))
            .andExpect(jsonPath("$.[*].sabor").value(hasItem(DEFAULT_SABOR)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].olor").value(hasItem(DEFAULT_OLOR)))
            .andExpect(jsonPath("$.[*].textura").value(hasItem(DEFAULT_TEXTURA)))
            .andExpect(jsonPath("$.[*].hilado").value(hasItem(DEFAULT_HILADO)))
            .andExpect(jsonPath("$.[*].dummy1").value(hasItem(DEFAULT_DUMMY_1.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy2").value(hasItem(DEFAULT_DUMMY_2.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy3").value(hasItem(DEFAULT_DUMMY_3.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy4").value(hasItem(DEFAULT_DUMMY_4.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy5").value(hasItem(DEFAULT_DUMMY_5.doubleValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }
    
    @Test
    @Transactional
    public void getFQQueso() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get the fQQueso
        restFQQuesoMockMvc.perform(get("/api/fq-quesos/{id}", fQQueso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fQQueso.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.lote").value(DEFAULT_LOTE))
            .andExpect(jsonPath("$.humedad").value(DEFAULT_HUMEDAD.doubleValue()))
            .andExpect(jsonPath("$.ph").value(DEFAULT_PH.doubleValue()))
            .andExpect(jsonPath("$.fundicion").value(DEFAULT_FUNDICION))
            .andExpect(jsonPath("$.presentacion").value(DEFAULT_PRESENTACION))
            .andExpect(jsonPath("$.caducidad").value(DEFAULT_CADUCIDAD.toString()))
            .andExpect(jsonPath("$.apariencia").value(DEFAULT_APARIENCIA))
            .andExpect(jsonPath("$.sabor").value(DEFAULT_SABOR))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.olor").value(DEFAULT_OLOR))
            .andExpect(jsonPath("$.textura").value(DEFAULT_TEXTURA))
            .andExpect(jsonPath("$.hilado").value(DEFAULT_HILADO))
            .andExpect(jsonPath("$.dummy1").value(DEFAULT_DUMMY_1.doubleValue()))
            .andExpect(jsonPath("$.dummy2").value(DEFAULT_DUMMY_2.doubleValue()))
            .andExpect(jsonPath("$.dummy3").value(DEFAULT_DUMMY_3.doubleValue()))
            .andExpect(jsonPath("$.dummy4").value(DEFAULT_DUMMY_4.doubleValue()))
            .andExpect(jsonPath("$.dummy5").value(DEFAULT_DUMMY_5.doubleValue()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }


    @Test
    @Transactional
    public void getFQQuesosByIdFiltering() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        Long id = fQQueso.getId();

        defaultFQQuesoShouldBeFound("id.equals=" + id);
        defaultFQQuesoShouldNotBeFound("id.notEquals=" + id);

        defaultFQQuesoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFQQuesoShouldNotBeFound("id.greaterThan=" + id);

        defaultFQQuesoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFQQuesoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fecha equals to DEFAULT_FECHA
        defaultFQQuesoShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the fQQuesoList where fecha equals to UPDATED_FECHA
        defaultFQQuesoShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fecha not equals to DEFAULT_FECHA
        defaultFQQuesoShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the fQQuesoList where fecha not equals to UPDATED_FECHA
        defaultFQQuesoShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultFQQuesoShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the fQQuesoList where fecha equals to UPDATED_FECHA
        defaultFQQuesoShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fecha is not null
        defaultFQQuesoShouldBeFound("fecha.specified=true");

        // Get all the fQQuesoList where fecha is null
        defaultFQQuesoShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByLoteIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where lote equals to DEFAULT_LOTE
        defaultFQQuesoShouldBeFound("lote.equals=" + DEFAULT_LOTE);

        // Get all the fQQuesoList where lote equals to UPDATED_LOTE
        defaultFQQuesoShouldNotBeFound("lote.equals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByLoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where lote not equals to DEFAULT_LOTE
        defaultFQQuesoShouldNotBeFound("lote.notEquals=" + DEFAULT_LOTE);

        // Get all the fQQuesoList where lote not equals to UPDATED_LOTE
        defaultFQQuesoShouldBeFound("lote.notEquals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByLoteIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where lote in DEFAULT_LOTE or UPDATED_LOTE
        defaultFQQuesoShouldBeFound("lote.in=" + DEFAULT_LOTE + "," + UPDATED_LOTE);

        // Get all the fQQuesoList where lote equals to UPDATED_LOTE
        defaultFQQuesoShouldNotBeFound("lote.in=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByLoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where lote is not null
        defaultFQQuesoShouldBeFound("lote.specified=true");

        // Get all the fQQuesoList where lote is null
        defaultFQQuesoShouldNotBeFound("lote.specified=false");
    }
                @Test
    @Transactional
    public void getAllFQQuesosByLoteContainsSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where lote contains DEFAULT_LOTE
        defaultFQQuesoShouldBeFound("lote.contains=" + DEFAULT_LOTE);

        // Get all the fQQuesoList where lote contains UPDATED_LOTE
        defaultFQQuesoShouldNotBeFound("lote.contains=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByLoteNotContainsSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where lote does not contain DEFAULT_LOTE
        defaultFQQuesoShouldNotBeFound("lote.doesNotContain=" + DEFAULT_LOTE);

        // Get all the fQQuesoList where lote does not contain UPDATED_LOTE
        defaultFQQuesoShouldBeFound("lote.doesNotContain=" + UPDATED_LOTE);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByHumedadIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where humedad equals to DEFAULT_HUMEDAD
        defaultFQQuesoShouldBeFound("humedad.equals=" + DEFAULT_HUMEDAD);

        // Get all the fQQuesoList where humedad equals to UPDATED_HUMEDAD
        defaultFQQuesoShouldNotBeFound("humedad.equals=" + UPDATED_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHumedadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where humedad not equals to DEFAULT_HUMEDAD
        defaultFQQuesoShouldNotBeFound("humedad.notEquals=" + DEFAULT_HUMEDAD);

        // Get all the fQQuesoList where humedad not equals to UPDATED_HUMEDAD
        defaultFQQuesoShouldBeFound("humedad.notEquals=" + UPDATED_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHumedadIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where humedad in DEFAULT_HUMEDAD or UPDATED_HUMEDAD
        defaultFQQuesoShouldBeFound("humedad.in=" + DEFAULT_HUMEDAD + "," + UPDATED_HUMEDAD);

        // Get all the fQQuesoList where humedad equals to UPDATED_HUMEDAD
        defaultFQQuesoShouldNotBeFound("humedad.in=" + UPDATED_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHumedadIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where humedad is not null
        defaultFQQuesoShouldBeFound("humedad.specified=true");

        // Get all the fQQuesoList where humedad is null
        defaultFQQuesoShouldNotBeFound("humedad.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHumedadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where humedad is greater than or equal to DEFAULT_HUMEDAD
        defaultFQQuesoShouldBeFound("humedad.greaterThanOrEqual=" + DEFAULT_HUMEDAD);

        // Get all the fQQuesoList where humedad is greater than or equal to UPDATED_HUMEDAD
        defaultFQQuesoShouldNotBeFound("humedad.greaterThanOrEqual=" + UPDATED_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHumedadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where humedad is less than or equal to DEFAULT_HUMEDAD
        defaultFQQuesoShouldBeFound("humedad.lessThanOrEqual=" + DEFAULT_HUMEDAD);

        // Get all the fQQuesoList where humedad is less than or equal to SMALLER_HUMEDAD
        defaultFQQuesoShouldNotBeFound("humedad.lessThanOrEqual=" + SMALLER_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHumedadIsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where humedad is less than DEFAULT_HUMEDAD
        defaultFQQuesoShouldNotBeFound("humedad.lessThan=" + DEFAULT_HUMEDAD);

        // Get all the fQQuesoList where humedad is less than UPDATED_HUMEDAD
        defaultFQQuesoShouldBeFound("humedad.lessThan=" + UPDATED_HUMEDAD);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHumedadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where humedad is greater than DEFAULT_HUMEDAD
        defaultFQQuesoShouldNotBeFound("humedad.greaterThan=" + DEFAULT_HUMEDAD);

        // Get all the fQQuesoList where humedad is greater than SMALLER_HUMEDAD
        defaultFQQuesoShouldBeFound("humedad.greaterThan=" + SMALLER_HUMEDAD);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByPhIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where ph equals to DEFAULT_PH
        defaultFQQuesoShouldBeFound("ph.equals=" + DEFAULT_PH);

        // Get all the fQQuesoList where ph equals to UPDATED_PH
        defaultFQQuesoShouldNotBeFound("ph.equals=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where ph not equals to DEFAULT_PH
        defaultFQQuesoShouldNotBeFound("ph.notEquals=" + DEFAULT_PH);

        // Get all the fQQuesoList where ph not equals to UPDATED_PH
        defaultFQQuesoShouldBeFound("ph.notEquals=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPhIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where ph in DEFAULT_PH or UPDATED_PH
        defaultFQQuesoShouldBeFound("ph.in=" + DEFAULT_PH + "," + UPDATED_PH);

        // Get all the fQQuesoList where ph equals to UPDATED_PH
        defaultFQQuesoShouldNotBeFound("ph.in=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPhIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where ph is not null
        defaultFQQuesoShouldBeFound("ph.specified=true");

        // Get all the fQQuesoList where ph is null
        defaultFQQuesoShouldNotBeFound("ph.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where ph is greater than or equal to DEFAULT_PH
        defaultFQQuesoShouldBeFound("ph.greaterThanOrEqual=" + DEFAULT_PH);

        // Get all the fQQuesoList where ph is greater than or equal to UPDATED_PH
        defaultFQQuesoShouldNotBeFound("ph.greaterThanOrEqual=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where ph is less than or equal to DEFAULT_PH
        defaultFQQuesoShouldBeFound("ph.lessThanOrEqual=" + DEFAULT_PH);

        // Get all the fQQuesoList where ph is less than or equal to SMALLER_PH
        defaultFQQuesoShouldNotBeFound("ph.lessThanOrEqual=" + SMALLER_PH);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPhIsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where ph is less than DEFAULT_PH
        defaultFQQuesoShouldNotBeFound("ph.lessThan=" + DEFAULT_PH);

        // Get all the fQQuesoList where ph is less than UPDATED_PH
        defaultFQQuesoShouldBeFound("ph.lessThan=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where ph is greater than DEFAULT_PH
        defaultFQQuesoShouldNotBeFound("ph.greaterThan=" + DEFAULT_PH);

        // Get all the fQQuesoList where ph is greater than SMALLER_PH
        defaultFQQuesoShouldBeFound("ph.greaterThan=" + SMALLER_PH);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByFundicionIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fundicion equals to DEFAULT_FUNDICION
        defaultFQQuesoShouldBeFound("fundicion.equals=" + DEFAULT_FUNDICION);

        // Get all the fQQuesoList where fundicion equals to UPDATED_FUNDICION
        defaultFQQuesoShouldNotBeFound("fundicion.equals=" + UPDATED_FUNDICION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByFundicionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fundicion not equals to DEFAULT_FUNDICION
        defaultFQQuesoShouldNotBeFound("fundicion.notEquals=" + DEFAULT_FUNDICION);

        // Get all the fQQuesoList where fundicion not equals to UPDATED_FUNDICION
        defaultFQQuesoShouldBeFound("fundicion.notEquals=" + UPDATED_FUNDICION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByFundicionIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fundicion in DEFAULT_FUNDICION or UPDATED_FUNDICION
        defaultFQQuesoShouldBeFound("fundicion.in=" + DEFAULT_FUNDICION + "," + UPDATED_FUNDICION);

        // Get all the fQQuesoList where fundicion equals to UPDATED_FUNDICION
        defaultFQQuesoShouldNotBeFound("fundicion.in=" + UPDATED_FUNDICION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByFundicionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fundicion is not null
        defaultFQQuesoShouldBeFound("fundicion.specified=true");

        // Get all the fQQuesoList where fundicion is null
        defaultFQQuesoShouldNotBeFound("fundicion.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByFundicionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fundicion is greater than or equal to DEFAULT_FUNDICION
        defaultFQQuesoShouldBeFound("fundicion.greaterThanOrEqual=" + DEFAULT_FUNDICION);

        // Get all the fQQuesoList where fundicion is greater than or equal to UPDATED_FUNDICION
        defaultFQQuesoShouldNotBeFound("fundicion.greaterThanOrEqual=" + UPDATED_FUNDICION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByFundicionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fundicion is less than or equal to DEFAULT_FUNDICION
        defaultFQQuesoShouldBeFound("fundicion.lessThanOrEqual=" + DEFAULT_FUNDICION);

        // Get all the fQQuesoList where fundicion is less than or equal to SMALLER_FUNDICION
        defaultFQQuesoShouldNotBeFound("fundicion.lessThanOrEqual=" + SMALLER_FUNDICION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByFundicionIsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fundicion is less than DEFAULT_FUNDICION
        defaultFQQuesoShouldNotBeFound("fundicion.lessThan=" + DEFAULT_FUNDICION);

        // Get all the fQQuesoList where fundicion is less than UPDATED_FUNDICION
        defaultFQQuesoShouldBeFound("fundicion.lessThan=" + UPDATED_FUNDICION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByFundicionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where fundicion is greater than DEFAULT_FUNDICION
        defaultFQQuesoShouldNotBeFound("fundicion.greaterThan=" + DEFAULT_FUNDICION);

        // Get all the fQQuesoList where fundicion is greater than SMALLER_FUNDICION
        defaultFQQuesoShouldBeFound("fundicion.greaterThan=" + SMALLER_FUNDICION);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByPresentacionIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where presentacion equals to DEFAULT_PRESENTACION
        defaultFQQuesoShouldBeFound("presentacion.equals=" + DEFAULT_PRESENTACION);

        // Get all the fQQuesoList where presentacion equals to UPDATED_PRESENTACION
        defaultFQQuesoShouldNotBeFound("presentacion.equals=" + UPDATED_PRESENTACION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPresentacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where presentacion not equals to DEFAULT_PRESENTACION
        defaultFQQuesoShouldNotBeFound("presentacion.notEquals=" + DEFAULT_PRESENTACION);

        // Get all the fQQuesoList where presentacion not equals to UPDATED_PRESENTACION
        defaultFQQuesoShouldBeFound("presentacion.notEquals=" + UPDATED_PRESENTACION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPresentacionIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where presentacion in DEFAULT_PRESENTACION or UPDATED_PRESENTACION
        defaultFQQuesoShouldBeFound("presentacion.in=" + DEFAULT_PRESENTACION + "," + UPDATED_PRESENTACION);

        // Get all the fQQuesoList where presentacion equals to UPDATED_PRESENTACION
        defaultFQQuesoShouldNotBeFound("presentacion.in=" + UPDATED_PRESENTACION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPresentacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where presentacion is not null
        defaultFQQuesoShouldBeFound("presentacion.specified=true");

        // Get all the fQQuesoList where presentacion is null
        defaultFQQuesoShouldNotBeFound("presentacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPresentacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where presentacion is greater than or equal to DEFAULT_PRESENTACION
        defaultFQQuesoShouldBeFound("presentacion.greaterThanOrEqual=" + DEFAULT_PRESENTACION);

        // Get all the fQQuesoList where presentacion is greater than or equal to UPDATED_PRESENTACION
        defaultFQQuesoShouldNotBeFound("presentacion.greaterThanOrEqual=" + UPDATED_PRESENTACION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPresentacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where presentacion is less than or equal to DEFAULT_PRESENTACION
        defaultFQQuesoShouldBeFound("presentacion.lessThanOrEqual=" + DEFAULT_PRESENTACION);

        // Get all the fQQuesoList where presentacion is less than or equal to SMALLER_PRESENTACION
        defaultFQQuesoShouldNotBeFound("presentacion.lessThanOrEqual=" + SMALLER_PRESENTACION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPresentacionIsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where presentacion is less than DEFAULT_PRESENTACION
        defaultFQQuesoShouldNotBeFound("presentacion.lessThan=" + DEFAULT_PRESENTACION);

        // Get all the fQQuesoList where presentacion is less than UPDATED_PRESENTACION
        defaultFQQuesoShouldBeFound("presentacion.lessThan=" + UPDATED_PRESENTACION);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByPresentacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where presentacion is greater than DEFAULT_PRESENTACION
        defaultFQQuesoShouldNotBeFound("presentacion.greaterThan=" + DEFAULT_PRESENTACION);

        // Get all the fQQuesoList where presentacion is greater than SMALLER_PRESENTACION
        defaultFQQuesoShouldBeFound("presentacion.greaterThan=" + SMALLER_PRESENTACION);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByCaducidadIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where caducidad equals to DEFAULT_CADUCIDAD
        defaultFQQuesoShouldBeFound("caducidad.equals=" + DEFAULT_CADUCIDAD);

        // Get all the fQQuesoList where caducidad equals to UPDATED_CADUCIDAD
        defaultFQQuesoShouldNotBeFound("caducidad.equals=" + UPDATED_CADUCIDAD);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByCaducidadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where caducidad not equals to DEFAULT_CADUCIDAD
        defaultFQQuesoShouldNotBeFound("caducidad.notEquals=" + DEFAULT_CADUCIDAD);

        // Get all the fQQuesoList where caducidad not equals to UPDATED_CADUCIDAD
        defaultFQQuesoShouldBeFound("caducidad.notEquals=" + UPDATED_CADUCIDAD);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByCaducidadIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where caducidad in DEFAULT_CADUCIDAD or UPDATED_CADUCIDAD
        defaultFQQuesoShouldBeFound("caducidad.in=" + DEFAULT_CADUCIDAD + "," + UPDATED_CADUCIDAD);

        // Get all the fQQuesoList where caducidad equals to UPDATED_CADUCIDAD
        defaultFQQuesoShouldNotBeFound("caducidad.in=" + UPDATED_CADUCIDAD);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByCaducidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where caducidad is not null
        defaultFQQuesoShouldBeFound("caducidad.specified=true");

        // Get all the fQQuesoList where caducidad is null
        defaultFQQuesoShouldNotBeFound("caducidad.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByAparienciaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where apariencia equals to DEFAULT_APARIENCIA
        defaultFQQuesoShouldBeFound("apariencia.equals=" + DEFAULT_APARIENCIA);

        // Get all the fQQuesoList where apariencia equals to UPDATED_APARIENCIA
        defaultFQQuesoShouldNotBeFound("apariencia.equals=" + UPDATED_APARIENCIA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByAparienciaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where apariencia not equals to DEFAULT_APARIENCIA
        defaultFQQuesoShouldNotBeFound("apariencia.notEquals=" + DEFAULT_APARIENCIA);

        // Get all the fQQuesoList where apariencia not equals to UPDATED_APARIENCIA
        defaultFQQuesoShouldBeFound("apariencia.notEquals=" + UPDATED_APARIENCIA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByAparienciaIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where apariencia in DEFAULT_APARIENCIA or UPDATED_APARIENCIA
        defaultFQQuesoShouldBeFound("apariencia.in=" + DEFAULT_APARIENCIA + "," + UPDATED_APARIENCIA);

        // Get all the fQQuesoList where apariencia equals to UPDATED_APARIENCIA
        defaultFQQuesoShouldNotBeFound("apariencia.in=" + UPDATED_APARIENCIA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByAparienciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where apariencia is not null
        defaultFQQuesoShouldBeFound("apariencia.specified=true");

        // Get all the fQQuesoList where apariencia is null
        defaultFQQuesoShouldNotBeFound("apariencia.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByAparienciaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where apariencia is greater than or equal to DEFAULT_APARIENCIA
        defaultFQQuesoShouldBeFound("apariencia.greaterThanOrEqual=" + DEFAULT_APARIENCIA);

        // Get all the fQQuesoList where apariencia is greater than or equal to UPDATED_APARIENCIA
        defaultFQQuesoShouldNotBeFound("apariencia.greaterThanOrEqual=" + UPDATED_APARIENCIA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByAparienciaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where apariencia is less than or equal to DEFAULT_APARIENCIA
        defaultFQQuesoShouldBeFound("apariencia.lessThanOrEqual=" + DEFAULT_APARIENCIA);

        // Get all the fQQuesoList where apariencia is less than or equal to SMALLER_APARIENCIA
        defaultFQQuesoShouldNotBeFound("apariencia.lessThanOrEqual=" + SMALLER_APARIENCIA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByAparienciaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where apariencia is less than DEFAULT_APARIENCIA
        defaultFQQuesoShouldNotBeFound("apariencia.lessThan=" + DEFAULT_APARIENCIA);

        // Get all the fQQuesoList where apariencia is less than UPDATED_APARIENCIA
        defaultFQQuesoShouldBeFound("apariencia.lessThan=" + UPDATED_APARIENCIA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByAparienciaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where apariencia is greater than DEFAULT_APARIENCIA
        defaultFQQuesoShouldNotBeFound("apariencia.greaterThan=" + DEFAULT_APARIENCIA);

        // Get all the fQQuesoList where apariencia is greater than SMALLER_APARIENCIA
        defaultFQQuesoShouldBeFound("apariencia.greaterThan=" + SMALLER_APARIENCIA);
    }


    @Test
    @Transactional
    public void getAllFQQuesosBySaborIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where sabor equals to DEFAULT_SABOR
        defaultFQQuesoShouldBeFound("sabor.equals=" + DEFAULT_SABOR);

        // Get all the fQQuesoList where sabor equals to UPDATED_SABOR
        defaultFQQuesoShouldNotBeFound("sabor.equals=" + UPDATED_SABOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosBySaborIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where sabor not equals to DEFAULT_SABOR
        defaultFQQuesoShouldNotBeFound("sabor.notEquals=" + DEFAULT_SABOR);

        // Get all the fQQuesoList where sabor not equals to UPDATED_SABOR
        defaultFQQuesoShouldBeFound("sabor.notEquals=" + UPDATED_SABOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosBySaborIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where sabor in DEFAULT_SABOR or UPDATED_SABOR
        defaultFQQuesoShouldBeFound("sabor.in=" + DEFAULT_SABOR + "," + UPDATED_SABOR);

        // Get all the fQQuesoList where sabor equals to UPDATED_SABOR
        defaultFQQuesoShouldNotBeFound("sabor.in=" + UPDATED_SABOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosBySaborIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where sabor is not null
        defaultFQQuesoShouldBeFound("sabor.specified=true");

        // Get all the fQQuesoList where sabor is null
        defaultFQQuesoShouldNotBeFound("sabor.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosBySaborIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where sabor is greater than or equal to DEFAULT_SABOR
        defaultFQQuesoShouldBeFound("sabor.greaterThanOrEqual=" + DEFAULT_SABOR);

        // Get all the fQQuesoList where sabor is greater than or equal to UPDATED_SABOR
        defaultFQQuesoShouldNotBeFound("sabor.greaterThanOrEqual=" + UPDATED_SABOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosBySaborIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where sabor is less than or equal to DEFAULT_SABOR
        defaultFQQuesoShouldBeFound("sabor.lessThanOrEqual=" + DEFAULT_SABOR);

        // Get all the fQQuesoList where sabor is less than or equal to SMALLER_SABOR
        defaultFQQuesoShouldNotBeFound("sabor.lessThanOrEqual=" + SMALLER_SABOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosBySaborIsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where sabor is less than DEFAULT_SABOR
        defaultFQQuesoShouldNotBeFound("sabor.lessThan=" + DEFAULT_SABOR);

        // Get all the fQQuesoList where sabor is less than UPDATED_SABOR
        defaultFQQuesoShouldBeFound("sabor.lessThan=" + UPDATED_SABOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosBySaborIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where sabor is greater than DEFAULT_SABOR
        defaultFQQuesoShouldNotBeFound("sabor.greaterThan=" + DEFAULT_SABOR);

        // Get all the fQQuesoList where sabor is greater than SMALLER_SABOR
        defaultFQQuesoShouldBeFound("sabor.greaterThan=" + SMALLER_SABOR);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where color equals to DEFAULT_COLOR
        defaultFQQuesoShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the fQQuesoList where color equals to UPDATED_COLOR
        defaultFQQuesoShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where color not equals to DEFAULT_COLOR
        defaultFQQuesoShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the fQQuesoList where color not equals to UPDATED_COLOR
        defaultFQQuesoShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByColorIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultFQQuesoShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the fQQuesoList where color equals to UPDATED_COLOR
        defaultFQQuesoShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where color is not null
        defaultFQQuesoShouldBeFound("color.specified=true");

        // Get all the fQQuesoList where color is null
        defaultFQQuesoShouldNotBeFound("color.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByColorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where color is greater than or equal to DEFAULT_COLOR
        defaultFQQuesoShouldBeFound("color.greaterThanOrEqual=" + DEFAULT_COLOR);

        // Get all the fQQuesoList where color is greater than or equal to UPDATED_COLOR
        defaultFQQuesoShouldNotBeFound("color.greaterThanOrEqual=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByColorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where color is less than or equal to DEFAULT_COLOR
        defaultFQQuesoShouldBeFound("color.lessThanOrEqual=" + DEFAULT_COLOR);

        // Get all the fQQuesoList where color is less than or equal to SMALLER_COLOR
        defaultFQQuesoShouldNotBeFound("color.lessThanOrEqual=" + SMALLER_COLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByColorIsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where color is less than DEFAULT_COLOR
        defaultFQQuesoShouldNotBeFound("color.lessThan=" + DEFAULT_COLOR);

        // Get all the fQQuesoList where color is less than UPDATED_COLOR
        defaultFQQuesoShouldBeFound("color.lessThan=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByColorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where color is greater than DEFAULT_COLOR
        defaultFQQuesoShouldNotBeFound("color.greaterThan=" + DEFAULT_COLOR);

        // Get all the fQQuesoList where color is greater than SMALLER_COLOR
        defaultFQQuesoShouldBeFound("color.greaterThan=" + SMALLER_COLOR);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByOlorIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where olor equals to DEFAULT_OLOR
        defaultFQQuesoShouldBeFound("olor.equals=" + DEFAULT_OLOR);

        // Get all the fQQuesoList where olor equals to UPDATED_OLOR
        defaultFQQuesoShouldNotBeFound("olor.equals=" + UPDATED_OLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByOlorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where olor not equals to DEFAULT_OLOR
        defaultFQQuesoShouldNotBeFound("olor.notEquals=" + DEFAULT_OLOR);

        // Get all the fQQuesoList where olor not equals to UPDATED_OLOR
        defaultFQQuesoShouldBeFound("olor.notEquals=" + UPDATED_OLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByOlorIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where olor in DEFAULT_OLOR or UPDATED_OLOR
        defaultFQQuesoShouldBeFound("olor.in=" + DEFAULT_OLOR + "," + UPDATED_OLOR);

        // Get all the fQQuesoList where olor equals to UPDATED_OLOR
        defaultFQQuesoShouldNotBeFound("olor.in=" + UPDATED_OLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByOlorIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where olor is not null
        defaultFQQuesoShouldBeFound("olor.specified=true");

        // Get all the fQQuesoList where olor is null
        defaultFQQuesoShouldNotBeFound("olor.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByOlorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where olor is greater than or equal to DEFAULT_OLOR
        defaultFQQuesoShouldBeFound("olor.greaterThanOrEqual=" + DEFAULT_OLOR);

        // Get all the fQQuesoList where olor is greater than or equal to UPDATED_OLOR
        defaultFQQuesoShouldNotBeFound("olor.greaterThanOrEqual=" + UPDATED_OLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByOlorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where olor is less than or equal to DEFAULT_OLOR
        defaultFQQuesoShouldBeFound("olor.lessThanOrEqual=" + DEFAULT_OLOR);

        // Get all the fQQuesoList where olor is less than or equal to SMALLER_OLOR
        defaultFQQuesoShouldNotBeFound("olor.lessThanOrEqual=" + SMALLER_OLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByOlorIsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where olor is less than DEFAULT_OLOR
        defaultFQQuesoShouldNotBeFound("olor.lessThan=" + DEFAULT_OLOR);

        // Get all the fQQuesoList where olor is less than UPDATED_OLOR
        defaultFQQuesoShouldBeFound("olor.lessThan=" + UPDATED_OLOR);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByOlorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where olor is greater than DEFAULT_OLOR
        defaultFQQuesoShouldNotBeFound("olor.greaterThan=" + DEFAULT_OLOR);

        // Get all the fQQuesoList where olor is greater than SMALLER_OLOR
        defaultFQQuesoShouldBeFound("olor.greaterThan=" + SMALLER_OLOR);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByTexturaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where textura equals to DEFAULT_TEXTURA
        defaultFQQuesoShouldBeFound("textura.equals=" + DEFAULT_TEXTURA);

        // Get all the fQQuesoList where textura equals to UPDATED_TEXTURA
        defaultFQQuesoShouldNotBeFound("textura.equals=" + UPDATED_TEXTURA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByTexturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where textura not equals to DEFAULT_TEXTURA
        defaultFQQuesoShouldNotBeFound("textura.notEquals=" + DEFAULT_TEXTURA);

        // Get all the fQQuesoList where textura not equals to UPDATED_TEXTURA
        defaultFQQuesoShouldBeFound("textura.notEquals=" + UPDATED_TEXTURA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByTexturaIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where textura in DEFAULT_TEXTURA or UPDATED_TEXTURA
        defaultFQQuesoShouldBeFound("textura.in=" + DEFAULT_TEXTURA + "," + UPDATED_TEXTURA);

        // Get all the fQQuesoList where textura equals to UPDATED_TEXTURA
        defaultFQQuesoShouldNotBeFound("textura.in=" + UPDATED_TEXTURA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByTexturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where textura is not null
        defaultFQQuesoShouldBeFound("textura.specified=true");

        // Get all the fQQuesoList where textura is null
        defaultFQQuesoShouldNotBeFound("textura.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByTexturaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where textura is greater than or equal to DEFAULT_TEXTURA
        defaultFQQuesoShouldBeFound("textura.greaterThanOrEqual=" + DEFAULT_TEXTURA);

        // Get all the fQQuesoList where textura is greater than or equal to UPDATED_TEXTURA
        defaultFQQuesoShouldNotBeFound("textura.greaterThanOrEqual=" + UPDATED_TEXTURA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByTexturaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where textura is less than or equal to DEFAULT_TEXTURA
        defaultFQQuesoShouldBeFound("textura.lessThanOrEqual=" + DEFAULT_TEXTURA);

        // Get all the fQQuesoList where textura is less than or equal to SMALLER_TEXTURA
        defaultFQQuesoShouldNotBeFound("textura.lessThanOrEqual=" + SMALLER_TEXTURA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByTexturaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where textura is less than DEFAULT_TEXTURA
        defaultFQQuesoShouldNotBeFound("textura.lessThan=" + DEFAULT_TEXTURA);

        // Get all the fQQuesoList where textura is less than UPDATED_TEXTURA
        defaultFQQuesoShouldBeFound("textura.lessThan=" + UPDATED_TEXTURA);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByTexturaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where textura is greater than DEFAULT_TEXTURA
        defaultFQQuesoShouldNotBeFound("textura.greaterThan=" + DEFAULT_TEXTURA);

        // Get all the fQQuesoList where textura is greater than SMALLER_TEXTURA
        defaultFQQuesoShouldBeFound("textura.greaterThan=" + SMALLER_TEXTURA);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByHiladoIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where hilado equals to DEFAULT_HILADO
        defaultFQQuesoShouldBeFound("hilado.equals=" + DEFAULT_HILADO);

        // Get all the fQQuesoList where hilado equals to UPDATED_HILADO
        defaultFQQuesoShouldNotBeFound("hilado.equals=" + UPDATED_HILADO);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHiladoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where hilado not equals to DEFAULT_HILADO
        defaultFQQuesoShouldNotBeFound("hilado.notEquals=" + DEFAULT_HILADO);

        // Get all the fQQuesoList where hilado not equals to UPDATED_HILADO
        defaultFQQuesoShouldBeFound("hilado.notEquals=" + UPDATED_HILADO);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHiladoIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where hilado in DEFAULT_HILADO or UPDATED_HILADO
        defaultFQQuesoShouldBeFound("hilado.in=" + DEFAULT_HILADO + "," + UPDATED_HILADO);

        // Get all the fQQuesoList where hilado equals to UPDATED_HILADO
        defaultFQQuesoShouldNotBeFound("hilado.in=" + UPDATED_HILADO);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHiladoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where hilado is not null
        defaultFQQuesoShouldBeFound("hilado.specified=true");

        // Get all the fQQuesoList where hilado is null
        defaultFQQuesoShouldNotBeFound("hilado.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHiladoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where hilado is greater than or equal to DEFAULT_HILADO
        defaultFQQuesoShouldBeFound("hilado.greaterThanOrEqual=" + DEFAULT_HILADO);

        // Get all the fQQuesoList where hilado is greater than or equal to UPDATED_HILADO
        defaultFQQuesoShouldNotBeFound("hilado.greaterThanOrEqual=" + UPDATED_HILADO);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHiladoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where hilado is less than or equal to DEFAULT_HILADO
        defaultFQQuesoShouldBeFound("hilado.lessThanOrEqual=" + DEFAULT_HILADO);

        // Get all the fQQuesoList where hilado is less than or equal to SMALLER_HILADO
        defaultFQQuesoShouldNotBeFound("hilado.lessThanOrEqual=" + SMALLER_HILADO);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHiladoIsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where hilado is less than DEFAULT_HILADO
        defaultFQQuesoShouldNotBeFound("hilado.lessThan=" + DEFAULT_HILADO);

        // Get all the fQQuesoList where hilado is less than UPDATED_HILADO
        defaultFQQuesoShouldBeFound("hilado.lessThan=" + UPDATED_HILADO);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByHiladoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where hilado is greater than DEFAULT_HILADO
        defaultFQQuesoShouldNotBeFound("hilado.greaterThan=" + DEFAULT_HILADO);

        // Get all the fQQuesoList where hilado is greater than SMALLER_HILADO
        defaultFQQuesoShouldBeFound("hilado.greaterThan=" + SMALLER_HILADO);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByDummy1IsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy1 equals to DEFAULT_DUMMY_1
        defaultFQQuesoShouldBeFound("dummy1.equals=" + DEFAULT_DUMMY_1);

        // Get all the fQQuesoList where dummy1 equals to UPDATED_DUMMY_1
        defaultFQQuesoShouldNotBeFound("dummy1.equals=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy1 not equals to DEFAULT_DUMMY_1
        defaultFQQuesoShouldNotBeFound("dummy1.notEquals=" + DEFAULT_DUMMY_1);

        // Get all the fQQuesoList where dummy1 not equals to UPDATED_DUMMY_1
        defaultFQQuesoShouldBeFound("dummy1.notEquals=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy1IsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy1 in DEFAULT_DUMMY_1 or UPDATED_DUMMY_1
        defaultFQQuesoShouldBeFound("dummy1.in=" + DEFAULT_DUMMY_1 + "," + UPDATED_DUMMY_1);

        // Get all the fQQuesoList where dummy1 equals to UPDATED_DUMMY_1
        defaultFQQuesoShouldNotBeFound("dummy1.in=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy1IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy1 is not null
        defaultFQQuesoShouldBeFound("dummy1.specified=true");

        // Get all the fQQuesoList where dummy1 is null
        defaultFQQuesoShouldNotBeFound("dummy1.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy1 is greater than or equal to DEFAULT_DUMMY_1
        defaultFQQuesoShouldBeFound("dummy1.greaterThanOrEqual=" + DEFAULT_DUMMY_1);

        // Get all the fQQuesoList where dummy1 is greater than or equal to UPDATED_DUMMY_1
        defaultFQQuesoShouldNotBeFound("dummy1.greaterThanOrEqual=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy1 is less than or equal to DEFAULT_DUMMY_1
        defaultFQQuesoShouldBeFound("dummy1.lessThanOrEqual=" + DEFAULT_DUMMY_1);

        // Get all the fQQuesoList where dummy1 is less than or equal to SMALLER_DUMMY_1
        defaultFQQuesoShouldNotBeFound("dummy1.lessThanOrEqual=" + SMALLER_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy1IsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy1 is less than DEFAULT_DUMMY_1
        defaultFQQuesoShouldNotBeFound("dummy1.lessThan=" + DEFAULT_DUMMY_1);

        // Get all the fQQuesoList where dummy1 is less than UPDATED_DUMMY_1
        defaultFQQuesoShouldBeFound("dummy1.lessThan=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy1 is greater than DEFAULT_DUMMY_1
        defaultFQQuesoShouldNotBeFound("dummy1.greaterThan=" + DEFAULT_DUMMY_1);

        // Get all the fQQuesoList where dummy1 is greater than SMALLER_DUMMY_1
        defaultFQQuesoShouldBeFound("dummy1.greaterThan=" + SMALLER_DUMMY_1);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByDummy2IsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy2 equals to DEFAULT_DUMMY_2
        defaultFQQuesoShouldBeFound("dummy2.equals=" + DEFAULT_DUMMY_2);

        // Get all the fQQuesoList where dummy2 equals to UPDATED_DUMMY_2
        defaultFQQuesoShouldNotBeFound("dummy2.equals=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy2 not equals to DEFAULT_DUMMY_2
        defaultFQQuesoShouldNotBeFound("dummy2.notEquals=" + DEFAULT_DUMMY_2);

        // Get all the fQQuesoList where dummy2 not equals to UPDATED_DUMMY_2
        defaultFQQuesoShouldBeFound("dummy2.notEquals=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy2IsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy2 in DEFAULT_DUMMY_2 or UPDATED_DUMMY_2
        defaultFQQuesoShouldBeFound("dummy2.in=" + DEFAULT_DUMMY_2 + "," + UPDATED_DUMMY_2);

        // Get all the fQQuesoList where dummy2 equals to UPDATED_DUMMY_2
        defaultFQQuesoShouldNotBeFound("dummy2.in=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy2IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy2 is not null
        defaultFQQuesoShouldBeFound("dummy2.specified=true");

        // Get all the fQQuesoList where dummy2 is null
        defaultFQQuesoShouldNotBeFound("dummy2.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy2 is greater than or equal to DEFAULT_DUMMY_2
        defaultFQQuesoShouldBeFound("dummy2.greaterThanOrEqual=" + DEFAULT_DUMMY_2);

        // Get all the fQQuesoList where dummy2 is greater than or equal to UPDATED_DUMMY_2
        defaultFQQuesoShouldNotBeFound("dummy2.greaterThanOrEqual=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy2 is less than or equal to DEFAULT_DUMMY_2
        defaultFQQuesoShouldBeFound("dummy2.lessThanOrEqual=" + DEFAULT_DUMMY_2);

        // Get all the fQQuesoList where dummy2 is less than or equal to SMALLER_DUMMY_2
        defaultFQQuesoShouldNotBeFound("dummy2.lessThanOrEqual=" + SMALLER_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy2IsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy2 is less than DEFAULT_DUMMY_2
        defaultFQQuesoShouldNotBeFound("dummy2.lessThan=" + DEFAULT_DUMMY_2);

        // Get all the fQQuesoList where dummy2 is less than UPDATED_DUMMY_2
        defaultFQQuesoShouldBeFound("dummy2.lessThan=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy2 is greater than DEFAULT_DUMMY_2
        defaultFQQuesoShouldNotBeFound("dummy2.greaterThan=" + DEFAULT_DUMMY_2);

        // Get all the fQQuesoList where dummy2 is greater than SMALLER_DUMMY_2
        defaultFQQuesoShouldBeFound("dummy2.greaterThan=" + SMALLER_DUMMY_2);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByDummy3IsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy3 equals to DEFAULT_DUMMY_3
        defaultFQQuesoShouldBeFound("dummy3.equals=" + DEFAULT_DUMMY_3);

        // Get all the fQQuesoList where dummy3 equals to UPDATED_DUMMY_3
        defaultFQQuesoShouldNotBeFound("dummy3.equals=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy3 not equals to DEFAULT_DUMMY_3
        defaultFQQuesoShouldNotBeFound("dummy3.notEquals=" + DEFAULT_DUMMY_3);

        // Get all the fQQuesoList where dummy3 not equals to UPDATED_DUMMY_3
        defaultFQQuesoShouldBeFound("dummy3.notEquals=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy3IsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy3 in DEFAULT_DUMMY_3 or UPDATED_DUMMY_3
        defaultFQQuesoShouldBeFound("dummy3.in=" + DEFAULT_DUMMY_3 + "," + UPDATED_DUMMY_3);

        // Get all the fQQuesoList where dummy3 equals to UPDATED_DUMMY_3
        defaultFQQuesoShouldNotBeFound("dummy3.in=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy3IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy3 is not null
        defaultFQQuesoShouldBeFound("dummy3.specified=true");

        // Get all the fQQuesoList where dummy3 is null
        defaultFQQuesoShouldNotBeFound("dummy3.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy3 is greater than or equal to DEFAULT_DUMMY_3
        defaultFQQuesoShouldBeFound("dummy3.greaterThanOrEqual=" + DEFAULT_DUMMY_3);

        // Get all the fQQuesoList where dummy3 is greater than or equal to UPDATED_DUMMY_3
        defaultFQQuesoShouldNotBeFound("dummy3.greaterThanOrEqual=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy3 is less than or equal to DEFAULT_DUMMY_3
        defaultFQQuesoShouldBeFound("dummy3.lessThanOrEqual=" + DEFAULT_DUMMY_3);

        // Get all the fQQuesoList where dummy3 is less than or equal to SMALLER_DUMMY_3
        defaultFQQuesoShouldNotBeFound("dummy3.lessThanOrEqual=" + SMALLER_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy3IsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy3 is less than DEFAULT_DUMMY_3
        defaultFQQuesoShouldNotBeFound("dummy3.lessThan=" + DEFAULT_DUMMY_3);

        // Get all the fQQuesoList where dummy3 is less than UPDATED_DUMMY_3
        defaultFQQuesoShouldBeFound("dummy3.lessThan=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy3 is greater than DEFAULT_DUMMY_3
        defaultFQQuesoShouldNotBeFound("dummy3.greaterThan=" + DEFAULT_DUMMY_3);

        // Get all the fQQuesoList where dummy3 is greater than SMALLER_DUMMY_3
        defaultFQQuesoShouldBeFound("dummy3.greaterThan=" + SMALLER_DUMMY_3);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByDummy4IsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy4 equals to DEFAULT_DUMMY_4
        defaultFQQuesoShouldBeFound("dummy4.equals=" + DEFAULT_DUMMY_4);

        // Get all the fQQuesoList where dummy4 equals to UPDATED_DUMMY_4
        defaultFQQuesoShouldNotBeFound("dummy4.equals=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy4 not equals to DEFAULT_DUMMY_4
        defaultFQQuesoShouldNotBeFound("dummy4.notEquals=" + DEFAULT_DUMMY_4);

        // Get all the fQQuesoList where dummy4 not equals to UPDATED_DUMMY_4
        defaultFQQuesoShouldBeFound("dummy4.notEquals=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy4IsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy4 in DEFAULT_DUMMY_4 or UPDATED_DUMMY_4
        defaultFQQuesoShouldBeFound("dummy4.in=" + DEFAULT_DUMMY_4 + "," + UPDATED_DUMMY_4);

        // Get all the fQQuesoList where dummy4 equals to UPDATED_DUMMY_4
        defaultFQQuesoShouldNotBeFound("dummy4.in=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy4IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy4 is not null
        defaultFQQuesoShouldBeFound("dummy4.specified=true");

        // Get all the fQQuesoList where dummy4 is null
        defaultFQQuesoShouldNotBeFound("dummy4.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy4 is greater than or equal to DEFAULT_DUMMY_4
        defaultFQQuesoShouldBeFound("dummy4.greaterThanOrEqual=" + DEFAULT_DUMMY_4);

        // Get all the fQQuesoList where dummy4 is greater than or equal to UPDATED_DUMMY_4
        defaultFQQuesoShouldNotBeFound("dummy4.greaterThanOrEqual=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy4 is less than or equal to DEFAULT_DUMMY_4
        defaultFQQuesoShouldBeFound("dummy4.lessThanOrEqual=" + DEFAULT_DUMMY_4);

        // Get all the fQQuesoList where dummy4 is less than or equal to SMALLER_DUMMY_4
        defaultFQQuesoShouldNotBeFound("dummy4.lessThanOrEqual=" + SMALLER_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy4IsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy4 is less than DEFAULT_DUMMY_4
        defaultFQQuesoShouldNotBeFound("dummy4.lessThan=" + DEFAULT_DUMMY_4);

        // Get all the fQQuesoList where dummy4 is less than UPDATED_DUMMY_4
        defaultFQQuesoShouldBeFound("dummy4.lessThan=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy4 is greater than DEFAULT_DUMMY_4
        defaultFQQuesoShouldNotBeFound("dummy4.greaterThan=" + DEFAULT_DUMMY_4);

        // Get all the fQQuesoList where dummy4 is greater than SMALLER_DUMMY_4
        defaultFQQuesoShouldBeFound("dummy4.greaterThan=" + SMALLER_DUMMY_4);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByDummy5IsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy5 equals to DEFAULT_DUMMY_5
        defaultFQQuesoShouldBeFound("dummy5.equals=" + DEFAULT_DUMMY_5);

        // Get all the fQQuesoList where dummy5 equals to UPDATED_DUMMY_5
        defaultFQQuesoShouldNotBeFound("dummy5.equals=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy5 not equals to DEFAULT_DUMMY_5
        defaultFQQuesoShouldNotBeFound("dummy5.notEquals=" + DEFAULT_DUMMY_5);

        // Get all the fQQuesoList where dummy5 not equals to UPDATED_DUMMY_5
        defaultFQQuesoShouldBeFound("dummy5.notEquals=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy5IsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy5 in DEFAULT_DUMMY_5 or UPDATED_DUMMY_5
        defaultFQQuesoShouldBeFound("dummy5.in=" + DEFAULT_DUMMY_5 + "," + UPDATED_DUMMY_5);

        // Get all the fQQuesoList where dummy5 equals to UPDATED_DUMMY_5
        defaultFQQuesoShouldNotBeFound("dummy5.in=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy5IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy5 is not null
        defaultFQQuesoShouldBeFound("dummy5.specified=true");

        // Get all the fQQuesoList where dummy5 is null
        defaultFQQuesoShouldNotBeFound("dummy5.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy5 is greater than or equal to DEFAULT_DUMMY_5
        defaultFQQuesoShouldBeFound("dummy5.greaterThanOrEqual=" + DEFAULT_DUMMY_5);

        // Get all the fQQuesoList where dummy5 is greater than or equal to UPDATED_DUMMY_5
        defaultFQQuesoShouldNotBeFound("dummy5.greaterThanOrEqual=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy5 is less than or equal to DEFAULT_DUMMY_5
        defaultFQQuesoShouldBeFound("dummy5.lessThanOrEqual=" + DEFAULT_DUMMY_5);

        // Get all the fQQuesoList where dummy5 is less than or equal to SMALLER_DUMMY_5
        defaultFQQuesoShouldNotBeFound("dummy5.lessThanOrEqual=" + SMALLER_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy5IsLessThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy5 is less than DEFAULT_DUMMY_5
        defaultFQQuesoShouldNotBeFound("dummy5.lessThan=" + DEFAULT_DUMMY_5);

        // Get all the fQQuesoList where dummy5 is less than UPDATED_DUMMY_5
        defaultFQQuesoShouldBeFound("dummy5.lessThan=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByDummy5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where dummy5 is greater than DEFAULT_DUMMY_5
        defaultFQQuesoShouldNotBeFound("dummy5.greaterThan=" + DEFAULT_DUMMY_5);

        // Get all the fQQuesoList where dummy5 is greater than SMALLER_DUMMY_5
        defaultFQQuesoShouldBeFound("dummy5.greaterThan=" + SMALLER_DUMMY_5);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByObservacionesIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where observaciones equals to DEFAULT_OBSERVACIONES
        defaultFQQuesoShouldBeFound("observaciones.equals=" + DEFAULT_OBSERVACIONES);

        // Get all the fQQuesoList where observaciones equals to UPDATED_OBSERVACIONES
        defaultFQQuesoShouldNotBeFound("observaciones.equals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByObservacionesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where observaciones not equals to DEFAULT_OBSERVACIONES
        defaultFQQuesoShouldNotBeFound("observaciones.notEquals=" + DEFAULT_OBSERVACIONES);

        // Get all the fQQuesoList where observaciones not equals to UPDATED_OBSERVACIONES
        defaultFQQuesoShouldBeFound("observaciones.notEquals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByObservacionesIsInShouldWork() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where observaciones in DEFAULT_OBSERVACIONES or UPDATED_OBSERVACIONES
        defaultFQQuesoShouldBeFound("observaciones.in=" + DEFAULT_OBSERVACIONES + "," + UPDATED_OBSERVACIONES);

        // Get all the fQQuesoList where observaciones equals to UPDATED_OBSERVACIONES
        defaultFQQuesoShouldNotBeFound("observaciones.in=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByObservacionesIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where observaciones is not null
        defaultFQQuesoShouldBeFound("observaciones.specified=true");

        // Get all the fQQuesoList where observaciones is null
        defaultFQQuesoShouldNotBeFound("observaciones.specified=false");
    }
                @Test
    @Transactional
    public void getAllFQQuesosByObservacionesContainsSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where observaciones contains DEFAULT_OBSERVACIONES
        defaultFQQuesoShouldBeFound("observaciones.contains=" + DEFAULT_OBSERVACIONES);

        // Get all the fQQuesoList where observaciones contains UPDATED_OBSERVACIONES
        defaultFQQuesoShouldNotBeFound("observaciones.contains=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQQuesosByObservacionesNotContainsSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);

        // Get all the fQQuesoList where observaciones does not contain DEFAULT_OBSERVACIONES
        defaultFQQuesoShouldNotBeFound("observaciones.doesNotContain=" + DEFAULT_OBSERVACIONES);

        // Get all the fQQuesoList where observaciones does not contain UPDATED_OBSERVACIONES
        defaultFQQuesoShouldBeFound("observaciones.doesNotContain=" + UPDATED_OBSERVACIONES);
    }


    @Test
    @Transactional
    public void getAllFQQuesosByAreaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Area area = fQQueso.getArea();
        fQQuesoRepository.saveAndFlush(fQQueso);
        Long areaId = area.getId();

        // Get all the fQQuesoList where area equals to areaId
        defaultFQQuesoShouldBeFound("areaId.equals=" + areaId);

        // Get all the fQQuesoList where area equals to areaId + 1
        defaultFQQuesoShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }


    @Test
    @Transactional
    public void getAllFQQuesosByProductoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Producto producto = fQQueso.getProducto();
        fQQuesoRepository.saveAndFlush(fQQueso);
        Long productoId = producto.getId();

        // Get all the fQQuesoList where producto equals to productoId
        defaultFQQuesoShouldBeFound("productoId.equals=" + productoId);

        // Get all the fQQuesoList where producto equals to productoId + 1
        defaultFQQuesoShouldNotBeFound("productoId.equals=" + (productoId + 1));
    }


    @Test
    @Transactional
    public void getAllFQQuesosByAnalistaIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserExtra analista = fQQueso.getAnalista();
        fQQuesoRepository.saveAndFlush(fQQueso);
        Long analistaId = analista.getId();

        // Get all the fQQuesoList where analista equals to analistaId
        defaultFQQuesoShouldBeFound("analistaId.equals=" + analistaId);

        // Get all the fQQuesoList where analista equals to analistaId + 1
        defaultFQQuesoShouldNotBeFound("analistaId.equals=" + (analistaId + 1));
    }


    @Test
    @Transactional
    public void getAllFQQuesosByProveedorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Personal proveedor = fQQueso.getProveedor();
        fQQuesoRepository.saveAndFlush(fQQueso);
        Long proveedorId = proveedor.getId();

        // Get all the fQQuesoList where proveedor equals to proveedorId
        defaultFQQuesoShouldBeFound("proveedorId.equals=" + proveedorId);

        // Get all the fQQuesoList where proveedor equals to proveedorId + 1
        defaultFQQuesoShouldNotBeFound("proveedorId.equals=" + (proveedorId + 1));
    }


    @Test
    @Transactional
    public void getAllFQQuesosByContenedorIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);
        Contenedor contenedor = ContenedorResourceIT.createEntity(em);
        em.persist(contenedor);
        em.flush();
        fQQueso.setContenedor(contenedor);
        fQQuesoRepository.saveAndFlush(fQQueso);
        Long contenedorId = contenedor.getId();

        // Get all the fQQuesoList where contenedor equals to contenedorId
        defaultFQQuesoShouldBeFound("contenedorId.equals=" + contenedorId);

        // Get all the fQQuesoList where contenedor equals to contenedorId + 1
        defaultFQQuesoShouldNotBeFound("contenedorId.equals=" + (contenedorId + 1));
    }


    @Test
    @Transactional
    public void getAllFQQuesosByProcesoIsEqualToSomething() throws Exception {
        // Initialize the database
        fQQuesoRepository.saveAndFlush(fQQueso);
        Proceso proceso = ProcesoResourceIT.createEntity(em);
        em.persist(proceso);
        em.flush();
        fQQueso.setProceso(proceso);
        fQQuesoRepository.saveAndFlush(fQQueso);
        Long procesoId = proceso.getId();

        // Get all the fQQuesoList where proceso equals to procesoId
        defaultFQQuesoShouldBeFound("procesoId.equals=" + procesoId);

        // Get all the fQQuesoList where proceso equals to procesoId + 1
        defaultFQQuesoShouldNotBeFound("procesoId.equals=" + (procesoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFQQuesoShouldBeFound(String filter) throws Exception {
        restFQQuesoMockMvc.perform(get("/api/fq-quesos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fQQueso.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].humedad").value(hasItem(DEFAULT_HUMEDAD.doubleValue())))
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.doubleValue())))
            .andExpect(jsonPath("$.[*].fundicion").value(hasItem(DEFAULT_FUNDICION)))
            .andExpect(jsonPath("$.[*].presentacion").value(hasItem(DEFAULT_PRESENTACION)))
            .andExpect(jsonPath("$.[*].caducidad").value(hasItem(DEFAULT_CADUCIDAD.toString())))
            .andExpect(jsonPath("$.[*].apariencia").value(hasItem(DEFAULT_APARIENCIA)))
            .andExpect(jsonPath("$.[*].sabor").value(hasItem(DEFAULT_SABOR)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].olor").value(hasItem(DEFAULT_OLOR)))
            .andExpect(jsonPath("$.[*].textura").value(hasItem(DEFAULT_TEXTURA)))
            .andExpect(jsonPath("$.[*].hilado").value(hasItem(DEFAULT_HILADO)))
            .andExpect(jsonPath("$.[*].dummy1").value(hasItem(DEFAULT_DUMMY_1.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy2").value(hasItem(DEFAULT_DUMMY_2.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy3").value(hasItem(DEFAULT_DUMMY_3.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy4").value(hasItem(DEFAULT_DUMMY_4.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy5").value(hasItem(DEFAULT_DUMMY_5.doubleValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));

        // Check, that the count call also returns 1
        restFQQuesoMockMvc.perform(get("/api/fq-quesos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFQQuesoShouldNotBeFound(String filter) throws Exception {
        restFQQuesoMockMvc.perform(get("/api/fq-quesos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFQQuesoMockMvc.perform(get("/api/fq-quesos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFQQueso() throws Exception {
        // Get the fQQueso
        restFQQuesoMockMvc.perform(get("/api/fq-quesos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFQQueso() throws Exception {
        // Initialize the database
        fQQuesoService.save(fQQueso);

        int databaseSizeBeforeUpdate = fQQuesoRepository.findAll().size();

        // Update the fQQueso
        FQQueso updatedFQQueso = fQQuesoRepository.findById(fQQueso.getId()).get();
        // Disconnect from session so that the updates on updatedFQQueso are not directly saved in db
        em.detach(updatedFQQueso);
        updatedFQQueso
            .fecha(UPDATED_FECHA)
            .lote(UPDATED_LOTE)
            .humedad(UPDATED_HUMEDAD)
            .ph(UPDATED_PH)
            .fundicion(UPDATED_FUNDICION)
            .presentacion(UPDATED_PRESENTACION)
            .caducidad(UPDATED_CADUCIDAD)
            .apariencia(UPDATED_APARIENCIA)
            .sabor(UPDATED_SABOR)
            .color(UPDATED_COLOR)
            .olor(UPDATED_OLOR)
            .textura(UPDATED_TEXTURA)
            .hilado(UPDATED_HILADO)
            .dummy1(UPDATED_DUMMY_1)
            .dummy2(UPDATED_DUMMY_2)
            .dummy3(UPDATED_DUMMY_3)
            .dummy4(UPDATED_DUMMY_4)
            .dummy5(UPDATED_DUMMY_5)
            .observaciones(UPDATED_OBSERVACIONES);

        restFQQuesoMockMvc.perform(put("/api/fq-quesos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFQQueso)))
            .andExpect(status().isOk());

        // Validate the FQQueso in the database
        List<FQQueso> fQQuesoList = fQQuesoRepository.findAll();
        assertThat(fQQuesoList).hasSize(databaseSizeBeforeUpdate);
        FQQueso testFQQueso = fQQuesoList.get(fQQuesoList.size() - 1);
        assertThat(testFQQueso.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFQQueso.getLote()).isEqualTo(UPDATED_LOTE);
        assertThat(testFQQueso.getHumedad()).isEqualTo(UPDATED_HUMEDAD);
        assertThat(testFQQueso.getPh()).isEqualTo(UPDATED_PH);
        assertThat(testFQQueso.getFundicion()).isEqualTo(UPDATED_FUNDICION);
        assertThat(testFQQueso.getPresentacion()).isEqualTo(UPDATED_PRESENTACION);
        assertThat(testFQQueso.getCaducidad()).isEqualTo(UPDATED_CADUCIDAD);
        assertThat(testFQQueso.getApariencia()).isEqualTo(UPDATED_APARIENCIA);
        assertThat(testFQQueso.getSabor()).isEqualTo(UPDATED_SABOR);
        assertThat(testFQQueso.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testFQQueso.getOlor()).isEqualTo(UPDATED_OLOR);
        assertThat(testFQQueso.getTextura()).isEqualTo(UPDATED_TEXTURA);
        assertThat(testFQQueso.getHilado()).isEqualTo(UPDATED_HILADO);
        assertThat(testFQQueso.getDummy1()).isEqualTo(UPDATED_DUMMY_1);
        assertThat(testFQQueso.getDummy2()).isEqualTo(UPDATED_DUMMY_2);
        assertThat(testFQQueso.getDummy3()).isEqualTo(UPDATED_DUMMY_3);
        assertThat(testFQQueso.getDummy4()).isEqualTo(UPDATED_DUMMY_4);
        assertThat(testFQQueso.getDummy5()).isEqualTo(UPDATED_DUMMY_5);
        assertThat(testFQQueso.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingFQQueso() throws Exception {
        int databaseSizeBeforeUpdate = fQQuesoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFQQuesoMockMvc.perform(put("/api/fq-quesos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQQueso)))
            .andExpect(status().isBadRequest());

        // Validate the FQQueso in the database
        List<FQQueso> fQQuesoList = fQQuesoRepository.findAll();
        assertThat(fQQuesoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFQQueso() throws Exception {
        // Initialize the database
        fQQuesoService.save(fQQueso);

        int databaseSizeBeforeDelete = fQQuesoRepository.findAll().size();

        // Delete the fQQueso
        restFQQuesoMockMvc.perform(delete("/api/fq-quesos/{id}", fQQueso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FQQueso> fQQuesoList = fQQuesoRepository.findAll();
        assertThat(fQQuesoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
