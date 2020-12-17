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
 * Criteria class for the {@link com.provi.lab.domain.PruebaMicro} entity. This class is used
 * in {@link com.provi.lab.web.rest.PruebaMicroResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /prueba-micros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PruebaMicroCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter tipodeMuestra;

    private StringFilter lote;

    private InstantFilter inicio;

    private InstantFilter fin;

    private IntegerFilter resultado;

    private IntegerFilter unidades;

    private StringFilter observaciones;

    private LongFilter areaId;

    private LongFilter cultivoId;

    private LongFilter superficieId;

    private LongFilter productoId;

    private LongFilter analistaId;

    private LongFilter proveedorId;

    private LongFilter procesoId;

    public PruebaMicroCriteria() {
    }

    public PruebaMicroCriteria(PruebaMicroCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipodeMuestra = other.tipodeMuestra == null ? null : other.tipodeMuestra.copy();
        this.lote = other.lote == null ? null : other.lote.copy();
        this.inicio = other.inicio == null ? null : other.inicio.copy();
        this.fin = other.fin == null ? null : other.fin.copy();
        this.resultado = other.resultado == null ? null : other.resultado.copy();
        this.unidades = other.unidades == null ? null : other.unidades.copy();
        this.observaciones = other.observaciones == null ? null : other.observaciones.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.cultivoId = other.cultivoId == null ? null : other.cultivoId.copy();
        this.superficieId = other.superficieId == null ? null : other.superficieId.copy();
        this.productoId = other.productoId == null ? null : other.productoId.copy();
        this.analistaId = other.analistaId == null ? null : other.analistaId.copy();
        this.proveedorId = other.proveedorId == null ? null : other.proveedorId.copy();
        this.procesoId = other.procesoId == null ? null : other.procesoId.copy();
    }

    @Override
    public PruebaMicroCriteria copy() {
        return new PruebaMicroCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getTipodeMuestra() {
        return tipodeMuestra;
    }

    public void setTipodeMuestra(IntegerFilter tipodeMuestra) {
        this.tipodeMuestra = tipodeMuestra;
    }

    public StringFilter getLote() {
        return lote;
    }

    public void setLote(StringFilter lote) {
        this.lote = lote;
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

    public IntegerFilter getResultado() {
        return resultado;
    }

    public void setResultado(IntegerFilter resultado) {
        this.resultado = resultado;
    }

    public IntegerFilter getUnidades() {
        return unidades;
    }

    public void setUnidades(IntegerFilter unidades) {
        this.unidades = unidades;
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

    public LongFilter getCultivoId() {
        return cultivoId;
    }

    public void setCultivoId(LongFilter cultivoId) {
        this.cultivoId = cultivoId;
    }

    public LongFilter getSuperficieId() {
        return superficieId;
    }

    public void setSuperficieId(LongFilter superficieId) {
        this.superficieId = superficieId;
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

    public LongFilter getProcesoId() {
        return procesoId;
    }

    public void setProcesoId(LongFilter procesoId) {
        this.procesoId = procesoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PruebaMicroCriteria that = (PruebaMicroCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipodeMuestra, that.tipodeMuestra) &&
            Objects.equals(lote, that.lote) &&
            Objects.equals(inicio, that.inicio) &&
            Objects.equals(fin, that.fin) &&
            Objects.equals(resultado, that.resultado) &&
            Objects.equals(unidades, that.unidades) &&
            Objects.equals(observaciones, that.observaciones) &&
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(cultivoId, that.cultivoId) &&
            Objects.equals(superficieId, that.superficieId) &&
            Objects.equals(productoId, that.productoId) &&
            Objects.equals(analistaId, that.analistaId) &&
            Objects.equals(proveedorId, that.proveedorId) &&
            Objects.equals(procesoId, that.procesoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipodeMuestra,
        lote,
        inicio,
        fin,
        resultado,
        unidades,
        observaciones,
        areaId,
        cultivoId,
        superficieId,
        productoId,
        analistaId,
        proveedorId,
        procesoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PruebaMicroCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipodeMuestra != null ? "tipodeMuestra=" + tipodeMuestra + ", " : "") +
                (lote != null ? "lote=" + lote + ", " : "") +
                (inicio != null ? "inicio=" + inicio + ", " : "") +
                (fin != null ? "fin=" + fin + ", " : "") +
                (resultado != null ? "resultado=" + resultado + ", " : "") +
                (unidades != null ? "unidades=" + unidades + ", " : "") +
                (observaciones != null ? "observaciones=" + observaciones + ", " : "") +
                (areaId != null ? "areaId=" + areaId + ", " : "") +
                (cultivoId != null ? "cultivoId=" + cultivoId + ", " : "") +
                (superficieId != null ? "superficieId=" + superficieId + ", " : "") +
                (productoId != null ? "productoId=" + productoId + ", " : "") +
                (analistaId != null ? "analistaId=" + analistaId + ", " : "") +
                (proveedorId != null ? "proveedorId=" + proveedorId + ", " : "") +
                (procesoId != null ? "procesoId=" + procesoId + ", " : "") +
            "}";
    }

}
