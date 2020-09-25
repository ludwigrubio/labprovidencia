package com.provi.lab.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Cultivo.
 */
@Entity
@Table(name = "cultivo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cultivo extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 45)
    @Column(name = "cultivo", length = 45, nullable = false)
    private String cultivo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCultivo() {
        return cultivo;
    }

    public Cultivo cultivo(String cultivo) {
        this.cultivo = cultivo;
        return this;
    }

    public void setCultivo(String cultivo) {
        this.cultivo = cultivo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cultivo)) {
            return false;
        }
        return id != null && id.equals(((Cultivo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cultivo{" +
            "id=" + getId() +
            ", cultivo='" + getCultivo() + "'" +
            "}";
    }
}
