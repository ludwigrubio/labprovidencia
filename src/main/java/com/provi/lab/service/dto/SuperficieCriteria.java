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
 * Criteria class for the {@link com.provi.lab.domain.Superficie} entity. This class is used
 * in {@link com.provi.lab.web.rest.SuperficieResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /superficies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SuperficieCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter superficie;

    private StringFilter descripcion;

    public SuperficieCriteria() {
    }

    public SuperficieCriteria(SuperficieCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.superficie = other.superficie == null ? null : other.superficie.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
    }

    @Override
    public SuperficieCriteria copy() {
        return new SuperficieCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSuperficie() {
        return superficie;
    }

    public void setSuperficie(StringFilter superficie) {
        this.superficie = superficie;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SuperficieCriteria that = (SuperficieCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(superficie, that.superficie) &&
            Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        superficie,
        descripcion
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SuperficieCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (superficie != null ? "superficie=" + superficie + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
            "}";
    }

}
