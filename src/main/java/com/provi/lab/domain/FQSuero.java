package com.provi.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A FQSuero.
 */
@Entity
@Table(name = "fq_suero")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FQSuero extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "temperatura")
    private Double temperatura;

    @Column(name = "delvo")
    private Double delvo;

    @Column(name = "solidos")
    private Double solidos;

    @Column(name = "neutralizantes")
    private Double neutralizantes;

    @Column(name = "ph")
    private Double ph;

    @Column(name = "cloro")
    private Double cloro;

    @Column(name = "almidon")
    private Double almidon;

    @Size(max = 100)
    @Column(name = "observaciones", length = 100)
    private String observaciones;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQSueros", allowSetters = true)
    private Area area;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQSueros", allowSetters = true)
    private Producto producto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQSueros", allowSetters = true)
    private UserExtra analista;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQSueros", allowSetters = true)
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

    public FQSuero fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getLote() {
        return lote;
    }

    public FQSuero lote(String lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Double getAcidez() {
        return acidez;
    }

    public FQSuero acidez(Double acidez) {
        this.acidez = acidez;
        return this;
    }

    public void setAcidez(Double acidez) {
        this.acidez = acidez;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public FQSuero temperatura(Double temperatura) {
        this.temperatura = temperatura;
        return this;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getDelvo() {
        return delvo;
    }

    public FQSuero delvo(Double delvo) {
        this.delvo = delvo;
        return this;
    }

    public void setDelvo(Double delvo) {
        this.delvo = delvo;
    }

    public Double getSolidos() {
        return solidos;
    }

    public FQSuero solidos(Double solidos) {
        this.solidos = solidos;
        return this;
    }

    public void setSolidos(Double solidos) {
        this.solidos = solidos;
    }

    public Double getNeutralizantes() {
        return neutralizantes;
    }

    public FQSuero neutralizantes(Double neutralizantes) {
        this.neutralizantes = neutralizantes;
        return this;
    }

    public void setNeutralizantes(Double neutralizantes) {
        this.neutralizantes = neutralizantes;
    }

    public Double getPh() {
        return ph;
    }

    public FQSuero ph(Double ph) {
        this.ph = ph;
        return this;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public Double getCloro() {
        return cloro;
    }

    public FQSuero cloro(Double cloro) {
        this.cloro = cloro;
        return this;
    }

    public void setCloro(Double cloro) {
        this.cloro = cloro;
    }

    public Double getAlmidon() {
        return almidon;
    }

    public FQSuero almidon(Double almidon) {
        this.almidon = almidon;
        return this;
    }

    public void setAlmidon(Double almidon) {
        this.almidon = almidon;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public FQSuero observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Area getArea() {
        return area;
    }

    public FQSuero area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Producto getProducto() {
        return producto;
    }

    public FQSuero producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public UserExtra getAnalista() {
        return analista;
    }

    public FQSuero analista(UserExtra userExtra) {
        this.analista = userExtra;
        return this;
    }

    public void setAnalista(UserExtra userExtra) {
        this.analista = userExtra;
    }

    public Personal getProveedor() {
        return proveedor;
    }

    public FQSuero proveedor(Personal personal) {
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
        if (!(o instanceof FQSuero)) {
            return false;
        }
        return id != null && id.equals(((FQSuero) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FQSuero{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", lote='" + getLote() + "'" +
            ", acidez=" + getAcidez() +
            ", temperatura=" + getTemperatura() +
            ", delvo=" + getDelvo() +
            ", solidos=" + getSolidos() +
            ", neutralizantes=" + getNeutralizantes() +
            ", ph=" + getPh() +
            ", cloro=" + getCloro() +
            ", almidon=" + getAlmidon() +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
