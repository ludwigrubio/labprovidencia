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
 * Criteria class for the {@link com.provi.lab.domain.LogLactoEscan} entity. This class is used
 * in {@link com.provi.lab.web.rest.LogLactoEscanResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /log-lacto-escans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LogLactoEscanCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter tipo;

    private InstantFilter fecha;

    private StringFilter nombreArchivo;

    private IntegerFilter numeroFila;

    private StringFilter mensajeError;

    public LogLactoEscanCriteria() {
    }

    public LogLactoEscanCriteria(LogLactoEscanCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.nombreArchivo = other.nombreArchivo == null ? null : other.nombreArchivo.copy();
        this.numeroFila = other.numeroFila == null ? null : other.numeroFila.copy();
        this.mensajeError = other.mensajeError == null ? null : other.mensajeError.copy();
    }

    @Override
    public LogLactoEscanCriteria copy() {
        return new LogLactoEscanCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getTipo() {
        return tipo;
    }

    public void setTipo(IntegerFilter tipo) {
        this.tipo = tipo;
    }

    public InstantFilter getFecha() {
        return fecha;
    }

    public void setFecha(InstantFilter fecha) {
        this.fecha = fecha;
    }

    public StringFilter getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(StringFilter nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public IntegerFilter getNumeroFila() {
        return numeroFila;
    }

    public void setNumeroFila(IntegerFilter numeroFila) {
        this.numeroFila = numeroFila;
    }

    public StringFilter getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(StringFilter mensajeError) {
        this.mensajeError = mensajeError;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LogLactoEscanCriteria that = (LogLactoEscanCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(nombreArchivo, that.nombreArchivo) &&
            Objects.equals(numeroFila, that.numeroFila) &&
            Objects.equals(mensajeError, that.mensajeError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipo,
        fecha,
        nombreArchivo,
        numeroFila,
        mensajeError
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogLactoEscanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (nombreArchivo != null ? "nombreArchivo=" + nombreArchivo + ", " : "") +
                (numeroFila != null ? "numeroFila=" + numeroFila + ", " : "") +
                (mensajeError != null ? "mensajeError=" + mensajeError + ", " : "") +
            "}";
    }

}
