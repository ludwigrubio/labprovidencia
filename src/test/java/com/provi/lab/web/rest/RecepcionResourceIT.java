package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.Recepcion;
import com.provi.lab.domain.Personal;
import com.provi.lab.repository.RecepcionRepository;
import com.provi.lab.service.RecepcionService;
import com.provi.lab.service.dto.RecepcionCriteria;
import com.provi.lab.service.RecepcionQueryService;

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
 * Integration tests for the {@link RecepcionResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecepcionResourceIT {

    private static final Integer DEFAULT_ID_PROVEEDOR = 1;
    private static final Integer UPDATED_ID_PROVEEDOR = 2;
    private static final Integer SMALLER_ID_PROVEEDOR = 1 - 1;

    private static final Float DEFAULT_LITROS = 1F;
    private static final Float UPDATED_LITROS = 2F;
    private static final Float SMALLER_LITROS = 1F - 1F;

    private static final Instant DEFAULT_TIEMPO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIEMPO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TURNO = "A";
    private static final String UPDATED_TURNO = "B";

    private static final Double DEFAULT_INCENTIVO_LT = 1D;
    private static final Double UPDATED_INCENTIVO_LT = 2D;
    private static final Double SMALLER_INCENTIVO_LT = 1D - 1D;

    private static final Double DEFAULT_INCENTIVO_T = 1D;
    private static final Double UPDATED_INCENTIVO_T = 2D;
    private static final Double SMALLER_INCENTIVO_T = 1D - 1D;

    @Autowired
    private RecepcionRepository recepcionRepository;

    @Autowired
    private RecepcionService recepcionService;

    @Autowired
    private RecepcionQueryService recepcionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecepcionMockMvc;

    private Recepcion recepcion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recepcion createEntity(EntityManager em) {
        Recepcion recepcion = new Recepcion()
            .idProveedor(DEFAULT_ID_PROVEEDOR)
            .litros(DEFAULT_LITROS)
            .tiempo(DEFAULT_TIEMPO)
            .turno(DEFAULT_TURNO)
            .incentivoLT(DEFAULT_INCENTIVO_LT)
            .incentivoT(DEFAULT_INCENTIVO_T);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        recepcion.setProveedor(personal);
        return recepcion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recepcion createUpdatedEntity(EntityManager em) {
        Recepcion recepcion = new Recepcion()
            .idProveedor(UPDATED_ID_PROVEEDOR)
            .litros(UPDATED_LITROS)
            .tiempo(UPDATED_TIEMPO)
            .turno(UPDATED_TURNO)
            .incentivoLT(UPDATED_INCENTIVO_LT)
            .incentivoT(UPDATED_INCENTIVO_T);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createUpdatedEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        recepcion.setProveedor(personal);
        return recepcion;
    }

    @BeforeEach
    public void initTest() {
        recepcion = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecepcion() throws Exception {
        int databaseSizeBeforeCreate = recepcionRepository.findAll().size();
        // Create the Recepcion
        restRecepcionMockMvc.perform(post("/api/recepcions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recepcion)))
            .andExpect(status().isCreated());

        // Validate the Recepcion in the database
        List<Recepcion> recepcionList = recepcionRepository.findAll();
        assertThat(recepcionList).hasSize(databaseSizeBeforeCreate + 1);
        Recepcion testRecepcion = recepcionList.get(recepcionList.size() - 1);
        assertThat(testRecepcion.getIdProveedor()).isEqualTo(DEFAULT_ID_PROVEEDOR);
        assertThat(testRecepcion.getLitros()).isEqualTo(DEFAULT_LITROS);
        assertThat(testRecepcion.getTiempo()).isEqualTo(DEFAULT_TIEMPO);
        assertThat(testRecepcion.getTurno()).isEqualTo(DEFAULT_TURNO);
        assertThat(testRecepcion.getIncentivoLT()).isEqualTo(DEFAULT_INCENTIVO_LT);
        assertThat(testRecepcion.getIncentivoT()).isEqualTo(DEFAULT_INCENTIVO_T);
    }

    @Test
    @Transactional
    public void createRecepcionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recepcionRepository.findAll().size();

        // Create the Recepcion with an existing ID
        recepcion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecepcionMockMvc.perform(post("/api/recepcions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recepcion)))
            .andExpect(status().isBadRequest());

        // Validate the Recepcion in the database
        List<Recepcion> recepcionList = recepcionRepository.findAll();
        assertThat(recepcionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdProveedorIsRequired() throws Exception {
        int databaseSizeBeforeTest = recepcionRepository.findAll().size();
        // set the field null
        recepcion.setIdProveedor(null);

        // Create the Recepcion, which fails.


        restRecepcionMockMvc.perform(post("/api/recepcions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recepcion)))
            .andExpect(status().isBadRequest());

        List<Recepcion> recepcionList = recepcionRepository.findAll();
        assertThat(recepcionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLitrosIsRequired() throws Exception {
        int databaseSizeBeforeTest = recepcionRepository.findAll().size();
        // set the field null
        recepcion.setLitros(null);

        // Create the Recepcion, which fails.


        restRecepcionMockMvc.perform(post("/api/recepcions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recepcion)))
            .andExpect(status().isBadRequest());

        List<Recepcion> recepcionList = recepcionRepository.findAll();
        assertThat(recepcionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTiempoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recepcionRepository.findAll().size();
        // set the field null
        recepcion.setTiempo(null);

        // Create the Recepcion, which fails.


        restRecepcionMockMvc.perform(post("/api/recepcions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recepcion)))
            .andExpect(status().isBadRequest());

        List<Recepcion> recepcionList = recepcionRepository.findAll();
        assertThat(recepcionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTurnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recepcionRepository.findAll().size();
        // set the field null
        recepcion.setTurno(null);

        // Create the Recepcion, which fails.


        restRecepcionMockMvc.perform(post("/api/recepcions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recepcion)))
            .andExpect(status().isBadRequest());

        List<Recepcion> recepcionList = recepcionRepository.findAll();
        assertThat(recepcionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecepcions() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList
        restRecepcionMockMvc.perform(get("/api/recepcions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recepcion.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProveedor").value(hasItem(DEFAULT_ID_PROVEEDOR)))
            .andExpect(jsonPath("$.[*].litros").value(hasItem(DEFAULT_LITROS.doubleValue())))
            .andExpect(jsonPath("$.[*].tiempo").value(hasItem(DEFAULT_TIEMPO.toString())))
            .andExpect(jsonPath("$.[*].turno").value(hasItem(DEFAULT_TURNO)))
            .andExpect(jsonPath("$.[*].incentivoLT").value(hasItem(DEFAULT_INCENTIVO_LT.doubleValue())))
            .andExpect(jsonPath("$.[*].incentivoT").value(hasItem(DEFAULT_INCENTIVO_T.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getRecepcion() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get the recepcion
        restRecepcionMockMvc.perform(get("/api/recepcions/{id}", recepcion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recepcion.getId().intValue()))
            .andExpect(jsonPath("$.idProveedor").value(DEFAULT_ID_PROVEEDOR))
            .andExpect(jsonPath("$.litros").value(DEFAULT_LITROS.doubleValue()))
            .andExpect(jsonPath("$.tiempo").value(DEFAULT_TIEMPO.toString()))
            .andExpect(jsonPath("$.turno").value(DEFAULT_TURNO))
            .andExpect(jsonPath("$.incentivoLT").value(DEFAULT_INCENTIVO_LT.doubleValue()))
            .andExpect(jsonPath("$.incentivoT").value(DEFAULT_INCENTIVO_T.doubleValue()));
    }


    @Test
    @Transactional
    public void getRecepcionsByIdFiltering() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        Long id = recepcion.getId();

        defaultRecepcionShouldBeFound("id.equals=" + id);
        defaultRecepcionShouldNotBeFound("id.notEquals=" + id);

        defaultRecepcionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRecepcionShouldNotBeFound("id.greaterThan=" + id);

        defaultRecepcionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRecepcionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRecepcionsByIdProveedorIsEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where idProveedor equals to DEFAULT_ID_PROVEEDOR
        defaultRecepcionShouldBeFound("idProveedor.equals=" + DEFAULT_ID_PROVEEDOR);

        // Get all the recepcionList where idProveedor equals to UPDATED_ID_PROVEEDOR
        defaultRecepcionShouldNotBeFound("idProveedor.equals=" + UPDATED_ID_PROVEEDOR);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIdProveedorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where idProveedor not equals to DEFAULT_ID_PROVEEDOR
        defaultRecepcionShouldNotBeFound("idProveedor.notEquals=" + DEFAULT_ID_PROVEEDOR);

        // Get all the recepcionList where idProveedor not equals to UPDATED_ID_PROVEEDOR
        defaultRecepcionShouldBeFound("idProveedor.notEquals=" + UPDATED_ID_PROVEEDOR);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIdProveedorIsInShouldWork() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where idProveedor in DEFAULT_ID_PROVEEDOR or UPDATED_ID_PROVEEDOR
        defaultRecepcionShouldBeFound("idProveedor.in=" + DEFAULT_ID_PROVEEDOR + "," + UPDATED_ID_PROVEEDOR);

        // Get all the recepcionList where idProveedor equals to UPDATED_ID_PROVEEDOR
        defaultRecepcionShouldNotBeFound("idProveedor.in=" + UPDATED_ID_PROVEEDOR);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIdProveedorIsNullOrNotNull() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where idProveedor is not null
        defaultRecepcionShouldBeFound("idProveedor.specified=true");

        // Get all the recepcionList where idProveedor is null
        defaultRecepcionShouldNotBeFound("idProveedor.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIdProveedorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where idProveedor is greater than or equal to DEFAULT_ID_PROVEEDOR
        defaultRecepcionShouldBeFound("idProveedor.greaterThanOrEqual=" + DEFAULT_ID_PROVEEDOR);

        // Get all the recepcionList where idProveedor is greater than or equal to UPDATED_ID_PROVEEDOR
        defaultRecepcionShouldNotBeFound("idProveedor.greaterThanOrEqual=" + UPDATED_ID_PROVEEDOR);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIdProveedorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where idProveedor is less than or equal to DEFAULT_ID_PROVEEDOR
        defaultRecepcionShouldBeFound("idProveedor.lessThanOrEqual=" + DEFAULT_ID_PROVEEDOR);

        // Get all the recepcionList where idProveedor is less than or equal to SMALLER_ID_PROVEEDOR
        defaultRecepcionShouldNotBeFound("idProveedor.lessThanOrEqual=" + SMALLER_ID_PROVEEDOR);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIdProveedorIsLessThanSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where idProveedor is less than DEFAULT_ID_PROVEEDOR
        defaultRecepcionShouldNotBeFound("idProveedor.lessThan=" + DEFAULT_ID_PROVEEDOR);

        // Get all the recepcionList where idProveedor is less than UPDATED_ID_PROVEEDOR
        defaultRecepcionShouldBeFound("idProveedor.lessThan=" + UPDATED_ID_PROVEEDOR);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIdProveedorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where idProveedor is greater than DEFAULT_ID_PROVEEDOR
        defaultRecepcionShouldNotBeFound("idProveedor.greaterThan=" + DEFAULT_ID_PROVEEDOR);

        // Get all the recepcionList where idProveedor is greater than SMALLER_ID_PROVEEDOR
        defaultRecepcionShouldBeFound("idProveedor.greaterThan=" + SMALLER_ID_PROVEEDOR);
    }


    @Test
    @Transactional
    public void getAllRecepcionsByLitrosIsEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where litros equals to DEFAULT_LITROS
        defaultRecepcionShouldBeFound("litros.equals=" + DEFAULT_LITROS);

        // Get all the recepcionList where litros equals to UPDATED_LITROS
        defaultRecepcionShouldNotBeFound("litros.equals=" + UPDATED_LITROS);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByLitrosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where litros not equals to DEFAULT_LITROS
        defaultRecepcionShouldNotBeFound("litros.notEquals=" + DEFAULT_LITROS);

        // Get all the recepcionList where litros not equals to UPDATED_LITROS
        defaultRecepcionShouldBeFound("litros.notEquals=" + UPDATED_LITROS);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByLitrosIsInShouldWork() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where litros in DEFAULT_LITROS or UPDATED_LITROS
        defaultRecepcionShouldBeFound("litros.in=" + DEFAULT_LITROS + "," + UPDATED_LITROS);

        // Get all the recepcionList where litros equals to UPDATED_LITROS
        defaultRecepcionShouldNotBeFound("litros.in=" + UPDATED_LITROS);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByLitrosIsNullOrNotNull() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where litros is not null
        defaultRecepcionShouldBeFound("litros.specified=true");

        // Get all the recepcionList where litros is null
        defaultRecepcionShouldNotBeFound("litros.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecepcionsByLitrosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where litros is greater than or equal to DEFAULT_LITROS
        defaultRecepcionShouldBeFound("litros.greaterThanOrEqual=" + DEFAULT_LITROS);

        // Get all the recepcionList where litros is greater than or equal to UPDATED_LITROS
        defaultRecepcionShouldNotBeFound("litros.greaterThanOrEqual=" + UPDATED_LITROS);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByLitrosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where litros is less than or equal to DEFAULT_LITROS
        defaultRecepcionShouldBeFound("litros.lessThanOrEqual=" + DEFAULT_LITROS);

        // Get all the recepcionList where litros is less than or equal to SMALLER_LITROS
        defaultRecepcionShouldNotBeFound("litros.lessThanOrEqual=" + SMALLER_LITROS);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByLitrosIsLessThanSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where litros is less than DEFAULT_LITROS
        defaultRecepcionShouldNotBeFound("litros.lessThan=" + DEFAULT_LITROS);

        // Get all the recepcionList where litros is less than UPDATED_LITROS
        defaultRecepcionShouldBeFound("litros.lessThan=" + UPDATED_LITROS);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByLitrosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where litros is greater than DEFAULT_LITROS
        defaultRecepcionShouldNotBeFound("litros.greaterThan=" + DEFAULT_LITROS);

        // Get all the recepcionList where litros is greater than SMALLER_LITROS
        defaultRecepcionShouldBeFound("litros.greaterThan=" + SMALLER_LITROS);
    }


    @Test
    @Transactional
    public void getAllRecepcionsByTiempoIsEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where tiempo equals to DEFAULT_TIEMPO
        defaultRecepcionShouldBeFound("tiempo.equals=" + DEFAULT_TIEMPO);

        // Get all the recepcionList where tiempo equals to UPDATED_TIEMPO
        defaultRecepcionShouldNotBeFound("tiempo.equals=" + UPDATED_TIEMPO);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByTiempoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where tiempo not equals to DEFAULT_TIEMPO
        defaultRecepcionShouldNotBeFound("tiempo.notEquals=" + DEFAULT_TIEMPO);

        // Get all the recepcionList where tiempo not equals to UPDATED_TIEMPO
        defaultRecepcionShouldBeFound("tiempo.notEquals=" + UPDATED_TIEMPO);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByTiempoIsInShouldWork() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where tiempo in DEFAULT_TIEMPO or UPDATED_TIEMPO
        defaultRecepcionShouldBeFound("tiempo.in=" + DEFAULT_TIEMPO + "," + UPDATED_TIEMPO);

        // Get all the recepcionList where tiempo equals to UPDATED_TIEMPO
        defaultRecepcionShouldNotBeFound("tiempo.in=" + UPDATED_TIEMPO);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByTiempoIsNullOrNotNull() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where tiempo is not null
        defaultRecepcionShouldBeFound("tiempo.specified=true");

        // Get all the recepcionList where tiempo is null
        defaultRecepcionShouldNotBeFound("tiempo.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecepcionsByTurnoIsEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where turno equals to DEFAULT_TURNO
        defaultRecepcionShouldBeFound("turno.equals=" + DEFAULT_TURNO);

        // Get all the recepcionList where turno equals to UPDATED_TURNO
        defaultRecepcionShouldNotBeFound("turno.equals=" + UPDATED_TURNO);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByTurnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where turno not equals to DEFAULT_TURNO
        defaultRecepcionShouldNotBeFound("turno.notEquals=" + DEFAULT_TURNO);

        // Get all the recepcionList where turno not equals to UPDATED_TURNO
        defaultRecepcionShouldBeFound("turno.notEquals=" + UPDATED_TURNO);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByTurnoIsInShouldWork() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where turno in DEFAULT_TURNO or UPDATED_TURNO
        defaultRecepcionShouldBeFound("turno.in=" + DEFAULT_TURNO + "," + UPDATED_TURNO);

        // Get all the recepcionList where turno equals to UPDATED_TURNO
        defaultRecepcionShouldNotBeFound("turno.in=" + UPDATED_TURNO);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByTurnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where turno is not null
        defaultRecepcionShouldBeFound("turno.specified=true");

        // Get all the recepcionList where turno is null
        defaultRecepcionShouldNotBeFound("turno.specified=false");
    }
                @Test
    @Transactional
    public void getAllRecepcionsByTurnoContainsSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where turno contains DEFAULT_TURNO
        defaultRecepcionShouldBeFound("turno.contains=" + DEFAULT_TURNO);

        // Get all the recepcionList where turno contains UPDATED_TURNO
        defaultRecepcionShouldNotBeFound("turno.contains=" + UPDATED_TURNO);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByTurnoNotContainsSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where turno does not contain DEFAULT_TURNO
        defaultRecepcionShouldNotBeFound("turno.doesNotContain=" + DEFAULT_TURNO);

        // Get all the recepcionList where turno does not contain UPDATED_TURNO
        defaultRecepcionShouldBeFound("turno.doesNotContain=" + UPDATED_TURNO);
    }


    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoLTIsEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoLT equals to DEFAULT_INCENTIVO_LT
        defaultRecepcionShouldBeFound("incentivoLT.equals=" + DEFAULT_INCENTIVO_LT);

        // Get all the recepcionList where incentivoLT equals to UPDATED_INCENTIVO_LT
        defaultRecepcionShouldNotBeFound("incentivoLT.equals=" + UPDATED_INCENTIVO_LT);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoLTIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoLT not equals to DEFAULT_INCENTIVO_LT
        defaultRecepcionShouldNotBeFound("incentivoLT.notEquals=" + DEFAULT_INCENTIVO_LT);

        // Get all the recepcionList where incentivoLT not equals to UPDATED_INCENTIVO_LT
        defaultRecepcionShouldBeFound("incentivoLT.notEquals=" + UPDATED_INCENTIVO_LT);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoLTIsInShouldWork() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoLT in DEFAULT_INCENTIVO_LT or UPDATED_INCENTIVO_LT
        defaultRecepcionShouldBeFound("incentivoLT.in=" + DEFAULT_INCENTIVO_LT + "," + UPDATED_INCENTIVO_LT);

        // Get all the recepcionList where incentivoLT equals to UPDATED_INCENTIVO_LT
        defaultRecepcionShouldNotBeFound("incentivoLT.in=" + UPDATED_INCENTIVO_LT);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoLTIsNullOrNotNull() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoLT is not null
        defaultRecepcionShouldBeFound("incentivoLT.specified=true");

        // Get all the recepcionList where incentivoLT is null
        defaultRecepcionShouldNotBeFound("incentivoLT.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoLTIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoLT is greater than or equal to DEFAULT_INCENTIVO_LT
        defaultRecepcionShouldBeFound("incentivoLT.greaterThanOrEqual=" + DEFAULT_INCENTIVO_LT);

        // Get all the recepcionList where incentivoLT is greater than or equal to UPDATED_INCENTIVO_LT
        defaultRecepcionShouldNotBeFound("incentivoLT.greaterThanOrEqual=" + UPDATED_INCENTIVO_LT);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoLTIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoLT is less than or equal to DEFAULT_INCENTIVO_LT
        defaultRecepcionShouldBeFound("incentivoLT.lessThanOrEqual=" + DEFAULT_INCENTIVO_LT);

        // Get all the recepcionList where incentivoLT is less than or equal to SMALLER_INCENTIVO_LT
        defaultRecepcionShouldNotBeFound("incentivoLT.lessThanOrEqual=" + SMALLER_INCENTIVO_LT);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoLTIsLessThanSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoLT is less than DEFAULT_INCENTIVO_LT
        defaultRecepcionShouldNotBeFound("incentivoLT.lessThan=" + DEFAULT_INCENTIVO_LT);

        // Get all the recepcionList where incentivoLT is less than UPDATED_INCENTIVO_LT
        defaultRecepcionShouldBeFound("incentivoLT.lessThan=" + UPDATED_INCENTIVO_LT);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoLTIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoLT is greater than DEFAULT_INCENTIVO_LT
        defaultRecepcionShouldNotBeFound("incentivoLT.greaterThan=" + DEFAULT_INCENTIVO_LT);

        // Get all the recepcionList where incentivoLT is greater than SMALLER_INCENTIVO_LT
        defaultRecepcionShouldBeFound("incentivoLT.greaterThan=" + SMALLER_INCENTIVO_LT);
    }


    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoTIsEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoT equals to DEFAULT_INCENTIVO_T
        defaultRecepcionShouldBeFound("incentivoT.equals=" + DEFAULT_INCENTIVO_T);

        // Get all the recepcionList where incentivoT equals to UPDATED_INCENTIVO_T
        defaultRecepcionShouldNotBeFound("incentivoT.equals=" + UPDATED_INCENTIVO_T);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoTIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoT not equals to DEFAULT_INCENTIVO_T
        defaultRecepcionShouldNotBeFound("incentivoT.notEquals=" + DEFAULT_INCENTIVO_T);

        // Get all the recepcionList where incentivoT not equals to UPDATED_INCENTIVO_T
        defaultRecepcionShouldBeFound("incentivoT.notEquals=" + UPDATED_INCENTIVO_T);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoTIsInShouldWork() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoT in DEFAULT_INCENTIVO_T or UPDATED_INCENTIVO_T
        defaultRecepcionShouldBeFound("incentivoT.in=" + DEFAULT_INCENTIVO_T + "," + UPDATED_INCENTIVO_T);

        // Get all the recepcionList where incentivoT equals to UPDATED_INCENTIVO_T
        defaultRecepcionShouldNotBeFound("incentivoT.in=" + UPDATED_INCENTIVO_T);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoTIsNullOrNotNull() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoT is not null
        defaultRecepcionShouldBeFound("incentivoT.specified=true");

        // Get all the recepcionList where incentivoT is null
        defaultRecepcionShouldNotBeFound("incentivoT.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoTIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoT is greater than or equal to DEFAULT_INCENTIVO_T
        defaultRecepcionShouldBeFound("incentivoT.greaterThanOrEqual=" + DEFAULT_INCENTIVO_T);

        // Get all the recepcionList where incentivoT is greater than or equal to UPDATED_INCENTIVO_T
        defaultRecepcionShouldNotBeFound("incentivoT.greaterThanOrEqual=" + UPDATED_INCENTIVO_T);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoTIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoT is less than or equal to DEFAULT_INCENTIVO_T
        defaultRecepcionShouldBeFound("incentivoT.lessThanOrEqual=" + DEFAULT_INCENTIVO_T);

        // Get all the recepcionList where incentivoT is less than or equal to SMALLER_INCENTIVO_T
        defaultRecepcionShouldNotBeFound("incentivoT.lessThanOrEqual=" + SMALLER_INCENTIVO_T);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoTIsLessThanSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoT is less than DEFAULT_INCENTIVO_T
        defaultRecepcionShouldNotBeFound("incentivoT.lessThan=" + DEFAULT_INCENTIVO_T);

        // Get all the recepcionList where incentivoT is less than UPDATED_INCENTIVO_T
        defaultRecepcionShouldBeFound("incentivoT.lessThan=" + UPDATED_INCENTIVO_T);
    }

    @Test
    @Transactional
    public void getAllRecepcionsByIncentivoTIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recepcionRepository.saveAndFlush(recepcion);

        // Get all the recepcionList where incentivoT is greater than DEFAULT_INCENTIVO_T
        defaultRecepcionShouldNotBeFound("incentivoT.greaterThan=" + DEFAULT_INCENTIVO_T);

        // Get all the recepcionList where incentivoT is greater than SMALLER_INCENTIVO_T
        defaultRecepcionShouldBeFound("incentivoT.greaterThan=" + SMALLER_INCENTIVO_T);
    }


    @Test
    @Transactional
    public void getAllRecepcionsByProveedorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Personal proveedor = recepcion.getProveedor();
        recepcionRepository.saveAndFlush(recepcion);
        Long proveedorId = proveedor.getId();

        // Get all the recepcionList where proveedor equals to proveedorId
        defaultRecepcionShouldBeFound("proveedorId.equals=" + proveedorId);

        // Get all the recepcionList where proveedor equals to proveedorId + 1
        defaultRecepcionShouldNotBeFound("proveedorId.equals=" + (proveedorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecepcionShouldBeFound(String filter) throws Exception {
        restRecepcionMockMvc.perform(get("/api/recepcions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recepcion.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProveedor").value(hasItem(DEFAULT_ID_PROVEEDOR)))
            .andExpect(jsonPath("$.[*].litros").value(hasItem(DEFAULT_LITROS.doubleValue())))
            .andExpect(jsonPath("$.[*].tiempo").value(hasItem(DEFAULT_TIEMPO.toString())))
            .andExpect(jsonPath("$.[*].turno").value(hasItem(DEFAULT_TURNO)))
            .andExpect(jsonPath("$.[*].incentivoLT").value(hasItem(DEFAULT_INCENTIVO_LT.doubleValue())))
            .andExpect(jsonPath("$.[*].incentivoT").value(hasItem(DEFAULT_INCENTIVO_T.doubleValue())));

        // Check, that the count call also returns 1
        restRecepcionMockMvc.perform(get("/api/recepcions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRecepcionShouldNotBeFound(String filter) throws Exception {
        restRecepcionMockMvc.perform(get("/api/recepcions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRecepcionMockMvc.perform(get("/api/recepcions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRecepcion() throws Exception {
        // Get the recepcion
        restRecepcionMockMvc.perform(get("/api/recepcions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecepcion() throws Exception {
        // Initialize the database
        recepcionService.save(recepcion);

        int databaseSizeBeforeUpdate = recepcionRepository.findAll().size();

        // Update the recepcion
        Recepcion updatedRecepcion = recepcionRepository.findById(recepcion.getId()).get();
        // Disconnect from session so that the updates on updatedRecepcion are not directly saved in db
        em.detach(updatedRecepcion);
        updatedRecepcion
            .idProveedor(UPDATED_ID_PROVEEDOR)
            .litros(UPDATED_LITROS)
            .tiempo(UPDATED_TIEMPO)
            .turno(UPDATED_TURNO)
            .incentivoLT(UPDATED_INCENTIVO_LT)
            .incentivoT(UPDATED_INCENTIVO_T);

        restRecepcionMockMvc.perform(put("/api/recepcions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecepcion)))
            .andExpect(status().isOk());

        // Validate the Recepcion in the database
        List<Recepcion> recepcionList = recepcionRepository.findAll();
        assertThat(recepcionList).hasSize(databaseSizeBeforeUpdate);
        Recepcion testRecepcion = recepcionList.get(recepcionList.size() - 1);
        assertThat(testRecepcion.getIdProveedor()).isEqualTo(UPDATED_ID_PROVEEDOR);
        assertThat(testRecepcion.getLitros()).isEqualTo(UPDATED_LITROS);
        assertThat(testRecepcion.getTiempo()).isEqualTo(UPDATED_TIEMPO);
        assertThat(testRecepcion.getTurno()).isEqualTo(UPDATED_TURNO);
        assertThat(testRecepcion.getIncentivoLT()).isEqualTo(UPDATED_INCENTIVO_LT);
        assertThat(testRecepcion.getIncentivoT()).isEqualTo(UPDATED_INCENTIVO_T);
    }

    @Test
    @Transactional
    public void updateNonExistingRecepcion() throws Exception {
        int databaseSizeBeforeUpdate = recepcionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecepcionMockMvc.perform(put("/api/recepcions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recepcion)))
            .andExpect(status().isBadRequest());

        // Validate the Recepcion in the database
        List<Recepcion> recepcionList = recepcionRepository.findAll();
        assertThat(recepcionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecepcion() throws Exception {
        // Initialize the database
        recepcionService.save(recepcion);

        int databaseSizeBeforeDelete = recepcionRepository.findAll().size();

        // Delete the recepcion
        restRecepcionMockMvc.perform(delete("/api/recepcions/{id}", recepcion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recepcion> recepcionList = recepcionRepository.findAll();
        assertThat(recepcionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
