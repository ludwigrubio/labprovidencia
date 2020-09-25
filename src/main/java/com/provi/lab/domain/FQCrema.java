package com.provi.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A FQCrema.
 */
@Entity
@Table(name = "fq_crema")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FQCrema extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @Size(max = 45)
    @Column(name = "lote", length = 45, nullable = false)
    private String lote;

    @Column(name = "acidez")
    private Double acidez;

    @Column(name = "grasa")
    private Double grasa;

    @Size(max = 100)
    @Column(name = "observaciones", length = 100)
    private String observaciones;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQCremas", allowSetters = true)
    private Area area;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQCremas", allowSetters = true)
    private Producto producto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQCremas", allowSetters = true)
    private UserExtra analista;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQCremas", allowSetters = true)
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

    public FQCrema fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getLote() {
        return lote;
    }

    public FQCrema lote(String lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Double getAcidez() {
        return acidez;
    }

    public FQCrema acidez(Double acidez) {
        this.acidez = acidez;
        return this;
    }

    public void setAcidez(Double acidez) {
        this.acidez = acidez;
    }

    public Double getGrasa() {
        return grasa;
    }

    public FQCrema grasa(Double grasa) {
        this.grasa = grasa;
        return this;
    }

    public void setGrasa(Double grasa) {
        this.grasa = grasa;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public FQCrema observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Area getArea() {
        return area;
    }

    public FQCrema area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Producto getProducto() {
        return producto;
    }

    public FQCrema producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public UserExtra getAnalista() {
        return analista;
    }

    public FQCrema analista(UserExtra userExtra) {
        this.analista = userExtra;
        return this;
    }

    public void setAnalista(UserExtra userExtra) {
        this.analista = userExtra;
    }

    public Personal getProveedor() {
        return proveedor;
    }

    public FQCrema proveedor(Personal personal) {
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
        if (!(o instanceof FQCrema)) {
            return false;
        }
        return id != null && id.equals(((FQCrema) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FQCrema{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", lote='" + getLote() + "'" +
            ", acidez=" + getAcidez() +
            ", grasa=" + getGrasa() +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
