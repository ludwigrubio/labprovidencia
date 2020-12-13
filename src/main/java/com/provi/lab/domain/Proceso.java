package com.provi.lab.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Proceso.
 */
@Entity
@Table(name = "proceso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Proceso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "proceso", length = 100, nullable = false)
    private String proceso;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProceso() {
        return proceso;
    }

    public Proceso proceso(String proceso) {
        this.proceso = proceso;
        return this;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proceso)) {
            return false;
        }
        return id != null && id.equals(((Proceso) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proceso{" +
            "id=" + getId() +
            ", proceso='" + getProceso() + "'" +
            "}";
    }
}
