package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.Personal;
import com.provi.lab.domain.Relacion;
import com.provi.lab.repository.PersonalRepository;
import com.provi.lab.service.PersonalService;
import com.provi.lab.service.dto.PersonalCriteria;
import com.provi.lab.service.PersonalQueryService;

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
 * Integration tests for the {@link PersonalResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PersonalResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_1 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_2 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    private static final String DEFAULT_DOMICILIO = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO = "BBBBBBBBBB";

    private static final String DEFAULT_COLONIA = "AAAAAAAAAA";
    private static final String UPDATED_COLONIA = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUD = "AAAAAAAAAA";
    private static final String UPDATED_LATITUD = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUD = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUD = "BBBBBBBBBB";

    private static final Integer DEFAULT_CP = 1;
    private static final Integer UPDATED_CP = 2;
    private static final Integer SMALLER_CP = 1 - 1;

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

    private static final Instant DEFAULT_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private PersonalQueryService personalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonalMockMvc;

    private Personal personal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personal createEntity(EntityManager em) {
        Personal personal = new Personal()
            .nombre(DEFAULT_NOMBRE)
            .apellido1(DEFAULT_APELLIDO_1)
            .apellido2(DEFAULT_APELLIDO_2)
            .alias(DEFAULT_ALIAS)
            .domicilio(DEFAULT_DOMICILIO)
            .colonia(DEFAULT_COLONIA)
            .localidad(DEFAULT_LOCALIDAD)
            .estado(DEFAULT_ESTADO)
            .pais(DEFAULT_PAIS)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD)
            .cp(DEFAULT_CP)
            .telefono(DEFAULT_TELEFONO)
            .email(DEFAULT_EMAIL)
            .rfc(DEFAULT_RFC)
            .inicio(DEFAULT_INICIO)
            .fin(DEFAULT_FIN)
            .cargo(DEFAULT_CARGO)
            .comentario(DEFAULT_COMENTARIO);
        // Add required entity
        Relacion relacion;
        if (TestUtil.findAll(em, Relacion.class).isEmpty()) {
            relacion = RelacionResourceIT.createEntity(em);
            em.persist(relacion);
            em.flush();
        } else {
            relacion = TestUtil.findAll(em, Relacion.class).get(0);
        }
        personal.setRelacion(relacion);
        return personal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personal createUpdatedEntity(EntityManager em) {
        Personal personal = new Personal()
            .nombre(UPDATED_NOMBRE)
            .apellido1(UPDATED_APELLIDO_1)
            .apellido2(UPDATED_APELLIDO_2)
            .alias(UPDATED_ALIAS)
            .domicilio(UPDATED_DOMICILIO)
            .colonia(UPDATED_COLONIA)
            .localidad(UPDATED_LOCALIDAD)
            .estado(UPDATED_ESTADO)
            .pais(UPDATED_PAIS)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .cp(UPDATED_CP)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .rfc(UPDATED_RFC)
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN)
            .cargo(UPDATED_CARGO)
            .comentario(UPDATED_COMENTARIO);
        // Add required entity
        Relacion relacion;
        if (TestUtil.findAll(em, Relacion.class).isEmpty()) {
            relacion = RelacionResourceIT.createUpdatedEntity(em);
            em.persist(relacion);
            em.flush();
        } else {
            relacion = TestUtil.findAll(em, Relacion.class).get(0);
        }
        personal.setRelacion(relacion);
        return personal;
    }

    @BeforeEach
    public void initTest() {
        personal = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonal() throws Exception {
        int databaseSizeBeforeCreate = personalRepository.findAll().size();
        // Create the Personal
        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isCreated());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeCreate + 1);
        Personal testPersonal = personalList.get(personalList.size() - 1);
        assertThat(testPersonal.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersonal.getApellido1()).isEqualTo(DEFAULT_APELLIDO_1);
        assertThat(testPersonal.getApellido2()).isEqualTo(DEFAULT_APELLIDO_2);
        assertThat(testPersonal.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testPersonal.getDomicilio()).isEqualTo(DEFAULT_DOMICILIO);
        assertThat(testPersonal.getColonia()).isEqualTo(DEFAULT_COLONIA);
        assertThat(testPersonal.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
        assertThat(testPersonal.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testPersonal.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testPersonal.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testPersonal.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testPersonal.getCp()).isEqualTo(DEFAULT_CP);
        assertThat(testPersonal.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testPersonal.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersonal.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testPersonal.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testPersonal.getFin()).isEqualTo(DEFAULT_FIN);
        assertThat(testPersonal.getCargo()).isEqualTo(DEFAULT_CARGO);
        assertThat(testPersonal.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void createPersonalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personalRepository.findAll().size();

        // Create the Personal with an existing ID
        personal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setNombre(null);

        // Create the Personal, which fails.


        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomicilioIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setDomicilio(null);

        // Create the Personal, which fails.


        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColoniaIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setColonia(null);

        // Create the Personal, which fails.


        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setEstado(null);

        // Create the Personal, which fails.


        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setPais(null);

        // Create the Personal, which fails.


        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setCp(null);

        // Create the Personal, which fails.


        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setTelefono(null);

        // Create the Personal, which fails.


        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setRfc(null);

        // Create the Personal, which fails.


        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setInicio(null);

        // Create the Personal, which fails.


        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCargoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setCargo(null);

        // Create the Personal, which fails.


        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonals() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList
        restPersonalMockMvc.perform(get("/api/personals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1)))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2)))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO)))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA)))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD)))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD)))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)));
    }
    
    @Test
    @Transactional
    public void getPersonal() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get the personal
        restPersonalMockMvc.perform(get("/api/personals/{id}", personal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personal.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellido1").value(DEFAULT_APELLIDO_1))
            .andExpect(jsonPath("$.apellido2").value(DEFAULT_APELLIDO_2))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS))
            .andExpect(jsonPath("$.domicilio").value(DEFAULT_DOMICILIO))
            .andExpect(jsonPath("$.colonia").value(DEFAULT_COLONIA))
            .andExpect(jsonPath("$.localidad").value(DEFAULT_LOCALIDAD))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD))
            .andExpect(jsonPath("$.cp").value(DEFAULT_CP))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO));
    }


    @Test
    @Transactional
    public void getPersonalsByIdFiltering() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        Long id = personal.getId();

        defaultPersonalShouldBeFound("id.equals=" + id);
        defaultPersonalShouldNotBeFound("id.notEquals=" + id);

        defaultPersonalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPersonalShouldNotBeFound("id.greaterThan=" + id);

        defaultPersonalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPersonalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPersonalsByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where nombre equals to DEFAULT_NOMBRE
        defaultPersonalShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the personalList where nombre equals to UPDATED_NOMBRE
        defaultPersonalShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPersonalsByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where nombre not equals to DEFAULT_NOMBRE
        defaultPersonalShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the personalList where nombre not equals to UPDATED_NOMBRE
        defaultPersonalShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPersonalsByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultPersonalShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the personalList where nombre equals to UPDATED_NOMBRE
        defaultPersonalShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPersonalsByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where nombre is not null
        defaultPersonalShouldBeFound("nombre.specified=true");

        // Get all the personalList where nombre is null
        defaultPersonalShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByNombreContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where nombre contains DEFAULT_NOMBRE
        defaultPersonalShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the personalList where nombre contains UPDATED_NOMBRE
        defaultPersonalShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPersonalsByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where nombre does not contain DEFAULT_NOMBRE
        defaultPersonalShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the personalList where nombre does not contain UPDATED_NOMBRE
        defaultPersonalShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllPersonalsByApellido1IsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido1 equals to DEFAULT_APELLIDO_1
        defaultPersonalShouldBeFound("apellido1.equals=" + DEFAULT_APELLIDO_1);

        // Get all the personalList where apellido1 equals to UPDATED_APELLIDO_1
        defaultPersonalShouldNotBeFound("apellido1.equals=" + UPDATED_APELLIDO_1);
    }

    @Test
    @Transactional
    public void getAllPersonalsByApellido1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido1 not equals to DEFAULT_APELLIDO_1
        defaultPersonalShouldNotBeFound("apellido1.notEquals=" + DEFAULT_APELLIDO_1);

        // Get all the personalList where apellido1 not equals to UPDATED_APELLIDO_1
        defaultPersonalShouldBeFound("apellido1.notEquals=" + UPDATED_APELLIDO_1);
    }

    @Test
    @Transactional
    public void getAllPersonalsByApellido1IsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido1 in DEFAULT_APELLIDO_1 or UPDATED_APELLIDO_1
        defaultPersonalShouldBeFound("apellido1.in=" + DEFAULT_APELLIDO_1 + "," + UPDATED_APELLIDO_1);

        // Get all the personalList where apellido1 equals to UPDATED_APELLIDO_1
        defaultPersonalShouldNotBeFound("apellido1.in=" + UPDATED_APELLIDO_1);
    }

    @Test
    @Transactional
    public void getAllPersonalsByApellido1IsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido1 is not null
        defaultPersonalShouldBeFound("apellido1.specified=true");

        // Get all the personalList where apellido1 is null
        defaultPersonalShouldNotBeFound("apellido1.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByApellido1ContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido1 contains DEFAULT_APELLIDO_1
        defaultPersonalShouldBeFound("apellido1.contains=" + DEFAULT_APELLIDO_1);

        // Get all the personalList where apellido1 contains UPDATED_APELLIDO_1
        defaultPersonalShouldNotBeFound("apellido1.contains=" + UPDATED_APELLIDO_1);
    }

    @Test
    @Transactional
    public void getAllPersonalsByApellido1NotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido1 does not contain DEFAULT_APELLIDO_1
        defaultPersonalShouldNotBeFound("apellido1.doesNotContain=" + DEFAULT_APELLIDO_1);

        // Get all the personalList where apellido1 does not contain UPDATED_APELLIDO_1
        defaultPersonalShouldBeFound("apellido1.doesNotContain=" + UPDATED_APELLIDO_1);
    }


    @Test
    @Transactional
    public void getAllPersonalsByApellido2IsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido2 equals to DEFAULT_APELLIDO_2
        defaultPersonalShouldBeFound("apellido2.equals=" + DEFAULT_APELLIDO_2);

        // Get all the personalList where apellido2 equals to UPDATED_APELLIDO_2
        defaultPersonalShouldNotBeFound("apellido2.equals=" + UPDATED_APELLIDO_2);
    }

    @Test
    @Transactional
    public void getAllPersonalsByApellido2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido2 not equals to DEFAULT_APELLIDO_2
        defaultPersonalShouldNotBeFound("apellido2.notEquals=" + DEFAULT_APELLIDO_2);

        // Get all the personalList where apellido2 not equals to UPDATED_APELLIDO_2
        defaultPersonalShouldBeFound("apellido2.notEquals=" + UPDATED_APELLIDO_2);
    }

    @Test
    @Transactional
    public void getAllPersonalsByApellido2IsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido2 in DEFAULT_APELLIDO_2 or UPDATED_APELLIDO_2
        defaultPersonalShouldBeFound("apellido2.in=" + DEFAULT_APELLIDO_2 + "," + UPDATED_APELLIDO_2);

        // Get all the personalList where apellido2 equals to UPDATED_APELLIDO_2
        defaultPersonalShouldNotBeFound("apellido2.in=" + UPDATED_APELLIDO_2);
    }

    @Test
    @Transactional
    public void getAllPersonalsByApellido2IsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido2 is not null
        defaultPersonalShouldBeFound("apellido2.specified=true");

        // Get all the personalList where apellido2 is null
        defaultPersonalShouldNotBeFound("apellido2.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByApellido2ContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido2 contains DEFAULT_APELLIDO_2
        defaultPersonalShouldBeFound("apellido2.contains=" + DEFAULT_APELLIDO_2);

        // Get all the personalList where apellido2 contains UPDATED_APELLIDO_2
        defaultPersonalShouldNotBeFound("apellido2.contains=" + UPDATED_APELLIDO_2);
    }

    @Test
    @Transactional
    public void getAllPersonalsByApellido2NotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where apellido2 does not contain DEFAULT_APELLIDO_2
        defaultPersonalShouldNotBeFound("apellido2.doesNotContain=" + DEFAULT_APELLIDO_2);

        // Get all the personalList where apellido2 does not contain UPDATED_APELLIDO_2
        defaultPersonalShouldBeFound("apellido2.doesNotContain=" + UPDATED_APELLIDO_2);
    }


    @Test
    @Transactional
    public void getAllPersonalsByAliasIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where alias equals to DEFAULT_ALIAS
        defaultPersonalShouldBeFound("alias.equals=" + DEFAULT_ALIAS);

        // Get all the personalList where alias equals to UPDATED_ALIAS
        defaultPersonalShouldNotBeFound("alias.equals=" + UPDATED_ALIAS);
    }

    @Test
    @Transactional
    public void getAllPersonalsByAliasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where alias not equals to DEFAULT_ALIAS
        defaultPersonalShouldNotBeFound("alias.notEquals=" + DEFAULT_ALIAS);

        // Get all the personalList where alias not equals to UPDATED_ALIAS
        defaultPersonalShouldBeFound("alias.notEquals=" + UPDATED_ALIAS);
    }

    @Test
    @Transactional
    public void getAllPersonalsByAliasIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where alias in DEFAULT_ALIAS or UPDATED_ALIAS
        defaultPersonalShouldBeFound("alias.in=" + DEFAULT_ALIAS + "," + UPDATED_ALIAS);

        // Get all the personalList where alias equals to UPDATED_ALIAS
        defaultPersonalShouldNotBeFound("alias.in=" + UPDATED_ALIAS);
    }

    @Test
    @Transactional
    public void getAllPersonalsByAliasIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where alias is not null
        defaultPersonalShouldBeFound("alias.specified=true");

        // Get all the personalList where alias is null
        defaultPersonalShouldNotBeFound("alias.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByAliasContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where alias contains DEFAULT_ALIAS
        defaultPersonalShouldBeFound("alias.contains=" + DEFAULT_ALIAS);

        // Get all the personalList where alias contains UPDATED_ALIAS
        defaultPersonalShouldNotBeFound("alias.contains=" + UPDATED_ALIAS);
    }

    @Test
    @Transactional
    public void getAllPersonalsByAliasNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where alias does not contain DEFAULT_ALIAS
        defaultPersonalShouldNotBeFound("alias.doesNotContain=" + DEFAULT_ALIAS);

        // Get all the personalList where alias does not contain UPDATED_ALIAS
        defaultPersonalShouldBeFound("alias.doesNotContain=" + UPDATED_ALIAS);
    }


    @Test
    @Transactional
    public void getAllPersonalsByDomicilioIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where domicilio equals to DEFAULT_DOMICILIO
        defaultPersonalShouldBeFound("domicilio.equals=" + DEFAULT_DOMICILIO);

        // Get all the personalList where domicilio equals to UPDATED_DOMICILIO
        defaultPersonalShouldNotBeFound("domicilio.equals=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByDomicilioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where domicilio not equals to DEFAULT_DOMICILIO
        defaultPersonalShouldNotBeFound("domicilio.notEquals=" + DEFAULT_DOMICILIO);

        // Get all the personalList where domicilio not equals to UPDATED_DOMICILIO
        defaultPersonalShouldBeFound("domicilio.notEquals=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByDomicilioIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where domicilio in DEFAULT_DOMICILIO or UPDATED_DOMICILIO
        defaultPersonalShouldBeFound("domicilio.in=" + DEFAULT_DOMICILIO + "," + UPDATED_DOMICILIO);

        // Get all the personalList where domicilio equals to UPDATED_DOMICILIO
        defaultPersonalShouldNotBeFound("domicilio.in=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByDomicilioIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where domicilio is not null
        defaultPersonalShouldBeFound("domicilio.specified=true");

        // Get all the personalList where domicilio is null
        defaultPersonalShouldNotBeFound("domicilio.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByDomicilioContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where domicilio contains DEFAULT_DOMICILIO
        defaultPersonalShouldBeFound("domicilio.contains=" + DEFAULT_DOMICILIO);

        // Get all the personalList where domicilio contains UPDATED_DOMICILIO
        defaultPersonalShouldNotBeFound("domicilio.contains=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByDomicilioNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where domicilio does not contain DEFAULT_DOMICILIO
        defaultPersonalShouldNotBeFound("domicilio.doesNotContain=" + DEFAULT_DOMICILIO);

        // Get all the personalList where domicilio does not contain UPDATED_DOMICILIO
        defaultPersonalShouldBeFound("domicilio.doesNotContain=" + UPDATED_DOMICILIO);
    }


    @Test
    @Transactional
    public void getAllPersonalsByColoniaIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where colonia equals to DEFAULT_COLONIA
        defaultPersonalShouldBeFound("colonia.equals=" + DEFAULT_COLONIA);

        // Get all the personalList where colonia equals to UPDATED_COLONIA
        defaultPersonalShouldNotBeFound("colonia.equals=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllPersonalsByColoniaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where colonia not equals to DEFAULT_COLONIA
        defaultPersonalShouldNotBeFound("colonia.notEquals=" + DEFAULT_COLONIA);

        // Get all the personalList where colonia not equals to UPDATED_COLONIA
        defaultPersonalShouldBeFound("colonia.notEquals=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllPersonalsByColoniaIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where colonia in DEFAULT_COLONIA or UPDATED_COLONIA
        defaultPersonalShouldBeFound("colonia.in=" + DEFAULT_COLONIA + "," + UPDATED_COLONIA);

        // Get all the personalList where colonia equals to UPDATED_COLONIA
        defaultPersonalShouldNotBeFound("colonia.in=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllPersonalsByColoniaIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where colonia is not null
        defaultPersonalShouldBeFound("colonia.specified=true");

        // Get all the personalList where colonia is null
        defaultPersonalShouldNotBeFound("colonia.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByColoniaContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where colonia contains DEFAULT_COLONIA
        defaultPersonalShouldBeFound("colonia.contains=" + DEFAULT_COLONIA);

        // Get all the personalList where colonia contains UPDATED_COLONIA
        defaultPersonalShouldNotBeFound("colonia.contains=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllPersonalsByColoniaNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where colonia does not contain DEFAULT_COLONIA
        defaultPersonalShouldNotBeFound("colonia.doesNotContain=" + DEFAULT_COLONIA);

        // Get all the personalList where colonia does not contain UPDATED_COLONIA
        defaultPersonalShouldBeFound("colonia.doesNotContain=" + UPDATED_COLONIA);
    }


    @Test
    @Transactional
    public void getAllPersonalsByLocalidadIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where localidad equals to DEFAULT_LOCALIDAD
        defaultPersonalShouldBeFound("localidad.equals=" + DEFAULT_LOCALIDAD);

        // Get all the personalList where localidad equals to UPDATED_LOCALIDAD
        defaultPersonalShouldNotBeFound("localidad.equals=" + UPDATED_LOCALIDAD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLocalidadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where localidad not equals to DEFAULT_LOCALIDAD
        defaultPersonalShouldNotBeFound("localidad.notEquals=" + DEFAULT_LOCALIDAD);

        // Get all the personalList where localidad not equals to UPDATED_LOCALIDAD
        defaultPersonalShouldBeFound("localidad.notEquals=" + UPDATED_LOCALIDAD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLocalidadIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where localidad in DEFAULT_LOCALIDAD or UPDATED_LOCALIDAD
        defaultPersonalShouldBeFound("localidad.in=" + DEFAULT_LOCALIDAD + "," + UPDATED_LOCALIDAD);

        // Get all the personalList where localidad equals to UPDATED_LOCALIDAD
        defaultPersonalShouldNotBeFound("localidad.in=" + UPDATED_LOCALIDAD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLocalidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where localidad is not null
        defaultPersonalShouldBeFound("localidad.specified=true");

        // Get all the personalList where localidad is null
        defaultPersonalShouldNotBeFound("localidad.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByLocalidadContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where localidad contains DEFAULT_LOCALIDAD
        defaultPersonalShouldBeFound("localidad.contains=" + DEFAULT_LOCALIDAD);

        // Get all the personalList where localidad contains UPDATED_LOCALIDAD
        defaultPersonalShouldNotBeFound("localidad.contains=" + UPDATED_LOCALIDAD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLocalidadNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where localidad does not contain DEFAULT_LOCALIDAD
        defaultPersonalShouldNotBeFound("localidad.doesNotContain=" + DEFAULT_LOCALIDAD);

        // Get all the personalList where localidad does not contain UPDATED_LOCALIDAD
        defaultPersonalShouldBeFound("localidad.doesNotContain=" + UPDATED_LOCALIDAD);
    }


    @Test
    @Transactional
    public void getAllPersonalsByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where estado equals to DEFAULT_ESTADO
        defaultPersonalShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the personalList where estado equals to UPDATED_ESTADO
        defaultPersonalShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where estado not equals to DEFAULT_ESTADO
        defaultPersonalShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the personalList where estado not equals to UPDATED_ESTADO
        defaultPersonalShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultPersonalShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the personalList where estado equals to UPDATED_ESTADO
        defaultPersonalShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where estado is not null
        defaultPersonalShouldBeFound("estado.specified=true");

        // Get all the personalList where estado is null
        defaultPersonalShouldNotBeFound("estado.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByEstadoContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where estado contains DEFAULT_ESTADO
        defaultPersonalShouldBeFound("estado.contains=" + DEFAULT_ESTADO);

        // Get all the personalList where estado contains UPDATED_ESTADO
        defaultPersonalShouldNotBeFound("estado.contains=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByEstadoNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where estado does not contain DEFAULT_ESTADO
        defaultPersonalShouldNotBeFound("estado.doesNotContain=" + DEFAULT_ESTADO);

        // Get all the personalList where estado does not contain UPDATED_ESTADO
        defaultPersonalShouldBeFound("estado.doesNotContain=" + UPDATED_ESTADO);
    }


    @Test
    @Transactional
    public void getAllPersonalsByPaisIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where pais equals to DEFAULT_PAIS
        defaultPersonalShouldBeFound("pais.equals=" + DEFAULT_PAIS);

        // Get all the personalList where pais equals to UPDATED_PAIS
        defaultPersonalShouldNotBeFound("pais.equals=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllPersonalsByPaisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where pais not equals to DEFAULT_PAIS
        defaultPersonalShouldNotBeFound("pais.notEquals=" + DEFAULT_PAIS);

        // Get all the personalList where pais not equals to UPDATED_PAIS
        defaultPersonalShouldBeFound("pais.notEquals=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllPersonalsByPaisIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where pais in DEFAULT_PAIS or UPDATED_PAIS
        defaultPersonalShouldBeFound("pais.in=" + DEFAULT_PAIS + "," + UPDATED_PAIS);

        // Get all the personalList where pais equals to UPDATED_PAIS
        defaultPersonalShouldNotBeFound("pais.in=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllPersonalsByPaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where pais is not null
        defaultPersonalShouldBeFound("pais.specified=true");

        // Get all the personalList where pais is null
        defaultPersonalShouldNotBeFound("pais.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByPaisContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where pais contains DEFAULT_PAIS
        defaultPersonalShouldBeFound("pais.contains=" + DEFAULT_PAIS);

        // Get all the personalList where pais contains UPDATED_PAIS
        defaultPersonalShouldNotBeFound("pais.contains=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllPersonalsByPaisNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where pais does not contain DEFAULT_PAIS
        defaultPersonalShouldNotBeFound("pais.doesNotContain=" + DEFAULT_PAIS);

        // Get all the personalList where pais does not contain UPDATED_PAIS
        defaultPersonalShouldBeFound("pais.doesNotContain=" + UPDATED_PAIS);
    }


    @Test
    @Transactional
    public void getAllPersonalsByLatitudIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where latitud equals to DEFAULT_LATITUD
        defaultPersonalShouldBeFound("latitud.equals=" + DEFAULT_LATITUD);

        // Get all the personalList where latitud equals to UPDATED_LATITUD
        defaultPersonalShouldNotBeFound("latitud.equals=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLatitudIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where latitud not equals to DEFAULT_LATITUD
        defaultPersonalShouldNotBeFound("latitud.notEquals=" + DEFAULT_LATITUD);

        // Get all the personalList where latitud not equals to UPDATED_LATITUD
        defaultPersonalShouldBeFound("latitud.notEquals=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLatitudIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where latitud in DEFAULT_LATITUD or UPDATED_LATITUD
        defaultPersonalShouldBeFound("latitud.in=" + DEFAULT_LATITUD + "," + UPDATED_LATITUD);

        // Get all the personalList where latitud equals to UPDATED_LATITUD
        defaultPersonalShouldNotBeFound("latitud.in=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLatitudIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where latitud is not null
        defaultPersonalShouldBeFound("latitud.specified=true");

        // Get all the personalList where latitud is null
        defaultPersonalShouldNotBeFound("latitud.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByLatitudContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where latitud contains DEFAULT_LATITUD
        defaultPersonalShouldBeFound("latitud.contains=" + DEFAULT_LATITUD);

        // Get all the personalList where latitud contains UPDATED_LATITUD
        defaultPersonalShouldNotBeFound("latitud.contains=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLatitudNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where latitud does not contain DEFAULT_LATITUD
        defaultPersonalShouldNotBeFound("latitud.doesNotContain=" + DEFAULT_LATITUD);

        // Get all the personalList where latitud does not contain UPDATED_LATITUD
        defaultPersonalShouldBeFound("latitud.doesNotContain=" + UPDATED_LATITUD);
    }


    @Test
    @Transactional
    public void getAllPersonalsByLongitudIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where longitud equals to DEFAULT_LONGITUD
        defaultPersonalShouldBeFound("longitud.equals=" + DEFAULT_LONGITUD);

        // Get all the personalList where longitud equals to UPDATED_LONGITUD
        defaultPersonalShouldNotBeFound("longitud.equals=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLongitudIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where longitud not equals to DEFAULT_LONGITUD
        defaultPersonalShouldNotBeFound("longitud.notEquals=" + DEFAULT_LONGITUD);

        // Get all the personalList where longitud not equals to UPDATED_LONGITUD
        defaultPersonalShouldBeFound("longitud.notEquals=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLongitudIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where longitud in DEFAULT_LONGITUD or UPDATED_LONGITUD
        defaultPersonalShouldBeFound("longitud.in=" + DEFAULT_LONGITUD + "," + UPDATED_LONGITUD);

        // Get all the personalList where longitud equals to UPDATED_LONGITUD
        defaultPersonalShouldNotBeFound("longitud.in=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLongitudIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where longitud is not null
        defaultPersonalShouldBeFound("longitud.specified=true");

        // Get all the personalList where longitud is null
        defaultPersonalShouldNotBeFound("longitud.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByLongitudContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where longitud contains DEFAULT_LONGITUD
        defaultPersonalShouldBeFound("longitud.contains=" + DEFAULT_LONGITUD);

        // Get all the personalList where longitud contains UPDATED_LONGITUD
        defaultPersonalShouldNotBeFound("longitud.contains=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllPersonalsByLongitudNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where longitud does not contain DEFAULT_LONGITUD
        defaultPersonalShouldNotBeFound("longitud.doesNotContain=" + DEFAULT_LONGITUD);

        // Get all the personalList where longitud does not contain UPDATED_LONGITUD
        defaultPersonalShouldBeFound("longitud.doesNotContain=" + UPDATED_LONGITUD);
    }


    @Test
    @Transactional
    public void getAllPersonalsByCpIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cp equals to DEFAULT_CP
        defaultPersonalShouldBeFound("cp.equals=" + DEFAULT_CP);

        // Get all the personalList where cp equals to UPDATED_CP
        defaultPersonalShouldNotBeFound("cp.equals=" + UPDATED_CP);
    }

    @Test
    @Transactional
    public void getAllPersonalsByCpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cp not equals to DEFAULT_CP
        defaultPersonalShouldNotBeFound("cp.notEquals=" + DEFAULT_CP);

        // Get all the personalList where cp not equals to UPDATED_CP
        defaultPersonalShouldBeFound("cp.notEquals=" + UPDATED_CP);
    }

    @Test
    @Transactional
    public void getAllPersonalsByCpIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cp in DEFAULT_CP or UPDATED_CP
        defaultPersonalShouldBeFound("cp.in=" + DEFAULT_CP + "," + UPDATED_CP);

        // Get all the personalList where cp equals to UPDATED_CP
        defaultPersonalShouldNotBeFound("cp.in=" + UPDATED_CP);
    }

    @Test
    @Transactional
    public void getAllPersonalsByCpIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cp is not null
        defaultPersonalShouldBeFound("cp.specified=true");

        // Get all the personalList where cp is null
        defaultPersonalShouldNotBeFound("cp.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalsByCpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cp is greater than or equal to DEFAULT_CP
        defaultPersonalShouldBeFound("cp.greaterThanOrEqual=" + DEFAULT_CP);

        // Get all the personalList where cp is greater than or equal to UPDATED_CP
        defaultPersonalShouldNotBeFound("cp.greaterThanOrEqual=" + UPDATED_CP);
    }

    @Test
    @Transactional
    public void getAllPersonalsByCpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cp is less than or equal to DEFAULT_CP
        defaultPersonalShouldBeFound("cp.lessThanOrEqual=" + DEFAULT_CP);

        // Get all the personalList where cp is less than or equal to SMALLER_CP
        defaultPersonalShouldNotBeFound("cp.lessThanOrEqual=" + SMALLER_CP);
    }

    @Test
    @Transactional
    public void getAllPersonalsByCpIsLessThanSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cp is less than DEFAULT_CP
        defaultPersonalShouldNotBeFound("cp.lessThan=" + DEFAULT_CP);

        // Get all the personalList where cp is less than UPDATED_CP
        defaultPersonalShouldBeFound("cp.lessThan=" + UPDATED_CP);
    }

    @Test
    @Transactional
    public void getAllPersonalsByCpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cp is greater than DEFAULT_CP
        defaultPersonalShouldNotBeFound("cp.greaterThan=" + DEFAULT_CP);

        // Get all the personalList where cp is greater than SMALLER_CP
        defaultPersonalShouldBeFound("cp.greaterThan=" + SMALLER_CP);
    }


    @Test
    @Transactional
    public void getAllPersonalsByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where telefono equals to DEFAULT_TELEFONO
        defaultPersonalShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the personalList where telefono equals to UPDATED_TELEFONO
        defaultPersonalShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByTelefonoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where telefono not equals to DEFAULT_TELEFONO
        defaultPersonalShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

        // Get all the personalList where telefono not equals to UPDATED_TELEFONO
        defaultPersonalShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultPersonalShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the personalList where telefono equals to UPDATED_TELEFONO
        defaultPersonalShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where telefono is not null
        defaultPersonalShouldBeFound("telefono.specified=true");

        // Get all the personalList where telefono is null
        defaultPersonalShouldNotBeFound("telefono.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where telefono contains DEFAULT_TELEFONO
        defaultPersonalShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

        // Get all the personalList where telefono contains UPDATED_TELEFONO
        defaultPersonalShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where telefono does not contain DEFAULT_TELEFONO
        defaultPersonalShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

        // Get all the personalList where telefono does not contain UPDATED_TELEFONO
        defaultPersonalShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
    }


    @Test
    @Transactional
    public void getAllPersonalsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where email equals to DEFAULT_EMAIL
        defaultPersonalShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the personalList where email equals to UPDATED_EMAIL
        defaultPersonalShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPersonalsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where email not equals to DEFAULT_EMAIL
        defaultPersonalShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the personalList where email not equals to UPDATED_EMAIL
        defaultPersonalShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPersonalsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultPersonalShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the personalList where email equals to UPDATED_EMAIL
        defaultPersonalShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPersonalsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where email is not null
        defaultPersonalShouldBeFound("email.specified=true");

        // Get all the personalList where email is null
        defaultPersonalShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByEmailContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where email contains DEFAULT_EMAIL
        defaultPersonalShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the personalList where email contains UPDATED_EMAIL
        defaultPersonalShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPersonalsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where email does not contain DEFAULT_EMAIL
        defaultPersonalShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the personalList where email does not contain UPDATED_EMAIL
        defaultPersonalShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllPersonalsByRfcIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where rfc equals to DEFAULT_RFC
        defaultPersonalShouldBeFound("rfc.equals=" + DEFAULT_RFC);

        // Get all the personalList where rfc equals to UPDATED_RFC
        defaultPersonalShouldNotBeFound("rfc.equals=" + UPDATED_RFC);
    }

    @Test
    @Transactional
    public void getAllPersonalsByRfcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where rfc not equals to DEFAULT_RFC
        defaultPersonalShouldNotBeFound("rfc.notEquals=" + DEFAULT_RFC);

        // Get all the personalList where rfc not equals to UPDATED_RFC
        defaultPersonalShouldBeFound("rfc.notEquals=" + UPDATED_RFC);
    }

    @Test
    @Transactional
    public void getAllPersonalsByRfcIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where rfc in DEFAULT_RFC or UPDATED_RFC
        defaultPersonalShouldBeFound("rfc.in=" + DEFAULT_RFC + "," + UPDATED_RFC);

        // Get all the personalList where rfc equals to UPDATED_RFC
        defaultPersonalShouldNotBeFound("rfc.in=" + UPDATED_RFC);
    }

    @Test
    @Transactional
    public void getAllPersonalsByRfcIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where rfc is not null
        defaultPersonalShouldBeFound("rfc.specified=true");

        // Get all the personalList where rfc is null
        defaultPersonalShouldNotBeFound("rfc.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByRfcContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where rfc contains DEFAULT_RFC
        defaultPersonalShouldBeFound("rfc.contains=" + DEFAULT_RFC);

        // Get all the personalList where rfc contains UPDATED_RFC
        defaultPersonalShouldNotBeFound("rfc.contains=" + UPDATED_RFC);
    }

    @Test
    @Transactional
    public void getAllPersonalsByRfcNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where rfc does not contain DEFAULT_RFC
        defaultPersonalShouldNotBeFound("rfc.doesNotContain=" + DEFAULT_RFC);

        // Get all the personalList where rfc does not contain UPDATED_RFC
        defaultPersonalShouldBeFound("rfc.doesNotContain=" + UPDATED_RFC);
    }


    @Test
    @Transactional
    public void getAllPersonalsByInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where inicio equals to DEFAULT_INICIO
        defaultPersonalShouldBeFound("inicio.equals=" + DEFAULT_INICIO);

        // Get all the personalList where inicio equals to UPDATED_INICIO
        defaultPersonalShouldNotBeFound("inicio.equals=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByInicioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where inicio not equals to DEFAULT_INICIO
        defaultPersonalShouldNotBeFound("inicio.notEquals=" + DEFAULT_INICIO);

        // Get all the personalList where inicio not equals to UPDATED_INICIO
        defaultPersonalShouldBeFound("inicio.notEquals=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByInicioIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where inicio in DEFAULT_INICIO or UPDATED_INICIO
        defaultPersonalShouldBeFound("inicio.in=" + DEFAULT_INICIO + "," + UPDATED_INICIO);

        // Get all the personalList where inicio equals to UPDATED_INICIO
        defaultPersonalShouldNotBeFound("inicio.in=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where inicio is not null
        defaultPersonalShouldBeFound("inicio.specified=true");

        // Get all the personalList where inicio is null
        defaultPersonalShouldNotBeFound("inicio.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalsByFinIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where fin equals to DEFAULT_FIN
        defaultPersonalShouldBeFound("fin.equals=" + DEFAULT_FIN);

        // Get all the personalList where fin equals to UPDATED_FIN
        defaultPersonalShouldNotBeFound("fin.equals=" + UPDATED_FIN);
    }

    @Test
    @Transactional
    public void getAllPersonalsByFinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where fin not equals to DEFAULT_FIN
        defaultPersonalShouldNotBeFound("fin.notEquals=" + DEFAULT_FIN);

        // Get all the personalList where fin not equals to UPDATED_FIN
        defaultPersonalShouldBeFound("fin.notEquals=" + UPDATED_FIN);
    }

    @Test
    @Transactional
    public void getAllPersonalsByFinIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where fin in DEFAULT_FIN or UPDATED_FIN
        defaultPersonalShouldBeFound("fin.in=" + DEFAULT_FIN + "," + UPDATED_FIN);

        // Get all the personalList where fin equals to UPDATED_FIN
        defaultPersonalShouldNotBeFound("fin.in=" + UPDATED_FIN);
    }

    @Test
    @Transactional
    public void getAllPersonalsByFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where fin is not null
        defaultPersonalShouldBeFound("fin.specified=true");

        // Get all the personalList where fin is null
        defaultPersonalShouldNotBeFound("fin.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalsByCargoIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cargo equals to DEFAULT_CARGO
        defaultPersonalShouldBeFound("cargo.equals=" + DEFAULT_CARGO);

        // Get all the personalList where cargo equals to UPDATED_CARGO
        defaultPersonalShouldNotBeFound("cargo.equals=" + UPDATED_CARGO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByCargoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cargo not equals to DEFAULT_CARGO
        defaultPersonalShouldNotBeFound("cargo.notEquals=" + DEFAULT_CARGO);

        // Get all the personalList where cargo not equals to UPDATED_CARGO
        defaultPersonalShouldBeFound("cargo.notEquals=" + UPDATED_CARGO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByCargoIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cargo in DEFAULT_CARGO or UPDATED_CARGO
        defaultPersonalShouldBeFound("cargo.in=" + DEFAULT_CARGO + "," + UPDATED_CARGO);

        // Get all the personalList where cargo equals to UPDATED_CARGO
        defaultPersonalShouldNotBeFound("cargo.in=" + UPDATED_CARGO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByCargoIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cargo is not null
        defaultPersonalShouldBeFound("cargo.specified=true");

        // Get all the personalList where cargo is null
        defaultPersonalShouldNotBeFound("cargo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByCargoContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cargo contains DEFAULT_CARGO
        defaultPersonalShouldBeFound("cargo.contains=" + DEFAULT_CARGO);

        // Get all the personalList where cargo contains UPDATED_CARGO
        defaultPersonalShouldNotBeFound("cargo.contains=" + UPDATED_CARGO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByCargoNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where cargo does not contain DEFAULT_CARGO
        defaultPersonalShouldNotBeFound("cargo.doesNotContain=" + DEFAULT_CARGO);

        // Get all the personalList where cargo does not contain UPDATED_CARGO
        defaultPersonalShouldBeFound("cargo.doesNotContain=" + UPDATED_CARGO);
    }


    @Test
    @Transactional
    public void getAllPersonalsByComentarioIsEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where comentario equals to DEFAULT_COMENTARIO
        defaultPersonalShouldBeFound("comentario.equals=" + DEFAULT_COMENTARIO);

        // Get all the personalList where comentario equals to UPDATED_COMENTARIO
        defaultPersonalShouldNotBeFound("comentario.equals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByComentarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where comentario not equals to DEFAULT_COMENTARIO
        defaultPersonalShouldNotBeFound("comentario.notEquals=" + DEFAULT_COMENTARIO);

        // Get all the personalList where comentario not equals to UPDATED_COMENTARIO
        defaultPersonalShouldBeFound("comentario.notEquals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByComentarioIsInShouldWork() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where comentario in DEFAULT_COMENTARIO or UPDATED_COMENTARIO
        defaultPersonalShouldBeFound("comentario.in=" + DEFAULT_COMENTARIO + "," + UPDATED_COMENTARIO);

        // Get all the personalList where comentario equals to UPDATED_COMENTARIO
        defaultPersonalShouldNotBeFound("comentario.in=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByComentarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where comentario is not null
        defaultPersonalShouldBeFound("comentario.specified=true");

        // Get all the personalList where comentario is null
        defaultPersonalShouldNotBeFound("comentario.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalsByComentarioContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where comentario contains DEFAULT_COMENTARIO
        defaultPersonalShouldBeFound("comentario.contains=" + DEFAULT_COMENTARIO);

        // Get all the personalList where comentario contains UPDATED_COMENTARIO
        defaultPersonalShouldNotBeFound("comentario.contains=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllPersonalsByComentarioNotContainsSomething() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList where comentario does not contain DEFAULT_COMENTARIO
        defaultPersonalShouldNotBeFound("comentario.doesNotContain=" + DEFAULT_COMENTARIO);

        // Get all the personalList where comentario does not contain UPDATED_COMENTARIO
        defaultPersonalShouldBeFound("comentario.doesNotContain=" + UPDATED_COMENTARIO);
    }


    @Test
    @Transactional
    public void getAllPersonalsByRelacionIsEqualToSomething() throws Exception {
        // Get already existing entity
        Relacion relacion = personal.getRelacion();
        personalRepository.saveAndFlush(personal);
        Long relacionId = relacion.getId();

        // Get all the personalList where relacion equals to relacionId
        defaultPersonalShouldBeFound("relacionId.equals=" + relacionId);

        // Get all the personalList where relacion equals to relacionId + 1
        defaultPersonalShouldNotBeFound("relacionId.equals=" + (relacionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPersonalShouldBeFound(String filter) throws Exception {
        restPersonalMockMvc.perform(get("/api/personals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1)))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2)))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO)))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA)))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD)))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD)))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)));

        // Check, that the count call also returns 1
        restPersonalMockMvc.perform(get("/api/personals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPersonalShouldNotBeFound(String filter) throws Exception {
        restPersonalMockMvc.perform(get("/api/personals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonalMockMvc.perform(get("/api/personals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPersonal() throws Exception {
        // Get the personal
        restPersonalMockMvc.perform(get("/api/personals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonal() throws Exception {
        // Initialize the database
        personalService.save(personal);

        int databaseSizeBeforeUpdate = personalRepository.findAll().size();

        // Update the personal
        Personal updatedPersonal = personalRepository.findById(personal.getId()).get();
        // Disconnect from session so that the updates on updatedPersonal are not directly saved in db
        em.detach(updatedPersonal);
        updatedPersonal
            .nombre(UPDATED_NOMBRE)
            .apellido1(UPDATED_APELLIDO_1)
            .apellido2(UPDATED_APELLIDO_2)
            .alias(UPDATED_ALIAS)
            .domicilio(UPDATED_DOMICILIO)
            .colonia(UPDATED_COLONIA)
            .localidad(UPDATED_LOCALIDAD)
            .estado(UPDATED_ESTADO)
            .pais(UPDATED_PAIS)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .cp(UPDATED_CP)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .rfc(UPDATED_RFC)
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN)
            .cargo(UPDATED_CARGO)
            .comentario(UPDATED_COMENTARIO);

        restPersonalMockMvc.perform(put("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonal)))
            .andExpect(status().isOk());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeUpdate);
        Personal testPersonal = personalList.get(personalList.size() - 1);
        assertThat(testPersonal.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersonal.getApellido1()).isEqualTo(UPDATED_APELLIDO_1);
        assertThat(testPersonal.getApellido2()).isEqualTo(UPDATED_APELLIDO_2);
        assertThat(testPersonal.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testPersonal.getDomicilio()).isEqualTo(UPDATED_DOMICILIO);
        assertThat(testPersonal.getColonia()).isEqualTo(UPDATED_COLONIA);
        assertThat(testPersonal.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testPersonal.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testPersonal.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testPersonal.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testPersonal.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testPersonal.getCp()).isEqualTo(UPDATED_CP);
        assertThat(testPersonal.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testPersonal.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonal.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testPersonal.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testPersonal.getFin()).isEqualTo(UPDATED_FIN);
        assertThat(testPersonal.getCargo()).isEqualTo(UPDATED_CARGO);
        assertThat(testPersonal.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonal() throws Exception {
        int databaseSizeBeforeUpdate = personalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalMockMvc.perform(put("/api/personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonal() throws Exception {
        // Initialize the database
        personalService.save(personal);

        int databaseSizeBeforeDelete = personalRepository.findAll().size();

        // Delete the personal
        restPersonalMockMvc.perform(delete("/api/personals/{id}", personal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
