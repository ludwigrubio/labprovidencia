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
 * Criteria class for the {@link com.provi.lab.domain.FQMantequilla} entity. This class is used
 * in {@link com.provi.lab.web.rest.FQMantequillaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fq-mantequillas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FQMantequillaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter fecha;

    private StringFilter lote;

    private DoubleFilter ph;

    private DoubleFilter humedad;

    private StringFilter observaciones;

    private LongFilter areaId;

    private LongFilter productoId;

    private LongFilter analistaId;

    private LongFilter proveedorId;

    public FQMantequillaCriteria() {
    }

    public FQMantequillaCriteria(FQMantequillaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.lote = other.lote == null ? null : other.lote.copy();
        this.ph = other.ph == null ? null : other.ph.copy();
        this.humedad = other.humedad == null ? null : other.humedad.copy();
        this.observaciones = other.observaciones == null ? null : other.observaciones.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.productoId = other.productoId == null ? null : other.productoId.copy();
        this.analistaId = other.analistaId == null ? null : other.analistaId.copy();
        this.proveedorId = other.proveedorId == null ? null : other.proveedorId.copy();
    }

    @Override
    public FQMantequillaCriteria copy() {
        return new FQMantequillaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getFecha() {
        return fecha;
    }

    public void setFecha(InstantFilter fecha) {
        this.fecha = fecha;
    }

    public StringFilter getLote() {
        return lote;
    }

    public void setLote(StringFilter lote) {
        this.lote = lote;
    }

    public DoubleFilter getPh() {
        return ph;
    }

    public void setPh(DoubleFilter ph) {
        this.ph = ph;
    }

    public DoubleFilter getHumedad() {
        return humedad;
    }

    public void setHumedad(DoubleFilter humedad) {
        this.humedad = humedad;
    }

    public StringFilter getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(StringFilter observaciones) {
        this.observaciones = observaciones;
    }

    public LongFilter getAreaId() {
        return areaId;
    }

    public void setAreaId(LongFilter areaId) {
        this.areaId = areaId;
    }

    public LongFilter getProductoId() {
        return productoId;
    }

    public void setProductoId(LongFilter productoId) {
        this.productoId = productoId;
    }

    public LongFilter getAnalistaId() {
        return analistaId;
    }

    public void setAnalistaId(LongFilter analistaId) {
        this.analistaId = analistaId;
    }

    public LongFilter getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(LongFilter proveedorId) {
        this.proveedorId = proveedorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FQMantequillaCriteria that = (FQMantequillaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(lote, that.lote) &&
            Objects.equals(ph, that.ph) &&
            Objects.equals(humedad, that.humedad) &&
            Objects.equals(observaciones, that.observaciones) &&
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(productoId, that.productoId) &&
            Objects.equals(analistaId, that.analistaId) &&
            Objects.equals(proveedorId, that.proveedorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fecha,
        lote,
        ph,
        humedad,
        observaciones,
        areaId,
        productoId,
        analistaId,
        proveedorId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FQMantequillaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (lote != null ? "lote=" + lote + ", " : "") +
                (ph != null ? "ph=" + ph + ", " : "") +
                (humedad != null ? "humedad=" + humedad + ", " : "") +
                (observaciones != null ? "observaciones=" + observaciones + ", " : "") +
                (areaId != null ? "areaId=" + areaId + ", " : "") +
                (productoId != null ? "productoId=" + productoId + ", " : "") +
                (analistaId != null ? "analistaId=" + analistaId + ", " : "") +
                (proveedorId != null ? "proveedorId=" + proveedorId + ", " : "") +
            "}";
    }

}
