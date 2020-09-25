package com.provi.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A FQMantequilla.
 */
@Entity
@Table(name = "fq_mantequilla")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FQMantequilla extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private Instant fecha;

    @NotNull
    @Size(max = 45)
    @Column(name = "lote", length = 45, nullable = false)
    private String lote;

    @Column(name = "ph")
    private Double ph;

    @Column(name = "humedad")
    private Double humedad;

    @Size(max = 100)
    @Column(name = "observaciones", length = 100)
    private String observaciones;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQMantequillas", allowSetters = true)
    private Area area;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQMantequillas", allowSetters = true)
    private Producto producto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQMantequillas", allowSetters = true)
    private UserExtra analista;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQMantequillas", allowSetters = true)
    private Personal proveedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public FQMantequilla fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getLote() {
        return lote;
    }

    public FQMantequilla lote(String lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Double getPh() {
        return ph;
    }

    public FQMantequilla ph(Double ph) {
        this.ph = ph;
        return this;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public Double getHumedad() {
        return humedad;
    }

    public FQMantequilla humedad(Double humedad) {
        this.humedad = humedad;
        return this;
    }

    public void setHumedad(Double humedad) {
        this.humedad = humedad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public FQMantequilla observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Area getArea() {
        return area;
    }

    public FQMantequilla area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Producto getProducto() {
        return producto;
    }

    public FQMantequilla producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public UserExtra getAnalista() {
        return analista;
    }

    public FQMantequilla analista(UserExtra userExtra) {
        this.analista = userExtra;
        return this;
    }

    public void setAnalista(UserExtra userExtra) {
        this.analista = userExtra;
    }

    public Personal getProveedor() {
        return proveedor;
    }

    public FQMantequilla proveedor(Personal personal) {
        this.proveedor = personal;
        return this;
    }

    public void setProveedor(Personal personal) {
        this.proveedor = personal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FQMantequilla)) {
            return false;
        }
        return id != null && id.equals(((FQMantequilla) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FQMantequilla{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", lote='" + getLote() + "'" +
            ", ph=" + getPh() +
            ", humedad=" + getHumedad() +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
