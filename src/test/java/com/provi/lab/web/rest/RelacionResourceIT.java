package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.Relacion;
import com.provi.lab.repository.RelacionRepository;

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
 * Integration tests for the {@link RelacionResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RelacionResourceIT {

    private static final String DEFAULT_RELACION = "AAAAAAAAAA";
    private static final String UPDATED_RELACION = "BBBBBBBBBB";

    @Autowired
    private RelacionRepository relacionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelacionMockMvc;

    private Relacion relacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relacion createEntity(EntityManager em) {
        Relacion relacion = new Relacion()
            .relacion(DEFAULT_RELACION);
        return relacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relacion createUpdatedEntity(EntityManager em) {
        Relacion relacion = new Relacion()
            .relacion(UPDATED_RELACION);
        return relacion;
    }

    @BeforeEach
    public void initTest() {
        relacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelacion() throws Exception {
        int databaseSizeBeforeCreate = relacionRepository.findAll().size();
        // Create the Relacion
        restRelacionMockMvc.perform(post("/api/relacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relacion)))
            .andExpect(status().isCreated());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeCreate + 1);
        Relacion testRelacion = relacionList.get(relacionList.size() - 1);
        assertThat(testRelacion.getRelacion()).isEqualTo(DEFAULT_RELACION);
    }

    @Test
    @Transactional
    public void createRelacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relacionRepository.findAll().size();

        // Create the Relacion with an existing ID
        relacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelacionMockMvc.perform(post("/api/relacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relacion)))
            .andExpect(status().isBadRequest());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRelacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = relacionRepository.findAll().size();
        // set the field null
        relacion.setRelacion(null);

        // Create the Relacion, which fails.


        restRelacionMockMvc.perform(post("/api/relacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relacion)))
            .andExpect(status().isBadRequest());

        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRelacions() throws Exception {
        // Initialize the database
        relacionRepository.saveAndFlush(relacion);

        // Get all the relacionList
        restRelacionMockMvc.perform(get("/api/relacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].relacion").value(hasItem(DEFAULT_RELACION)));
    }
    
    @Test
    @Transactional
    public void getRelacion() throws Exception {
        // Initialize the database
        relacionRepository.saveAndFlush(relacion);

        // Get the relacion
        restRelacionMockMvc.perform(get("/api/relacions/{id}", relacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relacion.getId().intValue()))
            .andExpect(jsonPath("$.relacion").value(DEFAULT_RELACION));
    }
    @Test
    @Transactional
    public void getNonExistingRelacion() throws Exception {
        // Get the relacion
        restRelacionMockMvc.perform(get("/api/relacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelacion() throws Exception {
        // Initialize the database
        relacionRepository.saveAndFlush(relacion);

        int databaseSizeBeforeUpdate = relacionRepository.findAll().size();

        // Update the relacion
        Relacion updatedRelacion = relacionRepository.findById(relacion.getId()).get();
        // Disconnect from session so that the updates on updatedRelacion are not directly saved in db
        em.detach(updatedRelacion);
        updatedRelacion
            .relacion(UPDATED_RELACION);

        restRelacionMockMvc.perform(put("/api/relacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRelacion)))
            .andExpect(status().isOk());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeUpdate);
        Relacion testRelacion = relacionList.get(relacionList.size() - 1);
        assertThat(testRelacion.getRelacion()).isEqualTo(UPDATED_RELACION);
    }

    @Test
    @Transactional
    public void updateNonExistingRelacion() throws Exception {
        int databaseSizeBeforeUpdate = relacionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelacionMockMvc.perform(put("/api/relacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relacion)))
            .andExpect(status().isBadRequest());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRelacion() throws Exception {
        // Initialize the database
        relacionRepository.saveAndFlush(relacion);

        int databaseSizeBeforeDelete = relacionRepository.findAll().size();

        // Delete the relacion
        restRelacionMockMvc.perform(delete("/api/relacions/{id}", relacion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
