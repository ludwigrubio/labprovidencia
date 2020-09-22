package com.provi.lab.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Area.
 */
@Entity
@Table(name = "area")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Area extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 45)
    @Column(name = "area", length = 45, nullable = false)
    private String area;

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

    public String getArea() {
        return area;
    }

    public Area area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Area descripcion(String descripcion) {
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
        if (!(o instanceof Area)) {
            return false;
        }
        return id != null && id.equals(((Area) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Area{" +
            "id=" + getId() +
            ", area='" + getArea() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
