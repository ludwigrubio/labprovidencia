package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.Cultivo;
import com.provi.lab.repository.CultivoRepository;
import com.provi.lab.service.CultivoService;
import com.provi.lab.service.dto.CultivoCriteria;
import com.provi.lab.service.CultivoQueryService;

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
 * Integration tests for the {@link CultivoResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CultivoResourceIT {

    private static final String DEFAULT_CULTIVO = "AAAAAAAAAA";
    private static final String UPDATED_CULTIVO = "BBBBBBBBBB";

    @Autowired
    private CultivoRepository cultivoRepository;

    @Autowired
    private CultivoService cultivoService;

    @Autowired
    private CultivoQueryService cultivoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCultivoMockMvc;

    private Cultivo cultivo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cultivo createEntity(EntityManager em) {
        Cultivo cultivo = new Cultivo()
            .cultivo(DEFAULT_CULTIVO);
        return cultivo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cultivo createUpdatedEntity(EntityManager em) {
        Cultivo cultivo = new Cultivo()
            .cultivo(UPDATED_CULTIVO);
        return cultivo;
    }

    @BeforeEach
    public void initTest() {
        cultivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCultivo() throws Exception {
        int databaseSizeBeforeCreate = cultivoRepository.findAll().size();
        // Create the Cultivo
        restCultivoMockMvc.perform(post("/api/cultivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cultivo)))
            .andExpect(status().isCreated());

        // Validate the Cultivo in the database
        List<Cultivo> cultivoList = cultivoRepository.findAll();
        assertThat(cultivoList).hasSize(databaseSizeBeforeCreate + 1);
        Cultivo testCultivo = cultivoList.get(cultivoList.size() - 1);
        assertThat(testCultivo.getCultivo()).isEqualTo(DEFAULT_CULTIVO);
    }

    @Test
    @Transactional
    public void createCultivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cultivoRepository.findAll().size();

        // Create the Cultivo with an existing ID
        cultivo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCultivoMockMvc.perform(post("/api/cultivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cultivo)))
            .andExpect(status().isBadRequest());

        // Validate the Cultivo in the database
        List<Cultivo> cultivoList = cultivoRepository.findAll();
        assertThat(cultivoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCultivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cultivoRepository.findAll().size();
        // set the field null
        cultivo.setCultivo(null);

        // Create the Cultivo, which fails.


        restCultivoMockMvc.perform(post("/api/cultivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cultivo)))
            .andExpect(status().isBadRequest());

        List<Cultivo> cultivoList = cultivoRepository.findAll();
        assertThat(cultivoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCultivos() throws Exception {
        // Initialize the database
        cultivoRepository.saveAndFlush(cultivo);

        // Get all the cultivoList
        restCultivoMockMvc.perform(get("/api/cultivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cultivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cultivo").value(hasItem(DEFAULT_CULTIVO)));
    }
    
    @Test
    @Transactional
    public void getCultivo() throws Exception {
        // Initialize the database
        cultivoRepository.saveAndFlush(cultivo);

        // Get the cultivo
        restCultivoMockMvc.perform(get("/api/cultivos/{id}", cultivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cultivo.getId().intValue()))
            .andExpect(jsonPath("$.cultivo").value(DEFAULT_CULTIVO));
    }


    @Test
    @Transactional
    public void getCultivosByIdFiltering() throws Exception {
        // Initialize the database
        cultivoRepository.saveAndFlush(cultivo);

        Long id = cultivo.getId();

        defaultCultivoShouldBeFound("id.equals=" + id);
        defaultCultivoShouldNotBeFound("id.notEquals=" + id);

        defaultCultivoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCultivoShouldNotBeFound("id.greaterThan=" + id);

        defaultCultivoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCultivoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCultivosByCultivoIsEqualToSomething() throws Exception {
        // Initialize the database
        cultivoRepository.saveAndFlush(cultivo);

        // Get all the cultivoList where cultivo equals to DEFAULT_CULTIVO
        defaultCultivoShouldBeFound("cultivo.equals=" + DEFAULT_CULTIVO);

        // Get all the cultivoList where cultivo equals to UPDATED_CULTIVO
        defaultCultivoShouldNotBeFound("cultivo.equals=" + UPDATED_CULTIVO);
    }

    @Test
    @Transactional
    public void getAllCultivosByCultivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cultivoRepository.saveAndFlush(cultivo);

        // Get all the cultivoList where cultivo not equals to DEFAULT_CULTIVO
        defaultCultivoShouldNotBeFound("cultivo.notEquals=" + DEFAULT_CULTIVO);

        // Get all the cultivoList where cultivo not equals to UPDATED_CULTIVO
        defaultCultivoShouldBeFound("cultivo.notEquals=" + UPDATED_CULTIVO);
    }

    @Test
    @Transactional
    public void getAllCultivosByCultivoIsInShouldWork() throws Exception {
        // Initialize the database
        cultivoRepository.saveAndFlush(cultivo);

        // Get all the cultivoList where cultivo in DEFAULT_CULTIVO or UPDATED_CULTIVO
        defaultCultivoShouldBeFound("cultivo.in=" + DEFAULT_CULTIVO + "," + UPDATED_CULTIVO);

        // Get all the cultivoList where cultivo equals to UPDATED_CULTIVO
        defaultCultivoShouldNotBeFound("cultivo.in=" + UPDATED_CULTIVO);
    }

    @Test
    @Transactional
    public void getAllCultivosByCultivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cultivoRepository.saveAndFlush(cultivo);

        // Get all the cultivoList where cultivo is not null
        defaultCultivoShouldBeFound("cultivo.specified=true");

        // Get all the cultivoList where cultivo is null
        defaultCultivoShouldNotBeFound("cultivo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCultivosByCultivoContainsSomething() throws Exception {
        // Initialize the database
        cultivoRepository.saveAndFlush(cultivo);

        // Get all the cultivoList where cultivo contains DEFAULT_CULTIVO
        defaultCultivoShouldBeFound("cultivo.contains=" + DEFAULT_CULTIVO);

        // Get all the cultivoList where cultivo contains UPDATED_CULTIVO
        defaultCultivoShouldNotBeFound("cultivo.contains=" + UPDATED_CULTIVO);
    }

    @Test
    @Transactional
    public void getAllCultivosByCultivoNotContainsSomething() throws Exception {
        // Initialize the database
        cultivoRepository.saveAndFlush(cultivo);

        // Get all the cultivoList where cultivo does not contain DEFAULT_CULTIVO
        defaultCultivoShouldNotBeFound("cultivo.doesNotContain=" + DEFAULT_CULTIVO);

        // Get all the cultivoList where cultivo does not contain UPDATED_CULTIVO
        defaultCultivoShouldBeFound("cultivo.doesNotContain=" + UPDATED_CULTIVO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCultivoShouldBeFound(String filter) throws Exception {
        restCultivoMockMvc.perform(get("/api/cultivos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cultivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cultivo").value(hasItem(DEFAULT_CULTIVO)));

        // Check, that the count call also returns 1
        restCultivoMockMvc.perform(get("/api/cultivos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCultivoShouldNotBeFound(String filter) throws Exception {
        restCultivoMockMvc.perform(get("/api/cultivos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCultivoMockMvc.perform(get("/api/cultivos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCultivo() throws Exception {
        // Get the cultivo
        restCultivoMockMvc.perform(get("/api/cultivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCultivo() throws Exception {
        // Initialize the database
        cultivoService.save(cultivo);

        int databaseSizeBeforeUpdate = cultivoRepository.findAll().size();

        // Update the cultivo
        Cultivo updatedCultivo = cultivoRepository.findById(cultivo.getId()).get();
        // Disconnect from session so that the updates on updatedCultivo are not directly saved in db
        em.detach(updatedCultivo);
        updatedCultivo
            .cultivo(UPDATED_CULTIVO);

        restCultivoMockMvc.perform(put("/api/cultivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCultivo)))
            .andExpect(status().isOk());

        // Validate the Cultivo in the database
        List<Cultivo> cultivoList = cultivoRepository.findAll();
        assertThat(cultivoList).hasSize(databaseSizeBeforeUpdate);
        Cultivo testCultivo = cultivoList.get(cultivoList.size() - 1);
        assertThat(testCultivo.getCultivo()).isEqualTo(UPDATED_CULTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingCultivo() throws Exception {
        int databaseSizeBeforeUpdate = cultivoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCultivoMockMvc.perform(put("/api/cultivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cultivo)))
            .andExpect(status().isBadRequest());

        // Validate the Cultivo in the database
        List<Cultivo> cultivoList = cultivoRepository.findAll();
        assertThat(cultivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCultivo() throws Exception {
        // Initialize the database
        cultivoService.save(cultivo);

        int databaseSizeBeforeDelete = cultivoRepository.findAll().size();

        // Delete the cultivo
        restCultivoMockMvc.perform(delete("/api/cultivos/{id}", cultivo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cultivo> cultivoList = cultivoRepository.findAll();
        assertThat(cultivoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
