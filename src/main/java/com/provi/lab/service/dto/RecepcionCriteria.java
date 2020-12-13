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
 * Criteria class for the {@link com.provi.lab.domain.Recepcion} entity. This class is used
 * in {@link com.provi.lab.web.rest.RecepcionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /recepcions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RecepcionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter litros;

    private InstantFilter tiempo;

    private StringFilter turno;

    private DoubleFilter incentivoLT;

    private DoubleFilter incentivoT;

    private StringFilter tipoLeche;

    private StringFilter flete;

    private LongFilter proveedorId;

    public RecepcionCriteria() {
    }

    public RecepcionCriteria(RecepcionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.litros = other.litros == null ? null : other.litros.copy();
        this.tiempo = other.tiempo == null ? null : other.tiempo.copy();
        this.turno = other.turno == null ? null : other.turno.copy();
        this.incentivoLT = other.incentivoLT == null ? null : other.incentivoLT.copy();
        this.incentivoT = other.incentivoT == null ? null : other.incentivoT.copy();
        this.tipoLeche = other.tipoLeche == null ? null : other.tipoLeche.copy();
        this.flete = other.flete == null ? null : other.flete.copy();
        this.proveedorId = other.proveedorId == null ? null : other.proveedorId.copy();
    }

    @Override
    public RecepcionCriteria copy() {
        return new RecepcionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getLitros() {
        return litros;
    }

    public void setLitros(FloatFilter litros) {
        this.litros = litros;
    }

    public InstantFilter getTiempo() {
        return tiempo;
    }

    public void setTiempo(InstantFilter tiempo) {
        this.tiempo = tiempo;
    }

    public StringFilter getTurno() {
        return turno;
    }

    public void setTurno(StringFilter turno) {
        this.turno = turno;
    }

    public DoubleFilter getIncentivoLT() {
        return incentivoLT;
    }

    public void setIncentivoLT(DoubleFilter incentivoLT) {
        this.incentivoLT = incentivoLT;
    }

    public DoubleFilter getIncentivoT() {
        return incentivoT;
    }

    public void setIncentivoT(DoubleFilter incentivoT) {
        this.incentivoT = incentivoT;
    }

    public StringFilter getTipoLeche() {
        return tipoLeche;
    }

    public void setTipoLeche(StringFilter tipoLeche) {
        this.tipoLeche = tipoLeche;
    }

    public StringFilter getFlete() {
        return flete;
    }

    public void setFlete(StringFilter flete) {
        this.flete = flete;
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
        final RecepcionCriteria that = (RecepcionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(litros, that.litros) &&
            Objects.equals(tiempo, that.tiempo) &&
            Objects.equals(turno, that.turno) &&
            Objects.equals(incentivoLT, that.incentivoLT) &&
            Objects.equals(incentivoT, that.incentivoT) &&
            Objects.equals(tipoLeche, that.tipoLeche) &&
            Objects.equals(flete, that.flete) &&
            Objects.equals(proveedorId, that.proveedorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        litros,
        tiempo,
        turno,
        incentivoLT,
        incentivoT,
        tipoLeche,
        flete,
        proveedorId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecepcionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (litros != null ? "litros=" + litros + ", " : "") +
                (tiempo != null ? "tiempo=" + tiempo + ", " : "") +
                (turno != null ? "turno=" + turno + ", " : "") +
                (incentivoLT != null ? "incentivoLT=" + incentivoLT + ", " : "") +
                (incentivoT != null ? "incentivoT=" + incentivoT + ", " : "") +
                (tipoLeche != null ? "tipoLeche=" + tipoLeche + ", " : "") +
                (flete != null ? "flete=" + flete + ", " : "") +
                (proveedorId != null ? "proveedorId=" + proveedorId + ", " : "") +
            "}";
    }

}
