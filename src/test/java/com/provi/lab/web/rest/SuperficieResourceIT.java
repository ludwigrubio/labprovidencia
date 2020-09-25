package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.Superficie;
import com.provi.lab.repository.SuperficieRepository;
import com.provi.lab.service.SuperficieService;
import com.provi.lab.service.dto.SuperficieCriteria;
import com.provi.lab.service.SuperficieQueryService;

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
 * Integration tests for the {@link SuperficieResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SuperficieResourceIT {

    private static final String DEFAULT_SUPERFICIE = "AAAAAAAAAA";
    private static final String UPDATED_SUPERFICIE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private SuperficieRepository superficieRepository;

    @Autowired
    private SuperficieService superficieService;

    @Autowired
    private SuperficieQueryService superficieQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSuperficieMockMvc;

    private Superficie superficie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Superficie createEntity(EntityManager em) {
        Superficie superficie = new Superficie()
            .superficie(DEFAULT_SUPERFICIE)
            .descripcion(DEFAULT_DESCRIPCION);
        return superficie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Superficie createUpdatedEntity(EntityManager em) {
        Superficie superficie = new Superficie()
            .superficie(UPDATED_SUPERFICIE)
            .descripcion(UPDATED_DESCRIPCION);
        return superficie;
    }

    @BeforeEach
    public void initTest() {
        superficie = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuperficie() throws Exception {
        int databaseSizeBeforeCreate = superficieRepository.findAll().size();
        // Create the Superficie
        restSuperficieMockMvc.perform(post("/api/superficies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(superficie)))
            .andExpect(status().isCreated());

        // Validate the Superficie in the database
        List<Superficie> superficieList = superficieRepository.findAll();
        assertThat(superficieList).hasSize(databaseSizeBeforeCreate + 1);
        Superficie testSuperficie = superficieList.get(superficieList.size() - 1);
        assertThat(testSuperficie.getSuperficie()).isEqualTo(DEFAULT_SUPERFICIE);
        assertThat(testSuperficie.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createSuperficieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = superficieRepository.findAll().size();

        // Create the Superficie with an existing ID
        superficie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuperficieMockMvc.perform(post("/api/superficies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(superficie)))
            .andExpect(status().isBadRequest());

        // Validate the Superficie in the database
        List<Superficie> superficieList = superficieRepository.findAll();
        assertThat(superficieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSuperficieIsRequired() throws Exception {
        int databaseSizeBeforeTest = superficieRepository.findAll().size();
        // set the field null
        superficie.setSuperficie(null);

        // Create the Superficie, which fails.


        restSuperficieMockMvc.perform(post("/api/superficies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(superficie)))
            .andExpect(status().isBadRequest());

        List<Superficie> superficieList = superficieRepository.findAll();
        assertThat(superficieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSuperficies() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList
        restSuperficieMockMvc.perform(get("/api/superficies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(superficie.getId().intValue())))
            .andExpect(jsonPath("$.[*].superficie").value(hasItem(DEFAULT_SUPERFICIE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getSuperficie() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get the superficie
        restSuperficieMockMvc.perform(get("/api/superficies/{id}", superficie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(superficie.getId().intValue()))
            .andExpect(jsonPath("$.superficie").value(DEFAULT_SUPERFICIE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }


    @Test
    @Transactional
    public void getSuperficiesByIdFiltering() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        Long id = superficie.getId();

        defaultSuperficieShouldBeFound("id.equals=" + id);
        defaultSuperficieShouldNotBeFound("id.notEquals=" + id);

        defaultSuperficieShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSuperficieShouldNotBeFound("id.greaterThan=" + id);

        defaultSuperficieShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSuperficieShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSuperficiesBySuperficieIsEqualToSomething() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where superficie equals to DEFAULT_SUPERFICIE
        defaultSuperficieShouldBeFound("superficie.equals=" + DEFAULT_SUPERFICIE);

        // Get all the superficieList where superficie equals to UPDATED_SUPERFICIE
        defaultSuperficieShouldNotBeFound("superficie.equals=" + UPDATED_SUPERFICIE);
    }

    @Test
    @Transactional
    public void getAllSuperficiesBySuperficieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where superficie not equals to DEFAULT_SUPERFICIE
        defaultSuperficieShouldNotBeFound("superficie.notEquals=" + DEFAULT_SUPERFICIE);

        // Get all the superficieList where superficie not equals to UPDATED_SUPERFICIE
        defaultSuperficieShouldBeFound("superficie.notEquals=" + UPDATED_SUPERFICIE);
    }

    @Test
    @Transactional
    public void getAllSuperficiesBySuperficieIsInShouldWork() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where superficie in DEFAULT_SUPERFICIE or UPDATED_SUPERFICIE
        defaultSuperficieShouldBeFound("superficie.in=" + DEFAULT_SUPERFICIE + "," + UPDATED_SUPERFICIE);

        // Get all the superficieList where superficie equals to UPDATED_SUPERFICIE
        defaultSuperficieShouldNotBeFound("superficie.in=" + UPDATED_SUPERFICIE);
    }

    @Test
    @Transactional
    public void getAllSuperficiesBySuperficieIsNullOrNotNull() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where superficie is not null
        defaultSuperficieShouldBeFound("superficie.specified=true");

        // Get all the superficieList where superficie is null
        defaultSuperficieShouldNotBeFound("superficie.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuperficiesBySuperficieContainsSomething() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where superficie contains DEFAULT_SUPERFICIE
        defaultSuperficieShouldBeFound("superficie.contains=" + DEFAULT_SUPERFICIE);

        // Get all the superficieList where superficie contains UPDATED_SUPERFICIE
        defaultSuperficieShouldNotBeFound("superficie.contains=" + UPDATED_SUPERFICIE);
    }

    @Test
    @Transactional
    public void getAllSuperficiesBySuperficieNotContainsSomething() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where superficie does not contain DEFAULT_SUPERFICIE
        defaultSuperficieShouldNotBeFound("superficie.doesNotContain=" + DEFAULT_SUPERFICIE);

        // Get all the superficieList where superficie does not contain UPDATED_SUPERFICIE
        defaultSuperficieShouldBeFound("superficie.doesNotContain=" + UPDATED_SUPERFICIE);
    }


    @Test
    @Transactional
    public void getAllSuperficiesByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where descripcion equals to DEFAULT_DESCRIPCION
        defaultSuperficieShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the superficieList where descripcion equals to UPDATED_DESCRIPCION
        defaultSuperficieShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllSuperficiesByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultSuperficieShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the superficieList where descripcion not equals to UPDATED_DESCRIPCION
        defaultSuperficieShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllSuperficiesByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultSuperficieShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the superficieList where descripcion equals to UPDATED_DESCRIPCION
        defaultSuperficieShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllSuperficiesByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where descripcion is not null
        defaultSuperficieShouldBeFound("descripcion.specified=true");

        // Get all the superficieList where descripcion is null
        defaultSuperficieShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuperficiesByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where descripcion contains DEFAULT_DESCRIPCION
        defaultSuperficieShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the superficieList where descripcion contains UPDATED_DESCRIPCION
        defaultSuperficieShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllSuperficiesByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficieList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultSuperficieShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the superficieList where descripcion does not contain UPDATED_DESCRIPCION
        defaultSuperficieShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSuperficieShouldBeFound(String filter) throws Exception {
        restSuperficieMockMvc.perform(get("/api/superficies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(superficie.getId().intValue())))
            .andExpect(jsonPath("$.[*].superficie").value(hasItem(DEFAULT_SUPERFICIE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restSuperficieMockMvc.perform(get("/api/superficies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSuperficieShouldNotBeFound(String filter) throws Exception {
        restSuperficieMockMvc.perform(get("/api/superficies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSuperficieMockMvc.perform(get("/api/superficies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSuperficie() throws Exception {
        // Get the superficie
        restSuperficieMockMvc.perform(get("/api/superficies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuperficie() throws Exception {
        // Initialize the database
        superficieService.save(superficie);

        int databaseSizeBeforeUpdate = superficieRepository.findAll().size();

        // Update the superficie
        Superficie updatedSuperficie = superficieRepository.findById(superficie.getId()).get();
        // Disconnect from session so that the updates on updatedSuperficie are not directly saved in db
        em.detach(updatedSuperficie);
        updatedSuperficie
            .superficie(UPDATED_SUPERFICIE)
            .descripcion(UPDATED_DESCRIPCION);

        restSuperficieMockMvc.perform(put("/api/superficies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSuperficie)))
            .andExpect(status().isOk());

        // Validate the Superficie in the database
        List<Superficie> superficieList = superficieRepository.findAll();
        assertThat(superficieList).hasSize(databaseSizeBeforeUpdate);
        Superficie testSuperficie = superficieList.get(superficieList.size() - 1);
        assertThat(testSuperficie.getSuperficie()).isEqualTo(UPDATED_SUPERFICIE);
        assertThat(testSuperficie.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingSuperficie() throws Exception {
        int databaseSizeBeforeUpdate = superficieRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuperficieMockMvc.perform(put("/api/superficies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(superficie)))
            .andExpect(status().isBadRequest());

        // Validate the Superficie in the database
        List<Superficie> superficieList = superficieRepository.findAll();
        assertThat(superficieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuperficie() throws Exception {
        // Initialize the database
        superficieService.save(superficie);

        int databaseSizeBeforeDelete = superficieRepository.findAll().size();

        // Delete the superficie
        restSuperficieMockMvc.perform(delete("/api/superficies/{id}", superficie.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Superficie> superficieList = superficieRepository.findAll();
        assertThat(superficieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
