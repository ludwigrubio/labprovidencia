package com.provi.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A FQLeche.
 */
@Entity
@Table(name = "fq_leche")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FQLeche extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @Size(max = 45)
    @Column(name = "lote", length = 45)
    private String lote;

    @Column(name = "acidez")
    private Double acidez;

    @Column(name = "temperatura")
    private Double temperatura;

    @Column(name = "agua")
    private Double agua;

    @Column(name = "crioscopia")
    private Double crioscopia;

    @Column(name = "antibiotico")
    private Integer antibiotico;

    @Column(name = "delvo")
    private Double delvo;

    @Column(name = "grasa")
    private Double grasa;

    @Size(max = 45)
    @Column(name = "solidos", length = 45)
    private String solidos;

    @Column(name = "densidad")
    private Double densidad;

    @Column(name = "lactosa")
    private Double lactosa;

    @Column(name = "proteina")
    private Double proteina;

    @Column(name = "neutralizantes")
    private Double neutralizantes;

    @Column(name = "adulterantes")
    private Double adulterantes;

    @Column(name = "reductasa")
    private Double reductasa;

    @Column(name = "fosfatasa")
    private Double fosfatasa;

    @Column(name = "ph")
    private Double ph;

    @Column(name = "dummy_1")
    private Double dummy1;

    @Column(name = "dummy_2")
    private Double dummy2;

    @Column(name = "dummy_3")
    private Double dummy3;

    @Column(name = "dummy_4")
    private Double dummy4;

    @Column(name = "dummy_5")
    private Double dummy5;

    @Size(max = 100)
    @Column(name = "observaciones", length = 100)
    private String observaciones;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQLeches", allowSetters = true)
    private Area area;

    @ManyToOne
    @JsonIgnoreProperties(value = "fQLeches", allowSetters = true)
    private Recepcion recepcion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQLeches", allowSetters = true)
    private UserExtra analista;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "fQLeches", allowSetters = true)
    private Personal proveedor;

    @ManyToOne
    @JsonIgnoreProperties(value = "fQCremas", allowSetters = true)
    private Contenedor contenedor;

    @ManyToOne
    @JsonIgnoreProperties(value = "fQCremas", allowSetters = true)
    private Proceso proceso;

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

    public FQLeche fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getLote() {
        return lote;
    }

    public FQLeche lote(String lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Double getAcidez() {
        return acidez;
    }

    public FQLeche acidez(Double acidez) {
        this.acidez = acidez;
        return this;
    }

    public void setAcidez(Double acidez) {
        this.acidez = acidez;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public FQLeche temperatura(Double temperatura) {
        this.temperatura = temperatura;
        return this;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getAgua() {
        return agua;
    }

    public FQLeche agua(Double agua) {
        this.agua = agua;
        return this;
    }

    public void setAgua(Double agua) {
        this.agua = agua;
    }

    public Double getCrioscopia() {
        return crioscopia;
    }

    public FQLeche crioscopia(Double crioscopia) {
        this.crioscopia = crioscopia;
        return this;
    }

    public void setCrioscopia(Double crioscopia) {
        this.crioscopia = crioscopia;
    }

    public Integer getAntibiotico() {
        return antibiotico;
    }

    public FQLeche antibiotico(Integer antibiotico) {
        this.antibiotico = antibiotico;
        return this;
    }

    public void setAntibiotico(Integer antibiotico) {
        this.antibiotico = antibiotico;
    }

    public Double getDelvo() {
        return delvo;
    }

    public FQLeche delvo(Double delvo) {
        this.delvo = delvo;
        return this;
    }

    public void setDelvo(Double delvo) {
        this.delvo = delvo;
    }

    public Double getGrasa() {
        return grasa;
    }

    public FQLeche grasa(Double grasa) {
        this.grasa = grasa;
        return this;
    }

    public void setGrasa(Double grasa) {
        this.grasa = grasa;
    }

    public String getSolidos() {
        return solidos;
    }

    public FQLeche solidos(String solidos) {
        this.solidos = solidos;
        return this;
    }

    public void setSolidos(String solidos) {
        this.solidos = solidos;
    }

    public Double getDensidad() {
        return densidad;
    }

    public FQLeche densidad(Double densidad) {
        this.densidad = densidad;
        return this;
    }

    public void setDensidad(Double densidad) {
        this.densidad = densidad;
    }

    public Double getLactosa() {
        return lactosa;
    }

    public FQLeche lactosa(Double lactosa) {
        this.lactosa = lactosa;
        return this;
    }

    public void setLactosa(Double lactosa) {
        this.lactosa = lactosa;
    }

    public Double getProteina() {
        return proteina;
    }

    public FQLeche proteina(Double proteina) {
        this.proteina = proteina;
        return this;
    }

    public void setProteina(Double proteina) {
        this.proteina = proteina;
    }

    public Double getNeutralizantes() {
        return neutralizantes;
    }

    public FQLeche neutralizantes(Double neutralizantes) {
        this.neutralizantes = neutralizantes;
        return this;
    }

    public void setNeutralizantes(Double neutralizantes) {
        this.neutralizantes = neutralizantes;
    }

    public Double getAdulterantes() {
        return adulterantes;
    }

    public FQLeche adulterantes(Double adulterantes) {
        this.adulterantes = adulterantes;
        return this;
    }

    public void setAdulterantes(Double adulterantes) {
        this.adulterantes = adulterantes;
    }

    public Double getReductasa() {
        return reductasa;
    }

    public FQLeche reductasa(Double reductasa) {
        this.reductasa = reductasa;
        return this;
    }

    public void setReductasa(Double reductasa) {
        this.reductasa = reductasa;
    }

    public Double getFosfatasa() {
        return fosfatasa;
    }

    public FQLeche fosfatasa(Double fosfatasa) {
        this.fosfatasa = fosfatasa;
        return this;
    }

    public void setFosfatasa(Double fosfatasa) {
        this.fosfatasa = fosfatasa;
    }

    public Double getPh() {
        return ph;
    }

    public FQLeche ph(Double ph) {
        this.ph = ph;
        return this;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public Double getDummy1() {
        return dummy1;
    }

    public FQLeche dummy1(Double dummy1) {
        this.dummy1 = dummy1;
        return this;
    }

    public void setDummy1(Double dummy1) {
        this.dummy1 = dummy1;
    }

    public Double getDummy2() {
        return dummy2;
    }

    public FQLeche dummy2(Double dummy2) {
        this.dummy2 = dummy2;
        return this;
    }

    public void setDummy2(Double dummy2) {
        this.dummy2 = dummy2;
    }

    public Double getDummy3() {
        return dummy3;
    }

    public FQLeche dummy3(Double dummy3) {
        this.dummy3 = dummy3;
        return this;
    }

    public void setDummy3(Double dummy3) {
        this.dummy3 = dummy3;
    }

    public Double getDummy4() {
        return dummy4;
    }

    public FQLeche dummy4(Double dummy4) {
        this.dummy4 = dummy4;
        return this;
    }

    public void setDummy4(Double dummy4) {
        this.dummy4 = dummy4;
    }

    public Double getDummy5() {
        return dummy5;
    }

    public FQLeche dummy5(Double dummy5) {
        this.dummy5 = dummy5;
        return this;
    }

    public void setDummy5(Double dummy5) {
        this.dummy5 = dummy5;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public FQLeche observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Area getArea() {
        return area;
    }

    public FQLeche area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Recepcion getRecepcion() {
        return recepcion;
    }

    public FQLeche recepcion(Recepcion recepcion) {
        this.recepcion = recepcion;
        return this;
    }

    public void setRecepcion(Recepcion recepcion) {
        this.recepcion = recepcion;
    }

    public UserExtra getAnalista() {
        return analista;
    }

    public FQLeche analista(UserExtra userExtra) {
        this.analista = userExtra;
        return this;
    }

    public void setAnalista(UserExtra userExtra) {
        this.analista = userExtra;
    }

    public Personal getProveedor() {
        return proveedor;
    }

    public FQLeche proveedor(Personal personal) {
        this.proveedor = personal;
        return this;
    }

    public void setProveedor(Personal personal) {
        this.proveedor = personal;
    }

    public Contenedor getContenedor() {
        return contenedor;
    }

    public FQLeche contenedor(Contenedor contenedor) {
        this.contenedor = contenedor;
        return this;
    }

    public void setContenedor(Contenedor contenedor) {
        this.contenedor = contenedor;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public FQLeche proceso(Proceso proceso) {
        this.proceso = proceso;
        return this;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FQLeche)) {
            return false;
        }
        return id != null && id.equals(((FQLeche) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FQLeche{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", lote='" + getLote() + "'" +
            ", acidez=" + getAcidez() +
            ", temperatura=" + getTemperatura() +
            ", agua=" + getAgua() +
            ", crioscopia=" + getCrioscopia() +
            ", antibiotico=" + getAntibiotico() +
            ", delvo=" + getDelvo() +
            ", grasa=" + getGrasa() +
            ", solidos='" + getSolidos() + "'" +
            ", densidad=" + getDensidad() +
            ", lactosa=" + getLactosa() +
            ", proteina=" + getProteina() +
            ", neutralizantes=" + getNeutralizantes() +
            ", adulterantes=" + getAdulterantes() +
            ", reductasa=" + getReductasa() +
            ", fosfatasa=" + getFosfatasa() +
            ", ph=" + getPh() +
            ", dummy1=" + getDummy1() +
            ", dummy2=" + getDummy2() +
            ", dummy3=" + getDummy3() +
            ", dummy4=" + getDummy4() +
            ", dummy5=" + getDummy5() +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
