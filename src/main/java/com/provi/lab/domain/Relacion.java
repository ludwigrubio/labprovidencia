package com.provi.lab.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Relacion.
 */
@Entity
@Table(name = "relacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Relacion extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 45)
    @Column(name = "relacion", length = 45, nullable = false)
    private String relacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelacion() {
        return relacion;
    }

    public Relacion relacion(String relacion) {
        this.relacion = relacion;
        return this;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Relacion)) {
            return false;
        }
        return id != null && id.equals(((Relacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Relacion{" +
            "id=" + getId() +
            ", relacion='" + getRelacion() + "'" +
            "}";
    }
}
