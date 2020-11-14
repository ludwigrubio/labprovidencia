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
 * Criteria class for the {@link com.provi.lab.domain.FQLeche} entity. This class is used
 * in {@link com.provi.lab.web.rest.FQLecheResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fq-leches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FQLecheCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter fecha;

    private StringFilter lote;

    private DoubleFilter acidez;

    private DoubleFilter temperatura;

    private DoubleFilter agua;

    private DoubleFilter crioscopia;

    private IntegerFilter antibiotico;

    private DoubleFilter delvo;

    private DoubleFilter grasa;

    private StringFilter solidos;

    private DoubleFilter densidad;

    private DoubleFilter lactosa;

    private DoubleFilter proteina;

    private DoubleFilter neutralizantes;

    private DoubleFilter adulterantes;

    private DoubleFilter reductasa;

    private DoubleFilter fosfatasa;

    private DoubleFilter ph;

    private DoubleFilter dummy1;

    private DoubleFilter dummy2;

    private DoubleFilter dummy3;

    private DoubleFilter dummy4;

    private DoubleFilter dummy5;

    private StringFilter observaciones;

    private LongFilter areaId;

    private LongFilter recepcionId;

    private LongFilter analistaId;

    private LongFilter proveedorId;

    private LongFilter contenedorId;

    public FQLecheCriteria() {
    }

    public FQLecheCriteria(FQLecheCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.lote = other.lote == null ? null : other.lote.copy();
        this.acidez = other.acidez == null ? null : other.acidez.copy();
        this.temperatura = other.temperatura == null ? null : other.temperatura.copy();
        this.agua = other.agua == null ? null : other.agua.copy();
        this.crioscopia = other.crioscopia == null ? null : other.crioscopia.copy();
        this.antibiotico = other.antibiotico == null ? null : other.antibiotico.copy();
        this.delvo = other.delvo == null ? null : other.delvo.copy();
        this.grasa = other.grasa == null ? null : other.grasa.copy();
        this.solidos = other.solidos == null ? null : other.solidos.copy();
        this.densidad = other.densidad == null ? null : other.densidad.copy();
        this.lactosa = other.lactosa == null ? null : other.lactosa.copy();
        this.proteina = other.proteina == null ? null : other.proteina.copy();
        this.neutralizantes = other.neutralizantes == null ? null : other.neutralizantes.copy();
        this.adulterantes = other.adulterantes == null ? null : other.adulterantes.copy();
        this.reductasa = other.reductasa == null ? null : other.reductasa.copy();
        this.fosfatasa = other.fosfatasa == null ? null : other.fosfatasa.copy();
        this.ph = other.ph == null ? null : other.ph.copy();
        this.dummy1 = other.dummy1 == null ? null : other.dummy1.copy();
        this.dummy2 = other.dummy2 == null ? null : other.dummy2.copy();
        this.dummy3 = other.dummy3 == null ? null : other.dummy3.copy();
        this.dummy4 = other.dummy4 == null ? null : other.dummy4.copy();
        this.dummy5 = other.dummy5 == null ? null : other.dummy5.copy();
        this.observaciones = other.observaciones == null ? null : other.observaciones.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.recepcionId = other.recepcionId == null ? null : other.recepcionId.copy();
        this.analistaId = other.analistaId == null ? null : other.analistaId.copy();
        this.proveedorId = other.proveedorId == null ? null : other.proveedorId.copy();
        this.contenedorId = other.contenedorId == null ? null : other.contenedorId.copy();
    }

    @Override
    public FQLecheCriteria copy() {
        return new FQLecheCriteria(this);
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

    public DoubleFilter getAgua() {
        return agua;
    }

    public void setAgua(DoubleFilter agua) {
        this.agua = agua;
    }

    public DoubleFilter getCrioscopia() {
        return crioscopia;
    }

    public void setCrioscopia(DoubleFilter crioscopia) {
        this.crioscopia = crioscopia;
    }

    public IntegerFilter getAntibiotico() {
        return antibiotico;
    }

    public void setAntibiotico(IntegerFilter antibiotico) {
        this.antibiotico = antibiotico;
    }

    public DoubleFilter getDelvo() {
        return delvo;
    }

    public void setDelvo(DoubleFilter delvo) {
        this.delvo = delvo;
    }

    public DoubleFilter getGrasa() {
        return grasa;
    }

    public void setGrasa(DoubleFilter grasa) {
        this.grasa = grasa;
    }

    public StringFilter getSolidos() {
        return solidos;
    }

    public void setSolidos(StringFilter solidos) {
        this.solidos = solidos;
    }

    public DoubleFilter getDensidad() {
        return densidad;
    }

    public void setDensidad(DoubleFilter densidad) {
        this.densidad = densidad;
    }

    public DoubleFilter getLactosa() {
        return lactosa;
    }

    public void setLactosa(DoubleFilter lactosa) {
        this.lactosa = lactosa;
    }

    public DoubleFilter getProteina() {
        return proteina;
    }

    public void setProteina(DoubleFilter proteina) {
        this.proteina = proteina;
    }

    public DoubleFilter getNeutralizantes() {
        return neutralizantes;
    }

    public void setNeutralizantes(DoubleFilter neutralizantes) {
        this.neutralizantes = neutralizantes;
    }

    public DoubleFilter getAdulterantes() {
        return adulterantes;
    }

    public void setAdulterantes(DoubleFilter adulterantes) {
        this.adulterantes = adulterantes;
    }

    public DoubleFilter getReductasa() {
        return reductasa;
    }

    public void setReductasa(DoubleFilter reductasa) {
        this.reductasa = reductasa;
    }

    public DoubleFilter getFosfatasa() {
        return fosfatasa;
    }

    public void setFosfatasa(DoubleFilter fosfatasa) {
        this.fosfatasa = fosfatasa;
    }

    public DoubleFilter getPh() {
        return ph;
    }

    public void setPh(DoubleFilter ph) {
        this.ph = ph;
    }

    public DoubleFilter getDummy1() {
        return dummy1;
    }

    public void setDummy1(DoubleFilter dummy1) {
        this.dummy1 = dummy1;
    }

    public DoubleFilter getDummy2() {
        return dummy2;
    }

    public void setDummy2(DoubleFilter dummy2) {
        this.dummy2 = dummy2;
    }

    public DoubleFilter getDummy3() {
        return dummy3;
    }

    public void setDummy3(DoubleFilter dummy3) {
        this.dummy3 = dummy3;
    }

    public DoubleFilter getDummy4() {
        return dummy4;
    }

    public void setDummy4(DoubleFilter dummy4) {
        this.dummy4 = dummy4;
    }

    public DoubleFilter getDummy5() {
        return dummy5;
    }

    public void setDummy5(DoubleFilter dummy5) {
        this.dummy5 = dummy5;
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

    public LongFilter getRecepcionId() {
        return recepcionId;
    }

    public void setRecepcionId(LongFilter recepcionId) {
        this.recepcionId = recepcionId;
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

    public LongFilter getContenedorId() {
        return contenedorId;
    }

    public void setContenedorId(LongFilter contenedorId) {
        this.contenedorId = contenedorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FQLecheCriteria that = (FQLecheCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(lote, that.lote) &&
            Objects.equals(acidez, that.acidez) &&
            Objects.equals(temperatura, that.temperatura) &&
            Objects.equals(agua, that.agua) &&
            Objects.equals(crioscopia, that.crioscopia) &&
            Objects.equals(antibiotico, that.antibiotico) &&
            Objects.equals(delvo, that.delvo) &&
            Objects.equals(grasa, that.grasa) &&
            Objects.equals(solidos, that.solidos) &&
            Objects.equals(densidad, that.densidad) &&
            Objects.equals(lactosa, that.lactosa) &&
            Objects.equals(proteina, that.proteina) &&
            Objects.equals(neutralizantes, that.neutralizantes) &&
            Objects.equals(adulterantes, that.adulterantes) &&
            Objects.equals(reductasa, that.reductasa) &&
            Objects.equals(fosfatasa, that.fosfatasa) &&
            Objects.equals(ph, that.ph) &&
            Objects.equals(dummy1, that.dummy1) &&
            Objects.equals(dummy2, that.dummy2) &&
            Objects.equals(dummy3, that.dummy3) &&
            Objects.equals(dummy4, that.dummy4) &&
            Objects.equals(dummy5, that.dummy5) &&
            Objects.equals(observaciones, that.observaciones) &&
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(recepcionId, that.recepcionId) &&
            Objects.equals(analistaId, that.analistaId) &&
            Objects.equals(proveedorId, that.proveedorId) &&
            Objects.equals(contenedorId, that.contenedorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fecha,
        lote,
        acidez,
        temperatura,
        agua,
        crioscopia,
        antibiotico,
        delvo,
        grasa,
        solidos,
        densidad,
        lactosa,
        proteina,
        neutralizantes,
        adulterantes,
        reductasa,
        fosfatasa,
        ph,
        dummy1,
        dummy2,
        dummy3,
        dummy4,
        dummy5,
        observaciones,
        areaId,
        recepcionId,
        analistaId,
        proveedorId,
        contenedorId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FQLecheCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (lote != null ? "lote=" + lote + ", " : "") +
                (acidez != null ? "acidez=" + acidez + ", " : "") +
                (temperatura != null ? "temperatura=" + temperatura + ", " : "") +
                (agua != null ? "agua=" + agua + ", " : "") +
                (crioscopia != null ? "crioscopia=" + crioscopia + ", " : "") +
                (antibiotico != null ? "antibiotico=" + antibiotico + ", " : "") +
                (delvo != null ? "delvo=" + delvo + ", " : "") +
                (grasa != null ? "grasa=" + grasa + ", " : "") +
                (solidos != null ? "solidos=" + solidos + ", " : "") +
                (densidad != null ? "densidad=" + densidad + ", " : "") +
                (lactosa != null ? "lactosa=" + lactosa + ", " : "") +
                (proteina != null ? "proteina=" + proteina + ", " : "") +
                (neutralizantes != null ? "neutralizantes=" + neutralizantes + ", " : "") +
                (adulterantes != null ? "adulterantes=" + adulterantes + ", " : "") +
                (reductasa != null ? "reductasa=" + reductasa + ", " : "") +
                (fosfatasa != null ? "fosfatasa=" + fosfatasa + ", " : "") +
                (ph != null ? "ph=" + ph + ", " : "") +
                (dummy1 != null ? "dummy1=" + dummy1 + ", " : "") +
                (dummy2 != null ? "dummy2=" + dummy2 + ", " : "") +
                (dummy3 != null ? "dummy3=" + dummy3 + ", " : "") +
                (dummy4 != null ? "dummy4=" + dummy4 + ", " : "") +
                (dummy5 != null ? "dummy5=" + dummy5 + ", " : "") +
                (observaciones != null ? "observaciones=" + observaciones + ", " : "") +
                (areaId != null ? "areaId=" + areaId + ", " : "") +
                (recepcionId != null ? "recepcionId=" + recepcionId + ", " : "") +
                (analistaId != null ? "analistaId=" + analistaId + ", " : "") +
                (proveedorId != null ? "proveedorId=" + proveedorId + ", " : "") +
                (contenedorId != null ? "contenedorId=" + contenedorId + ", " : "") +
            "}";
    }

}
