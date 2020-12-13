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

/**
 * Criteria class for the {@link com.provi.lab.domain.Proceso} entity. This class is used
 * in {@link com.provi.lab.web.rest.ProcesoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /procesos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcesoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter proceso;

    public ProcesoCriteria() {
    }

    public ProcesoCriteria(ProcesoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.proceso = other.proceso == null ? null : other.proceso.copy();
    }

    @Override
    public ProcesoCriteria copy() {
        return new ProcesoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProceso() {
        return proceso;
    }

    public void setProceso(StringFilter proceso) {
        this.proceso = proceso;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProcesoCriteria that = (ProcesoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(proceso, that.proceso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        proceso
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcesoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (proceso != null ? "proceso=" + proceso + ", " : "") +
            "}";
    }

}
