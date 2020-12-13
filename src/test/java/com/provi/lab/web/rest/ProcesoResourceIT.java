package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.Proceso;
import com.provi.lab.repository.ProcesoRepository;
import com.provi.lab.service.ProcesoService;
import com.provi.lab.service.dto.ProcesoCriteria;
import com.provi.lab.service.ProcesoQueryService;

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
 * Integration tests for the {@link ProcesoResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProcesoResourceIT {

    private static final String DEFAULT_PROCESO = "AAAAAAAAAA";
    private static final String UPDATED_PROCESO = "BBBBBBBBBB";

    @Autowired
    private ProcesoRepository procesoRepository;

    @Autowired
    private ProcesoService procesoService;

    @Autowired
    private ProcesoQueryService procesoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcesoMockMvc;

    private Proceso proceso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proceso createEntity(EntityManager em) {
        Proceso proceso = new Proceso()
            .proceso(DEFAULT_PROCESO);
        return proceso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proceso createUpdatedEntity(EntityManager em) {
        Proceso proceso = new Proceso()
            .proceso(UPDATED_PROCESO);
        return proceso;
    }

    @BeforeEach
    public void initTest() {
        proceso = createEntity(em);
    }

    @Test
    @Transactional
    public void createProceso() throws Exception {
        int databaseSizeBeforeCreate = procesoRepository.findAll().size();
        // Create the Proceso
        restProcesoMockMvc.perform(post("/api/procesos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proceso)))
            .andExpect(status().isCreated());

        // Validate the Proceso in the database
        List<Proceso> procesoList = procesoRepository.findAll();
        assertThat(procesoList).hasSize(databaseSizeBeforeCreate + 1);
        Proceso testProceso = procesoList.get(procesoList.size() - 1);
        assertThat(testProceso.getProceso()).isEqualTo(DEFAULT_PROCESO);
    }

    @Test
    @Transactional
    public void createProcesoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procesoRepository.findAll().size();

        // Create the Proceso with an existing ID
        proceso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcesoMockMvc.perform(post("/api/procesos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proceso)))
            .andExpect(status().isBadRequest());

        // Validate the Proceso in the database
        List<Proceso> procesoList = procesoRepository.findAll();
        assertThat(procesoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProcesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = procesoRepository.findAll().size();
        // set the field null
        proceso.setProceso(null);

        // Create the Proceso, which fails.


        restProcesoMockMvc.perform(post("/api/procesos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proceso)))
            .andExpect(status().isBadRequest());

        List<Proceso> procesoList = procesoRepository.findAll();
        assertThat(procesoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcesos() throws Exception {
        // Initialize the database
        procesoRepository.saveAndFlush(proceso);

        // Get all the procesoList
        restProcesoMockMvc.perform(get("/api/procesos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proceso.getId().intValue())))
            .andExpect(jsonPath("$.[*].proceso").value(hasItem(DEFAULT_PROCESO)));
    }
    
    @Test
    @Transactional
    public void getProceso() throws Exception {
        // Initialize the database
        procesoRepository.saveAndFlush(proceso);

        // Get the proceso
        restProcesoMockMvc.perform(get("/api/procesos/{id}", proceso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proceso.getId().intValue()))
            .andExpect(jsonPath("$.proceso").value(DEFAULT_PROCESO));
    }


    @Test
    @Transactional
    public void getProcesosByIdFiltering() throws Exception {
        // Initialize the database
        procesoRepository.saveAndFlush(proceso);

        Long id = proceso.getId();

        defaultProcesoShouldBeFound("id.equals=" + id);
        defaultProcesoShouldNotBeFound("id.notEquals=" + id);

        defaultProcesoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcesoShouldNotBeFound("id.greaterThan=" + id);

        defaultProcesoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcesoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProcesosByProcesoIsEqualToSomething() throws Exception {
        // Initialize the database
        procesoRepository.saveAndFlush(proceso);

        // Get all the procesoList where proceso equals to DEFAULT_PROCESO
        defaultProcesoShouldBeFound("proceso.equals=" + DEFAULT_PROCESO);

        // Get all the procesoList where proceso equals to UPDATED_PROCESO
        defaultProcesoShouldNotBeFound("proceso.equals=" + UPDATED_PROCESO);
    }

    @Test
    @Transactional
    public void getAllProcesosByProcesoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        procesoRepository.saveAndFlush(proceso);

        // Get all the procesoList where proceso not equals to DEFAULT_PROCESO
        defaultProcesoShouldNotBeFound("proceso.notEquals=" + DEFAULT_PROCESO);

        // Get all the procesoList where proceso not equals to UPDATED_PROCESO
        defaultProcesoShouldBeFound("proceso.notEquals=" + UPDATED_PROCESO);
    }

    @Test
    @Transactional
    public void getAllProcesosByProcesoIsInShouldWork() throws Exception {
        // Initialize the database
        procesoRepository.saveAndFlush(proceso);

        // Get all the procesoList where proceso in DEFAULT_PROCESO or UPDATED_PROCESO
        defaultProcesoShouldBeFound("proceso.in=" + DEFAULT_PROCESO + "," + UPDATED_PROCESO);

        // Get all the procesoList where proceso equals to UPDATED_PROCESO
        defaultProcesoShouldNotBeFound("proceso.in=" + UPDATED_PROCESO);
    }

    @Test
    @Transactional
    public void getAllProcesosByProcesoIsNullOrNotNull() throws Exception {
        // Initialize the database
        procesoRepository.saveAndFlush(proceso);

        // Get all the procesoList where proceso is not null
        defaultProcesoShouldBeFound("proceso.specified=true");

        // Get all the procesoList where proceso is null
        defaultProcesoShouldNotBeFound("proceso.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcesosByProcesoContainsSomething() throws Exception {
        // Initialize the database
        procesoRepository.saveAndFlush(proceso);

        // Get all the procesoList where proceso contains DEFAULT_PROCESO
        defaultProcesoShouldBeFound("proceso.contains=" + DEFAULT_PROCESO);

        // Get all the procesoList where proceso contains UPDATED_PROCESO
        defaultProcesoShouldNotBeFound("proceso.contains=" + UPDATED_PROCESO);
    }

    @Test
    @Transactional
    public void getAllProcesosByProcesoNotContainsSomething() throws Exception {
        // Initialize the database
        procesoRepository.saveAndFlush(proceso);

        // Get all the procesoList where proceso does not contain DEFAULT_PROCESO
        defaultProcesoShouldNotBeFound("proceso.doesNotContain=" + DEFAULT_PROCESO);

        // Get all the procesoList where proceso does not contain UPDATED_PROCESO
        defaultProcesoShouldBeFound("proceso.doesNotContain=" + UPDATED_PROCESO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcesoShouldBeFound(String filter) throws Exception {
        restProcesoMockMvc.perform(get("/api/procesos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proceso.getId().intValue())))
            .andExpect(jsonPath("$.[*].proceso").value(hasItem(DEFAULT_PROCESO)));

        // Check, that the count call also returns 1
        restProcesoMockMvc.perform(get("/api/procesos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcesoShouldNotBeFound(String filter) throws Exception {
        restProcesoMockMvc.perform(get("/api/procesos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcesoMockMvc.perform(get("/api/procesos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProceso() throws Exception {
        // Get the proceso
        restProcesoMockMvc.perform(get("/api/procesos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProceso() throws Exception {
        // Initialize the database
        procesoService.save(proceso);

        int databaseSizeBeforeUpdate = procesoRepository.findAll().size();

        // Update the proceso
        Proceso updatedProceso = procesoRepository.findById(proceso.getId()).get();
        // Disconnect from session so that the updates on updatedProceso are not directly saved in db
        em.detach(updatedProceso);
        updatedProceso
            .proceso(UPDATED_PROCESO);

        restProcesoMockMvc.perform(put("/api/procesos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProceso)))
            .andExpect(status().isOk());

        // Validate the Proceso in the database
        List<Proceso> procesoList = procesoRepository.findAll();
        assertThat(procesoList).hasSize(databaseSizeBeforeUpdate);
        Proceso testProceso = procesoList.get(procesoList.size() - 1);
        assertThat(testProceso.getProceso()).isEqualTo(UPDATED_PROCESO);
    }

    @Test
    @Transactional
    public void updateNonExistingProceso() throws Exception {
        int databaseSizeBeforeUpdate = procesoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcesoMockMvc.perform(put("/api/procesos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proceso)))
            .andExpect(status().isBadRequest());

        // Validate the Proceso in the database
        List<Proceso> procesoList = procesoRepository.findAll();
        assertThat(procesoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProceso() throws Exception {
        // Initialize the database
        procesoService.save(proceso);

        int databaseSizeBeforeDelete = procesoRepository.findAll().size();

        // Delete the proceso
        restProcesoMockMvc.perform(delete("/api/procesos/{id}", proceso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proceso> procesoList = procesoRepository.findAll();
        assertThat(procesoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
