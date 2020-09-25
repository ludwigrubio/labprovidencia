package com.provi.lab.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Superficie.
 */
@Entity
@Table(name = "superficie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Superficie extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 45)
    @Column(name = "superficie", length = 45, nullable = false)
    private String superficie;

    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuperficie() {
        return superficie;
    }

    public Superficie superficie(String superficie) {
        this.superficie = superficie;
        return this;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Superficie descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Superficie)) {
            return false;
        }
        return id != null && id.equals(((Superficie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Superficie{" +
            "id=" + getId() +
            ", superficie='" + getSuperficie() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
