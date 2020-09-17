package com.provi.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Personal.
 */
@Entity
@Table(name = "personal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Personal extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Size(max = 50)
    @Column(name = "apellido_1", length = 50)
    private String apellido1;

    @Size(max = 45)
    @Column(name = "apellido_2", length = 45)
    private String apellido2;

    @Size(max = 45)
    @Column(name = "alias", length = 45)
    private String alias;

    @NotNull
    @Size(max = 200)
    @Column(name = "domicilio", length = 200, nullable = false)
    private String domicilio;

    @NotNull
    @Size(max = 45)
    @Column(name = "colonia", length = 45, nullable = false)
    private String colonia;

    @Size(max = 45)
    @Column(name = "localidad", length = 45)
    private String localidad;

    @NotNull
    @Size(max = 45)
    @Column(name = "estado", length = 45, nullable = false)
    private String estado;

    @NotNull
    @Size(max = 45)
    @Column(name = "pais", length = 45, nullable = false)
    private String pais;

    @Size(max = 20)
    @Column(name = "latitud", length = 20)
    private String latitud;

    @Size(max = 20)
    @Column(name = "longitud", length = 20)
    private String longitud;

    @NotNull
    @Column(name = "cp", nullable = false)
    private Integer cp;

    @NotNull
    @Size(max = 45)
    @Column(name = "telefono", length = 45, nullable = false)
    private String telefono;

    @Size(max = 45)
    @Column(name = "email", length = 45)
    private String email;

    @NotNull
    @Size(max = 45)
    @Column(name = "rfc", length = 45, nullable = false)
    private String rfc;

    @NotNull
    @Column(name = "inicio", nullable = false)
    private Instant inicio;

    @Column(name = "fin")
    private Instant fin;

    @NotNull
    @Size(max = 45)
    @Column(name = "cargo", length = 45, nullable = false)
    private String cargo;

    @Size(max = 300)
    @Column(name = "comentario", length = 300)
    private String comentario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "personals", allowSetters = true)
    private Area area;

    @ManyToOne
    @JsonIgnoreProperties(value = "personals", allowSetters = true)
    private Dummy dummy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Personal nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public Personal apellido1(String apellido1) {
        this.apellido1 = apellido1;
        return this;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public Personal apellido2(String apellido2) {
        this.apellido2 = apellido2;
        return this;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getAlias() {
        return alias;
    }

    public Personal alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public Personal domicilio(String domicilio) {
        this.domicilio = domicilio;
        return this;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getColonia() {
        return colonia;
    }

    public Personal colonia(String colonia) {
        this.colonia = colonia;
        return this;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Personal localidad(String localidad) {
        this.localidad = localidad;
        return this;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getEstado() {
        return estado;
    }

    public Personal estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public Personal pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getLatitud() {
        return latitud;
    }

    public Personal latitud(String latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public Personal longitud(String longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Integer getCp() {
        return cp;
    }

    public Personal cp(Integer cp) {
        this.cp = cp;
        return this;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getTelefono() {
        return telefono;
    }

    public Personal telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public Personal email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRfc() {
        return rfc;
    }

    public Personal rfc(String rfc) {
        this.rfc = rfc;
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Instant getInicio() {
        return inicio;
    }

    public Personal inicio(Instant inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(Instant inicio) {
        this.inicio = inicio;
    }

    public Instant getFin() {
        return fin;
    }

    public Personal fin(Instant fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public String getCargo() {
        return cargo;
    }

    public Personal cargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getComentario() {
        return comentario;
    }

    public Personal comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Area getArea() {
        return area;
    }

    public Personal area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Dummy getDummy() {
        return dummy;
    }

    public Personal dummy(Dummy dummy) {
        this.dummy = dummy;
        return this;
    }

    public void setDummy(Dummy dummy) {
        this.dummy = dummy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personal)) {
            return false;
        }
        return id != null && id.equals(((Personal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Personal{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido1='" + getApellido1() + "'" +
            ", apellido2='" + getApellido2() + "'" +
            ", alias='" + getAlias() + "'" +
            ", domicilio='" + getDomicilio() + "'" +
            ", colonia='" + getColonia() + "'" +
            ", localidad='" + getLocalidad() + "'" +
            ", estado='" + getEstado() + "'" +
            ", pais='" + getPais() + "'" +
            ", latitud='" + getLatitud() + "'" +
            ", longitud='" + getLongitud() + "'" +
            ", cp=" + getCp() +
            ", telefono='" + getTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            ", rfc='" + getRfc() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", comentario='" + getComentario() + "'" +
            "}";
    }
}
