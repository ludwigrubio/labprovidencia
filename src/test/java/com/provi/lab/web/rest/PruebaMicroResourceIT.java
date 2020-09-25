package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.PruebaMicro;
import com.provi.lab.domain.Area;
import com.provi.lab.domain.Cultivo;
import com.provi.lab.domain.Superficie;
import com.provi.lab.domain.UserExtra;
import com.provi.lab.domain.Personal;
import com.provi.lab.repository.PruebaMicroRepository;
import com.provi.lab.service.PruebaMicroService;
import com.provi.lab.service.dto.PruebaMicroCriteria;
import com.provi.lab.service.PruebaMicroQueryService;

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
 * Integration tests for the {@link PruebaMicroResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PruebaMicroResourceIT {

    private static final Integer DEFAULT_TIPODE_MUESTRA = 1;
    private static final Integer UPDATED_TIPODE_MUESTRA = 2;
    private static final Integer SMALLER_TIPODE_MUESTRA = 1 - 1;

    private static final String DEFAULT_ID_CATALOGO = "AAAAAAAAAA";
    private static final String UPDATED_ID_CATALOGO = "BBBBBBBBBB";

    private static final String DEFAULT_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_LOTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_RESULTADO = 1;
    private static final Integer UPDATED_RESULTADO = 2;
    private static final Integer SMALLER_RESULTADO = 1 - 1;

    private static final Integer DEFAULT_UNIDADES = 1;
    private static final Integer UPDATED_UNIDADES = 2;
    private static final Integer SMALLER_UNIDADES = 1 - 1;

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    @Autowired
    private PruebaMicroRepository pruebaMicroRepository;

    @Autowired
    private PruebaMicroService pruebaMicroService;

    @Autowired
    private PruebaMicroQueryService pruebaMicroQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPruebaMicroMockMvc;

    private PruebaMicro pruebaMicro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PruebaMicro createEntity(EntityManager em) {
        PruebaMicro pruebaMicro = new PruebaMicro()
            .tipodeMuestra(DEFAULT_TIPODE_MUESTRA)
            .idCatalogo(DEFAULT_ID_CATALOGO)
            .lote(DEFAULT_LOTE)
            .inicio(DEFAULT_INICIO)
            .fin(DEFAULT_FIN)
            .resultado(DEFAULT_RESULTADO)
            .unidades(DEFAULT_UNIDADES)
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
        pruebaMicro.setArea(area);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        pruebaMicro.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        pruebaMicro.setProveedor(personal);
        return pruebaMicro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PruebaMicro createUpdatedEntity(EntityManager em) {
        PruebaMicro pruebaMicro = new PruebaMicro()
            .tipodeMuestra(UPDATED_TIPODE_MUESTRA)
            .idCatalogo(UPDATED_ID_CATALOGO)
            .lote(UPDATED_LOTE)
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN)
            .resultado(UPDATED_RESULTADO)
            .unidades(UPDATED_UNIDADES)
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
        pruebaMicro.setArea(area);
        // Add required entity
        UserExtra userExtra;
        if (TestUtil.findAll(em, UserExtra.class).isEmpty()) {
            userExtra = UserExtraResourceIT.createUpdatedEntity(em);
            em.persist(userExtra);
            em.flush();
        } else {
            userExtra = TestUtil.findAll(em, UserExtra.class).get(0);
        }
        pruebaMicro.setAnalista(userExtra);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createUpdatedEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        pruebaMicro.setProveedor(personal);
        return pruebaMicro;
    }

    @BeforeEach
    public void initTest() {
        pruebaMicro = createEntity(em);
    }

    @Test
    @Transactional
    public void createPruebaMicro() throws Exception {
        int databaseSizeBeforeCreate = pruebaMicroRepository.findAll().size();
        // Create the PruebaMicro
        restPruebaMicroMockMvc.perform(post("/api/prueba-micros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pruebaMicro)))
            .andExpect(status().isCreated());

        // Validate the PruebaMicro in the database
        List<PruebaMicro> pruebaMicroList = pruebaMicroRepository.findAll();
        assertThat(pruebaMicroList).hasSize(databaseSizeBeforeCreate + 1);
        PruebaMicro testPruebaMicro = pruebaMicroList.get(pruebaMicroList.size() - 1);
        assertThat(testPruebaMicro.getTipodeMuestra()).isEqualTo(DEFAULT_TIPODE_MUESTRA);
        assertThat(testPruebaMicro.getIdCatalogo()).isEqualTo(DEFAULT_ID_CATALOGO);
        assertThat(testPruebaMicro.getLote()).isEqualTo(DEFAULT_LOTE);
        assertThat(testPruebaMicro.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testPruebaMicro.getFin()).isEqualTo(DEFAULT_FIN);
        assertThat(testPruebaMicro.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testPruebaMicro.getUnidades()).isEqualTo(DEFAULT_UNIDADES);
        assertThat(testPruebaMicro.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void createPruebaMicroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pruebaMicroRepository.findAll().size();

        // Create the PruebaMicro with an existing ID
        pruebaMicro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPruebaMicroMockMvc.perform(post("/api/prueba-micros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pruebaMicro)))
            .andExpect(status().isBadRequest());

        // Validate the PruebaMicro in the database
        List<PruebaMicro> pruebaMicroList = pruebaMicroRepository.findAll();
        assertThat(pruebaMicroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipodeMuestraIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaMicroRepository.findAll().size();
        // set the field null
        pruebaMicro.setTipodeMuestra(null);

        // Create the PruebaMicro, which fails.


        restPruebaMicroMockMvc.perform(post("/api/prueba-micros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pruebaMicro)))
            .andExpect(status().isBadRequest());

        List<PruebaMicro> pruebaMicroList = pruebaMicroRepository.findAll();
        assertThat(pruebaMicroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdCatalogoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaMicroRepository.findAll().size();
        // set the field null
        pruebaMicro.setIdCatalogo(null);

        // Create the PruebaMicro, which fails.


        restPruebaMicroMockMvc.perform(post("/api/prueba-micros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pruebaMicro)))
            .andExpect(status().isBadRequest());

        List<PruebaMicro> pruebaMicroList = pruebaMicroRepository.findAll();
        assertThat(pruebaMicroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaMicroRepository.findAll().size();
        // set the field null
        pruebaMicro.setInicio(null);

        // Create the PruebaMicro, which fails.


        restPruebaMicroMockMvc.perform(post("/api/prueba-micros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pruebaMicro)))
            .andExpect(status().isBadRequest());

        List<PruebaMicro> pruebaMicroList = pruebaMicroRepository.findAll();
        assertThat(pruebaMicroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPruebaMicros() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList
        restPruebaMicroMockMvc.perform(get("/api/prueba-micros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pruebaMicro.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipodeMuestra").value(hasItem(DEFAULT_TIPODE_MUESTRA)))
            .andExpect(jsonPath("$.[*].idCatalogo").value(hasItem(DEFAULT_ID_CATALOGO)))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO)))
            .andExpect(jsonPath("$.[*].unidades").value(hasItem(DEFAULT_UNIDADES)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }
    
    @Test
    @Transactional
    public void getPruebaMicro() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get the pruebaMicro
        restPruebaMicroMockMvc.perform(get("/api/prueba-micros/{id}", pruebaMicro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pruebaMicro.getId().intValue()))
            .andExpect(jsonPath("$.tipodeMuestra").value(DEFAULT_TIPODE_MUESTRA))
            .andExpect(jsonPath("$.idCatalogo").value(DEFAULT_ID_CATALOGO))
            .andExpect(jsonPath("$.lote").value(DEFAULT_LOTE))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO))
            .andExpect(jsonPath("$.unidades").value(DEFAULT_UNIDADES))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }


    @Test
    @Transactional
    public void getPruebaMicrosByIdFiltering() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        Long id = pruebaMicro.getId();

        defaultPruebaMicroShouldBeFound("id.equals=" + id);
        defaultPruebaMicroShouldNotBeFound("id.notEquals=" + id);

        defaultPruebaMicroShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPruebaMicroShouldNotBeFound("id.greaterThan=" + id);

        defaultPruebaMicroShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPruebaMicroShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPruebaMicrosByTipodeMuestraIsEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where tipodeMuestra equals to DEFAULT_TIPODE_MUESTRA
        defaultPruebaMicroShouldBeFound("tipodeMuestra.equals=" + DEFAULT_TIPODE_MUESTRA);

        // Get all the pruebaMicroList where tipodeMuestra equals to UPDATED_TIPODE_MUESTRA
        defaultPruebaMicroShouldNotBeFound("tipodeMuestra.equals=" + UPDATED_TIPODE_MUESTRA);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByTipodeMuestraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where tipodeMuestra not equals to DEFAULT_TIPODE_MUESTRA
        defaultPruebaMicroShouldNotBeFound("tipodeMuestra.notEquals=" + DEFAULT_TIPODE_MUESTRA);

        // Get all the pruebaMicroList where tipodeMuestra not equals to UPDATED_TIPODE_MUESTRA
        defaultPruebaMicroShouldBeFound("tipodeMuestra.notEquals=" + UPDATED_TIPODE_MUESTRA);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByTipodeMuestraIsInShouldWork() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where tipodeMuestra in DEFAULT_TIPODE_MUESTRA or UPDATED_TIPODE_MUESTRA
        defaultPruebaMicroShouldBeFound("tipodeMuestra.in=" + DEFAULT_TIPODE_MUESTRA + "," + UPDATED_TIPODE_MUESTRA);

        // Get all the pruebaMicroList where tipodeMuestra equals to UPDATED_TIPODE_MUESTRA
        defaultPruebaMicroShouldNotBeFound("tipodeMuestra.in=" + UPDATED_TIPODE_MUESTRA);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByTipodeMuestraIsNullOrNotNull() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where tipodeMuestra is not null
        defaultPruebaMicroShouldBeFound("tipodeMuestra.specified=true");

        // Get all the pruebaMicroList where tipodeMuestra is null
        defaultPruebaMicroShouldNotBeFound("tipodeMuestra.specified=false");
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByTipodeMuestraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where tipodeMuestra is greater than or equal to DEFAULT_TIPODE_MUESTRA
        defaultPruebaMicroShouldBeFound("tipodeMuestra.greaterThanOrEqual=" + DEFAULT_TIPODE_MUESTRA);

        // Get all the pruebaMicroList where tipodeMuestra is greater than or equal to UPDATED_TIPODE_MUESTRA
        defaultPruebaMicroShouldNotBeFound("tipodeMuestra.greaterThanOrEqual=" + UPDATED_TIPODE_MUESTRA);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByTipodeMuestraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where tipodeMuestra is less than or equal to DEFAULT_TIPODE_MUESTRA
        defaultPruebaMicroShouldBeFound("tipodeMuestra.lessThanOrEqual=" + DEFAULT_TIPODE_MUESTRA);

        // Get all the pruebaMicroList where tipodeMuestra is less than or equal to SMALLER_TIPODE_MUESTRA
        defaultPruebaMicroShouldNotBeFound("tipodeMuestra.lessThanOrEqual=" + SMALLER_TIPODE_MUESTRA);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByTipodeMuestraIsLessThanSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where tipodeMuestra is less than DEFAULT_TIPODE_MUESTRA
        defaultPruebaMicroShouldNotBeFound("tipodeMuestra.lessThan=" + DEFAULT_TIPODE_MUESTRA);

        // Get all the pruebaMicroList where tipodeMuestra is less than UPDATED_TIPODE_MUESTRA
        defaultPruebaMicroShouldBeFound("tipodeMuestra.lessThan=" + UPDATED_TIPODE_MUESTRA);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByTipodeMuestraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where tipodeMuestra is greater than DEFAULT_TIPODE_MUESTRA
        defaultPruebaMicroShouldNotBeFound("tipodeMuestra.greaterThan=" + DEFAULT_TIPODE_MUESTRA);

        // Get all the pruebaMicroList where tipodeMuestra is greater than SMALLER_TIPODE_MUESTRA
        defaultPruebaMicroShouldBeFound("tipodeMuestra.greaterThan=" + SMALLER_TIPODE_MUESTRA);
    }


    @Test
    @Transactional
    public void getAllPruebaMicrosByIdCatalogoIsEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where idCatalogo equals to DEFAULT_ID_CATALOGO
        defaultPruebaMicroShouldBeFound("idCatalogo.equals=" + DEFAULT_ID_CATALOGO);

        // Get all the pruebaMicroList where idCatalogo equals to UPDATED_ID_CATALOGO
        defaultPruebaMicroShouldNotBeFound("idCatalogo.equals=" + UPDATED_ID_CATALOGO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByIdCatalogoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where idCatalogo not equals to DEFAULT_ID_CATALOGO
        defaultPruebaMicroShouldNotBeFound("idCatalogo.notEquals=" + DEFAULT_ID_CATALOGO);

        // Get all the pruebaMicroList where idCatalogo not equals to UPDATED_ID_CATALOGO
        defaultPruebaMicroShouldBeFound("idCatalogo.notEquals=" + UPDATED_ID_CATALOGO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByIdCatalogoIsInShouldWork() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where idCatalogo in DEFAULT_ID_CATALOGO or UPDATED_ID_CATALOGO
        defaultPruebaMicroShouldBeFound("idCatalogo.in=" + DEFAULT_ID_CATALOGO + "," + UPDATED_ID_CATALOGO);

        // Get all the pruebaMicroList where idCatalogo equals to UPDATED_ID_CATALOGO
        defaultPruebaMicroShouldNotBeFound("idCatalogo.in=" + UPDATED_ID_CATALOGO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByIdCatalogoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where idCatalogo is not null
        defaultPruebaMicroShouldBeFound("idCatalogo.specified=true");

        // Get all the pruebaMicroList where idCatalogo is null
        defaultPruebaMicroShouldNotBeFound("idCatalogo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPruebaMicrosByIdCatalogoContainsSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where idCatalogo contains DEFAULT_ID_CATALOGO
        defaultPruebaMicroShouldBeFound("idCatalogo.contains=" + DEFAULT_ID_CATALOGO);

        // Get all the pruebaMicroList where idCatalogo contains UPDATED_ID_CATALOGO
        defaultPruebaMicroShouldNotBeFound("idCatalogo.contains=" + UPDATED_ID_CATALOGO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByIdCatalogoNotContainsSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where idCatalogo does not contain DEFAULT_ID_CATALOGO
        defaultPruebaMicroShouldNotBeFound("idCatalogo.doesNotContain=" + DEFAULT_ID_CATALOGO);

        // Get all the pruebaMicroList where idCatalogo does not contain UPDATED_ID_CATALOGO
        defaultPruebaMicroShouldBeFound("idCatalogo.doesNotContain=" + UPDATED_ID_CATALOGO);
    }


    @Test
    @Transactional
    public void getAllPruebaMicrosByLoteIsEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where lote equals to DEFAULT_LOTE
        defaultPruebaMicroShouldBeFound("lote.equals=" + DEFAULT_LOTE);

        // Get all the pruebaMicroList where lote equals to UPDATED_LOTE
        defaultPruebaMicroShouldNotBeFound("lote.equals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByLoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where lote not equals to DEFAULT_LOTE
        defaultPruebaMicroShouldNotBeFound("lote.notEquals=" + DEFAULT_LOTE);

        // Get all the pruebaMicroList where lote not equals to UPDATED_LOTE
        defaultPruebaMicroShouldBeFound("lote.notEquals=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByLoteIsInShouldWork() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where lote in DEFAULT_LOTE or UPDATED_LOTE
        defaultPruebaMicroShouldBeFound("lote.in=" + DEFAULT_LOTE + "," + UPDATED_LOTE);

        // Get all the pruebaMicroList where lote equals to UPDATED_LOTE
        defaultPruebaMicroShouldNotBeFound("lote.in=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByLoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where lote is not null
        defaultPruebaMicroShouldBeFound("lote.specified=true");

        // Get all the pruebaMicroList where lote is null
        defaultPruebaMicroShouldNotBeFound("lote.specified=false");
    }
                @Test
    @Transactional
    public void getAllPruebaMicrosByLoteContainsSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where lote contains DEFAULT_LOTE
        defaultPruebaMicroShouldBeFound("lote.contains=" + DEFAULT_LOTE);

        // Get all the pruebaMicroList where lote contains UPDATED_LOTE
        defaultPruebaMicroShouldNotBeFound("lote.contains=" + UPDATED_LOTE);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByLoteNotContainsSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where lote does not contain DEFAULT_LOTE
        defaultPruebaMicroShouldNotBeFound("lote.doesNotContain=" + DEFAULT_LOTE);

        // Get all the pruebaMicroList where lote does not contain UPDATED_LOTE
        defaultPruebaMicroShouldBeFound("lote.doesNotContain=" + UPDATED_LOTE);
    }


    @Test
    @Transactional
    public void getAllPruebaMicrosByInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where inicio equals to DEFAULT_INICIO
        defaultPruebaMicroShouldBeFound("inicio.equals=" + DEFAULT_INICIO);

        // Get all the pruebaMicroList where inicio equals to UPDATED_INICIO
        defaultPruebaMicroShouldNotBeFound("inicio.equals=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByInicioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where inicio not equals to DEFAULT_INICIO
        defaultPruebaMicroShouldNotBeFound("inicio.notEquals=" + DEFAULT_INICIO);

        // Get all the pruebaMicroList where inicio not equals to UPDATED_INICIO
        defaultPruebaMicroShouldBeFound("inicio.notEquals=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByInicioIsInShouldWork() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where inicio in DEFAULT_INICIO or UPDATED_INICIO
        defaultPruebaMicroShouldBeFound("inicio.in=" + DEFAULT_INICIO + "," + UPDATED_INICIO);

        // Get all the pruebaMicroList where inicio equals to UPDATED_INICIO
        defaultPruebaMicroShouldNotBeFound("inicio.in=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where inicio is not null
        defaultPruebaMicroShouldBeFound("inicio.specified=true");

        // Get all the pruebaMicroList where inicio is null
        defaultPruebaMicroShouldNotBeFound("inicio.specified=false");
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByFinIsEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where fin equals to DEFAULT_FIN
        defaultPruebaMicroShouldBeFound("fin.equals=" + DEFAULT_FIN);

        // Get all the pruebaMicroList where fin equals to UPDATED_FIN
        defaultPruebaMicroShouldNotBeFound("fin.equals=" + UPDATED_FIN);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByFinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where fin not equals to DEFAULT_FIN
        defaultPruebaMicroShouldNotBeFound("fin.notEquals=" + DEFAULT_FIN);

        // Get all the pruebaMicroList where fin not equals to UPDATED_FIN
        defaultPruebaMicroShouldBeFound("fin.notEquals=" + UPDATED_FIN);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByFinIsInShouldWork() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where fin in DEFAULT_FIN or UPDATED_FIN
        defaultPruebaMicroShouldBeFound("fin.in=" + DEFAULT_FIN + "," + UPDATED_FIN);

        // Get all the pruebaMicroList where fin equals to UPDATED_FIN
        defaultPruebaMicroShouldNotBeFound("fin.in=" + UPDATED_FIN);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where fin is not null
        defaultPruebaMicroShouldBeFound("fin.specified=true");

        // Get all the pruebaMicroList where fin is null
        defaultPruebaMicroShouldNotBeFound("fin.specified=false");
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByResultadoIsEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where resultado equals to DEFAULT_RESULTADO
        defaultPruebaMicroShouldBeFound("resultado.equals=" + DEFAULT_RESULTADO);

        // Get all the pruebaMicroList where resultado equals to UPDATED_RESULTADO
        defaultPruebaMicroShouldNotBeFound("resultado.equals=" + UPDATED_RESULTADO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByResultadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where resultado not equals to DEFAULT_RESULTADO
        defaultPruebaMicroShouldNotBeFound("resultado.notEquals=" + DEFAULT_RESULTADO);

        // Get all the pruebaMicroList where resultado not equals to UPDATED_RESULTADO
        defaultPruebaMicroShouldBeFound("resultado.notEquals=" + UPDATED_RESULTADO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByResultadoIsInShouldWork() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where resultado in DEFAULT_RESULTADO or UPDATED_RESULTADO
        defaultPruebaMicroShouldBeFound("resultado.in=" + DEFAULT_RESULTADO + "," + UPDATED_RESULTADO);

        // Get all the pruebaMicroList where resultado equals to UPDATED_RESULTADO
        defaultPruebaMicroShouldNotBeFound("resultado.in=" + UPDATED_RESULTADO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByResultadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where resultado is not null
        defaultPruebaMicroShouldBeFound("resultado.specified=true");

        // Get all the pruebaMicroList where resultado is null
        defaultPruebaMicroShouldNotBeFound("resultado.specified=false");
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByResultadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where resultado is greater than or equal to DEFAULT_RESULTADO
        defaultPruebaMicroShouldBeFound("resultado.greaterThanOrEqual=" + DEFAULT_RESULTADO);

        // Get all the pruebaMicroList where resultado is greater than or equal to UPDATED_RESULTADO
        defaultPruebaMicroShouldNotBeFound("resultado.greaterThanOrEqual=" + UPDATED_RESULTADO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByResultadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where resultado is less than or equal to DEFAULT_RESULTADO
        defaultPruebaMicroShouldBeFound("resultado.lessThanOrEqual=" + DEFAULT_RESULTADO);

        // Get all the pruebaMicroList where resultado is less than or equal to SMALLER_RESULTADO
        defaultPruebaMicroShouldNotBeFound("resultado.lessThanOrEqual=" + SMALLER_RESULTADO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByResultadoIsLessThanSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where resultado is less than DEFAULT_RESULTADO
        defaultPruebaMicroShouldNotBeFound("resultado.lessThan=" + DEFAULT_RESULTADO);

        // Get all the pruebaMicroList where resultado is less than UPDATED_RESULTADO
        defaultPruebaMicroShouldBeFound("resultado.lessThan=" + UPDATED_RESULTADO);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByResultadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where resultado is greater than DEFAULT_RESULTADO
        defaultPruebaMicroShouldNotBeFound("resultado.greaterThan=" + DEFAULT_RESULTADO);

        // Get all the pruebaMicroList where resultado is greater than SMALLER_RESULTADO
        defaultPruebaMicroShouldBeFound("resultado.greaterThan=" + SMALLER_RESULTADO);
    }


    @Test
    @Transactional
    public void getAllPruebaMicrosByUnidadesIsEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where unidades equals to DEFAULT_UNIDADES
        defaultPruebaMicroShouldBeFound("unidades.equals=" + DEFAULT_UNIDADES);

        // Get all the pruebaMicroList where unidades equals to UPDATED_UNIDADES
        defaultPruebaMicroShouldNotBeFound("unidades.equals=" + UPDATED_UNIDADES);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByUnidadesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where unidades not equals to DEFAULT_UNIDADES
        defaultPruebaMicroShouldNotBeFound("unidades.notEquals=" + DEFAULT_UNIDADES);

        // Get all the pruebaMicroList where unidades not equals to UPDATED_UNIDADES
        defaultPruebaMicroShouldBeFound("unidades.notEquals=" + UPDATED_UNIDADES);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByUnidadesIsInShouldWork() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where unidades in DEFAULT_UNIDADES or UPDATED_UNIDADES
        defaultPruebaMicroShouldBeFound("unidades.in=" + DEFAULT_UNIDADES + "," + UPDATED_UNIDADES);

        // Get all the pruebaMicroList where unidades equals to UPDATED_UNIDADES
        defaultPruebaMicroShouldNotBeFound("unidades.in=" + UPDATED_UNIDADES);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByUnidadesIsNullOrNotNull() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where unidades is not null
        defaultPruebaMicroShouldBeFound("unidades.specified=true");

        // Get all the pruebaMicroList where unidades is null
        defaultPruebaMicroShouldNotBeFound("unidades.specified=false");
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByUnidadesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where unidades is greater than or equal to DEFAULT_UNIDADES
        defaultPruebaMicroShouldBeFound("unidades.greaterThanOrEqual=" + DEFAULT_UNIDADES);

        // Get all the pruebaMicroList where unidades is greater than or equal to UPDATED_UNIDADES
        defaultPruebaMicroShouldNotBeFound("unidades.greaterThanOrEqual=" + UPDATED_UNIDADES);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByUnidadesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where unidades is less than or equal to DEFAULT_UNIDADES
        defaultPruebaMicroShouldBeFound("unidades.lessThanOrEqual=" + DEFAULT_UNIDADES);

        // Get all the pruebaMicroList where unidades is less than or equal to SMALLER_UNIDADES
        defaultPruebaMicroShouldNotBeFound("unidades.lessThanOrEqual=" + SMALLER_UNIDADES);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByUnidadesIsLessThanSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where unidades is less than DEFAULT_UNIDADES
        defaultPruebaMicroShouldNotBeFound("unidades.lessThan=" + DEFAULT_UNIDADES);

        // Get all the pruebaMicroList where unidades is less than UPDATED_UNIDADES
        defaultPruebaMicroShouldBeFound("unidades.lessThan=" + UPDATED_UNIDADES);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByUnidadesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where unidades is greater than DEFAULT_UNIDADES
        defaultPruebaMicroShouldNotBeFound("unidades.greaterThan=" + DEFAULT_UNIDADES);

        // Get all the pruebaMicroList where unidades is greater than SMALLER_UNIDADES
        defaultPruebaMicroShouldBeFound("unidades.greaterThan=" + SMALLER_UNIDADES);
    }


    @Test
    @Transactional
    public void getAllPruebaMicrosByObservacionesIsEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where observaciones equals to DEFAULT_OBSERVACIONES
        defaultPruebaMicroShouldBeFound("observaciones.equals=" + DEFAULT_OBSERVACIONES);

        // Get all the pruebaMicroList where observaciones equals to UPDATED_OBSERVACIONES
        defaultPruebaMicroShouldNotBeFound("observaciones.equals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByObservacionesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where observaciones not equals to DEFAULT_OBSERVACIONES
        defaultPruebaMicroShouldNotBeFound("observaciones.notEquals=" + DEFAULT_OBSERVACIONES);

        // Get all the pruebaMicroList where observaciones not equals to UPDATED_OBSERVACIONES
        defaultPruebaMicroShouldBeFound("observaciones.notEquals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByObservacionesIsInShouldWork() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where observaciones in DEFAULT_OBSERVACIONES or UPDATED_OBSERVACIONES
        defaultPruebaMicroShouldBeFound("observaciones.in=" + DEFAULT_OBSERVACIONES + "," + UPDATED_OBSERVACIONES);

        // Get all the pruebaMicroList where observaciones equals to UPDATED_OBSERVACIONES
        defaultPruebaMicroShouldNotBeFound("observaciones.in=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByObservacionesIsNullOrNotNull() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where observaciones is not null
        defaultPruebaMicroShouldBeFound("observaciones.specified=true");

        // Get all the pruebaMicroList where observaciones is null
        defaultPruebaMicroShouldNotBeFound("observaciones.specified=false");
    }
                @Test
    @Transactional
    public void getAllPruebaMicrosByObservacionesContainsSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where observaciones contains DEFAULT_OBSERVACIONES
        defaultPruebaMicroShouldBeFound("observaciones.contains=" + DEFAULT_OBSERVACIONES);

        // Get all the pruebaMicroList where observaciones contains UPDATED_OBSERVACIONES
        defaultPruebaMicroShouldNotBeFound("observaciones.contains=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllPruebaMicrosByObservacionesNotContainsSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);

        // Get all the pruebaMicroList where observaciones does not contain DEFAULT_OBSERVACIONES
        defaultPruebaMicroShouldNotBeFound("observaciones.doesNotContain=" + DEFAULT_OBSERVACIONES);

        // Get all the pruebaMicroList where observaciones does not contain UPDATED_OBSERVACIONES
        defaultPruebaMicroShouldBeFound("observaciones.doesNotContain=" + UPDATED_OBSERVACIONES);
    }


    @Test
    @Transactional
    public void getAllPruebaMicrosByAreaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Area area = pruebaMicro.getArea();
        pruebaMicroRepository.saveAndFlush(pruebaMicro);
        Long areaId = area.getId();

        // Get all the pruebaMicroList where area equals to areaId
        defaultPruebaMicroShouldBeFound("areaId.equals=" + areaId);

        // Get all the pruebaMicroList where area equals to areaId + 1
        defaultPruebaMicroShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }


    @Test
    @Transactional
    public void getAllPruebaMicrosByCultivoIsEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);
        Cultivo cultivo = CultivoResourceIT.createEntity(em);
        em.persist(cultivo);
        em.flush();
        pruebaMicro.setCultivo(cultivo);
        pruebaMicroRepository.saveAndFlush(pruebaMicro);
        Long cultivoId = cultivo.getId();

        // Get all the pruebaMicroList where cultivo equals to cultivoId
        defaultPruebaMicroShouldBeFound("cultivoId.equals=" + cultivoId);

        // Get all the pruebaMicroList where cultivo equals to cultivoId + 1
        defaultPruebaMicroShouldNotBeFound("cultivoId.equals=" + (cultivoId + 1));
    }


    @Test
    @Transactional
    public void getAllPruebaMicrosBySuperficieIsEqualToSomething() throws Exception {
        // Initialize the database
        pruebaMicroRepository.saveAndFlush(pruebaMicro);
        Superficie superficie = SuperficieResourceIT.createEntity(em);
        em.persist(superficie);
        em.flush();
        pruebaMicro.setSuperficie(superficie);
        pruebaMicroRepository.saveAndFlush(pruebaMicro);
        Long superficieId = superficie.getId();

        // Get all the pruebaMicroList where superficie equals to superficieId
        defaultPruebaMicroShouldBeFound("superficieId.equals=" + superficieId);

        // Get all the pruebaMicroList where superficie equals to superficieId + 1
        defaultPruebaMicroShouldNotBeFound("superficieId.equals=" + (superficieId + 1));
    }


    @Test
    @Transactional
    public void getAllPruebaMicrosByAnalistaIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserExtra analista = pruebaMicro.getAnalista();
        pruebaMicroRepository.saveAndFlush(pruebaMicro);
        Long analistaId = analista.getId();

        // Get all the pruebaMicroList where analista equals to analistaId
        defaultPruebaMicroShouldBeFound("analistaId.equals=" + analistaId);

        // Get all the pruebaMicroList where analista equals to analistaId + 1
        defaultPruebaMicroShouldNotBeFound("analistaId.equals=" + (analistaId + 1));
    }


    @Test
    @Transactional
    public void getAllPruebaMicrosByProveedorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Personal proveedor = pruebaMicro.getProveedor();
        pruebaMicroRepository.saveAndFlush(pruebaMicro);
        Long proveedorId = proveedor.getId();

        // Get all the pruebaMicroList where proveedor equals to proveedorId
        defaultPruebaMicroShouldBeFound("proveedorId.equals=" + proveedorId);

        // Get all the pruebaMicroList where proveedor equals to proveedorId + 1
        defaultPruebaMicroShouldNotBeFound("proveedorId.equals=" + (proveedorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPruebaMicroShouldBeFound(String filter) throws Exception {
        restPruebaMicroMockMvc.perform(get("/api/prueba-micros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pruebaMicro.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipodeMuestra").value(hasItem(DEFAULT_TIPODE_MUESTRA)))
            .andExpect(jsonPath("$.[*].idCatalogo").value(hasItem(DEFAULT_ID_CATALOGO)))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO)))
            .andExpect(jsonPath("$.[*].unidades").value(hasItem(DEFAULT_UNIDADES)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));

        // Check, that the count call also returns 1
        restPruebaMicroMockMvc.perform(get("/api/prueba-micros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPruebaMicroShouldNotBeFound(String filter) throws Exception {
        restPruebaMicroMockMvc.perform(get("/api/prueba-micros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPruebaMicroMockMvc.perform(get("/api/prueba-micros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPruebaMicro() throws Exception {
        // Get the pruebaMicro
        restPruebaMicroMockMvc.perform(get("/api/prueba-micros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePruebaMicro() throws Exception {
        // Initialize the database
        pruebaMicroService.save(pruebaMicro);

        int databaseSizeBeforeUpdate = pruebaMicroRepository.findAll().size();

        // Update the pruebaMicro
        PruebaMicro updatedPruebaMicro = pruebaMicroRepository.findById(pruebaMicro.getId()).get();
        // Disconnect from session so that the updates on updatedPruebaMicro are not directly saved in db
        em.detach(updatedPruebaMicro);
        updatedPruebaMicro
            .tipodeMuestra(UPDATED_TIPODE_MUESTRA)
            .idCatalogo(UPDATED_ID_CATALOGO)
            .lote(UPDATED_LOTE)
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN)
            .resultado(UPDATED_RESULTADO)
            .unidades(UPDATED_UNIDADES)
            .observaciones(UPDATED_OBSERVACIONES);

        restPruebaMicroMockMvc.perform(put("/api/prueba-micros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPruebaMicro)))
            .andExpect(status().isOk());

        // Validate the PruebaMicro in the database
        List<PruebaMicro> pruebaMicroList = pruebaMicroRepository.findAll();
        assertThat(pruebaMicroList).hasSize(databaseSizeBeforeUpdate);
        PruebaMicro testPruebaMicro = pruebaMicroList.get(pruebaMicroList.size() - 1);
        assertThat(testPruebaMicro.getTipodeMuestra()).isEqualTo(UPDATED_TIPODE_MUESTRA);
        assertThat(testPruebaMicro.getIdCatalogo()).isEqualTo(UPDATED_ID_CATALOGO);
        assertThat(testPruebaMicro.getLote()).isEqualTo(UPDATED_LOTE);
        assertThat(testPruebaMicro.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testPruebaMicro.getFin()).isEqualTo(UPDATED_FIN);
        assertThat(testPruebaMicro.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testPruebaMicro.getUnidades()).isEqualTo(UPDATED_UNIDADES);
        assertThat(testPruebaMicro.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingPruebaMicro() throws Exception {
        int databaseSizeBeforeUpdate = pruebaMicroRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPruebaMicroMockMvc.perform(put("/api/prueba-micros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pruebaMicro)))
            .andExpect(status().isBadRequest());

        // Validate the PruebaMicro in the database
        List<PruebaMicro> pruebaMicroList = pruebaMicroRepository.findAll();
        assertThat(pruebaMicroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePruebaMicro() throws Exception {
        // Initialize the database
        pruebaMicroService.save(pruebaMicro);

        int databaseSizeBeforeDelete = pruebaMicroRepository.findAll().size();

        // Delete the pruebaMicro
        restPruebaMicroMockMvc.perform(delete("/api/prueba-micros/{id}", pruebaMicro.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PruebaMicro> pruebaMicroList = pruebaMicroRepository.findAll();
        assertThat(pruebaMicroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
