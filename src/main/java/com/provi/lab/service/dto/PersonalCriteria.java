package com.provi.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.provi.lab.domain.Personal} entity. This class is used
 * in {@link com.provi.lab.web.rest.PersonalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /personals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PersonalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter apellido1;

    private StringFilter apellido2;

    private StringFilter alias;

    private StringFilter domicilio;

    private StringFilter colonia;

    private StringFilter localidad;

    private StringFilter estado;

    private StringFilter pais;

    private StringFilter latitud;

    private StringFilter longitud;

    private IntegerFilter cp;

    private StringFilter telefono;

    private StringFilter email;

    private StringFilter rfc;

    private InstantFilter inicio;

    private InstantFilter fin;

    private StringFilter cargo;

    private StringFilter comentario;

    private LongFilter relacionId;

    public PersonalCriteria() {
    }

    public PersonalCriteria(PersonalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.apellido1 = other.apellido1 == null ? null : other.apellido1.copy();
        this.apellido2 = other.apellido2 == null ? null : other.apellido2.copy();
        this.alias = other.alias == null ? null : other.alias.copy();
        this.domicilio = other.domicilio == null ? null : other.domicilio.copy();
        this.colonia = other.colonia == null ? null : other.colonia.copy();
        this.localidad = other.localidad == null ? null : other.localidad.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.pais = other.pais == null ? null : other.pais.copy();
        this.latitud = other.latitud == null ? null : other.latitud.copy();
        this.longitud = other.longitud == null ? null : other.longitud.copy();
        this.cp = other.cp == null ? null : other.cp.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.rfc = other.rfc == null ? null : other.rfc.copy();
        this.inicio = other.inicio == null ? null : other.inicio.copy();
        this.fin = other.fin == null ? null : other.fin.copy();
        this.cargo = other.cargo == null ? null : other.cargo.copy();
        this.comentario = other.comentario == null ? null : other.comentario.copy();
        this.relacionId = other.relacionId == null ? null : other.relacionId.copy();
    }

    @Override
    public PersonalCriteria copy() {
        return new PersonalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getApellido1() {
        return apellido1;
    }

    public void setApellido1(StringFilter apellido1) {
        this.apellido1 = apellido1;
    }

    public StringFilter getApellido2() {
        return apellido2;
    }

    public void setApellido2(StringFilter apellido2) {
        this.apellido2 = apellido2;
    }

    public StringFilter getAlias() {
        return alias;
    }

    public void setAlias(StringFilter alias) {
        this.alias = alias;
    }

    public StringFilter getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(StringFilter domicilio) {
        this.domicilio = domicilio;
    }

    public StringFilter getColonia() {
        return colonia;
    }

    public void setColonia(StringFilter colonia) {
        this.colonia = colonia;
    }

    public StringFilter getLocalidad() {
        return localidad;
    }

    public void setLocalidad(StringFilter localidad) {
        this.localidad = localidad;
    }

    public StringFilter getEstado() {
        return estado;
    }

    public void setEstado(StringFilter estado) {
        this.estado = estado;
    }

    public StringFilter getPais() {
        return pais;
    }

    public void setPais(StringFilter pais) {
        this.pais = pais;
    }

    public StringFilter getLatitud() {
        return latitud;
    }

    public void setLatitud(StringFilter latitud) {
        this.latitud = latitud;
    }

    public StringFilter getLongitud() {
        return longitud;
    }

    public void setLongitud(StringFilter longitud) {
        this.longitud = longitud;
    }

    public IntegerFilter getCp() {
        return cp;
    }

    public void setCp(IntegerFilter cp) {
        this.cp = cp;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getRfc() {
        return rfc;
    }

    public void setRfc(StringFilter rfc) {
        this.rfc = rfc;
    }

    public InstantFilter getInicio() {
        return inicio;
    }

    public void setInicio(InstantFilter inicio) {
        this.inicio = inicio;
    }

    public InstantFilter getFin() {
        return fin;
    }

    public void setFin(InstantFilter fin) {
        this.fin = fin;
    }

    public StringFilter getCargo() {
        return cargo;
    }

    public void setCargo(StringFilter cargo) {
        this.cargo = cargo;
    }

    public StringFilter getComentario() {
        return comentario;
    }

    public void setComentario(StringFilter comentario) {
        this.comentario = comentario;
    }

    public LongFilter getRelacionId() {
        return relacionId;
    }

    public void setRelacionId(LongFilter relacionId) {
        this.relacionId = relacionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonalCriteria that = (PersonalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(apellido1, that.apellido1) &&
            Objects.equals(apellido2, that.apellido2) &&
            Objects.equals(alias, that.alias) &&
            Objects.equals(domicilio, that.domicilio) &&
            Objects.equals(colonia, that.colonia) &&
            Objects.equals(localidad, that.localidad) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(pais, that.pais) &&
            Objects.equals(latitud, that.latitud) &&
            Objects.equals(longitud, that.longitud) &&
            Objects.equals(cp, that.cp) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(email, that.email) &&
            Objects.equals(rfc, that.rfc) &&
            Objects.equals(inicio, that.inicio) &&
            Objects.equals(fin, that.fin) &&
            Objects.equals(cargo, that.cargo) &&
            Objects.equals(comentario, that.comentario) &&
            Objects.equals(relacionId, that.relacionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        apellido1,
        apellido2,
        alias,
        domicilio,
        colonia,
        localidad,
        estado,
        pais,
        latitud,
        longitud,
        cp,
        telefono,
        email,
        rfc,
        inicio,
        fin,
        cargo,
        comentario,
        relacionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (apellido1 != null ? "apellido1=" + apellido1 + ", " : "") +
                (apellido2 != null ? "apellido2=" + apellido2 + ", " : "") +
                (alias != null ? "alias=" + alias + ", " : "") +
                (domicilio != null ? "domicilio=" + domicilio + ", " : "") +
                (colonia != null ? "colonia=" + colonia + ", " : "") +
                (localidad != null ? "localidad=" + localidad + ", " : "") +
                (estado != null ? "estado=" + estado + ", " : "") +
                (pais != null ? "pais=" + pais + ", " : "") +
                (latitud != null ? "latitud=" + latitud + ", " : "") +
                (longitud != null ? "longitud=" + longitud + ", " : "") +
                (cp != null ? "cp=" + cp + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (rfc != null ? "rfc=" + rfc + ", " : "") +
                (inicio != null ? "inicio=" + inicio + ", " : "") +
                (fin != null ? "fin=" + fin + ", " : "") +
                (cargo != null ? "cargo=" + cargo + ", " : "") +
                (comentario != null ? "comentario=" + comentario + ", " : "") +
                (relacionId != null ? "relacionId=" + relacionId + ", " : "") +
            "}";
    }

}
