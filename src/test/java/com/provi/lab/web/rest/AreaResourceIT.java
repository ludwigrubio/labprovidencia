package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.Area;
import com.provi.lab.repository.AreaRepository;
import com.provi.lab.service.AreaService;
import com.provi.lab.service.dto.AreaCriteria;
import com.provi.lab.service.AreaQueryService;

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
 * Integration tests for the {@link AreaResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AreaResourceIT {

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaQueryService areaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaMockMvc;

    private Area area;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Area createEntity(EntityManager em) {
        Area area = new Area()
            .area(DEFAULT_AREA)
            .descripcion(DEFAULT_DESCRIPCION);
        return area;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Area createUpdatedEntity(EntityManager em) {
        Area area = new Area()
            .area(UPDATED_AREA)
            .descripcion(UPDATED_DESCRIPCION);
        return area;
    }

    @BeforeEach
    public void initTest() {
        area = createEntity(em);
    }

    @Test
    @Transactional
    public void createArea() throws Exception {
        int databaseSizeBeforeCreate = areaRepository.findAll().size();
        // Create the Area
        restAreaMockMvc.perform(post("/api/areas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(area)))
            .andExpect(status().isCreated());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeCreate + 1);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testArea.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createAreaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = areaRepository.findAll().size();

        // Create the Area with an existing ID
        area.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaMockMvc.perform(post("/api/areas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(area)))
            .andExpect(status().isBadRequest());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaRepository.findAll().size();
        // set the field null
        area.setArea(null);

        // Create the Area, which fails.


        restAreaMockMvc.perform(post("/api/areas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(area)))
            .andExpect(status().isBadRequest());

        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAreas() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList
        restAreaMockMvc.perform(get("/api/areas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(area.getId().intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get the area
        restAreaMockMvc.perform(get("/api/areas/{id}", area.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(area.getId().intValue()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }


    @Test
    @Transactional
    public void getAreasByIdFiltering() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        Long id = area.getId();

        defaultAreaShouldBeFound("id.equals=" + id);
        defaultAreaShouldNotBeFound("id.notEquals=" + id);

        defaultAreaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAreaShouldNotBeFound("id.greaterThan=" + id);

        defaultAreaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAreaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAreasByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where area equals to DEFAULT_AREA
        defaultAreaShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the areaList where area equals to UPDATED_AREA
        defaultAreaShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllAreasByAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where area not equals to DEFAULT_AREA
        defaultAreaShouldNotBeFound("area.notEquals=" + DEFAULT_AREA);

        // Get all the areaList where area not equals to UPDATED_AREA
        defaultAreaShouldBeFound("area.notEquals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllAreasByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where area in DEFAULT_AREA or UPDATED_AREA
        defaultAreaShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the areaList where area equals to UPDATED_AREA
        defaultAreaShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllAreasByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where area is not null
        defaultAreaShouldBeFound("area.specified=true");

        // Get all the areaList where area is null
        defaultAreaShouldNotBeFound("area.specified=false");
    }
                @Test
    @Transactional
    public void getAllAreasByAreaContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where area contains DEFAULT_AREA
        defaultAreaShouldBeFound("area.contains=" + DEFAULT_AREA);

        // Get all the areaList where area contains UPDATED_AREA
        defaultAreaShouldNotBeFound("area.contains=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllAreasByAreaNotContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where area does not contain DEFAULT_AREA
        defaultAreaShouldNotBeFound("area.doesNotContain=" + DEFAULT_AREA);

        // Get all the areaList where area does not contain UPDATED_AREA
        defaultAreaShouldBeFound("area.doesNotContain=" + UPDATED_AREA);
    }


    @Test
    @Transactional
    public void getAllAreasByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where descripcion equals to DEFAULT_DESCRIPCION
        defaultAreaShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the areaList where descripcion equals to UPDATED_DESCRIPCION
        defaultAreaShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllAreasByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultAreaShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the areaList where descripcion not equals to UPDATED_DESCRIPCION
        defaultAreaShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllAreasByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultAreaShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the areaList where descripcion equals to UPDATED_DESCRIPCION
        defaultAreaShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllAreasByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where descripcion is not null
        defaultAreaShouldBeFound("descripcion.specified=true");

        // Get all the areaList where descripcion is null
        defaultAreaShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllAreasByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where descripcion contains DEFAULT_DESCRIPCION
        defaultAreaShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the areaList where descripcion contains UPDATED_DESCRIPCION
        defaultAreaShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllAreasByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultAreaShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the areaList where descripcion does not contain UPDATED_DESCRIPCION
        defaultAreaShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAreaShouldBeFound(String filter) throws Exception {
        restAreaMockMvc.perform(get("/api/areas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(area.getId().intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restAreaMockMvc.perform(get("/api/areas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAreaShouldNotBeFound(String filter) throws Exception {
        restAreaMockMvc.perform(get("/api/areas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAreaMockMvc.perform(get("/api/areas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingArea() throws Exception {
        // Get the area
        restAreaMockMvc.perform(get("/api/areas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArea() throws Exception {
        // Initialize the database
        areaService.save(area);

        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // Update the area
        Area updatedArea = areaRepository.findById(area.getId()).get();
        // Disconnect from session so that the updates on updatedArea are not directly saved in db
        em.detach(updatedArea);
        updatedArea
            .area(UPDATED_AREA)
            .descripcion(UPDATED_DESCRIPCION);

        restAreaMockMvc.perform(put("/api/areas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArea)))
            .andExpect(status().isOk());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testArea.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingArea() throws Exception {
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaMockMvc.perform(put("/api/areas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(area)))
            .andExpect(status().isBadRequest());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArea() throws Exception {
        // Initialize the database
        areaService.save(area);

        int databaseSizeBeforeDelete = areaRepository.findAll().size();

        // Delete the area
        restAreaMockMvc.perform(delete("/api/areas/{id}", area.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
