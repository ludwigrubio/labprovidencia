package com.provi.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A UserExtra.
 */
@Entity
@Table(name = "user_extra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserExtra extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Size(max = 200)
    @Column(name = "nombre_completo", length = 200)
    private String nombreCompleto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "userExtras", allowSetters = true)
    private Area area;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "userExtras", allowSetters = true)
    private Personal personal;

    @OneToOne(optional = false)
    @NotNull
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public UserExtra nombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        return this;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Area getArea() {
        return area;
    }

    public UserExtra area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Personal getPersonal() {
        return personal;
    }

    public UserExtra personal(Personal personal) {
        this.personal = personal;
        return this;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public User getUser() {
        return user;
    }

    public UserExtra user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtra)) {
            return false;
        }
        return id != null && id.equals(((UserExtra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            ", nombreCompleto='" + getNombreCompleto() + "'" +
            "}";
    }
}
