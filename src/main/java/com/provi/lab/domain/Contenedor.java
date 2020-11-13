package com.provi.lab.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Contenedor.
 */
@Entity
@Table(name = "contenedor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contenedor extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 45)
    @Column(name = "contenedor", length = 45, nullable = false)
    private String contenedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenedor() {
        return contenedor;
    }

    public Contenedor contenedor(String contenedor) {
        this.contenedor = contenedor;
        return this;
    }

    public void setContenedor(String contenedor) {
        this.contenedor = contenedor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contenedor)) {
            return false;
        }
        return id != null && id.equals(((Contenedor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contenedor{" +
            "id=" + getId() +
            ", contenedor='" + getContenedor() + "'" +
            "}";
    }
}
