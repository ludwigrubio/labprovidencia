package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.FQLeche;
import com.provi.lab.domain.Area;
import com.provi.lab.domain.Recepcion;
import com.provi.lab.domain.UserExtra;
import com.provi.lab.domain.Personal;
import com.provi.lab.repository.FQLecheRepository;
import com.provi.lab.service.FQLecheService;
import com.provi.lab.service.dto.FQLecheCriteria;
import com.provi.lab.service.FQLecheQueryService;

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
 * Integration tests for the {@link FQLecheResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FQLecheResourceIT {

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

    private static final Double DEFAULT_AGUA = 1D;
    private static final Double UPDATED_AGUA = 2D;
    private static final Double SMALLER_AGUA = 1D - 1D;

    private static final Double DEFAULT_CRIOSCOPIA = 1D;
    private static final Double UPDATED_CRIOSCOPIA = 2D;
    private static final Double SMALLER_CRIOSCOPIA = 1D - 1D;

    private static final Integer DEFAULT_ANTIBIOTICO = 1;
    private static final Integer UPDATED_ANTIBIOTICO = 2;
    private static final Integer SMALLER_ANTIBIOTICO = 1 - 1;

    private static final Double DEFAULT_DELVO = 1D;
    private static final Double UPDATED_DELVO = 2D;
    private static final Double SMALLER_DELVO = 1D - 1D;

    private static final Double DEFAULT_GRASA = 1D;
    private static final Double UPDATED_GRASA = 2D;
    private static final Double SMALLER_GRASA = 1D - 1D;

    private static final String DEFAULT_SOLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_SOLIDOS = "BBBBBBBBBB";

    private static final Double DEFAULT_DENSIDAD = 1D;
    private static final Double UPDATED_DENSIDAD = 2D;
    private static final Double SMALLER_DENSIDAD = 1D - 1D;

    private static final Double DEFAULT_LACTOSA = 1D;
    private static final Double UPDATED_LACTOSA = 2D;
    private static final Double SMALLER_LACTOSA = 1D - 1D;

    private static final Double DEFAULT_PROTEINA = 1D;
    private static final Double UPDATED_PROTEINA = 2D;
    private static final Double SMALLER_PROTEINA = 1D - 1D;

    private static final Double DEFAULT_NEUTRALIZANTES = 1D;
    private static final Double UPDATED_NEUTRALIZANTES = 2D;
    private static final Double SMALLER_NEUTRALIZANTES = 1D - 1D;

    private static final Double DEFAULT_ADULTERANTES = 1D;
    private static final Double UPDATED_ADULTERANTES = 2D;
    private static final Double SMALLER_ADULTERANTES = 1D - 1D;

    private static final Double DEFAULT_REDUCTASA = 1D;
    private static final Double UPDATED_REDUCTASA = 2D;
    private static final Double SMALLER_REDUCTASA = 1D - 1D;

    private static final Double DEFAULT_FOSFATASA = 1D;
    private static final Double UPDATED_FOSFATASA = 2D;
    private static final Double SMALLER_FOSFATASA = 1D - 1D;

    private static final Double DEFAULT_PH = 1D;
    private static final Double UPDATED_PH = 2D;
    private static final Double SMALLER_PH = 1D - 1D;

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    @Autowired
    private FQLecheRepository fQLecheRepository;

    @Autowired
    private FQLecheService fQLecheService;

    @Autowired
    private FQLecheQueryService fQLecheQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFQLecheMockMvc;

    private FQLeche fQLeche;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FQLeche createEntity(EntityManager em) {
        FQLeche fQLeche = new FQLeche()
            .fecha(DEFAULT_FECHA)
            .lote(DEFAULT_LOTE)
            .acidez(DEFAULT_ACIDEZ)
            .temperatura(DEFAULT_TEMPERATURA)
            .agua(DEFAULT_AGUA)
            .crioscopia(DEFAULT_CRIOSCOPIA)
            .antibiotico(DEFAULT_ANTIBIOTICO)
            .delvo(DEFAULT_DELVO)
            .grasa(DEFAULT_GRASA)
            .solidos(DEFAULT_SOLIDOS)
            .densidad(DEFAULT_DENSIDAD)
            .lactosa(DEFAULT_LACTOSA)
            .proteina(DEFAULT_PROTEINA)
            .neutralizantes(DEFAULT_NEUTRALIZANTES)
            .adulterantes(DEFAULT_ADULTERANTES)
            .reductasa(DEFAULT_REDUCTASA)
            .fosfatasa(DEFAULT_FOSFATASA)
            .ph(DEFAULT_PH)
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
        fQLeche.setArea(area);
        // Add required entity
        Recepcion recepcion;
        if (TestUtil.findAll(em, Recepcion.class).isEmpty()) {
            recepcion = RecepcionResourceIT.createEntity(em);
            em.persist(recepcion);
            em.flush();
        } else {
            recepcion = TestUtil.findAll(em, Recepcion.class).get(0);
        }
        fQLeche.setRecepcion(recepcion);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        fQLeche.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        fQLeche.setProveedor(personal);
        return fQLeche;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FQLeche createUpdatedEntity(EntityManager em) {
        FQLeche fQLeche = new FQLeche()
            .fecha(UPDATED_FECHA)
            .lote(UPDATED_LOTE)
            .acidez(UPDATED_ACIDEZ)
            .temperatura(UPDATED_TEMPERATURA)
            .agua(UPDATED_AGUA)
            .crioscopia(UPDATED_CRIOSCOPIA)
            .antibiotico(UPDATED_ANTIBIOTICO)
            .delvo(UPDATED_DELVO)
            .grasa(UPDATED_GRASA)
            .solidos(UPDATED_SOLIDOS)
            .densidad(UPDATED_DENSIDAD)
            .lactosa(UPDATED_LACTOSA)
            .proteina(UPDATED_PROTEINA)
            .neutralizantes(UPDATED_NEUTRALIZANTES)
            .adulterantes(UPDATED_ADULTERANTES)
            .reductasa(UPDATED_REDUCTASA)
            .fosfatasa(UPDATED_FOSFATASA)
            .ph(UPDATED_PH)
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
        fQLeche.setArea(area);
        // Add required entity
        Recepcion recepcion;
        if (TestUtil.findAll(em, Recepcion.class).isEmpty()) {
            recepcion = RecepcionResourceIT.createUpdatedEntity(em);
            em.persist(recepcion);
            em.flush();
        } else {
            recepcion = TestUtil.findAll(em, Recepcion.class).get(0);
        }
        fQLeche.setRecepcion(recepcion);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createUpdatedEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        fQLeche.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createUpdatedEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        fQLeche.setProveedor(personal);
        return fQLeche;
    }

    @BeforeEach
    public void initTest() {
        fQLeche = createEntity(em);
    }

    @Test
    @Transactional
    public void createFQLeche() throws Exception {
        int databaseSizeBeforeCreate = fQLecheRepository.findAll().size();
        // Create the FQLeche
        restFQLecheMockMvc.perform(post("/api/fq-leches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQLeche)))
            .andExpect(status().isCreated());

        // Validate the FQLeche in the database
        List<FQLeche> fQLecheList = fQLecheRepository.findAll();
        assertThat(fQLecheList).hasSize(databaseSizeBeforeCreate + 1);
        FQLeche testFQLeche = fQLecheList.get(fQLecheList.size() - 1);
        assertThat(testFQLeche.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testFQLeche.getLote()).isEqualTo(DEFAULT_LOTE);
        assertThat(testFQLeche.getAcidez()).isEqualTo(DEFAULT_ACIDEZ);
        assertThat(testFQLeche.getTemperatura()).isEqualTo(DEFAULT_TEMPERATURA);
        assertThat(testFQLeche.getAgua()).isEqualTo(DEFAULT_AGUA);
        assertThat(testFQLeche.getCrioscopia()).isEqualTo(DEFAULT_CRIOSCOPIA);
        assertThat(testFQLeche.getAntibiotico()).isEqualTo(DEFAULT_ANTIBIOTICO);
        assertThat(testFQLeche.getDelvo()).isEqualTo(DEFAULT_DELVO);
        assertThat(testFQLeche.getGrasa()).isEqualTo(DEFAULT_GRASA);
        assertThat(testFQLeche.getSolidos()).isEqualTo(DEFAULT_SOLIDOS);
        assertThat(testFQLeche.getDensidad()).isEqualTo(DEFAULT_DENSIDAD);
        assertThat(testFQLeche.getLactosa()).isEqualTo(DEFAULT_LACTOSA);
        assertThat(testFQLeche.getProteina()).isEqualTo(DEFAULT_PROTEINA);
        assertThat(testFQLeche.getNeutralizantes()).isEqualTo(DEFAULT_NEUTRALIZANTES);
        assertThat(testFQLeche.getAdulterantes()).isEqualTo(DEFAULT_ADULTERANTES);
        assertThat(testFQLeche.getReductasa()).isEqualTo(DEFAULT_REDUCTASA);
        assertThat(testFQLeche.getFosfatasa()).isEqualTo(DEFAULT_FOSFATASA);
        assertThat(testFQLeche.getPh()).isEqualTo(DEFAULT_PH);
        assertThat(testFQLeche.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void createFQLecheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fQLecheRepository.findAll().size();

        // Create the FQLeche with an existing ID
        fQLeche.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFQLecheMockMvc.perform(post("/api/fq-leches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQLeche)))
            .andExpect(status().isBadRequest());

        // Validate the FQLeche in the database
        List<FQLeche> fQLecheList = fQLecheRepository.findAll();
        assertThat(fQLecheList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fQLecheRepository.findAll().size();
        // set the field null
        fQLeche.setFecha(null);

        // Create the FQLeche, which fails.


        restFQLecheMockMvc.perform(post("/api/fq-leches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQLeche)))
            .andExpect(status().isBadRequest());

        List<FQLeche> fQLecheList = fQLecheRepository.findAll();
        assertThat(fQLecheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFQLeches() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList
        restFQLecheMockMvc.perform(get("/api/fq-leches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fQLeche.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].acidez").value(hasItem(DEFAULT_ACIDEZ.doubleValue())))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA.doubleValue())))
            .andExpect(jsonPath("$.[*].agua").value(hasItem(DEFAULT_AGUA.doubleValue())))
            .andExpect(jsonPath("$.[*].crioscopia").value(hasItem(DEFAULT_CRIOSCOPIA.doubleValue())))
            .andExpect(jsonPath("$.[*].antibiotico").value(hasItem(DEFAULT_ANTIBIOTICO)))
            .andExpect(jsonPath("$.[*].delvo").value(hasItem(DEFAULT_DELVO.doubleValue())))
            .andExpect(jsonPath("$.[*].grasa").value(hasItem(DEFAULT_GRASA.doubleValue())))
            .andExpect(jsonPath("$.[*].solidos").value(hasItem(DEFAULT_SOLIDOS)))
            .andExpect(jsonPath("$.[*].densidad").value(hasItem(DEFAULT_DENSIDAD.doubleValue())))
            .andExpect(jsonPath("$.[*].lactosa").value(hasItem(DEFAULT_LACTOSA.doubleValue())))
            .andExpect(jsonPath("$.[*].proteina").value(hasItem(DEFAULT_PROTEINA.doubleValue())))
            .andExpect(jsonPath("$.[*].neutralizantes").value(hasItem(DEFAULT_NEUTRALIZANTES.doubleValue())))
            .andExpect(jsonPath("$.[*].adulterantes").value(hasItem(DEFAULT_ADULTERANTES.doubleValue())))
            .andExpect(jsonPath("$.[*].reductasa").value(hasItem(DEFAULT_REDUCTASA.doubleValue())))
            .andExpect(jsonPath("$.[*].fosfatasa").value(hasItem(DEFAULT_FOSFATASA.doubleValue())))
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.doubleValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }
    
    @Test
    @Transactional
    public void getFQLeche() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get the fQLeche
        restFQLecheMockMvc.perform(get("/api/fq-leches/{id}", fQLeche.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fQLeche.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.lote").value(DEFAULT_LOTE))
            .andExpect(jsonPath("$.acidez").value(DEFAULT_ACIDEZ.doubleValue()))
            .andExpect(jsonPath("$.temperatura").value(DEFAULT_TEMPERATURA.doubleValue()))
            .andExpect(jsonPath("$.agua").value(DEFAULT_AGUA.doubleValue()))
            .andExpect(jsonPath("$.crioscopia").value(DEFAULT_CRIOSCOPIA.doubleValue()))
            .andExpect(jsonPath("$.antibiotico").value(DEFAULT_ANTIBIOTICO))
            .andExpect(jsonPath("$.delvo").value(DEFAULT_DELVO.doubleValue()))
            .andExpect(jsonPath("$.grasa").value(DEFAULT_GRASA.doubleValue()))
            .andExpect(jsonPath("$.solidos").value(DEFAULT_SOLIDOS))
            .andExpect(jsonPath("$.densidad").value(DEFAULT_DENSIDAD.doubleValue()))
            .andExpect(jsonPath("$.lactosa").value(DEFAULT_LACTOSA.doubleValue()))
            .andExpect(jsonPath("$.proteina").value(DEFAULT_PROTEINA.doubleValue()))
            .andExpect(jsonPath("$.neutralizantes").value(DEFAULT_NEUTRALIZANTES.doubleValue()))
            .andExpect(jsonPath("$.adulterantes").value(DEFAULT_ADULTERANTES.doubleValue()))
            .andExpect(jsonPath("$.reductasa").value(DEFAULT_REDUCTASA.doubleValue()))
            .andExpect(jsonPath("$.fosfatasa").value(DEFAULT_FOSFATASA.doubleValue()))
            .andExpect(jsonPath("$.ph").value(DEFAULT_PH.doubleValue()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }


    @Test
    @Transactional
    public void getFQLechesByIdFiltering() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        Long id = fQLeche.getId();

        defaultFQLecheShouldBeFound("id.equals=" + id);
        defaultFQLecheShouldNotBeFound("id.notEquals=" + id);

        defaultFQLecheShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFQLecheShouldNotBeFound("id.greaterThan=" + id);

        defaultFQLecheShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFQLecheShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFQLechesByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fecha equals to DEFAULT_FECHA
        defaultFQLecheShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the fQLecheList where fecha equals to UPDATED_FECHA
        defaultFQLecheShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fecha not equals to DEFAULT_FECHA
        defaultFQLecheShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the fQLecheList where fecha not equals to UPDATED_FECHA
        defaultFQLecheShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultFQLecheShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the fQLecheList where fecha equals to UPDATED_FECHA
        defaultFQLecheShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fecha is not null
        defaultFQLecheShouldBeFound("fecha.specified=true");

        // Get all the fQLecheList where fecha is null
        defaultFQLecheShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByLoteIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lote equals to DEFAULT_LOTE
        defaultFQLecheShouldBeFound("lote.equals=" + DEFAULT_LOTE);

        // Get all the fQLecheList where lote equals to UPDATED_LOTE
        defaultFQLecheShouldNotBeFound("lote.equals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQLechesByLoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lote not equals to DEFAULT_LOTE
        defaultFQLecheShouldNotBeFound("lote.notEquals=" + DEFAULT_LOTE);

        // Get all the fQLecheList where lote not equals to UPDATED_LOTE
        defaultFQLecheShouldBeFound("lote.notEquals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQLechesByLoteIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lote in DEFAULT_LOTE or UPDATED_LOTE
        defaultFQLecheShouldBeFound("lote.in=" + DEFAULT_LOTE + "," + UPDATED_LOTE);

        // Get all the fQLecheList where lote equals to UPDATED_LOTE
        defaultFQLecheShouldNotBeFound("lote.in=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQLechesByLoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lote is not null
        defaultFQLecheShouldBeFound("lote.specified=true");

        // Get all the fQLecheList where lote is null
        defaultFQLecheShouldNotBeFound("lote.specified=false");
    }
                @Test
    @Transactional
    public void getAllFQLechesByLoteContainsSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lote contains DEFAULT_LOTE
        defaultFQLecheShouldBeFound("lote.contains=" + DEFAULT_LOTE);

        // Get all the fQLecheList where lote contains UPDATED_LOTE
        defaultFQLecheShouldNotBeFound("lote.contains=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQLechesByLoteNotContainsSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lote does not contain DEFAULT_LOTE
        defaultFQLecheShouldNotBeFound("lote.doesNotContain=" + DEFAULT_LOTE);

        // Get all the fQLecheList where lote does not contain UPDATED_LOTE
        defaultFQLecheShouldBeFound("lote.doesNotContain=" + UPDATED_LOTE);
    }


    @Test
    @Transactional
    public void getAllFQLechesByAcidezIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where acidez equals to DEFAULT_ACIDEZ
        defaultFQLecheShouldBeFound("acidez.equals=" + DEFAULT_ACIDEZ);

        // Get all the fQLecheList where acidez equals to UPDATED_ACIDEZ
        defaultFQLecheShouldNotBeFound("acidez.equals=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAcidezIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where acidez not equals to DEFAULT_ACIDEZ
        defaultFQLecheShouldNotBeFound("acidez.notEquals=" + DEFAULT_ACIDEZ);

        // Get all the fQLecheList where acidez not equals to UPDATED_ACIDEZ
        defaultFQLecheShouldBeFound("acidez.notEquals=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAcidezIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where acidez in DEFAULT_ACIDEZ or UPDATED_ACIDEZ
        defaultFQLecheShouldBeFound("acidez.in=" + DEFAULT_ACIDEZ + "," + UPDATED_ACIDEZ);

        // Get all the fQLecheList where acidez equals to UPDATED_ACIDEZ
        defaultFQLecheShouldNotBeFound("acidez.in=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAcidezIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where acidez is not null
        defaultFQLecheShouldBeFound("acidez.specified=true");

        // Get all the fQLecheList where acidez is null
        defaultFQLecheShouldNotBeFound("acidez.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByAcidezIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where acidez is greater than or equal to DEFAULT_ACIDEZ
        defaultFQLecheShouldBeFound("acidez.greaterThanOrEqual=" + DEFAULT_ACIDEZ);

        // Get all the fQLecheList where acidez is greater than or equal to UPDATED_ACIDEZ
        defaultFQLecheShouldNotBeFound("acidez.greaterThanOrEqual=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAcidezIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where acidez is less than or equal to DEFAULT_ACIDEZ
        defaultFQLecheShouldBeFound("acidez.lessThanOrEqual=" + DEFAULT_ACIDEZ);

        // Get all the fQLecheList where acidez is less than or equal to SMALLER_ACIDEZ
        defaultFQLecheShouldNotBeFound("acidez.lessThanOrEqual=" + SMALLER_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAcidezIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where acidez is less than DEFAULT_ACIDEZ
        defaultFQLecheShouldNotBeFound("acidez.lessThan=" + DEFAULT_ACIDEZ);

        // Get all the fQLecheList where acidez is less than UPDATED_ACIDEZ
        defaultFQLecheShouldBeFound("acidez.lessThan=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAcidezIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where acidez is greater than DEFAULT_ACIDEZ
        defaultFQLecheShouldNotBeFound("acidez.greaterThan=" + DEFAULT_ACIDEZ);

        // Get all the fQLecheList where acidez is greater than SMALLER_ACIDEZ
        defaultFQLecheShouldBeFound("acidez.greaterThan=" + SMALLER_ACIDEZ);
    }


    @Test
    @Transactional
    public void getAllFQLechesByTemperaturaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where temperatura equals to DEFAULT_TEMPERATURA
        defaultFQLecheShouldBeFound("temperatura.equals=" + DEFAULT_TEMPERATURA);

        // Get all the fQLecheList where temperatura equals to UPDATED_TEMPERATURA
        defaultFQLecheShouldNotBeFound("temperatura.equals=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByTemperaturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where temperatura not equals to DEFAULT_TEMPERATURA
        defaultFQLecheShouldNotBeFound("temperatura.notEquals=" + DEFAULT_TEMPERATURA);

        // Get all the fQLecheList where temperatura not equals to UPDATED_TEMPERATURA
        defaultFQLecheShouldBeFound("temperatura.notEquals=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByTemperaturaIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where temperatura in DEFAULT_TEMPERATURA or UPDATED_TEMPERATURA
        defaultFQLecheShouldBeFound("temperatura.in=" + DEFAULT_TEMPERATURA + "," + UPDATED_TEMPERATURA);

        // Get all the fQLecheList where temperatura equals to UPDATED_TEMPERATURA
        defaultFQLecheShouldNotBeFound("temperatura.in=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByTemperaturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where temperatura is not null
        defaultFQLecheShouldBeFound("temperatura.specified=true");

        // Get all the fQLecheList where temperatura is null
        defaultFQLecheShouldNotBeFound("temperatura.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByTemperaturaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where temperatura is greater than or equal to DEFAULT_TEMPERATURA
        defaultFQLecheShouldBeFound("temperatura.greaterThanOrEqual=" + DEFAULT_TEMPERATURA);

        // Get all the fQLecheList where temperatura is greater than or equal to UPDATED_TEMPERATURA
        defaultFQLecheShouldNotBeFound("temperatura.greaterThanOrEqual=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByTemperaturaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where temperatura is less than or equal to DEFAULT_TEMPERATURA
        defaultFQLecheShouldBeFound("temperatura.lessThanOrEqual=" + DEFAULT_TEMPERATURA);

        // Get all the fQLecheList where temperatura is less than or equal to SMALLER_TEMPERATURA
        defaultFQLecheShouldNotBeFound("temperatura.lessThanOrEqual=" + SMALLER_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByTemperaturaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where temperatura is less than DEFAULT_TEMPERATURA
        defaultFQLecheShouldNotBeFound("temperatura.lessThan=" + DEFAULT_TEMPERATURA);

        // Get all the fQLecheList where temperatura is less than UPDATED_TEMPERATURA
        defaultFQLecheShouldBeFound("temperatura.lessThan=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByTemperaturaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where temperatura is greater than DEFAULT_TEMPERATURA
        defaultFQLecheShouldNotBeFound("temperatura.greaterThan=" + DEFAULT_TEMPERATURA);

        // Get all the fQLecheList where temperatura is greater than SMALLER_TEMPERATURA
        defaultFQLecheShouldBeFound("temperatura.greaterThan=" + SMALLER_TEMPERATURA);
    }


    @Test
    @Transactional
    public void getAllFQLechesByAguaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where agua equals to DEFAULT_AGUA
        defaultFQLecheShouldBeFound("agua.equals=" + DEFAULT_AGUA);

        // Get all the fQLecheList where agua equals to UPDATED_AGUA
        defaultFQLecheShouldNotBeFound("agua.equals=" + UPDATED_AGUA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAguaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where agua not equals to DEFAULT_AGUA
        defaultFQLecheShouldNotBeFound("agua.notEquals=" + DEFAULT_AGUA);

        // Get all the fQLecheList where agua not equals to UPDATED_AGUA
        defaultFQLecheShouldBeFound("agua.notEquals=" + UPDATED_AGUA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAguaIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where agua in DEFAULT_AGUA or UPDATED_AGUA
        defaultFQLecheShouldBeFound("agua.in=" + DEFAULT_AGUA + "," + UPDATED_AGUA);

        // Get all the fQLecheList where agua equals to UPDATED_AGUA
        defaultFQLecheShouldNotBeFound("agua.in=" + UPDATED_AGUA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAguaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where agua is not null
        defaultFQLecheShouldBeFound("agua.specified=true");

        // Get all the fQLecheList where agua is null
        defaultFQLecheShouldNotBeFound("agua.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByAguaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where agua is greater than or equal to DEFAULT_AGUA
        defaultFQLecheShouldBeFound("agua.greaterThanOrEqual=" + DEFAULT_AGUA);

        // Get all the fQLecheList where agua is greater than or equal to UPDATED_AGUA
        defaultFQLecheShouldNotBeFound("agua.greaterThanOrEqual=" + UPDATED_AGUA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAguaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where agua is less than or equal to DEFAULT_AGUA
        defaultFQLecheShouldBeFound("agua.lessThanOrEqual=" + DEFAULT_AGUA);

        // Get all the fQLecheList where agua is less than or equal to SMALLER_AGUA
        defaultFQLecheShouldNotBeFound("agua.lessThanOrEqual=" + SMALLER_AGUA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAguaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where agua is less than DEFAULT_AGUA
        defaultFQLecheShouldNotBeFound("agua.lessThan=" + DEFAULT_AGUA);

        // Get all the fQLecheList where agua is less than UPDATED_AGUA
        defaultFQLecheShouldBeFound("agua.lessThan=" + UPDATED_AGUA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAguaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where agua is greater than DEFAULT_AGUA
        defaultFQLecheShouldNotBeFound("agua.greaterThan=" + DEFAULT_AGUA);

        // Get all the fQLecheList where agua is greater than SMALLER_AGUA
        defaultFQLecheShouldBeFound("agua.greaterThan=" + SMALLER_AGUA);
    }


    @Test
    @Transactional
    public void getAllFQLechesByCrioscopiaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where crioscopia equals to DEFAULT_CRIOSCOPIA
        defaultFQLecheShouldBeFound("crioscopia.equals=" + DEFAULT_CRIOSCOPIA);

        // Get all the fQLecheList where crioscopia equals to UPDATED_CRIOSCOPIA
        defaultFQLecheShouldNotBeFound("crioscopia.equals=" + UPDATED_CRIOSCOPIA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByCrioscopiaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where crioscopia not equals to DEFAULT_CRIOSCOPIA
        defaultFQLecheShouldNotBeFound("crioscopia.notEquals=" + DEFAULT_CRIOSCOPIA);

        // Get all the fQLecheList where crioscopia not equals to UPDATED_CRIOSCOPIA
        defaultFQLecheShouldBeFound("crioscopia.notEquals=" + UPDATED_CRIOSCOPIA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByCrioscopiaIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where crioscopia in DEFAULT_CRIOSCOPIA or UPDATED_CRIOSCOPIA
        defaultFQLecheShouldBeFound("crioscopia.in=" + DEFAULT_CRIOSCOPIA + "," + UPDATED_CRIOSCOPIA);

        // Get all the fQLecheList where crioscopia equals to UPDATED_CRIOSCOPIA
        defaultFQLecheShouldNotBeFound("crioscopia.in=" + UPDATED_CRIOSCOPIA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByCrioscopiaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where crioscopia is not null
        defaultFQLecheShouldBeFound("crioscopia.specified=true");

        // Get all the fQLecheList where crioscopia is null
        defaultFQLecheShouldNotBeFound("crioscopia.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByCrioscopiaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where crioscopia is greater than or equal to DEFAULT_CRIOSCOPIA
        defaultFQLecheShouldBeFound("crioscopia.greaterThanOrEqual=" + DEFAULT_CRIOSCOPIA);

        // Get all the fQLecheList where crioscopia is greater than or equal to UPDATED_CRIOSCOPIA
        defaultFQLecheShouldNotBeFound("crioscopia.greaterThanOrEqual=" + UPDATED_CRIOSCOPIA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByCrioscopiaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where crioscopia is less than or equal to DEFAULT_CRIOSCOPIA
        defaultFQLecheShouldBeFound("crioscopia.lessThanOrEqual=" + DEFAULT_CRIOSCOPIA);

        // Get all the fQLecheList where crioscopia is less than or equal to SMALLER_CRIOSCOPIA
        defaultFQLecheShouldNotBeFound("crioscopia.lessThanOrEqual=" + SMALLER_CRIOSCOPIA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByCrioscopiaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where crioscopia is less than DEFAULT_CRIOSCOPIA
        defaultFQLecheShouldNotBeFound("crioscopia.lessThan=" + DEFAULT_CRIOSCOPIA);

        // Get all the fQLecheList where crioscopia is less than UPDATED_CRIOSCOPIA
        defaultFQLecheShouldBeFound("crioscopia.lessThan=" + UPDATED_CRIOSCOPIA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByCrioscopiaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where crioscopia is greater than DEFAULT_CRIOSCOPIA
        defaultFQLecheShouldNotBeFound("crioscopia.greaterThan=" + DEFAULT_CRIOSCOPIA);

        // Get all the fQLecheList where crioscopia is greater than SMALLER_CRIOSCOPIA
        defaultFQLecheShouldBeFound("crioscopia.greaterThan=" + SMALLER_CRIOSCOPIA);
    }


    @Test
    @Transactional
    public void getAllFQLechesByAntibioticoIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where antibiotico equals to DEFAULT_ANTIBIOTICO
        defaultFQLecheShouldBeFound("antibiotico.equals=" + DEFAULT_ANTIBIOTICO);

        // Get all the fQLecheList where antibiotico equals to UPDATED_ANTIBIOTICO
        defaultFQLecheShouldNotBeFound("antibiotico.equals=" + UPDATED_ANTIBIOTICO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAntibioticoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where antibiotico not equals to DEFAULT_ANTIBIOTICO
        defaultFQLecheShouldNotBeFound("antibiotico.notEquals=" + DEFAULT_ANTIBIOTICO);

        // Get all the fQLecheList where antibiotico not equals to UPDATED_ANTIBIOTICO
        defaultFQLecheShouldBeFound("antibiotico.notEquals=" + UPDATED_ANTIBIOTICO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAntibioticoIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where antibiotico in DEFAULT_ANTIBIOTICO or UPDATED_ANTIBIOTICO
        defaultFQLecheShouldBeFound("antibiotico.in=" + DEFAULT_ANTIBIOTICO + "," + UPDATED_ANTIBIOTICO);

        // Get all the fQLecheList where antibiotico equals to UPDATED_ANTIBIOTICO
        defaultFQLecheShouldNotBeFound("antibiotico.in=" + UPDATED_ANTIBIOTICO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAntibioticoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where antibiotico is not null
        defaultFQLecheShouldBeFound("antibiotico.specified=true");

        // Get all the fQLecheList where antibiotico is null
        defaultFQLecheShouldNotBeFound("antibiotico.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByAntibioticoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where antibiotico is greater than or equal to DEFAULT_ANTIBIOTICO
        defaultFQLecheShouldBeFound("antibiotico.greaterThanOrEqual=" + DEFAULT_ANTIBIOTICO);

        // Get all the fQLecheList where antibiotico is greater than or equal to UPDATED_ANTIBIOTICO
        defaultFQLecheShouldNotBeFound("antibiotico.greaterThanOrEqual=" + UPDATED_ANTIBIOTICO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAntibioticoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where antibiotico is less than or equal to DEFAULT_ANTIBIOTICO
        defaultFQLecheShouldBeFound("antibiotico.lessThanOrEqual=" + DEFAULT_ANTIBIOTICO);

        // Get all the fQLecheList where antibiotico is less than or equal to SMALLER_ANTIBIOTICO
        defaultFQLecheShouldNotBeFound("antibiotico.lessThanOrEqual=" + SMALLER_ANTIBIOTICO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAntibioticoIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where antibiotico is less than DEFAULT_ANTIBIOTICO
        defaultFQLecheShouldNotBeFound("antibiotico.lessThan=" + DEFAULT_ANTIBIOTICO);

        // Get all the fQLecheList where antibiotico is less than UPDATED_ANTIBIOTICO
        defaultFQLecheShouldBeFound("antibiotico.lessThan=" + UPDATED_ANTIBIOTICO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAntibioticoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where antibiotico is greater than DEFAULT_ANTIBIOTICO
        defaultFQLecheShouldNotBeFound("antibiotico.greaterThan=" + DEFAULT_ANTIBIOTICO);

        // Get all the fQLecheList where antibiotico is greater than SMALLER_ANTIBIOTICO
        defaultFQLecheShouldBeFound("antibiotico.greaterThan=" + SMALLER_ANTIBIOTICO);
    }


    @Test
    @Transactional
    public void getAllFQLechesByDelvoIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where delvo equals to DEFAULT_DELVO
        defaultFQLecheShouldBeFound("delvo.equals=" + DEFAULT_DELVO);

        // Get all the fQLecheList where delvo equals to UPDATED_DELVO
        defaultFQLecheShouldNotBeFound("delvo.equals=" + UPDATED_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDelvoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where delvo not equals to DEFAULT_DELVO
        defaultFQLecheShouldNotBeFound("delvo.notEquals=" + DEFAULT_DELVO);

        // Get all the fQLecheList where delvo not equals to UPDATED_DELVO
        defaultFQLecheShouldBeFound("delvo.notEquals=" + UPDATED_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDelvoIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where delvo in DEFAULT_DELVO or UPDATED_DELVO
        defaultFQLecheShouldBeFound("delvo.in=" + DEFAULT_DELVO + "," + UPDATED_DELVO);

        // Get all the fQLecheList where delvo equals to UPDATED_DELVO
        defaultFQLecheShouldNotBeFound("delvo.in=" + UPDATED_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDelvoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where delvo is not null
        defaultFQLecheShouldBeFound("delvo.specified=true");

        // Get all the fQLecheList where delvo is null
        defaultFQLecheShouldNotBeFound("delvo.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByDelvoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where delvo is greater than or equal to DEFAULT_DELVO
        defaultFQLecheShouldBeFound("delvo.greaterThanOrEqual=" + DEFAULT_DELVO);

        // Get all the fQLecheList where delvo is greater than or equal to UPDATED_DELVO
        defaultFQLecheShouldNotBeFound("delvo.greaterThanOrEqual=" + UPDATED_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDelvoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where delvo is less than or equal to DEFAULT_DELVO
        defaultFQLecheShouldBeFound("delvo.lessThanOrEqual=" + DEFAULT_DELVO);

        // Get all the fQLecheList where delvo is less than or equal to SMALLER_DELVO
        defaultFQLecheShouldNotBeFound("delvo.lessThanOrEqual=" + SMALLER_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDelvoIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where delvo is less than DEFAULT_DELVO
        defaultFQLecheShouldNotBeFound("delvo.lessThan=" + DEFAULT_DELVO);

        // Get all the fQLecheList where delvo is less than UPDATED_DELVO
        defaultFQLecheShouldBeFound("delvo.lessThan=" + UPDATED_DELVO);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDelvoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where delvo is greater than DEFAULT_DELVO
        defaultFQLecheShouldNotBeFound("delvo.greaterThan=" + DEFAULT_DELVO);

        // Get all the fQLecheList where delvo is greater than SMALLER_DELVO
        defaultFQLecheShouldBeFound("delvo.greaterThan=" + SMALLER_DELVO);
    }


    @Test
    @Transactional
    public void getAllFQLechesByGrasaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where grasa equals to DEFAULT_GRASA
        defaultFQLecheShouldBeFound("grasa.equals=" + DEFAULT_GRASA);

        // Get all the fQLecheList where grasa equals to UPDATED_GRASA
        defaultFQLecheShouldNotBeFound("grasa.equals=" + UPDATED_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByGrasaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where grasa not equals to DEFAULT_GRASA
        defaultFQLecheShouldNotBeFound("grasa.notEquals=" + DEFAULT_GRASA);

        // Get all the fQLecheList where grasa not equals to UPDATED_GRASA
        defaultFQLecheShouldBeFound("grasa.notEquals=" + UPDATED_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByGrasaIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where grasa in DEFAULT_GRASA or UPDATED_GRASA
        defaultFQLecheShouldBeFound("grasa.in=" + DEFAULT_GRASA + "," + UPDATED_GRASA);

        // Get all the fQLecheList where grasa equals to UPDATED_GRASA
        defaultFQLecheShouldNotBeFound("grasa.in=" + UPDATED_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByGrasaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where grasa is not null
        defaultFQLecheShouldBeFound("grasa.specified=true");

        // Get all the fQLecheList where grasa is null
        defaultFQLecheShouldNotBeFound("grasa.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByGrasaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where grasa is greater than or equal to DEFAULT_GRASA
        defaultFQLecheShouldBeFound("grasa.greaterThanOrEqual=" + DEFAULT_GRASA);

        // Get all the fQLecheList where grasa is greater than or equal to UPDATED_GRASA
        defaultFQLecheShouldNotBeFound("grasa.greaterThanOrEqual=" + UPDATED_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByGrasaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where grasa is less than or equal to DEFAULT_GRASA
        defaultFQLecheShouldBeFound("grasa.lessThanOrEqual=" + DEFAULT_GRASA);

        // Get all the fQLecheList where grasa is less than or equal to SMALLER_GRASA
        defaultFQLecheShouldNotBeFound("grasa.lessThanOrEqual=" + SMALLER_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByGrasaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where grasa is less than DEFAULT_GRASA
        defaultFQLecheShouldNotBeFound("grasa.lessThan=" + DEFAULT_GRASA);

        // Get all the fQLecheList where grasa is less than UPDATED_GRASA
        defaultFQLecheShouldBeFound("grasa.lessThan=" + UPDATED_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByGrasaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where grasa is greater than DEFAULT_GRASA
        defaultFQLecheShouldNotBeFound("grasa.greaterThan=" + DEFAULT_GRASA);

        // Get all the fQLecheList where grasa is greater than SMALLER_GRASA
        defaultFQLecheShouldBeFound("grasa.greaterThan=" + SMALLER_GRASA);
    }


    @Test
    @Transactional
    public void getAllFQLechesBySolidosIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where solidos equals to DEFAULT_SOLIDOS
        defaultFQLecheShouldBeFound("solidos.equals=" + DEFAULT_SOLIDOS);

        // Get all the fQLecheList where solidos equals to UPDATED_SOLIDOS
        defaultFQLecheShouldNotBeFound("solidos.equals=" + UPDATED_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllFQLechesBySolidosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where solidos not equals to DEFAULT_SOLIDOS
        defaultFQLecheShouldNotBeFound("solidos.notEquals=" + DEFAULT_SOLIDOS);

        // Get all the fQLecheList where solidos not equals to UPDATED_SOLIDOS
        defaultFQLecheShouldBeFound("solidos.notEquals=" + UPDATED_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllFQLechesBySolidosIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where solidos in DEFAULT_SOLIDOS or UPDATED_SOLIDOS
        defaultFQLecheShouldBeFound("solidos.in=" + DEFAULT_SOLIDOS + "," + UPDATED_SOLIDOS);

        // Get all the fQLecheList where solidos equals to UPDATED_SOLIDOS
        defaultFQLecheShouldNotBeFound("solidos.in=" + UPDATED_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllFQLechesBySolidosIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where solidos is not null
        defaultFQLecheShouldBeFound("solidos.specified=true");

        // Get all the fQLecheList where solidos is null
        defaultFQLecheShouldNotBeFound("solidos.specified=false");
    }
                @Test
    @Transactional
    public void getAllFQLechesBySolidosContainsSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where solidos contains DEFAULT_SOLIDOS
        defaultFQLecheShouldBeFound("solidos.contains=" + DEFAULT_SOLIDOS);

        // Get all the fQLecheList where solidos contains UPDATED_SOLIDOS
        defaultFQLecheShouldNotBeFound("solidos.contains=" + UPDATED_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllFQLechesBySolidosNotContainsSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where solidos does not contain DEFAULT_SOLIDOS
        defaultFQLecheShouldNotBeFound("solidos.doesNotContain=" + DEFAULT_SOLIDOS);

        // Get all the fQLecheList where solidos does not contain UPDATED_SOLIDOS
        defaultFQLecheShouldBeFound("solidos.doesNotContain=" + UPDATED_SOLIDOS);
    }


    @Test
    @Transactional
    public void getAllFQLechesByDensidadIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where densidad equals to DEFAULT_DENSIDAD
        defaultFQLecheShouldBeFound("densidad.equals=" + DEFAULT_DENSIDAD);

        // Get all the fQLecheList where densidad equals to UPDATED_DENSIDAD
        defaultFQLecheShouldNotBeFound("densidad.equals=" + UPDATED_DENSIDAD);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDensidadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where densidad not equals to DEFAULT_DENSIDAD
        defaultFQLecheShouldNotBeFound("densidad.notEquals=" + DEFAULT_DENSIDAD);

        // Get all the fQLecheList where densidad not equals to UPDATED_DENSIDAD
        defaultFQLecheShouldBeFound("densidad.notEquals=" + UPDATED_DENSIDAD);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDensidadIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where densidad in DEFAULT_DENSIDAD or UPDATED_DENSIDAD
        defaultFQLecheShouldBeFound("densidad.in=" + DEFAULT_DENSIDAD + "," + UPDATED_DENSIDAD);

        // Get all the fQLecheList where densidad equals to UPDATED_DENSIDAD
        defaultFQLecheShouldNotBeFound("densidad.in=" + UPDATED_DENSIDAD);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDensidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where densidad is not null
        defaultFQLecheShouldBeFound("densidad.specified=true");

        // Get all the fQLecheList where densidad is null
        defaultFQLecheShouldNotBeFound("densidad.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByDensidadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where densidad is greater than or equal to DEFAULT_DENSIDAD
        defaultFQLecheShouldBeFound("densidad.greaterThanOrEqual=" + DEFAULT_DENSIDAD);

        // Get all the fQLecheList where densidad is greater than or equal to UPDATED_DENSIDAD
        defaultFQLecheShouldNotBeFound("densidad.greaterThanOrEqual=" + UPDATED_DENSIDAD);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDensidadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where densidad is less than or equal to DEFAULT_DENSIDAD
        defaultFQLecheShouldBeFound("densidad.lessThanOrEqual=" + DEFAULT_DENSIDAD);

        // Get all the fQLecheList where densidad is less than or equal to SMALLER_DENSIDAD
        defaultFQLecheShouldNotBeFound("densidad.lessThanOrEqual=" + SMALLER_DENSIDAD);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDensidadIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where densidad is less than DEFAULT_DENSIDAD
        defaultFQLecheShouldNotBeFound("densidad.lessThan=" + DEFAULT_DENSIDAD);

        // Get all the fQLecheList where densidad is less than UPDATED_DENSIDAD
        defaultFQLecheShouldBeFound("densidad.lessThan=" + UPDATED_DENSIDAD);
    }

    @Test
    @Transactional
    public void getAllFQLechesByDensidadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where densidad is greater than DEFAULT_DENSIDAD
        defaultFQLecheShouldNotBeFound("densidad.greaterThan=" + DEFAULT_DENSIDAD);

        // Get all the fQLecheList where densidad is greater than SMALLER_DENSIDAD
        defaultFQLecheShouldBeFound("densidad.greaterThan=" + SMALLER_DENSIDAD);
    }


    @Test
    @Transactional
    public void getAllFQLechesByLactosaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lactosa equals to DEFAULT_LACTOSA
        defaultFQLecheShouldBeFound("lactosa.equals=" + DEFAULT_LACTOSA);

        // Get all the fQLecheList where lactosa equals to UPDATED_LACTOSA
        defaultFQLecheShouldNotBeFound("lactosa.equals=" + UPDATED_LACTOSA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByLactosaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lactosa not equals to DEFAULT_LACTOSA
        defaultFQLecheShouldNotBeFound("lactosa.notEquals=" + DEFAULT_LACTOSA);

        // Get all the fQLecheList where lactosa not equals to UPDATED_LACTOSA
        defaultFQLecheShouldBeFound("lactosa.notEquals=" + UPDATED_LACTOSA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByLactosaIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lactosa in DEFAULT_LACTOSA or UPDATED_LACTOSA
        defaultFQLecheShouldBeFound("lactosa.in=" + DEFAULT_LACTOSA + "," + UPDATED_LACTOSA);

        // Get all the fQLecheList where lactosa equals to UPDATED_LACTOSA
        defaultFQLecheShouldNotBeFound("lactosa.in=" + UPDATED_LACTOSA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByLactosaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lactosa is not null
        defaultFQLecheShouldBeFound("lactosa.specified=true");

        // Get all the fQLecheList where lactosa is null
        defaultFQLecheShouldNotBeFound("lactosa.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByLactosaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lactosa is greater than or equal to DEFAULT_LACTOSA
        defaultFQLecheShouldBeFound("lactosa.greaterThanOrEqual=" + DEFAULT_LACTOSA);

        // Get all the fQLecheList where lactosa is greater than or equal to UPDATED_LACTOSA
        defaultFQLecheShouldNotBeFound("lactosa.greaterThanOrEqual=" + UPDATED_LACTOSA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByLactosaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lactosa is less than or equal to DEFAULT_LACTOSA
        defaultFQLecheShouldBeFound("lactosa.lessThanOrEqual=" + DEFAULT_LACTOSA);

        // Get all the fQLecheList where lactosa is less than or equal to SMALLER_LACTOSA
        defaultFQLecheShouldNotBeFound("lactosa.lessThanOrEqual=" + SMALLER_LACTOSA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByLactosaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lactosa is less than DEFAULT_LACTOSA
        defaultFQLecheShouldNotBeFound("lactosa.lessThan=" + DEFAULT_LACTOSA);

        // Get all the fQLecheList where lactosa is less than UPDATED_LACTOSA
        defaultFQLecheShouldBeFound("lactosa.lessThan=" + UPDATED_LACTOSA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByLactosaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where lactosa is greater than DEFAULT_LACTOSA
        defaultFQLecheShouldNotBeFound("lactosa.greaterThan=" + DEFAULT_LACTOSA);

        // Get all the fQLecheList where lactosa is greater than SMALLER_LACTOSA
        defaultFQLecheShouldBeFound("lactosa.greaterThan=" + SMALLER_LACTOSA);
    }


    @Test
    @Transactional
    public void getAllFQLechesByProteinaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where proteina equals to DEFAULT_PROTEINA
        defaultFQLecheShouldBeFound("proteina.equals=" + DEFAULT_PROTEINA);

        // Get all the fQLecheList where proteina equals to UPDATED_PROTEINA
        defaultFQLecheShouldNotBeFound("proteina.equals=" + UPDATED_PROTEINA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByProteinaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where proteina not equals to DEFAULT_PROTEINA
        defaultFQLecheShouldNotBeFound("proteina.notEquals=" + DEFAULT_PROTEINA);

        // Get all the fQLecheList where proteina not equals to UPDATED_PROTEINA
        defaultFQLecheShouldBeFound("proteina.notEquals=" + UPDATED_PROTEINA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByProteinaIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where proteina in DEFAULT_PROTEINA or UPDATED_PROTEINA
        defaultFQLecheShouldBeFound("proteina.in=" + DEFAULT_PROTEINA + "," + UPDATED_PROTEINA);

        // Get all the fQLecheList where proteina equals to UPDATED_PROTEINA
        defaultFQLecheShouldNotBeFound("proteina.in=" + UPDATED_PROTEINA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByProteinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where proteina is not null
        defaultFQLecheShouldBeFound("proteina.specified=true");

        // Get all the fQLecheList where proteina is null
        defaultFQLecheShouldNotBeFound("proteina.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByProteinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where proteina is greater than or equal to DEFAULT_PROTEINA
        defaultFQLecheShouldBeFound("proteina.greaterThanOrEqual=" + DEFAULT_PROTEINA);

        // Get all the fQLecheList where proteina is greater than or equal to UPDATED_PROTEINA
        defaultFQLecheShouldNotBeFound("proteina.greaterThanOrEqual=" + UPDATED_PROTEINA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByProteinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where proteina is less than or equal to DEFAULT_PROTEINA
        defaultFQLecheShouldBeFound("proteina.lessThanOrEqual=" + DEFAULT_PROTEINA);

        // Get all the fQLecheList where proteina is less than or equal to SMALLER_PROTEINA
        defaultFQLecheShouldNotBeFound("proteina.lessThanOrEqual=" + SMALLER_PROTEINA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByProteinaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where proteina is less than DEFAULT_PROTEINA
        defaultFQLecheShouldNotBeFound("proteina.lessThan=" + DEFAULT_PROTEINA);

        // Get all the fQLecheList where proteina is less than UPDATED_PROTEINA
        defaultFQLecheShouldBeFound("proteina.lessThan=" + UPDATED_PROTEINA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByProteinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where proteina is greater than DEFAULT_PROTEINA
        defaultFQLecheShouldNotBeFound("proteina.greaterThan=" + DEFAULT_PROTEINA);

        // Get all the fQLecheList where proteina is greater than SMALLER_PROTEINA
        defaultFQLecheShouldBeFound("proteina.greaterThan=" + SMALLER_PROTEINA);
    }


    @Test
    @Transactional
    public void getAllFQLechesByNeutralizantesIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where neutralizantes equals to DEFAULT_NEUTRALIZANTES
        defaultFQLecheShouldBeFound("neutralizantes.equals=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQLecheList where neutralizantes equals to UPDATED_NEUTRALIZANTES
        defaultFQLecheShouldNotBeFound("neutralizantes.equals=" + UPDATED_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByNeutralizantesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where neutralizantes not equals to DEFAULT_NEUTRALIZANTES
        defaultFQLecheShouldNotBeFound("neutralizantes.notEquals=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQLecheList where neutralizantes not equals to UPDATED_NEUTRALIZANTES
        defaultFQLecheShouldBeFound("neutralizantes.notEquals=" + UPDATED_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByNeutralizantesIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where neutralizantes in DEFAULT_NEUTRALIZANTES or UPDATED_NEUTRALIZANTES
        defaultFQLecheShouldBeFound("neutralizantes.in=" + DEFAULT_NEUTRALIZANTES + "," + UPDATED_NEUTRALIZANTES);

        // Get all the fQLecheList where neutralizantes equals to UPDATED_NEUTRALIZANTES
        defaultFQLecheShouldNotBeFound("neutralizantes.in=" + UPDATED_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByNeutralizantesIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where neutralizantes is not null
        defaultFQLecheShouldBeFound("neutralizantes.specified=true");

        // Get all the fQLecheList where neutralizantes is null
        defaultFQLecheShouldNotBeFound("neutralizantes.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByNeutralizantesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where neutralizantes is greater than or equal to DEFAULT_NEUTRALIZANTES
        defaultFQLecheShouldBeFound("neutralizantes.greaterThanOrEqual=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQLecheList where neutralizantes is greater than or equal to UPDATED_NEUTRALIZANTES
        defaultFQLecheShouldNotBeFound("neutralizantes.greaterThanOrEqual=" + UPDATED_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByNeutralizantesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where neutralizantes is less than or equal to DEFAULT_NEUTRALIZANTES
        defaultFQLecheShouldBeFound("neutralizantes.lessThanOrEqual=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQLecheList where neutralizantes is less than or equal to SMALLER_NEUTRALIZANTES
        defaultFQLecheShouldNotBeFound("neutralizantes.lessThanOrEqual=" + SMALLER_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByNeutralizantesIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where neutralizantes is less than DEFAULT_NEUTRALIZANTES
        defaultFQLecheShouldNotBeFound("neutralizantes.lessThan=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQLecheList where neutralizantes is less than UPDATED_NEUTRALIZANTES
        defaultFQLecheShouldBeFound("neutralizantes.lessThan=" + UPDATED_NEUTRALIZANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByNeutralizantesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where neutralizantes is greater than DEFAULT_NEUTRALIZANTES
        defaultFQLecheShouldNotBeFound("neutralizantes.greaterThan=" + DEFAULT_NEUTRALIZANTES);

        // Get all the fQLecheList where neutralizantes is greater than SMALLER_NEUTRALIZANTES
        defaultFQLecheShouldBeFound("neutralizantes.greaterThan=" + SMALLER_NEUTRALIZANTES);
    }


    @Test
    @Transactional
    public void getAllFQLechesByAdulterantesIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where adulterantes equals to DEFAULT_ADULTERANTES
        defaultFQLecheShouldBeFound("adulterantes.equals=" + DEFAULT_ADULTERANTES);

        // Get all the fQLecheList where adulterantes equals to UPDATED_ADULTERANTES
        defaultFQLecheShouldNotBeFound("adulterantes.equals=" + UPDATED_ADULTERANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAdulterantesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where adulterantes not equals to DEFAULT_ADULTERANTES
        defaultFQLecheShouldNotBeFound("adulterantes.notEquals=" + DEFAULT_ADULTERANTES);

        // Get all the fQLecheList where adulterantes not equals to UPDATED_ADULTERANTES
        defaultFQLecheShouldBeFound("adulterantes.notEquals=" + UPDATED_ADULTERANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAdulterantesIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where adulterantes in DEFAULT_ADULTERANTES or UPDATED_ADULTERANTES
        defaultFQLecheShouldBeFound("adulterantes.in=" + DEFAULT_ADULTERANTES + "," + UPDATED_ADULTERANTES);

        // Get all the fQLecheList where adulterantes equals to UPDATED_ADULTERANTES
        defaultFQLecheShouldNotBeFound("adulterantes.in=" + UPDATED_ADULTERANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAdulterantesIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where adulterantes is not null
        defaultFQLecheShouldBeFound("adulterantes.specified=true");

        // Get all the fQLecheList where adulterantes is null
        defaultFQLecheShouldNotBeFound("adulterantes.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByAdulterantesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where adulterantes is greater than or equal to DEFAULT_ADULTERANTES
        defaultFQLecheShouldBeFound("adulterantes.greaterThanOrEqual=" + DEFAULT_ADULTERANTES);

        // Get all the fQLecheList where adulterantes is greater than or equal to UPDATED_ADULTERANTES
        defaultFQLecheShouldNotBeFound("adulterantes.greaterThanOrEqual=" + UPDATED_ADULTERANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAdulterantesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where adulterantes is less than or equal to DEFAULT_ADULTERANTES
        defaultFQLecheShouldBeFound("adulterantes.lessThanOrEqual=" + DEFAULT_ADULTERANTES);

        // Get all the fQLecheList where adulterantes is less than or equal to SMALLER_ADULTERANTES
        defaultFQLecheShouldNotBeFound("adulterantes.lessThanOrEqual=" + SMALLER_ADULTERANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAdulterantesIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where adulterantes is less than DEFAULT_ADULTERANTES
        defaultFQLecheShouldNotBeFound("adulterantes.lessThan=" + DEFAULT_ADULTERANTES);

        // Get all the fQLecheList where adulterantes is less than UPDATED_ADULTERANTES
        defaultFQLecheShouldBeFound("adulterantes.lessThan=" + UPDATED_ADULTERANTES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByAdulterantesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where adulterantes is greater than DEFAULT_ADULTERANTES
        defaultFQLecheShouldNotBeFound("adulterantes.greaterThan=" + DEFAULT_ADULTERANTES);

        // Get all the fQLecheList where adulterantes is greater than SMALLER_ADULTERANTES
        defaultFQLecheShouldBeFound("adulterantes.greaterThan=" + SMALLER_ADULTERANTES);
    }


    @Test
    @Transactional
    public void getAllFQLechesByReductasaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where reductasa equals to DEFAULT_REDUCTASA
        defaultFQLecheShouldBeFound("reductasa.equals=" + DEFAULT_REDUCTASA);

        // Get all the fQLecheList where reductasa equals to UPDATED_REDUCTASA
        defaultFQLecheShouldNotBeFound("reductasa.equals=" + UPDATED_REDUCTASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByReductasaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where reductasa not equals to DEFAULT_REDUCTASA
        defaultFQLecheShouldNotBeFound("reductasa.notEquals=" + DEFAULT_REDUCTASA);

        // Get all the fQLecheList where reductasa not equals to UPDATED_REDUCTASA
        defaultFQLecheShouldBeFound("reductasa.notEquals=" + UPDATED_REDUCTASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByReductasaIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where reductasa in DEFAULT_REDUCTASA or UPDATED_REDUCTASA
        defaultFQLecheShouldBeFound("reductasa.in=" + DEFAULT_REDUCTASA + "," + UPDATED_REDUCTASA);

        // Get all the fQLecheList where reductasa equals to UPDATED_REDUCTASA
        defaultFQLecheShouldNotBeFound("reductasa.in=" + UPDATED_REDUCTASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByReductasaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where reductasa is not null
        defaultFQLecheShouldBeFound("reductasa.specified=true");

        // Get all the fQLecheList where reductasa is null
        defaultFQLecheShouldNotBeFound("reductasa.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByReductasaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where reductasa is greater than or equal to DEFAULT_REDUCTASA
        defaultFQLecheShouldBeFound("reductasa.greaterThanOrEqual=" + DEFAULT_REDUCTASA);

        // Get all the fQLecheList where reductasa is greater than or equal to UPDATED_REDUCTASA
        defaultFQLecheShouldNotBeFound("reductasa.greaterThanOrEqual=" + UPDATED_REDUCTASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByReductasaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where reductasa is less than or equal to DEFAULT_REDUCTASA
        defaultFQLecheShouldBeFound("reductasa.lessThanOrEqual=" + DEFAULT_REDUCTASA);

        // Get all the fQLecheList where reductasa is less than or equal to SMALLER_REDUCTASA
        defaultFQLecheShouldNotBeFound("reductasa.lessThanOrEqual=" + SMALLER_REDUCTASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByReductasaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where reductasa is less than DEFAULT_REDUCTASA
        defaultFQLecheShouldNotBeFound("reductasa.lessThan=" + DEFAULT_REDUCTASA);

        // Get all the fQLecheList where reductasa is less than UPDATED_REDUCTASA
        defaultFQLecheShouldBeFound("reductasa.lessThan=" + UPDATED_REDUCTASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByReductasaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where reductasa is greater than DEFAULT_REDUCTASA
        defaultFQLecheShouldNotBeFound("reductasa.greaterThan=" + DEFAULT_REDUCTASA);

        // Get all the fQLecheList where reductasa is greater than SMALLER_REDUCTASA
        defaultFQLecheShouldBeFound("reductasa.greaterThan=" + SMALLER_REDUCTASA);
    }


    @Test
    @Transactional
    public void getAllFQLechesByFosfatasaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fosfatasa equals to DEFAULT_FOSFATASA
        defaultFQLecheShouldBeFound("fosfatasa.equals=" + DEFAULT_FOSFATASA);

        // Get all the fQLecheList where fosfatasa equals to UPDATED_FOSFATASA
        defaultFQLecheShouldNotBeFound("fosfatasa.equals=" + UPDATED_FOSFATASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByFosfatasaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fosfatasa not equals to DEFAULT_FOSFATASA
        defaultFQLecheShouldNotBeFound("fosfatasa.notEquals=" + DEFAULT_FOSFATASA);

        // Get all the fQLecheList where fosfatasa not equals to UPDATED_FOSFATASA
        defaultFQLecheShouldBeFound("fosfatasa.notEquals=" + UPDATED_FOSFATASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByFosfatasaIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fosfatasa in DEFAULT_FOSFATASA or UPDATED_FOSFATASA
        defaultFQLecheShouldBeFound("fosfatasa.in=" + DEFAULT_FOSFATASA + "," + UPDATED_FOSFATASA);

        // Get all the fQLecheList where fosfatasa equals to UPDATED_FOSFATASA
        defaultFQLecheShouldNotBeFound("fosfatasa.in=" + UPDATED_FOSFATASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByFosfatasaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fosfatasa is not null
        defaultFQLecheShouldBeFound("fosfatasa.specified=true");

        // Get all the fQLecheList where fosfatasa is null
        defaultFQLecheShouldNotBeFound("fosfatasa.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByFosfatasaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fosfatasa is greater than or equal to DEFAULT_FOSFATASA
        defaultFQLecheShouldBeFound("fosfatasa.greaterThanOrEqual=" + DEFAULT_FOSFATASA);

        // Get all the fQLecheList where fosfatasa is greater than or equal to UPDATED_FOSFATASA
        defaultFQLecheShouldNotBeFound("fosfatasa.greaterThanOrEqual=" + UPDATED_FOSFATASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByFosfatasaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fosfatasa is less than or equal to DEFAULT_FOSFATASA
        defaultFQLecheShouldBeFound("fosfatasa.lessThanOrEqual=" + DEFAULT_FOSFATASA);

        // Get all the fQLecheList where fosfatasa is less than or equal to SMALLER_FOSFATASA
        defaultFQLecheShouldNotBeFound("fosfatasa.lessThanOrEqual=" + SMALLER_FOSFATASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByFosfatasaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fosfatasa is less than DEFAULT_FOSFATASA
        defaultFQLecheShouldNotBeFound("fosfatasa.lessThan=" + DEFAULT_FOSFATASA);

        // Get all the fQLecheList where fosfatasa is less than UPDATED_FOSFATASA
        defaultFQLecheShouldBeFound("fosfatasa.lessThan=" + UPDATED_FOSFATASA);
    }

    @Test
    @Transactional
    public void getAllFQLechesByFosfatasaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where fosfatasa is greater than DEFAULT_FOSFATASA
        defaultFQLecheShouldNotBeFound("fosfatasa.greaterThan=" + DEFAULT_FOSFATASA);

        // Get all the fQLecheList where fosfatasa is greater than SMALLER_FOSFATASA
        defaultFQLecheShouldBeFound("fosfatasa.greaterThan=" + SMALLER_FOSFATASA);
    }


    @Test
    @Transactional
    public void getAllFQLechesByPhIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where ph equals to DEFAULT_PH
        defaultFQLecheShouldBeFound("ph.equals=" + DEFAULT_PH);

        // Get all the fQLecheList where ph equals to UPDATED_PH
        defaultFQLecheShouldNotBeFound("ph.equals=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQLechesByPhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where ph not equals to DEFAULT_PH
        defaultFQLecheShouldNotBeFound("ph.notEquals=" + DEFAULT_PH);

        // Get all the fQLecheList where ph not equals to UPDATED_PH
        defaultFQLecheShouldBeFound("ph.notEquals=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQLechesByPhIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where ph in DEFAULT_PH or UPDATED_PH
        defaultFQLecheShouldBeFound("ph.in=" + DEFAULT_PH + "," + UPDATED_PH);

        // Get all the fQLecheList where ph equals to UPDATED_PH
        defaultFQLecheShouldNotBeFound("ph.in=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQLechesByPhIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where ph is not null
        defaultFQLecheShouldBeFound("ph.specified=true");

        // Get all the fQLecheList where ph is null
        defaultFQLecheShouldNotBeFound("ph.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQLechesByPhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where ph is greater than or equal to DEFAULT_PH
        defaultFQLecheShouldBeFound("ph.greaterThanOrEqual=" + DEFAULT_PH);

        // Get all the fQLecheList where ph is greater than or equal to UPDATED_PH
        defaultFQLecheShouldNotBeFound("ph.greaterThanOrEqual=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQLechesByPhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where ph is less than or equal to DEFAULT_PH
        defaultFQLecheShouldBeFound("ph.lessThanOrEqual=" + DEFAULT_PH);

        // Get all the fQLecheList where ph is less than or equal to SMALLER_PH
        defaultFQLecheShouldNotBeFound("ph.lessThanOrEqual=" + SMALLER_PH);
    }

    @Test
    @Transactional
    public void getAllFQLechesByPhIsLessThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where ph is less than DEFAULT_PH
        defaultFQLecheShouldNotBeFound("ph.lessThan=" + DEFAULT_PH);

        // Get all the fQLecheList where ph is less than UPDATED_PH
        defaultFQLecheShouldBeFound("ph.lessThan=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQLechesByPhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where ph is greater than DEFAULT_PH
        defaultFQLecheShouldNotBeFound("ph.greaterThan=" + DEFAULT_PH);

        // Get all the fQLecheList where ph is greater than SMALLER_PH
        defaultFQLecheShouldBeFound("ph.greaterThan=" + SMALLER_PH);
    }


    @Test
    @Transactional
    public void getAllFQLechesByObservacionesIsEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where observaciones equals to DEFAULT_OBSERVACIONES
        defaultFQLecheShouldBeFound("observaciones.equals=" + DEFAULT_OBSERVACIONES);

        // Get all the fQLecheList where observaciones equals to UPDATED_OBSERVACIONES
        defaultFQLecheShouldNotBeFound("observaciones.equals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByObservacionesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where observaciones not equals to DEFAULT_OBSERVACIONES
        defaultFQLecheShouldNotBeFound("observaciones.notEquals=" + DEFAULT_OBSERVACIONES);

        // Get all the fQLecheList where observaciones not equals to UPDATED_OBSERVACIONES
        defaultFQLecheShouldBeFound("observaciones.notEquals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByObservacionesIsInShouldWork() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where observaciones in DEFAULT_OBSERVACIONES or UPDATED_OBSERVACIONES
        defaultFQLecheShouldBeFound("observaciones.in=" + DEFAULT_OBSERVACIONES + "," + UPDATED_OBSERVACIONES);

        // Get all the fQLecheList where observaciones equals to UPDATED_OBSERVACIONES
        defaultFQLecheShouldNotBeFound("observaciones.in=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByObservacionesIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where observaciones is not null
        defaultFQLecheShouldBeFound("observaciones.specified=true");

        // Get all the fQLecheList where observaciones is null
        defaultFQLecheShouldNotBeFound("observaciones.specified=false");
    }
                @Test
    @Transactional
    public void getAllFQLechesByObservacionesContainsSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where observaciones contains DEFAULT_OBSERVACIONES
        defaultFQLecheShouldBeFound("observaciones.contains=" + DEFAULT_OBSERVACIONES);

        // Get all the fQLecheList where observaciones contains UPDATED_OBSERVACIONES
        defaultFQLecheShouldNotBeFound("observaciones.contains=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQLechesByObservacionesNotContainsSomething() throws Exception {
        // Initialize the database
        fQLecheRepository.saveAndFlush(fQLeche);

        // Get all the fQLecheList where observaciones does not contain DEFAULT_OBSERVACIONES
        defaultFQLecheShouldNotBeFound("observaciones.doesNotContain=" + DEFAULT_OBSERVACIONES);

        // Get all the fQLecheList where observaciones does not contain UPDATED_OBSERVACIONES
        defaultFQLecheShouldBeFound("observaciones.doesNotContain=" + UPDATED_OBSERVACIONES);
    }


    @Test
    @Transactional
    public void getAllFQLechesByAreaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Area area = fQLeche.getArea();
        fQLecheRepository.saveAndFlush(fQLeche);
        Long areaId = area.getId();

        // Get all the fQLecheList where area equals to areaId
        defaultFQLecheShouldBeFound("areaId.equals=" + areaId);

        // Get all the fQLecheList where area equals to areaId + 1
        defaultFQLecheShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }


    @Test
    @Transactional
    public void getAllFQLechesByRecepcionIsEqualToSomething() throws Exception {
        // Get already existing entity
        Recepcion recepcion = fQLeche.getRecepcion();
        fQLecheRepository.saveAndFlush(fQLeche);
        Long recepcionId = recepcion.getId();

        // Get all the fQLecheList where recepcion equals to recepcionId
        defaultFQLecheShouldBeFound("recepcionId.equals=" + recepcionId);

        // Get all the fQLecheList where recepcion equals to recepcionId + 1
        defaultFQLecheShouldNotBeFound("recepcionId.equals=" + (recepcionId + 1));
    }


    @Test
    @Transactional
    public void getAllFQLechesByAnalistaIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserExtra analista = fQLeche.getAnalista();
        fQLecheRepository.saveAndFlush(fQLeche);
        Long analistaId = analista.getId();

        // Get all the fQLecheList where analista equals to analistaId
        defaultFQLecheShouldBeFound("analistaId.equals=" + analistaId);

        // Get all the fQLecheList where analista equals to analistaId + 1
        defaultFQLecheShouldNotBeFound("analistaId.equals=" + (analistaId + 1));
    }


    @Test
    @Transactional
    public void getAllFQLechesByProveedorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Personal proveedor = fQLeche.getProveedor();
        fQLecheRepository.saveAndFlush(fQLeche);
        Long proveedorId = proveedor.getId();

        // Get all the fQLecheList where proveedor equals to proveedorId
        defaultFQLecheShouldBeFound("proveedorId.equals=" + proveedorId);

        // Get all the fQLecheList where proveedor equals to proveedorId + 1
        defaultFQLecheShouldNotBeFound("proveedorId.equals=" + (proveedorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFQLecheShouldBeFound(String filter) throws Exception {
        restFQLecheMockMvc.perform(get("/api/fq-leches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fQLeche.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].acidez").value(hasItem(DEFAULT_ACIDEZ.doubleValue())))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA.doubleValue())))
            .andExpect(jsonPath("$.[*].agua").value(hasItem(DEFAULT_AGUA.doubleValue())))
            .andExpect(jsonPath("$.[*].crioscopia").value(hasItem(DEFAULT_CRIOSCOPIA.doubleValue())))
            .andExpect(jsonPath("$.[*].antibiotico").value(hasItem(DEFAULT_ANTIBIOTICO)))
            .andExpect(jsonPath("$.[*].delvo").value(hasItem(DEFAULT_DELVO.doubleValue())))
            .andExpect(jsonPath("$.[*].grasa").value(hasItem(DEFAULT_GRASA.doubleValue())))
            .andExpect(jsonPath("$.[*].solidos").value(hasItem(DEFAULT_SOLIDOS)))
            .andExpect(jsonPath("$.[*].densidad").value(hasItem(DEFAULT_DENSIDAD.doubleValue())))
            .andExpect(jsonPath("$.[*].lactosa").value(hasItem(DEFAULT_LACTOSA.doubleValue())))
            .andExpect(jsonPath("$.[*].proteina").value(hasItem(DEFAULT_PROTEINA.doubleValue())))
            .andExpect(jsonPath("$.[*].neutralizantes").value(hasItem(DEFAULT_NEUTRALIZANTES.doubleValue())))
            .andExpect(jsonPath("$.[*].adulterantes").value(hasItem(DEFAULT_ADULTERANTES.doubleValue())))
            .andExpect(jsonPath("$.[*].reductasa").value(hasItem(DEFAULT_REDUCTASA.doubleValue())))
            .andExpect(jsonPath("$.[*].fosfatasa").value(hasItem(DEFAULT_FOSFATASA.doubleValue())))
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.doubleValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));

        // Check, that the count call also returns 1
        restFQLecheMockMvc.perform(get("/api/fq-leches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFQLecheShouldNotBeFound(String filter) throws Exception {
        restFQLecheMockMvc.perform(get("/api/fq-leches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFQLecheMockMvc.perform(get("/api/fq-leches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFQLeche() throws Exception {
        // Get the fQLeche
        restFQLecheMockMvc.perform(get("/api/fq-leches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFQLeche() throws Exception {
        // Initialize the database
        fQLecheService.save(fQLeche);

        int databaseSizeBeforeUpdate = fQLecheRepository.findAll().size();

        // Update the fQLeche
        FQLeche updatedFQLeche = fQLecheRepository.findById(fQLeche.getId()).get();
        // Disconnect from session so that the updates on updatedFQLeche are not directly saved in db
        em.detach(updatedFQLeche);
        updatedFQLeche
            .fecha(UPDATED_FECHA)
            .lote(UPDATED_LOTE)
            .acidez(UPDATED_ACIDEZ)
            .temperatura(UPDATED_TEMPERATURA)
            .agua(UPDATED_AGUA)
            .crioscopia(UPDATED_CRIOSCOPIA)
            .antibiotico(UPDATED_ANTIBIOTICO)
            .delvo(UPDATED_DELVO)
            .grasa(UPDATED_GRASA)
            .solidos(UPDATED_SOLIDOS)
            .densidad(UPDATED_DENSIDAD)
            .lactosa(UPDATED_LACTOSA)
            .proteina(UPDATED_PROTEINA)
            .neutralizantes(UPDATED_NEUTRALIZANTES)
            .adulterantes(UPDATED_ADULTERANTES)
            .reductasa(UPDATED_REDUCTASA)
            .fosfatasa(UPDATED_FOSFATASA)
            .ph(UPDATED_PH)
            .observaciones(UPDATED_OBSERVACIONES);

        restFQLecheMockMvc.perform(put("/api/fq-leches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFQLeche)))
            .andExpect(status().isOk());

        // Validate the FQLeche in the database
        List<FQLeche> fQLecheList = fQLecheRepository.findAll();
        assertThat(fQLecheList).hasSize(databaseSizeBeforeUpdate);
        FQLeche testFQLeche = fQLecheList.get(fQLecheList.size() - 1);
        assertThat(testFQLeche.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFQLeche.getLote()).isEqualTo(UPDATED_LOTE);
        assertThat(testFQLeche.getAcidez()).isEqualTo(UPDATED_ACIDEZ);
        assertThat(testFQLeche.getTemperatura()).isEqualTo(UPDATED_TEMPERATURA);
        assertThat(testFQLeche.getAgua()).isEqualTo(UPDATED_AGUA);
        assertThat(testFQLeche.getCrioscopia()).isEqualTo(UPDATED_CRIOSCOPIA);
        assertThat(testFQLeche.getAntibiotico()).isEqualTo(UPDATED_ANTIBIOTICO);
        assertThat(testFQLeche.getDelvo()).isEqualTo(UPDATED_DELVO);
        assertThat(testFQLeche.getGrasa()).isEqualTo(UPDATED_GRASA);
        assertThat(testFQLeche.getSolidos()).isEqualTo(UPDATED_SOLIDOS);
        assertThat(testFQLeche.getDensidad()).isEqualTo(UPDATED_DENSIDAD);
        assertThat(testFQLeche.getLactosa()).isEqualTo(UPDATED_LACTOSA);
        assertThat(testFQLeche.getProteina()).isEqualTo(UPDATED_PROTEINA);
        assertThat(testFQLeche.getNeutralizantes()).isEqualTo(UPDATED_NEUTRALIZANTES);
        assertThat(testFQLeche.getAdulterantes()).isEqualTo(UPDATED_ADULTERANTES);
        assertThat(testFQLeche.getReductasa()).isEqualTo(UPDATED_REDUCTASA);
        assertThat(testFQLeche.getFosfatasa()).isEqualTo(UPDATED_FOSFATASA);
        assertThat(testFQLeche.getPh()).isEqualTo(UPDATED_PH);
        assertThat(testFQLeche.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingFQLeche() throws Exception {
        int databaseSizeBeforeUpdate = fQLecheRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFQLecheMockMvc.perform(put("/api/fq-leches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQLeche)))
            .andExpect(status().isBadRequest());

        // Validate the FQLeche in the database
        List<FQLeche> fQLecheList = fQLecheRepository.findAll();
        assertThat(fQLecheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFQLeche() throws Exception {
        // Initialize the database
        fQLecheService.save(fQLeche);

        int databaseSizeBeforeDelete = fQLecheRepository.findAll().size();

        // Delete the fQLeche
        restFQLecheMockMvc.perform(delete("/api/fq-leches/{id}", fQLeche.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FQLeche> fQLecheList = fQLecheRepository.findAll();
        assertThat(fQLecheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
