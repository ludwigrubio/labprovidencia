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
 * Criteria class for the {@link com.provi.lab.domain.Cultivo} entity. This class is used
 * in {@link com.provi.lab.web.rest.CultivoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cultivos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CultivoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cultivo;

    public CultivoCriteria() {
    }

    public CultivoCriteria(CultivoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cultivo = other.cultivo == null ? null : other.cultivo.copy();
    }

    @Override
    public CultivoCriteria copy() {
        return new CultivoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCultivo() {
        return cultivo;
    }

    public void setCultivo(StringFilter cultivo) {
        this.cultivo = cultivo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CultivoCriteria that = (CultivoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cultivo, that.cultivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cultivo
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CultivoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cultivo != null ? "cultivo=" + cultivo + ", " : "") +
            "}";
    }

}
