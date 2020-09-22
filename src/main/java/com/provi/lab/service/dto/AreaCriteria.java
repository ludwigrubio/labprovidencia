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
 * Criteria class for the {@link com.provi.lab.domain.Area} entity. This class is used
 * in {@link com.provi.lab.web.rest.AreaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /areas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AreaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter area;

    private StringFilter descripcion;

    public AreaCriteria() {
    }

    public AreaCriteria(AreaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
    }

    @Override
    public AreaCriteria copy() {
        return new AreaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getArea() {
        return area;
    }

    public void setArea(StringFilter area) {
        this.area = area;
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
        final AreaCriteria that = (AreaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(area, that.area) &&
            Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        area,
        descripcion
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (area != null ? "area=" + area + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
            "}";
    }

}
