package com.provi.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PruebaMicro.
 */
@Entity
@Table(name = "prueba_micro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PruebaMicro extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tipode_muestra", nullable = false)
    private Integer tipodeMuestra;

    @NotNull
    @Size(max = 10)
    @Column(name = "id_catalogo", length = 10, nullable = false)
    private String idCatalogo;

    @Size(max = 45)
    @Column(name = "lote", length = 45)
    private String lote;

    @NotNull
    @Column(name = "inicio", nullable = false)
    private Instant inicio;

    @Column(name = "fin")
    private Instant fin;

    @Column(name = "resultado")
    private Integer resultado;

    @Column(name = "unidades")
    private Integer unidades;

    @Size(max = 100)
    @Column(name = "observaciones", length = 100)
    private String observaciones;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "pruebaMicros", allowSetters = true)
    private Area area;

    @ManyToOne
    @JsonIgnoreProperties(value = "pruebaMicros", allowSetters = true)
    private Cultivo cultivo;

    @ManyToOne
    @JsonIgnoreProperties(value = "pruebaMicros", allowSetters = true)
    private Superficie superficie;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "pruebaMicros", allowSetters = true)
    private UserExtra analista;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "pruebaMicros", allowSetters = true)
    private Personal proveedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTipodeMuestra() {
        return tipodeMuestra;
    }

    public PruebaMicro tipodeMuestra(Integer tipodeMuestra) {
        this.tipodeMuestra = tipodeMuestra;
        return this;
    }

    public void setTipodeMuestra(Integer tipodeMuestra) {
        this.tipodeMuestra = tipodeMuestra;
    }

    public String getIdCatalogo() {
        return idCatalogo;
    }

    public PruebaMicro idCatalogo(String idCatalogo) {
        this.idCatalogo = idCatalogo;
        return this;
    }

    public void setIdCatalogo(String idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getLote() {
        return lote;
    }

    public PruebaMicro lote(String lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Instant getInicio() {
        return inicio;
    }

    public PruebaMicro inicio(Instant inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(Instant inicio) {
        this.inicio = inicio;
    }

    public Instant getFin() {
        return fin;
    }

    public PruebaMicro fin(Instant fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public Integer getResultado() {
        return resultado;
    }

    public PruebaMicro resultado(Integer resultado) {
        this.resultado = resultado;
        return this;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public PruebaMicro unidades(Integer unidades) {
        this.unidades = unidades;
        return this;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public PruebaMicro observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Area getArea() {
        return area;
    }

    public PruebaMicro area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Cultivo getCultivo() {
        return cultivo;
    }

    public PruebaMicro cultivo(Cultivo cultivo) {
        this.cultivo = cultivo;
        return this;
    }

    public void setCultivo(Cultivo cultivo) {
        this.cultivo = cultivo;
    }

    public Superficie getSuperficie() {
        return superficie;
    }

    public PruebaMicro superficie(Superficie superficie) {
        this.superficie = superficie;
        return this;
    }

    public void setSuperficie(Superficie superficie) {
        this.superficie = superficie;
    }

    public UserExtra getAnalista() {
        return analista;
    }

    public PruebaMicro analista(UserExtra userExtra) {
        this.analista = userExtra;
        return this;
    }

    public void setAnalista(UserExtra userExtra) {
        this.analista = userExtra;
    }

    public Personal getProveedor() {
        return proveedor;
    }

    public PruebaMicro proveedor(Personal personal) {
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
        if (!(o instanceof PruebaMicro)) {
            return false;
        }
        return id != null && id.equals(((PruebaMicro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PruebaMicro{" +
            "id=" + getId() +
            ", tipodeMuestra=" + getTipodeMuestra() +
            ", idCatalogo='" + getIdCatalogo() + "'" +
            ", lote='" + getLote() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            ", resultado=" + getResultado() +
            ", unidades=" + getUnidades() +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
