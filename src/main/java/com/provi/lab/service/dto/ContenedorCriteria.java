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
 * Criteria class for the {@link com.provi.lab.domain.Contenedor} entity. This class is used
 * in {@link com.provi.lab.web.rest.ContenedorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contenedors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContenedorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter contenedor;

    public ContenedorCriteria() {
    }

    public ContenedorCriteria(ContenedorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.contenedor = other.contenedor == null ? null : other.contenedor.copy();
    }

    @Override
    public ContenedorCriteria copy() {
        return new ContenedorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getContenedor() {
        return contenedor;
    }

    public void setContenedor(StringFilter contenedor) {
        this.contenedor = contenedor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContenedorCriteria that = (ContenedorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(contenedor, that.contenedor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        contenedor
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContenedorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (contenedor != null ? "contenedor=" + contenedor + ", " : "") +
            "}";
    }

}
