package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.Producto;
import com.provi.lab.domain.TipoProducto;
import com.provi.lab.repository.ProductoRepository;
import com.provi.lab.service.ProductoService;
import com.provi.lab.service.dto.ProductoCriteria;
import com.provi.lab.service.ProductoQueryService;

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
 * Integration tests for the {@link ProductoResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductoResourceIT {

    private static final String DEFAULT_PRODUCTO = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTO = "BBBBBBBBBB";

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_SERIE_EAN = "AAAAAAAAAA";
    private static final String UPDATED_SERIE_EAN = "BBBBBBBBBB";

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoQueryService productoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoMockMvc;

    private Producto producto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createEntity(EntityManager em) {
        Producto producto = new Producto()
            .producto(DEFAULT_PRODUCTO)
            .clave(DEFAULT_CLAVE)
            .serieEAN(DEFAULT_SERIE_EAN);
        // Add required entity
        TipoProducto tipoProducto;
        if (TestUtil.findAll(em, TipoProducto.class).isEmpty()) {
            tipoProducto = TipoProductoResourceIT.createEntity(em);
            em.persist(tipoProducto);
            em.flush();
        } else {
            tipoProducto = TestUtil.findAll(em, TipoProducto.class).get(0);
        }
        producto.setTipo(tipoProducto);
        return producto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createUpdatedEntity(EntityManager em) {
        Producto producto = new Producto()
            .producto(UPDATED_PRODUCTO)
            .clave(UPDATED_CLAVE)
            .serieEAN(UPDATED_SERIE_EAN);
        // Add required entity
        TipoProducto tipoProducto;
        if (TestUtil.findAll(em, TipoProducto.class).isEmpty()) {
            tipoProducto = TipoProductoResourceIT.createUpdatedEntity(em);
            em.persist(tipoProducto);
            em.flush();
        } else {
            tipoProducto = TestUtil.findAll(em, TipoProducto.class).get(0);
        }
        producto.setTipo(tipoProducto);
        return producto;
    }

    @BeforeEach
    public void initTest() {
        producto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();
        // Create the Producto
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getProducto()).isEqualTo(DEFAULT_PRODUCTO);
        assertThat(testProducto.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testProducto.getSerieEAN()).isEqualTo(DEFAULT_SERIE_EAN);
    }

    @Test
    @Transactional
    public void createProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto with an existing ID
        producto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProductoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setProducto(null);

        // Create the Producto, which fails.


        restProductoMockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setClave(null);

        // Create the Producto, which fails.


        restProductoMockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSerieEANIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setSerieEAN(null);

        // Create the Producto, which fails.


        restProductoMockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].producto").value(hasItem(DEFAULT_PRODUCTO)))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].serieEAN").value(hasItem(DEFAULT_SERIE_EAN)));
    }
    
    @Test
    @Transactional
    public void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
            .andExpect(jsonPath("$.producto").value(DEFAULT_PRODUCTO))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.serieEAN").value(DEFAULT_SERIE_EAN));
    }


    @Test
    @Transactional
    public void getProductosByIdFiltering() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        Long id = producto.getId();

        defaultProductoShouldBeFound("id.equals=" + id);
        defaultProductoShouldNotBeFound("id.notEquals=" + id);

        defaultProductoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductoShouldNotBeFound("id.greaterThan=" + id);

        defaultProductoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductosByProductoIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where producto equals to DEFAULT_PRODUCTO
        defaultProductoShouldBeFound("producto.equals=" + DEFAULT_PRODUCTO);

        // Get all the productoList where producto equals to UPDATED_PRODUCTO
        defaultProductoShouldNotBeFound("producto.equals=" + UPDATED_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllProductosByProductoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where producto not equals to DEFAULT_PRODUCTO
        defaultProductoShouldNotBeFound("producto.notEquals=" + DEFAULT_PRODUCTO);

        // Get all the productoList where producto not equals to UPDATED_PRODUCTO
        defaultProductoShouldBeFound("producto.notEquals=" + UPDATED_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllProductosByProductoIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where producto in DEFAULT_PRODUCTO or UPDATED_PRODUCTO
        defaultProductoShouldBeFound("producto.in=" + DEFAULT_PRODUCTO + "," + UPDATED_PRODUCTO);

        // Get all the productoList where producto equals to UPDATED_PRODUCTO
        defaultProductoShouldNotBeFound("producto.in=" + UPDATED_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllProductosByProductoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where producto is not null
        defaultProductoShouldBeFound("producto.specified=true");

        // Get all the productoList where producto is null
        defaultProductoShouldNotBeFound("producto.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductosByProductoContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where producto contains DEFAULT_PRODUCTO
        defaultProductoShouldBeFound("producto.contains=" + DEFAULT_PRODUCTO);

        // Get all the productoList where producto contains UPDATED_PRODUCTO
        defaultProductoShouldNotBeFound("producto.contains=" + UPDATED_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllProductosByProductoNotContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where producto does not contain DEFAULT_PRODUCTO
        defaultProductoShouldNotBeFound("producto.doesNotContain=" + DEFAULT_PRODUCTO);

        // Get all the productoList where producto does not contain UPDATED_PRODUCTO
        defaultProductoShouldBeFound("producto.doesNotContain=" + UPDATED_PRODUCTO);
    }


    @Test
    @Transactional
    public void getAllProductosByClaveIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where clave equals to DEFAULT_CLAVE
        defaultProductoShouldBeFound("clave.equals=" + DEFAULT_CLAVE);

        // Get all the productoList where clave equals to UPDATED_CLAVE
        defaultProductoShouldNotBeFound("clave.equals=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllProductosByClaveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where clave not equals to DEFAULT_CLAVE
        defaultProductoShouldNotBeFound("clave.notEquals=" + DEFAULT_CLAVE);

        // Get all the productoList where clave not equals to UPDATED_CLAVE
        defaultProductoShouldBeFound("clave.notEquals=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllProductosByClaveIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where clave in DEFAULT_CLAVE or UPDATED_CLAVE
        defaultProductoShouldBeFound("clave.in=" + DEFAULT_CLAVE + "," + UPDATED_CLAVE);

        // Get all the productoList where clave equals to UPDATED_CLAVE
        defaultProductoShouldNotBeFound("clave.in=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllProductosByClaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where clave is not null
        defaultProductoShouldBeFound("clave.specified=true");

        // Get all the productoList where clave is null
        defaultProductoShouldNotBeFound("clave.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductosByClaveContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where clave contains DEFAULT_CLAVE
        defaultProductoShouldBeFound("clave.contains=" + DEFAULT_CLAVE);

        // Get all the productoList where clave contains UPDATED_CLAVE
        defaultProductoShouldNotBeFound("clave.contains=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllProductosByClaveNotContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where clave does not contain DEFAULT_CLAVE
        defaultProductoShouldNotBeFound("clave.doesNotContain=" + DEFAULT_CLAVE);

        // Get all the productoList where clave does not contain UPDATED_CLAVE
        defaultProductoShouldBeFound("clave.doesNotContain=" + UPDATED_CLAVE);
    }


    @Test
    @Transactional
    public void getAllProductosBySerieEANIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where serieEAN equals to DEFAULT_SERIE_EAN
        defaultProductoShouldBeFound("serieEAN.equals=" + DEFAULT_SERIE_EAN);

        // Get all the productoList where serieEAN equals to UPDATED_SERIE_EAN
        defaultProductoShouldNotBeFound("serieEAN.equals=" + UPDATED_SERIE_EAN);
    }

    @Test
    @Transactional
    public void getAllProductosBySerieEANIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where serieEAN not equals to DEFAULT_SERIE_EAN
        defaultProductoShouldNotBeFound("serieEAN.notEquals=" + DEFAULT_SERIE_EAN);

        // Get all the productoList where serieEAN not equals to UPDATED_SERIE_EAN
        defaultProductoShouldBeFound("serieEAN.notEquals=" + UPDATED_SERIE_EAN);
    }

    @Test
    @Transactional
    public void getAllProductosBySerieEANIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where serieEAN in DEFAULT_SERIE_EAN or UPDATED_SERIE_EAN
        defaultProductoShouldBeFound("serieEAN.in=" + DEFAULT_SERIE_EAN + "," + UPDATED_SERIE_EAN);

        // Get all the productoList where serieEAN equals to UPDATED_SERIE_EAN
        defaultProductoShouldNotBeFound("serieEAN.in=" + UPDATED_SERIE_EAN);
    }

    @Test
    @Transactional
    public void getAllProductosBySerieEANIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where serieEAN is not null
        defaultProductoShouldBeFound("serieEAN.specified=true");

        // Get all the productoList where serieEAN is null
        defaultProductoShouldNotBeFound("serieEAN.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductosBySerieEANContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where serieEAN contains DEFAULT_SERIE_EAN
        defaultProductoShouldBeFound("serieEAN.contains=" + DEFAULT_SERIE_EAN);

        // Get all the productoList where serieEAN contains UPDATED_SERIE_EAN
        defaultProductoShouldNotBeFound("serieEAN.contains=" + UPDATED_SERIE_EAN);
    }

    @Test
    @Transactional
    public void getAllProductosBySerieEANNotContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where serieEAN does not contain DEFAULT_SERIE_EAN
        defaultProductoShouldNotBeFound("serieEAN.doesNotContain=" + DEFAULT_SERIE_EAN);

        // Get all the productoList where serieEAN does not contain UPDATED_SERIE_EAN
        defaultProductoShouldBeFound("serieEAN.doesNotContain=" + UPDATED_SERIE_EAN);
    }


    @Test
    @Transactional
    public void getAllProductosByTipoIsEqualToSomething() throws Exception {
        // Get already existing entity
        TipoProducto tipo = producto.getTipo();
        productoRepository.saveAndFlush(producto);
        Long tipoId = tipo.getId();

        // Get all the productoList where tipo equals to tipoId
        defaultProductoShouldBeFound("tipoId.equals=" + tipoId);

        // Get all the productoList where tipo equals to tipoId + 1
        defaultProductoShouldNotBeFound("tipoId.equals=" + (tipoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductoShouldBeFound(String filter) throws Exception {
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].producto").value(hasItem(DEFAULT_PRODUCTO)))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].serieEAN").value(hasItem(DEFAULT_SERIE_EAN)));

        // Check, that the count call also returns 1
        restProductoMockMvc.perform(get("/api/productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductoShouldNotBeFound(String filter) throws Exception {
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductoMockMvc.perform(get("/api/productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducto() throws Exception {
        // Initialize the database
        productoService.save(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        Producto updatedProducto = productoRepository.findById(producto.getId()).get();
        // Disconnect from session so that the updates on updatedProducto are not directly saved in db
        em.detach(updatedProducto);
        updatedProducto
            .producto(UPDATED_PRODUCTO)
            .clave(UPDATED_CLAVE)
            .serieEAN(UPDATED_SERIE_EAN);

        restProductoMockMvc.perform(put("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProducto)))
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getProducto()).isEqualTo(UPDATED_PRODUCTO);
        assertThat(testProducto.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testProducto.getSerieEAN()).isEqualTo(UPDATED_SERIE_EAN);
    }

    @Test
    @Transactional
    public void updateNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc.perform(put("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProducto() throws Exception {
        // Initialize the database
        productoService.save(producto);

        int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Delete the producto
        restProductoMockMvc.perform(delete("/api/productos/{id}", producto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
