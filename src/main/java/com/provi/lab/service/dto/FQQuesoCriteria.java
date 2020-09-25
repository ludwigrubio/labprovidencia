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
 * Criteria class for the {@link com.provi.lab.domain.FQQueso} entity. This class is used
 * in {@link com.provi.lab.web.rest.FQQuesoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fq-quesos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FQQuesoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter fecha;

    private StringFilter lote;

    private DoubleFilter humedad;

    private DoubleFilter ph;

    private IntegerFilter fundicion;

    private IntegerFilter presentacion;

    private InstantFilter caducidad;

    private IntegerFilter apariencia;

    private IntegerFilter sabor;

    private IntegerFilter color;

    private IntegerFilter olor;

    private IntegerFilter textura;

    private IntegerFilter hilado;

    private StringFilter observaciones;

    private LongFilter areaId;

    private LongFilter productoId;

    private LongFilter analistaId;

    private LongFilter proveedorId;

    public FQQuesoCriteria() {
    }

    public FQQuesoCriteria(FQQuesoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.lote = other.lote == null ? null : other.lote.copy();
        this.humedad = other.humedad == null ? null : other.humedad.copy();
        this.ph = other.ph == null ? null : other.ph.copy();
        this.fundicion = other.fundicion == null ? null : other.fundicion.copy();
        this.presentacion = other.presentacion == null ? null : other.presentacion.copy();
        this.caducidad = other.caducidad == null ? null : other.caducidad.copy();
        this.apariencia = other.apariencia == null ? null : other.apariencia.copy();
        this.sabor = other.sabor == null ? null : other.sabor.copy();
        this.color = other.color == null ? null : other.color.copy();
        this.olor = other.olor == null ? null : other.olor.copy();
        this.textura = other.textura == null ? null : other.textura.copy();
        this.hilado = other.hilado == null ? null : other.hilado.copy();
        this.observaciones = other.observaciones == null ? null : other.observaciones.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.productoId = other.productoId == null ? null : other.productoId.copy();
        this.analistaId = other.analistaId == null ? null : other.analistaId.copy();
        this.proveedorId = other.proveedorId == null ? null : other.proveedorId.copy();
    }

    @Override
    public FQQuesoCriteria copy() {
        return new FQQuesoCriteria(this);
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

    public DoubleFilter getHumedad() {
        return humedad;
    }

    public void setHumedad(DoubleFilter humedad) {
        this.humedad = humedad;
    }

    public DoubleFilter getPh() {
        return ph;
    }

    public void setPh(DoubleFilter ph) {
        this.ph = ph;
    }

    public IntegerFilter getFundicion() {
        return fundicion;
    }

    public void setFundicion(IntegerFilter fundicion) {
        this.fundicion = fundicion;
    }

    public IntegerFilter getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(IntegerFilter presentacion) {
        this.presentacion = presentacion;
    }

    public InstantFilter getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(InstantFilter caducidad) {
        this.caducidad = caducidad;
    }

    public IntegerFilter getApariencia() {
        return apariencia;
    }

    public void setApariencia(IntegerFilter apariencia) {
        this.apariencia = apariencia;
    }

    public IntegerFilter getSabor() {
        return sabor;
    }

    public void setSabor(IntegerFilter sabor) {
        this.sabor = sabor;
    }

    public IntegerFilter getColor() {
        return color;
    }

    public void setColor(IntegerFilter color) {
        this.color = color;
    }

    public IntegerFilter getOlor() {
        return olor;
    }

    public void setOlor(IntegerFilter olor) {
        this.olor = olor;
    }

    public IntegerFilter getTextura() {
        return textura;
    }

    public void setTextura(IntegerFilter textura) {
        this.textura = textura;
    }

    public IntegerFilter getHilado() {
        return hilado;
    }

    public void setHilado(IntegerFilter hilado) {
        this.hilado = hilado;
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
        final FQQuesoCriteria that = (FQQuesoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(lote, that.lote) &&
            Objects.equals(humedad, that.humedad) &&
            Objects.equals(ph, that.ph) &&
            Objects.equals(fundicion, that.fundicion) &&
            Objects.equals(presentacion, that.presentacion) &&
            Objects.equals(caducidad, that.caducidad) &&
            Objects.equals(apariencia, that.apariencia) &&
            Objects.equals(sabor, that.sabor) &&
            Objects.equals(color, that.color) &&
            Objects.equals(olor, that.olor) &&
            Objects.equals(textura, that.textura) &&
            Objects.equals(hilado, that.hilado) &&
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
        humedad,
        ph,
        fundicion,
        presentacion,
        caducidad,
        apariencia,
        sabor,
        color,
        olor,
        textura,
        hilado,
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
        return "FQQuesoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (lote != null ? "lote=" + lote + ", " : "") +
                (humedad != null ? "humedad=" + humedad + ", " : "") +
                (ph != null ? "ph=" + ph + ", " : "") +
                (fundicion != null ? "fundicion=" + fundicion + ", " : "") +
                (presentacion != null ? "presentacion=" + presentacion + ", " : "") +
                (caducidad != null ? "caducidad=" + caducidad + ", " : "") +
                (apariencia != null ? "apariencia=" + apariencia + ", " : "") +
                (sabor != null ? "sabor=" + sabor + ", " : "") +
                (color != null ? "color=" + color + ", " : "") +
                (olor != null ? "olor=" + olor + ", " : "") +
                (textura != null ? "textura=" + textura + ", " : "") +
                (hilado != null ? "hilado=" + hilado + ", " : "") +
                (observaciones != null ? "observaciones=" + observaciones + ", " : "") +
                (areaId != null ? "areaId=" + areaId + ", " : "") +
                (productoId != null ? "productoId=" + productoId + ", " : "") +
                (analistaId != null ? "analistaId=" + analistaId + ", " : "") +
                (proveedorId != null ? "proveedorId=" + proveedorId + ", " : "") +
            "}";
    }

}
