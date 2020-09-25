package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.TipoProducto;
import com.provi.lab.repository.TipoProductoRepository;

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
 * Integration tests for the {@link TipoProductoResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoProductoResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoProductoMockMvc;

    private TipoProducto tipoProducto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProducto createEntity(EntityManager em) {
        TipoProducto tipoProducto = new TipoProducto()
            .tipo(DEFAULT_TIPO);
        return tipoProducto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProducto createUpdatedEntity(EntityManager em) {
        TipoProducto tipoProducto = new TipoProducto()
            .tipo(UPDATED_TIPO);
        return tipoProducto;
    }

    @BeforeEach
    public void initTest() {
        tipoProducto = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoProducto() throws Exception {
        int databaseSizeBeforeCreate = tipoProductoRepository.findAll().size();
        // Create the TipoProducto
        restTipoProductoMockMvc.perform(post("/api/tipo-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProducto)))
            .andExpect(status().isCreated());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoProducto testTipoProducto = tipoProductoList.get(tipoProductoList.size() - 1);
        assertThat(testTipoProducto.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createTipoProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoProductoRepository.findAll().size();

        // Create the TipoProducto with an existing ID
        tipoProducto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoProductoMockMvc.perform(post("/api/tipo-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProducto)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoProductoRepository.findAll().size();
        // set the field null
        tipoProducto.setTipo(null);

        // Create the TipoProducto, which fails.


        restTipoProductoMockMvc.perform(post("/api/tipo-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProducto)))
            .andExpect(status().isBadRequest());

        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoProductos() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        // Get all the tipoProductoList
        restTipoProductoMockMvc.perform(get("/api/tipo-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));
    }
    
    @Test
    @Transactional
    public void getTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        // Get the tipoProducto
        restTipoProductoMockMvc.perform(get("/api/tipo-productos/{id}", tipoProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoProducto.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO));
    }
    @Test
    @Transactional
    public void getNonExistingTipoProducto() throws Exception {
        // Get the tipoProducto
        restTipoProductoMockMvc.perform(get("/api/tipo-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();

        // Update the tipoProducto
        TipoProducto updatedTipoProducto = tipoProductoRepository.findById(tipoProducto.getId()).get();
        // Disconnect from session so that the updates on updatedTipoProducto are not directly saved in db
        em.detach(updatedTipoProducto);
        updatedTipoProducto
            .tipo(UPDATED_TIPO);

        restTipoProductoMockMvc.perform(put("/api/tipo-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoProducto)))
            .andExpect(status().isOk());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
        TipoProducto testTipoProducto = tipoProductoList.get(tipoProductoList.size() - 1);
        assertThat(testTipoProducto.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoProducto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoProductoMockMvc.perform(put("/api/tipo-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProducto)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        int databaseSizeBeforeDelete = tipoProductoRepository.findAll().size();

        // Delete the tipoProducto
        restTipoProductoMockMvc.perform(delete("/api/tipo-productos/{id}", tipoProducto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
