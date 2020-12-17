package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.FQCrema;
import com.provi.lab.domain.Area;
import com.provi.lab.domain.Producto;
import com.provi.lab.domain.UserExtra;
import com.provi.lab.domain.Personal;
import com.provi.lab.domain.Contenedor;
import com.provi.lab.domain.Proceso;
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

    private static final Double DEFAULT_PH = 1D;
    private static final Double UPDATED_PH = 2D;
    private static final Double SMALLER_PH = 1D - 1D;

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
            .ph(DEFAULT_PH)
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
            .ph(UPDATED_PH)
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
        assertThat(testFQCrema.getPh()).isEqualTo(DEFAULT_PH);
        assertThat(testFQCrema.getDummy1()).isEqualTo(DEFAULT_DUMMY_1);
        assertThat(testFQCrema.getDummy2()).isEqualTo(DEFAULT_DUMMY_2);
        assertThat(testFQCrema.getDummy3()).isEqualTo(DEFAULT_DUMMY_3);
        assertThat(testFQCrema.getDummy4()).isEqualTo(DEFAULT_DUMMY_4);
        assertThat(testFQCrema.getDummy5()).isEqualTo(DEFAULT_DUMMY_5);
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
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy1").value(hasItem(DEFAULT_DUMMY_1.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy2").value(hasItem(DEFAULT_DUMMY_2.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy3").value(hasItem(DEFAULT_DUMMY_3.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy4").value(hasItem(DEFAULT_DUMMY_4.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy5").value(hasItem(DEFAULT_DUMMY_5.doubleValue())))
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
            .andExpect(jsonPath("$.ph").value(DEFAULT_PH.doubleValue()))
            .andExpect(jsonPath("$.dummy1").value(DEFAULT_DUMMY_1.doubleValue()))
            .andExpect(jsonPath("$.dummy2").value(DEFAULT_DUMMY_2.doubleValue()))
            .andExpect(jsonPath("$.dummy3").value(DEFAULT_DUMMY_3.doubleValue()))
            .andExpect(jsonPath("$.dummy4").value(DEFAULT_DUMMY_4.doubleValue()))
            .andExpect(jsonPath("$.dummy5").value(DEFAULT_DUMMY_5.doubleValue()))
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
    public void getAllFQCremasByPhIsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where ph equals to DEFAULT_PH
        defaultFQCremaShouldBeFound("ph.equals=" + DEFAULT_PH);

        // Get all the fQCremaList where ph equals to UPDATED_PH
        defaultFQCremaShouldNotBeFound("ph.equals=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQCremasByPhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where ph not equals to DEFAULT_PH
        defaultFQCremaShouldNotBeFound("ph.notEquals=" + DEFAULT_PH);

        // Get all the fQCremaList where ph not equals to UPDATED_PH
        defaultFQCremaShouldBeFound("ph.notEquals=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQCremasByPhIsInShouldWork() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where ph in DEFAULT_PH or UPDATED_PH
        defaultFQCremaShouldBeFound("ph.in=" + DEFAULT_PH + "," + UPDATED_PH);

        // Get all the fQCremaList where ph equals to UPDATED_PH
        defaultFQCremaShouldNotBeFound("ph.in=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQCremasByPhIsNullOrNotNull() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where ph is not null
        defaultFQCremaShouldBeFound("ph.specified=true");

        // Get all the fQCremaList where ph is null
        defaultFQCremaShouldNotBeFound("ph.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQCremasByPhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where ph is greater than or equal to DEFAULT_PH
        defaultFQCremaShouldBeFound("ph.greaterThanOrEqual=" + DEFAULT_PH);

        // Get all the fQCremaList where ph is greater than or equal to UPDATED_PH
        defaultFQCremaShouldNotBeFound("ph.greaterThanOrEqual=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQCremasByPhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where ph is less than or equal to DEFAULT_PH
        defaultFQCremaShouldBeFound("ph.lessThanOrEqual=" + DEFAULT_PH);

        // Get all the fQCremaList where ph is less than or equal to SMALLER_PH
        defaultFQCremaShouldNotBeFound("ph.lessThanOrEqual=" + SMALLER_PH);
    }

    @Test
    @Transactional
    public void getAllFQCremasByPhIsLessThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where ph is less than DEFAULT_PH
        defaultFQCremaShouldNotBeFound("ph.lessThan=" + DEFAULT_PH);

        // Get all the fQCremaList where ph is less than UPDATED_PH
        defaultFQCremaShouldBeFound("ph.lessThan=" + UPDATED_PH);
    }

    @Test
    @Transactional
    public void getAllFQCremasByPhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where ph is greater than DEFAULT_PH
        defaultFQCremaShouldNotBeFound("ph.greaterThan=" + DEFAULT_PH);

        // Get all the fQCremaList where ph is greater than SMALLER_PH
        defaultFQCremaShouldBeFound("ph.greaterThan=" + SMALLER_PH);
    }


    @Test
    @Transactional
    public void getAllFQCremasByDummy1IsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy1 equals to DEFAULT_DUMMY_1
        defaultFQCremaShouldBeFound("dummy1.equals=" + DEFAULT_DUMMY_1);

        // Get all the fQCremaList where dummy1 equals to UPDATED_DUMMY_1
        defaultFQCremaShouldNotBeFound("dummy1.equals=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy1 not equals to DEFAULT_DUMMY_1
        defaultFQCremaShouldNotBeFound("dummy1.notEquals=" + DEFAULT_DUMMY_1);

        // Get all the fQCremaList where dummy1 not equals to UPDATED_DUMMY_1
        defaultFQCremaShouldBeFound("dummy1.notEquals=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy1IsInShouldWork() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy1 in DEFAULT_DUMMY_1 or UPDATED_DUMMY_1
        defaultFQCremaShouldBeFound("dummy1.in=" + DEFAULT_DUMMY_1 + "," + UPDATED_DUMMY_1);

        // Get all the fQCremaList where dummy1 equals to UPDATED_DUMMY_1
        defaultFQCremaShouldNotBeFound("dummy1.in=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy1IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy1 is not null
        defaultFQCremaShouldBeFound("dummy1.specified=true");

        // Get all the fQCremaList where dummy1 is null
        defaultFQCremaShouldNotBeFound("dummy1.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy1 is greater than or equal to DEFAULT_DUMMY_1
        defaultFQCremaShouldBeFound("dummy1.greaterThanOrEqual=" + DEFAULT_DUMMY_1);

        // Get all the fQCremaList where dummy1 is greater than or equal to UPDATED_DUMMY_1
        defaultFQCremaShouldNotBeFound("dummy1.greaterThanOrEqual=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy1 is less than or equal to DEFAULT_DUMMY_1
        defaultFQCremaShouldBeFound("dummy1.lessThanOrEqual=" + DEFAULT_DUMMY_1);

        // Get all the fQCremaList where dummy1 is less than or equal to SMALLER_DUMMY_1
        defaultFQCremaShouldNotBeFound("dummy1.lessThanOrEqual=" + SMALLER_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy1IsLessThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy1 is less than DEFAULT_DUMMY_1
        defaultFQCremaShouldNotBeFound("dummy1.lessThan=" + DEFAULT_DUMMY_1);

        // Get all the fQCremaList where dummy1 is less than UPDATED_DUMMY_1
        defaultFQCremaShouldBeFound("dummy1.lessThan=" + UPDATED_DUMMY_1);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy1 is greater than DEFAULT_DUMMY_1
        defaultFQCremaShouldNotBeFound("dummy1.greaterThan=" + DEFAULT_DUMMY_1);

        // Get all the fQCremaList where dummy1 is greater than SMALLER_DUMMY_1
        defaultFQCremaShouldBeFound("dummy1.greaterThan=" + SMALLER_DUMMY_1);
    }


    @Test
    @Transactional
    public void getAllFQCremasByDummy2IsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy2 equals to DEFAULT_DUMMY_2
        defaultFQCremaShouldBeFound("dummy2.equals=" + DEFAULT_DUMMY_2);

        // Get all the fQCremaList where dummy2 equals to UPDATED_DUMMY_2
        defaultFQCremaShouldNotBeFound("dummy2.equals=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy2 not equals to DEFAULT_DUMMY_2
        defaultFQCremaShouldNotBeFound("dummy2.notEquals=" + DEFAULT_DUMMY_2);

        // Get all the fQCremaList where dummy2 not equals to UPDATED_DUMMY_2
        defaultFQCremaShouldBeFound("dummy2.notEquals=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy2IsInShouldWork() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy2 in DEFAULT_DUMMY_2 or UPDATED_DUMMY_2
        defaultFQCremaShouldBeFound("dummy2.in=" + DEFAULT_DUMMY_2 + "," + UPDATED_DUMMY_2);

        // Get all the fQCremaList where dummy2 equals to UPDATED_DUMMY_2
        defaultFQCremaShouldNotBeFound("dummy2.in=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy2IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy2 is not null
        defaultFQCremaShouldBeFound("dummy2.specified=true");

        // Get all the fQCremaList where dummy2 is null
        defaultFQCremaShouldNotBeFound("dummy2.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy2 is greater than or equal to DEFAULT_DUMMY_2
        defaultFQCremaShouldBeFound("dummy2.greaterThanOrEqual=" + DEFAULT_DUMMY_2);

        // Get all the fQCremaList where dummy2 is greater than or equal to UPDATED_DUMMY_2
        defaultFQCremaShouldNotBeFound("dummy2.greaterThanOrEqual=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy2 is less than or equal to DEFAULT_DUMMY_2
        defaultFQCremaShouldBeFound("dummy2.lessThanOrEqual=" + DEFAULT_DUMMY_2);

        // Get all the fQCremaList where dummy2 is less than or equal to SMALLER_DUMMY_2
        defaultFQCremaShouldNotBeFound("dummy2.lessThanOrEqual=" + SMALLER_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy2IsLessThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy2 is less than DEFAULT_DUMMY_2
        defaultFQCremaShouldNotBeFound("dummy2.lessThan=" + DEFAULT_DUMMY_2);

        // Get all the fQCremaList where dummy2 is less than UPDATED_DUMMY_2
        defaultFQCremaShouldBeFound("dummy2.lessThan=" + UPDATED_DUMMY_2);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy2 is greater than DEFAULT_DUMMY_2
        defaultFQCremaShouldNotBeFound("dummy2.greaterThan=" + DEFAULT_DUMMY_2);

        // Get all the fQCremaList where dummy2 is greater than SMALLER_DUMMY_2
        defaultFQCremaShouldBeFound("dummy2.greaterThan=" + SMALLER_DUMMY_2);
    }


    @Test
    @Transactional
    public void getAllFQCremasByDummy3IsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy3 equals to DEFAULT_DUMMY_3
        defaultFQCremaShouldBeFound("dummy3.equals=" + DEFAULT_DUMMY_3);

        // Get all the fQCremaList where dummy3 equals to UPDATED_DUMMY_3
        defaultFQCremaShouldNotBeFound("dummy3.equals=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy3 not equals to DEFAULT_DUMMY_3
        defaultFQCremaShouldNotBeFound("dummy3.notEquals=" + DEFAULT_DUMMY_3);

        // Get all the fQCremaList where dummy3 not equals to UPDATED_DUMMY_3
        defaultFQCremaShouldBeFound("dummy3.notEquals=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy3IsInShouldWork() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy3 in DEFAULT_DUMMY_3 or UPDATED_DUMMY_3
        defaultFQCremaShouldBeFound("dummy3.in=" + DEFAULT_DUMMY_3 + "," + UPDATED_DUMMY_3);

        // Get all the fQCremaList where dummy3 equals to UPDATED_DUMMY_3
        defaultFQCremaShouldNotBeFound("dummy3.in=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy3IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy3 is not null
        defaultFQCremaShouldBeFound("dummy3.specified=true");

        // Get all the fQCremaList where dummy3 is null
        defaultFQCremaShouldNotBeFound("dummy3.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy3 is greater than or equal to DEFAULT_DUMMY_3
        defaultFQCremaShouldBeFound("dummy3.greaterThanOrEqual=" + DEFAULT_DUMMY_3);

        // Get all the fQCremaList where dummy3 is greater than or equal to UPDATED_DUMMY_3
        defaultFQCremaShouldNotBeFound("dummy3.greaterThanOrEqual=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy3 is less than or equal to DEFAULT_DUMMY_3
        defaultFQCremaShouldBeFound("dummy3.lessThanOrEqual=" + DEFAULT_DUMMY_3);

        // Get all the fQCremaList where dummy3 is less than or equal to SMALLER_DUMMY_3
        defaultFQCremaShouldNotBeFound("dummy3.lessThanOrEqual=" + SMALLER_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy3IsLessThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy3 is less than DEFAULT_DUMMY_3
        defaultFQCremaShouldNotBeFound("dummy3.lessThan=" + DEFAULT_DUMMY_3);

        // Get all the fQCremaList where dummy3 is less than UPDATED_DUMMY_3
        defaultFQCremaShouldBeFound("dummy3.lessThan=" + UPDATED_DUMMY_3);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy3 is greater than DEFAULT_DUMMY_3
        defaultFQCremaShouldNotBeFound("dummy3.greaterThan=" + DEFAULT_DUMMY_3);

        // Get all the fQCremaList where dummy3 is greater than SMALLER_DUMMY_3
        defaultFQCremaShouldBeFound("dummy3.greaterThan=" + SMALLER_DUMMY_3);
    }


    @Test
    @Transactional
    public void getAllFQCremasByDummy4IsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy4 equals to DEFAULT_DUMMY_4
        defaultFQCremaShouldBeFound("dummy4.equals=" + DEFAULT_DUMMY_4);

        // Get all the fQCremaList where dummy4 equals to UPDATED_DUMMY_4
        defaultFQCremaShouldNotBeFound("dummy4.equals=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy4 not equals to DEFAULT_DUMMY_4
        defaultFQCremaShouldNotBeFound("dummy4.notEquals=" + DEFAULT_DUMMY_4);

        // Get all the fQCremaList where dummy4 not equals to UPDATED_DUMMY_4
        defaultFQCremaShouldBeFound("dummy4.notEquals=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy4IsInShouldWork() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy4 in DEFAULT_DUMMY_4 or UPDATED_DUMMY_4
        defaultFQCremaShouldBeFound("dummy4.in=" + DEFAULT_DUMMY_4 + "," + UPDATED_DUMMY_4);

        // Get all the fQCremaList where dummy4 equals to UPDATED_DUMMY_4
        defaultFQCremaShouldNotBeFound("dummy4.in=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy4IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy4 is not null
        defaultFQCremaShouldBeFound("dummy4.specified=true");

        // Get all the fQCremaList where dummy4 is null
        defaultFQCremaShouldNotBeFound("dummy4.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy4 is greater than or equal to DEFAULT_DUMMY_4
        defaultFQCremaShouldBeFound("dummy4.greaterThanOrEqual=" + DEFAULT_DUMMY_4);

        // Get all the fQCremaList where dummy4 is greater than or equal to UPDATED_DUMMY_4
        defaultFQCremaShouldNotBeFound("dummy4.greaterThanOrEqual=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy4 is less than or equal to DEFAULT_DUMMY_4
        defaultFQCremaShouldBeFound("dummy4.lessThanOrEqual=" + DEFAULT_DUMMY_4);

        // Get all the fQCremaList where dummy4 is less than or equal to SMALLER_DUMMY_4
        defaultFQCremaShouldNotBeFound("dummy4.lessThanOrEqual=" + SMALLER_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy4IsLessThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy4 is less than DEFAULT_DUMMY_4
        defaultFQCremaShouldNotBeFound("dummy4.lessThan=" + DEFAULT_DUMMY_4);

        // Get all the fQCremaList where dummy4 is less than UPDATED_DUMMY_4
        defaultFQCremaShouldBeFound("dummy4.lessThan=" + UPDATED_DUMMY_4);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy4 is greater than DEFAULT_DUMMY_4
        defaultFQCremaShouldNotBeFound("dummy4.greaterThan=" + DEFAULT_DUMMY_4);

        // Get all the fQCremaList where dummy4 is greater than SMALLER_DUMMY_4
        defaultFQCremaShouldBeFound("dummy4.greaterThan=" + SMALLER_DUMMY_4);
    }


    @Test
    @Transactional
    public void getAllFQCremasByDummy5IsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy5 equals to DEFAULT_DUMMY_5
        defaultFQCremaShouldBeFound("dummy5.equals=" + DEFAULT_DUMMY_5);

        // Get all the fQCremaList where dummy5 equals to UPDATED_DUMMY_5
        defaultFQCremaShouldNotBeFound("dummy5.equals=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy5 not equals to DEFAULT_DUMMY_5
        defaultFQCremaShouldNotBeFound("dummy5.notEquals=" + DEFAULT_DUMMY_5);

        // Get all the fQCremaList where dummy5 not equals to UPDATED_DUMMY_5
        defaultFQCremaShouldBeFound("dummy5.notEquals=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy5IsInShouldWork() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy5 in DEFAULT_DUMMY_5 or UPDATED_DUMMY_5
        defaultFQCremaShouldBeFound("dummy5.in=" + DEFAULT_DUMMY_5 + "," + UPDATED_DUMMY_5);

        // Get all the fQCremaList where dummy5 equals to UPDATED_DUMMY_5
        defaultFQCremaShouldNotBeFound("dummy5.in=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy5IsNullOrNotNull() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy5 is not null
        defaultFQCremaShouldBeFound("dummy5.specified=true");

        // Get all the fQCremaList where dummy5 is null
        defaultFQCremaShouldNotBeFound("dummy5.specified=false");
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy5 is greater than or equal to DEFAULT_DUMMY_5
        defaultFQCremaShouldBeFound("dummy5.greaterThanOrEqual=" + DEFAULT_DUMMY_5);

        // Get all the fQCremaList where dummy5 is greater than or equal to UPDATED_DUMMY_5
        defaultFQCremaShouldNotBeFound("dummy5.greaterThanOrEqual=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy5 is less than or equal to DEFAULT_DUMMY_5
        defaultFQCremaShouldBeFound("dummy5.lessThanOrEqual=" + DEFAULT_DUMMY_5);

        // Get all the fQCremaList where dummy5 is less than or equal to SMALLER_DUMMY_5
        defaultFQCremaShouldNotBeFound("dummy5.lessThanOrEqual=" + SMALLER_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy5IsLessThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy5 is less than DEFAULT_DUMMY_5
        defaultFQCremaShouldNotBeFound("dummy5.lessThan=" + DEFAULT_DUMMY_5);

        // Get all the fQCremaList where dummy5 is less than UPDATED_DUMMY_5
        defaultFQCremaShouldBeFound("dummy5.lessThan=" + UPDATED_DUMMY_5);
    }

    @Test
    @Transactional
    public void getAllFQCremasByDummy5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);

        // Get all the fQCremaList where dummy5 is greater than DEFAULT_DUMMY_5
        defaultFQCremaShouldNotBeFound("dummy5.greaterThan=" + DEFAULT_DUMMY_5);

        // Get all the fQCremaList where dummy5 is greater than SMALLER_DUMMY_5
        defaultFQCremaShouldBeFound("dummy5.greaterThan=" + SMALLER_DUMMY_5);
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


    @Test
    @Transactional
    public void getAllFQCremasByContenedorIsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);
        Contenedor contenedor = ContenedorResourceIT.createEntity(em);
        em.persist(contenedor);
        em.flush();
        fQCrema.setContenedor(contenedor);
        fQCremaRepository.saveAndFlush(fQCrema);
        Long contenedorId = contenedor.getId();

        // Get all the fQCremaList where contenedor equals to contenedorId
        defaultFQCremaShouldBeFound("contenedorId.equals=" + contenedorId);

        // Get all the fQCremaList where contenedor equals to contenedorId + 1
        defaultFQCremaShouldNotBeFound("contenedorId.equals=" + (contenedorId + 1));
    }


    @Test
    @Transactional
    public void getAllFQCremasByProcesoIsEqualToSomething() throws Exception {
        // Initialize the database
        fQCremaRepository.saveAndFlush(fQCrema);
        Proceso proceso = ProcesoResourceIT.createEntity(em);
        em.persist(proceso);
        em.flush();
        fQCrema.setProceso(proceso);
        fQCremaRepository.saveAndFlush(fQCrema);
        Long procesoId = proceso.getId();

        // Get all the fQCremaList where proceso equals to procesoId
        defaultFQCremaShouldBeFound("procesoId.equals=" + procesoId);

        // Get all the fQCremaList where proceso equals to procesoId + 1
        defaultFQCremaShouldNotBeFound("procesoId.equals=" + (procesoId + 1));
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
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy1").value(hasItem(DEFAULT_DUMMY_1.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy2").value(hasItem(DEFAULT_DUMMY_2.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy3").value(hasItem(DEFAULT_DUMMY_3.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy4").value(hasItem(DEFAULT_DUMMY_4.doubleValue())))
            .andExpect(jsonPath("$.[*].dummy5").value(hasItem(DEFAULT_DUMMY_5.doubleValue())))
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
            .ph(UPDATED_PH)
            .dummy1(UPDATED_DUMMY_1)
            .dummy2(UPDATED_DUMMY_2)
            .dummy3(UPDATED_DUMMY_3)
            .dummy4(UPDATED_DUMMY_4)
            .dummy5(UPDATED_DUMMY_5)
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
        assertThat(testFQCrema.getPh()).isEqualTo(UPDATED_PH);
        assertThat(testFQCrema.getDummy1()).isEqualTo(UPDATED_DUMMY_1);
        assertThat(testFQCrema.getDummy2()).isEqualTo(UPDATED_DUMMY_2);
        assertThat(testFQCrema.getDummy3()).isEqualTo(UPDATED_DUMMY_3);
        assertThat(testFQCrema.getDummy4()).isEqualTo(UPDATED_DUMMY_4);
        assertThat(testFQCrema.getDummy5()).isEqualTo(UPDATED_DUMMY_5);
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
