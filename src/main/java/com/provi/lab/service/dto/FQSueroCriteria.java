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
 * Criteria class for the {@link com.provi.lab.domain.FQSuero} entity. This class is used
 * in {@link com.provi.lab.web.rest.FQSueroResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fq-sueros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FQSueroCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter fecha;

    private StringFilter lote;

    private DoubleFilter acidez;

    private DoubleFilter temperatura;

    private DoubleFilter delvo;

    private DoubleFilter solidos;

    private DoubleFilter neutralizantes;

    private DoubleFilter ph;

    private DoubleFilter cloro;

    private DoubleFilter almidon;

    private StringFilter observaciones;

    private LongFilter areaId;

    private LongFilter productoId;

    private LongFilter analistaId;

    private LongFilter proveedorId;

    public FQSueroCriteria() {
    }

    public FQSueroCriteria(FQSueroCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.lote = other.lote == null ? null : other.lote.copy();
        this.acidez = other.acidez == null ? null : other.acidez.copy();
        this.temperatura = other.temperatura == null ? null : other.temperatura.copy();
        this.delvo = other.delvo == null ? null : other.delvo.copy();
        this.solidos = other.solidos == null ? null : other.solidos.copy();
        this.neutralizantes = other.neutralizantes == null ? null : other.neutralizantes.copy();
        this.ph = other.ph == null ? null : other.ph.copy();
        this.cloro = other.cloro == null ? null : other.cloro.copy();
        this.almidon = other.almidon == null ? null : other.almidon.copy();
        this.observaciones = other.observaciones == null ? null : other.observaciones.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.productoId = other.productoId == null ? null : other.productoId.copy();
        this.analistaId = other.analistaId == null ? null : other.analistaId.copy();
        this.proveedorId = other.proveedorId == null ? null : other.proveedorId.copy();
    }

    @Override
    public FQSueroCriteria copy() {
        return new FQSueroCriteria(this);
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

    public DoubleFilter getAcidez() {
        return acidez;
    }

    public void setAcidez(DoubleFilter acidez) {
        this.acidez = acidez;
    }

    public DoubleFilter getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(DoubleFilter temperatura) {
        this.temperatura = temperatura;
    }

    public DoubleFilter getDelvo() {
        return delvo;
    }

    public void setDelvo(DoubleFilter delvo) {
        this.delvo = delvo;
    }

    public DoubleFilter getSolidos() {
        return solidos;
    }

    public void setSolidos(DoubleFilter solidos) {
        this.solidos = solidos;
    }

    public DoubleFilter getNeutralizantes() {
        return neutralizantes;
    }

    public void setNeutralizantes(DoubleFilter neutralizantes) {
        this.neutralizantes = neutralizantes;
    }

    public DoubleFilter getPh() {
        return ph;
    }

    public void setPh(DoubleFilter ph) {
        this.ph = ph;
    }

    public DoubleFilter getCloro() {
        return cloro;
    }

    public void setCloro(DoubleFilter cloro) {
        this.cloro = cloro;
    }

    public DoubleFilter getAlmidon() {
        return almidon;
    }

    public void setAlmidon(DoubleFilter almidon) {
        this.almidon = almidon;
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
        final FQSueroCriteria that = (FQSueroCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(lote, that.lote) &&
            Objects.equals(acidez, that.acidez) &&
            Objects.equals(temperatura, that.temperatura) &&
            Objects.equals(delvo, that.delvo) &&
            Objects.equals(solidos, that.solidos) &&
            Objects.equals(neutralizantes, that.neutralizantes) &&
            Objects.equals(ph, that.ph) &&
            Objects.equals(cloro, that.cloro) &&
            Objects.equals(almidon, that.almidon) &&
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
        acidez,
        temperatura,
        delvo,
        solidos,
        neutralizantes,
        ph,
        cloro,
        almidon,
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
        return "FQSueroCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (lote != null ? "lote=" + lote + ", " : "") +
                (acidez != null ? "acidez=" + acidez + ", " : "") +
                (temperatura != null ? "temperatura=" + temperatura + ", " : "") +
                (delvo != null ? "delvo=" + delvo + ", " : "") +
                (solidos != null ? "solidos=" + solidos + ", " : "") +
                (neutralizantes != null ? "neutralizantes=" + neutralizantes + ", " : "") +
                (ph != null ? "ph=" + ph + ", " : "") +
                (cloro != null ? "cloro=" + cloro + ", " : "") +
                (almidon != null ? "almidon=" + almidon + ", " : "") +
                (observaciones != null ? "observaciones=" + observaciones + ", " : "") +
                (areaId != null ? "areaId=" + areaId + ", " : "") +
                (productoId != null ? "productoId=" + productoId + ", " : "") +
                (analistaId != null ? "analistaId=" + analistaId + ", " : "") +
                (proveedorId != null ? "proveedorId=" + proveedorId + ", " : "") +
            "}";
    }

}
