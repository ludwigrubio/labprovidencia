package com.provi.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A FQQueso.
 */
@Entity
@Table(name = "fq_queso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FQQueso extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "humedad")
    private Double humedad;

    @Column(name = "ph")
    private Double ph;

    @Column(name = "fundicion")
    private Integer fundicion;

    @Column(name = "presentacion")
    private Integer presentacion;

    @Column(name = "caducidad")
    private Instant caducidad;

    @Column(name = "apariencia")
    private Integer apariencia;

    @Column(name = "sabor")
    private Integer sabor;

    @Column(name = "color")
    private Integer color;

    @Column(name = "olor")
    private Integer olor;

    @Column(name = "textura")
    private Integer textura;

    @Column(name = "hilado")
    private Integer hilado;

    @Size(max = 100)
    @Column(name = "observaciones", length = 100)
    private String observaciones;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQQuesos", allowSetters = true)
    private Area area;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQQuesos", allowSetters = true)
    private Producto producto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQQuesos", allowSetters = true)
    private UserExtra analista;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQQuesos", allowSetters = true)
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

    public FQQueso fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getLote() {
        return lote;
    }

    public FQQueso lote(String lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Double getHumedad() {
        return humedad;
    }

    public FQQueso humedad(Double humedad) {
        this.humedad = humedad;
        return this;
    }

    public void setHumedad(Double humedad) {
        this.humedad = humedad;
    }

    public Double getPh() {
        return ph;
    }

    public FQQueso ph(Double ph) {
        this.ph = ph;
        return this;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public Integer getFundicion() {
        return fundicion;
    }

    public FQQueso fundicion(Integer fundicion) {
        this.fundicion = fundicion;
        return this;
    }

    public void setFundicion(Integer fundicion) {
        this.fundicion = fundicion;
    }

    public Integer getPresentacion() {
        return presentacion;
    }

    public FQQueso presentacion(Integer presentacion) {
        this.presentacion = presentacion;
        return this;
    }

    public void setPresentacion(Integer presentacion) {
        this.presentacion = presentacion;
    }

    public Instant getCaducidad() {
        return caducidad;
    }

    public FQQueso caducidad(Instant caducidad) {
        this.caducidad = caducidad;
        return this;
    }

    public void setCaducidad(Instant caducidad) {
        this.caducidad = caducidad;
    }

    public Integer getApariencia() {
        return apariencia;
    }

    public FQQueso apariencia(Integer apariencia) {
        this.apariencia = apariencia;
        return this;
    }

    public void setApariencia(Integer apariencia) {
        this.apariencia = apariencia;
    }

    public Integer getSabor() {
        return sabor;
    }

    public FQQueso sabor(Integer sabor) {
        this.sabor = sabor;
        return this;
    }

    public void setSabor(Integer sabor) {
        this.sabor = sabor;
    }

    public Integer getColor() {
        return color;
    }

    public FQQueso color(Integer color) {
        this.color = color;
        return this;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getOlor() {
        return olor;
    }

    public FQQueso olor(Integer olor) {
        this.olor = olor;
        return this;
    }

    public void setOlor(Integer olor) {
        this.olor = olor;
    }

    public Integer getTextura() {
        return textura;
    }

    public FQQueso textura(Integer textura) {
        this.textura = textura;
        return this;
    }

    public void setTextura(Integer textura) {
        this.textura = textura;
    }

    public Integer getHilado() {
        return hilado;
    }

    public FQQueso hilado(Integer hilado) {
        this.hilado = hilado;
        return this;
    }

    public void setHilado(Integer hilado) {
        this.hilado = hilado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public FQQueso observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Area getArea() {
        return area;
    }

    public FQQueso area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Producto getProducto() {
        return producto;
    }

    public FQQueso producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public UserExtra getAnalista() {
        return analista;
    }

    public FQQueso analista(UserExtra userExtra) {
        this.analista = userExtra;
        return this;
    }

    public void setAnalista(UserExtra userExtra) {
        this.analista = userExtra;
    }

    public Personal getProveedor() {
        return proveedor;
    }

    public FQQueso proveedor(Personal personal) {
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
        if (!(o instanceof FQQueso)) {
            return false;
        }
        return id != null && id.equals(((FQQueso) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FQQueso{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", lote='" + getLote() + "'" +
            ", humedad=" + getHumedad() +
            ", ph=" + getPh() +
            ", fundicion=" + getFundicion() +
            ", presentacion=" + getPresentacion() +
            ", caducidad='" + getCaducidad() + "'" +
            ", apariencia=" + getApariencia() +
            ", sabor=" + getSabor() +
            ", color=" + getColor() +
            ", olor=" + getOlor() +
            ", textura=" + getTextura() +
            ", hilado=" + getHilado() +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}