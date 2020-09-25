package com.provi.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Producto extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 45)
    @Column(name = "producto", length = 45, nullable = false)
    private String producto;

    @NotNull
    @Size(max = 45)
    @Column(name = "clave", length = 45, nullable = false)
    private String clave;

    @NotNull
    @Size(max = 45)
    @Column(name = "serie_ean", length = 45, nullable = false)
    private String serieEAN;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "productos", allowSetters = true)
    private TipoProducto tipo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public Producto producto(String producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getClave() {
        return clave;
    }

    public Producto clave(String clave) {
        this.clave = clave;
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getSerieEAN() {
        return serieEAN;
    }

    public Producto serieEAN(String serieEAN) {
        this.serieEAN = serieEAN;
        return this;
    }

    public void setSerieEAN(String serieEAN) {
        this.serieEAN = serieEAN;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public Producto tipo(TipoProducto tipoProducto) {
        this.tipo = tipoProducto;
        return this;
    }

    public void setTipo(TipoProducto tipoProducto) {
        this.tipo = tipoProducto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", producto='" + getProducto() + "'" +
            ", clave='" + getClave() + "'" +
            ", serieEAN='" + getSerieEAN() + "'" +
            "}";
    }
}
