package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.FQCrema;
import com.provi.lab.domain.Area;
import com.provi.lab.domain.Producto;
import com.provi.lab.domain.UserExtra;
import com.provi.lab.domain.Personal;
import com.provi.lab.repository.FQCremaRepository;
import com.provi.lab.service.FQCremaService;
import com.provi.lab.service.dto.FQCremaCriteria;
import com.provi.lab.service.FQCremaQueryService;

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
 * Integration tests for the {@link FQCremaResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FQCremaResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_LOTE = "BBBBBBBBBB";

    private static final Double DEFAULT_ACIDEZ = 1D;
    private static final Double UPDATED_ACIDEZ = 2D;
    private static final Double SMALLER_ACIDEZ = 1D - 1D;

    private static final Double DEFAULT_GRASA = 1D;
    private static final Double UPDATED_GRASA = 2D;
    private static final Double SMALLER_GRASA = 1D - 1D;

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    @Autowired
    private FQCremaRepository fQCremaRepository;

    @Autowired
    private FQCremaService fQCremaService;

    @Autowired
    private FQCremaQueryService fQCremaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFQCremaMockMvc;

    private FQCrema fQCrema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FQCrema createEntity(EntityManager em) {
        FQCrema fQCrema = new FQCrema()
            .fecha(DEFAULT_FECHA)
            .lote(DEFAULT_LOTE)
            .acidez(DEFAULT_ACIDEZ)
            .grasa(DEFAULT_GRASA)
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
        fQCrema.setArea(area);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        fQCrema.setProducto(producto);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        fQCrema.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        fQCrema.setProveedor(personal);
        return fQCrema;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FQCrema createUpdatedEntity(EntityManager em) {
        FQCrema fQCrema = new FQCrema()
            .fecha(UPDATED_FECHA)
            .lote(UPDATED_LOTE)
            .acidez(UPDATED_ACIDEZ)
            .grasa(UPDATED_GRASA)
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
        fQCrema.setArea(area);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        fQCrema.setProducto(producto);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createUpdatedEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        fQCrema.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createUpdatedEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        fQCrema.setProveedor(personal);
        return fQCrema;
    }

    @BeforeEach
    public void initTest() {
        fQCrema = createEntity(em);
    }

    @Test
    @Transactional
    public void createFQCrema() throws Exception {
        int databaseSizeBeforeCreate = fQCremaRepository.findAll().size();
        // Create the FQCrema
        restFQCremaMockMvc.perform(post("/api/fq-cremas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQCrema)))
            .andExpect(status().isCreated());

        // Validate the FQCrema in the database
        List<FQCrema> fQCremaList = fQCremaRepository.findAll();
        assertThat(fQCremaList).hasSize(databaseSizeBeforeCreate + 1);
        FQCrema testFQCrema = fQCremaList.get(fQCremaList.size() - 1);
        assertThat(testFQCrema.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testFQCrema.getLote()).isEqualTo(DEFAULT_LOTE);
        assertThat(testFQCrema.getAcidez()).isEqualTo(DEFAULT_ACIDEZ);
        assertThat(testFQCrema.getGrasa()).isEqualTo(DEFAULT_GRASA);
        assertThat(testFQCrema.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void createFQCremaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fQCremaRepository.findAll().size();

        // Create the FQCrema with an existing ID
        fQCrema.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFQCremaMockMvc.perform(post("/api/fq-cremas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQCrema)))
            .andExpect(status().isBadRequest());

        // Validate the FQCrema in the database
        List<FQCrema> fQCremaList = fQCremaRepository.findAll();
        assertThat(fQCremaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fQCremaRepository.findAll().size();
        // set the field null
        fQCrema.setFecha(null);

        // Create the FQCrema, which fails.


        restFQCremaMockMvc.perform(post("/api/fq-cremas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQCrema)))
            .andExpect(status().isBadRequest());

        List<FQCrema> fQCremaList = fQCremaRepository.findAll();
        assertThat(fQCremaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = fQCremaRepository.findAll().size();
        // set the field null
        fQCrema.setLote(null);

        // Create the FQCrema, which fails.


        restFQCremaMockMvc.perform(post("/api/fq-cremas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQCrema)))
            .andExpect(status().isBadRequest());

        List<FQCrema> fQCremaList = fQCremaRepository.findAll();
        assertThat(fQCremaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFQCremas() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList
        restFQCremaMockMvc.perform(get("/api/fq-cremas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fQCrema.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].acidez").value(hasItem(DEFAULT_ACIDEZ.doubleValue())))
            .andExpect(jsonPath("$.[*].grasa").value(hasItem(DEFAULT_GRASA.doubleValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }
    
    @Test
    @Transactional
    public void getFQCrema() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get the fQCrema
        restFQCremaMockMvc.perform(get("/api/fq-cremas/{id}", fQCrema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fQCrema.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.lote").value(DEFAULT_LOTE))
            .andExpect(jsonPath("$.acidez").value(DEFAULT_ACIDEZ.doubleValue()))
            .andExpect(jsonPath("$.grasa").value(DEFAULT_GRASA.doubleValue()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }


    @Test
    @Transactional
    public void getFQCremasByIdFiltering() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        Long id = fQCrema.getId();

        defaultFQCremaShouldBeFound("id.equals=" + id);
        defaultFQCremaShouldNotBeFound("id.notEquals=" + id);

        defaultFQCremaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFQCremaShouldNotBeFound("id.greaterThan=" + id);

        defaultFQCremaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFQCremaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFQCremasByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where fecha equals to DEFAULT_FECHA
        defaultFQCremaShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the fQCremaList where fecha equals to UPDATED_FECHA
        defaultFQCremaShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQCremasByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where fecha not equals to DEFAULT_FECHA
        defaultFQCremaShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the fQCremaList where fecha not equals to UPDATED_FECHA
        defaultFQCremaShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQCremasByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultFQCremaShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the fQCremaList where fecha equals to UPDATED_FECHA
        defaultFQCremaShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFQCremasByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where fecha is not null
        defaultFQCremaShouldBeFound("fecha.specified=true");

        // Get all the fQCremaList where fecha is null
        defaultFQCremaShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQCremasByLoteIsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where lote equals to DEFAULT_LOTE
        defaultFQCremaShouldBeFound("lote.equals=" + DEFAULT_LOTE);

        // Get all the fQCremaList where lote equals to UPDATED_LOTE
        defaultFQCremaShouldNotBeFound("lote.equals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQCremasByLoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where lote not equals to DEFAULT_LOTE
        defaultFQCremaShouldNotBeFound("lote.notEquals=" + DEFAULT_LOTE);

        // Get all the fQCremaList where lote not equals to UPDATED_LOTE
        defaultFQCremaShouldBeFound("lote.notEquals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQCremasByLoteIsInShouldWork() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where lote in DEFAULT_LOTE or UPDATED_LOTE
        defaultFQCremaShouldBeFound("lote.in=" + DEFAULT_LOTE + "," + UPDATED_LOTE);

        // Get all the fQCremaList where lote equals to UPDATED_LOTE
        defaultFQCremaShouldNotBeFound("lote.in=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQCremasByLoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where lote is not null
        defaultFQCremaShouldBeFound("lote.specified=true");

        // Get all the fQCremaList where lote is null
        defaultFQCremaShouldNotBeFound("lote.specified=false");
    }
                @Test
    @Transactional
    public void getAllFQCremasByLoteContainsSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where lote contains DEFAULT_LOTE
        defaultFQCremaShouldBeFound("lote.contains=" + DEFAULT_LOTE);

        // Get all the fQCremaList where lote contains UPDATED_LOTE
        defaultFQCremaShouldNotBeFound("lote.contains=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllFQCremasByLoteNotContainsSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where lote does not contain DEFAULT_LOTE
        defaultFQCremaShouldNotBeFound("lote.doesNotContain=" + DEFAULT_LOTE);

        // Get all the fQCremaList where lote does not contain UPDATED_LOTE
        defaultFQCremaShouldBeFound("lote.doesNotContain=" + UPDATED_LOTE);
    }


    @Test
    @Transactional
    public void getAllFQCremasByAcidezIsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where acidez equals to DEFAULT_ACIDEZ
        defaultFQCremaShouldBeFound("acidez.equals=" + DEFAULT_ACIDEZ);

        // Get all the fQCremaList where acidez equals to UPDATED_ACIDEZ
        defaultFQCremaShouldNotBeFound("acidez.equals=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQCremasByAcidezIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where acidez not equals to DEFAULT_ACIDEZ
        defaultFQCremaShouldNotBeFound("acidez.notEquals=" + DEFAULT_ACIDEZ);

        // Get all the fQCremaList where acidez not equals to UPDATED_ACIDEZ
        defaultFQCremaShouldBeFound("acidez.notEquals=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQCremasByAcidezIsInShouldWork() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where acidez in DEFAULT_ACIDEZ or UPDATED_ACIDEZ
        defaultFQCremaShouldBeFound("acidez.in=" + DEFAULT_ACIDEZ + "," + UPDATED_ACIDEZ);

        // Get all the fQCremaList where acidez equals to UPDATED_ACIDEZ
        defaultFQCremaShouldNotBeFound("acidez.in=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQCremasByAcidezIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where acidez is not null
        defaultFQCremaShouldBeFound("acidez.specified=true");

        // Get all the fQCremaList where acidez is null
        defaultFQCremaShouldNotBeFound("acidez.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQCremasByAcidezIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where acidez is greater than or equal to DEFAULT_ACIDEZ
        defaultFQCremaShouldBeFound("acidez.greaterThanOrEqual=" + DEFAULT_ACIDEZ);

        // Get all the fQCremaList where acidez is greater than or equal to UPDATED_ACIDEZ
        defaultFQCremaShouldNotBeFound("acidez.greaterThanOrEqual=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQCremasByAcidezIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where acidez is less than or equal to DEFAULT_ACIDEZ
        defaultFQCremaShouldBeFound("acidez.lessThanOrEqual=" + DEFAULT_ACIDEZ);

        // Get all the fQCremaList where acidez is less than or equal to SMALLER_ACIDEZ
        defaultFQCremaShouldNotBeFound("acidez.lessThanOrEqual=" + SMALLER_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQCremasByAcidezIsLessThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where acidez is less than DEFAULT_ACIDEZ
        defaultFQCremaShouldNotBeFound("acidez.lessThan=" + DEFAULT_ACIDEZ);

        // Get all the fQCremaList where acidez is less than UPDATED_ACIDEZ
        defaultFQCremaShouldBeFound("acidez.lessThan=" + UPDATED_ACIDEZ);
    }

    @Test
    @Transactional
    public void getAllFQCremasByAcidezIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where acidez is greater than DEFAULT_ACIDEZ
        defaultFQCremaShouldNotBeFound("acidez.greaterThan=" + DEFAULT_ACIDEZ);

        // Get all the fQCremaList where acidez is greater than SMALLER_ACIDEZ
        defaultFQCremaShouldBeFound("acidez.greaterThan=" + SMALLER_ACIDEZ);
    }


    @Test
    @Transactional
    public void getAllFQCremasByGrasaIsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where grasa equals to DEFAULT_GRASA
        defaultFQCremaShouldBeFound("grasa.equals=" + DEFAULT_GRASA);

        // Get all the fQCremaList where grasa equals to UPDATED_GRASA
        defaultFQCremaShouldNotBeFound("grasa.equals=" + UPDATED_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQCremasByGrasaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where grasa not equals to DEFAULT_GRASA
        defaultFQCremaShouldNotBeFound("grasa.notEquals=" + DEFAULT_GRASA);

        // Get all the fQCremaList where grasa not equals to UPDATED_GRASA
        defaultFQCremaShouldBeFound("grasa.notEquals=" + UPDATED_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQCremasByGrasaIsInShouldWork() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where grasa in DEFAULT_GRASA or UPDATED_GRASA
        defaultFQCremaShouldBeFound("grasa.in=" + DEFAULT_GRASA + "," + UPDATED_GRASA);

        // Get all the fQCremaList where grasa equals to UPDATED_GRASA
        defaultFQCremaShouldNotBeFound("grasa.in=" + UPDATED_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQCremasByGrasaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where grasa is not null
        defaultFQCremaShouldBeFound("grasa.specified=true");

        // Get all the fQCremaList where grasa is null
        defaultFQCremaShouldNotBeFound("grasa.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQCremasByGrasaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where grasa is greater than or equal to DEFAULT_GRASA
        defaultFQCremaShouldBeFound("grasa.greaterThanOrEqual=" + DEFAULT_GRASA);

        // Get all the fQCremaList where grasa is greater than or equal to UPDATED_GRASA
        defaultFQCremaShouldNotBeFound("grasa.greaterThanOrEqual=" + UPDATED_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQCremasByGrasaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where grasa is less than or equal to DEFAULT_GRASA
        defaultFQCremaShouldBeFound("grasa.lessThanOrEqual=" + DEFAULT_GRASA);

        // Get all the fQCremaList where grasa is less than or equal to SMALLER_GRASA
        defaultFQCremaShouldNotBeFound("grasa.lessThanOrEqual=" + SMALLER_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQCremasByGrasaIsLessThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where grasa is less than DEFAULT_GRASA
        defaultFQCremaShouldNotBeFound("grasa.lessThan=" + DEFAULT_GRASA);

        // Get all the fQCremaList where grasa is less than UPDATED_GRASA
        defaultFQCremaShouldBeFound("grasa.lessThan=" + UPDATED_GRASA);
    }

    @Test
    @Transactional
    public void getAllFQCremasByGrasaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where grasa is greater than DEFAULT_GRASA
        defaultFQCremaShouldNotBeFound("grasa.greaterThan=" + DEFAULT_GRASA);

        // Get all the fQCremaList where grasa is greater than SMALLER_GRASA
        defaultFQCremaShouldBeFound("grasa.greaterThan=" + SMALLER_GRASA);
    }


    @Test
    @Transactional
    public void getAllFQCremasByObservacionesIsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where observaciones equals to DEFAULT_OBSERVACIONES
        defaultFQCremaShouldBeFound("observaciones.equals=" + DEFAULT_OBSERVACIONES);

        // Get all the fQCremaList where observaciones equals to UPDATED_OBSERVACIONES
        defaultFQCremaShouldNotBeFound("observaciones.equals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQCremasByObservacionesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where observaciones not equals to DEFAULT_OBSERVACIONES
        defaultFQCremaShouldNotBeFound("observaciones.notEquals=" + DEFAULT_OBSERVACIONES);

        // Get all the fQCremaList where observaciones not equals to UPDATED_OBSERVACIONES
        defaultFQCremaShouldBeFound("observaciones.notEquals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQCremasByObservacionesIsInShouldWork() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where observaciones in DEFAULT_OBSERVACIONES or UPDATED_OBSERVACIONES
        defaultFQCremaShouldBeFound("observaciones.in=" + DEFAULT_OBSERVACIONES + "," + UPDATED_OBSERVACIONES);

        // Get all the fQCremaList where observaciones equals to UPDATED_OBSERVACIONES
        defaultFQCremaShouldNotBeFound("observaciones.in=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQCremasByObservacionesIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where observaciones is not null
        defaultFQCremaShouldBeFound("observaciones.specified=true");

        // Get all the fQCremaList where observaciones is null
        defaultFQCremaShouldNotBeFound("observaciones.specified=false");
    }
                @Test
    @Transactional
    public void getAllFQCremasByObservacionesContainsSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where observaciones contains DEFAULT_OBSERVACIONES
        defaultFQCremaShouldBeFound("observaciones.contains=" + DEFAULT_OBSERVACIONES);

        // Get all the fQCremaList where observaciones contains UPDATED_OBSERVACIONES
        defaultFQCremaShouldNotBeFound("observaciones.contains=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllFQCremasByObservacionesNotContainsSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where observaciones does not contain DEFAULT_OBSERVACIONES
        defaultFQCremaShouldNotBeFound("observaciones.doesNotContain=" + DEFAULT_OBSERVACIONES);

        // Get all the fQCremaList where observaciones does not contain UPDATED_OBSERVACIONES
        defaultFQCremaShouldBeFound("observaciones.doesNotContain=" + UPDATED_OBSERVACIONES);
    }


    @Test
    @Transactional
    public void getAllFQCremasByAreaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Area area = fQCrema.getArea();
        fQCremaRepository.saveAndFlush(fQCrema);
        Long areaId = area.getId();

        // Get all the fQCremaList where area equals to areaId
        defaultFQCremaShouldBeFound("areaId.equals=" + areaId);

        // Get all the fQCremaList where area equals to areaId + 1
        defaultFQCremaShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }


    @Test
    @Transactional
    public void getAllFQCremasByProductoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Producto producto = fQCrema.getProducto();
        fQCremaRepository.saveAndFlush(fQCrema);
        Long productoId = producto.getId();

        // Get all the fQCremaList where producto equals to productoId
        defaultFQCremaShouldBeFound("productoId.equals=" + productoId);

        // Get all the fQCremaList where producto equals to productoId + 1
        defaultFQCremaShouldNotBeFound("productoId.equals=" + (productoId + 1));
    }


    @Test
    @Transactional
    public void getAllFQCremasByAnalistaIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserExtra analista = fQCrema.getAnalista();
        fQCremaRepository.saveAndFlush(fQCrema);
        Long analistaId = analista.getId();

        // Get all the fQCremaList where analista equals to analistaId
        defaultFQCremaShouldBeFound("analistaId.equals=" + analistaId);

        // Get all the fQCremaList where analista equals to analistaId + 1
        defaultFQCremaShouldNotBeFound("analistaId.equals=" + (analistaId + 1));
    }


    @Test
    @Transactional
    public void getAllFQCremasByProveedorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Personal proveedor = fQCrema.getProveedor();
        fQCremaRepository.saveAndFlush(fQCrema);
        Long proveedorId = proveedor.getId();

        // Get all the fQCremaList where proveedor equals to proveedorId
        defaultFQCremaShouldBeFound("proveedorId.equals=" + proveedorId);

        // Get all the fQCremaList where proveedor equals to proveedorId + 1
        defaultFQCremaShouldNotBeFound("proveedorId.equals=" + (proveedorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFQCremaShouldBeFound(String filter) throws Exception {
        restFQCremaMockMvc.perform(get("/api/fq-cremas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fQCrema.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].acidez").value(hasItem(DEFAULT_ACIDEZ.doubleValue())))
            .andExpect(jsonPath("$.[*].grasa").value(hasItem(DEFAULT_GRASA.doubleValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));

        // Check, that the count call also returns 1
        restFQCremaMockMvc.perform(get("/api/fq-cremas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFQCremaShouldNotBeFound(String filter) throws Exception {
        restFQCremaMockMvc.perform(get("/api/fq-cremas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFQCremaMockMvc.perform(get("/api/fq-cremas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFQCrema() throws Exception {
        // Get the fQCrema
        restFQCremaMockMvc.perform(get("/api/fq-cremas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFQCrema() throws Exception {
        // Initialize the database
        fQCremaService.save(fQCrema);

        int databaseSizeBeforeUpdate = fQCremaRepository.findAll().size();

        // Update the fQCrema
        FQCrema updatedFQCrema = fQCremaRepository.findById(fQCrema.getId()).get();
        // Disconnect from session so that the updates on updatedFQCrema are not directly saved in db
        em.detach(updatedFQCrema);
        updatedFQCrema
            .fecha(UPDATED_FECHA)
            .lote(UPDATED_LOTE)
            .acidez(UPDATED_ACIDEZ)
            .grasa(UPDATED_GRASA)
            .observaciones(UPDATED_OBSERVACIONES);

        restFQCremaMockMvc.perform(put("/api/fq-cremas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFQCrema)))
            .andExpect(status().isOk());

        // Validate the FQCrema in the database
        List<FQCrema> fQCremaList = fQCremaRepository.findAll();
        assertThat(fQCremaList).hasSize(databaseSizeBeforeUpdate);
        FQCrema testFQCrema = fQCremaList.get(fQCremaList.size() - 1);
        assertThat(testFQCrema.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFQCrema.getLote()).isEqualTo(UPDATED_LOTE);
        assertThat(testFQCrema.getAcidez()).isEqualTo(UPDATED_ACIDEZ);
        assertThat(testFQCrema.getGrasa()).isEqualTo(UPDATED_GRASA);
        assertThat(testFQCrema.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingFQCrema() throws Exception {
        int databaseSizeBeforeUpdate = fQCremaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFQCremaMockMvc.perform(put("/api/fq-cremas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fQCrema)))
            .andExpect(status().isBadRequest());

        // Validate the FQCrema in the database
        List<FQCrema> fQCremaList = fQCremaRepository.findAll();
        assertThat(fQCremaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFQCrema() throws Exception {
        // Initialize the database
        fQCremaService.save(fQCrema);

        int databaseSizeBeforeDelete = fQCremaRepository.findAll().size();

        // Delete the fQCrema
        restFQCremaMockMvc.perform(delete("/api/fq-cremas/{id}", fQCrema.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FQCrema> fQCremaList = fQCremaRepository.findAll();
        assertThat(fQCremaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
