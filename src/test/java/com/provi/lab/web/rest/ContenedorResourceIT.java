package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.Contenedor;
import com.provi.lab.repository.ContenedorRepository;
import com.provi.lab.service.ContenedorService;
import com.provi.lab.service.dto.ContenedorCriteria;
import com.provi.lab.service.ContenedorQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContenedorResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContenedorResourceIT {

    private static final String DEFAULT_CONTENEDOR = "AAAAAAAAAA";
    private static final String UPDATED_CONTENEDOR = "BBBBBBBBBB";

    @Autowired
    private ContenedorRepository contenedorRepository;

    @Autowired
    private ContenedorService contenedorService;

    @Autowired
    private ContenedorQueryService contenedorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContenedorMockMvc;

    private Contenedor contenedor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contenedor createEntity(EntityManager em) {
        Contenedor contenedor = new Contenedor()
            .contenedor(DEFAULT_CONTENEDOR);
        return contenedor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contenedor createUpdatedEntity(EntityManager em) {
        Contenedor contenedor = new Contenedor()
            .contenedor(UPDATED_CONTENEDOR);
        return contenedor;
    }

    @BeforeEach
    public void initTest() {
        contenedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createContenedor() throws Exception {
        int databaseSizeBeforeCreate = contenedorRepository.findAll().size();
        // Create the Contenedor
        restContenedorMockMvc.perform(post("/api/contenedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenedor)))
            .andExpect(status().isCreated());

        // Validate the Contenedor in the database
        List<Contenedor> contenedorList = contenedorRepository.findAll();
        assertThat(contenedorList).hasSize(databaseSizeBeforeCreate + 1);
        Contenedor testContenedor = contenedorList.get(contenedorList.size() - 1);
        assertThat(testContenedor.getContenedor()).isEqualTo(DEFAULT_CONTENEDOR);
    }

    @Test
    @Transactional
    public void createContenedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contenedorRepository.findAll().size();

        // Create the Contenedor with an existing ID
        contenedor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContenedorMockMvc.perform(post("/api/contenedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenedor)))
            .andExpect(status().isBadRequest());

        // Validate the Contenedor in the database
        List<Contenedor> contenedorList = contenedorRepository.findAll();
        assertThat(contenedorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkContenedorIsRequired() throws Exception {
        int databaseSizeBeforeTest = contenedorRepository.findAll().size();
        // set the field null
        contenedor.setContenedor(null);

        // Create the Contenedor, which fails.


        restContenedorMockMvc.perform(post("/api/contenedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenedor)))
            .andExpect(status().isBadRequest());

        List<Contenedor> contenedorList = contenedorRepository.findAll();
        assertThat(contenedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContenedors() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

        // Get all the contenedorList
        restContenedorMockMvc.perform(get("/api/contenedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contenedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].contenedor").value(hasItem(DEFAULT_CONTENEDOR)));
    }
    
    @Test
    @Transactional
    public void getContenedor() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

        // Get the contenedor
        restContenedorMockMvc.perform(get("/api/contenedors/{id}", contenedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contenedor.getId().intValue()))
            .andExpect(jsonPath("$.contenedor").value(DEFAULT_CONTENEDOR));
    }


    @Test
    @Transactional
    public void getContenedorsByIdFiltering() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

        Long id = contenedor.getId();

        defaultContenedorShouldBeFound("id.equals=" + id);
        defaultContenedorShouldNotBeFound("id.notEquals=" + id);

        defaultContenedorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContenedorShouldNotBeFound("id.greaterThan=" + id);

        defaultContenedorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContenedorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContenedorsByContenedorIsEqualToSomething() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

        // Get all the contenedorList where contenedor equals to DEFAULT_CONTENEDOR
        defaultContenedorShouldBeFound("contenedor.equals=" + DEFAULT_CONTENEDOR);

        // Get all the contenedorList where contenedor equals to UPDATED_CONTENEDOR
        defaultContenedorShouldNotBeFound("contenedor.equals=" + UPDATED_CONTENEDOR);
    }

    @Test
    @Transactional
    public void getAllContenedorsByContenedorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

        // Get all the contenedorList where contenedor not equals to DEFAULT_CONTENEDOR
        defaultContenedorShouldNotBeFound("contenedor.notEquals=" + DEFAULT_CONTENEDOR);

        // Get all the contenedorList where contenedor not equals to UPDATED_CONTENEDOR
        defaultContenedorShouldBeFound("contenedor.notEquals=" + UPDATED_CONTENEDOR);
    }

    @Test
    @Transactional
    public void getAllContenedorsByContenedorIsInShouldWork() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

        // Get all the contenedorList where contenedor in DEFAULT_CONTENEDOR or UPDATED_CONTENEDOR
        defaultContenedorShouldBeFound("contenedor.in=" + DEFAULT_CONTENEDOR + "," + UPDATED_CONTENEDOR);

        // Get all the contenedorList where contenedor equals to UPDATED_CONTENEDOR
        defaultContenedorShouldNotBeFound("contenedor.in=" + UPDATED_CONTENEDOR);
    }

    @Test
    @Transactional
    public void getAllContenedorsByContenedorIsNullOrNotNull() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

        // Get all the contenedorList where contenedor is not null
        defaultContenedorShouldBeFound("contenedor.specified=true");

        // Get all the contenedorList where contenedor is null
        defaultContenedorShouldNotBeFound("contenedor.specified=false");
    }
                @Test
    @Transactional
    public void getAllContenedorsByContenedorContainsSomething() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

        // Get all the contenedorList where contenedor contains DEFAULT_CONTENEDOR
        defaultContenedorShouldBeFound("contenedor.contains=" + DEFAULT_CONTENEDOR);

        // Get all the contenedorList where contenedor contains UPDATED_CONTENEDOR
        defaultContenedorShouldNotBeFound("contenedor.contains=" + UPDATED_CONTENEDOR);
    }

    @Test
    @Transactional
    public void getAllContenedorsByContenedorNotContainsSomething() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

        // Get all the contenedorList where contenedor does not contain DEFAULT_CONTENEDOR
        defaultContenedorShouldNotBeFound("contenedor.doesNotContain=" + DEFAULT_CONTENEDOR);

        // Get all the contenedorList where contenedor does not contain UPDATED_CONTENEDOR
        defaultContenedorShouldBeFound("contenedor.doesNotContain=" + UPDATED_CONTENEDOR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContenedorShouldBeFound(String filter) throws Exception {
        restContenedorMockMvc.perform(get("/api/contenedors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contenedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].contenedor").value(hasItem(DEFAULT_CONTENEDOR)));

        // Check, that the count call also returns 1
        restContenedorMockMvc.perform(get("/api/contenedors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContenedorShouldNotBeFound(String filter) throws Exception {
        restContenedorMockMvc.perform(get("/api/contenedors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContenedorMockMvc.perform(get("/api/contenedors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingContenedor() throws Exception {
        // Get the contenedor
        restContenedorMockMvc.perform(get("/api/contenedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContenedor() throws Exception {
        // Initialize the database
        contenedorService.save(contenedor);

        int databaseSizeBeforeUpdate = contenedorRepository.findAll().size();

        // Update the contenedor
        Contenedor updatedContenedor = contenedorRepository.findById(contenedor.getId()).get();
        // Disconnect from session so that the updates on updatedContenedor are not directly saved in db
        em.detach(updatedContenedor);
        updatedContenedor
            .contenedor(UPDATED_CONTENEDOR);

        restContenedorMockMvc.perform(put("/api/contenedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContenedor)))
            .andExpect(status().isOk());

        // Validate the Contenedor in the database
        List<Contenedor> contenedorList = contenedorRepository.findAll();
        assertThat(contenedorList).hasSize(databaseSizeBeforeUpdate);
        Contenedor testContenedor = contenedorList.get(contenedorList.size() - 1);
        assertThat(testContenedor.getContenedor()).isEqualTo(UPDATED_CONTENEDOR);
    }

    @Test
    @Transactional
    public void updateNonExistingContenedor() throws Exception {
        int databaseSizeBeforeUpdate = contenedorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContenedorMockMvc.perform(put("/api/contenedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenedor)))
            .andExpect(status().isBadRequest());

        // Validate the Contenedor in the database
        List<Contenedor> contenedorList = contenedorRepository.findAll();
        assertThat(contenedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContenedor() throws Exception {
        // Initialize the database
        contenedorService.save(contenedor);

        int databaseSizeBeforeDelete = contenedorRepository.findAll().size();

        // Delete the contenedor
        restContenedorMockMvc.perform(delete("/api/contenedors/{id}", contenedor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contenedor> contenedorList = contenedorRepository.findAll();
        assertThat(contenedorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
