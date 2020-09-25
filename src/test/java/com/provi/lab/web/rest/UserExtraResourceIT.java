package com.provi.lab.web.rest;

import com.provi.lab.LabprovidenciaApp;
import com.provi.lab.domain.UserExtra;
import com.provi.lab.domain.Area;
import com.provi.lab.domain.Personal;
import com.provi.lab.domain.User;
import com.provi.lab.repository.UserExtraRepository;
import com.provi.lab.service.UserExtraService;
import com.provi.lab.service.dto.UserExtraCriteria;
import com.provi.lab.service.UserExtraQueryService;

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
 * Integration tests for the {@link UserExtraResource} REST controller.
 */
@SpringBootTest(classes = LabprovidenciaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserExtraResourceIT {

    private static final String DEFAULT_NOMBRE_COMPLETO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COMPLETO = "BBBBBBBBBB";

    @Autowired
    private UserExtraRepository userExtraRepository;

    @Autowired
    private UserExtraService userExtraService;

    @Autowired
    private UserExtraQueryService userExtraQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExtraMockMvc;

    private UserExtra userExtra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra()
            .nombreCompleto(DEFAULT_NOMBRE_COMPLETO);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        userExtra.setArea(area);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        userExtra.setPersonal(personal);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userExtra.setUser(user);
        return userExtra;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createUpdatedEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra()
            .nombreCompleto(UPDATED_NOMBRE_COMPLETO);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createUpdatedEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        userExtra.setArea(area);
        // Add required entity
        Personal personal;
        if (TestUtil.findAll(em, Personal.class).isEmpty()) {
            personal = PersonalResourceIT.createUpdatedEntity(em);
            em.persist(personal);
            em.flush();
        } else {
            personal = TestUtil.findAll(em, Personal.class).get(0);
        }
        userExtra.setPersonal(personal);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userExtra.setUser(user);
        return userExtra;
    }

    @BeforeEach
    public void initTest() {
        userExtra = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExtra() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();
        // Create the UserExtra
        restUserExtraMockMvc.perform(post("/api/user-extras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isCreated());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getNombreCompleto()).isEqualTo(DEFAULT_NOMBRE_COMPLETO);

        // Validate the id for MapsId, the ids must be same
        assertThat(testUserExtra.getId()).isEqualTo(testUserExtra.getUser().getId());
    }

    @Test
    @Transactional
    public void createUserExtraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();

        // Create the UserExtra with an existing ID
        userExtra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtraMockMvc.perform(post("/api/user-extras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateUserExtraMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        userExtraService.save(userExtra);
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();


        // Load the userExtra
        UserExtra updatedUserExtra = userExtraRepository.findById(userExtra.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtra are not directly saved in db
        em.detach(updatedUserExtra);

        // Update the User with new association value
        //  updatedUserExtra.setUser();

        // Update the entity
        restUserExtraMockMvc.perform(put("/api/user-extras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExtra)))
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testUserExtra.getId()).isEqualTo(testUserExtra.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllUserExtras() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList
        restUserExtraMockMvc.perform(get("/api/user-extras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCompleto").value(hasItem(DEFAULT_NOMBRE_COMPLETO)));
    }

    @Test
    @Transactional
    public void getUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get the userExtra
        restUserExtraMockMvc.perform(get("/api/user-extras/{id}", userExtra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExtra.getId().intValue()))
            .andExpect(jsonPath("$.nombreCompleto").value(DEFAULT_NOMBRE_COMPLETO));
    }


    @Test
    @Transactional
    public void getUserExtrasByIdFiltering() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        Long id = userExtra.getId();

        defaultUserExtraShouldBeFound("id.equals=" + id);
        defaultUserExtraShouldNotBeFound("id.notEquals=" + id);

        defaultUserExtraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserExtraShouldNotBeFound("id.greaterThan=" + id);

        defaultUserExtraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserExtraShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserExtrasByNombreCompletoIsEqualToSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where nombreCompleto equals to DEFAULT_NOMBRE_COMPLETO
        defaultUserExtraShouldBeFound("nombreCompleto.equals=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the userExtraList where nombreCompleto equals to UPDATED_NOMBRE_COMPLETO
        defaultUserExtraShouldNotBeFound("nombreCompleto.equals=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    public void getAllUserExtrasByNombreCompletoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where nombreCompleto not equals to DEFAULT_NOMBRE_COMPLETO
        defaultUserExtraShouldNotBeFound("nombreCompleto.notEquals=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the userExtraList where nombreCompleto not equals to UPDATED_NOMBRE_COMPLETO
        defaultUserExtraShouldBeFound("nombreCompleto.notEquals=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    public void getAllUserExtrasByNombreCompletoIsInShouldWork() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where nombreCompleto in DEFAULT_NOMBRE_COMPLETO or UPDATED_NOMBRE_COMPLETO
        defaultUserExtraShouldBeFound("nombreCompleto.in=" + DEFAULT_NOMBRE_COMPLETO + "," + UPDATED_NOMBRE_COMPLETO);

        // Get all the userExtraList where nombreCompleto equals to UPDATED_NOMBRE_COMPLETO
        defaultUserExtraShouldNotBeFound("nombreCompleto.in=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    public void getAllUserExtrasByNombreCompletoIsNullOrNotNull() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where nombreCompleto is not null
        defaultUserExtraShouldBeFound("nombreCompleto.specified=true");

        // Get all the userExtraList where nombreCompleto is null
        defaultUserExtraShouldNotBeFound("nombreCompleto.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserExtrasByNombreCompletoContainsSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where nombreCompleto contains DEFAULT_NOMBRE_COMPLETO
        defaultUserExtraShouldBeFound("nombreCompleto.contains=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the userExtraList where nombreCompleto contains UPDATED_NOMBRE_COMPLETO
        defaultUserExtraShouldNotBeFound("nombreCompleto.contains=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    public void getAllUserExtrasByNombreCompletoNotContainsSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where nombreCompleto does not contain DEFAULT_NOMBRE_COMPLETO
        defaultUserExtraShouldNotBeFound("nombreCompleto.doesNotContain=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the userExtraList where nombreCompleto does not contain UPDATED_NOMBRE_COMPLETO
        defaultUserExtraShouldBeFound("nombreCompleto.doesNotContain=" + UPDATED_NOMBRE_COMPLETO);
    }


    @Test
    @Transactional
    public void getAllUserExtrasByAreaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Area area = userExtra.getArea();
        userExtraRepository.saveAndFlush(userExtra);
        Long areaId = area.getId();

        // Get all the userExtraList where area equals to areaId
        defaultUserExtraShouldBeFound("areaId.equals=" + areaId);

        // Get all the userExtraList where area equals to areaId + 1
        defaultUserExtraShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }


    @Test
    @Transactional
    public void getAllUserExtrasByPersonalIsEqualToSomething() throws Exception {
        // Get already existing entity
        Personal personal = userExtra.getPersonal();
        userExtraRepository.saveAndFlush(userExtra);
        Long personalId = personal.getId();

        // Get all the userExtraList where personal equals to personalId
        defaultUserExtraShouldBeFound("personalId.equals=" + personalId);

        // Get all the userExtraList where personal equals to personalId + 1
        defaultUserExtraShouldNotBeFound("personalId.equals=" + (personalId + 1));
    }


    @Test
    @Transactional
    public void getAllUserExtrasByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = userExtra.getUser();
        userExtraRepository.saveAndFlush(userExtra);
        Long userId = user.getId();

        // Get all the userExtraList where user equals to userId
        defaultUserExtraShouldBeFound("userId.equals=" + userId);

        // Get all the userExtraList where user equals to userId + 1
        defaultUserExtraShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserExtraShouldBeFound(String filter) throws Exception {
        restUserExtraMockMvc.perform(get("/api/user-extras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCompleto").value(hasItem(DEFAULT_NOMBRE_COMPLETO)));

        // Check, that the count call also returns 1
        restUserExtraMockMvc.perform(get("/api/user-extras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserExtraShouldNotBeFound(String filter) throws Exception {
        restUserExtraMockMvc.perform(get("/api/user-extras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserExtraMockMvc.perform(get("/api/user-extras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUserExtra() throws Exception {
        // Get the userExtra
        restUserExtraMockMvc.perform(get("/api/user-extras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExtra() throws Exception {
        // Initialize the database
        userExtraService.save(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra
        UserExtra updatedUserExtra = userExtraRepository.findById(userExtra.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtra are not directly saved in db
        em.detach(updatedUserExtra);
        updatedUserExtra
            .nombreCompleto(UPDATED_NOMBRE_COMPLETO);

        restUserExtraMockMvc.perform(put("/api/user-extras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExtra)))
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getNombreCompleto()).isEqualTo(UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtraMockMvc.perform(put("/api/user-extras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserExtra() throws Exception {
        // Initialize the database
        userExtraService.save(userExtra);

        int databaseSizeBeforeDelete = userExtraRepository.findAll().size();

        // Delete the userExtra
        restUserExtraMockMvc.perform(delete("/api/user-extras/{id}", userExtra.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
