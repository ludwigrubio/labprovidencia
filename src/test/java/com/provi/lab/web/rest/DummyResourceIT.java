package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.Dummy;
import com.provi.lab.domain.Area;
import com.provi.lab.repository.DummyRepository;
import com.provi.lab.service.DummyService;
import com.provi.lab.service.dto.DummyCriteria;
import com.provi.lab.service.DummyQueryService;

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
 * Integration tests for the {@link DummyResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DummyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DummyRepository dummyRepository;

    @Autowired
    private DummyService dummyService;

    @Autowired
    private DummyQueryService dummyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDummyMockMvc;

    private Dummy dummy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dummy createEntity(EntityManager em) {
        Dummy dummy = new Dummy()
            .name(DEFAULT_NAME);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        dummy.setArea(area);
        return dummy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dummy createUpdatedEntity(EntityManager em) {
        Dummy dummy = new Dummy()
            .name(UPDATED_NAME);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createUpdatedEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        dummy.setArea(area);
        return dummy;
    }

    @BeforeEach
    public void initTest() {
        dummy = createEntity(em);
    }

    @Test
    @Transactional
    public void createDummy() throws Exception {
        int databaseSizeBeforeCreate = dummyRepository.findAll().size();
        // Create the Dummy
        restDummyMockMvc.perform(post("/api/dummies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dummy)))
            .andExpect(status().isCreated());

        // Validate the Dummy in the database
        List<Dummy> dummyList = dummyRepository.findAll();
        assertThat(dummyList).hasSize(databaseSizeBeforeCreate + 1);
        Dummy testDummy = dummyList.get(dummyList.size() - 1);
        assertThat(testDummy.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDummyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dummyRepository.findAll().size();

        // Create the Dummy with an existing ID
        dummy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDummyMockMvc.perform(post("/api/dummies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dummy)))
            .andExpect(status().isBadRequest());

        // Validate the Dummy in the database
        List<Dummy> dummyList = dummyRepository.findAll();
        assertThat(dummyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dummyRepository.findAll().size();
        // set the field null
        dummy.setName(null);

        // Create the Dummy, which fails.


        restDummyMockMvc.perform(post("/api/dummies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dummy)))
            .andExpect(status().isBadRequest());

        List<Dummy> dummyList = dummyRepository.findAll();
        assertThat(dummyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDummies() throws Exception {
        // Initialize the database
        dummyRepository.saveAndFlush(dummy);

        // Get all the dummyList
        restDummyMockMvc.perform(get("/api/dummies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDummy() throws Exception {
        // Initialize the database
        dummyRepository.saveAndFlush(dummy);

        // Get the dummy
        restDummyMockMvc.perform(get("/api/dummies/{id}", dummy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dummy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getDummiesByIdFiltering() throws Exception {
        // Initialize the database
        dummyRepository.saveAndFlush(dummy);

        Long id = dummy.getId();

        defaultDummyShouldBeFound("id.equals=" + id);
        defaultDummyShouldNotBeFound("id.notEquals=" + id);

        defaultDummyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDummyShouldNotBeFound("id.greaterThan=" + id);

        defaultDummyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDummyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDummiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dummyRepository.saveAndFlush(dummy);

        // Get all the dummyList where name equals to DEFAULT_NAME
        defaultDummyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the dummyList where name equals to UPDATED_NAME
        defaultDummyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDummiesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dummyRepository.saveAndFlush(dummy);

        // Get all the dummyList where name not equals to DEFAULT_NAME
        defaultDummyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the dummyList where name not equals to UPDATED_NAME
        defaultDummyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDummiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        dummyRepository.saveAndFlush(dummy);

        // Get all the dummyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDummyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the dummyList where name equals to UPDATED_NAME
        defaultDummyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDummiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dummyRepository.saveAndFlush(dummy);

        // Get all the dummyList where name is not null
        defaultDummyShouldBeFound("name.specified=true");

        // Get all the dummyList where name is null
        defaultDummyShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDummiesByNameContainsSomething() throws Exception {
        // Initialize the database
        dummyRepository.saveAndFlush(dummy);

        // Get all the dummyList where name contains DEFAULT_NAME
        defaultDummyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the dummyList where name contains UPDATED_NAME
        defaultDummyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDummiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        dummyRepository.saveAndFlush(dummy);

        // Get all the dummyList where name does not contain DEFAULT_NAME
        defaultDummyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the dummyList where name does not contain UPDATED_NAME
        defaultDummyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDummiesByAreaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Area area = dummy.getArea();
        dummyRepository.saveAndFlush(dummy);
        Long areaId = area.getId();

        // Get all the dummyList where area equals to areaId
        defaultDummyShouldBeFound("areaId.equals=" + areaId);

        // Get all the dummyList where area equals to areaId + 1
        defaultDummyShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDummyShouldBeFound(String filter) throws Exception {
        restDummyMockMvc.perform(get("/api/dummies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restDummyMockMvc.perform(get("/api/dummies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDummyShouldNotBeFound(String filter) throws Exception {
        restDummyMockMvc.perform(get("/api/dummies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDummyMockMvc.perform(get("/api/dummies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDummy() throws Exception {
        // Get the dummy
        restDummyMockMvc.perform(get("/api/dummies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDummy() throws Exception {
        // Initialize the database
        dummyService.save(dummy);

        int databaseSizeBeforeUpdate = dummyRepository.findAll().size();

        // Update the dummy
        Dummy updatedDummy = dummyRepository.findById(dummy.getId()).get();
        // Disconnect from session so that the updates on updatedDummy are not directly saved in db
        em.detach(updatedDummy);
        updatedDummy
            .name(UPDATED_NAME);

        restDummyMockMvc.perform(put("/api/dummies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDummy)))
            .andExpect(status().isOk());

        // Validate the Dummy in the database
        List<Dummy> dummyList = dummyRepository.findAll();
        assertThat(dummyList).hasSize(databaseSizeBeforeUpdate);
        Dummy testDummy = dummyList.get(dummyList.size() - 1);
        assertThat(testDummy.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDummy() throws Exception {
        int databaseSizeBeforeUpdate = dummyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDummyMockMvc.perform(put("/api/dummies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dummy)))
            .andExpect(status().isBadRequest());

        // Validate the Dummy in the database
        List<Dummy> dummyList = dummyRepository.findAll();
        assertThat(dummyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDummy() throws Exception {
        // Initialize the database
        dummyService.save(dummy);

        int databaseSizeBeforeDelete = dummyRepository.findAll().size();

        // Delete the dummy
        restDummyMockMvc.perform(delete("/api/dummies/{id}", dummy.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dummy> dummyList = dummyRepository.findAll();
        assertThat(dummyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
